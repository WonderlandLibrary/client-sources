/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.Config;
import net.optifine.render.VertexPosition;
import net.optifine.shaders.BlockAliases;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL20;

public class SVertexBuilder {
    int vertexSize;
    int offsetNormal;
    int offsetUV;
    int offsetUVCenter;
    boolean hasNormal;
    boolean hasTangent;
    boolean hasUV;
    boolean hasUVCenter;
    long[] entityData = new long[10];
    int entityDataIndex = 0;

    public SVertexBuilder() {
        this.entityData[this.entityDataIndex] = 0L;
    }

    public static void initVertexBuilder(BufferBuilder bufferBuilder) {
        bufferBuilder.sVertexBuilder = new SVertexBuilder();
    }

    public void pushEntity(long l) {
        ++this.entityDataIndex;
        this.entityData[this.entityDataIndex] = l;
    }

    public void popEntity() {
        this.entityData[this.entityDataIndex] = 0L;
        --this.entityDataIndex;
    }

    public static void pushEntity(BlockState blockState, IVertexBuilder iVertexBuilder) {
        if (iVertexBuilder instanceof BufferBuilder) {
            BufferBuilder bufferBuilder = (BufferBuilder)iVertexBuilder;
            int n = BlockAliases.getAliasBlockId(blockState);
            int n2 = BlockAliases.getAliasMetadata(blockState);
            int n3 = BlockAliases.getRenderType(blockState);
            int n4 = ((n3 & 0xFFFF) << 16) + (n & 0xFFFF);
            int n5 = n2 & 0xFFFF;
            bufferBuilder.sVertexBuilder.pushEntity(((long)n5 << 32) + (long)n4);
        }
    }

    public static void popEntity(IVertexBuilder iVertexBuilder) {
        if (iVertexBuilder instanceof BufferBuilder) {
            BufferBuilder bufferBuilder = (BufferBuilder)iVertexBuilder;
            bufferBuilder.sVertexBuilder.popEntity();
        }
    }

    public static boolean popEntity(boolean bl, BufferBuilder bufferBuilder) {
        bufferBuilder.sVertexBuilder.popEntity();
        return bl;
    }

    public static void endSetVertexFormat(BufferBuilder bufferBuilder) {
        SVertexBuilder sVertexBuilder = bufferBuilder.sVertexBuilder;
        VertexFormat vertexFormat = bufferBuilder.getVertexFormat();
        sVertexBuilder.vertexSize = vertexFormat.getSize() / 4;
        sVertexBuilder.hasTangent = sVertexBuilder.hasNormal = vertexFormat.hasNormal();
        sVertexBuilder.hasUV = vertexFormat.hasUV(1);
        sVertexBuilder.offsetNormal = sVertexBuilder.hasNormal ? vertexFormat.getNormalOffset() / 4 : 0;
        sVertexBuilder.offsetUV = sVertexBuilder.hasUV ? vertexFormat.getUvOffsetById(0) / 4 : 0;
        sVertexBuilder.offsetUVCenter = 8;
    }

    public static void beginAddVertex(BufferBuilder bufferBuilder) {
        if (bufferBuilder.getVertexCount() == 0) {
            SVertexBuilder.endSetVertexFormat(bufferBuilder);
        }
    }

    public static void endAddVertex(BufferBuilder bufferBuilder) {
        SVertexBuilder sVertexBuilder = bufferBuilder.sVertexBuilder;
        if (sVertexBuilder.vertexSize == 18) {
            if (bufferBuilder.getDrawMode() == 7 && bufferBuilder.getVertexCount() % 4 == 0) {
                sVertexBuilder.calcNormal(bufferBuilder, bufferBuilder.getBufferIntSize() - 4 * sVertexBuilder.vertexSize);
            }
            long l = sVertexBuilder.entityData[sVertexBuilder.entityDataIndex];
            int n = bufferBuilder.getBufferIntSize() - 18 + 13;
            bufferBuilder.getIntBuffer().put(n += bufferBuilder.getIntStartPosition(), (int)l);
            bufferBuilder.getIntBuffer().put(n + 1, (int)(l >> 32));
        }
    }

