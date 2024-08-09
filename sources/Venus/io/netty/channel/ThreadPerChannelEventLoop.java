/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.ThreadPerChannelEventLoopGroup;
import io.netty.util.concurrent.Future;

public class ThreadPerChannelEventLoop
extends SingleThreadEventLoop {
    private final ThreadPerChannelEventLoopGroup parent;
    private Channel ch;

    public ThreadPerChannelEventLoop(ThreadPerChannelEventLoopGroup threadPerChannelEventLoopGroup) {
        super((EventLoopGroup)threadPerChannelEventLoopGroup, threadPerChannelEventLoopGroup.executor, true);
        this.parent = threadPerChannelEventLoopGroup;
    }

    @Override
    public ChannelFuture register(ChannelPromise channelPromise) {
        return super.register(channelPromise).addListener(new ChannelFutureListener(this){
            final ThreadPerChannelEventLoop this$0;
            {
                this.this$0 = threadPerChannelEventLoop;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    ThreadPerChannelEventLoop.access$002(this.this$0, channelFuture.channel());
                } else {
                    this.this$0.deregister();
                }
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }

    @Override
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        return super.register(channel, channelPromise).addListener(new ChannelFutureListener(this){
            final ThreadPerChannelEventLoop this$0;
            {
                this.this$0 = threadPerChannelEventLoop;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    ThreadPerChannelEventLoop.access$002(this.this$0, channelFuture.channel());
                } else {
                    this.this$0.deregister();
                }
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }

    @Override
    protected void run() {
        while (true) {
            Runnable runnable;
            if ((runnable = this.takeTask()) != null) {
                runnable.run();
                this.updateLastExecutionTime();
            }
            Channel channel = this.ch;
            if (this.isShuttingDown()) {
                if (channel != null) {
                    channel.unsafe().close(channel.unsafe().voidPromise());
                }
                if (!this.confirmShutdown()) continue;
                break;
            }
            if (channel == null || channel.isRegistered()) continue;
            this.runAllTasks();
            this.deregister();
        }
    }

    protected void deregister() {
        this.ch = null;
        this.parent.activeChildren.remove(this);
        this.parent.idleChildren.add(this);
    }

    static Channel access$002(ThreadPerChannelEventLoop threadPerChannelEventLoop, Channel channel) {
        threadPerChannelEventLoop.ch = channel;
        return threadPerChannelEventLoop.ch;
    }
}

