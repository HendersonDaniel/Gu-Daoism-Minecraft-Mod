package net.hendersondaniel.gu_daoism.fluid;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.block.ModBlocks;
import net.hendersondaniel.gu_daoism.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, GuDaoism.MOD_ID);

    public static final RegistryObject<FlowingFluid> SOURCE_PRIMEVAL_ESSENCE_LIQUID = FLUIDS.register("primeval_essence_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluids.PRIMEVAL_ESSENCE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_PRIMEVAL_ESSENCE_FLUID = FLUIDS.register("flowing_primeval_essence_fluid",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.PRIMEVAL_ESSENCE_FLUID_PROPERTIES));


    public static final ForgeFlowingFluid.Properties PRIMEVAL_ESSENCE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidTypes.PRIMEVAL_ESSENCE_FLUID_TYPE, SOURCE_PRIMEVAL_ESSENCE_LIQUID, FLOWING_PRIMEVAL_ESSENCE_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(2).block(ModBlocks.PRIMEVAL_ESSENCE_FLUID_BLOCK).bucket(ModItems.PRIMEVAL_ESSENCE_BUCKET);


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
