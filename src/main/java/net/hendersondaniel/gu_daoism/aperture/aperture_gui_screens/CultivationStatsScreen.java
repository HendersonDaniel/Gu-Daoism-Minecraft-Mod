package net.hendersondaniel.gu_daoism.aperture.aperture_gui_screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.aperture.primeval_essence.PlayerStats;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static net.hendersondaniel.gu_daoism.util.FormattingMethods.rawStageToRealm;
import static net.hendersondaniel.gu_daoism.util.FormattingMethods.stageIntToStageName;

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

    private void handleExampleButton(Button button) {
        // logic here
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
                Component.literal("Progress: " + playerStats.getCultivationProgress() + "/" + Math.ceil(500*Math.pow(1.5,playerStats.getRawStage()))),
                leftPos + 20, topPos + 60,
                0x404040);


        renderAperture(poseStack);

        // Call super last for widgets
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    private void renderAperture(PoseStack poseStack) {
        if (playerStats.getTalent() <= 0) {
            return;
        }

        float wallRed, wallGreen, wallBlue;
        float seaRed, seaGreen, seaBlue;

        // aperture wall color
        switch (playerStats.getRawStage() % 4) {
            case 0: // wall should be a yellow
                wallRed = 1.0F;
                wallGreen = 216 / 255.0F;
                wallBlue = 0.0F;
                break;
            case 1: // wall should be a blue
                wallRed = 0.0F;
                wallGreen = 105 / 255.0F;
                wallBlue = 1.0F;
                break;
            case 2: // wall should be a gray
                wallRed = 128 / 255.0F;
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
        switch (playerStats.getRawStage() / 4) {
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

