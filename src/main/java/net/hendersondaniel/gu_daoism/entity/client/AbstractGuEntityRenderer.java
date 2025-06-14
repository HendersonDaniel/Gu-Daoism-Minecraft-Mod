package net.hendersondaniel.gu_daoism.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.hendersondaniel.gu_daoism.GuDaoism;
import net.hendersondaniel.gu_daoism.entity.custom.AbstractGuEntity;
import net.hendersondaniel.gu_daoism.entity.custom.JadeSkinGuEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public abstract class AbstractGuEntityRenderer<T extends AbstractGuEntity> extends GeoEntityRenderer<T> {

    private final String entityName;
    public AbstractGuEntityRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> model, String entityName) {
        super(renderManager, model);
        this.entityName = entityName;
    }

    @Override
    public T getAnimatable() {
        return super.getAnimatable();
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T animatable) {
        return ResourceLocation.fromNamespaceAndPath(GuDaoism.MOD_ID, "textures/entity/" + entityName+ ".png");
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource bufferSource, int packedLight) {

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

}
