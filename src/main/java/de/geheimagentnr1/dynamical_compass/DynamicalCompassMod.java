package de.geheimagentnr1.dynamical_compass;

import de.geheimagentnr1.dynamical_compass.elements.commands.ModCommandsRegistryFactory;
import de.geheimagentnr1.dynamical_compass.elements.creative_mod_tabs.ModCreativeModeTabRegisterFactory;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItemPropertyFunctionsRegisterFactory;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItemsRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
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
		DistExecutor.safeRunWhenOn(
			Dist.CLIENT,
			() -> () -> registerEventHandler( new ModItemPropertyFunctionsRegisterFactory() )
		);
		registerEventHandler( new ModCreativeModeTabRegisterFactory( modItemsRegisterFactory ) );
	}
}
