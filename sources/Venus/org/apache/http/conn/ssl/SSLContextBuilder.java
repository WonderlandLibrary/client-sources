/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.PrivateKeyDetails;
import org.apache.http.conn.ssl.PrivateKeyStrategy;
import org.apache.http.conn.ssl.TrustStrategy;

@Deprecated
public class SSLContextBuilder {
    static final String TLS = "TLS";
    static final String SSL = "SSL";
    private String protocol;
    private final Set<KeyManager> keymanagers = new LinkedHashSet<KeyManager>();
    private final Set<TrustManager> trustmanagers = new LinkedHashSet<TrustManager>();
    private SecureRandom secureRandom;

    public SSLContextBuilder useTLS() {
        this.protocol = TLS;
        return this;
    }

    public SSLContextBuilder useSSL() {
        this.protocol = SSL;
        return this;
    }

    public SSLContextBuilder useProtocol(String string) {
        this.protocol = string;
        return this;
    }

    public SSLContextBuilder setSecureRandom(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(KeyStore keyStore, TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
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
            Collections.addAll(this.trustmanagers, trustManagerArray);
        }
        return this;
    }

    public SSLContextBuilder loadTrustMaterial(KeyStore keyStore) throws NoSuchAlgorithmException, KeyStoreException {
        return this.loadTrustMaterial(keyStore, null);
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keyStore, char[] cArray) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        this.loadKeyMaterial(keyStore, cArray, null);
        return this;
    }

    public SSLContextBuilder loadKeyMaterial(KeyStore keyStore, char[] cArray, PrivateKeyStrategy privateKeyStrategy) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, cArray);
        KeyManager[] keyManagerArray = keyManagerFactory.getKeyManagers();
        if (keyManagerArray != null) {
            if (privateKeyStrategy != null) {
                for (int i = 0; i < keyManagerArray.length; ++i) {
                    KeyManager keyManager = keyManagerArray[i];
                    if (!(keyManager instanceof X509KeyManager)) continue;
                    keyManagerArray[i] = new KeyManagerDelegate((X509KeyManager)keyManager, privateKeyStrategy);
                }
            }
            Collections.addAll(this.keymanagers, keyManagerArray);
        }
        return this;
    }

    public SSLContext build() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sSLContext = SSLContext.getInstance(this.protocol != null ? this.protocol : TLS);
        sSLContext.init(!this.keymanagers.isEmpty() ? this.keymanagers.toArray(new KeyManager[this.keymanagers.size()]) : null, !this.trustmanagers.isEmpty() ? this.trustmanagers.toArray(new TrustManager[this.trustmanagers.size()]) : null, this.secureRandom);
        return sSLContext;
    }

    static class KeyManagerDelegate
    implements X509KeyManager {
        private final X509KeyManager keyManager;
        private final PrivateKeyStrategy aliasStrategy;

        KeyManagerDelegate(X509KeyManager x509KeyManager, PrivateKeyStrategy privateKeyStrategy) {
            this.keyManager = x509KeyManager;
            this.aliasStrategy = privateKeyStrategy;
        }

        @Override
        public String[] getClientAliases(String string, Principal[] principalArray) {
            return this.keyManager.getClientAliases(string, principalArray);
        }

        @Override
        public String chooseClientAlias(String[] stringArray, Principal[] principalArray, Socket socket) {
            HashMap<String, PrivateKeyDetails> hashMap = new HashMap<String, PrivateKeyDetails>();
            for (String string : stringArray) {
                String[] stringArray2 = this.keyManager.getClientAliases(string, principalArray);
                if (stringArray2 == null) continue;
                for (String string2 : stringArray2) {
                    hashMap.put(string2, new PrivateKeyDetails(string, this.keyManager.getCertificateChain(string2)));
                }
            }
            return this.aliasStrategy.chooseAlias(hashMap, socket);
        }

        @Override
        public String[] getServerAliases(String string, Principal[] principalArray) {
            return this.keyManager.getServerAliases(string, principalArray);
        }

        @Override
        public String chooseServerAlias(String string, Principal[] principalArray, Socket socket) {
            HashMap<String, PrivateKeyDetails> hashMap = new HashMap<String, PrivateKeyDetails>();
            String[] stringArray = this.keyManager.getServerAliases(string, principalArray);
            if (stringArray != null) {
                for (String string2 : stringArray) {
                    hashMap.put(string2, new PrivateKeyDetails(string, this.keyManager.getCertificateChain(string2)));
                }
            }
            return this.aliasStrategy.chooseAlias(hashMap, socket);
        }

        @Override
        public X509Certificate[] getCertificateChain(String string) {
            return this.keyManager.getCertificateChain(string);
        }

        @Override
        public PrivateKey getPrivateKey(String string) {
            return this.keyManager.getPrivateKey(string);
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

