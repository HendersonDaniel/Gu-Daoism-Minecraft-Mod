package net.hendersondaniel.gu_daoism.event;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.hendersondaniel.gu_daoism.fluid.ModFluidTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FluidInteractionEvents {

    private static final FluidType PRIMEVAL_ESSENCE_FLUID_TYPE = ModFluidTypes.PRIMEVAL_ESSENCE_FLUID_TYPE.get();
    private static final int CHECK_INTERVAL = 15;
    private static final float FAST_REGEN_PERCENTAGE = 0.15f;

    @Mod.EventBusSubscriber(modid = GuDaoism.MOD_ID)
    public static class FluidEvents {

        private static final Set<UUID> playersAwakeningAperture = new HashSet<>();

        @SubscribeEvent
        public static void onPlayerInFluid(LivingEvent.LivingTickEvent event) {
            if (!(event.getEntity() instanceof Player player)) return;

            if (player.tickCount % CHECK_INTERVAL != 0) return;

            if (player.isInFluidType(PRIMEVAL_ESSENCE_FLUID_TYPE)) {
                handlePlayerInSpiritSpring(player);
            }
        }

        private static void handlePlayerInSpiritSpring(Player player){

            player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(cap -> {

                UUID playerUUID = player.getUUID();
                boolean isNotAwakened = cap.getTalent() <= 0;

                if(isNotAwakened || playersAwakeningAperture.contains(playerUUID)) {

                    if(isNotAwakened){
                        int hashCode = playerUUID.hashCode();
                        int positiveHashCode = hashCode == Integer.MIN_VALUE ? 0 : Math.abs(hashCode);
                        int talent = positiveHashCode % 78 + 20; // a number between 20 and 98 inclusive.
                        cap.setTalent(talent);
                        playersAwakeningAperture.add(playerUUID);
                    }

                    double currentEssence = cap.getPrimevalEssence();
                    double talent = cap.getTalent();

                    if(currentEssence < talent){
                        cap.addPrimevalEssence(talent*FAST_REGEN_PERCENTAGE);

                        //TODO: particles
                        //TODO: sound
                    } else {
                        playersAwakeningAperture.remove(playerUUID);
                    }

                }

            });

        }

    }
}
