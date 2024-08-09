/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.Buffer
 *  io.netty.internal.tcnative.SSL
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolAccessor;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.CipherSuiteConverter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.Java7SslParametersUtils;
import io.netty.handler.ssl.Java8SslUtils;
import io.netty.handler.ssl.NotSslRecordException;
import io.netty.handler.ssl.OpenSsl;
import io.netty.handler.ssl.OpenSslApplicationProtocolNegotiator;
import io.netty.handler.ssl.OpenSslEngineMap;
import io.netty.handler.ssl.OpenSslJavaxX509Certificate;
import io.netty.handler.ssl.OpenSslKeyMaterialManager;
import io.netty.handler.ssl.OpenSslSessionContext;
import io.netty.handler.ssl.OpenSslX509Certificate;
import io.netty.handler.ssl.ReferenceCountedOpenSslContext;
import io.netty.handler.ssl.SslUtils;
import io.netty.internal.tcnative.Buffer;
import io.netty.internal.tcnative.SSL;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetectorFactory;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.locks.Lock;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;

public class ReferenceCountedOpenSslEngine
extends SSLEngine
implements ReferenceCounted,
ApplicationProtocolAccessor {
    private static final InternalLogger logger;
    private static final SSLException BEGIN_HANDSHAKE_ENGINE_CLOSED;
    private static final SSLException HANDSHAKE_ENGINE_CLOSED;
    private static final SSLException RENEGOTIATION_UNSUPPORTED;
    private static final ResourceLeakDetector<ReferenceCountedOpenSslEngine> leakDetector;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_SSLV2 = 0;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_SSLV3 = 1;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1 = 2;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_1 = 3;
    private static final int OPENSSL_OP_NO_PROTOCOL_INDEX_TLSv1_2 = 4;
    private static final int[] OPENSSL_OP_NO_PROTOCOLS;
    private static final int DEFAULT_HOSTNAME_VALIDATION_FLAGS = 0;
    static final int MAX_PLAINTEXT_LENGTH;
    private static final int MAX_RECORD_SIZE;
    private static final AtomicIntegerFieldUpdater<ReferenceCountedOpenSslEngine> DESTROYED_UPDATER;
    private static final String INVALID_CIPHER = "SSL_NULL_WITH_NULL_NULL";
    private static final SSLEngineResult NEED_UNWRAP_OK;
    private static final SSLEngineResult NEED_UNWRAP_CLOSED;
    private static final SSLEngineResult NEED_WRAP_OK;
    private static final SSLEngineResult NEED_WRAP_CLOSED;
    private static final SSLEngineResult CLOSED_NOT_HANDSHAKING;
    private long ssl;
    private long networkBIO;
    private boolean certificateSet;
    private HandshakeState handshakeState = HandshakeState.NOT_STARTED;
    private boolean receivedShutdown;
    private volatile int destroyed;
    private volatile String applicationProtocol;
    private final ResourceLeakTracker<ReferenceCountedOpenSslEngine> leak;
    private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted(this){
        static final boolean $assertionsDisabled = !ReferenceCountedOpenSslEngine.class.desiredAssertionStatus();
        final ReferenceCountedOpenSslEngine this$0;
        {
            this.this$0 = referenceCountedOpenSslEngine;
        }

        @Override
        public ReferenceCounted touch(Object object) {
            if (ReferenceCountedOpenSslEngine.access$000(this.this$0) != null) {
                ReferenceCountedOpenSslEngine.access$000(this.this$0).record(object);
            }
            return this.this$0;
        }

        @Override
        protected void deallocate() {
            this.this$0.shutdown();
            if (ReferenceCountedOpenSslEngine.access$000(this.this$0) != null) {
                boolean bl = ReferenceCountedOpenSslEngine.access$000(this.this$0).close(this.this$0);
                if (!$assertionsDisabled && !bl) {
                    throw new AssertionError();
                }
            }
        }
    };
    private volatile ClientAuth clientAuth = ClientAuth.NONE;
    private volatile long lastAccessed = -1L;
    private String endPointIdentificationAlgorithm;
    private Object algorithmConstraints;
    private List<String> sniHostNames;
    private volatile Collection<?> matchers;
    private boolean isInboundDone;
    private boolean outboundClosed;
    final boolean jdkCompatibilityMode;
    private final boolean clientMode;
    private final ByteBufAllocator alloc;
    private final OpenSslEngineMap engineMap;
    private final OpenSslApplicationProtocolNegotiator apn;
    private final OpenSslSession session;
    private final Certificate[] localCerts;
    private final ByteBuffer[] singleSrcBuffer = new ByteBuffer[1];
    private final ByteBuffer[] singleDstBuffer = new ByteBuffer[1];
    private final OpenSslKeyMaterialManager keyMaterialManager;
    private final boolean enableOcsp;
    private int maxWrapOverhead;
    private int maxWrapBufferSize;
    SSLHandshakeException handshakeException;
    static final boolean $assertionsDisabled;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    ReferenceCountedOpenSslEngine(ReferenceCountedOpenSslContext referenceCountedOpenSslContext, ByteBufAllocator byteBufAllocator, String string, int n, boolean bl, boolean bl2) {
        super(string, n);
        long l;
        OpenSsl.ensureAvailability();
        this.alloc = ObjectUtil.checkNotNull(byteBufAllocator, "alloc");
        this.apn = (OpenSslApplicationProtocolNegotiator)referenceCountedOpenSslContext.applicationProtocolNegotiator();
        this.session = new OpenSslSession(this, referenceCountedOpenSslContext.sessionContext());
        this.clientMode = referenceCountedOpenSslContext.isClient();
        this.engineMap = referenceCountedOpenSslContext.engineMap;
        this.localCerts = referenceCountedOpenSslContext.keyCertChain;
        this.keyMaterialManager = referenceCountedOpenSslContext.keyMaterialManager();
        this.enableOcsp = referenceCountedOpenSslContext.enableOcsp;
        this.jdkCompatibilityMode = bl;
        Lock lock = referenceCountedOpenSslContext.ctxLock.readLock();
        lock.lock();
        try {
            l = SSL.newSSL((long)referenceCountedOpenSslContext.ctx, (!referenceCountedOpenSslContext.isClient() ? 1 : 0) != 0);
        } finally {
            lock.unlock();
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            this.ssl = l;
            try {
                this.networkBIO = SSL.bioNewByteBuffer((long)this.ssl, (int)referenceCountedOpenSslContext.getBioNonApplicationBufferSize());
                this.setClientAuth(this.clientMode ? ClientAuth.NONE : referenceCountedOpenSslContext.clientAuth);
                if (referenceCountedOpenSslContext.protocols != null) {
                    this.setEnabledProtocols(referenceCountedOpenSslContext.protocols);
                }
                if (this.clientMode && string != null) {
                    SSL.setTlsExtHostName((long)this.ssl, (String)string);
                }
                if (this.enableOcsp) {
                    SSL.enableOcsp((long)this.ssl);
                }
                if (!bl) {
                    SSL.setMode((long)this.ssl, (int)(SSL.getMode((long)this.ssl) | SSL.SSL_MODE_ENABLE_PARTIAL_WRITE));
                }
                this.calculateMaxWrapOverhead();
            } catch (Throwable throwable) {
                SSL.freeSSL((long)this.ssl);
                PlatformDependent.throwException(throwable);
            }
        }
        this.leak = bl2 ? leakDetector.track(this) : null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setOcspResponse(byte[] byArray) {
        if (!this.enableOcsp) {
            throw new IllegalStateException("OCSP stapling is not enabled");
        }
        if (this.clientMode) {
            throw new IllegalStateException("Not a server SSLEngine");
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            SSL.setOcspResponse((long)this.ssl, (byte[])byArray);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] getOcspResponse() {
        if (!this.enableOcsp) {
            throw new IllegalStateException("OCSP stapling is not enabled");
        }
        if (!this.clientMode) {
            throw new IllegalStateException("Not a client SSLEngine");
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            return SSL.getOcspResponse((long)this.ssl);
        }
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

    @Override
    public final synchronized SSLSession getHandshakeSession() {
        switch (this.handshakeState) {
            case NOT_STARTED: 
            case FINISHED: {
                return null;
            }
        }
        return this.session;
    }

    public final synchronized long sslPointer() {
        return this.ssl;
    }

    public final synchronized void shutdown() {
        if (DESTROYED_UPDATER.compareAndSet(this, 0, 0)) {
            this.engineMap.remove(this.ssl);
            SSL.freeSSL((long)this.ssl);
            this.networkBIO = 0L;
            this.ssl = 0L;
            this.outboundClosed = true;
            this.isInboundDone = true;
        }
        SSL.clearError();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int writePlaintextData(ByteBuffer byteBuffer, int n) {
        int n2;
        int n3 = byteBuffer.position();
        int n4 = byteBuffer.limit();
        if (byteBuffer.isDirect()) {
            n2 = SSL.writeToSSL((long)this.ssl, (long)(ReferenceCountedOpenSslEngine.bufferAddress(byteBuffer) + (long)n3), (int)n);
            if (n2 > 0) {
                byteBuffer.position(n3 + n2);
            }
        } else {
            ByteBuf byteBuf = this.alloc.directBuffer(n);
            try {
                byteBuffer.limit(n3 + n);
                byteBuf.setBytes(0, byteBuffer);
                byteBuffer.limit(n4);
                n2 = SSL.writeToSSL((long)this.ssl, (long)OpenSsl.memoryAddress(byteBuf), (int)n);
                if (n2 > 0) {
                    byteBuffer.position(n3 + n2);
                } else {
                    byteBuffer.position(n3);
                }
            } finally {
                byteBuf.release();
            }
        }
        return n2;
    }

    private ByteBuf writeEncryptedData(ByteBuffer byteBuffer, int n) {
        int n2 = byteBuffer.position();
        if (byteBuffer.isDirect()) {
            SSL.bioSetByteBuffer((long)this.networkBIO, (long)(ReferenceCountedOpenSslEngine.bufferAddress(byteBuffer) + (long)n2), (int)n, (boolean)false);
        } else {
            ByteBuf byteBuf = this.alloc.directBuffer(n);
            try {
                int n3 = byteBuffer.limit();
                byteBuffer.limit(n2 + n);
                byteBuf.writeBytes(byteBuffer);
                byteBuffer.position(n2);
                byteBuffer.limit(n3);
                SSL.bioSetByteBuffer((long)this.networkBIO, (long)OpenSsl.memoryAddress(byteBuf), (int)n, (boolean)false);
                return byteBuf;
            } catch (Throwable throwable) {
                byteBuf.release();
                PlatformDependent.throwException(throwable);
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int readPlaintextData(ByteBuffer byteBuffer) {
        int n;
        int n2 = byteBuffer.position();
        if (byteBuffer.isDirect()) {
            n = SSL.readFromSSL((long)this.ssl, (long)(ReferenceCountedOpenSslEngine.bufferAddress(byteBuffer) + (long)n2), (int)(byteBuffer.limit() - n2));
            if (n > 0) {
                byteBuffer.position(n2 + n);
            }
        } else {
            int n3 = byteBuffer.limit();
            int n4 = Math.min(this.maxEncryptedPacketLength0(), n3 - n2);
            ByteBuf byteBuf = this.alloc.directBuffer(n4);
            try {
                n = SSL.readFromSSL((long)this.ssl, (long)OpenSsl.memoryAddress(byteBuf), (int)n4);
                if (n > 0) {
                    byteBuffer.limit(n2 + n);
                    byteBuf.getBytes(byteBuf.readerIndex(), byteBuffer);
                    byteBuffer.limit(n3);
                }
            } finally {
                byteBuf.release();
            }
        }
        return n;
    }

    final synchronized int maxWrapOverhead() {
        return this.maxWrapOverhead;
    }

    final synchronized int maxEncryptedPacketLength() {
        return this.maxEncryptedPacketLength0();
    }

    final int maxEncryptedPacketLength0() {
        return this.maxWrapOverhead + MAX_PLAINTEXT_LENGTH;
    }

    final int calculateMaxLengthForWrap(int n, int n2) {
        return (int)Math.min((long)this.maxWrapBufferSize, (long)n + (long)this.maxWrapOverhead * (long)n2);
    }

    final synchronized int sslPending() {
        return this.sslPending0();
    }

    private void calculateMaxWrapOverhead() {
        this.maxWrapOverhead = SSL.getMaxWrapOverhead((long)this.ssl);
        this.maxWrapBufferSize = this.jdkCompatibilityMode ? this.maxEncryptedPacketLength0() : this.maxEncryptedPacketLength0() << 4;
    }

    private int sslPending0() {
        return this.handshakeState != HandshakeState.FINISHED ? 0 : SSL.sslPending((long)this.ssl);
    }

    private boolean isBytesAvailableEnoughForWrap(int n, int n2, int n3) {
        return (long)n - (long)this.maxWrapOverhead * (long)n3 >= (long)n2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final SSLEngineResult wrap(ByteBuffer[] byteBufferArray, int n, int n2, ByteBuffer byteBuffer) throws SSLException {
        if (byteBufferArray == null) {
            throw new IllegalArgumentException("srcs is null");
        }
        if (byteBuffer == null) {
            throw new IllegalArgumentException("dst is null");
        }
        if (n >= byteBufferArray.length || n + n2 > byteBufferArray.length) {
            throw new IndexOutOfBoundsException("offset: " + n + ", length: " + n2 + " (expected: offset <= offset + length <= srcs.length (" + byteBufferArray.length + "))");
        }
        if (byteBuffer.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (this.isOutboundDone()) {
                return this.isInboundDone() || this.isDestroyed() ? CLOSED_NOT_HANDSHAKING : NEED_UNWRAP_CLOSED;
            }
            int n3 = 0;
            ByteBuf byteBuf = null;
            try {
                int n4;
                if (byteBuffer.isDirect()) {
                    SSL.bioSetByteBuffer((long)this.networkBIO, (long)(ReferenceCountedOpenSslEngine.bufferAddress(byteBuffer) + (long)byteBuffer.position()), (int)byteBuffer.remaining(), (boolean)true);
                } else {
                    byteBuf = this.alloc.directBuffer(byteBuffer.remaining());
                    SSL.bioSetByteBuffer((long)this.networkBIO, (long)OpenSsl.memoryAddress(byteBuf), (int)byteBuf.writableBytes(), (boolean)true);
                }
                int n5 = SSL.bioLengthByteBuffer((long)this.networkBIO);
                if (this.outboundClosed) {
                    n3 = SSL.bioFlushByteBuffer((long)this.networkBIO);
                    if (n3 <= 0) {
                        SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
                        return sSLEngineResult;
                    }
                    if (!this.doSSLShutdown()) {
                        SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, n3);
                        return sSLEngineResult;
                    }
                    n3 = n5 - SSL.bioLengthByteBuffer((long)this.networkBIO);
                    SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, n3);
                    return sSLEngineResult;
                }
                SSLEngineResult.HandshakeStatus handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                if (this.handshakeState != HandshakeState.FINISHED) {
                    if (this.handshakeState != HandshakeState.STARTED_EXPLICITLY) {
                        this.handshakeState = HandshakeState.STARTED_IMPLICITLY;
                    }
                    if ((n3 = SSL.bioFlushByteBuffer((long)this.networkBIO)) > 0 && this.handshakeException != null) {
                        SSLEngineResult sSLEngineResult = this.newResult(SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, n3);
                        return sSLEngineResult;
                    }
                    handshakeStatus = this.handshake();
                    n3 = n5 - SSL.bioLengthByteBuffer((long)this.networkBIO);
                    if (n3 > 0) {
                        SSLEngineResult sSLEngineResult = this.newResult(this.mayFinishHandshake(handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED ? (n3 == n5 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : this.getHandshakeStatus(SSL.bioLengthNonApplication((long)this.networkBIO))) : SSLEngineResult.HandshakeStatus.FINISHED), 0, n3);
                        return sSLEngineResult;
                    }
                    if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                        SSLEngineResult sSLEngineResult = this.isOutboundDone() ? NEED_UNWRAP_CLOSED : NEED_UNWRAP_OK;
                        return sSLEngineResult;
                    }
                    if (this.outboundClosed) {
                        n3 = SSL.bioFlushByteBuffer((long)this.networkBIO);
                        SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(handshakeStatus, 0, n3);
                        return sSLEngineResult;
                    }
                }
                int n6 = n + n2;
                if (this.jdkCompatibilityMode) {
                    n4 = 0;
                    for (int i = n; i < n6; ++i) {
                        ByteBuffer byteBuffer2 = byteBufferArray[i];
                        if (byteBuffer2 == null) {
                            throw new IllegalArgumentException("srcs[" + i + "] is null");
                        }
                        if (n4 == MAX_PLAINTEXT_LENGTH || (n4 += byteBuffer2.remaining()) <= MAX_PLAINTEXT_LENGTH && n4 >= 0) continue;
                        n4 = MAX_PLAINTEXT_LENGTH;
                    }
                    if (!this.isBytesAvailableEnoughForWrap(byteBuffer.remaining(), n4, 1)) {
                        SSLEngineResult sSLEngineResult = new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, this.getHandshakeStatus(), 0, 0);
                        return sSLEngineResult;
                    }
                }
                n4 = 0;
                n3 = SSL.bioFlushByteBuffer((long)this.networkBIO);
                while (n < n6) {
                    ByteBuffer byteBuffer3 = byteBufferArray[n];
                    int n7 = byteBuffer3.remaining();
                    if (n7 != 0) {
                        int n8;
                        int n9;
                        if (this.jdkCompatibilityMode) {
                            n9 = this.writePlaintextData(byteBuffer3, Math.min(n7, MAX_PLAINTEXT_LENGTH - n4));
                        } else {
                            n8 = byteBuffer.remaining() - n3 - this.maxWrapOverhead;
                            if (n8 <= 0) {
                                SSLEngineResult sSLEngineResult = new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, this.getHandshakeStatus(), n4, n3);
                                return sSLEngineResult;
                            }
                            n9 = this.writePlaintextData(byteBuffer3, Math.min(n7, n8));
                        }
                        if (n9 > 0) {
                            n4 += n9;
                            n8 = SSL.bioLengthByteBuffer((long)this.networkBIO);
                            n5 = n8;
                            if (this.jdkCompatibilityMode || (n3 += n5 - n8) == byteBuffer.remaining()) {
                                SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(handshakeStatus, n4, n3);
                                return sSLEngineResult;
                            }
                        } else {
                            n8 = SSL.getError((long)this.ssl, (int)n9);
                            if (n8 == SSL.SSL_ERROR_ZERO_RETURN) {
                                if (!this.receivedShutdown) {
                                    this.closeAll();
                                    SSLEngineResult.HandshakeStatus handshakeStatus2 = this.mayFinishHandshake(handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED ? ((n3 += n5 - SSL.bioLengthByteBuffer((long)this.networkBIO)) == byteBuffer.remaining() ? SSLEngineResult.HandshakeStatus.NEED_WRAP : this.getHandshakeStatus(SSL.bioLengthNonApplication((long)this.networkBIO))) : SSLEngineResult.HandshakeStatus.FINISHED);
                                    SSLEngineResult sSLEngineResult = this.newResult(handshakeStatus2, n4, n3);
                                    return sSLEngineResult;
                                }
                                SSLEngineResult sSLEngineResult = this.newResult(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, n4, n3);
                                return sSLEngineResult;
                            }
                            if (n8 == SSL.SSL_ERROR_WANT_READ) {
                                SSLEngineResult sSLEngineResult = this.newResult(SSLEngineResult.HandshakeStatus.NEED_UNWRAP, n4, n3);
                                return sSLEngineResult;
                            }
                            if (n8 != SSL.SSL_ERROR_WANT_WRITE) {
                                throw this.shutdownWithError("SSL_write");
                            }
                            SSLEngineResult sSLEngineResult = this.newResult(SSLEngineResult.Status.BUFFER_OVERFLOW, handshakeStatus, n4, n3);
                            return sSLEngineResult;
                        }
                    }
                    ++n;
                }
                SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(handshakeStatus, n4, n3);
                return sSLEngineResult;
            } finally {
                SSL.bioClearByteBuffer((long)this.networkBIO);
                if (byteBuf == null) {
                    byteBuffer.position(byteBuffer.position() + n3);
                } else {
                    if (!$assertionsDisabled && byteBuf.readableBytes() > byteBuffer.remaining()) {
                        throw new AssertionError((Object)("The destination buffer " + byteBuffer + " didn't have enough remaining space to hold the encrypted content in " + byteBuf));
                    }
                    byteBuffer.put(byteBuf.internalNioBuffer(byteBuf.readerIndex(), n3));
                    byteBuf.release();
                }
            }
        }
    }

    private SSLEngineResult newResult(SSLEngineResult.HandshakeStatus handshakeStatus, int n, int n2) {
        return this.newResult(SSLEngineResult.Status.OK, handshakeStatus, n, n2);
    }

    private SSLEngineResult newResult(SSLEngineResult.Status status2, SSLEngineResult.HandshakeStatus handshakeStatus, int n, int n2) {
        if (this.isOutboundDone()) {
            if (this.isInboundDone()) {
                handshakeStatus = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                this.shutdown();
            }
            return new SSLEngineResult(SSLEngineResult.Status.CLOSED, handshakeStatus, n, n2);
        }
        return new SSLEngineResult(status2, handshakeStatus, n, n2);
    }

    private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus handshakeStatus, int n, int n2) throws SSLException {
        return this.newResult(this.mayFinishHandshake(handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED ? this.getHandshakeStatus() : SSLEngineResult.HandshakeStatus.FINISHED), n, n2);
    }

    private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.Status status2, SSLEngineResult.HandshakeStatus handshakeStatus, int n, int n2) throws SSLException {
        return this.newResult(status2, this.mayFinishHandshake(handshakeStatus != SSLEngineResult.HandshakeStatus.FINISHED ? this.getHandshakeStatus() : SSLEngineResult.HandshakeStatus.FINISHED), n, n2);
    }

    private SSLException shutdownWithError(String string) {
        String string2 = SSL.getLastError();
        return this.shutdownWithError(string, string2);
    }

    private SSLException shutdownWithError(String string, String string2) {
        if (logger.isDebugEnabled()) {
            logger.debug("{} failed: OpenSSL error: {}", (Object)string, (Object)string2);
        }
        this.shutdown();
        if (this.handshakeState == HandshakeState.FINISHED) {
            return new SSLException(string2);
        }
        return new SSLHandshakeException(string2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final SSLEngineResult unwrap(ByteBuffer[] byteBufferArray, int n, int n2, ByteBuffer[] byteBufferArray2, int n3, int n4) throws SSLException {
        Object object;
        int n5;
        ByteBuffer byteBuffer;
        if (byteBufferArray == null) {
            throw new NullPointerException("srcs");
        }
        if (n >= byteBufferArray.length || n + n2 > byteBufferArray.length) {
            throw new IndexOutOfBoundsException("offset: " + n + ", length: " + n2 + " (expected: offset <= offset + length <= srcs.length (" + byteBufferArray.length + "))");
        }
        if (byteBufferArray2 == null) {
            throw new IllegalArgumentException("dsts is null");
        }
        if (n3 >= byteBufferArray2.length || n3 + n4 > byteBufferArray2.length) {
            throw new IndexOutOfBoundsException("offset: " + n3 + ", length: " + n4 + " (expected: offset <= offset + length <= dsts.length (" + byteBufferArray2.length + "))");
        }
        long l = 0L;
        int n6 = n3 + n4;
        for (n5 = n3; n5 < n6; l += (long)byteBuffer.remaining(), ++n5) {
            byteBuffer = byteBufferArray2[n5];
            if (byteBuffer == null) {
                throw new IllegalArgumentException("dsts[" + n5 + "] is null");
            }
            if (!byteBuffer.isReadOnly()) continue;
            throw new ReadOnlyBufferException();
        }
        n5 = n + n2;
        long l2 = 0L;
        for (int i = n; i < n5; l2 += (long)object.remaining(), ++i) {
            object = byteBufferArray[i];
            if (object != null) continue;
            throw new IllegalArgumentException("srcs[" + i + "] is null");
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            int n7;
            int n8;
            block50: {
                int n9;
                if (this.isInboundDone()) {
                    return this.isOutboundDone() || this.isDestroyed() ? CLOSED_NOT_HANDSHAKING : NEED_WRAP_CLOSED;
                }
                object = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                if (this.handshakeState != HandshakeState.FINISHED) {
                    if (this.handshakeState != HandshakeState.STARTED_EXPLICITLY) {
                        this.handshakeState = HandshakeState.STARTED_IMPLICITLY;
                    }
                    if ((object = this.handshake()) == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
                        return NEED_WRAP_OK;
                    }
                    if (this.isInboundDone) {
                        return NEED_WRAP_CLOSED;
                    }
                }
                int n10 = this.sslPending0();
                if (this.jdkCompatibilityMode) {
                    if (l2 < 5L) {
                        return this.newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, (SSLEngineResult.HandshakeStatus)((Object)object), 0, 0);
                    }
                    n9 = SslUtils.getEncryptedPacketLength(byteBufferArray, n);
                    if (n9 == -2) {
                        throw new NotSslRecordException("not an SSL/TLS record");
                    }
                    n8 = n9 - 5;
                    if ((long)n8 > l) {
                        if (n8 > MAX_RECORD_SIZE) {
                            throw new SSLException("Illegal packet length: " + n8 + " > " + this.session.getApplicationBufferSize());
                        }
                        this.session.tryExpandApplicationBufferSize(n8);
                        return this.newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_OVERFLOW, (SSLEngineResult.HandshakeStatus)((Object)object), 0, 0);
                    }
                    if (l2 < (long)n9) {
                        return this.newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, (SSLEngineResult.HandshakeStatus)((Object)object), 0, 0);
                    }
                } else {
                    if (l2 == 0L && n10 <= 0) {
                        return this.newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, (SSLEngineResult.HandshakeStatus)((Object)object), 0, 0);
                    }
                    if (l == 0L) {
                        return this.newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_OVERFLOW, (SSLEngineResult.HandshakeStatus)((Object)object), 0, 0);
                    }
                    n9 = (int)Math.min(Integer.MAX_VALUE, l2);
                }
                if (!$assertionsDisabled && n >= n5) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && l <= 0L) {
                    throw new AssertionError();
                }
                n8 = 0;
                n7 = 0;
                try {
                    while (true) {
                        int n11;
                        ByteBuf byteBuf;
                        ByteBuffer byteBuffer2;
                        int n12;
                        if ((n12 = (byteBuffer2 = byteBufferArray[n]).remaining()) == 0) {
                            if (n10 <= 0) {
                                if (++n < n5) continue;
                                break;
                            }
                            byteBuf = null;
                            n11 = SSL.bioLengthByteBuffer((long)this.networkBIO);
                        } else {
                            n11 = Math.min(n9, n12);
                            byteBuf = this.writeEncryptedData(byteBuffer2, n11);
                        }
                        try {
                            int n13;
                            block51: {
                                while (true) {
                                    ByteBuffer byteBuffer3;
                                    if (!(byteBuffer3 = byteBufferArray2[n3]).hasRemaining()) {
                                        if (++n3 < n6) continue;
                                        break block50;
                                    }
                                    n13 = this.readPlaintextData(byteBuffer3);
                                    int n14 = n11 - SSL.bioLengthByteBuffer((long)this.networkBIO);
                                    n7 += n14;
                                    n9 -= n14;
                                    n11 -= n14;
                                    byteBuffer2.position(byteBuffer2.position() + n14);
                                    if (n13 <= 0) break block51;
                                    n8 += n13;
                                    if (!byteBuffer3.hasRemaining()) {
                                        n10 = this.sslPending0();
                                        if (++n3 < n6) continue;
                                        SSLEngineResult sSLEngineResult = n10 > 0 ? this.newResult(SSLEngineResult.Status.BUFFER_OVERFLOW, (SSLEngineResult.HandshakeStatus)((Object)object), n7, n8) : this.newResultMayFinishHandshake(this.isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, (SSLEngineResult.HandshakeStatus)((Object)object), n7, n8);
                                        return sSLEngineResult;
                                    }
                                    if (n9 == 0 || this.jdkCompatibilityMode) break;
                                }
                                break;
                            }
                            int n15 = SSL.getError((long)this.ssl, (int)n13);
                            if (n15 != SSL.SSL_ERROR_WANT_READ && n15 != SSL.SSL_ERROR_WANT_WRITE) {
                                if (n15 == SSL.SSL_ERROR_ZERO_RETURN) {
                                    if (!this.receivedShutdown) {
                                        this.closeAll();
                                    }
                                    SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(this.isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, (SSLEngineResult.HandshakeStatus)((Object)object), n7, n8);
                                    return sSLEngineResult;
                                }
                                SSLEngineResult sSLEngineResult = this.sslReadErrorResult(SSL.getLastErrorNumber(), n7, n8);
                                return sSLEngineResult;
                            }
                            if (++n < n5) continue;
                        } finally {
                            if (byteBuf == null) continue;
                            byteBuf.release();
                            continue;
                        }
                        break;
                    }
                } finally {
                    SSL.bioClearByteBuffer((long)this.networkBIO);
                    this.rejectRemoteInitiatedRenegotiation();
                }
            }
            if (!this.receivedShutdown && (SSL.getShutdown((long)this.ssl) & SSL.SSL_RECEIVED_SHUTDOWN) == SSL.SSL_RECEIVED_SHUTDOWN) {
                this.closeAll();
            }
            return this.newResultMayFinishHandshake(this.isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, (SSLEngineResult.HandshakeStatus)((Object)object), n7, n8);
        }
    }

    private SSLEngineResult sslReadErrorResult(int n, int n2, int n3) throws SSLException {
        String string = SSL.getErrorString((long)n);
        if (SSL.bioLengthNonApplication((long)this.networkBIO) > 0) {
            if (this.handshakeException == null && this.handshakeState != HandshakeState.FINISHED) {
                this.handshakeException = new SSLHandshakeException(string);
            }
            return new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, n2, n3);
        }
        throw this.shutdownWithError("SSL_read", string);
    }

    private void closeAll() throws SSLException {
        this.receivedShutdown = true;
        this.closeOutbound();
        this.closeInbound();
    }

    private void rejectRemoteInitiatedRenegotiation() throws SSLHandshakeException {
        if (!this.isDestroyed() && SSL.getHandshakeCount((long)this.ssl) > 1) {
            this.shutdown();
            throw new SSLHandshakeException("remote-initiated renegotiation not allowed");
        }
    }

    public final SSLEngineResult unwrap(ByteBuffer[] byteBufferArray, ByteBuffer[] byteBufferArray2) throws SSLException {
        return this.unwrap(byteBufferArray, 0, byteBufferArray.length, byteBufferArray2, 0, byteBufferArray2.length);
    }

    private ByteBuffer[] singleSrcBuffer(ByteBuffer byteBuffer) {
        this.singleSrcBuffer[0] = byteBuffer;
        return this.singleSrcBuffer;
    }

    private void resetSingleSrcBuffer() {
        this.singleSrcBuffer[0] = null;
    }

    private ByteBuffer[] singleDstBuffer(ByteBuffer byteBuffer) {
        this.singleDstBuffer[0] = byteBuffer;
        return this.singleDstBuffer;
    }

    private void resetSingleDstBuffer() {
        this.singleDstBuffer[0] = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArray, int n, int n2) throws SSLException {
        try {
            SSLEngineResult sSLEngineResult = this.unwrap(this.singleSrcBuffer(byteBuffer), 0, 1, byteBufferArray, n, n2);
            return sSLEngineResult;
        } finally {
            this.resetSingleSrcBuffer();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final synchronized SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        try {
            SSLEngineResult sSLEngineResult = this.wrap(this.singleSrcBuffer(byteBuffer), byteBuffer2);
            return sSLEngineResult;
        } finally {
            this.resetSingleSrcBuffer();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
        try {
            SSLEngineResult sSLEngineResult = this.unwrap(this.singleSrcBuffer(byteBuffer), this.singleDstBuffer(byteBuffer2));
            return sSLEngineResult;
        } finally {
            this.resetSingleSrcBuffer();
            this.resetSingleDstBuffer();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final synchronized SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBufferArray) throws SSLException {
        try {
            SSLEngineResult sSLEngineResult = this.unwrap(this.singleSrcBuffer(byteBuffer), byteBufferArray);
            return sSLEngineResult;
        } finally {
            this.resetSingleSrcBuffer();
        }
    }

    @Override
    public final Runnable getDelegatedTask() {
        return null;
    }

    @Override
    public final synchronized void closeInbound() throws SSLException {
        if (this.isInboundDone) {
            return;
        }
        this.isInboundDone = true;
        if (this.isOutboundDone()) {
            this.shutdown();
        }
        if (this.handshakeState != HandshakeState.NOT_STARTED && !this.receivedShutdown) {
            throw new SSLException("Inbound closed before receiving peer's close_notify: possible truncation attack?");
        }
    }

    @Override
    public final synchronized boolean isInboundDone() {
        return this.isInboundDone;
    }

    @Override
    public final synchronized void closeOutbound() {
        if (this.outboundClosed) {
            return;
        }
        this.outboundClosed = true;
        if (this.handshakeState != HandshakeState.NOT_STARTED && !this.isDestroyed()) {
            int n = SSL.getShutdown((long)this.ssl);
            if ((n & SSL.SSL_SENT_SHUTDOWN) != SSL.SSL_SENT_SHUTDOWN) {
                this.doSSLShutdown();
            }
        } else {
            this.shutdown();
        }
    }

    private boolean doSSLShutdown() {
        if (SSL.isInInit((long)this.ssl) != 0) {
            return true;
        }
        int n = SSL.shutdownSSL((long)this.ssl);
        if (n < 0) {
            int n2 = SSL.getError((long)this.ssl, (int)n);
            if (n2 == SSL.SSL_ERROR_SYSCALL || n2 == SSL.SSL_ERROR_SSL) {
                if (logger.isDebugEnabled()) {
                    logger.debug("SSL_shutdown failed: OpenSSL error: {}", (Object)SSL.getLastError());
                }
                this.shutdown();
                return true;
            }
            SSL.clearError();
        }
        return false;
    }

    @Override
    public final synchronized boolean isOutboundDone() {
        return this.outboundClosed && (this.networkBIO == 0L || SSL.bioLengthNonApplication((long)this.networkBIO) == 0);
    }

    @Override
    public final String[] getSupportedCipherSuites() {
        return OpenSsl.AVAILABLE_CIPHER_SUITES.toArray(new String[OpenSsl.AVAILABLE_CIPHER_SUITES.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final String[] getEnabledCipherSuites() {
        String[] stringArray;
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (this.isDestroyed()) {
                return EmptyArrays.EMPTY_STRINGS;
            }
            stringArray = SSL.getCiphers((long)this.ssl);
        }
        if (stringArray == null) {
            return EmptyArrays.EMPTY_STRINGS;
        }
        referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            for (int i = 0; i < stringArray.length; ++i) {
                String string = this.toJavaCipherSuite(stringArray[i]);
                if (string == null) continue;
                stringArray[i] = string;
            }
        }
        return stringArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void setEnabledCipherSuites(String[] stringArray) {
        ObjectUtil.checkNotNull(stringArray, "cipherSuites");
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringArray) {
            if (string == null) break;
            String string2 = CipherSuiteConverter.toOpenSsl(string);
            if (string2 == null) {
                string2 = string;
            }
            if (!OpenSsl.isCipherSuiteAvailable(string2)) {
                throw new IllegalArgumentException("unsupported cipher suite: " + string + '(' + string2 + ')');
            }
            stringBuilder.append(string2);
            stringBuilder.append(':');
        }
        if (stringBuilder.length() == 0) {
            throw new IllegalArgumentException("empty cipher suites");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        String string = stringBuilder.toString();
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (!this.isDestroyed()) {
                try {
                    SSL.setCipherSuites((long)this.ssl, (String)string);
                } catch (Exception exception) {
                    throw new IllegalStateException("failed to enable cipher suites: " + string, exception);
                }
            } else {
                throw new IllegalStateException("failed to enable cipher suites: " + string);
            }
        }
    }

    @Override
    public final String[] getSupportedProtocols() {
        return OpenSsl.SUPPORTED_PROTOCOLS_SET.toArray(new String[OpenSsl.SUPPORTED_PROTOCOLS_SET.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final String[] getEnabledProtocols() {
        int n;
        ArrayList<String> arrayList = new ArrayList<String>(6);
        arrayList.add("SSLv2Hello");
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (this.isDestroyed()) {
                return arrayList.toArray(new String[1]);
            }
            n = SSL.getOptions((long)this.ssl);
        }
        if (ReferenceCountedOpenSslEngine.isProtocolEnabled(n, SSL.SSL_OP_NO_TLSv1, "TLSv1")) {
            arrayList.add("TLSv1");
        }
        if (ReferenceCountedOpenSslEngine.isProtocolEnabled(n, SSL.SSL_OP_NO_TLSv1_1, "TLSv1.1")) {
            arrayList.add("TLSv1.1");
        }
        if (ReferenceCountedOpenSslEngine.isProtocolEnabled(n, SSL.SSL_OP_NO_TLSv1_2, "TLSv1.2")) {
            arrayList.add("TLSv1.2");
        }
        if (ReferenceCountedOpenSslEngine.isProtocolEnabled(n, SSL.SSL_OP_NO_SSLv2, "SSLv2")) {
            arrayList.add("SSLv2");
        }
        if (ReferenceCountedOpenSslEngine.isProtocolEnabled(n, SSL.SSL_OP_NO_SSLv3, "SSLv3")) {
            arrayList.add("SSLv3");
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    private static boolean isProtocolEnabled(int n, int n2, String string) {
        return (n & n2) == 0 && OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void setEnabledProtocols(String[] stringArray) {
        if (stringArray == null) {
            throw new IllegalArgumentException();
        }
        int n = OPENSSL_OP_NO_PROTOCOLS.length;
        int n2 = 0;
        for (String string : stringArray) {
            if (!OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(string)) {
                throw new IllegalArgumentException("Protocol " + string + " is not supported.");
            }
            if (string.equals("SSLv2")) {
                if (n > 0) {
                    n = 0;
                }
                if (n2 >= 0) continue;
                n2 = 0;
                continue;
            }
            if (string.equals("SSLv3")) {
                if (n > 1) {
                    n = 1;
                }
                if (n2 >= 1) continue;
                n2 = 1;
                continue;
            }
            if (string.equals("TLSv1")) {
                if (n > 2) {
                    n = 2;
                }
                if (n2 >= 2) continue;
                n2 = 2;
                continue;
            }
            if (string.equals("TLSv1.1")) {
                if (n > 3) {
                    n = 3;
                }
                if (n2 >= 3) continue;
                n2 = 3;
                continue;
            }
            if (!string.equals("TLSv1.2")) continue;
            if (n > 4) {
                n = 4;
            }
            if (n2 >= 4) continue;
            n2 = 4;
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            int n3;
            if (!this.isDestroyed()) {
                int n4;
                SSL.clearOptions((long)this.ssl, (int)(SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_NO_TLSv1 | SSL.SSL_OP_NO_TLSv1_1 | SSL.SSL_OP_NO_TLSv1_2));
                n3 = 0;
                for (n4 = 0; n4 < n; ++n4) {
                    n3 |= OPENSSL_OP_NO_PROTOCOLS[n4];
                }
                if (!$assertionsDisabled && n2 == Integer.MAX_VALUE) {
                    throw new AssertionError();
                }
                for (n4 = n2 + 1; n4 < OPENSSL_OP_NO_PROTOCOLS.length; ++n4) {
                    n3 |= OPENSSL_OP_NO_PROTOCOLS[n4];
                }
            } else {
                throw new IllegalStateException("failed to enable protocols: " + Arrays.asList(stringArray));
            }
            SSL.setOptions((long)this.ssl, (int)n3);
        }
    }

    @Override
    public final SSLSession getSession() {
        return this.session;
    }

    @Override
    public final synchronized void beginHandshake() throws SSLException {
        switch (this.handshakeState) {
            case STARTED_IMPLICITLY: {
                this.checkEngineClosed(BEGIN_HANDSHAKE_ENGINE_CLOSED);
                this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
                this.calculateMaxWrapOverhead();
                break;
            }
            case STARTED_EXPLICITLY: {
                break;
            }
            case FINISHED: {
                throw RENEGOTIATION_UNSUPPORTED;
            }
            case NOT_STARTED: {
                this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
                this.handshake();
                this.calculateMaxWrapOverhead();
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    private void checkEngineClosed(SSLException sSLException) throws SSLException {
        if (this.isDestroyed()) {
            throw sSLException;
        }
    }

    private static SSLEngineResult.HandshakeStatus pendingStatus(int n) {
        return n > 0 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
    }

    private static boolean isEmpty(Object[] objectArray) {
        return objectArray == null || objectArray.length == 0;
    }

    private static boolean isEmpty(byte[] byArray) {
        return byArray == null || byArray.length == 0;
    }

    private SSLEngineResult.HandshakeStatus handshake() throws SSLException {
        int n;
        if (this.handshakeState == HandshakeState.FINISHED) {
            return SSLEngineResult.HandshakeStatus.FINISHED;
        }
        this.checkEngineClosed(HANDSHAKE_ENGINE_CLOSED);
        SSLHandshakeException sSLHandshakeException = this.handshakeException;
        if (sSLHandshakeException != null) {
            if (SSL.bioLengthNonApplication((long)this.networkBIO) > 0) {
                return SSLEngineResult.HandshakeStatus.NEED_WRAP;
            }
            this.handshakeException = null;
            this.shutdown();
            throw sSLHandshakeException;
        }
        this.engineMap.add(this);
        if (this.lastAccessed == -1L) {
            this.lastAccessed = System.currentTimeMillis();
        }
        if (!this.certificateSet && this.keyMaterialManager != null) {
            this.certificateSet = true;
            this.keyMaterialManager.setKeyMaterial(this);
        }
        if ((n = SSL.doHandshake((long)this.ssl)) <= 0) {
            if (this.handshakeException != null) {
                sSLHandshakeException = this.handshakeException;
                this.handshakeException = null;
                this.shutdown();
                throw sSLHandshakeException;
            }
            int n2 = SSL.getError((long)this.ssl, (int)n);
            if (n2 == SSL.SSL_ERROR_WANT_READ || n2 == SSL.SSL_ERROR_WANT_WRITE) {
                return ReferenceCountedOpenSslEngine.pendingStatus(SSL.bioLengthNonApplication((long)this.networkBIO));
            }
            throw this.shutdownWithError("SSL_do_handshake");
        }
        this.session.handshakeFinished();
        this.engineMap.remove(this.ssl);
        return SSLEngineResult.HandshakeStatus.FINISHED;
    }

    private SSLEngineResult.HandshakeStatus mayFinishHandshake(SSLEngineResult.HandshakeStatus handshakeStatus) throws SSLException {
        if (handshakeStatus == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING && this.handshakeState != HandshakeState.FINISHED) {
            return this.handshake();
        }
        return handshakeStatus;
    }

    @Override
    public final synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return this.needPendingStatus() ? ReferenceCountedOpenSslEngine.pendingStatus(SSL.bioLengthNonApplication((long)this.networkBIO)) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private SSLEngineResult.HandshakeStatus getHandshakeStatus(int n) {
        return this.needPendingStatus() ? ReferenceCountedOpenSslEngine.pendingStatus(n) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private boolean needPendingStatus() {
        return this.handshakeState != HandshakeState.NOT_STARTED && !this.isDestroyed() && (this.handshakeState != HandshakeState.FINISHED || this.isInboundDone() || this.isOutboundDone());
    }

    private String toJavaCipherSuite(String string) {
        if (string == null) {
            return null;
        }
        String string2 = ReferenceCountedOpenSslEngine.toJavaCipherSuitePrefix(SSL.getVersion((long)this.ssl));
        return CipherSuiteConverter.toJava(string, string2);
    }

    private static String toJavaCipherSuitePrefix(String string) {
        int n = string == null || string.isEmpty() ? 0 : (int)string.charAt(0);
        switch (n) {
            case 84: {
                return "TLS";
            }
            case 83: {
                return "SSL";
            }
        }
        return "UNKNOWN";
    }

    @Override
    public final void setUseClientMode(boolean bl) {
        if (bl != this.clientMode) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public final boolean getUseClientMode() {
        return this.clientMode;
    }

    @Override
    public final void setNeedClientAuth(boolean bl) {
        this.setClientAuth(bl ? ClientAuth.REQUIRE : ClientAuth.NONE);
    }

    @Override
    public final boolean getNeedClientAuth() {
        return this.clientAuth == ClientAuth.REQUIRE;
    }

    @Override
    public final void setWantClientAuth(boolean bl) {
        this.setClientAuth(bl ? ClientAuth.OPTIONAL : ClientAuth.NONE);
    }

    @Override
    public final boolean getWantClientAuth() {
        return this.clientAuth == ClientAuth.OPTIONAL;
    }

    public final synchronized void setVerify(int n, int n2) {
        SSL.setVerify((long)this.ssl, (int)n, (int)n2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setClientAuth(ClientAuth clientAuth) {
        if (this.clientMode) {
            return;
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (this.clientAuth == clientAuth) {
                return;
            }
            switch (clientAuth) {
                case NONE: {
                    SSL.setVerify((long)this.ssl, (int)0, (int)10);
                    break;
                }
                case REQUIRE: {
                    SSL.setVerify((long)this.ssl, (int)2, (int)10);
                    break;
                }
                case OPTIONAL: {
                    SSL.setVerify((long)this.ssl, (int)1, (int)10);
                    break;
                }
                default: {
                    throw new Error(clientAuth.toString());
                }
            }
            this.clientAuth = clientAuth;
        }
    }

    @Override
    public final void setEnableSessionCreation(boolean bl) {
        if (bl) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public final boolean getEnableSessionCreation() {
        return true;
    }

    @Override
    public final synchronized SSLParameters getSSLParameters() {
        SSLParameters sSLParameters = super.getSSLParameters();
        int n = PlatformDependent.javaVersion();
        if (n >= 7) {
            sSLParameters.setEndpointIdentificationAlgorithm(this.endPointIdentificationAlgorithm);
            Java7SslParametersUtils.setAlgorithmConstraints(sSLParameters, this.algorithmConstraints);
            if (n >= 8) {
                if (this.sniHostNames != null) {
                    Java8SslUtils.setSniHostNames(sSLParameters, this.sniHostNames);
                }
                if (!this.isDestroyed()) {
                    Java8SslUtils.setUseCipherSuitesOrder(sSLParameters, (SSL.getOptions((long)this.ssl) & SSL.SSL_OP_CIPHER_SERVER_PREFERENCE) != 0);
                }
                Java8SslUtils.setSNIMatchers(sSLParameters, this.matchers);
            }
        }
        return sSLParameters;
    }

    @Override
    public final synchronized void setSSLParameters(SSLParameters sSLParameters) {
        int n = PlatformDependent.javaVersion();
        if (n >= 7) {
            Object object;
            if (sSLParameters.getAlgorithmConstraints() != null) {
                throw new IllegalArgumentException("AlgorithmConstraints are not supported.");
            }
            if (n >= 8) {
                if (!this.isDestroyed()) {
                    if (this.clientMode) {
                        object = Java8SslUtils.getSniHostNames(sSLParameters);
                        Iterator iterator2 = object.iterator();
                        while (iterator2.hasNext()) {
                            String string = (String)iterator2.next();
                            SSL.setTlsExtHostName((long)this.ssl, (String)string);
                        }
                        this.sniHostNames = object;
                    }
                    if (Java8SslUtils.getUseCipherSuitesOrder(sSLParameters)) {
                        SSL.setOptions((long)this.ssl, (int)SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                    } else {
                        SSL.clearOptions((long)this.ssl, (int)SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                    }
                }
                this.matchers = sSLParameters.getSNIMatchers();
            }
            boolean bl = (object = sSLParameters.getEndpointIdentificationAlgorithm()) != null && !((String)object).isEmpty();
            SSL.setHostNameValidation((long)this.ssl, (int)0, (String)(bl ? this.getPeerHost() : null));
            if (this.clientMode && bl) {
                SSL.setVerify((long)this.ssl, (int)2, (int)-1);
            }
            this.endPointIdentificationAlgorithm = object;
            this.algorithmConstraints = sSLParameters.getAlgorithmConstraints();
        }
        super.setSSLParameters(sSLParameters);
    }

    private boolean isDestroyed() {
        return this.destroyed != 0;
    }

    final boolean checkSniHostnameMatch(String string) {
        return Java8SslUtils.checkSniHostnameMatch(this.matchers, string);
    }

    @Override
    public String getNegotiatedApplicationProtocol() {
        return this.applicationProtocol;
    }

    private static long bufferAddress(ByteBuffer byteBuffer) {
        if (!$assertionsDisabled && !byteBuffer.isDirect()) {
            throw new AssertionError();
        }
        if (PlatformDependent.hasUnsafe()) {
            return PlatformDependent.directBufferAddress(byteBuffer);
        }
        return Buffer.address((ByteBuffer)byteBuffer);
    }

    static ResourceLeakTracker access$000(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
        return referenceCountedOpenSslEngine.leak;
    }

    static boolean access$100(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
        return referenceCountedOpenSslEngine.isDestroyed();
    }

    static long access$200(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
        return referenceCountedOpenSslEngine.ssl;
    }

    static long access$300(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
        return referenceCountedOpenSslEngine.lastAccessed;
    }

    static String access$400(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String string) {
        return referenceCountedOpenSslEngine.toJavaCipherSuite(string);
    }

    static void access$500(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
        referenceCountedOpenSslEngine.calculateMaxWrapOverhead();
    }

    static HandshakeState access$602(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, HandshakeState handshakeState) {
        referenceCountedOpenSslEngine.handshakeState = handshakeState;
        return referenceCountedOpenSslEngine.handshakeState;
    }

    static boolean access$700(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
        return referenceCountedOpenSslEngine.clientMode;
    }

    static boolean access$800(Object[] objectArray) {
        return ReferenceCountedOpenSslEngine.isEmpty(objectArray);
    }

    static boolean access$900(byte[] byArray) {
        return ReferenceCountedOpenSslEngine.isEmpty(byArray);
    }

    static OpenSslApplicationProtocolNegotiator access$1000(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
        return referenceCountedOpenSslEngine.apn;
    }

    static String access$1102(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, String string) {
        referenceCountedOpenSslEngine.applicationProtocol = string;
        return referenceCountedOpenSslEngine.applicationProtocol;
    }

    static Certificate[] access$1200(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine) {
        return referenceCountedOpenSslEngine.localCerts;
    }

    static int access$1300() {
        return MAX_RECORD_SIZE;
    }

    static {
        $assertionsDisabled = !ReferenceCountedOpenSslEngine.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslEngine.class);
        BEGIN_HANDSHAKE_ENGINE_CLOSED = ThrowableUtil.unknownStackTrace(new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "beginHandshake()");
        HANDSHAKE_ENGINE_CLOSED = ThrowableUtil.unknownStackTrace(new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "handshake()");
        RENEGOTIATION_UNSUPPORTED = ThrowableUtil.unknownStackTrace(new SSLException("renegotiation unsupported"), ReferenceCountedOpenSslEngine.class, "beginHandshake()");
        leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslEngine.class);
        OPENSSL_OP_NO_PROTOCOLS = new int[]{SSL.SSL_OP_NO_SSLv2, SSL.SSL_OP_NO_SSLv3, SSL.SSL_OP_NO_TLSv1, SSL.SSL_OP_NO_TLSv1_1, SSL.SSL_OP_NO_TLSv1_2};
        MAX_PLAINTEXT_LENGTH = SSL.SSL_MAX_PLAINTEXT_LENGTH;
        MAX_RECORD_SIZE = SSL.SSL_MAX_RECORD_LENGTH;
        DESTROYED_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ReferenceCountedOpenSslEngine.class, "destroyed");
        NEED_UNWRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
        NEED_UNWRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
        NEED_WRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
        NEED_WRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
        CLOSED_NOT_HANDSHAKING = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
    }

    private final class OpenSslSession
    implements SSLSession {
        private final OpenSslSessionContext sessionContext;
        private X509Certificate[] x509PeerCerts;
        private Certificate[] peerCerts;
        private String protocol;
        private String cipher;
        private byte[] id;
        private long creationTime;
        private volatile int applicationBufferSize;
        private Map<String, Object> values;
        static final boolean $assertionsDisabled = !ReferenceCountedOpenSslEngine.class.desiredAssertionStatus();
        final ReferenceCountedOpenSslEngine this$0;

        OpenSslSession(ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine, OpenSslSessionContext openSslSessionContext) {
            this.this$0 = referenceCountedOpenSslEngine;
            this.applicationBufferSize = MAX_PLAINTEXT_LENGTH;
            this.sessionContext = openSslSessionContext;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte[] getId() {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.this$0;
            synchronized (referenceCountedOpenSslEngine) {
                if (this.id == null) {
                    return EmptyArrays.EMPTY_BYTES;
                }
                return (byte[])this.id.clone();
            }
        }

        @Override
        public SSLSessionContext getSessionContext() {
            return this.sessionContext;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public long getCreationTime() {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.this$0;
            synchronized (referenceCountedOpenSslEngine) {
                if (this.creationTime == 0L && !ReferenceCountedOpenSslEngine.access$100(this.this$0)) {
                    this.creationTime = SSL.getTime((long)ReferenceCountedOpenSslEngine.access$200(this.this$0)) * 1000L;
                }
            }
            return this.creationTime;
        }

        @Override
        public long getLastAccessedTime() {
            long l = ReferenceCountedOpenSslEngine.access$300(this.this$0);
            return l == -1L ? this.getCreationTime() : l;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void invalidate() {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.this$0;
            synchronized (referenceCountedOpenSslEngine) {
                if (!ReferenceCountedOpenSslEngine.access$100(this.this$0)) {
                    SSL.setTimeout((long)ReferenceCountedOpenSslEngine.access$200(this.this$0), (long)0L);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isValid() {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.this$0;
            synchronized (referenceCountedOpenSslEngine) {
                if (!ReferenceCountedOpenSslEngine.access$100(this.this$0)) {
                    return System.currentTimeMillis() - SSL.getTimeout((long)ReferenceCountedOpenSslEngine.access$200(this.this$0)) * 1000L < SSL.getTime((long)ReferenceCountedOpenSslEngine.access$200(this.this$0)) * 1000L;
                }
            }
            return true;
        }

        @Override
        public void putValue(String string, Object object) {
            if (string == null) {
                throw new NullPointerException("name");
            }
            if (object == null) {
                throw new NullPointerException("value");
            }
            Map<String, Object> map = this.values;
            if (map == null) {
                map = this.values = new HashMap<String, Object>(2);
            }
            Object object2 = map.put(string, object);
            if (object instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener)object).valueBound(new SSLSessionBindingEvent(this, string));
            }
            this.notifyUnbound(object2, string);
        }

        @Override
        public Object getValue(String string) {
            if (string == null) {
                throw new NullPointerException("name");
            }
            if (this.values == null) {
                return null;
            }
            return this.values.get(string);
        }

        @Override
        public void removeValue(String string) {
            if (string == null) {
                throw new NullPointerException("name");
            }
            Map<String, Object> map = this.values;
            if (map == null) {
                return;
            }
            Object object = map.remove(string);
            this.notifyUnbound(object, string);
        }

        @Override
        public String[] getValueNames() {
            Map<String, Object> map = this.values;
            if (map == null || map.isEmpty()) {
                return EmptyArrays.EMPTY_STRINGS;
            }
            return map.keySet().toArray(new String[map.size()]);
        }

        private void notifyUnbound(Object object, String string) {
            if (object instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener)object).valueUnbound(new SSLSessionBindingEvent(this, string));
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void handshakeFinished() throws SSLException {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.this$0;
            synchronized (referenceCountedOpenSslEngine) {
                if (ReferenceCountedOpenSslEngine.access$100(this.this$0)) {
                    throw new SSLException("Already closed");
                }
                this.id = SSL.getSessionId((long)ReferenceCountedOpenSslEngine.access$200(this.this$0));
                this.cipher = ReferenceCountedOpenSslEngine.access$400(this.this$0, SSL.getCipherForSSL((long)ReferenceCountedOpenSslEngine.access$200(this.this$0)));
                this.protocol = SSL.getVersion((long)ReferenceCountedOpenSslEngine.access$200(this.this$0));
                this.initPeerCerts();
                this.selectApplicationProtocol();
                ReferenceCountedOpenSslEngine.access$500(this.this$0);
                ReferenceCountedOpenSslEngine.access$602(this.this$0, HandshakeState.FINISHED);
            }
        }

        private void initPeerCerts() {
            byte[][] byArray = SSL.getPeerCertChain((long)ReferenceCountedOpenSslEngine.access$200(this.this$0));
            if (ReferenceCountedOpenSslEngine.access$700(this.this$0)) {
                if (ReferenceCountedOpenSslEngine.access$800((Object[])byArray)) {
                    this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
                    this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
                } else {
                    this.peerCerts = new Certificate[byArray.length];
                    this.x509PeerCerts = new X509Certificate[byArray.length];
                    this.initCerts(byArray, 0);
                }
            } else {
                byte[] byArray2 = SSL.getPeerCertificate((long)ReferenceCountedOpenSslEngine.access$200(this.this$0));
                if (ReferenceCountedOpenSslEngine.access$900(byArray2)) {
                    this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
                    this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
                } else if (ReferenceCountedOpenSslEngine.access$800((Object[])byArray)) {
                    this.peerCerts = new Certificate[]{new OpenSslX509Certificate(byArray2)};
                    this.x509PeerCerts = new X509Certificate[]{new OpenSslJavaxX509Certificate(byArray2)};
                } else {
                    this.peerCerts = new Certificate[byArray.length + 1];
                    this.x509PeerCerts = new X509Certificate[byArray.length + 1];
                    this.peerCerts[0] = new OpenSslX509Certificate(byArray2);
                    this.x509PeerCerts[0] = new OpenSslJavaxX509Certificate(byArray2);
                    this.initCerts(byArray, 1);
                }
            }
        }

        private void initCerts(byte[][] byArray, int n) {
            for (int i = 0; i < byArray.length; ++i) {
                int n2 = n + i;
                this.peerCerts[n2] = new OpenSslX509Certificate(byArray[i]);
                this.x509PeerCerts[n2] = new OpenSslJavaxX509Certificate(byArray[i]);
            }
        }

        private void selectApplicationProtocol() throws SSLException {
            ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior = ReferenceCountedOpenSslEngine.access$1000(this.this$0).selectedListenerFailureBehavior();
            List<String> list = ReferenceCountedOpenSslEngine.access$1000(this.this$0).protocols();
            switch (ReferenceCountedOpenSslEngine.access$1000(this.this$0).protocol()) {
                case NONE: {
                    break;
                }
                case ALPN: {
                    String string = SSL.getAlpnSelected((long)ReferenceCountedOpenSslEngine.access$200(this.this$0));
                    if (string == null) break;
                    ReferenceCountedOpenSslEngine.access$1102(this.this$0, this.selectApplicationProtocol(list, selectedListenerFailureBehavior, string));
                    break;
                }
                case NPN: {
                    String string = SSL.getNextProtoNegotiated((long)ReferenceCountedOpenSslEngine.access$200(this.this$0));
                    if (string == null) break;
                    ReferenceCountedOpenSslEngine.access$1102(this.this$0, this.selectApplicationProtocol(list, selectedListenerFailureBehavior, string));
                    break;
                }
                case NPN_AND_ALPN: {
                    String string = SSL.getAlpnSelected((long)ReferenceCountedOpenSslEngine.access$200(this.this$0));
                    if (string == null) {
                        string = SSL.getNextProtoNegotiated((long)ReferenceCountedOpenSslEngine.access$200(this.this$0));
                    }
                    if (string == null) break;
                    ReferenceCountedOpenSslEngine.access$1102(this.this$0, this.selectApplicationProtocol(list, selectedListenerFailureBehavior, string));
                    break;
                }
                default: {
                    throw new Error();
                }
            }
        }

        private String selectApplicationProtocol(List<String> list, ApplicationProtocolConfig.SelectedListenerFailureBehavior selectedListenerFailureBehavior, String string) throws SSLException {
            if (selectedListenerFailureBehavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT) {
                return string;
            }
            int n = list.size();
            if (!$assertionsDisabled && n <= 0) {
                throw new AssertionError();
            }
            if (list.contains(string)) {
                return string;
            }
            if (selectedListenerFailureBehavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL) {
                return list.get(n - 1);
            }
            throw new SSLException("unknown protocol " + string);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.this$0;
            synchronized (referenceCountedOpenSslEngine) {
                if (ReferenceCountedOpenSslEngine.access$800(this.peerCerts)) {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
                return (Certificate[])this.peerCerts.clone();
            }
        }

        @Override
        public Certificate[] getLocalCertificates() {
            if (ReferenceCountedOpenSslEngine.access$1200(this.this$0) == null) {
                return null;
            }
            return (Certificate[])ReferenceCountedOpenSslEngine.access$1200(this.this$0).clone();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.this$0;
            synchronized (referenceCountedOpenSslEngine) {
                if (ReferenceCountedOpenSslEngine.access$800(this.x509PeerCerts)) {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
                return (X509Certificate[])this.x509PeerCerts.clone();
            }
        }

        @Override
        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            Certificate[] certificateArray = this.getPeerCertificates();
            return ((java.security.cert.X509Certificate)certificateArray[0]).getSubjectX500Principal();
        }

        @Override
        public Principal getLocalPrincipal() {
            Certificate[] certificateArray = ReferenceCountedOpenSslEngine.access$1200(this.this$0);
            if (certificateArray == null || certificateArray.length == 0) {
                return null;
            }
            return ((java.security.cert.X509Certificate)certificateArray[0]).getIssuerX500Principal();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public String getCipherSuite() {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.this$0;
            synchronized (referenceCountedOpenSslEngine) {
                if (this.cipher == null) {
                    return ReferenceCountedOpenSslEngine.INVALID_CIPHER;
                }
                return this.cipher;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public String getProtocol() {
            String string = this.protocol;
            if (string == null) {
                ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this.this$0;
                synchronized (referenceCountedOpenSslEngine) {
                    string = !ReferenceCountedOpenSslEngine.access$100(this.this$0) ? SSL.getVersion((long)ReferenceCountedOpenSslEngine.access$200(this.this$0)) : "";
                }
            }
            return string;
        }

        @Override
        public String getPeerHost() {
            return this.this$0.getPeerHost();
        }

        @Override
        public int getPeerPort() {
            return this.this$0.getPeerPort();
        }

        @Override
        public int getPacketBufferSize() {
            return this.this$0.maxEncryptedPacketLength();
        }

        @Override
        public int getApplicationBufferSize() {
            return this.applicationBufferSize;
        }

        void tryExpandApplicationBufferSize(int n) {
            if (n > MAX_PLAINTEXT_LENGTH && this.applicationBufferSize != ReferenceCountedOpenSslEngine.access$1300()) {
                this.applicationBufferSize = ReferenceCountedOpenSslEngine.access$1300();
            }
        }
    }

    private static enum HandshakeState {
        NOT_STARTED,
        STARTED_IMPLICITLY,
        STARTED_EXPLICITLY,
        FINISHED;

    }
}

