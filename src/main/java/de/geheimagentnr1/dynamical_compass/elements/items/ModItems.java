package de.geheimagentnr1.dynamical_compass.elements.items;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompass;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ObjectHolder;


@SuppressWarnings( { "StaticNonFinalField", "unused", "PublicField", "PublicStaticArrayField" } )
public class ModItems {
	
	//TODO:
	// F - Funktion fertig
	// I - Item Texture fertig
	// N - Name und Registierungsname vorhanden und fertig
	// R - Rezept fertig
	// T - Tags fertig
	
	public final static Item[] ITEMS = {
		//Dynamical Compass
		new DynamicalCompass(),//FIN_T
	};
	
	//Dynamical Compass
	
	@ObjectHolder( DynamicalCompassMod.MODID + ":" + DynamicalCompass.registry_name )
	public static DynamicalCompass DYNAMICAL_COMPASS;
}
