/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.CipherSuiteFilter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.IdentityCipherSuiteFilter;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkDefaultApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkSslContext;
import java.io.File;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;

@Deprecated
public final class JdkSslClientContext
extends JdkSslContext {
    @Deprecated
    public JdkSslClientContext() throws SSLException {
        this(null, null);
    }

    @Deprecated
    public JdkSslClientContext(File file) throws SSLException {
        this(file, null);
    }

    @Deprecated
    public JdkSslClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
        this(null, trustManagerFactory);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory) throws SSLException {
        this(file, trustManagerFactory, null, (CipherSuiteFilter)IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, 0L, 0L);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long l, long l2) throws SSLException {
        this(file, trustManagerFactory, iterable, (CipherSuiteFilter)IdentityCipherSuiteFilter.INSTANCE, JdkSslClientContext.toNegotiator(JdkSslClientContext.toApplicationProtocolConfig(iterable2), false), l, l2);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        this(file, trustManagerFactory, iterable, cipherSuiteFilter, JdkSslClientContext.toNegotiator(applicationProtocolConfig, false), l, l2);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long l, long l2) throws SSLException {
        this(null, file, trustManagerFactory, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, l, l2);
    }

    JdkSslClientContext(Provider provider, File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long l, long l2) throws SSLException {
        super(JdkSslClientContext.newSSLContext(provider, JdkSslClientContext.toX509CertificatesInternal(file), trustManagerFactory, null, null, null, null, l, l2), true, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, ClientAuth.NONE, null, false);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        this(file, trustManagerFactory, file2, file3, string, keyManagerFactory, iterable, cipherSuiteFilter, JdkSslClientContext.toNegotiator(applicationProtocolConfig, false), l, l2);
    }

    @Deprecated
    public JdkSslClientContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long l, long l2) throws SSLException {
        super(JdkSslClientContext.newSSLContext(null, JdkSslClientContext.toX509CertificatesInternal(file), trustManagerFactory, JdkSslClientContext.toX509CertificatesInternal(file2), JdkSslClientContext.toPrivateKeyInternal(file3, string), string, keyManagerFactory, l, l2), true, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, ClientAuth.NONE, null, false);
    }

    JdkSslClientContext(Provider provider, X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, String[] stringArray, long l, long l2) throws SSLException {
        super(JdkSslClientContext.newSSLContext(provider, x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory, l, l2), true, iterable, cipherSuiteFilter, JdkSslClientContext.toNegotiator(applicationProtocolConfig, false), ClientAuth.NONE, stringArray, false);
    }

    private static SSLContext newSSLContext(Provider provider, X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, long l, long l2) throws SSLException {
        try {
            if (x509CertificateArray != null) {
                trustManagerFactory = JdkSslClientContext.buildTrustManagerFactory(x509CertificateArray, trustManagerFactory);
            }
            if (x509CertificateArray2 != null) {
                keyManagerFactory = JdkSslClientContext.buildKeyManagerFactory(x509CertificateArray2, privateKey, string, keyManagerFactory);
            }
            SSLContext sSLContext = provider == null ? SSLContext.getInstance("TLS") : SSLContext.getInstance("TLS", provider);
            sSLContext.init(keyManagerFactory == null ? null : keyManagerFactory.getKeyManagers(), trustManagerFactory == null ? null : trustManagerFactory.getTrustManagers(), null);
            SSLSessionContext sSLSessionContext = sSLContext.getClientSessionContext();
            if (l > 0L) {
                sSLSessionContext.setSessionCacheSize((int)Math.min(l, Integer.MAX_VALUE));
            }
            if (l2 > 0L) {
                sSLSessionContext.setSessionTimeout((int)Math.min(l2, Integer.MAX_VALUE));
            }
            return sSLContext;
        } catch (Exception exception) {
            if (exception instanceof SSLException) {
                throw (SSLException)exception;
            }
            throw new SSLException("failed to initialize the client-side SSL context", exception);
        }
    }
}

