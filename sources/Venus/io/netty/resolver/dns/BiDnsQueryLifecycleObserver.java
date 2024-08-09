/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsResponseCode;
import io.netty.resolver.dns.DnsQueryLifecycleObserver;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;
import java.util.List;

public final class BiDnsQueryLifecycleObserver
implements DnsQueryLifecycleObserver {
    private final DnsQueryLifecycleObserver a;
    private final DnsQueryLifecycleObserver b;

    public BiDnsQueryLifecycleObserver(DnsQueryLifecycleObserver dnsQueryLifecycleObserver, DnsQueryLifecycleObserver dnsQueryLifecycleObserver2) {
        this.a = ObjectUtil.checkNotNull(dnsQueryLifecycleObserver, "a");
        this.b = ObjectUtil.checkNotNull(dnsQueryLifecycleObserver2, "b");
    }

    @Override
    public void queryWritten(InetSocketAddress inetSocketAddress, ChannelFuture channelFuture) {
        try {
            this.a.queryWritten(inetSocketAddress, channelFuture);
        } finally {
            this.b.queryWritten(inetSocketAddress, channelFuture);
        }
    }

    @Override
    public void queryCancelled(int n) {
        try {
            this.a.queryCancelled(n);
        } finally {
            this.b.queryCancelled(n);
        }
    }

    @Override
    public DnsQueryLifecycleObserver queryRedirected(List<InetSocketAddress> list) {
        try {
            this.a.queryRedirected(list);
        } finally {
            this.b.queryRedirected(list);
        }
        return this;
    }

    @Override
    public DnsQueryLifecycleObserver queryCNAMEd(DnsQuestion dnsQuestion) {
        try {
            this.a.queryCNAMEd(dnsQuestion);
        } finally {
            this.b.queryCNAMEd(dnsQuestion);
        }
        return this;
    }

    @Override
    public DnsQueryLifecycleObserver queryNoAnswer(DnsResponseCode dnsResponseCode) {
        try {
            this.a.queryNoAnswer(dnsResponseCode);
        } finally {
            this.b.queryNoAnswer(dnsResponseCode);
        }
        return this;
    }

    @Override
    public void queryFailed(Throwable throwable) {
        try {
            this.a.queryFailed(throwable);
        } finally {
            this.b.queryFailed(throwable);
        }
    }

    @Override
    public void querySucceed() {
        try {
            this.a.querySucceed();
        } finally {
            this.b.querySucceed();
        }
    }
}

