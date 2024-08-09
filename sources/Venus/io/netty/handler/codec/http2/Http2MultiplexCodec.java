/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelOutboundInvoker;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelProgressivePromise;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator;
import io.netty.channel.EventLoop;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.VoidChannelPromise;
import io.netty.channel.WriteBufferWaterMark;
import io.netty.handler.codec.http2.DefaultHttp2ResetFrame;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2ConnectionDecoder;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2DataFrame;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Frame;
import io.netty.handler.codec.http2.Http2FrameCodec;
import io.netty.handler.codec.http2.Http2FrameStream;
import io.netty.handler.codec.http2.Http2FrameStreamException;
import io.netty.handler.codec.http2.Http2FrameStreamVisitor;
import io.netty.handler.codec.http2.Http2GoAwayFrame;
import io.netty.handler.codec.http2.Http2HeadersFrame;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.Http2SettingsFrame;
import io.netty.handler.codec.http2.Http2StreamChannel;
import io.netty.handler.codec.http2.Http2StreamChannelId;
import io.netty.handler.codec.http2.Http2StreamFrame;
import io.netty.util.DefaultAttributeMap;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.ThrowableUtil;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Http2MultiplexCodec
extends Http2FrameCodec {
    private static final ChannelFutureListener CHILD_CHANNEL_REGISTRATION_LISTENER;
    private static final ChannelMetadata METADATA;
    private static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION;
    private static final int MIN_HTTP2_FRAME_SIZE = 9;
    private final ChannelHandler inboundStreamHandler;
    private int initialOutboundStreamWindow = 65535;
    private boolean parentReadInProgress;
    private int idCount;
    private DefaultHttp2StreamChannel head;
    private DefaultHttp2StreamChannel tail;
    volatile ChannelHandlerContext ctx;
    static final boolean $assertionsDisabled;

    Http2MultiplexCodec(Http2ConnectionEncoder http2ConnectionEncoder, Http2ConnectionDecoder http2ConnectionDecoder, Http2Settings http2Settings, ChannelHandler channelHandler) {
        super(http2ConnectionEncoder, http2ConnectionDecoder, http2Settings);
        this.inboundStreamHandler = channelHandler;
    }

    private static void registerDone(ChannelFuture channelFuture) {
        if (!channelFuture.isSuccess()) {
            Channel channel = channelFuture.channel();
            if (channel.isRegistered()) {
                channel.close();
            } else {
                channel.unsafe().closeForcibly();
            }
        }
    }

    @Override
    public final void handlerAdded0(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (channelHandlerContext.executor() != channelHandlerContext.channel().eventLoop()) {
            throw new IllegalStateException("EventExecutor must be EventLoop of Channel");
        }
        this.ctx = channelHandlerContext;
    }

    @Override
    public final void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerRemoved0(channelHandlerContext);
        DefaultHttp2StreamChannel defaultHttp2StreamChannel = this.head;
        while (defaultHttp2StreamChannel != null) {
            DefaultHttp2StreamChannel defaultHttp2StreamChannel2 = defaultHttp2StreamChannel;
            defaultHttp2StreamChannel = defaultHttp2StreamChannel2.next;
            defaultHttp2StreamChannel2.next = null;
        }
        this.tail = null;
        this.head = null;
    }

    @Override
    Http2MultiplexCodecStream newStream() {
        return new Http2MultiplexCodecStream();
    }

    @Override
    final void onHttp2Frame(ChannelHandlerContext channelHandlerContext, Http2Frame http2Frame) {
        if (http2Frame instanceof Http2StreamFrame) {
            Http2StreamFrame http2StreamFrame = (Http2StreamFrame)http2Frame;
            this.onHttp2StreamFrame(((Http2MultiplexCodecStream)http2StreamFrame.stream()).channel, http2StreamFrame);
        } else if (http2Frame instanceof Http2GoAwayFrame) {
            this.onHttp2GoAwayFrame(channelHandlerContext, (Http2GoAwayFrame)http2Frame);
            channelHandlerContext.fireChannelRead(http2Frame);
        } else if (http2Frame instanceof Http2SettingsFrame) {
            Http2Settings http2Settings = ((Http2SettingsFrame)http2Frame).settings();
            if (http2Settings.initialWindowSize() != null) {
                this.initialOutboundStreamWindow = http2Settings.initialWindowSize();
            }
            channelHandlerContext.fireChannelRead(http2Frame);
        } else {
            channelHandlerContext.fireChannelRead(http2Frame);
        }
    }

    @Override
    final void onHttp2StreamStateChanged(ChannelHandlerContext channelHandlerContext, Http2FrameStream http2FrameStream) {
        Http2MultiplexCodecStream http2MultiplexCodecStream = (Http2MultiplexCodecStream)http2FrameStream;
        switch (http2FrameStream.state()) {
            case HALF_CLOSED_REMOTE: 
            case OPEN: {
                if (http2MultiplexCodecStream.channel != null) break;
                ChannelFuture channelFuture = channelHandlerContext.channel().eventLoop().register(new DefaultHttp2StreamChannel(this, http2MultiplexCodecStream, false));
                if (channelFuture.isDone()) {
                    Http2MultiplexCodec.registerDone(channelFuture);
                    break;
                }
                channelFuture.addListener(CHILD_CHANNEL_REGISTRATION_LISTENER);
                break;
            }
            case CLOSED: {
                DefaultHttp2StreamChannel defaultHttp2StreamChannel = http2MultiplexCodecStream.channel;
                if (defaultHttp2StreamChannel == null) break;
                defaultHttp2StreamChannel.streamClosed();
                break;
            }
        }
    }

    @Override
    final void onHttp2StreamWritabilityChanged(ChannelHandlerContext channelHandlerContext, Http2FrameStream http2FrameStream, boolean bl) {
        ((Http2MultiplexCodecStream)http2FrameStream).channel.writabilityChanged(bl);
    }

    final Http2StreamChannel newOutboundStream() {
        return new DefaultHttp2StreamChannel(this, this.newStream(), true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    final void onHttp2FrameStreamException(ChannelHandlerContext channelHandlerContext, Http2FrameStreamException http2FrameStreamException) {
        Http2FrameStream http2FrameStream = http2FrameStreamException.stream();
        DefaultHttp2StreamChannel defaultHttp2StreamChannel = ((Http2MultiplexCodecStream)http2FrameStream).channel;
        try {
            defaultHttp2StreamChannel.pipeline().fireExceptionCaught(http2FrameStreamException.getCause());
        } finally {
            defaultHttp2StreamChannel.unsafe().closeForcibly();
        }
    }

    private void onHttp2StreamFrame(DefaultHttp2StreamChannel defaultHttp2StreamChannel, Http2StreamFrame http2StreamFrame) {
        switch (defaultHttp2StreamChannel.fireChildRead(http2StreamFrame)) {
            case READ_PROCESSED_BUT_STOP_READING: {
                defaultHttp2StreamChannel.fireChildReadComplete();
                break;
            }
            case READ_PROCESSED_OK_TO_PROCESS_MORE: {
                this.addChildChannelToReadPendingQueue(defaultHttp2StreamChannel);
                break;
            }
            case READ_IGNORED_CHANNEL_INACTIVE: 
            case READ_QUEUED: {
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    final void addChildChannelToReadPendingQueue(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
        if (!defaultHttp2StreamChannel.fireChannelReadPending) {
            if (!$assertionsDisabled && defaultHttp2StreamChannel.next != null) {
                throw new AssertionError();
            }
            if (this.tail == null) {
                if (!$assertionsDisabled && this.head != null) {
                    throw new AssertionError();
                }
                this.tail = this.head = defaultHttp2StreamChannel;
            } else {
                this.tail.next = defaultHttp2StreamChannel;
                this.tail = defaultHttp2StreamChannel;
            }
            defaultHttp2StreamChannel.fireChannelReadPending = true;
        }
    }

    private void onHttp2GoAwayFrame(ChannelHandlerContext channelHandlerContext, Http2GoAwayFrame http2GoAwayFrame) {
        try {
            this.forEachActiveStream(new Http2FrameStreamVisitor(this, http2GoAwayFrame){
                final Http2GoAwayFrame val$goAwayFrame;
                final Http2MultiplexCodec this$0;
                {
                    this.this$0 = http2MultiplexCodec;
                    this.val$goAwayFrame = http2GoAwayFrame;
                }

                @Override
                public boolean visit(Http2FrameStream http2FrameStream) {
                    int n = http2FrameStream.id();
                    DefaultHttp2StreamChannel defaultHttp2StreamChannel = ((Http2MultiplexCodecStream)http2FrameStream).channel;
                    if (n > this.val$goAwayFrame.lastStreamId() && this.this$0.connection().local().isValidStreamId(n)) {
                        defaultHttp2StreamChannel.pipeline().fireUserEventTriggered(this.val$goAwayFrame.retainedDuplicate());
                    }
                    return false;
                }
            });
        } catch (Http2Exception http2Exception) {
            channelHandlerContext.fireExceptionCaught(http2Exception);
            channelHandlerContext.close();
        }
    }

    @Override
    public final void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.parentReadInProgress = false;
        this.onChannelReadComplete(channelHandlerContext);
        this.channelReadComplete0(channelHandlerContext);
    }

    @Override
    public final void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        this.parentReadInProgress = true;
        super.channelRead(channelHandlerContext, object);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void onChannelReadComplete(ChannelHandlerContext channelHandlerContext) {
        try {
            DefaultHttp2StreamChannel defaultHttp2StreamChannel = this.head;
            while (defaultHttp2StreamChannel != null) {
                DefaultHttp2StreamChannel defaultHttp2StreamChannel2 = defaultHttp2StreamChannel;
                if (defaultHttp2StreamChannel2.fireChannelReadPending) {
                    defaultHttp2StreamChannel2.fireChannelReadPending = false;
                    defaultHttp2StreamChannel2.fireChildReadComplete();
                }
                defaultHttp2StreamChannel2.next = null;
                defaultHttp2StreamChannel = defaultHttp2StreamChannel.next;
            }
        } finally {
            this.head = null;
            this.tail = null;
            this.flush0(channelHandlerContext);
        }
    }

    void flush0(ChannelHandlerContext channelHandlerContext) {
        this.flush(channelHandlerContext);
    }

    boolean onBytesConsumed(ChannelHandlerContext channelHandlerContext, Http2FrameStream http2FrameStream, int n) throws Http2Exception {
        return this.consumeBytes(http2FrameStream.id(), n);
    }

    private boolean initialWritability(Http2FrameCodec.DefaultHttp2FrameStream defaultHttp2FrameStream) {
        return !Http2CodecUtil.isStreamIdValid(defaultHttp2FrameStream.id()) || this.isWritable(defaultHttp2FrameStream);
    }

    @Override
    Http2FrameCodec.DefaultHttp2FrameStream newStream() {
        return this.newStream();
    }

    static void access$000(ChannelFuture channelFuture) {
        Http2MultiplexCodec.registerDone(channelFuture);
    }

    static boolean access$200(Http2MultiplexCodec http2MultiplexCodec, Http2FrameCodec.DefaultHttp2FrameStream defaultHttp2FrameStream) {
        return http2MultiplexCodec.initialWritability(defaultHttp2FrameStream);
    }

    static int access$304(Http2MultiplexCodec http2MultiplexCodec) {
        return ++http2MultiplexCodec.idCount;
    }

    static ChannelMetadata access$400() {
        return METADATA;
    }

    static ChannelHandler access$700(Http2MultiplexCodec http2MultiplexCodec) {
        return http2MultiplexCodec.inboundStreamHandler;
    }

    static boolean access$1500(Http2MultiplexCodec http2MultiplexCodec) {
        return http2MultiplexCodec.parentReadInProgress;
    }

    static ClosedChannelException access$1700() {
        return CLOSED_CHANNEL_EXCEPTION;
    }

    static int access$2400(Http2MultiplexCodec http2MultiplexCodec) {
        return http2MultiplexCodec.initialOutboundStreamWindow;
    }

    static {
        $assertionsDisabled = !Http2MultiplexCodec.class.desiredAssertionStatus();
        CHILD_CHANNEL_REGISTRATION_LISTENER = new ChannelFutureListener(){

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Http2MultiplexCodec.access$000(channelFuture);
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        };
        METADATA = new ChannelMetadata(false, 16);
        CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), DefaultHttp2StreamChannel.Http2ChannelUnsafe.class, "write(...)");
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class DefaultHttp2StreamChannel
    extends DefaultAttributeMap
    implements Http2StreamChannel {
        private final Http2StreamChannelConfig config;
        private final Http2ChannelUnsafe unsafe;
        private final ChannelId channelId;
        private final ChannelPipeline pipeline;
        private final Http2FrameCodec.DefaultHttp2FrameStream stream;
        private final ChannelPromise closePromise;
        private final boolean outbound;
        private volatile boolean registered;
        private volatile boolean writable;
        private boolean outboundClosed;
        private boolean closePending;
        private boolean readInProgress;
        private Queue<Object> inboundBuffer;
        private boolean firstFrameWritten;
        private boolean streamClosedWithoutError;
        private boolean inFireChannelReadComplete;
        boolean fireChannelReadPending;
        DefaultHttp2StreamChannel next;
        static final boolean $assertionsDisabled = !Http2MultiplexCodec.class.desiredAssertionStatus();
        final Http2MultiplexCodec this$0;

        DefaultHttp2StreamChannel(Http2MultiplexCodec http2MultiplexCodec, Http2FrameCodec.DefaultHttp2FrameStream defaultHttp2FrameStream, boolean bl) {
            this.this$0 = http2MultiplexCodec;
            this.config = new Http2StreamChannelConfig(this, this);
            this.unsafe = new Http2ChannelUnsafe(this, null);
            this.stream = defaultHttp2FrameStream;
            this.outbound = bl;
            this.writable = Http2MultiplexCodec.access$200(http2MultiplexCodec, defaultHttp2FrameStream);
            ((Http2MultiplexCodecStream)defaultHttp2FrameStream).channel = this;
            this.pipeline = new DefaultChannelPipeline(this, this, http2MultiplexCodec){
                final Http2MultiplexCodec val$this$0;
                final DefaultHttp2StreamChannel this$1;
                {
                    this.this$1 = defaultHttp2StreamChannel;
                    this.val$this$0 = http2MultiplexCodec;
                    super(channel);
                }

                @Override
                protected void incrementPendingOutboundBytes(long l) {
                }

                @Override
                protected void decrementPendingOutboundBytes(long l) {
                }
            };
            this.closePromise = this.pipeline.newPromise();
            this.channelId = new Http2StreamChannelId(this.parent().id(), Http2MultiplexCodec.access$304(http2MultiplexCodec));
        }

        @Override
        public Http2FrameStream stream() {
            return this.stream;
        }

        void streamClosed() {
            this.streamClosedWithoutError = true;
            if (this.readInProgress) {
                this.unsafe().closeForcibly();
            } else {
                this.closePending = true;
            }
        }

        @Override
        public ChannelMetadata metadata() {
            return Http2MultiplexCodec.access$400();
        }

        @Override
        public ChannelConfig config() {
            return this.config;
        }

        @Override
        public boolean isOpen() {
            return !this.closePromise.isDone();
        }

        @Override
        public boolean isActive() {
            return this.isOpen();
        }

        @Override
        public boolean isWritable() {
            return this.writable;
        }

        @Override
        public ChannelId id() {
            return this.channelId;
        }

        @Override
        public EventLoop eventLoop() {
            return this.parent().eventLoop();
        }

        @Override
        public Channel parent() {
            return this.this$0.ctx.channel();
        }

        @Override
        public boolean isRegistered() {
            return this.registered;
        }

        @Override
        public SocketAddress localAddress() {
            return this.parent().localAddress();
        }

        @Override
        public SocketAddress remoteAddress() {
            return this.parent().remoteAddress();
        }

        @Override
        public ChannelFuture closeFuture() {
            return this.closePromise;
        }

        @Override
        public long bytesBeforeUnwritable() {
            return this.config().getWriteBufferHighWaterMark();
        }

        @Override
        public long bytesBeforeWritable() {
            return 0L;
        }

        @Override
        public Channel.Unsafe unsafe() {
            return this.unsafe;
        }

        @Override
        public ChannelPipeline pipeline() {
            return this.pipeline;
        }

        @Override
        public ByteBufAllocator alloc() {
            return this.config().getAllocator();
        }

        @Override
        public Channel read() {
            this.pipeline().read();
            return this;
        }

        @Override
        public Channel flush() {
            this.pipeline().flush();
            return this;
        }

        @Override
        public ChannelFuture bind(SocketAddress socketAddress) {
            return this.pipeline().bind(socketAddress);
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress) {
            return this.pipeline().connect(socketAddress);
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2) {
            return this.pipeline().connect(socketAddress, socketAddress2);
        }

        @Override
        public ChannelFuture disconnect() {
            return this.pipeline().disconnect();
        }

        @Override
        public ChannelFuture close() {
            return this.pipeline().close();
        }

        @Override
        public ChannelFuture deregister() {
            return this.pipeline().deregister();
        }

        @Override
        public ChannelFuture bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return this.pipeline().bind(socketAddress, channelPromise);
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, ChannelPromise channelPromise) {
            return this.pipeline().connect(socketAddress, channelPromise);
        }

        @Override
        public ChannelFuture connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            return this.pipeline().connect(socketAddress, socketAddress2, channelPromise);
        }

        @Override
        public ChannelFuture disconnect(ChannelPromise channelPromise) {
            return this.pipeline().disconnect(channelPromise);
        }

        @Override
        public ChannelFuture close(ChannelPromise channelPromise) {
            return this.pipeline().close(channelPromise);
        }

        @Override
        public ChannelFuture deregister(ChannelPromise channelPromise) {
            return this.pipeline().deregister(channelPromise);
        }

        @Override
        public ChannelFuture write(Object object) {
            return this.pipeline().write(object);
        }

        @Override
        public ChannelFuture write(Object object, ChannelPromise channelPromise) {
            return this.pipeline().write(object, channelPromise);
        }

        @Override
        public ChannelFuture writeAndFlush(Object object, ChannelPromise channelPromise) {
            return this.pipeline().writeAndFlush(object, channelPromise);
        }

        @Override
        public ChannelFuture writeAndFlush(Object object) {
            return this.pipeline().writeAndFlush(object);
        }

        @Override
        public ChannelPromise newPromise() {
            return this.pipeline().newPromise();
        }

        @Override
        public ChannelProgressivePromise newProgressivePromise() {
            return this.pipeline().newProgressivePromise();
        }

        @Override
        public ChannelFuture newSucceededFuture() {
            return this.pipeline().newSucceededFuture();
        }

        @Override
        public ChannelFuture newFailedFuture(Throwable throwable) {
            return this.pipeline().newFailedFuture(throwable);
        }

        @Override
        public ChannelPromise voidPromise() {
            return this.pipeline().voidPromise();
        }

        public int hashCode() {
            return this.id().hashCode();
        }

        public boolean equals(Object object) {
            return this == object;
        }

        @Override
        public int compareTo(Channel channel) {
            if (this == channel) {
                return 1;
            }
            return this.id().compareTo(channel.id());
        }

        public String toString() {
            return this.parent().toString() + "(H2 - " + this.stream + ')';
        }

        void writabilityChanged(boolean bl) {
            if (!$assertionsDisabled && !this.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            if (bl != this.writable && this.isActive()) {
                this.writable = bl;
                this.pipeline().fireChannelWritabilityChanged();
            }
        }

        ReadState fireChildRead(Http2Frame http2Frame) {
            if (!$assertionsDisabled && !this.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            if (!this.isActive()) {
                ReferenceCountUtil.release(http2Frame);
                return ReadState.READ_IGNORED_CHANNEL_INACTIVE;
            }
            if (this.readInProgress && (this.inboundBuffer == null || this.inboundBuffer.isEmpty())) {
                RecvByteBufAllocator.ExtendedHandle extendedHandle = this.unsafe.recvBufAllocHandle();
                this.unsafe.doRead0(http2Frame, extendedHandle);
                return extendedHandle.continueReading() ? ReadState.READ_PROCESSED_OK_TO_PROCESS_MORE : ReadState.READ_PROCESSED_BUT_STOP_READING;
            }
            if (this.inboundBuffer == null) {
                this.inboundBuffer = new ArrayDeque<Object>(4);
            }
            this.inboundBuffer.add(http2Frame);
            return ReadState.READ_QUEUED;
        }

        void fireChildReadComplete() {
            if (!$assertionsDisabled && !this.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            try {
                if (this.readInProgress) {
                    this.inFireChannelReadComplete = true;
                    this.readInProgress = false;
                    this.unsafe().recvBufAllocHandle().readComplete();
                    this.pipeline().fireChannelReadComplete();
                }
            } finally {
                this.inFireChannelReadComplete = false;
            }
        }

        @Override
        public ChannelOutboundInvoker flush() {
            return this.flush();
        }

        @Override
        public ChannelOutboundInvoker read() {
            return this.read();
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Channel)object);
        }

        static boolean access$500(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.registered;
        }

        static boolean access$502(DefaultHttp2StreamChannel defaultHttp2StreamChannel, boolean bl) {
            defaultHttp2StreamChannel.registered = bl;
            return defaultHttp2StreamChannel.registered;
        }

        static boolean access$600(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.outbound;
        }

        static ChannelPromise access$800(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.closePromise;
        }

        static boolean access$902(DefaultHttp2StreamChannel defaultHttp2StreamChannel, boolean bl) {
            defaultHttp2StreamChannel.closePending = bl;
            return defaultHttp2StreamChannel.closePending;
        }

        static boolean access$1000(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.streamClosedWithoutError;
        }

        static Queue access$1100(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.inboundBuffer;
        }

        static boolean access$1202(DefaultHttp2StreamChannel defaultHttp2StreamChannel, boolean bl) {
            defaultHttp2StreamChannel.outboundClosed = bl;
            return defaultHttp2StreamChannel.outboundClosed;
        }

        static boolean access$1300(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.readInProgress;
        }

        static boolean access$1302(DefaultHttp2StreamChannel defaultHttp2StreamChannel, boolean bl) {
            defaultHttp2StreamChannel.readInProgress = bl;
            return defaultHttp2StreamChannel.readInProgress;
        }

        static boolean access$900(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.closePending;
        }

        static Http2ChannelUnsafe access$1400(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.unsafe;
        }

        static Http2FrameCodec.DefaultHttp2FrameStream access$1600(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.stream;
        }

        static boolean access$1200(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.outboundClosed;
        }

        static boolean access$1800(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.firstFrameWritten;
        }

        static boolean access$1802(DefaultHttp2StreamChannel defaultHttp2StreamChannel, boolean bl) {
            defaultHttp2StreamChannel.firstFrameWritten = bl;
            return defaultHttp2StreamChannel.firstFrameWritten;
        }

        static Http2StreamChannelConfig access$2100(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.config;
        }

        static boolean access$2200(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
            return defaultHttp2StreamChannel.inFireChannelReadComplete;
        }

        private final class Http2StreamChannelConfig
        extends DefaultChannelConfig {
            final DefaultHttp2StreamChannel this$1;

            Http2StreamChannelConfig(DefaultHttp2StreamChannel defaultHttp2StreamChannel, Channel channel) {
                this.this$1 = defaultHttp2StreamChannel;
                super(channel);
                this.setRecvByteBufAllocator(new Http2StreamChannelRecvByteBufAllocator(null));
            }

            @Override
            public int getWriteBufferHighWaterMark() {
                return Math.min(this.this$1.parent().config().getWriteBufferHighWaterMark(), Http2MultiplexCodec.access$2400(this.this$1.this$0));
            }

            @Override
            public int getWriteBufferLowWaterMark() {
                return Math.min(this.this$1.parent().config().getWriteBufferLowWaterMark(), Http2MultiplexCodec.access$2400(this.this$1.this$0));
            }

            @Override
            public MessageSizeEstimator getMessageSizeEstimator() {
                return FlowControlledFrameSizeEstimator.INSTANCE;
            }

            @Override
            public WriteBufferWaterMark getWriteBufferWaterMark() {
                int n = this.getWriteBufferHighWaterMark();
                return new WriteBufferWaterMark(n, n);
            }

            @Override
            public ChannelConfig setMessageSizeEstimator(MessageSizeEstimator messageSizeEstimator) {
                throw new UnsupportedOperationException();
            }

            @Override
            @Deprecated
            public ChannelConfig setWriteBufferHighWaterMark(int n) {
                throw new UnsupportedOperationException();
            }

            @Override
            @Deprecated
            public ChannelConfig setWriteBufferLowWaterMark(int n) {
                throw new UnsupportedOperationException();
            }

            @Override
            public ChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
                throw new UnsupportedOperationException();
            }

            @Override
            public ChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator recvByteBufAllocator) {
                if (!(recvByteBufAllocator.newHandle() instanceof RecvByteBufAllocator.ExtendedHandle)) {
                    throw new IllegalArgumentException("allocator.newHandle() must return an object of type: " + RecvByteBufAllocator.ExtendedHandle.class);
                }
                super.setRecvByteBufAllocator(recvByteBufAllocator);
                return this;
            }
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private final class Http2ChannelUnsafe
        implements Channel.Unsafe {
            private final VoidChannelPromise unsafeVoidPromise;
            private RecvByteBufAllocator.ExtendedHandle recvHandle;
            private boolean writeDoneAndNoFlush;
            private boolean closeInitiated;
            final DefaultHttp2StreamChannel this$1;

            private Http2ChannelUnsafe(DefaultHttp2StreamChannel defaultHttp2StreamChannel) {
                this.this$1 = defaultHttp2StreamChannel;
                this.unsafeVoidPromise = new VoidChannelPromise(this.this$1, false);
            }

            @Override
            public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
                if (!channelPromise.setUncancellable()) {
                    return;
                }
                channelPromise.setFailure(new UnsupportedOperationException());
            }

            @Override
            public RecvByteBufAllocator.ExtendedHandle recvBufAllocHandle() {
                if (this.recvHandle == null) {
                    this.recvHandle = (RecvByteBufAllocator.ExtendedHandle)this.this$1.config().getRecvByteBufAllocator().newHandle();
                }
                return this.recvHandle;
            }

            @Override
            public SocketAddress localAddress() {
                return this.this$1.parent().unsafe().localAddress();
            }

            @Override
            public SocketAddress remoteAddress() {
                return this.this$1.parent().unsafe().remoteAddress();
            }

            @Override
            public void register(EventLoop eventLoop, ChannelPromise channelPromise) {
                if (!channelPromise.setUncancellable()) {
                    return;
                }
                if (DefaultHttp2StreamChannel.access$500(this.this$1)) {
                    throw new UnsupportedOperationException("Re-register is not supported");
                }
                DefaultHttp2StreamChannel.access$502(this.this$1, true);
                if (!DefaultHttp2StreamChannel.access$600(this.this$1)) {
                    this.this$1.pipeline().addLast(Http2MultiplexCodec.access$700(this.this$1.this$0));
                }
                channelPromise.setSuccess();
                this.this$1.pipeline().fireChannelRegistered();
                if (this.this$1.isActive()) {
                    this.this$1.pipeline().fireChannelActive();
                }
            }

            @Override
            public void bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
                if (!channelPromise.setUncancellable()) {
                    return;
                }
                channelPromise.setFailure(new UnsupportedOperationException());
            }

            @Override
            public void disconnect(ChannelPromise channelPromise) {
                this.close(channelPromise);
            }

            @Override
            public void close(ChannelPromise channelPromise) {
                Object object;
                if (!channelPromise.setUncancellable()) {
                    return;
                }
                if (this.closeInitiated) {
                    if (DefaultHttp2StreamChannel.access$800(this.this$1).isDone()) {
                        channelPromise.setSuccess();
                    } else if (!(channelPromise instanceof VoidChannelPromise)) {
                        DefaultHttp2StreamChannel.access$800(this.this$1).addListener(new ChannelFutureListener(this, channelPromise){
                            final ChannelPromise val$promise;
                            final Http2ChannelUnsafe this$2;
                            {
                                this.this$2 = http2ChannelUnsafe;
                                this.val$promise = channelPromise;
                            }

                            @Override
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                this.val$promise.setSuccess();
                            }

                            @Override
                            public void operationComplete(Future future) throws Exception {
                                this.operationComplete((ChannelFuture)future);
                            }
                        });
                    }
                    return;
                }
                this.closeInitiated = true;
                DefaultHttp2StreamChannel.access$902(this.this$1, false);
                this.this$1.fireChannelReadPending = false;
                if (this.this$1.parent().isActive() && !DefaultHttp2StreamChannel.access$1000(this.this$1) && Http2CodecUtil.isStreamIdValid(this.this$1.stream().id())) {
                    object = new DefaultHttp2ResetFrame(Http2Error.CANCEL).stream(this.this$1.stream());
                    this.write(object, this.this$1.unsafe().voidPromise());
                    this.flush();
                }
                if (DefaultHttp2StreamChannel.access$1100(this.this$1) != null) {
                    while ((object = DefaultHttp2StreamChannel.access$1100(this.this$1).poll()) != null) {
                        ReferenceCountUtil.release(object);
                    }
                }
                DefaultHttp2StreamChannel.access$1202(this.this$1, true);
                DefaultHttp2StreamChannel.access$800(this.this$1).setSuccess();
                channelPromise.setSuccess();
                this.this$1.pipeline().fireChannelInactive();
                if (this.this$1.isRegistered()) {
                    this.deregister(this.this$1.unsafe().voidPromise());
                }
            }

            @Override
            public void closeForcibly() {
                this.close(this.this$1.unsafe().voidPromise());
            }

            @Override
            public void deregister(ChannelPromise channelPromise) {
                if (!channelPromise.setUncancellable()) {
                    return;
                }
                if (DefaultHttp2StreamChannel.access$500(this.this$1)) {
                    DefaultHttp2StreamChannel.access$502(this.this$1, true);
                    channelPromise.setSuccess();
                    this.this$1.pipeline().fireChannelUnregistered();
                } else {
                    channelPromise.setFailure(new IllegalStateException("Not registered"));
                }
            }

            @Override
            public void beginRead() {
                boolean bl;
                if (DefaultHttp2StreamChannel.access$1300(this.this$1) || !this.this$1.isActive()) {
                    return;
                }
                DefaultHttp2StreamChannel.access$1302(this.this$1, true);
                RecvByteBufAllocator.Handle handle = this.this$1.unsafe().recvBufAllocHandle();
                handle.reset(this.this$1.config());
                if (DefaultHttp2StreamChannel.access$1100(this.this$1) == null || DefaultHttp2StreamChannel.access$1100(this.this$1).isEmpty()) {
                    if (DefaultHttp2StreamChannel.access$900(this.this$1)) {
                        DefaultHttp2StreamChannel.access$1400(this.this$1).closeForcibly();
                    }
                    return;
                }
                do {
                    Object e;
                    if ((e = DefaultHttp2StreamChannel.access$1100(this.this$1).poll()) == null) {
                        bl = false;
                        break;
                    }
                    this.doRead0((Http2Frame)e, handle);
                } while (bl = handle.continueReading());
                if (bl && Http2MultiplexCodec.access$1500(this.this$1.this$0)) {
                    this.this$1.this$0.addChildChannelToReadPendingQueue(this.this$1);
                } else {
                    DefaultHttp2StreamChannel.access$1302(this.this$1, false);
                    handle.readComplete();
                    this.this$1.pipeline().fireChannelReadComplete();
                    this.flush();
                    if (DefaultHttp2StreamChannel.access$900(this.this$1)) {
                        DefaultHttp2StreamChannel.access$1400(this.this$1).closeForcibly();
                    }
                }
            }

            void doRead0(Http2Frame http2Frame, RecvByteBufAllocator.Handle handle) {
                int n = 0;
                if (http2Frame instanceof Http2DataFrame) {
                    n = ((Http2DataFrame)http2Frame).initialFlowControlledBytes();
                    handle.lastBytesRead(n);
                } else {
                    handle.lastBytesRead(9);
                }
                handle.incMessagesRead(1);
                this.this$1.pipeline().fireChannelRead(http2Frame);
                if (n != 0) {
                    try {
                        this.writeDoneAndNoFlush |= this.this$1.this$0.onBytesConsumed(this.this$1.this$0.ctx, DefaultHttp2StreamChannel.access$1600(this.this$1), n);
                    } catch (Http2Exception http2Exception) {
                        this.this$1.pipeline().fireExceptionCaught(http2Exception);
                    }
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void write(Object object, ChannelPromise channelPromise) {
                if (!channelPromise.setUncancellable()) {
                    ReferenceCountUtil.release(object);
                    return;
                }
                if (!this.this$1.isActive() || DefaultHttp2StreamChannel.access$1200(this.this$1) && (object instanceof Http2HeadersFrame || object instanceof Http2DataFrame)) {
                    ReferenceCountUtil.release(object);
                    channelPromise.setFailure(Http2MultiplexCodec.access$1700());
                    return;
                }
                try {
                    Object object2;
                    if (object instanceof Http2StreamFrame) {
                        object2 = this.validateStreamFrame((Http2StreamFrame)object).stream(this.this$1.stream());
                        if (!DefaultHttp2StreamChannel.access$1800(this.this$1) && !Http2CodecUtil.isStreamIdValid(this.this$1.stream().id())) {
                            if (!(object2 instanceof Http2HeadersFrame)) {
                                ReferenceCountUtil.release(object2);
                                channelPromise.setFailure(new IllegalArgumentException("The first frame must be a headers frame. Was: " + object2.name()));
                                return;
                            }
                            DefaultHttp2StreamChannel.access$1802(this.this$1, true);
                            ChannelFuture channelFuture = this.write0(object2);
                            if (channelFuture.isDone()) {
                                this.firstWriteComplete(channelFuture, channelPromise);
                            } else {
                                channelFuture.addListener(new ChannelFutureListener(this, channelPromise){
                                    final ChannelPromise val$promise;
                                    final Http2ChannelUnsafe this$2;
                                    {
                                        this.this$2 = http2ChannelUnsafe;
                                        this.val$promise = channelPromise;
                                    }

                                    @Override
                                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                        Http2ChannelUnsafe.access$1900(this.this$2, channelFuture, this.val$promise);
                                    }

                                    @Override
                                    public void operationComplete(Future future) throws Exception {
                                        this.operationComplete((ChannelFuture)future);
                                    }
                                });
                            }
                            return;
                        }
                    } else {
                        String string = object.toString();
                        ReferenceCountUtil.release(object);
                        channelPromise.setFailure(new IllegalArgumentException("Message must be an " + StringUtil.simpleClassName(Http2StreamFrame.class) + ": " + string));
                        return;
                    }
                    object2 = this.write0(object);
                    if (object2.isDone()) {
                        this.writeComplete((ChannelFuture)object2, channelPromise);
                    } else {
                        object2.addListener(new ChannelFutureListener(this, channelPromise){
                            final ChannelPromise val$promise;
                            final Http2ChannelUnsafe this$2;
                            {
                                this.this$2 = http2ChannelUnsafe;
                                this.val$promise = channelPromise;
                            }

                            @Override
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                Http2ChannelUnsafe.access$2000(this.this$2, channelFuture, this.val$promise);
                            }

                            @Override
                            public void operationComplete(Future future) throws Exception {
                                this.operationComplete((ChannelFuture)future);
                            }
                        });
                    }
                } catch (Throwable throwable) {
                    channelPromise.tryFailure(throwable);
                } finally {
                    this.writeDoneAndNoFlush = true;
                }
            }

            private void firstWriteComplete(ChannelFuture channelFuture, ChannelPromise channelPromise) {
                Throwable throwable = channelFuture.cause();
                if (throwable == null) {
                    this.this$1.writabilityChanged(this.this$1.this$0.isWritable(DefaultHttp2StreamChannel.access$1600(this.this$1)));
                    channelPromise.setSuccess();
                } else {
                    channelPromise.setFailure(this.wrapStreamClosedError(throwable));
                    this.closeForcibly();
                }
            }

            private void writeComplete(ChannelFuture channelFuture, ChannelPromise channelPromise) {
                Throwable throwable = channelFuture.cause();
                if (throwable == null) {
                    channelPromise.setSuccess();
                } else {
                    Throwable throwable2 = this.wrapStreamClosedError(throwable);
                    channelPromise.setFailure(throwable2);
                    if (throwable2 instanceof ClosedChannelException) {
                        if (DefaultHttp2StreamChannel.access$2100(this.this$1).isAutoClose()) {
                            this.closeForcibly();
                        } else {
                            DefaultHttp2StreamChannel.access$1202(this.this$1, true);
                        }
                    }
                }
            }

            private Throwable wrapStreamClosedError(Throwable throwable) {
                if (throwable instanceof Http2Exception && ((Http2Exception)throwable).error() == Http2Error.STREAM_CLOSED) {
                    return new ClosedChannelException().initCause(throwable);
                }
                return throwable;
            }

            private Http2StreamFrame validateStreamFrame(Http2StreamFrame http2StreamFrame) {
                if (http2StreamFrame.stream() != null && http2StreamFrame.stream() != DefaultHttp2StreamChannel.access$1600(this.this$1)) {
                    String string = http2StreamFrame.toString();
                    ReferenceCountUtil.release(http2StreamFrame);
                    throw new IllegalArgumentException("Stream " + http2StreamFrame.stream() + " must not be set on the frame: " + string);
                }
                return http2StreamFrame;
            }

            private ChannelFuture write0(Object object) {
                ChannelPromise channelPromise = this.this$1.this$0.ctx.newPromise();
                this.this$1.this$0.write(this.this$1.this$0.ctx, object, channelPromise);
                return channelPromise;
            }

            @Override
            public void flush() {
                if (!this.writeDoneAndNoFlush) {
                    return;
                }
                try {
                    if (!DefaultHttp2StreamChannel.access$2200(this.this$1)) {
                        this.this$1.this$0.flush0(this.this$1.this$0.ctx);
                    }
                } finally {
                    this.writeDoneAndNoFlush = false;
                }
            }

            @Override
            public ChannelPromise voidPromise() {
                return this.unsafeVoidPromise;
            }

            @Override
            public ChannelOutboundBuffer outboundBuffer() {
                return null;
            }

            @Override
            public RecvByteBufAllocator.Handle recvBufAllocHandle() {
                return this.recvBufAllocHandle();
            }

            Http2ChannelUnsafe(DefaultHttp2StreamChannel defaultHttp2StreamChannel, 1 var2_2) {
                this(defaultHttp2StreamChannel);
            }

            static void access$1900(Http2ChannelUnsafe http2ChannelUnsafe, ChannelFuture channelFuture, ChannelPromise channelPromise) {
                http2ChannelUnsafe.firstWriteComplete(channelFuture, channelPromise);
            }

            static void access$2000(Http2ChannelUnsafe http2ChannelUnsafe, ChannelFuture channelFuture, ChannelPromise channelPromise) {
                http2ChannelUnsafe.writeComplete(channelFuture, channelPromise);
            }
        }
    }

    private static enum ReadState {
        READ_QUEUED,
        READ_IGNORED_CHANNEL_INACTIVE,
        READ_PROCESSED_BUT_STOP_READING,
        READ_PROCESSED_OK_TO_PROCESS_MORE;

    }

    static class Http2MultiplexCodecStream
    extends Http2FrameCodec.DefaultHttp2FrameStream {
        DefaultHttp2StreamChannel channel;

        Http2MultiplexCodecStream() {
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class Http2StreamChannelRecvByteBufAllocator
    extends DefaultMaxMessagesRecvByteBufAllocator {
        private Http2StreamChannelRecvByteBufAllocator() {
        }

        @Override
        public DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle newHandle() {
            return new DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle(this){
                final Http2StreamChannelRecvByteBufAllocator this$0;
                {
                    this.this$0 = http2StreamChannelRecvByteBufAllocator;
                    super(http2StreamChannelRecvByteBufAllocator);
                }

                @Override
                public int guess() {
                    return 1;
                }
            };
        }

        @Override
        public RecvByteBufAllocator.Handle newHandle() {
            return this.newHandle();
        }

        Http2StreamChannelRecvByteBufAllocator(1 var1_1) {
            this();
        }
    }

    private static final class FlowControlledFrameSizeEstimator
    implements MessageSizeEstimator {
        static final FlowControlledFrameSizeEstimator INSTANCE = new FlowControlledFrameSizeEstimator();
        static final MessageSizeEstimator.Handle HANDLE_INSTANCE = new MessageSizeEstimator.Handle(){

            @Override
            public int size(Object object) {
                return object instanceof Http2DataFrame ? (int)Math.min(Integer.MAX_VALUE, (long)((Http2DataFrame)object).initialFlowControlledBytes() + 9L) : 9;
            }
        };

        private FlowControlledFrameSizeEstimator() {
        }

        @Override
        public MessageSizeEstimator.Handle newHandle() {
            return HANDLE_INSTANCE;
        }
    }
}

