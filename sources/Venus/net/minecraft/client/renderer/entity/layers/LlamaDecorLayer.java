/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.LlamaModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;

public class LlamaDecorLayer
extends LayerRenderer<LlamaEntity, LlamaModel<LlamaEntity>> {
    private static final ResourceLocation[] LLAMA_DECOR_TEXTURES = new ResourceLocation[]{new ResourceLocation("textures/entity/llama/decor/white.png"), new ResourceLocation("textures/entity/llama/decor/orange.png"), new ResourceLocation("textures/entity/llama/decor/magenta.png"), new ResourceLocation("textures/entity/llama/decor/light_blue.png"), new ResourceLocation("textures/entity/llama/decor/yellow.png"), new ResourceLocation("textures/entity/llama/decor/lime.png"), new ResourceLocation("textures/entity/llama/decor/pink.png"), new ResourceLocation("textures/entity/llama/decor/gray.png"), new ResourceLocation("textures/entity/llama/decor/light_gray.png"), new ResourceLocation("textures/entity/llama/decor/cyan.png"), new ResourceLocation("textures/entity/llama/decor/purple.png"), new ResourceLocation("textures/entity/llama/decor/blue.png"), new ResourceLocation("textures/entity/llama/decor/brown.png"), new ResourceLocation("textures/entity/llama/decor/green.png"), new ResourceLocation("textures/entity/llama/decor/red.png"), new ResourceLocation("textures/entity/llama/decor/black.png")};
    private static final ResourceLocation TRADER_LLAMA = new ResourceLocation("textures/entity/llama/decor/trader_llama.png");
    private final LlamaModel<LlamaEntity> model = new LlamaModel(0.5f);

    public LlamaDecorLayer(IEntityRenderer<LlamaEntity, LlamaModel<LlamaEntity>> iEntityRenderer) {
        super(iEntityRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, LlamaEntity llamaEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        ResourceLocation resourceLocation;
        DyeColor dyeColor = llamaEntity.getColor();
        if (dyeColor != null) {
            resourceLocation = LLAMA_DECOR_TEXTURES[dyeColor.getId()];
        } else {
            if (!llamaEntity.isTraderLlama()) {
                return;
            }
            resourceLocation = TRADER_LLAMA;
        }
        ((LlamaModel)this.getEntityModel()).copyModelAttributesTo(this.model);
        this.model.setRotationAngles(llamaEntity, f, f2, f4, f5, f6);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityCutoutNoCull(resourceLocation));
        this.model.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (LlamaEntity)entity2, f, f2, f3, f4, f5, f6);
    }
}

