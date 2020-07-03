package de.geheimagentnr1.dynamical_compass.setup;

import de.geheimagentnr1.dynamical_compass.elements.item_groups.DynamicalCompassItemGroup;
import de.geheimagentnr1.dynamical_compass.elements.recipes.RecipeSerializers;


public class ModSetup {
	
	
	@SuppressWarnings( "PublicField" )
	public final DynamicalCompassItemGroup dynamicalCompassItemGroup = new DynamicalCompassItemGroup();
	
	public ModSetup() {
		
		RecipeSerializers.init();
	}
}
