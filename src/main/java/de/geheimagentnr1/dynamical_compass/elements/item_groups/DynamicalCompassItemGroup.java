package de.geheimagentnr1.dynamical_compass.elements.item_groups;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;


public class DynamicalCompassItemGroup extends CreativeModeTab {
	
	
	//package-private
	DynamicalCompassItemGroup() {
		
		super( DynamicalCompassMod.MODID );
	}
	
	@Nonnull
	@Override
	public ItemStack makeIcon() {
		
		return new ItemStack( ModItems.DYNAMICAL_COMPASS );
	}
}
