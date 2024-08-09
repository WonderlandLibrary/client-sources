/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.ApplicationProtocolNegotiator;
import io.netty.handler.ssl.CipherSuiteFilter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.IdentityCipherSuiteFilter;
import io.netty.handler.ssl.JdkSslClientContext;
import io.netty.handler.ssl.JdkSslServerContext;
import io.netty.handler.ssl.OpenSsl;
import io.netty.handler.ssl.OpenSslClientContext;
import io.netty.handler.ssl.OpenSslServerContext;
import io.netty.handler.ssl.PemReader;
import io.netty.handler.ssl.ReferenceCountedOpenSslClientContext;
import io.netty.handler.ssl.ReferenceCountedOpenSslServerContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.ssl.SslProvider;
import io.netty.util.internal.EmptyArrays;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;

public abstract class SslContext {
    static final CertificateFactory X509_CERT_FACTORY;
    private final boolean startTls;

    public static SslProvider defaultServerProvider() {
        return SslContext.defaultProvider();
    }

    public static SslProvider defaultClientProvider() {
        return SslContext.defaultProvider();
    }

    private static SslProvider defaultProvider() {
        if (OpenSsl.isAvailable()) {
            return SslProvider.OPENSSL;
        }
        return SslProvider.JDK;
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2) throws SSLException {
        return SslContext.newServerContext(file, file2, null);
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2, String string) throws SSLException {
        return SslContext.newServerContext(null, file, file2, string);
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2, String string, Iterable<String> iterable, Iterable<String> iterable2, long l, long l2) throws SSLException {
        return SslContext.newServerContext(null, file, file2, string, iterable, iterable2, l, l2);
    }

