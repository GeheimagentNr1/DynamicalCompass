package de.geheimagentnr1.dynamical_compass;

import de.geheimagentnr1.dynamical_compass.elements.commands.ModCommandsRegistryFactory;
import de.geheimagentnr1.dynamical_compass.elements.creative_mod_tabs.ModCreativeModeTabRegisterFactory;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItemPropertyFunctionsRegisterFactory;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItemsRegisterFactory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import org.jetbrains.annotations.NotNull;


@Mod( DynamicalCompassMod.MODID )
public class DynamicalCompassMod {
	
	
	@NotNull
	public static final String MODID = "dynamical_compass";
	
	public DynamicalCompassMod( @NotNull IEventBus modEventBus, @NotNull ModContainer modContainer ) {
		
		ModCommandsRegistryFactory modCommandsRegistryFactory = new ModCommandsRegistryFactory();
		modCommandsRegistryFactory.register( modEventBus );
		
		ModItemsRegisterFactory modItemsRegisterFactory = new ModItemsRegisterFactory();
		modItemsRegisterFactory.register( modEventBus );
		
		if( FMLLoader.getDist() == Dist.CLIENT ) {
			ModItemPropertyFunctionsRegisterFactory modItemPropertyFunctionsRegisterFactory =
				new ModItemPropertyFunctionsRegisterFactory();
			modItemPropertyFunctionsRegisterFactory.register( modEventBus );
		}
		
		ModCreativeModeTabRegisterFactory modCreativeModeTabRegisterFactory =
			new ModCreativeModeTabRegisterFactory( modItemsRegisterFactory );
		modCreativeModeTabRegisterFactory.register( modEventBus );
	}
}
