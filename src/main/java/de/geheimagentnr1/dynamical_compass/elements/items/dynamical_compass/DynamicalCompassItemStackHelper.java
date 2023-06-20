package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class DynamicalCompassItemStackHelper {
	
	
	@NotNull
	private static final String destinationName = "destination";
	
	@NotNull
	private static final String dimensionName = "destination_dimension";
	
	@NotNull
	private static final String posName = "destination_pos";
	
	@NotNull
	private static final String lockedName = "locked";
	
	public static void setDimensionAndPos( @NotNull ItemStack stack, @NotNull Level level, @NotNull BlockPos pos ) {
		
		CompoundTag compound = new CompoundTag();
		compound.putString( dimensionName, Objects.requireNonNull( level.dimension().location() ).toString() );
		compound.put( posName, NbtUtils.writeBlockPos( pos ) );
		stack.getOrCreateTag().put( destinationName, compound );
	}
	
	//package-private
	static boolean isDimensionEqual( @NotNull ItemStack stack, @NotNull Level level ) {
		
		return Objects.equals(
			level.dimension().location(),
			ResourceLocation.tryParse( stack.getOrCreateTag()
				.getCompound( destinationName )
				.getString( dimensionName ) )
		);
	}
	
	//package-private
	@NotNull
	static BlockPos getDestinationPos( @NotNull ItemStack stack ) {
		
		return NbtUtils.readBlockPos( stack.getOrCreateTag().getCompound( destinationName ).getCompound( posName ) );
	}
	
	//package-private
	static boolean isLocked( @NotNull ItemStack stack ) {
		
		return stack.getOrCreateTag().getBoolean( lockedName );
	}
	
	public static void setLocked( @NotNull ItemStack stack, boolean locked ) {
		
		stack.getOrCreateTag().putBoolean( lockedName, locked );
	}
}
