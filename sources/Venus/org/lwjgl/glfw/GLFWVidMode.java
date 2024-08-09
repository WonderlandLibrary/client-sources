/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.glfw;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType(value="struct GLFWvidmode")
public class GLFWVidMode
extends Struct {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int WIDTH;
    public static final int HEIGHT;
    public static final int REDBITS;
    public static final int GREENBITS;
    public static final int BLUEBITS;
    public static final int REFRESHRATE;

    public GLFWVidMode(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), GLFWVidMode.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    public int width() {
        return GLFWVidMode.nwidth(this.address());
    }

    public int height() {
        return GLFWVidMode.nheight(this.address());
    }

    public int redBits() {
        return GLFWVidMode.nredBits(this.address());
    }

    public int greenBits() {
        return GLFWVidMode.ngreenBits(this.address());
    }

    public int blueBits() {
        return GLFWVidMode.nblueBits(this.address());
    }

    public int refreshRate() {
        return GLFWVidMode.nrefreshRate(this.address());
    }

    public static GLFWVidMode create(long l) {
        return GLFWVidMode.wrap(GLFWVidMode.class, l);
    }

    @Nullable
    public static GLFWVidMode createSafe(long l) {
        return l == 0L ? null : GLFWVidMode.wrap(GLFWVidMode.class, l);
    }

    public static Buffer create(long l, int n) {
        return GLFWVidMode.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : GLFWVidMode.wrap(Buffer.class, l, n);
    }

    public static int nwidth(long l) {
        return UNSAFE.getInt(null, l + (long)WIDTH);
    }

    public static int nheight(long l) {
        return UNSAFE.getInt(null, l + (long)HEIGHT);
    }

    public static int nredBits(long l) {
        return UNSAFE.getInt(null, l + (long)REDBITS);
    }

    public static int ngreenBits(long l) {
        return UNSAFE.getInt(null, l + (long)GREENBITS);
    }

    public static int nblueBits(long l) {
        return UNSAFE.getInt(null, l + (long)BLUEBITS);
    }

    public static int nrefreshRate(long l) {
        return UNSAFE.getInt(null, l + (long)REFRESHRATE);
    }

    static {
        Struct.Layout layout = GLFWVidMode.__struct(GLFWVidMode.__member(4), GLFWVidMode.__member(4), GLFWVidMode.__member(4), GLFWVidMode.__member(4), GLFWVidMode.__member(4), GLFWVidMode.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        WIDTH = layout.offsetof(0);
        HEIGHT = layout.offsetof(1);
        REDBITS = layout.offsetof(2);
        GREENBITS = layout.offsetof(3);
        BLUEBITS = layout.offsetof(4);
        REFRESHRATE = layout.offsetof(5);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<GLFWVidMode, Buffer> {
        private static final GLFWVidMode ELEMENT_FACTORY = GLFWVidMode.create(-1L);

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
        protected GLFWVidMode getElementFactory() {
            return ELEMENT_FACTORY;
        }

        public int width() {
            return GLFWVidMode.nwidth(this.address());
        }

        public int height() {
            return GLFWVidMode.nheight(this.address());
        }

        public int redBits() {
            return GLFWVidMode.nredBits(this.address());
        }

        public int greenBits() {
            return GLFWVidMode.ngreenBits(this.address());
        }

        public int blueBits() {
            return GLFWVidMode.nblueBits(this.address());
        }

        public int refreshRate() {
            return GLFWVidMode.nrefreshRate(this.address());
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

