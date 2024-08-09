/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.stb;

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

@NativeType(value="struct stbrp_rect")
public class STBRPRect
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int ID;
    public static final int W;
    public static final int H;
    public static final int X;
    public static final int Y;
    public static final int WAS_PACKED;

    public STBRPRect(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), STBRPRect.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    public int id() {
        return STBRPRect.nid(this.address());
    }

    @NativeType(value="stbrp_coord")
    public short w() {
        return STBRPRect.nw(this.address());
    }

    @NativeType(value="stbrp_coord")
    public short h() {
        return STBRPRect.nh(this.address());
    }

    @NativeType(value="stbrp_coord")
    public short x() {
        return STBRPRect.nx(this.address());
    }

    @NativeType(value="stbrp_coord")
    public short y() {
        return STBRPRect.ny(this.address());
    }

    @NativeType(value="int")
    public boolean was_packed() {
        return STBRPRect.nwas_packed(this.address()) != 0;
    }

    public STBRPRect id(int n) {
        STBRPRect.nid(this.address(), n);
        return this;
    }

    public STBRPRect w(@NativeType(value="stbrp_coord") short s) {
        STBRPRect.nw(this.address(), s);
        return this;
    }

    public STBRPRect h(@NativeType(value="stbrp_coord") short s) {
        STBRPRect.nh(this.address(), s);
        return this;
    }

    public STBRPRect x(@NativeType(value="stbrp_coord") short s) {
        STBRPRect.nx(this.address(), s);
        return this;
    }

    public STBRPRect y(@NativeType(value="stbrp_coord") short s) {
        STBRPRect.ny(this.address(), s);
        return this;
    }

    public STBRPRect was_packed(@NativeType(value="int") boolean bl) {
        STBRPRect.nwas_packed(this.address(), bl ? 1 : 0);
        return this;
    }

    public STBRPRect set(int n, short s, short s2, short s3, short s4, boolean bl) {
        this.id(n);
        this.w(s);
        this.h(s2);
        this.x(s3);
        this.y(s4);
        this.was_packed(bl);
        return this;
    }

    public STBRPRect set(STBRPRect sTBRPRect) {
        MemoryUtil.memCopy(sTBRPRect.address(), this.address(), SIZEOF);
        return this;
    }

    public static STBRPRect malloc() {
        return STBRPRect.wrap(STBRPRect.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static STBRPRect calloc() {
        return STBRPRect.wrap(STBRPRect.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static STBRPRect create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return STBRPRect.wrap(STBRPRect.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static STBRPRect create(long l) {
        return STBRPRect.wrap(STBRPRect.class, l);
    }

    @Nullable
    public static STBRPRect createSafe(long l) {
        return l == 0L ? null : STBRPRect.wrap(STBRPRect.class, l);
    }

    public static Buffer malloc(int n) {
        return STBRPRect.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(STBRPRect.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return STBRPRect.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = STBRPRect.__create(n, SIZEOF);
        return STBRPRect.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return STBRPRect.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : STBRPRect.wrap(Buffer.class, l, n);
    }

    public static STBRPRect mallocStack() {
        return STBRPRect.mallocStack(MemoryStack.stackGet());
    }

    public static STBRPRect callocStack() {
        return STBRPRect.callocStack(MemoryStack.stackGet());
    }

    public static STBRPRect mallocStack(MemoryStack memoryStack) {
        return STBRPRect.wrap(STBRPRect.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static STBRPRect callocStack(MemoryStack memoryStack) {
        return STBRPRect.wrap(STBRPRect.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return STBRPRect.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return STBRPRect.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return STBRPRect.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return STBRPRect.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static int nid(long l) {
        return UNSAFE.getInt(null, l + (long)ID);
    }

    public static short nw(long l) {
        return UNSAFE.getShort(null, l + (long)W);
    }

    public static short nh(long l) {
        return UNSAFE.getShort(null, l + (long)H);
    }

    public static short nx(long l) {
        return UNSAFE.getShort(null, l + (long)X);
    }

    public static short ny(long l) {
        return UNSAFE.getShort(null, l + (long)Y);
    }

    public static int nwas_packed(long l) {
        return UNSAFE.getInt(null, l + (long)WAS_PACKED);
    }

    public static void nid(long l, int n) {
        UNSAFE.putInt(null, l + (long)ID, n);
    }

    public static void nw(long l, short s) {
        UNSAFE.putShort(null, l + (long)W, s);
    }

    public static void nh(long l, short s) {
        UNSAFE.putShort(null, l + (long)H, s);
    }

    public static void nx(long l, short s) {
        UNSAFE.putShort(null, l + (long)X, s);
    }

    public static void ny(long l, short s) {
        UNSAFE.putShort(null, l + (long)Y, s);
    }

    public static void nwas_packed(long l, int n) {
        UNSAFE.putInt(null, l + (long)WAS_PACKED, n);
    }

    static {
        Struct.Layout layout = STBRPRect.__struct(STBRPRect.__member(4), STBRPRect.__member(2), STBRPRect.__member(2), STBRPRect.__member(2), STBRPRect.__member(2), STBRPRect.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        ID = layout.offsetof(0);
        W = layout.offsetof(1);
        H = layout.offsetof(2);
        X = layout.offsetof(3);
        Y = layout.offsetof(4);
        WAS_PACKED = layout.offsetof(5);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<STBRPRect, Buffer>
    implements NativeResource {
        private static final STBRPRect ELEMENT_FACTORY = STBRPRect.create(-1L);

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
        protected STBRPRect getElementFactory() {
            return ELEMENT_FACTORY;
        }

        public int id() {
            return STBRPRect.nid(this.address());
        }

        @NativeType(value="stbrp_coord")
        public short w() {
            return STBRPRect.nw(this.address());
        }

        @NativeType(value="stbrp_coord")
        public short h() {
            return STBRPRect.nh(this.address());
        }

        @NativeType(value="stbrp_coord")
        public short x() {
            return STBRPRect.nx(this.address());
        }

        @NativeType(value="stbrp_coord")
        public short y() {
            return STBRPRect.ny(this.address());
        }

        @NativeType(value="int")
        public boolean was_packed() {
            return STBRPRect.nwas_packed(this.address()) != 0;
        }

        public Buffer id(int n) {
            STBRPRect.nid(this.address(), n);
            return this;
        }

        public Buffer w(@NativeType(value="stbrp_coord") short s) {
            STBRPRect.nw(this.address(), s);
            return this;
        }

        public Buffer h(@NativeType(value="stbrp_coord") short s) {
            STBRPRect.nh(this.address(), s);
            return this;
        }

        public Buffer x(@NativeType(value="stbrp_coord") short s) {
            STBRPRect.nx(this.address(), s);
            return this;
        }

        public Buffer y(@NativeType(value="stbrp_coord") short s) {
            STBRPRect.ny(this.address(), s);
            return this;
        }

        public Buffer was_packed(@NativeType(value="int") boolean bl) {
            STBRPRect.nwas_packed(this.address(), bl ? 1 : 0);
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