    public static void beginAddVertexData(BufferBuilder bufferBuilder, int[] nArray) {
        if (bufferBuilder.getVertexCount() == 0) {
            SVertexBuilder.endSetVertexFormat(bufferBuilder);
        }
        SVertexBuilder sVertexBuilder = bufferBuilder.sVertexBuilder;
        if (sVertexBuilder.vertexSize == 18) {
            long l = sVertexBuilder.entityData[sVertexBuilder.entityDataIndex];
            int n = 13;
            while (n + 1 < nArray.length) {
                nArray[n] = (int)l;
                nArray[n + 1] = (int)(l >> 32);
                n += 18;
            }
        }
    }

    public static void beginAddVertexData(BufferBuilder bufferBuilder, ByteBuffer byteBuffer) {
        if (bufferBuilder.getVertexCount() == 0) {
            SVertexBuilder.endSetVertexFormat(bufferBuilder);
        }
        SVertexBuilder sVertexBuilder = bufferBuilder.sVertexBuilder;
        if (sVertexBuilder.vertexSize == 18) {
            long l = sVertexBuilder.entityData[sVertexBuilder.entityDataIndex];
            int n = byteBuffer.limit() / 4;
            int n2 = 13;
            while (n2 + 1 < n) {
                int n3 = (int)l;
                int n4 = (int)(l >> 32);
                byteBuffer.putInt(n2 * 4, n3);
                byteBuffer.putInt((n2 + 1) * 4, n4);
                n2 += 18;
            }
        }
    }

    public static void endAddVertexData(BufferBuilder bufferBuilder) {
        SVertexBuilder sVertexBuilder = bufferBuilder.sVertexBuilder;
        if (sVertexBuilder.vertexSize == 18 && bufferBuilder.getDrawMode() == 7 && bufferBuilder.getVertexCount() % 4 == 0) {
            sVertexBuilder.calcNormal(bufferBuilder, bufferBuilder.getBufferIntSize() - 4 * sVertexBuilder.vertexSize);
        }
    }

