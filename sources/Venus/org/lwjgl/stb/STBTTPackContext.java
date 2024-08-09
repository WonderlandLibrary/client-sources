/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBRPContext;
import org.lwjgl.stb.STBRPNode;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType(value="struct stbtt_pack_context")
public class STBTTPackContext
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int USER_ALLOCATOR_CONTEXT;
    public static final int PACK_INFO;
    public static final int WIDTH;
    public static final int HEIGHT;
    public static final int STRIDE_IN_BYTES;
    public static final int PADDING;
    public static final int SKIP_MISSING;
    public static final int H_OVERSAMPLE;
    public static final int V_OVERSAMPLE;
    public static final int PIXELS;
    public static final int NODES;

    public STBTTPackContext(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBTTPackContext.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="void *")
    public long user_allocator_context() {
        return STBTTPackContext.nuser_allocator_context(this.address());
    }

    @NativeType(value="stbrp_context *")
    public STBRPContext pack_info() {
        return STBTTPackContext.npack_info(this.address());
    }

    public int width() {
        return STBTTPackContext.nwidth(this.address());
    }

    public int height() {
        return STBTTPackContext.nheight(this.address());
    }

    public int stride_in_bytes() {
        return STBTTPackContext.nstride_in_bytes(this.address());
    }

    public int padding() {
        return STBTTPackContext.npadding(this.address());
    }

    @NativeType(value="int")
    public boolean skip_missing() {
        return STBTTPackContext.nskip_missing(this.address()) != 0;
    }

    @NativeType(value="unsigned int")
    public int h_oversample() {
        return STBTTPackContext.nh_oversample(this.address());
    }

    @NativeType(value="unsigned int")
    public int v_oversample() {
        return STBTTPackContext.nv_oversample(this.address());
    }

    @NativeType(value="unsigned char *")
    public ByteBuffer pixels(int n) {
        return STBTTPackContext.npixels(this.address(), n);
    }

    @NativeType(value="stbrp_node *")
    public STBRPNode.Buffer nodes(int n) {
        return STBTTPackContext.nnodes(this.address(), n);
    }

    public static STBTTPackContext malloc() {
        return STBTTPackContext.wrap(STBTTPackContext.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBTTPackContext calloc() {
        return STBTTPackContext.wrap(STBTTPackContext.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBTTPackContext create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBTTPackContext.wrap(STBTTPackContext.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBTTPackContext create(long l) {
        return STBTTPackContext.wrap(STBTTPackContext.class, l);
    }

    @Nullable
    public static STBTTPackContext createSafe(long l) {
        return l == 0L ? null : STBTTPackContext.wrap(STBTTPackContext.class, l);
    }

    public static Buffer malloc(int n) {
        return STBTTPackContext.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBTTPackContext.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBTTPackContext.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBTTPackContext.__create(n, SIZEOF);
        return STBTTPackContext.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBTTPackContext.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBTTPackContext.wrap(Buffer.class, l, n);
    }

    public static STBTTPackContext mallocStack() {
        return STBTTPackContext.mallocStack(MemoryStack.stackGet());
    }

    public static STBTTPackContext callocStack() {
        return STBTTPackContext.callocStack(MemoryStack.stackGet());
    }

    public static STBTTPackContext mallocStack(MemoryStack memoryStack) {
        return STBTTPackContext.wrap(STBTTPackContext.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBTTPackContext callocStack(MemoryStack memoryStack) {
        return STBTTPackContext.wrap(STBTTPackContext.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBTTPackContext.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBTTPackContext.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBTTPackContext.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBTTPackContext.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static long nuser_allocator_context(long l) {
        return MemoryUtil.memGetAddress(l + (long)USER_ALLOCATOR_CONTEXT);
    }

    public static STBRPContext npack_info(long l) {
        return STBRPContext.create(MemoryUtil.memGetAddress(l + (long)PACK_INFO));
    }

    public static int nwidth(long l) {
        return UNSAFE.getInt(null, l + (long)WIDTH);
    }

    public static int nheight(long l) {
        return UNSAFE.getInt(null, l + (long)HEIGHT);
    }

    public static int nstride_in_bytes(long l) {
        return UNSAFE.getInt(null, l + (long)STRIDE_IN_BYTES);
    }

    public static int npadding(long l) {
        return UNSAFE.getInt(null, l + (long)PADDING);
    }

    public static int nskip_missing(long l) {
        return UNSAFE.getInt(null, l + (long)SKIP_MISSING);
    }

    public static int nh_oversample(long l) {
        return UNSAFE.getInt(null, l + (long)H_OVERSAMPLE);
    }

    public static int nv_oversample(long l) {
        return UNSAFE.getInt(null, l + (long)V_OVERSAMPLE);
    }

    public static ByteBuffer npixels(long l, int n) {
        return MemoryUtil.memByteBuffer(MemoryUtil.memGetAddress(l + (long)PIXELS), n);
    }

    public static STBRPNode.Buffer nnodes(long l, int n) {
        return STBRPNode.create(MemoryUtil.memGetAddress(l + (long)NODES), n);
    }

    static {
        Struct.Layout layout = STBTTPackContext.__struct(STBTTPackContext.__member(POINTER_SIZE), STBTTPackContext.__member(POINTER_SIZE), STBTTPackContext.__member(4), STBTTPackContext.__member(4), STBTTPackContext.__member(4), STBTTPackContext.__member(4), STBTTPackContext.__member(4), STBTTPackContext.__member(4), STBTTPackContext.__member(4), STBTTPackContext.__member(POINTER_SIZE), STBTTPackContext.__member(POINTER_SIZE));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        USER_ALLOCATOR_CONTEXT = layout.offsetof(0);
        PACK_INFO = layout.offsetof(1);
        WIDTH = layout.offsetof(2);
        HEIGHT = layout.offsetof(3);
        STRIDE_IN_BYTES = layout.offsetof(4);
        PADDING = layout.offsetof(5);
        SKIP_MISSING = layout.offsetof(6);
        H_OVERSAMPLE = layout.offsetof(7);
        V_OVERSAMPLE = layout.offsetof(8);
        PIXELS = layout.offsetof(9);
        NODES = layout.offsetof(10);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBTTPackContext, Buffer>
    implements NativeResource {
        private static final STBTTPackContext ELEMENT_FACTORY = STBTTPackContext.create(-1L);

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
        protected STBTTPackContext getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="void *")
        public long user_allocator_context() {
            return STBTTPackContext.nuser_allocator_context(this.address());
        }

        @NativeType(value="stbrp_context *")
        public STBRPContext pack_info() {
            return STBTTPackContext.npack_info(this.address());
        }

        public int width() {
            return STBTTPackContext.nwidth(this.address());
        }

        public int height() {
            return STBTTPackContext.nheight(this.address());
        }

        public int stride_in_bytes() {
            return STBTTPackContext.nstride_in_bytes(this.address());
        }

        public int padding() {
            return STBTTPackContext.npadding(this.address());
        }

        @NativeType(value="int")
        public boolean skip_missing() {
            return STBTTPackContext.nskip_missing(this.address()) != 0;
        }

        @NativeType(value="unsigned int")
        public int h_oversample() {
            return STBTTPackContext.nh_oversample(this.address());
        }

        @NativeType(value="unsigned int")
        public int v_oversample() {
            return STBTTPackContext.nv_oversample(this.address());
        }

        @NativeType(value="unsigned char *")
        public ByteBuffer pixels(int n) {
            return STBTTPackContext.npixels(this.address(), n);
        }

        @NativeType(value="stbrp_node *")
        public STBRPNode.Buffer nodes(int n) {
            return STBTTPackContext.nnodes(this.address(), n);
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

