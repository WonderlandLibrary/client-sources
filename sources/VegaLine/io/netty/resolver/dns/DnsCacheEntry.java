/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.resolver.dns;

import io.netty.channel.EventLoop;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public final class DnsCacheEntry {
    private final String hostname;
    private final InetAddress address;
    private final Throwable cause;
    private volatile ScheduledFuture<?> expirationFuture;

    public DnsCacheEntry(String hostname, InetAddress address) {
        this.hostname = ObjectUtil.checkNotNull(hostname, "hostname");
        this.address = ObjectUtil.checkNotNull(address, "address");
        this.cause = null;
    }

    public DnsCacheEntry(String hostname, Throwable cause) {
        this.hostname = ObjectUtil.checkNotNull(hostname, "hostname");
        this.cause = ObjectUtil.checkNotNull(cause, "cause");
        this.address = null;
    }

    public String hostname() {
        return this.hostname;
    }

    public InetAddress address() {
        return this.address;
    }

    public Throwable cause() {
        return this.cause;
    }

    void scheduleExpiration(EventLoop loop, Runnable task, long delay, TimeUnit unit) {
        assert (this.expirationFuture == null) : "expiration task scheduled already";
        this.expirationFuture = loop.schedule(task, delay, unit);
    }

    void cancelExpiration() {
        ScheduledFuture<?> expirationFuture = this.expirationFuture;
        if (expirationFuture != null) {
            expirationFuture.cancel(false);
        }
    }

    public String toString() {
        if (this.cause != null) {
            return this.hostname + '/' + this.cause;
        }
        return this.address.toString();
    }
}

