package net.hendersondaniel.gu_daoism.effect;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.effect.custom.JadeSkinGuEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, GuDaoism.MOD_ID);

    public static final RegistryObject<MobEffect> JADE_SKIN_GU_EFFECT = EFFECTS.register("jade_skin_gu_effect",
            () -> new JadeSkinGuEffect(MobEffectCategory.BENEFICIAL, 0x00FF00));

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}

