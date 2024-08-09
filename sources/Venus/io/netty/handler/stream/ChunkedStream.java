/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedInput;
import java.io.InputStream;
import java.io.PushbackInputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ChunkedStream
implements ChunkedInput<ByteBuf> {
    static final int DEFAULT_CHUNK_SIZE = 8192;
    private final PushbackInputStream in;
    private final int chunkSize;
    private long offset;
    private boolean closed;

    public ChunkedStream(InputStream inputStream) {
        this(inputStream, 8192);
    }

    public ChunkedStream(InputStream inputStream, int n) {
        if (inputStream == null) {
            throw new NullPointerException("in");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("chunkSize: " + n + " (expected: a positive integer)");
        }
        this.in = inputStream instanceof PushbackInputStream ? (PushbackInputStream)inputStream : new PushbackInputStream(inputStream);
        this.chunkSize = n;
    }

    public long transferredBytes() {
        return this.offset;
    }

    @Override
    public boolean isEndOfInput() throws Exception {
        if (this.closed) {
            return false;
        }
        int n = this.in.read();
        if (n < 0) {
            return false;
        }
        this.in.unread(n);
        return true;
    }

    @Override
    public void close() throws Exception {
        this.closed = true;
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
        if (this.isEndOfInput()) {
            return null;
        }
        int n = this.in.available();
        int n2 = n <= 0 ? this.chunkSize : Math.min(this.chunkSize, this.in.available());
        boolean bl = true;
        ByteBuf byteBuf = byteBufAllocator.buffer(n2);
        try {
            this.offset += (long)byteBuf.writeBytes(this.in, n2);
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

