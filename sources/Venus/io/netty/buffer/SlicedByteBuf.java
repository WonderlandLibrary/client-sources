/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractUnpooledSlicedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

@Deprecated
public class SlicedByteBuf
extends AbstractUnpooledSlicedByteBuf {
    private int length;

    public SlicedByteBuf(ByteBuf byteBuf, int n, int n2) {
        super(byteBuf, n, n2);
    }

    @Override
    final void initLength(int n) {
        this.length = n;
    }

    @Override
    final int length() {
        return this.length;
    }

    @Override
    public int capacity() {
        return this.length;
    }

    @Override
    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) {
        return super.forEachByteDesc(n, n2, byteProcessor);
    }

    @Override
    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) {
        return super.forEachByte(n, n2, byteProcessor);
    }

    @Override
    public ByteBuffer[] nioBuffers(int n, int n2) {
        return super.nioBuffers(n, n2);
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        return super.nioBuffer(n, n2);
    }

    @Override
    public int nioBufferCount() {
        return super.nioBufferCount();
    }

    @Override
    public int setBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return super.setBytes(n, fileChannel, l, n2);
    }

    @Override
    public int setBytes(int n, ScatteringByteChannel scatteringByteChannel, int n2) throws IOException {
        return super.setBytes(n, scatteringByteChannel, n2);
    }

    @Override
    public int setBytes(int n, InputStream inputStream, int n2) throws IOException {
        return super.setBytes(n, inputStream, n2);
    }

    @Override
    public int getBytes(int n, FileChannel fileChannel, long l, int n2) throws IOException {
        return super.getBytes(n, fileChannel, l, n2);
    }

    @Override
    public int getBytes(int n, GatheringByteChannel gatheringByteChannel, int n2) throws IOException {
        return super.getBytes(n, gatheringByteChannel, n2);
    }

    @Override
    public ByteBuf getBytes(int n, OutputStream outputStream, int n2) throws IOException {
        return super.getBytes(n, outputStream, n2);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuffer byteBuffer) {
        return super.setBytes(n, byteBuffer);
    }

    @Override
    public ByteBuf setBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        return super.setBytes(n, byteBuf, n2, n3);
    }

    @Override
    public ByteBuf setBytes(int n, byte[] byArray, int n2, int n3) {
        return super.setBytes(n, byArray, n2, n3);
    }

    @Override
    public ByteBuf setLongLE(int n, long l) {
        return super.setLongLE(n, l);
    }

    @Override
    public ByteBuf setLong(int n, long l) {
        return super.setLong(n, l);
    }

    @Override
    public ByteBuf setIntLE(int n, int n2) {
        return super.setIntLE(n, n2);
    }

    @Override
    public ByteBuf setInt(int n, int n2) {
        return super.setInt(n, n2);
    }

    @Override
    public ByteBuf setMediumLE(int n, int n2) {
        return super.setMediumLE(n, n2);
    }

    @Override
    public ByteBuf setMedium(int n, int n2) {
        return super.setMedium(n, n2);
    }

    @Override
    public ByteBuf setShortLE(int n, int n2) {
        return super.setShortLE(n, n2);
    }

    @Override
    public ByteBuf setShort(int n, int n2) {
        return super.setShort(n, n2);
    }

    @Override
    public CharSequence getCharSequence(int n, int n2, Charset charset) {
        return super.getCharSequence(n, n2, charset);
    }

    @Override
    public ByteBuf setByte(int n, int n2) {
        return super.setByte(n, n2);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuffer byteBuffer) {
        return super.getBytes(n, byteBuffer);
    }

    @Override
    public ByteBuf getBytes(int n, byte[] byArray, int n2, int n3) {
        return super.getBytes(n, byArray, n2, n3);
    }

    @Override
    public ByteBuf getBytes(int n, ByteBuf byteBuf, int n2, int n3) {
        return super.getBytes(n, byteBuf, n2, n3);
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        return super.slice(n, n2);
    }

    @Override
    public ByteBuf copy(int n, int n2) {
        return super.copy(n, n2);
    }

    @Override
    public ByteBuf duplicate() {
        return super.duplicate();
    }

    @Override
    public long getLongLE(int n) {
        return super.getLongLE(n);
    }

    @Override
    public long getLong(int n) {
        return super.getLong(n);
    }

    @Override
    public int getIntLE(int n) {
        return super.getIntLE(n);
    }

    @Override
    public int getInt(int n) {
        return super.getInt(n);
    }

    @Override
    public int getUnsignedMediumLE(int n) {
        return super.getUnsignedMediumLE(n);
    }

    @Override
    public int getUnsignedMedium(int n) {
        return super.getUnsignedMedium(n);
    }

    @Override
    public short getShortLE(int n) {
        return super.getShortLE(n);
    }

    @Override
    public short getShort(int n) {
        return super.getShort(n);
    }

    @Override
    public byte getByte(int n) {
        return super.getByte(n);
    }

    @Override
    public long memoryAddress() {
        return super.memoryAddress();
    }

    @Override
    public boolean hasMemoryAddress() {
        return super.hasMemoryAddress();
    }

    @Override
    public int arrayOffset() {
        return super.arrayOffset();
    }

    @Override
    public byte[] array() {
        return super.array();
    }

    @Override
    public boolean hasArray() {
        return super.hasArray();
    }

    @Override
    public ByteBuf capacity(int n) {
        return super.capacity(n);
    }

    @Override
    public boolean isDirect() {
        return super.isDirect();
    }

    @Override
    @Deprecated
    public ByteOrder order() {
        return super.order();
    }

    @Override
    public ByteBufAllocator alloc() {
        return super.alloc();
    }

    @Override
    public ByteBuf unwrap() {
        return super.unwrap();
    }
}

