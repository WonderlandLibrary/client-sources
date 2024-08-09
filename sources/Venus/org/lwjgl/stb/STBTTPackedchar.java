/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType(value="struct stbtt_packedchar")
public class STBTTPackedchar
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int X0;
    public static final int Y0;
    public static final int X1;
    public static final int Y1;
    public static final int XOFF;
    public static final int YOFF;
    public static final int XADVANCE;
    public static final int XOFF2;
    public static final int YOFF2;

    public STBTTPackedchar(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBTTPackedchar.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="unsigned short")
    public short x0() {
        return STBTTPackedchar.nx0(this.address());
    }

    @NativeType(value="unsigned short")
    public short y0() {
        return STBTTPackedchar.ny0(this.address());
    }

    @NativeType(value="unsigned short")
    public short x1() {
        return STBTTPackedchar.nx1(this.address());
    }

    @NativeType(value="unsigned short")
    public short y1() {
        return STBTTPackedchar.ny1(this.address());
    }

    public float xoff() {
        return STBTTPackedchar.nxoff(this.address());
    }

    public float yoff() {
        return STBTTPackedchar.nyoff(this.address());
    }

    public float xadvance() {
        return STBTTPackedchar.nxadvance(this.address());
    }

    public float xoff2() {
        return STBTTPackedchar.nxoff2(this.address());
    }

    public float yoff2() {
        return STBTTPackedchar.nyoff2(this.address());
    }

    public STBTTPackedchar x0(@NativeType(value="unsigned short") short s) {
        STBTTPackedchar.nx0(this.address(), s);
        return this;
    }

    public STBTTPackedchar y0(@NativeType(value="unsigned short") short s) {
        STBTTPackedchar.ny0(this.address(), s);
        return this;
    }

    public STBTTPackedchar x1(@NativeType(value="unsigned short") short s) {
        STBTTPackedchar.nx1(this.address(), s);
        return this;
    }

    public STBTTPackedchar y1(@NativeType(value="unsigned short") short s) {
        STBTTPackedchar.ny1(this.address(), s);
        return this;
    }

    public STBTTPackedchar xoff(float f) {
        STBTTPackedchar.nxoff(this.address(), f);
        return this;
    }

    public STBTTPackedchar yoff(float f) {
        STBTTPackedchar.nyoff(this.address(), f);
        return this;
    }

    public STBTTPackedchar xadvance(float f) {
        STBTTPackedchar.nxadvance(this.address(), f);
        return this;
    }

    public STBTTPackedchar xoff2(float f) {
        STBTTPackedchar.nxoff2(this.address(), f);
        return this;
    }

    public STBTTPackedchar yoff2(float f) {
        STBTTPackedchar.nyoff2(this.address(), f);
        return this;
    }

    public STBTTPackedchar set(short s, short s2, short s3, short s4, float f, float f2, float f3, float f4, float f5) {
        this.x0(s);
        this.y0(s2);
        this.x1(s3);
        this.y1(s4);
        this.xoff(f);
        this.yoff(f2);
        this.xadvance(f3);
        this.xoff2(f4);
        this.yoff2(f5);
        return this;
    }

    public STBTTPackedchar set(STBTTPackedchar sTBTTPackedchar) {
        MemoryUtil.memCopy(sTBTTPackedchar.address(), this.address(), SIZEOF);
        return this;
    }

    public static STBTTPackedchar malloc() {
        return STBTTPackedchar.wrap(STBTTPackedchar.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBTTPackedchar calloc() {
        return STBTTPackedchar.wrap(STBTTPackedchar.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBTTPackedchar create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBTTPackedchar.wrap(STBTTPackedchar.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBTTPackedchar create(long l) {
        return STBTTPackedchar.wrap(STBTTPackedchar.class, l);
    }

    @Nullable
    public static STBTTPackedchar createSafe(long l) {
        return l == 0L ? null : STBTTPackedchar.wrap(STBTTPackedchar.class, l);
    }

    public static Buffer malloc(int n) {
        return STBTTPackedchar.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBTTPackedchar.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBTTPackedchar.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBTTPackedchar.__create(n, SIZEOF);
        return STBTTPackedchar.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBTTPackedchar.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBTTPackedchar.wrap(Buffer.class, l, n);
    }

    public static STBTTPackedchar mallocStack() {
        return STBTTPackedchar.mallocStack(MemoryStack.stackGet());
    }

    public static STBTTPackedchar callocStack() {
        return STBTTPackedchar.callocStack(MemoryStack.stackGet());
    }

    public static STBTTPackedchar mallocStack(MemoryStack memoryStack) {
        return STBTTPackedchar.wrap(STBTTPackedchar.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBTTPackedchar callocStack(MemoryStack memoryStack) {
        return STBTTPackedchar.wrap(STBTTPackedchar.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBTTPackedchar.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBTTPackedchar.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBTTPackedchar.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBTTPackedchar.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static short nx0(long l) {
        return UNSAFE.getShort(null, l + (long)X0);
    }

    public static short ny0(long l) {
        return UNSAFE.getShort(null, l + (long)Y0);
    }

    public static short nx1(long l) {
        return UNSAFE.getShort(null, l + (long)X1);
    }

    public static short ny1(long l) {
        return UNSAFE.getShort(null, l + (long)Y1);
    }

    public static float nxoff(long l) {
        return UNSAFE.getFloat(null, l + (long)XOFF);
    }

    public static float nyoff(long l) {
        return UNSAFE.getFloat(null, l + (long)YOFF);
    }

    public static float nxadvance(long l) {
        return UNSAFE.getFloat(null, l + (long)XADVANCE);
    }

    public static float nxoff2(long l) {
        return UNSAFE.getFloat(null, l + (long)XOFF2);
    }

    public static float nyoff2(long l) {
        return UNSAFE.getFloat(null, l + (long)YOFF2);
    }

    public static void nx0(long l, short s) {
        UNSAFE.putShort(null, l + (long)X0, s);
    }

    public static void ny0(long l, short s) {
        UNSAFE.putShort(null, l + (long)Y0, s);
    }

    public static void nx1(long l, short s) {
        UNSAFE.putShort(null, l + (long)X1, s);
    }

    public static void ny1(long l, short s) {
        UNSAFE.putShort(null, l + (long)Y1, s);
    }

    public static void nxoff(long l, float f) {
        UNSAFE.putFloat(null, l + (long)XOFF, f);
    }

    public static void nyoff(long l, float f) {
        UNSAFE.putFloat(null, l + (long)YOFF, f);
    }

    public static void nxadvance(long l, float f) {
        UNSAFE.putFloat(null, l + (long)XADVANCE, f);
    }

    public static void nxoff2(long l, float f) {
        UNSAFE.putFloat(null, l + (long)XOFF2, f);
    }

    public static void nyoff2(long l, float f) {
        UNSAFE.putFloat(null, l + (long)YOFF2, f);
    }

    static {
        Struct.Layout layout = STBTTPackedchar.__struct(STBTTPackedchar.__member(2), STBTTPackedchar.__member(2), STBTTPackedchar.__member(2), STBTTPackedchar.__member(2), STBTTPackedchar.__member(4), STBTTPackedchar.__member(4), STBTTPackedchar.__member(4), STBTTPackedchar.__member(4), STBTTPackedchar.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        X0 = layout.offsetof(0);
        Y0 = layout.offsetof(1);
        X1 = layout.offsetof(2);
        Y1 = layout.offsetof(3);
        XOFF = layout.offsetof(4);
        YOFF = layout.offsetof(5);
        XADVANCE = layout.offsetof(6);
        XOFF2 = layout.offsetof(7);
        YOFF2 = layout.offsetof(8);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBTTPackedchar, Buffer>
    implements NativeResource {
        private static final STBTTPackedchar ELEMENT_FACTORY = STBTTPackedchar.create(-1L);

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
        protected STBTTPackedchar getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="unsigned short")
        public short x0() {
            return STBTTPackedchar.nx0(this.address());
        }

        @NativeType(value="unsigned short")
        public short y0() {
            return STBTTPackedchar.ny0(this.address());
        }

        @NativeType(value="unsigned short")
        public short x1() {
            return STBTTPackedchar.nx1(this.address());
        }

        @NativeType(value="unsigned short")
        public short y1() {
            return STBTTPackedchar.ny1(this.address());
        }

        public float xoff() {
            return STBTTPackedchar.nxoff(this.address());
        }

        public float yoff() {
            return STBTTPackedchar.nyoff(this.address());
        }

        public float xadvance() {
            return STBTTPackedchar.nxadvance(this.address());
        }

        public float xoff2() {
            return STBTTPackedchar.nxoff2(this.address());
        }

        public float yoff2() {
            return STBTTPackedchar.nyoff2(this.address());
        }

        public Buffer x0(@NativeType(value="unsigned short") short s) {
            STBTTPackedchar.nx0(this.address(), s);
            return this;
        }

        public Buffer y0(@NativeType(value="unsigned short") short s) {
            STBTTPackedchar.ny0(this.address(), s);
            return this;
        }

        public Buffer x1(@NativeType(value="unsigned short") short s) {
            STBTTPackedchar.nx1(this.address(), s);
            return this;
        }

        public Buffer y1(@NativeType(value="unsigned short") short s) {
            STBTTPackedchar.ny1(this.address(), s);
            return this;
        }

        public Buffer xoff(float f) {
            STBTTPackedchar.nxoff(this.address(), f);
            return this;
        }

        public Buffer yoff(float f) {
            STBTTPackedchar.nyoff(this.address(), f);
            return this;
        }

        public Buffer xadvance(float f) {
            STBTTPackedchar.nxadvance(this.address(), f);
            return this;
        }

        public Buffer xoff2(float f) {
            STBTTPackedchar.nxoff2(this.address(), f);
            return this;
        }

        public Buffer yoff2(float f) {
            STBTTPackedchar.nyoff2(this.address(), f);
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

