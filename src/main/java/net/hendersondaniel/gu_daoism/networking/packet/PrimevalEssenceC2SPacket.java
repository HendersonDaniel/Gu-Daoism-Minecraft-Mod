package net.hendersondaniel.gu_daoism.networking.packet;

import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.hendersondaniel.gu_daoism.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PrimevalEssenceC2SPacket {

    private double primevalEssence = 0; //amount you are subtracting

    public PrimevalEssenceC2SPacket(int primevalEssence){
        this.primevalEssence = primevalEssence;
    }
    public PrimevalEssenceC2SPacket(FriendlyByteBuf buf){
        this.primevalEssence = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(primevalEssence);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // on the server
            ServerPlayer player = context.getSender();
            assert player != null;
            ServerLevel level = player.getLevel();

            player.sendSystemMessage(Component.literal("used a gu").withStyle(ChatFormatting.DARK_AQUA));

            player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(s -> {
                s.subPrimevalEssence(primevalEssence);
                player.sendSystemMessage(Component.literal("Current Primeval Essence: " + s.getPrimevalEssence())
                        .withStyle(ChatFormatting.AQUA));
                ModMessages.sendToPlayer(new PrimevalEssenceSyncS2CPacket(s.getPrimevalEssence()), player);
            });


        });
        supplier.get().setPacketHandled(true);
    }


}
