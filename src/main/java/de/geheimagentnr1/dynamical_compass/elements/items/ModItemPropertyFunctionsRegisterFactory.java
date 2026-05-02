package de.geheimagentnr1.dynamical_compass.elements.items;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompassPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;


@EventBusSubscriber( modid = DynamicalCompassMod.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD )
public class ModItemPropertyFunctionsRegisterFactory {


	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public static void handleFMLClientSetupEvent( @NotNull FMLClientSetupEvent event ) {

		event.enqueueWork( () -> ItemProperties.register(
			ModItemsRegisterFactory.DYNAMICAL_COMPASS.get(),
			ResourceLocation.withDefaultNamespace( "angle" ),
			new DynamicalCompassPropertyFunction()
		) );
	}
}
