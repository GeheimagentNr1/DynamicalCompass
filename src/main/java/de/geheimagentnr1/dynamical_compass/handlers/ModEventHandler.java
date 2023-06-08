package de.geheimagentnr1.dynamical_compass.handlers;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.creative_mod_tabs.ModCreativeTabs;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItems;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompassPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;


@Mod.EventBusSubscriber( modid = DynamicalCompassMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModEventHandler {
	
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public static void handleClientSetupEvent( FMLClientSetupEvent event ) {
		
		ItemProperties.register(
			ModItems.DYNAMICAL_COMPASS,
			new ResourceLocation( "angle" ),
			new DynamicalCompassPropertyFunction()
		);
	}
	
	@SubscribeEvent
	public static void hangleRegisterItemEvent( RegisterEvent event ) {
		
		if( event.getRegistryKey().equals( ForgeRegistries.Keys.ITEMS ) ) {
			event.register(
				ForgeRegistries.Keys.ITEMS,
				registerHelper -> ModItems.ITEMS.forEach( registryEntry -> registerHelper.register(
					registryEntry.getRegistryName(),
					registryEntry.getValue()
				) )
			);
		}
	}
	
	@SubscribeEvent
	public static void handleCreativeModeTabRegisterEvent( RegisterEvent event ) {
		
		if( event.getRegistryKey().equals( Registries.CREATIVE_MODE_TAB ) ) {
			ModCreativeTabs.CREATIVE_TAB_FACTORIES.forEach( creativeModeTabFactory -> {
					event.register(
						Registries.CREATIVE_MODE_TAB,
						creativeModeTabRegisterHelper -> {
							creativeModeTabRegisterHelper.register(
								creativeModeTabFactory.getName(),
								creativeModeTabFactory.get()
							);
						}
					);
				}
			);
		}
	}
}