    @Deprecated
    public static SslContext newServerContext(File file, File file2, String string, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        return SslContext.newServerContext(null, file, file2, string, iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2) throws SSLException {
        return SslContext.newServerContext(sslProvider, file, file2, null);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String string) throws SSLException {
        return SslContext.newServerContext(sslProvider, file, file2, string, null, IdentityCipherSuiteFilter.INSTANCE, null, 0L, 0L);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String string, Iterable<String> iterable, Iterable<String> iterable2, long l, long l2) throws SSLException {
        return SslContext.newServerContext(sslProvider, file, file2, string, iterable, IdentityCipherSuiteFilter.INSTANCE, SslContext.toApplicationProtocolConfig(iterable2), l, l2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String string, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long l, long l2) throws SSLException {
        return SslContext.newServerContext(sslProvider, null, trustManagerFactory, file, file2, string, null, iterable, IdentityCipherSuiteFilter.INSTANCE, SslContext.toApplicationProtocolConfig(iterable2), l, l2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, File file2, String string, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        return SslContext.newServerContext(sslProvider, null, null, file, file2, string, null, iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2);
    }

    @Deprecated
    public static SslContext newServerContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        try {
            return SslContext.newServerContextInternal(sslProvider, null, SslContext.toX509Certificates(file), trustManagerFactory, SslContext.toX509Certificates(file2), SslContext.toPrivateKey(file3, string), string, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2, ClientAuth.NONE, null, false, false);
        } catch (Exception exception) {
            if (exception instanceof SSLException) {
                throw (SSLException)exception;
            }
            throw new SSLException("failed to initialize the server-side SSL context", exception);
        }
    }

    static SslContext newServerContextInternal(SslProvider sslProvider, Provider provider, X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2, ClientAuth clientAuth, String[] stringArray, boolean bl, boolean bl2) throws SSLException {
        if (sslProvider == null) {
            sslProvider = SslContext.defaultServerProvider();
        }
        switch (1.$SwitchMap$io$netty$handler$ssl$SslProvider[sslProvider.ordinal()]) {
            case 1: {
                if (bl2) {
                    throw new IllegalArgumentException("OCSP is not supported with this SslProvider: " + (Object)((Object)sslProvider));
                }
                return new JdkSslServerContext(provider, x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2, clientAuth, stringArray, bl);
            }
            case 2: {
                SslContext.verifyNullSslContextProvider(sslProvider, provider);
                return new OpenSslServerContext(x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2, clientAuth, stringArray, bl, bl2);
            }
            case 3: {
                SslContext.verifyNullSslContextProvider(sslProvider, provider);
                return new ReferenceCountedOpenSslServerContext(x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2, clientAuth, stringArray, bl, bl2);
            }
        }
        throw new Error(sslProvider.toString());
    }

    private static void verifyNullSslContextProvider(SslProvider sslProvider, Provider provider) {
        if (provider != null) {
            throw new IllegalArgumentException("Java Security Provider unsupported for SslProvider: " + (Object)((Object)sslProvider));
        }
    }

    @Deprecated
    public static SslContext newClientContext() throws SSLException {
        return SslContext.newClientContext(null, null, null);
    }

    @Deprecated
    public static SslContext newClientContext(File file) throws SSLException {
        return SslContext.newClientContext(null, file);
    }

    @Deprecated
    public static SslContext newClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
        return SslContext.newClientContext(null, null, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(File file, TrustManagerFactory trustManagerFactory) throws SSLException {
        return SslContext.newClientContext(null, file, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long l, long l2) throws SSLException {
        return SslContext.newClientContext(null, file, trustManagerFactory, iterable, iterable2, l, l2);
    }

    @Deprecated
    public static SslContext newClientContext(File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        return SslContext.newClientContext(null, file, trustManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider) throws SSLException {
        return SslContext.newClientContext(sslProvider, null, null);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file) throws SSLException {
        return SslContext.newClientContext(sslProvider, file, null);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, TrustManagerFactory trustManagerFactory) throws SSLException {
        return SslContext.newClientContext(sslProvider, null, trustManagerFactory);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory) throws SSLException {
        return SslContext.newClientContext(sslProvider, file, trustManagerFactory, null, IdentityCipherSuiteFilter.INSTANCE, null, 0L, 0L);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, Iterable<String> iterable2, long l, long l2) throws SSLException {
        return SslContext.newClientContext(sslProvider, file, trustManagerFactory, null, null, null, null, iterable, IdentityCipherSuiteFilter.INSTANCE, SslContext.toApplicationProtocolConfig(iterable2), l, l2);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        return SslContext.newClientContext(sslProvider, file, trustManagerFactory, null, null, null, null, iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2);
    }

    @Deprecated
    public static SslContext newClientContext(SslProvider sslProvider, File file, TrustManagerFactory trustManagerFactory, File file2, File file3, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2) throws SSLException {
        try {
            return SslContext.newClientContextInternal(sslProvider, null, SslContext.toX509Certificates(file), trustManagerFactory, SslContext.toX509Certificates(file2), SslContext.toPrivateKey(file3, string), string, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, null, l, l2, false);
        } catch (Exception exception) {
            if (exception instanceof SSLException) {
                throw (SSLException)exception;
            }
            throw new SSLException("failed to initialize the client-side SSL context", exception);
        }
    }

    static SslContext newClientContextInternal(SslProvider sslProvider, Provider provider, X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, String[] stringArray, long l, long l2, boolean bl) throws SSLException {
        if (sslProvider == null) {
            sslProvider = SslContext.defaultClientProvider();
        }
        switch (1.$SwitchMap$io$netty$handler$ssl$SslProvider[sslProvider.ordinal()]) {
            case 1: {
                if (bl) {
                    throw new IllegalArgumentException("OCSP is not supported with this SslProvider: " + (Object)((Object)sslProvider));
                }
                return new JdkSslClientContext(provider, x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, stringArray, l, l2);
            }
            case 2: {
                SslContext.verifyNullSslContextProvider(sslProvider, provider);
                return new OpenSslClientContext(x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, stringArray, l, l2, bl);
            }
            case 3: {
                SslContext.verifyNullSslContextProvider(sslProvider, provider);
                return new ReferenceCountedOpenSslClientContext(x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory, iterable, cipherSuiteFilter, applicationProtocolConfig, stringArray, l, l2, bl);
            }
        }
        throw new Error(sslProvider.toString());
    }

    static ApplicationProtocolConfig toApplicationProtocolConfig(Iterable<String> iterable) {
        ApplicationProtocolConfig applicationProtocolConfig = iterable == null ? ApplicationProtocolConfig.DISABLED : new ApplicationProtocolConfig(ApplicationProtocolConfig.Protocol.NPN_AND_ALPN, ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL, ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT, iterable);
        return applicationProtocolConfig;
    }

    protected SslContext() {
        this(false);
    }

    protected SslContext(boolean bl) {
        this.startTls = bl;
    }

    public final boolean isServer() {
        return !this.isClient();
    }

    public abstract boolean isClient();

    public abstract List<String> cipherSuites();

    public abstract long sessionCacheSize();

    public abstract long sessionTimeout();

    @Deprecated
    public final List<String> nextProtocols() {
        return this.applicationProtocolNegotiator().protocols();
    }

    public abstract ApplicationProtocolNegotiator applicationProtocolNegotiator();

    public abstract SSLEngine newEngine(ByteBufAllocator var1);

    public abstract SSLEngine newEngine(ByteBufAllocator var1, String var2, int var3);

    public abstract SSLSessionContext sessionContext();

    public final SslHandler newHandler(ByteBufAllocator byteBufAllocator) {
        return this.newHandler(byteBufAllocator, this.startTls);
    }

    protected SslHandler newHandler(ByteBufAllocator byteBufAllocator, boolean bl) {
        return new SslHandler(this.newEngine(byteBufAllocator), bl);
    }

    public final SslHandler newHandler(ByteBufAllocator byteBufAllocator, String string, int n) {
        return this.newHandler(byteBufAllocator, string, n, this.startTls);
    }

    protected SslHandler newHandler(ByteBufAllocator byteBufAllocator, String string, int n, boolean bl) {
        return new SslHandler(this.newEngine(byteBufAllocator, string, n), bl);
    }

    protected static PKCS8EncodedKeySpec generateKeySpec(char[] cArray, byte[] byArray) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (cArray == null) {
            return new PKCS8EncodedKeySpec(byArray);
        }
        EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(byArray);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName());
        PBEKeySpec pBEKeySpec = new PBEKeySpec(cArray);
        SecretKey secretKey = secretKeyFactory.generateSecret(pBEKeySpec);
        Cipher cipher = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
        cipher.init(2, (Key)secretKey, encryptedPrivateKeyInfo.getAlgParameters());
        return encryptedPrivateKeyInfo.getKeySpec(cipher);
    }

    static KeyStore buildKeyStore(X509Certificate[] x509CertificateArray, PrivateKey privateKey, char[] cArray) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        keyStore.setKeyEntry("key", privateKey, cArray, x509CertificateArray);
        return keyStore;
    }

    static PrivateKey toPrivateKey(File file, String string) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        if (file == null) {
            return null;
        }
        return SslContext.getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(file), string);
    }

