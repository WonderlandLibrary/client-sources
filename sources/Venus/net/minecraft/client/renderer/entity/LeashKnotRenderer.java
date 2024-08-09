/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.LeashKnotModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.util.ResourceLocation;

public class LeashKnotRenderer
extends EntityRenderer<LeashKnotEntity> {
    private static final ResourceLocation LEASH_KNOT_TEXTURES = new ResourceLocation("textures/entity/lead_knot.png");
    private final LeashKnotModel<LeashKnotEntity> leashKnotModel = new LeashKnotModel();

    public LeashKnotRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public void render(LeashKnotEntity leashKnotEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        this.leashKnotModel.setRotationAngles(leashKnotEntity, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(this.leashKnotModel.getRenderType(LEASH_KNOT_TEXTURES));
        this.leashKnotModel.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(leashKnotEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(LeashKnotEntity leashKnotEntity) {
        return LEASH_KNOT_TEXTURES;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((LeashKnotEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((LeashKnotEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

