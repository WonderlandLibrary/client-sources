/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpStatusClass;
import io.netty.handler.codec.http2.DefaultHttp2LocalFlowController;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2FrameReader;
import io.netty.handler.codec.http2.Http2FrameSizePolicy;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersDecoder;
import io.netty.handler.codec.http2.Http2LifecycleManager;
import io.netty.handler.codec.http2.Http2LocalFlowController;
import io.netty.handler.codec.http2.Http2PromisedRequestVerifier;
import io.netty.handler.codec.http2.Http2RemoteFlowController;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.List;

public class DefaultHttp2ConnectionDecoder
implements Http2ConnectionDecoder {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultHttp2ConnectionDecoder.class);
    private Http2FrameListener internalFrameListener = new PrefaceFrameListener(this, null);
    private final Http2Connection connection;
    private Http2LifecycleManager lifecycleManager;
    private final Http2ConnectionEncoder encoder;
    private final Http2FrameReader frameReader;
    private Http2FrameListener listener;
    private final Http2PromisedRequestVerifier requestVerifier;

    public DefaultHttp2ConnectionDecoder(Http2Connection http2Connection, Http2ConnectionEncoder http2ConnectionEncoder, Http2FrameReader http2FrameReader) {
        this(http2Connection, http2ConnectionEncoder, http2FrameReader, Http2PromisedRequestVerifier.ALWAYS_VERIFY);
    }

    public DefaultHttp2ConnectionDecoder(Http2Connection http2Connection, Http2ConnectionEncoder http2ConnectionEncoder, Http2FrameReader http2FrameReader, Http2PromisedRequestVerifier http2PromisedRequestVerifier) {
        this.connection = ObjectUtil.checkNotNull(http2Connection, "connection");
        this.frameReader = ObjectUtil.checkNotNull(http2FrameReader, "frameReader");
        this.encoder = ObjectUtil.checkNotNull(http2ConnectionEncoder, "encoder");
        this.requestVerifier = ObjectUtil.checkNotNull(http2PromisedRequestVerifier, "requestVerifier");
        if (http2Connection.local().flowController() == null) {
            http2Connection.local().flowController(new DefaultHttp2LocalFlowController(http2Connection));
        }
        http2Connection.local().flowController().frameWriter(http2ConnectionEncoder.frameWriter());
    }

    @Override
    public void lifecycleManager(Http2LifecycleManager http2LifecycleManager) {
        this.lifecycleManager = ObjectUtil.checkNotNull(http2LifecycleManager, "lifecycleManager");
    }

    @Override
    public Http2Connection connection() {
        return this.connection;
    }

    @Override
    public final Http2LocalFlowController flowController() {
        return this.connection.local().flowController();
    }

    @Override
    public void frameListener(Http2FrameListener http2FrameListener) {
        this.listener = ObjectUtil.checkNotNull(http2FrameListener, "listener");
    }

    @Override
    public Http2FrameListener frameListener() {
        return this.listener;
    }

    Http2FrameListener internalFrameListener() {
        return this.internalFrameListener;
    }

    @Override
    public boolean prefaceReceived() {
        return FrameReadListener.class == this.internalFrameListener.getClass();
    }

    @Override
    public void decodeFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Http2Exception {
        this.frameReader.readFrame(channelHandlerContext, byteBuf, this.internalFrameListener);
    }

    @Override
    public Http2Settings localSettings() {
        Http2Settings http2Settings = new Http2Settings();
        Http2FrameReader.Configuration configuration = this.frameReader.configuration();
        Http2HeadersDecoder.Configuration configuration2 = configuration.headersConfiguration();
        Http2FrameSizePolicy http2FrameSizePolicy = configuration.frameSizePolicy();
        http2Settings.initialWindowSize(this.flowController().initialWindowSize());
        http2Settings.maxConcurrentStreams(this.connection.remote().maxActiveStreams());
        http2Settings.headerTableSize(configuration2.maxHeaderTableSize());
        http2Settings.maxFrameSize(http2FrameSizePolicy.maxFrameSize());
        http2Settings.maxHeaderListSize(configuration2.maxHeaderListSize());
        if (!this.connection.isServer()) {
            http2Settings.pushEnabled(this.connection.local().allowPushTo());
        }
        return http2Settings;
    }

    @Override
    public void close() {
        this.frameReader.close();
    }

    protected long calculateMaxHeaderListSizeGoAway(long l) {
        return Http2CodecUtil.calculateMaxHeaderListSizeGoAway(l);
    }

    private int unconsumedBytes(Http2Stream http2Stream) {
        return this.flowController().unconsumedBytes(http2Stream);
    }

    void onGoAwayRead0(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf) throws Http2Exception {
        if (this.connection.goAwayReceived() && this.connection.local().lastStreamKnownByPeer() < n) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "lastStreamId MUST NOT increase. Current value: %d new value: %d", this.connection.local().lastStreamKnownByPeer(), n);
        }
        this.listener.onGoAwayRead(channelHandlerContext, n, l, byteBuf);
        this.connection.goAwayReceived(n, l, byteBuf);
    }

    void onUnknownFrame0(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception {
        this.listener.onUnknownFrame(channelHandlerContext, by, n, http2Flags, byteBuf);
    }

    static Http2Connection access$100(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder) {
        return defaultHttp2ConnectionDecoder.connection;
    }

    static int access$200(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder, Http2Stream http2Stream) {
        return defaultHttp2ConnectionDecoder.unconsumedBytes(http2Stream);
    }

    static Http2FrameListener access$300(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder) {
        return defaultHttp2ConnectionDecoder.listener;
    }

    static Http2LifecycleManager access$400(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder) {
        return defaultHttp2ConnectionDecoder.lifecycleManager;
    }

    static Http2ConnectionEncoder access$500(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder) {
        return defaultHttp2ConnectionDecoder.encoder;
    }

    static Http2FrameReader access$600(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder) {
        return defaultHttp2ConnectionDecoder.frameReader;
    }

    static Http2PromisedRequestVerifier access$700(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder) {
        return defaultHttp2ConnectionDecoder.requestVerifier;
    }

    static InternalLogger access$800() {
        return logger;
    }

    static Http2FrameListener access$900(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder) {
        return defaultHttp2ConnectionDecoder.internalFrameListener;
    }

    static Http2FrameListener access$902(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder, Http2FrameListener http2FrameListener) {
        defaultHttp2ConnectionDecoder.internalFrameListener = http2FrameListener;
        return defaultHttp2ConnectionDecoder.internalFrameListener;
    }

    private final class PrefaceFrameListener
    implements Http2FrameListener {
        final DefaultHttp2ConnectionDecoder this$0;

        private PrefaceFrameListener(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder) {
            this.this$0 = defaultHttp2ConnectionDecoder;
        }

        private void verifyPrefaceReceived() throws Http2Exception {
            if (!this.this$0.prefaceReceived()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received non-SETTINGS as first frame.", new Object[0]);
            }
        }

        @Override
        public int onDataRead(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl) throws Http2Exception {
            this.verifyPrefaceReceived();
            return DefaultHttp2ConnectionDecoder.access$900(this.this$0).onDataRead(channelHandlerContext, n, byteBuf, n2, bl);
        }

        @Override
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl) throws Http2Exception {
            this.verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.access$900(this.this$0).onHeadersRead(channelHandlerContext, n, http2Headers, n2, bl);
        }

        @Override
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2) throws Http2Exception {
            this.verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.access$900(this.this$0).onHeadersRead(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2);
        }

        @Override
        public void onPriorityRead(ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl) throws Http2Exception {
            this.verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.access$900(this.this$0).onPriorityRead(channelHandlerContext, n, n2, s, bl);
        }

        @Override
        public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int n, long l) throws Http2Exception {
            this.verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.access$900(this.this$0).onRstStreamRead(channelHandlerContext, n, l);
        }

        @Override
        public void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
            this.verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.access$900(this.this$0).onSettingsAckRead(channelHandlerContext);
        }

        @Override
        public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception {
            if (!this.this$0.prefaceReceived()) {
                DefaultHttp2ConnectionDecoder.access$902(this.this$0, new FrameReadListener(this.this$0, null));
            }
            DefaultHttp2ConnectionDecoder.access$900(this.this$0).onSettingsRead(channelHandlerContext, http2Settings);
        }

        @Override
        public void onPingRead(ChannelHandlerContext channelHandlerContext, long l) throws Http2Exception {
            this.verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.access$900(this.this$0).onPingRead(channelHandlerContext, l);
        }

        @Override
        public void onPingAckRead(ChannelHandlerContext channelHandlerContext, long l) throws Http2Exception {
            this.verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.access$900(this.this$0).onPingAckRead(channelHandlerContext, l);
        }

        @Override
        public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3) throws Http2Exception {
            this.verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.access$900(this.this$0).onPushPromiseRead(channelHandlerContext, n, n2, http2Headers, n3);
        }

        @Override
        public void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf) throws Http2Exception {
            this.this$0.onGoAwayRead0(channelHandlerContext, n, l, byteBuf);
        }

        @Override
        public void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int n, int n2) throws Http2Exception {
            this.verifyPrefaceReceived();
            DefaultHttp2ConnectionDecoder.access$900(this.this$0).onWindowUpdateRead(channelHandlerContext, n, n2);
        }

        @Override
        public void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception {
            this.this$0.onUnknownFrame0(channelHandlerContext, by, n, http2Flags, byteBuf);
        }

        PrefaceFrameListener(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder, 1 var2_2) {
            this(defaultHttp2ConnectionDecoder);
        }
    }

    private final class FrameReadListener
    implements Http2FrameListener {
        final DefaultHttp2ConnectionDecoder this$0;

        private FrameReadListener(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder) {
            this.this$0 = defaultHttp2ConnectionDecoder;
        }

        @Override
        public int onDataRead(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl) throws Http2Exception {
            boolean bl2;
            Http2Stream http2Stream = DefaultHttp2ConnectionDecoder.access$100(this.this$0).stream(n);
            Http2LocalFlowController http2LocalFlowController = this.this$0.flowController();
            int n3 = byteBuf.readableBytes() + n2;
            try {
                bl2 = this.shouldIgnoreHeadersOrDataFrame(channelHandlerContext, n, http2Stream, "DATA");
            } catch (Http2Exception http2Exception) {
                http2LocalFlowController.receiveFlowControlledFrame(http2Stream, byteBuf, n2, bl);
                http2LocalFlowController.consumeBytes(http2Stream, n3);
                throw http2Exception;
            } catch (Throwable throwable) {
                throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, throwable, "Unhandled error on data stream id %d", n);
            }
            if (bl2) {
                http2LocalFlowController.receiveFlowControlledFrame(http2Stream, byteBuf, n2, bl);
                http2LocalFlowController.consumeBytes(http2Stream, n3);
                this.verifyStreamMayHaveExisted(n);
                return n3;
            }
            Http2Exception http2Exception = null;
            switch (1.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State[http2Stream.state().ordinal()]) {
                case 1: 
                case 2: {
                    break;
                }
                case 3: 
                case 4: {
                    http2Exception = Http2Exception.streamError(http2Stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", new Object[]{http2Stream.id(), http2Stream.state()});
                    break;
                }
                default: {
                    http2Exception = Http2Exception.streamError(http2Stream.id(), Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state: %s", new Object[]{http2Stream.id(), http2Stream.state()});
                }
            }
            int n4 = DefaultHttp2ConnectionDecoder.access$200(this.this$0, http2Stream);
            try {
                http2LocalFlowController.receiveFlowControlledFrame(http2Stream, byteBuf, n2, bl);
                n4 = DefaultHttp2ConnectionDecoder.access$200(this.this$0, http2Stream);
                if (http2Exception != null) {
                    throw http2Exception;
                }
                int n5 = n3 = DefaultHttp2ConnectionDecoder.access$300(this.this$0).onDataRead(channelHandlerContext, n, byteBuf, n2, bl);
                return n5;
            } catch (Http2Exception http2Exception2) {
                int n6 = n4 - DefaultHttp2ConnectionDecoder.access$200(this.this$0, http2Stream);
                n3 -= n6;
                throw http2Exception2;
            } catch (RuntimeException runtimeException) {
                int n7 = n4 - DefaultHttp2ConnectionDecoder.access$200(this.this$0, http2Stream);
                n3 -= n7;
                throw runtimeException;
            } finally {
                http2LocalFlowController.consumeBytes(http2Stream, n3);
                if (bl) {
                    DefaultHttp2ConnectionDecoder.access$400(this.this$0).closeStreamRemote(http2Stream, channelHandlerContext.newSucceededFuture());
                }
            }
        }

        @Override
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl) throws Http2Exception {
            this.onHeadersRead(channelHandlerContext, n, http2Headers, 0, (short)16, false, n2, bl);
        }

        @Override
        public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2) throws Http2Exception {
            boolean bl3;
            Http2Stream http2Stream = DefaultHttp2ConnectionDecoder.access$100(this.this$0).stream(n);
            boolean bl4 = false;
            if (http2Stream == null && !DefaultHttp2ConnectionDecoder.access$100(this.this$0).streamMayHaveExisted(n)) {
                http2Stream = DefaultHttp2ConnectionDecoder.access$100(this.this$0).remote().createStream(n, bl2);
                boolean bl5 = bl4 = http2Stream.state() == Http2Stream.State.HALF_CLOSED_REMOTE;
            }
            if (this.shouldIgnoreHeadersOrDataFrame(channelHandlerContext, n, http2Stream, "HEADERS")) {
                return;
            }
            boolean bl6 = bl3 = !DefaultHttp2ConnectionDecoder.access$100(this.this$0).isServer() && HttpStatusClass.valueOf(http2Headers.status()) == HttpStatusClass.INFORMATIONAL;
            if ((bl3 || !bl2) && http2Stream.isHeadersReceived() || http2Stream.isTrailersReceived()) {
                throw Http2Exception.streamError(n, Http2Error.PROTOCOL_ERROR, "Stream %d received too many headers EOS: %s state: %s", new Object[]{n, bl2, http2Stream.state()});
            }
            switch (1.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State[http2Stream.state().ordinal()]) {
                case 5: {
                    http2Stream.open(bl2);
                    break;
                }
                case 1: 
                case 2: {
                    break;
                }
                case 3: {
                    if (bl4) break;
                    throw Http2Exception.streamError(http2Stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", new Object[]{http2Stream.id(), http2Stream.state()});
                }
                case 4: {
                    throw Http2Exception.streamError(http2Stream.id(), Http2Error.STREAM_CLOSED, "Stream %d in unexpected state: %s", new Object[]{http2Stream.id(), http2Stream.state()});
                }
                default: {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state: %s", new Object[]{http2Stream.id(), http2Stream.state()});
                }
            }
            http2Stream.headersReceived(bl3);
            DefaultHttp2ConnectionDecoder.access$500(this.this$0).flowController().updateDependencyTree(n, n2, s, bl);
            DefaultHttp2ConnectionDecoder.access$300(this.this$0).onHeadersRead(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2);
            if (bl2) {
                DefaultHttp2ConnectionDecoder.access$400(this.this$0).closeStreamRemote(http2Stream, channelHandlerContext.newSucceededFuture());
            }
        }

        @Override
        public void onPriorityRead(ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.access$500(this.this$0).flowController().updateDependencyTree(n, n2, s, bl);
            DefaultHttp2ConnectionDecoder.access$300(this.this$0).onPriorityRead(channelHandlerContext, n, n2, s, bl);
        }

        @Override
        public void onRstStreamRead(ChannelHandlerContext channelHandlerContext, int n, long l) throws Http2Exception {
            Http2Stream http2Stream = DefaultHttp2ConnectionDecoder.access$100(this.this$0).stream(n);
            if (http2Stream == null) {
                this.verifyStreamMayHaveExisted(n);
                return;
            }
            switch (1.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State[http2Stream.state().ordinal()]) {
                case 6: {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "RST_STREAM received for IDLE stream %d", n);
                }
                case 4: {
                    return;
                }
            }
            DefaultHttp2ConnectionDecoder.access$300(this.this$0).onRstStreamRead(channelHandlerContext, n, l);
            DefaultHttp2ConnectionDecoder.access$400(this.this$0).closeStream(http2Stream, channelHandlerContext.newSucceededFuture());
        }

        @Override
        public void onSettingsAckRead(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
            Http2Settings http2Settings = DefaultHttp2ConnectionDecoder.access$500(this.this$0).pollSentSettings();
            if (http2Settings != null) {
                this.applyLocalSettings(http2Settings);
            }
            DefaultHttp2ConnectionDecoder.access$300(this.this$0).onSettingsAckRead(channelHandlerContext);
        }

        private void applyLocalSettings(Http2Settings http2Settings) throws Http2Exception {
            Integer n;
            Integer n2;
            Long l;
            Long l2;
            Long l3;
            Boolean bl = http2Settings.pushEnabled();
            Http2FrameReader.Configuration configuration = DefaultHttp2ConnectionDecoder.access$600(this.this$0).configuration();
            Http2HeadersDecoder.Configuration configuration2 = configuration.headersConfiguration();
            Http2FrameSizePolicy http2FrameSizePolicy = configuration.frameSizePolicy();
            if (bl != null) {
                if (DefaultHttp2ConnectionDecoder.access$100(this.this$0).isServer()) {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server sending SETTINGS frame with ENABLE_PUSH specified", new Object[0]);
                }
                DefaultHttp2ConnectionDecoder.access$100(this.this$0).local().allowPushTo(bl);
            }
            if ((l3 = http2Settings.maxConcurrentStreams()) != null) {
                DefaultHttp2ConnectionDecoder.access$100(this.this$0).remote().maxActiveStreams((int)Math.min(l3, Integer.MAX_VALUE));
            }
            if ((l2 = http2Settings.headerTableSize()) != null) {
                configuration2.maxHeaderTableSize(l2);
            }
            if ((l = http2Settings.maxHeaderListSize()) != null) {
                configuration2.maxHeaderListSize(l, this.this$0.calculateMaxHeaderListSizeGoAway(l));
            }
            if ((n2 = http2Settings.maxFrameSize()) != null) {
                http2FrameSizePolicy.maxFrameSize(n2);
            }
            if ((n = http2Settings.initialWindowSize()) != null) {
                this.this$0.flowController().initialWindowSize(n);
            }
        }

        @Override
        public void onSettingsRead(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.access$500(this.this$0).writeSettingsAck(channelHandlerContext, channelHandlerContext.newPromise());
            DefaultHttp2ConnectionDecoder.access$500(this.this$0).remoteSettings(http2Settings);
            DefaultHttp2ConnectionDecoder.access$300(this.this$0).onSettingsRead(channelHandlerContext, http2Settings);
        }

        @Override
        public void onPingRead(ChannelHandlerContext channelHandlerContext, long l) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.access$500(this.this$0).writePing(channelHandlerContext, true, l, channelHandlerContext.newPromise());
            DefaultHttp2ConnectionDecoder.access$300(this.this$0).onPingRead(channelHandlerContext, l);
        }

        @Override
        public void onPingAckRead(ChannelHandlerContext channelHandlerContext, long l) throws Http2Exception {
            DefaultHttp2ConnectionDecoder.access$300(this.this$0).onPingAckRead(channelHandlerContext, l);
        }

        @Override
        public void onPushPromiseRead(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3) throws Http2Exception {
            if (this.this$0.connection().isServer()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A client cannot push.", new Object[0]);
            }
            Http2Stream http2Stream = DefaultHttp2ConnectionDecoder.access$100(this.this$0).stream(n);
            if (this.shouldIgnoreHeadersOrDataFrame(channelHandlerContext, n, http2Stream, "PUSH_PROMISE")) {
                return;
            }
            if (http2Stream == null) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d does not exist", n);
            }
            switch (1.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State[http2Stream.state().ordinal()]) {
                case 1: 
                case 2: {
                    break;
                }
                default: {
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d in unexpected state for receiving push promise: %s", new Object[]{http2Stream.id(), http2Stream.state()});
                }
            }
            if (!DefaultHttp2ConnectionDecoder.access$700(this.this$0).isAuthoritative(channelHandlerContext, http2Headers)) {
                throw Http2Exception.streamError(n2, Http2Error.PROTOCOL_ERROR, "Promised request on stream %d for promised stream %d is not authoritative", n, n2);
            }
            if (!DefaultHttp2ConnectionDecoder.access$700(this.this$0).isCacheable(http2Headers)) {
                throw Http2Exception.streamError(n2, Http2Error.PROTOCOL_ERROR, "Promised request on stream %d for promised stream %d is not known to be cacheable", n, n2);
            }
            if (!DefaultHttp2ConnectionDecoder.access$700(this.this$0).isSafe(http2Headers)) {
                throw Http2Exception.streamError(n2, Http2Error.PROTOCOL_ERROR, "Promised request on stream %d for promised stream %d is not known to be safe", n, n2);
            }
            DefaultHttp2ConnectionDecoder.access$100(this.this$0).remote().reservePushStream(n2, http2Stream);
            DefaultHttp2ConnectionDecoder.access$300(this.this$0).onPushPromiseRead(channelHandlerContext, n, n2, http2Headers, n3);
        }

        @Override
        public void onGoAwayRead(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf) throws Http2Exception {
            this.this$0.onGoAwayRead0(channelHandlerContext, n, l, byteBuf);
        }

        @Override
        public void onWindowUpdateRead(ChannelHandlerContext channelHandlerContext, int n, int n2) throws Http2Exception {
            Http2Stream http2Stream = DefaultHttp2ConnectionDecoder.access$100(this.this$0).stream(n);
            if (http2Stream == null || http2Stream.state() == Http2Stream.State.CLOSED || this.streamCreatedAfterGoAwaySent(n)) {
                this.verifyStreamMayHaveExisted(n);
                return;
            }
            DefaultHttp2ConnectionDecoder.access$500(this.this$0).flowController().incrementWindowSize(http2Stream, n2);
            DefaultHttp2ConnectionDecoder.access$300(this.this$0).onWindowUpdateRead(channelHandlerContext, n, n2);
        }

        @Override
        public void onUnknownFrame(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf) throws Http2Exception {
            this.this$0.onUnknownFrame0(channelHandlerContext, by, n, http2Flags, byteBuf);
        }

        private boolean shouldIgnoreHeadersOrDataFrame(ChannelHandlerContext channelHandlerContext, int n, Http2Stream http2Stream, String string) throws Http2Exception {
            if (http2Stream == null) {
                if (this.streamCreatedAfterGoAwaySent(n)) {
                    DefaultHttp2ConnectionDecoder.access$800().info("{} ignoring {} frame for stream {}. Stream sent after GOAWAY sent", channelHandlerContext.channel(), string, n);
                    return false;
                }
                throw Http2Exception.streamError(n, Http2Error.STREAM_CLOSED, "Received %s frame for an unknown stream %d", string, n);
            }
            if (http2Stream.isResetSent() || this.streamCreatedAfterGoAwaySent(n)) {
                if (DefaultHttp2ConnectionDecoder.access$800().isInfoEnabled()) {
                    DefaultHttp2ConnectionDecoder.access$800().info("{} ignoring {} frame for stream {} {}", channelHandlerContext.channel(), string, http2Stream.isResetSent() ? "RST_STREAM sent." : "Stream created after GOAWAY sent. Last known stream by peer " + DefaultHttp2ConnectionDecoder.access$100(this.this$0).remote().lastStreamKnownByPeer());
                }
                return false;
            }
            return true;
        }

        private boolean streamCreatedAfterGoAwaySent(int n) {
            Http2Connection.Endpoint<Http2RemoteFlowController> endpoint = DefaultHttp2ConnectionDecoder.access$100(this.this$0).remote();
            return DefaultHttp2ConnectionDecoder.access$100(this.this$0).goAwaySent() && endpoint.isValidStreamId(n) && n > endpoint.lastStreamKnownByPeer();
        }

        private void verifyStreamMayHaveExisted(int n) throws Http2Exception {
            if (!DefaultHttp2ConnectionDecoder.access$100(this.this$0).streamMayHaveExisted(n)) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d does not exist", n);
            }
        }

        FrameReadListener(DefaultHttp2ConnectionDecoder defaultHttp2ConnectionDecoder, 1 var2_2) {
            this(defaultHttp2ConnectionDecoder);
        }
    }
}

