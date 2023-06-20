package de.geheimagentnr1.dynamical_compass;

import de.geheimagentnr1.dynamical_compass.elements.commands.ModCommandsRegistryFactory;
import de.geheimagentnr1.dynamical_compass.elements.creative_mod_tabs.ModCreativeModeTabRegisterFactory;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItemsRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;


@Mod( DynamicalCompassMod.MODID )
public class DynamicalCompassMod extends AbstractMod {
	
	
	@NotNull
	public static final String MODID = "dynamical_compass";
	
	@NotNull
	@Override
	public String getModId() {
		
		return MODID;
	}
	
	@Override
	protected void initMod() {
		
		registerEventHandler( new ModCommandsRegistryFactory() );
		ModItemsRegisterFactory modItemsRegisterFactory = registerEventHandler( new ModItemsRegisterFactory() );
		registerEventHandler( new ModCreativeModeTabRegisterFactory( modItemsRegisterFactory ) );
	}
}
