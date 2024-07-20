/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.CipherSuiteFilter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.OpenSsl;
import io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator;
import io.netty.handler.ssl.OpenSslEngine;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import java.security.cert.Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;

public abstract class OpenSslContext
extends ReferenceCountedOpenSslContext {
    OpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apnCfg, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls) throws SSLException {
        super(ciphers, cipherFilter, apnCfg, sessionCacheSize, sessionTimeout, mode, keyCertChain, clientAuth, protocols, startTls, false);
    }

    OpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls) throws SSLException {
        super(ciphers, cipherFilter, apn, sessionCacheSize, sessionTimeout, mode, keyCertChain, clientAuth, protocols, startTls, false);
    }

    @Override
    final SSLEngine newEngine0(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return new OpenSslEngine(this, alloc, peerHost, peerPort);
    }

    protected final void finalize() throws Throwable {
        super.finalize();
        OpenSsl.releaseIfNeeded(this);
    }
}

