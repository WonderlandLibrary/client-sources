/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.oio;

import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.ThreadPerChannelEventLoop;
import java.net.SocketAddress;

public abstract class AbstractOioChannel
extends AbstractChannel {
    protected static final int SO_TIMEOUT = 1000;
    boolean readPending;
    private final Runnable readTask = new Runnable(this){
        final AbstractOioChannel this$0;
        {
            this.this$0 = abstractOioChannel;
        }

        @Override
        public void run() {
            this.this$0.doRead();
        }
    };
    private final Runnable clearReadPendingRunnable = new Runnable(this){
        final AbstractOioChannel this$0;
        {
            this.this$0 = abstractOioChannel;
        }

        @Override
        public void run() {
            this.this$0.readPending = false;
        }
    };

    protected AbstractOioChannel(Channel channel) {
        super(channel);
    }

    @Override
    protected AbstractChannel.AbstractUnsafe newUnsafe() {
        return new DefaultOioUnsafe(this, null);
    }

    @Override
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof ThreadPerChannelEventLoop;
    }

    protected abstract void doConnect(SocketAddress var1, SocketAddress var2) throws Exception;

    @Override
    protected void doBeginRead() throws Exception {
        if (this.readPending) {
            return;
        }
        this.readPending = true;
        this.eventLoop().execute(this.readTask);
    }

    protected abstract void doRead();

    @Deprecated
    protected boolean isReadPending() {
        return this.readPending;
    }

    @Deprecated
    protected void setReadPending(boolean bl) {
        if (this.isRegistered()) {
            EventLoop eventLoop = this.eventLoop();
            if (eventLoop.inEventLoop()) {
                this.readPending = bl;
            } else {
                eventLoop.execute(new Runnable(this, bl){
                    final boolean val$readPending;
                    final AbstractOioChannel this$0;
                    {
                        this.this$0 = abstractOioChannel;
                        this.val$readPending = bl;
                    }

                    @Override
                    public void run() {
                        this.this$0.readPending = this.val$readPending;
                    }
                });
            }
        } else {
            this.readPending = bl;
        }
    }

    protected final void clearReadPending() {
        if (this.isRegistered()) {
            EventLoop eventLoop = this.eventLoop();
            if (eventLoop.inEventLoop()) {
                this.readPending = false;
            } else {
                eventLoop.execute(this.clearReadPendingRunnable);
            }
        } else {
            this.readPending = false;
        }
    }

    private final class DefaultOioUnsafe
    extends AbstractChannel.AbstractUnsafe {
        final AbstractOioChannel this$0;

        private DefaultOioUnsafe(AbstractOioChannel abstractOioChannel) {
            this.this$0 = abstractOioChannel;
            super(abstractOioChannel);
        }

        @Override
        public void connect(SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) {
            if (!channelPromise.setUncancellable() || !this.ensureOpen(channelPromise)) {
                return;
            }
            try {
                boolean bl = this.this$0.isActive();
                this.this$0.doConnect(socketAddress, socketAddress2);
                boolean bl2 = this.this$0.isActive();
                this.safeSetSuccess(channelPromise);
                if (!bl && bl2) {
                    this.this$0.pipeline().fireChannelActive();
                }
            } catch (Throwable throwable) {
                this.safeSetFailure(channelPromise, this.annotateConnectException(throwable, socketAddress));
                this.closeIfClosed();
            }
        }

        DefaultOioUnsafe(AbstractOioChannel abstractOioChannel, 1 var2_2) {
            this(abstractOioChannel);
        }
    }
}

