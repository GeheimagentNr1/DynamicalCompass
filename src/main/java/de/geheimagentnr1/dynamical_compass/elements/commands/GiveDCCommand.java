package de.geheimagentnr1.dynamical_compass.elements.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItems;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompassItemStackHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

import java.util.Collection;


public class GiveDCCommand {
	
	
	public static void register( CommandDispatcher<CommandSourceStack> dispatcher ) {
		
		LiteralArgumentBuilder<CommandSourceStack> giveDC = Commands.literal( "giveDC" )
			.requires( source -> source.hasPermission( 2 ) );
		giveDC.then( Commands.argument( "targets", EntityArgument.players() )
			.then( Commands.argument( "destination", Vec2Argument.vec2() )
				.then( Commands.argument( "dimension", DimensionArgument.dimension() )
					.then( Commands.argument( "locked", BoolArgumentType.bool() )
						.executes( GiveDCCommand::giveDC ) ) ) ) );
		dispatcher.register( giveDC );
	}
	
	private static int giveDC( CommandContext<CommandSourceStack> context ) throws CommandSyntaxException {
		
		CommandSourceStack source = context.getSource();
		Collection<ServerPlayer> playerEntities = EntityArgument.getPlayers( context, "targets" );
		ServerLevel level = DimensionArgument.getDimension( context, "dimension" );
		Vec2 vecPos = Vec2Argument.getVec2( context, "destination" );
		BlockPos pos = BlockPos.containing( vecPos.x, 0, vecPos.y );
		boolean locked = BoolArgumentType.getBool( context, "locked" );
		
		for( ServerPlayer player : playerEntities ) {
			ItemStack stack = createItemstack( level, pos, locked );
			boolean couldAdd = player.getInventory().add( stack );
			ItemEntity entity;
			if( couldAdd && stack.isEmpty() ) {
				stack.setCount( 1 );
				entity = player.drop( stack, false );
				if( entity != null ) {
					entity.makeFakeItem();
				}
				player.level.playSound(
					null,
					player.getX(),
					player.getY(),
					player.getZ(),
					SoundEvents.ITEM_PICKUP,
					SoundSource.PLAYERS,
					0.2F,
					( ( player.getRandom().nextFloat() - player.getRandom().nextFloat() ) * 0.7F + 1.0F ) * 2.0F
				);
				player.inventoryMenu.broadcastChanges();
			} else {
				entity = player.drop( stack, false );
				if( entity != null ) {
					entity.setNoPickUpDelay();
					entity.setTarget( player.getUUID() );
				}
			}
		}
		ItemStack stack = createItemstack( level, pos, locked );
		if( playerEntities.size() == 1 ) {
			source.sendSuccess( Component.translatable(
				"commands.give.success.single",
				1,
				stack.getDisplayName(),
				playerEntities.iterator().next().getDisplayName()
			), true );
		} else {
			source.sendSuccess( Component.translatable(
				"commands.give.success.single",
				1,
				stack.getDisplayName(),
				playerEntities.size()
			), true );
		}
		return playerEntities.size();
	}
	
	private static ItemStack createItemstack( ServerLevel level, BlockPos pos, boolean locked ) {
		
		ItemStack stack = new ItemStack( ModItems.DYNAMICAL_COMPASS );
		DynamicalCompassItemStackHelper.setDimensionAndPos( stack, level, pos );
		DynamicalCompassItemStackHelper.setLocked( stack, locked );
		return stack;
	}
}
