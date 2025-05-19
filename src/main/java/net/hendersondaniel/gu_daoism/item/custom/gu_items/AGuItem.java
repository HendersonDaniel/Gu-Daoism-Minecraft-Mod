package net.hendersondaniel.gu_daoism.item.custom.gu_items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import static net.hendersondaniel.gu_daoism.util.FormattingMethods.formatTicksToTime;

public abstract class AGuItem extends Item {


    // defaults that must be changed
    private int rank = -1;
    private String food = null;
    private long maxSatiationTime = -1; // in ticks
    private double primevalEssenceCost = -1;

    public AGuItem(Properties p_41383_) {
        super(p_41383_);
    }


    protected ChatFormatting getRankColor(){
        switch(getRank()){
            case 1:
                return ChatFormatting.DARK_GREEN;
            case 2:
                return ChatFormatting.DARK_RED;
            case 3:
                return ChatFormatting.GRAY;
            case 4:
                return ChatFormatting.GOLD;
            case 5:
                return ChatFormatting.DARK_PURPLE;
            default:
                return ChatFormatting.BLACK;
        }
    }

    public int getRank() {
        return rank;
    }

    public String getFoodType() {
        return food;
    }

    public long getMaxSatiationTime() {
        return maxSatiationTime;
    }

    public double getPrimevalEssenceCost() {
        return primevalEssenceCost;
    }

    protected void setRank(int rank) {
        this.rank = rank;
    }

    protected void setFoodType(String food) {
        this.food = food;
    }

    protected void setMaxSatiationTime(long maxSatiationTime) {
        this.maxSatiationTime = maxSatiationTime;
    }

    protected void setPrimevalEssenceCost(double primevalEssenceCost) {
        this.primevalEssenceCost = primevalEssenceCost;
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            CompoundTag tag = stack.getTag();

            components.add(Component.literal("Rank "+getRank()).withStyle(getRankColor()));

            if (tag != null && tag.contains("LastFedTime")) {
                long lastFedTime = tag.getLong("LastFedTime");
                long currentWorldTime = level != null ? level.getGameTime() : 0;
                long ticksSinceFed = currentWorldTime - lastFedTime;
                long ticksRemaining = Math.max(0, getMaxSatiationTime() - ticksSinceFed);

                if (ticksRemaining <= 0) {
                    components.add(Component.literal("Starving.").withStyle(ChatFormatting.RED));
                } else {
                    components.add(Component.literal("Starvation in: " + formatTicksToTime(ticksRemaining))
                            .withStyle(ChatFormatting.GRAY));
                }
            }

            components.add(Component.literal("Eats: " + getFoodType()).withStyle(ChatFormatting.GRAY));
        } else {
            components.add(Component.literal("Hold SHIFT for more info").withStyle(ChatFormatting.YELLOW));
        }

        super.appendHoverText(stack, level, components, flag);
    }
}
