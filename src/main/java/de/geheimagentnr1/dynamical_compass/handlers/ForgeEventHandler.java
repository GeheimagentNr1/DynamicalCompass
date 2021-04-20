package de.geheimagentnr1.dynamical_compass.handlers;

import de.geheimagentnr1.dynamical_compass.elements.commands.GiveDCCommand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;


@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ForgeEventHandler {
	
	
	@SubscribeEvent
	public static void handlerServerStartingEvent( FMLServerStartingEvent event ) {
		
		GiveDCCommand.register( event.getCommandDispatcher() );
	}
}
