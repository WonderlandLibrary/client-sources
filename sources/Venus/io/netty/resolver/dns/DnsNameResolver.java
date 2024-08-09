/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.InternetProtocolFamily;
import io.netty.handler.codec.dns.DatagramDnsQueryEncoder;
import io.netty.handler.codec.dns.DatagramDnsResponse;
import io.netty.handler.codec.dns.DatagramDnsResponseDecoder;
import io.netty.handler.codec.dns.DefaultDnsRawRecord;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRawRecord;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.resolver.HostsFileEntriesResolver;
import io.netty.resolver.InetNameResolver;
import io.netty.resolver.ResolvedAddressTypes;
import io.netty.resolver.dns.BiDnsQueryLifecycleObserverFactory;
import io.netty.resolver.dns.DnsAddressResolveContext;
import io.netty.resolver.dns.DnsCache;
import io.netty.resolver.dns.DnsCacheEntry;
import io.netty.resolver.dns.DnsNameResolverException;
import io.netty.resolver.dns.DnsNameResolverTimeoutException;
import io.netty.resolver.dns.DnsQueryContext;
import io.netty.resolver.dns.DnsQueryContextManager;
import io.netty.resolver.dns.DnsQueryLifecycleObserverFactory;
import io.netty.resolver.dns.DnsRecordResolveContext;
import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import io.netty.resolver.dns.DnsServerAddresses;
import io.netty.resolver.dns.NoopDnsQueryLifecycleObserverFactory;
import io.netty.resolver.dns.TraceDnsQueryLifeCycleObserverFactory;
import io.netty.resolver.dns.UnixResolverDnsServerAddressStreamProvider;
import io.netty.util.NetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.reflect.Method;
import java.net.IDN;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DnsNameResolver
extends InetNameResolver {
    private static final InternalLogger logger;
    private static final String LOCALHOST = "localhost";
    private static final InetAddress LOCALHOST_ADDRESS;
    private static final DnsRecord[] EMPTY_ADDITIONALS;
    private static final DnsRecordType[] IPV4_ONLY_RESOLVED_RECORD_TYPES;
    private static final InternetProtocolFamily[] IPV4_ONLY_RESOLVED_PROTOCOL_FAMILIES;
    private static final DnsRecordType[] IPV4_PREFERRED_RESOLVED_RECORD_TYPES;
    private static final InternetProtocolFamily[] IPV4_PREFERRED_RESOLVED_PROTOCOL_FAMILIES;
    private static final DnsRecordType[] IPV6_ONLY_RESOLVED_RECORD_TYPES;
    private static final InternetProtocolFamily[] IPV6_ONLY_RESOLVED_PROTOCOL_FAMILIES;
    private static final DnsRecordType[] IPV6_PREFERRED_RESOLVED_RECORD_TYPES;
    private static final InternetProtocolFamily[] IPV6_PREFERRED_RESOLVED_PROTOCOL_FAMILIES;
    static final ResolvedAddressTypes DEFAULT_RESOLVE_ADDRESS_TYPES;
    static final String[] DEFAULT_SEARCH_DOMAINS;
    private static final int DEFAULT_NDOTS;
    private static final DatagramDnsResponseDecoder DECODER;
    private static final DatagramDnsQueryEncoder ENCODER;
    final Future<Channel> channelFuture;
    final DatagramChannel ch;
    final DnsQueryContextManager queryContextManager = new DnsQueryContextManager();
    private final DnsCache resolveCache;
    private final DnsCache authoritativeDnsServerCache;
    private final FastThreadLocal<DnsServerAddressStream> nameServerAddrStream = new FastThreadLocal<DnsServerAddressStream>(this){
        final DnsNameResolver this$0;
        {
            this.this$0 = dnsNameResolver;
        }

        @Override
        protected DnsServerAddressStream initialValue() throws Exception {
            return DnsNameResolver.access$000(this.this$0).nameServerAddressStream("");
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    };
    private final long queryTimeoutMillis;
    private final int maxQueriesPerResolve;
    private final ResolvedAddressTypes resolvedAddressTypes;
    private final InternetProtocolFamily[] resolvedInternetProtocolFamilies;
    private final boolean recursionDesired;
    private final int maxPayloadSize;
    private final boolean optResourceEnabled;
    private final HostsFileEntriesResolver hostsFileEntriesResolver;
    private final DnsServerAddressStreamProvider dnsServerAddressStreamProvider;
    private final String[] searchDomains;
    private final int ndots;
    private final boolean supportsAAAARecords;
    private final boolean supportsARecords;
    private final InternetProtocolFamily preferredAddressType;
    private final DnsRecordType[] resolveRecordTypes;
    private final boolean decodeIdn;
    private final DnsQueryLifecycleObserverFactory dnsQueryLifecycleObserverFactory;
    static final boolean $assertionsDisabled;

    public DnsNameResolver(EventLoop eventLoop, ChannelFactory<? extends DatagramChannel> channelFactory, DnsCache dnsCache, DnsCache dnsCache2, DnsQueryLifecycleObserverFactory dnsQueryLifecycleObserverFactory, long l, ResolvedAddressTypes resolvedAddressTypes, boolean bl, int n, boolean bl2, int n2, boolean bl3, HostsFileEntriesResolver hostsFileEntriesResolver, DnsServerAddressStreamProvider dnsServerAddressStreamProvider, String[] stringArray, int n3, boolean bl4) {
        super(eventLoop);
        this.queryTimeoutMillis = ObjectUtil.checkPositive(l, "queryTimeoutMillis");
        this.resolvedAddressTypes = resolvedAddressTypes != null ? resolvedAddressTypes : DEFAULT_RESOLVE_ADDRESS_TYPES;
        this.recursionDesired = bl;
        this.maxQueriesPerResolve = ObjectUtil.checkPositive(n, "maxQueriesPerResolve");
        this.maxPayloadSize = ObjectUtil.checkPositive(n2, "maxPayloadSize");
        this.optResourceEnabled = bl3;
        this.hostsFileEntriesResolver = ObjectUtil.checkNotNull(hostsFileEntriesResolver, "hostsFileEntriesResolver");
        this.dnsServerAddressStreamProvider = ObjectUtil.checkNotNull(dnsServerAddressStreamProvider, "dnsServerAddressStreamProvider");
        this.resolveCache = ObjectUtil.checkNotNull(dnsCache, "resolveCache");
        this.authoritativeDnsServerCache = ObjectUtil.checkNotNull(dnsCache2, "authoritativeDnsServerCache");
        this.dnsQueryLifecycleObserverFactory = bl2 ? (dnsQueryLifecycleObserverFactory instanceof NoopDnsQueryLifecycleObserverFactory ? new TraceDnsQueryLifeCycleObserverFactory() : new BiDnsQueryLifecycleObserverFactory(new TraceDnsQueryLifeCycleObserverFactory(), dnsQueryLifecycleObserverFactory)) : ObjectUtil.checkNotNull(dnsQueryLifecycleObserverFactory, "dnsQueryLifecycleObserverFactory");
        this.searchDomains = stringArray != null ? (String[])stringArray.clone() : DEFAULT_SEARCH_DOMAINS;
        this.ndots = n3 >= 0 ? n3 : DEFAULT_NDOTS;
        this.decodeIdn = bl4;
        switch (5.$SwitchMap$io$netty$resolver$ResolvedAddressTypes[this.resolvedAddressTypes.ordinal()]) {
            case 1: {
                this.supportsAAAARecords = false;
                this.supportsARecords = true;
                this.resolveRecordTypes = IPV4_ONLY_RESOLVED_RECORD_TYPES;
                this.resolvedInternetProtocolFamilies = IPV4_ONLY_RESOLVED_PROTOCOL_FAMILIES;
                this.preferredAddressType = InternetProtocolFamily.IPv4;
                break;
            }
            case 2: {
                this.supportsAAAARecords = true;
                this.supportsARecords = true;
                this.resolveRecordTypes = IPV4_PREFERRED_RESOLVED_RECORD_TYPES;
                this.resolvedInternetProtocolFamilies = IPV4_PREFERRED_RESOLVED_PROTOCOL_FAMILIES;
                this.preferredAddressType = InternetProtocolFamily.IPv4;
                break;
            }
            case 3: {
                this.supportsAAAARecords = true;
                this.supportsARecords = false;
                this.resolveRecordTypes = IPV6_ONLY_RESOLVED_RECORD_TYPES;
                this.resolvedInternetProtocolFamilies = IPV6_ONLY_RESOLVED_PROTOCOL_FAMILIES;
                this.preferredAddressType = InternetProtocolFamily.IPv6;
                break;
            }
            case 4: {
                this.supportsAAAARecords = true;
                this.supportsARecords = true;
                this.resolveRecordTypes = IPV6_PREFERRED_RESOLVED_RECORD_TYPES;
                this.resolvedInternetProtocolFamilies = IPV6_PREFERRED_RESOLVED_PROTOCOL_FAMILIES;
                this.preferredAddressType = InternetProtocolFamily.IPv6;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown ResolvedAddressTypes " + (Object)((Object)resolvedAddressTypes));
            }
        }
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(this.executor());
        bootstrap.channelFactory(channelFactory);
        bootstrap.option(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION, true);
        DnsResponseHandler dnsResponseHandler = new DnsResponseHandler(this, this.executor().newPromise());
        bootstrap.handler(new ChannelInitializer<DatagramChannel>(this, dnsResponseHandler){
            final DnsResponseHandler val$responseHandler;
            final DnsNameResolver this$0;
            {
                this.this$0 = dnsNameResolver;
                this.val$responseHandler = dnsResponseHandler;
            }

            @Override
            protected void initChannel(DatagramChannel datagramChannel) throws Exception {
                datagramChannel.pipeline().addLast(DnsNameResolver.access$100(), DnsNameResolver.access$200(), this.val$responseHandler);
            }

            @Override
            protected void initChannel(Channel channel) throws Exception {
                this.initChannel((DatagramChannel)channel);
            }
        });
        this.channelFuture = DnsResponseHandler.access$300(dnsResponseHandler);
        this.ch = (DatagramChannel)bootstrap.register().channel();
        this.ch.config().setRecvByteBufAllocator(new FixedRecvByteBufAllocator(n2));
        this.ch.closeFuture().addListener(new ChannelFutureListener(this, dnsCache){
            final DnsCache val$resolveCache;
            final DnsNameResolver this$0;
            {
                this.this$0 = dnsNameResolver;
                this.val$resolveCache = dnsCache;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                this.val$resolveCache.clear();
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }

    int dnsRedirectPort(InetAddress inetAddress) {
        return 0;
    }

    final DnsQueryLifecycleObserverFactory dnsQueryLifecycleObserverFactory() {
        return this.dnsQueryLifecycleObserverFactory;
    }

    protected DnsServerAddressStream uncachedRedirectDnsServerStream(List<InetSocketAddress> list) {
        return DnsServerAddresses.sequential(list).stream();
    }

    public DnsCache resolveCache() {
        return this.resolveCache;
    }

    public DnsCache authoritativeDnsServerCache() {
        return this.authoritativeDnsServerCache;
    }

    public long queryTimeoutMillis() {
        return this.queryTimeoutMillis;
    }

    public ResolvedAddressTypes resolvedAddressTypes() {
        return this.resolvedAddressTypes;
    }

    InternetProtocolFamily[] resolvedInternetProtocolFamiliesUnsafe() {
        return this.resolvedInternetProtocolFamilies;
    }

    final String[] searchDomains() {
        return this.searchDomains;
    }

    final int ndots() {
        return this.ndots;
    }

    final boolean supportsAAAARecords() {
        return this.supportsAAAARecords;
    }

    final boolean supportsARecords() {
        return this.supportsARecords;
    }

    final InternetProtocolFamily preferredAddressType() {
        return this.preferredAddressType;
    }

    final DnsRecordType[] resolveRecordTypes() {
        return this.resolveRecordTypes;
    }

    final boolean isDecodeIdn() {
        return this.decodeIdn;
    }

    public boolean isRecursionDesired() {
        return this.recursionDesired;
    }

    public int maxQueriesPerResolve() {
        return this.maxQueriesPerResolve;
    }

    public int maxPayloadSize() {
        return this.maxPayloadSize;
    }

    public boolean isOptResourceEnabled() {
        return this.optResourceEnabled;
    }

    public HostsFileEntriesResolver hostsFileEntriesResolver() {
        return this.hostsFileEntriesResolver;
    }

    @Override
    public void close() {
        if (this.ch.isOpen()) {
            this.ch.close();
        }
    }

    @Override
    protected EventLoop executor() {
        return (EventLoop)super.executor();
    }

    private InetAddress resolveHostsFileEntry(String string) {
        if (this.hostsFileEntriesResolver == null) {
            return null;
        }
        InetAddress inetAddress = this.hostsFileEntriesResolver.address(string, this.resolvedAddressTypes);
        if (inetAddress == null && PlatformDependent.isWindows() && LOCALHOST.equalsIgnoreCase(string)) {
            return LOCALHOST_ADDRESS;
        }
        return inetAddress;
    }

    @Override
    public final Future<InetAddress> resolve(String string, Iterable<DnsRecord> iterable) {
        return this.resolve(string, iterable, this.executor().newPromise());
    }

    public final Future<InetAddress> resolve(String string, Iterable<DnsRecord> iterable, Promise<InetAddress> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        DnsRecord[] dnsRecordArray = DnsNameResolver.toArray(iterable, true);
        try {
            this.doResolve(string, dnsRecordArray, promise, this.resolveCache);
            return promise;
        } catch (Exception exception) {
            return promise.setFailure(exception);
        }
    }

    public final Future<List<InetAddress>> resolveAll(String string, Iterable<DnsRecord> iterable) {
        return this.resolveAll(string, iterable, this.executor().newPromise());
    }

    public final Future<List<InetAddress>> resolveAll(String string, Iterable<DnsRecord> iterable, Promise<List<InetAddress>> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        DnsRecord[] dnsRecordArray = DnsNameResolver.toArray(iterable, true);
        try {
            this.doResolveAll(string, dnsRecordArray, promise, this.resolveCache);
            return promise;
        } catch (Exception exception) {
            return promise.setFailure(exception);
        }
    }

    @Override
    protected void doResolve(String string, Promise<InetAddress> promise) throws Exception {
        this.doResolve(string, EMPTY_ADDITIONALS, promise, this.resolveCache);
    }

    public final Future<List<DnsRecord>> resolveAll(DnsQuestion dnsQuestion) {
        return this.resolveAll(dnsQuestion, EMPTY_ADDITIONALS, this.executor().newPromise());
    }

    public final Future<List<DnsRecord>> resolveAll(DnsQuestion dnsQuestion, Iterable<DnsRecord> iterable) {
        return this.resolveAll(dnsQuestion, iterable, this.executor().newPromise());
    }

    public final Future<List<DnsRecord>> resolveAll(DnsQuestion dnsQuestion, Iterable<DnsRecord> iterable, Promise<List<DnsRecord>> promise) {
        DnsRecord[] dnsRecordArray = DnsNameResolver.toArray(iterable, true);
        return this.resolveAll(dnsQuestion, dnsRecordArray, promise);
    }

    private Future<List<DnsRecord>> resolveAll(DnsQuestion dnsQuestion, DnsRecord[] dnsRecordArray, Promise<List<DnsRecord>> promise) {
        Object object;
        ObjectUtil.checkNotNull(dnsQuestion, "question");
        ObjectUtil.checkNotNull(promise, "promise");
        DnsRecordType dnsRecordType = dnsQuestion.type();
        String string = dnsQuestion.name();
        if ((dnsRecordType == DnsRecordType.A || dnsRecordType == DnsRecordType.AAAA) && (object = this.resolveHostsFileEntry(string)) != null) {
            ByteBuf byteBuf = null;
            if (object instanceof Inet4Address) {
                if (dnsRecordType == DnsRecordType.A) {
                    byteBuf = Unpooled.wrappedBuffer(((InetAddress)object).getAddress());
                }
            } else if (object instanceof Inet6Address && dnsRecordType == DnsRecordType.AAAA) {
                byteBuf = Unpooled.wrappedBuffer(((InetAddress)object).getAddress());
            }
            if (byteBuf != null) {
                DnsNameResolver.trySuccess(promise, Collections.singletonList(new DefaultDnsRawRecord(string, dnsRecordType, 86400L, byteBuf)));
                return promise;
            }
        }
        object = this.dnsServerAddressStreamProvider.nameServerAddressStream(string);
        new DnsRecordResolveContext(this, dnsQuestion, dnsRecordArray, (DnsServerAddressStream)object).resolve(promise);
        return promise;
    }

    private static DnsRecord[] toArray(Iterable<DnsRecord> iterable, boolean bl) {
        ObjectUtil.checkNotNull(iterable, "additionals");
        if (iterable instanceof Collection) {
            Collection collection = (Collection)iterable;
            for (DnsRecord dnsRecord : iterable) {
                DnsNameResolver.validateAdditional(dnsRecord, bl);
            }
            return collection.toArray(new DnsRecord[collection.size()]);
        }
        Iterator<DnsRecord> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) {
            return EMPTY_ADDITIONALS;
        }
        ArrayList<DnsRecord> arrayList = new ArrayList<DnsRecord>();
        do {
            DnsRecord dnsRecord = iterator2.next();
            DnsNameResolver.validateAdditional(dnsRecord, bl);
            arrayList.add(dnsRecord);
        } while (iterator2.hasNext());
        return arrayList.toArray(new DnsRecord[arrayList.size()]);
    }

    private static void validateAdditional(DnsRecord dnsRecord, boolean bl) {
        ObjectUtil.checkNotNull(dnsRecord, "record");
        if (bl && dnsRecord instanceof DnsRawRecord) {
            throw new IllegalArgumentException("DnsRawRecord implementations not allowed: " + dnsRecord);
        }
    }

    private InetAddress loopbackAddress() {
        return this.preferredAddressType().localhost();
    }

    protected void doResolve(String string, DnsRecord[] dnsRecordArray, Promise<InetAddress> promise, DnsCache dnsCache) throws Exception {
        if (string == null || string.isEmpty()) {
            promise.setSuccess(this.loopbackAddress());
            return;
        }
        byte[] byArray = NetUtil.createByteArrayFromIpAddressString(string);
        if (byArray != null) {
            promise.setSuccess(InetAddress.getByAddress(byArray));
            return;
        }
        String string2 = DnsNameResolver.hostname(string);
        InetAddress inetAddress = this.resolveHostsFileEntry(string2);
        if (inetAddress != null) {
            promise.setSuccess(inetAddress);
            return;
        }
        if (!this.doResolveCached(string2, dnsRecordArray, promise, dnsCache)) {
            this.doResolveUncached(string2, dnsRecordArray, promise, dnsCache);
        }
    }

    private boolean doResolveCached(String string, DnsRecord[] dnsRecordArray, Promise<InetAddress> promise, DnsCache dnsCache) {
        List<? extends DnsCacheEntry> list = dnsCache.get(string, dnsRecordArray);
        if (list == null || list.isEmpty()) {
            return true;
        }
        Throwable throwable = list.get(0).cause();
        if (throwable == null) {
            int n = list.size();
            for (InternetProtocolFamily internetProtocolFamily : this.resolvedInternetProtocolFamilies) {
                for (int i = 0; i < n; ++i) {
                    DnsCacheEntry dnsCacheEntry = list.get(i);
                    if (!internetProtocolFamily.addressType().isInstance(dnsCacheEntry.address())) continue;
                    DnsNameResolver.trySuccess(promise, dnsCacheEntry.address());
                    return false;
                }
            }
            return true;
        }
        DnsNameResolver.tryFailure(promise, throwable);
        return false;
    }

    static <T> void trySuccess(Promise<T> promise, T t) {
        if (!promise.trySuccess(t)) {
            logger.warn("Failed to notify success ({}) to a promise: {}", (Object)t, (Object)promise);
        }
    }

    private static void tryFailure(Promise<?> promise, Throwable throwable) {
        if (!promise.tryFailure(throwable)) {
            logger.warn("Failed to notify failure to a promise: {}", (Object)promise, (Object)throwable);
        }
    }

    private void doResolveUncached(String string, DnsRecord[] dnsRecordArray, Promise<InetAddress> promise, DnsCache dnsCache) {
        Promise<List<InetAddress>> promise2 = this.executor().newPromise();
        this.doResolveAllUncached(string, dnsRecordArray, promise2, dnsCache);
        promise2.addListener((GenericFutureListener<Future<List<InetAddress>>>)new FutureListener<List<InetAddress>>(this, promise){
            final Promise val$promise;
            final DnsNameResolver this$0;
            {
                this.this$0 = dnsNameResolver;
                this.val$promise = promise;
            }

            @Override
            public void operationComplete(Future<List<InetAddress>> future) {
                if (future.isSuccess()) {
                    DnsNameResolver.trySuccess(this.val$promise, future.getNow().get(0));
                } else {
                    DnsNameResolver.access$400(this.val$promise, future.cause());
                }
            }
        });
    }

    @Override
    protected void doResolveAll(String string, Promise<List<InetAddress>> promise) throws Exception {
        this.doResolveAll(string, EMPTY_ADDITIONALS, promise, this.resolveCache);
    }

    protected void doResolveAll(String string, DnsRecord[] dnsRecordArray, Promise<List<InetAddress>> promise, DnsCache dnsCache) throws Exception {
        if (string == null || string.isEmpty()) {
            promise.setSuccess(Collections.singletonList(this.loopbackAddress()));
            return;
        }
        byte[] byArray = NetUtil.createByteArrayFromIpAddressString(string);
        if (byArray != null) {
            promise.setSuccess(Collections.singletonList(InetAddress.getByAddress(byArray)));
            return;
        }
        String string2 = DnsNameResolver.hostname(string);
        InetAddress inetAddress = this.resolveHostsFileEntry(string2);
        if (inetAddress != null) {
            promise.setSuccess(Collections.singletonList(inetAddress));
            return;
        }
        if (!this.doResolveAllCached(string2, dnsRecordArray, promise, dnsCache)) {
            this.doResolveAllUncached(string2, dnsRecordArray, promise, dnsCache);
        }
    }

    private boolean doResolveAllCached(String string, DnsRecord[] dnsRecordArray, Promise<List<InetAddress>> promise, DnsCache dnsCache) {
        List<? extends DnsCacheEntry> list = dnsCache.get(string, dnsRecordArray);
        if (list == null || list.isEmpty()) {
            return true;
        }
        Throwable throwable = list.get(0).cause();
        if (throwable == null) {
            ArrayList<InetAddress> arrayList = null;
            int n = list.size();
            for (InternetProtocolFamily internetProtocolFamily : this.resolvedInternetProtocolFamilies) {
                for (int i = 0; i < n; ++i) {
                    DnsCacheEntry dnsCacheEntry = list.get(i);
                    if (!internetProtocolFamily.addressType().isInstance(dnsCacheEntry.address())) continue;
                    if (arrayList == null) {
                        arrayList = new ArrayList<InetAddress>(n);
                    }
                    arrayList.add(dnsCacheEntry.address());
                }
            }
            if (arrayList != null) {
                DnsNameResolver.trySuccess(promise, arrayList);
                return false;
            }
            return true;
        }
        DnsNameResolver.tryFailure(promise, throwable);
        return false;
    }

    private void doResolveAllUncached(String string, DnsRecord[] dnsRecordArray, Promise<List<InetAddress>> promise, DnsCache dnsCache) {
        DnsServerAddressStream dnsServerAddressStream = this.dnsServerAddressStreamProvider.nameServerAddressStream(string);
        new DnsAddressResolveContext(this, string, dnsRecordArray, dnsServerAddressStream, dnsCache).resolve(promise);
    }

    private static String hostname(String string) {
        String string2 = IDN.toASCII(string);
        if (StringUtil.endsWith(string, '.') && !StringUtil.endsWith(string2, '.')) {
            string2 = string2 + ".";
        }
        return string2;
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion dnsQuestion) {
        return this.query(this.nextNameServerAddress(), dnsQuestion);
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion dnsQuestion, Iterable<DnsRecord> iterable) {
        return this.query(this.nextNameServerAddress(), dnsQuestion, iterable);
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(DnsQuestion dnsQuestion, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        return this.query(this.nextNameServerAddress(), dnsQuestion, Collections.<DnsRecord>emptyList(), promise);
    }

    private InetSocketAddress nextNameServerAddress() {
        return this.nameServerAddrStream.get().next();
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion) {
        return this.query0(inetSocketAddress, dnsQuestion, EMPTY_ADDITIONALS, this.ch.eventLoop().newPromise());
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, Iterable<DnsRecord> iterable) {
        return this.query0(inetSocketAddress, dnsQuestion, DnsNameResolver.toArray(iterable, false), this.ch.eventLoop().newPromise());
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        return this.query0(inetSocketAddress, dnsQuestion, EMPTY_ADDITIONALS, promise);
    }

    public Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, Iterable<DnsRecord> iterable, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        return this.query0(inetSocketAddress, dnsQuestion, DnsNameResolver.toArray(iterable, false), promise);
    }

    public static boolean isTransportOrTimeoutError(Throwable throwable) {
        return throwable != null && throwable.getCause() instanceof DnsNameResolverException;
    }

    public static boolean isTimeoutError(Throwable throwable) {
        return throwable != null && throwable.getCause() instanceof DnsNameResolverTimeoutException;
    }

    final Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query0(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, DnsRecord[] dnsRecordArray, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        return this.query0(inetSocketAddress, dnsQuestion, dnsRecordArray, this.ch.newPromise(), promise);
    }

    final Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> query0(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, DnsRecord[] dnsRecordArray, ChannelPromise channelPromise, Promise<AddressedEnvelope<? extends DnsResponse, InetSocketAddress>> promise) {
        if (!$assertionsDisabled && channelPromise.isVoid()) {
            throw new AssertionError();
        }
        Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> promise2 = DnsNameResolver.cast(ObjectUtil.checkNotNull(promise, "promise"));
        try {
            new DnsQueryContext(this, inetSocketAddress, dnsQuestion, dnsRecordArray, promise2).query(channelPromise);
            return promise2;
        } catch (Exception exception) {
            return promise2.setFailure(exception);
        }
    }

    private static Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> cast(Promise<?> promise) {
        return promise;
    }

    @Override
    protected EventExecutor executor() {
        return this.executor();
    }

    static DnsServerAddressStreamProvider access$000(DnsNameResolver dnsNameResolver) {
        return dnsNameResolver.dnsServerAddressStreamProvider;
    }

    static DatagramDnsResponseDecoder access$100() {
        return DECODER;
    }

    static DatagramDnsQueryEncoder access$200() {
        return ENCODER;
    }

    static void access$400(Promise promise, Throwable throwable) {
        DnsNameResolver.tryFailure(promise, throwable);
    }

    static InternalLogger access$500() {
        return logger;
    }

    static {
        int n;
        String[] stringArray;
        $assertionsDisabled = !DnsNameResolver.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(DnsNameResolver.class);
        EMPTY_ADDITIONALS = new DnsRecord[0];
        IPV4_ONLY_RESOLVED_RECORD_TYPES = new DnsRecordType[]{DnsRecordType.A};
        IPV4_ONLY_RESOLVED_PROTOCOL_FAMILIES = new InternetProtocolFamily[]{InternetProtocolFamily.IPv4};
        IPV4_PREFERRED_RESOLVED_RECORD_TYPES = new DnsRecordType[]{DnsRecordType.A, DnsRecordType.AAAA};
        IPV4_PREFERRED_RESOLVED_PROTOCOL_FAMILIES = new InternetProtocolFamily[]{InternetProtocolFamily.IPv4, InternetProtocolFamily.IPv6};
        IPV6_ONLY_RESOLVED_RECORD_TYPES = new DnsRecordType[]{DnsRecordType.AAAA};
        IPV6_ONLY_RESOLVED_PROTOCOL_FAMILIES = new InternetProtocolFamily[]{InternetProtocolFamily.IPv6};
        IPV6_PREFERRED_RESOLVED_RECORD_TYPES = new DnsRecordType[]{DnsRecordType.AAAA, DnsRecordType.A};
        IPV6_PREFERRED_RESOLVED_PROTOCOL_FAMILIES = new InternetProtocolFamily[]{InternetProtocolFamily.IPv6, InternetProtocolFamily.IPv4};
        if (NetUtil.isIpV4StackPreferred()) {
            DEFAULT_RESOLVE_ADDRESS_TYPES = ResolvedAddressTypes.IPV4_ONLY;
            LOCALHOST_ADDRESS = NetUtil.LOCALHOST4;
        } else if (NetUtil.isIpV6AddressesPreferred()) {
            DEFAULT_RESOLVE_ADDRESS_TYPES = ResolvedAddressTypes.IPV6_PREFERRED;
            LOCALHOST_ADDRESS = NetUtil.LOCALHOST6;
        } else {
            DEFAULT_RESOLVE_ADDRESS_TYPES = ResolvedAddressTypes.IPV4_PREFERRED;
            LOCALHOST_ADDRESS = NetUtil.LOCALHOST4;
        }
        try {
            Class<?> clazz = Class.forName("sun.net.dns.ResolverConfiguration");
            Method method = clazz.getMethod("open", new Class[0]);
            Method method2 = clazz.getMethod("searchlist", new Class[0]);
            Object object = method.invoke(null, new Object[0]);
            List list = (List)method2.invoke(object, new Object[0]);
            stringArray = list.toArray(new String[list.size()]);
        } catch (Exception exception) {
            stringArray = EmptyArrays.EMPTY_STRINGS;
        }
        DEFAULT_SEARCH_DOMAINS = stringArray;
        try {
            n = UnixResolverDnsServerAddressStreamProvider.parseEtcResolverFirstNdots();
        } catch (Exception exception) {
            n = 1;
        }
        DEFAULT_NDOTS = n;
        DECODER = new DatagramDnsResponseDecoder();
        ENCODER = new DatagramDnsQueryEncoder();
    }

    private final class DnsResponseHandler
    extends ChannelInboundHandlerAdapter {
        private final Promise<Channel> channelActivePromise;
        final DnsNameResolver this$0;

        DnsResponseHandler(DnsNameResolver dnsNameResolver, Promise<Channel> promise) {
            this.this$0 = dnsNameResolver;
            this.channelActivePromise = promise;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
            try {
                DnsQueryContext dnsQueryContext;
                DatagramDnsResponse datagramDnsResponse = (DatagramDnsResponse)object;
                int n = datagramDnsResponse.id();
                if (DnsNameResolver.access$500().isDebugEnabled()) {
                    DnsNameResolver.access$500().debug("{} RECEIVED: [{}: {}], {}", this.this$0.ch, n, datagramDnsResponse.sender(), datagramDnsResponse);
                }
                if ((dnsQueryContext = this.this$0.queryContextManager.get(datagramDnsResponse.sender(), n)) == null) {
                    DnsNameResolver.access$500().warn("{} Received a DNS response with an unknown ID: {}", (Object)this.this$0.ch, (Object)n);
                    return;
                }
                dnsQueryContext.finish(datagramDnsResponse);
            } finally {
                ReferenceCountUtil.safeRelease(object);
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
            super.channelActive(channelHandlerContext);
            this.channelActivePromise.setSuccess(channelHandlerContext.channel());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
            DnsNameResolver.access$500().warn("{} Unexpected exception: ", (Object)this.this$0.ch, (Object)throwable);
        }

        static Promise access$300(DnsResponseHandler dnsResponseHandler) {
            return dnsResponseHandler.channelActivePromise;
        }
    }
}

