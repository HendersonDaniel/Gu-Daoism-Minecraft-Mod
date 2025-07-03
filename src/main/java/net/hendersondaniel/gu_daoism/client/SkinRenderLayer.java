package net.hendersondaniel.gu_daoism.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.hendersondaniel.gu_daoism.effect.ModEffects;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class SkinRenderLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public SkinRenderLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        if (!player.hasEffect(ModEffects.JADE_SKIN_GU_EFFECT.get())) return;

        RenderType renderType = RenderType.entityTranslucent(player.getSkinTextureLocation());

        this.getParentModel().copyPropertiesTo(this.getParentModel());
        this.getParentModel().renderToBuffer(
                poseStack,
                bufferSource.getBuffer(renderType),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                0.4f, 1f, 0.4f, 1f
        );


    }
}

