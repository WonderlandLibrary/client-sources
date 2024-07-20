/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.socket.InternetProtocolFamily;
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
import io.netty.resolver.dns.DnsCache;
import io.netty.resolver.dns.DnsCacheEntry;
import io.netty.resolver.dns.DnsNameResolver;
import io.netty.resolver.dns.DnsServerAddressStream;
import io.netty.resolver.dns.DnsServerAddresses;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.StringUtil;
import java.net.IDN;
import java.net.Inet4Address;
import java.net.Inet6Address;
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

abstract class DnsNameResolverContext<T> {
    private static final int INADDRSZ4 = 4;
    private static final int INADDRSZ6 = 16;
    private static final FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>> RELEASE_RESPONSE = new FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>>(){

        @Override
        public void operationComplete(Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> future) {
            if (future.isSuccess()) {
                future.getNow().release();
            }
        }
    };
    private final DnsNameResolver parent;
    private final DnsServerAddressStream nameServerAddrs;
    private final String hostname;
    protected String pristineHostname;
    private final DnsCache resolveCache;
    private final boolean traceEnabled;
    private final int maxAllowedQueries;
    private final InternetProtocolFamily[] resolvedInternetProtocolFamilies;
    private final DnsRecord[] additionals;
    private final Set<Future<AddressedEnvelope<DnsResponse, InetSocketAddress>>> queriesInProgress = Collections.newSetFromMap(new IdentityHashMap());
    private List<DnsCacheEntry> resolvedEntries;
    private StringBuilder trace;
    private int allowedQueries;
    private boolean triedCNAME;

    protected DnsNameResolverContext(DnsNameResolver parent, String hostname, DnsRecord[] additionals, DnsCache resolveCache, DnsServerAddressStream nameServerAddrs) {
        this.parent = parent;
        this.hostname = hostname;
        this.additionals = additionals;
        this.resolveCache = resolveCache;
        this.nameServerAddrs = nameServerAddrs;
        this.maxAllowedQueries = parent.maxQueriesPerResolve();
        this.resolvedInternetProtocolFamilies = parent.resolvedInternetProtocolFamiliesUnsafe();
        this.traceEnabled = parent.isTraceEnabled();
        this.allowedQueries = this.maxAllowedQueries;
    }

    void resolve(Promise<T> promise) {
        boolean directSearch;
        boolean bl = directSearch = this.parent.searchDomains().length == 0 || StringUtil.endsWith(this.hostname, '.');
        if (directSearch) {
            this.internalResolve(promise);
        } else {
            final Promise<T> original = promise;
            promise = this.parent.executor().newPromise();
            promise.addListener(new FutureListener<T>(){
                int count;

                @Override
                public void operationComplete(Future<T> future) throws Exception {
                    if (future.isSuccess()) {
                        original.trySuccess(future.getNow());
                    } else if (this.count < DnsNameResolverContext.this.parent.searchDomains().length) {
                        String searchDomain = DnsNameResolverContext.this.parent.searchDomains()[this.count++];
                        Promise nextPromise = DnsNameResolverContext.this.parent.executor().newPromise();
                        String nextHostname = DnsNameResolverContext.this.hostname + '.' + searchDomain;
                        DnsNameResolverContext nextContext = DnsNameResolverContext.this.newResolverContext(DnsNameResolverContext.this.parent, nextHostname, DnsNameResolverContext.this.additionals, DnsNameResolverContext.this.resolveCache, DnsNameResolverContext.this.nameServerAddrs);
                        nextContext.pristineHostname = DnsNameResolverContext.this.hostname;
                        nextContext.internalResolve(nextPromise);
                        nextPromise.addListener(this);
                    } else {
                        original.tryFailure(future.cause());
                    }
                }
            });
            if (this.parent.ndots() == 0) {
                this.internalResolve(promise);
            } else {
                int dots = 0;
                for (int idx = this.hostname.length() - 1; idx >= 0; --idx) {
                    if (this.hostname.charAt(idx) != '.' || ++dots < this.parent.ndots()) continue;
                    this.internalResolve(promise);
                    return;
                }
                promise.tryFailure(new UnknownHostException(this.hostname));
            }
        }
    }

