package de.geheimagentnr1.dynamical_compass.handlers;

import de.geheimagentnr1.dynamical_compass.elements.items.ModItems;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompassPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@SuppressWarnings( "unused" )
@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
public class RegistryHandler {
	
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public static void handleClientSetupEvent( FMLClientSetupEvent event ) {
		
		ItemModelsProperties.registerProperty( ModItems.DYNAMICAL_COMPASS, new ResourceLocation( "angle" ),
			new DynamicalCompassPropertyGetter() );
	}
	
	@SubscribeEvent
	public static void onItemsRegistry( RegistryEvent.Register<Item> itemRegistryEvent ) {
		
		itemRegistryEvent.getRegistry().registerAll( ModItems.ITEMS );
	}
}
