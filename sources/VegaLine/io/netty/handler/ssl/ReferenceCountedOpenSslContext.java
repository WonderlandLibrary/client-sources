/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.ApplicationProtocolNegotiator;
import io.netty.handler.ssl.CipherSuiteConverter;
import io.netty.handler.ssl.CipherSuiteFilter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.OpenSsl;
import io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator;
import io.netty.handler.ssl.OpenSslCertificateException;
import io.netty.handler.ssl.OpenSslDefaultApplicationProtocolNegotiator;
import io.netty.handler.ssl.OpenSslEngineMap;
import io.netty.handler.ssl.OpenSslKeyMaterialManager;
import io.netty.handler.ssl.OpenSslSessionContext;
import io.netty.handler.ssl.OpenSslSessionStats;
import io.netty.handler.ssl.OpenSslX509Certificate;
import io.netty.handler.ssl.PemEncoded;
import io.netty.handler.ssl.PemPrivateKey;
import io.netty.handler.ssl.PemX509Certificate;
import io.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslUtils;
import io.netty.internal.tcnative.CertificateVerifier;
import io.netty.internal.tcnative.SSL;
import io.netty.internal.tcnative.SSLContext;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateRevokedException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

public abstract class ReferenceCountedOpenSslContext
extends SslContext
implements ReferenceCounted {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslContext.class);
    private static final boolean JDK_REJECT_CLIENT_INITIATED_RENEGOTIATION = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

        @Override
        public Boolean run() {
            return SystemPropertyUtil.getBoolean("jdk.tls.rejectClientInitiatedRenegotiation", false);
        }
    });
    private static final int DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE = AccessController.doPrivileged(new PrivilegedAction<Integer>(){

        @Override
        public Integer run() {
            return Math.max(1, SystemPropertyUtil.getInt("io.netty.handler.ssl.openssl.bioNonApplicationBufferSize", 2048));
        }
    });
    private static final List<String> DEFAULT_CIPHERS;
    private static final Integer DH_KEY_LENGTH;
    private static final ResourceLeakDetector<ReferenceCountedOpenSslContext> leakDetector;
    protected static final int VERIFY_DEPTH = 10;
    protected volatile long ctx;
    private volatile int aprPoolDestroyed;
    private final List<String> unmodifiableCiphers;
    private final long sessionCacheSize;
    private final long sessionTimeout;
    private final OpenSslApplicationProtocolNegotiator apn;
    private final int mode;
    private final ResourceLeakTracker<ReferenceCountedOpenSslContext> leak;
    private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted(){

        @Override
        public ReferenceCounted touch(Object hint) {
            if (ReferenceCountedOpenSslContext.this.leak != null) {
                ReferenceCountedOpenSslContext.this.leak.record(hint);
            }
            return ReferenceCountedOpenSslContext.this;
        }

        @Override
        protected void deallocate() {
            ReferenceCountedOpenSslContext.this.destroy();
            if (ReferenceCountedOpenSslContext.this.leak != null) {
                boolean closed = ReferenceCountedOpenSslContext.this.leak.close(ReferenceCountedOpenSslContext.this);
                assert (closed);
            }
        }
    };
    final Certificate[] keyCertChain;
    final ClientAuth clientAuth;
    final String[] protocols;
    final OpenSslEngineMap engineMap = new DefaultOpenSslEngineMap();
    private volatile boolean rejectRemoteInitiatedRenegotiation;
    private volatile int bioNonApplicationBufferSize = DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE;
    static final OpenSslApplicationProtocolNegotiator NONE_PROTOCOL_NEGOTIATOR;

    ReferenceCountedOpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, ApplicationProtocolConfig apnCfg, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean leakDetection) throws SSLException {
        this(ciphers, cipherFilter, ReferenceCountedOpenSslContext.toNegotiator(apnCfg), sessionCacheSize, sessionTimeout, mode, keyCertChain, clientAuth, protocols, startTls, leakDetection);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    ReferenceCountedOpenSslContext(Iterable<String> ciphers, CipherSuiteFilter cipherFilter, OpenSslApplicationProtocolNegotiator apn, long sessionCacheSize, long sessionTimeout, int mode, Certificate[] keyCertChain, ClientAuth clientAuth, String[] protocols, boolean startTls, boolean leakDetection) throws SSLException {
        super(startTls);
        ArrayList<String> convertedCiphers;
        OpenSsl.ensureAvailability();
        if (mode != 1 && mode != 0) {
            throw new IllegalArgumentException("mode most be either SSL.SSL_MODE_SERVER or SSL.SSL_MODE_CLIENT");
        }
        this.leak = leakDetection ? leakDetector.track(this) : null;
        this.mode = mode;
        this.clientAuth = this.isServer() ? ObjectUtil.checkNotNull(clientAuth, "clientAuth") : ClientAuth.NONE;
        this.protocols = protocols;
        if (mode == 1) {
            this.rejectRemoteInitiatedRenegotiation = JDK_REJECT_CLIENT_INITIATED_RENEGOTIATION;
        }
        Certificate[] certificateArray = this.keyCertChain = keyCertChain == null ? null : (Certificate[])keyCertChain.clone();
        if (ciphers == null) {
            convertedCiphers = null;
        } else {
            convertedCiphers = new ArrayList<String>();
            for (String c : ciphers) {
                if (c == null) break;
                String converted = CipherSuiteConverter.toOpenSsl(c);
                if (converted != null) {
                    c = converted;
                }
                convertedCiphers.add(c);
            }
        }
        this.unmodifiableCiphers = Arrays.asList(ObjectUtil.checkNotNull(cipherFilter, "cipherFilter").filterCipherSuites(convertedCiphers, DEFAULT_CIPHERS, OpenSsl.availableOpenSslCipherSuites()));
        this.apn = ObjectUtil.checkNotNull(apn, "apn");
        boolean success = false;
        try {
            Class<ReferenceCountedOpenSslContext> clazz = ReferenceCountedOpenSslContext.class;
            synchronized (ReferenceCountedOpenSslContext.class) {
                try {
                    this.ctx = SSLContext.make(31, mode);
                } catch (Exception e) {
                    throw new SSLException("failed to create an SSL_CTX", e);
                }
                SSLContext.setOptions(this.ctx, SSLContext.getOptions(this.ctx) | SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_CIPHER_SERVER_PREFERENCE | SSL.SSL_OP_NO_COMPRESSION | SSL.SSL_OP_NO_TICKET);
                SSLContext.setMode(this.ctx, SSLContext.getMode(this.ctx) | SSL.SSL_MODE_ACCEPT_MOVING_WRITE_BUFFER);
                if (DH_KEY_LENGTH != null) {
                    SSLContext.setTmpDHLength(this.ctx, DH_KEY_LENGTH);
                }
                try {
                    SSLContext.setCipherSuite(this.ctx, CipherSuiteConverter.toOpenSsl(this.unmodifiableCiphers));
                } catch (SSLException e) {
                    throw e;
                } catch (Exception e) {
                    throw new SSLException("failed to set cipher suite: " + this.unmodifiableCiphers, e);
                }
                List<String> nextProtoList = apn.protocols();
                if (!nextProtoList.isEmpty()) {
                    String[] appProtocols = nextProtoList.toArray(new String[nextProtoList.size()]);
                    int selectorBehavior = ReferenceCountedOpenSslContext.opensslSelectorFailureBehavior(apn.selectorFailureBehavior());
                    switch (apn.protocol()) {
                        case NPN: {
                            SSLContext.setNpnProtos(this.ctx, appProtocols, selectorBehavior);
                            break;
                        }
                        case ALPN: {
                            SSLContext.setAlpnProtos(this.ctx, appProtocols, selectorBehavior);
                            break;
                        }
                        case NPN_AND_ALPN: {
                            SSLContext.setNpnProtos(this.ctx, appProtocols, selectorBehavior);
                            SSLContext.setAlpnProtos(this.ctx, appProtocols, selectorBehavior);
                            break;
                        }
                        default: {
                            throw new Error();
                        }
                    }
                }
                if (sessionCacheSize > 0L) {
                    this.sessionCacheSize = sessionCacheSize;
                    SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
                } else {
                    this.sessionCacheSize = sessionCacheSize = SSLContext.setSessionCacheSize(this.ctx, 20480L);
                    SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
                }
                if (sessionTimeout > 0L) {
                    this.sessionTimeout = sessionTimeout;
                    SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
                } else {
                    this.sessionTimeout = sessionTimeout = SSLContext.setSessionCacheTimeout(this.ctx, 300L);
                    SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
                }
                // ** MonitorExit[var16_15] (shouldn't be in output)
                success = true;
            }
        } finally {
            if (!success) {
                this.release();
            }
        }
        {
            return;
        }
    }

    private static int opensslSelectorFailureBehavior(ApplicationProtocolConfig.SelectorFailureBehavior behavior) {
        switch (behavior) {
            case NO_ADVERTISE: {
                return 0;
            }
            case CHOOSE_MY_LAST_PROTOCOL: {
                return 1;
            }
        }
        throw new Error();
    }

    @Override
    public final List<String> cipherSuites() {
        return this.unmodifiableCiphers;
    }

    @Override
    public final long sessionCacheSize() {
        return this.sessionCacheSize;
    }

    @Override
    public final long sessionTimeout() {
        return this.sessionTimeout;
    }

    @Override
    public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.apn;
    }

    @Override
    public final boolean isClient() {
        return this.mode == 0;
    }

    @Override
    public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return this.newEngine0(alloc, peerHost, peerPort);
    }

    SSLEngine newEngine0(ByteBufAllocator alloc, String peerHost, int peerPort) {
        return new ReferenceCountedOpenSslEngine(this, alloc, peerHost, peerPort, true);
    }

    abstract OpenSslKeyMaterialManager keyMaterialManager();

    @Override
    public final SSLEngine newEngine(ByteBufAllocator alloc) {
        return this.newEngine(alloc, null, -1);
    }

    @Deprecated
    public final long context() {
        return this.ctx;
    }

    @Deprecated
    public final OpenSslSessionStats stats() {
        return this.sessionContext().stats();
    }

    public void setRejectRemoteInitiatedRenegotiation(boolean rejectRemoteInitiatedRenegotiation) {
        this.rejectRemoteInitiatedRenegotiation = rejectRemoteInitiatedRenegotiation;
    }

    public boolean getRejectRemoteInitiatedRenegotiation() {
        return this.rejectRemoteInitiatedRenegotiation;
    }

    public void setBioNonApplicationBufferSize(int bioNonApplicationBufferSize) {
        this.bioNonApplicationBufferSize = ObjectUtil.checkPositiveOrZero(bioNonApplicationBufferSize, "bioNonApplicationBufferSize");
    }

    public int getBioNonApplicationBufferSize() {
        return this.bioNonApplicationBufferSize;
    }

    @Deprecated
    public final void setTicketKeys(byte[] keys) {
        this.sessionContext().setTicketKeys(keys);
    }

    @Override
    public abstract OpenSslSessionContext sessionContext();

    public final long sslCtxPointer() {
        return this.ctx;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void destroy() {
        Class<ReferenceCountedOpenSslContext> clazz = ReferenceCountedOpenSslContext.class;
        synchronized (ReferenceCountedOpenSslContext.class) {
            if (this.ctx != 0L) {
                SSLContext.free(this.ctx);
                this.ctx = 0L;
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    protected static X509Certificate[] certificates(byte[][] chain) {
        X509Certificate[] peerCerts = new X509Certificate[chain.length];
        for (int i = 0; i < peerCerts.length; ++i) {
            peerCerts[i] = new OpenSslX509Certificate(chain[i]);
        }
        return peerCerts;
    }

    protected static X509TrustManager chooseTrustManager(TrustManager[] managers) {
        for (TrustManager m : managers) {
            if (!(m instanceof X509TrustManager)) continue;
            return (X509TrustManager)m;
        }
        throw new IllegalStateException("no X509TrustManager found");
    }

    protected static X509KeyManager chooseX509KeyManager(KeyManager[] kms) {
        for (KeyManager km : kms) {
            if (!(km instanceof X509KeyManager)) continue;
            return (X509KeyManager)km;
        }
        throw new IllegalStateException("no X509KeyManager found");
    }

    static OpenSslApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig config) {
        if (config == null) {
            return NONE_PROTOCOL_NEGOTIATOR;
        }
        switch (config.protocol()) {
            case NONE: {
                return NONE_PROTOCOL_NEGOTIATOR;
            }
            case NPN: 
            case ALPN: 
            case NPN_AND_ALPN: {
                switch (config.selectedListenerFailureBehavior()) {
                    case CHOOSE_MY_LAST_PROTOCOL: 
                    case ACCEPT: {
                        switch (config.selectorFailureBehavior()) {
                            case NO_ADVERTISE: 
                            case CHOOSE_MY_LAST_PROTOCOL: {
                                return new OpenSslDefaultApplicationProtocolNegotiator(config);
                            }
                        }
                        throw new UnsupportedOperationException("OpenSSL provider does not support " + (Object)((Object)config.selectorFailureBehavior()) + " behavior");
                    }
                }
                throw new UnsupportedOperationException("OpenSSL provider does not support " + (Object)((Object)config.selectedListenerFailureBehavior()) + " behavior");
            }
        }
        throw new Error();
    }

    static boolean useExtendedTrustManager(X509TrustManager trustManager) {
        return PlatformDependent.javaVersion() >= 7 && trustManager instanceof X509ExtendedTrustManager;
    }

    static boolean useExtendedKeyManager(X509KeyManager keyManager) {
        return PlatformDependent.javaVersion() >= 7 && keyManager instanceof X509ExtendedKeyManager;
    }

    @Override
    public final int refCnt() {
        return this.refCnt.refCnt();
    }

    @Override
    public final ReferenceCounted retain() {
        this.refCnt.retain();
        return this;
    }

    @Override
    public final ReferenceCounted retain(int increment) {
        this.refCnt.retain(increment);
        return this;
    }

    @Override
    public final ReferenceCounted touch() {
        this.refCnt.touch();
        return this;
    }

    @Override
    public final ReferenceCounted touch(Object hint) {
        this.refCnt.touch(hint);
        return this;
    }

    @Override
    public final boolean release() {
        return this.refCnt.release();
    }

    @Override
    public final boolean release(int decrement) {
        return this.refCnt.release(decrement);
    }

    static void setKeyMaterial(long ctx, X509Certificate[] keyCertChain, PrivateKey key, String keyPassword) throws SSLException {
        long keyBio = 0L;
        long keyCertChainBio = 0L;
        long keyCertChainBio2 = 0L;
        PemEncoded encoded = null;
        try {
            encoded = PemX509Certificate.toPEM(ByteBufAllocator.DEFAULT, true, keyCertChain);
            keyCertChainBio = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
            keyCertChainBio2 = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, encoded.retain());
            if (key != null) {
                keyBio = ReferenceCountedOpenSslContext.toBIO(key);
            }
            SSLContext.setCertificateBio(ctx, keyCertChainBio, keyBio, keyPassword == null ? "" : keyPassword);
            SSLContext.setCertificateChainBio(ctx, keyCertChainBio2, true);
        } catch (SSLException e) {
            throw e;
        } catch (Exception e) {
            throw new SSLException("failed to set certificate and key", e);
        } finally {
            ReferenceCountedOpenSslContext.freeBio(keyBio);
            ReferenceCountedOpenSslContext.freeBio(keyCertChainBio);
            ReferenceCountedOpenSslContext.freeBio(keyCertChainBio2);
            if (encoded != null) {
                encoded.release();
            }
        }
    }

    static void freeBio(long bio) {
        if (bio != 0L) {
            SSL.freeBIO(bio);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static long toBIO(PrivateKey key) throws Exception {
        if (key == null) {
            return 0L;
        }
        ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
        PemEncoded pem = PemPrivateKey.toPEM(allocator, true, key);
        try {
            long l = ReferenceCountedOpenSslContext.toBIO(allocator, pem.retain());
            return l;
        } finally {
            pem.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static long toBIO(X509Certificate ... certChain) throws Exception {
        if (certChain == null) {
            return 0L;
        }
        if (certChain.length == 0) {
            throw new IllegalArgumentException("certChain can't be empty");
        }
        ByteBufAllocator allocator = ByteBufAllocator.DEFAULT;
        PemEncoded pem = PemX509Certificate.toPEM(allocator, true, certChain);
        try {
            long l = ReferenceCountedOpenSslContext.toBIO(allocator, pem.retain());
            return l;
        } finally {
            pem.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static long toBIO(ByteBufAllocator allocator, PemEncoded pem) throws Exception {
        try {
            long l;
            ByteBuf content = pem.content();
            if (content.isDirect()) {
                long l2 = ReferenceCountedOpenSslContext.newBIO(content.retainedSlice());
                return l2;
            }
            ByteBuf buffer = allocator.directBuffer(content.readableBytes());
            try {
                buffer.writeBytes(content, content.readerIndex(), content.readableBytes());
                l = ReferenceCountedOpenSslContext.newBIO(buffer.retainedSlice());
            } catch (Throwable throwable) {
                try {
                    if (pem.isSensitive()) {
                        SslUtils.zeroout(buffer);
                    }
                } finally {
                    buffer.release();
                }
                throw throwable;
            }
            try {
                if (pem.isSensitive()) {
                    SslUtils.zeroout(buffer);
                }
            } finally {
                buffer.release();
            }
            return l;
        } finally {
            pem.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static long newBIO(ByteBuf buffer) throws Exception {
        try {
            long bio = SSL.newMemBIO();
            int readable = buffer.readableBytes();
            if (SSL.bioWrite(bio, OpenSsl.memoryAddress(buffer) + (long)buffer.readerIndex(), readable) != readable) {
                SSL.freeBIO(bio);
                throw new IllegalStateException("Could not write data to memory BIO");
            }
            long l = bio;
            return l;
        } finally {
            buffer.release();
        }
    }

    static {
        leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslContext.class);
        NONE_PROTOCOL_NEGOTIATOR = new OpenSslApplicationProtocolNegotiator(){

            @Override
            public ApplicationProtocolConfig.Protocol protocol() {
                return ApplicationProtocolConfig.Protocol.NONE;
            }

            @Override
            public List<String> protocols() {
                return Collections.emptyList();
            }

            @Override
            public ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior() {
                return ApplicationProtocolConfig.SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
            }

            @Override
            public ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
                return ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT;
            }
        };
        ArrayList ciphers = new ArrayList();
        Collections.addAll(ciphers, "ECDHE-ECDSA-AES256-GCM-SHA384", "ECDHE-ECDSA-AES128-GCM-SHA256", "ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-RSA-AES128-SHA", "ECDHE-RSA-AES256-SHA", "AES128-GCM-SHA256", "AES128-SHA", "AES256-SHA");
        DEFAULT_CIPHERS = Collections.unmodifiableList(ciphers);
        if (logger.isDebugEnabled()) {
            logger.debug("Default cipher suite (OpenSSL): " + ciphers);
        }
        Integer dhLen = null;
        try {
            String dhKeySize = AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return SystemPropertyUtil.get("jdk.tls.ephemeralDHKeySize");
                }
            });
            if (dhKeySize != null) {
                try {
                    dhLen = Integer.valueOf(dhKeySize);
                } catch (NumberFormatException e) {
                    logger.debug("ReferenceCountedOpenSslContext supports -Djdk.tls.ephemeralDHKeySize={int}, but got: " + dhKeySize);
                }
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
        DH_KEY_LENGTH = dhLen;
    }

    private static final class DefaultOpenSslEngineMap
    implements OpenSslEngineMap {
        private final Map<Long, ReferenceCountedOpenSslEngine> engines = PlatformDependent.newConcurrentHashMap();

        private DefaultOpenSslEngineMap() {
        }

        @Override
        public ReferenceCountedOpenSslEngine remove(long ssl) {
            return this.engines.remove(ssl);
        }

        @Override
        public void add(ReferenceCountedOpenSslEngine engine) {
            this.engines.put(engine.sslPointer(), engine);
        }

        @Override
        public ReferenceCountedOpenSslEngine get(long ssl) {
            return this.engines.get(ssl);
        }
    }

    static abstract class AbstractCertificateVerifier
    implements CertificateVerifier {
        private final OpenSslEngineMap engineMap;

        AbstractCertificateVerifier(OpenSslEngineMap engineMap) {
            this.engineMap = engineMap;
        }

        @Override
        public final int verify(long ssl, byte[][] chain, String auth) {
            X509Certificate[] peerCerts = ReferenceCountedOpenSslContext.certificates(chain);
            ReferenceCountedOpenSslEngine engine = this.engineMap.get(ssl);
            try {
                this.verify(engine, peerCerts, auth);
                return CertificateVerifier.X509_V_OK;
            } catch (Throwable cause) {
                logger.debug("verification of certificate failed", cause);
                SSLHandshakeException e = new SSLHandshakeException("General OpenSslEngine problem");
                e.initCause(cause);
                engine.handshakeException = e;
                if (cause instanceof OpenSslCertificateException) {
                    return ((OpenSslCertificateException)cause).errorCode();
                }
                if (cause instanceof CertificateExpiredException) {
                    return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
                }
                if (cause instanceof CertificateNotYetValidException) {
                    return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
                }
                if (PlatformDependent.javaVersion() >= 7) {
                    if (cause instanceof CertificateRevokedException) {
                        return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
                    }
                    for (Throwable wrapped = cause.getCause(); wrapped != null; wrapped = wrapped.getCause()) {
                        if (!(wrapped instanceof CertPathValidatorException)) continue;
                        CertPathValidatorException ex = (CertPathValidatorException)wrapped;
                        CertPathValidatorException.Reason reason = ex.getReason();
                        if (reason == CertPathValidatorException.BasicReason.EXPIRED) {
                            return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
                        }
                        if (reason == CertPathValidatorException.BasicReason.NOT_YET_VALID) {
                            return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
                        }
                        if (reason != CertPathValidatorException.BasicReason.REVOKED) continue;
                        return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
                    }
                }
                return CertificateVerifier.X509_V_ERR_UNSPECIFIED;
            }
        }

        abstract void verify(ReferenceCountedOpenSslEngine var1, X509Certificate[] var2, String var3) throws Exception;
    }
}

