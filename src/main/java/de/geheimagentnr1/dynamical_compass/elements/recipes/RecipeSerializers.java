package de.geheimagentnr1.dynamical_compass.elements.recipes;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.recipes.shapless_nbt.ShapelessNBTRecipe;
import de.geheimagentnr1.dynamical_compass.elements.recipes.shapless_nbt.ShapelessNBTRecipeSerializer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;


@SuppressWarnings( { "StaticNonFinalField", "PublicField" } )
public class RecipeSerializers {
	
	//Shapeless NBT
	
	public static IRecipeSerializer<ShapelessNBTRecipe> SHAPELESS_NBT;
	
	@SuppressWarnings( "deprecation" )
	private static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S register( String key,
		S recipeSerializer ) {
		
		return Registry.register( Registry.RECIPE_SERIALIZER, new ResourceLocation( DynamicalCompassMod.MODID, key ),
			recipeSerializer );
	}
	
	public static void init() {
		
		//Shapeless NBT
		SHAPELESS_NBT = register( ShapelessNBTRecipe.registry_name, new ShapelessNBTRecipeSerializer() );
	}
}
