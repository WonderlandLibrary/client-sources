/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoop;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.dns.DefaultDnsQuestion;
import io.netty.handler.codec.dns.DefaultDnsRecordDecoder;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRawRecord;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsRecordType;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsResponseCode;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.resolver.dns.DnsAddressDecoder;
import io.netty.resolver.dns.DnsCacheEntry;
import io.netty.resolver.dns.DnsNameResolver;
import io.netty.resolver.dns.DnsQueryLifecycleObserver;
import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.DnsServerAddresses;
import io.netty.resolver.dns.NoopDnsQueryLifecycleObserver;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.ThrowableUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

abstract class DnsResolveContext<T> {
    private static final FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>> RELEASE_RESPONSE;
    private static final RuntimeException NXDOMAIN_QUERY_FAILED_EXCEPTION;
    private static final RuntimeException CNAME_NOT_FOUND_QUERY_FAILED_EXCEPTION;
    private static final RuntimeException NO_MATCHING_RECORD_QUERY_FAILED_EXCEPTION;
    private static final RuntimeException UNRECOGNIZED_TYPE_QUERY_FAILED_EXCEPTION;
    private static final RuntimeException NAME_SERVERS_EXHAUSTED_EXCEPTION;
    final DnsNameResolver parent;
    private final DnsServerAddressStream nameServerAddrs;
    private final String hostname;
    private final int dnsClass;
    private final DnsRecordType[] expectedTypes;
    private final int maxAllowedQueries;
    private final DnsRecord[] additionals;
    private final Set<Future<AddressedEnvelope<DnsResponse, InetSocketAddress>>> queriesInProgress = Collections.newSetFromMap(new IdentityHashMap());
    private List<T> finalResult;
    private int allowedQueries;
    private boolean triedCNAME;
    static final boolean $assertionsDisabled;

    DnsResolveContext(DnsNameResolver dnsNameResolver, String string, int n, DnsRecordType[] dnsRecordTypeArray, DnsRecord[] dnsRecordArray, DnsServerAddressStream dnsServerAddressStream) {
        if (!$assertionsDisabled && dnsRecordTypeArray.length <= 0) {
            throw new AssertionError();
        }
        this.parent = dnsNameResolver;
        this.hostname = string;
        this.dnsClass = n;
        this.expectedTypes = dnsRecordTypeArray;
        this.additionals = dnsRecordArray;
        this.nameServerAddrs = ObjectUtil.checkNotNull(dnsServerAddressStream, "nameServerAddrs");
        this.allowedQueries = this.maxAllowedQueries = dnsNameResolver.maxQueriesPerResolve();
    }

    abstract DnsResolveContext<T> newResolverContext(DnsNameResolver var1, String var2, int var3, DnsRecordType[] var4, DnsRecord[] var5, DnsServerAddressStream var6);

    abstract T convertRecord(DnsRecord var1, String var2, DnsRecord[] var3, EventLoop var4);

    abstract List<T> filterResults(List<T> var1);

    abstract void cache(String var1, DnsRecord[] var2, DnsRecord var3, T var4);

    abstract void cache(String var1, DnsRecord[] var2, UnknownHostException var3);

