/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.linux;

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
import org.lwjgl.system.linux.Visual;

public class XVisualInfo
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int VISUAL;
    public static final int VISUALID;
    public static final int SCREEN;
    public static final int DEPTH;
    public static final int CLASS;
    public static final int RED_MASK;
    public static final int GREEN_MASK;
    public static final int BLUE_MASK;
    public static final int COLORMAP_SIZE;
    public static final int BITS_PER_RGB;

    public XVisualInfo(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), XVisualInfo.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="Visual *")
    public Visual visual() {
        return XVisualInfo.nvisual(this.address());
    }

    @NativeType(value="VisualID")
    public long visualid() {
        return XVisualInfo.nvisualid(this.address());
    }

    public int screen() {
        return XVisualInfo.nscreen(this.address());
    }

    public int depth() {
        return XVisualInfo.ndepth(this.address());
    }

    public int class$() {
        return XVisualInfo.nclass$(this.address());
    }

    @NativeType(value="unsigned long")
    public long red_mask() {
        return XVisualInfo.nred_mask(this.address());
    }

    @NativeType(value="unsigned long")
    public long green_mask() {
        return XVisualInfo.ngreen_mask(this.address());
    }

    @NativeType(value="unsigned long")
    public long blue_mask() {
        return XVisualInfo.nblue_mask(this.address());
    }

    public int colormap_size() {
        return XVisualInfo.ncolormap_size(this.address());
    }

    public int bits_per_rgb() {
        return XVisualInfo.nbits_per_rgb(this.address());
    }

    public XVisualInfo visual(@NativeType(value="Visual *") Visual visual) {
        XVisualInfo.nvisual(this.address(), visual);
        return this;
    }

    public XVisualInfo visualid(@NativeType(value="VisualID") long l) {
        XVisualInfo.nvisualid(this.address(), l);
        return this;
    }

    public XVisualInfo screen(int n) {
        XVisualInfo.nscreen(this.address(), n);
        return this;
    }

    public XVisualInfo depth(int n) {
        XVisualInfo.ndepth(this.address(), n);
        return this;
    }

    public XVisualInfo class$(int n) {
        XVisualInfo.nclass$(this.address(), n);
        return this;
    }

    public XVisualInfo red_mask(@NativeType(value="unsigned long") long l) {
        XVisualInfo.nred_mask(this.address(), l);
        return this;
    }

    public XVisualInfo green_mask(@NativeType(value="unsigned long") long l) {
        XVisualInfo.ngreen_mask(this.address(), l);
        return this;
    }

    public XVisualInfo blue_mask(@NativeType(value="unsigned long") long l) {
        XVisualInfo.nblue_mask(this.address(), l);
        return this;
    }

    public XVisualInfo colormap_size(int n) {
        XVisualInfo.ncolormap_size(this.address(), n);
        return this;
    }

    public XVisualInfo bits_per_rgb(int n) {
        XVisualInfo.nbits_per_rgb(this.address(), n);
        return this;
    }

    public XVisualInfo set(Visual visual, long l, int n, int n2, int n3, long l2, long l3, long l4, int n4, int n5) {
        this.visual(visual);
        this.visualid(l);
        this.screen(n);
        this.depth(n2);
        this.class$(n3);
        this.red_mask(l2);
        this.green_mask(l3);
        this.blue_mask(l4);
        this.colormap_size(n4);
        this.bits_per_rgb(n5);
        return this;
    }

    public XVisualInfo set(XVisualInfo xVisualInfo) {
        MemoryUtil.memCopy(xVisualInfo.address(), this.address(), SIZEOF);
        return this;
    }

    public static XVisualInfo malloc() {
        return XVisualInfo.wrap(XVisualInfo.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static XVisualInfo calloc() {
        return XVisualInfo.wrap(XVisualInfo.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static XVisualInfo create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return XVisualInfo.wrap(XVisualInfo.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static XVisualInfo create(long l) {
        return XVisualInfo.wrap(XVisualInfo.class, l);
    }

    @Nullable
    public static XVisualInfo createSafe(long l) {
        return l == 0L ? null : XVisualInfo.wrap(XVisualInfo.class, l);
    }

    public static Buffer malloc(int n) {
        return XVisualInfo.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(XVisualInfo.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return XVisualInfo.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = XVisualInfo.__create(n, SIZEOF);
        return XVisualInfo.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return XVisualInfo.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : XVisualInfo.wrap(Buffer.class, l, n);
    }

    public static XVisualInfo mallocStack() {
        return XVisualInfo.mallocStack(MemoryStack.stackGet());
    }

    public static XVisualInfo callocStack() {
        return XVisualInfo.callocStack(MemoryStack.stackGet());
    }

    public static XVisualInfo mallocStack(MemoryStack memoryStack) {
        return XVisualInfo.wrap(XVisualInfo.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static XVisualInfo callocStack(MemoryStack memoryStack) {
        return XVisualInfo.wrap(XVisualInfo.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return XVisualInfo.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return XVisualInfo.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return XVisualInfo.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return XVisualInfo.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static Visual nvisual(long l) {
        return Visual.create(MemoryUtil.memGetAddress(l + (long)VISUAL));
    }

    public static long nvisualid(long l) {
        return MemoryUtil.memGetAddress(l + (long)VISUALID);
    }

    public static int nscreen(long l) {
        return UNSAFE.getInt(null, l + (long)SCREEN);
    }

    public static int ndepth(long l) {
        return UNSAFE.getInt(null, l + (long)DEPTH);
    }

    public static int nclass$(long l) {
        return UNSAFE.getInt(null, l + (long)CLASS);
    }

    public static long nred_mask(long l) {
        return MemoryUtil.memGetAddress(l + (long)RED_MASK);
    }

    public static long ngreen_mask(long l) {
        return MemoryUtil.memGetAddress(l + (long)GREEN_MASK);
    }

    public static long nblue_mask(long l) {
        return MemoryUtil.memGetAddress(l + (long)BLUE_MASK);
    }

    public static int ncolormap_size(long l) {
        return UNSAFE.getInt(null, l + (long)COLORMAP_SIZE);
    }

    public static int nbits_per_rgb(long l) {
        return UNSAFE.getInt(null, l + (long)BITS_PER_RGB);
    }

    public static void nvisual(long l, Visual visual) {
        MemoryUtil.memPutAddress(l + (long)VISUAL, visual.address());
    }

    public static void nvisualid(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)VISUALID, l2);
    }

    public static void nscreen(long l, int n) {
        UNSAFE.putInt(null, l + (long)SCREEN, n);
    }

    public static void ndepth(long l, int n) {
        UNSAFE.putInt(null, l + (long)DEPTH, n);
    }

    public static void nclass$(long l, int n) {
        UNSAFE.putInt(null, l + (long)CLASS, n);
    }

    public static void nred_mask(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)RED_MASK, l2);
    }

    public static void ngreen_mask(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)GREEN_MASK, l2);
    }

    public static void nblue_mask(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)BLUE_MASK, l2);
    }

    public static void ncolormap_size(long l, int n) {
        UNSAFE.putInt(null, l + (long)COLORMAP_SIZE, n);
    }

    public static void nbits_per_rgb(long l, int n) {
        UNSAFE.putInt(null, l + (long)BITS_PER_RGB, n);
    }

    public static void validate(long l) {
        Checks.check(MemoryUtil.memGetAddress(l + (long)VISUAL));
    }

    public static void validate(long l, int n) {
        for (int i = 0; i < n; ++i) {
            XVisualInfo.validate(l + Integer.toUnsignedLong(i) * (long)SIZEOF);
        }
    }

    static {
        Struct.Layout layout = XVisualInfo.__struct(XVisualInfo.__member(POINTER_SIZE), XVisualInfo.__member(POINTER_SIZE), XVisualInfo.__member(4), XVisualInfo.__member(4), XVisualInfo.__member(4), XVisualInfo.__member(POINTER_SIZE), XVisualInfo.__member(POINTER_SIZE), XVisualInfo.__member(POINTER_SIZE), XVisualInfo.__member(4), XVisualInfo.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        VISUAL = layout.offsetof(0);
        VISUALID = layout.offsetof(1);
        SCREEN = layout.offsetof(2);
        DEPTH = layout.offsetof(3);
        CLASS = layout.offsetof(4);
        RED_MASK = layout.offsetof(5);
        GREEN_MASK = layout.offsetof(6);
        BLUE_MASK = layout.offsetof(7);
        COLORMAP_SIZE = layout.offsetof(8);
        BITS_PER_RGB = layout.offsetof(9);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<XVisualInfo, Buffer>
    implements NativeResource {
        private static final XVisualInfo ELEMENT_FACTORY = XVisualInfo.create(-1L);

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
        protected XVisualInfo getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="Visual *")
        public Visual visual() {
            return XVisualInfo.nvisual(this.address());
        }

        @NativeType(value="VisualID")
        public long visualid() {
            return XVisualInfo.nvisualid(this.address());
        }

        public int screen() {
            return XVisualInfo.nscreen(this.address());
        }

        public int depth() {
            return XVisualInfo.ndepth(this.address());
        }

        public int class$() {
            return XVisualInfo.nclass$(this.address());
        }

        @NativeType(value="unsigned long")
        public long red_mask() {
            return XVisualInfo.nred_mask(this.address());
        }

        @NativeType(value="unsigned long")
        public long green_mask() {
            return XVisualInfo.ngreen_mask(this.address());
        }

        @NativeType(value="unsigned long")
        public long blue_mask() {
            return XVisualInfo.nblue_mask(this.address());
        }

        public int colormap_size() {
            return XVisualInfo.ncolormap_size(this.address());
        }

        public int bits_per_rgb() {
            return XVisualInfo.nbits_per_rgb(this.address());
        }

        public Buffer visual(@NativeType(value="Visual *") Visual visual) {
            XVisualInfo.nvisual(this.address(), visual);
            return this;
        }

        public Buffer visualid(@NativeType(value="VisualID") long l) {
            XVisualInfo.nvisualid(this.address(), l);
            return this;
        }

        public Buffer screen(int n) {
            XVisualInfo.nscreen(this.address(), n);
            return this;
        }

        public Buffer depth(int n) {
            XVisualInfo.ndepth(this.address(), n);
            return this;
        }

        public Buffer class$(int n) {
            XVisualInfo.nclass$(this.address(), n);
            return this;
        }

        public Buffer red_mask(@NativeType(value="unsigned long") long l) {
            XVisualInfo.nred_mask(this.address(), l);
            return this;
        }

        public Buffer green_mask(@NativeType(value="unsigned long") long l) {
            XVisualInfo.ngreen_mask(this.address(), l);
            return this;
        }

        public Buffer blue_mask(@NativeType(value="unsigned long") long l) {
            XVisualInfo.nblue_mask(this.address(), l);
            return this;
        }

        public Buffer colormap_size(int n) {
            XVisualInfo.ncolormap_size(this.address(), n);
            return this;
        }

        public Buffer bits_per_rgb(int n) {
            XVisualInfo.nbits_per_rgb(this.address(), n);
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

