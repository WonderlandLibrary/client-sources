/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http2.DefaultHttp2Connection;
import io.netty.handler.codec.http2.DefaultHttp2ConnectionDecoder;
import io.netty.handler.codec.http2.DefaultHttp2ConnectionEncoder;
import io.netty.handler.codec.http2.DefaultHttp2FrameReader;
import io.netty.handler.codec.http2.DefaultHttp2HeadersDecoder;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2ConnectionPrefaceAndSettingsFrameWrittenEvent;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FrameLogger;
import io.netty.handler.codec.http2.Http2FrameReader;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2InboundFrameLogger;
import io.netty.handler.codec.http2.Http2LifecycleManager;
import io.netty.handler.codec.http2.Http2OutboundFrameLogger;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.ReadOnlyHttp2Headers;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Http2ConnectionHandler
extends ByteToMessageDecoder
implements Http2LifecycleManager,
ChannelOutboundHandler {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(Http2ConnectionHandler.class);
    private static final Http2Headers HEADERS_TOO_LARGE_HEADERS = ReadOnlyHttp2Headers.serverHeaders(false, HttpResponseStatus.REQUEST_HEADER_FIELDS_TOO_LARGE.codeAsText(), new AsciiString[0]);
    private static final ByteBuf HTTP_1_X_BUF = Unpooled.unreleasableBuffer(Unpooled.wrappedBuffer(new byte[]{72, 84, 84, 80, 47, 49, 46})).asReadOnly();
    private final Http2ConnectionDecoder decoder;
    private final Http2ConnectionEncoder encoder;
    private final Http2Settings initialSettings;
    private ChannelFutureListener closeListener;
    private BaseDecoder byteDecoder;
    private long gracefulShutdownTimeoutMillis;

    protected Http2ConnectionHandler(Http2ConnectionDecoder http2ConnectionDecoder, Http2ConnectionEncoder http2ConnectionEncoder, Http2Settings http2Settings) {
        this.initialSettings = ObjectUtil.checkNotNull(http2Settings, "initialSettings");
        this.decoder = ObjectUtil.checkNotNull(http2ConnectionDecoder, "decoder");
        this.encoder = ObjectUtil.checkNotNull(http2ConnectionEncoder, "encoder");
        if (http2ConnectionEncoder.connection() != http2ConnectionDecoder.connection()) {
            throw new IllegalArgumentException("Encoder and Decoder do not share the same connection object");
        }
    }

    Http2ConnectionHandler(boolean bl, Http2FrameWriter http2FrameWriter, Http2FrameLogger http2FrameLogger, Http2Settings http2Settings) {
        this.initialSettings = ObjectUtil.checkNotNull(http2Settings, "initialSettings");
        DefaultHttp2Connection defaultHttp2Connection = new DefaultHttp2Connection(bl);
        Long l = http2Settings.maxHeaderListSize();
        Http2FrameReader http2FrameReader = new DefaultHttp2FrameReader(l == null ? new DefaultHttp2HeadersDecoder(true) : new DefaultHttp2HeadersDecoder(true, l));
        if (http2FrameLogger != null) {
            http2FrameWriter = new Http2OutboundFrameLogger(http2FrameWriter, http2FrameLogger);
            http2FrameReader = new Http2InboundFrameLogger(http2FrameReader, http2FrameLogger);
        }
        this.encoder = new DefaultHttp2ConnectionEncoder(defaultHttp2Connection, http2FrameWriter);
        this.decoder = new DefaultHttp2ConnectionDecoder(defaultHttp2Connection, this.encoder, http2FrameReader);
    }

    public long gracefulShutdownTimeoutMillis() {
        return this.gracefulShutdownTimeoutMillis;
    }

    public void gracefulShutdownTimeoutMillis(long l) {
        if (l < -1L) {
            throw new IllegalArgumentException("gracefulShutdownTimeoutMillis: " + l + " (expected: -1 for indefinite or >= 0)");
        }
        this.gracefulShutdownTimeoutMillis = l;
    }

    public Http2Connection connection() {
        return this.encoder.connection();
    }

    public Http2ConnectionDecoder decoder() {
        return this.decoder;
    }

    public Http2ConnectionEncoder encoder() {
        return this.encoder;
    }

    private boolean prefaceSent() {
        return this.byteDecoder != null && this.byteDecoder.prefaceSent();
    }

    public void onHttpClientUpgrade() throws Http2Exception {
        if (this.connection().isServer()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Client-side HTTP upgrade requested for a server", new Object[0]);
        }
        if (!this.prefaceSent()) {
            throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, "HTTP upgrade must occur after preface was sent", new Object[0]);
        }
        if (this.decoder.prefaceReceived()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "HTTP upgrade must occur before HTTP/2 preface is received", new Object[0]);
        }
        this.connection().local().createStream(1, true);
    }

    public void onHttpServerUpgrade(Http2Settings http2Settings) throws Http2Exception {
        if (!this.connection().isServer()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server-side HTTP upgrade requested for a client", new Object[0]);
        }
        if (!this.prefaceSent()) {
            throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, "HTTP upgrade must occur after preface was sent", new Object[0]);
        }
        if (this.decoder.prefaceReceived()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "HTTP upgrade must occur before HTTP/2 preface is received", new Object[0]);
        }
        this.encoder.remoteSettings(http2Settings);
        this.connection().remote().createStream(1, true);
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) {
        try {
            this.encoder.flowController().writePendingBytes();
            channelHandlerContext.flush();
        } catch (Http2Exception http2Exception) {
            this.onError(channelHandlerContext, true, http2Exception);
        } catch (Throwable throwable) {
            this.onError(channelHandlerContext, true, Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, throwable, "Error flushing", new Object[0]));
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.encoder.lifecycleManager(this);
        this.decoder.lifecycleManager(this);
        this.encoder.flowController().channelHandlerContext(channelHandlerContext);
        this.decoder.flowController().channelHandlerContext(channelHandlerContext);
        this.byteDecoder = new PrefaceDecoder(this, channelHandlerContext);
    }

    @Override
    protected void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.byteDecoder != null) {
            this.byteDecoder.handlerRemoved(channelHandlerContext);
            this.byteDecoder = null;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.byteDecoder == null) {
            this.byteDecoder = new PrefaceDecoder(this, channelHandlerContext);
        }
        this.byteDecoder.channelActive(channelHandlerContext);
        super.channelActive(channelHandlerContext);
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelInactive(channelHandlerContext);
        if (this.byteDecoder != null) {
            this.byteDecoder.channelInactive(channelHandlerContext);
            this.byteDecoder = null;
        }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            if (channelHandlerContext.channel().isWritable()) {
                this.flush(channelHandlerContext);
            }
            this.encoder.flowController().channelWritabilityChanged();
        } finally {
            super.channelWritabilityChanged(channelHandlerContext);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        this.byteDecoder.decode(channelHandlerContext, byteBuf, list);
    }

    @Override
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.disconnect(channelPromise);
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelPromise = channelPromise.unvoid();
        if (!channelHandlerContext.channel().isActive()) {
            channelHandlerContext.close(channelPromise);
            return;
        }
        ChannelFuture channelFuture = this.connection().goAwaySent() ? channelHandlerContext.write(Unpooled.EMPTY_BUFFER) : this.goAway(channelHandlerContext, null);
        channelHandlerContext.flush();
        this.doGracefulShutdown(channelHandlerContext, channelFuture, channelPromise);
    }

    private void doGracefulShutdown(ChannelHandlerContext channelHandlerContext, ChannelFuture channelFuture, ChannelPromise channelPromise) {
        if (this.isGracefulShutdownComplete()) {
            channelFuture.addListener(new ClosingChannelFutureListener(channelHandlerContext, channelPromise));
        } else {
            this.closeListener = this.gracefulShutdownTimeoutMillis < 0L ? new ClosingChannelFutureListener(channelHandlerContext, channelPromise) : new ClosingChannelFutureListener(channelHandlerContext, channelPromise, this.gracefulShutdownTimeoutMillis, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.read();
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.write(object, channelPromise);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            this.channelReadComplete0(channelHandlerContext);
        } finally {
            this.flush(channelHandlerContext);
        }
    }

    void channelReadComplete0(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelReadComplete(channelHandlerContext);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (Http2CodecUtil.getEmbeddedHttp2Exception(throwable) != null) {
            this.onError(channelHandlerContext, false, throwable);
        } else {
            super.exceptionCaught(channelHandlerContext, throwable);
        }
    }

    @Override
    public void closeStreamLocal(Http2Stream http2Stream, ChannelFuture channelFuture) {
        switch (http2Stream.state()) {
            case HALF_CLOSED_LOCAL: 
            case OPEN: {
                http2Stream.closeLocalSide();
                break;
            }
            default: {
                this.closeStream(http2Stream, channelFuture);
            }
        }
    }

    @Override
    public void closeStreamRemote(Http2Stream http2Stream, ChannelFuture channelFuture) {
        switch (http2Stream.state()) {
            case OPEN: 
            case HALF_CLOSED_REMOTE: {
                http2Stream.closeRemoteSide();
                break;
            }
            default: {
                this.closeStream(http2Stream, channelFuture);
            }
        }
    }

    @Override
    public void closeStream(Http2Stream http2Stream, ChannelFuture channelFuture) {
        http2Stream.close();
        if (channelFuture.isDone()) {
            this.checkCloseConnection(channelFuture);
        } else {
            channelFuture.addListener(new ChannelFutureListener(this){
                final Http2ConnectionHandler this$0;
                {
                    this.this$0 = http2ConnectionHandler;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    Http2ConnectionHandler.access$800(this.this$0, channelFuture);
                }

                @Override
                public void operationComplete(Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
    }

    @Override
    public void onError(ChannelHandlerContext channelHandlerContext, boolean bl, Throwable throwable) {
        Http2Exception http2Exception = Http2CodecUtil.getEmbeddedHttp2Exception(throwable);
        if (Http2Exception.isStreamError(http2Exception)) {
            this.onStreamError(channelHandlerContext, bl, throwable, (Http2Exception.StreamException)http2Exception);
        } else if (http2Exception instanceof Http2Exception.CompositeStreamException) {
            Http2Exception.CompositeStreamException compositeStreamException = (Http2Exception.CompositeStreamException)http2Exception;
            for (Http2Exception.StreamException streamException : compositeStreamException) {
                this.onStreamError(channelHandlerContext, bl, throwable, streamException);
            }
        } else {
            this.onConnectionError(channelHandlerContext, bl, throwable, http2Exception);
        }
        channelHandlerContext.flush();
    }

    protected boolean isGracefulShutdownComplete() {
        return this.connection().numActiveStreams() == 0;
    }

    protected void onConnectionError(ChannelHandlerContext channelHandlerContext, boolean bl, Throwable throwable, Http2Exception http2Exception) {
        if (http2Exception == null) {
            http2Exception = new Http2Exception(Http2Error.INTERNAL_ERROR, throwable.getMessage(), throwable);
        }
        ChannelPromise channelPromise = channelHandlerContext.newPromise();
        ChannelFuture channelFuture = this.goAway(channelHandlerContext, http2Exception);
        switch (http2Exception.shutdownHint()) {
            case GRACEFUL_SHUTDOWN: {
                this.doGracefulShutdown(channelHandlerContext, channelFuture, channelPromise);
                break;
            }
            default: {
                channelFuture.addListener(new ClosingChannelFutureListener(channelHandlerContext, channelPromise));
            }
        }
    }

    protected void onStreamError(ChannelHandlerContext channelHandlerContext, boolean bl, Throwable throwable, Http2Exception.StreamException streamException) {
        int n = streamException.streamId();
        Http2Stream http2Stream = this.connection().stream(n);
        if (streamException instanceof Http2Exception.HeaderListSizeException && ((Http2Exception.HeaderListSizeException)streamException).duringDecode() && this.connection().isServer()) {
            if (http2Stream == null) {
                try {
                    http2Stream = this.encoder.connection().remote().createStream(n, true);
                } catch (Http2Exception http2Exception) {
                    this.resetUnknownStream(channelHandlerContext, n, streamException.error().code(), channelHandlerContext.newPromise());
                    return;
                }
            }
            if (http2Stream != null && !http2Stream.isHeadersSent()) {
                try {
                    this.handleServerHeaderDecodeSizeError(channelHandlerContext, http2Stream);
                } catch (Throwable throwable2) {
                    this.onError(channelHandlerContext, bl, Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, throwable2, "Error DecodeSizeError", new Object[0]));
                }
            }
        }
        if (http2Stream == null) {
            this.resetUnknownStream(channelHandlerContext, n, streamException.error().code(), channelHandlerContext.newPromise());
        } else {
            this.resetStream(channelHandlerContext, http2Stream, streamException.error().code(), channelHandlerContext.newPromise());
        }
    }

    protected void handleServerHeaderDecodeSizeError(ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream) {
        this.encoder().writeHeaders(channelHandlerContext, http2Stream.id(), HEADERS_TOO_LARGE_HEADERS, 0, true, channelHandlerContext.newPromise());
    }

    protected Http2FrameWriter frameWriter() {
        return this.encoder().frameWriter();
    }

    private ChannelFuture resetUnknownStream(ChannelHandlerContext channelHandlerContext, int n, long l, ChannelPromise channelPromise) {
        ChannelFuture channelFuture = this.frameWriter().writeRstStream(channelHandlerContext, n, l, channelPromise);
        if (channelFuture.isDone()) {
            this.closeConnectionOnError(channelHandlerContext, channelFuture);
        } else {
            channelFuture.addListener(new ChannelFutureListener(this, channelHandlerContext){
                final ChannelHandlerContext val$ctx;
                final Http2ConnectionHandler this$0;
                {
                    this.this$0 = http2ConnectionHandler;
                    this.val$ctx = channelHandlerContext;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    Http2ConnectionHandler.access$900(this.this$0, this.val$ctx, channelFuture);
                }

                @Override
                public void operationComplete(Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
        return channelFuture;
    }

    @Override
    public ChannelFuture resetStream(ChannelHandlerContext channelHandlerContext, int n, long l, ChannelPromise channelPromise) {
        Http2Stream http2Stream = this.connection().stream(n);
        if (http2Stream == null) {
            return this.resetUnknownStream(channelHandlerContext, n, l, channelPromise.unvoid());
        }
        return this.resetStream(channelHandlerContext, http2Stream, l, channelPromise);
    }

    private ChannelFuture resetStream(ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream, long l, ChannelPromise channelPromise) {
        channelPromise = channelPromise.unvoid();
        if (http2Stream.isResetSent()) {
            return channelPromise.setSuccess();
        }
        ChannelFuture channelFuture = http2Stream.state() == Http2Stream.State.IDLE || this.connection().local().created(http2Stream) && !http2Stream.isHeadersSent() && !http2Stream.isPushPromiseSent() ? channelPromise.setSuccess() : this.frameWriter().writeRstStream(channelHandlerContext, http2Stream.id(), l, channelPromise);
        http2Stream.resetSent();
        if (channelFuture.isDone()) {
            this.processRstStreamWriteResult(channelHandlerContext, http2Stream, channelFuture);
        } else {
            channelFuture.addListener(new ChannelFutureListener(this, channelHandlerContext, http2Stream){
                final ChannelHandlerContext val$ctx;
                final Http2Stream val$stream;
                final Http2ConnectionHandler this$0;
                {
                    this.this$0 = http2ConnectionHandler;
                    this.val$ctx = channelHandlerContext;
                    this.val$stream = http2Stream;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    Http2ConnectionHandler.access$1000(this.this$0, this.val$ctx, this.val$stream, channelFuture);
                }

                @Override
                public void operationComplete(Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
        return channelFuture;
    }

    @Override
    public ChannelFuture goAway(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf, ChannelPromise channelPromise) {
        try {
            channelPromise = channelPromise.unvoid();
            Http2Connection http2Connection = this.connection();
            if (this.connection().goAwaySent()) {
                if (n == this.connection().remote().lastStreamKnownByPeer()) {
                    byteBuf.release();
                    return channelPromise.setSuccess();
                }
                if (n > http2Connection.remote().lastStreamKnownByPeer()) {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Last stream identifier must not increase between sending multiple GOAWAY frames (was '%d', is '%d').", http2Connection.remote().lastStreamKnownByPeer(), n);
                }
            }
            http2Connection.goAwaySent(n, l, byteBuf);
            byteBuf.retain();
            ChannelFuture channelFuture = this.frameWriter().writeGoAway(channelHandlerContext, n, l, byteBuf, channelPromise);
            if (channelFuture.isDone()) {
                Http2ConnectionHandler.processGoAwayWriteResult(channelHandlerContext, n, l, byteBuf, channelFuture);
            } else {
                channelFuture.addListener(new ChannelFutureListener(this, channelHandlerContext, n, l, byteBuf){
                    final ChannelHandlerContext val$ctx;
                    final int val$lastStreamId;
                    final long val$errorCode;
                    final ByteBuf val$debugData;
                    final Http2ConnectionHandler this$0;
                    {
                        this.this$0 = http2ConnectionHandler;
                        this.val$ctx = channelHandlerContext;
                        this.val$lastStreamId = n;
                        this.val$errorCode = l;
                        this.val$debugData = byteBuf;
                    }

                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        Http2ConnectionHandler.access$1100(this.val$ctx, this.val$lastStreamId, this.val$errorCode, this.val$debugData, channelFuture);
                    }

                    @Override
                    public void operationComplete(Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
            }
            return channelFuture;
        } catch (Throwable throwable) {
            byteBuf.release();
            return channelPromise.setFailure(throwable);
        }
    }

    private void checkCloseConnection(ChannelFuture channelFuture) {
        if (this.closeListener != null && this.isGracefulShutdownComplete()) {
            ChannelFutureListener channelFutureListener = this.closeListener;
            this.closeListener = null;
            try {
                channelFutureListener.operationComplete(channelFuture);
            } catch (Exception exception) {
                throw new IllegalStateException("Close listener threw an unexpected exception", exception);
            }
        }
    }

    private ChannelFuture goAway(ChannelHandlerContext channelHandlerContext, Http2Exception http2Exception) {
        long l = http2Exception != null ? http2Exception.error().code() : Http2Error.NO_ERROR.code();
        int n = this.connection().remote().lastStreamCreated();
        return this.goAway(channelHandlerContext, n, l, Http2CodecUtil.toByteBuf(channelHandlerContext, http2Exception), channelHandlerContext.newPromise());
    }

    private void processRstStreamWriteResult(ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream, ChannelFuture channelFuture) {
        if (channelFuture.isSuccess()) {
            this.closeStream(http2Stream, channelFuture);
        } else {
            this.onConnectionError(channelHandlerContext, true, channelFuture.cause(), null);
        }
    }

    private void closeConnectionOnError(ChannelHandlerContext channelHandlerContext, ChannelFuture channelFuture) {
        if (!channelFuture.isSuccess()) {
            this.onConnectionError(channelHandlerContext, true, channelFuture.cause(), null);
        }
    }

    private static ByteBuf clientPrefaceString(Http2Connection http2Connection) {
        return http2Connection.isServer() ? Http2CodecUtil.connectionPrefaceBuf() : null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void processGoAwayWriteResult(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf, ChannelFuture channelFuture) {
        try {
            if (channelFuture.isSuccess()) {
                if (l != Http2Error.NO_ERROR.code()) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("{} Sent GOAWAY: lastStreamId '{}', errorCode '{}', debugData '{}'. Forcing shutdown of the connection.", channelHandlerContext.channel(), n, l, byteBuf.toString(CharsetUtil.UTF_8), channelFuture.cause());
                    }
                    channelHandlerContext.close();
                }
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("{} Sending GOAWAY failed: lastStreamId '{}', errorCode '{}', debugData '{}'. Forcing shutdown of the connection.", channelHandlerContext.channel(), n, l, byteBuf.toString(CharsetUtil.UTF_8), channelFuture.cause());
                }
                channelHandlerContext.close();
            }
        } finally {
            byteBuf.release();
        }
    }

    static Http2ConnectionEncoder access$100(Http2ConnectionHandler http2ConnectionHandler) {
        return http2ConnectionHandler.encoder;
    }

    static ByteBuf access$200(Http2Connection http2Connection) {
        return Http2ConnectionHandler.clientPrefaceString(http2Connection);
    }

    static BaseDecoder access$302(Http2ConnectionHandler http2ConnectionHandler, BaseDecoder baseDecoder) {
        http2ConnectionHandler.byteDecoder = baseDecoder;
        return http2ConnectionHandler.byteDecoder;
    }

    static BaseDecoder access$300(Http2ConnectionHandler http2ConnectionHandler) {
        return http2ConnectionHandler.byteDecoder;
    }

    static ByteBuf access$500() {
        return HTTP_1_X_BUF;
    }

    static Http2Settings access$600(Http2ConnectionHandler http2ConnectionHandler) {
        return http2ConnectionHandler.initialSettings;
    }

    static Http2ConnectionDecoder access$700(Http2ConnectionHandler http2ConnectionHandler) {
        return http2ConnectionHandler.decoder;
    }

    static void access$800(Http2ConnectionHandler http2ConnectionHandler, ChannelFuture channelFuture) {
        http2ConnectionHandler.checkCloseConnection(channelFuture);
    }

    static void access$900(Http2ConnectionHandler http2ConnectionHandler, ChannelHandlerContext channelHandlerContext, ChannelFuture channelFuture) {
        http2ConnectionHandler.closeConnectionOnError(channelHandlerContext, channelFuture);
    }

    static void access$1000(Http2ConnectionHandler http2ConnectionHandler, ChannelHandlerContext channelHandlerContext, Http2Stream http2Stream, ChannelFuture channelFuture) {
        http2ConnectionHandler.processRstStreamWriteResult(channelHandlerContext, http2Stream, channelFuture);
    }

    static void access$1100(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf, ChannelFuture channelFuture) {
        Http2ConnectionHandler.processGoAwayWriteResult(channelHandlerContext, n, l, byteBuf, channelFuture);
    }

    private static final class ClosingChannelFutureListener
    implements ChannelFutureListener {
        private final ChannelHandlerContext ctx;
        private final ChannelPromise promise;
        private final ScheduledFuture<?> timeoutTask;

        ClosingChannelFutureListener(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
            this.ctx = channelHandlerContext;
            this.promise = channelPromise;
            this.timeoutTask = null;
        }

        ClosingChannelFutureListener(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise, long l, TimeUnit timeUnit) {
            this.ctx = channelHandlerContext;
            this.promise = channelPromise;
            this.timeoutTask = channelHandlerContext.executor().schedule(new Runnable(this, channelHandlerContext, channelPromise){
                final ChannelHandlerContext val$ctx;
                final ChannelPromise val$promise;
                final ClosingChannelFutureListener this$0;
                {
                    this.this$0 = closingChannelFutureListener;
                    this.val$ctx = channelHandlerContext;
                    this.val$promise = channelPromise;
                }

                @Override
                public void run() {
                    this.val$ctx.close(this.val$promise);
                }
            }, l, timeUnit);
        }

        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (this.timeoutTask != null) {
                this.timeoutTask.cancel(false);
            }
            this.ctx.close(this.promise);
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    }

    private final class FrameDecoder
    extends BaseDecoder {
        final Http2ConnectionHandler this$0;

        private FrameDecoder(Http2ConnectionHandler http2ConnectionHandler) {
            this.this$0 = http2ConnectionHandler;
            super(http2ConnectionHandler, null);
        }

        @Override
        public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            try {
                Http2ConnectionHandler.access$700(this.this$0).decodeFrame(channelHandlerContext, byteBuf, list);
            } catch (Throwable throwable) {
                this.this$0.onError(channelHandlerContext, false, throwable);
            }
        }

        FrameDecoder(Http2ConnectionHandler http2ConnectionHandler, 1 var2_2) {
            this(http2ConnectionHandler);
        }
    }

    private final class PrefaceDecoder
    extends BaseDecoder {
        private ByteBuf clientPrefaceString;
        private boolean prefaceSent;
        final Http2ConnectionHandler this$0;

        public PrefaceDecoder(Http2ConnectionHandler http2ConnectionHandler, ChannelHandlerContext channelHandlerContext) throws Exception {
            this.this$0 = http2ConnectionHandler;
            super(http2ConnectionHandler, null);
            this.clientPrefaceString = Http2ConnectionHandler.access$200(Http2ConnectionHandler.access$100(http2ConnectionHandler).connection());
            this.sendPreface(channelHandlerContext);
        }

        @Override
        public boolean prefaceSent() {
            return this.prefaceSent;
        }

        @Override
        public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            try {
                if (channelHandlerContext.channel().isActive() && this.readClientPrefaceString(byteBuf) && this.verifyFirstFrameIsSettings(byteBuf)) {
                    Http2ConnectionHandler.access$302(this.this$0, new FrameDecoder(this.this$0, null));
                    Http2ConnectionHandler.access$300(this.this$0).decode(channelHandlerContext, byteBuf, list);
                }
            } catch (Throwable throwable) {
                this.this$0.onError(channelHandlerContext, false, throwable);
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.sendPreface(channelHandlerContext);
        }

        @Override
        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.cleanup();
            super.channelInactive(channelHandlerContext);
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.cleanup();
        }

        private void cleanup() {
            if (this.clientPrefaceString != null) {
                this.clientPrefaceString.release();
                this.clientPrefaceString = null;
            }
        }

        private boolean readClientPrefaceString(ByteBuf byteBuf) throws Http2Exception {
            if (this.clientPrefaceString == null) {
                return false;
            }
            int n = this.clientPrefaceString.readableBytes();
            int n2 = Math.min(byteBuf.readableBytes(), n);
            if (n2 == 0 || !ByteBufUtil.equals(byteBuf, byteBuf.readerIndex(), this.clientPrefaceString, this.clientPrefaceString.readerIndex(), n2)) {
                int n3 = 1024;
                int n4 = ByteBufUtil.indexOf(Http2ConnectionHandler.access$500(), byteBuf.slice(byteBuf.readerIndex(), Math.min(byteBuf.readableBytes(), n3)));
                if (n4 != -1) {
                    String string = byteBuf.toString(byteBuf.readerIndex(), n4 - byteBuf.readerIndex(), CharsetUtil.US_ASCII);
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Unexpected HTTP/1.x request: %s", string);
                }
                String string = ByteBufUtil.hexDump(byteBuf, byteBuf.readerIndex(), Math.min(byteBuf.readableBytes(), this.clientPrefaceString.readableBytes()));
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "HTTP/2 client preface string missing or corrupt. Hex dump for received bytes: %s", string);
            }
            byteBuf.skipBytes(n2);
            this.clientPrefaceString.skipBytes(n2);
            if (!this.clientPrefaceString.isReadable()) {
                this.clientPrefaceString.release();
                this.clientPrefaceString = null;
                return false;
            }
            return true;
        }

        private boolean verifyFirstFrameIsSettings(ByteBuf byteBuf) throws Http2Exception {
            if (byteBuf.readableBytes() < 5) {
                return true;
            }
            short s = byteBuf.getUnsignedByte(byteBuf.readerIndex() + 3);
            short s2 = byteBuf.getUnsignedByte(byteBuf.readerIndex() + 4);
            if (s != 4 || (s2 & 1) != 0) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "First received frame was not SETTINGS. Hex dump for first 5 bytes: %s", ByteBufUtil.hexDump(byteBuf, byteBuf.readerIndex(), 5));
            }
            return false;
        }

        private void sendPreface(ChannelHandlerContext channelHandlerContext) throws Exception {
            boolean bl;
            if (this.prefaceSent || !channelHandlerContext.channel().isActive()) {
                return;
            }
            this.prefaceSent = true;
            boolean bl2 = bl = !this.this$0.connection().isServer();
            if (bl) {
                channelHandlerContext.write(Http2CodecUtil.connectionPrefaceBuf()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
            Http2ConnectionHandler.access$100(this.this$0).writeSettings(channelHandlerContext, Http2ConnectionHandler.access$600(this.this$0), channelHandlerContext.newPromise()).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            if (bl) {
                this.this$0.userEventTriggered(channelHandlerContext, Http2ConnectionPrefaceAndSettingsFrameWrittenEvent.INSTANCE);
            }
        }
    }

    private abstract class BaseDecoder {
        final Http2ConnectionHandler this$0;

        private BaseDecoder(Http2ConnectionHandler http2ConnectionHandler) {
            this.this$0 = http2ConnectionHandler;
        }

        public abstract void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception;

        public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        }

        public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
            this.this$0.encoder().close();
            this.this$0.decoder().close();
            this.this$0.connection().close(channelHandlerContext.voidPromise());
        }

        public boolean prefaceSent() {
            return false;
        }

        BaseDecoder(Http2ConnectionHandler http2ConnectionHandler, 1 var2_2) {
            this(http2ConnectionHandler);
        }
    }
}

