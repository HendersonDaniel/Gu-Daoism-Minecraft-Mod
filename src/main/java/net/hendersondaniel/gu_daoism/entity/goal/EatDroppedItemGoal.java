package net.hendersondaniel.gu_daoism.entity.goal;

import net.hendersondaniel.gu_daoism.entity.custom.AbstractGuEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class EatDroppedItemGoal extends Goal {
    private final PathfinderMob mob;
    private final Predicate<ItemEntity> itemSelector;
    private final int cooldownTicks;
    private final int requiredEatCount; // Number of items needed before setting lastFedTime
    private int currentEatCount;
    private int cooldownTimer;
    private ItemEntity targetItem;

    public EatDroppedItemGoal(PathfinderMob mob, Item item, int cooldownTicks, int requiredEatCount) {
        this(mob, (itemEntity) -> itemEntity.getItem().is(item), cooldownTicks, requiredEatCount);
    }

    public EatDroppedItemGoal(PathfinderMob mob, Predicate<ItemEntity> itemSelector, int cooldownTicks, int requiredEatCount) {
        this.mob = mob;
        this.itemSelector = itemSelector;
        this.cooldownTicks = cooldownTicks;
        this.requiredEatCount = requiredEatCount;
        this.currentEatCount = 0;
        this.cooldownTimer = 0;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.cooldownTimer > 0) {
            this.cooldownTimer--;
            return false;
        }

        List<ItemEntity> items = mob.getLevel().getEntitiesOfClass(
                ItemEntity.class,
                mob.getBoundingBox().inflate(8.0),
                itemEntity -> itemSelector.test(itemEntity) && itemEntity.isAlive() && !itemEntity.getItem().isEmpty()
        );

        if (items.isEmpty()) {
            return false;
        }

        targetItem = items.stream()
                .min((a, b) -> Double.compare(
                        mob.distanceToSqr(a),
                        mob.distanceToSqr(b)
                ))
                .orElse(null);

        return targetItem != null;
    }

    @Override
    public boolean canContinueToUse() {
        if (targetItem == null || !targetItem.isAlive() || targetItem.getItem().isEmpty()) {
            return false;
        }

        return mob.getNavigation().isInProgress() || mob.distanceToSqr(targetItem) < 4.0D;
    }

    @Override
    public void start() {
        if (targetItem != null) {
            mob.getNavigation().moveTo(targetItem, 1.2D);
        }
    }

    @Override
    public void tick() {
        if (targetItem == null) {
            return;
        }

        mob.getLookControl().setLookAt(targetItem, 30.0F, 30.0F);

        if (mob.distanceToSqr(targetItem) < 1.0D && isFacingItem()) {
            eat(targetItem);
        } else if (!mob.getNavigation().isInProgress()) {
            mob.getNavigation().moveTo(targetItem, 1.0D);
        }
    }

    private boolean isFacingItem() {
        if (targetItem == null) return false;

        Vec3 mobFacing = mob.getViewVector(1.0F).normalize();
        Vec3 toItem = new Vec3(
                targetItem.getX() - mob.getX(),
                targetItem.getY() - mob.getY(),
                targetItem.getZ() - mob.getZ()
        ).normalize();

        return mobFacing.dot(toItem) > 0.707;
    }

    private void eat(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        if (stack.isEmpty()) {
            return;
        }

        stack.shrink(1);
        currentEatCount++;

        if (mob instanceof AbstractGuEntity abstractGuEntity) {
            if (currentEatCount >= requiredEatCount) {
                abstractGuEntity.setLastFedTime(mob.getLevel().getGameTime());
                abstractGuEntity.setEating(true);
                currentEatCount = 0;
            }
        }

        if (stack.isEmpty()) {
            itemEntity.discard();
        }

        cooldownTimer = cooldownTicks;
        mob.getNavigation().stop();
    }

    @Override
    public void stop() {
        targetItem = null;
        mob.getNavigation().stop();
    }
}