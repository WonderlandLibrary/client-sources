/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedInput;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ChunkedNioStream
implements ChunkedInput<ByteBuf> {
    private final ReadableByteChannel in;
    private final int chunkSize;
    private long offset;
    private final ByteBuffer byteBuffer;

    public ChunkedNioStream(ReadableByteChannel readableByteChannel) {
        this(readableByteChannel, 8192);
    }

    public ChunkedNioStream(ReadableByteChannel readableByteChannel, int n) {
        if (readableByteChannel == null) {
            throw new NullPointerException("in");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("chunkSize: " + n + " (expected: a positive integer)");
        }
        this.in = readableByteChannel;
        this.offset = 0L;
        this.chunkSize = n;
        this.byteBuffer = ByteBuffer.allocate(n);
    }

    public long transferredBytes() {
        return this.offset;
    }

    @Override
    public boolean isEndOfInput() throws Exception {
        if (this.byteBuffer.position() > 0) {
            return true;
        }
        if (this.in.isOpen()) {
            int n = this.in.read(this.byteBuffer);
            if (n < 0) {
                return false;
            }
            this.offset += (long)n;
            return true;
        }
        return false;
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
        int n;
        if (this.isEndOfInput()) {
            return null;
        }
        int n2 = this.byteBuffer.position();
        while ((n = this.in.read(this.byteBuffer)) >= 0) {
            this.offset += (long)n;
            if ((n2 += n) != this.chunkSize) continue;
            break;
        }
        this.byteBuffer.flip();
        n = 1;
        ByteBuf byteBuf = byteBufAllocator.buffer(this.byteBuffer.remaining());
        try {
            byteBuf.writeBytes(this.byteBuffer);
            this.byteBuffer.clear();
            n = 0;
            ByteBuf byteBuf2 = byteBuf;
            return byteBuf2;
        } finally {
            if (n != 0) {
                byteBuf.release();
            }
        }
    }

    @Override
    public long length() {
        return -1L;
    }

    @Override
    public long progress() {
        return this.offset;
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

