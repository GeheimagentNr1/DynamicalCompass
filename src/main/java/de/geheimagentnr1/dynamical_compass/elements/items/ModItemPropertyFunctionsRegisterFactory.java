package de.geheimagentnr1.dynamical_compass.elements.items;

import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompassPropertyFunction;
import de.geheimagentnr1.minecraft_forge_api.events.ModEventHandlerInterface;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;


public class ModItemPropertyFunctionsRegisterFactory implements ModEventHandlerInterface {
	
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	@Override
	public void handleFMLClientSetupEvent( @NotNull FMLClientSetupEvent event ) {
		
		ItemProperties.register(
			ModItemsRegisterFactory.DYNAMICAL_COMPASS,
			ResourceLocation.withDefaultNamespace( "angle" ),
			new DynamicalCompassPropertyFunction()
		);
	}
}
