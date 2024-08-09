/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBIEOFCallback;
import org.lwjgl.stb.STBIEOFCallbackI;
import org.lwjgl.stb.STBIReadCallback;
import org.lwjgl.stb.STBIReadCallbackI;
import org.lwjgl.stb.STBISkipCallback;
import org.lwjgl.stb.STBISkipCallbackI;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType(value="struct stbi_io_callbacks")
public class STBIIOCallbacks
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int READ;
    public static final int SKIP;
    public static final int EOF;

    public STBIIOCallbacks(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBIIOCallbacks.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="int (*) (void *, char *, int)")
    public STBIReadCallback read() {
        return STBIIOCallbacks.nread(this.address());
    }

    @NativeType(value="void (*) (void *, int)")
    public STBISkipCallback skip() {
        return STBIIOCallbacks.nskip(this.address());
    }

    @NativeType(value="int (*) (void *)")
    public STBIEOFCallback eof() {
        return STBIIOCallbacks.neof(this.address());
    }

    public STBIIOCallbacks read(@NativeType(value="int (*) (void *, char *, int)") STBIReadCallbackI sTBIReadCallbackI) {
        STBIIOCallbacks.nread(this.address(), sTBIReadCallbackI);
        return this;
    }

    public STBIIOCallbacks skip(@NativeType(value="void (*) (void *, int)") STBISkipCallbackI sTBISkipCallbackI) {
        STBIIOCallbacks.nskip(this.address(), sTBISkipCallbackI);
        return this;
    }

    public STBIIOCallbacks eof(@NativeType(value="int (*) (void *)") STBIEOFCallbackI sTBIEOFCallbackI) {
        STBIIOCallbacks.neof(this.address(), sTBIEOFCallbackI);
        return this;
    }

    public STBIIOCallbacks set(STBIReadCallbackI sTBIReadCallbackI, STBISkipCallbackI sTBISkipCallbackI, STBIEOFCallbackI sTBIEOFCallbackI) {
        this.read(sTBIReadCallbackI);
        this.skip(sTBISkipCallbackI);
        this.eof(sTBIEOFCallbackI);
        return this;
    }

    public STBIIOCallbacks set(STBIIOCallbacks sTBIIOCallbacks) {
        MemoryUtil.memCopy(sTBIIOCallbacks.address(), this.address(), SIZEOF);
        return this;
    }

    public static STBIIOCallbacks malloc() {
        return STBIIOCallbacks.wrap(STBIIOCallbacks.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBIIOCallbacks calloc() {
        return STBIIOCallbacks.wrap(STBIIOCallbacks.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBIIOCallbacks create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBIIOCallbacks.wrap(STBIIOCallbacks.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBIIOCallbacks create(long l) {
        return STBIIOCallbacks.wrap(STBIIOCallbacks.class, l);
    }

    @Nullable
    public static STBIIOCallbacks createSafe(long l) {
        return l == 0L ? null : STBIIOCallbacks.wrap(STBIIOCallbacks.class, l);
    }

    public static Buffer malloc(int n) {
        return STBIIOCallbacks.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBIIOCallbacks.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBIIOCallbacks.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBIIOCallbacks.__create(n, SIZEOF);
        return STBIIOCallbacks.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBIIOCallbacks.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBIIOCallbacks.wrap(Buffer.class, l, n);
    }

    public static STBIIOCallbacks mallocStack() {
        return STBIIOCallbacks.mallocStack(MemoryStack.stackGet());
    }

    public static STBIIOCallbacks callocStack() {
        return STBIIOCallbacks.callocStack(MemoryStack.stackGet());
    }

    public static STBIIOCallbacks mallocStack(MemoryStack memoryStack) {
        return STBIIOCallbacks.wrap(STBIIOCallbacks.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBIIOCallbacks callocStack(MemoryStack memoryStack) {
        return STBIIOCallbacks.wrap(STBIIOCallbacks.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBIIOCallbacks.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBIIOCallbacks.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBIIOCallbacks.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBIIOCallbacks.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static STBIReadCallback nread(long l) {
        return STBIReadCallback.create(MemoryUtil.memGetAddress(l + (long)READ));
    }

    public static STBISkipCallback nskip(long l) {
        return STBISkipCallback.create(MemoryUtil.memGetAddress(l + (long)SKIP));
    }

    public static STBIEOFCallback neof(long l) {
        return STBIEOFCallback.create(MemoryUtil.memGetAddress(l + (long)EOF));
    }

    public static void nread(long l, STBIReadCallbackI sTBIReadCallbackI) {
        MemoryUtil.memPutAddress(l + (long)READ, sTBIReadCallbackI.address());
    }

    public static void nskip(long l, STBISkipCallbackI sTBISkipCallbackI) {
        MemoryUtil.memPutAddress(l + (long)SKIP, sTBISkipCallbackI.address());
    }

    public static void neof(long l, STBIEOFCallbackI sTBIEOFCallbackI) {
        MemoryUtil.memPutAddress(l + (long)EOF, sTBIEOFCallbackI.address());
    }

    public static void validate(long l) {
        Checks.check(MemoryUtil.memGetAddress(l + (long)READ));
        Checks.check(MemoryUtil.memGetAddress(l + (long)SKIP));
        Checks.check(MemoryUtil.memGetAddress(l + (long)EOF));
    }

    public static void validate(long l, int n) {
        for (int i = 0; i < n; ++i) {
            STBIIOCallbacks.validate(l + Integer.toUnsignedLong(i) * (long)SIZEOF);
        }
    }

    static {
        Struct.Layout layout = STBIIOCallbacks.__struct(STBIIOCallbacks.__member(POINTER_SIZE), STBIIOCallbacks.__member(POINTER_SIZE), STBIIOCallbacks.__member(POINTER_SIZE));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        READ = layout.offsetof(0);
        SKIP = layout.offsetof(1);
        EOF = layout.offsetof(2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBIIOCallbacks, Buffer>
    implements NativeResource {
        private static final STBIIOCallbacks ELEMENT_FACTORY = STBIIOCallbacks.create(-1L);

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
        protected STBIIOCallbacks getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="int (*) (void *, char *, int)")
        public STBIReadCallback read() {
            return STBIIOCallbacks.nread(this.address());
        }

        @NativeType(value="void (*) (void *, int)")
        public STBISkipCallback skip() {
            return STBIIOCallbacks.nskip(this.address());
        }

        @NativeType(value="int (*) (void *)")
        public STBIEOFCallback eof() {
            return STBIIOCallbacks.neof(this.address());
        }

        public Buffer read(@NativeType(value="int (*) (void *, char *, int)") STBIReadCallbackI sTBIReadCallbackI) {
            STBIIOCallbacks.nread(this.address(), sTBIReadCallbackI);
            return this;
        }

        public Buffer skip(@NativeType(value="void (*) (void *, int)") STBISkipCallbackI sTBISkipCallbackI) {
            STBIIOCallbacks.nskip(this.address(), sTBISkipCallbackI);
            return this;
        }

        public Buffer eof(@NativeType(value="int (*) (void *)") STBIEOFCallbackI sTBIEOFCallbackI) {
            STBIIOCallbacks.neof(this.address(), sTBIEOFCallbackI);
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

