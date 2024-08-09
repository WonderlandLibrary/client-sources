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

public class PIXELFORMATDESCRIPTOR
extends Struct
implements NativeResource {
    public static final int SIZEOF;
    public static final int ALIGNOF;
    public static final int NSIZE;
    public static final int NVERSION;
    public static final int DWFLAGS;
    public static final int IPIXELTYPE;
    public static final int CCOLORBITS;
    public static final int CREDBITS;
    public static final int CREDSHIFT;
    public static final int CGREENBITS;
    public static final int CGREENSHIFT;
    public static final int CBLUEBITS;
    public static final int CBLUESHIFT;
    public static final int CALPHABITS;
    public static final int CALPHASHIFT;
    public static final int CACCUMBITS;
    public static final int CACCUMREDBITS;
    public static final int CACCUMGREENBITS;
    public static final int CACCUMBLUEBITS;
    public static final int CACCUMALPHABITS;
    public static final int CDEPTHBITS;
    public static final int CSTENCILBITS;
    public static final int CAUXBUFFERS;
    public static final int ILAYERTYPE;
    public static final int BRESERVED;
    public static final int DWLAYERMASK;
    public static final int DWVISIBLEMASK;
    public static final int DWDAMAGEMASK;

    public PIXELFORMATDESCRIPTOR(ByteBuffer byteBuffer) {
        super(MemoryUtil.memAddress(byteBuffer), PIXELFORMATDESCRIPTOR.__checkContainer(byteBuffer, SIZEOF));
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    @NativeType(value="WORD")
    public short nSize() {
        return PIXELFORMATDESCRIPTOR.nnSize(this.address());
    }

    @NativeType(value="WORD")
    public short nVersion() {
        return PIXELFORMATDESCRIPTOR.nnVersion(this.address());
    }

    @NativeType(value="DWORD")
    public int dwFlags() {
        return PIXELFORMATDESCRIPTOR.ndwFlags(this.address());
    }

    @NativeType(value="BYTE")
    public byte iPixelType() {
        return PIXELFORMATDESCRIPTOR.niPixelType(this.address());
    }

    @NativeType(value="BYTE")
    public byte cColorBits() {
        return PIXELFORMATDESCRIPTOR.ncColorBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cRedBits() {
        return PIXELFORMATDESCRIPTOR.ncRedBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cRedShift() {
        return PIXELFORMATDESCRIPTOR.ncRedShift(this.address());
    }

    @NativeType(value="BYTE")
    public byte cGreenBits() {
        return PIXELFORMATDESCRIPTOR.ncGreenBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cGreenShift() {
        return PIXELFORMATDESCRIPTOR.ncGreenShift(this.address());
    }

    @NativeType(value="BYTE")
    public byte cBlueBits() {
        return PIXELFORMATDESCRIPTOR.ncBlueBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cBlueShift() {
        return PIXELFORMATDESCRIPTOR.ncBlueShift(this.address());
    }

    @NativeType(value="BYTE")
    public byte cAlphaBits() {
        return PIXELFORMATDESCRIPTOR.ncAlphaBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cAlphaShift() {
        return PIXELFORMATDESCRIPTOR.ncAlphaShift(this.address());
    }

    @NativeType(value="BYTE")
    public byte cAccumBits() {
        return PIXELFORMATDESCRIPTOR.ncAccumBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cAccumRedBits() {
        return PIXELFORMATDESCRIPTOR.ncAccumRedBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cAccumGreenBits() {
        return PIXELFORMATDESCRIPTOR.ncAccumGreenBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cAccumBlueBits() {
        return PIXELFORMATDESCRIPTOR.ncAccumBlueBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cAccumAlphaBits() {
        return PIXELFORMATDESCRIPTOR.ncAccumAlphaBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cDepthBits() {
        return PIXELFORMATDESCRIPTOR.ncDepthBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cStencilBits() {
        return PIXELFORMATDESCRIPTOR.ncStencilBits(this.address());
    }

    @NativeType(value="BYTE")
    public byte cAuxBuffers() {
        return PIXELFORMATDESCRIPTOR.ncAuxBuffers(this.address());
    }

    @NativeType(value="BYTE")
    public byte iLayerType() {
        return PIXELFORMATDESCRIPTOR.niLayerType(this.address());
    }

    @NativeType(value="BYTE")
    public byte bReserved() {
        return PIXELFORMATDESCRIPTOR.nbReserved(this.address());
    }

    @NativeType(value="DWORD")
    public int dwLayerMask() {
        return PIXELFORMATDESCRIPTOR.ndwLayerMask(this.address());
    }

    @NativeType(value="DWORD")
    public int dwVisibleMask() {
        return PIXELFORMATDESCRIPTOR.ndwVisibleMask(this.address());
    }

    @NativeType(value="DWORD")
    public int dwDamageMask() {
        return PIXELFORMATDESCRIPTOR.ndwDamageMask(this.address());
    }

    public PIXELFORMATDESCRIPTOR nSize(@NativeType(value="WORD") short s) {
        PIXELFORMATDESCRIPTOR.nnSize(this.address(), s);
        return this;
    }

    public PIXELFORMATDESCRIPTOR nVersion(@NativeType(value="WORD") short s) {
        PIXELFORMATDESCRIPTOR.nnVersion(this.address(), s);
        return this;
    }

    public PIXELFORMATDESCRIPTOR dwFlags(@NativeType(value="DWORD") int n) {
        PIXELFORMATDESCRIPTOR.ndwFlags(this.address(), n);
        return this;
    }

    public PIXELFORMATDESCRIPTOR iPixelType(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.niPixelType(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cColorBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncColorBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cRedBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncRedBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cRedShift(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncRedShift(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cGreenBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncGreenBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cGreenShift(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncGreenShift(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cBlueBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncBlueBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cBlueShift(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncBlueShift(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cAlphaBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncAlphaBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cAlphaShift(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncAlphaShift(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cAccumBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncAccumBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cAccumRedBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncAccumRedBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cAccumGreenBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncAccumGreenBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cAccumBlueBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncAccumBlueBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cAccumAlphaBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncAccumAlphaBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cDepthBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncDepthBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cStencilBits(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncStencilBits(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR cAuxBuffers(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.ncAuxBuffers(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR iLayerType(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.niLayerType(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR bReserved(@NativeType(value="BYTE") byte by) {
        PIXELFORMATDESCRIPTOR.nbReserved(this.address(), by);
        return this;
    }

    public PIXELFORMATDESCRIPTOR dwLayerMask(@NativeType(value="DWORD") int n) {
        PIXELFORMATDESCRIPTOR.ndwLayerMask(this.address(), n);
        return this;
    }

    public PIXELFORMATDESCRIPTOR dwVisibleMask(@NativeType(value="DWORD") int n) {
        PIXELFORMATDESCRIPTOR.ndwVisibleMask(this.address(), n);
        return this;
    }

    public PIXELFORMATDESCRIPTOR dwDamageMask(@NativeType(value="DWORD") int n) {
        PIXELFORMATDESCRIPTOR.ndwDamageMask(this.address(), n);
        return this;
    }

    public PIXELFORMATDESCRIPTOR set(short s, short s2, int n, byte by, byte by2, byte by3, byte by4, byte by5, byte by6, byte by7, byte by8, byte by9, byte by10, byte by11, byte by12, byte by13, byte by14, byte by15, byte by16, byte by17, byte by18, byte by19, byte by20, int n2, int n3, int n4) {
        this.nSize(s);
        this.nVersion(s2);
        this.dwFlags(n);
        this.iPixelType(by);
        this.cColorBits(by2);
        this.cRedBits(by3);
        this.cRedShift(by4);
        this.cGreenBits(by5);
        this.cGreenShift(by6);
        this.cBlueBits(by7);
        this.cBlueShift(by8);
        this.cAlphaBits(by9);
        this.cAlphaShift(by10);
        this.cAccumBits(by11);
        this.cAccumRedBits(by12);
        this.cAccumGreenBits(by13);
        this.cAccumBlueBits(by14);
        this.cAccumAlphaBits(by15);
        this.cDepthBits(by16);
        this.cStencilBits(by17);
        this.cAuxBuffers(by18);
        this.iLayerType(by19);
        this.bReserved(by20);
        this.dwLayerMask(n2);
        this.dwVisibleMask(n3);
        this.dwDamageMask(n4);
        return this;
    }

    public PIXELFORMATDESCRIPTOR set(PIXELFORMATDESCRIPTOR pIXELFORMATDESCRIPTOR) {
        MemoryUtil.memCopy(pIXELFORMATDESCRIPTOR.address(), this.address(), SIZEOF);
        return this;
    }

    public static PIXELFORMATDESCRIPTOR malloc() {
        return PIXELFORMATDESCRIPTOR.wrap(PIXELFORMATDESCRIPTOR.class, MemoryUtil.nmemAllocChecked(SIZEOF));
    }

    public static PIXELFORMATDESCRIPTOR calloc() {
        return PIXELFORMATDESCRIPTOR.wrap(PIXELFORMATDESCRIPTOR.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
    }

    public static PIXELFORMATDESCRIPTOR create() {
        ByteBuffer byteBuffer = BufferUtils.createByteBuffer(SIZEOF);
        return PIXELFORMATDESCRIPTOR.wrap(PIXELFORMATDESCRIPTOR.class, MemoryUtil.memAddress(byteBuffer), byteBuffer);
    }

    public static PIXELFORMATDESCRIPTOR create(long l) {
        return PIXELFORMATDESCRIPTOR.wrap(PIXELFORMATDESCRIPTOR.class, l);
    }

    @Nullable
    public static PIXELFORMATDESCRIPTOR createSafe(long l) {
        return l == 0L ? null : PIXELFORMATDESCRIPTOR.wrap(PIXELFORMATDESCRIPTOR.class, l);
    }

    public static Buffer malloc(int n) {
        return PIXELFORMATDESCRIPTOR.wrap(Buffer.class, MemoryUtil.nmemAllocChecked(PIXELFORMATDESCRIPTOR.__checkMalloc(n, SIZEOF)), n);
    }

    public static Buffer calloc(int n) {
        return PIXELFORMATDESCRIPTOR.wrap(Buffer.class, MemoryUtil.nmemCallocChecked(n, SIZEOF), n);
    }

    public static Buffer create(int n) {
        ByteBuffer byteBuffer = PIXELFORMATDESCRIPTOR.__create(n, SIZEOF);
        return PIXELFORMATDESCRIPTOR.wrap(Buffer.class, MemoryUtil.memAddress(byteBuffer), n, byteBuffer);
    }

    public static Buffer create(long l, int n) {
        return PIXELFORMATDESCRIPTOR.wrap(Buffer.class, l, n);
    }

    @Nullable
    public static Buffer createSafe(long l, int n) {
        return l == 0L ? null : PIXELFORMATDESCRIPTOR.wrap(Buffer.class, l, n);
    }

    public static PIXELFORMATDESCRIPTOR mallocStack() {
        return PIXELFORMATDESCRIPTOR.mallocStack(MemoryStack.stackGet());
    }

    public static PIXELFORMATDESCRIPTOR callocStack() {
        return PIXELFORMATDESCRIPTOR.callocStack(MemoryStack.stackGet());
    }

    public static PIXELFORMATDESCRIPTOR mallocStack(MemoryStack memoryStack) {
        return PIXELFORMATDESCRIPTOR.wrap(PIXELFORMATDESCRIPTOR.class, memoryStack.nmalloc(ALIGNOF, SIZEOF));
    }

    public static PIXELFORMATDESCRIPTOR callocStack(MemoryStack memoryStack) {
        return PIXELFORMATDESCRIPTOR.wrap(PIXELFORMATDESCRIPTOR.class, memoryStack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public static Buffer mallocStack(int n) {
        return PIXELFORMATDESCRIPTOR.mallocStack(n, MemoryStack.stackGet());
    }

    public static Buffer callocStack(int n) {
        return PIXELFORMATDESCRIPTOR.callocStack(n, MemoryStack.stackGet());
    }

    public static Buffer mallocStack(int n, MemoryStack memoryStack) {
        return PIXELFORMATDESCRIPTOR.wrap(Buffer.class, memoryStack.nmalloc(ALIGNOF, n * SIZEOF), n);
    }

    public static Buffer callocStack(int n, MemoryStack memoryStack) {
        return PIXELFORMATDESCRIPTOR.wrap(Buffer.class, memoryStack.ncalloc(ALIGNOF, n, SIZEOF), n);
    }

    public static short nnSize(long l) {
        return UNSAFE.getShort(null, l + (long)NSIZE);
    }

    public static short nnVersion(long l) {
        return UNSAFE.getShort(null, l + (long)NVERSION);
    }

    public static int ndwFlags(long l) {
        return UNSAFE.getInt(null, l + (long)DWFLAGS);
    }

    public static byte niPixelType(long l) {
        return UNSAFE.getByte(null, l + (long)IPIXELTYPE);
    }

    public static byte ncColorBits(long l) {
        return UNSAFE.getByte(null, l + (long)CCOLORBITS);
    }

    public static byte ncRedBits(long l) {
        return UNSAFE.getByte(null, l + (long)CREDBITS);
    }

    public static byte ncRedShift(long l) {
        return UNSAFE.getByte(null, l + (long)CREDSHIFT);
    }

    public static byte ncGreenBits(long l) {
        return UNSAFE.getByte(null, l + (long)CGREENBITS);
    }

    public static byte ncGreenShift(long l) {
        return UNSAFE.getByte(null, l + (long)CGREENSHIFT);
    }

    public static byte ncBlueBits(long l) {
        return UNSAFE.getByte(null, l + (long)CBLUEBITS);
    }

    public static byte ncBlueShift(long l) {
        return UNSAFE.getByte(null, l + (long)CBLUESHIFT);
    }

    public static byte ncAlphaBits(long l) {
        return UNSAFE.getByte(null, l + (long)CALPHABITS);
    }

    public static byte ncAlphaShift(long l) {
        return UNSAFE.getByte(null, l + (long)CALPHASHIFT);
    }

    public static byte ncAccumBits(long l) {
        return UNSAFE.getByte(null, l + (long)CACCUMBITS);
    }

    public static byte ncAccumRedBits(long l) {
        return UNSAFE.getByte(null, l + (long)CACCUMREDBITS);
    }

    public static byte ncAccumGreenBits(long l) {
        return UNSAFE.getByte(null, l + (long)CACCUMGREENBITS);
    }

    public static byte ncAccumBlueBits(long l) {
        return UNSAFE.getByte(null, l + (long)CACCUMBLUEBITS);
    }

    public static byte ncAccumAlphaBits(long l) {
        return UNSAFE.getByte(null, l + (long)CACCUMALPHABITS);
    }

    public static byte ncDepthBits(long l) {
        return UNSAFE.getByte(null, l + (long)CDEPTHBITS);
    }

    public static byte ncStencilBits(long l) {
        return UNSAFE.getByte(null, l + (long)CSTENCILBITS);
    }

    public static byte ncAuxBuffers(long l) {
        return UNSAFE.getByte(null, l + (long)CAUXBUFFERS);
    }

    public static byte niLayerType(long l) {
        return UNSAFE.getByte(null, l + (long)ILAYERTYPE);
    }

    public static byte nbReserved(long l) {
        return UNSAFE.getByte(null, l + (long)BRESERVED);
    }

    public static int ndwLayerMask(long l) {
        return UNSAFE.getInt(null, l + (long)DWLAYERMASK);
    }

    public static int ndwVisibleMask(long l) {
        return UNSAFE.getInt(null, l + (long)DWVISIBLEMASK);
    }

    public static int ndwDamageMask(long l) {
        return UNSAFE.getInt(null, l + (long)DWDAMAGEMASK);
    }

    public static void nnSize(long l, short s) {
        UNSAFE.putShort(null, l + (long)NSIZE, s);
    }

    public static void nnVersion(long l, short s) {
        UNSAFE.putShort(null, l + (long)NVERSION, s);
    }

    public static void ndwFlags(long l, int n) {
        UNSAFE.putInt(null, l + (long)DWFLAGS, n);
    }

    public static void niPixelType(long l, byte by) {
        UNSAFE.putByte(null, l + (long)IPIXELTYPE, by);
    }

    public static void ncColorBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CCOLORBITS, by);
    }

    public static void ncRedBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CREDBITS, by);
    }

    public static void ncRedShift(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CREDSHIFT, by);
    }

    public static void ncGreenBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CGREENBITS, by);
    }

    public static void ncGreenShift(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CGREENSHIFT, by);
    }

    public static void ncBlueBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CBLUEBITS, by);
    }

    public static void ncBlueShift(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CBLUESHIFT, by);
    }

    public static void ncAlphaBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CALPHABITS, by);
    }

    public static void ncAlphaShift(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CALPHASHIFT, by);
    }

    public static void ncAccumBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CACCUMBITS, by);
    }

    public static void ncAccumRedBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CACCUMREDBITS, by);
    }

    public static void ncAccumGreenBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CACCUMGREENBITS, by);
    }

    public static void ncAccumBlueBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CACCUMBLUEBITS, by);
    }

    public static void ncAccumAlphaBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CACCUMALPHABITS, by);
    }

    public static void ncDepthBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CDEPTHBITS, by);
    }

    public static void ncStencilBits(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CSTENCILBITS, by);
    }

    public static void ncAuxBuffers(long l, byte by) {
        UNSAFE.putByte(null, l + (long)CAUXBUFFERS, by);
    }

    public static void niLayerType(long l, byte by) {
        UNSAFE.putByte(null, l + (long)ILAYERTYPE, by);
    }

    public static void nbReserved(long l, byte by) {
        UNSAFE.putByte(null, l + (long)BRESERVED, by);
    }

    public static void ndwLayerMask(long l, int n) {
        UNSAFE.putInt(null, l + (long)DWLAYERMASK, n);
    }

    public static void ndwVisibleMask(long l, int n) {
        UNSAFE.putInt(null, l + (long)DWVISIBLEMASK, n);
    }

    public static void ndwDamageMask(long l, int n) {
        UNSAFE.putInt(null, l + (long)DWDAMAGEMASK, n);
    }

    static {
        Struct.Layout layout = PIXELFORMATDESCRIPTOR.__struct(PIXELFORMATDESCRIPTOR.__member(2), PIXELFORMATDESCRIPTOR.__member(2), PIXELFORMATDESCRIPTOR.__member(4), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(1), PIXELFORMATDESCRIPTOR.__member(4), PIXELFORMATDESCRIPTOR.__member(4), PIXELFORMATDESCRIPTOR.__member(4));
        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
        NSIZE = layout.offsetof(0);
        NVERSION = layout.offsetof(1);
        DWFLAGS = layout.offsetof(2);
        IPIXELTYPE = layout.offsetof(3);
        CCOLORBITS = layout.offsetof(4);
        CREDBITS = layout.offsetof(5);
        CREDSHIFT = layout.offsetof(6);
        CGREENBITS = layout.offsetof(7);
        CGREENSHIFT = layout.offsetof(8);
        CBLUEBITS = layout.offsetof(9);
        CBLUESHIFT = layout.offsetof(10);
        CALPHABITS = layout.offsetof(11);
        CALPHASHIFT = layout.offsetof(12);
        CACCUMBITS = layout.offsetof(13);
        CACCUMREDBITS = layout.offsetof(14);
        CACCUMGREENBITS = layout.offsetof(15);
        CACCUMBLUEBITS = layout.offsetof(16);
        CACCUMALPHABITS = layout.offsetof(17);
        CDEPTHBITS = layout.offsetof(18);
        CSTENCILBITS = layout.offsetof(19);
        CAUXBUFFERS = layout.offsetof(20);
        ILAYERTYPE = layout.offsetof(21);
        BRESERVED = layout.offsetof(22);
        DWLAYERMASK = layout.offsetof(23);
        DWVISIBLEMASK = layout.offsetof(24);
        DWDAMAGEMASK = layout.offsetof(25);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Buffer
    extends StructBuffer<PIXELFORMATDESCRIPTOR, Buffer>
    implements NativeResource {
        private static final PIXELFORMATDESCRIPTOR ELEMENT_FACTORY = PIXELFORMATDESCRIPTOR.create(-1L);

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
        protected PIXELFORMATDESCRIPTOR getElementFactory() {
            return ELEMENT_FACTORY;
        }

        @NativeType(value="WORD")
        public short nSize() {
            return PIXELFORMATDESCRIPTOR.nnSize(this.address());
        }

        @NativeType(value="WORD")
        public short nVersion() {
            return PIXELFORMATDESCRIPTOR.nnVersion(this.address());
        }

        @NativeType(value="DWORD")
        public int dwFlags() {
            return PIXELFORMATDESCRIPTOR.ndwFlags(this.address());
        }

        @NativeType(value="BYTE")
        public byte iPixelType() {
            return PIXELFORMATDESCRIPTOR.niPixelType(this.address());
        }

        @NativeType(value="BYTE")
        public byte cColorBits() {
            return PIXELFORMATDESCRIPTOR.ncColorBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cRedBits() {
            return PIXELFORMATDESCRIPTOR.ncRedBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cRedShift() {
            return PIXELFORMATDESCRIPTOR.ncRedShift(this.address());
        }

        @NativeType(value="BYTE")
        public byte cGreenBits() {
            return PIXELFORMATDESCRIPTOR.ncGreenBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cGreenShift() {
            return PIXELFORMATDESCRIPTOR.ncGreenShift(this.address());
        }

        @NativeType(value="BYTE")
        public byte cBlueBits() {
            return PIXELFORMATDESCRIPTOR.ncBlueBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cBlueShift() {
            return PIXELFORMATDESCRIPTOR.ncBlueShift(this.address());
        }

        @NativeType(value="BYTE")
        public byte cAlphaBits() {
            return PIXELFORMATDESCRIPTOR.ncAlphaBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cAlphaShift() {
            return PIXELFORMATDESCRIPTOR.ncAlphaShift(this.address());
        }

        @NativeType(value="BYTE")
        public byte cAccumBits() {
            return PIXELFORMATDESCRIPTOR.ncAccumBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cAccumRedBits() {
            return PIXELFORMATDESCRIPTOR.ncAccumRedBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cAccumGreenBits() {
            return PIXELFORMATDESCRIPTOR.ncAccumGreenBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cAccumBlueBits() {
            return PIXELFORMATDESCRIPTOR.ncAccumBlueBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cAccumAlphaBits() {
            return PIXELFORMATDESCRIPTOR.ncAccumAlphaBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cDepthBits() {
            return PIXELFORMATDESCRIPTOR.ncDepthBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cStencilBits() {
            return PIXELFORMATDESCRIPTOR.ncStencilBits(this.address());
        }

        @NativeType(value="BYTE")
        public byte cAuxBuffers() {
            return PIXELFORMATDESCRIPTOR.ncAuxBuffers(this.address());
        }

        @NativeType(value="BYTE")
        public byte iLayerType() {
            return PIXELFORMATDESCRIPTOR.niLayerType(this.address());
        }

        @NativeType(value="BYTE")
        public byte bReserved() {
            return PIXELFORMATDESCRIPTOR.nbReserved(this.address());
        }

        @NativeType(value="DWORD")
        public int dwLayerMask() {
            return PIXELFORMATDESCRIPTOR.ndwLayerMask(this.address());
        }

        @NativeType(value="DWORD")
        public int dwVisibleMask() {
            return PIXELFORMATDESCRIPTOR.ndwVisibleMask(this.address());
        }

        @NativeType(value="DWORD")
        public int dwDamageMask() {
            return PIXELFORMATDESCRIPTOR.ndwDamageMask(this.address());
        }

        public Buffer nSize(@NativeType(value="WORD") short s) {
            PIXELFORMATDESCRIPTOR.nnSize(this.address(), s);
            return this;
        }

        public Buffer nVersion(@NativeType(value="WORD") short s) {
            PIXELFORMATDESCRIPTOR.nnVersion(this.address(), s);
            return this;
        }

        public Buffer dwFlags(@NativeType(value="DWORD") int n) {
            PIXELFORMATDESCRIPTOR.ndwFlags(this.address(), n);
            return this;
        }

        public Buffer iPixelType(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.niPixelType(this.address(), by);
            return this;
        }

        public Buffer cColorBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncColorBits(this.address(), by);
            return this;
        }

        public Buffer cRedBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncRedBits(this.address(), by);
            return this;
        }

        public Buffer cRedShift(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncRedShift(this.address(), by);
            return this;
        }

        public Buffer cGreenBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncGreenBits(this.address(), by);
            return this;
        }

        public Buffer cGreenShift(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncGreenShift(this.address(), by);
            return this;
        }

        public Buffer cBlueBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncBlueBits(this.address(), by);
            return this;
        }

        public Buffer cBlueShift(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncBlueShift(this.address(), by);
            return this;
        }

        public Buffer cAlphaBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncAlphaBits(this.address(), by);
            return this;
        }

        public Buffer cAlphaShift(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncAlphaShift(this.address(), by);
            return this;
        }

        public Buffer cAccumBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncAccumBits(this.address(), by);
            return this;
        }

        public Buffer cAccumRedBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncAccumRedBits(this.address(), by);
            return this;
        }

        public Buffer cAccumGreenBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncAccumGreenBits(this.address(), by);
            return this;
        }

        public Buffer cAccumBlueBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncAccumBlueBits(this.address(), by);
            return this;
        }

        public Buffer cAccumAlphaBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncAccumAlphaBits(this.address(), by);
            return this;
        }

        public Buffer cDepthBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncDepthBits(this.address(), by);
            return this;
        }

        public Buffer cStencilBits(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncStencilBits(this.address(), by);
            return this;
        }

        public Buffer cAuxBuffers(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.ncAuxBuffers(this.address(), by);
            return this;
        }

        public Buffer iLayerType(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.niLayerType(this.address(), by);
            return this;
        }

        public Buffer bReserved(@NativeType(value="BYTE") byte by) {
            PIXELFORMATDESCRIPTOR.nbReserved(this.address(), by);
            return this;
        }

        public Buffer dwLayerMask(@NativeType(value="DWORD") int n) {
            PIXELFORMATDESCRIPTOR.ndwLayerMask(this.address(), n);
            return this;
        }

        public Buffer dwVisibleMask(@NativeType(value="DWORD") int n) {
            PIXELFORMATDESCRIPTOR.ndwVisibleMask(this.address(), n);
            return this;
        }

        public Buffer dwDamageMask(@NativeType(value="DWORD") int n) {
            PIXELFORMATDESCRIPTOR.ndwDamageMask(this.address(), n);
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

