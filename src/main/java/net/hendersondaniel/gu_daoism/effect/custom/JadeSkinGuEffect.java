package net.hendersondaniel.gu_daoism.effect.custom;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.Set;

// gives 2 heart dmg reduction per level.
public class JadeSkinGuEffect extends AbstractGuEffect implements IDefenseGuEffect{

    private static final Set<ResourceKey<DamageType>> BYPASS_SOURCES = Set.of(
            DamageTypes.FREEZE,DamageTypes.DROWN,DamageTypes.IN_WALL,DamageTypes.MAGIC,DamageTypes.STARVE,DamageTypes.WITHER,DamageTypes.INDIRECT_MAGIC
    );

    public JadeSkinGuEffect(MobEffectCategory p_19451_, int color) {
        super(p_19451_, color);
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }


    @Override
    public float getDamageAmount(DamageSource source, float amount, int amplifier) {
        if(isBypassed(source,amplifier, BYPASS_SOURCES)) {
            return amount;
        }
        return Math.max(0.0f, amount-4.0f*(amplifier+1));
    }

    @Override
    public DamageReductionType getReductionType() {
        return DamageReductionType.PERCENTAGE;
    }
}
