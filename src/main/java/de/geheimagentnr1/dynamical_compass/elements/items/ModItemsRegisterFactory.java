package de.geheimagentnr1.dynamical_compass.elements.items;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompass;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompassPropertyFunction;
import de.geheimagentnr1.minecraft_forge_api.elements.items.ItemsRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.registry.RegistryEntry;
import de.geheimagentnr1.minecraft_forge_api.registry.RegistryKeys;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@SuppressWarnings( "StaticNonFinalField" )
public class ModItemsRegisterFactory extends ItemsRegisterFactory {
	
	//TODO:
	// F - Funktion fertig
	// I - Item Texture fertig
	// N - Name und Registierungsname vorhanden und fertig
	// R - Rezept fertig
	// T - Tags fertig
	
	//Dynamical Compass
	
	@ObjectHolder( registryName = RegistryKeys.ITEMS,
		value = DynamicalCompassMod.MODID + ":" + DynamicalCompass.registry_name )
	public static DynamicalCompass DYNAMICAL_COMPASS;
	
	@NotNull
	@Override
	protected List<RegistryEntry<Item>> items() {
		
		return List.of(//FINRT
			RegistryEntry.create( DynamicalCompass.registry_name, new DynamicalCompass() )//FINRT
		);
	}
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	@Override
	public void handleFMLClientSetupEvent( @NotNull FMLClientSetupEvent event ) {
		
		ItemProperties.register(
			DYNAMICAL_COMPASS,
			new ResourceLocation( "angle" ),
			new DynamicalCompassPropertyFunction()
		);
	}
}