    private void internalResolve(Promise<T> promise) {
        DnsServerAddressStream nameServerAddressStream = this.getNameServers(this.hostname);
        for (DnsRecordType type2 : this.parent.resolveRecordTypes()) {
            if (this.query(this.hostname, type2, nameServerAddressStream, promise)) continue;
            return;
        }
    }

    private void addNameServerToCache(AuthoritativeNameServer name, InetAddress resolved, long ttl) {
        if (!name.isRootServer()) {
            this.parent.authoritativeDnsServerCache().cache(name.domainName(), this.additionals, resolved, ttl, this.parent.ch.eventLoop());
        }
    }

    private DnsServerAddressStream getNameServersFromCache(String hostname) {
        List<DnsCacheEntry> entries;
        int idx;
        int len = hostname.length();
        if (len == 0) {
            return null;
        }
        if (hostname.charAt(len - 1) != '.') {
            hostname = hostname + ".";
        }
        if ((idx = hostname.indexOf(46)) == hostname.length() - 1) {
            return null;
        }
        do {
            int idx2;
            if ((idx2 = (hostname = hostname.substring(idx + 1)).indexOf(46)) <= 0 || idx2 == hostname.length() - 1) {
                return null;
            }
            idx = idx2;
        } while ((entries = this.parent.authoritativeDnsServerCache().get(hostname, this.additionals)) == null || entries.isEmpty());
        return DnsServerAddresses.shuffled(new DnsCacheIterable(entries)).stream();
    }

