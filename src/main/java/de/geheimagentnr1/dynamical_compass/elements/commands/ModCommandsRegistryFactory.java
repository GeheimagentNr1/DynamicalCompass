package de.geheimagentnr1.dynamical_compass.elements.commands;

import de.geheimagentnr1.minecraft_forge_api.elements.commands.CommandInterface;
import de.geheimagentnr1.minecraft_forge_api.elements.commands.CommandsRegisterFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ModCommandsRegistryFactory extends CommandsRegisterFactory {
	
	
	@NotNull
	@Override
	public List<CommandInterface> commands() {
		
		return List.of(
			new GiveDCCommand()
		);
	}
}
