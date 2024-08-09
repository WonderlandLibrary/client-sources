/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.SSLContext
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.internal.tcnative.SSLContext;
import java.util.concurrent.locks.Lock;

public final class OpenSslSessionStats {
    private final ReferenceCountedOpenSslContext context;

    OpenSslSessionStats(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
        this.context = referenceCountedOpenSslContext;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long number() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionNumber((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long connect() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionConnect((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long connectGood() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionConnectGood((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long connectRenegotiate() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionConnectRenegotiate((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long accept() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionAccept((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long acceptGood() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionAcceptGood((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long acceptRenegotiate() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionAcceptRenegotiate((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long hits() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionHits((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long cbHits() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionCbHits((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long misses() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionMisses((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long timeouts() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionTimeouts((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long cacheFull() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionCacheFull((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long ticketKeyFail() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionTicketKeyFail((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long ticketKeyNew() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionTicketKeyNew((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long ticketKeyRenew() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionTicketKeyRenew((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public long ticketKeyResume() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            long l = SSLContext.sessionTicketKeyResume((long)this.context.ctx);
            return l;
        } finally {
            lock.unlock();
        }
    }
}

