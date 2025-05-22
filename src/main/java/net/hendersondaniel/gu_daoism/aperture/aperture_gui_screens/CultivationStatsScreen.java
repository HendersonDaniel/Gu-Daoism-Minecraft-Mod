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
    private static final Component TITLE = Component.literal("Aperture Information");

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
        //drawCenteredString(poseStack, this.font, TITLE, this.width / 2, topPos + 10, 0xFFFFFF);

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

        // Call super last for widgets
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }
}

