/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.local;

import io.netty.channel.AbstractServerChannel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.EventLoop;
import io.netty.channel.PreferHeapByteBufAllocator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalChannelRegistry;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import java.net.SocketAddress;
import java.util.ArrayDeque;
import java.util.Queue;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LocalServerChannel
extends AbstractServerChannel {
    private final ChannelConfig config = new DefaultChannelConfig(this);
    private final Queue<Object> inboundBuffer = new ArrayDeque<Object>();
    private final Runnable shutdownHook = new Runnable(this){
        final LocalServerChannel this$0;
        {
            this.this$0 = localServerChannel;
        }

        @Override
        public void run() {
            this.this$0.unsafe().close(this.this$0.unsafe().voidPromise());
        }
    };
    private volatile int state;
    private volatile LocalAddress localAddress;
    private volatile boolean acceptInProgress;

    public LocalServerChannel() {
        this.config().setAllocator(new PreferHeapByteBufAllocator(this.config.getAllocator()));
    }

    @Override
    public ChannelConfig config() {
        return this.config;
    }

    @Override
    public LocalAddress localAddress() {
        return (LocalAddress)super.localAddress();
    }

    @Override
    public LocalAddress remoteAddress() {
        return (LocalAddress)super.remoteAddress();
    }

    @Override
    public boolean isOpen() {
        return this.state < 2;
    }

    @Override
    public boolean isActive() {
        return this.state == 1;
    }

    @Override
    protected boolean isCompatible(EventLoop eventLoop) {
        return eventLoop instanceof SingleThreadEventLoop;
    }

    @Override
    protected SocketAddress localAddress0() {
        return this.localAddress;
    }

    @Override
    protected void doRegister() throws Exception {
        ((SingleThreadEventExecutor)((Object)this.eventLoop())).addShutdownHook(this.shutdownHook);
    }

    @Override
    protected void doBind(SocketAddress socketAddress) throws Exception {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, socketAddress);
        this.state = 1;
    }

    @Override
    protected void doClose() throws Exception {
        if (this.state <= 1) {
            if (this.localAddress != null) {
                LocalChannelRegistry.unregister(this.localAddress);
                this.localAddress = null;
            }
            this.state = 2;
        }
    }

    @Override
    protected void doDeregister() throws Exception {
        ((SingleThreadEventExecutor)((Object)this.eventLoop())).removeShutdownHook(this.shutdownHook);
    }

    @Override
    protected void doBeginRead() throws Exception {
        if (this.acceptInProgress) {
            return;
        }
        Queue<Object> queue = this.inboundBuffer;
        if (queue.isEmpty()) {
            this.acceptInProgress = true;
            return;
        }
        this.readInbound();
    }

    LocalChannel serve(LocalChannel localChannel) {
        LocalChannel localChannel2 = this.newLocalChannel(localChannel);
        if (this.eventLoop().inEventLoop()) {
            this.serve0(localChannel2);
        } else {
            this.eventLoop().execute(new Runnable(this, localChannel2){
                final LocalChannel val$child;
                final LocalServerChannel this$0;
                {
                    this.this$0 = localServerChannel;
                    this.val$child = localChannel;
                }

                @Override
                public void run() {
                    LocalServerChannel.access$000(this.this$0, this.val$child);
                }
            });
        }
        return localChannel2;
    }

    private void readInbound() {
        Object object;
        RecvByteBufAllocator.Handle handle = this.unsafe().recvBufAllocHandle();
        handle.reset(this.config());
        ChannelPipeline channelPipeline = this.pipeline();
        while ((object = this.inboundBuffer.poll()) != null) {
            channelPipeline.fireChannelRead(object);
            if (handle.continueReading()) continue;
        }
        channelPipeline.fireChannelReadComplete();
    }

    protected LocalChannel newLocalChannel(LocalChannel localChannel) {
        return new LocalChannel(this, localChannel);
    }

    private void serve0(LocalChannel localChannel) {
        this.inboundBuffer.add(localChannel);
        if (this.acceptInProgress) {
            this.acceptInProgress = false;
            this.readInbound();
        }
    }

    @Override
    public SocketAddress remoteAddress() {
        return this.remoteAddress();
    }

    @Override
    public SocketAddress localAddress() {
        return this.localAddress();
    }

    static void access$000(LocalServerChannel localServerChannel, LocalChannel localChannel) {
        localServerChannel.serve0(localChannel);
    }
}

