package de.geheimagentnr1.dynamical_compass.elements.recipes.shapless_nbt;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;


public class ShapelessNBTRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
	implements IRecipeSerializer<ShapelessNBTRecipe> {
	
	
	private static final int MAX_WIDTH = 3;
	
	private static final int MAX_HEIGHT = 3;
	
	@Nonnull
	@Override
	public ShapelessNBTRecipe read( @Nonnull ResourceLocation recipeId, @Nonnull JsonObject json ) {
		
		String group = JSONUtils.getString( json, "group", "" );
		NonNullList<Ingredient> ingredients = readIngredients( JSONUtils.getJsonArray( json, "ingredients" ) );
		if( ingredients.isEmpty() ) {
			throw new JsonParseException( "No ingredients for shapeless recipe" );
		} else {
			if( ingredients.size() > MAX_WIDTH * MAX_HEIGHT ) {
				throw new JsonParseException(
					"Too many ingredients for shapeless recipe the max is " + MAX_WIDTH * MAX_HEIGHT );
			} else {
				JsonObject resultJson = JSONUtils.getJsonObject( json, "result" );
				ItemStack result = ShapedRecipe.deserializeItem( resultJson );
				try {
					result.getOrCreateTag().merge(
						JsonToNBT.getTagFromJson( JSONUtils.getString( resultJson, "nbt" ) ) );
				} catch( CommandSyntaxException exception ) {
					throw new IllegalStateException( exception );
				}
				boolean merge_nbt = JSONUtils.getBoolean( resultJson, "merge_nbt" );
				return new ShapelessNBTRecipe( recipeId, group, result, ingredients, merge_nbt );
			}
		}
	}
	
	private static NonNullList<Ingredient> readIngredients( JsonArray ingredientsJson ) {
		
		NonNullList<Ingredient> ingredients = NonNullList.create();
		
		for( int i = 0; i < ingredientsJson.size(); ++i ) {
			Ingredient ingredient = Ingredient.deserialize( ingredientsJson.get( i ) );
			if( !ingredient.hasNoMatchingItems() ) {
				ingredients.add( ingredient );
			}
		}
		return ingredients;
	}
	
	@Override
	public ShapelessNBTRecipe read( @Nonnull ResourceLocation recipeId, PacketBuffer buffer ) {
		
		String group = buffer.readString( 32767 );
		int i = buffer.readVarInt();
		NonNullList<Ingredient> ingredients = NonNullList.withSize( i, Ingredient.EMPTY );
		for( int j = 0; j < ingredients.size(); ++j ) {
			ingredients.set( j, Ingredient.read( buffer ) );
		}
		ItemStack result = buffer.readItemStack();
		boolean merge_nbt = buffer.readBoolean();
		return new ShapelessNBTRecipe( recipeId, group, result, ingredients, merge_nbt );
	}
	
	@Override
	public void write( PacketBuffer buffer, ShapelessNBTRecipe recipe ) {
		
		buffer.writeString( recipe.getGroup() );
		buffer.writeVarInt( recipe.getIngredients().size() );
		for( Ingredient ingredient : recipe.getIngredients() ) {
			ingredient.write( buffer );
		}
		buffer.writeItemStack( recipe.getRecipeOutput() );
		buffer.writeBoolean( recipe.isMergeNbt() );
	}
}
