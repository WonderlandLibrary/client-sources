/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.utils.render.ColorUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraftforge.client.extensions.IForgeVertexBuilder;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntities;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderEnv;
import net.optifine.render.VertexPosition;
import net.optifine.shaders.Shaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface IVertexBuilder
extends IForgeVertexBuilder {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ThreadLocal<RenderEnv> RENDER_ENV = ThreadLocal.withInitial(IVertexBuilder::lambda$static$0);
    public static final boolean FORGE = Reflector.ForgeHooksClient.exists();

    default public RenderEnv getRenderEnv(BlockState blockState, BlockPos blockPos) {
        RenderEnv renderEnv = RENDER_ENV.get();
        renderEnv.reset(blockState, blockPos);
        return renderEnv;
    }

    public IVertexBuilder pos(double var1, double var3, double var5);

    public IVertexBuilder color(int var1, int var2, int var3, int var4);

    public IVertexBuilder tex(float var1, float var2);

    public IVertexBuilder overlay(int var1, int var2);

    public IVertexBuilder lightmap(int var1, int var2);

    public IVertexBuilder normal(float var1, float var2, float var3);

    public void endVertex();

    default public void addVertex(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n, int n2, float f10, float f11, float f12) {
        this.pos(f, f2, f3);
        this.color(f4, f5, f6, f7);
        this.tex(f8, f9);
        this.overlay(n);
        this.lightmap(n2);
        this.normal(f10, f11, f12);
        this.endVertex();
    }

    default public IVertexBuilder color(float f, float f2, float f3, float f4) {
        return this.color((int)(f * 255.0f), (int)(f2 * 255.0f), (int)(f3 * 255.0f), (int)(f4 * 255.0f));
    }

    default public IVertexBuilder color(int n) {
        float[] fArray = ColorUtils.rgba(n);
        return this.color((int)(fArray[0] * 255.0f), (int)(fArray[1] * 255.0f), (int)(fArray[2] * 255.0f), (int)(fArray[3] * 255.0f));
    }

    default public IVertexBuilder lightmap(int n) {
        return this.lightmap(n & 0xFFFF, n >> 16 & 0xFFFF);
    }

    default public IVertexBuilder overlay(int n) {
        return this.overlay(n & 0xFFFF, n >> 16 & 0xFFFF);
    }

    default public void addQuad(MatrixStack.Entry entry, BakedQuad bakedQuad, float f, float f2, float f3, int n, int n2) {
        this.addQuad(entry, bakedQuad, this.getTempFloat4(1.0f, 1.0f, 1.0f, 1.0f), f, f2, f3, this.getTempInt4(n, n, n, n), n2, false);
    }

    default public void addVertexData(MatrixStack.Entry entry, BakedQuad bakedQuad, float[] fArray, float f, float f2, float f3, float f4, int[] nArray, int n, boolean bl) {
        this.addQuad(entry, bakedQuad, fArray, f, f2, f3, f4, nArray, n, bl);
    }

    default public void addQuad(MatrixStack.Entry entry, BakedQuad bakedQuad, float[] fArray, float f, float f2, float f3, int[] nArray, int n, boolean bl) {
        this.addQuad(entry, bakedQuad, fArray, f, f2, f3, 1.0f, nArray, n, bl);
    }

    default public void addQuad(MatrixStack.Entry entry, BakedQuad bakedQuad, float[] fArray, float f, float f2, float f3, float f4, int[] nArray, int n, boolean bl) {
        IRandomEntity iRandomEntity;
        boolean bl2;
        int[] nArray2 = this.isMultiTexture() ? bakedQuad.getVertexDataSingle() : bakedQuad.getVertexData();
        this.putSprite(bakedQuad.getSprite());
        boolean bl3 = BlockModelRenderer.isSeparateAoLightValue();
        Vector3i vector3i = bakedQuad.getFace().getDirectionVec();
        float f5 = vector3i.getX();
        float f6 = vector3i.getY();
        float f7 = vector3i.getZ();
        Matrix4f matrix4f = entry.getMatrix();
        Matrix3f matrix3f = entry.getNormal();
        float f8 = matrix3f.getTransformX(f5, f6, f7);
        float f9 = matrix3f.getTransformY(f5, f6, f7);
        float f10 = matrix3f.getTransformZ(f5, f6, f7);
        int n2 = 8;
        int n3 = DefaultVertexFormats.BLOCK.getIntegerSize();
        int n4 = nArray2.length / n3;
        boolean bl4 = bl2 = Config.isShaders() && Shaders.useVelocityAttrib && Config.isMinecraftThread();
        if (bl2 && (iRandomEntity = RandomEntities.getRandomEntityRendered()) != null) {
            VertexPosition[] vertexPositionArray = bakedQuad.getVertexPositions(iRandomEntity.getId());
            this.setQuadVertexPositions(vertexPositionArray);
        }
        for (int i = 0; i < n4; ++i) {
            Vector3f vector3f;
            float f11;
            float f12;
            float f13;
            float f14;
            float f15;
            float f16;
            float f17;
            int n5;
            float f18;
            int n6 = i * n3;
            float f19 = Float.intBitsToFloat(nArray2[n6 + 0]);
            float f20 = Float.intBitsToFloat(nArray2[n6 + 1]);
            float f21 = Float.intBitsToFloat(nArray2[n6 + 2]);
            float f22 = 1.0f;
            float f23 = f18 = bl3 ? 1.0f : fArray[i];
            if (bl) {
                n5 = nArray2[n6 + 3];
                f17 = (float)(n5 & 0xFF) / 255.0f;
                f16 = (float)(n5 >> 8 & 0xFF) / 255.0f;
                f15 = (float)(n5 >> 16 & 0xFF) / 255.0f;
                f14 = f17 * f18 * f;
                f13 = f16 * f18 * f2;
                f12 = f15 * f18 * f3;
                if (FORGE) {
                    f11 = (float)(n5 >> 24 & 0xFF) / 255.0f;
                    f22 = f11 * f4;
                }
            } else {
                f14 = f18 * f;
                f13 = f18 * f2;
                f12 = f18 * f3;
                if (FORGE) {
                    f22 = f4;
                }
            }
            n5 = nArray[i];
            if (FORGE) {
                n5 = this.applyBakedLighting(nArray[i], nArray2, n6);
            }
            f17 = Float.intBitsToFloat(nArray2[n6 + 4]);
            f16 = Float.intBitsToFloat(nArray2[n6 + 5]);
            f15 = matrix4f.getTransformX(f19, f20, f21, 1.0f);
            f11 = matrix4f.getTransformY(f19, f20, f21, 1.0f);
            float f24 = matrix4f.getTransformZ(f19, f20, f21, 1.0f);
            if (FORGE && (vector3f = this.applyBakedNormals(nArray2, n6, entry.getNormal())) != null) {
                f8 = vector3f.getX();
                f9 = vector3f.getY();
                f10 = vector3f.getZ();
            }
            if (bl3) {
                f22 = fArray[i];
            }
            this.addVertex(f15, f11, f24, f14, f13, f12, f22, f17, f16, n, n5, f8, f9, f10);
        }
    }

    default public IVertexBuilder pos(Matrix4f matrix4f, float f, float f2, float f3) {
        float f4 = matrix4f.getTransformX(f, f2, f3, 1.0f);
        float f5 = matrix4f.getTransformY(f, f2, f3, 1.0f);
        float f6 = matrix4f.getTransformZ(f, f2, f3, 1.0f);
        return this.pos(f4, f5, f6);
    }

    default public IVertexBuilder normal(Matrix3f matrix3f, float f, float f2, float f3) {
        float f4 = matrix3f.getTransformX(f, f2, f3);
        float f5 = matrix3f.getTransformY(f, f2, f3);
        float f6 = matrix3f.getTransformZ(f, f2, f3);
        return this.normal(f4, f5, f6);
    }

    default public void putSprite(TextureAtlasSprite textureAtlasSprite) {
    }

    default public void setSprite(TextureAtlasSprite textureAtlasSprite) {
    }

    default public boolean isMultiTexture() {
        return true;
    }

    default public void setRenderType(RenderType renderType) {
    }

    default public RenderType getRenderType() {
        return null;
    }

    default public void setRenderBlocks(boolean bl) {
    }

    default public Vector3f getTempVec3f(Vector3f vector3f) {
        return vector3f.copy();
    }

    default public Vector3f getTempVec3f(float f, float f2, float f3) {
        return new Vector3f(f, f2, f3);
    }

    default public float[] getTempFloat4(float f, float f2, float f3, float f4) {
        return new float[]{f, f2, f3, f4};
    }

    default public int[] getTempInt4(int n, int n2, int n3, int n4) {
        return new int[]{n, n2, n3, n4};
    }

    default public IRenderTypeBuffer.Impl getRenderTypeBuffer() {
        return null;
    }

    default public void setQuadVertexPositions(VertexPosition[] vertexPositionArray) {
    }

    default public void setMidBlock(float f, float f2, float f3) {
    }

    default public IVertexBuilder getSecondaryBuilder() {
        return null;
    }

    default public int applyBakedLighting(int n, int[] nArray, int n2) {
        int n3 = IVertexBuilder.getLightOffset(0);
        int n4 = LightTexture.getLightBlock(nArray[n2 + n3]);
        int n5 = LightTexture.getLightSky(nArray[n2 + n3]);
        if (n4 == 0 && n5 == 0) {
            return n;
        }
        int n6 = LightTexture.getLightBlock(n);
        int n7 = LightTexture.getLightSky(n);
        n6 = Math.max(n6, n4);
        n7 = Math.max(n7, n5);
        return LightTexture.packLight(n6, n7);
    }

    public static int getLightOffset(int n) {
        return n * 8 + 6;
    }

    default public Vector3f applyBakedNormals(int[] nArray, int n, Matrix3f matrix3f) {
        int n2 = 7;
        int n3 = nArray[n + n2];
        byte by = (byte)(n3 >> 0 & 0xFF);
        byte by2 = (byte)(n3 >> 8 & 0xFF);
        byte by3 = (byte)(n3 >> 16 & 0xFF);
        if (by == 0 && by2 == 0 && by3 == 0) {
            return null;
        }
        Vector3f vector3f = this.getTempVec3f((float)by / 127.0f, (float)by2 / 127.0f, (float)by3 / 127.0f);
        vector3f.transform(matrix3f);
        return vector3f;
    }

    private static RenderEnv lambda$static$0() {
        return new RenderEnv(Blocks.AIR.getDefaultState(), new BlockPos(0, 0, 0));
    }
}

