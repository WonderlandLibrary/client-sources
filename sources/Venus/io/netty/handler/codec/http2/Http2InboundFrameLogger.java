/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2FrameReader;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.util.internal.ObjectUtil;

public class Http2InboundFrameLogger
implements Http2FrameReader {
    private final Http2FrameReader reader;
    private final Http2FrameLogger logger;

    public Http2InboundFrameLogger(Http2FrameReader http2FrameReader, Http2FrameLogger http2FrameLogger) {
        this.reader = ObjectUtil.checkNotNull(http2FrameReader, "reader");
        this.logger = ObjectUtil.checkNotNull(http2FrameLogger, "logger");
    }

    @Override
    public void readFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        this.reader.readFrame(channelHandlerContext, byteBuf, new Http2FrameListener(this, http2FrameListener){
            final Http2FrameListener val$listener;
            final Http2InboundFrameLogger this$0;
            {
                this.this$0 = http2InboundFrameLogger;
                this.val$listener = http2FrameListener;
            }

            @Override
            public int onDataRead(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logData(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, n, byteBuf, n2, bl);
                return this.val$listener.onDataRead(channelHandlerContext, n, byteBuf, n2, bl);
            }

            @Override
            public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logHeaders(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, n, http2Headers, n2, bl);
                this.val$listener.onHeadersRead(channelHandlerContext, n, http2Headers, n2, bl);
            }

            @Override
            public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logHeaders(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2);
                this.val$listener.onHeadersRead(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2);
            }

            @Override
            public void onPriorityRead(ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logPriority(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, n, n2, s, bl);
                this.val$listener.onPriorityRead(channelHandlerContext, n, n2, s, bl);
            }

            @Override
            public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int n, long l) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logRstStream(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, n, l);
                this.val$listener.onRstStreamRead(channelHandlerContext, n, l);
            }

            @Override
            public void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logSettingsAck(Http2FrameLogger.Direction.INBOUND, channelHandlerContext);
                this.val$listener.onSettingsAckRead(channelHandlerContext);
            }

            @Override
            public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logSettings(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, http2Settings);
                this.val$listener.onSettingsRead(channelHandlerContext, http2Settings);
            }

            @Override
            public void onPingRead(ChannelHandlerContext channelHandlerContext, long l) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logPing(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, l);
                this.val$listener.onPingRead(channelHandlerContext, l);
            }

            @Override
            public void onPingAckRead(ChannelHandlerContext channelHandlerContext, long l) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logPingAck(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, l);
                this.val$listener.onPingAckRead(channelHandlerContext, l);
            }

            @Override
            public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logPushPromise(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, n, n2, http2Headers, n3);
                this.val$listener.onPushPromiseRead(channelHandlerContext, n, n2, http2Headers, n3);
            }

            @Override
            public void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logGoAway(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, n, l, byteBuf);
                this.val$listener.onGoAwayRead(channelHandlerContext, n, l, byteBuf);
            }

            @Override
            public void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int n, int n2) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logWindowsUpdate(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, n, n2);
                this.val$listener.onWindowUpdateRead(channelHandlerContext, n, n2);
            }

            @Override
            public void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception {
                Http2InboundFrameLogger.access$000(this.this$0).logUnknownFrame(Http2FrameLogger.Direction.INBOUND, channelHandlerContext, by, n, http2Flags, byteBuf);
                this.val$listener.onUnknownFrame(channelHandlerContext, by, n, http2Flags, byteBuf);
            }
        });
    }

    @Override
    public void close() {
        this.reader.close();
    }

    @Override
    public Http2FrameReader.Configuration configuration() {
        return this.reader.configuration();
    }

    static Http2FrameLogger access$000(Http2InboundFrameLogger http2InboundFrameLogger) {
        return http2InboundFrameLogger.logger;
    }
}

