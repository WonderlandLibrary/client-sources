package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import optfine.*;
import org.apache.logging.log4j.*;
import org.lwjgl.opengl.*;
import java.nio.*;
import net.minecraft.util.*;
import java.util.*;

public class WorldRenderer
{
    private boolean isDrawing;
    private EnumWorldBlockLayer blockLayer;
    private TextureAtlasSprite quadSprite;
    private boolean needsUpdate;
    private TextureAtlasSprite[] quadSprites;
    private VertexFormatElement field_181677_f;
    private boolean[] drawnIcons;
    private int drawMode;
    private double zOffset;
    private ShortBuffer field_181676_c;
    private int field_181678_g;
    private IntBuffer rawIntBuffer;
    private FloatBuffer rawFloatBuffer;
    private double yOffset;
    private ByteBuffer byteBuffer;
    private int vertexCount;
    private double xOffset;
    private static final String[] I;
    private static final String __OBFID;
    private VertexFormat vertexFormat;
    
    public WorldRenderer color(final float n, final float n2, final float n3, final float n4) {
        return this.color((int)(n * 255.0f), (int)(n2 * 255.0f), (int)(n3 * 255.0f), (int)(n4 * 255.0f));
    }
    
    public void endVertex() {
        this.vertexCount += " ".length();
        this.func_181670_b(this.vertexFormat.func_181719_f());
    }
    
    public void addVertexData(final int[] array) {
        this.func_181670_b(array.length);
        this.rawIntBuffer.position(this.func_181664_j());
        this.rawIntBuffer.put(array);
        this.vertexCount += array.length / this.vertexFormat.func_181719_f();
    }
    
