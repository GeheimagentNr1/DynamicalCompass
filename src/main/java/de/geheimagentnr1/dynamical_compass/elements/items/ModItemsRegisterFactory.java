package de.geheimagentnr1.dynamical_compass.elements.items;

import com.mojang.serialization.Codec;
import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


public class ModItemsRegisterFactory {
	
	//TODO:
	// F - Funktion fertig
	// I - Item Texture fertig
	// N - Name und Registierungsname vorhanden und fertig
	// R - Rezept fertig
	// T - Tags fertig
	
	@NotNull
	private static final DeferredRegister<Item> ITEMS =
		DeferredRegister.create( Registries.ITEM, DynamicalCompassMod.MODID );
	
	@NotNull
	private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
		DeferredRegister.create( Registries.DATA_COMPONENT_TYPE, DynamicalCompassMod.MODID );
	
	//Dynamical Compass
	
	@NotNull
	public static final Supplier<DynamicalCompass> DYNAMICAL_COMPASS =
		ITEMS.register( DynamicalCompass.registry_name, DynamicalCompass::new );
	
	@NotNull
	public static final Supplier<DataComponentType<ResourceLocation>> DESTINATION_DIMENSION =
		DATA_COMPONENT_TYPES.register(
			"destination_dimension",
			() -> DataComponentType.<ResourceLocation>builder()
				.persistent( ResourceLocation.CODEC )
				.networkSynchronized( ResourceLocation.STREAM_CODEC )
				.build()
		);
	
	@NotNull
	public static final Supplier<DataComponentType<BlockPos>> DESTINATION_POS =
		DATA_COMPONENT_TYPES.register(
			"destination_pos",
			() -> DataComponentType.<BlockPos>builder()
				.persistent( BlockPos.CODEC )
				.networkSynchronized( BlockPos.STREAM_CODEC )
				.build()
		);
	
	@NotNull
	public static final Supplier<DataComponentType<Boolean>> LOCKED =
		DATA_COMPONENT_TYPES.register(
			"locked",
			() -> DataComponentType.<Boolean>builder()
				.persistent( Codec.BOOL )
				.networkSynchronized( ByteBufCodecs.BOOL )
				.build()
		);
	
	public void register( @NotNull IEventBus modEventBus ) {
		
		ITEMS.register( modEventBus );
		DATA_COMPONENT_TYPES.register( modEventBus );
	}
	
	@NotNull
	public Supplier<DynamicalCompass> getDynamicalCompass() {
		
		return DYNAMICAL_COMPASS;
	}
}
