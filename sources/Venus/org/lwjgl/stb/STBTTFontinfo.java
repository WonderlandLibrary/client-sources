/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.LibSTB;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType(value="struct stbtt_fontinfo")
public class STBTTFontinfo
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;

    private static native int offsets(long var0);

    public STBTTFontinfo(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBTTFontinfo.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    public static STBTTFontinfo malloc() {
        return STBTTFontinfo.wrap(STBTTFontinfo.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBTTFontinfo calloc() {
        return STBTTFontinfo.wrap(STBTTFontinfo.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBTTFontinfo create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBTTFontinfo.wrap(STBTTFontinfo.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBTTFontinfo create(long l) {
        return STBTTFontinfo.wrap(STBTTFontinfo.class, l);
    }

    @Nullable
    public static STBTTFontinfo createSafe(long l) {
        return l == 0L ? null : STBTTFontinfo.wrap(STBTTFontinfo.class, l);
    }

    public static Buffer malloc(int n) {
        return STBTTFontinfo.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBTTFontinfo.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBTTFontinfo.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBTTFontinfo.__create(n, SIZEOF);
        return STBTTFontinfo.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBTTFontinfo.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBTTFontinfo.wrap(Buffer.class, l, n);
    }

    public static STBTTFontinfo mallocStack() {
        return STBTTFontinfo.mallocStack(MemoryStack.stackGet());
    }

    public static STBTTFontinfo callocStack() {
        return STBTTFontinfo.callocStack(MemoryStack.stackGet());
    }

    public static STBTTFontinfo mallocStack(MemoryStack memoryStack) {
        return STBTTFontinfo.wrap(STBTTFontinfo.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBTTFontinfo callocStack(MemoryStack memoryStack) {
        return STBTTFontinfo.wrap(STBTTFontinfo.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBTTFontinfo.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBTTFontinfo.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBTTFontinfo.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBTTFontinfo.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    static {
        LibSTB.initialize();
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            SIZEOF = STBTTFontinfo.offsets(MemoryUtil.memAddress(intBuffer));
            ALIGNOF = intBuffer.get(0);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBTTFontinfo, Buffer>
    implements NativeResource {
        private static final STBTTFontinfo ELEMENT_FACTORY = STBTTFontinfo.create(-1L);

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
        protected STBTTFontinfo getElementFactory() {
            return ELEMENT_FACTORY;
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

