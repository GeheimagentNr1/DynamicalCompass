package de.geheimagentnr1.dynamical_compass.elements.creative_mod_tabs;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;


public class ManyIdeasCoreCreativeModeTabFactory implements CreativeModeTabFactory {
	
	
	@Override
	public String getModId() {
		
		return DynamicalCompassMod.MODID;
	}
	
	@Override
	public String getRegistryName() {
		
		return DynamicalCompassMod.MODID;
	}
	
	@Override
	public Item getDisplayItem() {
		
		return ModItems.DYNAMICAL_COMPASS.asItem();
	}
	
	@Override
	public List<ItemStack> getDisplayItems() {
		
		return ModItems.ITEMS.stream()
			.map( registryEntry -> new ItemStack( registryEntry.getValue() ) )
			.toList();
	}
}
