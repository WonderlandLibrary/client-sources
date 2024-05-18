/*      */ package io.netty.handler.ssl;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.ByteBufUtil;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.channel.Channel;
/*      */ import io.netty.channel.ChannelException;
/*      */ import io.netty.channel.ChannelFuture;
/*      */ import io.netty.channel.ChannelFutureListener;
/*      */ import io.netty.channel.ChannelHandlerContext;
/*      */ import io.netty.channel.ChannelOutboundHandler;
/*      */ import io.netty.channel.ChannelPromise;
/*      */ import io.netty.channel.PendingWriteQueue;
/*      */ import io.netty.handler.codec.ByteToMessageDecoder;
/*      */ import io.netty.util.concurrent.DefaultPromise;
/*      */ import io.netty.util.concurrent.EventExecutor;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import io.netty.util.concurrent.ImmediateExecutor;
/*      */ import io.netty.util.concurrent.ScheduledFuture;
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.logging.InternalLogger;
/*      */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.channels.ClosedChannelException;
/*      */ import java.nio.channels.DatagramChannel;
/*      */ import java.nio.channels.SocketChannel;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.CountDownLatch;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.ScheduledFuture;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.net.ssl.SSLEngine;
/*      */ import javax.net.ssl.SSLEngineResult;
/*      */ import javax.net.ssl.SSLException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SslHandler
/*      */   extends ByteToMessageDecoder
/*      */   implements ChannelOutboundHandler
/*      */ {
/*  159 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(SslHandler.class);
/*      */ 
/*      */   
/*  162 */   private static final Pattern IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
/*      */   
/*  164 */   private static final Pattern IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);
/*      */ 
/*      */   
/*  167 */   private static final SSLException SSLENGINE_CLOSED = new SSLException("SSLEngine closed already");
/*  168 */   private static final SSLException HANDSHAKE_TIMED_OUT = new SSLException("handshake timed out");
/*  169 */   private static final ClosedChannelException CHANNEL_CLOSED = new ClosedChannelException(); private volatile ChannelHandlerContext ctx;
/*      */   
/*      */   static {
/*  172 */     SSLENGINE_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  173 */     HANDSHAKE_TIMED_OUT.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*  174 */     CHANNEL_CLOSED.setStackTrace(EmptyArrays.EMPTY_STACK_TRACE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final SSLEngine engine;
/*      */ 
/*      */   
/*      */   private final int maxPacketBufferSize;
/*      */ 
/*      */   
/*      */   private final Executor delegatedTaskExecutor;
/*      */ 
/*      */   
/*      */   private final boolean wantsDirectBuffer;
/*      */ 
/*      */   
/*      */   private final boolean wantsLargeOutboundNetworkBuffer;
/*      */ 
/*      */   
/*      */   private boolean wantsInboundHeapBuffer;
/*      */ 
/*      */   
/*      */   private final boolean startTls;
/*      */ 
/*      */   
/*      */   private boolean sentFirstMessage;
/*      */ 
/*      */   
/*      */   private boolean flushedBeforeHandshakeDone;
/*      */ 
/*      */   
/*      */   private PendingWriteQueue pendingUnencryptedWrites;
/*      */ 
/*      */   
/*  210 */   private final LazyChannelPromise handshakePromise = new LazyChannelPromise();
/*  211 */   private final LazyChannelPromise sslCloseFuture = new LazyChannelPromise();
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean needsFlush;
/*      */ 
/*      */   
/*      */   private int packetLength;
/*      */ 
/*      */   
/*  221 */   private volatile long handshakeTimeoutMillis = 10000L;
/*  222 */   private volatile long closeNotifyTimeoutMillis = 3000L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler(SSLEngine engine) {
/*  230 */     this(engine, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SslHandler(SSLEngine engine, boolean startTls) {
/*  242 */     this(engine, startTls, (Executor)ImmediateExecutor.INSTANCE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public SslHandler(SSLEngine engine, Executor delegatedTaskExecutor) {
/*  250 */     this(engine, false, delegatedTaskExecutor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public SslHandler(SSLEngine engine, boolean startTls, Executor delegatedTaskExecutor) {
/*  258 */     if (engine == null) {
/*  259 */       throw new NullPointerException("engine");
/*      */     }
/*  261 */     if (delegatedTaskExecutor == null) {
/*  262 */       throw new NullPointerException("delegatedTaskExecutor");
/*      */     }
/*  264 */     this.engine = engine;
/*  265 */     this.delegatedTaskExecutor = delegatedTaskExecutor;
/*  266 */     this.startTls = startTls;
/*  267 */     this.maxPacketBufferSize = engine.getSession().getPacketBufferSize();
/*      */     
/*  269 */     this.wantsDirectBuffer = engine instanceof OpenSslEngine;
/*  270 */     this.wantsLargeOutboundNetworkBuffer = !(engine instanceof OpenSslEngine);
/*      */   }
/*      */   
/*      */   public long getHandshakeTimeoutMillis() {
/*  274 */     return this.handshakeTimeoutMillis;
/*      */   }
/*      */   
/*      */   public void setHandshakeTimeout(long handshakeTimeout, TimeUnit unit) {
/*  278 */     if (unit == null) {
/*  279 */       throw new NullPointerException("unit");
/*      */     }
/*      */     
/*  282 */     setHandshakeTimeoutMillis(unit.toMillis(handshakeTimeout));
/*      */   }
/*      */   
/*      */   public void setHandshakeTimeoutMillis(long handshakeTimeoutMillis) {
/*  286 */     if (handshakeTimeoutMillis < 0L) {
/*  287 */       throw new IllegalArgumentException("handshakeTimeoutMillis: " + handshakeTimeoutMillis + " (expected: >= 0)");
/*      */     }
/*      */     
/*  290 */     this.handshakeTimeoutMillis = handshakeTimeoutMillis;
/*      */   }
/*      */   
/*      */   public long getCloseNotifyTimeoutMillis() {
/*  294 */     return this.closeNotifyTimeoutMillis;
/*      */   }
/*      */   
/*      */   public void setCloseNotifyTimeout(long closeNotifyTimeout, TimeUnit unit) {
/*  298 */     if (unit == null) {
/*  299 */       throw new NullPointerException("unit");
/*      */     }
/*      */     
/*  302 */     setCloseNotifyTimeoutMillis(unit.toMillis(closeNotifyTimeout));
/*      */   }
/*      */   
/*      */   public void setCloseNotifyTimeoutMillis(long closeNotifyTimeoutMillis) {
/*  306 */     if (closeNotifyTimeoutMillis < 0L) {
/*  307 */       throw new IllegalArgumentException("closeNotifyTimeoutMillis: " + closeNotifyTimeoutMillis + " (expected: >= 0)");
/*      */     }
/*      */     
/*  310 */     this.closeNotifyTimeoutMillis = closeNotifyTimeoutMillis;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SSLEngine engine() {
/*  317 */     return this.engine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Future<Channel> handshakeFuture() {
/*  324 */     return (Future<Channel>)this.handshakePromise;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture close() {
/*  332 */     return close(this.ctx.newPromise());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChannelFuture close(final ChannelPromise future) {
/*  339 */     final ChannelHandlerContext ctx = this.ctx;
/*  340 */     ctx.executor().execute(new Runnable()
/*      */         {
/*      */           public void run() {
/*  343 */             SslHandler.this.engine.closeOutbound();
/*      */             try {
/*  345 */               SslHandler.this.write(ctx, Unpooled.EMPTY_BUFFER, future);
/*  346 */               SslHandler.this.flush(ctx);
/*  347 */             } catch (Exception e) {
/*  348 */               if (!future.tryFailure(e)) {
/*  349 */                 SslHandler.logger.warn("flush() raised a masked exception.", e);
/*      */               }
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  355 */     return (ChannelFuture)future;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Future<Channel> sslCloseFuture() {
/*  367 */     return (Future<Channel>)this.sslCloseFuture;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
/*  372 */     if (!this.pendingUnencryptedWrites.isEmpty())
/*      */     {
/*  374 */       this.pendingUnencryptedWrites.removeAndFailAll((Throwable)new ChannelException("Pending write on removal of SslHandler"));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/*  380 */     ctx.bind(localAddress, promise);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/*  386 */     ctx.connect(remoteAddress, localAddress, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  391 */     ctx.deregister(promise);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  397 */     closeOutboundAndChannel(ctx, promise, true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/*  403 */     closeOutboundAndChannel(ctx, promise, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read(ChannelHandlerContext ctx) {
/*  408 */     ctx.read();
/*      */   }
/*      */ 
/*      */   
/*      */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/*  413 */     this.pendingUnencryptedWrites.add(msg, promise);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush(ChannelHandlerContext ctx) throws Exception {
/*  420 */     if (this.startTls && !this.sentFirstMessage) {
/*  421 */       this.sentFirstMessage = true;
/*  422 */       this.pendingUnencryptedWrites.removeAndWriteAll();
/*  423 */       ctx.flush();
/*      */       return;
/*      */     } 
/*  426 */     if (this.pendingUnencryptedWrites.isEmpty()) {
/*  427 */       this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, ctx.voidPromise());
/*      */     }
/*  429 */     if (!this.handshakePromise.isDone()) {
/*  430 */       this.flushedBeforeHandshakeDone = true;
/*      */     }
/*  432 */     wrap(ctx, false);
/*  433 */     ctx.flush();
/*      */   }
/*      */   
/*      */   private void wrap(ChannelHandlerContext ctx, boolean inUnwrap) throws SSLException {
/*  437 */     ByteBuf out = null;
/*  438 */     ChannelPromise promise = null;
/*      */     try {
/*      */       while (true) {
/*  441 */         Object msg = this.pendingUnencryptedWrites.current();
/*  442 */         if (msg == null) {
/*      */           break;
/*      */         }
/*      */         
/*  446 */         if (!(msg instanceof ByteBuf)) {
/*  447 */           this.pendingUnencryptedWrites.removeAndWrite();
/*      */           
/*      */           continue;
/*      */         } 
/*  451 */         ByteBuf buf = (ByteBuf)msg;
/*  452 */         if (out == null) {
/*  453 */           out = allocateOutNetBuf(ctx, buf.readableBytes());
/*      */         }
/*      */         
/*  456 */         SSLEngineResult result = wrap(this.engine, buf, out);
/*      */         
/*  458 */         if (!buf.isReadable()) {
/*  459 */           promise = this.pendingUnencryptedWrites.remove();
/*      */         } else {
/*  461 */           promise = null;
/*      */         } 
/*      */         
/*  464 */         if (result.getStatus() == SSLEngineResult.Status.CLOSED) {
/*      */ 
/*      */           
/*  467 */           this.pendingUnencryptedWrites.removeAndFailAll(SSLENGINE_CLOSED);
/*      */           return;
/*      */         } 
/*  470 */         switch (result.getHandshakeStatus()) {
/*      */           case BUFFER_OVERFLOW:
/*  472 */             runDelegatedTasks();
/*      */             continue;
/*      */           case null:
/*  475 */             setHandshakeSuccess();
/*      */           
/*      */           case null:
/*  478 */             setHandshakeSuccessIfStillHandshaking();
/*      */           
/*      */           case null:
/*  481 */             finishWrap(ctx, out, promise, inUnwrap);
/*  482 */             promise = null;
/*  483 */             out = null;
/*      */             continue;
/*      */           case null:
/*      */             return;
/*      */         } 
/*  488 */         throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  493 */     catch (SSLException e) {
/*  494 */       setHandshakeFailure(e);
/*  495 */       throw e;
/*      */     } finally {
/*  497 */       finishWrap(ctx, out, promise, inUnwrap);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void finishWrap(ChannelHandlerContext ctx, ByteBuf out, ChannelPromise promise, boolean inUnwrap) {
/*  502 */     if (out == null) {
/*  503 */       out = Unpooled.EMPTY_BUFFER;
/*  504 */     } else if (!out.isReadable()) {
/*  505 */       out.release();
/*  506 */       out = Unpooled.EMPTY_BUFFER;
/*      */     } 
/*      */     
/*  509 */     if (promise != null) {
/*  510 */       ctx.write(out, promise);
/*      */     } else {
/*  512 */       ctx.write(out);
/*      */     } 
/*      */     
/*  515 */     if (inUnwrap) {
/*  516 */       this.needsFlush = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private void wrapNonAppData(ChannelHandlerContext ctx, boolean inUnwrap) throws SSLException {
/*  521 */     ByteBuf out = null; try {
/*      */       SSLEngineResult result;
/*      */       do {
/*  524 */         if (out == null) {
/*  525 */           out = allocateOutNetBuf(ctx, 0);
/*      */         }
/*  527 */         result = wrap(this.engine, Unpooled.EMPTY_BUFFER, out);
/*      */         
/*  529 */         if (result.bytesProduced() > 0) {
/*  530 */           ctx.write(out);
/*  531 */           if (inUnwrap) {
/*  532 */             this.needsFlush = true;
/*      */           }
/*  534 */           out = null;
/*      */         } 
/*      */         
/*  537 */         switch (result.getHandshakeStatus()) {
/*      */           case null:
/*  539 */             setHandshakeSuccess();
/*      */             break;
/*      */           case BUFFER_OVERFLOW:
/*  542 */             runDelegatedTasks();
/*      */             break;
/*      */           case null:
/*  545 */             if (!inUnwrap) {
/*  546 */               unwrapNonAppData(ctx);
/*      */             }
/*      */             break;
/*      */           case null:
/*      */             break;
/*      */           case null:
/*  552 */             setHandshakeSuccessIfStillHandshaking();
/*      */ 
/*      */             
/*  555 */             if (!inUnwrap) {
/*  556 */               unwrapNonAppData(ctx);
/*      */             }
/*      */             break;
/*      */           default:
/*  560 */             throw new IllegalStateException("Unknown handshake status: " + result.getHandshakeStatus());
/*      */         } 
/*      */       
/*  563 */       } while (result.bytesProduced() != 0);
/*      */ 
/*      */     
/*      */     }
/*  567 */     catch (SSLException e) {
/*  568 */       setHandshakeFailure(e);
/*  569 */       throw e;
/*      */     } finally {
/*  571 */       if (out != null)
/*  572 */         out.release(); 
/*      */     } 
/*      */   }
/*      */   
/*      */   private SSLEngineResult wrap(SSLEngine engine, ByteBuf in, ByteBuf out) throws SSLException {
/*      */     SSLEngineResult result;
/*  578 */     ByteBuffer in0 = in.nioBuffer();
/*  579 */     if (!in0.isDirect()) {
/*  580 */       ByteBuffer newIn0 = ByteBuffer.allocateDirect(in0.remaining());
/*  581 */       newIn0.put(in0).flip();
/*  582 */       in0 = newIn0;
/*      */     } 
/*      */     
/*      */     while (true) {
/*  586 */       ByteBuffer out0 = out.nioBuffer(out.writerIndex(), out.writableBytes());
/*  587 */       result = engine.wrap(in0, out0);
/*  588 */       in.skipBytes(result.bytesConsumed());
/*  589 */       out.writerIndex(out.writerIndex() + result.bytesProduced());
/*      */       
/*  591 */       switch (result.getStatus()) {
/*      */         case BUFFER_OVERFLOW:
/*  593 */           out.ensureWritable(this.maxPacketBufferSize); continue;
/*      */       }  break;
/*      */     } 
/*  596 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/*  605 */     setHandshakeFailure(CHANNEL_CLOSED);
/*  606 */     super.channelInactive(ctx);
/*      */   }
/*      */ 
/*      */   
/*      */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/*  611 */     if (ignoreException(cause)) {
/*      */ 
/*      */       
/*  614 */       if (logger.isDebugEnabled()) {
/*  615 */         logger.debug("Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", cause);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  622 */       if (ctx.channel().isActive()) {
/*  623 */         ctx.close();
/*      */       }
/*      */     } else {
/*  626 */       ctx.fireExceptionCaught(cause);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean ignoreException(Throwable t) {
/*  640 */     if (!(t instanceof SSLException) && t instanceof java.io.IOException && this.sslCloseFuture.isDone()) {
/*  641 */       String message = String.valueOf(t.getMessage()).toLowerCase();
/*      */ 
/*      */ 
/*      */       
/*  645 */       if (IGNORABLE_ERROR_MESSAGE.matcher(message).matches()) {
/*  646 */         return true;
/*      */       }
/*      */ 
/*      */       
/*  650 */       StackTraceElement[] elements = t.getStackTrace();
/*  651 */       for (StackTraceElement element : elements) {
/*  652 */         String classname = element.getClassName();
/*  653 */         String methodname = element.getMethodName();
/*      */ 
/*      */         
/*  656 */         if (!classname.startsWith("io.netty."))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  661 */           if ("read".equals(methodname)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  667 */             if (IGNORABLE_CLASS_IN_STACK.matcher(classname).matches()) {
/*  668 */               return true;
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             try {
/*  675 */               Class<?> clazz = PlatformDependent.getClassLoader(getClass()).loadClass(classname);
/*      */               
/*  677 */               if (SocketChannel.class.isAssignableFrom(clazz) || DatagramChannel.class.isAssignableFrom(clazz))
/*      */               {
/*  679 */                 return true;
/*      */               }
/*      */ 
/*      */               
/*  683 */               if (PlatformDependent.javaVersion() >= 7 && "com.sun.nio.sctp.SctpChannel".equals(clazz.getSuperclass().getName()))
/*      */               {
/*  685 */                 return true;
/*      */               }
/*  687 */             } catch (ClassNotFoundException e) {}
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  693 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEncrypted(ByteBuf buffer) {
/*  709 */     if (buffer.readableBytes() < 5) {
/*  710 */       throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
/*      */     }
/*  712 */     return (getEncryptedPacketLength(buffer, buffer.readerIndex()) != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getEncryptedPacketLength(ByteBuf buffer, int offset) {
/*      */     boolean tls;
/*  729 */     int packetLength = 0;
/*      */ 
/*      */ 
/*      */     
/*  733 */     switch (buffer.getUnsignedByte(offset)) {
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*  738 */         tls = true;
/*      */         break;
/*      */       
/*      */       default:
/*  742 */         tls = false;
/*      */         break;
/*      */     } 
/*  745 */     if (tls) {
/*      */       
/*  747 */       int majorVersion = buffer.getUnsignedByte(offset + 1);
/*  748 */       if (majorVersion == 3) {
/*      */         
/*  750 */         packetLength = buffer.getUnsignedShort(offset + 3) + 5;
/*  751 */         if (packetLength <= 5)
/*      */         {
/*  753 */           tls = false;
/*      */         }
/*      */       } else {
/*      */         
/*  757 */         tls = false;
/*      */       } 
/*      */     } 
/*      */     
/*  761 */     if (!tls) {
/*      */       
/*  763 */       boolean sslv2 = true;
/*  764 */       int headerLength = ((buffer.getUnsignedByte(offset) & 0x80) != 0) ? 2 : 3;
/*  765 */       int majorVersion = buffer.getUnsignedByte(offset + headerLength + 1);
/*  766 */       if (majorVersion == 2 || majorVersion == 3) {
/*      */         
/*  768 */         if (headerLength == 2) {
/*  769 */           packetLength = (buffer.getShort(offset) & Short.MAX_VALUE) + 2;
/*      */         } else {
/*  771 */           packetLength = (buffer.getShort(offset) & 0x3FFF) + 3;
/*      */         } 
/*  773 */         if (packetLength <= headerLength) {
/*  774 */           sslv2 = false;
/*      */         }
/*      */       } else {
/*  777 */         sslv2 = false;
/*      */       } 
/*      */       
/*  780 */       if (!sslv2) {
/*  781 */         return -1;
/*      */       }
/*      */     } 
/*  784 */     return packetLength;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws SSLException {
/*  790 */     int startOffset = in.readerIndex();
/*  791 */     int endOffset = in.writerIndex();
/*  792 */     int offset = startOffset;
/*  793 */     int totalLength = 0;
/*      */ 
/*      */     
/*  796 */     if (this.packetLength > 0) {
/*  797 */       if (endOffset - startOffset < this.packetLength) {
/*      */         return;
/*      */       }
/*  800 */       offset += this.packetLength;
/*  801 */       totalLength = this.packetLength;
/*  802 */       this.packetLength = 0;
/*      */     } 
/*      */ 
/*      */     
/*  806 */     boolean nonSslRecord = false;
/*      */     
/*  808 */     while (totalLength < 18713) {
/*  809 */       int readableBytes = endOffset - offset;
/*  810 */       if (readableBytes < 5) {
/*      */         break;
/*      */       }
/*      */       
/*  814 */       int packetLength = getEncryptedPacketLength(in, offset);
/*  815 */       if (packetLength == -1) {
/*  816 */         nonSslRecord = true;
/*      */         
/*      */         break;
/*      */       } 
/*  820 */       assert packetLength > 0;
/*      */       
/*  822 */       if (packetLength > readableBytes) {
/*      */         
/*  824 */         this.packetLength = packetLength;
/*      */         
/*      */         break;
/*      */       } 
/*  828 */       int newTotalLength = totalLength + packetLength;
/*  829 */       if (newTotalLength > 18713) {
/*      */         break;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  836 */       offset += packetLength;
/*  837 */       totalLength = newTotalLength;
/*      */     } 
/*      */     
/*  840 */     if (totalLength > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  852 */       in.skipBytes(totalLength);
/*  853 */       ByteBuffer inNetBuf = in.nioBuffer(startOffset, totalLength);
/*  854 */       unwrap(ctx, inNetBuf, totalLength);
/*  855 */       assert !inNetBuf.hasRemaining() || this.engine.isInboundDone();
/*      */     } 
/*      */     
/*  858 */     if (nonSslRecord) {
/*      */       
/*  860 */       NotSslRecordException e = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(in));
/*      */       
/*  862 */       in.skipBytes(in.readableBytes());
/*  863 */       ctx.fireExceptionCaught(e);
/*  864 */       setHandshakeFailure(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/*  870 */     if (this.needsFlush) {
/*  871 */       this.needsFlush = false;
/*  872 */       ctx.flush();
/*      */     } 
/*  874 */     super.channelReadComplete(ctx);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unwrapNonAppData(ChannelHandlerContext ctx) throws SSLException {
/*  881 */     unwrap(ctx, Unpooled.EMPTY_BUFFER.nioBuffer(), 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void unwrap(ChannelHandlerContext ctx, ByteBuffer packet, int initialOutAppBufCapacity) throws SSLException {
/*      */     ByteBuffer oldPacket;
/*      */     ByteBuf newPacket;
/*  893 */     int oldPos = packet.position();
/*  894 */     if (this.wantsInboundHeapBuffer && packet.isDirect()) {
/*  895 */       newPacket = ctx.alloc().heapBuffer(packet.limit() - oldPos);
/*  896 */       newPacket.writeBytes(packet);
/*  897 */       oldPacket = packet;
/*  898 */       packet = newPacket.nioBuffer();
/*      */     } else {
/*  900 */       oldPacket = null;
/*  901 */       newPacket = null;
/*      */     } 
/*      */     
/*  904 */     boolean wrapLater = false;
/*  905 */     ByteBuf decodeOut = allocate(ctx, initialOutAppBufCapacity);
/*      */     try {
/*      */       while (true) {
/*  908 */         SSLEngineResult result = unwrap(this.engine, packet, decodeOut);
/*  909 */         SSLEngineResult.Status status = result.getStatus();
/*  910 */         SSLEngineResult.HandshakeStatus handshakeStatus = result.getHandshakeStatus();
/*  911 */         int produced = result.bytesProduced();
/*  912 */         int consumed = result.bytesConsumed();
/*      */         
/*  914 */         if (status == SSLEngineResult.Status.CLOSED) {
/*      */           
/*  916 */           this.sslCloseFuture.trySuccess(ctx.channel());
/*      */           
/*      */           break;
/*      */         } 
/*  920 */         switch (handshakeStatus) {
/*      */           case null:
/*      */             break;
/*      */           case null:
/*  924 */             wrapNonAppData(ctx, true);
/*      */             break;
/*      */           case BUFFER_OVERFLOW:
/*  927 */             runDelegatedTasks();
/*      */             break;
/*      */           case null:
/*  930 */             setHandshakeSuccess();
/*  931 */             wrapLater = true;
/*      */             continue;
/*      */           case null:
/*  934 */             if (setHandshakeSuccessIfStillHandshaking()) {
/*  935 */               wrapLater = true;
/*      */               continue;
/*      */             } 
/*  938 */             if (this.flushedBeforeHandshakeDone) {
/*      */ 
/*      */ 
/*      */               
/*  942 */               this.flushedBeforeHandshakeDone = false;
/*  943 */               wrapLater = true;
/*      */             } 
/*      */             break;
/*      */           
/*      */           default:
/*  948 */             throw new IllegalStateException("Unknown handshake status: " + handshakeStatus);
/*      */         } 
/*      */         
/*  951 */         if (status == SSLEngineResult.Status.BUFFER_UNDERFLOW || (consumed == 0 && produced == 0)) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */       
/*  956 */       if (wrapLater) {
/*  957 */         wrap(ctx, true);
/*      */       }
/*  959 */     } catch (SSLException e) {
/*  960 */       setHandshakeFailure(e);
/*  961 */       throw e;
/*      */     }
/*      */     finally {
/*      */       
/*  965 */       if (newPacket != null) {
/*  966 */         oldPacket.position(oldPos + packet.position());
/*  967 */         newPacket.release();
/*      */       } 
/*      */       
/*  970 */       if (decodeOut.isReadable()) {
/*  971 */         ctx.fireChannelRead(decodeOut);
/*      */       } else {
/*  973 */         decodeOut.release();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private static SSLEngineResult unwrap(SSLEngine engine, ByteBuffer in, ByteBuf out) throws SSLException {
/*      */     SSLEngineResult result;
/*  979 */     int overflows = 0; while (true) {
/*      */       int max;
/*  981 */       ByteBuffer out0 = out.nioBuffer(out.writerIndex(), out.writableBytes());
/*  982 */       result = engine.unwrap(in, out0);
/*  983 */       out.writerIndex(out.writerIndex() + result.bytesProduced());
/*  984 */       switch (result.getStatus()) {
/*      */         case BUFFER_OVERFLOW:
/*  986 */           max = engine.getSession().getApplicationBufferSize();
/*  987 */           switch (overflows++) {
/*      */             case 0:
/*  989 */               out.ensureWritable(Math.min(max, in.remaining()));
/*      */               continue;
/*      */           } 
/*  992 */           out.ensureWritable(max); continue;
/*      */       } 
/*      */       break;
/*      */     } 
/*  996 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void runDelegatedTasks() {
/* 1008 */     if (this.delegatedTaskExecutor == ImmediateExecutor.INSTANCE) {
/*      */       while (true) {
/* 1010 */         Runnable task = this.engine.getDelegatedTask();
/* 1011 */         if (task == null) {
/*      */           break;
/*      */         }
/*      */         
/* 1015 */         task.run();
/*      */       } 
/*      */     } else {
/* 1018 */       final List<Runnable> tasks = new ArrayList<Runnable>(2);
/*      */       while (true) {
/* 1020 */         Runnable task = this.engine.getDelegatedTask();
/* 1021 */         if (task == null) {
/*      */           break;
/*      */         }
/*      */         
/* 1025 */         tasks.add(task);
/*      */       } 
/*      */       
/* 1028 */       if (tasks.isEmpty()) {
/*      */         return;
/*      */       }
/*      */       
/* 1032 */       final CountDownLatch latch = new CountDownLatch(1);
/* 1033 */       this.delegatedTaskExecutor.execute(new Runnable()
/*      */           {
/*      */             public void run() {
/*      */               try {
/* 1037 */                 for (Runnable task : tasks) {
/* 1038 */                   task.run();
/*      */                 }
/* 1040 */               } catch (Exception e) {
/* 1041 */                 SslHandler.this.ctx.fireExceptionCaught(e);
/*      */               } finally {
/* 1043 */                 latch.countDown();
/*      */               } 
/*      */             }
/*      */           });
/*      */       
/* 1048 */       boolean interrupted = false;
/* 1049 */       while (latch.getCount() != 0L) {
/*      */         try {
/* 1051 */           latch.await();
/* 1052 */         } catch (InterruptedException e) {
/*      */           
/* 1054 */           interrupted = true;
/*      */         } 
/*      */       } 
/*      */       
/* 1058 */       if (interrupted) {
/* 1059 */         Thread.currentThread().interrupt();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean setHandshakeSuccessIfStillHandshaking() {
/* 1072 */     if (!this.handshakePromise.isDone()) {
/* 1073 */       setHandshakeSuccess();
/* 1074 */       return true;
/*      */     } 
/* 1076 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setHandshakeSuccess() {
/* 1084 */     String cipherSuite = String.valueOf(this.engine.getSession().getCipherSuite());
/* 1085 */     if (!this.wantsDirectBuffer && (cipherSuite.contains("_GCM_") || cipherSuite.contains("-GCM-"))) {
/* 1086 */       this.wantsInboundHeapBuffer = true;
/*      */     }
/*      */     
/* 1089 */     if (this.handshakePromise.trySuccess(this.ctx.channel())) {
/* 1090 */       if (logger.isDebugEnabled()) {
/* 1091 */         logger.debug(this.ctx.channel() + " HANDSHAKEN: " + this.engine.getSession().getCipherSuite());
/*      */       }
/* 1093 */       this.ctx.fireUserEventTriggered(SslHandshakeCompletionEvent.SUCCESS);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setHandshakeFailure(Throwable cause) {
/* 1103 */     this.engine.closeOutbound();
/*      */     
/*      */     try {
/* 1106 */       this.engine.closeInbound();
/* 1107 */     } catch (SSLException e) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1112 */       String msg = e.getMessage();
/* 1113 */       if (msg == null || !msg.contains("possible truncation attack")) {
/* 1114 */         logger.debug("SSLEngine.closeInbound() raised an exception.", e);
/*      */       }
/*      */     } 
/* 1117 */     notifyHandshakeFailure(cause);
/* 1118 */     this.pendingUnencryptedWrites.removeAndFailAll(cause);
/*      */   }
/*      */   
/*      */   private void notifyHandshakeFailure(Throwable cause) {
/* 1122 */     if (this.handshakePromise.tryFailure(cause)) {
/* 1123 */       this.ctx.fireUserEventTriggered(new SslHandshakeCompletionEvent(cause));
/* 1124 */       this.ctx.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void closeOutboundAndChannel(ChannelHandlerContext ctx, ChannelPromise promise, boolean disconnect) throws Exception {
/* 1130 */     if (!ctx.channel().isActive()) {
/* 1131 */       if (disconnect) {
/* 1132 */         ctx.disconnect(promise);
/*      */       } else {
/* 1134 */         ctx.close(promise);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1139 */     this.engine.closeOutbound();
/*      */     
/* 1141 */     ChannelPromise closeNotifyFuture = ctx.newPromise();
/* 1142 */     write(ctx, Unpooled.EMPTY_BUFFER, closeNotifyFuture);
/* 1143 */     flush(ctx);
/* 1144 */     safeClose(ctx, (ChannelFuture)closeNotifyFuture, promise);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 1149 */     this.ctx = ctx;
/* 1150 */     this.pendingUnencryptedWrites = new PendingWriteQueue(ctx);
/*      */     
/* 1152 */     if (ctx.channel().isActive() && this.engine.getUseClientMode())
/*      */     {
/*      */       
/* 1155 */       handshake();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Future<Channel> handshake() {
/*      */     final ScheduledFuture<?> timeoutFuture;
/* 1164 */     if (this.handshakeTimeoutMillis > 0L) {
/* 1165 */       ScheduledFuture scheduledFuture = this.ctx.executor().schedule(new Runnable()
/*      */           {
/*      */             public void run() {
/* 1168 */               if (SslHandler.this.handshakePromise.isDone()) {
/*      */                 return;
/*      */               }
/* 1171 */               SslHandler.this.notifyHandshakeFailure(SslHandler.HANDSHAKE_TIMED_OUT);
/*      */             }
/*      */           },  this.handshakeTimeoutMillis, TimeUnit.MILLISECONDS);
/*      */     } else {
/* 1175 */       timeoutFuture = null;
/*      */     } 
/*      */     
/* 1178 */     this.handshakePromise.addListener(new GenericFutureListener<Future<Channel>>()
/*      */         {
/*      */           public void operationComplete(Future<Channel> f) throws Exception {
/* 1181 */             if (timeoutFuture != null) {
/* 1182 */               timeoutFuture.cancel(false);
/*      */             }
/*      */           }
/*      */         });
/*      */     try {
/* 1187 */       this.engine.beginHandshake();
/* 1188 */       wrapNonAppData(this.ctx, false);
/* 1189 */       this.ctx.flush();
/* 1190 */     } catch (Exception e) {
/* 1191 */       notifyHandshakeFailure(e);
/*      */     } 
/* 1193 */     return (Future<Channel>)this.handshakePromise;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void channelActive(final ChannelHandlerContext ctx) throws Exception {
/* 1201 */     if (!this.startTls && this.engine.getUseClientMode())
/*      */     {
/*      */       
/* 1204 */       handshake().addListener(new GenericFutureListener<Future<Channel>>()
/*      */           {
/*      */             public void operationComplete(Future<Channel> future) throws Exception {
/* 1207 */               if (!future.isSuccess()) {
/* 1208 */                 SslHandler.logger.debug("Failed to complete handshake", future.cause());
/* 1209 */                 ctx.close();
/*      */               } 
/*      */             }
/*      */           });
/*      */     }
/* 1214 */     ctx.fireChannelActive();
/*      */   }
/*      */ 
/*      */   
/*      */   private void safeClose(final ChannelHandlerContext ctx, ChannelFuture flushFuture, final ChannelPromise promise) {
/*      */     final ScheduledFuture<?> timeoutFuture;
/* 1220 */     if (!ctx.channel().isActive()) {
/* 1221 */       ctx.close(promise);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1226 */     if (this.closeNotifyTimeoutMillis > 0L) {
/*      */       
/* 1228 */       ScheduledFuture scheduledFuture = ctx.executor().schedule(new Runnable()
/*      */           {
/*      */             public void run() {
/* 1231 */               SslHandler.logger.warn(ctx.channel() + " last write attempt timed out." + " Force-closing the connection.");
/*      */ 
/*      */               
/* 1234 */               ctx.close(promise);
/*      */             }
/*      */           },  this.closeNotifyTimeoutMillis, TimeUnit.MILLISECONDS);
/*      */     } else {
/* 1238 */       timeoutFuture = null;
/*      */     } 
/*      */ 
/*      */     
/* 1242 */     flushFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*      */         {
/*      */           public void operationComplete(ChannelFuture f) throws Exception
/*      */           {
/* 1246 */             if (timeoutFuture != null) {
/* 1247 */               timeoutFuture.cancel(false);
/*      */             }
/*      */ 
/*      */             
/* 1251 */             ctx.close(promise);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuf allocate(ChannelHandlerContext ctx, int capacity) {
/* 1261 */     ByteBufAllocator alloc = ctx.alloc();
/* 1262 */     if (this.wantsDirectBuffer) {
/* 1263 */       return alloc.directBuffer(capacity);
/*      */     }
/* 1265 */     return alloc.buffer(capacity);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteBuf allocateOutNetBuf(ChannelHandlerContext ctx, int pendingBytes) {
/* 1274 */     if (this.wantsLargeOutboundNetworkBuffer) {
/* 1275 */       return allocate(ctx, this.maxPacketBufferSize);
/*      */     }
/* 1277 */     return allocate(ctx, Math.min(pendingBytes + 2329, this.maxPacketBufferSize));
/*      */   }
/*      */ 
/*      */   
/*      */   private final class LazyChannelPromise
/*      */     extends DefaultPromise<Channel>
/*      */   {
/*      */     private LazyChannelPromise() {}
/*      */     
/*      */     protected EventExecutor executor() {
/* 1287 */       if (SslHandler.this.ctx == null) {
/* 1288 */         throw new IllegalStateException();
/*      */       }
/* 1290 */       return SslHandler.this.ctx.executor();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\ssl\SslHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */