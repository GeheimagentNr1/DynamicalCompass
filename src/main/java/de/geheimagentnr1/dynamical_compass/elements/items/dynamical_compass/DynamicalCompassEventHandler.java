package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import de.geheimagentnr1.dynamical_compass.elements.items.ModItemsRegisterFactory;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;


public class DynamicalCompassEventHandler {


	@SubscribeEvent
	public void handleItemTooltipEvent( @NotNull ItemTooltipEvent event ) {

		ItemStack stack = event.getItemStack();
		if( stack.is( ModItemsRegisterFactory.DYNAMICAL_COMPASS.get() ) ) {
			event.getToolTip().add( Component.literal( "Locked: " + DynamicalCompassItemStackHelper.isLocked( stack ) )
				.withStyle( ChatFormatting.GRAY ) );
		}
	}

	//Compasses from worlds before 1.21.4 have no lodestone tracker, which the item model needs to point
	@SubscribeEvent
	public void handlePlayerLoggedInEvent( @NotNull PlayerEvent.PlayerLoggedInEvent event ) {

		Container inventory = event.getEntity().getInventory();
		for( int i = 0; i < inventory.getContainerSize(); i++ ) {
			ItemStack stack = inventory.getItem( i );
			if( stack.is( ModItemsRegisterFactory.DYNAMICAL_COMPASS.get() ) &&
				!stack.has( DataComponents.LODESTONE_TRACKER ) ) {
				DynamicalCompassItemStackHelper.updateLodestoneTracker( stack );
			}
		}
	}
}
