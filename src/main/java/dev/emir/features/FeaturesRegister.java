package dev.emir.features;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;

import static dev.emir.Main.MOD_ID;
import static dev.emir.features.OrichalcumOreOverworld.OVERWORLD_ORICHALCUM_ORE_CONFIGURED_FEATURE;
import static dev.emir.features.OrichalcumOreOverworld.OVERWORLD_ORICHALCUM_ORE_PLACED_FEATURE;

public class FeaturesRegister  {

    public static void registerBootstrap() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MOD_ID, "orichalcum_ore_placed_overworld"), OVERWORLD_ORICHALCUM_ORE_CONFIGURED_FEATURE);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(MOD_ID, "orichalcum_ore_placed_overworld"), OVERWORLD_ORICHALCUM_ORE_PLACED_FEATURE);

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MOD_ID, "orichalcum_ore_placed_overworld")));
    }
}
