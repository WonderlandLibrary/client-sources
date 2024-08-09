/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.udt;

import io.netty.channel.Channel;
import io.netty.channel.udt.UdtChannelConfig;
import java.net.InetSocketAddress;

@Deprecated
public interface UdtChannel
extends Channel {
    @Override
    public UdtChannelConfig config();

    @Override
    public InetSocketAddress localAddress();

    @Override
    public InetSocketAddress remoteAddress();
}

