package net.hendersondaniel.gu_daoism.networking.packet;

import net.hendersondaniel.gu_daoism.client.ClientStatsData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PrimevalEssenceSyncS2CPacket {

    private double primevalEssence = 0; //total

    public PrimevalEssenceSyncS2CPacket(double primevalEssence){
        this.primevalEssence = primevalEssence;
    }
    public PrimevalEssenceSyncS2CPacket(FriendlyByteBuf buf){
        this.primevalEssence = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf){
        buf.writeDouble(primevalEssence);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // on the client
            ClientStatsData.setPrimevalEssence(primevalEssence); //set the client's primeval essence to what it is on the server


        });
        supplier.get().setPacketHandled(true);
    }


}
