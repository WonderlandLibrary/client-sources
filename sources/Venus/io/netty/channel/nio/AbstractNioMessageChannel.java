/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.nio;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.AbstractNioChannel;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractNioMessageChannel
extends AbstractNioChannel {
    boolean inputShutdown;

    protected AbstractNioMessageChannel(Channel channel, SelectableChannel selectableChannel, int n) {
        super(channel, selectableChannel, n);
    }

    @Override
    protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new NioMessageUnsafe(this, null);
    }

    @Override
    protected void doBeginRead() throws Exception {
        if (this.inputShutdown) {
            return;
        }
        super.doBeginRead();
    }

    @Override
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        SelectionKey selectionKey = this.selectionKey();
        int n = selectionKey.interestOps();
        while (true) {
            Object object;
            if ((object = channelOutboundBuffer.current()) == null) {
                if ((n & 4) == 0) break;
                selectionKey.interestOps(n & 0xFFFFFFFB);
                break;
            }
            try {
                boolean bl = false;
                for (int i = this.config().getWriteSpinCount() - 1; i >= 0; --i) {
                    if (!this.doWriteMessage(object, channelOutboundBuffer)) continue;
                    bl = true;
                    break;
                }
                if (bl) {
                    channelOutboundBuffer.remove();
                    continue;
                }
                if ((n & 4) != 0) break;
                selectionKey.interestOps(n | 4);
            } catch (Exception exception) {
                if (this.continueOnWriteError()) {
                    channelOutboundBuffer.remove(exception);
                    continue;
                }
                throw exception;
            }
            break;
        }
    }

    protected boolean continueOnWriteError() {
        return true;
    }

    protected boolean closeOnReadError(Throwable throwable) {
        if (!this.isActive()) {
            return false;
        }
        if (throwable instanceof PortUnreachableException) {
            return true;
        }
        if (throwable instanceof IOException) {
            return !(this instanceof ServerChannel);
        }
        return false;
    }

    protected abstract int doReadMessages(List<Object> var1) throws Exception;

    protected abstract boolean doWriteMessage(Object var1, ChannelOutboundBuffer var2) throws Exception;

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return this.newUnsafe();
    }

    private final class NioMessageUnsafe
    extends AbstractNioChannel.AbstractNioUnsafe {
        private final List<Object> readBuf;
        static final boolean $assertionsDisabled = !AbstractNioMessageChannel.class.desiredAssertionStatus();
        final AbstractNioMessageChannel this$0;

        private NioMessageUnsafe(AbstractNioMessageChannel abstractNioMessageChannel) {
            this.this$0 = abstractNioMessageChannel;
            super(abstractNioMessageChannel);
            this.readBuf = new ArrayList<Object>();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void read() {
            if (!$assertionsDisabled && !this.this$0.eventLoop().inEventLoop()) {
                throw new AssertionError();
            }
            ChannelConfig channelConfig = this.this$0.config();
            ChannelPipeline channelPipeline = this.this$0.pipeline();
            RecvByteBufAllocator.Handle handle = this.this$0.unsafe().recvBufAllocHandle();
            handle.reset(channelConfig);
            boolean bl = false;
            Throwable throwable = null;
            try {
                int n;
                try {
                    while ((n = this.this$0.doReadMessages(this.readBuf)) != 0) {
                        if (n < 0) {
                            bl = true;
                        } else {
                            handle.incMessagesRead(n);
                            if (handle.continueReading()) continue;
                        }
                        break;
                    }
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                }
                n = this.readBuf.size();
                for (int i = 0; i < n; ++i) {
                    this.this$0.readPending = false;
                    channelPipeline.fireChannelRead(this.readBuf.get(i));
                }
                this.readBuf.clear();
                handle.readComplete();
                channelPipeline.fireChannelReadComplete();
                if (throwable != null) {
                    bl = this.this$0.closeOnReadError(throwable);
                    channelPipeline.fireExceptionCaught(throwable);
                }
                if (bl) {
                    this.this$0.inputShutdown = true;
                    if (this.this$0.isOpen()) {
                        this.close(this.voidPromise());
                    }
                }
            } finally {
                if (!this.this$0.readPending && !channelConfig.isAutoRead()) {
                    this.removeReadOp();
                }
            }
        }

        NioMessageUnsafe(AbstractNioMessageChannel abstractNioMessageChannel, 1 var2_2) {
            this(abstractNioMessageChannel);
        }
    }
}

