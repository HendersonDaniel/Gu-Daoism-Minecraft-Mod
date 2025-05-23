package net.hendersondaniel.gu_daoism.event;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.aperture.aperture_gui_screens.CultivationStatsScreen;
import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.hendersondaniel.gu_daoism.client.PrimevalEssenceHudOverlay;
import net.hendersondaniel.gu_daoism.keybindings.ModKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = GuDaoism.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (ModKeyBindings.KEY_OPEN_APERTURE_SCREEN.consumeClick()) {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null) {

                    player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(cap -> {
                        Minecraft.getInstance().setScreen(new CultivationStatsScreen(cap));
                    });
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = GuDaoism.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBindings.KEY_OPEN_APERTURE_SCREEN);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
//            event.registerAboveAll("aperture", PrimevalEssenceHudOverlay.HUD_APERTURE);
            event.registerAbove(ResourceLocation.fromNamespaceAndPath("minecraft","hotbar"),"aperture",PrimevalEssenceHudOverlay.HUD_APERTURE);
        }


    }
}
