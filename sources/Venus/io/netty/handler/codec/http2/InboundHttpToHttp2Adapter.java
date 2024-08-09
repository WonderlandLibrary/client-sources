/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.HttpConversionUtil;

public class InboundHttpToHttp2Adapter
extends ChannelInboundHandlerAdapter {
    private final Http2Connection connection;
    private final Http2FrameListener listener;

    public InboundHttpToHttp2Adapter(Http2Connection http2Connection, Http2FrameListener http2FrameListener) {
        this.connection = http2Connection;
        this.listener = http2FrameListener;
    }

    private static int getStreamId(Http2Connection http2Connection, HttpHeaders httpHeaders) {
        return httpHeaders.getInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text(), http2Connection.remote().incrementAndGetNextStreamId());
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (object instanceof FullHttpMessage) {
            InboundHttpToHttp2Adapter.handle(channelHandlerContext, this.connection, this.listener, (FullHttpMessage)object);
        } else {
            super.channelRead(channelHandlerContext, object);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void handle(ChannelHandlerContext channelHandlerContext, Http2Connection http2Connection, Http2FrameListener http2FrameListener, FullHttpMessage fullHttpMessage) throws Http2Exception {
        try {
            int n = InboundHttpToHttp2Adapter.getStreamId(http2Connection, fullHttpMessage.headers());
            Http2Stream http2Stream = http2Connection.stream(n);
            if (http2Stream == null) {
                http2Stream = http2Connection.remote().createStream(n, false);
            }
            fullHttpMessage.headers().set((CharSequence)HttpConversionUtil.ExtensionHeaderNames.SCHEME.text(), (Object)HttpScheme.HTTP.name());
            Http2Headers http2Headers = HttpConversionUtil.toHttp2Headers(fullHttpMessage, true);
            boolean bl = fullHttpMessage.content().isReadable();
            boolean bl2 = !fullHttpMessage.trailingHeaders().isEmpty();
            http2FrameListener.onHeadersRead(channelHandlerContext, n, http2Headers, 0, !bl && !bl2);
            if (bl) {
                http2FrameListener.onDataRead(channelHandlerContext, n, fullHttpMessage.content(), 0, !bl2);
            }
            if (bl2) {
                Http2Headers http2Headers2 = HttpConversionUtil.toHttp2Headers(fullHttpMessage.trailingHeaders(), true);
                http2FrameListener.onHeadersRead(channelHandlerContext, n, http2Headers2, 0, true);
            }
            http2Stream.closeRemoteSide();
        } finally {
            fullHttpMessage.release();
        }
    }
}

