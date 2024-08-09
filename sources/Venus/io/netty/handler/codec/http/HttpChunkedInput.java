/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedInput;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class HttpChunkedInput
implements ChunkedInput<HttpContent> {
    private final ChunkedInput<ByteBuf> input;
    private final LastHttpContent lastHttpContent;
    private boolean sentLastChunk;

    public HttpChunkedInput(ChunkedInput<ByteBuf> chunkedInput) {
        this.input = chunkedInput;
        this.lastHttpContent = LastHttpContent.EMPTY_LAST_CONTENT;
    }

    public HttpChunkedInput(ChunkedInput<ByteBuf> chunkedInput, LastHttpContent lastHttpContent) {
        this.input = chunkedInput;
        this.lastHttpContent = lastHttpContent;
    }

    @Override
    public boolean isEndOfInput() throws Exception {
        if (this.input.isEndOfInput()) {
            return this.sentLastChunk;
        }
        return true;
    }

    @Override
    public void close() throws Exception {
        this.input.close();
    }

    @Override
    @Deprecated
    public HttpContent readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext.alloc());
    }

    @Override
    public HttpContent readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        if (this.input.isEndOfInput()) {
            if (this.sentLastChunk) {
                return null;
            }
            this.sentLastChunk = true;
            return this.lastHttpContent;
        }
        ByteBuf byteBuf = this.input.readChunk(byteBufAllocator);
        if (byteBuf == null) {
            return null;
        }
        return new DefaultHttpContent(byteBuf);
    }

    @Override
    public long length() {
        return this.input.length();
    }

    @Override
    public long progress() {
        return this.input.progress();
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

