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
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
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
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
//            event.registerAboveAll("aperture", PrimevalEssenceHudOverlay.HUD_APERTURE);
            event.registerAbove(ResourceLocation.fromNamespaceAndPath("minecraft","hotbar"),"aperture",PrimevalEssenceHudOverlay.HUD_APERTURE);
        }


    }
}
