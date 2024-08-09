/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

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

@NativeType(value="struct GLFWimage")
public class GLFWImage
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int WIDTH;
    public static final int HEIGHT;
    public static final int PIXELS;

    public GLFWImage(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), GLFWImage.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    public int width() {
        return GLFWImage.nwidth(this.address());
    }

    public int height() {
        return GLFWImage.nheight(this.address());
    }

    @NativeType(value="unsigned char *")
    public ByteBuffer pixels(int n) {
        return GLFWImage.npixels(this.address(), n);
    }

    public GLFWImage width(int n) {
        GLFWImage.nwidth(this.address(), n);
        return this;
    }

    public GLFWImage height(int n) {
        GLFWImage.nheight(this.address(), n);
        return this;
    }

    public GLFWImage pixels(@NativeType(value="unsigned char *") ByteBuffer byteBuffer) {
        GLFWImage.npixels(this.address(), byteBuffer);
        return this;
    }

    public GLFWImage set(int n, int n2, ByteBuffer byteBuffer) {
        this.width(n);
        this.height(n2);
        this.pixels(byteBuffer);
        return this;
    }

    public GLFWImage set(GLFWImage gLFWImage) {
        MemoryUtil.memCopy(gLFWImage.address(), this.address(), SIZEOF);
        return this;
    }

    public static GLFWImage malloc() {
        return GLFWImage.wrap(GLFWImage.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static GLFWImage calloc() {
        return GLFWImage.wrap(GLFWImage.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static GLFWImage create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return GLFWImage.wrap(GLFWImage.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static GLFWImage create(long l) {
        return GLFWImage.wrap(GLFWImage.class, l);
    }

    @Nullable
    public static GLFWImage createSafe(long l) {
        return l == 0L ? null : GLFWImage.wrap(GLFWImage.class, l);
    }

    public static Buffer malloc(int n) {
        return GLFWImage.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(GLFWImage.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return GLFWImage.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = GLFWImage.__create(n, SIZEOF);
        return GLFWImage.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return GLFWImage.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : GLFWImage.wrap(Buffer.class, l, n);
    }

    public static GLFWImage mallocStack() {
        return GLFWImage.mallocStack(MemoryStack.stackGet());
    }

    public static GLFWImage callocStack() {
        return GLFWImage.callocStack(MemoryStack.stackGet());
    }

    public static GLFWImage mallocStack(MemoryStack memoryStack) {
        return GLFWImage.wrap(GLFWImage.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static GLFWImage callocStack(MemoryStack memoryStack) {
        return GLFWImage.wrap(GLFWImage.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return GLFWImage.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return GLFWImage.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return GLFWImage.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return GLFWImage.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int nwidth(long l) {
        return UNSAFE.getInt(null, l + (long)WIDTH);
    }

    public static int nheight(long l) {
        return UNSAFE.getInt(null, l + (long)HEIGHT);
    }

    public static ByteBuffer npixels(long l, int n) {
        return MemoryUtil.memByteBuffer(MemoryUtil.memGetAddress(l + (long)PIXELS), n);
    }

    public static void nwidth(long l, int n) {
        UNSAFE.putInt(null, l + (long)WIDTH, n);
    }

    public static void nheight(long l, int n) {
        UNSAFE.putInt(null, l + (long)HEIGHT, n);
    }

    public static void npixels(long l, ByteBuffer byteBuffer) {
        MemoryUtil.memPutAddress(l + (long)PIXELS, MemoryUtil.memAddress(byteBuffer));
    }

    public static void validate(long l) {
        Checks.check(MemoryUtil.memGetAddress(l + (long)PIXELS));
    }

    public static void validate(long l, int n) {
        for (int i = 0; i < n; ++i) {
            GLFWImage.validate(l + Integer.toUnsignedLong(i) * (long)SIZEOF);
        }
    }

    static {
        Struct.Layout layout = GLFWImage.__struct(GLFWImage.__member(4), GLFWImage.__member(4), GLFWImage.__member(POINTER_SIZE));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        WIDTH = layout.offsetof(0);
        HEIGHT = layout.offsetof(1);
        PIXELS = layout.offsetof(2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<GLFWImage, Buffer>
    implements NativeResource {
        private static final GLFWImage ELEMENT_FACTORY = GLFWImage.create(-1L);

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
        protected GLFWImage getElementFactory() {
            return ELEMENT_FACTORY;
        }

        public int width() {
            return GLFWImage.nwidth(this.address());
        }

        public int height() {
            return GLFWImage.nheight(this.address());
        }

        @NativeType(value="unsigned char *")
        public ByteBuffer pixels(int n) {
            return GLFWImage.npixels(this.address(), n);
        }

        public Buffer width(int n) {
            GLFWImage.nwidth(this.address(), n);
            return this;
        }

        public Buffer height(int n) {
            GLFWImage.nheight(this.address(), n);
            return this;
        }

        public Buffer pixels(@NativeType(value="unsigned char *") ByteBuffer byteBuffer) {
            GLFWImage.npixels(this.address(), byteBuffer);
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

