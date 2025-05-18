package net.hendersondaniel.gu_daoism.item.custom.gu_items;

import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.hendersondaniel.gu_daoism.config.CommonConfigs;
import net.hendersondaniel.gu_daoism.networking.ModMessages;
import net.hendersondaniel.gu_daoism.networking.packet.PrimevalEssenceSyncS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static net.hendersondaniel.gu_daoism.item.custom.interactables.GamblingRockItem.createGamblingRockWithNBT;

public class JadeSkinGuItem extends AGuItem {
    public JadeSkinGuItem(Properties properties) {
        super(properties);
    }

    private final int rank = 1;
    private final String food = "Jade";
    private final long maxSatiationTime = 1200*2; // in ticks
    private final double primevalEssenceCost = 10.0;

    @Override
    protected int getRank() {
        return rank;
    }

    @Override
    protected String getFoodType() {
        return food;
    }

    @Override
    public long getMaxSatiationTime() {
        return maxSatiationTime;
    }

    @Override
    protected double getPrimevalEssenceCost() {
        return primevalEssenceCost;
    }




    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {

            //uses primeval essence
            player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(s -> {

                //if enough primeval essence
                if(s.subPrimevalEssence(getPrimevalEssenceCost())){
                    player.sendSystemMessage(Component.literal("(S) Current Primeval Essence: " + s.getPrimevalEssence())
                            .withStyle(ChatFormatting.YELLOW));
                    ModMessages.sendToPlayer(new PrimevalEssenceSyncS2CPacket(s.getPrimevalEssence()), (ServerPlayer) player);
                } else {
                    player.sendSystemMessage(Component.literal("(F) Current Primeval Essence: " + s.getPrimevalEssence())
                            .withStyle(ChatFormatting.YELLOW));
                }


            });

            //TODO: shoots something here


            //cooldown
            //player.getCooldowns().addCooldown(this, 20);
        }

        return super.use(level, player, hand);
    }

    //TODO: useOn to get the entity to spawn so it goes towards food







}