    public void calcNormal(BufferBuilder bufferBuilder, int n) {
        Object object;
        FloatBuffer floatBuffer = bufferBuilder.getFloatBuffer();
        IntBuffer intBuffer = bufferBuilder.getIntBuffer();
        float f = floatBuffer.get((n += bufferBuilder.getIntStartPosition()) + 0 * this.vertexSize);
        float f2 = floatBuffer.get(n + 0 * this.vertexSize + 1);
        float f3 = floatBuffer.get(n + 0 * this.vertexSize + 2);
        float f4 = floatBuffer.get(n + 0 * this.vertexSize + this.offsetUV);
        float f5 = floatBuffer.get(n + 0 * this.vertexSize + this.offsetUV + 1);
        float f6 = floatBuffer.get(n + 1 * this.vertexSize);
        float f7 = floatBuffer.get(n + 1 * this.vertexSize + 1);
        float f8 = floatBuffer.get(n + 1 * this.vertexSize + 2);
        float f9 = floatBuffer.get(n + 1 * this.vertexSize + this.offsetUV);
        float f10 = floatBuffer.get(n + 1 * this.vertexSize + this.offsetUV + 1);
        float f11 = floatBuffer.get(n + 2 * this.vertexSize);
        float f12 = floatBuffer.get(n + 2 * this.vertexSize + 1);
        float f13 = floatBuffer.get(n + 2 * this.vertexSize + 2);
        float f14 = floatBuffer.get(n + 2 * this.vertexSize + this.offsetUV);
        float f15 = floatBuffer.get(n + 2 * this.vertexSize + this.offsetUV + 1);
        float f16 = floatBuffer.get(n + 3 * this.vertexSize);
        float f17 = floatBuffer.get(n + 3 * this.vertexSize + 1);
        float f18 = floatBuffer.get(n + 3 * this.vertexSize + 2);
        float f19 = floatBuffer.get(n + 3 * this.vertexSize + this.offsetUV);
        float f20 = floatBuffer.get(n + 3 * this.vertexSize + this.offsetUV + 1);
        float f21 = f12 - f2;
        float f22 = f18 - f8;
        float f23 = f17 - f7;
        float f24 = f13 - f3;
        float f25 = f21 * f22 - f23 * f24;
        float f26 = f16 - f6;
        float f27 = f11 - f;
        float f28 = f24 * f26 - f22 * f27;
        float f29 = f27 * f23 - f26 * f21;
        float f30 = f25 * f25 + f28 * f28 + f29 * f29;
        float f31 = (double)f30 != 0.0 ? (float)(1.0 / Math.sqrt(f30)) : 1.0f;
        f25 *= f31;
        f28 *= f31;
        f29 *= f31;
        f27 = f6 - f;
        f21 = f7 - f2;
        f24 = f8 - f3;
        float f32 = f9 - f4;
        float f33 = f10 - f5;
        f26 = f11 - f;
        f23 = f12 - f2;
        f22 = f13 - f3;
        float f34 = f14 - f4;
        float f35 = f15 - f5;
        float f36 = f32 * f35 - f34 * f33;
        float f37 = f36 != 0.0f ? 1.0f / f36 : 1.0f;
        float f38 = (f35 * f27 - f33 * f26) * f37;
        float f39 = (f35 * f21 - f33 * f23) * f37;
        float f40 = (f35 * f24 - f33 * f22) * f37;
        float f41 = (f32 * f26 - f34 * f27) * f37;
        float f42 = (f32 * f23 - f34 * f21) * f37;
        float f43 = (f32 * f22 - f34 * f24) * f37;
        f30 = f38 * f38 + f39 * f39 + f40 * f40;
        f31 = (double)f30 != 0.0 ? (float)(1.0 / Math.sqrt(f30)) : 1.0f;
        f38 *= f31;
        f39 *= f31;
        f40 *= f31;
        f30 = f41 * f41 + f42 * f42 + f43 * f43;
        f31 = (double)f30 != 0.0 ? (float)(1.0 / Math.sqrt(f30)) : 1.0f;
        float f44 = f29 * f39 - f28 * f40;
        float f45 = f25 * f40 - f29 * f38;
        float f46 = f28 * f38 - f25 * f39;
        float f47 = (f41 *= f31) * f44 + (f42 *= f31) * f45 + (f43 *= f31) * f46 < 0.0f ? -1.0f : 1.0f;
        int n2 = (int)(f25 * 127.0f) & 0xFF;
        int n3 = (int)(f28 * 127.0f) & 0xFF;
        int n4 = (int)(f29 * 127.0f) & 0xFF;
        int n5 = (n4 << 16) + (n3 << 8) + n2;
        intBuffer.put(n + 0 * this.vertexSize + this.offsetNormal, n5);
        intBuffer.put(n + 1 * this.vertexSize + this.offsetNormal, n5);
        intBuffer.put(n + 2 * this.vertexSize + this.offsetNormal, n5);
        intBuffer.put(n + 3 * this.vertexSize + this.offsetNormal, n5);
        int n6 = ((int)(f38 * 32767.0f) & 0xFFFF) + (((int)(f39 * 32767.0f) & 0xFFFF) << 16);
        int n7 = ((int)(f40 * 32767.0f) & 0xFFFF) + (((int)(f47 * 32767.0f) & 0xFFFF) << 16);
        intBuffer.put(n + 0 * this.vertexSize + 11, n6);
        intBuffer.put(n + 0 * this.vertexSize + 11 + 1, n7);
        intBuffer.put(n + 1 * this.vertexSize + 11, n6);
        intBuffer.put(n + 1 * this.vertexSize + 11 + 1, n7);
        intBuffer.put(n + 2 * this.vertexSize + 11, n6);
        intBuffer.put(n + 2 * this.vertexSize + 11 + 1, n7);
        intBuffer.put(n + 3 * this.vertexSize + 11, n6);
        intBuffer.put(n + 3 * this.vertexSize + 11 + 1, n7);
        float f48 = (f4 + f9 + f14 + f19) / 4.0f;
        float f49 = (f5 + f10 + f15 + f20) / 4.0f;
        floatBuffer.put(n + 0 * this.vertexSize + 9, f48);
        floatBuffer.put(n + 0 * this.vertexSize + 9 + 1, f49);
        floatBuffer.put(n + 1 * this.vertexSize + 9, f48);
        floatBuffer.put(n + 1 * this.vertexSize + 9 + 1, f49);
        floatBuffer.put(n + 2 * this.vertexSize + 9, f48);
        floatBuffer.put(n + 2 * this.vertexSize + 9 + 1, f49);
        floatBuffer.put(n + 3 * this.vertexSize + 9, f48);
        floatBuffer.put(n + 3 * this.vertexSize + 9 + 1, f49);
        if (Shaders.useVelocityAttrib) {
            object = bufferBuilder.getQuadVertexPositions();
            int n8 = Config.getWorldRenderer().getFrameCount();
            this.setVelocity(floatBuffer, n, 0, (VertexPosition[])object, n8, f, f2, f3);
            this.setVelocity(floatBuffer, n, 1, (VertexPosition[])object, n8, f6, f7, f8);
            this.setVelocity(floatBuffer, n, 2, (VertexPosition[])object, n8, f11, f12, f13);
            this.setVelocity(floatBuffer, n, 3, (VertexPosition[])object, n8, f16, f17, f18);
            bufferBuilder.setQuadVertexPositions(null);
        }
        if (bufferBuilder.getVertexFormat() == DefaultVertexFormats.BLOCK) {
            object = bufferBuilder.getMidBlock();
            float f50 = ((Vector3f)object).getX();
            float f51 = ((Vector3f)object).getY();
            float f52 = ((Vector3f)object).getZ();
            this.setMidBlock(intBuffer, n, 0, f50 - f, f51 - f2, f52 - f3);
            this.setMidBlock(intBuffer, n, 1, f50 - f6, f51 - f7, f52 - f8);
            this.setMidBlock(intBuffer, n, 2, f50 - f11, f51 - f12, f52 - f13);
            this.setMidBlock(intBuffer, n, 3, f50 - f16, f51 - f17, f52 - f18);
        }
    }

