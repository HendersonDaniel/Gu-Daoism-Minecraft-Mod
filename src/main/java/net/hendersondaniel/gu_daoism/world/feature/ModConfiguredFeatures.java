package net.hendersondaniel.gu_daoism.world.feature;


import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.fluid.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ModConfiguredFeatures {


    public static final ResourceKey<ConfiguredFeature<?, ?>> SPIRIT_SPRING_KEY = registerKey("spirit_spring");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        register(context, SPIRIT_SPRING_KEY, Feature.LAKE, new LakeFeature.Configuration(
                BlockStateProvider.simple(ModFluids.SOURCE_PRIMEVAL_ESSENCE_LIQUID.get().defaultFluidState().createLegacyBlock()),
                BlockStateProvider.simple(Blocks.STONE.defaultBlockState()))
        );

    }



    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}


