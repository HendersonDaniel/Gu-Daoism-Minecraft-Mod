package net.hendersondaniel.gu_daoism.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.hendersondaniel.gu_daoism.GuDaoism;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class PrimevalEssenceHudOverlay {

    private static final ResourceLocation EMPTY_APERTURE = new ResourceLocation(GuDaoism.MOD_ID, "textures/aperture/empty_aperture.png");
    private static final ResourceLocation FILLED_APERTURE = new ResourceLocation(GuDaoism.MOD_ID, "textures/aperture/filled_aperture.png");

    public static final IGuiOverlay HUD_APERTURE = (gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        // Render the empty aperture
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_APERTURE);
        GuiComponent.blit(poseStack, x - 206, y - 54, 0, 0, 48, 48, 48, 48);

        // Calculate current mana percentage
        int maxAperture = 100 * (int) Math.pow(2, ClientStatsData.getRawStage());
        double currentPrimevalEssence = ClientStatsData.getPrimevalEssence();
        float primevalEssencePercentage = (float) currentPrimevalEssence / maxAperture;

        // Debugging output to console
//        System.out.println("Current Primeval Essence: " + currentPrimevalEssence);
//        System.out.println("Max Aperture: " + maxAperture);
//        System.out.println("Primeval Essence Percentage: " + primevalEssencePercentage);

        try{
            //render filled aperture
            int filledHeight = (int) (48 * primevalEssencePercentage);
            if (filledHeight > 0) {
                RenderSystem.setShaderTexture(0, FILLED_APERTURE);
                // Adjust the y position to render from the bottom up
                GuiComponent.blit(poseStack, x - 206, y - 54 + (48 - filledHeight), 0,  48 - filledHeight, 48, filledHeight, 48, 48);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    };
}
