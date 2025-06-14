package net.hendersondaniel.gu_daoism.entity.custom;

import net.hendersondaniel.gu_daoism.entity.goal.FleeFromPlayerGoal;
import net.hendersondaniel.gu_daoism.item.ModItems;
import net.hendersondaniel.gu_daoism.item.custom.gu_items.AbstractGuItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;


public class JadeSkinGuEntity extends AbstractGuEntity {

    public JadeSkinGuEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level, () -> (AbstractGuItem) ModItems.JADE_SKIN_GU_ITEM.get());
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.4f).build();
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.0D, 6.0F, 5.0F, false));
        this.goalSelector.addGoal(4,new FleeFromPlayerGoal(this,6.0D,1.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        // TODO: Goal to go to food
        //this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, 10.0F));

    }



    @Override
    protected <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {
        // First check if entity is moving using navigation
        if (this.getNavigation().isInProgress()) {
            return state.setAndContinue(RawAnimation.begin()
                    .then("animation.jade_skin_gu.walk", Animation.LoopType.LOOP));
        }

        return state.setAndContinue(RawAnimation.begin()
                .then("animation.jade_skin_gu.idle", Animation.LoopType.LOOP));
    }
}
