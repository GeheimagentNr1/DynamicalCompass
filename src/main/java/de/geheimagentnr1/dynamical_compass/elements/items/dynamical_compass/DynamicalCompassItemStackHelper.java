package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import de.geheimagentnr1.dynamical_compass.elements.items.ModItemsRegisterFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;


public class DynamicalCompassItemStackHelper {
	
	
	public static void setDimensionAndPos( @NotNull ItemStack stack, @NotNull Level level, @NotNull BlockPos pos ) {
		
		stack.set( ModItemsRegisterFactory.DESTINATION_DIMENSION.get(), level.dimension().location() );
		stack.set( ModItemsRegisterFactory.DESTINATION_POS.get(), pos );
		updateLodestoneTracker( stack );
	}

	//Mirrors the destination into the vanilla lodestone tracker, which the item model reads since 1.21.4.
	//tracked=false: the target is kept without a lodestone at the position.
	public static void updateLodestoneTracker( @NotNull ItemStack stack ) {

		ResourceLocation dimension = stack.get( ModItemsRegisterFactory.DESTINATION_DIMENSION.get() );
		BlockPos pos = stack.get( ModItemsRegisterFactory.DESTINATION_POS.get() );
		if( dimension == null || pos == null ) {
			return;
		}
		stack.set(
			DataComponents.LODESTONE_TRACKER,
			new LodestoneTracker(
				Optional.of( GlobalPos.of( ResourceKey.create( Registries.DIMENSION, dimension ), pos ) ),
				false
			)
		);
	}
	
	//package-private
	static boolean isDimensionEqual( @NotNull ItemStack stack, @NotNull Level level ) {
		
		return Objects.equals(
			level.dimension().location(),
			stack.get( ModItemsRegisterFactory.DESTINATION_DIMENSION.get() )
		);
	}
	
	//package-private
	@Nullable
	static BlockPos getDestinationPos( @NotNull ItemStack stack ) {
		
		return stack.get( ModItemsRegisterFactory.DESTINATION_POS.get() );
	}
	
	//package-private
	static boolean isLocked( @NotNull ItemStack stack ) {
		
		return Boolean.TRUE.equals( stack.get( ModItemsRegisterFactory.LOCKED.get() ) );
	}
	
	public static void setLocked( @NotNull ItemStack stack, boolean locked ) {
		
		stack.set( ModItemsRegisterFactory.LOCKED.get(), locked );
	}
}
