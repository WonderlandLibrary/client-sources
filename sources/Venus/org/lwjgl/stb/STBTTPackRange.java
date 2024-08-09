/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType(value="struct stbtt_pack_range")
public class STBTTPackRange
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int FONT_SIZE;
    public static final int FIRST_UNICODE_CODEPOINT_IN_RANGE;
    public static final int ARRAY_OF_UNICODE_CODEPOINTS;
    public static final int NUM_CHARS;
    public static final int CHARDATA_FOR_RANGE;
    public static final int H_OVERSAMPLE;
    public static final int V_OVERSAMPLE;

    public STBTTPackRange(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBTTPackRange.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    public float font_size() {
        return STBTTPackRange.nfont_size(this.address());
    }

    public int first_unicode_codepoint_in_range() {
        return STBTTPackRange.nfirst_unicode_codepoint_in_range(this.address());
    }

    @Nullable
    @NativeType(value="int *")
    public IntBuffer array_of_unicode_codepoints() {
        return STBTTPackRange.narray_of_unicode_codepoints(this.address());
    }

    public int num_chars() {
        return STBTTPackRange.nnum_chars(this.address());
    }

    @NativeType(value="stbtt_packedchar *")
    public STBTTPackedchar.Buffer chardata_for_range() {
        return STBTTPackRange.nchardata_for_range(this.address());
    }

    @NativeType(value="unsigned char")
    public byte h_oversample() {
        return STBTTPackRange.nh_oversample(this.address());
    }

    @NativeType(value="unsigned char")
    public byte v_oversample() {
        return STBTTPackRange.nv_oversample(this.address());
    }

    public STBTTPackRange font_size(float f) {
        STBTTPackRange.nfont_size(this.address(), f);
        return this;
    }

    public STBTTPackRange first_unicode_codepoint_in_range(int n) {
        STBTTPackRange.nfirst_unicode_codepoint_in_range(this.address(), n);
        return this;
    }

    public STBTTPackRange array_of_unicode_codepoints(@Nullable @NativeType(value="int *") IntBuffer intBuffer) {
        STBTTPackRange.narray_of_unicode_codepoints(this.address(), intBuffer);
        return this;
    }

    public STBTTPackRange num_chars(int n) {
        STBTTPackRange.nnum_chars(this.address(), n);
        return this;
    }

    public STBTTPackRange chardata_for_range(@NativeType(value="stbtt_packedchar *") STBTTPackedchar.Buffer buffer) {
        STBTTPackRange.nchardata_for_range(this.address(), buffer);
        return this;
    }

    public STBTTPackRange h_oversample(@NativeType(value="unsigned char") byte by) {
        STBTTPackRange.nh_oversample(this.address(), by);
        return this;
    }

    public STBTTPackRange v_oversample(@NativeType(value="unsigned char") byte by) {
        STBTTPackRange.nv_oversample(this.address(), by);
        return this;
    }

    public STBTTPackRange set(float f, int n, @Nullable IntBuffer intBuffer, int n2, STBTTPackedchar.Buffer buffer, byte by, byte by2) {
        this.font_size(f);
        this.first_unicode_codepoint_in_range(n);
        this.array_of_unicode_codepoints(intBuffer);
        this.num_chars(n2);
        this.chardata_for_range(buffer);
        this.h_oversample(by);
        this.v_oversample(by2);
        return this;
    }

    public STBTTPackRange set(STBTTPackRange sTBTTPackRange) {
        MemoryUtil.memCopy(sTBTTPackRange.address(), this.address(), SIZEOF);
        return this;
    }

    public static STBTTPackRange malloc() {
        return STBTTPackRange.wrap(STBTTPackRange.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBTTPackRange calloc() {
        return STBTTPackRange.wrap(STBTTPackRange.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBTTPackRange create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBTTPackRange.wrap(STBTTPackRange.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBTTPackRange create(long l) {
        return STBTTPackRange.wrap(STBTTPackRange.class, l);
    }

    @Nullable
    public static STBTTPackRange createSafe(long l) {
        return l == 0L ? null : STBTTPackRange.wrap(STBTTPackRange.class, l);
    }

    public static Buffer malloc(int n) {
        return STBTTPackRange.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBTTPackRange.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBTTPackRange.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBTTPackRange.__create(n, SIZEOF);
        return STBTTPackRange.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBTTPackRange.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBTTPackRange.wrap(Buffer.class, l, n);
    }

    public static STBTTPackRange mallocStack() {
        return STBTTPackRange.mallocStack(MemoryStack.stackGet());
    }

    public static STBTTPackRange callocStack() {
        return STBTTPackRange.callocStack(MemoryStack.stackGet());
    }

    public static STBTTPackRange mallocStack(MemoryStack memoryStack) {
        return STBTTPackRange.wrap(STBTTPackRange.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBTTPackRange callocStack(MemoryStack memoryStack) {
        return STBTTPackRange.wrap(STBTTPackRange.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBTTPackRange.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBTTPackRange.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBTTPackRange.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBTTPackRange.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static float nfont_size(long l) {
        return UNSAFE.getFloat(null, l + (long)FONT_SIZE);
    }

    public static int nfirst_unicode_codepoint_in_range(long l) {
        return UNSAFE.getInt(null, l + (long)FIRST_UNICODE_CODEPOINT_IN_RANGE);
    }

    @Nullable
    public static IntBuffer narray_of_unicode_codepoints(long l) {
        return MemoryUtil.memIntBufferSafe(MemoryUtil.memGetAddress(l + (long)ARRAY_OF_UNICODE_CODEPOINTS), STBTTPackRange.nnum_chars(l));
    }

    public static int nnum_chars(long l) {
        return UNSAFE.getInt(null, l + (long)NUM_CHARS);
    }

    public static STBTTPackedchar.Buffer nchardata_for_range(long l) {
        return STBTTPackedchar.create(MemoryUtil.memGetAddress(l + (long)CHARDATA_FOR_RANGE), STBTTPackRange.nnum_chars(l));
    }

    public static byte nh_oversample(long l) {
        return UNSAFE.getByte(null, l + (long)H_OVERSAMPLE);
    }

    public static byte nv_oversample(long l) {
        return UNSAFE.getByte(null, l + (long)V_OVERSAMPLE);
    }

    public static void nfont_size(long l, float f) {
        UNSAFE.putFloat(null, l + (long)FONT_SIZE, f);
    }

    public static void nfirst_unicode_codepoint_in_range(long l, int n) {
        UNSAFE.putInt(null, l + (long)FIRST_UNICODE_CODEPOINT_IN_RANGE, n);
    }

    public static void narray_of_unicode_codepoints(long l, @Nullable IntBuffer intBuffer) {
        MemoryUtil.memPutAddress(l + (long)ARRAY_OF_UNICODE_CODEPOINTS, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void nnum_chars(long l, int n) {
        UNSAFE.putInt(null, l + (long)NUM_CHARS, n);
    }

    public static void nchardata_for_range(long l, STBTTPackedchar.Buffer buffer) {
        MemoryUtil.memPutAddress(l + (long)CHARDATA_FOR_RANGE, buffer.address());
    }

    public static void nh_oversample(long l, byte by) {
        UNSAFE.putByte(null, l + (long)H_OVERSAMPLE, by);
    }

    public static void nv_oversample(long l, byte by) {
        UNSAFE.putByte(null, l + (long)V_OVERSAMPLE, by);
    }

    public static void validate(long l) {
        Checks.check(MemoryUtil.memGetAddress(l + (long)CHARDATA_FOR_RANGE));
    }

    public static void validate(long l, int n) {
        for (int i = 0; i < n; ++i) {
            STBTTPackRange.validate(l + Integer.toUnsignedLong(i) * (long)SIZEOF);
        }
    }

    static {
        Struct.Layout layout = STBTTPackRange.__struct(STBTTPackRange.__member(4), STBTTPackRange.__member(4), STBTTPackRange.__member(POINTER_SIZE), STBTTPackRange.__member(4), STBTTPackRange.__member(POINTER_SIZE), STBTTPackRange.__member(1), STBTTPackRange.__member(1));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        FONT_SIZE = layout.offsetof(0);
        FIRST_UNICODE_CODEPOINT_IN_RANGE = layout.offsetof(1);
        ARRAY_OF_UNICODE_CODEPOINTS = layout.offsetof(2);
        NUM_CHARS = layout.offsetof(3);
        CHARDATA_FOR_RANGE = layout.offsetof(4);
        H_OVERSAMPLE = layout.offsetof(5);
        V_OVERSAMPLE = layout.offsetof(6);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBTTPackRange, Buffer>
    implements NativeResource {
        private static final STBTTPackRange ELEMENT_FACTORY = STBTTPackRange.create(-1L);

        public Buffer(ByteBuffer byteBuffer) {
            super(byteBuffer, byteBuffer.remaining() / SIZEOF);
        }

        public Buffer(long l, int n) {
            super(l, null, -1, 0, n, n);
        }

        Buffer(long l, @Nullable ByteBuffer byteBuffer, int n, int n2, int n3, int n4) {
            super(l, byteBuffer, n, n2, n3, n4);
        }

        @Override
        protected Buffer self() {
            return this;
        }

        @Override
        protected STBTTPackRange getElementFactory() {
            return ELEMENT_FACTORY;
        }

        public float font_size() {
            return STBTTPackRange.nfont_size(this.address());
        }

        public int first_unicode_codepoint_in_range() {
            return STBTTPackRange.nfirst_unicode_codepoint_in_range(this.address());
        }

        @Nullable
        @NativeType(value="int *")
        public IntBuffer array_of_unicode_codepoints() {
            return STBTTPackRange.narray_of_unicode_codepoints(this.address());
        }

        public int num_chars() {
            return STBTTPackRange.nnum_chars(this.address());
        }

        @NativeType(value="stbtt_packedchar *")
        public STBTTPackedchar.Buffer chardata_for_range() {
            return STBTTPackRange.nchardata_for_range(this.address());
        }

        @NativeType(value="unsigned char")
        public byte h_oversample() {
            return STBTTPackRange.nh_oversample(this.address());
        }

        @NativeType(value="unsigned char")
        public byte v_oversample() {
            return STBTTPackRange.nv_oversample(this.address());
        }

        public Buffer font_size(float f) {
            STBTTPackRange.nfont_size(this.address(), f);
            return this;
        }

        public Buffer first_unicode_codepoint_in_range(int n) {
            STBTTPackRange.nfirst_unicode_codepoint_in_range(this.address(), n);
            return this;
        }

        public Buffer array_of_unicode_codepoints(@Nullable @NativeType(value="int *") IntBuffer intBuffer) {
            STBTTPackRange.narray_of_unicode_codepoints(this.address(), intBuffer);
            return this;
        }

        public Buffer num_chars(int n) {
            STBTTPackRange.nnum_chars(this.address(), n);
            return this;
        }

        public Buffer chardata_for_range(@NativeType(value="stbtt_packedchar *") STBTTPackedchar.Buffer buffer) {
            STBTTPackRange.nchardata_for_range(this.address(), buffer);
            return this;
        }

        public Buffer h_oversample(@NativeType(value="unsigned char") byte by) {
            STBTTPackRange.nh_oversample(this.address(), by);
            return this;
        }

        public Buffer v_oversample(@NativeType(value="unsigned char") byte by) {
            STBTTPackRange.nv_oversample(this.address(), by);
            return this;
        }

        @Override
        protected Struct getElementFactory() {
            return this.getElementFactory();
        }

        @Override
        protected CustomBuffer self() {
            return this.self();
        }
    }
}

