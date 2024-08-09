/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.CertificateVerifier
 *  io.netty.internal.tcnative.SSL
 *  io.netty.internal.tcnative.SSLContext
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
import io.netty.handler.ssl.SslHandler;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class ReferenceCountedOpenSslContext
extends SslContext
implements ReferenceCounted {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslContext.class);
    private static final int DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE = AccessController.doPrivileged(new PrivilegedAction<Integer>(){

        @Override
        public Integer run() {
            return Math.max(1, SystemPropertyUtil.getInt("io.netty.handler.ssl.openssl.bioNonApplicationBufferSize", 2048));
        }

        @Override
        public Object run() {
            return this.run();
        }
    });
    private static final Integer DH_KEY_LENGTH;
    private static final ResourceLeakDetector<ReferenceCountedOpenSslContext> leakDetector;
    protected static final int VERIFY_DEPTH = 10;
    protected long ctx;
    private final List<String> unmodifiableCiphers;
    private final long sessionCacheSize;
    private final long sessionTimeout;
    private final OpenSslApplicationProtocolNegotiator apn;
    private final int mode;
    private final ResourceLeakTracker<ReferenceCountedOpenSslContext> leak;
    private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted(this){
        static final boolean $assertionsDisabled = !ReferenceCountedOpenSslContext.class.desiredAssertionStatus();
        final ReferenceCountedOpenSslContext this$0;
        {
            this.this$0 = referenceCountedOpenSslContext;
        }

        @Override
        public ReferenceCounted touch(Object object) {
            if (ReferenceCountedOpenSslContext.access$000(this.this$0) != null) {
                ReferenceCountedOpenSslContext.access$000(this.this$0).record(object);
            }
            return this.this$0;
        }

        @Override
        protected void deallocate() {
            ReferenceCountedOpenSslContext.access$100(this.this$0);
            if (ReferenceCountedOpenSslContext.access$000(this.this$0) != null) {
                boolean bl = ReferenceCountedOpenSslContext.access$000(this.this$0).close(this.this$0);
                if (!$assertionsDisabled && !bl) {
                    throw new AssertionError();
                }
            }
        }
    };
    final Certificate[] keyCertChain;
    final ClientAuth clientAuth;
    final String[] protocols;
    final boolean enableOcsp;
    final OpenSslEngineMap engineMap = new DefaultOpenSslEngineMap(null);
    final ReadWriteLock ctxLock = new ReentrantReadWriteLock();
    private volatile int bioNonApplicationBufferSize = DEFAULT_BIO_NON_APPLICATION_BUFFER_SIZE;
    static final OpenSslApplicationProtocolNegotiator NONE_PROTOCOL_NEGOTIATOR;

    ReferenceCountedOpenSslContext(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, long l, long l2, int n, Certificate[] certificateArray, ClientAuth clientAuth, String[] stringArray, boolean bl, boolean bl2, boolean bl3) throws SSLException {
        this(iterable, cipherSuiteFilter, ReferenceCountedOpenSslContext.toNegotiator(applicationProtocolConfig), l, l2, n, certificateArray, clientAuth, stringArray, bl, bl2, bl3);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    ReferenceCountedOpenSslContext(Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, OpenSslApplicationProtocolNegotiator openSslApplicationProtocolNegotiator, long l, long l2, int n, Certificate[] certificateArray, ClientAuth clientAuth, String[] stringArray, boolean bl, boolean bl2, boolean bl3) throws SSLException {
        super(bl);
        OpenSsl.ensureAvailability();
        if (bl2 && !OpenSsl.isOcspSupported()) {
            throw new IllegalStateException("OCSP is not supported.");
        }
        if (n != 1 && n != 0) {
            throw new IllegalArgumentException("mode most be either SSL.SSL_MODE_SERVER or SSL.SSL_MODE_CLIENT");
        }
        this.leak = bl3 ? leakDetector.track(this) : null;
        this.mode = n;
        this.clientAuth = this.isServer() ? ObjectUtil.checkNotNull(clientAuth, "clientAuth") : ClientAuth.NONE;
        this.protocols = stringArray;
        this.enableOcsp = bl2;
        this.keyCertChain = certificateArray == null ? null : (Certificate[])certificateArray.clone();
        this.unmodifiableCiphers = Arrays.asList(ObjectUtil.checkNotNull(cipherSuiteFilter, "cipherFilter").filterCipherSuites(iterable, OpenSsl.DEFAULT_CIPHERS, OpenSsl.availableJavaCipherSuites()));
        this.apn = ObjectUtil.checkNotNull(openSslApplicationProtocolNegotiator, "apn");
        boolean bl4 = false;
        try {
            try {
                this.ctx = SSLContext.make((int)31, (int)n);
            } catch (Exception exception) {
                throw new SSLException("failed to create an SSL_CTX", exception);
            }
            SSLContext.setOptions((long)this.ctx, (int)(SSLContext.getOptions((long)this.ctx) | SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_CIPHER_SERVER_PREFERENCE | SSL.SSL_OP_NO_COMPRESSION | SSL.SSL_OP_NO_TICKET));
            SSLContext.setMode((long)this.ctx, (int)(SSLContext.getMode((long)this.ctx) | SSL.SSL_MODE_ACCEPT_MOVING_WRITE_BUFFER));
            if (DH_KEY_LENGTH != null) {
                SSLContext.setTmpDHLength((long)this.ctx, (int)DH_KEY_LENGTH);
            }
            try {
                SSLContext.setCipherSuite((long)this.ctx, (String)CipherSuiteConverter.toOpenSsl(this.unmodifiableCiphers));
            } catch (SSLException sSLException) {
                throw sSLException;
            } catch (Exception exception) {
                throw new SSLException("failed to set cipher suite: " + this.unmodifiableCiphers, exception);
            }
            List<String> list = openSslApplicationProtocolNegotiator.protocols();
            if (!list.isEmpty()) {
                String[] stringArray2 = list.toArray(new String[list.size()]);
                int n2 = ReferenceCountedOpenSslContext.opensslSelectorFailureBehavior(openSslApplicationProtocolNegotiator.selectorFailureBehavior());
                switch (openSslApplicationProtocolNegotiator.protocol()) {
                    case NPN: {
                        SSLContext.setNpnProtos((long)this.ctx, (String[])stringArray2, (int)n2);
                        break;
                    }
                    case ALPN: {
                        SSLContext.setAlpnProtos((long)this.ctx, (String[])stringArray2, (int)n2);
                        break;
                    }
                    case NPN_AND_ALPN: {
                        SSLContext.setNpnProtos((long)this.ctx, (String[])stringArray2, (int)n2);
                        SSLContext.setAlpnProtos((long)this.ctx, (String[])stringArray2, (int)n2);
                        break;
                    }
                    default: {
                        throw new Error();
                    }
                }
            }
            if (l <= 0L) {
                l = SSLContext.setSessionCacheSize((long)this.ctx, (long)20480L);
            }
            this.sessionCacheSize = l;
            SSLContext.setSessionCacheSize((long)this.ctx, (long)l);
            if (l2 <= 0L) {
                l2 = SSLContext.setSessionCacheTimeout((long)this.ctx, (long)300L);
            }
            this.sessionTimeout = l2;
            SSLContext.setSessionCacheTimeout((long)this.ctx, (long)l2);
            if (bl2) {
                SSLContext.enableOcsp((long)this.ctx, (boolean)this.isClient());
            }
            bl4 = true;
        } finally {
            if (!bl4) {
                this.release();
            }
        }
    }

    private static int opensslSelectorFailureBehavior(ApplicationProtocolConfig.SelectorFailureBehavior selectorFailureBehavior) {
        switch (selectorFailureBehavior) {
            case NO_ADVERTISE: {
                return 1;
            }
            case CHOOSE_MY_LAST_PROTOCOL: {
                return 0;
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
    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String string, int n) {
        return this.newEngine0(byteBufAllocator, string, n, false);
    }

    @Override
    protected final SslHandler newHandler(ByteBufAllocator byteBufAllocator, boolean bl) {
        return new SslHandler(this.newEngine0(byteBufAllocator, null, -1, true), bl);
    }

    @Override
    protected final SslHandler newHandler(ByteBufAllocator byteBufAllocator, String string, int n, boolean bl) {
        return new SslHandler(this.newEngine0(byteBufAllocator, string, n, true), bl);
    }

    SSLEngine newEngine0(ByteBufAllocator byteBufAllocator, String string, int n, boolean bl) {
        return new ReferenceCountedOpenSslEngine(this, byteBufAllocator, string, n, bl, true);
    }

    abstract OpenSslKeyMaterialManager keyMaterialManager();

    @Override
    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator) {
        return this.newEngine(byteBufAllocator, null, -1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public final long context() {
        Lock lock = this.ctxLock.readLock();
        lock.lock();
        try {
            long l = this.ctx;
            return l;
        } finally {
            lock.unlock();
        }
    }

    @Deprecated
    public final OpenSslSessionStats stats() {
        return this.sessionContext().stats();
    }

    @Deprecated
    public void setRejectRemoteInitiatedRenegotiation(boolean bl) {
        if (!bl) {
            throw new UnsupportedOperationException("Renegotiation is not supported");
        }
    }

    @Deprecated
    public boolean getRejectRemoteInitiatedRenegotiation() {
        return false;
    }

    public void setBioNonApplicationBufferSize(int n) {
        this.bioNonApplicationBufferSize = ObjectUtil.checkPositiveOrZero(n, "bioNonApplicationBufferSize");
    }

    public int getBioNonApplicationBufferSize() {
        return this.bioNonApplicationBufferSize;
    }

    @Deprecated
    public final void setTicketKeys(byte[] byArray) {
        this.sessionContext().setTicketKeys(byArray);
    }

    @Override
    public abstract OpenSslSessionContext sessionContext();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public final long sslCtxPointer() {
        Lock lock = this.ctxLock.readLock();
        lock.lock();
        try {
            long l = this.ctx;
            return l;
        } finally {
            lock.unlock();
        }
    }

    private void destroy() {
        Lock lock = this.ctxLock.writeLock();
        lock.lock();
        try {
            if (this.ctx != 0L) {
                if (this.enableOcsp) {
                    SSLContext.disableOcsp((long)this.ctx);
                }
                SSLContext.free((long)this.ctx);
                this.ctx = 0L;
            }
        } finally {
            lock.unlock();
        }
    }

    protected static X509Certificate[] certificates(byte[][] byArray) {
        X509Certificate[] x509CertificateArray = new X509Certificate[byArray.length];
        for (int i = 0; i < x509CertificateArray.length; ++i) {
            x509CertificateArray[i] = new OpenSslX509Certificate(byArray[i]);
        }
        return x509CertificateArray;
    }

    protected static X509TrustManager chooseTrustManager(TrustManager[] trustManagerArray) {
        for (TrustManager trustManager : trustManagerArray) {
            if (!(trustManager instanceof X509TrustManager)) continue;
            return (X509TrustManager)trustManager;
        }
        throw new IllegalStateException("no X509TrustManager found");
    }

    protected static X509KeyManager chooseX509KeyManager(KeyManager[] keyManagerArray) {
        for (KeyManager keyManager : keyManagerArray) {
            if (!(keyManager instanceof X509KeyManager)) continue;
            return (X509KeyManager)keyManager;
        }
        throw new IllegalStateException("no X509KeyManager found");
    }

    static OpenSslApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig applicationProtocolConfig) {
        if (applicationProtocolConfig == null) {
            return NONE_PROTOCOL_NEGOTIATOR;
        }
        switch (applicationProtocolConfig.protocol()) {
            case NONE: {
                return NONE_PROTOCOL_NEGOTIATOR;
            }
            case NPN: 
            case ALPN: 
            case NPN_AND_ALPN: {
                switch (applicationProtocolConfig.selectedListenerFailureBehavior()) {
                    case CHOOSE_MY_LAST_PROTOCOL: 
                    case ACCEPT: {
                        switch (applicationProtocolConfig.selectorFailureBehavior()) {
                            case NO_ADVERTISE: 
                            case CHOOSE_MY_LAST_PROTOCOL: {
                                return new OpenSslDefaultApplicationProtocolNegotiator(applicationProtocolConfig);
                            }
                        }
                        throw new UnsupportedOperationException("OpenSSL provider does not support " + (Object)((Object)applicationProtocolConfig.selectorFailureBehavior()) + " behavior");
                    }
                }
                throw new UnsupportedOperationException("OpenSSL provider does not support " + (Object)((Object)applicationProtocolConfig.selectedListenerFailureBehavior()) + " behavior");
            }
        }
        throw new Error();
    }

    static boolean useExtendedTrustManager(X509TrustManager x509TrustManager) {
        return PlatformDependent.javaVersion() >= 7 && x509TrustManager instanceof X509ExtendedTrustManager;
    }

    static boolean useExtendedKeyManager(X509KeyManager x509KeyManager) {
        return PlatformDependent.javaVersion() >= 7 && x509KeyManager instanceof X509ExtendedKeyManager;
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
    public final ReferenceCounted retain(int n) {
        this.refCnt.retain(n);
        return this;
    }

    @Override
    public final ReferenceCounted touch() {
        this.refCnt.touch();
        return this;
    }

    @Override
    public final ReferenceCounted touch(Object object) {
        this.refCnt.touch(object);
        return this;
    }

    @Override
    public final boolean release() {
        return this.refCnt.release();
    }

    @Override
    public final boolean release(int n) {
        return this.refCnt.release(n);
    }

    static void setKeyMaterial(long l, X509Certificate[] x509CertificateArray, PrivateKey privateKey, String string) throws SSLException {
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;
        PemEncoded pemEncoded = null;
        try {
            pemEncoded = PemX509Certificate.toPEM(ByteBufAllocator.DEFAULT, true, x509CertificateArray);
            l3 = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, pemEncoded.retain());
            l4 = ReferenceCountedOpenSslContext.toBIO(ByteBufAllocator.DEFAULT, pemEncoded.retain());
            if (privateKey != null) {
                l2 = ReferenceCountedOpenSslContext.toBIO(privateKey);
            }
            SSLContext.setCertificateBio((long)l, (long)l3, (long)l2, (String)(string == null ? "" : string));
            SSLContext.setCertificateChainBio((long)l, (long)l4, (boolean)true);
        } catch (SSLException sSLException) {
            throw sSLException;
        } catch (Exception exception) {
            throw new SSLException("failed to set certificate and key", exception);
        } finally {
            ReferenceCountedOpenSslContext.freeBio(l2);
            ReferenceCountedOpenSslContext.freeBio(l3);
            ReferenceCountedOpenSslContext.freeBio(l4);
            if (pemEncoded != null) {
                pemEncoded.release();
            }
        }
    }

    static void freeBio(long l) {
        if (l != 0L) {
            SSL.freeBIO((long)l);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static long toBIO(PrivateKey privateKey) throws Exception {
        if (privateKey == null) {
            return 0L;
        }
        ByteBufAllocator byteBufAllocator = ByteBufAllocator.DEFAULT;
        PemEncoded pemEncoded = PemPrivateKey.toPEM(byteBufAllocator, true, privateKey);
        try {
            long l = ReferenceCountedOpenSslContext.toBIO(byteBufAllocator, pemEncoded.retain());
            return l;
        } finally {
            pemEncoded.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static long toBIO(X509Certificate ... x509CertificateArray) throws Exception {
        if (x509CertificateArray == null) {
            return 0L;
        }
        if (x509CertificateArray.length == 0) {
            throw new IllegalArgumentException("certChain can't be empty");
        }
        ByteBufAllocator byteBufAllocator = ByteBufAllocator.DEFAULT;
        PemEncoded pemEncoded = PemX509Certificate.toPEM(byteBufAllocator, true, x509CertificateArray);
        try {
            long l = ReferenceCountedOpenSslContext.toBIO(byteBufAllocator, pemEncoded.retain());
            return l;
        } finally {
            pemEncoded.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static long toBIO(ByteBufAllocator byteBufAllocator, PemEncoded pemEncoded) throws Exception {
        try {
            long l;
            ByteBuf byteBuf = pemEncoded.content();
            if (byteBuf.isDirect()) {
                long l2 = ReferenceCountedOpenSslContext.newBIO(byteBuf.retainedSlice());
                return l2;
            }
            ByteBuf byteBuf2 = byteBufAllocator.directBuffer(byteBuf.readableBytes());
            try {
                byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes());
                l = ReferenceCountedOpenSslContext.newBIO(byteBuf2.retainedSlice());
            } catch (Throwable throwable) {
                try {
                    if (pemEncoded.isSensitive()) {
                        SslUtils.zeroout(byteBuf2);
                    }
                } finally {
                    byteBuf2.release();
                }
                throw throwable;
            }
            try {
                if (pemEncoded.isSensitive()) {
                    SslUtils.zeroout(byteBuf2);
                }
            } finally {
                byteBuf2.release();
            }
            return l;
        } finally {
            pemEncoded.release();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static long newBIO(ByteBuf byteBuf) throws Exception {
        try {
            long l = SSL.newMemBIO();
            int n = byteBuf.readableBytes();
            if (SSL.bioWrite((long)l, (long)(OpenSsl.memoryAddress(byteBuf) + (long)byteBuf.readerIndex()), (int)n) != n) {
                SSL.freeBIO((long)l);
                throw new IllegalStateException("Could not write data to memory BIO");
            }
            long l2 = l;
            return l2;
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public SSLSessionContext sessionContext() {
        return this.sessionContext();
    }

    static ResourceLeakTracker access$000(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
        return referenceCountedOpenSslContext.leak;
    }

    static void access$100(ReferenceCountedOpenSslContext referenceCountedOpenSslContext) {
        referenceCountedOpenSslContext.destroy();
    }

    static InternalLogger access$300() {
        return logger;
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
        Integer n = null;
        try {
            String string = AccessController.doPrivileged(new PrivilegedAction<String>(){

                @Override
                public String run() {
                    return SystemPropertyUtil.get("jdk.tls.ephemeralDHKeySize");
                }

                @Override
                public Object run() {
                    return this.run();
                }
            });
            if (string != null) {
                try {
                    n = Integer.valueOf(string);
                } catch (NumberFormatException numberFormatException) {
                    logger.debug("ReferenceCountedOpenSslContext supports -Djdk.tls.ephemeralDHKeySize={int}, but got: " + string);
                }
            }
        } catch (Throwable throwable) {
            // empty catch block
        }
        DH_KEY_LENGTH = n;
    }

    private static final class DefaultOpenSslEngineMap
    implements OpenSslEngineMap {
        private final Map<Long, ReferenceCountedOpenSslEngine> engines = PlatformDependent.newConcurrentHashMap();

        private DefaultOpenSslEngineMap() {
        }

        @Override
        public ReferenceCountedOpenSslEngine remove(long l) {
            return this.engines.remove(l);
        }

        @Override
        public void add(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
            this.engines.put(referenceCountedOpenSslEngine.sslPointer(), referenceCountedOpenSslEngine);
        }

        @Override
        public ReferenceCountedOpenSslEngine get(long l) {
            return this.engines.get(l);
        }

        DefaultOpenSslEngineMap(1 var1_1) {
            this();
        }
    }

    static abstract class AbstractCertificateVerifier
    extends CertificateVerifier {
        private final OpenSslEngineMap engineMap;

        AbstractCertificateVerifier(OpenSslEngineMap openSslEngineMap) {
            this.engineMap = openSslEngineMap;
        }

        public final int verify(long l, byte[][] byArray, String string) {
            X509Certificate[] x509CertificateArray = ReferenceCountedOpenSslContext.certificates(byArray);
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.engineMap.get(l);
            try {
                this.verify(referenceCountedOpenSslEngine, x509CertificateArray, string);
                return CertificateVerifier.X509_V_OK;
            } catch (Throwable throwable) {
                ReferenceCountedOpenSslContext.access$300().debug("verification of certificate failed", throwable);
                SSLHandshakeException sSLHandshakeException = new SSLHandshakeException("General OpenSslEngine problem");
                sSLHandshakeException.initCause(throwable);
                referenceCountedOpenSslEngine.handshakeException = sSLHandshakeException;
                if (throwable instanceof OpenSslCertificateException) {
                    return ((OpenSslCertificateException)throwable).errorCode();
                }
                if (throwable instanceof CertificateExpiredException) {
                    return CertificateVerifier.X509_V_ERR_CERT_HAS_EXPIRED;
                }
                if (throwable instanceof CertificateNotYetValidException) {
                    return CertificateVerifier.X509_V_ERR_CERT_NOT_YET_VALID;
                }
                if (PlatformDependent.javaVersion() >= 7) {
                    if (throwable instanceof CertificateRevokedException) {
                        return CertificateVerifier.X509_V_ERR_CERT_REVOKED;
                    }
                    for (Throwable throwable2 = throwable.getCause(); throwable2 != null; throwable2 = throwable2.getCause()) {
                        if (!(throwable2 instanceof CertPathValidatorException)) continue;
                        CertPathValidatorException certPathValidatorException = (CertPathValidatorException)throwable2;
                        CertPathValidatorException.Reason reason = certPathValidatorException.getReason();
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

