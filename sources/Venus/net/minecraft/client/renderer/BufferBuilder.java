/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import com.mojang.blaze3d.vertex.DefaultColorVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.IVertexConsumer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrays;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.render.MultiTextureBuilder;
import net.optifine.render.MultiTextureData;
import net.optifine.render.RenderEnv;
import net.optifine.render.VertexPosition;
import net.optifine.shaders.SVertexBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BufferBuilder
extends DefaultColorVertexBuilder
implements IVertexConsumer {
    private static final Logger LOGGER = LogManager.getLogger();
    private ByteBuffer byteBuffer;
    private final List<DrawState> drawStates = Lists.newArrayList();
    private int drawStateIndex = 0;
    private int renderedBytes = 0;
    private int nextElementBytes = 0;
    private int uploadedBytes = 0;
    private int vertexCount;
    @Nullable
    private VertexFormatElement vertexFormatElement;
    private int vertexFormatIndex;
    private int drawMode;
    private VertexFormat vertexFormat;
    private boolean fastFormat;
    private boolean fullFormat;
    private boolean isDrawing;
    private RenderType renderType;
    private boolean renderBlocks;
    private TextureAtlasSprite[] quadSprites = null;
    private TextureAtlasSprite[] quadSpritesPrev = null;
    private TextureAtlasSprite quadSprite = null;
    private MultiTextureBuilder multiTextureBuilder = new MultiTextureBuilder();
    public SVertexBuilder sVertexBuilder;
    public RenderEnv renderEnv = null;
    public BitSet animatedSprites = null;
    public BitSet animatedSpritesCached = new BitSet();
    private ByteBuffer byteBufferTriangles;
    private Vector3f tempVec3f = new Vector3f();
    private float[] tempFloat4 = new float[4];
    private int[] tempInt4 = new int[4];
    private IntBuffer intBuffer;
    private FloatBuffer floatBuffer;
    private IRenderTypeBuffer.Impl renderTypeBuffer;
    private FloatBuffer floatBufferSort;
    private VertexPosition[] quadVertexPositions;
    private Vector3f midBlock = new Vector3f();

    public BufferBuilder(int n) {
        this.byteBuffer = GLAllocation.createDirectByteBuffer(n * 4);
        this.intBuffer = this.byteBuffer.asIntBuffer();
        this.floatBuffer = this.byteBuffer.asFloatBuffer();
        SVertexBuilder.initVertexBuilder(this);
    }

    protected void growBuffer() {
        this.growBuffer(this.vertexFormat.getSize());
    }

    private void growBuffer(int n) {
        if (this.nextElementBytes + n > this.byteBuffer.capacity()) {
            int n2 = this.byteBuffer.capacity();
            int n3 = n2 + BufferBuilder.roundUpPositive(n);
            LOGGER.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", (Object)n2, (Object)n3);
            ByteBuffer byteBuffer = GLAllocation.createDirectByteBuffer(n3);
            this.byteBuffer.position(0);
            byteBuffer.put(this.byteBuffer);
            byteBuffer.rewind();
            this.byteBuffer = byteBuffer;
            this.intBuffer = this.byteBuffer.asIntBuffer();
            this.floatBuffer = this.byteBuffer.asFloatBuffer();
            if (this.quadSprites != null) {
                TextureAtlasSprite[] textureAtlasSpriteArray = this.quadSprites;
                int n4 = this.getBufferQuadSize();
                this.quadSprites = new TextureAtlasSprite[n4];
                System.arraycopy(textureAtlasSpriteArray, 0, this.quadSprites, 0, Math.min(textureAtlasSpriteArray.length, this.quadSprites.length));
                this.quadSpritesPrev = null;
            }
        }
    }

    private static int roundUpPositive(int n) {
        int n2;
        int n3 = 0x200000;
        if (n == 0) {
            return n3;
        }
        if (n < 0) {
            n3 *= -1;
        }
        return (n2 = n % n3) == 0 ? n : n + n3 - n2;
    }

    public void sortVertexData(float f, float f2, float f3) {
        int n;
        int n2;
        int n3;
        this.byteBuffer.clear();
        FloatBuffer floatBuffer = this.byteBuffer.asFloatBuffer();
        FloatBuffer floatBuffer2 = floatBuffer.slice();
        int n4 = this.vertexCount / 4;
        float[] fArray = new float[n4];
        for (int i = 0; i < n4; ++i) {
            fArray[i] = BufferBuilder.getDistanceSq(floatBuffer, f, f2, f3, this.vertexFormat.getIntegerSize(), this.renderedBytes / 4 + i * this.vertexFormat.getSize());
        }
        int[] nArray = new int[n4];
        int n5 = 0;
        while (n5 < nArray.length) {
            nArray[n5] = n5++;
        }
        IntArrays.mergeSort(nArray, (arg_0, arg_1) -> BufferBuilder.lambda$sortVertexData$0(fArray, arg_0, arg_1));
        BitSet bitSet = new BitSet();
        FloatBuffer floatBuffer3 = this.getFloatBufferSort(this.vertexFormat.getIntegerSize() * 4);
        int n6 = bitSet.nextClearBit(0);
        while (n6 < nArray.length) {
            n3 = nArray[n6];
            if (n3 != n6) {
                this.limitToVertex(floatBuffer, n3);
                floatBuffer3.clear();
                floatBuffer3.put(floatBuffer);
                n2 = n3;
                n = nArray[n3];
                while (n2 != n6) {
                    this.limitToVertex(floatBuffer, n);
                    floatBuffer2.clear();
                    floatBuffer2.position(floatBuffer.position());
                    floatBuffer2.limit(floatBuffer.limit());
                    this.limitToVertex(floatBuffer, n2);
                    floatBuffer.put(floatBuffer2);
                    bitSet.set(n2);
                    n2 = n;
                    n = nArray[n];
                }
                this.limitToVertex(floatBuffer, n6);
                floatBuffer3.flip();
                floatBuffer.put(floatBuffer3);
            }
            bitSet.set(n6);
            n6 = bitSet.nextClearBit(n6 + 1);
        }
        if (this.quadSprites != null) {
            TextureAtlasSprite[] textureAtlasSpriteArray = new TextureAtlasSprite[this.vertexCount / 4];
            n3 = this.vertexFormat.getSize() / 4 * 4;
            for (n2 = 0; n2 < nArray.length; ++n2) {
                n = nArray[n2];
                textureAtlasSpriteArray[n2] = this.quadSprites[n];
            }
            System.arraycopy(textureAtlasSpriteArray, 0, this.quadSprites, 0, textureAtlasSpriteArray.length);
        }
    }

    private void limitToVertex(FloatBuffer floatBuffer, int n) {
        int n2 = this.vertexFormat.getIntegerSize() * 4;
        floatBuffer.limit(this.renderedBytes / 4 + (n + 1) * n2);
        floatBuffer.position(this.renderedBytes / 4 + n * n2);
    }

    public State getVertexState() {
        this.byteBuffer.limit(this.nextElementBytes);
        this.byteBuffer.position(this.renderedBytes);
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.vertexCount * this.vertexFormat.getSize());
        byteBuffer.put(this.byteBuffer);
        this.byteBuffer.clear();
        TextureAtlasSprite[] textureAtlasSpriteArray = this.getQuadSpritesCopy();
        return new State(byteBuffer, this.vertexFormat, textureAtlasSpriteArray);
    }

    private TextureAtlasSprite[] getQuadSpritesCopy() {
        if (this.quadSprites == null) {
            return null;
        }
        int n = this.vertexCount / 4;
        TextureAtlasSprite[] textureAtlasSpriteArray = new TextureAtlasSprite[n];
        System.arraycopy(this.quadSprites, 0, textureAtlasSpriteArray, 0, n);
        return textureAtlasSpriteArray;
    }

    private static float getDistanceSq(FloatBuffer floatBuffer, float f, float f2, float f3, int n, int n2) {
        float f4 = floatBuffer.get(n2 + n * 0 + 0);
        float f5 = floatBuffer.get(n2 + n * 0 + 1);
        float f6 = floatBuffer.get(n2 + n * 0 + 2);
        float f7 = floatBuffer.get(n2 + n * 1 + 0);
        float f8 = floatBuffer.get(n2 + n * 1 + 1);
        float f9 = floatBuffer.get(n2 + n * 1 + 2);
        float f10 = floatBuffer.get(n2 + n * 2 + 0);
        float f11 = floatBuffer.get(n2 + n * 2 + 1);
        float f12 = floatBuffer.get(n2 + n * 2 + 2);
        float f13 = floatBuffer.get(n2 + n * 3 + 0);
        float f14 = floatBuffer.get(n2 + n * 3 + 1);
        float f15 = floatBuffer.get(n2 + n * 3 + 2);
        float f16 = (f4 + f7 + f10 + f13) * 0.25f - f;
        float f17 = (f5 + f8 + f11 + f14) * 0.25f - f2;
        float f18 = (f6 + f9 + f12 + f15) * 0.25f - f3;
        return f16 * f16 + f17 * f17 + f18 * f18;
    }

    public void setVertexState(State state) {
        state.stateByteBuffer.clear();
        int n = state.stateByteBuffer.capacity();
        this.growBuffer(n);
        this.byteBuffer.limit(this.byteBuffer.capacity());
        this.byteBuffer.position(this.renderedBytes);
        this.byteBuffer.put(state.stateByteBuffer);
        this.byteBuffer.clear();
        VertexFormat vertexFormat = state.stateVertexFormat;
        this.setVertexFormat(vertexFormat);
        this.vertexCount = n / vertexFormat.getSize();
        this.nextElementBytes = this.renderedBytes + this.vertexCount * vertexFormat.getSize();
        if (state.stateQuadSprites != null) {
            if (this.quadSprites == null) {
                this.quadSprites = this.quadSpritesPrev;
            }
            if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
            }
            TextureAtlasSprite[] textureAtlasSpriteArray = state.stateQuadSprites;
            System.arraycopy(textureAtlasSpriteArray, 0, this.quadSprites, 0, textureAtlasSpriteArray.length);
        } else {
            if (this.quadSprites != null) {
                this.quadSpritesPrev = this.quadSprites;
            }
            this.quadSprites = null;
        }
    }

    public void begin(int n, VertexFormat vertexFormat) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already building!");
        }
        this.isDrawing = true;
        this.drawMode = n;
        this.setVertexFormat(vertexFormat);
        this.vertexFormatElement = (VertexFormatElement)vertexFormat.getElements().get(0);
        this.vertexFormatIndex = 0;
        this.byteBuffer.clear();
        if (Config.isShaders()) {
            SVertexBuilder.endSetVertexFormat(this);
        }
        if (Config.isMultiTexture()) {
            this.initQuadSprites();
        }
        if (SmartAnimations.isActive()) {
            if (this.animatedSprites == null) {
                this.animatedSprites = this.animatedSpritesCached;
            }
            this.animatedSprites.clear();
        } else if (this.animatedSprites != null) {
            this.animatedSprites = null;
        }
    }

    @Override
    public IVertexBuilder tex(float f, float f2) {
        if (this.quadSprite != null && this.quadSprites != null) {
            f = this.quadSprite.toSingleU(f);
            f2 = this.quadSprite.toSingleV(f2);
            this.quadSprites[this.vertexCount / 4] = this.quadSprite;
        }
        return IVertexConsumer.super.tex(f, f2);
    }

    private void setVertexFormat(VertexFormat vertexFormat) {
        if (this.vertexFormat != vertexFormat) {
            this.vertexFormat = vertexFormat;
            boolean bl = vertexFormat == DefaultVertexFormats.ENTITY;
            boolean bl2 = vertexFormat == DefaultVertexFormats.BLOCK;
            this.fastFormat = bl || bl2;
            this.fullFormat = bl;
        }
    }

    public void finishDrawing() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not building!");
        }
        this.isDrawing = false;
        MultiTextureData multiTextureData = this.multiTextureBuilder.build(this.vertexCount, this.renderType, this.quadSprites);
        this.drawStates.add(new DrawState(this.vertexFormat, this.vertexCount, this.drawMode, multiTextureData));
        this.renderType = null;
        this.renderBlocks = false;
        if (this.quadSprites != null) {
            this.quadSpritesPrev = this.quadSprites;
        }
        this.quadSprites = null;
        this.quadSprite = null;
        this.renderedBytes += this.vertexCount * this.vertexFormat.getSize();
        this.vertexCount = 0;
        this.vertexFormatElement = null;
        this.vertexFormatIndex = 0;
    }

    @Override
    public void putByte(int n, byte by) {
        this.byteBuffer.put(this.nextElementBytes + n, by);
    }

    @Override
    public void putShort(int n, short s) {
        this.byteBuffer.putShort(this.nextElementBytes + n, s);
    }

    @Override
    public void putFloat(int n, float f) {
        this.byteBuffer.putFloat(this.nextElementBytes + n, f);
    }

    @Override
    public void endVertex() {
        if (this.vertexFormatIndex != 0) {
            throw new IllegalStateException("Not filled all elements of the vertex");
        }
        ++this.vertexCount;
        this.growBuffer();
        if (Config.isShaders()) {
            SVertexBuilder.endAddVertex(this);
        }
    }

    @Override
    public void nextVertexFormatIndex() {
        VertexFormatElement vertexFormatElement;
        ImmutableList<VertexFormatElement> immutableList = this.vertexFormat.getElements();
        this.vertexFormatIndex = (this.vertexFormatIndex + 1) % immutableList.size();
        this.nextElementBytes += this.vertexFormatElement.getSize();
        this.vertexFormatElement = vertexFormatElement = (VertexFormatElement)immutableList.get(this.vertexFormatIndex);
        if (vertexFormatElement.getUsage() == VertexFormatElement.Usage.PADDING) {
            this.nextVertexFormatIndex();
        }
        if (this.defaultColor && this.vertexFormatElement.getUsage() == VertexFormatElement.Usage.COLOR) {
            IVertexConsumer.super.color(this.defaultRed, this.defaultGreen, this.defaultBlue, this.defaultAlpha);
        }
    }

    @Override
    public IVertexBuilder color(int n, int n2, int n3, int n4) {
        if (this.defaultColor) {
            throw new IllegalStateException();
        }
        return IVertexConsumer.super.color(n, n2, n3, n4);
    }

    @Override
    public void addVertex(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int n, int n2, float f10, float f11, float f12) {
        if (this.defaultColor) {
            throw new IllegalStateException();
        }
        if (this.fastFormat) {
            int n3;
            this.putFloat(0, f);
            this.putFloat(4, f2);
            this.putFloat(8, f3);
            this.putByte(12, (byte)(f4 * 255.0f));
            this.putByte(13, (byte)(f5 * 255.0f));
            this.putByte(14, (byte)(f6 * 255.0f));
            this.putByte(15, (byte)(f7 * 255.0f));
            this.putFloat(16, f8);
            this.putFloat(20, f9);
            if (this.fullFormat) {
                this.putShort(24, (short)(n & 0xFFFF));
                this.putShort(26, (short)(n >> 16 & 0xFFFF));
                n3 = 28;
            } else {
                n3 = 24;
            }
            this.putShort(n3 + 0, (short)(n2 & 0xFFFF));
            this.putShort(n3 + 2, (short)(n2 >> 16 & 0xFFFF));
            this.putByte(n3 + 4, IVertexConsumer.normalInt(f10));
            this.putByte(n3 + 5, IVertexConsumer.normalInt(f11));
            this.putByte(n3 + 6, IVertexConsumer.normalInt(f12));
            this.nextElementBytes += this.vertexFormat.getSize();
            this.endVertex();
        } else {
            super.addVertex(f, f2, f3, f4, f5, f6, f7, f8, f9, n, n2, f10, f11, f12);
        }
    }

    public Pair<DrawState, ByteBuffer> getNextBuffer() {
        DrawState drawState = this.drawStates.get(this.drawStateIndex++);
        this.byteBuffer.position(this.uploadedBytes);
        this.uploadedBytes += drawState.getVertexCount() * drawState.getFormat().getSize();
        this.byteBuffer.limit(this.uploadedBytes);
        if (this.drawStateIndex == this.drawStates.size() && this.vertexCount == 0) {
            this.reset();
        }
        ByteBuffer byteBuffer = this.byteBuffer.slice();
        byteBuffer.order(this.byteBuffer.order());
        this.byteBuffer.clear();
        if (drawState.drawMode == 7 && Config.isQuadsToTriangles()) {
            if (this.byteBufferTriangles == null) {
                this.byteBufferTriangles = GLAllocation.createDirectByteBuffer(this.byteBuffer.capacity() * 2);
            }
            if (this.byteBufferTriangles.capacity() < this.byteBuffer.capacity() * 2) {
                this.byteBufferTriangles = GLAllocation.createDirectByteBuffer(this.byteBuffer.capacity() * 2);
            }
            VertexFormat vertexFormat = drawState.getFormat();
            int n = drawState.getVertexCount();
            BufferBuilder.quadsToTriangles(byteBuffer, vertexFormat, n, this.byteBufferTriangles);
            int n2 = n * 6 / 4;
            DrawState drawState2 = new DrawState(vertexFormat, n2, 4);
            return Pair.of(drawState2, this.byteBufferTriangles);
        }
        return Pair.of(drawState, byteBuffer);
    }

    public void reset() {
        if (this.renderedBytes != this.uploadedBytes) {
            LOGGER.warn("Bytes mismatch " + this.renderedBytes + " " + this.uploadedBytes);
        }
        this.discard();
    }

    public void discard() {
        this.renderedBytes = 0;
        this.uploadedBytes = 0;
        this.nextElementBytes = 0;
        this.drawStates.clear();
        this.drawStateIndex = 0;
        this.quadSprite = null;
    }

    @Override
    public VertexFormatElement getCurrentElement() {
        if (this.vertexFormatElement == null) {
            throw new IllegalStateException("BufferBuilder not started");
        }
        return this.vertexFormatElement;
    }

    public boolean isDrawing() {
        return this.isDrawing;
    }

    @Override
    public void putSprite(TextureAtlasSprite textureAtlasSprite) {
        if (this.animatedSprites != null && textureAtlasSprite != null && textureAtlasSprite.isTerrain() && textureAtlasSprite.getAnimationIndex() >= 0) {
            this.animatedSprites.set(textureAtlasSprite.getAnimationIndex());
        }
        if (this.quadSprites != null) {
            int n = this.vertexCount / 4;
            this.quadSprites[n] = textureAtlasSprite;
        }
    }

    @Override
    public void setSprite(TextureAtlasSprite textureAtlasSprite) {
        if (this.animatedSprites != null && textureAtlasSprite != null && textureAtlasSprite.isTerrain() && textureAtlasSprite.getAnimationIndex() >= 0) {
            this.animatedSprites.set(textureAtlasSprite.getAnimationIndex());
        }
        if (this.quadSprites != null) {
            this.quadSprite = textureAtlasSprite;
        }
    }

    @Override
    public boolean isMultiTexture() {
        return this.quadSprites != null;
    }

    @Override
    public void setRenderType(RenderType renderType) {
        this.renderType = renderType;
    }

    @Override
    public RenderType getRenderType() {
        return this.renderType;
    }

    @Override
    public void setRenderBlocks(boolean bl) {
        this.renderBlocks = bl;
        if (Config.isMultiTexture()) {
            this.initQuadSprites();
        }
    }

    public void setBlockLayer(RenderType renderType) {
        this.renderType = renderType;
        this.renderBlocks = true;
    }

    private void initQuadSprites() {
        if (this.renderBlocks && this.renderType != null && this.quadSprites == null && this.isDrawing) {
            if (this.vertexCount > 0) {
                int n = this.drawMode;
                VertexFormat vertexFormat = this.vertexFormat;
                RenderType renderType = this.renderType;
                boolean bl = this.renderBlocks;
                this.renderType.finish(this, 0, 0, 0);
                this.begin(n, vertexFormat);
                this.renderType = renderType;
                this.renderBlocks = bl;
            }
            this.quadSprites = this.quadSpritesPrev;
            if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
            }
        }
    }

    private int getBufferQuadSize() {
        return this.byteBuffer.capacity() / this.vertexFormat.getSize();
    }

    @Override
    public RenderEnv getRenderEnv(BlockState blockState, BlockPos blockPos) {
        if (this.renderEnv == null) {
            this.renderEnv = new RenderEnv(blockState, blockPos);
            return this.renderEnv;
        }
        this.renderEnv.reset(blockState, blockPos);
        return this.renderEnv;
    }

    private static void quadsToTriangles(ByteBuffer byteBuffer, VertexFormat vertexFormat, int n, ByteBuffer byteBuffer2) {
        int n2 = vertexFormat.getSize();
        int n3 = byteBuffer.limit();
        byteBuffer.rewind();
        byteBuffer2.clear();
        for (int i = 0; i < n; i += 4) {
            byteBuffer.limit((i + 3) * n2);
            byteBuffer.position(i * n2);
            byteBuffer2.put(byteBuffer);
            byteBuffer.limit((i + 1) * n2);
            byteBuffer.position(i * n2);
            byteBuffer2.put(byteBuffer);
            byteBuffer.limit((i + 2 + 2) * n2);
            byteBuffer.position((i + 2) * n2);
            byteBuffer2.put(byteBuffer);
        }
        byteBuffer.limit(n3);
        byteBuffer.rewind();
        byteBuffer2.flip();
    }

    public int getDrawMode() {
        return this.drawMode;
    }

    public int getVertexCount() {
        return this.vertexCount;
    }

    @Override
    public Vector3f getTempVec3f(Vector3f vector3f) {
        this.tempVec3f.set(vector3f.getX(), vector3f.getY(), vector3f.getZ());
        return this.tempVec3f;
    }

    @Override
    public Vector3f getTempVec3f(float f, float f2, float f3) {
        this.tempVec3f.set(f, f2, f3);
        return this.tempVec3f;
    }

    @Override
    public float[] getTempFloat4(float f, float f2, float f3, float f4) {
        this.tempFloat4[0] = f;
        this.tempFloat4[1] = f2;
        this.tempFloat4[2] = f3;
        this.tempFloat4[3] = f4;
        return this.tempFloat4;
    }

    @Override
    public int[] getTempInt4(int n, int n2, int n3, int n4) {
        this.tempInt4[0] = n;
        this.tempInt4[1] = n2;
        this.tempInt4[2] = n3;
        this.tempInt4[3] = n4;
        return this.tempInt4;
    }

    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    public FloatBuffer getFloatBuffer() {
        return this.floatBuffer;
    }

    public IntBuffer getIntBuffer() {
        return this.intBuffer;
    }

    public int getBufferIntSize() {
        return this.vertexCount * this.vertexFormat.getIntegerSize();
    }

    private FloatBuffer getFloatBufferSort(int n) {
        if (this.floatBufferSort == null || this.floatBufferSort.capacity() < n) {
            this.floatBufferSort = GLAllocation.createDirectFloatBuffer(n);
        }
        return this.floatBufferSort;
    }

    @Override
    public IRenderTypeBuffer.Impl getRenderTypeBuffer() {
        return this.renderTypeBuffer;
    }

    public void setRenderTypeBuffer(IRenderTypeBuffer.Impl impl) {
        this.renderTypeBuffer = impl;
    }

    public void addVertexText(float f, float f2, float f3, int n, int n2, int n3, int n4, float f4, float f5, int n5, int n6) {
        if (this.vertexFormat.getSize() != DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP.getSize()) {
            throw new IllegalStateException("Invalid text vertex format: " + this.vertexFormat);
        }
        this.putFloat(0, f);
        this.putFloat(4, f2);
        this.putFloat(8, f3);
        this.putByte(12, (byte)n);
        this.putByte(13, (byte)n2);
        this.putByte(14, (byte)n3);
        this.putByte(15, (byte)n4);
        this.putFloat(16, f4);
        this.putFloat(20, f5);
        this.putShort(24, (short)n5);
        this.putShort(26, (short)n6);
        this.nextElementBytes += this.vertexFormat.getSize();
        this.endVertex();
    }

    @Override
    public void setQuadVertexPositions(VertexPosition[] vertexPositionArray) {
        this.quadVertexPositions = vertexPositionArray;
    }

    public VertexPosition[] getQuadVertexPositions() {
        return this.quadVertexPositions;
    }

    @Override
    public void setMidBlock(float f, float f2, float f3) {
        this.midBlock.set(f, f2, f3);
    }

    public Vector3f getMidBlock() {
        return this.midBlock;
    }

    public void putBulkData(ByteBuffer byteBuffer) {
        if (Config.isShaders()) {
            SVertexBuilder.beginAddVertexData(this, byteBuffer);
        }
        this.growBuffer(byteBuffer.limit() + this.vertexFormat.getSize());
        this.byteBuffer.position(this.vertexCount * this.vertexFormat.getSize());
        this.byteBuffer.put(byteBuffer);
        this.vertexCount += byteBuffer.limit() / this.vertexFormat.getSize();
        this.nextElementBytes += byteBuffer.limit();
        if (Config.isShaders()) {
            SVertexBuilder.endAddVertexData(this);
        }
    }

    public VertexFormat getVertexFormat() {
        return this.vertexFormat;
    }

    public int getStartPosition() {
        return this.renderedBytes;
    }

    public int getIntStartPosition() {
        return this.renderedBytes / 4;
    }

    private static int lambda$sortVertexData$0(float[] fArray, int n, int n2) {
        return Floats.compare(fArray[n2], fArray[n]);
    }

    public static class State {
        private final ByteBuffer stateByteBuffer;
        private final VertexFormat stateVertexFormat;
        private TextureAtlasSprite[] stateQuadSprites;

        public State(ByteBuffer byteBuffer, VertexFormat vertexFormat, TextureAtlasSprite[] textureAtlasSpriteArray) {
            this(byteBuffer, vertexFormat);
            this.stateQuadSprites = textureAtlasSpriteArray;
        }

        private State(ByteBuffer byteBuffer, VertexFormat vertexFormat) {
            this.stateByteBuffer = byteBuffer;
            this.stateVertexFormat = vertexFormat;
        }
    }

    public static final class DrawState {
        private final VertexFormat format;
        private final int vertexCount;
        private final int drawMode;
        private MultiTextureData multiTextureData;

        private DrawState(VertexFormat vertexFormat, int n, int n2, MultiTextureData multiTextureData) {
            this(vertexFormat, n, n2);
            this.multiTextureData = multiTextureData;
        }

        public MultiTextureData getMultiTextureData() {
            return this.multiTextureData;
        }

        private DrawState(VertexFormat vertexFormat, int n, int n2) {
            this.format = vertexFormat;
            this.vertexCount = n;
            this.drawMode = n2;
        }

        public VertexFormat getFormat() {
            return this.format;
        }

        public int getVertexCount() {
            return this.vertexCount;
        }

        public int getDrawMode() {
            return this.drawMode;
        }
    }
}

