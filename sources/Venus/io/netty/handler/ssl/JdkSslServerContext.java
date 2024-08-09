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
public final class JdkSslServerContext
extends JdkSslContext {
    @Deprecated
    public JdkSslServerContext(File file, File file2) throws SSLException {
        this(file, file2, null);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String string) throws SSLException {
        this(file, file2, string, null, (CipherSuiteFilter)IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, 0L, 0L);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String string, Iterable<String> iterable, Iterable<String> iterable2, long l, long l2) throws SSLException {
        this(file, file2, string, iterable, (CipherSuiteFilter)IdentityCipherSuiteFilter.INSTANCE, JdkSslServerContext.toNegotiator(JdkSslServerContext.toApplicationProtocolConfig(iterable2), true), l, l2);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String string, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        this(file, file2, string, iterable, cipherSuiteFilter, JdkSslServerContext.toNegotiator(applicationProtocolConfig, true), l, l2);
    }

    @Deprecated
    public JdkSslServerContext(File file, File file2, String string, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long l, long l2) throws SSLException {
        this(null, file, file2, string, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, l, l2);
    }

    JdkSslServerContext(Provider provider, File file, File file2, String string, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long l, long l2) throws SSLException {
        super(JdkSslServerContext.newSSLContext(provider, null, null, JdkSslServerContext.toX509CertificatesInternal(file), JdkSslServerContext.toPrivateKeyInternal(file2, string), string, null, l, l2), false, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, ClientAuth.NONE, null, false);
    }

    @Deprecated
    public JdkSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        this(file, trustManagerFactory, file2, file3, string, keyManagerFactory, iterable, cipherSuiteFilter, JdkSslServerContext.toNegotiator(applicationProtocolConfig, true), l, l2);
    }

    @Deprecated
    public JdkSslServerContext(File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, long l, long l2) throws SSLException {
        super(JdkSslServerContext.newSSLContext(null, JdkSslServerContext.toX509CertificatesInternal(file), trustManagerFactory, JdkSslServerContext.toX509CertificatesInternal(file2), JdkSslServerContext.toPrivateKeyInternal(file3, string), string, keyManagerFactory, l, l2), false, iterable, cipherSuiteFilter, jdkApplicationProtocolNegotiator, ClientAuth.NONE, null, false);
    }

    JdkSslServerContext(Provider provider, X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2, ClientAuth clientAuth, String[] stringArray, boolean bl) throws SSLException {
        super(JdkSslServerContext.newSSLContext(provider, x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory, l, l2), false, iterable, cipherSuiteFilter, JdkSslServerContext.toNegotiator(applicationProtocolConfig, true), clientAuth, stringArray, bl);
    }

    private static SSLContext newSSLContext(Provider provider, X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, long l, long l2) throws SSLException {
        if (privateKey == null && keyManagerFactory == null) {
            throw new NullPointerException("key, keyManagerFactory");
        }
        try {
            if (x509CertificateArray != null) {
                trustManagerFactory = JdkSslServerContext.buildTrustManagerFactory(x509CertificateArray, trustManagerFactory);
            }
            if (privateKey != null) {
                keyManagerFactory = JdkSslServerContext.buildKeyManagerFactory(x509CertificateArray2, privateKey, string, keyManagerFactory);
            }
            SSLContext sSLContext = provider == null ? SSLContext.getInstance("TLS") : SSLContext.getInstance("TLS", provider);
            sSLContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory == null ? null : trustManagerFactory.getTrustManagers(), null);
            SSLSessionContext sSLSessionContext = sSLContext.getServerSessionContext();
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
            throw new SSLException("failed to initialize the server-side SSL context", exception);
        }
    }
}

