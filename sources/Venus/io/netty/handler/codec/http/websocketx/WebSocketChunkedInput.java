/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.stream.ChunkedInput;
import io.netty.util.internal.ObjectUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class WebSocketChunkedInput
implements ChunkedInput<WebSocketFrame> {
    private final ChunkedInput<ByteBuf> input;
    private final int rsv;

    public WebSocketChunkedInput(ChunkedInput<ByteBuf> chunkedInput) {
        this(chunkedInput, 0);
    }

    public WebSocketChunkedInput(ChunkedInput<ByteBuf> chunkedInput, int n) {
        this.input = ObjectUtil.checkNotNull(chunkedInput, "input");
        this.rsv = n;
    }

    @Override
    public boolean isEndOfInput() throws Exception {
        return this.input.isEndOfInput();
    }

    @Override
    public void close() throws Exception {
        this.input.close();
    }

    @Override
    @Deprecated
    public WebSocketFrame readChunk(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.readChunk(channelHandlerContext.alloc());
    }

    @Override
    public WebSocketFrame readChunk(ByteBufAllocator byteBufAllocator) throws Exception {
        ByteBuf byteBuf = this.input.readChunk(byteBufAllocator);
        if (byteBuf == null) {
            return null;
        }
        return new ContinuationWebSocketFrame(this.input.isEndOfInput(), this.rsv, byteBuf);
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

