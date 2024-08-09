/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.CertificateVerifier
 *  io.netty.internal.tcnative.SSLContext
 *  io.netty.internal.tcnative.SniHostNameMatcher
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.CipherSuiteFilter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.OpenSsl;
import io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator;
import io.netty.handler.ssl.OpenSslEngineMap;
import io.netty.handler.ssl.OpenSslExtendedKeyMaterialManager;
import io.netty.handler.ssl.OpenSslKeyMaterialManager;
import io.netty.handler.ssl.OpenSslServerSessionContext;
import io.netty.handler.ssl.OpenSslSessionContext;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import io.netty.internal.tcnative.CertificateVerifier;
import io.netty.internal.tcnative.SSLContext;
import io.netty.internal.tcnative.SniHostNameMatcher;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ReferenceCountedOpenSslServerContext
extends ReferenceCountedOpenSslContext {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslServerContext.class);
    private static final byte[] ID = new byte[]{110, 101, 116, 116, 121};
    private final OpenSslServerSessionContext sessionContext;
    private final OpenSslKeyMaterialManager keyMaterialManager;

    ReferenceCountedOpenSslServerContext(X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2, ClientAuth clientAuth, String[] stringArray, boolean bl, boolean bl2) throws SSLException {
        this(x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory, iterable, cipherSuiteFilter, ReferenceCountedOpenSslServerContext.toNegotiator(applicationProtocolConfig), l, l2, clientAuth, stringArray, bl, bl2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ReferenceCountedOpenSslServerContext(X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long l, long l2, ClientAuth clientAuth, String[] stringArray, boolean bl, boolean bl2) throws SSLException {
        super(iterable, cipherSuiteFilter, openSslApplicationProtocolNegotiator, l, l2, 1, (Certificate[])x509CertificateArray2, clientAuth, stringArray, bl, bl2, true);
        boolean bl3 = false;
        try {
            ServerContext serverContext = ReferenceCountedOpenSslServerContext.newSessionContext(this, this.ctx, this.engineMap, x509CertificateArray, trustManagerFactory, x509CertificateArray2, privateKey, string, keyManagerFactory);
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static ServerContext newSessionContext(ReferenceCountedOpenSslContext referenceCountedOpenSslContext, long l, OpenSslEngineMap openSslEngineMap, X509Certificate[] x509CertificateArray, TrustManagerFactory trustManagerFactory, X509Certificate[] x509CertificateArray2, PrivateKey privateKey, String string, KeyManagerFactory keyManagerFactory) throws SSLException {
        Object object;
        ServerContext serverContext = new ServerContext();
        try {
            SSLContext.setVerify((long)l, (int)0, (int)10);
            if (!OpenSsl.useKeyManagerFactory()) {
                if (keyManagerFactory != null) {
                    throw new IllegalArgumentException("KeyManagerFactory not supported");
                }
                ObjectUtil.checkNotNull(x509CertificateArray2, "keyCertChain");
                ReferenceCountedOpenSslServerContext.setKeyMaterial(l, x509CertificateArray2, privateKey, string);
            } else {
                if (keyManagerFactory == null) {
                    keyManagerFactory = ReferenceCountedOpenSslServerContext.buildKeyManagerFactory(x509CertificateArray2, privateKey, string, keyManagerFactory);
                }
                serverContext.keyMaterialManager = ReferenceCountedOpenSslServerContext.useExtendedKeyManager((X509KeyManager)(object = ReferenceCountedOpenSslServerContext.chooseX509KeyManager(keyManagerFactory.getKeyManagers()))) ? new OpenSslExtendedKeyMaterialManager((X509ExtendedKeyManager)object, string) : new OpenSslKeyMaterialManager((X509KeyManager)object, string);
            }
        } catch (Exception exception) {
            throw new SSLException("failed to set certificate and key", exception);
        }
        try {
            if (x509CertificateArray != null) {
                trustManagerFactory = ReferenceCountedOpenSslServerContext.buildTrustManagerFactory(x509CertificateArray, trustManagerFactory);
            } else if (trustManagerFactory == null) {
                trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore)null);
            }
            object = ReferenceCountedOpenSslServerContext.chooseTrustManager(trustManagerFactory.getTrustManagers());
            if (ReferenceCountedOpenSslServerContext.useExtendedTrustManager((X509TrustManager)object)) {
                SSLContext.setCertVerifyCallback((long)l, (CertificateVerifier)new ExtendedTrustManagerVerifyCallback(openSslEngineMap, (X509ExtendedTrustManager)object));
            } else {
                SSLContext.setCertVerifyCallback((long)l, (CertificateVerifier)new TrustManagerVerifyCallback(openSslEngineMap, (X509TrustManager)object));
            }
            X509Certificate[] x509CertificateArray3 = object.getAcceptedIssuers();
            if (x509CertificateArray3 != null && x509CertificateArray3.length > 0) {
                long l2 = 0L;
                try {
                    l2 = ReferenceCountedOpenSslServerContext.toBIO(x509CertificateArray3);
                    if (!SSLContext.setCACertificateBio((long)l, (long)l2)) {
                        throw new SSLException("unable to setup accepted issuers for trustmanager " + object);
                    }
                } finally {
                    ReferenceCountedOpenSslServerContext.freeBio(l2);
                }
            }
            if (PlatformDependent.javaVersion() >= 8) {
                SSLContext.setSniHostnameMatcher((long)l, (SniHostNameMatcher)new OpenSslSniHostnameMatcher(openSslEngineMap));
            }
        } catch (SSLException sSLException) {
            throw sSLException;
        } catch (Exception exception) {
            throw new SSLException("unable to setup trustmanager", exception);
        }
        serverContext.sessionContext = new OpenSslServerSessionContext(referenceCountedOpenSslContext);
        serverContext.sessionContext.setSessionIdContext(ID);
        return serverContext;
    }

    @Override
    public OpenSslSessionContext sessionContext() {
        return this.sessionContext();
    }

    @Override
    public SSLSessionContext sessionContext() {
        return this.sessionContext();
    }

    static InternalLogger access$000() {
        return logger;
    }

    private static final class OpenSslSniHostnameMatcher
    implements SniHostNameMatcher {
        private final OpenSslEngineMap engineMap;

        OpenSslSniHostnameMatcher(OpenSslEngineMap openSslEngineMap) {
            this.engineMap = openSslEngineMap;
        }

        public boolean match(long l, String string) {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.engineMap.get(l);
            if (referenceCountedOpenSslEngine != null) {
                return referenceCountedOpenSslEngine.checkSniHostnameMatch(string);
            }
            ReferenceCountedOpenSslServerContext.access$000().warn("No ReferenceCountedOpenSslEngine found for SSL pointer: {}", (Object)l);
            return true;
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
            this.manager.checkClientTrusted(x509CertificateArray, string, referenceCountedOpenSslEngine);
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
            this.manager.checkClientTrusted(x509CertificateArray, string);
        }
    }

    static final class ServerContext {
        OpenSslServerSessionContext sessionContext;
        OpenSslKeyMaterialManager keyMaterialManager;

        ServerContext() {
        }
    }
}

