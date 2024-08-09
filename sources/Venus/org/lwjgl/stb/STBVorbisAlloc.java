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

@NativeType(value="struct stb_vorbis_alloc")
public class STBVorbisAlloc
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int ALLOC_BUFFER;
    public static final int ALLOC_BUFFER_LENGTH_IN_BYTES;

    public STBVorbisAlloc(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBVorbisAlloc.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="char *")
    public ByteBuffer alloc_buffer() {
        return STBVorbisAlloc.nalloc_buffer(this.address());
    }

    public int alloc_buffer_length_in_bytes() {
        return STBVorbisAlloc.nalloc_buffer_length_in_bytes(this.address());
    }

    public STBVorbisAlloc alloc_buffer(@NativeType(value="char *") ByteBuffer byteBuffer) {
        STBVorbisAlloc.nalloc_buffer(this.address(), byteBuffer);
        return this;
    }

    public STBVorbisAlloc set(STBVorbisAlloc sTBVorbisAlloc) {
        MemoryUtil.memCopy(sTBVorbisAlloc.address(), this.address(), SIZEOF);
        return this;
    }

    public static STBVorbisAlloc malloc() {
        return STBVorbisAlloc.wrap(STBVorbisAlloc.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBVorbisAlloc calloc() {
        return STBVorbisAlloc.wrap(STBVorbisAlloc.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBVorbisAlloc create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBVorbisAlloc.wrap(STBVorbisAlloc.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBVorbisAlloc create(long l) {
        return STBVorbisAlloc.wrap(STBVorbisAlloc.class, l);
    }

    @Nullable
    public static STBVorbisAlloc createSafe(long l) {
        return l == 0L ? null : STBVorbisAlloc.wrap(STBVorbisAlloc.class, l);
    }

    public static Buffer malloc(int n) {
        return STBVorbisAlloc.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBVorbisAlloc.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBVorbisAlloc.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBVorbisAlloc.__create(n, SIZEOF);
        return STBVorbisAlloc.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBVorbisAlloc.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBVorbisAlloc.wrap(Buffer.class, l, n);
    }

    public static STBVorbisAlloc mallocStack() {
        return STBVorbisAlloc.mallocStack(MemoryStack.stackGet());
    }

    public static STBVorbisAlloc callocStack() {
        return STBVorbisAlloc.callocStack(MemoryStack.stackGet());
    }

    public static STBVorbisAlloc mallocStack(MemoryStack memoryStack) {
        return STBVorbisAlloc.wrap(STBVorbisAlloc.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBVorbisAlloc callocStack(MemoryStack memoryStack) {
        return STBVorbisAlloc.wrap(STBVorbisAlloc.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBVorbisAlloc.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBVorbisAlloc.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBVorbisAlloc.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBVorbisAlloc.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static ByteBuffer nalloc_buffer(long l) {
        return MemoryUtil.memByteBuffer(MemoryUtil.memGetAddress(l + (long)ALLOC_BUFFER), STBVorbisAlloc.nalloc_buffer_length_in_bytes(l));
    }

    public static int nalloc_buffer_length_in_bytes(long l) {
        return UNSAFE.getInt(null, l + (long)ALLOC_BUFFER_LENGTH_IN_BYTES);
    }

    public static void nalloc_buffer(long l, ByteBuffer byteBuffer) {
        MemoryUtil.memPutAddress(l + (long)ALLOC_BUFFER, MemoryUtil.memAddress(byteBuffer));
        STBVorbisAlloc.nalloc_buffer_length_in_bytes(l, byteBuffer.remaining());
    }

    public static void nalloc_buffer_length_in_bytes(long l, int n) {
        UNSAFE.putInt(null, l + (long)ALLOC_BUFFER_LENGTH_IN_BYTES, n);
    }

    public static void validate(long l) {
        Checks.check(MemoryUtil.memGetAddress(l + (long)ALLOC_BUFFER));
    }

    public static void validate(long l, int n) {
        for (int i = 0; i < n; ++i) {
            STBVorbisAlloc.validate(l + Integer.toUnsignedLong(i) * (long)SIZEOF);
        }
    }

    static {
        Struct.Layout layout = STBVorbisAlloc.__struct(STBVorbisAlloc.__member(POINTER_SIZE), STBVorbisAlloc.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        ALLOC_BUFFER = layout.offsetof(0);
        ALLOC_BUFFER_LENGTH_IN_BYTES = layout.offsetof(1);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBVorbisAlloc, Buffer>
    implements NativeResource {
        private static final STBVorbisAlloc ELEMENT_FACTORY = STBVorbisAlloc.create(-1L);

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
        protected STBVorbisAlloc getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="char *")
        public ByteBuffer alloc_buffer() {
            return STBVorbisAlloc.nalloc_buffer(this.address());
        }

        public int alloc_buffer_length_in_bytes() {
            return STBVorbisAlloc.nalloc_buffer_length_in_bytes(this.address());
        }

        public Buffer alloc_buffer(@NativeType(value="char *") ByteBuffer byteBuffer) {
            STBVorbisAlloc.nalloc_buffer(this.address(), byteBuffer);
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

