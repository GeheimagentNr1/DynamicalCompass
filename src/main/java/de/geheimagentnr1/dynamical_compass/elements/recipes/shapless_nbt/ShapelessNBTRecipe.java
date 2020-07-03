package de.geheimagentnr1.dynamical_compass.elements.recipes.shapless_nbt;

import de.geheimagentnr1.dynamical_compass.elements.recipes.RecipeSerializers;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


public class ShapelessNBTRecipe implements ICraftingRecipe {
	
	
	public static final String registry_name = "crafting_shapeless_nbt";
	
	private final ResourceLocation id;
	
	private final String group;
	
	private final ItemStack recipeOutput;
	
	private final NonNullList<Ingredient> recipeItems;
	
	private final boolean isSimple;
	
	private final boolean merge_nbt;
	
	public ShapelessNBTRecipe( ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn,
		NonNullList<Ingredient> recipeItemsIn, boolean _merge_nbt ) {
		
		id = idIn;
		group = groupIn;
		recipeOutput = recipeOutputIn;
		recipeItems = recipeItemsIn;
		isSimple = recipeItemsIn.stream().allMatch( Ingredient::isSimple );
		merge_nbt = _merge_nbt;
	}
	
	@Nonnull
	@Override
	public ResourceLocation getId() {
		
		return id;
	}
	
	@Nonnull
	@Override
	public IRecipeSerializer<?> getSerializer() {
		
		return RecipeSerializers.SHAPELESS_NBT;
	}
	
	@Nonnull
	@Override
	public String getGroup() {
		
		return group;
	}
	
	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		
		return recipeOutput;
	}
	
	@Nonnull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		
		return recipeItems;
	}
	
	@Override
	public boolean matches( CraftingInventory inv, @Nonnull World worldIn ) {
		
		RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
		List<ItemStack> inputs = new ArrayList<>();
		int i = 0;
		
		for( int j = 0; j < inv.getSizeInventory(); ++j ) {
			ItemStack itemstack = inv.getStackInSlot( j );
			if( !itemstack.isEmpty() ) {
				++i;
				if( isSimple ) {
					recipeitemhelper.func_221264_a( itemstack, 1 );
				} else {
					inputs.add( itemstack );
				}
			}
		}
		return i == recipeItems.size() && ( isSimple ? recipeitemhelper.canCraft( this, null ) :
			RecipeMatcher.findMatches( inputs, recipeItems ) != null );
	}
	
	@Nonnull
	@Override
	public ItemStack getCraftingResult( @Nonnull CraftingInventory inv ) {
		
		if( merge_nbt ) {
			for( int j = 0; j < inv.getSizeInventory(); ++j ) {
				ItemStack itemstack = inv.getStackInSlot( j );
				if( itemstack.getItem() == recipeOutput.getItem() ) {
					ItemStack result = recipeOutput.copy();
					result.getOrCreateTag().merge( itemstack.getOrCreateTag() );
					return result;
				}
			}
		}
		return recipeOutput.copy();
	}
	
	@Override
	public boolean canFit( int width, int height ) {
		
		return width * height >= recipeItems.size();
	}
	
	public boolean isMergeNbt() {
		
		return merge_nbt;
	}
}