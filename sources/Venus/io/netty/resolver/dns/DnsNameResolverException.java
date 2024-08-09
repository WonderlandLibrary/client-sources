/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import java.net.InetSocketAddress;

public class DnsNameResolverException
extends RuntimeException {
    private static final long serialVersionUID = -8826717909627131850L;
    private final InetSocketAddress remoteAddress;
    private final DnsQuestion question;

    public DnsNameResolverException(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, String string) {
        super(string);
        this.remoteAddress = DnsNameResolverException.validateRemoteAddress(inetSocketAddress);
        this.question = DnsNameResolverException.validateQuestion(dnsQuestion);
    }

    public DnsNameResolverException(InetSocketAddress inetSocketAddress, DnsQuestion dnsQuestion, String string, Throwable throwable) {
        super(string, throwable);
        this.remoteAddress = DnsNameResolverException.validateRemoteAddress(inetSocketAddress);
        this.question = DnsNameResolverException.validateQuestion(dnsQuestion);
    }

    private static InetSocketAddress validateRemoteAddress(InetSocketAddress inetSocketAddress) {
        return ObjectUtil.checkNotNull(inetSocketAddress, "remoteAddress");
    }

    private static DnsQuestion validateQuestion(DnsQuestion dnsQuestion) {
        return ObjectUtil.checkNotNull(dnsQuestion, "question");
    }

    public InetSocketAddress remoteAddress() {
        return this.remoteAddress;
    }

    public DnsQuestion question() {
        return this.question;
    }

    @Override
    public Throwable fillInStackTrace() {
        this.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
        return this;
    }
}

