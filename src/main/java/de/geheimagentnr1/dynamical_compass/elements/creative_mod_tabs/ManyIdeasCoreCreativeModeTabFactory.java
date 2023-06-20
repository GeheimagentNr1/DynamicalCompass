package de.geheimagentnr1.dynamical_compass.elements.creative_mod_tabs;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItemsRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.elements.creative_mod_tabs.CreativeModeTabFactory;
import de.geheimagentnr1.minecraft_forge_api.registry.RegistryEntry;
import lombok.RequiredArgsConstructor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@RequiredArgsConstructor
public class ManyIdeasCoreCreativeModeTabFactory implements CreativeModeTabFactory {
	
	
	@NotNull
	private final ModItemsRegisterFactory modItemsRegisterFactory;
	
	@NotNull
	@Override
	public String getRegistryName() {
		
		return DynamicalCompassMod.MODID;
	}
	
	@NotNull
	@Override
	public ItemLike getIconItem() {
		
		return ModItemsRegisterFactory.DYNAMICAL_COMPASS;
	}
	
	@NotNull
	@Override
	public List<RegistryEntry<Item>> getDisplayItems() {
		
		return modItemsRegisterFactory.getItems();
	}
}
