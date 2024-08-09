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

@NativeType(value="struct stbtt_bakedchar")
public class STBTTBakedChar
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

    public STBTTBakedChar(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBTTBakedChar.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="unsigned short")
    public short x0() {
        return STBTTBakedChar.nx0(this.address());
    }

    @NativeType(value="unsigned short")
    public short y0() {
        return STBTTBakedChar.ny0(this.address());
    }

    @NativeType(value="unsigned short")
    public short x1() {
        return STBTTBakedChar.nx1(this.address());
    }

    @NativeType(value="unsigned short")
    public short y1() {
        return STBTTBakedChar.ny1(this.address());
    }

    public float xoff() {
        return STBTTBakedChar.nxoff(this.address());
    }

    public float yoff() {
        return STBTTBakedChar.nyoff(this.address());
    }

    public float xadvance() {
        return STBTTBakedChar.nxadvance(this.address());
    }

    public static STBTTBakedChar malloc() {
        return STBTTBakedChar.wrap(STBTTBakedChar.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBTTBakedChar calloc() {
        return STBTTBakedChar.wrap(STBTTBakedChar.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBTTBakedChar create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBTTBakedChar.wrap(STBTTBakedChar.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBTTBakedChar create(long l) {
        return STBTTBakedChar.wrap(STBTTBakedChar.class, l);
    }

    @Nullable
    public static STBTTBakedChar createSafe(long l) {
        return l == 0L ? null : STBTTBakedChar.wrap(STBTTBakedChar.class, l);
    }

    public static Buffer malloc(int n) {
        return STBTTBakedChar.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBTTBakedChar.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBTTBakedChar.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBTTBakedChar.__create(n, SIZEOF);
        return STBTTBakedChar.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBTTBakedChar.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBTTBakedChar.wrap(Buffer.class, l, n);
    }

    public static STBTTBakedChar mallocStack() {
        return STBTTBakedChar.mallocStack(MemoryStack.stackGet());
    }

    public static STBTTBakedChar callocStack() {
        return STBTTBakedChar.callocStack(MemoryStack.stackGet());
    }

    public static STBTTBakedChar mallocStack(MemoryStack memoryStack) {
        return STBTTBakedChar.wrap(STBTTBakedChar.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBTTBakedChar callocStack(MemoryStack memoryStack) {
        return STBTTBakedChar.wrap(STBTTBakedChar.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBTTBakedChar.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBTTBakedChar.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBTTBakedChar.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBTTBakedChar.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
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

    static {
        Struct.Layout layout = STBTTBakedChar.__struct(STBTTBakedChar.__member(2), STBTTBakedChar.__member(2), STBTTBakedChar.__member(2), STBTTBakedChar.__member(2), STBTTBakedChar.__member(4), STBTTBakedChar.__member(4), STBTTBakedChar.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        X0 = layout.offsetof(0);
        Y0 = layout.offsetof(1);
        X1 = layout.offsetof(2);
        Y1 = layout.offsetof(3);
        XOFF = layout.offsetof(4);
        YOFF = layout.offsetof(5);
        XADVANCE = layout.offsetof(6);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBTTBakedChar, Buffer>
    implements NativeResource {
        private static final STBTTBakedChar ELEMENT_FACTORY = STBTTBakedChar.create(-1L);

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
        protected STBTTBakedChar getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="unsigned short")
        public short x0() {
            return STBTTBakedChar.nx0(this.address());
        }

        @NativeType(value="unsigned short")
        public short y0() {
            return STBTTBakedChar.ny0(this.address());
        }

        @NativeType(value="unsigned short")
        public short x1() {
            return STBTTBakedChar.nx1(this.address());
        }

        @NativeType(value="unsigned short")
        public short y1() {
            return STBTTBakedChar.ny1(this.address());
        }

        public float xoff() {
            return STBTTBakedChar.nxoff(this.address());
        }

        public float yoff() {
            return STBTTBakedChar.nyoff(this.address());
        }

        public float xadvance() {
            return STBTTBakedChar.nxadvance(this.address());
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

