/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsResponseCode;
import io.netty.resolver.dns.DnsQueryLifecycleObserver;
import java.net.InetSocketAddress;
import java.util.List;

final class NoopDnsQueryLifecycleObserver
implements DnsQueryLifecycleObserver {
    static final NoopDnsQueryLifecycleObserver INSTANCE = new NoopDnsQueryLifecycleObserver();

    private NoopDnsQueryLifecycleObserver() {
    }

    @Override
    public void queryWritten(InetSocketAddress inetSocketAddress, ChannelFuture channelFuture) {
    }

    @Override
    public void queryCancelled(int n) {
    }

    @Override
    public DnsQueryLifecycleObserver queryRedirected(List<InetSocketAddress> list) {
        return this;
    }

    @Override
    public DnsQueryLifecycleObserver queryCNAMEd(DnsQuestion dnsQuestion) {
        return this;
    }

    @Override
    public DnsQueryLifecycleObserver queryNoAnswer(DnsResponseCode dnsResponseCode) {
        return this;
    }

    @Override
    public void queryFailed(Throwable throwable) {
    }

    @Override
    public void querySucceed() {
    }
}

