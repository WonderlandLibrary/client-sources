/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel;

public interface ChannelInboundInvoker {
    public ChannelInboundInvoker fireChannelRegistered();

    public ChannelInboundInvoker fireChannelUnregistered();

    public ChannelInboundInvoker fireChannelActive();

    public ChannelInboundInvoker fireChannelInactive();

    public ChannelInboundInvoker fireExceptionCaught(Throwable var1);

    public ChannelInboundInvoker fireUserEventTriggered(Object var1);

    public ChannelInboundInvoker fireChannelRead(Object var1);

    public ChannelInboundInvoker fireChannelReadComplete();

    public ChannelInboundInvoker fireChannelWritabilityChanged();
}

