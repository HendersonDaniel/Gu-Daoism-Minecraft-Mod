package net.hendersondaniel.gu_daoism.entity.goal;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class FleeFromPlayerGoal extends Goal {
    private final TamableAnimal entity;
    private final double fleeRange;
    private final double speedModifier;

    public FleeFromPlayerGoal(TamableAnimal entity, double fleeRange, double speedModifier) {
        this.entity = entity;
        this.fleeRange = fleeRange;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        return !entity.isTame() &&
                entity.getLevel().getNearestPlayer(entity, fleeRange) != null;
    }

    @Override
    public void start() {
        Player player = entity.getLevel().getNearestPlayer(entity, fleeRange);
        if (player != null) {
            Vec3 fleeDir = entity.position().subtract(player.position()).normalize();
            Vec3 fleePos = entity.position().add(fleeDir.scale(5));
            entity.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, speedModifier);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !entity.isTame() &&
                !entity.getNavigation().isDone() &&
                entity.getLevel().getNearestPlayer(entity, fleeRange * 1.5) != null;
    }
}
