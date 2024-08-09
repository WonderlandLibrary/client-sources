/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.resolver.dns.DnsQueryLifecycleObserver;

public interface DnsQueryLifecycleObserverFactory {
    public DnsQueryLifecycleObserver newDnsQueryLifecycleObserver(DnsQuestion var1);
}

