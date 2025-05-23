package net.hendersondaniel.gu_daoism.fluid;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidTypes {

    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.parse("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = ResourceLocation.parse("block/water_flow");
    public static final ResourceLocation PRIMEVAL_ESSENCE_FLUID_OVERLAY_RL = ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, "misc/in_primeval_essence_fluid");

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, GuDaoism.MOD_ID);

    public static final RegistryObject<FluidType> PRIMEVAL_ESSENCE_FLUID_TYPE = register("primeval_essence_fluid",
            FluidType.Properties.create().lightLevel(15).density(15).viscosity(5));



    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, PRIMEVAL_ESSENCE_FLUID_OVERLAY_RL,
                0xFFA6FCF6, new Vector3f(166f / 255f, 252f / 255f, 246f / 255f), properties));
    }
//    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
//        return FLUID_TYPES.register(name, () -> new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, PRIMEVAL_ESSENCE_FLUID_OVERLAY_RL,
//                0xFFFFFFFF, new Vector3f(255f / 255f, 255f / 255f, 255f / 255f), properties));
//    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }

}
