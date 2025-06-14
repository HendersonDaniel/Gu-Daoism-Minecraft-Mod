package net.hendersondaniel.gu_daoism.entity;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.entity.custom.JadeSkinGuEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GuDaoism.MOD_ID);

    public static final RegistryObject<EntityType<JadeSkinGuEntity>> JADE_SKIN_GU_ENTITY =
            ENTITY_TYPES.register("jade_skin_gu_entity",
                    () -> EntityType.Builder.of(JadeSkinGuEntity::new, MobCategory.CREATURE)
                            .sized(0.5f, 0.2f)
                            .build(ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID,"jade_skin_gu_entity").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}