/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.handler.codec.http2.Http2DataFrame;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;
import java.net.SocketAddress;
import java.nio.channels.ClosedChannelException;
import java.util.ArrayDeque;
import java.util.Queue;

abstract class AbstractHttp2StreamChannel
extends AbstractChannel {
    protected static final Object CLOSE_MESSAGE = new Object();
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private static final ClosedChannelException CLOSED_CHANNEL_EXCEPTION = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), AbstractHttp2StreamChannel.class, "doWrite(...)");
    private static final int ARBITRARY_MESSAGE_SIZE = 9;
    private final ChannelConfig config = new DefaultChannelConfig(this);
    private final Queue<Object> inboundBuffer = new ArrayDeque<Object>(4);
    private final Runnable fireChildReadCompleteTask = new Runnable(){

        @Override
        public void run() {
            if (AbstractHttp2StreamChannel.this.readInProgress) {
                AbstractHttp2StreamChannel.this.readInProgress = false;
                AbstractHttp2StreamChannel.this.unsafe().recvBufAllocHandle().readComplete();
                AbstractHttp2StreamChannel.this.pipeline().fireChannelReadComplete();
            }
        }
    };
    private volatile int streamId = -1;
    private boolean closed;
    private boolean readInProgress;

    protected AbstractHttp2StreamChannel(Channel parent) {
        super(parent);
    }

    @Override
    public ChannelMetadata metadata() {
        return METADATA;
    }

    @Override
    public ChannelConfig config() {
        return this.config;
    }

    @Override
    public boolean isOpen() {
        return !this.closed;
    }

    @Override
    public boolean isActive() {
        return this.isOpen();
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return new Unsafe();
    }

    @Override
    protected boolean isCompatible(EventLoop loop) {
        return true;
    }

    @Override
    protected SocketAddress localAddress0() {
        return this.parent().localAddress();
    }

    @Override
    protected SocketAddress remoteAddress0() {
        return this.parent().remoteAddress();
    }

    @Override
    protected void doBind(SocketAddress localAddress) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doDisconnect() throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doClose() throws Exception {
        this.closed = true;
        while (!this.inboundBuffer.isEmpty()) {
            ReferenceCountUtil.release(this.inboundBuffer.poll());
        }
    }

    @Override
    protected void doBeginRead() {
        Object m;
        if (this.readInProgress) {
            return;
        }
        RecvByteBufAllocator.Handle allocHandle = this.unsafe().recvBufAllocHandle();
        allocHandle.reset(this.config());
        if (this.inboundBuffer.isEmpty()) {
            this.readInProgress = true;
            return;
        }
        while ((m = this.inboundBuffer.poll()) != null) {
            if (!this.doRead0(m, allocHandle)) {
                return;
            }
            if (allocHandle.continueReading()) continue;
        }
        allocHandle.readComplete();
        this.pipeline().fireChannelReadComplete();
    }

    @Override
    protected final void doWrite(ChannelOutboundBuffer in) throws Exception {
        if (this.closed) {
            throw CLOSED_CHANNEL_EXCEPTION;
        }
        EventExecutor preferredExecutor = this.preferredEventExecutor();
        if (preferredExecutor.inEventLoop()) {
            Object msg;
            while ((msg = in.current()) != null) {
                try {
                    this.doWrite(ReferenceCountUtil.retain(msg));
                } catch (Throwable t) {
                    this.pipeline().fireExceptionCaught(t);
                }
                in.remove();
            }
            this.doWriteComplete();
        } else {
            final Object[] msgsCopy = new Object[in.size()];
            for (int i = 0; i < msgsCopy.length; ++i) {
                msgsCopy[i] = ReferenceCountUtil.retain(in.current());
                in.remove();
            }
            preferredExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    for (Object msg : msgsCopy) {
                        try {
                            AbstractHttp2StreamChannel.this.doWrite(msg);
                        } catch (Throwable t) {
                            AbstractHttp2StreamChannel.this.pipeline().fireExceptionCaught(t);
                        }
                    }
                    AbstractHttp2StreamChannel.this.doWriteComplete();
                }
            });
        }
    }

    protected abstract void doWrite(Object var1) throws Exception;

    protected abstract void doWriteComplete();

    protected abstract EventExecutor preferredEventExecutor();

    protected abstract void bytesConsumed(int var1);

    protected void fireChildRead(final Object msg) {
        if (this.eventLoop().inEventLoop()) {
            this.fireChildRead0(msg);
        } else {
            this.eventLoop().execute(new Runnable(){

                @Override
                public void run() {
                    AbstractHttp2StreamChannel.this.fireChildRead0(msg);
                }
            });
        }
    }

    private void fireChildRead0(Object msg) {
        if (this.closed) {
            ReferenceCountUtil.release(msg);
            return;
        }
        if (this.readInProgress) {
            assert (this.inboundBuffer.isEmpty());
            RecvByteBufAllocator.Handle allocHandle = this.unsafe().recvBufAllocHandle();
            this.readInProgress = this.doRead0(ObjectUtil.checkNotNull(msg, "msg"), allocHandle);
            if (!allocHandle.continueReading()) {
                this.fireChildReadCompleteTask.run();
            }
        } else {
            this.inboundBuffer.add(msg);
        }
    }

    protected void fireChildReadComplete() {
        if (this.eventLoop().inEventLoop()) {
            this.fireChildReadCompleteTask.run();
        } else {
            this.eventLoop().execute(this.fireChildReadCompleteTask);
        }
    }

    protected void streamId(int streamId) {
        if (this.streamId != -1) {
            throw new IllegalStateException("Stream identifier may only be set once.");
        }
        this.streamId = ObjectUtil.checkPositiveOrZero(streamId, "streamId");
    }

    protected int streamId() {
        return this.streamId;
    }

    private boolean doRead0(Object msg, RecvByteBufAllocator.Handle allocHandle) {
        if (msg == CLOSE_MESSAGE) {
            allocHandle.readComplete();
            this.pipeline().fireChannelReadComplete();
            this.unsafe().close(this.voidPromise());
            return false;
        }
        int numBytesToBeConsumed = 0;
        if (msg instanceof Http2DataFrame) {
            Http2DataFrame data = (Http2DataFrame)msg;
            numBytesToBeConsumed = data.content().readableBytes() + data.padding();
            allocHandle.lastBytesRead(numBytesToBeConsumed);
        } else {
            allocHandle.lastBytesRead(9);
        }
        allocHandle.incMessagesRead(1);
        this.pipeline().fireChannelRead(msg);
        if (numBytesToBeConsumed != 0) {
            this.bytesConsumed(numBytesToBeConsumed);
        }
        return true;
    }

    private final class Unsafe
    extends AbstractChannel.AbstractUnsafe {
        private Unsafe() {
        }

        @Override
        public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
            promise.setFailure(new UnsupportedOperationException());
        }
    }
}

