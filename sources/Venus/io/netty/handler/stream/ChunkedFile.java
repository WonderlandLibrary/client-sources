/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedInput;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ChunkedFile
implements ChunkedInput<ByteBuf> {
    private final RandomAccessFile file;
    private final long startOffset;
    private final long endOffset;
    private final int chunkSize;
    private long offset;

    public ChunkedFile(File file) throws IOException {
        this(file, 8192);
    }

    public ChunkedFile(File file, int n) throws IOException {
        this(new RandomAccessFile(file, "r"), n);
    }

    public ChunkedFile(RandomAccessFile randomAccessFile) throws IOException {
        this(randomAccessFile, 8192);
    }

    public ChunkedFile(RandomAccessFile randomAccessFile, int n) throws IOException {
        this(randomAccessFile, 0L, randomAccessFile.length(), n);
    }

    public ChunkedFile(RandomAccessFile randomAccessFile, long l, long l2, int n) throws IOException {
        if (randomAccessFile == null) {
            throw new NullPointerException("file");
        }
        if (l < 0L) {
            throw new IllegalArgumentException("offset: " + l + " (expected: 0 or greater)");
        }
        if (l2 < 0L) {
            throw new IllegalArgumentException("length: " + l2 + " (expected: 0 or greater)");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("chunkSize: " + n + " (expected: a positive integer)");
        }
        this.file = randomAccessFile;
        this.offset = this.startOffset = l;
        this.endOffset = l + l2;
        this.chunkSize = n;
        randomAccessFile.seek(l);
    }

    public long startOffset() {
        return this.startOffset;
    }

    public long endOffset() {
        return this.endOffset;
    }

    public long currentOffset() {
        return this.offset;
    }

    @Override
    public boolean isEndOfInput() throws Exception {
        return this.offset >= this.endOffset || !this.file.getChannel().isOpen();
    }

    @Override
    public void close() throws Exception {
        this.file.close();
    }

    @Override
    @Deprecated
    public ByteBuf readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext.alloc());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ByteBuf readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        long l = this.offset;
        if (l >= this.endOffset) {
            return null;
        }
        int n = (int)Math.min((long)this.chunkSize, this.endOffset - l);
        ByteBuf byteBuf = byteBufAllocator.heapBuffer(n);
        boolean bl = true;
        try {
            this.file.readFully(byteBuf.array(), byteBuf.arrayOffset(), n);
            byteBuf.writerIndex(n);
            this.offset = l + (long)n;
            bl = false;
            ByteBuf byteBuf2 = byteBuf;
            return byteBuf2;
        } finally {
            if (bl) {
                byteBuf.release();
            }
        }
    }

    @Override
    public long length() {
        return this.endOffset - this.startOffset;
    }

    @Override
    public long progress() {
        return this.offset - this.startOffset;
    }

    @Override
    public Object readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        return this.readChunk(byteBufAllocator);
    }

    @Override
    @Deprecated
    public Object readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext);
    }
}

