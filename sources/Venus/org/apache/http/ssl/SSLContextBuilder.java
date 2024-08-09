/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.ssl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.ssl.PrivateKeyDetails;
import org.apache.http.ssl.PrivateKeyStrategy;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.Args;

public class SSLContextBuilder {
    static final String TLS = "TLS";
    private String protocol;
    private final Set<KeyManager> keyManagers;
    private String keyManagerFactoryAlgorithm = KeyManagerFactory.getDefaultAlgorithm();
    private String keyStoreType = KeyStore.getDefaultType();
    private final Set<TrustManager> trustManagers;
    private String trustManagerFactoryAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
    private SecureRandom secureRandom;
    private Provider provider;

    public static SSLContextBuilder create() {
        return new SSLContextBuilder();
    }

    public SSLContextBuilder() {
        this.keyManagers = new LinkedHashSet<KeyManager>();
        this.trustManagers = new LinkedHashSet<TrustManager>();
    }

    @Deprecated
    public SSLContextBuilder useProtocol(String string) {
        this.protocol = string;
        return this;
    }

    public SSLContextBuilder setProtocol(String string) {
        this.protocol = string;
        return this;
    }

    public SSLContextBuilder setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
        return this;
    }

    public SSLContextBuilder setProvider(Provider provider) {
        this.provider = provider;
        return this;
    }

    public SSLContextBuilder setProvider(String string) {
        this.provider = Security.getProvider(string);
        return this;
    }

    public SSLContextBuilder setKeyStoreType(String string) {
        this.keyStoreType = string;
        return this;
    }

    public SSLContextBuilder setKeyManagerFactoryAlgorithm(String string) {
        this.keyManagerFactoryAlgorithm = string;
        return this;
    }

    public SSLContextBuilder setTrustManagerFactoryAlgorithm(String string) {
        this.trustManagerFactoryAlgorithm = string;
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(KeyStore keyStore, TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(this.trustManagerFactoryAlgorithm == null ? TrustManagerFactory.getDefaultAlgorithm() : this.trustManagerFactoryAlgorithm);
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagerArray = trustManagerFactory.getTrustManagers();
        if (trustManagerArray != null) {
            if (trustStrategy != null) {
                for (int i = 0; i < trustManagerArray.length; ++i) {
                    TrustManager trustManager = trustManagerArray[i];
                    if (!(trustManager instanceof X509TrustManager)) continue;
                    trustManagerArray[i] = new TrustManagerDelegate((X509TrustManager)trustManager, trustStrategy);
                }
            }
            Collections.addAll(this.trustManagers, trustManagerArray);
        }
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException {
        return this.loadTrustMaterial(null, trustStrategy);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SSLContextBuilder loadTrustMaterial(File file, char[] cArray, TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        Args.notNull(file, "Truststore file");
        KeyStore keyStore = KeyStore.getInstance(this.keyStoreType);
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            keyStore.load(fileInputStream, cArray);
        } finally {
            fileInputStream.close();
        }
        return this.loadTrustMaterial(keyStore, trustStrategy);
    }

    public SSLContextBuilder loadTrustMaterial(File file, char[] cArray) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        return this.loadTrustMaterial(file, cArray, null);
    }

    public SSLContextBuilder loadTrustMaterial(File file) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        return this.loadTrustMaterial(file, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SSLContextBuilder loadTrustMaterial(URL uRL, char[] cArray, TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        Args.notNull(uRL, "Truststore URL");
        KeyStore keyStore = KeyStore.getInstance(this.keyStoreType);
        InputStream inputStream = uRL.openStream();
        try {
            keyStore.load(inputStream, cArray);
        } finally {
            inputStream.close();
        }
        return this.loadTrustMaterial(keyStore, trustStrategy);
    }

    public SSLContextBuilder loadTrustMaterial(URL uRL, char[] cArray) throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        return this.loadTrustMaterial(uRL, cArray, null);
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keyStore, char[] cArray, PrivateKeyStrategy privateKeyStrategy) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(this.keyManagerFactoryAlgorithm == null ? KeyManagerFactory.getDefaultAlgorithm() : this.keyManagerFactoryAlgorithm);
        keyManagerFactory.init(keyStore, cArray);
        KeyManager[] keyManagerArray = keyManagerFactory.getKeyManagers();
        if (keyManagerArray != null) {
            if (privateKeyStrategy != null) {
                for (int i = 0; i < keyManagerArray.length; ++i) {
                    KeyManager keyManager = keyManagerArray[i];
                    if (!(keyManager instanceof X509ExtendedKeyManager)) continue;
                    keyManagerArray[i] = new KeyManagerDelegate((X509ExtendedKeyManager)keyManager, privateKeyStrategy);
                }
            }
            Collections.addAll(this.keyManagers, keyManagerArray);
        }
        return this;
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keyStore, char[] cArray) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        return this.loadKeyMaterial(keyStore, cArray, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SSLContextBuilder loadKeyMaterial(File file, char[] cArray, char[] cArray2, PrivateKeyStrategy privateKeyStrategy) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
        Args.notNull(file, "Keystore file");
        KeyStore keyStore = KeyStore.getInstance(this.keyStoreType);
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            keyStore.load(fileInputStream, cArray);
        } finally {
            fileInputStream.close();
        }
        return this.loadKeyMaterial(keyStore, cArray2, privateKeyStrategy);
    }

    public SSLContextBuilder loadKeyMaterial(File file, char[] cArray, char[] cArray2) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
        return this.loadKeyMaterial(file, cArray, cArray2, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SSLContextBuilder loadKeyMaterial(URL uRL, char[] cArray, char[] cArray2, PrivateKeyStrategy privateKeyStrategy) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
        Args.notNull(uRL, "Keystore URL");
        KeyStore keyStore = KeyStore.getInstance(this.keyStoreType);
        InputStream inputStream = uRL.openStream();
        try {
            keyStore.load(inputStream, cArray);
        } finally {
            inputStream.close();
        }
        return this.loadKeyMaterial(keyStore, cArray2, privateKeyStrategy);
    }

    public SSLContextBuilder loadKeyMaterial(URL uRL, char[] cArray, char[] cArray2) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, CertificateException, IOException {
        return this.loadKeyMaterial(uRL, cArray, cArray2, null);
    }

    protected void initSSLContext(SSLContext sSLContext, Collection<KeyManager> collection, Collection<TrustManager> collection2, SecureRandom secureRandom) throws KeyManagementException {
        sSLContext.init(!collection.isEmpty() ? collection.toArray(new KeyManager[collection.size()]) : null, !collection2.isEmpty() ? collection2.toArray(new TrustManager[collection2.size()]) : null, secureRandom);
    }

    public SSLContext build() throws NoSuchAlgorithmException, KeyManagementException {
        String string = this.protocol != null ? this.protocol : TLS;
        SSLContext sSLContext = this.provider != null ? SSLContext.getInstance(string, this.provider) : SSLContext.getInstance(string);
        this.initSSLContext(sSLContext, this.keyManagers, this.trustManagers, this.secureRandom);
        return sSLContext;
    }

    public String toString() {
        return "[provider=" + this.provider + ", protocol=" + this.protocol + ", keyStoreType=" + this.keyStoreType + ", keyManagerFactoryAlgorithm=" + this.keyManagerFactoryAlgorithm + ", keyManagers=" + this.keyManagers + ", trustManagerFactoryAlgorithm=" + this.trustManagerFactoryAlgorithm + ", trustManagers=" + this.trustManagers + ", secureRandom=" + this.secureRandom + "]";
    }

    static class KeyManagerDelegate
    extends X509ExtendedKeyManager {
        private final X509ExtendedKeyManager keyManager;
        private final PrivateKeyStrategy aliasStrategy;

        KeyManagerDelegate(X509ExtendedKeyManager x509ExtendedKeyManager, PrivateKeyStrategy privateKeyStrategy) {
            this.keyManager = x509ExtendedKeyManager;
            this.aliasStrategy = privateKeyStrategy;
        }

        @Override
        public String[] getClientAliases(String string, Principal[] principalArray) {
            return this.keyManager.getClientAliases(string, principalArray);
        }

        public Map<String, PrivateKeyDetails> getClientAliasMap(String[] stringArray, Principal[] principalArray) {
            HashMap<String, PrivateKeyDetails> hashMap = new HashMap<String, PrivateKeyDetails>();
            for (String string : stringArray) {
                String[] stringArray2 = this.keyManager.getClientAliases(string, principalArray);
                if (stringArray2 == null) continue;
                for (String string2 : stringArray2) {
                    hashMap.put(string2, new PrivateKeyDetails(string, this.keyManager.getCertificateChain(string2)));
                }
            }
            return hashMap;
        }

        public Map<String, PrivateKeyDetails> getServerAliasMap(String string, Principal[] principalArray) {
            HashMap<String, PrivateKeyDetails> hashMap = new HashMap<String, PrivateKeyDetails>();
            String[] stringArray = this.keyManager.getServerAliases(string, principalArray);
            if (stringArray != null) {
                for (String string2 : stringArray) {
                    hashMap.put(string2, new PrivateKeyDetails(string, this.keyManager.getCertificateChain(string2)));
                }
            }
            return hashMap;
        }

        @Override
        public String chooseClientAlias(String[] stringArray, Principal[] principalArray, Socket socket) {
            Map<String, PrivateKeyDetails> map = this.getClientAliasMap(stringArray, principalArray);
            return this.aliasStrategy.chooseAlias(map, socket);
        }

        @Override
        public String[] getServerAliases(String string, Principal[] principalArray) {
            return this.keyManager.getServerAliases(string, principalArray);
        }

        @Override
        public String chooseServerAlias(String string, Principal[] principalArray, Socket socket) {
            Map<String, PrivateKeyDetails> map = this.getServerAliasMap(string, principalArray);
            return this.aliasStrategy.chooseAlias(map, socket);
        }

        @Override
        public X509Certificate[] getCertificateChain(String string) {
            return this.keyManager.getCertificateChain(string);
        }

        @Override
        public PrivateKey getPrivateKey(String string) {
            return this.keyManager.getPrivateKey(string);
        }

        @Override
        public String chooseEngineClientAlias(String[] stringArray, Principal[] principalArray, SSLEngine sSLEngine) {
            Map<String, PrivateKeyDetails> map = this.getClientAliasMap(stringArray, principalArray);
            return this.aliasStrategy.chooseAlias(map, null);
        }

        @Override
        public String chooseEngineServerAlias(String string, Principal[] principalArray, SSLEngine sSLEngine) {
            Map<String, PrivateKeyDetails> map = this.getServerAliasMap(string, principalArray);
            return this.aliasStrategy.chooseAlias(map, null);
        }
    }

    static class TrustManagerDelegate
    implements X509TrustManager {
        private final X509TrustManager trustManager;
        private final TrustStrategy trustStrategy;

        TrustManagerDelegate(X509TrustManager x509TrustManager, TrustStrategy trustStrategy) {
            this.trustManager = x509TrustManager;
            this.trustStrategy = trustStrategy;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509CertificateArray, String string) throws CertificateException {
            this.trustManager.checkClientTrusted(x509CertificateArray, string);
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509CertificateArray, String string) throws CertificateException {
            if (!this.trustStrategy.isTrusted(x509CertificateArray, string)) {
                this.trustManager.checkServerTrusted(x509CertificateArray, string);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return this.trustManager.getAcceptedIssuers();
        }
    }
}

