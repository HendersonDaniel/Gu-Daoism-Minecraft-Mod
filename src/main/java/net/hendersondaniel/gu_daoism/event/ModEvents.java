package net.hendersondaniel.gu_daoism.event;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStats;
import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.hendersondaniel.gu_daoism.item.custom.gu_items.AbstractGuItem;
import net.hendersondaniel.gu_daoism.networking.ModMessages;
import net.hendersondaniel.gu_daoism.networking.packet.PrimevalEssenceSyncS2CPacket;
import net.hendersondaniel.gu_daoism.networking.packet.RawStageSyncS2CPacket;
import net.hendersondaniel.gu_daoism.networking.packet.TalentSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static net.hendersondaniel.gu_daoism.item.custom.interactables.GamblingRockItem.createGamblingRockWithNBT;

public class ModEvents {

    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Set<ServerPlayer> trackedPlayers = new HashSet<>();

    @Mod.EventBusSubscriber(modid = GuDaoism.MOD_ID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                if (!event.getObject().getCapability(PlayerStatsProvider.PLAYER_STATS).isPresent()) {
                    event.addCapability(ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, "properties"), new PlayerStatsProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {
                event.getOriginal().getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(oldStore -> {
                    event.getEntity().getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(PlayerStats.class);
        }

        @SubscribeEvent
        public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
            if (event.getEntity() instanceof ServerPlayer) {
                trackedPlayers.add((ServerPlayer) event.getEntity());
            }
        }

        @SubscribeEvent
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
            if(!event.getLevel().isClientSide()) {
                if(event.getEntity() instanceof ServerPlayer player) {
                    player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(s -> {
                        ModMessages.sendToPlayer(new PrimevalEssenceSyncS2CPacket(s.getPrimevalEssence()), player);
                        ModMessages.sendToPlayer(new RawStageSyncS2CPacket(s.getRawStage()),player);
                        ModMessages.sendToPlayer(new TalentSyncS2CPacket(s.getTalent()),player);
                    });
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
            if (event.getEntity() instanceof ServerPlayer) {
                trackedPlayers.remove(event.getEntity());
            }
        }

        @SubscribeEvent
        public static void onServerStarted(ServerStartedEvent event) {
            if (scheduler == null || scheduler.isShutdown()) {
                scheduler = Executors.newScheduledThreadPool(1);
            }
            scheduler.scheduleAtFixedRate(() -> {
                for (ServerPlayer player : trackedPlayers) {
                    player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(stats -> {
                        double maxPrimevalEssence = stats.getMaxPrimevalEssence();
                        double currentPrimevalEssence = stats.getPrimevalEssence();

                        if(currentPrimevalEssence < maxPrimevalEssence){
                            stats.addPrimevalEssence(0.01 * maxPrimevalEssence);
                            ModMessages.sendToPlayer(new PrimevalEssenceSyncS2CPacket(stats.getPrimevalEssence()), player);
                        }

                    });
                }
            }, 0, 6, TimeUnit.SECONDS); //every one-tenth of a minute, regen one-hundredth of the maxPrimevalEssence. This means every minute, generate one-tenth.
        }

        @SubscribeEvent
        public static void onServerStopping(ServerStoppingEvent event) {
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdown();
                try {
                    if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                        scheduler.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    scheduler.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        }

        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {

            if(event.getLevel().isClientSide()) return;

            if (!(event.getEntity() instanceof ItemEntity itemEntity)) return;

            if (!(itemEntity.getItem().getItem() instanceof AbstractGuItem item)) return;

            // spawn gu entity
            // TODO: make it a gu and not a pig

            Level level = event.getLevel();
            Vec3 position = itemEntity.position();

            Pig pig = EntityType.PIG.create(level);
            if (pig != null) {
                pig.setPos(position);
                level.addFreshEntity(pig);
            }

            event.setCanceled(true); // prevent gu item from appearing
        }




        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;

            // Only run on server side and END phase
            if (player.getLevel().isClientSide() || event.phase != TickEvent.Phase.END) return;

            long currentWorldTime = player.getLevel().getGameTime();

            // Loop through inventory
            for (int i = 0; i < player.getInventory().items.size(); i++) {
                ItemStack stack = player.getInventory().getItem(i);



                if (!(stack.getItem() instanceof AbstractGuItem)) continue;

                AbstractGuItem item = (AbstractGuItem) stack.getItem();

                CompoundTag tag = stack.getOrCreateTag();

                if (!tag.contains("LastFedTime")) {
                    tag.putLong("LastFedTime", currentWorldTime);
                    continue;
                }

                long lastFedTime = tag.getLong("LastFedTime");
                long ticksSinceFed = currentWorldTime - lastFedTime;
                long ticksRemaining = Math.max(0, item.getMaxSatiationTime() - ticksSinceFed);

                if (ticksRemaining <= 0) {
                    ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
                    if(id == null) return;
                    ItemStack newStack = createGamblingRockWithNBT(id.toString(), player.getLevel().random.nextFloat());
                    player.getInventory().setItem(i, newStack);
                }
            }
        }
    }
}
