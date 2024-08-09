/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2Stream;

public class Http2ConnectionAdapter
implements Http2Connection.Listener {
    @Override
    public void onStreamAdded(Http2Stream http2Stream) {
    }

    @Override
    public void onStreamActive(Http2Stream http2Stream) {
    }

    @Override
    public void onStreamHalfClosed(Http2Stream http2Stream) {
    }

    @Override
    public void onStreamClosed(Http2Stream http2Stream) {
    }

    @Override
    public void onStreamRemoved(Http2Stream http2Stream) {
    }

    @Override
    public void onGoAwaySent(int n, long l, ByteBuf byteBuf) {
    }

    @Override
    public void onGoAwayReceived(int n, long l, ByteBuf byteBuf) {
    }
}

