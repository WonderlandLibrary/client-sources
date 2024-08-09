/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.ReflectiveChannelFactory;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.resolver.HostsFileEntriesResolver;
import io.netty.resolver.ResolvedAddressTypes;
import io.netty.resolver.dns.DefaultDnsCache;
import io.netty.resolver.dns.DnsCache;
import io.netty.resolver.dns.DnsNameResolver;
import io.netty.resolver.dns.DnsQueryLifecycleObserverFactory;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import io.netty.resolver.dns.DnsServerAddressStreamProviders;
import io.netty.resolver.dns.NoopDnsQueryLifecycleObserverFactory;
import io.netty.util.internal.ObjectUtil;
import java.util.ArrayList;
import java.util.Arrays;

public final class DnsNameResolverBuilder {
    private EventLoop eventLoop;
    private ChannelFactory<? extends DatagramChannel> channelFactory;
    private DnsCache resolveCache;
    private DnsCache authoritativeDnsServerCache;
    private Integer minTtl;
    private Integer maxTtl;
    private Integer negativeTtl;
    private long queryTimeoutMillis = 5000L;
    private ResolvedAddressTypes resolvedAddressTypes = DnsNameResolver.DEFAULT_RESOLVE_ADDRESS_TYPES;
    private boolean recursionDesired = true;
    private int maxQueriesPerResolve = 16;
    private boolean traceEnabled;
    private int maxPayloadSize = 4096;
    private boolean optResourceEnabled = true;
    private HostsFileEntriesResolver hostsFileEntriesResolver = HostsFileEntriesResolver.DEFAULT;
    private DnsServerAddressStreamProvider dnsServerAddressStreamProvider = DnsServerAddressStreamProviders.platformDefault();
    private DnsQueryLifecycleObserverFactory dnsQueryLifecycleObserverFactory = NoopDnsQueryLifecycleObserverFactory.INSTANCE;
    private String[] searchDomains;
    private int ndots = -1;
    private boolean decodeIdn = true;

    public DnsNameResolverBuilder() {
    }

    public DnsNameResolverBuilder(EventLoop eventLoop) {
        this.eventLoop(eventLoop);
    }

    public DnsNameResolverBuilder eventLoop(EventLoop eventLoop) {
        this.eventLoop = eventLoop;
        return this;
    }

    protected ChannelFactory<? extends DatagramChannel> channelFactory() {
        return this.channelFactory;
    }

    public DnsNameResolverBuilder channelFactory(ChannelFactory<? extends DatagramChannel> channelFactory) {
        this.channelFactory = channelFactory;
        return this;
    }

    public DnsNameResolverBuilder channelType(Class<? extends DatagramChannel> clazz) {
        return this.channelFactory(new ReflectiveChannelFactory<DatagramChannel>(clazz));
    }

    public DnsNameResolverBuilder resolveCache(DnsCache dnsCache) {
        this.resolveCache = dnsCache;
        return this;
    }

    public DnsNameResolverBuilder dnsQueryLifecycleObserverFactory(DnsQueryLifecycleObserverFactory dnsQueryLifecycleObserverFactory) {
        this.dnsQueryLifecycleObserverFactory = ObjectUtil.checkNotNull(dnsQueryLifecycleObserverFactory, "lifecycleObserverFactory");
        return this;
    }

    public DnsNameResolverBuilder authoritativeDnsServerCache(DnsCache dnsCache) {
        this.authoritativeDnsServerCache = dnsCache;
        return this;
    }

    public DnsNameResolverBuilder ttl(int n, int n2) {
        this.maxTtl = n2;
        this.minTtl = n;
        return this;
    }

    public DnsNameResolverBuilder negativeTtl(int n) {
        this.negativeTtl = n;
        return this;
    }

    public DnsNameResolverBuilder queryTimeoutMillis(long l) {
        this.queryTimeoutMillis = l;
        return this;
    }

    public static ResolvedAddressTypes computeResolvedAddressTypes(InternetProtocolFamily ... internetProtocolFamilyArray) {
        if (internetProtocolFamilyArray == null || internetProtocolFamilyArray.length == 0) {
            return DnsNameResolver.DEFAULT_RESOLVE_ADDRESS_TYPES;
        }
        if (internetProtocolFamilyArray.length > 2) {
            throw new IllegalArgumentException("No more than 2 InternetProtocolFamilies");
        }
        switch (1.$SwitchMap$io$netty$channel$socket$InternetProtocolFamily[internetProtocolFamilyArray[0].ordinal()]) {
            case 1: {
                return internetProtocolFamilyArray.length >= 2 && internetProtocolFamilyArray[5] == InternetProtocolFamily.IPv6 ? ResolvedAddressTypes.IPV4_PREFERRED : ResolvedAddressTypes.IPV4_ONLY;
            }
            case 2: {
                return internetProtocolFamilyArray.length >= 2 && internetProtocolFamilyArray[5] == InternetProtocolFamily.IPv4 ? ResolvedAddressTypes.IPV6_PREFERRED : ResolvedAddressTypes.IPV6_ONLY;
            }
        }
        throw new IllegalArgumentException("Couldn't resolve ResolvedAddressTypes from InternetProtocolFamily array");
    }

    public DnsNameResolverBuilder resolvedAddressTypes(ResolvedAddressTypes resolvedAddressTypes) {
        this.resolvedAddressTypes = resolvedAddressTypes;
        return this;
    }

    public DnsNameResolverBuilder recursionDesired(boolean bl) {
        this.recursionDesired = bl;
        return this;
    }

    public DnsNameResolverBuilder maxQueriesPerResolve(int n) {
        this.maxQueriesPerResolve = n;
        return this;
    }

