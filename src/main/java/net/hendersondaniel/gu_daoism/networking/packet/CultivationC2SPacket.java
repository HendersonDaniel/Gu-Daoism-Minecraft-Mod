package net.hendersondaniel.gu_daoism.networking.packet;

import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.hendersondaniel.gu_daoism.event.ModEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CultivationC2SPacket {

    private boolean isCultivating = false;

    public CultivationC2SPacket(boolean isCultivating){
        this.isCultivating = isCultivating;
    }
    public CultivationC2SPacket(FriendlyByteBuf buf){
        this.isCultivating = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeBoolean(isCultivating);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // on the server
            ServerPlayer player = context.getSender();
            if(player == null) return;

            player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(s -> {

                if(isCultivating){
                    ModEvents.ForgeEvents.startCultivating(player);
                } else {
                    ModEvents.ForgeEvents.stopCultivating(player);
                }



            });


        });
        supplier.get().setPacketHandled(true);
    }

}
