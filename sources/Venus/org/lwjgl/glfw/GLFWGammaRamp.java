/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
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

@NativeType(value="struct GLFWgammaramp")
public class GLFWGammaRamp
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int RED;
    public static final int GREEN;
    public static final int BLUE;
    public static final int SIZE;

    public GLFWGammaRamp(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), GLFWGammaRamp.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="unsigned short *")
    public ShortBuffer red() {
        return GLFWGammaRamp.nred(this.address());
    }

    @NativeType(value="unsigned short *")
    public ShortBuffer green() {
        return GLFWGammaRamp.ngreen(this.address());
    }

    @NativeType(value="unsigned short *")
    public ShortBuffer blue() {
        return GLFWGammaRamp.nblue(this.address());
    }

    @NativeType(value="unsigned int")
    public int size() {
        return GLFWGammaRamp.nsize(this.address());
    }

    public GLFWGammaRamp red(@NativeType(value="unsigned short *") ShortBuffer shortBuffer) {
        GLFWGammaRamp.nred(this.address(), shortBuffer);
        return this;
    }

    public GLFWGammaRamp green(@NativeType(value="unsigned short *") ShortBuffer shortBuffer) {
        GLFWGammaRamp.ngreen(this.address(), shortBuffer);
        return this;
    }

    public GLFWGammaRamp blue(@NativeType(value="unsigned short *") ShortBuffer shortBuffer) {
        GLFWGammaRamp.nblue(this.address(), shortBuffer);
        return this;
    }

    public GLFWGammaRamp size(@NativeType(value="unsigned int") int n) {
        GLFWGammaRamp.nsize(this.address(), n);
        return this;
    }

    public GLFWGammaRamp set(ShortBuffer shortBuffer, ShortBuffer shortBuffer2, ShortBuffer shortBuffer3, int n) {
        this.red(shortBuffer);
        this.green(shortBuffer2);
        this.blue(shortBuffer3);
        this.size(n);
        return this;
    }

    public GLFWGammaRamp set(GLFWGammaRamp gLFWGammaRamp) {
        MemoryUtil.memCopy(gLFWGammaRamp.address(), this.address(), SIZEOF);
        return this;
    }

    public static GLFWGammaRamp malloc() {
        return GLFWGammaRamp.wrap(GLFWGammaRamp.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static GLFWGammaRamp calloc() {
        return GLFWGammaRamp.wrap(GLFWGammaRamp.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static GLFWGammaRamp create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return GLFWGammaRamp.wrap(GLFWGammaRamp.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static GLFWGammaRamp create(long l) {
        return GLFWGammaRamp.wrap(GLFWGammaRamp.class, l);
    }

    @Nullable
    public static GLFWGammaRamp createSafe(long l) {
        return l == 0L ? null : GLFWGammaRamp.wrap(GLFWGammaRamp.class, l);
    }

    public static Buffer malloc(int n) {
        return GLFWGammaRamp.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(GLFWGammaRamp.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return GLFWGammaRamp.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = GLFWGammaRamp.__create(n, SIZEOF);
        return GLFWGammaRamp.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return GLFWGammaRamp.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : GLFWGammaRamp.wrap(Buffer.class, l, n);
    }

    public static GLFWGammaRamp mallocStack() {
        return GLFWGammaRamp.mallocStack(MemoryStack.stackGet());
    }

    public static GLFWGammaRamp callocStack() {
        return GLFWGammaRamp.callocStack(MemoryStack.stackGet());
    }

    public static GLFWGammaRamp mallocStack(MemoryStack memoryStack) {
        return GLFWGammaRamp.wrap(GLFWGammaRamp.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static GLFWGammaRamp callocStack(MemoryStack memoryStack) {
        return GLFWGammaRamp.wrap(GLFWGammaRamp.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return GLFWGammaRamp.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return GLFWGammaRamp.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return GLFWGammaRamp.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return GLFWGammaRamp.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static ShortBuffer nred(long l) {
        return MemoryUtil.memShortBuffer(MemoryUtil.memGetAddress(l + (long)RED), GLFWGammaRamp.nsize(l));
    }

    public static ShortBuffer ngreen(long l) {
        return MemoryUtil.memShortBuffer(MemoryUtil.memGetAddress(l + (long)GREEN), GLFWGammaRamp.nsize(l));
    }

    public static ShortBuffer nblue(long l) {
        return MemoryUtil.memShortBuffer(MemoryUtil.memGetAddress(l + (long)BLUE), GLFWGammaRamp.nsize(l));
    }

    public static int nsize(long l) {
        return UNSAFE.getInt(null, l + (long)SIZE);
    }

    public static void nred(long l, ShortBuffer shortBuffer) {
        MemoryUtil.memPutAddress(l + (long)RED, MemoryUtil.memAddress(shortBuffer));
    }

    public static void ngreen(long l, ShortBuffer shortBuffer) {
        MemoryUtil.memPutAddress(l + (long)GREEN, MemoryUtil.memAddress(shortBuffer));
    }

    public static void nblue(long l, ShortBuffer shortBuffer) {
        MemoryUtil.memPutAddress(l + (long)BLUE, MemoryUtil.memAddress(shortBuffer));
    }

    public static void nsize(long l, int n) {
        UNSAFE.putInt(null, l + (long)SIZE, n);
    }

    public static void validate(long l) {
        Checks.check(MemoryUtil.memGetAddress(l + (long)RED));
        Checks.check(MemoryUtil.memGetAddress(l + (long)GREEN));
        Checks.check(MemoryUtil.memGetAddress(l + (long)BLUE));
    }

    public static void validate(long l, int n) {
        for (int i = 0; i < n; ++i) {
            GLFWGammaRamp.validate(l + Integer.toUnsignedLong(i) * (long)SIZEOF);
        }
    }

    static {
        Struct.Layout layout = GLFWGammaRamp.__struct(GLFWGammaRamp.__member(POINTER_SIZE), GLFWGammaRamp.__member(POINTER_SIZE), GLFWGammaRamp.__member(POINTER_SIZE), GLFWGammaRamp.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        RED = layout.offsetof(0);
        GREEN = layout.offsetof(1);
        BLUE = layout.offsetof(2);
        SIZE = layout.offsetof(3);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<GLFWGammaRamp, Buffer>
    implements NativeResource {
        private static final GLFWGammaRamp ELEMENT_FACTORY = GLFWGammaRamp.create(-1L);

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
        protected GLFWGammaRamp getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="unsigned short *")
        public ShortBuffer red() {
            return GLFWGammaRamp.nred(this.address());
        }

        @NativeType(value="unsigned short *")
        public ShortBuffer green() {
            return GLFWGammaRamp.ngreen(this.address());
        }

        @NativeType(value="unsigned short *")
        public ShortBuffer blue() {
            return GLFWGammaRamp.nblue(this.address());
        }

        @NativeType(value="unsigned int")
        public int size() {
            return GLFWGammaRamp.nsize(this.address());
        }

        public Buffer red(@NativeType(value="unsigned short *") ShortBuffer shortBuffer) {
            GLFWGammaRamp.nred(this.address(), shortBuffer);
            return this;
        }

        public Buffer green(@NativeType(value="unsigned short *") ShortBuffer shortBuffer) {
            GLFWGammaRamp.ngreen(this.address(), shortBuffer);
            return this;
        }

        public Buffer blue(@NativeType(value="unsigned short *") ShortBuffer shortBuffer) {
            GLFWGammaRamp.nblue(this.address(), shortBuffer);
            return this;
        }

        public Buffer size(@NativeType(value="unsigned int") int n) {
            GLFWGammaRamp.nsize(this.address(), n);
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

