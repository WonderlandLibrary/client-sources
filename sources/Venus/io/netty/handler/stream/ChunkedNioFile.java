/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedInput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ChunkedNioFile
implements ChunkedInput<ByteBuf> {
    private final FileChannel in;
    private final long startOffset;
    private final long endOffset;
    private final int chunkSize;
    private long offset;

    public ChunkedNioFile(File file) throws IOException {
        this(new FileInputStream(file).getChannel());
    }

    public ChunkedNioFile(File file, int n) throws IOException {
        this(new FileInputStream(file).getChannel(), n);
    }

    public ChunkedNioFile(FileChannel fileChannel) throws IOException {
        this(fileChannel, 8192);
    }

    public ChunkedNioFile(FileChannel fileChannel, int n) throws IOException {
        this(fileChannel, 0L, fileChannel.size(), n);
    }

    public ChunkedNioFile(FileChannel fileChannel, long l, long l2, int n) throws IOException {
        if (fileChannel == null) {
            throw new NullPointerException("in");
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
        if (l != 0L) {
            fileChannel.position(l);
        }
        this.in = fileChannel;
        this.chunkSize = n;
        this.offset = this.startOffset = l;
        this.endOffset = l + l2;
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
        return this.offset >= this.endOffset || !this.in.isOpen();
    }

    @Override
    public void close() throws Exception {
        this.in.close();
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
        ByteBuf byteBuf = byteBufAllocator.buffer(n);
        boolean bl = true;
        try {
            int n2;
            int n3 = 0;
            while ((n2 = byteBuf.writeBytes(this.in, n - n3)) >= 0 && (n3 += n2) != n) {
            }
            this.offset += (long)n3;
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

