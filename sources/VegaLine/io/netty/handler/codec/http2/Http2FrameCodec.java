/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.HttpServerUpgradeHandler;
import io.netty.handler.codec.http2.DefaultHttp2Connection;
import io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder;
import io.netty.handler.codec.http2.DefaultHttp2ConnectionEncoder;
import io.netty.handler.codec.http2.DefaultHttp2DataFrame;
import io.netty.handler.codec.http2.DefaultHttp2FrameReader;
import io.netty.handler.codec.http2.DefaultHttp2FrameWriter;
import io.netty.handler.codec.http2.DefaultHttp2GoAwayFrame;
import io.netty.handler.codec.http2.DefaultHttp2HeadersDecoder;
import io.netty.handler.codec.http2.DefaultHttp2HeadersFrame;
import io.netty.handler.codec.http2.DefaultHttp2ResetFrame;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionAdapter;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2DataFrame;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FrameAdapter;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2GoAwayFrame;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersFrame;
import io.netty.handler.codec.http2.Http2InboundFrameLogger;
import io.netty.handler.codec.http2.Http2LocalFlowController;
import io.netty.handler.codec.http2.Http2OutboundFrameLogger;
import io.netty.handler.codec.http2.Http2ResetFrame;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.Http2StreamActiveEvent;
import io.netty.handler.codec.http2.Http2StreamClosedEvent;
import io.netty.handler.codec.http2.Http2StreamFrame;
import io.netty.handler.codec.http2.Http2WindowUpdateFrame;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.handler.codec.http2.InboundHttpToHttp2Adapter;
import io.netty.handler.logging.LogLevel;
import io.netty.util.ReferenceCountUtil;

