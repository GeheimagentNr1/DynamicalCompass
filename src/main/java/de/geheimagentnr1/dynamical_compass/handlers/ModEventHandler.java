package de.geheimagentnr1.dynamical_compass.handlers;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItems;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompassPropertyGetter;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber( modid = DynamicalCompassMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModEventHandler {
	
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public static void handleClientSetupEvent( FMLClientSetupEvent event ) {
		
		ItemProperties.register(
			ModItems.DYNAMICAL_COMPASS,
			new ResourceLocation( "angle" ),
			new DynamicalCompassPropertyGetter()
		);
	}
	
	@SubscribeEvent
	public static void hangleRegisterItemEvent( RegistryEvent.Register<Item> itemRegistryEvent ) {
		
		itemRegistryEvent.getRegistry().registerAll( ModItems.ITEMS );
	}
}
