/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.macosx;

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

@NativeType(value="struct objc_method_description")
public class ObjCMethodDescription
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int NAME;
    public static final int TYPES;

    public ObjCMethodDescription(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), ObjCMethodDescription.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="SEL")
    public long name() {
        return ObjCMethodDescription.nname(this.address());
    }

    @NativeType(value="char *")
    public ByteBuffer types() {
        return ObjCMethodDescription.ntypes(this.address());
    }

    @NativeType(value="char *")
    public String typesString() {
        return ObjCMethodDescription.ntypesString(this.address());
    }

    public static ObjCMethodDescription malloc() {
        return ObjCMethodDescription.wrap(ObjCMethodDescription.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static ObjCMethodDescription calloc() {
        return ObjCMethodDescription.wrap(ObjCMethodDescription.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static ObjCMethodDescription create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return ObjCMethodDescription.wrap(ObjCMethodDescription.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static ObjCMethodDescription create(long l) {
        return ObjCMethodDescription.wrap(ObjCMethodDescription.class, l);
    }

    @Nullable
    public static ObjCMethodDescription createSafe(long l) {
        return l == 0L ? null : ObjCMethodDescription.wrap(ObjCMethodDescription.class, l);
    }

    public static Buffer malloc(int n) {
        return ObjCMethodDescription.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(ObjCMethodDescription.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return ObjCMethodDescription.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = ObjCMethodDescription.__create(n, SIZEOF);
        return ObjCMethodDescription.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return ObjCMethodDescription.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : ObjCMethodDescription.wrap(Buffer.class, l, n);
    }

    public static ObjCMethodDescription mallocStack() {
        return ObjCMethodDescription.mallocStack(MemoryStack.stackGet());
    }

    public static ObjCMethodDescription callocStack() {
        return ObjCMethodDescription.callocStack(MemoryStack.stackGet());
    }

    public static ObjCMethodDescription mallocStack(MemoryStack memoryStack) {
        return ObjCMethodDescription.wrap(ObjCMethodDescription.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static ObjCMethodDescription callocStack(MemoryStack memoryStack) {
        return ObjCMethodDescription.wrap(ObjCMethodDescription.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return ObjCMethodDescription.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return ObjCMethodDescription.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return ObjCMethodDescription.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return ObjCMethodDescription.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static long nname(long l) {
        return MemoryUtil.memGetAddress(l + (long)NAME);
    }

    public static ByteBuffer ntypes(long l) {
        return MemoryUtil.memByteBufferNT1(MemoryUtil.memGetAddress(l + (long)TYPES));
    }

    public static String ntypesString(long l) {
        return MemoryUtil.memUTF8(MemoryUtil.memGetAddress(l + (long)TYPES));
    }

    static {
        Struct.Layout layout = ObjCMethodDescription.__struct(ObjCMethodDescription.__member(POINTER_SIZE), ObjCMethodDescription.__member(POINTER_SIZE));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        NAME = layout.offsetof(0);
        TYPES = layout.offsetof(1);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<ObjCMethodDescription, Buffer>
    implements NativeResource {
        private static final ObjCMethodDescription ELEMENT_FACTORY = ObjCMethodDescription.create(-1L);

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
        protected ObjCMethodDescription getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="SEL")
        public long name() {
            return ObjCMethodDescription.nname(this.address());
        }

        @NativeType(value="char *")
        public ByteBuffer types() {
            return ObjCMethodDescription.ntypes(this.address());
        }

        @NativeType(value="char *")
        public String typesString() {
            return ObjCMethodDescription.ntypesString(this.address());
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

