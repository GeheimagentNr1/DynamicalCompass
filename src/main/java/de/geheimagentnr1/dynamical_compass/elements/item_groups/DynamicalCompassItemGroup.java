package de.geheimagentnr1.dynamical_compass.elements.item_groups;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;


public class DynamicalCompassItemGroup extends ItemGroup {
	
	
	public DynamicalCompassItemGroup() {
		
		super( DynamicalCompassMod.MODID );
	}
	
	@Nonnull
	@Override
	public ItemStack createIcon() {
		
		return new ItemStack( ModItems.DYNAMICAL_COMPASS );
	}
}
