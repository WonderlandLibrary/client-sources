/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.macosx;

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

@NativeType(value="struct objc_property_attribute_t")
public class ObjCPropertyAttribute
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int NAME;
    public static final int VALUE;

    public ObjCPropertyAttribute(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), ObjCPropertyAttribute.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="char *")
    public ByteBuffer name() {
        return ObjCPropertyAttribute.nname(this.address());
    }

    @NativeType(value="char *")
    public String nameString() {
        return ObjCPropertyAttribute.nnameString(this.address());
    }

    @NativeType(value="char *")
    public ByteBuffer value() {
        return ObjCPropertyAttribute.nvalue(this.address());
    }

    @NativeType(value="char *")
    public String valueString() {
        return ObjCPropertyAttribute.nvalueString(this.address());
    }

    public ObjCPropertyAttribute name(@NativeType(value="char *") ByteBuffer byteBuffer) {
        ObjCPropertyAttribute.nname(this.address(), byteBuffer);
        return this;
    }

    public ObjCPropertyAttribute value(@NativeType(value="char *") ByteBuffer byteBuffer) {
        ObjCPropertyAttribute.nvalue(this.address(), byteBuffer);
        return this;
    }

    public ObjCPropertyAttribute set(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        this.name(byteBuffer);
        this.value(byteBuffer2);
        return this;
    }

    public ObjCPropertyAttribute set(ObjCPropertyAttribute objCPropertyAttribute) {
        MemoryUtil.memCopy(objCPropertyAttribute.address(), this.address(), SIZEOF);
        return this;
    }

    public static ObjCPropertyAttribute malloc() {
        return ObjCPropertyAttribute.wrap(ObjCPropertyAttribute.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static ObjCPropertyAttribute calloc() {
        return ObjCPropertyAttribute.wrap(ObjCPropertyAttribute.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static ObjCPropertyAttribute create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return ObjCPropertyAttribute.wrap(ObjCPropertyAttribute.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static ObjCPropertyAttribute create(long l) {
        return ObjCPropertyAttribute.wrap(ObjCPropertyAttribute.class, l);
    }

    @Nullable
    public static ObjCPropertyAttribute createSafe(long l) {
        return l == 0L ? null : ObjCPropertyAttribute.wrap(ObjCPropertyAttribute.class, l);
    }

    public static Buffer malloc(int n) {
        return ObjCPropertyAttribute.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(ObjCPropertyAttribute.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return ObjCPropertyAttribute.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = ObjCPropertyAttribute.__create(n, SIZEOF);
        return ObjCPropertyAttribute.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return ObjCPropertyAttribute.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : ObjCPropertyAttribute.wrap(Buffer.class, l, n);
    }

    public static ObjCPropertyAttribute mallocStack() {
        return ObjCPropertyAttribute.mallocStack(MemoryStack.stackGet());
    }

    public static ObjCPropertyAttribute callocStack() {
        return ObjCPropertyAttribute.callocStack(MemoryStack.stackGet());
    }

    public static ObjCPropertyAttribute mallocStack(MemoryStack memoryStack) {
        return ObjCPropertyAttribute.wrap(ObjCPropertyAttribute.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static ObjCPropertyAttribute callocStack(MemoryStack memoryStack) {
        return ObjCPropertyAttribute.wrap(ObjCPropertyAttribute.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return ObjCPropertyAttribute.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return ObjCPropertyAttribute.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return ObjCPropertyAttribute.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return ObjCPropertyAttribute.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static ByteBuffer nname(long l) {
        return MemoryUtil.memByteBufferNT1(MemoryUtil.memGetAddress(l + (long)NAME));
    }

    public static String nnameString(long l) {
        return MemoryUtil.memUTF8(MemoryUtil.memGetAddress(l + (long)NAME));
    }

    public static ByteBuffer nvalue(long l) {
        return MemoryUtil.memByteBufferNT1(MemoryUtil.memGetAddress(l + (long)VALUE));
    }

    public static String nvalueString(long l) {
        return MemoryUtil.memUTF8(MemoryUtil.memGetAddress(l + (long)VALUE));
    }

    public static void nname(long l, ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        MemoryUtil.memPutAddress(l + (long)NAME, MemoryUtil.memAddress(byteBuffer));
    }

    public static void nvalue(long l, ByteBuffer byteBuffer) {
        if (Checks.CHECKS) {
            Checks.checkNT1(byteBuffer);
        }
        MemoryUtil.memPutAddress(l + (long)VALUE, MemoryUtil.memAddress(byteBuffer));
    }

    public static void validate(long l) {
        Checks.check(MemoryUtil.memGetAddress(l + (long)NAME));
        Checks.check(MemoryUtil.memGetAddress(l + (long)VALUE));
    }

    public static void validate(long l, int n) {
        for (int i = 0; i < n; ++i) {
            ObjCPropertyAttribute.validate(l + Integer.toUnsignedLong(i) * (long)SIZEOF);
        }
    }

    static {
        Struct.Layout layout = ObjCPropertyAttribute.__struct(ObjCPropertyAttribute.__member(POINTER_SIZE), ObjCPropertyAttribute.__member(POINTER_SIZE));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        NAME = layout.offsetof(0);
        VALUE = layout.offsetof(1);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<ObjCPropertyAttribute, Buffer>
    implements NativeResource {
        private static final ObjCPropertyAttribute ELEMENT_FACTORY = ObjCPropertyAttribute.create(-1L);

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
        protected ObjCPropertyAttribute getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="char *")
        public ByteBuffer name() {
            return ObjCPropertyAttribute.nname(this.address());
        }

        @NativeType(value="char *")
        public String nameString() {
            return ObjCPropertyAttribute.nnameString(this.address());
        }

        @NativeType(value="char *")
        public ByteBuffer value() {
            return ObjCPropertyAttribute.nvalue(this.address());
        }

        @NativeType(value="char *")
        public String valueString() {
            return ObjCPropertyAttribute.nvalueString(this.address());
        }

        public Buffer name(@NativeType(value="char *") ByteBuffer byteBuffer) {
            ObjCPropertyAttribute.nname(this.address(), byteBuffer);
            return this;
        }

        public Buffer value(@NativeType(value="char *") ByteBuffer byteBuffer) {
            ObjCPropertyAttribute.nvalue(this.address(), byteBuffer);
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

