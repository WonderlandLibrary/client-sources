/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.CipherSuiteFilter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.IdentityCipherSuiteFilter;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslProvider;
import io.netty.util.internal.ObjectUtil;
import java.io.File;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManagerFactory;

public final class SslContextBuilder {
    private final boolean forServer;
    private SslProvider provider;
    private Provider sslContextProvider;
    private X509Certificate[] trustCertCollection;
    private TrustManagerFactory trustManagerFactory;
    private X509Certificate[] keyCertChain;
    private PrivateKey key;
    private String keyPassword;
    private KeyManagerFactory keyManagerFactory;
    private Iterable<String> ciphers;
    private CipherSuiteFilter cipherFilter = IdentityCipherSuiteFilter.INSTANCE;
    private ApplicationProtocolConfig apn;
    private long sessionCacheSize;
    private long sessionTimeout;
    private ClientAuth clientAuth = ClientAuth.NONE;
    private String[] protocols;
    private boolean startTls;
    private boolean enableOcsp;

    public static SslContextBuilder forClient() {
        return new SslContextBuilder(false);
    }

    public static SslContextBuilder forServer(File file, File file2) {
        return new SslContextBuilder(true).keyManager(file, file2);
    }

    public static SslContextBuilder forServer(InputStream inputStream, InputStream inputStream2) {
        return new SslContextBuilder(true).keyManager(inputStream, inputStream2);
    }

    public static SslContextBuilder forServer(PrivateKey privateKey, X509Certificate ... x509CertificateArray) {
        return new SslContextBuilder(true).keyManager(privateKey, x509CertificateArray);
    }

    public static SslContextBuilder forServer(File file, File file2, String string) {
        return new SslContextBuilder(true).keyManager(file, file2, string);
    }

    public static SslContextBuilder forServer(InputStream inputStream, InputStream inputStream2, String string) {
        return new SslContextBuilder(true).keyManager(inputStream, inputStream2, string);
    }

    public static SslContextBuilder forServer(PrivateKey privateKey, String string, X509Certificate ... x509CertificateArray) {
        return new SslContextBuilder(true).keyManager(privateKey, string, x509CertificateArray);
    }

    public static SslContextBuilder forServer(KeyManagerFactory keyManagerFactory) {
        return new SslContextBuilder(true).keyManager(keyManagerFactory);
    }

    private SslContextBuilder(boolean bl) {
        this.forServer = bl;
    }

    public SslContextBuilder sslProvider(SslProvider sslProvider) {
        this.provider = sslProvider;
        return this;
    }

    public SslContextBuilder sslContextProvider(Provider provider) {
        this.sslContextProvider = provider;
        return this;
    }

    public SslContextBuilder trustManager(File file) {
        try {
            return this.trustManager(SslContext.toX509Certificates(file));
        } catch (Exception exception) {
            throw new IllegalArgumentException("File does not contain valid certificates: " + file, exception);
        }
    }

    public SslContextBuilder trustManager(InputStream inputStream) {
        try {
            return this.trustManager(SslContext.toX509Certificates(inputStream));
        } catch (Exception exception) {
            throw new IllegalArgumentException("Input stream does not contain valid certificates.", exception);
        }
    }

    public SslContextBuilder trustManager(X509Certificate ... x509CertificateArray) {
        this.trustCertCollection = x509CertificateArray != null ? (X509Certificate[])x509CertificateArray.clone() : null;
        this.trustManagerFactory = null;
        return this;
    }

    public SslContextBuilder trustManager(TrustManagerFactory trustManagerFactory) {
        this.trustCertCollection = null;
        this.trustManagerFactory = trustManagerFactory;
        return this;
    }

    public SslContextBuilder keyManager(File file, File file2) {
        return this.keyManager(file, file2, null);
    }

    public SslContextBuilder keyManager(InputStream inputStream, InputStream inputStream2) {
        return this.keyManager(inputStream, inputStream2, null);
    }

    public SslContextBuilder keyManager(PrivateKey privateKey, X509Certificate ... x509CertificateArray) {
        return this.keyManager(privateKey, (String)null, x509CertificateArray);
    }

