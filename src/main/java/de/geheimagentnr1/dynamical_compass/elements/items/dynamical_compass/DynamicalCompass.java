package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import de.geheimagentnr1.dynamical_compass.elements.item_groups.ModItemGroups;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


public class DynamicalCompass extends Item {
	
	
	public static final String registry_name = "dynamical_compass";
	
	public DynamicalCompass() {
		
		super( new Item.Properties().tab( ModItemGroups.DYNAMICAL_COMPASS ) );
	}
	
	@Override
	public void appendHoverText(
		@Nonnull ItemStack stack,
		@Nullable Level level,
		@Nonnull List<Component> tooltip,
		@Nonnull TooltipFlag flag ) {
		
		tooltip.add( Component.literal( "Locked: " + DynamicalCompassItemStackHelper.isLocked( stack ) )
			.withStyle( ChatFormatting.GRAY ) );
	}
	
	@Nonnull
	@Override
	public InteractionResult useOn( @Nonnull UseOnContext context ) {
		
		Player player = context.getPlayer();
		if( player != null && player.isShiftKeyDown() &&
			!DynamicalCompassItemStackHelper.isLocked( context.getItemInHand() ) ) {
			DynamicalCompassItemStackHelper.setDimensionAndPos(
				context.getItemInHand(),
				context.getLevel(),
				context.getClickedPos()
			);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
