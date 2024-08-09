/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ipfilter;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ipfilter.AbstractRemoteAddressFilter;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ConcurrentSet;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Set;

@ChannelHandler.Sharable
public class UniqueIpFilter
extends AbstractRemoteAddressFilter<InetSocketAddress> {
    private final Set<InetAddress> connected = new ConcurrentSet<InetAddress>();

    @Override
    protected boolean accept(ChannelHandlerContext channelHandlerContext, InetSocketAddress inetSocketAddress) throws Exception {
        InetAddress inetAddress = inetSocketAddress.getAddress();
        if (this.connected.contains(inetAddress)) {
            return true;
        }
        this.connected.add(inetAddress);
        channelHandlerContext.channel().closeFuture().addListener(new ChannelFutureListener(this, inetAddress){
            final InetAddress val$remoteIp;
            final UniqueIpFilter this$0;
            {
                this.this$0 = uniqueIpFilter;
                this.val$remoteIp = inetAddress;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                UniqueIpFilter.access$000(this.this$0).remove(this.val$remoteIp);
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
        return false;
    }

    @Override
    protected boolean accept(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress) throws Exception {
        return this.accept(channelHandlerContext, (InetSocketAddress)socketAddress);
    }

    static Set access$000(UniqueIpFilter uniqueIpFilter) {
        return uniqueIpFilter.connected;
    }
}

