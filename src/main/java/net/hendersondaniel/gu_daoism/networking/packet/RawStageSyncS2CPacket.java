package net.hendersondaniel.gu_daoism.networking.packet;

import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RawStageSyncS2CPacket {

    private int rawStage = 0;

    public RawStageSyncS2CPacket(int rawStage){
        this.rawStage = rawStage;
    }
    public RawStageSyncS2CPacket(FriendlyByteBuf buf){
        this.rawStage = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(rawStage);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // on the client
            Minecraft.getInstance().player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(cap -> {
                cap.setRawStage(rawStage);
            });


        });
        supplier.get().setPacketHandled(true);
    }

}