    void resolve(Promise<List<T>> promise) {
        String[] stringArray = this.parent.searchDomains();
        if (stringArray.length == 0 || this.parent.ndots() == 0 || StringUtil.endsWith(this.hostname, '.')) {
            this.internalResolve(promise);
        } else {
            boolean bl = this.hasNDots();
            String string = bl ? this.hostname : this.hostname + '.' + stringArray[0];
            int n = bl ? 0 : 1;
            this.doSearchDomainQuery(string, new FutureListener<List<T>>(this, n, promise, stringArray, bl){
                private int searchDomainIdx;
                final int val$initialSearchDomainIdx;
                final Promise val$promise;
                final String[] val$searchDomains;
                final boolean val$startWithoutSearchDomain;
                final DnsResolveContext this$0;
                {
                    this.this$0 = dnsResolveContext;
                    this.val$initialSearchDomainIdx = n;
                    this.val$promise = promise;
                    this.val$searchDomains = stringArray;
                    this.val$startWithoutSearchDomain = bl;
                    this.searchDomainIdx = this.val$initialSearchDomainIdx;
                }

                @Override
                public void operationComplete(Future<List<T>> future) {
                    Throwable throwable = future.cause();
                    if (throwable == null) {
                        this.val$promise.trySuccess(future.getNow());
                    } else if (DnsNameResolver.isTransportOrTimeoutError(throwable)) {
                        this.val$promise.tryFailure(new SearchDomainUnknownHostException(throwable, DnsResolveContext.access$000(this.this$0)));
                    } else if (this.searchDomainIdx < this.val$searchDomains.length) {
                        DnsResolveContext.access$100(this.this$0, DnsResolveContext.access$000(this.this$0) + '.' + this.val$searchDomains[this.searchDomainIdx++], this);
                    } else if (!this.val$startWithoutSearchDomain) {
                        DnsResolveContext.access$200(this.this$0, this.val$promise);
                    } else {
                        this.val$promise.tryFailure(new SearchDomainUnknownHostException(throwable, DnsResolveContext.access$000(this.this$0)));
                    }
                }
            });
        }
    }

    private boolean hasNDots() {
        int n = 0;
        for (int i = this.hostname.length() - 1; i >= 0; --i) {
            if (this.hostname.charAt(i) != '.' || ++n < this.parent.ndots()) continue;
            return false;
        }
        return true;
    }

    private void doSearchDomainQuery(String string, FutureListener<List<T>> futureListener) {
        DnsResolveContext<T> dnsResolveContext = this.newResolverContext(this.parent, string, this.dnsClass, this.expectedTypes, this.additionals, this.nameServerAddrs);
        Promise<List<T>> promise = this.parent.executor().newPromise();
        super.internalResolve(promise);
        promise.addListener(futureListener);
    }

    private void internalResolve(Promise<List<T>> promise) {
        DnsServerAddressStream dnsServerAddressStream = this.getNameServers(this.hostname);
        int n = this.expectedTypes.length - 1;
        for (int i = 0; i < n; ++i) {
            if (this.query(this.hostname, this.expectedTypes[i], dnsServerAddressStream.duplicate(), promise)) continue;
            return;
        }
        this.query(this.hostname, this.expectedTypes[n], dnsServerAddressStream, promise);
    }

    private void addNameServerToCache(AuthoritativeNameServer authoritativeNameServer, InetAddress inetAddress, long l) {
        if (!authoritativeNameServer.isRootServer()) {
            this.parent.authoritativeDnsServerCache().cache(authoritativeNameServer.domainName(), this.additionals, inetAddress, l, this.parent.ch.eventLoop());
        }
    }

    private DnsServerAddressStream getNameServersFromCache(String string) {
        List<? extends DnsCacheEntry> list;
        int n;
        int n2 = string.length();
        if (n2 == 0) {
            return null;
        }
        if (string.charAt(n2 - 1) != '.') {
            string = string + ".";
        }
        if ((n = string.indexOf(46)) == string.length() - 1) {
            return null;
        }
        do {
            int n3;
            if ((n3 = (string = string.substring(n + 1)).indexOf(46)) <= 0 || n3 == string.length() - 1) {
                return null;
            }
            n = n3;
        } while ((list = this.parent.authoritativeDnsServerCache().get(string, this.additionals)) == null || list.isEmpty());
        return DnsServerAddresses.sequential(new DnsCacheIterable(this, list)).stream();
    }

    private void query(DnsServerAddressStream dnsServerAddressStream, int n, DnsQuestion dnsQuestion, Promise<List<T>> promise, Throwable throwable) {
        this.query(dnsServerAddressStream, n, dnsQuestion, this.parent.dnsQueryLifecycleObserverFactory().newDnsQueryLifecycleObserver(dnsQuestion), promise, throwable);
    }

