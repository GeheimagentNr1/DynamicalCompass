package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class DynamicalCompassPropertyFunction implements ClampedItemPropertyFunction {
	
	
	private final DynamicalCompassWobble wobble = new DynamicalCompassWobble();
	
	private final DynamicalCompassWobble wobbleRandom = new DynamicalCompassWobble();
	
	@Override
	public float unclampedCall(
		@Nonnull ItemStack stack,
		@Nullable ClientLevel clientLevel,
		@Nullable LivingEntity livingEntity,
		int seed ) {
		
		Entity entity = livingEntity != null ? livingEntity : stack.getEntityRepresentation();
		if( entity == null ) {
			return 0.0F;
		} else {
			Level level = clientLevel;
			if( level == null ) {
				level = entity.level;
			}
			
			BlockPos blockpos = DynamicalCompassItemStackHelper.isDimensionEqual( stack, level )
				? DynamicalCompassItemStackHelper.getDestinationPos( stack )
				: null;
			long gameTime = level.getGameTime();
			if( blockpos != null &&
				!( entity.position()
					.distanceToSqr( blockpos.getX() + 0.5D, entity.position().y(), blockpos.getZ() + 0.5D )
					< 1.0E-5F ) ) {
				boolean flag = livingEntity instanceof Player && ( (Player)livingEntity ).isLocalPlayer();
				double rota = 0.0D;
				if( flag ) {
					rota = livingEntity.getYRot();
				} else {
					if( entity instanceof ItemFrame ) {
						rota = getFrameRotation( (ItemFrame)entity );
					} else {
						if( entity instanceof ItemEntity ) {
							rota = 180.0F -
								( (ItemEntity)entity ).getSpin( 0.5F ) / ( (float)Math.PI * 2.0F ) * 360.0F;
						} else {
							if( livingEntity != null ) {
								rota = livingEntity.yBodyRot;
							}
						}
					}
				}
				
				rota = Mth.positiveModulo( rota / 360.0D, 1.0D );
				double angleTo = getAngleTo( Vec3.atCenterOf( blockpos ), entity ) / ( (float)Math.PI * 2.0F );
				double rotation;
				if( flag ) {
					if( wobble.shouldUpdate( gameTime ) ) {
						wobble.update( gameTime, 0.5D - ( rota - 0.25D ) );
					}
					
					rotation = angleTo + wobble.getRotation();
				} else {
					rotation = 0.5D - ( rota - 0.25D - angleTo );
				}
				
				return Mth.positiveModulo( (float)rotation, 1.0F );
			} else {
				if( wobbleRandom.shouldUpdate( gameTime ) ) {
					wobbleRandom.update( gameTime, StrictMath.random() );
				}
				return Mth.positiveModulo(
					(float)( wobbleRandom.getRotation() + ( hash( seed ) / 2.14748365E9F ) ),
					1.0F
				);
			}
		}
	}
	
	private int hash( int seed ) {
		
		return seed * 1327217883;
	}
	
	@OnlyIn( Dist.CLIENT )
	private double getFrameRotation( ItemFrame itemFrame ) {
		
		Direction direction = itemFrame.getDirection();
		int rota = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getStep() : 0;
		return Mth.wrapDegrees( 180 + direction.get2DDataValue() * 90 + itemFrame.getRotation() * 45 + rota );
	}
	
	@OnlyIn( Dist.CLIENT )
	private double getAngleTo( Vec3 vec3, Entity entity ) {
		
		return StrictMath.atan2( vec3.z() - entity.getZ(), vec3.x() - entity.getX() );
	}
}
