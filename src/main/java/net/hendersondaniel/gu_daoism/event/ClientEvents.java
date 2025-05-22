package net.hendersondaniel.gu_daoism.event;

import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.client.PrimevalEssenceHudOverlay;
import net.hendersondaniel.gu_daoism.keybindings.ModKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

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
                    //ModMessages.sendToServer(new OpenApertureC2SPacket());
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
            event.registerAboveAll("aperture", PrimevalEssenceHudOverlay.HUD_APERTURE);
        }


    }
}
