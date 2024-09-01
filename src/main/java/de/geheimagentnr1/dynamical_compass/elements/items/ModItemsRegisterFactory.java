package de.geheimagentnr1.dynamical_compass.elements.items;

import com.mojang.serialization.Codec;
import de.geheimagentnr1.dynamical_compass.DynamicalCompassMod;
import de.geheimagentnr1.dynamical_compass.elements.items.dynamical_compass.DynamicalCompass;
import de.geheimagentnr1.minecraft_forge_api.elements.items.ItemsRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.registry.RegistryEntry;
import de.geheimagentnr1.minecraft_forge_api.registry.RegistryKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
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
	public static final DataComponentType<ResourceLocation> DESTINATION_DIMENSION =
		DataComponentType.<ResourceLocation> builder()
			.persistent( ResourceLocation.CODEC )
			.networkSynchronized( ResourceLocation.STREAM_CODEC )
			.build();
	
	@NotNull
	public static final DataComponentType<BlockPos> DESTINATION_POS = DataComponentType.<BlockPos> builder()
		.persistent( BlockPos.CODEC )
		.networkSynchronized( BlockPos.STREAM_CODEC )
		.build();
	
	@NotNull
	public static final DataComponentType<Boolean> LOCKED = DataComponentType.<Boolean> builder()
		.persistent( Codec.BOOL )
		.networkSynchronized( ByteBufCodecs.BOOL )
		.build();
	
	@NotNull
	@Override
	protected List<RegistryEntry<Item>> items() {
		
		return List.of(//FINRT
			RegistryEntry.create( DynamicalCompass.registry_name, new DynamicalCompass() )//FINRT
		);
	}
	
	@Override
	protected @NotNull List<RegistryEntry<DataComponentType<?>>> dataComponentTypes() {
		
		return List.of(
			RegistryEntry.create(
				"destination_dimension",
				DESTINATION_DIMENSION
			),
			RegistryEntry.create(
				"destination_pos",
				DESTINATION_POS
			),
			RegistryEntry.create(
				"locked",
				LOCKED
			)
		);
	}
}
