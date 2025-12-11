package de.geheimagentnr1.dynamical_compass.elements.commands;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.jetbrains.annotations.NotNull;


public class ModCommandsRegistryFactory {
	
	
	public void register( @NotNull IEventBus modEventBus ) {
		
		NeoForge.EVENT_BUS.addListener( this::registerCommands );
	}
	
	@SubscribeEvent
	public void registerCommands( @NotNull RegisterCommandsEvent event ) {
		
		event.getDispatcher().register( new GiveDCCommand().build() );
	}
}
