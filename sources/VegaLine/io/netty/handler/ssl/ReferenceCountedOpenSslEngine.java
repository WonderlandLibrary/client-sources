/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolAccessor;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.CipherSuiteConverter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.Java7SslParametersUtils;
import io.netty.handler.ssl.Java8SslParametersUtils;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import javax.net.ssl.SNIMatcher;
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
implements ReferenceCounted {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountedOpenSslEngine.class);
    private static final SSLException BEGIN_HANDSHAKE_ENGINE_CLOSED = ThrowableUtil.unknownStackTrace(new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "beginHandshake()");
    private static final SSLException HANDSHAKE_ENGINE_CLOSED = ThrowableUtil.unknownStackTrace(new SSLException("engine closed"), ReferenceCountedOpenSslEngine.class, "handshake()");
    private static final SSLException RENEGOTIATION_UNSUPPORTED = ThrowableUtil.unknownStackTrace(new SSLException("renegotiation unsupported"), ReferenceCountedOpenSslEngine.class, "beginHandshake()");
    private static final ResourceLeakDetector<ReferenceCountedOpenSslEngine> leakDetector = ResourceLeakDetectorFactory.instance().newResourceLeakDetector(ReferenceCountedOpenSslEngine.class);
    private static final int DEFAULT_HOSTNAME_VALIDATION_FLAGS = 0;
    static final int MAX_PLAINTEXT_LENGTH = 16384;
    static final int MAX_TLS_RECORD_OVERHEAD_LENGTH = 90;
    static final int MAX_ENCRYPTED_PACKET_LENGTH = 16474;
    private static final AtomicIntegerFieldUpdater<ReferenceCountedOpenSslEngine> DESTROYED_UPDATER = AtomicIntegerFieldUpdater.newUpdater(ReferenceCountedOpenSslEngine.class, "destroyed");
    private static final String INVALID_CIPHER = "SSL_NULL_WITH_NULL_NULL";
    private static final SSLEngineResult NEED_UNWRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_UNWRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_UNWRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_OK = new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult NEED_WRAP_CLOSED = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, 0);
    private static final SSLEngineResult CLOSED_NOT_HANDSHAKING = new SSLEngineResult(SSLEngineResult.Status.CLOSED, SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
    private long ssl;
    private long networkBIO;
    private boolean certificateSet;
    private HandshakeState handshakeState = HandshakeState.NOT_STARTED;
    private boolean renegotiationPending;
    private boolean receivedShutdown;
    private volatile int destroyed;
    private final ResourceLeakTracker<ReferenceCountedOpenSslEngine> leak;
    private final AbstractReferenceCounted refCnt = new AbstractReferenceCounted(){

        @Override
        public ReferenceCounted touch(Object hint) {
            if (ReferenceCountedOpenSslEngine.this.leak != null) {
                ReferenceCountedOpenSslEngine.this.leak.record(hint);
            }
            return ReferenceCountedOpenSslEngine.this;
        }

        @Override
        protected void deallocate() {
            ReferenceCountedOpenSslEngine.this.shutdown();
            if (ReferenceCountedOpenSslEngine.this.leak != null) {
                boolean closed = ReferenceCountedOpenSslEngine.this.leak.close(ReferenceCountedOpenSslEngine.this);
                assert (closed);
            }
        }
    };
    private volatile ClientAuth clientAuth = ClientAuth.NONE;
    private volatile long lastAccessed = -1L;
    private String endPointIdentificationAlgorithm;
    private Object algorithmConstraints;
    private List<String> sniHostNames;
    private boolean isInboundDone;
    private boolean outboundClosed;
    private final boolean clientMode;
    private final ByteBufAllocator alloc;
    private final OpenSslEngineMap engineMap;
    private final OpenSslApplicationProtocolNegotiator apn;
    private final boolean rejectRemoteInitiatedRenegation;
    private final OpenSslSession session;
    private final Certificate[] localCerts;
    private final ByteBuffer[] singleSrcBuffer = new ByteBuffer[1];
    private final ByteBuffer[] singleDstBuffer = new ByteBuffer[1];
    private final OpenSslKeyMaterialManager keyMaterialManager;
    SSLHandshakeException handshakeException;

    ReferenceCountedOpenSslEngine(ReferenceCountedOpenSslContext context, ByteBufAllocator alloc, String peerHost, int peerPort, boolean leakDetection) {
        super(peerHost, peerPort);
        OpenSsl.ensureAvailability();
        this.leak = leakDetection ? leakDetector.track(this) : null;
        this.alloc = ObjectUtil.checkNotNull(alloc, "alloc");
        this.apn = (OpenSslApplicationProtocolNegotiator)context.applicationProtocolNegotiator();
        this.session = new OpenSslSession(context.sessionContext());
        this.clientMode = context.isClient();
        this.engineMap = context.engineMap;
        this.rejectRemoteInitiatedRenegation = context.getRejectRemoteInitiatedRenegotiation();
        this.localCerts = context.keyCertChain;
        this.keyMaterialManager = context.keyMaterialManager();
        this.ssl = SSL.newSSL(context.ctx, !context.isClient());
        try {
            this.networkBIO = SSL.bioNewByteBuffer(this.ssl, context.getBioNonApplicationBufferSize());
            this.setClientAuth(this.clientMode ? ClientAuth.NONE : context.clientAuth);
            if (context.protocols != null) {
                this.setEnabledProtocols(context.protocols);
            }
            if (this.clientMode && peerHost != null) {
                SSL.setTlsExtHostName(this.ssl, peerHost);
            }
        } catch (Throwable cause) {
            SSL.freeSSL(this.ssl);
            PlatformDependent.throwException(cause);
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
        if (DESTROYED_UPDATER.compareAndSet(this, 0, 1)) {
            this.engineMap.remove(this.ssl);
            SSL.freeSSL(this.ssl);
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
    private int writePlaintextData(ByteBuffer src, int len) {
        int sslWrote;
        int pos = src.position();
        int limit = src.limit();
        if (src.isDirect()) {
            sslWrote = SSL.writeToSSL(this.ssl, Buffer.address(src) + (long)pos, len);
            if (sslWrote > 0) {
                src.position(pos + sslWrote);
            }
        } else {
            ByteBuf buf = this.alloc.directBuffer(len);
            try {
                src.limit(pos + len);
                buf.setBytes(0, src);
                src.limit(limit);
                sslWrote = SSL.writeToSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
                if (sslWrote > 0) {
                    src.position(pos + sslWrote);
                } else {
                    src.position(pos);
                }
            } finally {
                buf.release();
            }
        }
        return sslWrote;
    }

    private ByteBuf writeEncryptedData(ByteBuffer src, int len) {
        int pos = src.position();
        if (src.isDirect()) {
            SSL.bioSetByteBuffer(this.networkBIO, Buffer.address(src) + (long)pos, len, false);
        } else {
            ByteBuf buf = this.alloc.directBuffer(len);
            try {
                int limit = src.limit();
                src.limit(pos + len);
                buf.writeBytes(src);
                src.position(pos);
                src.limit(limit);
                SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(buf), len, false);
                return buf;
            } catch (Throwable cause) {
                buf.release();
                PlatformDependent.throwException(cause);
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int readPlaintextData(ByteBuffer dst) {
        int sslRead;
        int pos = dst.position();
        if (dst.isDirect()) {
            sslRead = SSL.readFromSSL(this.ssl, Buffer.address(dst) + (long)pos, dst.limit() - pos);
            if (sslRead > 0) {
                dst.position(pos + sslRead);
            }
        } else {
            int limit = dst.limit();
            int len = Math.min(16474, limit - pos);
            ByteBuf buf = this.alloc.directBuffer(len);
            try {
                sslRead = SSL.readFromSSL(this.ssl, OpenSsl.memoryAddress(buf), len);
                if (sslRead > 0) {
                    dst.limit(pos + sslRead);
                    buf.getBytes(buf.readerIndex(), dst);
                    dst.limit(limit);
                }
            } finally {
                buf.release();
            }
        }
        return sslRead;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int length, ByteBuffer dst) throws SSLException {
        if (srcs == null) {
            throw new IllegalArgumentException("srcs is null");
        }
        if (dst == null) {
            throw new IllegalArgumentException("dst is null");
        }
        if (offset >= srcs.length || offset + length > srcs.length) {
            throw new IndexOutOfBoundsException("offset: " + offset + ", length: " + length + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))");
        }
        if (dst.isReadOnly()) {
            throw new ReadOnlyBufferException();
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (this.isOutboundDone()) {
                return this.isInboundDone() || this.isDestroyed() ? CLOSED_NOT_HANDSHAKING : NEED_UNWRAP_CLOSED;
            }
            int bytesProduced = 0;
            ByteBuf bioReadCopyBuf = null;
            try {
                ByteBuffer src;
                if (dst.isDirect()) {
                    SSL.bioSetByteBuffer(this.networkBIO, Buffer.address(dst) + (long)dst.position(), dst.remaining(), true);
                } else {
                    bioReadCopyBuf = this.alloc.directBuffer(dst.remaining());
                    SSL.bioSetByteBuffer(this.networkBIO, OpenSsl.memoryAddress(bioReadCopyBuf), bioReadCopyBuf.writableBytes(), true);
                }
                int bioLengthBefore = SSL.bioLengthByteBuffer(this.networkBIO);
                if (this.outboundClosed) {
                    bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
                    if (bytesProduced <= 0) {
                        SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, 0);
                        return sSLEngineResult;
                    }
                    if (!this.doSSLShutdown()) {
                        SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, 0, bytesProduced);
                        return sSLEngineResult;
                    }
                    bytesProduced = bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO);
                    SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, bytesProduced);
                    return sSLEngineResult;
                }
                SSLEngineResult.HandshakeStatus status = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                if (this.handshakeState != HandshakeState.FINISHED) {
                    if (this.handshakeState != HandshakeState.STARTED_EXPLICITLY) {
                        this.handshakeState = HandshakeState.STARTED_IMPLICITLY;
                    }
                    if ((bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO)) > 0 && this.handshakeException != null) {
                        SSLEngineResult sSLEngineResult = this.newResult(SSLEngineResult.HandshakeStatus.NEED_WRAP, 0, bytesProduced);
                        return sSLEngineResult;
                    }
                    status = this.handshake();
                    if (this.renegotiationPending && status == SSLEngineResult.HandshakeStatus.FINISHED) {
                        this.renegotiationPending = false;
                        SSL.setState(this.ssl, SSL.SSL_ST_ACCEPT);
                        this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
                        status = this.handshake();
                    }
                    if ((bytesProduced = bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO)) > 0) {
                        SSLEngineResult sSLEngineResult = this.newResult(this.mayFinishHandshake(status != SSLEngineResult.HandshakeStatus.FINISHED ? this.getHandshakeStatus(SSL.bioLengthNonApplication(this.networkBIO)) : SSLEngineResult.HandshakeStatus.FINISHED), 0, bytesProduced);
                        return sSLEngineResult;
                    }
                    if (status == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                        SSLEngineResult sSLEngineResult = this.isOutboundDone() ? NEED_UNWRAP_CLOSED : NEED_UNWRAP_OK;
                        return sSLEngineResult;
                    }
                    if (this.outboundClosed) {
                        bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
                        SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(status, 0, bytesProduced);
                        return sSLEngineResult;
                    }
                }
                int srcsLen = 0;
                int endOffset = offset + length;
                for (int i = offset; i < endOffset; ++i) {
                    src = srcs[i];
                    if (src == null) {
                        throw new IllegalArgumentException("srcs[" + i + "] is null");
                    }
                    if (srcsLen == 16384 || (srcsLen += src.remaining()) <= 16384 && srcsLen >= 0) continue;
                    srcsLen = 16384;
                }
                if (dst.remaining() < ReferenceCountedOpenSslEngine.calculateOutNetBufSize(srcsLen, endOffset - offset)) {
                    SSLEngineResult i = new SSLEngineResult(SSLEngineResult.Status.BUFFER_OVERFLOW, this.getHandshakeStatus(), 0, 0);
                    return i;
                }
                int bytesConsumed = 0;
                bytesProduced = SSL.bioFlushByteBuffer(this.networkBIO);
                while (offset < endOffset) {
                    src = srcs[offset];
                    int remaining = src.remaining();
                    if (remaining != 0) {
                        int bytesWritten = this.writePlaintextData(src, Math.min(remaining, 16384 - bytesConsumed));
                        if (bytesWritten > 0) {
                            int pendingNow = SSL.bioLengthByteBuffer(this.networkBIO);
                            bioLengthBefore = pendingNow;
                            if ((bytesConsumed += bytesWritten) == 16384 || (bytesProduced += bioLengthBefore - pendingNow) == dst.remaining()) {
                                SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(status, bytesConsumed, bytesProduced);
                                return sSLEngineResult;
                            }
                        } else {
                            int sslError = SSL.getError(this.ssl, bytesWritten);
                            if (sslError == SSL.SSL_ERROR_ZERO_RETURN) {
                                if (!this.receivedShutdown) {
                                    this.closeAll();
                                    SSLEngineResult.HandshakeStatus hs = this.mayFinishHandshake(status != SSLEngineResult.HandshakeStatus.FINISHED ? this.getHandshakeStatus(SSL.bioLengthNonApplication(this.networkBIO)) : SSLEngineResult.HandshakeStatus.FINISHED);
                                    SSLEngineResult sSLEngineResult = this.newResult(hs, bytesConsumed, bytesProduced += bioLengthBefore - SSL.bioLengthByteBuffer(this.networkBIO));
                                    return sSLEngineResult;
                                }
                                SSLEngineResult sSLEngineResult = this.newResult(SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING, bytesConsumed, bytesProduced);
                                return sSLEngineResult;
                            }
                            if (sslError == SSL.SSL_ERROR_WANT_READ) {
                                SSLEngineResult sSLEngineResult = this.newResult(SSLEngineResult.HandshakeStatus.NEED_UNWRAP, bytesConsumed, bytesProduced);
                                return sSLEngineResult;
                            }
                            if (sslError != SSL.SSL_ERROR_WANT_WRITE) {
                                throw this.shutdownWithError("SSL_write");
                            }
                            SSLEngineResult sSLEngineResult = this.newResult(SSLEngineResult.HandshakeStatus.NEED_WRAP, bytesConsumed, bytesProduced);
                            return sSLEngineResult;
                        }
                    }
                    ++offset;
                }
                SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(status, bytesConsumed, bytesProduced);
                return sSLEngineResult;
            } finally {
                SSL.bioClearByteBuffer(this.networkBIO);
                if (bioReadCopyBuf == null) {
                    dst.position(dst.position() + bytesProduced);
                } else {
                    assert (bioReadCopyBuf.readableBytes() <= dst.remaining()) : "The destination buffer " + dst + " didn't have enough remaining space to hold the encrypted content in " + bioReadCopyBuf;
                    dst.put(bioReadCopyBuf.internalNioBuffer(bioReadCopyBuf.readerIndex(), bytesProduced));
                    bioReadCopyBuf.release();
                }
            }
        }
    }

    private SSLEngineResult newResult(SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
        return this.newResult(SSLEngineResult.Status.OK, hs, bytesConsumed, bytesProduced);
    }

    private SSLEngineResult newResult(SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) {
        if (this.isOutboundDone()) {
            if (this.isInboundDone()) {
                hs = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
                this.shutdown();
            }
            return new SSLEngineResult(SSLEngineResult.Status.CLOSED, hs, bytesConsumed, bytesProduced);
        }
        return new SSLEngineResult(status, hs, bytesConsumed, bytesProduced);
    }

    private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) throws SSLException {
        return this.newResult(this.mayFinishHandshake(hs != SSLEngineResult.HandshakeStatus.FINISHED ? this.getHandshakeStatus() : SSLEngineResult.HandshakeStatus.FINISHED), bytesConsumed, bytesProduced);
    }

    private SSLEngineResult newResultMayFinishHandshake(SSLEngineResult.Status status, SSLEngineResult.HandshakeStatus hs, int bytesConsumed, int bytesProduced) throws SSLException {
        return this.newResult(status, this.mayFinishHandshake(hs != SSLEngineResult.HandshakeStatus.FINISHED ? this.getHandshakeStatus() : SSLEngineResult.HandshakeStatus.FINISHED), bytesConsumed, bytesProduced);
    }

    private SSLException shutdownWithError(String operations) {
        String err = SSL.getLastError();
        return this.shutdownWithError(operations, err);
    }

    private SSLException shutdownWithError(String operation, String err) {
        if (logger.isDebugEnabled()) {
            logger.debug("{} failed: OpenSSL error: {}", (Object)operation, (Object)err);
        }
        this.shutdown();
        if (this.handshakeState == HandshakeState.FINISHED) {
            return new SSLException(err);
        }
        return new SSLHandshakeException(err);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final SSLEngineResult unwrap(ByteBuffer[] srcs, int srcsOffset, int srcsLength, ByteBuffer[] dsts, int dstsOffset, int dstsLength) throws SSLException {
        ByteBuffer src;
        ByteBuffer dst;
        if (srcs == null) {
            throw new NullPointerException("srcs");
        }
        if (srcsOffset >= srcs.length || srcsOffset + srcsLength > srcs.length) {
            throw new IndexOutOfBoundsException("offset: " + srcsOffset + ", length: " + srcsLength + " (expected: offset <= offset + length <= srcs.length (" + srcs.length + "))");
        }
        if (dsts == null) {
            throw new IllegalArgumentException("dsts is null");
        }
        if (dstsOffset >= dsts.length || dstsOffset + dstsLength > dsts.length) {
            throw new IndexOutOfBoundsException("offset: " + dstsOffset + ", length: " + dstsLength + " (expected: offset <= offset + length <= dsts.length (" + dsts.length + "))");
        }
        long capacity = 0L;
        int dstsEndOffset = dstsOffset + dstsLength;
        for (int i = dstsOffset; i < dstsEndOffset; capacity += (long)dst.remaining(), ++i) {
            dst = dsts[i];
            if (dst == null) {
                throw new IllegalArgumentException("dsts[" + i + "] is null");
            }
            if (!dst.isReadOnly()) continue;
            throw new ReadOnlyBufferException();
        }
        int srcsEndOffset = srcsOffset + srcsLength;
        long len = 0L;
        for (int i = srcsOffset; i < srcsEndOffset; len += (long)src.remaining(), ++i) {
            src = srcs[i];
            if (src != null) continue;
            throw new IllegalArgumentException("srcs[" + i + "] is null");
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (this.isInboundDone()) {
                return this.isOutboundDone() || this.isDestroyed() ? CLOSED_NOT_HANDSHAKING : NEED_WRAP_CLOSED;
            }
            SSLEngineResult.HandshakeStatus status = SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
            if (this.handshakeState != HandshakeState.FINISHED) {
                if (this.handshakeState != HandshakeState.STARTED_EXPLICITLY) {
                    this.handshakeState = HandshakeState.STARTED_IMPLICITLY;
                }
                if ((status = this.handshake()) == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
                    return NEED_WRAP_OK;
                }
                if (this.isInboundDone) {
                    return NEED_WRAP_CLOSED;
                }
            }
            if (len < 5L) {
                return this.newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, status, 0, 0);
            }
            int packetLength = SslUtils.getEncryptedPacketLength(srcs, srcsOffset);
            if (packetLength == -2) {
                throw new NotSslRecordException("not an SSL/TLS record");
            }
            if ((long)(packetLength - 5) > capacity) {
                return this.newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_OVERFLOW, status, 0, 0);
            }
            if (len < (long)packetLength) {
                return this.newResultMayFinishHandshake(SSLEngineResult.Status.BUFFER_UNDERFLOW, status, 0, 0);
            }
            assert (srcsOffset < srcsEndOffset);
            assert (capacity > 0L);
            int bytesProduced = 0;
            int bytesConsumed = 0;
            try {
                while (srcsOffset < srcsEndOffset) {
                    block43: {
                        ByteBuffer src2 = srcs[srcsOffset];
                        int remaining = src2.remaining();
                        if (remaining != 0) {
                            int pendingEncryptedBytes = Math.min(packetLength, remaining);
                            ByteBuf bioWriteCopyBuf = this.writeEncryptedData(src2, pendingEncryptedBytes);
                            try {
                                while (dstsOffset < dstsEndOffset) {
                                    ByteBuffer dst2 = dsts[dstsOffset];
                                    if (dst2.hasRemaining()) {
                                        int bytesRead = this.readPlaintextData(dst2);
                                        int localBytesConsumed = pendingEncryptedBytes - SSL.bioLengthByteBuffer(this.networkBIO);
                                        bytesConsumed += localBytesConsumed;
                                        packetLength -= localBytesConsumed;
                                        pendingEncryptedBytes -= localBytesConsumed;
                                        src2.position(src2.position() + localBytesConsumed);
                                        if (bytesRead > 0) {
                                            bytesProduced += bytesRead;
                                            if (dst2.hasRemaining()) {
                                                if (packetLength != 0) break;
                                                SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(this.isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, status, bytesConsumed, bytesProduced);
                                                return sSLEngineResult;
                                            }
                                        } else {
                                            int sslError = SSL.getError(this.ssl, bytesRead);
                                            if (sslError == SSL.SSL_ERROR_WANT_READ || sslError == SSL.SSL_ERROR_WANT_WRITE) break;
                                            if (sslError == SSL.SSL_ERROR_ZERO_RETURN) {
                                                if (!this.receivedShutdown) {
                                                    this.closeAll();
                                                }
                                                SSLEngineResult sSLEngineResult = this.newResultMayFinishHandshake(this.isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, status, bytesConsumed, bytesProduced);
                                                return sSLEngineResult;
                                            }
                                            SSLEngineResult sSLEngineResult = this.sslReadErrorResult(SSL.getLastErrorNumber(), bytesConsumed, bytesProduced);
                                            return sSLEngineResult;
                                        }
                                    }
                                    ++dstsOffset;
                                }
                                if (dstsOffset < dstsEndOffset && packetLength != 0) break block43;
                                break;
                            } finally {
                                if (bioWriteCopyBuf != null) {
                                    bioWriteCopyBuf.release();
                                }
                            }
                        }
                    }
                    ++srcsOffset;
                }
            } finally {
                SSL.bioClearByteBuffer(this.networkBIO);
                this.rejectRemoteInitiatedRenegation();
            }
            if (!this.receivedShutdown && (SSL.getShutdown(this.ssl) & SSL.SSL_RECEIVED_SHUTDOWN) == SSL.SSL_RECEIVED_SHUTDOWN) {
                this.closeAll();
            }
            return this.newResultMayFinishHandshake(this.isInboundDone() ? SSLEngineResult.Status.CLOSED : SSLEngineResult.Status.OK, status, bytesConsumed, bytesProduced);
        }
    }

    private SSLEngineResult sslReadErrorResult(int err, int bytesConsumed, int bytesProduced) throws SSLException {
        String errStr = SSL.getErrorString(err);
        if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
            if (this.handshakeException == null && this.handshakeState != HandshakeState.FINISHED) {
                this.handshakeException = new SSLHandshakeException(errStr);
            }
            return new SSLEngineResult(SSLEngineResult.Status.OK, SSLEngineResult.HandshakeStatus.NEED_WRAP, bytesConsumed, bytesProduced);
        }
        throw this.shutdownWithError("SSL_read", errStr);
    }

    private void closeAll() throws SSLException {
        this.receivedShutdown = true;
        this.closeOutbound();
        this.closeInbound();
    }

    private void rejectRemoteInitiatedRenegation() throws SSLHandshakeException {
        if (this.rejectRemoteInitiatedRenegation && SSL.getHandshakeCount(this.ssl) > 1) {
            this.shutdown();
            throw new SSLHandshakeException("remote-initiated renegotation not allowed");
        }
    }

    public final SSLEngineResult unwrap(ByteBuffer[] srcs, ByteBuffer[] dsts) throws SSLException {
        return this.unwrap(srcs, 0, srcs.length, dsts, 0, dsts.length);
    }

    private ByteBuffer[] singleSrcBuffer(ByteBuffer src) {
        this.singleSrcBuffer[0] = src;
        return this.singleSrcBuffer;
    }

    private void resetSingleSrcBuffer() {
        this.singleSrcBuffer[0] = null;
    }

    private ByteBuffer[] singleDstBuffer(ByteBuffer src) {
        this.singleDstBuffer[0] = src;
        return this.singleDstBuffer;
    }

    private void resetSingleDstBuffer() {
        this.singleDstBuffer[0] = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) throws SSLException {
        try {
            SSLEngineResult sSLEngineResult = this.unwrap(this.singleSrcBuffer(src), 0, 1, dsts, offset, length);
            return sSLEngineResult;
        } finally {
            this.resetSingleSrcBuffer();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final synchronized SSLEngineResult wrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
        try {
            SSLEngineResult sSLEngineResult = this.wrap(this.singleSrcBuffer(src), dst);
            return sSLEngineResult;
        } finally {
            this.resetSingleSrcBuffer();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
        try {
            SSLEngineResult sSLEngineResult = this.unwrap(this.singleSrcBuffer(src), this.singleDstBuffer(dst));
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
    public final synchronized SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts) throws SSLException {
        try {
            SSLEngineResult sSLEngineResult = this.unwrap(this.singleSrcBuffer(src), dsts);
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
            int mode = SSL.getShutdown(this.ssl);
            if ((mode & SSL.SSL_SENT_SHUTDOWN) != SSL.SSL_SENT_SHUTDOWN) {
                this.doSSLShutdown();
            }
        } else {
            this.shutdown();
        }
    }

    private boolean doSSLShutdown() {
        if (SSL.isInInit(this.ssl) != 0) {
            return false;
        }
        int err = SSL.shutdownSSL(this.ssl);
        if (err < 0) {
            int sslErr = SSL.getError(this.ssl, err);
            if (sslErr == SSL.SSL_ERROR_SYSCALL || sslErr == SSL.SSL_ERROR_SSL) {
                if (logger.isDebugEnabled()) {
                    logger.debug("SSL_shutdown failed: OpenSSL error: {}", (Object)SSL.getLastError());
                }
                this.shutdown();
                return false;
            }
            SSL.clearError();
        }
        return true;
    }

    @Override
    public final synchronized boolean isOutboundDone() {
        return this.outboundClosed && (this.networkBIO == 0L || SSL.bioLengthNonApplication(this.networkBIO) == 0);
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
        String[] enabled;
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (this.isDestroyed()) {
                return EmptyArrays.EMPTY_STRINGS;
            }
            enabled = SSL.getCiphers(this.ssl);
        }
        if (enabled == null) {
            return EmptyArrays.EMPTY_STRINGS;
        }
        referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            for (int i = 0; i < enabled.length; ++i) {
                String mapped = this.toJavaCipherSuite(enabled[i]);
                if (mapped == null) continue;
                enabled[i] = mapped;
            }
        }
        return enabled;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void setEnabledCipherSuites(String[] cipherSuites) {
        ObjectUtil.checkNotNull(cipherSuites, "cipherSuites");
        StringBuilder buf = new StringBuilder();
        for (String c : cipherSuites) {
            if (c == null) break;
            String converted = CipherSuiteConverter.toOpenSsl(c);
            if (converted == null) {
                converted = c;
            }
            if (!OpenSsl.isCipherSuiteAvailable(converted)) {
                throw new IllegalArgumentException("unsupported cipher suite: " + c + '(' + converted + ')');
            }
            buf.append(converted);
            buf.append(':');
        }
        if (buf.length() == 0) {
            throw new IllegalArgumentException("empty cipher suites");
        }
        buf.setLength(buf.length() - 1);
        String cipherSuiteSpec = buf.toString();
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (!this.isDestroyed()) {
                try {
                    SSL.setCipherSuites(this.ssl, cipherSuiteSpec);
                } catch (Exception e) {
                    throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec, e);
                }
            } else {
                throw new IllegalStateException("failed to enable cipher suites: " + cipherSuiteSpec);
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
        int opts;
        ArrayList<String> enabled = new ArrayList<String>(6);
        enabled.add("SSLv2Hello");
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (this.isDestroyed()) {
                return enabled.toArray(new String[1]);
            }
            opts = SSL.getOptions(this.ssl);
        }
        if (ReferenceCountedOpenSslEngine.isProtocolEnabled(opts, SSL.SSL_OP_NO_TLSv1, "TLSv1")) {
            enabled.add("TLSv1");
        }
        if (ReferenceCountedOpenSslEngine.isProtocolEnabled(opts, SSL.SSL_OP_NO_TLSv1_1, "TLSv1.1")) {
            enabled.add("TLSv1.1");
        }
        if (ReferenceCountedOpenSslEngine.isProtocolEnabled(opts, SSL.SSL_OP_NO_TLSv1_2, "TLSv1.2")) {
            enabled.add("TLSv1.2");
        }
        if (ReferenceCountedOpenSslEngine.isProtocolEnabled(opts, SSL.SSL_OP_NO_SSLv2, "SSLv2")) {
            enabled.add("SSLv2");
        }
        if (ReferenceCountedOpenSslEngine.isProtocolEnabled(opts, SSL.SSL_OP_NO_SSLv3, "SSLv3")) {
            enabled.add("SSLv3");
        }
        return enabled.toArray(new String[enabled.size()]);
    }

    private static boolean isProtocolEnabled(int opts, int disableMask, String protocolString) {
        return (opts & disableMask) == 0 && OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(protocolString);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void setEnabledProtocols(String[] protocols) {
        if (protocols == null) {
            throw new IllegalArgumentException();
        }
        boolean sslv2 = false;
        boolean sslv3 = false;
        boolean tlsv1 = false;
        boolean tlsv1_1 = false;
        boolean tlsv1_2 = false;
        for (String p : protocols) {
            if (!OpenSsl.SUPPORTED_PROTOCOLS_SET.contains(p)) {
                throw new IllegalArgumentException("Protocol " + p + " is not supported.");
            }
            if (p.equals("SSLv2")) {
                sslv2 = true;
                continue;
            }
            if (p.equals("SSLv3")) {
                sslv3 = true;
                continue;
            }
            if (p.equals("TLSv1")) {
                tlsv1 = true;
                continue;
            }
            if (p.equals("TLSv1.1")) {
                tlsv1_1 = true;
                continue;
            }
            if (!p.equals("TLSv1.2")) continue;
            tlsv1_2 = true;
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            int opts;
            if (!this.isDestroyed()) {
                SSL.clearOptions(this.ssl, SSL.SSL_OP_NO_SSLv2 | SSL.SSL_OP_NO_SSLv3 | SSL.SSL_OP_NO_TLSv1 | SSL.SSL_OP_NO_TLSv1_1 | SSL.SSL_OP_NO_TLSv1_2);
                opts = 0;
                if (!sslv2) {
                    opts |= SSL.SSL_OP_NO_SSLv2;
                }
                if (!sslv3) {
                    opts |= SSL.SSL_OP_NO_SSLv3;
                }
                if (!tlsv1) {
                    opts |= SSL.SSL_OP_NO_TLSv1;
                }
                if (!tlsv1_1) {
                    opts |= SSL.SSL_OP_NO_TLSv1_1;
                }
                if (!tlsv1_2) {
                    opts |= SSL.SSL_OP_NO_TLSv1_2;
                }
            } else {
                throw new IllegalStateException("failed to enable protocols: " + Arrays.asList(protocols));
            }
            SSL.setOptions(this.ssl, opts);
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
                break;
            }
            case STARTED_EXPLICITLY: {
                break;
            }
            case FINISHED: {
                if (this.clientMode) {
                    throw RENEGOTIATION_UNSUPPORTED;
                }
                int status = SSL.renegotiate(this.ssl);
                if (status != 1 || (status = SSL.doHandshake(this.ssl)) != 1) {
                    int err = SSL.getError(this.ssl, status);
                    if (err == SSL.SSL_ERROR_WANT_READ || err == SSL.SSL_ERROR_WANT_WRITE) {
                        this.renegotiationPending = true;
                        this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
                        this.lastAccessed = System.currentTimeMillis();
                        return;
                    }
                    throw this.shutdownWithError("renegotiation failed");
                }
                SSL.setState(this.ssl, SSL.SSL_ST_ACCEPT);
                this.lastAccessed = System.currentTimeMillis();
            }
            case NOT_STARTED: {
                this.handshakeState = HandshakeState.STARTED_EXPLICITLY;
                this.handshake();
                break;
            }
            default: {
                throw new Error();
            }
        }
    }

    private void checkEngineClosed(SSLException cause) throws SSLException {
        if (this.isDestroyed()) {
            throw cause;
        }
    }

    private static SSLEngineResult.HandshakeStatus pendingStatus(int pendingStatus) {
        return pendingStatus > 0 ? SSLEngineResult.HandshakeStatus.NEED_WRAP : SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
    }

    private static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    private static boolean isEmpty(byte[] cert) {
        return cert == null || cert.length == 0;
    }

    private SSLEngineResult.HandshakeStatus handshake() throws SSLException {
        int code;
        if (this.handshakeState == HandshakeState.FINISHED) {
            return SSLEngineResult.HandshakeStatus.FINISHED;
        }
        this.checkEngineClosed(HANDSHAKE_ENGINE_CLOSED);
        SSLHandshakeException exception = this.handshakeException;
        if (exception != null) {
            if (SSL.bioLengthNonApplication(this.networkBIO) > 0) {
                return SSLEngineResult.HandshakeStatus.NEED_WRAP;
            }
            this.handshakeException = null;
            this.shutdown();
            throw exception;
        }
        this.engineMap.add(this);
        if (this.lastAccessed == -1L) {
            this.lastAccessed = System.currentTimeMillis();
        }
        if (!this.certificateSet && this.keyMaterialManager != null) {
            this.certificateSet = true;
            this.keyMaterialManager.setKeyMaterial(this);
        }
        if ((code = SSL.doHandshake(this.ssl)) <= 0) {
            if (this.handshakeException != null) {
                exception = this.handshakeException;
                this.handshakeException = null;
                this.shutdown();
                throw exception;
            }
            int sslError = SSL.getError(this.ssl, code);
            if (sslError == SSL.SSL_ERROR_WANT_READ || sslError == SSL.SSL_ERROR_WANT_WRITE) {
                return ReferenceCountedOpenSslEngine.pendingStatus(SSL.bioLengthNonApplication(this.networkBIO));
            }
            throw this.shutdownWithError("SSL_do_handshake");
        }
        this.session.handshakeFinished();
        this.engineMap.remove(this.ssl);
        return SSLEngineResult.HandshakeStatus.FINISHED;
    }

    private SSLEngineResult.HandshakeStatus mayFinishHandshake(SSLEngineResult.HandshakeStatus status) throws SSLException {
        if (status == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING && this.handshakeState != HandshakeState.FINISHED) {
            return this.handshake();
        }
        return status;
    }

    @Override
    public final synchronized SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return this.needPendingStatus() ? ReferenceCountedOpenSslEngine.pendingStatus(SSL.bioLengthNonApplication(this.networkBIO)) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private SSLEngineResult.HandshakeStatus getHandshakeStatus(int pending) {
        return this.needPendingStatus() ? ReferenceCountedOpenSslEngine.pendingStatus(pending) : SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING;
    }

    private boolean needPendingStatus() {
        return this.handshakeState != HandshakeState.NOT_STARTED && !this.isDestroyed() && (this.handshakeState != HandshakeState.FINISHED || this.isInboundDone() || this.isOutboundDone());
    }

    private String toJavaCipherSuite(String openSslCipherSuite) {
        if (openSslCipherSuite == null) {
            return null;
        }
        String prefix = ReferenceCountedOpenSslEngine.toJavaCipherSuitePrefix(SSL.getVersion(this.ssl));
        return CipherSuiteConverter.toJava(openSslCipherSuite, prefix);
    }

    private static String toJavaCipherSuitePrefix(String protocolVersion) {
        int c = protocolVersion == null || protocolVersion.isEmpty() ? 0 : (int)protocolVersion.charAt(0);
        switch (c) {
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
    public final void setUseClientMode(boolean clientMode) {
        if (clientMode != this.clientMode) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public final boolean getUseClientMode() {
        return this.clientMode;
    }

    @Override
    public final void setNeedClientAuth(boolean b) {
        this.setClientAuth(b ? ClientAuth.REQUIRE : ClientAuth.NONE);
    }

    @Override
    public final boolean getNeedClientAuth() {
        return this.clientAuth == ClientAuth.REQUIRE;
    }

    @Override
    public final void setWantClientAuth(boolean b) {
        this.setClientAuth(b ? ClientAuth.OPTIONAL : ClientAuth.NONE);
    }

    @Override
    public final boolean getWantClientAuth() {
        return this.clientAuth == ClientAuth.OPTIONAL;
    }

    public final synchronized void setVerify(int verifyMode, int depth) {
        SSL.setVerify(this.ssl, verifyMode, depth);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setClientAuth(ClientAuth mode) {
        if (this.clientMode) {
            return;
        }
        ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = this;
        synchronized (referenceCountedOpenSslEngine) {
            if (this.clientAuth == mode) {
                return;
            }
            switch (mode) {
                case NONE: {
                    SSL.setVerify(this.ssl, 0, 10);
                    break;
                }
                case REQUIRE: {
                    SSL.setVerify(this.ssl, 2, 10);
                    break;
                }
                case OPTIONAL: {
                    SSL.setVerify(this.ssl, 1, 10);
                    break;
                }
                default: {
                    throw new Error(mode.toString());
                }
            }
            this.clientAuth = mode;
        }
    }

    @Override
    public final void setEnableSessionCreation(boolean b) {
        if (b) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public final boolean getEnableSessionCreation() {
        return false;
    }

    @Override
    public final synchronized SSLParameters getSSLParameters() {
        SSLParameters sslParameters = super.getSSLParameters();
        int version = PlatformDependent.javaVersion();
        if (version >= 7) {
            sslParameters.setEndpointIdentificationAlgorithm(this.endPointIdentificationAlgorithm);
            Java7SslParametersUtils.setAlgorithmConstraints(sslParameters, this.algorithmConstraints);
            if (version >= 8) {
                if (this.sniHostNames != null) {
                    Java8SslParametersUtils.setSniHostNames(sslParameters, this.sniHostNames);
                }
                if (!this.isDestroyed()) {
                    Java8SslParametersUtils.setUseCipherSuitesOrder(sslParameters, (SSL.getOptions(this.ssl) & SSL.SSL_OP_CIPHER_SERVER_PREFERENCE) != 0);
                }
            }
        }
        return sslParameters;
    }

    @Override
    public final synchronized void setSSLParameters(SSLParameters sslParameters) {
        int version = PlatformDependent.javaVersion();
        if (version >= 7) {
            String endPointIdentificationAlgorithm;
            if (sslParameters.getAlgorithmConstraints() != null) {
                throw new IllegalArgumentException("AlgorithmConstraints are not supported.");
            }
            if (version >= 8) {
                Collection<SNIMatcher> matchers = sslParameters.getSNIMatchers();
                if (matchers != null && !matchers.isEmpty()) {
                    throw new IllegalArgumentException("SNIMatchers are not supported.");
                }
                if (!this.isDestroyed()) {
                    if (this.clientMode) {
                        List<String> sniHostNames = Java8SslParametersUtils.getSniHostNames(sslParameters);
                        for (String name : sniHostNames) {
                            SSL.setTlsExtHostName(this.ssl, name);
                        }
                        this.sniHostNames = sniHostNames;
                    }
                    if (Java8SslParametersUtils.getUseCipherSuitesOrder(sslParameters)) {
                        SSL.setOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                    } else {
                        SSL.clearOptions(this.ssl, SSL.SSL_OP_CIPHER_SERVER_PREFERENCE);
                    }
                }
            }
            boolean endPointVerificationEnabled = (endPointIdentificationAlgorithm = sslParameters.getEndpointIdentificationAlgorithm()) != null && !endPointIdentificationAlgorithm.isEmpty();
            SSL.setHostNameValidation(this.ssl, 0, endPointVerificationEnabled ? this.getPeerHost() : null);
            if (this.clientMode && endPointVerificationEnabled) {
                SSL.setVerify(this.ssl, 2, -1);
            }
            this.endPointIdentificationAlgorithm = endPointIdentificationAlgorithm;
            this.algorithmConstraints = sslParameters.getAlgorithmConstraints();
        }
        super.setSSLParameters(sslParameters);
    }

    private boolean isDestroyed() {
        return this.destroyed != 0;
    }

    static int calculateOutNetBufSize(int pendingBytes, int numComponents) {
        return (int)Math.min(Integer.MAX_VALUE, (long)pendingBytes + 90L * (long)numComponents);
    }

    private final class OpenSslSession
    implements SSLSession,
    ApplicationProtocolAccessor {
        private final OpenSslSessionContext sessionContext;
        private X509Certificate[] x509PeerCerts;
        private Certificate[] peerCerts;
        private String protocol;
        private String applicationProtocol;
        private String cipher;
        private byte[] id;
        private long creationTime;
        private Map<String, Object> values;

        OpenSslSession(OpenSslSessionContext sessionContext) {
            this.sessionContext = sessionContext;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public byte[] getId() {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
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
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
            synchronized (referenceCountedOpenSslEngine) {
                if (this.creationTime == 0L && !ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    this.creationTime = SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000L;
                }
            }
            return this.creationTime;
        }

        @Override
        public long getLastAccessedTime() {
            long lastAccessed = ReferenceCountedOpenSslEngine.this.lastAccessed;
            return lastAccessed == -1L ? this.getCreationTime() : lastAccessed;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void invalidate() {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
            synchronized (referenceCountedOpenSslEngine) {
                if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    SSL.setTimeout(ReferenceCountedOpenSslEngine.this.ssl, 0L);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean isValid() {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
            synchronized (referenceCountedOpenSslEngine) {
                if (!ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    return System.currentTimeMillis() - SSL.getTimeout(ReferenceCountedOpenSslEngine.this.ssl) * 1000L < SSL.getTime(ReferenceCountedOpenSslEngine.this.ssl) * 1000L;
                }
            }
            return false;
        }

        @Override
        public void putValue(String name, Object value) {
            if (name == null) {
                throw new NullPointerException("name");
            }
            if (value == null) {
                throw new NullPointerException("value");
            }
            Map<String, Object> values = this.values;
            if (values == null) {
                values = this.values = new HashMap<String, Object>(2);
            }
            Object old = values.put(name, value);
            if (value instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener)value).valueBound(new SSLSessionBindingEvent(this, name));
            }
            this.notifyUnbound(old, name);
        }

        @Override
        public Object getValue(String name) {
            if (name == null) {
                throw new NullPointerException("name");
            }
            if (this.values == null) {
                return null;
            }
            return this.values.get(name);
        }

        @Override
        public void removeValue(String name) {
            if (name == null) {
                throw new NullPointerException("name");
            }
            Map<String, Object> values = this.values;
            if (values == null) {
                return;
            }
            Object old = values.remove(name);
            this.notifyUnbound(old, name);
        }

        @Override
        public String[] getValueNames() {
            Map<String, Object> values = this.values;
            if (values == null || values.isEmpty()) {
                return EmptyArrays.EMPTY_STRINGS;
            }
            return values.keySet().toArray(new String[values.size()]);
        }

        private void notifyUnbound(Object value, String name) {
            if (value instanceof SSLSessionBindingListener) {
                ((SSLSessionBindingListener)value).valueUnbound(new SSLSessionBindingEvent(this, name));
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void handshakeFinished() throws SSLException {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
            synchronized (referenceCountedOpenSslEngine) {
                if (ReferenceCountedOpenSslEngine.this.isDestroyed()) {
                    throw new SSLException("Already closed");
                }
                this.id = SSL.getSessionId(ReferenceCountedOpenSslEngine.this.ssl);
                this.cipher = ReferenceCountedOpenSslEngine.this.toJavaCipherSuite(SSL.getCipherForSSL(ReferenceCountedOpenSslEngine.this.ssl));
                this.protocol = SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl);
                this.initPeerCerts();
                this.selectApplicationProtocol();
                ReferenceCountedOpenSslEngine.this.handshakeState = HandshakeState.FINISHED;
            }
        }

        private void initPeerCerts() {
            byte[][] chain = SSL.getPeerCertChain(ReferenceCountedOpenSslEngine.this.ssl);
            if (ReferenceCountedOpenSslEngine.this.clientMode) {
                if (ReferenceCountedOpenSslEngine.isEmpty((Object[])chain)) {
                    this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
                    this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
                } else {
                    this.peerCerts = new Certificate[chain.length];
                    this.x509PeerCerts = new X509Certificate[chain.length];
                    this.initCerts(chain, 0);
                }
            } else {
                byte[] clientCert = SSL.getPeerCertificate(ReferenceCountedOpenSslEngine.this.ssl);
                if (ReferenceCountedOpenSslEngine.isEmpty(clientCert)) {
                    this.peerCerts = EmptyArrays.EMPTY_CERTIFICATES;
                    this.x509PeerCerts = EmptyArrays.EMPTY_JAVAX_X509_CERTIFICATES;
                } else if (ReferenceCountedOpenSslEngine.isEmpty((Object[])chain)) {
                    this.peerCerts = new Certificate[]{new OpenSslX509Certificate(clientCert)};
                    this.x509PeerCerts = new X509Certificate[]{new OpenSslJavaxX509Certificate(clientCert)};
                } else {
                    this.peerCerts = new Certificate[chain.length + 1];
                    this.x509PeerCerts = new X509Certificate[chain.length + 1];
                    this.peerCerts[0] = new OpenSslX509Certificate(clientCert);
                    this.x509PeerCerts[0] = new OpenSslJavaxX509Certificate(clientCert);
                    this.initCerts(chain, 1);
                }
            }
        }

        private void initCerts(byte[][] chain, int startPos) {
            for (int i = 0; i < chain.length; ++i) {
                int certPos = startPos + i;
                this.peerCerts[certPos] = new OpenSslX509Certificate(chain[i]);
                this.x509PeerCerts[certPos] = new OpenSslJavaxX509Certificate(chain[i]);
            }
        }

        private void selectApplicationProtocol() throws SSLException {
            ApplicationProtocolConfig.SelectedListenerFailureBehavior behavior = ReferenceCountedOpenSslEngine.this.apn.selectedListenerFailureBehavior();
            List<String> protocols = ReferenceCountedOpenSslEngine.this.apn.protocols();
            switch (ReferenceCountedOpenSslEngine.this.apn.protocol()) {
                case NONE: {
                    break;
                }
                case ALPN: {
                    String applicationProtocol = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
                    if (applicationProtocol == null) break;
                    this.applicationProtocol = this.selectApplicationProtocol(protocols, behavior, applicationProtocol);
                    break;
                }
                case NPN: {
                    String applicationProtocol = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
                    if (applicationProtocol == null) break;
                    this.applicationProtocol = this.selectApplicationProtocol(protocols, behavior, applicationProtocol);
                    break;
                }
                case NPN_AND_ALPN: {
                    String applicationProtocol = SSL.getAlpnSelected(ReferenceCountedOpenSslEngine.this.ssl);
                    if (applicationProtocol == null) {
                        applicationProtocol = SSL.getNextProtoNegotiated(ReferenceCountedOpenSslEngine.this.ssl);
                    }
                    if (applicationProtocol == null) break;
                    this.applicationProtocol = this.selectApplicationProtocol(protocols, behavior, applicationProtocol);
                    break;
                }
                default: {
                    throw new Error();
                }
            }
        }

        private String selectApplicationProtocol(List<String> protocols, ApplicationProtocolConfig.SelectedListenerFailureBehavior behavior, String applicationProtocol) throws SSLException {
            if (behavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.ACCEPT) {
                return applicationProtocol;
            }
            int size = protocols.size();
            assert (size > 0);
            if (protocols.contains(applicationProtocol)) {
                return applicationProtocol;
            }
            if (behavior == ApplicationProtocolConfig.SelectedListenerFailureBehavior.CHOOSE_MY_LAST_PROTOCOL) {
                return protocols.get(size - 1);
            }
            throw new SSLException("unknown protocol " + applicationProtocol);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
            synchronized (referenceCountedOpenSslEngine) {
                if (ReferenceCountedOpenSslEngine.isEmpty(this.peerCerts)) {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
                return (Certificate[])this.peerCerts.clone();
            }
        }

        @Override
        public Certificate[] getLocalCertificates() {
            if (ReferenceCountedOpenSslEngine.this.localCerts == null) {
                return null;
            }
            return (Certificate[])ReferenceCountedOpenSslEngine.this.localCerts.clone();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
            synchronized (referenceCountedOpenSslEngine) {
                if (ReferenceCountedOpenSslEngine.isEmpty(this.x509PeerCerts)) {
                    throw new SSLPeerUnverifiedException("peer not verified");
                }
                return (X509Certificate[])this.x509PeerCerts.clone();
            }
        }

        @Override
        public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
            Certificate[] peer = this.getPeerCertificates();
            return ((java.security.cert.X509Certificate)peer[0]).getSubjectX500Principal();
        }

        @Override
        public Principal getLocalPrincipal() {
            Certificate[] local = ReferenceCountedOpenSslEngine.this.localCerts;
            if (local == null || local.length == 0) {
                return null;
            }
            return ((java.security.cert.X509Certificate)local[0]).getIssuerX500Principal();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public String getCipherSuite() {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
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
            String protocol = this.protocol;
            if (protocol == null) {
                ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
                synchronized (referenceCountedOpenSslEngine) {
                    protocol = !ReferenceCountedOpenSslEngine.this.isDestroyed() ? SSL.getVersion(ReferenceCountedOpenSslEngine.this.ssl) : "";
                }
            }
            return protocol;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public String getApplicationProtocol() {
            ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = ReferenceCountedOpenSslEngine.this;
            synchronized (referenceCountedOpenSslEngine) {
                return this.applicationProtocol;
            }
        }

        @Override
        public String getPeerHost() {
            return ReferenceCountedOpenSslEngine.this.getPeerHost();
        }

        @Override
        public int getPeerPort() {
            return ReferenceCountedOpenSslEngine.this.getPeerPort();
        }

        @Override
        public int getPacketBufferSize() {
            return 16474;
        }

        @Override
        public int getApplicationBufferSize() {
            return 16384;
        }
    }

    private static enum HandshakeState {
        NOT_STARTED,
        STARTED_IMPLICITLY,
        STARTED_EXPLICITLY,
        FINISHED;

    }
}

