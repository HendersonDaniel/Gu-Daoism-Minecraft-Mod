package net.hendersondaniel.gu_daoism.item.custom.gu_items;

import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.hendersondaniel.gu_daoism.entity.custom.AbstractGuEntity;
import net.hendersondaniel.gu_daoism.networking.ModMessages;
import net.hendersondaniel.gu_daoism.networking.packet.PrimevalEssenceSyncS2CPacket;
import net.hendersondaniel.gu_daoism.sounds.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

import static net.hendersondaniel.gu_daoism.util.FormattingMethods.formatTicksToTime;

public abstract class AbstractGuItem extends Item {


    // defaults that must be changed
    private int rank = -1;
    private String food = null;
    private long maxSatiationTime = -1; // in ticks
    private double primevalEssenceCost = -1;
    private final Supplier<? extends EntityType<?>> entityTypeSupplier;
    public AbstractGuItem(Properties p_41383_, Supplier<? extends EntityType<?>> entityTypeSupplier) {
        super(p_41383_);
        this.entityTypeSupplier = entityTypeSupplier;
    }
    public EntityType<?> getEntityType() {
        return entityTypeSupplier.get();
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

    protected abstract void runGuEffect(Level level, Player player, int amplifier);

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {

            //uses primeval essence
            player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(s -> {

                //if enough primeval essence
                if(s.subPrimevalEssence(getPrimevalEssenceCost())){
                    ModMessages.sendToPlayer(new PrimevalEssenceSyncS2CPacket(s.getPrimevalEssence()), (ServerPlayer) player);

                    // run logic for gu effect
                    runGuEffect(level, player, 0); //TODO: change default amplifier of 0 after making gu that amplify

                } else {
                    level.playSound(null, player.blockPosition(),ModSounds.OUT_OF_ESSENCE_SOUND.get(),SoundSource.PLAYERS,0.7F,1.0F);
                }
            });
        }
        return super.use(level, player, hand);
    }


}
