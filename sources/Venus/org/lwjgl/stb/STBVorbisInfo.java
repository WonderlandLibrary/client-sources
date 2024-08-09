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

@NativeType(value="struct stb_vorbis_info")
public class STBVorbisInfo
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int SAMPLE_RATE;
    public static final int CHANNELS;
    public static final int SETUP_MEMORY_REQUIRED;
    public static final int SETUP_TEMP_MEMORY_REQUIRED;
    public static final int TEMP_MEMORY_REQUIRED;
    public static final int MAX_FRAME_SIZE;

    public STBVorbisInfo(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBVorbisInfo.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="unsigned int")
    public int sample_rate() {
        return STBVorbisInfo.nsample_rate(this.address());
    }

    public int channels() {
        return STBVorbisInfo.nchannels(this.address());
    }

    @NativeType(value="unsigned int")
    public int setup_memory_required() {
        return STBVorbisInfo.nsetup_memory_required(this.address());
    }

    @NativeType(value="unsigned int")
    public int setup_temp_memory_required() {
        return STBVorbisInfo.nsetup_temp_memory_required(this.address());
    }

    @NativeType(value="unsigned int")
    public int temp_memory_required() {
        return STBVorbisInfo.ntemp_memory_required(this.address());
    }

    public int max_frame_size() {
        return STBVorbisInfo.nmax_frame_size(this.address());
    }

    public static STBVorbisInfo malloc() {
        return STBVorbisInfo.wrap(STBVorbisInfo.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBVorbisInfo calloc() {
        return STBVorbisInfo.wrap(STBVorbisInfo.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBVorbisInfo create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBVorbisInfo.wrap(STBVorbisInfo.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBVorbisInfo create(long l) {
        return STBVorbisInfo.wrap(STBVorbisInfo.class, l);
    }

    @Nullable
    public static STBVorbisInfo createSafe(long l) {
        return l == 0L ? null : STBVorbisInfo.wrap(STBVorbisInfo.class, l);
    }

    public static Buffer malloc(int n) {
        return STBVorbisInfo.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBVorbisInfo.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBVorbisInfo.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBVorbisInfo.__create(n, SIZEOF);
        return STBVorbisInfo.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBVorbisInfo.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBVorbisInfo.wrap(Buffer.class, l, n);
    }

    public static STBVorbisInfo mallocStack() {
        return STBVorbisInfo.mallocStack(MemoryStack.stackGet());
    }

    public static STBVorbisInfo callocStack() {
        return STBVorbisInfo.callocStack(MemoryStack.stackGet());
    }

    public static STBVorbisInfo mallocStack(MemoryStack memoryStack) {
        return STBVorbisInfo.wrap(STBVorbisInfo.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBVorbisInfo callocStack(MemoryStack memoryStack) {
        return STBVorbisInfo.wrap(STBVorbisInfo.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBVorbisInfo.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBVorbisInfo.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBVorbisInfo.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBVorbisInfo.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int nsample_rate(long l) {
        return UNSAFE.getInt(null, l + (long)SAMPLE_RATE);
    }

    public static int nchannels(long l) {
        return UNSAFE.getInt(null, l + (long)CHANNELS);
    }

    public static int nsetup_memory_required(long l) {
        return UNSAFE.getInt(null, l + (long)SETUP_MEMORY_REQUIRED);
    }

    public static int nsetup_temp_memory_required(long l) {
        return UNSAFE.getInt(null, l + (long)SETUP_TEMP_MEMORY_REQUIRED);
    }

    public static int ntemp_memory_required(long l) {
        return UNSAFE.getInt(null, l + (long)TEMP_MEMORY_REQUIRED);
    }

    public static int nmax_frame_size(long l) {
        return UNSAFE.getInt(null, l + (long)MAX_FRAME_SIZE);
    }

    static {
        Struct.Layout layout = STBVorbisInfo.__struct(STBVorbisInfo.__member(4), STBVorbisInfo.__member(4), STBVorbisInfo.__member(4), STBVorbisInfo.__member(4), STBVorbisInfo.__member(4), STBVorbisInfo.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        SAMPLE_RATE = layout.offsetof(0);
        CHANNELS = layout.offsetof(1);
        SETUP_MEMORY_REQUIRED = layout.offsetof(2);
        SETUP_TEMP_MEMORY_REQUIRED = layout.offsetof(3);
        TEMP_MEMORY_REQUIRED = layout.offsetof(4);
        MAX_FRAME_SIZE = layout.offsetof(5);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBVorbisInfo, Buffer>
    implements NativeResource {
        private static final STBVorbisInfo ELEMENT_FACTORY = STBVorbisInfo.create(-1L);

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
        protected STBVorbisInfo getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="unsigned int")
        public int sample_rate() {
            return STBVorbisInfo.nsample_rate(this.address());
        }

        public int channels() {
            return STBVorbisInfo.nchannels(this.address());
        }

        @NativeType(value="unsigned int")
        public int setup_memory_required() {
            return STBVorbisInfo.nsetup_memory_required(this.address());
        }

        @NativeType(value="unsigned int")
        public int setup_temp_memory_required() {
            return STBVorbisInfo.nsetup_temp_memory_required(this.address());
        }

        @NativeType(value="unsigned int")
        public int temp_memory_required() {
            return STBVorbisInfo.ntemp_memory_required(this.address());
        }

        public int max_frame_size() {
            return STBVorbisInfo.nmax_frame_size(this.address());
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