    private void query(final DnsServerAddressStream nameServerAddrStream, final DnsQuestion question, final Promise<T> promise) {
        if (this.allowedQueries == 0 || promise.isCancelled()) {
            this.tryToFinishResolve(promise);
            return;
        }
        --this.allowedQueries;
        Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> f = this.parent.query0(nameServerAddrStream.next(), question, this.additionals, this.parent.ch.eventLoop().newPromise());
        this.queriesInProgress.add(f);
        f.addListener((GenericFutureListener<Future<AddressedEnvelope<DnsResponse, InetSocketAddress>>>)new FutureListener<AddressedEnvelope<DnsResponse, InetSocketAddress>>(){

            @Override
            public void operationComplete(Future<AddressedEnvelope<DnsResponse, InetSocketAddress>> future) {
                DnsNameResolverContext.this.queriesInProgress.remove(future);
                if (promise.isDone() || future.isCancelled()) {
                    return;
                }
                try {
                    if (future.isSuccess()) {
                        DnsNameResolverContext.this.onResponse(nameServerAddrStream, question, future.getNow(), promise);
                    } else {
                        if (DnsNameResolverContext.this.traceEnabled) {
                            DnsNameResolverContext.this.addTrace(future.cause());
                        }
                        DnsNameResolverContext.this.query(nameServerAddrStream, question, promise);
                    }
                } finally {
                    DnsNameResolverContext.this.tryToFinishResolve(promise);
                }
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void onResponse(DnsServerAddressStream nameServerAddrStream, DnsQuestion question, AddressedEnvelope<DnsResponse, InetSocketAddress> envelope, Promise<T> promise) {
        try {
            DnsResponse res = envelope.content();
            DnsResponseCode code = res.code();
            if (code == DnsResponseCode.NOERROR) {
                if (this.handleRedirect(question, envelope, promise)) {
                    return;
                }
                DnsRecordType type2 = question.type();
                if (type2 == DnsRecordType.A || type2 == DnsRecordType.AAAA) {
                    this.onResponseAorAAAA(type2, question, envelope, promise);
                } else if (type2 == DnsRecordType.CNAME) {
                    this.onResponseCNAME(question, envelope, promise);
                }
                return;
            }
            if (this.traceEnabled) {
                this.addTrace(envelope.sender(), "response code: " + code + " with " + res.count(DnsSection.ANSWER) + " answer(s) and " + res.count(DnsSection.AUTHORITY) + " authority resource(s)");
            }
            if (code != DnsResponseCode.NXDOMAIN) {
                this.query(nameServerAddrStream, question, promise);
            }
        } finally {
            ReferenceCountUtil.safeRelease(envelope);
        }
    }

    private boolean handleRedirect(DnsQuestion question, AddressedEnvelope<DnsResponse, InetSocketAddress> envelope, Promise<T> promise) {
        AuthoritativeNameServerList serverNames;
        DnsResponse res = envelope.content();
        if (res.count(DnsSection.ANSWER) == 0 && (serverNames = DnsNameResolverContext.extractAuthoritativeNameServers(question.name(), res)) != null) {
            ArrayList<InetSocketAddress> nameServers = new ArrayList<InetSocketAddress>(serverNames.size());
            int additionalCount = res.count(DnsSection.ADDITIONAL);
            for (int i = 0; i < additionalCount; ++i) {
                InetAddress resolved;
                String recordName;
                AuthoritativeNameServer authoritativeNameServer;
                Object r = res.recordAt(DnsSection.ADDITIONAL, i);
                if (r.type() == DnsRecordType.A && !this.parent.supportsARecords() || r.type() == DnsRecordType.AAAA && !this.parent.supportsAAAARecords() || (authoritativeNameServer = serverNames.remove(recordName = r.name())) == null || (resolved = this.parseAddress((DnsRecord)r, recordName)) == null) continue;
                nameServers.add(new InetSocketAddress(resolved, this.parent.dnsRedirectPort(resolved)));
                this.addNameServerToCache(authoritativeNameServer, resolved, r.timeToLive());
            }
            if (nameServers.isEmpty()) {
                promise.tryFailure(new UnknownHostException("Unable to find correct name server for " + this.hostname));
            } else {
                this.query(DnsServerAddresses.shuffled(nameServers).stream(), question, promise);
            }
            return true;
        }
        return false;
    }

    private static AuthoritativeNameServerList extractAuthoritativeNameServers(String questionName, DnsResponse res) {
        int authorityCount = res.count(DnsSection.AUTHORITY);
        if (authorityCount == 0) {
            return null;
        }
        AuthoritativeNameServerList serverNames = new AuthoritativeNameServerList(questionName);
        for (int i = 0; i < authorityCount; ++i) {
            serverNames.add((DnsRecord)res.recordAt(DnsSection.AUTHORITY, i));
        }
        return serverNames;
    }

    private void onResponseAorAAAA(DnsRecordType qType, DnsQuestion question, AddressedEnvelope<DnsResponse, InetSocketAddress> envelope, Promise<T> promise) {
        DnsResponse response = envelope.content();
        Map<String, String> cnames = DnsNameResolverContext.buildAliasMap(response);
        int answerCount = response.count(DnsSection.ANSWER);
        boolean found = false;
        for (int i = 0; i < answerCount; ++i) {
            Object resolved;
            Object r = response.recordAt(DnsSection.ANSWER, i);
            DnsRecordType type2 = r.type();
            if (type2 != DnsRecordType.A && type2 != DnsRecordType.AAAA) continue;
            String questionName = question.name().toLowerCase(Locale.US);
            String recordName = r.name().toLowerCase(Locale.US);
            if (!recordName.equals(questionName)) {
                resolved = questionName;
                while (!recordName.equals(resolved = cnames.get(resolved)) && resolved != null) {
                }
                if (resolved == null) continue;
            }
            if ((resolved = this.parseAddress((DnsRecord)r, this.hostname)) == null) continue;
            if (this.resolvedEntries == null) {
                this.resolvedEntries = new ArrayList<DnsCacheEntry>(8);
            }
            DnsCacheEntry e = new DnsCacheEntry(this.hostname, (InetAddress)resolved);
            this.resolveCache.cache(this.hostname, this.additionals, (InetAddress)resolved, r.timeToLive(), this.parent.ch.eventLoop());
            this.resolvedEntries.add(e);
            found = true;
        }
        if (found) {
            return;
        }
        if (this.traceEnabled) {
            this.addTrace(envelope.sender(), "no matching " + qType + " record found");
        }
        if (!cnames.isEmpty()) {
            this.onResponseCNAME(question, envelope, cnames, false, promise);
        }
    }

    private InetAddress parseAddress(DnsRecord r, String name) {
        if (!(r instanceof DnsRawRecord)) {
            return null;
        }
        ByteBuf content = ((ByteBufHolder)((Object)r)).content();
        int contentLen = content.readableBytes();
        if (contentLen != 4 && contentLen != 16) {
            return null;
        }
        byte[] addrBytes = new byte[contentLen];
        content.getBytes(content.readerIndex(), addrBytes);
        try {
            return InetAddress.getByAddress(this.parent.isDecodeIdn() ? IDN.toUnicode(name) : name, addrBytes);
        } catch (UnknownHostException e) {
            throw new Error(e);
        }
    }

    private void onResponseCNAME(DnsQuestion question, AddressedEnvelope<DnsResponse, InetSocketAddress> envelope, Promise<T> promise) {
        this.onResponseCNAME(question, envelope, DnsNameResolverContext.buildAliasMap(envelope.content()), true, promise);
    }

    private void onResponseCNAME(DnsQuestion question, AddressedEnvelope<DnsResponse, InetSocketAddress> response, Map<String, String> cnames, boolean trace, Promise<T> promise) {
        String next;
        String name;
        String resolved = name = question.name().toLowerCase(Locale.US);
        boolean found = false;
        while (!cnames.isEmpty() && (next = cnames.remove(resolved)) != null) {
            found = true;
            resolved = next;
        }
        if (found) {
            this.followCname(response.sender(), name, resolved, promise);
        } else if (trace && this.traceEnabled) {
            this.addTrace(response.sender(), "no matching CNAME record found");
        }
    }

    private static Map<String, String> buildAliasMap(DnsResponse response) {
        int answerCount = response.count(DnsSection.ANSWER);
        Map<String, String> cnames = null;
        for (int i = 0; i < answerCount; ++i) {
            ByteBuf recordContent;
            String domainName;
            Object r = response.recordAt(DnsSection.ANSWER, i);
            DnsRecordType type2 = r.type();
            if (type2 != DnsRecordType.CNAME || !(r instanceof DnsRawRecord) || (domainName = DnsNameResolverContext.decodeDomainName(recordContent = ((ByteBufHolder)r).content())) == null) continue;
            if (cnames == null) {
                cnames = new HashMap<String, String>();
            }
            cnames.put(r.name().toLowerCase(Locale.US), domainName.toLowerCase(Locale.US));
        }
        return cnames != null ? cnames : Collections.emptyMap();
    }

    void tryToFinishResolve(Promise<T> promise) {
        if (!this.queriesInProgress.isEmpty()) {
            if (this.gotPreferredAddress()) {
                this.finishResolve(promise);
            }
            return;
        }
        if (this.resolvedEntries == null && !this.triedCNAME) {
            this.triedCNAME = true;
            this.query(this.hostname, DnsRecordType.CNAME, this.getNameServers(this.hostname), promise);
            return;
        }
        this.finishResolve(promise);
    }

    private boolean gotPreferredAddress() {
        if (this.resolvedEntries == null) {
            return false;
        }
        int size = this.resolvedEntries.size();
        switch (this.parent.preferredAddressType()) {
            case IPv4: {
                for (int i = 0; i < size; ++i) {
                    if (!(this.resolvedEntries.get(i).address() instanceof Inet4Address)) continue;
                    return true;
                }
                break;
            }
            case IPv6: {
                for (int i = 0; i < size; ++i) {
                    if (!(this.resolvedEntries.get(i).address() instanceof Inet6Address)) continue;
                    return true;
                }
                break;
            }
            default: {
                throw new Error();
            }
        }
        return false;
    }

    private void finishResolve(Promise<T> promise) {
        if (!this.queriesInProgress.isEmpty()) {
            InternetProtocolFamily[] i = this.queriesInProgress.iterator();
            while (i.hasNext()) {
                Future f = (Future)i.next();
                i.remove();
                if (f.cancel(false)) continue;
                f.addListener(RELEASE_RESPONSE);
            }
        }
        if (this.resolvedEntries != null) {
            for (InternetProtocolFamily f : this.resolvedInternetProtocolFamilies) {
                if (!this.finishResolve(f.addressType(), this.resolvedEntries, promise)) continue;
                return;
            }
        }
        int tries = this.maxAllowedQueries - this.allowedQueries;
        StringBuilder buf = new StringBuilder(64);
        buf.append("failed to resolve '");
        if (this.pristineHostname != null) {
            buf.append(this.pristineHostname);
        } else {
            buf.append(this.hostname);
        }
        buf.append('\'');
        if (tries > 1) {
            if (tries < this.maxAllowedQueries) {
                buf.append(" after ").append(tries).append(" queries ");
            } else {
                buf.append(". Exceeded max queries per resolve ").append(this.maxAllowedQueries).append(' ');
            }
        }
        if (this.trace != null) {
            buf.append(':').append((CharSequence)this.trace);
        }
        UnknownHostException cause = new UnknownHostException(buf.toString());
        this.resolveCache.cache(this.hostname, this.additionals, cause, this.parent.ch.eventLoop());
        promise.tryFailure(cause);
    }

    abstract boolean finishResolve(Class<? extends InetAddress> var1, List<DnsCacheEntry> var2, Promise<T> var3);

    abstract DnsNameResolverContext<T> newResolverContext(DnsNameResolver var1, String var2, DnsRecord[] var3, DnsCache var4, DnsServerAddressStream var5);

    static String decodeDomainName(ByteBuf in) {
        in.markReaderIndex();
        try {
            String string = DefaultDnsRecordDecoder.decodeName(in);
            return string;
        } catch (CorruptedFrameException e) {
            String string = null;
            return string;
        } finally {
            in.resetReaderIndex();
        }
    }

    private DnsServerAddressStream getNameServers(String hostame) {
        DnsServerAddressStream stream = this.getNameServersFromCache(hostame);
        return stream == null ? this.nameServerAddrs : stream;
    }

    private void followCname(InetSocketAddress nameServerAddr, String name, String cname, Promise<T> promise) {
        if (this.traceEnabled) {
            if (this.trace == null) {
                this.trace = new StringBuilder(128);
            }
            this.trace.append(StringUtil.NEWLINE);
            this.trace.append("\tfrom ");
            this.trace.append(nameServerAddr);
            this.trace.append(": ");
            this.trace.append(name);
            this.trace.append(" CNAME ");
            this.trace.append(cname);
        }
        DnsServerAddressStream stream = DnsServerAddresses.singleton(this.getNameServers(cname).next()).stream();
        if (this.parent.supportsARecords() && !this.query(this.hostname, DnsRecordType.A, stream, promise)) {
            return;
        }
        if (this.parent.supportsAAAARecords()) {
            this.query(this.hostname, DnsRecordType.AAAA, stream, promise);
        }
    }

    private boolean query(String hostname, DnsRecordType type2, DnsServerAddressStream nextAddr, Promise<T> promise) {
        DefaultDnsQuestion question;
        try {
            question = new DefaultDnsQuestion(hostname, type2);
        } catch (IllegalArgumentException e) {
            promise.tryFailure(e);
            return false;
        }
        this.query(nextAddr, question, promise);
        return true;
    }

    private void addTrace(InetSocketAddress nameServerAddr, String msg) {
        assert (this.traceEnabled);
        if (this.trace == null) {
            this.trace = new StringBuilder(128);
        }
        this.trace.append(StringUtil.NEWLINE);
        this.trace.append("\tfrom ");
        this.trace.append(nameServerAddr);
        this.trace.append(": ");
        this.trace.append(msg);
    }

    private void addTrace(Throwable cause) {
        assert (this.traceEnabled);
        if (this.trace == null) {
            this.trace = new StringBuilder(128);
        }
        this.trace.append(StringUtil.NEWLINE);
        this.trace.append("Caused by: ");
        this.trace.append(cause);
    }

    static final class AuthoritativeNameServer {
        final int dots;
        final String nsName;
        final String domainName;
        AuthoritativeNameServer next;
        boolean removed;

        AuthoritativeNameServer(int dots, String domainName, String nsName) {
            this.dots = dots;
            this.nsName = nsName;
            this.domainName = domainName;
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

        AuthoritativeNameServerList(String questionName) {
            this.questionName = questionName.toLowerCase(Locale.US);
        }

        void add(DnsRecord r) {
            if (r.type() != DnsRecordType.NS || !(r instanceof DnsRawRecord)) {
                return;
            }
            if (this.questionName.length() < r.name().length()) {
                return;
            }
            String recordName = r.name().toLowerCase(Locale.US);
            int dots = 0;
            int a = recordName.length() - 1;
            int b = this.questionName.length() - 1;
            while (a >= 0) {
                char c = recordName.charAt(a);
                if (this.questionName.charAt(b) != c) {
                    return;
                }
                if (c == '.') {
                    ++dots;
                }
                --a;
                --b;
            }
            if (this.head != null && this.head.dots > dots) {
                return;
            }
            ByteBuf recordContent = ((ByteBufHolder)((Object)r)).content();
            String domainName = DnsNameResolverContext.decodeDomainName(recordContent);
            if (domainName == null) {
                return;
            }
            if (this.head == null || this.head.dots < dots) {
                this.count = 1;
                this.head = new AuthoritativeNameServer(dots, recordName, domainName);
            } else if (this.head.dots == dots) {
                AuthoritativeNameServer serverName = this.head;
                while (serverName.next != null) {
                    serverName = serverName.next;
                }
                serverName.next = new AuthoritativeNameServer(dots, recordName, domainName);
                ++this.count;
            }
        }

        AuthoritativeNameServer remove(String nsName) {
            AuthoritativeNameServer serverName = this.head;
            while (serverName != null) {
                if (!serverName.removed && serverName.nsName.equalsIgnoreCase(nsName)) {
                    serverName.removed = true;
                    return serverName;
                }
                serverName = serverName.next;
            }
            return null;
        }

        int size() {
            return this.count;
        }
    }

    private final class DnsCacheIterable
    implements Iterable<InetSocketAddress> {
        private final List<DnsCacheEntry> entries;

        DnsCacheIterable(List<DnsCacheEntry> entries) {
            this.entries = entries;
        }

        @Override
        public Iterator<InetSocketAddress> iterator() {
            return new Iterator<InetSocketAddress>(){
                Iterator<DnsCacheEntry> entryIterator;
                {
                    this.entryIterator = DnsCacheIterable.this.entries.iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.entryIterator.hasNext();
                }

                @Override
                public InetSocketAddress next() {
                    InetAddress address = this.entryIterator.next().address();
                    return new InetSocketAddress(address, DnsNameResolverContext.this.parent.dnsRedirectPort(address));
                }

                @Override
                public void remove() {
                    this.entryIterator.remove();
                }
            };
        }
    }
}

