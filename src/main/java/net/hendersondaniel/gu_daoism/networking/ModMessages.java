package net.hendersondaniel.gu_daoism.networking;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.networking.packet.PrimevalEssenceC2SPacket;
import net.hendersondaniel.gu_daoism.networking.packet.PrimevalEssenceSyncS2CPacket;
import net.hendersondaniel.gu_daoism.networking.packet.RawStageSyncS2CPacket;
import net.hendersondaniel.gu_daoism.networking.packet.TalentSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class ModMessages {
    private static final String PROTOCOL_VERSION = "1.0";
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(GuDaoism.MOD_ID, "aperture_messages"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );

        // Registering messages
        INSTANCE.registerMessage(id(), PrimevalEssenceC2SPacket.class, PrimevalEssenceC2SPacket::toBytes, PrimevalEssenceC2SPacket::new, PrimevalEssenceC2SPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        INSTANCE.registerMessage(id(), PrimevalEssenceSyncS2CPacket.class, PrimevalEssenceSyncS2CPacket::toBytes, PrimevalEssenceSyncS2CPacket::new, PrimevalEssenceSyncS2CPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(id(), RawStageSyncS2CPacket.class, RawStageSyncS2CPacket::toBytes, RawStageSyncS2CPacket::new, RawStageSyncS2CPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        INSTANCE.registerMessage(id(), TalentSyncS2CPacket.class, TalentSyncS2CPacket::toBytes, TalentSyncS2CPacket::new, TalentSyncS2CPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

//        INSTANCE.registerMessage(id(), EnergySyncS2CPacket.class, EnergySyncS2CPacket::toBytes, EnergySyncS2CPacket::new, EnergySyncS2CPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
//        INSTANCE.registerMessage(id(), FluidSyncS2CPacket.class, FluidSyncS2CPacket::toBytes, FluidSyncS2CPacket::new, FluidSyncS2CPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
//        INSTANCE.registerMessage(id(), ItemStackSyncS2CPacket.class, ItemStackSyncS2CPacket::toBytes, ItemStackSyncS2CPacket::new, ItemStackSyncS2CPacket::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

     }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
