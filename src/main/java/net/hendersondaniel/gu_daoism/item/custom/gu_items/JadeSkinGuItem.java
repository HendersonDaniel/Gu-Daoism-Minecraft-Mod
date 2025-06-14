package net.hendersondaniel.gu_daoism.item.custom.gu_items;

import net.hendersondaniel.gu_daoism.entity.ModEntities;
import net.hendersondaniel.gu_daoism.entity.custom.AbstractGuEntity;
import net.hendersondaniel.gu_daoism.entity.custom.JadeSkinGuEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class JadeSkinGuItem extends AbstractGuItem {
    public JadeSkinGuItem(Properties properties, Supplier<? extends EntityType<?>> entityTypeSupplier) {
        super(properties, entityTypeSupplier);

        setRank(1);
        setFoodType("Jade");
        setMaxSatiationTime(1200*180);
        setPrimevalEssenceCost(10.0);
    }

    @Override
    protected void runGuEffect(Level level, Player player) {
        player.sendSystemMessage(Component.literal("(S)")
                .withStyle(ChatFormatting.YELLOW));
    }







}
