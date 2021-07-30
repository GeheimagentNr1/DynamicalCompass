package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Objects;


public class DynamicalCompassItemStackHelper {
	
	
	private static final String destinationName = "destination";
	
	private static final String dimensionName = "destination_dimension";
	
	private static final String posName = "destination_pos";
	
	private static final String lockedName = "locked";
	
	public static void setDimensionAndPos( ItemStack stack, Level level, BlockPos pos ) {
		
		CompoundTag compound = new CompoundTag();
		compound.putString( dimensionName, Objects.requireNonNull( level.dimension().location() ).toString() );
		compound.put( posName, NbtUtils.writeBlockPos( pos ) );
		stack.getOrCreateTag().put( destinationName, compound );
	}
	
	//package-private
	static boolean isDimensionEqual( ItemStack stack, Level level ) {
		
		return Objects.equals(
			level.dimension().location(),
			ResourceLocation.tryParse( stack.getOrCreateTag()
				.getCompound( destinationName )
				.getString( dimensionName ) )
		);
	}
	
	//package-private
	static BlockPos getDestinationPos( ItemStack stack ) {
		
		return NbtUtils.readBlockPos( stack.getOrCreateTag().getCompound( destinationName ).getCompound( posName ) );
	}
	
	//package-private
	static boolean isLocked( ItemStack stack ) {
		
		return stack.getOrCreateTag().getBoolean( lockedName );
	}
	
	public static void setLocked( ItemStack stack, boolean locked ) {
		
		stack.getOrCreateTag().putBoolean( lockedName, locked );
	}
}
