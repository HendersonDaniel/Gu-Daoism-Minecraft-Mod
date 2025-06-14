package net.hendersondaniel.gu_daoism.networking.packet;

import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TalentSyncS2CPacket {

    private int talent = 0;

    public TalentSyncS2CPacket(int talent){
        this.talent = talent;
    }
    public TalentSyncS2CPacket(FriendlyByteBuf buf){
        this.talent = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(talent);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // on the client
            Minecraft.getInstance().player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(cap -> {
                cap.setTalent(talent);
            });


        });
        supplier.get().setPacketHandled(true);
    }

}
