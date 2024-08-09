/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.SpellcastingIllagerEntity;
import net.minecraft.util.ResourceLocation;

public class EvokerRenderer<T extends SpellcastingIllagerEntity>
extends IllagerRenderer<T> {
    private static final ResourceLocation EVOKER_ILLAGER = new ResourceLocation("textures/entity/illager/evoker.png");

    public EvokerRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager, new IllagerModel(0.0f, 0.0f, 64, 64), 0.5f);
        this.addLayer(new HeldItemLayer<T, IllagerModel<T>>(this, this){
            final EvokerRenderer this$0;
            {
                this.this$0 = evokerRenderer;
                super(iEntityRenderer);
            }

            @Override
            public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
                if (((SpellcastingIllagerEntity)t).isSpellcasting()) {
                    super.render(matrixStack, iRenderTypeBuffer, n, t, f, f2, f3, f4, f5, f6);
                }
            }

            @Override
            public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, LivingEntity livingEntity, float f, float f2, float f3, float f4, float f5, float f6) {
                this.render(matrixStack, iRenderTypeBuffer, n, (Object)((SpellcastingIllagerEntity)livingEntity), f, f2, f3, f4, f5, f6);
            }

            @Override
            public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
                this.render(matrixStack, iRenderTypeBuffer, n, (Object)((SpellcastingIllagerEntity)entity2), f, f2, f3, f4, f5, f6);
            }
        });
    }

    @Override
    public ResourceLocation getEntityTexture(T t) {
        return EVOKER_ILLAGER;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((T)((SpellcastingIllagerEntity)entity2));
    }
}

