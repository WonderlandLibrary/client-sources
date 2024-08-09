/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.windows;

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

public class TOUCHINPUT
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int X;
    public static final int Y;
    public static final int HSOURCE;
    public static final int DWID;
    public static final int DWFLAGS;
    public static final int DWMASK;
    public static final int DWTIME;
    public static final int DWEXTRAINFO;
    public static final int CXCONTACT;
    public static final int CYCONTACT;

    public TOUCHINPUT(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), TOUCHINPUT.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="LONG")
    public int x() {
        return TOUCHINPUT.nx(this.address());
    }

    @NativeType(value="LONG")
    public int y() {
        return TOUCHINPUT.ny(this.address());
    }

    @NativeType(value="HANDLE")
    public long hSource() {
        return TOUCHINPUT.nhSource(this.address());
    }

    @NativeType(value="DWORD")
    public int dwID() {
        return TOUCHINPUT.ndwID(this.address());
    }

    @NativeType(value="DWORD")
    public int dwFlags() {
        return TOUCHINPUT.ndwFlags(this.address());
    }

    @NativeType(value="DWORD")
    public int dwMask() {
        return TOUCHINPUT.ndwMask(this.address());
    }

    @NativeType(value="DWORD")
    public int dwTime() {
        return TOUCHINPUT.ndwTime(this.address());
    }

    @NativeType(value="ULONG_PTR")
    public long dwExtraInfo() {
        return TOUCHINPUT.ndwExtraInfo(this.address());
    }

    @NativeType(value="DWORD")
    public int cxContact() {
        return TOUCHINPUT.ncxContact(this.address());
    }

    @NativeType(value="DWORD")
    public int cyContact() {
        return TOUCHINPUT.ncyContact(this.address());
    }

    public static TOUCHINPUT malloc() {
        return TOUCHINPUT.wrap(TOUCHINPUT.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static TOUCHINPUT calloc() {
        return TOUCHINPUT.wrap(TOUCHINPUT.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static TOUCHINPUT create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return TOUCHINPUT.wrap(TOUCHINPUT.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static TOUCHINPUT create(long l) {
        return TOUCHINPUT.wrap(TOUCHINPUT.class, l);
    }

    @Nullable
    public static TOUCHINPUT createSafe(long l) {
        return l == 0L ? null : TOUCHINPUT.wrap(TOUCHINPUT.class, l);
    }

    public static Buffer malloc(int n) {
        return TOUCHINPUT.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(TOUCHINPUT.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return TOUCHINPUT.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = TOUCHINPUT.__create(n, SIZEOF);
        return TOUCHINPUT.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return TOUCHINPUT.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : TOUCHINPUT.wrap(Buffer.class, l, n);
    }

    public static TOUCHINPUT mallocStack() {
        return TOUCHINPUT.mallocStack(MemoryStack.stackGet());
    }

    public static TOUCHINPUT callocStack() {
        return TOUCHINPUT.callocStack(MemoryStack.stackGet());
    }

    public static TOUCHINPUT mallocStack(MemoryStack memoryStack) {
        return TOUCHINPUT.wrap(TOUCHINPUT.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static TOUCHINPUT callocStack(MemoryStack memoryStack) {
        return TOUCHINPUT.wrap(TOUCHINPUT.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return TOUCHINPUT.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return TOUCHINPUT.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return TOUCHINPUT.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return TOUCHINPUT.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int nx(long l) {
        return UNSAFE.getInt(null, l + (long)X);
    }

    public static int ny(long l) {
        return UNSAFE.getInt(null, l + (long)Y);
    }

    public static long nhSource(long l) {
        return MemoryUtil.memGetAddress(l + (long)HSOURCE);
    }

    public static int ndwID(long l) {
        return UNSAFE.getInt(null, l + (long)DWID);
    }

    public static int ndwFlags(long l) {
        return UNSAFE.getInt(null, l + (long)DWFLAGS);
    }

    public static int ndwMask(long l) {
        return UNSAFE.getInt(null, l + (long)DWMASK);
    }

    public static int ndwTime(long l) {
        return UNSAFE.getInt(null, l + (long)DWTIME);
    }

    public static long ndwExtraInfo(long l) {
        return MemoryUtil.memGetAddress(l + (long)DWEXTRAINFO);
    }

    public static int ncxContact(long l) {
        return UNSAFE.getInt(null, l + (long)CXCONTACT);
    }

    public static int ncyContact(long l) {
        return UNSAFE.getInt(null, l + (long)CYCONTACT);
    }

    static {
        Struct.Layout layout = TOUCHINPUT.__struct(TOUCHINPUT.__member(4), TOUCHINPUT.__member(4), TOUCHINPUT.__member(POINTER_SIZE), TOUCHINPUT.__member(4), TOUCHINPUT.__member(4), TOUCHINPUT.__member(4), TOUCHINPUT.__member(4), TOUCHINPUT.__member(POINTER_SIZE), TOUCHINPUT.__member(4), TOUCHINPUT.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        X = layout.offsetof(0);
        Y = layout.offsetof(1);
        HSOURCE = layout.offsetof(2);
        DWID = layout.offsetof(3);
        DWFLAGS = layout.offsetof(4);
        DWMASK = layout.offsetof(5);
        DWTIME = layout.offsetof(6);
        DWEXTRAINFO = layout.offsetof(7);
        CXCONTACT = layout.offsetof(8);
        CYCONTACT = layout.offsetof(9);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<TOUCHINPUT, Buffer>
    implements NativeResource {
        private static final TOUCHINPUT ELEMENT_FACTORY = TOUCHINPUT.create(-1L);

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
        protected TOUCHINPUT getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="LONG")
        public int x() {
            return TOUCHINPUT.nx(this.address());
        }

        @NativeType(value="LONG")
        public int y() {
            return TOUCHINPUT.ny(this.address());
        }

        @NativeType(value="HANDLE")
        public long hSource() {
            return TOUCHINPUT.nhSource(this.address());
        }

        @NativeType(value="DWORD")
        public int dwID() {
            return TOUCHINPUT.ndwID(this.address());
        }

        @NativeType(value="DWORD")
        public int dwFlags() {
            return TOUCHINPUT.ndwFlags(this.address());
        }

        @NativeType(value="DWORD")
        public int dwMask() {
            return TOUCHINPUT.ndwMask(this.address());
        }

        @NativeType(value="DWORD")
        public int dwTime() {
            return TOUCHINPUT.ndwTime(this.address());
        }

        @NativeType(value="ULONG_PTR")
        public long dwExtraInfo() {
            return TOUCHINPUT.ndwExtraInfo(this.address());
        }

        @NativeType(value="DWORD")
        public int cxContact() {
            return TOUCHINPUT.ncxContact(this.address());
        }

        @NativeType(value="DWORD")
        public int cyContact() {
            return TOUCHINPUT.ncyContact(this.address());
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