    public void putColor4(final int n) {
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < (0x4D ^ 0x49)) {
            this.putColor(n, i + " ".length());
            ++i;
        }
    }
    
    public void drawMultiTexture() {
        if (this.quadSprites != null) {
            final int countRegisteredSprites = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();
            if (this.drawnIcons.length <= countRegisteredSprites) {
                this.drawnIcons = new boolean[countRegisteredSprites + " ".length()];
            }
            Arrays.fill(this.drawnIcons, "".length() != 0);
            int length = "".length();
            int n = -" ".length();
            final int n2 = this.vertexCount / (0x69 ^ 0x6D);
            int i = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (i < n2) {
                final TextureAtlasSprite textureAtlasSprite = this.quadSprites[i];
                if (textureAtlasSprite != null) {
                    final int indexInMap = textureAtlasSprite.getIndexInMap();
                    if (!this.drawnIcons[indexInMap]) {
                        if (textureAtlasSprite == TextureUtils.iconGrassSideOverlay) {
                            if (n < 0) {
                                n = i;
                                "".length();
                                if (3 < 0) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            i = this.drawForIcon(textureAtlasSprite, i) - " ".length();
                            ++length;
                            if (this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT) {
                                this.drawnIcons[indexInMap] = (" ".length() != 0);
                            }
                        }
                    }
                }
                ++i;
            }
            if (n >= 0) {
                this.drawForIcon(TextureUtils.iconGrassSideOverlay, n);
                ++length;
            }
            if (length > 0) {}
        }
    }
    
    public void putPosition(final double n, final double n2, final double n3) {
        final int func_181719_f = this.vertexFormat.func_181719_f();
        final int n4 = (this.vertexCount - (0x70 ^ 0x74)) * func_181719_f;
        int i = "".length();
        "".length();
        if (0 < 0) {
            throw null;
        }
        while (i < (0x3B ^ 0x3F)) {
            final int n5 = n4 + i * func_181719_f;
            final int n6 = n5 + " ".length();
            final int n7 = n6 + " ".length();
            this.rawIntBuffer.put(n5, Float.floatToRawIntBits((float)(n + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(n5))));
            this.rawIntBuffer.put(n6, Float.floatToRawIntBits((float)(n2 + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(n6))));
            this.rawIntBuffer.put(n7, Float.floatToRawIntBits((float)(n3 + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(n7))));
            ++i;
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void setBlockLayer(final EnumWorldBlockLayer blockLayer) {
        this.blockLayer = blockLayer;
    }
    
    private int func_181664_j() {
        return this.vertexCount * this.vertexFormat.func_181719_f();
    }
    
    public void putSprite(final TextureAtlasSprite textureAtlasSprite) {
        if (this.quadSprites != null) {
            this.quadSprites[this.vertexCount / (0xB7 ^ 0xB3) - " ".length()] = textureAtlasSprite;
        }
    }
    
    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }
    
    private void putColor(final int n, final int n2) {
        this.putColorRGBA(this.getColorIndex(n2), n >> (0x2D ^ 0x3D) & 160 + 157 - 106 + 44, n >> (0xB0 ^ 0xB8) & 42 + 197 - 189 + 205, n & 5 + 25 - 16 + 241, n >> (0x7B ^ 0x63) & 23 + 169 + 58 + 5);
    }
    
    private void func_181670_b(final int n) {
        if (n > this.rawIntBuffer.remaining()) {
            final int capacity = this.byteBuffer.capacity();
            final int n2 = capacity % (1097448 + 31909 + 490377 + 477418);
            final int n3 = n2 + (((this.rawIntBuffer.position() + n) * (0x2A ^ 0x2E) - n2) / (1186306 + 566870 - 274747 + 618723) + " ".length()) * (540052 + 1209948 - 608033 + 955185);
            LogManager.getLogger().warn(WorldRenderer.I["".length()] + capacity + WorldRenderer.I[" ".length()] + n3 + WorldRenderer.I["  ".length()]);
            final int position = this.rawIntBuffer.position();
            final ByteBuffer directByteBuffer = GLAllocation.createDirectByteBuffer(n3);
            this.byteBuffer.position("".length());
            directByteBuffer.put(this.byteBuffer);
            directByteBuffer.rewind();
            this.byteBuffer = directByteBuffer;
            this.rawFloatBuffer = this.byteBuffer.asFloatBuffer().asReadOnlyBuffer();
            (this.rawIntBuffer = this.byteBuffer.asIntBuffer()).position(position);
            (this.field_181676_c = this.byteBuffer.asShortBuffer()).position(position << " ".length());
            if (this.quadSprites != null) {
                final TextureAtlasSprite[] quadSprites = this.quadSprites;
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
                System.arraycopy(quadSprites, "".length(), this.quadSprites, "".length(), Math.min(quadSprites.length, this.quadSprites.length));
            }
        }
    }
    
    public void finishDrawing() {
        if (!this.isDrawing) {
            throw new IllegalStateException(WorldRenderer.I[0x51 ^ 0x55]);
        }
        this.isDrawing = ("".length() != 0);
        this.byteBuffer.position("".length());
        this.byteBuffer.limit(this.func_181664_j() * (0x13 ^ 0x17));
    }
    
    private int drawForIcon(final TextureAtlasSprite textureAtlasSprite, final int n) {
        GL11.glBindTexture(2951 + 2985 - 3038 + 655, textureAtlasSprite.glSpriteTextureId);
        int n2 = -" ".length();
        int n3 = -" ".length();
        final int n4 = this.vertexCount / (0xC2 ^ 0xC6);
        int i = n;
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i < n4) {
            if (this.quadSprites[i] == textureAtlasSprite) {
                if (n3 < 0) {
                    n3 = i;
                    "".length();
                    if (1 == 2) {
                        throw null;
                    }
                }
            }
            else if (n3 >= 0) {
                this.draw(n3, i);
                if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
                    return i;
                }
                n3 = -" ".length();
                if (n2 < 0) {
                    n2 = i;
                }
            }
            ++i;
        }
        if (n3 >= 0) {
            this.draw(n3, n4);
        }
        if (n2 < 0) {
            n2 = n4;
        }
        return n2;
    }
    
    public boolean isMultiTexture() {
        if (this.quadSprites != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private int getBufferQuadSize() {
        return this.rawIntBuffer.capacity() * (0x7 ^ 0x3) / (this.vertexFormat.func_181719_f() * (0x99 ^ 0x9D));
    }
    
    public void begin(final int drawMode, final VertexFormat vertexFormat) {
        if (this.isDrawing) {
            throw new IllegalStateException(WorldRenderer.I["   ".length()]);
        }
        this.isDrawing = (" ".length() != 0);
        this.reset();
        this.drawMode = drawMode;
        this.vertexFormat = vertexFormat;
        this.field_181677_f = vertexFormat.getElement(this.field_181678_g);
        this.needsUpdate = ("".length() != 0);
        this.byteBuffer.limit(this.byteBuffer.capacity());
        if (Config.isMultiTexture()) {
            if (this.blockLayer != null && this.quadSprites == null) {
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
        }
        else {
            this.quadSprites = null;
        }
    }
    
    public State func_181672_a() {
        this.rawIntBuffer.rewind();
        final int func_181664_j = this.func_181664_j();
        this.rawIntBuffer.limit(func_181664_j);
        final int[] array = new int[func_181664_j];
        this.rawIntBuffer.get(array);
        this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
        this.rawIntBuffer.position(func_181664_j);
        TextureAtlasSprite[] array2 = null;
        if (this.quadSprites != null) {
            final int n = this.vertexCount / (0xBF ^ 0xBB);
            array2 = new TextureAtlasSprite[n];
            System.arraycopy(this.quadSprites, "".length(), array2, "".length(), n);
        }
        return new State(array, new VertexFormat(this.vertexFormat), array2);
    }
    
    public WorldRenderer pos(final double n, final double n2, final double n3) {
        final int n4 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n4, (float)(n + this.xOffset));
                this.byteBuffer.putFloat(n4 + (0x34 ^ 0x30), (float)(n2 + this.yOffset));
                this.byteBuffer.putFloat(n4 + (0x3A ^ 0x32), (float)(n3 + this.zOffset));
                "".length();
                if (1 >= 4) {
                    throw null;
                }
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putInt(n4, Float.floatToRawIntBits((float)(n + this.xOffset)));
                this.byteBuffer.putInt(n4 + (0x17 ^ 0x13), Float.floatToRawIntBits((float)(n2 + this.yOffset)));
                this.byteBuffer.putInt(n4 + (0x70 ^ 0x78), Float.floatToRawIntBits((float)(n3 + this.zOffset)));
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n4, (short)(n + this.xOffset));
                this.byteBuffer.putShort(n4 + "  ".length(), (short)(n2 + this.yOffset));
                this.byteBuffer.putShort(n4 + (0x3C ^ 0x38), (short)(n3 + this.zOffset));
                "".length();
                if (3 < 2) {
                    throw null;
                }
                break;
            }
            case 6:
            case 7: {
                this.byteBuffer.put(n4, (byte)(n + this.xOffset));
                this.byteBuffer.put(n4 + " ".length(), (byte)(n2 + this.yOffset));
                this.byteBuffer.put(n4 + "  ".length(), (byte)(n3 + this.zOffset));
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    private static float func_181665_a(final FloatBuffer floatBuffer, final float n, final float n2, final float n3, final int n4, final int n5) {
        final float value = floatBuffer.get(n5 + n4 * "".length() + "".length());
        final float value2 = floatBuffer.get(n5 + n4 * "".length() + " ".length());
        final float value3 = floatBuffer.get(n5 + n4 * "".length() + "  ".length());
        final float value4 = floatBuffer.get(n5 + n4 * " ".length() + "".length());
        final float value5 = floatBuffer.get(n5 + n4 * " ".length() + " ".length());
        final float value6 = floatBuffer.get(n5 + n4 * " ".length() + "  ".length());
        final float value7 = floatBuffer.get(n5 + n4 * "  ".length() + "".length());
        final float value8 = floatBuffer.get(n5 + n4 * "  ".length() + " ".length());
        final float value9 = floatBuffer.get(n5 + n4 * "  ".length() + "  ".length());
        final float value10 = floatBuffer.get(n5 + n4 * "   ".length() + "".length());
        final float value11 = floatBuffer.get(n5 + n4 * "   ".length() + " ".length());
        final float value12 = floatBuffer.get(n5 + n4 * "   ".length() + "  ".length());
        final float n6 = (value + value4 + value7 + value10) * 0.25f - n;
        final float n7 = (value2 + value5 + value8 + value11) * 0.25f - n2;
        final float n8 = (value3 + value6 + value9 + value12) * 0.25f - n3;
        return n6 * n6 + n7 * n7 + n8 * n8;
    }
    
    private void putColorRGBA(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.rawIntBuffer.put(n, n5 << (0x6B ^ 0x73) | n4 << (0x8 ^ 0x18) | n3 << (0x64 ^ 0x6C) | n2);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            this.rawIntBuffer.put(n, n2 << (0x6F ^ 0x77) | n3 << (0x64 ^ 0x74) | n4 << (0x92 ^ 0x9A) | n5);
        }
    }
    
    public WorldRenderer(final int n) {
        this.blockLayer = null;
        this.drawnIcons = new boolean[223 + 127 - 308 + 214];
        this.quadSprites = null;
        this.quadSprite = null;
        this.byteBuffer = GLAllocation.createDirectByteBuffer(n * (0x14 ^ 0x10));
        this.rawIntBuffer = this.byteBuffer.asIntBuffer();
        this.field_181676_c = this.byteBuffer.asShortBuffer();
        this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
    }
    
    public int getVertexCount() {
        return this.vertexCount;
    }
    
    public void setVertexState(final State state) {
        this.rawIntBuffer.clear();
        this.func_181670_b(state.getRawBuffer().length);
        this.rawIntBuffer.put(state.getRawBuffer());
        this.vertexCount = state.getVertexCount();
        this.vertexFormat = new VertexFormat(state.getVertexFormat());
        if (State.access$0(state) != null) {
            if (this.quadSprites == null) {
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
            }
            final TextureAtlasSprite[] access$0 = State.access$0(state);
            System.arraycopy(access$0, "".length(), this.quadSprites, "".length(), access$0.length);
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            this.quadSprites = null;
        }
    }
    
    private int getColorIndex(final int n) {
        return ((this.vertexCount - n) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / (0x99 ^ 0x9D);
    }
    
    public void putBrightness4(final int n, final int n2, final int n3, final int n4) {
        final int n5 = (this.vertexCount - (0x37 ^ 0x33)) * this.vertexFormat.func_181719_f() + this.vertexFormat.getUvOffsetById(" ".length()) / (0x39 ^ 0x3D);
        final int n6 = this.vertexFormat.getNextOffset() >> "  ".length();
        this.rawIntBuffer.put(n5, n);
        this.rawIntBuffer.put(n5 + n6, n2);
        this.rawIntBuffer.put(n5 + n6 * "  ".length(), n3);
        this.rawIntBuffer.put(n5 + n6 * "   ".length(), n4);
    }
    
    private void draw(final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 > 0) {
            GL11.glDrawArrays(this.drawMode, n * (0xAE ^ 0xAA), n3 * (0x19 ^ 0x1D));
        }
    }
    
    public void setSprite(final TextureAtlasSprite quadSprite) {
        if (this.quadSprites != null) {
            this.quadSprite = quadSprite;
        }
    }
    
    public void putColorRGB_F(final float n, final float n2, final float n3, final int n4) {
        this.putColorRGBA(this.getColorIndex(n4), MathHelper.clamp_int((int)(n * 255.0f), "".length(), 186 + 68 - 13 + 14), MathHelper.clamp_int((int)(n2 * 255.0f), "".length(), 140 + 93 - 75 + 97), MathHelper.clamp_int((int)(n3 * 255.0f), "".length(), 217 + 220 - 379 + 197), 61 + 241 - 183 + 136);
    }
    
    public void markDirty() {
        this.needsUpdate = (" ".length() != 0);
    }
    
    public void putColorMultiplier(final float n, final float n2, final float n3, final int n4) {
        final int colorIndex = this.getColorIndex(n4);
        int n5 = -" ".length();
        if (!this.needsUpdate) {
            final int value = this.rawIntBuffer.get(colorIndex);
            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                n5 = ((value & -(13587229 + 13273114 - 12637921 + 2554794)) | (int)((value >> (0xAA ^ 0xBA) & 14 + 22 + 71 + 148) * n3) << (0x52 ^ 0x42) | (int)((value >> (0x3F ^ 0x37) & 153 + 238 - 352 + 216) * n2) << (0x20 ^ 0x28) | (int)((value & 24 + 49 + 137 + 45) * n));
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                n5 = ((value & 153 + 144 - 68 + 26) | (int)((value >> (0xA0 ^ 0xB8) & 242 + 137 - 266 + 142) * n) << (0x1D ^ 0x5) | (int)((value >> (0x4E ^ 0x5E) & 111 + 76 - 129 + 197) * n2) << (0xC ^ 0x1C) | (int)((value >> (0x84 ^ 0x8C) & 162 + 99 - 119 + 113) * n3) << (0x63 ^ 0x6B));
            }
        }
        this.rawIntBuffer.put(colorIndex, n5);
    }
    
    public void reset() {
        this.vertexCount = "".length();
        this.field_181677_f = null;
        this.field_181678_g = "".length();
        this.quadSprite = null;
    }
    
    public WorldRenderer normal(final float n, final float n2, final float n3) {
        final int n4 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n4, n);
                this.byteBuffer.putFloat(n4 + (0x1E ^ 0x1A), n2);
                this.byteBuffer.putFloat(n4 + (0xAD ^ 0xA5), n3);
                "".length();
                if (4 == 1) {
                    throw null;
                }
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putInt(n4, (int)n);
                this.byteBuffer.putInt(n4 + (0xAB ^ 0xAF), (int)n2);
                this.byteBuffer.putInt(n4 + (0xB ^ 0x3), (int)n3);
                "".length();
                if (4 < 4) {
                    throw null;
                }
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n4, (short)((int)n * (25626 + 10416 - 10569 + 7294) & 11942 + 37342 + 12488 + 3763));
                this.byteBuffer.putShort(n4 + "  ".length(), (short)((int)n2 * (317 + 15122 - 2285 + 19613) & 848 + 49224 - 28093 + 43556));
                this.byteBuffer.putShort(n4 + (0x66 ^ 0x62), (short)((int)n3 * (15921 + 16853 - 20893 + 20886) & 40555 + 61306 - 41487 + 5161));
                "".length();
                if (4 < 0) {
                    throw null;
                }
                break;
            }
            case 6:
            case 7: {
                this.byteBuffer.put(n4, (byte)((int)n * (21 + 20 + 84 + 2) & 52 + 46 + 8 + 149));
                this.byteBuffer.put(n4 + " ".length(), (byte)((int)n2 * (68 + 78 - 99 + 80) & 96 + 183 - 130 + 106));
                this.byteBuffer.put(n4 + "  ".length(), (byte)((int)n3 * (97 + 38 - 59 + 51) & 167 + 182 - 292 + 198));
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    public WorldRenderer color(final int n, final int n2, final int n3, final int n4) {
        if (this.needsUpdate) {
            return this;
        }
        final int n5 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n5, n / 255.0f);
                this.byteBuffer.putFloat(n5 + (0xAD ^ 0xA9), n2 / 255.0f);
                this.byteBuffer.putFloat(n5 + (0x9F ^ 0x97), n3 / 255.0f);
                this.byteBuffer.putFloat(n5 + (0x42 ^ 0x4E), n4 / 255.0f);
                "".length();
                if (4 < 0) {
                    throw null;
                }
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putFloat(n5, n);
                this.byteBuffer.putFloat(n5 + (0xAB ^ 0xAF), n2);
                this.byteBuffer.putFloat(n5 + (0x63 ^ 0x6B), n3);
                this.byteBuffer.putFloat(n5 + (0x59 ^ 0x55), n4);
                "".length();
                if (2 == 0) {
                    throw null;
                }
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n5, (short)n);
                this.byteBuffer.putShort(n5 + "  ".length(), (short)n2);
                this.byteBuffer.putShort(n5 + (0x1F ^ 0x1B), (short)n3);
                this.byteBuffer.putShort(n5 + (0x2F ^ 0x29), (short)n4);
                "".length();
                if (2 == 0) {
                    throw null;
                }
                break;
            }
            case 6:
            case 7: {
                if (ByteOrder.nativeOrder() != ByteOrder.LITTLE_ENDIAN) {
                    this.byteBuffer.put(n5, (byte)n4);
                    this.byteBuffer.put(n5 + " ".length(), (byte)n3);
                    this.byteBuffer.put(n5 + "  ".length(), (byte)n2);
                    this.byteBuffer.put(n5 + "   ".length(), (byte)n);
                    break;
                }
                this.byteBuffer.put(n5, (byte)n);
                this.byteBuffer.put(n5 + " ".length(), (byte)n2);
                this.byteBuffer.put(n5 + "  ".length(), (byte)n3);
                this.byteBuffer.put(n5 + "   ".length(), (byte)n4);
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    static {
        I();
        __OBFID = WorldRenderer.I[0x61 ^ 0x64];
    }
    
    public void putColorRGB_F4(final float n, final float n2, final float n3) {
        int i = "".length();
        "".length();
        if (3 == 4) {
            throw null;
        }
        while (i < (0xC6 ^ 0xC2)) {
            this.putColorRGB_F(n, n2, n3, i + " ".length());
            ++i;
        }
    }
    
    public void setTranslation(final double xOffset, final double yOffset, final double zOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }
    
    public void putNormal(final float n, final float n2, final float n3) {
        final int n4 = ((byte)(n * 127.0f) & 194 + 154 - 102 + 9) | ((byte)(n2 * 127.0f) & 102 + 35 - 53 + 171) << (0x41 ^ 0x49) | ((byte)(n3 * 127.0f) & 96 + 15 - 90 + 234) << (0x10 ^ 0x0);
        final int n5 = this.vertexFormat.getNextOffset() >> "  ".length();
        final int n6 = (this.vertexCount - (0x42 ^ 0x46)) * n5 + this.vertexFormat.getNormalOffset() / (0x11 ^ 0x15);
        this.rawIntBuffer.put(n6, n4);
        this.rawIntBuffer.put(n6 + n5, n4);
        this.rawIntBuffer.put(n6 + n5 * "  ".length(), n4);
        this.rawIntBuffer.put(n6 + n5 * "   ".length(), n4);
    }
    
    public WorldRenderer tex(double n, double n2) {
        if (this.quadSprite != null && this.quadSprites != null) {
            n = this.quadSprite.toSingleU((float)n);
            n2 = this.quadSprite.toSingleV((float)n2);
            this.quadSprites[this.vertexCount / (0x30 ^ 0x34)] = this.quadSprite;
        }
        final int n3 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n3, (float)n);
                this.byteBuffer.putFloat(n3 + (0x7F ^ 0x7B), (float)n2);
                "".length();
                if (0 >= 1) {
                    throw null;
                }
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putInt(n3, (int)n);
                this.byteBuffer.putInt(n3 + (0xC ^ 0x8), (int)n2);
                "".length();
                if (2 == -1) {
                    throw null;
                }
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n3, (short)n2);
                this.byteBuffer.putShort(n3 + "  ".length(), (short)n);
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                break;
            }
            case 6:
            case 7: {
                this.byteBuffer.put(n3, (byte)n2);
                this.byteBuffer.put(n3 + " ".length(), (byte)n);
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    public void func_181674_a(final float n, final float n2, final float n3) {
        final int n4 = this.vertexCount / (0x2E ^ 0x2A);
        final float[] array = new float[n4];
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < n4) {
            array[i] = func_181665_a(this.rawFloatBuffer, (float)(n + this.xOffset), (float)(n2 + this.yOffset), (float)(n3 + this.zOffset), this.vertexFormat.func_181719_f(), i * this.vertexFormat.getNextOffset());
            ++i;
        }
        final Integer[] array2 = new Integer[n4];
        int j = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (j < array2.length) {
            array2[j] = j;
            ++j;
        }
        Arrays.sort(array2, new WorldRenderer$1$1(this, array));
        final BitSet set = new BitSet();
        final int nextOffset = this.vertexFormat.getNextOffset();
        final int[] array3 = new int[nextOffset];
        int n5 = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while ((n5 = set.nextClearBit(n5)) < array2.length) {
            final int intValue = array2[n5];
            if (intValue != n5) {
                this.rawIntBuffer.limit(intValue * nextOffset + nextOffset);
                this.rawIntBuffer.position(intValue * nextOffset);
                this.rawIntBuffer.get(array3);
                int k = intValue;
                int n6 = array2[intValue];
                "".length();
                if (2 == 1) {
                    throw null;
                }
                while (k != n5) {
                    this.rawIntBuffer.limit(n6 * nextOffset + nextOffset);
                    this.rawIntBuffer.position(n6 * nextOffset);
                    final IntBuffer slice = this.rawIntBuffer.slice();
                    this.rawIntBuffer.limit(k * nextOffset + nextOffset);
                    this.rawIntBuffer.position(k * nextOffset);
                    this.rawIntBuffer.put(slice);
                    set.set(k);
                    k = n6;
                    n6 = array2[n6];
                }
                this.rawIntBuffer.limit(n5 * nextOffset + nextOffset);
                this.rawIntBuffer.position(n5 * nextOffset);
                this.rawIntBuffer.put(array3);
            }
            set.set(n5);
            ++n5;
        }
        if (this.quadSprites != null) {
            final TextureAtlasSprite[] array4 = new TextureAtlasSprite[this.vertexCount / (0x1D ^ 0x19)];
            final int n7 = this.vertexFormat.func_181719_f() / (0x2C ^ 0x28) * (0xBE ^ 0xBA);
            int l = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
            while (l < array2.length) {
                array4[l] = this.quadSprites[array2[l]];
                ++l;
            }
            System.arraycopy(array4, "".length(), this.quadSprites, "".length(), array4.length);
        }
    }
    
    public VertexFormat getVertexFormat() {
        return this.vertexFormat;
    }
    
    private static void I() {
        (I = new String[0xBB ^ 0xBD])["".length()] = I("%/\"!\u0015\u000fj3*P\f8(2P)?!#\u0015\u0019\b2,\u001c\u000f/5e\u0012\u001e,! \u0002Qj\b)\u0014K9.?\u0015K", "kJGEp");
        WorldRenderer.I[" ".length()] = I("n\t!\u001f\u001c=Gx\u0005\u001c9K+\u0002\u0003+K", "NkXky");
        WorldRenderer.I["  ".length()] = I("j6;>\u00179z", "JTBJr");
        WorldRenderer.I["   ".length()] = I("\u0016\u0007*\u0015\u00163\u0012x\u0012\u0002>\u0007<\u0019\u00190J", "WkXpw");
        WorldRenderer.I[0x9 ^ 0xD] = I("\"$\u0001y\u0005\u0019\"\u0019=\u000e\u0002,T", "lKuYg");
        WorldRenderer.I[0x65 ^ 0x60] = I("6\u000b/VUEw@_QG", "uGpfe");
    }
    
    public int getDrawMode() {
        return this.drawMode;
    }
    
    public WorldRenderer lightmap(final int n, final int n2) {
        final int n3 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
        switch (WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
            case 1: {
                this.byteBuffer.putFloat(n3, n);
                this.byteBuffer.putFloat(n3 + (0x3 ^ 0x7), n2);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 2:
            case 3: {
                this.byteBuffer.putInt(n3, n);
                this.byteBuffer.putInt(n3 + (0x4C ^ 0x48), n2);
                "".length();
                if (0 >= 4) {
                    throw null;
                }
                break;
            }
            case 4:
            case 5: {
                this.byteBuffer.putShort(n3, (short)n2);
                this.byteBuffer.putShort(n3 + "  ".length(), (short)n);
                "".length();
                if (2 == 4) {
                    throw null;
                }
                break;
            }
            case 6:
            case 7: {
                this.byteBuffer.put(n3, (byte)n2);
                this.byteBuffer.put(n3 + " ".length(), (byte)n);
                break;
            }
        }
        this.func_181667_k();
        return this;
    }
    
    private void func_181667_k() {
        this.field_181678_g += " ".length();
        this.field_181678_g %= this.vertexFormat.getElementCount();
        this.field_181677_f = this.vertexFormat.getElement(this.field_181678_g);
        if (this.field_181677_f.getUsage() == VertexFormatElement.EnumUsage.PADDING) {
            this.func_181667_k();
        }
    }
    
    static final class WorldRenderer$2
    {
        static final int[] field_181661_a;
        private static final String[] I;
        private static final String __OBFID;
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (3 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            __OBFID = WorldRenderer$2.I["".length()];
            field_181661_a = new int[VertexFormatElement.EnumType.values().length];
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.FLOAT.ordinal()] = " ".length();
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.UINT.ordinal()] = "  ".length();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.INT.ordinal()] = "   ".length();
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.USHORT.ordinal()] = (0x64 ^ 0x60);
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.SHORT.ordinal()] = (0x5C ^ 0x59);
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.UBYTE.ordinal()] = (0xB6 ^ 0xB0);
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                WorldRenderer$2.field_181661_a[VertexFormatElement.EnumType.BYTE.ordinal()] = (0x94 ^ 0x93);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I(".?\u001bxe]Cv}cT", "msDHU");
        }
    }
    
    public class State
    {
        private static final String[] I;
        private final VertexFormat stateVertexFormat;
        private TextureAtlasSprite[] stateQuadSprites;
        private final int[] stateRawBuffer;
        private static final String __OBFID;
        final WorldRenderer this$0;
        
        public State(final WorldRenderer this$0, final int[] stateRawBuffer, final VertexFormat stateVertexFormat, final TextureAtlasSprite[] stateQuadSprites) {
            this.this$0 = this$0;
            this.stateRawBuffer = stateRawBuffer;
            this.stateVertexFormat = stateVertexFormat;
            this.stateQuadSprites = stateQuadSprites;
        }
        
        static TextureAtlasSprite[] access$0(final State state) {
            return state.stateQuadSprites;
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public int[] getRawBuffer() {
            return this.stateRawBuffer;
        }
        
        public VertexFormat getVertexFormat() {
            return this.stateVertexFormat;
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("\u000b\u0002\u0012r~x~\u007fwxp", "HNMBN");
        }
        
        static {
            I();
            __OBFID = State.I["".length()];
        }
        
        public State(final WorldRenderer this$0, final int[] stateRawBuffer, final VertexFormat stateVertexFormat) {
            this.this$0 = this$0;
            this.stateRawBuffer = stateRawBuffer;
            this.stateVertexFormat = stateVertexFormat;
        }
        
        public int getVertexCount() {
            return this.stateRawBuffer.length / this.stateVertexFormat.func_181719_f();
        }
    }
}
