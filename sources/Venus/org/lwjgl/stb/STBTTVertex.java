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

@NativeType(value="struct stbtt_vertex")
public class STBTTVertex
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int X;
    public static final int Y;
    public static final int CX;
    public static final int CY;
    public static final int CX1;
    public static final int CY1;
    public static final int TYPE;

    public STBTTVertex(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBTTVertex.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="stbtt_vertex_type")
    public short x() {
        return STBTTVertex.nx(this.address());
    }

    @NativeType(value="stbtt_vertex_type")
    public short y() {
        return STBTTVertex.ny(this.address());
    }

    @NativeType(value="stbtt_vertex_type")
    public short cx() {
        return STBTTVertex.ncx(this.address());
    }

    @NativeType(value="stbtt_vertex_type")
    public short cy() {
        return STBTTVertex.ncy(this.address());
    }

    @NativeType(value="stbtt_vertex_type")
    public short cx1() {
        return STBTTVertex.ncx1(this.address());
    }

    @NativeType(value="stbtt_vertex_type")
    public short cy1() {
        return STBTTVertex.ncy1(this.address());
    }

    @NativeType(value="unsigned char")
    public byte type() {
        return STBTTVertex.ntype(this.address());
    }

    public static STBTTVertex malloc() {
        return STBTTVertex.wrap(STBTTVertex.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBTTVertex calloc() {
        return STBTTVertex.wrap(STBTTVertex.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBTTVertex create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBTTVertex.wrap(STBTTVertex.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBTTVertex create(long l) {
        return STBTTVertex.wrap(STBTTVertex.class, l);
    }

    @Nullable
    public static STBTTVertex createSafe(long l) {
        return l == 0L ? null : STBTTVertex.wrap(STBTTVertex.class, l);
    }

    public static Buffer malloc(int n) {
        return STBTTVertex.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBTTVertex.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBTTVertex.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBTTVertex.__create(n, SIZEOF);
        return STBTTVertex.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBTTVertex.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBTTVertex.wrap(Buffer.class, l, n);
    }

    public static STBTTVertex mallocStack() {
        return STBTTVertex.mallocStack(MemoryStack.stackGet());
    }

    public static STBTTVertex callocStack() {
        return STBTTVertex.callocStack(MemoryStack.stackGet());
    }

    public static STBTTVertex mallocStack(MemoryStack memoryStack) {
        return STBTTVertex.wrap(STBTTVertex.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBTTVertex callocStack(MemoryStack memoryStack) {
        return STBTTVertex.wrap(STBTTVertex.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBTTVertex.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBTTVertex.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBTTVertex.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBTTVertex.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static short nx(long l) {
        return UNSAFE.getShort(null, l + (long)X);
    }

    public static short ny(long l) {
        return UNSAFE.getShort(null, l + (long)Y);
    }

    public static short ncx(long l) {
        return UNSAFE.getShort(null, l + (long)CX);
    }

    public static short ncy(long l) {
        return UNSAFE.getShort(null, l + (long)CY);
    }

    public static short ncx1(long l) {
        return UNSAFE.getShort(null, l + (long)CX1);
    }

    public static short ncy1(long l) {
        return UNSAFE.getShort(null, l + (long)CY1);
    }

    public static byte ntype(long l) {
        return UNSAFE.getByte(null, l + (long)TYPE);
    }

    static {
        Struct.Layout layout = STBTTVertex.__struct(STBTTVertex.__member(2), STBTTVertex.__member(2), STBTTVertex.__member(2), STBTTVertex.__member(2), STBTTVertex.__member(2), STBTTVertex.__member(2), STBTTVertex.__member(1));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        X = layout.offsetof(0);
        Y = layout.offsetof(1);
        CX = layout.offsetof(2);
        CY = layout.offsetof(3);
        CX1 = layout.offsetof(4);
        CY1 = layout.offsetof(5);
        TYPE = layout.offsetof(6);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBTTVertex, Buffer>
    implements NativeResource {
        private static final STBTTVertex ELEMENT_FACTORY = STBTTVertex.create(-1L);

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
        protected STBTTVertex getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="stbtt_vertex_type")
        public short x() {
            return STBTTVertex.nx(this.address());
        }

        @NativeType(value="stbtt_vertex_type")
        public short y() {
            return STBTTVertex.ny(this.address());
        }

        @NativeType(value="stbtt_vertex_type")
        public short cx() {
            return STBTTVertex.ncx(this.address());
        }

        @NativeType(value="stbtt_vertex_type")
        public short cy() {
            return STBTTVertex.ncy(this.address());
        }

        @NativeType(value="stbtt_vertex_type")
        public short cx1() {
            return STBTTVertex.ncx1(this.address());
        }

        @NativeType(value="stbtt_vertex_type")
        public short cy1() {
            return STBTTVertex.ncy1(this.address());
        }

        @NativeType(value="unsigned char")
        public byte type() {
            return STBTTVertex.ntype(this.address());
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

