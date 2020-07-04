package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.security.SecureRandom;
import java.util.Objects;


public class DynamicalCompassPropertyGetter implements IItemPropertyGetter {
	
	
	@OnlyIn( Dist.CLIENT )
	@Override
	public float call( @Nonnull ItemStack stack, @Nullable World world, @Nullable LivingEntity livingEntity ) {
		
		if( livingEntity == null && !stack.isOnItemFrame() ) {
			return 0.0F;
		} else {
			boolean isLivingEntityNotNull = livingEntity != null;
			Entity entity = Objects.requireNonNull( isLivingEntityNotNull ? livingEntity : stack.getItemFrame() );
			if( world == null ) {
				world = entity.world;
			}
			double angel;
			if( DynamicalCompassItemStackHelper.isDimensionEqual( stack, world ) ) {
				double rotation = isLivingEntityNotNull ? entity.rotationYaw : getFrameRotation(
					(ItemFrameEntity)entity );
				rotation = MathHelper.positiveModulo( rotation / 360.0D, 1.0D );
				double d2 = getSpawnToAngle( stack, entity ) / ( (float)Math.PI * 2.0F );
				angel = 0.5D - ( rotation - 0.25D - d2 );
			} else {
				angel = new SecureRandom().nextDouble();
			}
			if( isLivingEntityNotNull ) {
				angel = wobble( stack, world, angel );
			}
			return MathHelper.positiveModulo( (float)angel, 1.0F );
		}
	}
	
	@OnlyIn( Dist.CLIENT )
	private double wobble( ItemStack stack, World worldIn, double angel ) {
		
		double rotation = DynamicalCompassItemStackHelper.getRotation( stack );
		
		if( worldIn.getGameTime() != DynamicalCompassItemStackHelper.getLastUpdateTick( stack ) ) {
			double rota = DynamicalCompassItemStackHelper.getRota( stack );
			double d0 = angel - rotation;
			d0 = MathHelper.positiveModulo( d0 + 0.5D, 1.0D ) - 0.5D;
			rota += d0 * 0.1D;
			rota *= 0.8D;
			rotation = MathHelper.positiveModulo( rotation + rota, 1.0D );
			DynamicalCompassItemStackHelper.setRotationRotaAndLastUpdateTick( stack, rotation, rota,
				worldIn.getGameTime() );
		}
		return rotation;
	}
	
	@OnlyIn( Dist.CLIENT )
	private double getFrameRotation( ItemFrameEntity itemFrameEntity ) {
		
		return MathHelper.wrapDegrees( 180 + itemFrameEntity.getHorizontalFacing().getHorizontalIndex() * 90 );
	}
	
	@OnlyIn( Dist.CLIENT )
	private double getSpawnToAngle( ItemStack stack, Entity entity ) {
		
		BlockPos blockpos = DynamicalCompassItemStackHelper.getDestinationPos( stack );
		return StrictMath.atan2( blockpos.getZ() - entity.getPosZ(), blockpos.getX() - entity.getPosX() );
	}
}
