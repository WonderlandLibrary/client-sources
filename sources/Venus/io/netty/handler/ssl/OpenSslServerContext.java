/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.CipherSuiteFilter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.IdentityCipherSuiteFilter;
import io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator;
import io.netty.handler.ssl.OpenSslContext;
import io.netty.handler.ssl.OpenSslKeyMaterialManager;
import io.netty.handler.ssl.OpenSslServerSessionContext;
import io.netty.handler.ssl.OpenSslSessionContext;
import io.netty.handler.ssl.ReferenceCountedOpenSslServerContext;
import java.io.File;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class OpenSslServerContext
extends OpenSslContext {
    private final OpenSslServerSessionContext sessionContext;
    private final OpenSslKeyMaterialManager keyMaterialManager;

    @Deprecated
    public OpenSslServerContext(File file, File file2) throws SSLException {
        this(file, file2, null);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String string) throws SSLException {
        this(file, file2, string, null, IdentityCipherSuiteFilter.INSTANCE, ApplicationProtocolConfig.DISABLED, 0L, 0L);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String string, Iterable<String> iterable, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        this(file, file2, string, iterable, IdentityCipherSuiteFilter.INSTANCE, applicationProtocolConfig, l, l2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String string, Iterable<String> iterable, Iterable<String> iterable2, long l, long l2) throws SSLException {
        this(file, file2, string, iterable, OpenSslServerContext.toApplicationProtocolConfig(iterable2), l, l2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String string, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        this(file, file2, string, trustManagerFactory, iterable, OpenSslServerContext.toNegotiator(applicationProtocolConfig), l, l2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String string, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long l, long l2) throws SSLException {
        this(null, trustManagerFactory, file, file2, string, null, iterable, null, openSslApplicationProtocolNegotiator, l, l2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String string, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        this(null, null, file, file2, string, null, iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2);
    }

    @Deprecated
    public OpenSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        this(file, trustManagerFactory, file2, file3, string, keyManagerFactory, iterable, cipherSuiteFilter, OpenSslServerContext.toNegotiator(applicationProtocolConfig), l, l2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String string, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        this(null, trustManagerFactory, file, file2, string, null, iterable, cipherSuiteFilter, OpenSslServerContext.toNegotiator(applicationProtocolConfig), l, l2);
    }

    @Deprecated
    public OpenSslServerContext(File file, File file2, String string, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long l, long l2) throws SSLException {
        this(null, trustManagerFactory, file, file2, string, null, iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, l, l2);
    }

    @Deprecated
    public OpenSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long l, long l2) throws SSLException {
        this(OpenSslServerContext.toX509CertificatesInternal(file), trustManagerFactory, OpenSslServerContext.toX509CertificatesInternal(file2), OpenSslServerContext.toPrivateKeyInternal(file3, string), string, keyManagerFactory, iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, l, l2, ClientAuth.NONE, null, false, false);
    }

    OpenSslServerContext(X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2, ClientAuth clientAuth, String[] stringArray, boolean bl, boolean bl2) throws SSLException {
        this(x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory, iterable, cipherSuiteFilter, OpenSslServerContext.toNegotiator(applicationProtocolConfig), l, l2, clientAuth, stringArray, bl, bl2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private OpenSslServerContext(X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long l, long l2, ClientAuth clientAuth, String[] stringArray, boolean bl, boolean bl2) throws SSLException {
        super(iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, l, l2, 1, (Certificate[])x509CertificateArray2, clientAuth, stringArray, bl, bl2);
        boolean bl3 = false;
        try {
            ReferenceCountedOpenSslServerContext.ServerContext serverContext = ReferenceCountedOpenSslServerContext.newSessionContext(this, this.ctx, this.engineMap, x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory);
            this.sessionContext = serverContext.sessionContext;
            this.keyMaterialManager = serverContext.keyMaterialManager;
            bl3 = true;
        } finally {
            if (!bl3) {
                this.release();
            }
        }
    }

    @Override
    public OpenSslServerSessionContext sessionContext() {
        return this.sessionContext;
    }

    @Override
    OpenSslKeyMaterialManager keyMaterialManager() {
        return this.keyMaterialManager;
    }

    @Override
    public OpenSslSessionContext sessionContext() {
        return this.sessionContext();
    }

    @Override
    public SSLSessionContext sessionContext() {
        return this.sessionContext();
    }
}

