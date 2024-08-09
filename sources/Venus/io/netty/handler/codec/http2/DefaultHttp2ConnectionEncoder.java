/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.CoalescingBufferQueue;
import io.netty.handler.codec.http.HttpStatusClass;
import io.netty.handler.codec.http2.DefaultHttp2RemoteFlowController;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameSizePolicy;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.handler.codec.http2.Http2LifecycleManager;
import io.netty.handler.codec.http2.Http2RemoteFlowController;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayDeque;

public class DefaultHttp2ConnectionEncoder
implements Http2ConnectionEncoder {
    private final Http2FrameWriter frameWriter;
    private final Http2Connection connection;
    private Http2LifecycleManager lifecycleManager;
    private final ArrayDeque<Http2Settings> outstandingLocalSettingsQueue = new ArrayDeque(4);

    public DefaultHttp2ConnectionEncoder(Http2Connection http2Connection, Http2FrameWriter http2FrameWriter) {
        this.connection = ObjectUtil.checkNotNull(http2Connection, "connection");
        this.frameWriter = ObjectUtil.checkNotNull(http2FrameWriter, "frameWriter");
        if (http2Connection.remote().flowController() == null) {
            http2Connection.remote().flowController(new DefaultHttp2RemoteFlowController(http2Connection));
        }
    }

    @Override
    public void lifecycleManager(Http2LifecycleManager http2LifecycleManager) {
        this.lifecycleManager = ObjectUtil.checkNotNull(http2LifecycleManager, "lifecycleManager");
    }

    @Override
    public Http2FrameWriter frameWriter() {
        return this.frameWriter;
    }

    @Override
    public Http2Connection connection() {
        return this.connection;
    }

    @Override
    public final Http2RemoteFlowController flowController() {
        return this.connection().remote().flowController();
    }

    @Override
    public void remoteSettings(Http2Settings http2Settings) throws Http2Exception {
        Integer n;
        Integer n2;
        Long l;
        Long l2;
        Long l3;
        Boolean bl = http2Settings.pushEnabled();
        Http2FrameWriter.Configuration configuration = this.configuration();
        Http2HeadersEncoder.Configuration configuration2 = configuration.headersConfiguration();
        Http2FrameSizePolicy http2FrameSizePolicy = configuration.frameSizePolicy();
        if (bl != null) {
            if (!this.connection.isServer() && bl.booleanValue()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Client received a value of ENABLE_PUSH specified to other than 0", new Object[0]);
            }
            this.connection.remote().allowPushTo(bl);
        }
        if ((l3 = http2Settings.maxConcurrentStreams()) != null) {
            this.connection.local().maxActiveStreams((int)Math.min(l3, Integer.MAX_VALUE));
        }
        if ((l2 = http2Settings.headerTableSize()) != null) {
            configuration2.maxHeaderTableSize((int)Math.min(l2, Integer.MAX_VALUE));
        }
        if ((l = http2Settings.maxHeaderListSize()) != null) {
            configuration2.maxHeaderListSize(l);
        }
        if ((n2 = http2Settings.maxFrameSize()) != null) {
            http2FrameSizePolicy.maxFrameSize(n2);
        }
        if ((n = http2Settings.initialWindowSize()) != null) {
            this.flowController().initialWindowSize(n);
        }
    }

    @Override
    public ChannelFuture writeData(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl, ChannelPromise channelPromise) {
        Http2Stream http2Stream;
        try {
            http2Stream = this.requireStream(n);
            switch (3.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State[http2Stream.state().ordinal()]) {
                case 1: 
                case 2: {
                    break;
                }
                default: {
                    throw new IllegalStateException("Stream " + http2Stream.id() + " in unexpected state " + (Object)((Object)http2Stream.state()));
                }
            }
        } catch (Throwable throwable) {
            byteBuf.release();
            return channelPromise.setFailure(throwable);
        }
        this.flowController().addFlowControlled(http2Stream, new FlowControlledData(this, http2Stream, byteBuf, n2, bl, channelPromise));
        return channelPromise;
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl, ChannelPromise channelPromise) {
        return this.writeHeaders(channelHandlerContext, n, http2Headers, 0, (short)16, false, n2, bl, channelPromise);
    }

    private static boolean validateHeadersSentState(Http2Stream http2Stream, Http2Headers http2Headers, boolean bl, boolean bl2) {
        boolean bl3;
        boolean bl4 = bl3 = bl && HttpStatusClass.valueOf(http2Headers.status()) == HttpStatusClass.INFORMATIONAL;
        if ((bl3 || !bl2) && http2Stream.isHeadersSent() || http2Stream.isTrailersSent()) {
            throw new IllegalStateException("Stream " + http2Stream.id() + " sent too many headers EOS: " + bl2);
        }
        return bl3;
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2, ChannelPromise channelPromise) {
        try {
            Http2Stream http2Stream = this.connection.stream(n);
            if (http2Stream == null) {
                try {
                    http2Stream = this.connection.local().createStream(n, bl2);
                } catch (Http2Exception http2Exception) {
                    if (this.connection.remote().mayHaveCreatedStream(n)) {
                        channelPromise.tryFailure(new IllegalStateException("Stream no longer exists: " + n, http2Exception));
                        return channelPromise;
                    }
                    throw http2Exception;
                }
            } else {
                switch (3.$SwitchMap$io$netty$handler$codec$http2$Http2Stream$State[http2Stream.state().ordinal()]) {
                    case 3: {
                        http2Stream.open(bl2);
                        break;
                    }
                    case 1: 
                    case 2: {
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Stream " + http2Stream.id() + " in unexpected state " + (Object)((Object)http2Stream.state()));
                    }
                }
            }
            Http2RemoteFlowController http2RemoteFlowController = this.flowController();
            if (!bl2 || !http2RemoteFlowController.hasFlowControlled(http2Stream)) {
                Object object;
                Object object2;
                boolean bl3 = DefaultHttp2ConnectionEncoder.validateHeadersSentState(http2Stream, http2Headers, this.connection.isServer(), bl2);
                if (bl2) {
                    object2 = http2Stream;
                    object = new ChannelFutureListener(this, (Http2Stream)object2){
                        final Http2Stream val$finalStream;
                        final DefaultHttp2ConnectionEncoder this$0;
                        {
                            this.this$0 = defaultHttp2ConnectionEncoder;
                            this.val$finalStream = http2Stream;
                        }

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            DefaultHttp2ConnectionEncoder.access$000(this.this$0).closeStreamLocal(this.val$finalStream, channelFuture);
                        }

                        @Override
                        public void operationComplete(Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    };
                    channelPromise = channelPromise.unvoid().addListener((GenericFutureListener<? extends Future<? super Void>>)object);
                }
                if ((object = (object2 = this.frameWriter.writeHeaders(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2, channelPromise)).cause()) == null) {
                    http2Stream.headersSent(bl3);
                    if (!object2.isSuccess()) {
                        this.notifyLifecycleManagerOnError((ChannelFuture)object2, channelHandlerContext);
                    }
                } else {
                    this.lifecycleManager.onError(channelHandlerContext, true, (Throwable)object);
                }
                return object2;
            }
            http2RemoteFlowController.addFlowControlled(http2Stream, new FlowControlledHeaders(this, http2Stream, http2Headers, n2, s, bl, n3, true, channelPromise));
            return channelPromise;
        } catch (Throwable throwable) {
            this.lifecycleManager.onError(channelHandlerContext, true, throwable);
            channelPromise.tryFailure(throwable);
            return channelPromise;
        }
    }

    @Override
    public ChannelFuture writePriority(ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl, ChannelPromise channelPromise) {
        return this.frameWriter.writePriority(channelHandlerContext, n, n2, s, bl, channelPromise);
    }

    @Override
    public ChannelFuture writeRstStream(ChannelHandlerContext channelHandlerContext, int n, long l, ChannelPromise channelPromise) {
        return this.lifecycleManager.resetStream(channelHandlerContext, n, l, channelPromise);
    }

    @Override
    public ChannelFuture writeSettings(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings, ChannelPromise channelPromise) {
        this.outstandingLocalSettingsQueue.add(http2Settings);
        try {
            Boolean bl = http2Settings.pushEnabled();
            if (bl != null && this.connection.isServer()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server sending SETTINGS frame with ENABLE_PUSH specified", new Object[0]);
            }
        } catch (Throwable throwable) {
            return channelPromise.setFailure(throwable);
        }
        return this.frameWriter.writeSettings(channelHandlerContext, http2Settings, channelPromise);
    }

    @Override
    public ChannelFuture writeSettingsAck(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        return this.frameWriter.writeSettingsAck(channelHandlerContext, channelPromise);
    }

    @Override
    public ChannelFuture writePing(ChannelHandlerContext channelHandlerContext, boolean bl, long l, ChannelPromise channelPromise) {
        return this.frameWriter.writePing(channelHandlerContext, bl, l, channelPromise);
    }

    @Override
    public ChannelFuture writePushPromise(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3, ChannelPromise channelPromise) {
        try {
            if (this.connection.goAwayReceived()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Sending PUSH_PROMISE after GO_AWAY received.", new Object[0]);
            }
            Http2Stream http2Stream = this.requireStream(n);
            this.connection.local().reservePushStream(n2, http2Stream);
            ChannelFuture channelFuture = this.frameWriter.writePushPromise(channelHandlerContext, n, n2, http2Headers, n3, channelPromise);
            Throwable throwable = channelFuture.cause();
            if (throwable == null) {
                http2Stream.pushPromiseSent();
                if (!channelFuture.isSuccess()) {
                    this.notifyLifecycleManagerOnError(channelFuture, channelHandlerContext);
                }
            } else {
                this.lifecycleManager.onError(channelHandlerContext, true, throwable);
            }
            return channelFuture;
        } catch (Throwable throwable) {
            this.lifecycleManager.onError(channelHandlerContext, true, throwable);
            channelPromise.tryFailure(throwable);
            return channelPromise;
        }
    }

    @Override
    public ChannelFuture writeGoAway(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf, ChannelPromise channelPromise) {
        return this.lifecycleManager.goAway(channelHandlerContext, n, l, byteBuf, channelPromise);
    }

    @Override
    public ChannelFuture writeWindowUpdate(ChannelHandlerContext channelHandlerContext, int n, int n2, ChannelPromise channelPromise) {
        return channelPromise.setFailure(new UnsupportedOperationException("Use the Http2[Inbound|Outbound]FlowController objects to control window sizes"));
    }

    @Override
    public ChannelFuture writeFrame(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf, ChannelPromise channelPromise) {
        return this.frameWriter.writeFrame(channelHandlerContext, by, n, http2Flags, byteBuf, channelPromise);
    }

    @Override
    public void close() {
        this.frameWriter.close();
    }

    @Override
    public Http2Settings pollSentSettings() {
        return this.outstandingLocalSettingsQueue.poll();
    }

    @Override
    public Http2FrameWriter.Configuration configuration() {
        return this.frameWriter.configuration();
    }

    private Http2Stream requireStream(int n) {
        Http2Stream http2Stream = this.connection.stream(n);
        if (http2Stream == null) {
            String string = this.connection.streamMayHaveExisted(n) ? "Stream no longer exists: " + n : "Stream does not exist: " + n;
            throw new IllegalArgumentException(string);
        }
        return http2Stream;
    }

    private void notifyLifecycleManagerOnError(ChannelFuture channelFuture, ChannelHandlerContext channelHandlerContext) {
        channelFuture.addListener(new ChannelFutureListener(this, channelHandlerContext){
            final ChannelHandlerContext val$ctx;
            final DefaultHttp2ConnectionEncoder this$0;
            {
                this.this$0 = defaultHttp2ConnectionEncoder;
                this.val$ctx = channelHandlerContext;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Throwable throwable = channelFuture.cause();
                if (throwable != null) {
                    DefaultHttp2ConnectionEncoder.access$000(this.this$0).onError(this.val$ctx, true, throwable);
                }
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }

    static Http2LifecycleManager access$000(DefaultHttp2ConnectionEncoder defaultHttp2ConnectionEncoder) {
        return defaultHttp2ConnectionEncoder.lifecycleManager;
    }

    static Http2Connection access$100(DefaultHttp2ConnectionEncoder defaultHttp2ConnectionEncoder) {
        return defaultHttp2ConnectionEncoder.connection;
    }

    static boolean access$200(Http2Stream http2Stream, Http2Headers http2Headers, boolean bl, boolean bl2) {
        return DefaultHttp2ConnectionEncoder.validateHeadersSentState(http2Stream, http2Headers, bl, bl2);
    }

    static Http2FrameWriter access$300(DefaultHttp2ConnectionEncoder defaultHttp2ConnectionEncoder) {
        return defaultHttp2ConnectionEncoder.frameWriter;
    }

    public abstract class FlowControlledBase
    implements Http2RemoteFlowController.FlowControlled,
    ChannelFutureListener {
        protected final Http2Stream stream;
        protected ChannelPromise promise;
        protected boolean endOfStream;
        protected int padding;
        final DefaultHttp2ConnectionEncoder this$0;

        FlowControlledBase(DefaultHttp2ConnectionEncoder defaultHttp2ConnectionEncoder, Http2Stream http2Stream, int n, boolean bl, ChannelPromise channelPromise) {
            this.this$0 = defaultHttp2ConnectionEncoder;
            if (n < 0) {
                throw new IllegalArgumentException("padding must be >= 0");
            }
            this.padding = n;
            this.endOfStream = bl;
            this.stream = http2Stream;
            this.promise = channelPromise;
        }

        @Override
        public void writeComplete() {
            if (this.endOfStream) {
                DefaultHttp2ConnectionEncoder.access$000(this.this$0).closeStreamLocal(this.stream, this.promise);
            }
        }

        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (!channelFuture.isSuccess()) {
                this.error(this.this$0.flowController().channelHandlerContext(), channelFuture.cause());
            }
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    }

    private final class FlowControlledHeaders
    extends FlowControlledBase {
        private final Http2Headers headers;
        private final int streamDependency;
        private final short weight;
        private final boolean exclusive;
        final DefaultHttp2ConnectionEncoder this$0;

        FlowControlledHeaders(DefaultHttp2ConnectionEncoder defaultHttp2ConnectionEncoder, Http2Stream http2Stream, Http2Headers http2Headers, int n, short s, boolean bl, int n2, boolean bl2, ChannelPromise channelPromise) {
            this.this$0 = defaultHttp2ConnectionEncoder;
            super(defaultHttp2ConnectionEncoder, http2Stream, n2, bl2, channelPromise);
            this.headers = http2Headers;
            this.streamDependency = n;
            this.weight = s;
            this.exclusive = bl;
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public void error(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
            if (channelHandlerContext != null) {
                DefaultHttp2ConnectionEncoder.access$000(this.this$0).onError(channelHandlerContext, true, throwable);
            }
            this.promise.tryFailure(throwable);
        }

        @Override
        public void write(ChannelHandlerContext channelHandlerContext, int n) {
            boolean bl = DefaultHttp2ConnectionEncoder.access$200(this.stream, this.headers, DefaultHttp2ConnectionEncoder.access$100(this.this$0).isServer(), this.endOfStream);
            if (this.promise.isVoid()) {
                this.promise = channelHandlerContext.newPromise();
            }
            this.promise.addListener(this);
            ChannelFuture channelFuture = DefaultHttp2ConnectionEncoder.access$300(this.this$0).writeHeaders(channelHandlerContext, this.stream.id(), this.headers, this.streamDependency, this.weight, this.exclusive, this.padding, this.endOfStream, this.promise);
            Throwable throwable = channelFuture.cause();
            if (throwable == null) {
                this.stream.headersSent(bl);
            }
        }

        @Override
        public boolean merge(ChannelHandlerContext channelHandlerContext, Http2RemoteFlowController.FlowControlled flowControlled) {
            return true;
        }
    }

    private final class FlowControlledData
    extends FlowControlledBase {
        private final CoalescingBufferQueue queue;
        private int dataSize;
        final DefaultHttp2ConnectionEncoder this$0;

        FlowControlledData(DefaultHttp2ConnectionEncoder defaultHttp2ConnectionEncoder, Http2Stream http2Stream, ByteBuf byteBuf, int n, boolean bl, ChannelPromise channelPromise) {
            this.this$0 = defaultHttp2ConnectionEncoder;
            super(defaultHttp2ConnectionEncoder, http2Stream, n, bl, channelPromise);
            this.queue = new CoalescingBufferQueue(channelPromise.channel());
            this.queue.add(byteBuf, channelPromise);
            this.dataSize = this.queue.readableBytes();
        }

        @Override
        public int size() {
            return this.dataSize + this.padding;
        }

        @Override
        public void error(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
            this.queue.releaseAndFailAll(throwable);
            DefaultHttp2ConnectionEncoder.access$000(this.this$0).onError(channelHandlerContext, true, throwable);
        }

        @Override
        public void write(ChannelHandlerContext channelHandlerContext, int n) {
            int n2 = this.queue.readableBytes();
            if (!this.endOfStream) {
                if (n2 == 0) {
                    ChannelPromise channelPromise = channelHandlerContext.newPromise().addListener(this);
                    channelHandlerContext.write(this.queue.remove(0, channelPromise), channelPromise);
                    return;
                }
                if (n == 0) {
                    return;
                }
            }
            int n3 = Math.min(n2, n);
            ChannelPromise channelPromise = channelHandlerContext.newPromise().addListener(this);
            ByteBuf byteBuf = this.queue.remove(n3, channelPromise);
            this.dataSize = this.queue.readableBytes();
            int n4 = Math.min(n - n3, this.padding);
            this.padding -= n4;
            this.this$0.frameWriter().writeData(channelHandlerContext, this.stream.id(), byteBuf, n4, this.endOfStream && this.size() == 0, channelPromise);
        }

        @Override
        public boolean merge(ChannelHandlerContext channelHandlerContext, Http2RemoteFlowController.FlowControlled flowControlled) {
            FlowControlledData flowControlledData;
            if (FlowControlledData.class != flowControlled.getClass() || Integer.MAX_VALUE - (flowControlledData = (FlowControlledData)flowControlled).size() < this.size()) {
                return true;
            }
            flowControlledData.queue.copyTo(this.queue);
            this.dataSize = this.queue.readableBytes();
            this.padding = Math.max(this.padding, flowControlledData.padding);
            this.endOfStream = flowControlledData.endOfStream;
            return false;
        }
    }
}