    public DnsNameResolverBuilder traceEnabled(boolean bl) {
        this.traceEnabled = bl;
        return this;
    }

    public DnsNameResolverBuilder maxPayloadSize(int n) {
        this.maxPayloadSize = n;
        return this;
    }

    public DnsNameResolverBuilder optResourceEnabled(boolean bl) {
        this.optResourceEnabled = bl;
        return this;
    }

    public DnsNameResolverBuilder hostsFileEntriesResolver(HostsFileEntriesResolver hostsFileEntriesResolver) {
        this.hostsFileEntriesResolver = hostsFileEntriesResolver;
        return this;
    }

    protected DnsServerAddressStreamProvider nameServerProvider() {
        return this.dnsServerAddressStreamProvider;
    }

    public DnsNameResolverBuilder nameServerProvider(DnsServerAddressStreamProvider dnsServerAddressStreamProvider) {
        this.dnsServerAddressStreamProvider = ObjectUtil.checkNotNull(dnsServerAddressStreamProvider, "dnsServerAddressStreamProvider");
        return this;
    }

    public DnsNameResolverBuilder searchDomains(Iterable<String> iterable) {
        ObjectUtil.checkNotNull(iterable, "searchDomains");
        ArrayList<String> arrayList = new ArrayList<String>(4);
        for (String string : iterable) {
            if (string == null) break;
            if (arrayList.contains(string)) continue;
            arrayList.add(string);
        }
        this.searchDomains = arrayList.toArray(new String[arrayList.size()]);
        return this;
    }

    public DnsNameResolverBuilder ndots(int n) {
        this.ndots = n;
        return this;
    }

    private DnsCache newCache() {
        return new DefaultDnsCache(ObjectUtil.intValue(this.minTtl, 0), ObjectUtil.intValue(this.maxTtl, Integer.MAX_VALUE), ObjectUtil.intValue(this.negativeTtl, 0));
    }

    public DnsNameResolverBuilder decodeIdn(boolean bl) {
        this.decodeIdn = bl;
        return this;
    }

    public DnsNameResolver build() {
        if (this.eventLoop == null) {
            throw new IllegalStateException("eventLoop should be specified to build a DnsNameResolver.");
        }
        if (this.resolveCache != null && (this.minTtl != null || this.maxTtl != null || this.negativeTtl != null)) {
            throw new IllegalStateException("resolveCache and TTLs are mutually exclusive");
        }
        if (this.authoritativeDnsServerCache != null && (this.minTtl != null || this.maxTtl != null || this.negativeTtl != null)) {
            throw new IllegalStateException("authoritativeDnsServerCache and TTLs are mutually exclusive");
        }
        DnsCache dnsCache = this.resolveCache != null ? this.resolveCache : this.newCache();
        DnsCache dnsCache2 = this.authoritativeDnsServerCache != null ? this.authoritativeDnsServerCache : this.newCache();
        return new DnsNameResolver(this.eventLoop, this.channelFactory, dnsCache, dnsCache2, this.dnsQueryLifecycleObserverFactory, this.queryTimeoutMillis, this.resolvedAddressTypes, this.recursionDesired, this.maxQueriesPerResolve, this.traceEnabled, this.maxPayloadSize, this.optResourceEnabled, this.hostsFileEntriesResolver, this.dnsServerAddressStreamProvider, this.searchDomains, this.ndots, this.decodeIdn);
    }

    public DnsNameResolverBuilder copy() {
        DnsNameResolverBuilder dnsNameResolverBuilder = new DnsNameResolverBuilder();
        if (this.eventLoop != null) {
            dnsNameResolverBuilder.eventLoop(this.eventLoop);
        }
        if (this.channelFactory != null) {
            dnsNameResolverBuilder.channelFactory(this.channelFactory);
        }
        if (this.resolveCache != null) {
            dnsNameResolverBuilder.resolveCache(this.resolveCache);
        }
        if (this.maxTtl != null && this.minTtl != null) {
            dnsNameResolverBuilder.ttl(this.minTtl, this.maxTtl);
        }
        if (this.negativeTtl != null) {
            dnsNameResolverBuilder.negativeTtl(this.negativeTtl);
        }
        if (this.authoritativeDnsServerCache != null) {
            dnsNameResolverBuilder.authoritativeDnsServerCache(this.authoritativeDnsServerCache);
        }
        if (this.dnsQueryLifecycleObserverFactory != null) {
            dnsNameResolverBuilder.dnsQueryLifecycleObserverFactory(this.dnsQueryLifecycleObserverFactory);
        }
        dnsNameResolverBuilder.queryTimeoutMillis(this.queryTimeoutMillis);
        dnsNameResolverBuilder.resolvedAddressTypes(this.resolvedAddressTypes);
        dnsNameResolverBuilder.recursionDesired(this.recursionDesired);
        dnsNameResolverBuilder.maxQueriesPerResolve(this.maxQueriesPerResolve);
        dnsNameResolverBuilder.traceEnabled(this.traceEnabled);
        dnsNameResolverBuilder.maxPayloadSize(this.maxPayloadSize);
        dnsNameResolverBuilder.optResourceEnabled(this.optResourceEnabled);
        dnsNameResolverBuilder.hostsFileEntriesResolver(this.hostsFileEntriesResolver);
        if (this.dnsServerAddressStreamProvider != null) {
            dnsNameResolverBuilder.nameServerProvider(this.dnsServerAddressStreamProvider);
        }
        if (this.searchDomains != null) {
            dnsNameResolverBuilder.searchDomains(Arrays.asList(this.searchDomains));
        }
        dnsNameResolverBuilder.ndots(this.ndots);
        dnsNameResolverBuilder.decodeIdn(this.decodeIdn);
        return dnsNameResolverBuilder;
    }
}

