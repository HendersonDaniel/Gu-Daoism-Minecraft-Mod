package net.hendersondaniel.gu_daoism.world.feature;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> SPIRIT_SPRING_CHECKED_KEY = createKey("spirit_spring_checked");



    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        // Get lookup for configured features to reference them by key
        Holder<ConfiguredFeature<?, ?>> spiritSpringConfigured = context.lookup(Registries.CONFIGURED_FEATURE)
                .getOrThrow(ModConfiguredFeatures.SPIRIT_SPRING_KEY);

        // Register the placed feature for the spirit spring lake
        register(context, SPIRIT_SPRING_CHECKED_KEY, spiritSpringConfigured,
                RarityFilter.onAverageOnceEvery(32),  // Adjust frequency as you want
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG)

        );
    }


    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, name));
    }


    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }


}
