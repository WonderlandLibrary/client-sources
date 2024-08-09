/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class GLXStereoNotifyEventEXT
extends Struct {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int TYPE;
    public static final int SERIAL;
    public static final int SEND_EVENT;
    public static final int DISPLAY;
    public static final int EXTENSION;
    public static final int EVTYPE;
    public static final int WINDOW;
    public static final int STEREO_TREE;

    public GLXStereoNotifyEventEXT(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), GLXStereoNotifyEventEXT.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    public int type() {
        return GLXStereoNotifyEventEXT.ntype(this.address());
    }

    @NativeType(value="unsigned long")
    public long serial() {
        return GLXStereoNotifyEventEXT.nserial(this.address());
    }

    @NativeType(value="Bool")
    public boolean send_event() {
        return GLXStereoNotifyEventEXT.nsend_event(this.address()) != 0;
    }

    @NativeType(value="Display *")
    public long display() {
        return GLXStereoNotifyEventEXT.ndisplay(this.address());
    }

    public int extension() {
        return GLXStereoNotifyEventEXT.nextension(this.address());
    }

    public int evtype() {
        return GLXStereoNotifyEventEXT.nevtype(this.address());
    }

    @NativeType(value="GLXDrawable")
    public long window() {
        return GLXStereoNotifyEventEXT.nwindow(this.address());
    }

    @NativeType(value="Bool")
    public boolean stereo_tree() {
        return GLXStereoNotifyEventEXT.nstereo_tree(this.address()) != 0;
    }

    public static GLXStereoNotifyEventEXT create(long l) {
        return GLXStereoNotifyEventEXT.wrap(GLXStereoNotifyEventEXT.class, l);
    }

    @Nullable
    public static GLXStereoNotifyEventEXT createSafe(long l) {
        return l == 0L ? null : GLXStereoNotifyEventEXT.wrap(GLXStereoNotifyEventEXT.class, l);
    }

    public static Buffer create(long l, int n) {
        return GLXStereoNotifyEventEXT.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : GLXStereoNotifyEventEXT.wrap(Buffer.class, l, n);
    }

    public static int ntype(long l) {
        return UNSAFE.getInt(null, l + (long)TYPE);
    }

    public static long nserial(long l) {
        return MemoryUtil.memGetAddress(l + (long)SERIAL);
    }

    public static int nsend_event(long l) {
        return UNSAFE.getInt(null, l + (long)SEND_EVENT);
    }

    public static long ndisplay(long l) {
        return MemoryUtil.memGetAddress(l + (long)DISPLAY);
    }

    public static int nextension(long l) {
        return UNSAFE.getInt(null, l + (long)EXTENSION);
    }

    public static int nevtype(long l) {
        return UNSAFE.getInt(null, l + (long)EVTYPE);
    }

    public static long nwindow(long l) {
        return MemoryUtil.memGetAddress(l + (long)WINDOW);
    }

    public static int nstereo_tree(long l) {
        return UNSAFE.getInt(null, l + (long)STEREO_TREE);
    }

    static {
        Struct.Layout layout = GLXStereoNotifyEventEXT.__struct(GLXStereoNotifyEventEXT.__member(4), GLXStereoNotifyEventEXT.__member(POINTER_SIZE), GLXStereoNotifyEventEXT.__member(4), GLXStereoNotifyEventEXT.__member(POINTER_SIZE), GLXStereoNotifyEventEXT.__member(4), GLXStereoNotifyEventEXT.__member(4), GLXStereoNotifyEventEXT.__member(POINTER_SIZE), GLXStereoNotifyEventEXT.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        TYPE = layout.offsetof(0);
        SERIAL = layout.offsetof(1);
        SEND_EVENT = layout.offsetof(2);
        DISPLAY = layout.offsetof(3);
        EXTENSION = layout.offsetof(4);
        EVTYPE = layout.offsetof(5);
        WINDOW = layout.offsetof(6);
        STEREO_TREE = layout.offsetof(7);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<GLXStereoNotifyEventEXT, Buffer> {
        private static final GLXStereoNotifyEventEXT ELEMENT_FACTORY = GLXStereoNotifyEventEXT.create(-1L);

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
        protected GLXStereoNotifyEventEXT getElementFactory() {
            return ELEMENT_FACTORY;
        }

        public int type() {
            return GLXStereoNotifyEventEXT.ntype(this.address());
        }

        @NativeType(value="unsigned long")
        public long serial() {
            return GLXStereoNotifyEventEXT.nserial(this.address());
        }

        @NativeType(value="Bool")
        public boolean send_event() {
            return GLXStereoNotifyEventEXT.nsend_event(this.address()) != 0;
        }

        @NativeType(value="Display *")
        public long display() {
            return GLXStereoNotifyEventEXT.ndisplay(this.address());
        }

        public int extension() {
            return GLXStereoNotifyEventEXT.nextension(this.address());
        }

        public int evtype() {
            return GLXStereoNotifyEventEXT.nevtype(this.address());
        }

        @NativeType(value="GLXDrawable")
        public long window() {
            return GLXStereoNotifyEventEXT.nwindow(this.address());
        }

        @NativeType(value="Bool")
        public boolean stereo_tree() {
            return GLXStereoNotifyEventEXT.nstereo_tree(this.address()) != 0;
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

