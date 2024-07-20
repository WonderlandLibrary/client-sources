/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.resolver.dns.DnsCache;
import io.netty.resolver.dns.DnsCacheEntry;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;

public final class NoopDnsCache
implements DnsCache {
    public static final NoopDnsCache INSTANCE = new NoopDnsCache();

    private NoopDnsCache() {
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean clear(String hostname) {
        return false;
    }

    @Override
    public List<DnsCacheEntry> get(String hostname, DnsRecord[] additionals) {
        return Collections.emptyList();
    }

    @Override
    public void cache(String hostname, DnsRecord[] additional, InetAddress address, long originalTtl, EventLoop loop) {
    }

    @Override
    public void cache(String hostname, DnsRecord[] additional, Throwable cause, EventLoop loop) {
    }

    public String toString() {
        return NoopDnsCache.class.getSimpleName();
    }
}