    private void query(DnsServerAddressStream dnsServerAddressStream, int n, DnsQuestion dnsQuestion, DnsQueryLifecycleObserver dnsQueryLifecycleObserver, Promise<List<T>> promise, Throwable throwable) {
        if (n >= dnsServerAddressStream.size() || this.allowedQueries == 0 || promise.isCancelled()) {
            this.tryToFinishResolve(dnsServerAddressStream, n, dnsQuestion, dnsQueryLifecycleObserver, promise, throwable);
            return;
        }
        --this.allowedQueries;
        InetSocketAddress inetSocketAddress = dnsServerAddressStream.next();
        ChannelPromise channelPromise = this.parent.ch.newPromise();
        Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> future = this.parent.query0(inetSocketAddress, dnsQuestion, this.additionals, channelPromise, this.parent.ch.eventLoop().newPromise());
        this.queriesInProgress.add(future);
        dnsQueryLifecycleObserver.queryWritten(inetSocketAddress, channelPromise);
        future.addListener((GenericFutureListener<Future<AddressedEnvelope<DnsResponse, InetSocketAddress>>>)new FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>>(this, promise, dnsQueryLifecycleObserver, dnsServerAddressStream, n, dnsQuestion){
            final Promise val$promise;
            final DnsQueryLifecycleObserver val$queryLifecycleObserver;
            final DnsServerAddressStream val$nameServerAddrStream;
            final int val$nameServerAddrStreamIndex;
            final DnsQuestion val$question;
            final DnsResolveContext this$0;
            {
                this.this$0 = dnsResolveContext;
                this.val$promise = promise;
                this.val$queryLifecycleObserver = dnsQueryLifecycleObserver;
                this.val$nameServerAddrStream = dnsServerAddressStream;
                this.val$nameServerAddrStreamIndex = n;
                this.val$question = dnsQuestion;
            }

            @Override
            public void operationComplete(Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> future) {
                DnsResolveContext.access$400(this.this$0).remove(future);
                if (this.val$promise.isDone() || future.isCancelled()) {
                    this.val$queryLifecycleObserver.queryCancelled(DnsResolveContext.access$500(this.this$0));
                    AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope = future.getNow();
                    if (addressedEnvelope != null) {
                        addressedEnvelope.release();
                    }
                    return;
                }
                Throwable throwable = future.cause();
                try {
                    if (throwable == null) {
                        DnsResolveContext.access$600(this.this$0, this.val$nameServerAddrStream, this.val$nameServerAddrStreamIndex, this.val$question, future.getNow(), this.val$queryLifecycleObserver, this.val$promise);
                    } else {
                        this.val$queryLifecycleObserver.queryFailed(throwable);
                        DnsResolveContext.access$700(this.this$0, this.val$nameServerAddrStream, this.val$nameServerAddrStreamIndex + 1, this.val$question, this.val$promise, throwable);
                    }
                } finally {
                    DnsResolveContext.access$800(this.this$0, this.val$nameServerAddrStream, this.val$nameServerAddrStreamIndex, this.val$question, NoopDnsQueryLifecycleObserver.INSTANCE, this.val$promise, throwable);
                }
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void onResponse(DnsServerAddressStream dnsServerAddressStream, int n, DnsQuestion dnsQuestion, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope, DnsQueryLifecycleObserver dnsQueryLifecycleObserver, Promise<List<T>> promise) {
        try {
            DnsResponse dnsResponse = addressedEnvelope.content();
            DnsResponseCode dnsResponseCode = dnsResponse.code();
            if (dnsResponseCode == DnsResponseCode.NOERROR) {
                if (this.handleRedirect(dnsQuestion, addressedEnvelope, dnsQueryLifecycleObserver, promise)) {
                    return;
                }
                DnsRecordType dnsRecordType = dnsQuestion.type();
                if (dnsRecordType == DnsRecordType.CNAME) {
                    this.onResponseCNAME(dnsQuestion, DnsResolveContext.buildAliasMap(addressedEnvelope.content()), dnsQueryLifecycleObserver, promise);
                    return;
                }
                for (DnsRecordType dnsRecordType2 : this.expectedTypes) {
                    if (dnsRecordType != dnsRecordType2) continue;
                    this.onExpectedResponse(dnsQuestion, addressedEnvelope, dnsQueryLifecycleObserver, promise);
                    return;
                }
                dnsQueryLifecycleObserver.queryFailed(UNRECOGNIZED_TYPE_QUERY_FAILED_EXCEPTION);
                return;
            }
            if (dnsResponseCode != DnsResponseCode.NXDOMAIN) {
                this.query(dnsServerAddressStream, n + 1, dnsQuestion, dnsQueryLifecycleObserver.queryNoAnswer(dnsResponseCode), promise, null);
            } else {
                dnsQueryLifecycleObserver.queryFailed(NXDOMAIN_QUERY_FAILED_EXCEPTION);
            }
        } finally {
            ReferenceCountUtil.safeRelease(addressedEnvelope);
        }
    }

    private boolean handleRedirect(DnsQuestion dnsQuestion, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope, DnsQueryLifecycleObserver dnsQueryLifecycleObserver, Promise<List<T>> promise) {
        AuthoritativeNameServerList authoritativeNameServerList;
        DnsResponse dnsResponse = addressedEnvelope.content();
        if (dnsResponse.count(DnsSection.ANSWER) == 0 && (authoritativeNameServerList = DnsResolveContext.extractAuthoritativeNameServers(dnsQuestion.name(), dnsResponse)) != null) {
            ArrayList<InetSocketAddress> arrayList = new ArrayList<InetSocketAddress>(authoritativeNameServerList.size());
            int n = dnsResponse.count(DnsSection.ADDITIONAL);
            for (int i = 0; i < n; ++i) {
                InetAddress inetAddress;
                String string;
                AuthoritativeNameServer authoritativeNameServer;
                Object t = dnsResponse.recordAt(DnsSection.ADDITIONAL, i);
                if (t.type() == DnsRecordType.A && !this.parent.supportsARecords() || t.type() == DnsRecordType.AAAA && !this.parent.supportsAAAARecords() || (authoritativeNameServer = authoritativeNameServerList.remove(string = t.name())) == null || (inetAddress = DnsAddressDecoder.decodeAddress(t, string, this.parent.isDecodeIdn())) == null) continue;
                arrayList.add(new InetSocketAddress(inetAddress, this.parent.dnsRedirectPort(inetAddress)));
                this.addNameServerToCache(authoritativeNameServer, inetAddress, t.timeToLive());
            }
            if (!arrayList.isEmpty()) {
                this.query(this.parent.uncachedRedirectDnsServerStream(arrayList), 0, dnsQuestion, dnsQueryLifecycleObserver.queryRedirected(Collections.unmodifiableList(arrayList)), promise, null);
                return false;
            }
        }
        return true;
    }

    private static AuthoritativeNameServerList extractAuthoritativeNameServers(String string, DnsResponse dnsResponse) {
        int n = dnsResponse.count(DnsSection.AUTHORITY);
        if (n == 0) {
            return null;
        }
        AuthoritativeNameServerList authoritativeNameServerList = new AuthoritativeNameServerList(string);
        for (int i = 0; i < n; ++i) {
            authoritativeNameServerList.add((DnsRecord)dnsResponse.recordAt(DnsSection.AUTHORITY, i));
        }
        return authoritativeNameServerList;
    }

    private void onExpectedResponse(DnsQuestion dnsQuestion, AddressedEnvelope<DnsResponse, InetSocketAddress> addressedEnvelope, DnsQueryLifecycleObserver dnsQueryLifecycleObserver, Promise<List<T>> promise) {
        DnsResponse dnsResponse = addressedEnvelope.content();
        Map<String, String> map = DnsResolveContext.buildAliasMap(dnsResponse);
        int n = dnsResponse.count(DnsSection.ANSWER);
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            T t;
            Object t2 = dnsResponse.recordAt(DnsSection.ANSWER, i);
            DnsRecordType dnsRecordType = t2.type();
            boolean bl2 = false;
            for (DnsRecordType dnsRecordType2 : this.expectedTypes) {
                if (dnsRecordType != dnsRecordType2) continue;
                bl2 = true;
                break;
            }
            if (!bl2) continue;
            String string = dnsQuestion.name().toLowerCase(Locale.US);
            String string2 = t2.name().toLowerCase(Locale.US);
            if (!string2.equals(string)) {
                String string3 = string;
                while (!string2.equals(string3 = map.get(string3)) && string3 != null) {
                }
                if (string3 == null) continue;
            }
            if ((t = this.convertRecord((DnsRecord)t2, this.hostname, this.additionals, this.parent.ch.eventLoop())) == null) continue;
            if (this.finalResult == null) {
                this.finalResult = new ArrayList<T>(8);
            }
            this.finalResult.add(t);
            this.cache(this.hostname, this.additionals, (DnsRecord)t2, t);
            bl = true;
        }
        if (map.isEmpty()) {
            if (bl) {
                dnsQueryLifecycleObserver.querySucceed();
                return;
            }
            dnsQueryLifecycleObserver.queryFailed(NO_MATCHING_RECORD_QUERY_FAILED_EXCEPTION);
        } else {
            dnsQueryLifecycleObserver.querySucceed();
            this.onResponseCNAME(dnsQuestion, map, this.parent.dnsQueryLifecycleObserverFactory().newDnsQueryLifecycleObserver(dnsQuestion), promise);
        }
    }

    private void onResponseCNAME(DnsQuestion dnsQuestion, Map<String, String> map, DnsQueryLifecycleObserver dnsQueryLifecycleObserver, Promise<List<T>> promise) {
        String string;
        String string2 = dnsQuestion.name().toLowerCase(Locale.US);
        boolean bl = false;
        while (!map.isEmpty() && (string = map.remove(string2)) != null) {
            bl = true;
            string2 = string;
        }
        if (bl) {
            this.followCname(dnsQuestion, string2, dnsQueryLifecycleObserver, promise);
        } else {
            dnsQueryLifecycleObserver.queryFailed(CNAME_NOT_FOUND_QUERY_FAILED_EXCEPTION);
        }
    }

    private static Map<String, String> buildAliasMap(DnsResponse dnsResponse) {
        int n = dnsResponse.count(DnsSection.ANSWER);
        Map<String, String> map = null;
        for (int i = 0; i < n; ++i) {
            ByteBuf byteBuf;
            String string;
            Object t = dnsResponse.recordAt(DnsSection.ANSWER, i);
            DnsRecordType dnsRecordType = t.type();
            if (dnsRecordType != DnsRecordType.CNAME || !(t instanceof DnsRawRecord) || (string = DnsResolveContext.decodeDomainName(byteBuf = ((ByteBufHolder)t).content())) == null) continue;
            if (map == null) {
                map = new HashMap<String, String>(Math.min(8, n));
            }
            map.put(t.name().toLowerCase(Locale.US), string.toLowerCase(Locale.US));
        }
        return map != null ? map : Collections.emptyMap();
    }

    private void tryToFinishResolve(DnsServerAddressStream dnsServerAddressStream, int n, DnsQuestion dnsQuestion, DnsQueryLifecycleObserver dnsQueryLifecycleObserver, Promise<List<T>> promise, Throwable throwable) {
        if (!this.queriesInProgress.isEmpty()) {
            dnsQueryLifecycleObserver.queryCancelled(this.allowedQueries);
            return;
        }
        if (this.finalResult == null) {
            if (n < dnsServerAddressStream.size()) {
                if (dnsQueryLifecycleObserver == NoopDnsQueryLifecycleObserver.INSTANCE) {
                    this.query(dnsServerAddressStream, n + 1, dnsQuestion, promise, throwable);
                } else {
                    this.query(dnsServerAddressStream, n + 1, dnsQuestion, dnsQueryLifecycleObserver, promise, throwable);
                }
                return;
            }
            dnsQueryLifecycleObserver.queryFailed(NAME_SERVERS_EXHAUSTED_EXCEPTION);
            if (throwable == null && !this.triedCNAME) {
                this.triedCNAME = true;
                this.query(this.hostname, DnsRecordType.CNAME, this.getNameServers(this.hostname), promise);
                return;
            }
        } else {
            dnsQueryLifecycleObserver.queryCancelled(this.allowedQueries);
        }
        this.finishResolve(promise, throwable);
    }

    private void finishResolve(Promise<List<T>> promise, Throwable throwable) {
        Object object;
        if (!this.queriesInProgress.isEmpty()) {
            Iterator<Future<AddressedEnvelope<DnsResponse, InetSocketAddress>>> iterator2 = this.queriesInProgress.iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                iterator2.remove();
                if (object.cancel(false)) continue;
                object.addListener(RELEASE_RESPONSE);
            }
        }
        if (this.finalResult != null) {
            DnsNameResolver.trySuccess(promise, this.filterResults(this.finalResult));
            return;
        }
        int n = this.maxAllowedQueries - this.allowedQueries;
        object = new StringBuilder(64);
        ((StringBuilder)object).append("failed to resolve '").append(this.hostname).append('\'');
        if (n > 1) {
            if (n < this.maxAllowedQueries) {
                ((StringBuilder)object).append(" after ").append(n).append(" queries ");
            } else {
                ((StringBuilder)object).append(". Exceeded max queries per resolve ").append(this.maxAllowedQueries).append(' ');
            }
        }
        UnknownHostException unknownHostException = new UnknownHostException(((StringBuilder)object).toString());
        if (throwable == null) {
            this.cache(this.hostname, this.additionals, unknownHostException);
        } else {
            unknownHostException.initCause(throwable);
        }
        promise.tryFailure(unknownHostException);
    }

    static String decodeDomainName(ByteBuf byteBuf) {
        byteBuf.markReaderIndex();
        try {
            String string = DefaultDnsRecordDecoder.decodeName(byteBuf);
            return string;
        } catch (CorruptedFrameException corruptedFrameException) {
            String string = null;
            return string;
        } finally {
            byteBuf.resetReaderIndex();
        }
    }

    private DnsServerAddressStream getNameServers(String string) {
        DnsServerAddressStream dnsServerAddressStream = this.getNameServersFromCache(string);
        return dnsServerAddressStream == null ? this.nameServerAddrs.duplicate() : dnsServerAddressStream;
    }

    private void followCname(DnsQuestion dnsQuestion, String string, DnsQueryLifecycleObserver dnsQueryLifecycleObserver, Promise<List<T>> promise) {
        DnsQuestion dnsQuestion2;
        DnsServerAddressStream dnsServerAddressStream = this.getNameServers(string);
        try {
            dnsQuestion2 = this.newQuestion(string, dnsQuestion.type());
        } catch (Throwable throwable) {
            dnsQueryLifecycleObserver.queryFailed(throwable);
            PlatformDependent.throwException(throwable);
            return;
        }
        this.query(dnsServerAddressStream, 0, dnsQuestion2, dnsQueryLifecycleObserver.queryCNAMEd(dnsQuestion2), promise, null);
    }

    private boolean query(String string, DnsRecordType dnsRecordType, DnsServerAddressStream dnsServerAddressStream, Promise<List<T>> promise) {
        DnsQuestion dnsQuestion = this.newQuestion(string, dnsRecordType);
        if (dnsQuestion == null) {
            return true;
        }
        this.query(dnsServerAddressStream, 0, dnsQuestion, promise, null);
        return false;
    }

    private DnsQuestion newQuestion(String string, DnsRecordType dnsRecordType) {
        try {
            return new DefaultDnsQuestion(string, dnsRecordType, this.dnsClass);
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
    }

    static String access$000(DnsResolveContext dnsResolveContext) {
        return dnsResolveContext.hostname;
    }

    static void access$100(DnsResolveContext dnsResolveContext, String string, FutureListener futureListener) {
        dnsResolveContext.doSearchDomainQuery(string, futureListener);
    }

    static void access$200(DnsResolveContext dnsResolveContext, Promise promise) {
        dnsResolveContext.internalResolve(promise);
    }

    static Set access$400(DnsResolveContext dnsResolveContext) {
        return dnsResolveContext.queriesInProgress;
    }

    static int access$500(DnsResolveContext dnsResolveContext) {
        return dnsResolveContext.allowedQueries;
    }

    static void access$600(DnsResolveContext dnsResolveContext, DnsServerAddressStream dnsServerAddressStream, int n, DnsQuestion dnsQuestion, AddressedEnvelope addressedEnvelope, DnsQueryLifecycleObserver dnsQueryLifecycleObserver, Promise promise) {
        dnsResolveContext.onResponse(dnsServerAddressStream, n, dnsQuestion, addressedEnvelope, dnsQueryLifecycleObserver, promise);
    }

    static void access$700(DnsResolveContext dnsResolveContext, DnsServerAddressStream dnsServerAddressStream, int n, DnsQuestion dnsQuestion, Promise promise, Throwable throwable) {
        dnsResolveContext.query(dnsServerAddressStream, n, dnsQuestion, promise, throwable);
    }

    static void access$800(DnsResolveContext dnsResolveContext, DnsServerAddressStream dnsServerAddressStream, int n, DnsQuestion dnsQuestion, DnsQueryLifecycleObserver dnsQueryLifecycleObserver, Promise promise, Throwable throwable) {
        dnsResolveContext.tryToFinishResolve(dnsServerAddressStream, n, dnsQuestion, dnsQueryLifecycleObserver, promise, throwable);
    }

    static {
        $assertionsDisabled = !DnsResolveContext.class.desiredAssertionStatus();
        RELEASE_RESPONSE = new FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>>(){

            @Override
            public void operationComplete(Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> future) {
                if (future.isSuccess()) {
                    future.getNow().release();
                }
            }
        };
        NXDOMAIN_QUERY_FAILED_EXCEPTION = ThrowableUtil.unknownStackTrace(new RuntimeException("No answer found and NXDOMAIN response code returned"), DnsResolveContext.class, "onResponse(..)");
        CNAME_NOT_FOUND_QUERY_FAILED_EXCEPTION = ThrowableUtil.unknownStackTrace(new RuntimeException("No matching CNAME record found"), DnsResolveContext.class, "onResponseCNAME(..)");
        NO_MATCHING_RECORD_QUERY_FAILED_EXCEPTION = ThrowableUtil.unknownStackTrace(new RuntimeException("No matching record type found"), DnsResolveContext.class, "onResponseAorAAAA(..)");
        UNRECOGNIZED_TYPE_QUERY_FAILED_EXCEPTION = ThrowableUtil.unknownStackTrace(new RuntimeException("Response type was unrecognized"), DnsResolveContext.class, "onResponse(..)");
        NAME_SERVERS_EXHAUSTED_EXCEPTION = ThrowableUtil.unknownStackTrace(new RuntimeException("No name servers returned an answer"), DnsResolveContext.class, "tryToFinishResolve(..)");
    }

    static final class AuthoritativeNameServer {
        final int dots;
        final String nsName;
        final String domainName;
        AuthoritativeNameServer next;
        boolean removed;

        AuthoritativeNameServer(int n, String string, String string2) {
            this.dots = n;
            this.nsName = string2;
            this.domainName = string;
        }

        boolean isRootServer() {
            return this.dots == 1;
        }

        String domainName() {
            return this.domainName;
        }
    }

    private static final class AuthoritativeNameServerList {
        private final String questionName;
        private AuthoritativeNameServer head;
        private int count;

        AuthoritativeNameServerList(String string) {
            this.questionName = string.toLowerCase(Locale.US);
        }

        void add(DnsRecord dnsRecord) {
            if (dnsRecord.type() != DnsRecordType.NS || !(dnsRecord instanceof DnsRawRecord)) {
                return;
            }
            if (this.questionName.length() < dnsRecord.name().length()) {
                return;
            }
            String string = dnsRecord.name().toLowerCase(Locale.US);
            int n = 0;
            int n2 = string.length() - 1;
            int n3 = this.questionName.length() - 1;
            while (n2 >= 0) {
                char c = string.charAt(n2);
                if (this.questionName.charAt(n3) != c) {
                    return;
                }
                if (c == '.') {
                    ++n;
                }
                --n2;
                --n3;
            }
            if (this.head != null && this.head.dots > n) {
                return;
            }
            ByteBuf byteBuf = ((ByteBufHolder)((Object)dnsRecord)).content();
            String string2 = DnsResolveContext.decodeDomainName(byteBuf);
            if (string2 == null) {
                return;
            }
            if (this.head == null || this.head.dots < n) {
                this.count = 1;
                this.head = new AuthoritativeNameServer(n, string, string2);
            } else if (this.head.dots == n) {
                AuthoritativeNameServer authoritativeNameServer = this.head;
                while (authoritativeNameServer.next != null) {
                    authoritativeNameServer = authoritativeNameServer.next;
                }
                authoritativeNameServer.next = new AuthoritativeNameServer(n, string, string2);
                ++this.count;
            }
        }

        AuthoritativeNameServer remove(String string) {
            AuthoritativeNameServer authoritativeNameServer = this.head;
            while (authoritativeNameServer != null) {
                if (!authoritativeNameServer.removed && authoritativeNameServer.nsName.equalsIgnoreCase(string)) {
                    authoritativeNameServer.removed = true;
                    return authoritativeNameServer;
                }
                authoritativeNameServer = authoritativeNameServer.next;
            }
            return null;
        }

        int size() {
            return this.count;
        }
    }

    private final class DnsCacheIterable
    implements Iterable<InetSocketAddress> {
        private final List<? extends DnsCacheEntry> entries;
        final DnsResolveContext this$0;

        DnsCacheIterable(DnsResolveContext dnsResolveContext, List<? extends DnsCacheEntry> list) {
            this.this$0 = dnsResolveContext;
            this.entries = list;
        }

        @Override
        public Iterator<InetSocketAddress> iterator() {
            return new Iterator<InetSocketAddress>(this){
                Iterator<? extends DnsCacheEntry> entryIterator;
                final DnsCacheIterable this$1;
                {
                    this.this$1 = dnsCacheIterable;
                    this.entryIterator = DnsCacheIterable.access$300(this.this$1).iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.entryIterator.hasNext();
                }

                @Override
                public InetSocketAddress next() {
                    InetAddress inetAddress = this.entryIterator.next().address();
                    return new InetSocketAddress(inetAddress, this.this$1.this$0.parent.dnsRedirectPort(inetAddress));
                }

                @Override
                public void remove() {
                    this.entryIterator.remove();
                }

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        static List access$300(DnsCacheIterable dnsCacheIterable) {
            return dnsCacheIterable.entries;
        }
    }

    private static final class SearchDomainUnknownHostException
    extends UnknownHostException {
        private static final long serialVersionUID = -8573510133644997085L;

        SearchDomainUnknownHostException(Throwable throwable, String string) {
            super("Search domain query failed. Original hostname: '" + string + "' " + throwable.getMessage());
            this.setStackTrace(throwable.getStackTrace());
            this.initCause(throwable.getCause());
        }

        @Override
        public Throwable fillInStackTrace() {
            return this;
        }
    }
}

