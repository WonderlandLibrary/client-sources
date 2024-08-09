/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.resolver.dns.DnsCache;
import io.netty.resolver.dns.DnsCacheEntry;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class DefaultDnsCache
implements DnsCache {
    private final ConcurrentMap<String, Entries> resolveCache = PlatformDependent.newConcurrentHashMap();
    private static final int MAX_SUPPORTED_TTL_SECS = (int)TimeUnit.DAYS.toSeconds(730L);
    private final int minTtl;
    private final int maxTtl;
    private final int negativeTtl;

    public DefaultDnsCache() {
        this(0, MAX_SUPPORTED_TTL_SECS, 0);
    }

    public DefaultDnsCache(int n, int n2, int n3) {
        this.minTtl = Math.min(MAX_SUPPORTED_TTL_SECS, ObjectUtil.checkPositiveOrZero(n, "minTtl"));
        this.maxTtl = Math.min(MAX_SUPPORTED_TTL_SECS, ObjectUtil.checkPositiveOrZero(n2, "maxTtl"));
        if (n > n2) {
            throw new IllegalArgumentException("minTtl: " + n + ", maxTtl: " + n2 + " (expected: 0 <= minTtl <= maxTtl)");
        }
        this.negativeTtl = ObjectUtil.checkPositiveOrZero(n3, "negativeTtl");
    }

    public int minTtl() {
        return this.minTtl;
    }

    public int maxTtl() {
        return this.maxTtl;
    }

    public int negativeTtl() {
        return this.negativeTtl;
    }

    @Override
    public void clear() {
        while (!this.resolveCache.isEmpty()) {
            Iterator iterator2 = this.resolveCache.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry entry = iterator2.next();
                iterator2.remove();
                ((Entries)entry.getValue()).clearAndCancel();
            }
        }
    }

    @Override
    public boolean clear(String string) {
        ObjectUtil.checkNotNull(string, "hostname");
        Entries entries = (Entries)this.resolveCache.remove(string);
        return entries != null && entries.clearAndCancel();
    }

    private static boolean emptyAdditionals(DnsRecord[] dnsRecordArray) {
        return dnsRecordArray == null || dnsRecordArray.length == 0;
    }

    @Override
    public List<? extends DnsCacheEntry> get(String string, DnsRecord[] dnsRecordArray) {
        ObjectUtil.checkNotNull(string, "hostname");
        if (!DefaultDnsCache.emptyAdditionals(dnsRecordArray)) {
            return Collections.emptyList();
        }
        Entries entries = (Entries)this.resolveCache.get(string);
        return entries == null ? null : (List)entries.get();
    }

    @Override
    public DnsCacheEntry cache(String string, DnsRecord[] dnsRecordArray, InetAddress inetAddress, long l, EventLoop eventLoop) {
        ObjectUtil.checkNotNull(string, "hostname");
        ObjectUtil.checkNotNull(inetAddress, "address");
        ObjectUtil.checkNotNull(eventLoop, "loop");
        DefaultDnsCacheEntry defaultDnsCacheEntry = new DefaultDnsCacheEntry(string, inetAddress);
        if (this.maxTtl == 0 || !DefaultDnsCache.emptyAdditionals(dnsRecordArray)) {
            return defaultDnsCacheEntry;
        }
        this.cache0(defaultDnsCacheEntry, Math.max(this.minTtl, Math.min(MAX_SUPPORTED_TTL_SECS, (int)Math.min((long)this.maxTtl, l))), eventLoop);
        return defaultDnsCacheEntry;
    }

    @Override
    public DnsCacheEntry cache(String string, DnsRecord[] dnsRecordArray, Throwable throwable, EventLoop eventLoop) {
        ObjectUtil.checkNotNull(string, "hostname");
        ObjectUtil.checkNotNull(throwable, "cause");
        ObjectUtil.checkNotNull(eventLoop, "loop");
        DefaultDnsCacheEntry defaultDnsCacheEntry = new DefaultDnsCacheEntry(string, throwable);
        if (this.negativeTtl == 0 || !DefaultDnsCache.emptyAdditionals(dnsRecordArray)) {
            return defaultDnsCacheEntry;
        }
        this.cache0(defaultDnsCacheEntry, Math.min(MAX_SUPPORTED_TTL_SECS, this.negativeTtl), eventLoop);
        return defaultDnsCacheEntry;
    }

    private void cache0(DefaultDnsCacheEntry defaultDnsCacheEntry, int n, EventLoop eventLoop) {
        Entries entries = (Entries)this.resolveCache.get(defaultDnsCacheEntry.hostname());
        if (entries == null) {
            entries = new Entries(defaultDnsCacheEntry);
            Entries entries2 = this.resolveCache.putIfAbsent(defaultDnsCacheEntry.hostname(), entries);
            if (entries2 != null) {
                entries = entries2;
            }
        }
        entries.add(defaultDnsCacheEntry);
        this.scheduleCacheExpiration(defaultDnsCacheEntry, n, eventLoop);
    }

    private void scheduleCacheExpiration(DefaultDnsCacheEntry defaultDnsCacheEntry, int n, EventLoop eventLoop) {
        defaultDnsCacheEntry.scheduleExpiration(eventLoop, new Runnable(this, defaultDnsCacheEntry){
            final DefaultDnsCacheEntry val$e;
            final DefaultDnsCache this$0;
            {
                this.this$0 = defaultDnsCache;
                this.val$e = defaultDnsCacheEntry;
            }

            @Override
            public void run() {
                Entries entries = (Entries)DefaultDnsCache.access$100(this.this$0).remove(DefaultDnsCacheEntry.access$000(this.val$e));
                if (entries != null) {
                    entries.clearAndCancel();
                }
            }
        }, n, TimeUnit.SECONDS);
    }

    public String toString() {
        return "DefaultDnsCache(minTtl=" + this.minTtl + ", maxTtl=" + this.maxTtl + ", negativeTtl=" + this.negativeTtl + ", cached resolved hostname=" + this.resolveCache.size() + ")";
    }

    static ConcurrentMap access$100(DefaultDnsCache defaultDnsCache) {
        return defaultDnsCache.resolveCache;
    }

    private static final class Entries
    extends AtomicReference<List<DefaultDnsCacheEntry>> {
        static final boolean $assertionsDisabled = !DefaultDnsCache.class.desiredAssertionStatus();

        Entries(DefaultDnsCacheEntry defaultDnsCacheEntry) {
            super(Collections.singletonList(defaultDnsCacheEntry));
        }

        void add(DefaultDnsCacheEntry defaultDnsCacheEntry) {
            if (defaultDnsCacheEntry.cause() == null) {
                while (true) {
                    List list;
                    if (!(list = (List)this.get()).isEmpty()) {
                        DefaultDnsCacheEntry defaultDnsCacheEntry2 = (DefaultDnsCacheEntry)list.get(0);
                        if (defaultDnsCacheEntry2.cause() != null) {
                            if (!$assertionsDisabled && list.size() != 1) {
                                throw new AssertionError();
                            }
                            if (!this.compareAndSet(list, Collections.singletonList(defaultDnsCacheEntry))) continue;
                            defaultDnsCacheEntry2.cancelExpiration();
                            return;
                        }
                        ArrayList<DefaultDnsCacheEntry> arrayList = new ArrayList<DefaultDnsCacheEntry>(list.size() + 1);
                        DefaultDnsCacheEntry defaultDnsCacheEntry3 = null;
                        for (int i = 0; i < list.size(); ++i) {
                            DefaultDnsCacheEntry defaultDnsCacheEntry4 = (DefaultDnsCacheEntry)list.get(i);
                            if (!defaultDnsCacheEntry.address().equals(defaultDnsCacheEntry4.address())) {
                                arrayList.add(defaultDnsCacheEntry4);
                                continue;
                            }
                            if (!$assertionsDisabled && defaultDnsCacheEntry3 != null) {
                                throw new AssertionError();
                            }
                            defaultDnsCacheEntry3 = defaultDnsCacheEntry4;
                        }
                        arrayList.add(defaultDnsCacheEntry);
                        if (!this.compareAndSet(list, arrayList)) continue;
                        if (defaultDnsCacheEntry3 != null) {
                            defaultDnsCacheEntry3.cancelExpiration();
                        }
                        return;
                    }
                    if (this.compareAndSet(list, Collections.singletonList(defaultDnsCacheEntry))) break;
                }
                return;
            }
            List<DefaultDnsCacheEntry> list = this.getAndSet(Collections.singletonList(defaultDnsCacheEntry));
            Entries.cancelExpiration(list);
        }

        boolean clearAndCancel() {
            List<DefaultDnsCacheEntry> list = this.getAndSet(Collections.emptyList());
            if (list.isEmpty()) {
                return true;
            }
            Entries.cancelExpiration(list);
            return false;
        }

        private static void cancelExpiration(List<DefaultDnsCacheEntry> list) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                list.get(i).cancelExpiration();
            }
        }
    }

    private static final class DefaultDnsCacheEntry
    implements DnsCacheEntry {
        private final String hostname;
        private final InetAddress address;
        private final Throwable cause;
        private volatile ScheduledFuture<?> expirationFuture;
        static final boolean $assertionsDisabled = !DefaultDnsCache.class.desiredAssertionStatus();

        DefaultDnsCacheEntry(String string, InetAddress inetAddress) {
            this.hostname = ObjectUtil.checkNotNull(string, "hostname");
            this.address = ObjectUtil.checkNotNull(inetAddress, "address");
            this.cause = null;
        }

        DefaultDnsCacheEntry(String string, Throwable throwable) {
            this.hostname = ObjectUtil.checkNotNull(string, "hostname");
            this.cause = ObjectUtil.checkNotNull(throwable, "cause");
            this.address = null;
        }

        @Override
        public InetAddress address() {
            return this.address;
        }

        @Override
        public Throwable cause() {
            return this.cause;
        }

        String hostname() {
            return this.hostname;
        }

        void scheduleExpiration(EventLoop eventLoop, Runnable runnable, long l, TimeUnit timeUnit) {
            if (!$assertionsDisabled && this.expirationFuture != null) {
                throw new AssertionError((Object)"expiration task scheduled already");
            }
            this.expirationFuture = eventLoop.schedule(runnable, l, timeUnit);
        }

        void cancelExpiration() {
            ScheduledFuture<?> scheduledFuture = this.expirationFuture;
            if (scheduledFuture != null) {
                scheduledFuture.cancel(false);
            }
        }

        public String toString() {
            if (this.cause != null) {
                return this.hostname + '/' + this.cause;
            }
            return this.address.toString();
        }

        static String access$000(DefaultDnsCacheEntry defaultDnsCacheEntry) {
            return defaultDnsCacheEntry.hostname;
        }
    }
}

