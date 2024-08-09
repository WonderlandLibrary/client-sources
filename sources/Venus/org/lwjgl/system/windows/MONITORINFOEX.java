/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.windows;

import java.nio.ByteBuffer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;
import org.lwjgl.system.windows.RECT;

public class MONITORINFOEX
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int CBSIZE;
    public static final int RCMONITOR;
    public static final int RCWORK;
    public static final int DWFLAGS;
    public static final int SZDEVICE;

    public MONITORINFOEX(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), MONITORINFOEX.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="DWORD")
    public int cbSize() {
        return MONITORINFOEX.ncbSize(this.address());
    }

    public RECT rcMonitor() {
        return MONITORINFOEX.nrcMonitor(this.address());
    }

    public MONITORINFOEX rcMonitor(Consumer<RECT> consumer) {
        consumer.accept(this.rcMonitor());
        return this;
    }

    public RECT rcWork() {
        return MONITORINFOEX.nrcWork(this.address());
    }

    public MONITORINFOEX rcWork(Consumer<RECT> consumer) {
        consumer.accept(this.rcWork());
        return this;
    }

    @NativeType(value="DWORD")
    public int dwFlags() {
        return MONITORINFOEX.ndwFlags(this.address());
    }

    @NativeType(value="TCHAR[32]")
    public ByteBuffer szDevice() {
        return MONITORINFOEX.nszDevice(this.address());
    }

    @NativeType(value="TCHAR[32]")
    public String szDeviceString() {
        return MONITORINFOEX.nszDeviceString(this.address());
    }

    public MONITORINFOEX cbSize(@NativeType(value="DWORD") int n) {
        MONITORINFOEX.ncbSize(this.address(), n);
        return this;
    }

    public MONITORINFOEX set(MONITORINFOEX mONITORINFOEX) {
        MemoryUtil.memCopy(mONITORINFOEX.address(), this.address(), SIZEOF);
        return this;
    }

    public static MONITORINFOEX malloc() {
        return MONITORINFOEX.wrap(MONITORINFOEX.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static MONITORINFOEX calloc() {
        return MONITORINFOEX.wrap(MONITORINFOEX.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static MONITORINFOEX create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return MONITORINFOEX.wrap(MONITORINFOEX.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static MONITORINFOEX create(long l) {
        return MONITORINFOEX.wrap(MONITORINFOEX.class, l);
    }

    @Nullable
    public static MONITORINFOEX createSafe(long l) {
        return l == 0L ? null : MONITORINFOEX.wrap(MONITORINFOEX.class, l);
    }

    public static Buffer malloc(int n) {
        return MONITORINFOEX.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(MONITORINFOEX.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return MONITORINFOEX.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = MONITORINFOEX.__create(n, SIZEOF);
        return MONITORINFOEX.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return MONITORINFOEX.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : MONITORINFOEX.wrap(Buffer.class, l, n);
    }

    public static MONITORINFOEX mallocStack() {
        return MONITORINFOEX.mallocStack(MemoryStack.stackGet());
    }

    public static MONITORINFOEX callocStack() {
        return MONITORINFOEX.callocStack(MemoryStack.stackGet());
    }

    public static MONITORINFOEX mallocStack(MemoryStack memoryStack) {
        return MONITORINFOEX.wrap(MONITORINFOEX.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static MONITORINFOEX callocStack(MemoryStack memoryStack) {
        return MONITORINFOEX.wrap(MONITORINFOEX.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return MONITORINFOEX.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return MONITORINFOEX.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return MONITORINFOEX.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return MONITORINFOEX.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int ncbSize(long l) {
        return UNSAFE.getInt(null, l + (long)CBSIZE);
    }

    public static RECT nrcMonitor(long l) {
        return RECT.create(l + (long)RCMONITOR);
    }

    public static RECT nrcWork(long l) {
        return RECT.create(l + (long)RCWORK);
    }

    public static int ndwFlags(long l) {
        return UNSAFE.getInt(null, l + (long)DWFLAGS);
    }

    public static ByteBuffer nszDevice(long l) {
        return MemoryUtil.memByteBuffer(l + (long)SZDEVICE, 64);
    }

    public static String nszDeviceString(long l) {
        return MemoryUtil.memUTF16(l + (long)SZDEVICE);
    }

    public static void ncbSize(long l, int n) {
        UNSAFE.putInt(null, l + (long)CBSIZE, n);
    }

    static {
        Struct.Layout layout = MONITORINFOEX.__struct(MONITORINFOEX.__member(4), MONITORINFOEX.__member(RECT.SIZEOF, RECT.ALIGNOF), MONITORINFOEX.__member(RECT.SIZEOF, RECT.ALIGNOF), MONITORINFOEX.__member(4), MONITORINFOEX.__array(2, 32));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        CBSIZE = layout.offsetof(0);
        RCMONITOR = layout.offsetof(1);
        RCWORK = layout.offsetof(2);
        DWFLAGS = layout.offsetof(3);
        SZDEVICE = layout.offsetof(4);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<MONITORINFOEX, Buffer>
    implements NativeResource {
        private static final MONITORINFOEX ELEMENT_FACTORY = MONITORINFOEX.create(-1L);

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
        protected MONITORINFOEX getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="DWORD")
        public int cbSize() {
            return MONITORINFOEX.ncbSize(this.address());
        }

        public RECT rcMonitor() {
            return MONITORINFOEX.nrcMonitor(this.address());
        }

        public Buffer rcMonitor(Consumer<RECT> consumer) {
            consumer.accept(this.rcMonitor());
            return this;
        }

        public RECT rcWork() {
            return MONITORINFOEX.nrcWork(this.address());
        }

        public Buffer rcWork(Consumer<RECT> consumer) {
            consumer.accept(this.rcWork());
            return this;
        }

        @NativeType(value="DWORD")
        public int dwFlags() {
            return MONITORINFOEX.ndwFlags(this.address());
        }

        @NativeType(value="TCHAR[32]")
        public ByteBuffer szDevice() {
            return MONITORINFOEX.nszDevice(this.address());
        }

        @NativeType(value="TCHAR[32]")
        public String szDeviceString() {
            return MONITORINFOEX.nszDeviceString(this.address());
        }

        public Buffer cbSize(@NativeType(value="DWORD") int n) {
            MONITORINFOEX.ncbSize(this.address(), n);
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

