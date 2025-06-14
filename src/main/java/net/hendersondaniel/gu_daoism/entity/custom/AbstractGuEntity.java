package net.hendersondaniel.gu_daoism.entity.custom;

import net.hendersondaniel.gu_daoism.item.custom.gu_items.AbstractGuItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.function.Supplier;

public abstract class AbstractGuEntity extends TamableAnimal implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final Supplier<? extends AbstractGuItem> itemSupplier;
    private long lastFedTime = 0;


    public AbstractGuEntity(EntityType<? extends TamableAnimal> entityType, Level level, Supplier<? extends AbstractGuItem> itemSupplier) {
        super(entityType, level);
        this.itemSupplier = itemSupplier;
    }

    public void setLastFedTime(long lastFedTime) {
        this.lastFedTime = lastFedTime;
    }
    public long getLastFedTime() {
        return lastFedTime;
    }

    public ItemStack getAsItem() {
        ItemStack stack = new ItemStack(itemSupplier.get(),1);
        CompoundTag tag = stack.getOrCreateTag();
        tag.putLong("LastFedTime", lastFedTime);
        return stack;
    }



    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }
    protected abstract <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state);


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }


    protected void handleFleeBehavior(double range) {
        if (!isTame()) {
            Player player = this.getLevel().getNearestPlayer(this, range);
            if (player != null) {
                Vec3 fleeDir = this.position().subtract(player.position()).normalize();
                Vec3 fleePos = this.position().add(fleeDir.scale(5));
                this.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 1.2D);
            }
        }
    }



}
