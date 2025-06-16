package net.hendersondaniel.gu_daoism.effect.custom;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

import java.util.Set;

public interface IDefenseGuEffect {

    /**
     * Checks if this defense effect bypasses the damage.
     * @param source The damage source being checked
     * @param amplifier The amplifier level of the effect (0 = base level)
     * @return true if the damage source should be completely negated
     */
    public default boolean isBypassed(DamageSource source, int amplifier, Set<ResourceKey<DamageType>> bypassSources) {
        for(ResourceKey<DamageType> s : bypassSources) {
            if(source.is(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the damage amount for this effect after reduction i
     * @param source The incoming damage source
     * @param amount The original damage amount
     * @param amplifier The amplifier level of the effect
     * @return The amount of damage
     */
    float getDamageAmount(DamageSource source, float amount, int amplifier);

    /**
     * The type of damage reduction this effect provides
     */
    DamageReductionType getReductionType();

    /**
     * Whether this effect should apply before or after armor calculations
     */
    default ApplicationOrder getApplicationOrder() {
        return ApplicationOrder.POST_ARMOR;
    }

    /**
     * Whether this effect stacks with other defense effects
     */
    default boolean isStackable() {
        return true;
    }

    /**
     * Whether this effect should be applied even when the entity is invulnerable
     */
    default boolean worksWhenInvulnerable() {
        return false;
    }

    /**
     * Called when damage is successfully reduced by this effect
     */
    default void onDamageReduced(LivingEntity entity, DamageSource source, float reducedAmount) {
    }

    public enum DamageReductionType {
        FLAT,       // Reduces damage by a fixed amount
        PERCENTAGE, // Reduces damage by a percentage
        HYBRID      // First applies percentage, then flat reduction
    }

    public enum ApplicationOrder {
        PRE_ARMOR,  // Applied before armor calculations
        POST_ARMOR  // Applied after armor calculations
    }
}