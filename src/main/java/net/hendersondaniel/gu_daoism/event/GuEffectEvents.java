package net.hendersondaniel.gu_daoism.event;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.effect.custom.IDefenseGuEffect;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GuDaoism.MOD_ID)
public class GuEffectEvents {

    @SubscribeEvent
    public static void applyDefenseGuEffects(LivingHurtEvent event) {
        if(!(event.getEntity() instanceof Player entity)) return; // maybe when mobs can do it then change it to entity instead of player
        for (MobEffectInstance effectInstance : entity.getActiveEffects()) {
            MobEffect effect = effectInstance.getEffect();

            if (!(effect instanceof IDefenseGuEffect defenseEffect)) {
                continue;
            }

            DamageSource source = event.getSource();
            int amplifier = effectInstance.getAmplifier();
            float originalDamage = event.getAmount();

            event.setAmount(defenseEffect.getDamageAmount(source, originalDamage, amplifier));

            // Callback for successful reduction
            if (event.getAmount() < originalDamage) {
                defenseEffect.onDamageReduced(entity, source, originalDamage - event.getAmount());
            }
        }
    }
}
