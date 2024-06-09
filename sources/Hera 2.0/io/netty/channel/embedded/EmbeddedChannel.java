/*     */ package io.netty.channel.embedded;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelMetadata;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.channel.DefaultChannelConfig;
/*     */ import io.netty.channel.EventLoop;
/*     */ import io.netty.util.ReferenceCountUtil;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.RecyclableArrayList;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmbeddedChannel
/*     */   extends AbstractChannel
/*     */ {
/*  47 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(EmbeddedChannel.class);
/*     */   
/*  49 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*  51 */   private final EmbeddedEventLoop loop = new EmbeddedEventLoop();
/*  52 */   private final ChannelConfig config = (ChannelConfig)new DefaultChannelConfig((Channel)this);
/*  53 */   private final SocketAddress localAddress = new EmbeddedSocketAddress();
/*  54 */   private final SocketAddress remoteAddress = new EmbeddedSocketAddress();
/*  55 */   private final Queue<Object> inboundMessages = new ArrayDeque();
/*  56 */   private final Queue<Object> outboundMessages = new ArrayDeque();
/*     */ 
/*     */   
/*     */   private Throwable lastException;
/*     */ 
/*     */   
/*     */   private int state;
/*     */ 
/*     */   
/*     */   public EmbeddedChannel(ChannelHandler... handlers) {
/*  66 */     super(null);
/*     */     
/*  68 */     if (handlers == null) {
/*  69 */       throw new NullPointerException("handlers");
/*     */     }
/*     */     
/*  72 */     int nHandlers = 0;
/*  73 */     ChannelPipeline p = pipeline();
/*  74 */     for (ChannelHandler h : handlers) {
/*  75 */       if (h == null) {
/*     */         break;
/*     */       }
/*  78 */       nHandlers++;
/*  79 */       p.addLast(new ChannelHandler[] { h });
/*     */     } 
/*     */     
/*  82 */     if (nHandlers == 0) {
/*  83 */       throw new IllegalArgumentException("handlers is empty.");
/*     */     }
/*     */     
/*  86 */     this.loop.register((Channel)this);
/*  87 */     p.addLast(new ChannelHandler[] { (ChannelHandler)new LastInboundHandler() });
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  92 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelConfig config() {
/*  97 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 102 */     return (this.state < 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 107 */     return (this.state == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Queue<Object> inboundMessages() {
/* 114 */     return this.inboundMessages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Queue<Object> lastInboundBuffer() {
/* 122 */     return inboundMessages();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Queue<Object> outboundMessages() {
/* 129 */     return this.outboundMessages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Queue<Object> lastOutboundBuffer() {
/* 137 */     return outboundMessages();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readInbound() {
/* 144 */     return this.inboundMessages.poll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object readOutbound() {
/* 151 */     return this.outboundMessages.poll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeInbound(Object... msgs) {
/* 162 */     ensureOpen();
/* 163 */     if (msgs.length == 0) {
/* 164 */       return !this.inboundMessages.isEmpty();
/*     */     }
/*     */     
/* 167 */     ChannelPipeline p = pipeline();
/* 168 */     for (Object m : msgs) {
/* 169 */       p.fireChannelRead(m);
/*     */     }
/* 171 */     p.fireChannelReadComplete();
/* 172 */     runPendingTasks();
/* 173 */     checkException();
/* 174 */     return !this.inboundMessages.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeOutbound(Object... msgs) {
/* 184 */     ensureOpen();
/* 185 */     if (msgs.length == 0) {
/* 186 */       return !this.outboundMessages.isEmpty();
/*     */     }
/*     */     
/* 189 */     RecyclableArrayList futures = RecyclableArrayList.newInstance(msgs.length);
/*     */     
/* 191 */     try { for (Object m : msgs) {
/* 192 */         if (m == null) {
/*     */           break;
/*     */         }
/* 195 */         futures.add(write(m));
/*     */       } 
/*     */       
/* 198 */       flush();
/*     */       
/* 200 */       int size = futures.size(); int i;
/* 201 */       for (i = 0; i < size; i++) {
/* 202 */         ChannelFuture future = (ChannelFuture)futures.get(i);
/* 203 */         assert future.isDone();
/* 204 */         if (future.cause() != null) {
/* 205 */           recordException(future.cause());
/*     */         }
/*     */       } 
/*     */       
/* 209 */       runPendingTasks();
/* 210 */       checkException();
/* 211 */       i = !this.outboundMessages.isEmpty() ? 1 : 0;
/*     */       
/* 213 */       return i; } finally { futures.recycle(); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean finish() {
/* 224 */     close();
/* 225 */     runPendingTasks();
/* 226 */     checkException();
/* 227 */     return (!this.inboundMessages.isEmpty() || !this.outboundMessages.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void runPendingTasks() {
/*     */     try {
/* 235 */       this.loop.runTasks();
/* 236 */     } catch (Exception e) {
/* 237 */       recordException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void recordException(Throwable cause) {
/* 242 */     if (this.lastException == null) {
/* 243 */       this.lastException = cause;
/*     */     } else {
/* 245 */       logger.warn("More than one exception was raised. Will report only the first one and log others.", cause);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkException() {
/* 255 */     Throwable t = this.lastException;
/* 256 */     if (t == null) {
/*     */       return;
/*     */     }
/*     */     
/* 260 */     this.lastException = null;
/*     */     
/* 262 */     PlatformDependent.throwException(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void ensureOpen() {
/* 269 */     if (!isOpen()) {
/* 270 */       recordException(new ClosedChannelException());
/* 271 */       checkException();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/* 277 */     return loop instanceof EmbeddedEventLoop;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 282 */     return isActive() ? this.localAddress : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 287 */     return isActive() ? this.remoteAddress : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRegister() throws Exception {
/* 292 */     this.state = 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 302 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 307 */     this.state = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/* 317 */     return new DefaultUnsafe();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     while (true) {
/* 323 */       Object msg = in.current();
/* 324 */       if (msg == null) {
/*     */         break;
/*     */       }
/*     */       
/* 328 */       ReferenceCountUtil.retain(msg);
/* 329 */       this.outboundMessages.add(msg);
/* 330 */       in.remove();
/*     */     } 
/*     */   }
/*     */   private class DefaultUnsafe extends AbstractChannel.AbstractUnsafe { private DefaultUnsafe() {
/* 334 */       super(EmbeddedChannel.this);
/*     */     }
/*     */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 337 */       safeSetSuccess(promise);
/*     */     } }
/*     */   
/*     */   private final class LastInboundHandler extends ChannelInboundHandlerAdapter {
/*     */     private LastInboundHandler() {}
/*     */     
/*     */     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 344 */       EmbeddedChannel.this.inboundMessages.add(msg);
/*     */     }
/*     */ 
/*     */     
/*     */     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 349 */       EmbeddedChannel.this.recordException(cause);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\embedded\EmbeddedChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */