package net.hendersondaniel.gu_daoism.client;

import ca.weblite.objc.Client;
import com.mojang.blaze3d.systems.RenderSystem;
import net.hendersondaniel.gu_daoism.GuDaoism;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class PrimevalEssenceHudOverlay {

    private static final ResourceLocation EMPTY_APERTURE = ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, "textures/aperture/empty_aperture.png");
    private static final ResourceLocation FILLED_APERTURE = ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, "textures/aperture/filled_aperture.png");

    public static final IGuiOverlay HUD_APERTURE = (gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        try {

            if(ClientStatsData.getTalent() <= 0){
                return;
            }

            float wallRed, wallGreen, wallBlue;
            float seaRed, seaGreen, seaBlue;

            // aperture wall color
            switch (ClientStatsData.getRawStage() % 4) {
                case 0: // wall should be a yellow
                    wallRed = 1.0F; // 255
                    wallGreen = 216 / 255.0F; // 216
                    wallBlue = 0.0F; // 0
                    break;
                case 1: // wall should be a blue
                    wallRed = 0.0F; // 0
                    wallGreen = 105 / 255.0F; // 105
                    wallBlue = 1.0F; // 255
                    break;
                case 2: // wall should be a gray
                    wallRed = 128 / 255.0F; // 128
                    wallGreen = 128 / 255.0F;
                    wallBlue = 128 / 255.0F;
                    break;
                case 3: // wall should be a light lilac
                    wallRed = 159 / 255.0F;
                    wallGreen = 167 / 255.0F;
                    wallBlue = 1.0F;
                    break;
                default: // wall should be black
                    wallRed = 0.0F;
                    wallGreen = 0.0F;
                    wallBlue = 0.0F;
                    break;
            }

            // primeval sea color
            switch (ClientStatsData.getRawStage() / 4) {
                case 0: // sea should be green
                    seaRed = 144 / 255.0F;
                    seaGreen = 238 / 255.0F;
                    seaBlue = 144 / 255.0F;
                    break;
                case 1: // sea should be red
                    seaRed = 1.0F;
                    seaGreen = 0.0F;
                    seaBlue = 0.0F;
                    break;
                case 2: // sea should be silver/white
                    seaRed = 245 / 255.0F;
                    seaGreen = 245 / 255.0F;
                    seaBlue = 245 / 255.0F;
                    break;
                case 3: // sea should be gold/yellow
                    seaRed = 244 / 255.0F;
                    seaGreen = 224 / 255.0F;
                    seaBlue = 0.0F;
                    break;
                case 4: // sea should be purple
                    seaRed = 178 / 255.0F;
                    seaGreen = 118 / 255.0F;
                    seaBlue = 242 / 255.0F;
                    break;
                default: // sea should be black
                    seaRed = 0.0F;
                    seaGreen = 0.0F;
                    seaBlue = 0.0F;
                    break;
            }

            // Render the empty aperture
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(wallRed, wallGreen, wallBlue, 1.0F);
            RenderSystem.setShaderTexture(0, EMPTY_APERTURE);
            GuiComponent.blit(poseStack, x - 206, y - 54, 0, 0, 48, 48, 48, 48);

            // Calculate current mana percentage
            int maxAperture = 100 * (int) Math.pow(2, ClientStatsData.getRawStage());
            double currentPrimevalEssence = ClientStatsData.getPrimevalEssence();
            float primevalEssencePercentage = (float) currentPrimevalEssence / maxAperture;


            //        System.out.println("Current Primeval Essence: " + currentPrimevalEssence);
            //        System.out.println("Max Aperture: " + maxAperture);
            //        System.out.println("Primeval Essence Percentage: " + primevalEssencePercentage);


            //render filled aperture
            int filledHeight = (int) (48 * primevalEssencePercentage);
            if (filledHeight > 0) {
                RenderSystem.setShaderColor(seaRed, seaGreen, seaBlue, 1.0F);
                RenderSystem.setShaderTexture(0, FILLED_APERTURE);
                GuiComponent.blit(poseStack, x - 206, y - 54 + (48 - filledHeight), 0, 48 - filledHeight, 48, filledHeight, 48, 48);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    };
}
