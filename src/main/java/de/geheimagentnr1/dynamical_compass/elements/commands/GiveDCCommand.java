package de.geheimagentnr1.dynamical_compass.elements.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItems;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompassItemStackHelper;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.DimensionArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.Vec2Argument;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.Collection;


public class GiveDCCommand {
	
	
	public static void register( CommandDispatcher<CommandSource> dispatcher ) {
		
		LiteralArgumentBuilder<CommandSource> giveDC = Commands.literal( "giveDC" )
			.requires( commandSource -> commandSource.hasPermission( 2 ) );
		giveDC.then( Commands.argument( "targets", EntityArgument.players() )
			.then( Commands.argument( "destination", Vec2Argument.vec2() )
				.then( Commands.argument( "dimension", DimensionArgument.dimension() )
					.then( Commands.argument( "locked", BoolArgumentType.bool() )
						.executes( GiveDCCommand::giveDC ) ) ) ) );
		dispatcher.register( giveDC );
	}
	
	private static int giveDC( CommandContext<CommandSource> context ) throws CommandSyntaxException {
		
		CommandSource source = context.getSource();
		Collection<ServerPlayerEntity> playerEntities = EntityArgument.getPlayers( context, "targets" );
		ServerWorld world = DimensionArgument.getDimension( context, "dimension" );
		Vector2f vecPos = Vec2Argument.getVec2( context, "destination" );
		BlockPos pos = new BlockPos( vecPos.x, 0, vecPos.y );
		boolean locked = BoolArgumentType.getBool( context, "locked" );
		
		for( ServerPlayerEntity player : playerEntities ) {
			ItemStack stack = createItemstack( world, pos, locked );
			boolean couldAdd = player.inventory.add( stack );
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
					SoundCategory.PLAYERS,
					0.2F,
					( ( player.getRandom().nextFloat() - player.getRandom().nextFloat() ) * 0.7F + 1.0F ) * 2.0F
				);
				player.inventoryMenu.broadcastChanges();
			} else {
				entity = player.drop( stack, false );
				if( entity != null ) {
					entity.setNoPickUpDelay();
					entity.setOwner( player.getUUID() );
				}
			}
		}
		ItemStack stack = createItemstack( world, pos, locked );
		if( playerEntities.size() == 1 ) {
			source.sendSuccess( new TranslationTextComponent(
				"commands.give.success.single",
				1,
				stack.getDisplayName(),
				playerEntities.iterator().next().getDisplayName()
			), true );
		} else {
			source.sendSuccess( new TranslationTextComponent(
				"commands.give.success.single",
				1,
				stack.getDisplayName(),
				playerEntities.size()
			), true );
		}
		return playerEntities.size();
	}
	
	private static ItemStack createItemstack( ServerWorld world, BlockPos pos, boolean locked ) {
		
		ItemStack stack = new ItemStack( ModItems.DYNAMICAL_COMPASS );
		DynamicalCompassItemStackHelper.setDimensionAndPos( stack, world, pos );
		DynamicalCompassItemStackHelper.setLocked( stack, locked );
		return stack;
	}
}
