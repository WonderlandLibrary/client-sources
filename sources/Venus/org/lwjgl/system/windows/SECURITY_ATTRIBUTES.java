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

public class SECURITY_ATTRIBUTES
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int NLENGTH;
    public static final int LPSECURITYDESCRIPTOR;
    public static final int BINHERITHANDLE;

    public SECURITY_ATTRIBUTES(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), SECURITY_ATTRIBUTES.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="DWORD")
    public int nLength() {
        return SECURITY_ATTRIBUTES.nnLength(this.address());
    }

    @NativeType(value="LPVOID")
    public long lpSecurityDescriptor() {
        return SECURITY_ATTRIBUTES.nlpSecurityDescriptor(this.address());
    }

    @NativeType(value="BOOL")
    public boolean bInheritHandle() {
        return SECURITY_ATTRIBUTES.nbInheritHandle(this.address()) != 0;
    }

    public SECURITY_ATTRIBUTES nLength(@NativeType(value="DWORD") int n) {
        SECURITY_ATTRIBUTES.nnLength(this.address(), n);
        return this;
    }

    public SECURITY_ATTRIBUTES lpSecurityDescriptor(@NativeType(value="LPVOID") long l) {
        SECURITY_ATTRIBUTES.nlpSecurityDescriptor(this.address(), l);
        return this;
    }

    public SECURITY_ATTRIBUTES bInheritHandle(@NativeType(value="BOOL") boolean bl) {
        SECURITY_ATTRIBUTES.nbInheritHandle(this.address(), bl ? 1 : 0);
        return this;
    }

    public SECURITY_ATTRIBUTES set(int n, long l, boolean bl) {
        this.nLength(n);
        this.lpSecurityDescriptor(l);
        this.bInheritHandle(bl);
        return this;
    }

    public SECURITY_ATTRIBUTES set(SECURITY_ATTRIBUTES sECURITY_ATTRIBUTES) {
        MemoryUtil.memCopy(sECURITY_ATTRIBUTES.address(), this.address(), SIZEOF);
        return this;
    }

    public static SECURITY_ATTRIBUTES malloc() {
        return SECURITY_ATTRIBUTES.wrap(SECURITY_ATTRIBUTES.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static SECURITY_ATTRIBUTES calloc() {
        return SECURITY_ATTRIBUTES.wrap(SECURITY_ATTRIBUTES.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static SECURITY_ATTRIBUTES create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return SECURITY_ATTRIBUTES.wrap(SECURITY_ATTRIBUTES.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static SECURITY_ATTRIBUTES create(long l) {
        return SECURITY_ATTRIBUTES.wrap(SECURITY_ATTRIBUTES.class, l);
    }

    @Nullable
    public static SECURITY_ATTRIBUTES createSafe(long l) {
        return l == 0L ? null : SECURITY_ATTRIBUTES.wrap(SECURITY_ATTRIBUTES.class, l);
    }

    public static Buffer malloc(int n) {
        return SECURITY_ATTRIBUTES.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(SECURITY_ATTRIBUTES.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return SECURITY_ATTRIBUTES.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = SECURITY_ATTRIBUTES.__create(n, SIZEOF);
        return SECURITY_ATTRIBUTES.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return SECURITY_ATTRIBUTES.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : SECURITY_ATTRIBUTES.wrap(Buffer.class, l, n);
    }

    public static SECURITY_ATTRIBUTES mallocStack() {
        return SECURITY_ATTRIBUTES.mallocStack(MemoryStack.stackGet());
    }

    public static SECURITY_ATTRIBUTES callocStack() {
        return SECURITY_ATTRIBUTES.callocStack(MemoryStack.stackGet());
    }

    public static SECURITY_ATTRIBUTES mallocStack(MemoryStack memoryStack) {
        return SECURITY_ATTRIBUTES.wrap(SECURITY_ATTRIBUTES.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static SECURITY_ATTRIBUTES callocStack(MemoryStack memoryStack) {
        return SECURITY_ATTRIBUTES.wrap(SECURITY_ATTRIBUTES.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return SECURITY_ATTRIBUTES.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return SECURITY_ATTRIBUTES.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return SECURITY_ATTRIBUTES.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return SECURITY_ATTRIBUTES.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int nnLength(long l) {
        return UNSAFE.getInt(null, l + (long)NLENGTH);
    }

    public static long nlpSecurityDescriptor(long l) {
        return MemoryUtil.memGetAddress(l + (long)LPSECURITYDESCRIPTOR);
    }

    public static int nbInheritHandle(long l) {
        return UNSAFE.getInt(null, l + (long)BINHERITHANDLE);
    }

    public static void nnLength(long l, int n) {
        UNSAFE.putInt(null, l + (long)NLENGTH, n);
    }

    public static void nlpSecurityDescriptor(long l, long l2) {
        MemoryUtil.memPutAddress(l + (long)LPSECURITYDESCRIPTOR, Checks.check(l2));
    }

    public static void nbInheritHandle(long l, int n) {
        UNSAFE.putInt(null, l + (long)BINHERITHANDLE, n);
    }

    public static void validate(long l) {
        Checks.check(MemoryUtil.memGetAddress(l + (long)LPSECURITYDESCRIPTOR));
    }

    public static void validate(long l, int n) {
        for (int i = 0; i < n; ++i) {
            SECURITY_ATTRIBUTES.validate(l + Integer.toUnsignedLong(i) * (long)SIZEOF);
        }
    }

    static {
        Struct.Layout layout = SECURITY_ATTRIBUTES.__struct(SECURITY_ATTRIBUTES.__member(4), SECURITY_ATTRIBUTES.__member(POINTER_SIZE), SECURITY_ATTRIBUTES.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        NLENGTH = layout.offsetof(0);
        LPSECURITYDESCRIPTOR = layout.offsetof(1);
        BINHERITHANDLE = layout.offsetof(2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<SECURITY_ATTRIBUTES, Buffer>
    implements NativeResource {
        private static final SECURITY_ATTRIBUTES ELEMENT_FACTORY = SECURITY_ATTRIBUTES.create(-1L);

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
        protected SECURITY_ATTRIBUTES getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="DWORD")
        public int nLength() {
            return SECURITY_ATTRIBUTES.nnLength(this.address());
        }

        @NativeType(value="LPVOID")
        public long lpSecurityDescriptor() {
            return SECURITY_ATTRIBUTES.nlpSecurityDescriptor(this.address());
        }

        @NativeType(value="BOOL")
        public boolean bInheritHandle() {
            return SECURITY_ATTRIBUTES.nbInheritHandle(this.address()) != 0;
        }

        public Buffer nLength(@NativeType(value="DWORD") int n) {
            SECURITY_ATTRIBUTES.nnLength(this.address(), n);
            return this;
        }

        public Buffer lpSecurityDescriptor(@NativeType(value="LPVOID") long l) {
            SECURITY_ATTRIBUTES.nlpSecurityDescriptor(this.address(), l);
            return this;
        }

        public Buffer bInheritHandle(@NativeType(value="BOOL") boolean bl) {
            SECURITY_ATTRIBUTES.nbInheritHandle(this.address(), bl ? 1 : 0);
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

