/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.SSL
 *  io.netty.internal.tcnative.SSLContext
 *  io.netty.internal.tcnative.SessionTicketKey
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.OpenSslSessionStats;
import io.netty.handler.ssl.OpenSslSessionTicketKey;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.internal.tcnative.SessionTicketKey;
import io.netty.util.internal.ObjectUtil;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;

public abstract class OpenSslSessionContext
implements SSLSessionContext {
    private static final Enumeration<byte[]> EMPTY = new EmptyEnumeration(null);
    private final OpenSslSessionStats stats;
    final ReferenceCountedOpenSslContext context;

    OpenSslSessionContext(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
        this.context = referenceCountedOpenSslContext;
        this.stats = new OpenSslSessionStats(referenceCountedOpenSslContext);
    }

    @Override
    public SSLSession getSession(byte[] byArray) {
        if (byArray == null) {
            throw new NullPointerException("bytes");
        }
        return null;
    }

    @Override
    public Enumeration<byte[]> getIds() {
        return EMPTY;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public void setTicketKeys(byte[] byArray) {
        if (byArray.length % 48 != 0) {
            throw new IllegalArgumentException("keys.length % 48 != 0");
        }
        SessionTicketKey[] sessionTicketKeyArray = new SessionTicketKey[byArray.length / 48];
        int n = 0;
        for (int i = 0; i < sessionTicketKeyArray.length; ++i) {
            byte[] byArray2 = Arrays.copyOfRange(byArray, n, 16);
            byte[] byArray3 = Arrays.copyOfRange(byArray, n += 16, 16);
            byte[] byArray4 = Arrays.copyOfRange(byArray, n, 16);
            n += 16;
            sessionTicketKeyArray[i += 16] = new SessionTicketKey(byArray2, byArray3, byArray4);
        }
        Lock lock = this.context.ctxLock.writeLock();
        lock.lock();
        try {
            SSLContext.clearOptions((long)this.context.ctx, (int)SSL.SSL_OP_NO_TICKET);
            SSLContext.setSessionTicketKeys((long)this.context.ctx, (SessionTicketKey[])sessionTicketKeyArray);
        } finally {
            lock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setTicketKeys(OpenSslSessionTicketKey ... openSslSessionTicketKeyArray) {
        ObjectUtil.checkNotNull(openSslSessionTicketKeyArray, "keys");
        SessionTicketKey[] sessionTicketKeyArray = new SessionTicketKey[openSslSessionTicketKeyArray.length];
        for (int i = 0; i < sessionTicketKeyArray.length; ++i) {
            sessionTicketKeyArray[i] = openSslSessionTicketKeyArray[i].key;
        }
        Lock lock = this.context.ctxLock.writeLock();
        lock.lock();
        try {
            SSLContext.clearOptions((long)this.context.ctx, (int)SSL.SSL_OP_NO_TICKET);
            SSLContext.setSessionTicketKeys((long)this.context.ctx, (SessionTicketKey[])sessionTicketKeyArray);
        } finally {
            lock.unlock();
        }
    }

    public abstract void setSessionCacheEnabled(boolean var1);

    public abstract boolean isSessionCacheEnabled();

    public OpenSslSessionStats stats() {
        return this.stats;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class EmptyEnumeration
    implements Enumeration<byte[]> {
        private EmptyEnumeration() {
        }

        @Override
        public boolean hasMoreElements() {
            return true;
        }

        @Override
        public byte[] nextElement() {
            throw new NoSuchElementException();
        }

        @Override
        public Object nextElement() {
            return this.nextElement();
        }

        EmptyEnumeration(1 var1_1) {
            this();
        }
    }
}