    public void setMidBlock(IntBuffer intBuffer, int n, int n2, float f, float f2, float f3) {
        int n3 = (int)(f * 64.0f) & 0xFF;
        int n4 = (int)(f2 * 64.0f) & 0xFF;
        int n5 = (int)(f3 * 64.0f) & 0xFF;
        int n6 = (n5 << 16) + (n4 << 8) + n3;
        intBuffer.put(n + n2 * this.vertexSize + 8, n6);
    }

    public void setVelocity(FloatBuffer floatBuffer, int n, int n2, VertexPosition[] vertexPositionArray, int n3, float f, float f2, float f3) {
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        if (vertexPositionArray != null && vertexPositionArray.length == 4) {
            VertexPosition vertexPosition = vertexPositionArray[n2];
            vertexPosition.setPosition(n3, f, f2, f3);
            if (vertexPosition.isVelocityValid()) {
                f4 = vertexPosition.getVelocityX();
                f5 = vertexPosition.getVelocityY();
                f6 = vertexPosition.getVelocityZ();
            }
        }
        int n4 = n + n2 * this.vertexSize + 15;
        floatBuffer.put(n4 + 0, f4);
        floatBuffer.put(n4 + 1, f5);
        floatBuffer.put(n4 + 2, f6);
    }

    public static void calcNormalChunkLayer(BufferBuilder bufferBuilder) {
        if (bufferBuilder.getVertexFormat().hasNormal() && bufferBuilder.getDrawMode() == 7 && bufferBuilder.getVertexCount() % 4 == 0) {
            SVertexBuilder sVertexBuilder = bufferBuilder.sVertexBuilder;
            SVertexBuilder.endSetVertexFormat(bufferBuilder);
            int n = bufferBuilder.getVertexCount() * sVertexBuilder.vertexSize;
            for (int i = 0; i < n; i += sVertexBuilder.vertexSize * 4) {
                sVertexBuilder.calcNormal(bufferBuilder, i);
            }
        }
    }

    public static boolean preDrawArrays(VertexFormat vertexFormat, ByteBuffer byteBuffer) {
        int n = vertexFormat.getSize();
        if (n != 72) {
            return true;
        }
        byteBuffer.position(36);
        GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, n, byteBuffer);
        byteBuffer.position(44);
        GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, n, byteBuffer);
        byteBuffer.position(52);
        GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, n, byteBuffer);
        byteBuffer.position(60);
        GL20.glVertexAttribPointer(Shaders.velocityAttrib, 3, 5126, false, n, byteBuffer);
        byteBuffer.position(0);
        GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
        GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
        GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
        GL20.glEnableVertexAttribArray(Shaders.velocityAttrib);
        return false;
    }

    public static void postDrawArrays() {
        GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
        GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
        GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
        GL20.glDisableVertexAttribArray(Shaders.velocityAttrib);
    }
}

