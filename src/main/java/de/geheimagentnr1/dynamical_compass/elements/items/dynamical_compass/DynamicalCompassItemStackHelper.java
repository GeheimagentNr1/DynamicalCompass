package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;


public class DynamicalCompassItemStackHelper {
	
	
	private static final String destinationName = "destination";
	
	private static final String dimensionName = "destination_dimension";
	
	private static final String posName = "destination_pos";
	
	private static final String lockedName = "locked";
	
	private static final String directionDataName = "directionData";
	
	private static final String rotationName = "rotation";
	
	private static final String rotaName = "rota";
	
	private static final String lastUpdateTickName = "last_update_tick";
	
	
	public static void setDimensionAndPos( ItemStack stack, World world, BlockPos pos ) {
		
		CompoundNBT compound = new CompoundNBT();
		compound.putString( dimensionName, Objects.requireNonNull( world.getDimensionKey().getLocation() )
			.toString() );
		compound.put( posName, NBTUtil.writeBlockPos( pos ) );
		stack.getOrCreateTag().put( destinationName, compound );
	}
	
	//package-private
	static boolean isDimensionEqual( ItemStack stack, World world ) {
		
		return Objects.equals( world.getDimensionKey().getLocation(), ResourceLocation.tryCreate(
			stack.getOrCreateTag().getCompound( destinationName ).getString( dimensionName ) ) );
	}
	
	//package-private
	static BlockPos getDestinationPos( ItemStack stack ) {
		
		return NBTUtil.readBlockPos( stack.getOrCreateTag().getCompound( destinationName ).getCompound( posName ) );
	}
	
	//package-private
	static boolean isLocked( ItemStack stack ) {
		
		return stack.getOrCreateTag().getBoolean( lockedName );
	}
	
	public static void setLocked( ItemStack stack, boolean locked ) {
		
		stack.getOrCreateTag().putBoolean( lockedName, locked );
	}
	
	//package-private
	static void setRotationRotaAndLastUpdateTick( ItemStack stack, double rotation, double rota,
		long lastUpdateTick ) {
		
		CompoundNBT compound = new CompoundNBT();
		compound.putDouble( rotationName, rotation );
		compound.putDouble( rotaName, rota );
		compound.putLong( lastUpdateTickName, lastUpdateTick );
		stack.getOrCreateTag().put( directionDataName, compound );
	}
	
	//package-private
	static double getRotation( ItemStack stack ) {
		
		return stack.getOrCreateTag().getCompound( directionDataName ).getDouble( rotationName );
	}
	
	//package-private
	static double getRota( ItemStack stack ) {
		
		return stack.getOrCreateTag().getCompound( directionDataName ).getDouble( rotaName );
	}
	
	//package-private
	static long getLastUpdateTick( ItemStack stack ) {
		
		return stack.getOrCreateTag().getCompound( directionDataName ).getLong( lastUpdateTickName );
	}
}
