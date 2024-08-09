/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class DragonFireballRenderer
extends EntityRenderer<DragonFireballEntity> {
    private static final ResourceLocation DRAGON_FIREBALL_TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");
    private static final RenderType field_229044_e_ = RenderType.getEntityCutoutNoCull(DRAGON_FIREBALL_TEXTURE);

    public DragonFireballRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    protected int getBlockLight(DragonFireballEntity dragonFireballEntity, BlockPos blockPos) {
        return 0;
    }

    @Override
    public void render(DragonFireballEntity dragonFireballEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        matrixStack.scale(2.0f, 2.0f, 2.0f);
        matrixStack.rotate(this.renderManager.getCameraOrientation());
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
        MatrixStack.Entry entry = matrixStack.getLast();
        Matrix4f matrix4f = entry.getMatrix();
        Matrix3f matrix3f = entry.getNormal();
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(field_229044_e_);
        DragonFireballRenderer.func_229045_a_(iVertexBuilder, matrix4f, matrix3f, n, 0.0f, 0, 0, 1);
        DragonFireballRenderer.func_229045_a_(iVertexBuilder, matrix4f, matrix3f, n, 1.0f, 0, 1, 1);
        DragonFireballRenderer.func_229045_a_(iVertexBuilder, matrix4f, matrix3f, n, 1.0f, 1, 1, 0);
        DragonFireballRenderer.func_229045_a_(iVertexBuilder, matrix4f, matrix3f, n, 0.0f, 1, 0, 0);
        matrixStack.pop();
        super.render(dragonFireballEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    private static void func_229045_a_(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, Matrix3f matrix3f, int n, float f, int n2, int n3, int n4) {
        iVertexBuilder.pos(matrix4f, f - 0.5f, (float)n2 - 0.25f, 0.0f).color(255, 255, 255, 255).tex(n3, n4).overlay(OverlayTexture.NO_OVERLAY).lightmap(n).normal(matrix3f, 0.0f, 1.0f, 0.0f).endVertex();
    }

    @Override
    public ResourceLocation getEntityTexture(DragonFireballEntity dragonFireballEntity) {
        return DRAGON_FIREBALL_TEXTURE;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((DragonFireballEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((DragonFireballEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    protected int getBlockLight(Entity entity2, BlockPos blockPos) {
        return this.getBlockLight((DragonFireballEntity)entity2, blockPos);
    }
}

