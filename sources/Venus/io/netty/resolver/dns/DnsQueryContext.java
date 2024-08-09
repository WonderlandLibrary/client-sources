/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.AddressedEnvelope;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.dns.AbstractDnsOptPseudoRrRecord;
import io.netty.handler.codec.dns.DatagramDnsQuery;
import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsRecord;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.handler.codec.dns.DnsSection;
import io.netty.resolver.dns.DnsNameResolver;
import io.netty.resolver.dns.DnsNameResolverException;
import io.netty.resolver.dns.DnsNameResolverTimeoutException;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

final class DnsQueryContext {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DnsQueryContext.class);
    private final DnsNameResolver parent;
    private final Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> promise;
    private final int id;
    private final DnsQuestion question;
    private final DnsRecord[] additionals;
    private final DnsRecord optResource;
    private final InetSocketAddress nameServerAddr;
    private final boolean recursionDesired;
    private volatile ScheduledFuture<?> timeoutFuture;

    DnsQueryContext(DnsNameResolver dnsNameResolver, InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, DnsRecord[] dnsRecordArray, Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> promise) {
        this.parent = ObjectUtil.checkNotNull(dnsNameResolver, "parent");
        this.nameServerAddr = ObjectUtil.checkNotNull(inetSocketAddress, "nameServerAddr");
        this.question = ObjectUtil.checkNotNull(dnsQuestion, "question");
        this.additionals = ObjectUtil.checkNotNull(dnsRecordArray, "additionals");
        this.promise = ObjectUtil.checkNotNull(promise, "promise");
        this.recursionDesired = dnsNameResolver.isRecursionDesired();
        this.id = dnsNameResolver.queryContextManager.add(this);
        this.optResource = dnsNameResolver.isOptResourceEnabled() ? new AbstractDnsOptPseudoRrRecord(this, dnsNameResolver.maxPayloadSize(), 0, 0){
            final DnsQueryContext this$0;
            {
                this.this$0 = dnsQueryContext;
                super(n, n2, n3);
            }
        } : null;
    }

    InetSocketAddress nameServerAddr() {
        return this.nameServerAddr;
    }

    DnsQuestion question() {
        return this.question;
    }

    void query(ChannelPromise channelPromise) {
        DnsQuestion dnsQuestion = this.question();
        InetSocketAddress inetSocketAddress = this.nameServerAddr();
        DatagramDnsQuery datagramDnsQuery = new DatagramDnsQuery(null, inetSocketAddress, this.id);
        datagramDnsQuery.setRecursionDesired(this.recursionDesired);
        datagramDnsQuery.addRecord(DnsSection.QUESTION, dnsQuestion);
        for (DnsRecord dnsRecord : this.additionals) {
            datagramDnsQuery.addRecord(DnsSection.ADDITIONAL, dnsRecord);
        }
        if (this.optResource != null) {
            datagramDnsQuery.addRecord(DnsSection.ADDITIONAL, this.optResource);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("{} WRITE: [{}: {}], {}", this.parent.ch, this.id, inetSocketAddress, dnsQuestion);
        }
        this.sendQuery(datagramDnsQuery, channelPromise);
    }

    private void sendQuery(DnsQuery dnsQuery, ChannelPromise channelPromise) {
        if (this.parent.channelFuture.isDone()) {
            this.writeQuery(dnsQuery, channelPromise);
        } else {
            this.parent.channelFuture.addListener((GenericFutureListener<Future<Channel>>)new GenericFutureListener<Future<? super Channel>>(this, dnsQuery, channelPromise){
                final DnsQuery val$query;
                final ChannelPromise val$writePromise;
                final DnsQueryContext this$0;
                {
                    this.this$0 = dnsQueryContext;
                    this.val$query = dnsQuery;
                    this.val$writePromise = channelPromise;
                }

                @Override
                public void operationComplete(Future<? super Channel> future) throws Exception {
                    if (future.isSuccess()) {
                        DnsQueryContext.access$000(this.this$0, this.val$query, this.val$writePromise);
                    } else {
                        Throwable throwable = future.cause();
                        DnsQueryContext.access$100(this.this$0).tryFailure(throwable);
                        this.val$writePromise.setFailure(throwable);
                    }
                }
            });
        }
    }

    private void writeQuery(DnsQuery dnsQuery, ChannelPromise channelPromise) {
        ChannelFuture channelFuture = this.parent.ch.writeAndFlush(dnsQuery, channelPromise);
        if (channelFuture.isDone()) {
            this.onQueryWriteCompletion(channelFuture);
        } else {
            channelFuture.addListener(new ChannelFutureListener(this, channelFuture){
                final ChannelFuture val$writeFuture;
                final DnsQueryContext this$0;
                {
                    this.this$0 = dnsQueryContext;
                    this.val$writeFuture = channelFuture;
                }

                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    DnsQueryContext.access$200(this.this$0, this.val$writeFuture);
                }

                @Override
                public void operationComplete(Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
        }
    }

    private void onQueryWriteCompletion(ChannelFuture channelFuture) {
        if (!channelFuture.isSuccess()) {
            this.setFailure("failed to send a query", channelFuture.cause());
            return;
        }
        long l = this.parent.queryTimeoutMillis();
        if (l > 0L) {
            this.timeoutFuture = this.parent.ch.eventLoop().schedule(new Runnable(this, l){
                final long val$queryTimeoutMillis;
                final DnsQueryContext this$0;
                {
                    this.this$0 = dnsQueryContext;
                    this.val$queryTimeoutMillis = l;
                }

                @Override
                public void run() {
                    if (DnsQueryContext.access$100(this.this$0).isDone()) {
                        return;
                    }
                    DnsQueryContext.access$300(this.this$0, "query timed out after " + this.val$queryTimeoutMillis + " milliseconds", null);
                }
            }, l, TimeUnit.MILLISECONDS);
        }
    }

    void finish(AddressedEnvelope<? extends DnsResponse, InetSocketAddress> addressedEnvelope) {
        DnsResponse dnsResponse = addressedEnvelope.content();
        if (dnsResponse.count(DnsSection.QUESTION) != 1) {
            logger.warn("Received a DNS response with invalid number of questions: {}", (Object)addressedEnvelope);
            return;
        }
        if (!this.question().equals(dnsResponse.recordAt(DnsSection.QUESTION))) {
            logger.warn("Received a mismatching DNS response: {}", (Object)addressedEnvelope);
            return;
        }
        this.setSuccess(addressedEnvelope);
    }

    private void setSuccess(AddressedEnvelope<? extends DnsResponse, InetSocketAddress> addressedEnvelope) {
        AddressedEnvelope<? extends DnsResponse, InetSocketAddress> addressedEnvelope2;
        Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> promise;
        this.parent.queryContextManager.remove(this.nameServerAddr(), this.id);
        ScheduledFuture<?> scheduledFuture = this.timeoutFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        if ((promise = this.promise).setUncancellable() && !promise.trySuccess(addressedEnvelope2 = addressedEnvelope.retain())) {
            addressedEnvelope.release();
        }
    }

    private void setFailure(String string, Throwable throwable) {
        InetSocketAddress inetSocketAddress = this.nameServerAddr();
        this.parent.queryContextManager.remove(inetSocketAddress, this.id);
        StringBuilder stringBuilder = new StringBuilder(string.length() + 64);
        stringBuilder.append('[').append(inetSocketAddress).append("] ").append(string).append(" (no stack trace available)");
        DnsNameResolverException dnsNameResolverException = throwable == null ? new DnsNameResolverTimeoutException(inetSocketAddress, this.question(), stringBuilder.toString()) : new DnsNameResolverException(inetSocketAddress, this.question(), stringBuilder.toString(), throwable);
        this.promise.tryFailure(dnsNameResolverException);
    }

    static void access$000(DnsQueryContext dnsQueryContext, DnsQuery dnsQuery, ChannelPromise channelPromise) {
        dnsQueryContext.writeQuery(dnsQuery, channelPromise);
    }

    static Promise access$100(DnsQueryContext dnsQueryContext) {
        return dnsQueryContext.promise;
    }

    static void access$200(DnsQueryContext dnsQueryContext, ChannelFuture channelFuture) {
        dnsQueryContext.onQueryWriteCompletion(channelFuture);
    }

    static void access$300(DnsQueryContext dnsQueryContext, String string, Throwable throwable) {
        dnsQueryContext.setFailure(string, throwable);
    }
}

