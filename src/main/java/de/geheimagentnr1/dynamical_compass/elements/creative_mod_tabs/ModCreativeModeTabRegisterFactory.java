package de.geheimagentnr1.dynamical_compass.elements.creative_mod_tabs;

import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.ModItemsRegisterFactory;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


@RequiredArgsConstructor
public class ModCreativeModeTabRegisterFactory {
	
	
	@NotNull
	private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
		DeferredRegister.create( Registries.CREATIVE_MODE_TAB, DynamicalCompassMod.MODID );
	
	@NotNull
	private final ModItemsRegisterFactory modItemsRegisterFactory;
	
	public void register( @NotNull IEventBus modEventBus ) {
		
		Supplier<CreativeModeTab> tab = CREATIVE_MODE_TABS.register(
			DynamicalCompassMod.MODID,
			() -> CreativeModeTab.builder()
				.title( Component.translatable( "itemGroup." + DynamicalCompassMod.MODID ) )
				.icon( () -> new ItemStack( ModItemsRegisterFactory.DYNAMICAL_COMPASS.get() ) )
				.displayItems( ( parameters, output ) -> {
					output.accept( ModItemsRegisterFactory.DYNAMICAL_COMPASS.get() );
				} )
				.build()
		);
		
		CREATIVE_MODE_TABS.register( modEventBus );
	}
}
