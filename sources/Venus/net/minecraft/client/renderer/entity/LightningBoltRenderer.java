/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Random;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;

public class LightningBoltRenderer
extends EntityRenderer<LightningBoltEntity> {
    public LightningBoltRenderer(EntityRendererManager entityRendererManager) {
        super(entityRendererManager);
    }

    @Override
    public void render(LightningBoltEntity lightningBoltEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        float[] fArray = new float[8];
        float[] fArray2 = new float[8];
        float f3 = 0.0f;
        float f4 = 0.0f;
        Random random2 = new Random(lightningBoltEntity.boltVertex);
        for (int i = 7; i >= 0; --i) {
            fArray[i] = f3;
            fArray2[i] = f4;
            f3 += (float)(random2.nextInt(11) - 5);
            f4 += (float)(random2.nextInt(11) - 5);
        }
        IVertexBuilder iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getLightning());
        Matrix4f matrix4f = matrixStack.getLast().getMatrix();
        for (int i = 0; i < 4; ++i) {
            Random random3 = new Random(lightningBoltEntity.boltVertex);
            for (int j = 0; j < 3; ++j) {
                int n2 = 7;
                int n3 = 0;
                if (j > 0) {
                    n2 = 7 - j;
                }
                if (j > 0) {
                    n3 = n2 - 2;
                }
                float f5 = fArray[n2] - f3;
                float f6 = fArray2[n2] - f4;
                for (int k = n2; k >= n3; --k) {
                    float f7 = f5;
                    float f8 = f6;
                    if (j == 0) {
                        f5 += (float)(random3.nextInt(11) - 5);
                        f6 += (float)(random3.nextInt(11) - 5);
                    } else {
                        f5 += (float)(random3.nextInt(31) - 15);
                        f6 += (float)(random3.nextInt(31) - 15);
                    }
                    float f9 = 0.5f;
                    float f10 = 0.45f;
                    float f11 = 0.45f;
                    float f12 = 0.5f;
                    float f13 = 0.1f + (float)i * 0.2f;
                    if (j == 0) {
                        f13 = (float)((double)f13 * ((double)k * 0.1 + 1.0));
                    }
                    float f14 = 0.1f + (float)i * 0.2f;
                    if (j == 0) {
                        f14 *= (float)(k - 1) * 0.1f + 1.0f;
                    }
                    LightningBoltRenderer.func_229116_a_(matrix4f, iVertexBuilder, f5, f6, k, f7, f8, 0.45f, 0.45f, 0.5f, f13, f14, false, false, true, false);
                    LightningBoltRenderer.func_229116_a_(matrix4f, iVertexBuilder, f5, f6, k, f7, f8, 0.45f, 0.45f, 0.5f, f13, f14, true, false, true, true);
                    LightningBoltRenderer.func_229116_a_(matrix4f, iVertexBuilder, f5, f6, k, f7, f8, 0.45f, 0.45f, 0.5f, f13, f14, true, true, false, true);
                    LightningBoltRenderer.func_229116_a_(matrix4f, iVertexBuilder, f5, f6, k, f7, f8, 0.45f, 0.45f, 0.5f, f13, f14, false, true, false, false);
                }
            }
        }
    }

    private static void func_229116_a_(Matrix4f matrix4f, IVertexBuilder iVertexBuilder, float f, float f2, int n, float f3, float f4, float f5, float f6, float f7, float f8, float f9, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        iVertexBuilder.pos(matrix4f, f + (bl ? f9 : -f9), n * 16, f2 + (bl2 ? f9 : -f9)).color(f5, f6, f7, 0.3f).endVertex();
        iVertexBuilder.pos(matrix4f, f3 + (bl ? f8 : -f8), (n + 1) * 16, f4 + (bl2 ? f8 : -f8)).color(f5, f6, f7, 0.3f).endVertex();
        iVertexBuilder.pos(matrix4f, f3 + (bl3 ? f8 : -f8), (n + 1) * 16, f4 + (bl4 ? f8 : -f8)).color(f5, f6, f7, 0.3f).endVertex();
        iVertexBuilder.pos(matrix4f, f + (bl3 ? f9 : -f9), n * 16, f2 + (bl4 ? f9 : -f9)).color(f5, f6, f7, 0.3f).endVertex();
    }

    @Override
    public ResourceLocation getEntityTexture(LightningBoltEntity lightningBoltEntity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((LightningBoltEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((LightningBoltEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }
}

