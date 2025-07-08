package net.hendersondaniel.gu_daoism.event;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.hendersondaniel.gu_daoism.networking.ModMessages;
import net.hendersondaniel.gu_daoism.networking.packet.CultivationSyncS2CPacket;
import net.hendersondaniel.gu_daoism.networking.packet.PrimevalEssenceSyncS2CPacket;
import net.hendersondaniel.gu_daoism.networking.packet.RawStageSyncS2CPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = GuDaoism.MOD_ID)
public class CultivationEvents {

    private static final Map<UUID, Vec3> cultivatingPlayers = new HashMap<>();

    private static boolean positionsAreClose(Vec3 a, Vec3 b) {
        // Allow small movements
        double threshold = 0.05;
        return a.distanceToSqr(b) < threshold * threshold;
    }

    public static void startCultivating(ServerPlayer player) {
        cultivatingPlayers.put(player.getUUID(), player.position());
    }

    public static void stopCultivating(ServerPlayer player) {
        cultivatingPlayers.remove(player.getUUID());
    }

    public static boolean isCultivating(ServerPlayer player) {
        return cultivatingPlayers.containsKey(player.getUUID());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        MinecraftServer server = event.getServer();
        if (server == null) return;
        if(server.getTickCount() % 20 != 0) return;

        for (ServerLevel level : server.getAllLevels()) {
            for (ServerPlayer player : level.players()) {
                UUID uuid = player.getUUID();

                if (cultivatingPlayers.containsKey(uuid)) {
                    Vec3 startPos = cultivatingPlayers.get(uuid);
                    Vec3 currentPos = player.position();

                    // Check if player has moved significantly
                    if (!positionsAreClose(startPos, currentPos)) {
                        cultivatingPlayers.remove(uuid);
                        player.sendSystemMessage(Component.literal("You moved! Cultivation interrupted.")); //TODO: remove this and make it a sound
                        continue;
                    }

                    player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(s -> {
                        s.addCultivationProgress(1);
                        s.subPrimevalEssence(1*Math.pow(2,s.getRawStage()));

                        ModMessages.sendToPlayer(new PrimevalEssenceSyncS2CPacket(s.getPrimevalEssence()), player);
                        ModMessages.sendToPlayer(new RawStageSyncS2CPacket(s.getRawStage()),player);
                        ModMessages.sendToPlayer(new CultivationSyncS2CPacket(s.getCultivationProgress()),player);
                    });
                }
            }
        }
    }

}
