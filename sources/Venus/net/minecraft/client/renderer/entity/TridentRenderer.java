/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class TridentRenderer
extends EntityRenderer<TridentEntity> {
    public static final ResourceLocation TRIDENT = new ResourceLocation("textures/entity/trident.png");
    private final TridentModel tridentModel = new TridentModel();

    public TridentRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public void render(TridentEntity tridentEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        matrixStack.rotate(Vector3f.YP.rotationDegrees(MathHelper.lerp(f2, tridentEntity.prevRotationYaw, tridentEntity.rotationYaw) - 90.0f));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(MathHelper.lerp(f2, tridentEntity.prevRotationPitch, tridentEntity.rotationPitch) + 90.0f));
        IVertexBuilder iVertexBuilder = ItemRenderer.getEntityGlintVertexBuilder(iRenderTypeBuffer, this.tridentModel.getRenderType(this.getEntityTexture(tridentEntity)), false, tridentEntity.func_226572_w_());
        this.tridentModel.render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
        super.render(tridentEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(TridentEntity tridentEntity) {
        return TRIDENT;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((TridentEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((TridentEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

