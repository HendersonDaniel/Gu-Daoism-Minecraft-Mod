package net.hendersondaniel.gu_daoism.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStatsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static net.hendersondaniel.gu_daoism.util.FormattingMethods.rawStageToApertureWallRGB;
import static net.hendersondaniel.gu_daoism.util.FormattingMethods.rawStageToPrimevalSeaRGB;

public class PrimevalEssenceHudOverlay {

    private static final ResourceLocation EMPTY_APERTURE = ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, "textures/aperture/empty_aperture.png");
    private static final ResourceLocation FILLED_APERTURE = ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, "textures/aperture/filled_aperture.png");

    private static final int X_OFFSET = 10;
    private static final int Y_OFFSET = 10;
    private static final int APERTURE_SIZE = 48;

    public static final IGuiOverlay HUD_APERTURE = (gui, poseStack, partialTick, width, height) -> {

        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.getCapability(PlayerStatsProvider.PLAYER_STATS).ifPresent(cap -> {

            int x = X_OFFSET;
            int y = height - Y_OFFSET - APERTURE_SIZE;


            try {

                if(cap.getTalent() <= 0){
                    return;
                }

                float[] wallColor = rawStageToApertureWallRGB(cap.getRawStage());
                float wallRed = wallColor[0];
                float wallGreen = wallColor[1];
                float wallBlue = wallColor[2];

                float[] seaColor = rawStageToPrimevalSeaRGB(cap.getRawStage());
                float seaRed = seaColor[0];
                float seaGreen = seaColor[1];
                float seaBlue = seaColor[2];


                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(wallRed, wallGreen, wallBlue, 1.0F);
                RenderSystem.setShaderTexture(0, EMPTY_APERTURE);
                GuiComponent.blit(poseStack, x, y, 0, 0, APERTURE_SIZE, APERTURE_SIZE, APERTURE_SIZE, APERTURE_SIZE);

                // Calculate current essence percentage
                int maxAperture = 100 * (int) Math.pow(2, cap.getRawStage());
                double currentPrimevalEssence = cap.getPrimevalEssence();
                float primevalEssencePercentage = (float) currentPrimevalEssence / maxAperture;

                // Render filled aperture
                int filledHeight = (int) (APERTURE_SIZE * primevalEssencePercentage);
                if (filledHeight > 0) {
                    RenderSystem.setShaderColor(seaRed, seaGreen, seaBlue, 1.0F);
                    RenderSystem.setShaderTexture(0, FILLED_APERTURE);
                    GuiComponent.blit(poseStack, x, y + (APERTURE_SIZE - filledHeight),
                            0, APERTURE_SIZE - filledHeight,
                            APERTURE_SIZE, filledHeight,
                            APERTURE_SIZE, APERTURE_SIZE);
                }


            } catch(Exception e){
                e.printStackTrace();
            }



        });
    };
}
