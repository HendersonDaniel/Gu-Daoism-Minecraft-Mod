package net.hendersondaniel.gu_daoism.aperture.aperture_gui_screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStats;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static net.hendersondaniel.gu_daoism.util.FormattingMethods.*;
import static net.hendersondaniel.gu_daoism.util.FormattingMethods.rawStageToPrimevalSeaRGB;

public class CultivationStatsScreen extends Screen {

    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID,"textures/gui/blank_container.png");
    private static final ResourceLocation EMPTY_APERTURE = ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, "textures/aperture/empty_aperture.png");
    private static final ResourceLocation FILLED_APERTURE = ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, "textures/aperture/filled_aperture.png");
    private static final Component TITLE = Component.literal("Primeval Sea");

    private final PlayerStats playerStats;

    private final int imageWidth, imageHeight;
    private int leftPos, topPos;

    public CultivationStatsScreen(PlayerStats playerStats) {
        super(TITLE);
        this.imageWidth = 256;
        this.imageHeight = 256;
        this.playerStats = playerStats;
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        // Render transparent background
        this.renderBackground(poseStack);

        // Render custom background
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        blit(poseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // Draw title
        //drawCenteredString(poseStack, this.font, TITLE, this.width / 2, topPos + 15, 0x404040);

        // Draw centered title
        int titleWidth = font.width(TITLE); // Get the pixel width of the text
        font.draw(poseStack,
                TITLE,
                (float)(this.width / 2 - titleWidth / 2), // Properly centered calculation
                topPos + 15,
                0x404040);

        font.draw(poseStack,
                Component.literal("Rank: " + rawStageToRealm(playerStats.getRawStage())),
                leftPos + 20, topPos + 30,
                0x404040);

        font.draw(poseStack,
                Component.literal("Stage: " + stageIntToStageName(playerStats.getRawStage() % 4)),
                leftPos + 20, topPos + 40,
                0x404040);

        font.draw(poseStack,
                Component.literal("Talent: " + playerStats.getTalent() + "%"),
                leftPos + 20, topPos + 50,
                0x404040);

        font.draw(poseStack,
                Component.literal("Progress: " + totalProgressToStageProgress(playerStats.getCultivationProgress(), playerStats.getRawStage())),
                leftPos + 20, topPos + 60,
                0x404040);
//
//        font.draw(poseStack,
//                Component.literal("Essence: " + (int) playerStats.getPrimevalEssence() + "/" + (int) playerStats.getMaxPrimevalEssence()),
//                leftPos + 20, topPos + 70,
//                0x404040);


        renderAperture(poseStack);

        // Call super last for widgets
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void renderAperture(PoseStack poseStack) {
        if (playerStats.getTalent() <= 0) {
            return;
        }

        float[] wallColor = rawStageToApertureWallRGB(playerStats.getRawStage());
        float wallRed = wallColor[0];
        float wallGreen = wallColor[1];
        float wallBlue = wallColor[2];

        float[] seaColor = rawStageToPrimevalSeaRGB(playerStats.getRawStage());
        float seaRed = seaColor[0];
        float seaGreen = seaColor[1];
        float seaBlue = seaColor[2];

        float scale = 3f;
        int baseSize = 48;
        int scaledSize = (int)(baseSize * scale);

        int centerX = this.width / 2;
        int centerY = this.height / 2 + 30;
        int apertureX = centerX - scaledSize / 2;
        int apertureY = centerY - scaledSize / 2;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(wallRed, wallGreen, wallBlue, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_APERTURE);

        poseStack.pushPose();
        poseStack.translate(apertureX, apertureY, 0);
        poseStack.scale(scale, scale, 1.0f);
        blit(poseStack, 0, 0, 0, 0, baseSize, baseSize, baseSize, baseSize);
        poseStack.popPose();

        int maxAperture = 100 * (int) Math.pow(2, playerStats.getRawStage());
        double currentPrimevalEssence = playerStats.getPrimevalEssence();
        float primevalEssencePercentage = (float) currentPrimevalEssence / maxAperture;

        int filledHeight = (int) (baseSize * primevalEssencePercentage);
        if (filledHeight > 0) {
            RenderSystem.setShaderColor(seaRed, seaGreen, seaBlue, 1.0F);
            RenderSystem.setShaderTexture(0, FILLED_APERTURE);

            poseStack.pushPose();
            poseStack.translate(apertureX, apertureY + (scaledSize - (filledHeight * scale)), 0);
            poseStack.scale(scale, scale, 1.0f);
            blit(poseStack, 0, 0,
                    0, baseSize - filledHeight, // Source Y
                    baseSize, filledHeight, // Source width and height
                    baseSize, baseSize);
            poseStack.popPose();
        }

        // Reset color to white for other elements
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}

