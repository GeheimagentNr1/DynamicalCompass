package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


public class DynamicalCompass extends Item {
	
	
	public static final String registry_name = "dynamical_compass";
	
	public DynamicalCompass() {
		
		super( new Item.Properties().group( DynamicalCompassMod.setup.dynamicalCompassItemGroup ) );
		setRegistryName( registry_name );
	}
	
	@Override
	public void addInformation( @Nonnull ItemStack stack, @Nullable World worldIn,
		@Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn ) {
		
		tooltip.add( new StringTextComponent( "Locked: " + DynamicalCompassItemStackHelper.isLocked( stack ) )
			.func_240699_a_( TextFormatting.GRAY ) );
	}
	
	@Nonnull
	@Override
	public ActionResultType onItemUse( ItemUseContext context ) {
		
		PlayerEntity player = context.getPlayer();
		if( player != null && player.isSneaking() && !DynamicalCompassItemStackHelper.isLocked( context.getItem() ) ) {
			DynamicalCompassItemStackHelper.setDimensionAndPos( context.getItem(), context.getWorld(),
				context.getPos() );
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
}
