package de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;


public class DynamicalCompass extends Item {
	
	
	@NotNull
	public static final String registry_name = "dynamical_compass";
	
	public DynamicalCompass() {
		
		super( new Item.Properties() );
	}
	
	@Override
	public void appendHoverText(
		@NotNull ItemStack stack,
		@Nullable Level level,
		@NotNull List<Component> tooltip,
		@NotNull TooltipFlag flag ) {
		
		tooltip.add( Component.literal( "Locked: " + DynamicalCompassItemStackHelper.isLocked( stack ) )
			.withStyle( ChatFormatting.GRAY ) );
	}
	
	@NotNull
	@Override
	public InteractionResult useOn( @NotNull UseOnContext context ) {
		
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
