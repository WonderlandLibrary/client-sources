/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.embedded;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.DefaultChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannelId;
import io.netty.channel.embedded.EmbeddedEventLoop;
import io.netty.channel.embedded.EmbeddedSocketAddress;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.RecyclableArrayList;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

public class EmbeddedChannel
extends AbstractChannel {
    private static final SocketAddress LOCAL_ADDRESS;
    private static final SocketAddress REMOTE_ADDRESS;
    private static final ChannelHandler[] EMPTY_HANDLERS;
    private static final InternalLogger logger;
    private static final ChannelMetadata METADATA_NO_DISCONNECT;
    private static final ChannelMetadata METADATA_DISCONNECT;
    private final EmbeddedEventLoop loop = new EmbeddedEventLoop();
    private final ChannelFutureListener recordExceptionListener = new ChannelFutureListener(this){
        final EmbeddedChannel this$0;
        {
            this.this$0 = embeddedChannel;
        }

        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            EmbeddedChannel.access$000(this.this$0, channelFuture);
        }

        @Override
        public void operationComplete(Future future) throws Exception {
            this.operationComplete((ChannelFuture)future);
        }
    };
    private final ChannelMetadata metadata;
    private final ChannelConfig config;
    private Queue<Object> inboundMessages;
    private Queue<Object> outboundMessages;
    private Throwable lastException;
    private State state;
    static final boolean $assertionsDisabled;

    public EmbeddedChannel() {
        this(EMPTY_HANDLERS);
    }

    public EmbeddedChannel(ChannelId channelId) {
        this(channelId, EMPTY_HANDLERS);
    }

    public EmbeddedChannel(ChannelHandler ... channelHandlerArray) {
        this(EmbeddedChannelId.INSTANCE, channelHandlerArray);
    }

    public EmbeddedChannel(boolean bl, ChannelHandler ... channelHandlerArray) {
        this(EmbeddedChannelId.INSTANCE, bl, channelHandlerArray);
    }

    public EmbeddedChannel(boolean bl, boolean bl2, ChannelHandler ... channelHandlerArray) {
        this(EmbeddedChannelId.INSTANCE, bl, bl2, channelHandlerArray);
    }

    public EmbeddedChannel(ChannelId channelId, ChannelHandler ... channelHandlerArray) {
        this(channelId, false, channelHandlerArray);
    }

    public EmbeddedChannel(ChannelId channelId, boolean bl, ChannelHandler ... channelHandlerArray) {
        this(channelId, true, bl, channelHandlerArray);
    }

    public EmbeddedChannel(ChannelId channelId, boolean bl, boolean bl2, ChannelHandler ... channelHandlerArray) {
        super(null, channelId);
        this.metadata = EmbeddedChannel.metadata(bl2);
        this.config = new DefaultChannelConfig(this);
        this.setup(bl, channelHandlerArray);
    }

    public EmbeddedChannel(ChannelId channelId, boolean bl, ChannelConfig channelConfig, ChannelHandler ... channelHandlerArray) {
        super(null, channelId);
        this.metadata = EmbeddedChannel.metadata(bl);
        this.config = ObjectUtil.checkNotNull(channelConfig, "config");
        this.setup(true, channelHandlerArray);
    }

    private static ChannelMetadata metadata(boolean bl) {
        return bl ? METADATA_DISCONNECT : METADATA_NO_DISCONNECT;
    }

    private void setup(boolean bl, ChannelHandler ... channelHandlerArray) {
        ObjectUtil.checkNotNull(channelHandlerArray, "handlers");
        ChannelPipeline channelPipeline = this.pipeline();
        channelPipeline.addLast(new ChannelInitializer<Channel>(this, channelHandlerArray){
            final ChannelHandler[] val$handlers;
            final EmbeddedChannel this$0;
            {
                this.this$0 = embeddedChannel;
                this.val$handlers = channelHandlerArray;
            }

            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline channelPipeline = channel.pipeline();
                for (ChannelHandler channelHandler : this.val$handlers) {
                    if (channelHandler == null) break;
                    channelPipeline.addLast(channelHandler);
                }
            }
        });
        if (bl) {
            ChannelFuture channelFuture = this.loop.register(this);
            if (!$assertionsDisabled && !channelFuture.isDone()) {
                throw new AssertionError();
            }
        }
    }

    public void register() throws Exception {
        ChannelFuture channelFuture = this.loop.register(this);
        if (!$assertionsDisabled && !channelFuture.isDone()) {
            throw new AssertionError();
        }
        Throwable throwable = channelFuture.cause();
        if (throwable != null) {
            PlatformDependent.throwException(throwable);
        }
    }

    @Override
    protected final DefaultChannelPipeline newChannelPipeline() {
        return new EmbeddedChannelPipeline(this, this);
    }

    @Override
    public ChannelMetadata metadata() {
        return this.metadata;
    }

    @Override
    public ChannelConfig config() {
        return this.config;
    }

    @Override
    public boolean isOpen() {
        return this.state != State.CLOSED;
    }

    @Override
    public boolean isActive() {
        return this.state == State.ACTIVE;
    }

    public Queue<Object> inboundMessages() {
        if (this.inboundMessages == null) {
            this.inboundMessages = new ArrayDeque<Object>();
        }
        return this.inboundMessages;
    }

    @Deprecated
    public Queue<Object> lastInboundBuffer() {
        return this.inboundMessages();
    }

    public Queue<Object> outboundMessages() {
        if (this.outboundMessages == null) {
            this.outboundMessages = new ArrayDeque<Object>();
        }
        return this.outboundMessages;
    }

    @Deprecated
    public Queue<Object> lastOutboundBuffer() {
        return this.outboundMessages();
    }

    public <T> T readInbound() {
        Object object = EmbeddedChannel.poll(this.inboundMessages);
        if (object != null) {
            ReferenceCountUtil.touch(object, "Caller of readInbound() will handle the message from this point");
        }
        return (T)object;
    }

    public <T> T readOutbound() {
        Object object = EmbeddedChannel.poll(this.outboundMessages);
        if (object != null) {
            ReferenceCountUtil.touch(object, "Caller of readOutbound() will handle the message from this point.");
        }
        return (T)object;
    }

    public boolean writeInbound(Object ... objectArray) {
        this.ensureOpen();
        if (objectArray.length == 0) {
            return EmbeddedChannel.isNotEmpty(this.inboundMessages);
        }
        ChannelPipeline channelPipeline = this.pipeline();
        for (Object object : objectArray) {
            channelPipeline.fireChannelRead(object);
        }
        this.flushInbound(false, this.voidPromise());
        return EmbeddedChannel.isNotEmpty(this.inboundMessages);
    }

    public ChannelFuture writeOneInbound(Object object) {
        return this.writeOneInbound(object, this.newPromise());
    }

    public ChannelFuture writeOneInbound(Object object, ChannelPromise channelPromise) {
        if (this.checkOpen(true)) {
            this.pipeline().fireChannelRead(object);
        }
        return this.checkException(channelPromise);
    }

    public EmbeddedChannel flushInbound() {
        this.flushInbound(true, this.voidPromise());
        return this;
    }

    private ChannelFuture flushInbound(boolean bl, ChannelPromise channelPromise) {
        if (this.checkOpen(bl)) {
            this.pipeline().fireChannelReadComplete();
            this.runPendingTasks();
        }
        return this.checkException(channelPromise);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean writeOutbound(Object ... objectArray) {
        this.ensureOpen();
        if (objectArray.length == 0) {
            return EmbeddedChannel.isNotEmpty(this.outboundMessages);
        }
        RecyclableArrayList recyclableArrayList = RecyclableArrayList.newInstance(objectArray.length);
        try {
            Object object;
            Object[] objectArray2 = objectArray;
            int n = objectArray2.length;
            for (int i = 0; i < n && (object = objectArray2[i]) != null; ++i) {
                recyclableArrayList.add(this.write(object));
            }
            this.flushOutbound0();
            int n2 = recyclableArrayList.size();
            for (n = 0; n < n2; ++n) {
                ChannelFuture channelFuture = (ChannelFuture)recyclableArrayList.get(n);
                if (channelFuture.isDone()) {
                    this.recordException(channelFuture);
                    continue;
                }
                channelFuture.addListener(this.recordExceptionListener);
            }
            this.checkException();
            n = EmbeddedChannel.isNotEmpty(this.outboundMessages) ? 1 : 0;
            return n != 0;
        } finally {
            recyclableArrayList.recycle();
        }
    }

    public ChannelFuture writeOneOutbound(Object object) {
        return this.writeOneOutbound(object, this.newPromise());
    }

    public ChannelFuture writeOneOutbound(Object object, ChannelPromise channelPromise) {
        if (this.checkOpen(true)) {
            return this.write(object, channelPromise);
        }
        return this.checkException(channelPromise);
    }

    public EmbeddedChannel flushOutbound() {
        if (this.checkOpen(true)) {
            this.flushOutbound0();
        }
        this.checkException(this.voidPromise());
        return this;
    }

    private void flushOutbound0() {
        this.runPendingTasks();
        this.flush();
    }

    public boolean finish() {
        return this.finish(false);
    }

    public boolean finishAndReleaseAll() {
        return this.finish(true);
    }

    private boolean finish(boolean bl) {
        this.close();
        try {
            this.checkException();
            boolean bl2 = EmbeddedChannel.isNotEmpty(this.inboundMessages) || EmbeddedChannel.isNotEmpty(this.outboundMessages);
            return bl2;
        } finally {
            if (bl) {
                EmbeddedChannel.releaseAll(this.inboundMessages);
                EmbeddedChannel.releaseAll(this.outboundMessages);
            }
        }
    }

    public boolean releaseInbound() {
        return EmbeddedChannel.releaseAll(this.inboundMessages);
    }

    public boolean releaseOutbound() {
        return EmbeddedChannel.releaseAll(this.outboundMessages);
    }

    private static boolean releaseAll(Queue<Object> queue) {
        if (EmbeddedChannel.isNotEmpty(queue)) {
            Object object;
            while ((object = queue.poll()) != null) {
                ReferenceCountUtil.release(object);
            }
            return false;
        }
        return true;
    }

    private void finishPendingTasks(boolean bl) {
        this.runPendingTasks();
        if (bl) {
            this.loop.cancelScheduledTasks();
        }
    }

    @Override
    public final ChannelFuture close() {
        return this.close(this.newPromise());
    }

    @Override
    public final ChannelFuture disconnect() {
        return this.disconnect(this.newPromise());
    }

    @Override
    public final ChannelFuture close(ChannelPromise channelPromise) {
        this.runPendingTasks();
        ChannelFuture channelFuture = super.close(channelPromise);
        this.finishPendingTasks(true);
        return channelFuture;
    }

    @Override
    public final ChannelFuture disconnect(ChannelPromise channelPromise) {
        ChannelFuture channelFuture = super.disconnect(channelPromise);
        this.finishPendingTasks(!this.metadata.hasDisconnect());
        return channelFuture;
    }

    private static boolean isNotEmpty(Queue<Object> queue) {
        return queue != null && !queue.isEmpty();
    }

    private static Object poll(Queue<Object> queue) {
        return queue != null ? queue.poll() : null;
    }

    public void runPendingTasks() {
        try {
            this.loop.runTasks();
        } catch (Exception exception) {
            this.recordException(exception);
        }
        try {
            this.loop.runScheduledTasks();
        } catch (Exception exception) {
            this.recordException(exception);
        }
    }

    public long runScheduledPendingTasks() {
        try {
            return this.loop.runScheduledTasks();
        } catch (Exception exception) {
            this.recordException(exception);
            return this.loop.nextScheduledTask();
        }
    }

    private void recordException(ChannelFuture channelFuture) {
        if (!channelFuture.isSuccess()) {
            this.recordException(channelFuture.cause());
        }
    }

    private void recordException(Throwable throwable) {
        if (this.lastException == null) {
            this.lastException = throwable;
        } else {
            logger.warn("More than one exception was raised. Will report only the first one and log others.", throwable);
        }
    }

    private ChannelFuture checkException(ChannelPromise channelPromise) {
        Throwable throwable = this.lastException;
        if (throwable != null) {
            this.lastException = null;
            if (channelPromise.isVoid()) {
                PlatformDependent.throwException(throwable);
            }
            return channelPromise.setFailure(throwable);
        }
        return channelPromise.setSuccess();
    }

    public void checkException() {
        this.checkException(this.voidPromise());
    }

    private boolean checkOpen(boolean bl) {
        if (!this.isOpen()) {
            if (bl) {
                this.recordException(new ClosedChannelException());
            }
            return true;
        }
        return false;
    }

    protected final void ensureOpen() {
        if (!this.checkOpen(true)) {
            this.checkException();
        }
    }

    @Override
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof EmbeddedEventLoop;
    }

    @Override
    protected SocketAddress localAddress0() {
        return this.isActive() ? LOCAL_ADDRESS : null;
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return this.isActive() ? REMOTE_ADDRESS : null;
    }

    @Override
    protected void doRegister() throws Exception {
        this.state = State.ACTIVE;
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
    }

    @Override
    protected void doDisconnect() throws Exception {
        if (!this.metadata.hasDisconnect()) {
            this.doClose();
        }
    }

    @Override
    protected void doClose() throws Exception {
        this.state = State.CLOSED;
    }

    @Override
    protected void doBeginRead() throws Exception {
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return new EmbeddedUnsafe(this, null);
    }

    @Override
    public Channel.Unsafe unsafe() {
        return ((EmbeddedUnsafe)super.unsafe()).wrapped;
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        Object object;
        while ((object = channelOutboundBuffer.current()) != null) {
            ReferenceCountUtil.retain(object);
            this.handleOutboundMessage(object);
            channelOutboundBuffer.remove();
        }
    }

    protected void handleOutboundMessage(Object object) {
        this.outboundMessages().add(object);
    }

    protected void handleInboundMessage(Object object) {
        this.inboundMessages().add(object);
    }

    static void access$000(EmbeddedChannel embeddedChannel, ChannelFuture channelFuture) {
        embeddedChannel.recordException(channelFuture);
    }

    static void access$200(EmbeddedChannel embeddedChannel, Throwable throwable) {
        embeddedChannel.recordException(throwable);
    }

    static {
        $assertionsDisabled = !EmbeddedChannel.class.desiredAssertionStatus();
        LOCAL_ADDRESS = new EmbeddedSocketAddress();
        REMOTE_ADDRESS = new EmbeddedSocketAddress();
        EMPTY_HANDLERS = new ChannelHandler[0];
        logger = InternalLoggerFactory.getInstance(EmbeddedChannel.class);
        METADATA_NO_DISCONNECT = new ChannelMetadata(false);
        METADATA_DISCONNECT = new ChannelMetadata(true);
    }

    private final class EmbeddedChannelPipeline
    extends DefaultChannelPipeline {
        final EmbeddedChannel this$0;

        EmbeddedChannelPipeline(EmbeddedChannel embeddedChannel, EmbeddedChannel embeddedChannel2) {
            this.this$0 = embeddedChannel;
            super(embeddedChannel2);
        }

        @Override
        protected void onUnhandledInboundException(Throwable throwable) {
            EmbeddedChannel.access$200(this.this$0, throwable);
        }

        @Override
        protected void onUnhandledInboundMessage(Object object) {
            this.this$0.handleInboundMessage(object);
        }
    }

    private final class EmbeddedUnsafe
    extends AbstractChannel.AbstractUnsafe {
        final Channel.Unsafe wrapped;
        final EmbeddedChannel this$0;

        private EmbeddedUnsafe(EmbeddedChannel embeddedChannel) {
            this.this$0 = embeddedChannel;
            super(embeddedChannel);
            this.wrapped = new Channel.Unsafe(this){
                final EmbeddedUnsafe this$1;
                {
                    this.this$1 = embeddedUnsafe;
                }

                @Override
                public RecvByteBufAllocator.Handle recvBufAllocHandle() {
                    return this.this$1.recvBufAllocHandle();
                }

                @Override
                public SocketAddress localAddress() {
                    return this.this$1.localAddress();
                }

                @Override
                public SocketAddress remoteAddress() {
                    return this.this$1.remoteAddress();
                }

                @Override
                public void register(EventLoop eventLoop, ChannelPromise channelPromise) {
                    this.this$1.register(eventLoop, channelPromise);
                    this.this$1.this$0.runPendingTasks();
                }

                @Override
                public void bind(SocketAddress socketAddress, ChannelPromise channelPromise) {
                    this.this$1.bind(socketAddress, channelPromise);
                    this.this$1.this$0.runPendingTasks();
                }

                @Override
                public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
                    this.this$1.connect(socketAddress, socketAddress2, channelPromise);
                    this.this$1.this$0.runPendingTasks();
                }

                @Override
                public void disconnect(ChannelPromise channelPromise) {
                    this.this$1.disconnect(channelPromise);
                    this.this$1.this$0.runPendingTasks();
                }

                @Override
                public void close(ChannelPromise channelPromise) {
                    this.this$1.close(channelPromise);
                    this.this$1.this$0.runPendingTasks();
                }

                @Override
                public void closeForcibly() {
                    this.this$1.closeForcibly();
                    this.this$1.this$0.runPendingTasks();
                }

                @Override
                public void deregister(ChannelPromise channelPromise) {
                    this.this$1.deregister(channelPromise);
                    this.this$1.this$0.runPendingTasks();
                }

                @Override
                public void beginRead() {
                    this.this$1.beginRead();
                    this.this$1.this$0.runPendingTasks();
                }

                @Override
                public void write(Object object, ChannelPromise channelPromise) {
                    this.this$1.write(object, channelPromise);
                    this.this$1.this$0.runPendingTasks();
                }

                @Override
                public void flush() {
                    this.this$1.flush();
                    this.this$1.this$0.runPendingTasks();
                }

                @Override
                public ChannelPromise voidPromise() {
                    return this.this$1.voidPromise();
                }

                @Override
                public ChannelOutboundBuffer outboundBuffer() {
                    return this.this$1.outboundBuffer();
                }
            };
        }

        @Override
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            this.safeSetSuccess(channelPromise);
        }

        EmbeddedUnsafe(EmbeddedChannel embeddedChannel, 1 var2_2) {
            this(embeddedChannel);
        }
    }

    private static enum State {
        OPEN,
        ACTIVE,
        CLOSED;

    }
}

