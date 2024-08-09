/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType(value="struct stbtt__bitmap")
public class STBTTBitmap
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int W;
    public static final int H;
    public static final int STRIDE;
    public static final int PIXELS;

    public STBTTBitmap(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBTTBitmap.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    public int w() {
        return STBTTBitmap.nw(this.address());
    }

    public int h() {
        return STBTTBitmap.nh(this.address());
    }

    public int stride() {
        return STBTTBitmap.nstride(this.address());
    }

    @NativeType(value="unsigned char *")
    public ByteBuffer pixels(int n) {
        return STBTTBitmap.npixels(this.address(), n);
    }

    public STBTTBitmap w(int n) {
        STBTTBitmap.nw(this.address(), n);
        return this;
    }

    public STBTTBitmap h(int n) {
        STBTTBitmap.nh(this.address(), n);
        return this;
    }

    public STBTTBitmap stride(int n) {
        STBTTBitmap.nstride(this.address(), n);
        return this;
    }

    public STBTTBitmap pixels(@NativeType(value="unsigned char *") ByteBuffer byteBuffer) {
        STBTTBitmap.npixels(this.address(), byteBuffer);
        return this;
    }

    public STBTTBitmap set(int n, int n2, int n3, ByteBuffer byteBuffer) {
        this.w(n);
        this.h(n2);
        this.stride(n3);
        this.pixels(byteBuffer);
        return this;
    }

    public STBTTBitmap set(STBTTBitmap sTBTTBitmap) {
        MemoryUtil.memCopy(sTBTTBitmap.address(), this.address(), SIZEOF);
        return this;
    }

    public static STBTTBitmap malloc() {
        return STBTTBitmap.wrap(STBTTBitmap.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBTTBitmap calloc() {
        return STBTTBitmap.wrap(STBTTBitmap.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBTTBitmap create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBTTBitmap.wrap(STBTTBitmap.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBTTBitmap create(long l) {
        return STBTTBitmap.wrap(STBTTBitmap.class, l);
    }

    @Nullable
    public static STBTTBitmap createSafe(long l) {
        return l == 0L ? null : STBTTBitmap.wrap(STBTTBitmap.class, l);
    }

    public static Buffer malloc(int n) {
        return STBTTBitmap.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBTTBitmap.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBTTBitmap.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBTTBitmap.__create(n, SIZEOF);
        return STBTTBitmap.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBTTBitmap.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBTTBitmap.wrap(Buffer.class, l, n);
    }

    public static STBTTBitmap mallocStack() {
        return STBTTBitmap.mallocStack(MemoryStack.stackGet());
    }

    public static STBTTBitmap callocStack() {
        return STBTTBitmap.callocStack(MemoryStack.stackGet());
    }

    public static STBTTBitmap mallocStack(MemoryStack memoryStack) {
        return STBTTBitmap.wrap(STBTTBitmap.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBTTBitmap callocStack(MemoryStack memoryStack) {
        return STBTTBitmap.wrap(STBTTBitmap.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBTTBitmap.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBTTBitmap.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBTTBitmap.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBTTBitmap.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int nw(long l) {
        return UNSAFE.getInt(null, l + (long)W);
    }

    public static int nh(long l) {
        return UNSAFE.getInt(null, l + (long)H);
    }

    public static int nstride(long l) {
        return UNSAFE.getInt(null, l + (long)STRIDE);
    }

    public static ByteBuffer npixels(long l, int n) {
        return MemoryUtil.memByteBuffer(MemoryUtil.memGetAddress(l + (long)PIXELS), n);
    }

    public static void nw(long l, int n) {
        UNSAFE.putInt(null, l + (long)W, n);
    }

    public static void nh(long l, int n) {
        UNSAFE.putInt(null, l + (long)H, n);
    }

    public static void nstride(long l, int n) {
        UNSAFE.putInt(null, l + (long)STRIDE, n);
    }

    public static void npixels(long l, ByteBuffer byteBuffer) {
        MemoryUtil.memPutAddress(l + (long)PIXELS, MemoryUtil.memAddress(byteBuffer));
    }

    public static void validate(long l) {
        Checks.check(MemoryUtil.memGetAddress(l + (long)PIXELS));
    }

    public static void validate(long l, int n) {
        for (int i = 0; i < n; ++i) {
            STBTTBitmap.validate(l + Integer.toUnsignedLong(i) * (long)SIZEOF);
        }
    }

    static {
        Struct.Layout layout = STBTTBitmap.__struct(STBTTBitmap.__member(4), STBTTBitmap.__member(4), STBTTBitmap.__member(4), STBTTBitmap.__member(POINTER_SIZE));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        W = layout.offsetof(0);
        H = layout.offsetof(1);
        STRIDE = layout.offsetof(2);
        PIXELS = layout.offsetof(3);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBTTBitmap, Buffer>
    implements NativeResource {
        private static final STBTTBitmap ELEMENT_FACTORY = STBTTBitmap.create(-1L);

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
        protected STBTTBitmap getElementFactory() {
            return ELEMENT_FACTORY;
        }

        public int w() {
            return STBTTBitmap.nw(this.address());
        }

        public int h() {
            return STBTTBitmap.nh(this.address());
        }

        public int stride() {
            return STBTTBitmap.nstride(this.address());
        }

        @NativeType(value="unsigned char *")
        public ByteBuffer pixels(int n) {
            return STBTTBitmap.npixels(this.address(), n);
        }

        public Buffer w(int n) {
            STBTTBitmap.nw(this.address(), n);
            return this;
        }

        public Buffer h(int n) {
            STBTTBitmap.nh(this.address(), n);
            return this;
        }

        public Buffer stride(int n) {
            STBTTBitmap.nstride(this.address(), n);
            return this;
        }

        public Buffer pixels(@NativeType(value="unsigned char *") ByteBuffer byteBuffer) {
            STBTTBitmap.npixels(this.address(), byteBuffer);
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

