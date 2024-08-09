/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.windows;

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

public class RECT
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int LEFT;
    public static final int TOP;
    public static final int RIGHT;
    public static final int BOTTOM;

    public RECT(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), RECT.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="LONG")
    public int left() {
        return RECT.nleft(this.address());
    }

    @NativeType(value="LONG")
    public int top() {
        return RECT.ntop(this.address());
    }

    @NativeType(value="LONG")
    public int right() {
        return RECT.nright(this.address());
    }

    @NativeType(value="LONG")
    public int bottom() {
        return RECT.nbottom(this.address());
    }

    public RECT left(@NativeType(value="LONG") int n) {
        RECT.nleft(this.address(), n);
        return this;
    }

    public RECT top(@NativeType(value="LONG") int n) {
        RECT.ntop(this.address(), n);
        return this;
    }

    public RECT right(@NativeType(value="LONG") int n) {
        RECT.nright(this.address(), n);
        return this;
    }

    public RECT bottom(@NativeType(value="LONG") int n) {
        RECT.nbottom(this.address(), n);
        return this;
    }

    public RECT set(int n, int n2, int n3, int n4) {
        this.left(n);
        this.top(n2);
        this.right(n3);
        this.bottom(n4);
        return this;
    }

    public RECT set(RECT rECT) {
        MemoryUtil.memCopy(rECT.address(), this.address(), SIZEOF);
        return this;
    }

    public static RECT malloc() {
        return RECT.wrap(RECT.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static RECT calloc() {
        return RECT.wrap(RECT.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static RECT create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return RECT.wrap(RECT.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static RECT create(long l) {
        return RECT.wrap(RECT.class, l);
    }

    @Nullable
    public static RECT createSafe(long l) {
        return l == 0L ? null : RECT.wrap(RECT.class, l);
    }

    public static Buffer malloc(int n) {
        return RECT.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(RECT.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return RECT.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = RECT.__create(n, SIZEOF);
        return RECT.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return RECT.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : RECT.wrap(Buffer.class, l, n);
    }

    public static RECT mallocStack() {
        return RECT.mallocStack(MemoryStack.stackGet());
    }

    public static RECT callocStack() {
        return RECT.callocStack(MemoryStack.stackGet());
    }

    public static RECT mallocStack(MemoryStack memoryStack) {
        return RECT.wrap(RECT.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static RECT callocStack(MemoryStack memoryStack) {
        return RECT.wrap(RECT.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return RECT.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return RECT.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return RECT.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return RECT.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int nleft(long l) {
        return UNSAFE.getInt(null, l + (long)LEFT);
    }

    public static int ntop(long l) {
        return UNSAFE.getInt(null, l + (long)TOP);
    }

    public static int nright(long l) {
        return UNSAFE.getInt(null, l + (long)RIGHT);
    }

    public static int nbottom(long l) {
        return UNSAFE.getInt(null, l + (long)BOTTOM);
    }

    public static void nleft(long l, int n) {
        UNSAFE.putInt(null, l + (long)LEFT, n);
    }

    public static void ntop(long l, int n) {
        UNSAFE.putInt(null, l + (long)TOP, n);
    }

    public static void nright(long l, int n) {
        UNSAFE.putInt(null, l + (long)RIGHT, n);
    }

    public static void nbottom(long l, int n) {
        UNSAFE.putInt(null, l + (long)BOTTOM, n);
    }

    static {
        Struct.Layout layout = RECT.__struct(RECT.__member(4), RECT.__member(4), RECT.__member(4), RECT.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        LEFT = layout.offsetof(0);
        TOP = layout.offsetof(1);
        RIGHT = layout.offsetof(2);
        BOTTOM = layout.offsetof(3);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<RECT, Buffer>
    implements NativeResource {
        private static final RECT ELEMENT_FACTORY = RECT.create(-1L);

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
        protected RECT getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="LONG")
        public int left() {
            return RECT.nleft(this.address());
        }

        @NativeType(value="LONG")
        public int top() {
            return RECT.ntop(this.address());
        }

        @NativeType(value="LONG")
        public int right() {
            return RECT.nright(this.address());
        }

        @NativeType(value="LONG")
        public int bottom() {
            return RECT.nbottom(this.address());
        }

        public Buffer left(@NativeType(value="LONG") int n) {
            RECT.nleft(this.address(), n);
            return this;
        }

        public Buffer top(@NativeType(value="LONG") int n) {
            RECT.ntop(this.address(), n);
            return this;
        }

        public Buffer right(@NativeType(value="LONG") int n) {
            RECT.nright(this.address(), n);
            return this;
        }

        public Buffer bottom(@NativeType(value="LONG") int n) {
            RECT.nbottom(this.address(), n);
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

