/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.stream;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;

public interface ChunkedInput<B> {
    public boolean isEndOfInput() throws Exception;

    public void close() throws Exception;

    @Deprecated
    public B readChunk(ChannelHandlerContext var1) throws Exception;

    public B readChunk(ByteBufAllocator var1) throws Exception;

    public long length();

    public long progress();
}

