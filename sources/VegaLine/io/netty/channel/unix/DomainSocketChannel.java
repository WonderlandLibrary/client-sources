/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import io.netty.channel.socket.DuplexChannel;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.channel.unix.DomainSocketChannelConfig;
import io.netty.channel.unix.UnixChannel;

public interface DomainSocketChannel
extends UnixChannel,
DuplexChannel {
    @Override
    public DomainSocketAddress remoteAddress();

    @Override
    public DomainSocketAddress localAddress();

    @Override
    public DomainSocketChannelConfig config();
}

