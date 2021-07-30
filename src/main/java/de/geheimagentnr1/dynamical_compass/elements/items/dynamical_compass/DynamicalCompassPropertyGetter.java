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


public class DynamicalCompassPropertyGetter implements ClampedItemPropertyFunction {
	
	
	private final DynamicalCompassPropertyGetter.CompassWobble wobble =
		new DynamicalCompassPropertyGetter.CompassWobble();
	
	private final DynamicalCompassPropertyGetter.CompassWobble wobbleRandom =
		new DynamicalCompassPropertyGetter.CompassWobble();
	
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
			long i = level.getGameTime();
			if( blockpos != null && !( entity.position().distanceToSqr(
				blockpos.getX() + 0.5D,
				entity.position().y(),
				blockpos.getZ() + 0.5D
			) < 1.0E-5F ) ) {
				boolean flag = livingEntity instanceof Player && ( (Player)livingEntity ).isLocalPlayer();
				double d1 = 0.0D;
				if( flag ) {
					d1 = livingEntity.getYRot();
				} else {
					if( entity instanceof ItemFrame ) {
						d1 = getFrameRotation( (ItemFrame)entity );
					} else {
						if( entity instanceof ItemEntity ) {
							d1 = 180.0F -
								( (ItemEntity)entity ).getSpin( 0.5F ) / ( (float)Math.PI * 2.0F ) * 360.0F;
						} else {
							if( livingEntity != null ) {
								d1 = livingEntity.yBodyRot;
							}
						}
					}
				}
				
				d1 = Mth.positiveModulo( d1 / 360.0D, 1.0D );
				double d2 = getAngleTo( Vec3.atCenterOf( blockpos ), entity ) / ( (float)Math.PI * 2.0F );
				double d3;
				if( flag ) {
					if( wobble.shouldUpdate( i ) ) {
						wobble.update( i, 0.5D - ( d1 - 0.25D ) );
					}
					
					d3 = d2 + wobble.rotation;
				} else {
					d3 = 0.5D - ( d1 - 0.25D - d2 );
				}
				
				return Mth.positiveModulo( (float)d3, 1.0F );
			} else {
				if( wobbleRandom.shouldUpdate( i ) ) {
					wobbleRandom.update( i, StrictMath.random() );
				}
				
				double d0 = wobbleRandom.rotation + ( hash( seed ) / 2.14748365E9F );
				return Mth.positiveModulo( (float)d0, 1.0F );
			}
		}
	}
	
	private int hash( int seed ) {
		
		return seed * 1327217883;
	}
	
	@OnlyIn( Dist.CLIENT )
	private double getFrameRotation( ItemFrame itemFrame ) {
		
		Direction direction = itemFrame.getDirection();
		int i = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getStep() : 0;
		return Mth.wrapDegrees( 180 + direction.get2DDataValue() * 90 + itemFrame.getRotation() * 45 + i );
	}
	
	@OnlyIn( Dist.CLIENT )
	private double getAngleTo( Vec3 vec3, Entity entity ) {
		
		return StrictMath.atan2( vec3.z() - entity.getZ(), vec3.x() - entity.getX() );
	}
	
	
	@OnlyIn( Dist.CLIENT )
	private static class CompassWobble {
		
		
		double rotation;
		
		private double deltaRotation;
		
		private long lastUpdateTick;
		
		boolean shouldUpdate( long gameTime ) {
			
			return lastUpdateTick != gameTime;
		}
		
		void update( long gameTime, double p_117937_ ) {
			
			lastUpdateTick = gameTime;
			double d0 = p_117937_ - rotation;
			d0 = Mth.positiveModulo( d0 + 0.5D, 1.0D ) - 0.5D;
			deltaRotation += d0 * 0.1D;
			deltaRotation *= 0.8D;
			rotation = Mth.positiveModulo( rotation + deltaRotation, 1.0D );
		}
	}
}
