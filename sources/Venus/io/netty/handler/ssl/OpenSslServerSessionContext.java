/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.SSL
 *  io.netty.internal.tcnative.SSLContext
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.OpenSslSessionContext;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import java.util.concurrent.locks.Lock;

public final class OpenSslServerSessionContext
extends OpenSslSessionContext {
    OpenSslServerSessionContext(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
        super(referenceCountedOpenSslContext);
    }

    @Override
    public void setSessionTimeout(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        Lock lock = this.context.ctxLock.writeLock();
        lock.lock();
        try {
            SSLContext.setSessionCacheTimeout((long)this.context.ctx, (long)n);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getSessionTimeout() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            int n = (int)SSLContext.getSessionCacheTimeout((long)this.context.ctx);
            return n;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setSessionCacheSize(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        Lock lock = this.context.ctxLock.writeLock();
        lock.lock();
        try {
            SSLContext.setSessionCacheSize((long)this.context.ctx, (long)n);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getSessionCacheSize() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            int n = (int)SSLContext.getSessionCacheSize((long)this.context.ctx);
            return n;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setSessionCacheEnabled(boolean bl) {
        long l = bl ? SSL.SSL_SESS_CACHE_SERVER : SSL.SSL_SESS_CACHE_OFF;
        Lock lock = this.context.ctxLock.writeLock();
        lock.lock();
        try {
            SSLContext.setSessionCacheMode((long)this.context.ctx, (long)l);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isSessionCacheEnabled() {
        Lock lock = this.context.ctxLock.readLock();
        lock.lock();
        try {
            boolean bl = SSLContext.getSessionCacheMode((long)this.context.ctx) == SSL.SSL_SESS_CACHE_SERVER;
            return bl;
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean setSessionIdContext(byte[] byArray) {
        Lock lock = this.context.ctxLock.writeLock();
        lock.lock();
        try {
            boolean bl = SSLContext.setSessionIdContext((long)this.context.ctx, (byte[])byArray);
            return bl;
        } finally {
            lock.unlock();
        }
    }
}

