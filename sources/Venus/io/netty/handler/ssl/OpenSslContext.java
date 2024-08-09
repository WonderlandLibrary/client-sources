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
    OpenSslContext(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2, int n, Certificate[] certificateArray, ClientAuth clientAuth, String[] stringArray, boolean bl, boolean bl2) throws SSLException {
        super(iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2, n, certificateArray, clientAuth, stringArray, bl, bl2, false);
    }

    OpenSslContext(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long l, long l2, int n, Certificate[] certificateArray, ClientAuth clientAuth, String[] stringArray, boolean bl, boolean bl2) throws SSLException {
        super(iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, l, l2, n, certificateArray, clientAuth, stringArray, bl, bl2, false);
    }

    @Override
    final SSLEngine newEngine0(ByteBufAllocator byteBufAllocator, String string, int n, boolean bl) {
        return new OpenSslEngine(this, byteBufAllocator, string, n, bl);
    }

    protected final void finalize() throws Throwable {
        super.finalize();
        OpenSsl.releaseIfNeeded(this);
    }
}

