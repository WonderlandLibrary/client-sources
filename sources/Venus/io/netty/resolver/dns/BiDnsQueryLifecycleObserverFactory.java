/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.resolver.dns.BiDnsQueryLifecycleObserver;
import io.netty.resolver.dns.DnsQueryLifecycleObserver;
import io.netty.resolver.dns.DnsQueryLifecycleObserverFactory;
import io.netty.util.internal.ObjectUtil;

public final class BiDnsQueryLifecycleObserverFactory
implements DnsQueryLifecycleObserverFactory {
    private final DnsQueryLifecycleObserverFactory a;
    private final DnsQueryLifecycleObserverFactory b;

    public BiDnsQueryLifecycleObserverFactory(DnsQueryLifecycleObserverFactory dnsQueryLifecycleObserverFactory, DnsQueryLifecycleObserverFactory dnsQueryLifecycleObserverFactory2) {
        this.a = ObjectUtil.checkNotNull(dnsQueryLifecycleObserverFactory, "a");
        this.b = ObjectUtil.checkNotNull(dnsQueryLifecycleObserverFactory2, "b");
    }

    @Override
    public DnsQueryLifecycleObserver newDnsQueryLifecycleObserver(DnsQuestion dnsQuestion) {
        return new BiDnsQueryLifecycleObserver(this.a.newDnsQueryLifecycleObserver(dnsQuestion), this.b.newDnsQueryLifecycleObserver(dnsQuestion));
    }
}

