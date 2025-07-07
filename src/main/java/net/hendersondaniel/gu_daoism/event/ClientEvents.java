package net.hendersondaniel.gu_daoism.event;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.aperture.aperture_gui_screens.CultivationStatsScreen;
import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.hendersondaniel.gu_daoism.client.PrimevalEssenceHudOverlay;
import net.hendersondaniel.gu_daoism.client.SkinRenderLayer;
import net.hendersondaniel.gu_daoism.effect.ModEffects;
import net.hendersondaniel.gu_daoism.keybindings.ModKeyBindings;
import net.hendersondaniel.gu_daoism.networking.ModMessages;
import net.hendersondaniel.gu_daoism.networking.packet.CultivationC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

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

        private static boolean wasCultivatingKeyDown = false;
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            if (Minecraft.getInstance().player == null) return;

            boolean isDown = ModKeyBindings.KEY_CULTIVATE_APERTURE.isDown();

            if (isDown && !wasCultivatingKeyDown) {
                // Key just pressed
                ModMessages.sendToServer(new CultivationC2SPacket(true));
            }

            if (!isDown && wasCultivatingKeyDown) {
                // Key just released
                ModMessages.sendToServer(new CultivationC2SPacket(false));
            }

            wasCultivatingKeyDown = isDown;
        }

    }

    @Mod.EventBusSubscriber(modid = GuDaoism.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
            for (String skin : List.of("default", "slim")) {
                PlayerRenderer renderer = event.getSkin(skin);
                if (renderer != null) {
                    renderer.addLayer(new SkinRenderLayer(renderer));
                }
            }
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(ModKeyBindings.KEY_OPEN_APERTURE_SCREEN);
            event.register(ModKeyBindings.KEY_CULTIVATE_APERTURE);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAbove(ResourceLocation.fromNamespaceAndPath("minecraft","hotbar"),"aperture",PrimevalEssenceHudOverlay.HUD_APERTURE);
        }


    }
}
