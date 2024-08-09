/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.codec.http.HttpServerUpgradeHandler;
import io.netty.handler.codec.http2.DefaultHttp2DataFrame;
import io.netty.handler.codec.http2.DefaultHttp2GoAwayFrame;
import io.netty.handler.codec.http2.DefaultHttp2HeadersFrame;
import io.netty.handler.codec.http2.DefaultHttp2PingFrame;
import io.netty.handler.codec.http2.DefaultHttp2ResetFrame;
import io.netty.handler.codec.http2.DefaultHttp2SettingsFrame;
import io.netty.handler.codec.http2.DefaultHttp2UnknownFrame;
import io.netty.handler.codec.http2.DefaultHttp2WindowUpdateFrame;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionAdapter;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2ConnectionHandler;
import io.netty.handler.codec.http2.Http2ConnectionPrefaceAndSettingsFrameWrittenEvent;
import io.netty.handler.codec.http2.Http2DataFrame;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2Frame;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.handler.codec.http2.Http2FrameStreamEvent;
import io.netty.handler.codec.http2.Http2FrameStreamException;
import io.netty.handler.codec.http2.Http2FrameStreamVisitor;
import io.netty.handler.codec.http2.Http2GoAwayFrame;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersFrame;
import io.netty.handler.codec.http2.Http2LocalFlowController;
import io.netty.handler.codec.http2.Http2NoMoreStreamIdsException;
import io.netty.handler.codec.http2.Http2PingFrame;
import io.netty.handler.codec.http2.Http2RemoteFlowController;
import io.netty.handler.codec.http2.Http2ResetFrame;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.Http2SettingsFrame;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.Http2StreamVisitor;
import io.netty.handler.codec.http2.Http2UnknownFrame;
import io.netty.handler.codec.http2.Http2WindowUpdateFrame;
import io.netty.handler.codec.http2.HttpConversionUtil;
import io.netty.handler.codec.http2.InboundHttpToHttp2Adapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class Http2FrameCodec
extends Http2ConnectionHandler {
    private static final InternalLogger LOG;
    private final Http2Connection.PropertyKey streamKey;
    private final Http2Connection.PropertyKey upgradeKey;
    private final Integer initialFlowControlWindowSize;
    private ChannelHandlerContext ctx;
    private int numBufferedStreams;
    private DefaultHttp2FrameStream frameStreamToInitialize;
    static final boolean $assertionsDisabled;

    Http2FrameCodec(Http2ConnectionEncoder http2ConnectionEncoder, Http2ConnectionDecoder http2ConnectionDecoder, Http2Settings http2Settings) {
        super(http2ConnectionDecoder, http2ConnectionEncoder, http2Settings);
        http2ConnectionDecoder.frameListener(new FrameListener(this, null));
        this.connection().addListener(new ConnectionListener(this, null));
        this.connection().remote().flowController().listener(new Http2RemoteFlowControllerListener(this, null));
        this.streamKey = this.connection().newKey();
        this.upgradeKey = this.connection().newKey();
        this.initialFlowControlWindowSize = http2Settings.initialWindowSize();
    }

    DefaultHttp2FrameStream newStream() {
        return new DefaultHttp2FrameStream();
    }

    final void forEachActiveStream(Http2FrameStreamVisitor http2FrameStreamVisitor) throws Http2Exception {
        if (!$assertionsDisabled && !this.ctx.executor().inEventLoop()) {
            throw new AssertionError();
        }
        this.connection().forEachActiveStream(new Http2StreamVisitor(this, http2FrameStreamVisitor){
            final Http2FrameStreamVisitor val$streamVisitor;
            final Http2FrameCodec this$0;
            {
                this.this$0 = http2FrameCodec;
                this.val$streamVisitor = http2FrameStreamVisitor;
            }

            @Override
            public boolean visit(Http2Stream http2Stream) {
                try {
                    return this.val$streamVisitor.visit((Http2FrameStream)http2Stream.getProperty(Http2FrameCodec.access$300(this.this$0)));
                } catch (Throwable throwable) {
                    this.this$0.onError(Http2FrameCodec.access$400(this.this$0), false, throwable);
                    return true;
                }
            }
        });
    }

    @Override
    public final void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
        super.handlerAdded(channelHandlerContext);
        this.handlerAdded0(channelHandlerContext);
        Http2Connection http2Connection = this.connection();
        if (http2Connection.isServer()) {
            this.tryExpandConnectionFlowControlWindow(http2Connection);
        }
    }

    private void tryExpandConnectionFlowControlWindow(Http2Connection http2Connection) throws Http2Exception {
        if (this.initialFlowControlWindowSize != null) {
            Http2Stream http2Stream = http2Connection.connectionStream();
            Http2LocalFlowController http2LocalFlowController = http2Connection.local().flowController();
            int n = this.initialFlowControlWindowSize - http2LocalFlowController.initialWindowSize(http2Stream);
            if (n > 0) {
                http2LocalFlowController.incrementWindowSize(http2Stream, Math.max(n << 1, n));
                this.flush(this.ctx);
            }
        }
    }

    void handlerAdded0(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (object == Http2ConnectionPrefaceAndSettingsFrameWrittenEvent.INSTANCE) {
            this.tryExpandConnectionFlowControlWindow(this.connection());
        } else if (object instanceof HttpServerUpgradeHandler.UpgradeEvent) {
            HttpServerUpgradeHandler.UpgradeEvent upgradeEvent = (HttpServerUpgradeHandler.UpgradeEvent)object;
            try {
                this.onUpgradeEvent(channelHandlerContext, upgradeEvent.retain());
                Http2Stream http2Stream = this.connection().stream(1);
                if (http2Stream.getProperty(this.streamKey) == null) {
                    this.onStreamActive0(http2Stream);
                }
                upgradeEvent.upgradeRequest().headers().setInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text(), 1);
                http2Stream.setProperty(this.upgradeKey, true);
                InboundHttpToHttp2Adapter.handle(channelHandlerContext, this.connection(), this.decoder().frameListener(), upgradeEvent.upgradeRequest().retain());
            } finally {
                upgradeEvent.release();
            }
            return;
        }
        super.userEventTriggered(channelHandlerContext, object);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) {
        if (object instanceof Http2DataFrame) {
            Http2DataFrame http2DataFrame = (Http2DataFrame)object;
            this.encoder().writeData(channelHandlerContext, http2DataFrame.stream().id(), http2DataFrame.content(), http2DataFrame.padding(), http2DataFrame.isEndStream(), channelPromise);
        } else if (object instanceof Http2HeadersFrame) {
            this.writeHeadersFrame(channelHandlerContext, (Http2HeadersFrame)object, channelPromise);
        } else if (object instanceof Http2WindowUpdateFrame) {
            Http2WindowUpdateFrame http2WindowUpdateFrame = (Http2WindowUpdateFrame)object;
            Http2FrameStream http2FrameStream = http2WindowUpdateFrame.stream();
            try {
                if (http2FrameStream == null) {
                    this.increaseInitialConnectionWindow(http2WindowUpdateFrame.windowSizeIncrement());
                } else {
                    this.consumeBytes(http2FrameStream.id(), http2WindowUpdateFrame.windowSizeIncrement());
                }
                channelPromise.setSuccess();
            } catch (Throwable throwable) {
                channelPromise.setFailure(throwable);
            }
        } else if (object instanceof Http2ResetFrame) {
            Http2ResetFrame http2ResetFrame = (Http2ResetFrame)object;
            this.encoder().writeRstStream(channelHandlerContext, http2ResetFrame.stream().id(), http2ResetFrame.errorCode(), channelPromise);
        } else if (object instanceof Http2PingFrame) {
            Http2PingFrame http2PingFrame = (Http2PingFrame)object;
            this.encoder().writePing(channelHandlerContext, http2PingFrame.ack(), http2PingFrame.content(), channelPromise);
        } else if (object instanceof Http2SettingsFrame) {
            this.encoder().writeSettings(channelHandlerContext, ((Http2SettingsFrame)object).settings(), channelPromise);
        } else if (object instanceof Http2GoAwayFrame) {
            this.writeGoAwayFrame(channelHandlerContext, (Http2GoAwayFrame)object, channelPromise);
        } else if (object instanceof Http2UnknownFrame) {
            Http2UnknownFrame http2UnknownFrame = (Http2UnknownFrame)object;
            this.encoder().writeFrame(channelHandlerContext, http2UnknownFrame.frameType(), http2UnknownFrame.stream().id(), http2UnknownFrame.flags(), http2UnknownFrame.content(), channelPromise);
        } else if (!(object instanceof Http2Frame)) {
            channelHandlerContext.write(object, channelPromise);
        } else {
            ReferenceCountUtil.release(object);
            throw new UnsupportedMessageTypeException(object, new Class[0]);
        }
    }

    private void increaseInitialConnectionWindow(int n) throws Http2Exception {
        this.connection().local().flowController().incrementWindowSize(this.connection().connectionStream(), n);
    }

    final boolean consumeBytes(int n, int n2) throws Http2Exception {
        Boolean bl;
        Http2Stream http2Stream = this.connection().stream(n);
        if (http2Stream != null && n == 1 && Boolean.TRUE.equals(bl = (Boolean)http2Stream.getProperty(this.upgradeKey))) {
            return true;
        }
        return this.connection().local().flowController().consumeBytes(http2Stream, n2);
    }

    private void writeGoAwayFrame(ChannelHandlerContext channelHandlerContext, Http2GoAwayFrame http2GoAwayFrame, ChannelPromise channelPromise) {
        if (http2GoAwayFrame.lastStreamId() > -1) {
            http2GoAwayFrame.release();
            throw new IllegalArgumentException("Last stream id must not be set on GOAWAY frame");
        }
        int n = this.connection().remote().lastStreamCreated();
        long l = (long)n + (long)http2GoAwayFrame.extraStreamIds() * 2L;
        if (l > Integer.MAX_VALUE) {
            l = Integer.MAX_VALUE;
        }
        this.goAway(channelHandlerContext, (int)l, http2GoAwayFrame.errorCode(), http2GoAwayFrame.content(), channelPromise);
    }

    private void writeHeadersFrame(ChannelHandlerContext channelHandlerContext, Http2HeadersFrame http2HeadersFrame, ChannelPromise channelPromise) {
        if (Http2CodecUtil.isStreamIdValid(http2HeadersFrame.stream().id())) {
            this.encoder().writeHeaders(channelHandlerContext, http2HeadersFrame.stream().id(), http2HeadersFrame.headers(), http2HeadersFrame.padding(), http2HeadersFrame.isEndStream(), channelPromise);
        } else {
            DefaultHttp2FrameStream defaultHttp2FrameStream = (DefaultHttp2FrameStream)http2HeadersFrame.stream();
            Http2Connection http2Connection = this.connection();
            int n = http2Connection.local().incrementAndGetNextStreamId();
            if (n < 0) {
                channelPromise.setFailure(new Http2NoMoreStreamIdsException());
                return;
            }
            DefaultHttp2FrameStream.access$502(defaultHttp2FrameStream, n);
            if (!$assertionsDisabled && this.frameStreamToInitialize != null) {
                throw new AssertionError();
            }
            this.frameStreamToInitialize = defaultHttp2FrameStream;
            ChannelPromise channelPromise2 = channelHandlerContext.newPromise();
            this.encoder().writeHeaders(channelHandlerContext, n, http2HeadersFrame.headers(), http2HeadersFrame.padding(), http2HeadersFrame.isEndStream(), channelPromise2);
            if (channelPromise2.isDone()) {
                Http2FrameCodec.notifyHeaderWritePromise(channelPromise2, channelPromise);
            } else {
                ++this.numBufferedStreams;
                channelPromise2.addListener(new ChannelFutureListener(this, channelPromise){
                    final ChannelPromise val$promise;
                    final Http2FrameCodec this$0;
                    {
                        this.this$0 = http2FrameCodec;
                        this.val$promise = channelPromise;
                    }

                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        Http2FrameCodec.access$610(this.this$0);
                        Http2FrameCodec.access$700(channelFuture, this.val$promise);
                    }

                    @Override
                    public void operationComplete(Future future) throws Exception {
                        this.operationComplete((ChannelFuture)future);
                    }
                });
            }
        }
    }

    private static void notifyHeaderWritePromise(ChannelFuture channelFuture, ChannelPromise channelPromise) {
        Throwable throwable = channelFuture.cause();
        if (throwable == null) {
            channelPromise.setSuccess();
        } else {
            channelPromise.setFailure(throwable);
        }
    }

    private void onStreamActive0(Http2Stream http2Stream) {
        if (this.connection().local().isValidStreamId(http2Stream.id())) {
            return;
        }
        DefaultHttp2FrameStream defaultHttp2FrameStream = this.newStream().setStreamAndProperty(this.streamKey, http2Stream);
        this.onHttp2StreamStateChanged(this.ctx, defaultHttp2FrameStream);
    }

    @Override
    protected void onConnectionError(ChannelHandlerContext channelHandlerContext, boolean bl, Throwable throwable, Http2Exception http2Exception) {
        if (!bl) {
            channelHandlerContext.fireExceptionCaught(throwable);
        }
        super.onConnectionError(channelHandlerContext, bl, throwable, http2Exception);
    }

    @Override
    protected final void onStreamError(ChannelHandlerContext channelHandlerContext, boolean bl, Throwable throwable, Http2Exception.StreamException streamException) {
        int n = streamException.streamId();
        Http2Stream http2Stream = this.connection().stream(n);
        if (http2Stream == null) {
            this.onHttp2UnknownStreamError(channelHandlerContext, throwable, streamException);
            super.onStreamError(channelHandlerContext, bl, throwable, streamException);
            return;
        }
        Http2FrameStream http2FrameStream = (Http2FrameStream)http2Stream.getProperty(this.streamKey);
        if (http2FrameStream == null) {
            LOG.warn("Stream exception thrown without stream object attached.", throwable);
            super.onStreamError(channelHandlerContext, bl, throwable, streamException);
            return;
        }
        if (!bl) {
            this.onHttp2FrameStreamException(channelHandlerContext, new Http2FrameStreamException(http2FrameStream, streamException.error(), throwable));
        }
    }

    void onHttp2UnknownStreamError(ChannelHandlerContext channelHandlerContext, Throwable throwable, Http2Exception.StreamException streamException) {
        LOG.warn("Stream exception thrown for unkown stream {}.", (Object)streamException.streamId(), (Object)throwable);
    }

    @Override
    protected final boolean isGracefulShutdownComplete() {
        return super.isGracefulShutdownComplete() && this.numBufferedStreams == 0;
    }

    void onUpgradeEvent(ChannelHandlerContext channelHandlerContext, HttpServerUpgradeHandler.UpgradeEvent upgradeEvent) {
        channelHandlerContext.fireUserEventTriggered(upgradeEvent);
    }

    void onHttp2StreamWritabilityChanged(ChannelHandlerContext channelHandlerContext, Http2FrameStream http2FrameStream, boolean bl) {
        channelHandlerContext.fireUserEventTriggered(Http2FrameStreamEvent.writabilityChanged(http2FrameStream));
    }

    void onHttp2StreamStateChanged(ChannelHandlerContext channelHandlerContext, Http2FrameStream http2FrameStream) {
        channelHandlerContext.fireUserEventTriggered(Http2FrameStreamEvent.stateChanged(http2FrameStream));
    }

    void onHttp2Frame(ChannelHandlerContext channelHandlerContext, Http2Frame http2Frame) {
        channelHandlerContext.fireChannelRead(http2Frame);
    }

    void onHttp2FrameStreamException(ChannelHandlerContext channelHandlerContext, Http2FrameStreamException http2FrameStreamException) {
        channelHandlerContext.fireExceptionCaught(http2FrameStreamException);
    }

    final boolean isWritable(DefaultHttp2FrameStream defaultHttp2FrameStream) {
        Http2Stream http2Stream = defaultHttp2FrameStream.stream;
        return http2Stream != null && this.connection().remote().flowController().isWritable(http2Stream);
    }

    static Http2Connection.PropertyKey access$300(Http2FrameCodec http2FrameCodec) {
        return http2FrameCodec.streamKey;
    }

    static ChannelHandlerContext access$400(Http2FrameCodec http2FrameCodec) {
        return http2FrameCodec.ctx;
    }

    static int access$610(Http2FrameCodec http2FrameCodec) {
        return http2FrameCodec.numBufferedStreams--;
    }

    static void access$700(ChannelFuture channelFuture, ChannelPromise channelPromise) {
        Http2FrameCodec.notifyHeaderWritePromise(channelFuture, channelPromise);
    }

    static DefaultHttp2FrameStream access$800(Http2FrameCodec http2FrameCodec) {
        return http2FrameCodec.frameStreamToInitialize;
    }

    static DefaultHttp2FrameStream access$802(Http2FrameCodec http2FrameCodec, DefaultHttp2FrameStream defaultHttp2FrameStream) {
        http2FrameCodec.frameStreamToInitialize = defaultHttp2FrameStream;
        return http2FrameCodec.frameStreamToInitialize;
    }

    static void access$900(Http2FrameCodec http2FrameCodec, Http2Stream http2Stream) {
        http2FrameCodec.onStreamActive0(http2Stream);
    }

    static {
        $assertionsDisabled = !Http2FrameCodec.class.desiredAssertionStatus();
        LOG = InternalLoggerFactory.getInstance(Http2FrameCodec.class);
    }

    static class DefaultHttp2FrameStream
    implements Http2FrameStream {
        private volatile int id = -1;
        volatile Http2Stream stream;
        static final boolean $assertionsDisabled = !Http2FrameCodec.class.desiredAssertionStatus();

        DefaultHttp2FrameStream() {
        }

        DefaultHttp2FrameStream setStreamAndProperty(Http2Connection.PropertyKey propertyKey, Http2Stream http2Stream) {
            if (!$assertionsDisabled && this.id != -1 && http2Stream.id() != this.id) {
                throw new AssertionError();
            }
            this.stream = http2Stream;
            http2Stream.setProperty(propertyKey, this);
            return this;
        }

        @Override
        public int id() {
            Http2Stream http2Stream = this.stream;
            return http2Stream == null ? this.id : http2Stream.id();
        }

        @Override
        public Http2Stream.State state() {
            Http2Stream http2Stream = this.stream;
            return http2Stream == null ? Http2Stream.State.IDLE : http2Stream.state();
        }

        public String toString() {
            return String.valueOf(this.id());
        }

        static int access$502(DefaultHttp2FrameStream defaultHttp2FrameStream, int n) {
            defaultHttp2FrameStream.id = n;
            return defaultHttp2FrameStream.id;
        }
    }

    private final class Http2RemoteFlowControllerListener
    implements Http2RemoteFlowController.Listener {
        final Http2FrameCodec this$0;

        private Http2RemoteFlowControllerListener(Http2FrameCodec http2FrameCodec) {
            this.this$0 = http2FrameCodec;
        }

        @Override
        public void writabilityChanged(Http2Stream http2Stream) {
            Http2FrameStream http2FrameStream = (Http2FrameStream)http2Stream.getProperty(Http2FrameCodec.access$300(this.this$0));
            if (http2FrameStream == null) {
                return;
            }
            this.this$0.onHttp2StreamWritabilityChanged(Http2FrameCodec.access$400(this.this$0), http2FrameStream, this.this$0.connection().remote().flowController().isWritable(http2Stream));
        }

        Http2RemoteFlowControllerListener(Http2FrameCodec http2FrameCodec, 1 var2_2) {
            this(http2FrameCodec);
        }
    }

    private final class FrameListener
    implements Http2FrameListener {
        final Http2FrameCodec this$0;

        private FrameListener(Http2FrameCodec http2FrameCodec) {
            this.this$0 = http2FrameCodec;
        }

        @Override
        public void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf) {
            this.this$0.onHttp2Frame(channelHandlerContext, new DefaultHttp2UnknownFrame(by, http2Flags, byteBuf).stream(this.requireStream(n)).retain());
        }

        @Override
        public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) {
            this.this$0.onHttp2Frame(channelHandlerContext, new DefaultHttp2SettingsFrame(http2Settings));
        }

        @Override
        public void onPingRead(ChannelHandlerContext channelHandlerContext, long l) {
            this.this$0.onHttp2Frame(channelHandlerContext, new DefaultHttp2PingFrame(l, false));
        }

        @Override
        public void onPingAckRead(ChannelHandlerContext channelHandlerContext, long l) {
            this.this$0.onHttp2Frame(channelHandlerContext, new DefaultHttp2PingFrame(l, true));
        }

        @Override
        public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int n, long l) {
            this.this$0.onHttp2Frame(channelHandlerContext, new DefaultHttp2ResetFrame(l).stream(this.requireStream(n)));
        }

        @Override
        public void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int n, int n2) {
            if (n == 0) {
                return;
            }
            this.this$0.onHttp2Frame(channelHandlerContext, new DefaultHttp2WindowUpdateFrame(n2).stream(this.requireStream(n)));
        }

        @Override
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2) {
            this.onHeadersRead(channelHandlerContext, n, http2Headers, n3, bl2);
        }

        @Override
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl) {
            this.this$0.onHttp2Frame(channelHandlerContext, new DefaultHttp2HeadersFrame(http2Headers, bl, n2).stream(this.requireStream(n)));
        }

        @Override
        public int onDataRead(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl) {
            this.this$0.onHttp2Frame(channelHandlerContext, new DefaultHttp2DataFrame(byteBuf, bl, n2).stream(this.requireStream(n)).retain());
            return 1;
        }

        @Override
        public void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf) {
            this.this$0.onHttp2Frame(channelHandlerContext, new DefaultHttp2GoAwayFrame(n, l, byteBuf).retain());
        }

        @Override
        public void onPriorityRead(ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl) {
        }

        @Override
        public void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) {
        }

        @Override
        public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3) {
        }

        private Http2FrameStream requireStream(int n) {
            Http2FrameStream http2FrameStream = (Http2FrameStream)this.this$0.connection().stream(n).getProperty(Http2FrameCodec.access$300(this.this$0));
            if (http2FrameStream == null) {
                throw new IllegalStateException("Stream object required for identifier: " + n);
            }
            return http2FrameStream;
        }

        FrameListener(Http2FrameCodec http2FrameCodec, 1 var2_2) {
            this(http2FrameCodec);
        }
    }

    private final class ConnectionListener
    extends Http2ConnectionAdapter {
        final Http2FrameCodec this$0;

        private ConnectionListener(Http2FrameCodec http2FrameCodec) {
            this.this$0 = http2FrameCodec;
        }

        @Override
        public void onStreamAdded(Http2Stream http2Stream) {
            if (Http2FrameCodec.access$800(this.this$0) != null && http2Stream.id() == Http2FrameCodec.access$800(this.this$0).id()) {
                Http2FrameCodec.access$800(this.this$0).setStreamAndProperty(Http2FrameCodec.access$300(this.this$0), http2Stream);
                Http2FrameCodec.access$802(this.this$0, null);
            }
        }

        @Override
        public void onStreamActive(Http2Stream http2Stream) {
            Http2FrameCodec.access$900(this.this$0, http2Stream);
        }

        @Override
        public void onStreamClosed(Http2Stream http2Stream) {
            DefaultHttp2FrameStream defaultHttp2FrameStream = (DefaultHttp2FrameStream)http2Stream.getProperty(Http2FrameCodec.access$300(this.this$0));
            if (defaultHttp2FrameStream != null) {
                this.this$0.onHttp2StreamStateChanged(Http2FrameCodec.access$400(this.this$0), defaultHttp2FrameStream);
            }
        }

        @Override
        public void onStreamHalfClosed(Http2Stream http2Stream) {
            DefaultHttp2FrameStream defaultHttp2FrameStream = (DefaultHttp2FrameStream)http2Stream.getProperty(Http2FrameCodec.access$300(this.this$0));
            if (defaultHttp2FrameStream != null) {
                this.this$0.onHttp2StreamStateChanged(Http2FrameCodec.access$400(this.this$0), defaultHttp2FrameStream);
            }
        }

        ConnectionListener(Http2FrameCodec http2FrameCodec, 1 var2_2) {
            this(http2FrameCodec);
        }
    }
}

