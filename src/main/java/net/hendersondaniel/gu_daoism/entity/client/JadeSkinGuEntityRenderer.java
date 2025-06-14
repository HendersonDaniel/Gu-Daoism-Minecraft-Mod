package net.hendersondaniel.gu_daoism.entity.client;

import net.hendersondaniel.gu_daoism.entity.custom.JadeSkinGuEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class JadeSkinGuEntityRenderer extends AbstractGuEntityRenderer<JadeSkinGuEntity> {
    public JadeSkinGuEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new JadeSkinGuEntityModel(),"jade_skin_gu_entity");
    }

}
