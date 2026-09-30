package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;


public class DynamicalCompass extends Item {
	
	
	@NotNull
	public static final String registry_name = "dynamical_compass";
	
	public DynamicalCompass( @NotNull Item.Properties properties ) {

		super( properties );
	}

	//Tooltip via ItemTooltipEvent (DynamicalCompassEventHandler): the appendHoverText signature changed in 1.21.5

	@NotNull
	@Override
	public InteractionResult useOn( @NotNull UseOnContext pContext ) {
		
		ItemStack stack = pContext.getItemInHand();
		Player player = pContext.getPlayer();
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
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