    public SslContextBuilder keyManager(File file, File file2, String string) {
        PrivateKey privateKey;
        X509Certificate[] x509CertificateArray;
        try {
            x509CertificateArray = SslContext.toX509Certificates(file);
        } catch (Exception exception) {
            throw new IllegalArgumentException("File does not contain valid certificates: " + file, exception);
        }
        try {
            privateKey = SslContext.toPrivateKey(file2, string);
        } catch (Exception exception) {
            throw new IllegalArgumentException("File does not contain valid private key: " + file2, exception);
        }
        return this.keyManager(privateKey, string, x509CertificateArray);
    }

    public SslContextBuilder keyManager(InputStream inputStream, InputStream inputStream2, String string) {
        PrivateKey privateKey;
        X509Certificate[] x509CertificateArray;
        try {
            x509CertificateArray = SslContext.toX509Certificates(inputStream);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Input stream not contain valid certificates.", exception);
        }
        try {
            privateKey = SslContext.toPrivateKey(inputStream2, string);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Input stream does not contain valid private key.", exception);
        }
        return this.keyManager(privateKey, string, x509CertificateArray);
    }

    public SslContextBuilder keyManager(PrivateKey privateKey, String string, X509Certificate ... x509CertificateArray) {
        if (this.forServer) {
            ObjectUtil.checkNotNull(x509CertificateArray, "keyCertChain required for servers");
            if (x509CertificateArray.length == 0) {
                throw new IllegalArgumentException("keyCertChain must be non-empty");
            }
            ObjectUtil.checkNotNull(privateKey, "key required for servers");
        }
        if (x509CertificateArray == null || x509CertificateArray.length == 0) {
            this.keyCertChain = null;
        } else {
            for (X509Certificate x509Certificate : x509CertificateArray) {
                if (x509Certificate != null) continue;
                throw new IllegalArgumentException("keyCertChain contains null entry");
            }
            this.keyCertChain = (X509Certificate[])x509CertificateArray.clone();
        }
        this.key = privateKey;
        this.keyPassword = string;
        this.keyManagerFactory = null;
        return this;
    }

    public SslContextBuilder keyManager(KeyManagerFactory keyManagerFactory) {
        if (this.forServer) {
            ObjectUtil.checkNotNull(keyManagerFactory, "keyManagerFactory required for servers");
        }
        this.keyCertChain = null;
        this.key = null;
        this.keyPassword = null;
        this.keyManagerFactory = keyManagerFactory;
        return this;
    }

    public SslContextBuilder ciphers(Iterable<String> iterable) {
        return this.ciphers(iterable, IdentityCipherSuiteFilter.INSTANCE);
    }

    public SslContextBuilder ciphers(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter) {
        ObjectUtil.checkNotNull(cipherSuiteFilter, "cipherFilter");
        this.ciphers = iterable;
        this.cipherFilter = cipherSuiteFilter;
        return this;
    }

    public SslContextBuilder applicationProtocolConfig(ApplicationProtocolConfig applicationProtocolConfig) {
        this.apn = applicationProtocolConfig;
        return this;
    }

    public SslContextBuilder sessionCacheSize(long l) {
        this.sessionCacheSize = l;
        return this;
    }

    public SslContextBuilder sessionTimeout(long l) {
        this.sessionTimeout = l;
        return this;
    }

    public SslContextBuilder clientAuth(ClientAuth clientAuth) {
        this.clientAuth = ObjectUtil.checkNotNull(clientAuth, "clientAuth");
        return this;
    }

    public SslContextBuilder protocols(String ... stringArray) {
        this.protocols = stringArray == null ? null : (String[])stringArray.clone();
        return this;
    }

    public SslContextBuilder startTls(boolean bl) {
        this.startTls = bl;
        return this;
    }

    public SslContextBuilder enableOcsp(boolean bl) {
        this.enableOcsp = bl;
        return this;
    }

    public SslContext build() throws SSLException {
        if (this.forServer) {
            return SslContext.newServerContextInternal(this.provider, this.sslContextProvider, this.trustCertCollection, this.trustManagerFactory, this.keyCertChain, this.key, this.keyPassword, this.keyManagerFactory, this.ciphers, this.cipherFilter, this.apn, this.sessionCacheSize, this.sessionTimeout, this.clientAuth, this.protocols, this.startTls, this.enableOcsp);
        }
        return SslContext.newClientContextInternal(this.provider, this.sslContextProvider, this.trustCertCollection, this.trustManagerFactory, this.keyCertChain, this.key, this.keyPassword, this.keyManagerFactory, this.ciphers, this.cipherFilter, this.apn, this.protocols, this.sessionCacheSize, this.sessionTimeout, this.enableOcsp);
    }
}

