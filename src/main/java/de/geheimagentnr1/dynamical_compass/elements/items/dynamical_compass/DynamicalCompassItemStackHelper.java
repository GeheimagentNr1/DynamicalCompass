package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import de.geheimagentnr1.dynamical_compass.elements.items.ModItemsRegisterFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


public class DynamicalCompassItemStackHelper {
	
	
	public static void setDimensionAndPos( @NotNull ItemStack stack, @NotNull Level level, @NotNull BlockPos pos ) {
		
		stack.set( ModItemsRegisterFactory.DESTINATION_DIMENSION, level.dimension().location() );
		stack.set( ModItemsRegisterFactory.DESTINATION_POS, pos );
	}
	
	//package-private
	static boolean isDimensionEqual( @NotNull ItemStack stack, @NotNull Level level ) {
		
		return Objects.equals(
			level.dimension().location(),
			stack.get( ModItemsRegisterFactory.DESTINATION_DIMENSION )
		);
	}
	
	//package-private
	@Nullable
	static BlockPos getDestinationPos( @NotNull ItemStack stack ) {
		
		return stack.get( ModItemsRegisterFactory.DESTINATION_POS );
	}
	
	//package-private
	static boolean isLocked( @NotNull ItemStack stack ) {
		
		return Boolean.TRUE.equals( stack.get( ModItemsRegisterFactory.LOCKED ) );
	}
	
	public static void setLocked( @NotNull ItemStack stack, boolean locked ) {
		
		stack.set( ModItemsRegisterFactory.LOCKED, locked );
	}
}
