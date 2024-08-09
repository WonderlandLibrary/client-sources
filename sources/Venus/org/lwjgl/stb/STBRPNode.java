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

@NativeType(value="struct stbrp_node")
public class STBRPNode
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int X;
    public static final int Y;
    public static final int NEXT;

    public STBRPNode(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBRPNode.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="stbrp_coord")
    public short x() {
        return STBRPNode.nx(this.address());
    }

    @NativeType(value="stbrp_coord")
    public short y() {
        return STBRPNode.ny(this.address());
    }

    @Nullable
    @NativeType(value="stbrp_node *")
    public STBRPNode next() {
        return STBRPNode.nnext(this.address());
    }

    public static STBRPNode malloc() {
        return STBRPNode.wrap(STBRPNode.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBRPNode calloc() {
        return STBRPNode.wrap(STBRPNode.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBRPNode create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBRPNode.wrap(STBRPNode.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBRPNode create(long l) {
        return STBRPNode.wrap(STBRPNode.class, l);
    }

    @Nullable
    public static STBRPNode createSafe(long l) {
        return l == 0L ? null : STBRPNode.wrap(STBRPNode.class, l);
    }

    public static Buffer malloc(int n) {
        return STBRPNode.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBRPNode.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBRPNode.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBRPNode.__create(n, SIZEOF);
        return STBRPNode.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBRPNode.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBRPNode.wrap(Buffer.class, l, n);
    }

    public static STBRPNode mallocStack() {
        return STBRPNode.mallocStack(MemoryStack.stackGet());
    }

    public static STBRPNode callocStack() {
        return STBRPNode.callocStack(MemoryStack.stackGet());
    }

    public static STBRPNode mallocStack(MemoryStack memoryStack) {
        return STBRPNode.wrap(STBRPNode.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBRPNode callocStack(MemoryStack memoryStack) {
        return STBRPNode.wrap(STBRPNode.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBRPNode.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBRPNode.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBRPNode.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBRPNode.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static short nx(long l) {
        return UNSAFE.getShort(null, l + (long)X);
    }

    public static short ny(long l) {
        return UNSAFE.getShort(null, l + (long)Y);
    }

    @Nullable
    public static STBRPNode nnext(long l) {
        return STBRPNode.createSafe(MemoryUtil.memGetAddress(l + (long)NEXT));
    }

    static {
        Struct.Layout layout = STBRPNode.__struct(STBRPNode.__member(2), STBRPNode.__member(2), STBRPNode.__member(POINTER_SIZE));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        X = layout.offsetof(0);
        Y = layout.offsetof(1);
        NEXT = layout.offsetof(2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBRPNode, Buffer>
    implements NativeResource {
        private static final STBRPNode ELEMENT_FACTORY = STBRPNode.create(-1L);

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
        protected STBRPNode getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="stbrp_coord")
        public short x() {
            return STBRPNode.nx(this.address());
        }

        @NativeType(value="stbrp_coord")
        public short y() {
            return STBRPNode.ny(this.address());
        }

        @Nullable
        @NativeType(value="stbrp_node *")
        public STBRPNode next() {
            return STBRPNode.nnext(this.address());
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

