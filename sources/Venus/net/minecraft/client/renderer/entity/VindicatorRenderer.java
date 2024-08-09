/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.util.ResourceLocation;

public class VindicatorRenderer
extends IllagerRenderer<VindicatorEntity> {
    private static final ResourceLocation VINDICATOR_TEXTURE = new ResourceLocation("textures/entity/illager/vindicator.png");

    public VindicatorRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new IllagerModel(0.0f, 0.0f, 64, 64), 0.5f);
        this.addLayer(new HeldItemLayer<VindicatorEntity, IllagerModel<VindicatorEntity>>(this, (IEntityRenderer)this){
            final VindicatorRenderer this$0;
            {
                this.this$0 = vindicatorRenderer;
                super(iEntityRenderer);
            }

            @Override
            public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, VindicatorEntity vindicatorEntity, float f, float f2, float f3, float f4, float f5, float f6) {
                if (vindicatorEntity.isAggressive()) {
                    super.render(matrixStack, iRenderTypeBuffer, n, vindicatorEntity, f, f2, f3, f4, f5, f6);
                }
            }

            @Override
            public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, LivingEntity livingEntity, float f, float f2, float f3, float f4, float f5, float f6) {
                this.render(matrixStack, iRenderTypeBuffer, n, (VindicatorEntity)livingEntity, f, f2, f3, f4, f5, f6);
            }

            @Override
            public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
                this.render(matrixStack, iRenderTypeBuffer, n, (VindicatorEntity)entity2, f, f2, f3, f4, f5, f6);
            }
        });
    }

    @Override
    public ResourceLocation getEntityTexture(VindicatorEntity vindicatorEntity) {
        return VINDICATOR_TEXTURE;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((VindicatorEntity)entity2);
    }
}

