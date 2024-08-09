/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractCoalescingBufferQueue;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.handler.ssl.ApplicationProtocolAccessor;
import io.netty.handler.ssl.ConscryptAlpnSslEngine;
import io.netty.handler.ssl.NotSslRecordException;
import io.netty.handler.ssl.ReferenceCountedOpenSslEngine;
import io.netty.handler.ssl.SslCloseCompletionEvent;
import io.netty.handler.ssl.SslHandshakeCompletionEvent;
import io.netty.handler.ssl.SslUtils;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ImmediateExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ThrowableUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public class SslHandler
extends ByteToMessageDecoder
implements ChannelOutboundHandler {
    private static final InternalLogger logger;
    private static final Pattern IGNORABLE_CLASS_IN_STACK;
    private static final Pattern IGNORABLE_ERROR_MESSAGE;
    private static final SSLException SSLENGINE_CLOSED;
    private static final SSLException HANDSHAKE_TIMED_OUT;
    private static final ClosedChannelException CHANNEL_CLOSED;
    private static final int MAX_PLAINTEXT_LENGTH = 16384;
    private volatile ChannelHandlerContext ctx;
    private final SSLEngine engine;
    private final SslEngineType engineType;
    private final Executor delegatedTaskExecutor;
    private final boolean jdkCompatibilityMode;
    private final ByteBuffer[] singleBuffer = new ByteBuffer[1];
    private final boolean startTls;
    private boolean sentFirstMessage;
    private boolean flushedBeforeHandshake;
    private boolean readDuringHandshake;
    private boolean handshakeStarted;
    private SslHandlerCoalescingBufferQueue pendingUnencryptedWrites;
    private Promise<Channel> handshakePromise = new LazyChannelPromise(this, null);
    private final LazyChannelPromise sslClosePromise = new LazyChannelPromise(this, null);
    private boolean needsFlush;
    private boolean outboundClosed;
    private boolean closeNotify;
    private int packetLength;
    private boolean firedChannelRead;
    private volatile long handshakeTimeoutMillis = 10000L;
    private volatile long closeNotifyFlushTimeoutMillis = 3000L;
    private volatile long closeNotifyReadTimeoutMillis;
    volatile int wrapDataSize = 16384;
    static final boolean $assertionsDisabled;

    public SslHandler(SSLEngine sSLEngine) {
        this(sSLEngine, false);
    }

    public SslHandler(SSLEngine sSLEngine, boolean bl) {
        this(sSLEngine, bl, ImmediateExecutor.INSTANCE);
    }

    @Deprecated
    public SslHandler(SSLEngine sSLEngine, Executor executor) {
        this(sSLEngine, false, executor);
    }

    @Deprecated
    public SslHandler(SSLEngine sSLEngine, boolean bl, Executor executor) {
        if (sSLEngine == null) {
            throw new NullPointerException("engine");
        }
        if (executor == null) {
            throw new NullPointerException("delegatedTaskExecutor");
        }
        this.engine = sSLEngine;
        this.engineType = SslEngineType.forEngine(sSLEngine);
        this.delegatedTaskExecutor = executor;
        this.startTls = bl;
        this.jdkCompatibilityMode = this.engineType.jdkCompatibilityMode(sSLEngine);
        this.setCumulator(this.engineType.cumulator);
    }

    public long getHandshakeTimeoutMillis() {
        return this.handshakeTimeoutMillis;
    }

    public void setHandshakeTimeout(long l, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        this.setHandshakeTimeoutMillis(timeUnit.toMillis(l));
    }

    public void setHandshakeTimeoutMillis(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("handshakeTimeoutMillis: " + l + " (expected: >= 0)");
        }
        this.handshakeTimeoutMillis = l;
    }

    public final void setWrapDataSize(int n) {
        this.wrapDataSize = n;
    }

    @Deprecated
    public long getCloseNotifyTimeoutMillis() {
        return this.getCloseNotifyFlushTimeoutMillis();
    }

    @Deprecated
    public void setCloseNotifyTimeout(long l, TimeUnit timeUnit) {
        this.setCloseNotifyFlushTimeout(l, timeUnit);
    }

    @Deprecated
    public void setCloseNotifyTimeoutMillis(long l) {
        this.setCloseNotifyFlushTimeoutMillis(l);
    }

    public final long getCloseNotifyFlushTimeoutMillis() {
        return this.closeNotifyFlushTimeoutMillis;
    }

    public final void setCloseNotifyFlushTimeout(long l, TimeUnit timeUnit) {
        this.setCloseNotifyFlushTimeoutMillis(timeUnit.toMillis(l));
    }

    public final void setCloseNotifyFlushTimeoutMillis(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("closeNotifyFlushTimeoutMillis: " + l + " (expected: >= 0)");
        }
        this.closeNotifyFlushTimeoutMillis = l;
    }

    public final long getCloseNotifyReadTimeoutMillis() {
        return this.closeNotifyReadTimeoutMillis;
    }

    public final void setCloseNotifyReadTimeout(long l, TimeUnit timeUnit) {
        this.setCloseNotifyReadTimeoutMillis(timeUnit.toMillis(l));
    }

    public final void setCloseNotifyReadTimeoutMillis(long l) {
        if (l < 0L) {
            throw new IllegalArgumentException("closeNotifyReadTimeoutMillis: " + l + " (expected: >= 0)");
        }
        this.closeNotifyReadTimeoutMillis = l;
    }

    public SSLEngine engine() {
        return this.engine;
    }

    public String applicationProtocol() {
        SSLEngine sSLEngine = this.engine();
        if (!(sSLEngine instanceof ApplicationProtocolAccessor)) {
            return null;
        }
        return ((ApplicationProtocolAccessor)((Object)sSLEngine)).getNegotiatedApplicationProtocol();
    }

    public Future<Channel> handshakeFuture() {
        return this.handshakePromise;
    }

    @Deprecated
    public ChannelFuture close() {
        return this.close(this.ctx.newPromise());
    }

    @Deprecated
    public ChannelFuture close(ChannelPromise channelPromise) {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        channelHandlerContext.executor().execute(new Runnable(this, channelHandlerContext, channelPromise){
            final ChannelHandlerContext val$ctx;
            final ChannelPromise val$promise;
            final SslHandler this$0;
            {
                this.this$0 = sslHandler;
                this.val$ctx = channelHandlerContext;
                this.val$promise = channelPromise;
            }

            @Override
            public void run() {
                block2: {
                    SslHandler.access$502(this.this$0, true);
                    SslHandler.access$100(this.this$0).closeOutbound();
                    try {
                        SslHandler.access$600(this.this$0, this.val$ctx, this.val$promise);
                    } catch (Exception exception) {
                        if (this.val$promise.tryFailure(exception)) break block2;
                        SslHandler.access$700().warn("{} flush() raised a masked exception.", (Object)this.val$ctx.channel(), (Object)exception);
                    }
                }
            }
        });
        return channelPromise;
    }

    public Future<Channel> sslCloseFuture() {
        return this.sslClosePromise;
    }

    @Override
    public void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.releaseAndFailAll(channelHandlerContext, new ChannelException("Pending write on removal of SslHandler"));
        }
        this.pendingUnencryptedWrites = null;
        if (this.engine instanceof ReferenceCounted) {
            ((ReferenceCounted)((Object)this.engine)).release();
        }
    }

    @Override
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.closeOutboundAndChannel(channelHandlerContext, channelPromise, true);
    }

    @Override
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        this.closeOutboundAndChannel(channelHandlerContext, channelPromise, false);
    }

    @Override
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.handshakePromise.isDone()) {
            this.readDuringHandshake = true;
        }
        channelHandlerContext.read();
    }

    private static IllegalStateException newPendingWritesNullException() {
        return new IllegalStateException("pendingUnencryptedWrites is null, handlerRemoved0 called?");
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        if (!(object instanceof ByteBuf)) {
            UnsupportedMessageTypeException unsupportedMessageTypeException = new UnsupportedMessageTypeException(object, ByteBuf.class);
            ReferenceCountUtil.safeRelease(object);
            channelPromise.setFailure(unsupportedMessageTypeException);
        } else if (this.pendingUnencryptedWrites == null) {
            ReferenceCountUtil.safeRelease(object);
            channelPromise.setFailure(SslHandler.newPendingWritesNullException());
        } else {
            this.pendingUnencryptedWrites.add((ByteBuf)object, channelPromise);
        }
    }

    @Override
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.startTls && !this.sentFirstMessage) {
            this.sentFirstMessage = true;
            this.pendingUnencryptedWrites.writeAndRemoveAll(channelHandlerContext);
            this.forceFlush(channelHandlerContext);
            return;
        }
        try {
            this.wrapAndFlush(channelHandlerContext);
        } catch (Throwable throwable) {
            this.setHandshakeFailure(channelHandlerContext, throwable);
            PlatformDependent.throwException(throwable);
        }
    }

    private void wrapAndFlush(ChannelHandlerContext channelHandlerContext) throws SSLException {
        if (this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, channelHandlerContext.newPromise());
        }
        if (!this.handshakePromise.isDone()) {
            this.flushedBeforeHandshake = true;
        }
        try {
            this.wrap(channelHandlerContext, false);
        } finally {
            this.forceFlush(channelHandlerContext);
        }
    }

    /*
     * Exception decompiling
     */
    private void wrap(ChannelHandlerContext var1_1, boolean var2_2) throws SSLException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 8[CASE]
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:435)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:484)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:538)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at async.DecompilerRunnable.cfrDecompilation(DecompilerRunnable.java:348)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:309)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:31)
         *     at java.util.concurrent.FutureTask.run(FutureTask.java:266)
         *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         *     at java.lang.Thread.run(Thread.java:750)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private void finishWrap(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ChannelPromise channelPromise, boolean bl, boolean bl2) {
        if (byteBuf == null) {
            byteBuf = Unpooled.EMPTY_BUFFER;
        } else if (!byteBuf.isReadable()) {
            byteBuf.release();
            byteBuf = Unpooled.EMPTY_BUFFER;
        }
        if (channelPromise != null) {
            channelHandlerContext.write(byteBuf, channelPromise);
        } else {
            channelHandlerContext.write(byteBuf);
        }
        if (bl) {
            this.needsFlush = true;
        }
        if (bl2) {
            this.readIfNeeded(channelHandlerContext);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean wrapNonAppData(ChannelHandlerContext channelHandlerContext, boolean bl) throws SSLException {
        ReferenceCounted referenceCounted = null;
        ByteBufAllocator byteBufAllocator = channelHandlerContext.alloc();
        try {
            while (!channelHandlerContext.isRemoved()) {
                SSLEngineResult sSLEngineResult;
                if (referenceCounted == null) {
                    referenceCounted = this.allocateOutNetBuf(channelHandlerContext, 2048, 1);
                }
                if ((sSLEngineResult = this.wrap(byteBufAllocator, this.engine, Unpooled.EMPTY_BUFFER, (ByteBuf)referenceCounted)).bytesProduced() > 0) {
                    channelHandlerContext.write(referenceCounted);
                    if (bl) {
                        this.needsFlush = true;
                    }
                    referenceCounted = null;
                }
                switch (sSLEngineResult.getHandshakeStatus()) {
                    case FINISHED: {
                        this.setHandshakeSuccess();
                        boolean bl2 = false;
                        return bl2;
                    }
                    case NEED_TASK: {
                        this.runDelegatedTasks();
                        break;
                    }
                    case NEED_UNWRAP: {
                        if (bl) {
                            boolean bl3 = false;
                            return bl3;
                        }
                        this.unwrapNonAppData(channelHandlerContext);
                        break;
                    }
                    case NEED_WRAP: {
                        break;
                    }
                    case NOT_HANDSHAKING: {
                        this.setHandshakeSuccessIfStillHandshaking();
                        if (!bl) {
                            this.unwrapNonAppData(channelHandlerContext);
                        }
                        boolean bl4 = true;
                        return bl4;
                    }
                    default: {
                        throw new IllegalStateException("Unknown handshake status: " + (Object)((Object)sSLEngineResult.getHandshakeStatus()));
                    }
                }
                if (sSLEngineResult.bytesProduced() == 0) {
                } else if (sSLEngineResult.bytesConsumed() != 0 || sSLEngineResult.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) continue;
                break;
            }
        } finally {
            if (referenceCounted != null) {
                referenceCounted.release();
            }
        }
        return true;
    }

    private SSLEngineResult wrap(ByteBufAllocator byteBufAllocator, SSLEngine sSLEngine, ByteBuf byteBuf, ByteBuf byteBuf2) throws SSLException {
        ReferenceCounted referenceCounted = null;
        try {
            ByteBuffer[] byteBufferArray;
            int n = byteBuf.readerIndex();
            int n2 = byteBuf.readableBytes();
            if (byteBuf.isDirect() || !this.engineType.wantsDirectBuffer) {
                if (!(byteBuf instanceof CompositeByteBuf) && byteBuf.nioBufferCount() == 1) {
                    byteBufferArray = this.singleBuffer;
                    byteBufferArray[0] = byteBuf.internalNioBuffer(n, n2);
                } else {
                    byteBufferArray = byteBuf.nioBuffers();
                }
            } else {
                referenceCounted = byteBufAllocator.directBuffer(n2);
                ((ByteBuf)referenceCounted).writeBytes(byteBuf, n, n2);
                byteBufferArray = this.singleBuffer;
                byteBufferArray[0] = ((ByteBuf)referenceCounted).internalNioBuffer(((ByteBuf)referenceCounted).readerIndex(), n2);
            }
            while (true) {
                ByteBuffer byteBuffer = byteBuf2.nioBuffer(byteBuf2.writerIndex(), byteBuf2.writableBytes());
                SSLEngineResult sSLEngineResult = sSLEngine.wrap(byteBufferArray, byteBuffer);
                byteBuf.skipBytes(sSLEngineResult.bytesConsumed());
                byteBuf2.writerIndex(byteBuf2.writerIndex() + sSLEngineResult.bytesProduced());
                switch (sSLEngineResult.getStatus()) {
                    case BUFFER_OVERFLOW: {
                        byteBuf2.ensureWritable(sSLEngine.getSession().getPacketBufferSize());
                        break;
                    }
                    default: {
                        SSLEngineResult sSLEngineResult2 = sSLEngineResult;
                        return sSLEngineResult2;
                    }
                }
            }
        } finally {
            this.singleBuffer[0] = null;
            if (referenceCounted != null) {
                referenceCounted.release();
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.setHandshakeFailure(channelHandlerContext, CHANNEL_CLOSED, !this.outboundClosed, this.handshakeStarted, false);
        this.notifyClosePromise(CHANNEL_CLOSED);
        super.channelInactive(channelHandlerContext);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        if (this.ignoreException(throwable)) {
            if (logger.isDebugEnabled()) {
                logger.debug("{} Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", (Object)channelHandlerContext.channel(), (Object)throwable);
            }
            if (channelHandlerContext.channel().isActive()) {
                channelHandlerContext.close();
            }
        } else {
            channelHandlerContext.fireExceptionCaught(throwable);
        }
    }

    private boolean ignoreException(Throwable throwable) {
        if (!(throwable instanceof SSLException) && throwable instanceof IOException && this.sslClosePromise.isDone()) {
            StackTraceElement[] stackTraceElementArray;
            String string = throwable.getMessage();
            if (string != null && IGNORABLE_ERROR_MESSAGE.matcher(string).matches()) {
                return false;
            }
            for (StackTraceElement stackTraceElement : stackTraceElementArray = throwable.getStackTrace()) {
                String string2 = stackTraceElement.getClassName();
                String string3 = stackTraceElement.getMethodName();
                if (string2.startsWith("io.netty.") || !"read".equals(string3)) continue;
                if (IGNORABLE_CLASS_IN_STACK.matcher(string2).matches()) {
                    return false;
                }
                try {
                    Class<?> clazz = PlatformDependent.getClassLoader(this.getClass()).loadClass(string2);
                    if (SocketChannel.class.isAssignableFrom(clazz) || DatagramChannel.class.isAssignableFrom(clazz)) {
                        return true;
                    }
                    if (PlatformDependent.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(clazz.getSuperclass().getName())) {
                        return true;
                    }
                } catch (Throwable throwable2) {
                    logger.debug("Unexpected exception while loading class {} classname {}", this.getClass(), string2, throwable2);
                }
            }
        }
        return true;
    }

    public static boolean isEncrypted(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 5) {
            throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
        }
        return SslUtils.getEncryptedPacketLength(byteBuf, byteBuf.readerIndex()) != -2;
    }

    private void decodeJdkCompatible(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws NotSslRecordException {
        int n;
        int n2 = this.packetLength;
        if (n2 > 0) {
            if (byteBuf.readableBytes() < n2) {
                return;
            }
        } else {
            n = byteBuf.readableBytes();
            if (n < 5) {
                return;
            }
            n2 = SslUtils.getEncryptedPacketLength(byteBuf, byteBuf.readerIndex());
            if (n2 == -2) {
                NotSslRecordException notSslRecordException = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(byteBuf));
                byteBuf.skipBytes(byteBuf.readableBytes());
                this.setHandshakeFailure(channelHandlerContext, notSslRecordException);
                throw notSslRecordException;
            }
            if (!$assertionsDisabled && n2 <= 0) {
                throw new AssertionError();
            }
            if (n2 > n) {
                this.packetLength = n2;
                return;
            }
        }
        this.packetLength = 0;
        try {
            n = this.unwrap(channelHandlerContext, byteBuf, byteBuf.readerIndex(), n2);
            if (!$assertionsDisabled && n != n2 && !this.engine.isInboundDone()) {
                throw new AssertionError((Object)("we feed the SSLEngine a packets worth of data: " + n2 + " but it only consumed: " + n));
            }
            byteBuf.skipBytes(n);
        } catch (Throwable throwable) {
            this.handleUnwrapThrowable(channelHandlerContext, throwable);
        }
    }

    private void decodeNonJdkCompatible(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        try {
            byteBuf.skipBytes(this.unwrap(channelHandlerContext, byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes()));
        } catch (Throwable throwable) {
            this.handleUnwrapThrowable(channelHandlerContext, throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void handleUnwrapThrowable(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
        try {
            if (this.handshakePromise.tryFailure(throwable)) {
                channelHandlerContext.fireUserEventTriggered(new SslHandshakeCompletionEvent(throwable));
            }
            this.wrapAndFlush(channelHandlerContext);
        } catch (SSLException sSLException) {
            logger.debug("SSLException during trying to call SSLEngine.wrap(...) because of an previous SSLException, ignoring...", sSLException);
        } finally {
            this.setHandshakeFailure(channelHandlerContext, throwable, true, false, true);
        }
        PlatformDependent.throwException(throwable);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws SSLException {
        if (this.jdkCompatibilityMode) {
            this.decodeJdkCompatible(channelHandlerContext, byteBuf);
        } else {
            this.decodeNonJdkCompatible(channelHandlerContext, byteBuf);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.discardSomeReadBytes();
        this.flushIfNeeded(channelHandlerContext);
        this.readIfNeeded(channelHandlerContext);
        this.firedChannelRead = false;
        channelHandlerContext.fireChannelReadComplete();
    }

    private void readIfNeeded(ChannelHandlerContext channelHandlerContext) {
        if (!(channelHandlerContext.channel().config().isAutoRead() || this.firedChannelRead && this.handshakePromise.isDone())) {
            channelHandlerContext.read();
        }
    }

    private void flushIfNeeded(ChannelHandlerContext channelHandlerContext) {
        if (this.needsFlush) {
            this.forceFlush(channelHandlerContext);
        }
    }

    private void unwrapNonAppData(ChannelHandlerContext channelHandlerContext) throws SSLException {
        this.unwrap(channelHandlerContext, Unpooled.EMPTY_BUFFER, 0, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int unwrap(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int n, int n2) throws SSLException {
        int n3 = n2;
        boolean bl = false;
        boolean bl2 = false;
        int n4 = -1;
        ByteBuf byteBuf2 = this.allocate(channelHandlerContext, n2);
        try {
            block14: while (!channelHandlerContext.isRemoved()) {
                SSLEngineResult sSLEngineResult = this.engineType.unwrap(this, byteBuf, n, n2, byteBuf2);
                SSLEngineResult.Status status2 = sSLEngineResult.getStatus();
                SSLEngineResult.HandshakeStatus handshakeStatus = sSLEngineResult.getHandshakeStatus();
                int n5 = sSLEngineResult.bytesProduced();
                int n6 = sSLEngineResult.bytesConsumed();
                n += n6;
                n2 -= n6;
                switch (status2) {
                    case BUFFER_OVERFLOW: {
                        int n7 = byteBuf2.readableBytes();
                        int n8 = n4;
                        n4 = n7;
                        int n9 = this.engine.getSession().getApplicationBufferSize() - n7;
                        if (n7 > 0) {
                            this.firedChannelRead = true;
                            channelHandlerContext.fireChannelRead(byteBuf2);
                            byteBuf2 = null;
                            if (n9 <= 0) {
                                n9 = this.engine.getSession().getApplicationBufferSize();
                            }
                        } else {
                            byteBuf2.release();
                            byteBuf2 = null;
                        }
                        if (n7 == 0 && n8 == 0) {
                            throw new IllegalStateException("Two consecutive overflows but no content was consumed. " + SSLSession.class.getSimpleName() + " getApplicationBufferSize: " + this.engine.getSession().getApplicationBufferSize() + " maybe too small.");
                        }
                        byteBuf2 = this.allocate(channelHandlerContext, this.engineType.calculatePendingData(this, n9));
                        continue block14;
                    }
                    case CLOSED: {
                        bl2 = true;
                        n4 = -1;
                        break;
                    }
                    default: {
                        n4 = -1;
                    }
                }
                switch (handshakeStatus) {
                    case NEED_UNWRAP: {
                        break;
                    }
                    case NEED_WRAP: {
                        if (!this.wrapNonAppData(channelHandlerContext, true) || n2 != 0) break;
                        break block14;
                    }
                    case NEED_TASK: {
                        this.runDelegatedTasks();
                        break;
                    }
                    case FINISHED: {
                        this.setHandshakeSuccess();
                        bl = true;
                        break;
                    }
                    case NOT_HANDSHAKING: {
                        if (this.setHandshakeSuccessIfStillHandshaking()) {
                            bl = true;
                            continue block14;
                        }
                        if (this.flushedBeforeHandshake) {
                            this.flushedBeforeHandshake = false;
                            bl = true;
                        }
                        if (n2 != 0) break;
                        break block14;
                    }
                    default: {
                        throw new IllegalStateException("unknown handshake status: " + (Object)((Object)handshakeStatus));
                    }
                }
                if (status2 != SSLEngineResult.Status.BUFFER_UNDERFLOW && (n6 != 0 || n5 != 0)) continue;
                if (handshakeStatus != SSLEngineResult.HandshakeStatus.NEED_UNWRAP) break;
                this.readIfNeeded(channelHandlerContext);
                break;
            }
            if (bl) {
                this.wrap(channelHandlerContext, true);
            }
            if (bl2) {
                this.notifyClosePromise(null);
            }
        } finally {
            if (byteBuf2 != null) {
                if (byteBuf2.isReadable()) {
                    this.firedChannelRead = true;
                    channelHandlerContext.fireChannelRead(byteBuf2);
                } else {
                    byteBuf2.release();
                }
            }
        }
        return n3 - n2;
    }

    private static ByteBuffer toByteBuffer(ByteBuf byteBuf, int n, int n2) {
        return byteBuf.nioBufferCount() == 1 ? byteBuf.internalNioBuffer(n, n2) : byteBuf.nioBuffer(n, n2);
    }

    private void runDelegatedTasks() {
        if (this.delegatedTaskExecutor == ImmediateExecutor.INSTANCE) {
            Runnable runnable;
            while ((runnable = this.engine.getDelegatedTask()) != null) {
                runnable.run();
            }
        } else {
            Object object;
            ArrayList<Runnable> arrayList = new ArrayList<Runnable>(2);
            while ((object = this.engine.getDelegatedTask()) != null) {
                arrayList.add((Runnable)object);
            }
            if (arrayList.isEmpty()) {
                return;
            }
            object = new CountDownLatch(1);
            this.delegatedTaskExecutor.execute(new Runnable(this, arrayList, (CountDownLatch)object){
                final List val$tasks;
                final CountDownLatch val$latch;
                final SslHandler this$0;
                {
                    this.this$0 = sslHandler;
                    this.val$tasks = list;
                    this.val$latch = countDownLatch;
                }

                @Override
                public void run() {
                    try {
                        for (Runnable runnable : this.val$tasks) {
                            runnable.run();
                        }
                    } catch (Exception exception) {
                        SslHandler.access$800(this.this$0).fireExceptionCaught(exception);
                    } finally {
                        this.val$latch.countDown();
                    }
                }
            });
            boolean bl = false;
            while (((CountDownLatch)object).getCount() != 0L) {
                try {
                    ((CountDownLatch)object).await();
                } catch (InterruptedException interruptedException) {
                    bl = true;
                }
            }
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean setHandshakeSuccessIfStillHandshaking() {
        if (!this.handshakePromise.isDone()) {
            this.setHandshakeSuccess();
            return false;
        }
        return true;
    }

    private void setHandshakeSuccess() {
        this.handshakePromise.trySuccess(this.ctx.channel());
        if (logger.isDebugEnabled()) {
            logger.debug("{} HANDSHAKEN: {}", (Object)this.ctx.channel(), (Object)this.engine.getSession().getCipherSuite());
        }
        this.ctx.fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
        if (this.readDuringHandshake && !this.ctx.channel().config().isAutoRead()) {
            this.readDuringHandshake = false;
            this.ctx.read();
        }
    }

    private void setHandshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
        this.setHandshakeFailure(channelHandlerContext, throwable, true, true, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setHandshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable throwable, boolean bl, boolean bl2, boolean bl3) {
        try {
            block7: {
                this.outboundClosed = true;
                this.engine.closeOutbound();
                if (bl) {
                    try {
                        this.engine.closeInbound();
                    } catch (SSLException sSLException) {
                        String string;
                        if (!logger.isDebugEnabled() || (string = sSLException.getMessage()) != null && string.contains("possible truncation attack")) break block7;
                        logger.debug("{} SSLEngine.closeInbound() raised an exception.", (Object)channelHandlerContext.channel(), (Object)sSLException);
                    }
                }
            }
            if (this.handshakePromise.tryFailure(throwable) || bl3) {
                SslUtils.handleHandshakeFailure(channelHandlerContext, throwable, bl2);
            }
        } finally {
            this.releaseAndFailAll(throwable);
        }
    }

    private void releaseAndFailAll(Throwable throwable) {
        if (this.pendingUnencryptedWrites != null) {
            this.pendingUnencryptedWrites.releaseAndFailAll(this.ctx, throwable);
        }
    }

    private void notifyClosePromise(Throwable throwable) {
        if (throwable == null) {
            if (this.sslClosePromise.trySuccess(this.ctx.channel())) {
                this.ctx.fireUserEventTriggered(SslCloseCompletionEvent.SUCCESS);
            }
        } else if (this.sslClosePromise.tryFailure(throwable)) {
            this.ctx.fireUserEventTriggered(new SslCloseCompletionEvent(throwable));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void closeOutboundAndChannel(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise, boolean bl) throws Exception {
        block8: {
            block7: {
                this.outboundClosed = true;
                this.engine.closeOutbound();
                if (!channelHandlerContext.channel().isActive()) {
                    if (bl) {
                        channelHandlerContext.disconnect(channelPromise);
                    } else {
                        channelHandlerContext.close(channelPromise);
                    }
                    return;
                }
                ChannelPromise channelPromise2 = channelHandlerContext.newPromise();
                try {
                    this.flush(channelHandlerContext, channelPromise2);
                    if (this.closeNotify) break block7;
                    this.closeNotify = true;
                } catch (Throwable throwable) {
                    if (!this.closeNotify) {
                        this.closeNotify = true;
                        this.safeClose(channelHandlerContext, channelPromise2, channelHandlerContext.newPromise().addListener(new ChannelPromiseNotifier(false, channelPromise)));
                    } else {
                        this.sslClosePromise.addListener(new FutureListener<Channel>(this, channelPromise){
                            final ChannelPromise val$promise;
                            final SslHandler this$0;
                            {
                                this.this$0 = sslHandler;
                                this.val$promise = channelPromise;
                            }

                            @Override
                            public void operationComplete(Future<Channel> future) {
                                this.val$promise.setSuccess();
                            }
                        });
                    }
                    throw throwable;
                }
                this.safeClose(channelHandlerContext, channelPromise2, channelHandlerContext.newPromise().addListener(new ChannelPromiseNotifier(false, channelPromise)));
                break block8;
            }
            this.sslClosePromise.addListener(new /* invalid duplicate definition of identical inner class */);
        }
    }

    private void flush(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (this.pendingUnencryptedWrites != null) {
            this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, channelPromise);
        } else {
            channelPromise.setFailure(SslHandler.newPendingWritesNullException());
        }
        this.flush(channelHandlerContext);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
        this.pendingUnencryptedWrites = new SslHandlerCoalescingBufferQueue(this, channelHandlerContext.channel(), 16);
        if (channelHandlerContext.channel().isActive()) {
            this.startHandshakeProcessing();
        }
    }

    private void startHandshakeProcessing() {
        this.handshakeStarted = true;
        if (this.engine.getUseClientMode()) {
            this.handshake(null);
        } else {
            this.applyHandshakeTimeout(null);
        }
    }

    public Future<Channel> renegotiate() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext == null) {
            throw new IllegalStateException();
        }
        return this.renegotiate(channelHandlerContext.executor().newPromise());
    }

    public Future<Channel> renegotiate(Promise<Channel> promise) {
        if (promise == null) {
            throw new NullPointerException("promise");
        }
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext == null) {
            throw new IllegalStateException();
        }
        EventExecutor eventExecutor = channelHandlerContext.executor();
        if (!eventExecutor.inEventLoop()) {
            eventExecutor.execute(new Runnable(this, promise){
                final Promise val$promise;
                final SslHandler this$0;
                {
                    this.this$0 = sslHandler;
                    this.val$promise = promise;
                }

                @Override
                public void run() {
                    SslHandler.access$900(this.this$0, this.val$promise);
                }
            });
            return promise;
        }
        this.handshake(promise);
        return promise;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void handshake(Promise<Channel> promise) {
        Promise<Channel> promise2;
        Promise<Channel> promise3;
        if (promise != null) {
            promise3 = this.handshakePromise;
            if (!promise3.isDone()) {
                promise3.addListener((GenericFutureListener<Future<Channel>>)new FutureListener<Channel>(this, promise){
                    final Promise val$newHandshakePromise;
                    final SslHandler this$0;
                    {
                        this.this$0 = sslHandler;
                        this.val$newHandshakePromise = promise;
                    }

                    @Override
                    public void operationComplete(Future<Channel> future) throws Exception {
                        if (future.isSuccess()) {
                            this.val$newHandshakePromise.setSuccess(future.getNow());
                        } else {
                            this.val$newHandshakePromise.setFailure(future.cause());
                        }
                    }
                });
                return;
            }
            this.handshakePromise = promise2 = promise;
        } else {
            if (this.engine.getHandshakeStatus() != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING) {
                return;
            }
            promise2 = this.handshakePromise;
            if (!$assertionsDisabled && promise2.isDone()) {
                throw new AssertionError();
            }
        }
        promise3 = this.ctx;
        try {
            this.engine.beginHandshake();
            this.wrapNonAppData((ChannelHandlerContext)((Object)promise3), false);
        } catch (Throwable throwable) {
            this.setHandshakeFailure((ChannelHandlerContext)((Object)promise3), throwable);
        } finally {
            this.forceFlush((ChannelHandlerContext)((Object)promise3));
        }
        this.applyHandshakeTimeout(promise2);
    }

    private void applyHandshakeTimeout(Promise<Channel> promise) {
        Promise<Channel> promise2 = promise == null ? this.handshakePromise : promise;
        long l = this.handshakeTimeoutMillis;
        if (l <= 0L || promise2.isDone()) {
            return;
        }
        io.netty.util.concurrent.ScheduledFuture<?> scheduledFuture = this.ctx.executor().schedule(new Runnable(this, promise2){
            final Promise val$promise;
            final SslHandler this$0;
            {
                this.this$0 = sslHandler;
                this.val$promise = promise;
            }

            @Override
            public void run() {
                if (this.val$promise.isDone()) {
                    return;
                }
                try {
                    if (SslHandler.access$1100(this.this$0).tryFailure(SslHandler.access$1000())) {
                        SslUtils.handleHandshakeFailure(SslHandler.access$800(this.this$0), SslHandler.access$1000(), true);
                    }
                } finally {
                    SslHandler.access$1200(this.this$0, SslHandler.access$1000());
                }
            }
        }, l, TimeUnit.MILLISECONDS);
        promise2.addListener((GenericFutureListener<Future<Channel>>)new FutureListener<Channel>(this, scheduledFuture){
            final ScheduledFuture val$timeoutFuture;
            final SslHandler this$0;
            {
                this.this$0 = sslHandler;
                this.val$timeoutFuture = scheduledFuture;
            }

            @Override
            public void operationComplete(Future<Channel> future) throws Exception {
                this.val$timeoutFuture.cancel(false);
            }
        });
    }

    private void forceFlush(ChannelHandlerContext channelHandlerContext) {
        this.needsFlush = false;
        channelHandlerContext.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.startTls) {
            this.startHandshakeProcessing();
        }
        channelHandlerContext.fireChannelActive();
    }

    private void safeClose(ChannelHandlerContext channelHandlerContext, ChannelFuture channelFuture, ChannelPromise channelPromise) {
        long l;
        if (!channelHandlerContext.channel().isActive()) {
            channelHandlerContext.close(channelPromise);
            return;
        }
        io.netty.util.concurrent.ScheduledFuture<?> scheduledFuture = !channelFuture.isDone() ? ((l = this.closeNotifyFlushTimeoutMillis) > 0L ? channelHandlerContext.executor().schedule(new Runnable(this, channelFuture, channelHandlerContext, channelPromise){
            final ChannelFuture val$flushFuture;
            final ChannelHandlerContext val$ctx;
            final ChannelPromise val$promise;
            final SslHandler this$0;
            {
                this.this$0 = sslHandler;
                this.val$flushFuture = channelFuture;
                this.val$ctx = channelHandlerContext;
                this.val$promise = channelPromise;
            }

            @Override
            public void run() {
                if (!this.val$flushFuture.isDone()) {
                    SslHandler.access$700().warn("{} Last write attempt timed out; force-closing the connection.", (Object)this.val$ctx.channel());
                    SslHandler.access$1300(this.val$ctx.close(this.val$ctx.newPromise()), this.val$promise);
                }
            }
        }, l, TimeUnit.MILLISECONDS) : null) : null;
        channelFuture.addListener(new ChannelFutureListener(this, scheduledFuture, channelHandlerContext, channelPromise){
            final ScheduledFuture val$timeoutFuture;
            final ChannelHandlerContext val$ctx;
            final ChannelPromise val$promise;
            final SslHandler this$0;
            {
                this.this$0 = sslHandler;
                this.val$timeoutFuture = scheduledFuture;
                this.val$ctx = channelHandlerContext;
                this.val$promise = channelPromise;
            }

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                long l;
                if (this.val$timeoutFuture != null) {
                    this.val$timeoutFuture.cancel(false);
                }
                if ((l = SslHandler.access$1400(this.this$0)) <= 0L) {
                    SslHandler.access$1300(this.val$ctx.close(this.val$ctx.newPromise()), this.val$promise);
                } else {
                    io.netty.util.concurrent.ScheduledFuture<?> scheduledFuture = !SslHandler.access$1500(this.this$0).isDone() ? this.val$ctx.executor().schedule(new Runnable(this, l){
                        final long val$closeNotifyReadTimeout;
                        final 9 this$1;
                        {
                            this.this$1 = var1_1;
                            this.val$closeNotifyReadTimeout = l;
                        }

                        @Override
                        public void run() {
                            if (!SslHandler.access$1500(this.this$1.this$0).isDone()) {
                                SslHandler.access$700().debug("{} did not receive close_notify in {}ms; force-closing the connection.", (Object)this.this$1.val$ctx.channel(), (Object)this.val$closeNotifyReadTimeout);
                                SslHandler.access$1300(this.this$1.val$ctx.close(this.this$1.val$ctx.newPromise()), this.this$1.val$promise);
                            }
                        }
                    }, l, TimeUnit.MILLISECONDS) : null;
                    SslHandler.access$1500(this.this$0).addListener(new FutureListener<Channel>(this, scheduledFuture){
                        final ScheduledFuture val$closeNotifyReadTimeoutFuture;
                        final 9 this$1;
                        {
                            this.this$1 = var1_1;
                            this.val$closeNotifyReadTimeoutFuture = scheduledFuture;
                        }

                        @Override
                        public void operationComplete(Future<Channel> future) throws Exception {
                            if (this.val$closeNotifyReadTimeoutFuture != null) {
                                this.val$closeNotifyReadTimeoutFuture.cancel(false);
                            }
                            SslHandler.access$1300(this.this$1.val$ctx.close(this.this$1.val$ctx.newPromise()), this.this$1.val$promise);
                        }
                    });
                }
            }

            @Override
            public void operationComplete(Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }

    private static void addCloseListener(ChannelFuture channelFuture, ChannelPromise channelPromise) {
        channelFuture.addListener(new ChannelPromiseNotifier(false, channelPromise));
    }

    private ByteBuf allocate(ChannelHandlerContext channelHandlerContext, int n) {
        ByteBufAllocator byteBufAllocator = channelHandlerContext.alloc();
        if (this.engineType.wantsDirectBuffer) {
            return byteBufAllocator.directBuffer(n);
        }
        return byteBufAllocator.buffer(n);
    }

    private ByteBuf allocateOutNetBuf(ChannelHandlerContext channelHandlerContext, int n, int n2) {
        return this.allocate(channelHandlerContext, this.engineType.calculateWrapBufferCapacity(this, n, n2));
    }

    private static boolean attemptCopyToCumulation(ByteBuf byteBuf, ByteBuf byteBuf2, int n) {
        int n2 = byteBuf2.readableBytes();
        int n3 = byteBuf.capacity();
        if (n - byteBuf.readableBytes() >= n2 && (byteBuf.isWritable(n2) && n3 >= n || n3 < n && ByteBufUtil.ensureWritableSuccess(byteBuf.ensureWritable(n2, true)))) {
            byteBuf.writeBytes(byteBuf2);
            byteBuf2.release();
            return false;
        }
        return true;
    }

    static SSLEngine access$100(SslHandler sslHandler) {
        return sslHandler.engine;
    }

    static ByteBuffer[] access$200(SslHandler sslHandler) {
        return sslHandler.singleBuffer;
    }

    static ByteBuffer access$300(ByteBuf byteBuf, int n, int n2) {
        return SslHandler.toByteBuffer(byteBuf, n, n2);
    }

    static boolean access$502(SslHandler sslHandler, boolean bl) {
        sslHandler.outboundClosed = bl;
        return sslHandler.outboundClosed;
    }

    static void access$600(SslHandler sslHandler, ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        sslHandler.flush(channelHandlerContext, channelPromise);
    }

    static InternalLogger access$700() {
        return logger;
    }

    static ChannelHandlerContext access$800(SslHandler sslHandler) {
        return sslHandler.ctx;
    }

    static void access$900(SslHandler sslHandler, Promise promise) {
        sslHandler.handshake(promise);
    }

    static SSLException access$1000() {
        return HANDSHAKE_TIMED_OUT;
    }

    static Promise access$1100(SslHandler sslHandler) {
        return sslHandler.handshakePromise;
    }

    static void access$1200(SslHandler sslHandler, Throwable throwable) {
        sslHandler.releaseAndFailAll(throwable);
    }

    static void access$1300(ChannelFuture channelFuture, ChannelPromise channelPromise) {
        SslHandler.addCloseListener(channelFuture, channelPromise);
    }

    static long access$1400(SslHandler sslHandler) {
        return sslHandler.closeNotifyReadTimeoutMillis;
    }

    static LazyChannelPromise access$1500(SslHandler sslHandler) {
        return sslHandler.sslClosePromise;
    }

    static boolean access$1600(ByteBuf byteBuf, ByteBuf byteBuf2, int n) {
        return SslHandler.attemptCopyToCumulation(byteBuf, byteBuf2, n);
    }

    static {
        $assertionsDisabled = !SslHandler.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(SslHandler.class);
        IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
        IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
        SSLENGINE_CLOSED = ThrowableUtil.unknownStackTrace(new SSLException("SSLEngine closed already"), SslHandler.class, "wrap(...)");
        HANDSHAKE_TIMED_OUT = ThrowableUtil.unknownStackTrace(new SSLException("handshake timed out"), SslHandler.class, "handshake(...)");
        CHANNEL_CLOSED = ThrowableUtil.unknownStackTrace(new ClosedChannelException(), SslHandler.class, "channelInactive(...)");
    }

    private final class LazyChannelPromise
    extends DefaultPromise<Channel> {
        final SslHandler this$0;

        private LazyChannelPromise(SslHandler sslHandler) {
            this.this$0 = sslHandler;
        }

        @Override
        protected EventExecutor executor() {
            if (SslHandler.access$800(this.this$0) == null) {
                throw new IllegalStateException();
            }
            return SslHandler.access$800(this.this$0).executor();
        }

        @Override
        protected void checkDeadLock() {
            if (SslHandler.access$800(this.this$0) == null) {
                return;
            }
            super.checkDeadLock();
        }

        LazyChannelPromise(SslHandler sslHandler, 1 var2_2) {
            this(sslHandler);
        }
    }

    private final class SslHandlerCoalescingBufferQueue
    extends AbstractCoalescingBufferQueue {
        final SslHandler this$0;

        SslHandlerCoalescingBufferQueue(SslHandler sslHandler, Channel channel, int n) {
            this.this$0 = sslHandler;
            super(channel, n);
        }

        @Override
        protected ByteBuf compose(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
            int n = this.this$0.wrapDataSize;
            if (byteBuf instanceof CompositeByteBuf) {
                CompositeByteBuf compositeByteBuf = (CompositeByteBuf)byteBuf;
                int n2 = compositeByteBuf.numComponents();
                if (n2 == 0 || !SslHandler.access$1600(compositeByteBuf.internalComponent(n2 - 1), byteBuf2, n)) {
                    compositeByteBuf.addComponent(true, byteBuf2);
                }
                return compositeByteBuf;
            }
            return SslHandler.access$1600(byteBuf, byteBuf2, n) ? byteBuf : this.copyAndCompose(byteBufAllocator, byteBuf, byteBuf2);
        }

        @Override
        protected ByteBuf composeFirst(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf) {
            if (byteBuf instanceof CompositeByteBuf) {
                CompositeByteBuf compositeByteBuf = (CompositeByteBuf)byteBuf;
                byteBuf = byteBufAllocator.directBuffer(compositeByteBuf.readableBytes());
                try {
                    byteBuf.writeBytes(compositeByteBuf);
                } catch (Throwable throwable) {
                    byteBuf.release();
                    PlatformDependent.throwException(throwable);
                }
                compositeByteBuf.release();
            }
            return byteBuf;
        }

        @Override
        protected ByteBuf removeEmptyValue() {
            return null;
        }
    }

    private static enum SslEngineType {
        TCNATIVE(true, ByteToMessageDecoder.COMPOSITE_CUMULATOR){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int n, int n2, ByteBuf byteBuf2) throws SSLException {
                SSLEngineResult sSLEngineResult;
                int n3 = byteBuf.nioBufferCount();
                int n4 = byteBuf2.writerIndex();
                if (n3 > 1) {
                    ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = (ReferenceCountedOpenSslEngine)SslHandler.access$100(sslHandler);
                    try {
                        SslHandler.access$200((SslHandler)sslHandler)[0] = SslHandler.access$300(byteBuf2, n4, byteBuf2.writableBytes());
                        sSLEngineResult = referenceCountedOpenSslEngine.unwrap(byteBuf.nioBuffers(n, n2), SslHandler.access$200(sslHandler));
                    } finally {
                        SslHandler.access$200((SslHandler)sslHandler)[0] = null;
                    }
                } else {
                    sSLEngineResult = SslHandler.access$100(sslHandler).unwrap(SslHandler.access$300(byteBuf, n, n2), SslHandler.access$300(byteBuf2, n4, byteBuf2.writableBytes()));
                }
                byteBuf2.writerIndex(n4 + sSLEngineResult.bytesProduced());
                return sSLEngineResult;
            }

            @Override
            int getPacketBufferSize(SslHandler sslHandler) {
                return ((ReferenceCountedOpenSslEngine)SslHandler.access$100(sslHandler)).maxEncryptedPacketLength0();
            }

            @Override
            int calculateWrapBufferCapacity(SslHandler sslHandler, int n, int n2) {
                return ((ReferenceCountedOpenSslEngine)SslHandler.access$100(sslHandler)).calculateMaxLengthForWrap(n, n2);
            }

            @Override
            int calculatePendingData(SslHandler sslHandler, int n) {
                int n2 = ((ReferenceCountedOpenSslEngine)SslHandler.access$100(sslHandler)).sslPending();
                return n2 > 0 ? n2 : n;
            }

            @Override
            boolean jdkCompatibilityMode(SSLEngine sSLEngine) {
                return ((ReferenceCountedOpenSslEngine)sSLEngine).jdkCompatibilityMode;
            }
        }
        ,
        CONSCRYPT(true, ByteToMessageDecoder.COMPOSITE_CUMULATOR){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int n, int n2, ByteBuf byteBuf2) throws SSLException {
                SSLEngineResult sSLEngineResult;
                int n3 = byteBuf.nioBufferCount();
                int n4 = byteBuf2.writerIndex();
                if (n3 > 1) {
                    try {
                        SslHandler.access$200((SslHandler)sslHandler)[0] = SslHandler.access$300(byteBuf2, n4, byteBuf2.writableBytes());
                        sSLEngineResult = ((ConscryptAlpnSslEngine)SslHandler.access$100(sslHandler)).unwrap(byteBuf.nioBuffers(n, n2), SslHandler.access$200(sslHandler));
                    } finally {
                        SslHandler.access$200((SslHandler)sslHandler)[0] = null;
                    }
                } else {
                    sSLEngineResult = SslHandler.access$100(sslHandler).unwrap(SslHandler.access$300(byteBuf, n, n2), SslHandler.access$300(byteBuf2, n4, byteBuf2.writableBytes()));
                }
                byteBuf2.writerIndex(n4 + sSLEngineResult.bytesProduced());
                return sSLEngineResult;
            }

            @Override
            int calculateWrapBufferCapacity(SslHandler sslHandler, int n, int n2) {
                return ((ConscryptAlpnSslEngine)SslHandler.access$100(sslHandler)).calculateOutNetBufSize(n, n2);
            }

            @Override
            int calculatePendingData(SslHandler sslHandler, int n) {
                return n;
            }

            @Override
            boolean jdkCompatibilityMode(SSLEngine sSLEngine) {
                return false;
            }
        }
        ,
        JDK(false, ByteToMessageDecoder.MERGE_CUMULATOR){

            @Override
            SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int n, int n2, ByteBuf byteBuf2) throws SSLException {
                int n3;
                int n4 = byteBuf2.writerIndex();
                ByteBuffer byteBuffer = SslHandler.access$300(byteBuf, n, n2);
                int n5 = byteBuffer.position();
                SSLEngineResult sSLEngineResult = SslHandler.access$100(sslHandler).unwrap(byteBuffer, SslHandler.access$300(byteBuf2, n4, byteBuf2.writableBytes()));
                byteBuf2.writerIndex(n4 + sSLEngineResult.bytesProduced());
                if (sSLEngineResult.bytesConsumed() == 0 && (n3 = byteBuffer.position() - n5) != sSLEngineResult.bytesConsumed()) {
                    return new SSLEngineResult(sSLEngineResult.getStatus(), sSLEngineResult.getHandshakeStatus(), n3, sSLEngineResult.bytesProduced());
                }
                return sSLEngineResult;
            }

            @Override
            int calculateWrapBufferCapacity(SslHandler sslHandler, int n, int n2) {
                return SslHandler.access$100(sslHandler).getSession().getPacketBufferSize();
            }

            @Override
            int calculatePendingData(SslHandler sslHandler, int n) {
                return n;
            }

            @Override
            boolean jdkCompatibilityMode(SSLEngine sSLEngine) {
                return false;
            }
        };

        final boolean wantsDirectBuffer;
        final ByteToMessageDecoder.Cumulator cumulator;

        static SslEngineType forEngine(SSLEngine sSLEngine) {
            return sSLEngine instanceof ReferenceCountedOpenSslEngine ? TCNATIVE : (sSLEngine instanceof ConscryptAlpnSslEngine ? CONSCRYPT : JDK);
        }

        private SslEngineType(boolean bl, ByteToMessageDecoder.Cumulator cumulator) {
            this.wantsDirectBuffer = bl;
            this.cumulator = cumulator;
        }

        int getPacketBufferSize(SslHandler sslHandler) {
            return SslHandler.access$100(sslHandler).getSession().getPacketBufferSize();
        }

        abstract SSLEngineResult unwrap(SslHandler var1, ByteBuf var2, int var3, int var4, ByteBuf var5) throws SSLException;

        abstract int calculateWrapBufferCapacity(SslHandler var1, int var2, int var3);

        abstract int calculatePendingData(SslHandler var1, int var2);

        abstract boolean jdkCompatibilityMode(SSLEngine var1);

        SslEngineType(boolean bl, ByteToMessageDecoder.Cumulator cumulator, 1 var5_5) {
            this(bl, cumulator);
        }
    }
}

