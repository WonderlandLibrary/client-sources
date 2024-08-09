/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.windows;

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
import org.lwjgl.system.windows.WindowProc;
import org.lwjgl.system.windows.WindowProcI;

public class WNDCLASSEX
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int CBSIZE;
    public static final int STYLE;
    public static final int LPFNWNDPROC;
    public static final int CBCLSEXTRA;
    public static final int CBWNDEXTRA;
    public static final int HINSTANCE;
    public static final int HICON;
    public static final int HCURSOR;
    public static final int HBRBACKGROUND;
    public static final int LPSZMENUNAME;
    public static final int LPSZCLASSNAME;
    public static final int HICONSM;

    public WNDCLASSEX(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), WNDCLASSEX.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="UINT")
    public int cbSize() {
        return WNDCLASSEX.ncbSize(this.address());
    }

    @NativeType(value="UINT")
    public int style() {
        return WNDCLASSEX.nstyle(this.address());
    }

    @NativeType(value="WNDPROC")
    public WindowProc lpfnWndProc() {
        return WNDCLASSEX.nlpfnWndProc(this.address());
    }

    public int cbClsExtra() {
        return WNDCLASSEX.ncbClsExtra(this.address());
    }

    public int cbWndExtra() {
        return WNDCLASSEX.ncbWndExtra(this.address());
    }

    @NativeType(value="HINSTANCE")
    public long hInstance() {
        return WNDCLASSEX.nhInstance(this.address());
    }

    @NativeType(value="HICON")
    public long hIcon() {
        return WNDCLASSEX.nhIcon(this.address());
    }

    @NativeType(value="HCURSOR")
    public long hCursor() {
        return WNDCLASSEX.nhCursor(this.address());
    }

    @NativeType(value="HBRUSH")
    public long hbrBackground() {
        return WNDCLASSEX.nhbrBackground(this.address());
    }

    @Nullable
    @NativeType(value="LPCTSTR")
    public ByteBuffer lpszMenuName() {
        return WNDCLASSEX.nlpszMenuName(this.address());
    }

    @Nullable
    @NativeType(value="LPCTSTR")
    public String lpszMenuNameString() {
        return WNDCLASSEX.nlpszMenuNameString(this.address());
    }

    @NativeType(value="LPCTSTR")
    public ByteBuffer lpszClassName() {
        return WNDCLASSEX.nlpszClassName(this.address());
    }

    @NativeType(value="LPCTSTR")
    public String lpszClassNameString() {
        return WNDCLASSEX.nlpszClassNameString(this.address());
    }

    @NativeType(value="HICON")
    public long hIconSm() {
        return WNDCLASSEX.nhIconSm(this.address());
    }

    public WNDCLASSEX cbSize(@NativeType(value="UINT") int n) {
        WNDCLASSEX.ncbSize(this.address(), n);
        return this;
    }

    public WNDCLASSEX style(@NativeType(value="UINT") int n) {
        WNDCLASSEX.nstyle(this.address(), n);
        return this;
    }

    public WNDCLASSEX lpfnWndProc(@NativeType(value="WNDPROC") WindowProcI windowProcI) {
        WNDCLASSEX.nlpfnWndProc(this.address(), windowProcI);
        return this;
    }

    public WNDCLASSEX cbClsExtra(int n) {
        WNDCLASSEX.ncbClsExtra(this.address(), n);
        return this;
    }

    public WNDCLASSEX cbWndExtra(int n) {
        WNDCLASSEX.ncbWndExtra(this.address(), n);
        return this;
    }

    public WNDCLASSEX hInstance(@NativeType(value="HINSTANCE") long l) {
        WNDCLASSEX.nhInstance(this.address(), l);
        return this;
    }

    public WNDCLASSEX hIcon(@NativeType(value="HICON") long l) {
        WNDCLASSEX.nhIcon(this.address(), l);
        return this;
    }

    public WNDCLASSEX hCursor(@NativeType(value="HCURSOR") long l) {
        WNDCLASSEX.nhCursor(this.address(), l);
        return this;
    }

    public WNDCLASSEX hbrBackground(@NativeType(value="HBRUSH") long l) {
        WNDCLASSEX.nhbrBackground(this.address(), l);
        return this;
    }

    public WNDCLASSEX lpszMenuName(@Nullable @NativeType(value="LPCTSTR") ByteBuffer byteBuffer) {
        WNDCLASSEX.nlpszMenuName(this.address(), byteBuffer);
        return this;
    }

    public WNDCLASSEX lpszClassName(@NativeType(value="LPCTSTR") ByteBuffer byteBuffer) {
        WNDCLASSEX.nlpszClassName(this.address(), byteBuffer);
        return this;
    }

    public WNDCLASSEX hIconSm(@NativeType(value="HICON") long l) {
        WNDCLASSEX.nhIconSm(this.address(), l);
        return this;
    }

    public WNDCLASSEX set(int n, int n2, WindowProcI windowProcI, int n3, int n4, long l, long l2, long l3, long l4, @Nullable ByteBuffer byteBuffer, ByteBuffer byteBuffer2, long l5) {
        this.cbSize(n);
        this.style(n2);
        this.lpfnWndProc(windowProcI);
        this.cbClsExtra(n3);
        this.cbWndExtra(n4);
        this.hInstance(l);
        this.hIcon(l2);
        this.hCursor(l3);
        this.hbrBackground(l4);
        this.lpszMenuName(byteBuffer);
        this.lpszClassName(byteBuffer2);
        this.hIconSm(l5);
        return this;
    }

    public WNDCLASSEX set(WNDCLASSEX wNDCLASSEX) {
        MemoryUtil.memCopy(wNDCLASSEX.address(), this.address(), SIZEOF);
        return this;
    }

    public static WNDCLASSEX malloc() {
        return WNDCLASSEX.wrap(WNDCLASSEX.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static WNDCLASSEX calloc() {
        return WNDCLASSEX.wrap(WNDCLASSEX.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static WNDCLASSEX create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return WNDCLASSEX.wrap(WNDCLASSEX.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static WNDCLASSEX create(long l) {
        return WNDCLASSEX.wrap(WNDCLASSEX.class, l);
    }

    @Nullable
    public static WNDCLASSEX createSafe(long l) {
        return l == 0L ? null : WNDCLASSEX.wrap(WNDCLASSEX.class, l);
    }

    public static Buffer malloc(int n) {
        return WNDCLASSEX.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(WNDCLASSEX.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return WNDCLASSEX.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = WNDCLASSEX.__create(n, SIZEOF);
        return WNDCLASSEX.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return WNDCLASSEX.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : WNDCLASSEX.wrap(Buffer.class, l, n);
    }

    public static WNDCLASSEX mallocStack() {
        return WNDCLASSEX.mallocStack(MemoryStack.stackGet());
    }

    public static WNDCLASSEX callocStack() {
        return WNDCLASSEX.callocStack(MemoryStack.stackGet());
    }

    public static WNDCLASSEX mallocStack(MemoryStack memoryStack) {
        return WNDCLASSEX.wrap(WNDCLASSEX.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static WNDCLASSEX callocStack(MemoryStack memoryStack) {
        return WNDCLASSEX.wrap(WNDCLASSEX.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return WNDCLASSEX.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return WNDCLASSEX.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return WNDCLASSEX.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return WNDCLASSEX.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int ncbSize(long l) {
        return UNSAFE.getInt(null, l + (long)CBSIZE);
    }

    public static int nstyle(long l) {
        return UNSAFE.getInt(null, l + (long)STYLE);
    }

    public static WindowProc nlpfnWndProc(long l) {
        return WindowProc.create(MemoryUtil.memGetAddress(l + (long)LPFNWNDPROC));
    }

    public static int ncbClsExtra(long l) {
        return UNSAFE.getInt(null, l + (long)CBCLSEXTRA);
    }

    public static int ncbWndExtra(long l) {
        return UNSAFE.getInt(null, l + (long)CBWNDEXTRA);
    }

    public static long nhInstance(long l) {
        return MemoryUtil.memGetAddress(l + (long)HINSTANCE);
    }

    public static long nhIcon(long l) {
        return MemoryUtil.memGetAddress(l + (long)HICON);
    }

    public static long nhCursor(long l) {
        return MemoryUtil.memGetAddress(l + (long)HCURSOR);
    }

    public static long nhbrBackground(long l) {
        return MemoryUtil.memGetAddress(l + (long)HBRBACKGROUND);
    }

    @Nullable
    public static ByteBuffer nlpszMenuName(long l) {
        return MemoryUtil.memByteBufferNT2Safe(MemoryUtil.memGetAddress(l + (long)LPSZMENUNAME));
    }

    @Nullable
    public static String nlpszMenuNameString(long l) {
        return MemoryUtil.memUTF16Safe(MemoryUtil.memGetAddress(l + (long)LPSZMENUNAME));
    }

    public static ByteBuffer nlpszClassName(long l) {
        return MemoryUtil.memByteBufferNT2(MemoryUtil.memGetAddress(l + (long)LPSZCLASSNAME));
    }

    public static String nlpszClassNameString(long l) {
        return MemoryUtil.memUTF16(MemoryUtil.memGetAddress(l + (long)LPSZCLASSNAME));
    }

    public static long nhIconSm(long l) {
        return MemoryUtil.memGetAddress(l + (long)HICONSM);
    }

    public static void ncbSize(long l, int n) {
        UNSAFE.putInt(null, l + (long)CBSIZE, n);
    }

    public static void nstyle(long l, int n) {
        UNSAFE.putInt(null, l + (long)STYLE, n);
    }

    public static void nlpfnWndProc(long l, WindowProcI windowProcI) {
        MemoryUtil.memPutAddress(l + (long)LPFNWNDPROC, windowProcI.address());
    }

    public static void ncbClsExtra(long l, int n) {
        UNSAFE.putInt(null, l + (long)CBCLSEXTRA, n);
    }

    public static void ncbWndExtra(long l, int n) {
        UNSAFE.putInt(null, l + (long)CBWNDEXTRA, n);
    }

    public static void nhInstance(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)HINSTANCE, Checks.check(l2));
    }

    public static void nhIcon(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)HICON, l2);
    }

    public static void nhCursor(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)HCURSOR, l2);
    }

    public static void nhbrBackground(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)HBRBACKGROUND, l2);
    }

    public static void nlpszMenuName(long l, @Nullable ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT2Safe(byteBuffer);
        }
        MemoryUtil.memPutAddress(l + (long)LPSZMENUNAME, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void nlpszClassName(long l, ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT2(byteBuffer);
        }
        MemoryUtil.memPutAddress(l + (long)LPSZCLASSNAME, MemoryUtil.memAddress(byteBuffer));
    }

    public static void nhIconSm(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)HICONSM, l2);
    }

    public static void validate(long l) {
        Checks.check(MemoryUtil.memGetAddress(l + (long)LPFNWNDPROC));
        Checks.check(MemoryUtil.memGetAddress(l + (long)HINSTANCE));
        Checks.check(MemoryUtil.memGetAddress(l + (long)LPSZCLASSNAME));
    }

    public static void validate(long l, int n) {
        for (int i = 0; i < n; ++i) {
            WNDCLASSEX.validate(l + Integer.toUnsignedLong(i) * (long)SIZEOF);
        }
    }

    static {
        Struct.Layout layout = WNDCLASSEX.__struct(WNDCLASSEX.__member(4), WNDCLASSEX.__member(4), WNDCLASSEX.__member(POINTER_SIZE), WNDCLASSEX.__member(4), WNDCLASSEX.__member(4), WNDCLASSEX.__member(POINTER_SIZE), WNDCLASSEX.__member(POINTER_SIZE), WNDCLASSEX.__member(POINTER_SIZE), WNDCLASSEX.__member(POINTER_SIZE), WNDCLASSEX.__member(POINTER_SIZE), WNDCLASSEX.__member(POINTER_SIZE), WNDCLASSEX.__member(POINTER_SIZE));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        CBSIZE = layout.offsetof(0);
        STYLE = layout.offsetof(1);
        LPFNWNDPROC = layout.offsetof(2);
        CBCLSEXTRA = layout.offsetof(3);
        CBWNDEXTRA = layout.offsetof(4);
        HINSTANCE = layout.offsetof(5);
        HICON = layout.offsetof(6);
        HCURSOR = layout.offsetof(7);
        HBRBACKGROUND = layout.offsetof(8);
        LPSZMENUNAME = layout.offsetof(9);
        LPSZCLASSNAME = layout.offsetof(10);
        HICONSM = layout.offsetof(11);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<WNDCLASSEX, Buffer>
    implements NativeResource {
        private static final WNDCLASSEX ELEMENT_FACTORY = WNDCLASSEX.create(-1L);

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
        protected WNDCLASSEX getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="UINT")
        public int cbSize() {
            return WNDCLASSEX.ncbSize(this.address());
        }

        @NativeType(value="UINT")
        public int style() {
            return WNDCLASSEX.nstyle(this.address());
        }

        @NativeType(value="WNDPROC")
        public WindowProc lpfnWndProc() {
            return WNDCLASSEX.nlpfnWndProc(this.address());
        }

        public int cbClsExtra() {
            return WNDCLASSEX.ncbClsExtra(this.address());
        }

        public int cbWndExtra() {
            return WNDCLASSEX.ncbWndExtra(this.address());
        }

        @NativeType(value="HINSTANCE")
        public long hInstance() {
            return WNDCLASSEX.nhInstance(this.address());
        }

        @NativeType(value="HICON")
        public long hIcon() {
            return WNDCLASSEX.nhIcon(this.address());
        }

        @NativeType(value="HCURSOR")
        public long hCursor() {
            return WNDCLASSEX.nhCursor(this.address());
        }

        @NativeType(value="HBRUSH")
        public long hbrBackground() {
            return WNDCLASSEX.nhbrBackground(this.address());
        }

        @Nullable
        @NativeType(value="LPCTSTR")
        public ByteBuffer lpszMenuName() {
            return WNDCLASSEX.nlpszMenuName(this.address());
        }

        @Nullable
        @NativeType(value="LPCTSTR")
        public String lpszMenuNameString() {
            return WNDCLASSEX.nlpszMenuNameString(this.address());
        }

        @NativeType(value="LPCTSTR")
        public ByteBuffer lpszClassName() {
            return WNDCLASSEX.nlpszClassName(this.address());
        }

        @NativeType(value="LPCTSTR")
        public String lpszClassNameString() {
            return WNDCLASSEX.nlpszClassNameString(this.address());
        }

        @NativeType(value="HICON")
        public long hIconSm() {
            return WNDCLASSEX.nhIconSm(this.address());
        }

        public Buffer cbSize(@NativeType(value="UINT") int n) {
            WNDCLASSEX.ncbSize(this.address(), n);
            return this;
        }

        public Buffer style(@NativeType(value="UINT") int n) {
            WNDCLASSEX.nstyle(this.address(), n);
            return this;
        }

        public Buffer lpfnWndProc(@NativeType(value="WNDPROC") WindowProcI windowProcI) {
            WNDCLASSEX.nlpfnWndProc(this.address(), windowProcI);
            return this;
        }

        public Buffer cbClsExtra(int n) {
            WNDCLASSEX.ncbClsExtra(this.address(), n);
            return this;
        }

        public Buffer cbWndExtra(int n) {
            WNDCLASSEX.ncbWndExtra(this.address(), n);
            return this;
        }

        public Buffer hInstance(@NativeType(value="HINSTANCE") long l) {
            WNDCLASSEX.nhInstance(this.address(), l);
            return this;
        }

        public Buffer hIcon(@NativeType(value="HICON") long l) {
            WNDCLASSEX.nhIcon(this.address(), l);
            return this;
        }

        public Buffer hCursor(@NativeType(value="HCURSOR") long l) {
            WNDCLASSEX.nhCursor(this.address(), l);
            return this;
        }

        public Buffer hbrBackground(@NativeType(value="HBRUSH") long l) {
            WNDCLASSEX.nhbrBackground(this.address(), l);
            return this;
        }

        public Buffer lpszMenuName(@Nullable @NativeType(value="LPCTSTR") ByteBuffer byteBuffer) {
            WNDCLASSEX.nlpszMenuName(this.address(), byteBuffer);
            return this;
        }

        public Buffer lpszClassName(@NativeType(value="LPCTSTR") ByteBuffer byteBuffer) {
            WNDCLASSEX.nlpszClassName(this.address(), byteBuffer);
            return this;
        }

        public Buffer hIconSm(@NativeType(value="HICON") long l) {
            WNDCLASSEX.nhIconSm(this.address(), l);
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