public class Http2FrameCodec
extends ChannelDuplexHandler {
    private static final Http2FrameLogger HTTP2_FRAME_LOGGER = new Http2FrameLogger(LogLevel.INFO, Http2FrameCodec.class);
    private final Http2ConnectionHandler http2Handler;
    private final boolean server;
    private ChannelHandlerContext ctx;
    private ChannelHandlerContext http2HandlerCtx;

    public Http2FrameCodec(boolean server) {
        this(server, HTTP2_FRAME_LOGGER);
    }

    public Http2FrameCodec(boolean server, Http2FrameLogger frameLogger) {
        this(server, new DefaultHttp2FrameWriter(), frameLogger, new Http2Settings());
    }

    Http2FrameCodec(boolean server, Http2FrameWriter frameWriter, Http2FrameLogger frameLogger, Http2Settings initialSettings) {
        DefaultHttp2Connection connection = new DefaultHttp2Connection(server);
        frameWriter = new Http2OutboundFrameLogger(frameWriter, frameLogger);
        DefaultHttp2ConnectionEncoder encoder = new DefaultHttp2ConnectionEncoder(connection, frameWriter);
        Long maxHeaderListSize = initialSettings.maxHeaderListSize();
        DefaultHttp2FrameReader frameReader = new DefaultHttp2FrameReader(maxHeaderListSize == null ? new DefaultHttp2HeadersDecoder(true) : new DefaultHttp2HeadersDecoder(true, maxHeaderListSize));
        Http2InboundFrameLogger reader = new Http2InboundFrameLogger(frameReader, frameLogger);
        DefaultHttp2ConnectionDecoder decoder = new DefaultHttp2ConnectionDecoder(connection, encoder, reader);
        decoder.frameListener(new FrameListener());
        this.http2Handler = new InternalHttp2ConnectionHandler(decoder, encoder, initialSettings);
        this.http2Handler.connection().addListener(new ConnectionListener());
        this.server = server;
    }

    Http2ConnectionHandler connectionHandler() {
        return this.http2Handler;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        ctx.pipeline().addBefore(ctx.executor(), ctx.name(), null, this.http2Handler);
        this.http2HandlerCtx = ctx.pipeline().context(this.http2Handler);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().remove(this.http2Handler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!(evt instanceof HttpServerUpgradeHandler.UpgradeEvent)) {
            super.userEventTriggered(ctx, evt);
            return;
        }
        HttpServerUpgradeHandler.UpgradeEvent upgrade = (HttpServerUpgradeHandler.UpgradeEvent)evt;
        ctx.fireUserEventTriggered(upgrade.retain());
        try {
            Http2Stream stream = this.http2Handler.connection().stream(1);
            new ConnectionListener().onStreamActive(stream);
            upgrade.upgradeRequest().headers().setInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text(), 1);
            new InboundHttpToHttp2Adapter(this.http2Handler.connection(), this.http2Handler.decoder().frameListener()).channelRead(ctx, upgrade.upgradeRequest().retain());
        } finally {
            upgrade.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.fireExceptionCaught(cause);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        block6: {
            try {
                if (msg instanceof Http2WindowUpdateFrame) {
                    Http2WindowUpdateFrame frame = (Http2WindowUpdateFrame)msg;
                    this.consumeBytes(frame.streamId(), frame.windowSizeIncrement(), promise);
                    break block6;
                }
                if (msg instanceof Http2StreamFrame) {
                    this.writeStreamFrame((Http2StreamFrame)msg, promise);
                    break block6;
                }
                if (msg instanceof Http2GoAwayFrame) {
                    this.writeGoAwayFrame((Http2GoAwayFrame)msg, promise);
                    break block6;
                }
                throw new UnsupportedMessageTypeException(msg, new Class[0]);
            } finally {
                ReferenceCountUtil.release(msg);
            }
        }
    }

    private void consumeBytes(int streamId, int bytes, ChannelPromise promise) {
        try {
            Http2Stream stream = this.http2Handler.connection().stream(streamId);
            this.http2Handler.connection().local().flowController().consumeBytes(stream, bytes);
            promise.setSuccess();
        } catch (Throwable t) {
            promise.setFailure(t);
        }
    }

    private void writeGoAwayFrame(Http2GoAwayFrame frame, ChannelPromise promise) {
        if (frame.lastStreamId() > -1) {
            throw new IllegalArgumentException("Last stream id must not be set on GOAWAY frame");
        }
        int lastStreamCreated = this.http2Handler.connection().remote().lastStreamCreated();
        int lastStreamId = lastStreamCreated + frame.extraStreamIds() * 2;
        if (lastStreamId < lastStreamCreated) {
            lastStreamId = Integer.MAX_VALUE;
        }
        this.http2Handler.goAway(this.http2HandlerCtx, lastStreamId, frame.errorCode(), frame.content().retain(), promise);
    }

    private void writeStreamFrame(Http2StreamFrame frame, ChannelPromise promise) {
        if (frame instanceof Http2DataFrame) {
            Http2DataFrame dataFrame = (Http2DataFrame)frame;
            this.http2Handler.encoder().writeData(this.http2HandlerCtx, frame.streamId(), dataFrame.content().retain(), dataFrame.padding(), dataFrame.isEndStream(), promise);
        } else if (frame instanceof Http2HeadersFrame) {
            this.writeHeadersFrame((Http2HeadersFrame)frame, promise);
        } else if (frame instanceof Http2ResetFrame) {
            Http2ResetFrame rstFrame = (Http2ResetFrame)frame;
            this.http2Handler.resetStream(this.http2HandlerCtx, frame.streamId(), rstFrame.errorCode(), promise);
        } else {
            throw new UnsupportedMessageTypeException(frame, new Class[0]);
        }
    }

    private void writeHeadersFrame(Http2HeadersFrame headersFrame, ChannelPromise promise) {
        int streamId = headersFrame.streamId();
        if (!Http2CodecUtil.isStreamIdValid(streamId)) {
            Http2Connection.Endpoint<Http2LocalFlowController> localEndpoint = this.http2Handler.connection().local();
            streamId = localEndpoint.incrementAndGetNextStreamId();
            try {
                localEndpoint.createStream(streamId, false);
            } catch (Http2Exception e) {
                promise.setFailure(e);
                return;
            }
            this.ctx.fireUserEventTriggered(new Http2StreamActiveEvent(streamId, headersFrame));
        }
        this.http2Handler.encoder().writeHeaders(this.http2HandlerCtx, streamId, headersFrame.headers(), headersFrame.padding(), headersFrame.isEndStream(), promise);
    }

    private static final class FrameListener
    extends Http2FrameAdapter {
        private FrameListener() {
        }

        @Override
        public void onRstStreamRead(ChannelHandlerContext ctx, int streamId, long errorCode) {
            DefaultHttp2ResetFrame rstFrame = new DefaultHttp2ResetFrame(errorCode);
            rstFrame.streamId(streamId);
            ctx.fireChannelRead(rstFrame);
        }

        @Override
        public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int streamDependency, short weight, boolean exclusive, int padding, boolean endStream) {
            this.onHeadersRead(ctx, streamId, headers, padding, endStream);
        }

        @Override
        public void onHeadersRead(ChannelHandlerContext ctx, int streamId, Http2Headers headers, int padding, boolean endOfStream) {
            DefaultHttp2HeadersFrame headersFrame = new DefaultHttp2HeadersFrame(headers, endOfStream, padding);
            headersFrame.streamId(streamId);
            ctx.fireChannelRead(headersFrame);
        }

        @Override
        public int onDataRead(ChannelHandlerContext ctx, int streamId, ByteBuf data, int padding, boolean endOfStream) {
            DefaultHttp2DataFrame dataFrame = new DefaultHttp2DataFrame(data.retain(), endOfStream, padding);
            dataFrame.streamId(streamId);
            ctx.fireChannelRead(dataFrame);
            return 0;
        }
    }

    private static final class InternalHttp2ConnectionHandler
    extends Http2ConnectionHandler {
        InternalHttp2ConnectionHandler(Http2ConnectionDecoder decoder, Http2ConnectionEncoder encoder, Http2Settings initialSettings) {
            super(decoder, encoder, initialSettings);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected void onStreamError(ChannelHandlerContext ctx, Throwable cause, Http2Exception.StreamException http2Ex) {
            try {
                Http2Stream stream = this.connection().stream(http2Ex.streamId());
                if (stream == null) {
                    return;
                }
                ctx.fireExceptionCaught(http2Ex);
            } finally {
                super.onStreamError(ctx, cause, http2Ex);
            }
        }
    }

    private final class ConnectionListener
    extends Http2ConnectionAdapter {
        private ConnectionListener() {
        }

        @Override
        public void onStreamActive(Http2Stream stream) {
            if (Http2FrameCodec.this.ctx == null) {
                return;
            }
            if (Http2CodecUtil.isOutboundStream(Http2FrameCodec.this.server, stream.id())) {
                return;
            }
            Http2FrameCodec.this.ctx.fireUserEventTriggered(new Http2StreamActiveEvent(stream.id()));
        }

        @Override
        public void onStreamClosed(Http2Stream stream) {
            Http2FrameCodec.this.ctx.fireUserEventTriggered(new Http2StreamClosedEvent(stream.id()));
        }

        @Override
        public void onGoAwayReceived(int lastStreamId, long errorCode, ByteBuf debugData) {
            Http2FrameCodec.this.ctx.fireChannelRead(new DefaultHttp2GoAwayFrame(lastStreamId, errorCode, debugData.retain()));
        }
    }
}