    static PrivateKey toPrivateKey(InputStream inputStream, String string) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        if (inputStream == null) {
            return null;
        }
        return SslContext.getPrivateKeyFromByteBuffer(PemReader.readPrivateKey(inputStream), string);
    }

    private static PrivateKey getPrivateKeyFromByteBuffer(ByteBuf byteBuf, String string) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, KeyException, IOException {
        byte[] byArray = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(byArray).release();
        PKCS8EncodedKeySpec pKCS8EncodedKeySpec = SslContext.generateKeySpec(string == null ? null : string.toCharArray(), byArray);
        try {
            return KeyFactory.getInstance("RSA").generatePrivate(pKCS8EncodedKeySpec);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            try {
                return KeyFactory.getInstance("DSA").generatePrivate(pKCS8EncodedKeySpec);
            } catch (InvalidKeySpecException invalidKeySpecException2) {
                try {
                    return KeyFactory.getInstance("EC").generatePrivate(pKCS8EncodedKeySpec);
                } catch (InvalidKeySpecException invalidKeySpecException3) {
                    throw new InvalidKeySpecException("Neither RSA, DSA nor EC worked", invalidKeySpecException3);
                }
            }
        }
    }

    @Deprecated
    protected static TrustManagerFactory buildTrustManagerFactory(File file, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
        X509Certificate[] x509CertificateArray = SslContext.toX509Certificates(file);
        return SslContext.buildTrustManagerFactory(x509CertificateArray, trustManagerFactory);
    }

    static X509Certificate[] toX509Certificates(File file) throws CertificateException {
        if (file == null) {
            return null;
        }
        return SslContext.getCertificatesFromBuffers(PemReader.readCertificates(file));
    }

    static X509Certificate[] toX509Certificates(InputStream inputStream) throws CertificateException {
        if (inputStream == null) {
            return null;
        }
        return SslContext.getCertificatesFromBuffers(PemReader.readCertificates(inputStream));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static X509Certificate[] getCertificatesFromBuffers(ByteBuf[] byteBufArray) throws CertificateException {
        int n;
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate[] x509CertificateArray = new X509Certificate[byteBufArray.length];
        try {
            for (n = 0; n < byteBufArray.length; ++n) {
                ByteBufInputStream byteBufInputStream = new ByteBufInputStream(byteBufArray[n], true);
                try {
                    x509CertificateArray[n] = (X509Certificate)certificateFactory.generateCertificate(byteBufInputStream);
                    continue;
                } finally {
                    try {
                        ((InputStream)byteBufInputStream).close();
                    } catch (IOException iOException) {
                        throw new RuntimeException(iOException);
                    }
                }
            }
        } finally {
            while (n < byteBufArray.length) {
                byteBufArray[n].release();
                ++n;
            }
        }
        return x509CertificateArray;
    }

    static TrustManagerFactory buildTrustManagerFactory(X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory) throws NoSuchAlgorithmException, CertificateException, KeyStoreException, IOException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null);
        int n = 1;
        for (X509Certificate x509Certificate : x509CertificateArray) {
            String string = Integer.toString(n);
            keyStore.setCertificateEntry(string, x509Certificate);
            ++n;
        }
        if (trustManagerFactory == null) {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        }
        trustManagerFactory.init(keyStore);
        return trustManagerFactory;
    }

    static PrivateKey toPrivateKeyInternal(File file, String string) throws SSLException {
        try {
            return SslContext.toPrivateKey(file, string);
        } catch (Exception exception) {
            throw new SSLException(exception);
        }
    }

    static X509Certificate[] toX509CertificatesInternal(File file) throws SSLException {
        try {
            return SslContext.toX509Certificates(file);
        } catch (CertificateException certificateException) {
            throw new SSLException(certificateException);
        }
    }

    static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] x509CertificateArray, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        return SslContext.buildKeyManagerFactory(x509CertificateArray, KeyManagerFactory.getDefaultAlgorithm(), privateKey, string, keyManagerFactory);
    }

    static KeyManagerFactory buildKeyManagerFactory(X509Certificate[] x509CertificateArray, String string, PrivateKey privateKey, String string2, KeyManagerFactory keyManagerFactory) throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, UnrecoverableKeyException {
        char[] cArray = string2 == null ? EmptyArrays.EMPTY_CHARS : string2.toCharArray();
        KeyStore keyStore = SslContext.buildKeyStore(x509CertificateArray, privateKey, cArray);
        if (keyManagerFactory == null) {
            keyManagerFactory = KeyManagerFactory.getInstance(string);
        }
        keyManagerFactory.init(keyStore, cArray);
        return keyManagerFactory;
    }

    static {
        try {
            X509_CERT_FACTORY = CertificateFactory.getInstance("X.509");
        } catch (CertificateException certificateException) {
            throw new IllegalStateException("unable to instance X.509 CertificateFactory", certificateException);
        }
    }
}

