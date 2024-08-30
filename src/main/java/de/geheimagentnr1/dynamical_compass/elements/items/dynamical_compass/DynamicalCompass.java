package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class DynamicalCompass extends Item {
	
	
	@NotNull
	public static final String registry_name = "dynamical_compass";
	
	public DynamicalCompass() {
		
		super( new Item.Properties() );
	}
	
	@Override
	public void appendHoverText(
		@NotNull ItemStack pStack,
		@NotNull TooltipContext pContext,
		@NotNull List<Component> pTooltipComponents,
		@NotNull TooltipFlag pTooltipFlag ) {
		
		pTooltipComponents.add( Component.literal( "Locked: " + DynamicalCompassItemStackHelper.isLocked( pStack ) )
			.withStyle( ChatFormatting.GRAY ) );
	}
	
	@NotNull
	@Override
	public InteractionResult useOn( @NotNull UseOnContext pContext ) {
		
		ItemStack stack = pContext.getItemInHand();
		Player player = pContext.getPlayer();
		boolean isClientSide = pContext.getLevel().isClientSide();
		if( player != null && player.isShiftKeyDown() &&
			!DynamicalCompassItemStackHelper.isLocked( stack ) ) {
			if( player.hasInfiniteMaterials() ) {
				ItemStack targetStack = stack.transmuteCopy( stack.getItem(), 1 );
				DynamicalCompassItemStackHelper.setDimensionAndPos(
					targetStack,
					pContext.getLevel(),
					pContext.getClickedPos()
				);
				if( !player.getInventory().add( targetStack ) ) {
					player.drop( targetStack, false );
				}
			} else {
				DynamicalCompassItemStackHelper.setDimensionAndPos(
					stack,
					pContext.getLevel(),
					pContext.getClickedPos()
				);
			}
			return InteractionResult.sidedSuccess( isClientSide );
		}
		return InteractionResult.PASS;
	}
}
