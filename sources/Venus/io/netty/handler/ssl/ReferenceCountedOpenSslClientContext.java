/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.CertificateRequestedCallback
 *  io.netty.internal.tcnative.CertificateRequestedCallback$KeyMaterial
 *  io.netty.internal.tcnative.CertificateVerifier
 *  io.netty.internal.tcnative.SSLContext
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.CipherSuiteFilter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.OpenSsl;
import io.netty.handler.ssl.OpenSslEngineMap;
import io.netty.handler.ssl.OpenSslExtendedKeyMaterialManager;
import io.netty.handler.ssl.OpenSslKeyMaterialManager;
import io.netty.handler.ssl.OpenSslSessionContext;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import io.netty.internal.tcnative.CertificateRequestedCallback;
import io.netty.internal.tcnative.CertificateVerifier;
import io.netty.internal.tcnative.SSLContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.x500.X500Principal;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ReferenceCountedOpenSslClientContext
extends ReferenceCountedOpenSslContext {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslClientContext.class);
    private final OpenSslSessionContext sessionContext;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    ReferenceCountedOpenSslClientContext(X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, String[] stringArray, long l, long l2, boolean bl) throws SSLException {
        super(iterable, cipherSuiteFilter, applicationProtocolConfig, l, l2, 0, (Certificate[])x509CertificateArray2, ClientAuth.NONE, stringArray, false, bl, true);
        boolean bl2 = false;
        try {
            this.sessionContext = ReferenceCountedOpenSslClientContext.newSessionContext(this, this.ctx, this.engineMap, x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory);
            bl2 = true;
        } finally {
            if (!bl2) {
                this.release();
            }
        }
    }

    @Override
    OpenSslKeyMaterialManager keyMaterialManager() {
        return null;
    }

    @Override
    public OpenSslSessionContext sessionContext() {
        return this.sessionContext;
    }

    static OpenSslSessionContext newSessionContext(ReferenceCountedOpenSslContext referenceCountedOpenSslContext, long l, OpenSslEngineMap openSslEngineMap, X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory) throws SSLException {
        Object object;
        if (privateKey == null && x509CertificateArray2 != null || privateKey != null && x509CertificateArray2 == null) {
            throw new IllegalArgumentException("Either both keyCertChain and key needs to be null or none of them");
        }
        try {
            if (!OpenSsl.useKeyManagerFactory()) {
                if (keyManagerFactory != null) {
                    throw new IllegalArgumentException("KeyManagerFactory not supported");
                }
                if (x509CertificateArray2 != null) {
                    ReferenceCountedOpenSslClientContext.setKeyMaterial(l, x509CertificateArray2, privateKey, string);
                }
            } else {
                if (keyManagerFactory == null && x509CertificateArray2 != null) {
                    keyManagerFactory = ReferenceCountedOpenSslClientContext.buildKeyManagerFactory(x509CertificateArray2, privateKey, string, keyManagerFactory);
                }
                if (keyManagerFactory != null) {
                    object = ReferenceCountedOpenSslClientContext.chooseX509KeyManager(keyManagerFactory.getKeyManagers());
                    OpenSslKeyMaterialManager openSslKeyMaterialManager = ReferenceCountedOpenSslClientContext.useExtendedKeyManager((X509KeyManager)object) ? new OpenSslExtendedKeyMaterialManager((X509ExtendedKeyManager)object, string) : new OpenSslKeyMaterialManager((X509KeyManager)object, string);
                    SSLContext.setCertRequestedCallback((long)l, (CertificateRequestedCallback)new OpenSslCertificateRequestedCallback(openSslEngineMap, openSslKeyMaterialManager));
                }
            }
        } catch (Exception exception) {
            throw new SSLException("failed to set certificate and key", exception);
        }
        SSLContext.setVerify((long)l, (int)0, (int)10);
        try {
            if (x509CertificateArray != null) {
                trustManagerFactory = ReferenceCountedOpenSslClientContext.buildTrustManagerFactory(x509CertificateArray, trustManagerFactory);
            } else if (trustManagerFactory == null) {
                trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore)null);
            }
            object = ReferenceCountedOpenSslClientContext.chooseTrustManager(trustManagerFactory.getTrustManagers());
            if (ReferenceCountedOpenSslClientContext.useExtendedTrustManager((X509TrustManager)object)) {
                SSLContext.setCertVerifyCallback((long)l, (CertificateVerifier)new ExtendedTrustManagerVerifyCallback(openSslEngineMap, (X509ExtendedTrustManager)object));
            } else {
                SSLContext.setCertVerifyCallback((long)l, (CertificateVerifier)new TrustManagerVerifyCallback(openSslEngineMap, (X509TrustManager)object));
            }
        } catch (Exception exception) {
            throw new SSLException("unable to setup trustmanager", exception);
        }
        return new OpenSslClientSessionContext(referenceCountedOpenSslContext);
    }

    @Override
    public SSLSessionContext sessionContext() {
        return this.sessionContext();
    }

    static InternalLogger access$000() {
        return logger;
    }

    private static final class OpenSslCertificateRequestedCallback
    implements CertificateRequestedCallback {
        private final OpenSslEngineMap engineMap;
        private final OpenSslKeyMaterialManager keyManagerHolder;

        OpenSslCertificateRequestedCallback(OpenSslEngineMap openSslEngineMap, OpenSslKeyMaterialManager openSslKeyMaterialManager) {
            this.engineMap = openSslEngineMap;
            this.keyManagerHolder = openSslKeyMaterialManager;
        }

        public CertificateRequestedCallback.KeyMaterial requested(long l, byte[] byArray, byte[][] byArray2) {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.engineMap.get(l);
            try {
                X500Principal[] x500PrincipalArray;
                Set<String> set = OpenSslCertificateRequestedCallback.supportedClientKeyTypes(byArray);
                String[] stringArray = set.toArray(new String[set.size()]);
                if (byArray2 == null) {
                    x500PrincipalArray = null;
                } else {
                    x500PrincipalArray = new X500Principal[byArray2.length];
                    for (int i = 0; i < byArray2.length; ++i) {
                        x500PrincipalArray[i] = new X500Principal(byArray2[i]);
                    }
                }
                return this.keyManagerHolder.keyMaterial(referenceCountedOpenSslEngine, stringArray, x500PrincipalArray);
            } catch (Throwable throwable) {
                ReferenceCountedOpenSslClientContext.access$000().debug("request of key failed", throwable);
                SSLHandshakeException sSLHandshakeException = new SSLHandshakeException("General OpenSslEngine problem");
                sSLHandshakeException.initCause(throwable);
                referenceCountedOpenSslEngine.handshakeException = sSLHandshakeException;
                return null;
            }
        }

        private static Set<String> supportedClientKeyTypes(byte[] byArray) {
            HashSet<String> hashSet = new HashSet<String>(byArray.length);
            for (byte by : byArray) {
                String string = OpenSslCertificateRequestedCallback.clientKeyType(by);
                if (string == null) continue;
                hashSet.add(string);
            }
            return hashSet;
        }

        private static String clientKeyType(byte by) {
            switch (by) {
                case 1: {
                    return "RSA";
                }
                case 3: {
                    return "DH_RSA";
                }
                case 64: {
                    return "EC";
                }
                case 65: {
                    return "EC_RSA";
                }
                case 66: {
                    return "EC_EC";
                }
            }
            return null;
        }
    }

    private static final class ExtendedTrustManagerVerifyCallback
    extends ReferenceCountedOpenSslContext.AbstractCertificateVerifier {
        private final X509ExtendedTrustManager manager;

        ExtendedTrustManagerVerifyCallback(OpenSslEngineMap openSslEngineMap, X509ExtendedTrustManager x509ExtendedTrustManager) {
            super(openSslEngineMap);
            this.manager = x509ExtendedTrustManager;
        }

        @Override
        void verify(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, X509Certificate[] x509CertificateArray, String string) throws Exception {
            this.manager.checkServerTrusted(x509CertificateArray, string, referenceCountedOpenSslEngine);
        }
    }

    private static final class TrustManagerVerifyCallback
    extends ReferenceCountedOpenSslContext.AbstractCertificateVerifier {
        private final X509TrustManager manager;

        TrustManagerVerifyCallback(OpenSslEngineMap openSslEngineMap, X509TrustManager x509TrustManager) {
            super(openSslEngineMap);
            this.manager = x509TrustManager;
        }

        @Override
        void verify(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, X509Certificate[] x509CertificateArray, String string) throws Exception {
            this.manager.checkServerTrusted(x509CertificateArray, string);
        }
    }

    static final class OpenSslClientSessionContext
    extends OpenSslSessionContext {
        OpenSslClientSessionContext(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
            super(referenceCountedOpenSslContext);
        }

        @Override
        public void setSessionTimeout(int n) {
            if (n < 0) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public int getSessionTimeout() {
            return 1;
        }

        @Override
        public void setSessionCacheSize(int n) {
            if (n < 0) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public int getSessionCacheSize() {
            return 1;
        }

        @Override
        public void setSessionCacheEnabled(boolean bl) {
        }

        @Override
        public boolean isSessionCacheEnabled() {
            return true;
        }
    }
}

