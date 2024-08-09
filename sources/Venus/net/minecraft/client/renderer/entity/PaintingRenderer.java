/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.PaintingSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class PaintingRenderer
extends EntityRenderer<PaintingEntity> {
    public PaintingRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public void render(PaintingEntity paintingEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        matrixStack.push();
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f - f));
        PaintingType paintingType = paintingEntity.art;
        float f3 = 0.0625f;
        matrixStack.scale(0.0625f, 0.0625f, 0.0625f);
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntitySolid(this.getEntityTexture(paintingEntity)));
        PaintingSpriteUploader paintingSpriteUploader = Minecraft.getInstance().getPaintingSpriteUploader();
        this.func_229122_a_(matrixStack, iVertexBuilder, paintingEntity, paintingType.getWidth(), paintingType.getHeight(), paintingSpriteUploader.getSpriteForPainting(paintingType), paintingSpriteUploader.getBackSprite());
        matrixStack.pop();
        super.render(paintingEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(PaintingEntity paintingEntity) {
        return Minecraft.getInstance().getPaintingSpriteUploader().getBackSprite().getAtlasTexture().getTextureLocation();
    }

    private void func_229122_a_(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, PaintingEntity paintingEntity, int n, int n2, TextureAtlasSprite textureAtlasSprite, TextureAtlasSprite textureAtlasSprite2) {
        MatrixStack.Entry entry = matrixStack.getLast();
        Matrix4f matrix4f = entry.getMatrix();
        Matrix3f matrix3f = entry.getNormal();
        float f = (float)(-n) / 2.0f;
        float f2 = (float)(-n2) / 2.0f;
        float f3 = 0.5f;
        float f4 = textureAtlasSprite2.getMinU();
        float f5 = textureAtlasSprite2.getMaxU();
        float f6 = textureAtlasSprite2.getMinV();
        float f7 = textureAtlasSprite2.getMaxV();
        float f8 = textureAtlasSprite2.getMinU();
        float f9 = textureAtlasSprite2.getMaxU();
        float f10 = textureAtlasSprite2.getMinV();
        float f11 = textureAtlasSprite2.getInterpolatedV(1.0);
        float f12 = textureAtlasSprite2.getMinU();
        float f13 = textureAtlasSprite2.getInterpolatedU(1.0);
        float f14 = textureAtlasSprite2.getMinV();
        float f15 = textureAtlasSprite2.getMaxV();
        int n3 = n / 16;
        int n4 = n2 / 16;
        double d = 16.0 / (double)n3;
        double d2 = 16.0 / (double)n4;
        for (int i = 0; i < n3; ++i) {
            for (int j = 0; j < n4; ++j) {
                float f16 = f + (float)((i + 1) * 16);
                float f17 = f + (float)(i * 16);
                float f18 = f2 + (float)((j + 1) * 16);
                float f19 = f2 + (float)(j * 16);
                int n5 = MathHelper.floor(paintingEntity.getPosX());
                int n6 = MathHelper.floor(paintingEntity.getPosY() + (double)((f18 + f19) / 2.0f / 16.0f));
                int n7 = MathHelper.floor(paintingEntity.getPosZ());
                Direction direction = paintingEntity.getHorizontalFacing();
                if (direction == Direction.NORTH) {
                    n5 = MathHelper.floor(paintingEntity.getPosX() + (double)((f16 + f17) / 2.0f / 16.0f));
                }
                if (direction == Direction.WEST) {
                    n7 = MathHelper.floor(paintingEntity.getPosZ() - (double)((f16 + f17) / 2.0f / 16.0f));
                }
                if (direction == Direction.SOUTH) {
                    n5 = MathHelper.floor(paintingEntity.getPosX() - (double)((f16 + f17) / 2.0f / 16.0f));
                }
                if (direction == Direction.EAST) {
                    n7 = MathHelper.floor(paintingEntity.getPosZ() + (double)((f16 + f17) / 2.0f / 16.0f));
                }
                int n8 = WorldRenderer.getCombinedLight(paintingEntity.world, new BlockPos(n5, n6, n7));
                float f20 = textureAtlasSprite.getInterpolatedU(d * (double)(n3 - i));
                float f21 = textureAtlasSprite.getInterpolatedU(d * (double)(n3 - (i + 1)));
                float f22 = textureAtlasSprite.getInterpolatedV(d2 * (double)(n4 - j));
                float f23 = textureAtlasSprite.getInterpolatedV(d2 * (double)(n4 - (j + 1)));
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f19, f21, f22, -0.5f, 0, 0, -1, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f19, f20, f22, -0.5f, 0, 0, -1, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f18, f20, f23, -0.5f, 0, 0, -1, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f18, f21, f23, -0.5f, 0, 0, -1, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f18, f4, f6, 0.5f, 0, 0, 1, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f18, f5, f6, 0.5f, 0, 0, 1, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f19, f5, f7, 0.5f, 0, 0, 1, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f19, f4, f7, 0.5f, 0, 0, 1, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f18, f8, f10, -0.5f, 0, 1, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f18, f9, f10, -0.5f, 0, 1, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f18, f9, f11, 0.5f, 0, 1, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f18, f8, f11, 0.5f, 0, 1, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f19, f8, f10, 0.5f, 0, -1, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f19, f9, f10, 0.5f, 0, -1, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f19, f9, f11, -0.5f, 0, -1, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f19, f8, f11, -0.5f, 0, -1, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f18, f13, f14, 0.5f, -1, 0, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f19, f13, f15, 0.5f, -1, 0, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f19, f12, f15, -0.5f, -1, 0, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f16, f18, f12, f14, -0.5f, -1, 0, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f18, f13, f14, -0.5f, 1, 0, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f19, f13, f15, -0.5f, 1, 0, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f19, f12, f15, 0.5f, 1, 0, 0, n8);
                this.func_229121_a_(matrix4f, matrix3f, iVertexBuilder, f17, f18, f12, f14, 0.5f, 1, 0, 0, n8);
            }
        }
    }

    private void func_229121_a_(Matrix4f matrix4f, Matrix3f matrix3f, IVertexBuilder iVertexBuilder, float f, float f2, float f3, float f4, float f5, int n, int n2, int n3, int n4) {
        iVertexBuilder.pos(matrix4f, f, f2, f5).color(255, 255, 255, 255).tex(f3, f4).overlay(OverlayTexture.NO_OVERLAY).lightmap(n4).normal(matrix3f, n, n2, n3).endVertex();
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((PaintingEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((PaintingEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

