/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.dns.DnsQuestion;
import io.netty.handler.codec.dns.DnsResponseCode;
import io.netty.resolver.dns.DnsQueryLifecycleObserver;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import java.net.InetSocketAddress;
import java.util.List;

final class TraceDnsQueryLifecycleObserver
implements DnsQueryLifecycleObserver {
    private final InternalLogger logger;
    private final InternalLogLevel level;
    private final DnsQuestion question;
    private InetSocketAddress dnsServerAddress;

    TraceDnsQueryLifecycleObserver(DnsQuestion dnsQuestion, InternalLogger internalLogger, InternalLogLevel internalLogLevel) {
        this.question = ObjectUtil.checkNotNull(dnsQuestion, "question");
        this.logger = ObjectUtil.checkNotNull(internalLogger, "logger");
        this.level = ObjectUtil.checkNotNull(internalLogLevel, "level");
    }

    @Override
    public void queryWritten(InetSocketAddress inetSocketAddress, ChannelFuture channelFuture) {
        this.dnsServerAddress = inetSocketAddress;
    }

    @Override
    public void queryCancelled(int n) {
        if (this.dnsServerAddress != null) {
            this.logger.log(this.level, "from {} : {} cancelled with {} queries remaining", this.dnsServerAddress, this.question, n);
        } else {
            this.logger.log(this.level, "{} query never written and cancelled with {} queries remaining", (Object)this.question, (Object)n);
        }
    }

    @Override
    public DnsQueryLifecycleObserver queryRedirected(List<InetSocketAddress> list) {
        this.logger.log(this.level, "from {} : {} redirected", (Object)this.dnsServerAddress, (Object)this.question);
        return this;
    }

    @Override
    public DnsQueryLifecycleObserver queryCNAMEd(DnsQuestion dnsQuestion) {
        this.logger.log(this.level, "from {} : {} CNAME question {}", this.dnsServerAddress, this.question, dnsQuestion);
        return this;
    }

    @Override
    public DnsQueryLifecycleObserver queryNoAnswer(DnsResponseCode dnsResponseCode) {
        this.logger.log(this.level, "from {} : {} no answer {}", this.dnsServerAddress, this.question, dnsResponseCode);
        return this;
    }

    @Override
    public void queryFailed(Throwable throwable) {
        if (this.dnsServerAddress != null) {
            this.logger.log(this.level, "from {} : {} failure", this.dnsServerAddress, this.question, throwable);
        } else {
            this.logger.log(this.level, "{} query never written and failed", (Object)this.question, (Object)throwable);
        }
    }

    @Override
    public void querySucceed() {
    }
}

