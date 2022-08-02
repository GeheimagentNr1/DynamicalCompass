package de.geheimagentnr1.dynamical_compass.elements.items;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.RegistryEntry;
import de.geheimagentnr1.dynamical_compass.elements.RegistryKeys;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompass;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;


@SuppressWarnings( "StaticNonFinalField" )
public class ModItems {
	
	//TODO:
	// F - Funktion fertig
	// I - Item Texture fertig
	// N - Name und Registierungsname vorhanden und fertig
	// R - Rezept fertig
	// T - Tags fertig
	
	public static final List<RegistryEntry<? extends Item>> ITEMS = List.of(
		//Dynamical Compass
		RegistryEntry.create( DynamicalCompass.registry_name, new DynamicalCompass() )//FINRT
	);
	
	//Dynamical Compass
	
	@ObjectHolder( registryName = RegistryKeys.ITEMS,
		value = DynamicalCompassMod.MODID + ":" + DynamicalCompass.registry_name )
	public static DynamicalCompass DYNAMICAL_COMPASS;
}
