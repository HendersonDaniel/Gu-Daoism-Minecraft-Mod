package net.hendersondaniel.gu_daoism.networking.packet;

import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CultivationSyncS2CPacket {

    private final int cultivationProgress; //total progress

    public CultivationSyncS2CPacket(int cultivationProgress){
        this.cultivationProgress = cultivationProgress;
    }
    public CultivationSyncS2CPacket(FriendlyByteBuf buf){
        this.cultivationProgress = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(cultivationProgress);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // on the client
            if(Minecraft.getInstance().player == null) return;
            Minecraft.getInstance().player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(cap -> {
                cap.setCultivationProgress(cultivationProgress);
                // TODO: check if breakthrough and provide sound and particle effects
            });


        });
        supplier.get().setPacketHandled(true);
    }
}
