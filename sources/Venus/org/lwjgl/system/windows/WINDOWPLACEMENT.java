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
import org.lwjgl.system.windows.RECT;

public class WINDOWPLACEMENT
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int LENGTH;
    public static final int FLAGS;
    public static final int SHOWCMD;
    public static final int PTMINPOSITION;
    public static final int PTMAXPOSITION;
    public static final int RCNORMALPOSITION;

    public WINDOWPLACEMENT(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), WINDOWPLACEMENT.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="UINT")
    public int length() {
        return WINDOWPLACEMENT.nlength(this.address());
    }

    @NativeType(value="UINT")
    public int flags() {
        return WINDOWPLACEMENT.nflags(this.address());
    }

    @NativeType(value="UINT")
    public int showCmd() {
        return WINDOWPLACEMENT.nshowCmd(this.address());
    }

    public POINT ptMinPosition() {
        return WINDOWPLACEMENT.nptMinPosition(this.address());
    }

    public WINDOWPLACEMENT ptMinPosition(Consumer<POINT> consumer) {
        consumer.accept(this.ptMinPosition());
        return this;
    }

    public POINT ptMaxPosition() {
        return WINDOWPLACEMENT.nptMaxPosition(this.address());
    }

    public WINDOWPLACEMENT ptMaxPosition(Consumer<POINT> consumer) {
        consumer.accept(this.ptMaxPosition());
        return this;
    }

    public RECT rcNormalPosition() {
        return WINDOWPLACEMENT.nrcNormalPosition(this.address());
    }

    public WINDOWPLACEMENT rcNormalPosition(Consumer<RECT> consumer) {
        consumer.accept(this.rcNormalPosition());
        return this;
    }

    public WINDOWPLACEMENT length(@NativeType(value="UINT") int n) {
        WINDOWPLACEMENT.nlength(this.address(), n);
        return this;
    }

    public WINDOWPLACEMENT flags(@NativeType(value="UINT") int n) {
        WINDOWPLACEMENT.nflags(this.address(), n);
        return this;
    }

    public WINDOWPLACEMENT showCmd(@NativeType(value="UINT") int n) {
        WINDOWPLACEMENT.nshowCmd(this.address(), n);
        return this;
    }

    public WINDOWPLACEMENT ptMinPosition(POINT pOINT) {
        WINDOWPLACEMENT.nptMinPosition(this.address(), pOINT);
        return this;
    }

    public WINDOWPLACEMENT ptMaxPosition(POINT pOINT) {
        WINDOWPLACEMENT.nptMaxPosition(this.address(), pOINT);
        return this;
    }

    public WINDOWPLACEMENT rcNormalPosition(RECT rECT) {
        WINDOWPLACEMENT.nrcNormalPosition(this.address(), rECT);
        return this;
    }

    public WINDOWPLACEMENT set(int n, int n2, int n3, POINT pOINT, POINT pOINT2, RECT rECT) {
        this.length(n);
        this.flags(n2);
        this.showCmd(n3);
        this.ptMinPosition(pOINT);
        this.ptMaxPosition(pOINT2);
        this.rcNormalPosition(rECT);
        return this;
    }

    public WINDOWPLACEMENT set(WINDOWPLACEMENT wINDOWPLACEMENT) {
        MemoryUtil.memCopy(wINDOWPLACEMENT.address(), this.address(), SIZEOF);
        return this;
    }

    public static WINDOWPLACEMENT malloc() {
        return WINDOWPLACEMENT.wrap(WINDOWPLACEMENT.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static WINDOWPLACEMENT calloc() {
        return WINDOWPLACEMENT.wrap(WINDOWPLACEMENT.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static WINDOWPLACEMENT create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return WINDOWPLACEMENT.wrap(WINDOWPLACEMENT.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static WINDOWPLACEMENT create(long l) {
        return WINDOWPLACEMENT.wrap(WINDOWPLACEMENT.class, l);
    }

    @Nullable
    public static WINDOWPLACEMENT createSafe(long l) {
        return l == 0L ? null : WINDOWPLACEMENT.wrap(WINDOWPLACEMENT.class, l);
    }

    public static Buffer malloc(int n) {
        return WINDOWPLACEMENT.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(WINDOWPLACEMENT.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return WINDOWPLACEMENT.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = WINDOWPLACEMENT.__create(n, SIZEOF);
        return WINDOWPLACEMENT.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return WINDOWPLACEMENT.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : WINDOWPLACEMENT.wrap(Buffer.class, l, n);
    }

    public static WINDOWPLACEMENT mallocStack() {
        return WINDOWPLACEMENT.mallocStack(MemoryStack.stackGet());
    }

    public static WINDOWPLACEMENT callocStack() {
        return WINDOWPLACEMENT.callocStack(MemoryStack.stackGet());
    }

    public static WINDOWPLACEMENT mallocStack(MemoryStack memoryStack) {
        return WINDOWPLACEMENT.wrap(WINDOWPLACEMENT.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static WINDOWPLACEMENT callocStack(MemoryStack memoryStack) {
        return WINDOWPLACEMENT.wrap(WINDOWPLACEMENT.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return WINDOWPLACEMENT.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return WINDOWPLACEMENT.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return WINDOWPLACEMENT.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return WINDOWPLACEMENT.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int nlength(long l) {
        return UNSAFE.getInt(null, l + (long)LENGTH);
    }

    public static int nflags(long l) {
        return UNSAFE.getInt(null, l + (long)FLAGS);
    }

    public static int nshowCmd(long l) {
        return UNSAFE.getInt(null, l + (long)SHOWCMD);
    }

    public static POINT nptMinPosition(long l) {
        return POINT.create(l + (long)PTMINPOSITION);
    }

    public static POINT nptMaxPosition(long l) {
        return POINT.create(l + (long)PTMAXPOSITION);
    }

    public static RECT nrcNormalPosition(long l) {
        return RECT.create(l + (long)RCNORMALPOSITION);
    }

    public static void nlength(long l, int n) {
        UNSAFE.putInt(null, l + (long)LENGTH, n);
    }

    public static void nflags(long l, int n) {
        UNSAFE.putInt(null, l + (long)FLAGS, n);
    }

    public static void nshowCmd(long l, int n) {
        UNSAFE.putInt(null, l + (long)SHOWCMD, n);
    }

    public static void nptMinPosition(long l, POINT pOINT) {
        MemoryUtil.memCopy(pOINT.address(), l + (long)PTMINPOSITION, POINT.SIZEOF);
    }

    public static void nptMaxPosition(long l, POINT pOINT) {
        MemoryUtil.memCopy(pOINT.address(), l + (long)PTMAXPOSITION, POINT.SIZEOF);
    }

    public static void nrcNormalPosition(long l, RECT rECT) {
        MemoryUtil.memCopy(rECT.address(), l + (long)RCNORMALPOSITION, RECT.SIZEOF);
    }

    static {
        Struct.Layout layout = WINDOWPLACEMENT.__struct(WINDOWPLACEMENT.__member(4), WINDOWPLACEMENT.__member(4), WINDOWPLACEMENT.__member(4), WINDOWPLACEMENT.__member(POINT.SIZEOF, POINT.ALIGNOF), WINDOWPLACEMENT.__member(POINT.SIZEOF, POINT.ALIGNOF), WINDOWPLACEMENT.__member(RECT.SIZEOF, RECT.ALIGNOF));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        LENGTH = layout.offsetof(0);
        FLAGS = layout.offsetof(1);
        SHOWCMD = layout.offsetof(2);
        PTMINPOSITION = layout.offsetof(3);
        PTMAXPOSITION = layout.offsetof(4);
        RCNORMALPOSITION = layout.offsetof(5);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<WINDOWPLACEMENT, Buffer>
    implements NativeResource {
        private static final WINDOWPLACEMENT ELEMENT_FACTORY = WINDOWPLACEMENT.create(-1L);

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
        protected WINDOWPLACEMENT getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="UINT")
        public int length() {
            return WINDOWPLACEMENT.nlength(this.address());
        }

        @NativeType(value="UINT")
        public int flags() {
            return WINDOWPLACEMENT.nflags(this.address());
        }

        @NativeType(value="UINT")
        public int showCmd() {
            return WINDOWPLACEMENT.nshowCmd(this.address());
        }

        public POINT ptMinPosition() {
            return WINDOWPLACEMENT.nptMinPosition(this.address());
        }

        public Buffer ptMinPosition(Consumer<POINT> consumer) {
            consumer.accept(this.ptMinPosition());
            return this;
        }

        public POINT ptMaxPosition() {
            return WINDOWPLACEMENT.nptMaxPosition(this.address());
        }

        public Buffer ptMaxPosition(Consumer<POINT> consumer) {
            consumer.accept(this.ptMaxPosition());
            return this;
        }

        public RECT rcNormalPosition() {
            return WINDOWPLACEMENT.nrcNormalPosition(this.address());
        }

        public Buffer rcNormalPosition(Consumer<RECT> consumer) {
            consumer.accept(this.rcNormalPosition());
            return this;
        }

        public Buffer length(@NativeType(value="UINT") int n) {
            WINDOWPLACEMENT.nlength(this.address(), n);
            return this;
        }

        public Buffer flags(@NativeType(value="UINT") int n) {
            WINDOWPLACEMENT.nflags(this.address(), n);
            return this;
        }

        public Buffer showCmd(@NativeType(value="UINT") int n) {
            WINDOWPLACEMENT.nshowCmd(this.address(), n);
            return this;
        }

        public Buffer ptMinPosition(POINT pOINT) {
            WINDOWPLACEMENT.nptMinPosition(this.address(), pOINT);
            return this;
        }

        public Buffer ptMaxPosition(POINT pOINT) {
            WINDOWPLACEMENT.nptMaxPosition(this.address(), pOINT);
            return this;
        }

        public Buffer rcNormalPosition(RECT rECT) {
            WINDOWPLACEMENT.nrcNormalPosition(this.address(), rECT);
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

