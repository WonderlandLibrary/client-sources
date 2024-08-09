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
    public boolean clear(String string) {
        return true;
    }

    @Override
    public List<? extends DnsCacheEntry> get(String string, DnsRecord[] dnsRecordArray) {
        return Collections.emptyList();
    }

    @Override
    public DnsCacheEntry cache(String string, DnsRecord[] dnsRecordArray, InetAddress inetAddress, long l, EventLoop eventLoop) {
        return new NoopDnsCacheEntry(inetAddress);
    }

    @Override
    public DnsCacheEntry cache(String string, DnsRecord[] dnsRecordArray, Throwable throwable, EventLoop eventLoop) {
        return null;
    }

    public String toString() {
        return NoopDnsCache.class.getSimpleName();
    }

    private static final class NoopDnsCacheEntry
    implements DnsCacheEntry {
        private final InetAddress address;

        NoopDnsCacheEntry(InetAddress inetAddress) {
            this.address = inetAddress;
        }

        @Override
        public InetAddress address() {
            return this.address;
        }

        @Override
        public Throwable cause() {
            return null;
        }

        public String toString() {
            return this.address.toString();
        }
    }
}

