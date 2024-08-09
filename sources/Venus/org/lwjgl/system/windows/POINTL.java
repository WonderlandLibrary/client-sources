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

public class POINTL
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int X;
    public static final int Y;

    public POINTL(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), POINTL.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="LONG")
    public int x() {
        return POINTL.nx(this.address());
    }

    @NativeType(value="LONG")
    public int y() {
        return POINTL.ny(this.address());
    }

    public POINTL x(@NativeType(value="LONG") int n) {
        POINTL.nx(this.address(), n);
        return this;
    }

    public POINTL y(@NativeType(value="LONG") int n) {
        POINTL.ny(this.address(), n);
        return this;
    }

    public POINTL set(int n, int n2) {
        this.x(n);
        this.y(n2);
        return this;
    }

    public POINTL set(POINTL pOINTL) {
        MemoryUtil.memCopy(pOINTL.address(), this.address(), SIZEOF);
        return this;
    }

    public static POINTL malloc() {
        return POINTL.wrap(POINTL.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static POINTL calloc() {
        return POINTL.wrap(POINTL.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static POINTL create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return POINTL.wrap(POINTL.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static POINTL create(long l) {
        return POINTL.wrap(POINTL.class, l);
    }

    @Nullable
    public static POINTL createSafe(long l) {
        return l == 0L ? null : POINTL.wrap(POINTL.class, l);
    }

    public static Buffer malloc(int n) {
        return POINTL.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(POINTL.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return POINTL.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = POINTL.__create(n, SIZEOF);
        return POINTL.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return POINTL.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : POINTL.wrap(Buffer.class, l, n);
    }

    public static POINTL mallocStack() {
        return POINTL.mallocStack(MemoryStack.stackGet());
    }

    public static POINTL callocStack() {
        return POINTL.callocStack(MemoryStack.stackGet());
    }

    public static POINTL mallocStack(MemoryStack memoryStack) {
        return POINTL.wrap(POINTL.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static POINTL callocStack(MemoryStack memoryStack) {
        return POINTL.wrap(POINTL.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return POINTL.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return POINTL.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return POINTL.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return POINTL.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int nx(long l) {
        return UNSAFE.getInt(null, l + (long)X);
    }

    public static int ny(long l) {
        return UNSAFE.getInt(null, l + (long)Y);
    }

    public static void nx(long l, int n) {
        UNSAFE.putInt(null, l + (long)X, n);
    }

    public static void ny(long l, int n) {
        UNSAFE.putInt(null, l + (long)Y, n);
    }

    static {
        Struct.Layout layout = POINTL.__struct(POINTL.__member(4), POINTL.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        X = layout.offsetof(0);
        Y = layout.offsetof(1);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<POINTL, Buffer>
    implements NativeResource {
        private static final POINTL ELEMENT_FACTORY = POINTL.create(-1L);

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
        protected POINTL getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="LONG")
        public int x() {
            return POINTL.nx(this.address());
        }

        @NativeType(value="LONG")
        public int y() {
            return POINTL.ny(this.address());
        }

        public Buffer x(@NativeType(value="LONG") int n) {
            POINTL.nx(this.address(), n);
            return this;
        }

        public Buffer y(@NativeType(value="LONG") int n) {
            POINTL.ny(this.address(), n);
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

