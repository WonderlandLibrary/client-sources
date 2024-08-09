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
import org.lwjgl.system.windows.POINT;

public class MSG
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int HWND;
    public static final int MESSAGE;
    public static final int WPARAM;
    public static final int LPARAM;
    public static final int TIME;
    public static final int PT;

    public MSG(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), MSG.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="HWND")
    public long hwnd() {
        return MSG.nhwnd(this.address());
    }

    @NativeType(value="UINT")
    public int message() {
        return MSG.nmessage(this.address());
    }

    @NativeType(value="WPARAM")
    public long wParam() {
        return MSG.nwParam(this.address());
    }

    @NativeType(value="LPARAM")
    public long lParam() {
        return MSG.nlParam(this.address());
    }

    @NativeType(value="DWORD")
    public int time() {
        return MSG.ntime(this.address());
    }

    public POINT pt() {
        return MSG.npt(this.address());
    }

    public MSG pt(Consumer<POINT> consumer) {
        consumer.accept(this.pt());
        return this;
    }

    public MSG hwnd(@NativeType(value="HWND") long l) {
        MSG.nhwnd(this.address(), l);
        return this;
    }

    public MSG message(@NativeType(value="UINT") int n) {
        MSG.nmessage(this.address(), n);
        return this;
    }

    public MSG wParam(@NativeType(value="WPARAM") long l) {
        MSG.nwParam(this.address(), l);
        return this;
    }

    public MSG lParam(@NativeType(value="LPARAM") long l) {
        MSG.nlParam(this.address(), l);
        return this;
    }

    public MSG time(@NativeType(value="DWORD") int n) {
        MSG.ntime(this.address(), n);
        return this;
    }

    public MSG pt(POINT pOINT) {
        MSG.npt(this.address(), pOINT);
        return this;
    }

    public MSG set(long l, int n, long l2, long l3, int n2, POINT pOINT) {
        this.hwnd(l);
        this.message(n);
        this.wParam(l2);
        this.lParam(l3);
        this.time(n2);
        this.pt(pOINT);
        return this;
    }

    public MSG set(MSG mSG) {
        MemoryUtil.memCopy(mSG.address(), this.address(), SIZEOF);
        return this;
    }

    public static MSG malloc() {
        return MSG.wrap(MSG.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static MSG calloc() {
        return MSG.wrap(MSG.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static MSG create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return MSG.wrap(MSG.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static MSG create(long l) {
        return MSG.wrap(MSG.class, l);
    }

    @Nullable
    public static MSG createSafe(long l) {
        return l == 0L ? null : MSG.wrap(MSG.class, l);
    }

    public static Buffer malloc(int n) {
        return MSG.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(MSG.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return MSG.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = MSG.__create(n, SIZEOF);
        return MSG.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return MSG.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : MSG.wrap(Buffer.class, l, n);
    }

    public static MSG mallocStack() {
        return MSG.mallocStack(MemoryStack.stackGet());
    }

    public static MSG callocStack() {
        return MSG.callocStack(MemoryStack.stackGet());
    }

    public static MSG mallocStack(MemoryStack memoryStack) {
        return MSG.wrap(MSG.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static MSG callocStack(MemoryStack memoryStack) {
        return MSG.wrap(MSG.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return MSG.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return MSG.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return MSG.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return MSG.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static long nhwnd(long l) {
        return MemoryUtil.memGetAddress(l + (long)HWND);
    }

    public static int nmessage(long l) {
        return UNSAFE.getInt(null, l + (long)MESSAGE);
    }

    public static long nwParam(long l) {
        return MemoryUtil.memGetAddress(l + (long)WPARAM);
    }

    public static long nlParam(long l) {
        return MemoryUtil.memGetAddress(l + (long)LPARAM);
    }

    public static int ntime(long l) {
        return UNSAFE.getInt(null, l + (long)TIME);
    }

    public static POINT npt(long l) {
        return POINT.create(l + (long)PT);
    }

    public static void nhwnd(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)HWND, l2);
    }

    public static void nmessage(long l, int n) {
        UNSAFE.putInt(null, l + (long)MESSAGE, n);
    }

    public static void nwParam(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)WPARAM, l2);
    }

    public static void nlParam(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)LPARAM, l2);
    }

    public static void ntime(long l, int n) {
        UNSAFE.putInt(null, l + (long)TIME, n);
    }

    public static void npt(long l, POINT pOINT) {
        MemoryUtil.memCopy(pOINT.address(), l + (long)PT, POINT.SIZEOF);
    }

    static {
        Struct.Layout layout = MSG.__struct(MSG.__member(POINTER_SIZE), MSG.__member(4), MSG.__member(POINTER_SIZE), MSG.__member(POINTER_SIZE), MSG.__member(4), MSG.__member(POINT.SIZEOF, POINT.ALIGNOF));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        HWND = layout.offsetof(0);
        MESSAGE = layout.offsetof(1);
        WPARAM = layout.offsetof(2);
        LPARAM = layout.offsetof(3);
        TIME = layout.offsetof(4);
        PT = layout.offsetof(5);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<MSG, Buffer>
    implements NativeResource {
        private static final MSG ELEMENT_FACTORY = MSG.create(-1L);

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
        protected MSG getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="HWND")
        public long hwnd() {
            return MSG.nhwnd(this.address());
        }

        @NativeType(value="UINT")
        public int message() {
            return MSG.nmessage(this.address());
        }

        @NativeType(value="WPARAM")
        public long wParam() {
            return MSG.nwParam(this.address());
        }

        @NativeType(value="LPARAM")
        public long lParam() {
            return MSG.nlParam(this.address());
        }

        @NativeType(value="DWORD")
        public int time() {
            return MSG.ntime(this.address());
        }

        public POINT pt() {
            return MSG.npt(this.address());
        }

        public Buffer pt(Consumer<POINT> consumer) {
            consumer.accept(this.pt());
            return this;
        }

        public Buffer hwnd(@NativeType(value="HWND") long l) {
            MSG.nhwnd(this.address(), l);
            return this;
        }

        public Buffer message(@NativeType(value="UINT") int n) {
            MSG.nmessage(this.address(), n);
            return this;
        }

        public Buffer wParam(@NativeType(value="WPARAM") long l) {
            MSG.nwParam(this.address(), l);
            return this;
        }

        public Buffer lParam(@NativeType(value="LPARAM") long l) {
            MSG.nlParam(this.address(), l);
            return this;
        }

        public Buffer time(@NativeType(value="DWORD") int n) {
            MSG.ntime(this.address(), n);
            return this;
        }

        public Buffer pt(POINT pOINT) {
            MSG.npt(this.address(), pOINT);
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

