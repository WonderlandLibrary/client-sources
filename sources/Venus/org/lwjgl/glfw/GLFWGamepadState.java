/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
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

@NativeType(value="struct GLFWgamepadstate")
public class GLFWGamepadState
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int BUTTONS;
    public static final int AXES;

    public GLFWGamepadState(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), GLFWGamepadState.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="unsigned char[15]")
    public ByteBuffer buttons() {
        return GLFWGamepadState.nbuttons(this.address());
    }

    @NativeType(value="unsigned char")
    public byte buttons(int n) {
        return GLFWGamepadState.nbuttons(this.address(), n);
    }

    @NativeType(value="float[6]")
    public FloatBuffer axes() {
        return GLFWGamepadState.naxes(this.address());
    }

    public float axes(int n) {
        return GLFWGamepadState.naxes(this.address(), n);
    }

    public GLFWGamepadState buttons(@NativeType(value="unsigned char[15]") ByteBuffer byteBuffer) {
        GLFWGamepadState.nbuttons(this.address(), byteBuffer);
        return this;
    }

    public GLFWGamepadState buttons(int n, @NativeType(value="unsigned char") byte by) {
        GLFWGamepadState.nbuttons(this.address(), n, by);
        return this;
    }

    public GLFWGamepadState axes(@NativeType(value="float[6]") FloatBuffer floatBuffer) {
        GLFWGamepadState.naxes(this.address(), floatBuffer);
        return this;
    }

    public GLFWGamepadState axes(int n, float f) {
        GLFWGamepadState.naxes(this.address(), n, f);
        return this;
    }

    public GLFWGamepadState set(ByteBuffer byteBuffer, FloatBuffer floatBuffer) {
        this.buttons(byteBuffer);
        this.axes(floatBuffer);
        return this;
    }

    public GLFWGamepadState set(GLFWGamepadState gLFWGamepadState) {
        MemoryUtil.memCopy(gLFWGamepadState.address(), this.address(), SIZEOF);
        return this;
    }

    public static GLFWGamepadState malloc() {
        return GLFWGamepadState.wrap(GLFWGamepadState.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static GLFWGamepadState calloc() {
        return GLFWGamepadState.wrap(GLFWGamepadState.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static GLFWGamepadState create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return GLFWGamepadState.wrap(GLFWGamepadState.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static GLFWGamepadState create(long l) {
        return GLFWGamepadState.wrap(GLFWGamepadState.class, l);
    }

    @Nullable
    public static GLFWGamepadState createSafe(long l) {
        return l == 0L ? null : GLFWGamepadState.wrap(GLFWGamepadState.class, l);
    }

    public static Buffer malloc(int n) {
        return GLFWGamepadState.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(GLFWGamepadState.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return GLFWGamepadState.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = GLFWGamepadState.__create(n, SIZEOF);
        return GLFWGamepadState.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return GLFWGamepadState.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : GLFWGamepadState.wrap(Buffer.class, l, n);
    }

    public static GLFWGamepadState mallocStack() {
        return GLFWGamepadState.mallocStack(MemoryStack.stackGet());
    }

    public static GLFWGamepadState callocStack() {
        return GLFWGamepadState.callocStack(MemoryStack.stackGet());
    }

    public static GLFWGamepadState mallocStack(MemoryStack memoryStack) {
        return GLFWGamepadState.wrap(GLFWGamepadState.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static GLFWGamepadState callocStack(MemoryStack memoryStack) {
        return GLFWGamepadState.wrap(GLFWGamepadState.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return GLFWGamepadState.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return GLFWGamepadState.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return GLFWGamepadState.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return GLFWGamepadState.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static ByteBuffer nbuttons(long l) {
        return MemoryUtil.memByteBuffer(l + (long)BUTTONS, 15);
    }

    public static byte nbuttons(long l, int n) {
        return UNSAFE.getByte(null, l + (long)BUTTONS + Checks.check(n, 15) * 1L);
    }

    public static FloatBuffer naxes(long l) {
        return MemoryUtil.memFloatBuffer(l + (long)AXES, 6);
    }

    public static float naxes(long l, int n) {
        return UNSAFE.getFloat(null, l + (long)AXES + Checks.check(n, 6) * 4L);
    }

    public static void nbuttons(long l, ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkGT(byteBuffer, 15);
        }
        MemoryUtil.memCopy(MemoryUtil.memAddress(byteBuffer), l + (long)BUTTONS, byteBuffer.remaining() * 1);
    }

    public static void nbuttons(long l, int n, byte by) {
        UNSAFE.putByte(null, l + (long)BUTTONS + Checks.check(n, 15) * 1L, by);
    }

    public static void naxes(long l, FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.checkGT(floatBuffer, 6);
        }
        MemoryUtil.memCopy(MemoryUtil.memAddress(floatBuffer), l + (long)AXES, floatBuffer.remaining() * 4);
    }

    public static void naxes(long l, int n, float f) {
        UNSAFE.putFloat(null, l + (long)AXES + Checks.check(n, 6) * 4L, f);
    }

    static {
        Struct.Layout layout = GLFWGamepadState.__struct(GLFWGamepadState.__array(1, 15), GLFWGamepadState.__array(4, 6));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        BUTTONS = layout.offsetof(0);
        AXES = layout.offsetof(1);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<GLFWGamepadState, Buffer>
    implements NativeResource {
        private static final GLFWGamepadState ELEMENT_FACTORY = GLFWGamepadState.create(-1L);

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
        protected GLFWGamepadState getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="unsigned char[15]")
        public ByteBuffer buttons() {
            return GLFWGamepadState.nbuttons(this.address());
        }

        @NativeType(value="unsigned char")
        public byte buttons(int n) {
            return GLFWGamepadState.nbuttons(this.address(), n);
        }

        @NativeType(value="float[6]")
        public FloatBuffer axes() {
            return GLFWGamepadState.naxes(this.address());
        }

        public float axes(int n) {
            return GLFWGamepadState.naxes(this.address(), n);
        }

        public Buffer buttons(@NativeType(value="unsigned char[15]") ByteBuffer byteBuffer) {
            GLFWGamepadState.nbuttons(this.address(), byteBuffer);
            return this;
        }

        public Buffer buttons(int n, @NativeType(value="unsigned char") byte by) {
            GLFWGamepadState.nbuttons(this.address(), n, by);
            return this;
        }

        public Buffer axes(@NativeType(value="float[6]") FloatBuffer floatBuffer) {
            GLFWGamepadState.naxes(this.address(), floatBuffer);
            return this;
        }

        public Buffer axes(int n, float f) {
            GLFWGamepadState.naxes(this.address(), n, f);
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

