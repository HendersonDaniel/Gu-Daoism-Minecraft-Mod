package net.hendersondaniel.gu_daoism.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class OpenApertureC2SPacket {


    //int cultivationPoints;

    public OpenApertureC2SPacket() {}

    public OpenApertureC2SPacket(FriendlyByteBuf buf) {
        //this.cultivationPoints = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        //buf.writeInt(cultivationPoints);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player1 = context.getSender();
            if (player1 != null) {

//                NetworkHooks.openScreen(player1, new SimpleMenuProvider(
//                        (containerId, playerInventory, player) -> new MyMenu(containerId, playerInventory),
//                        Component.literal("Aperture")
//                ));
            }
        });
    }
}

