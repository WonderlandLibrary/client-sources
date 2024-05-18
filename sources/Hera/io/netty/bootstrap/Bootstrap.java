/*     */ package io.netty.bootstrap;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelOption;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ public final class Bootstrap
/*     */   extends AbstractBootstrap<Bootstrap, Channel>
/*     */ {
/*  43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Bootstrap.class);
/*     */   
/*     */   private volatile SocketAddress remoteAddress;
/*     */   
/*     */   public Bootstrap() {}
/*     */   
/*     */   private Bootstrap(Bootstrap bootstrap) {
/*  50 */     super(bootstrap);
/*  51 */     this.remoteAddress = bootstrap.remoteAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bootstrap remoteAddress(SocketAddress remoteAddress) {
/*  59 */     this.remoteAddress = remoteAddress;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bootstrap remoteAddress(String inetHost, int inetPort) {
/*  67 */     this.remoteAddress = new InetSocketAddress(inetHost, inetPort);
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bootstrap remoteAddress(InetAddress inetHost, int inetPort) {
/*  75 */     this.remoteAddress = new InetSocketAddress(inetHost, inetPort);
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect() {
/*  83 */     validate();
/*  84 */     SocketAddress remoteAddress = this.remoteAddress;
/*  85 */     if (remoteAddress == null) {
/*  86 */       throw new IllegalStateException("remoteAddress not set");
/*     */     }
/*     */     
/*  89 */     return doConnect(remoteAddress, localAddress());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(String inetHost, int inetPort) {
/*  96 */     return connect(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(InetAddress inetHost, int inetPort) {
/* 103 */     return connect(new InetSocketAddress(inetHost, inetPort));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress) {
/* 110 */     if (remoteAddress == null) {
/* 111 */       throw new NullPointerException("remoteAddress");
/*     */     }
/*     */     
/* 114 */     validate();
/* 115 */     return doConnect(remoteAddress, localAddress());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
/* 122 */     if (remoteAddress == null) {
/* 123 */       throw new NullPointerException("remoteAddress");
/*     */     }
/* 125 */     validate();
/* 126 */     return doConnect(remoteAddress, localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelFuture doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress) {
/* 133 */     final ChannelFuture regFuture = initAndRegister();
/* 134 */     final Channel channel = regFuture.channel();
/* 135 */     if (regFuture.cause() != null) {
/* 136 */       return regFuture;
/*     */     }
/*     */     
/* 139 */     final ChannelPromise promise = channel.newPromise();
/* 140 */     if (regFuture.isDone()) {
/* 141 */       doConnect0(regFuture, channel, remoteAddress, localAddress, promise);
/*     */     } else {
/* 143 */       regFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */           {
/*     */             public void operationComplete(ChannelFuture future) throws Exception {
/* 146 */               Bootstrap.doConnect0(regFuture, channel, remoteAddress, localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 151 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void doConnect0(final ChannelFuture regFuture, final Channel channel, final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
/* 160 */     channel.eventLoop().execute(new Runnable()
/*     */         {
/*     */           public void run() {
/* 163 */             if (regFuture.isSuccess()) {
/* 164 */               if (localAddress == null) {
/* 165 */                 channel.connect(remoteAddress, promise);
/*     */               } else {
/* 167 */                 channel.connect(remoteAddress, localAddress, promise);
/*     */               } 
/* 169 */               promise.addListener((GenericFutureListener)ChannelFutureListener.CLOSE_ON_FAILURE);
/*     */             } else {
/* 171 */               promise.setFailure(regFuture.cause());
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void init(Channel channel) throws Exception {
/* 180 */     ChannelPipeline p = channel.pipeline();
/* 181 */     p.addLast(new ChannelHandler[] { handler() });
/*     */     
/* 183 */     Map<ChannelOption<?>, Object> options = options();
/* 184 */     synchronized (options) {
/* 185 */       for (Map.Entry<ChannelOption<?>, Object> e : options.entrySet()) {
/*     */         try {
/* 187 */           if (!channel.config().setOption(e.getKey(), e.getValue())) {
/* 188 */             logger.warn("Unknown channel option: " + e);
/*     */           }
/* 190 */         } catch (Throwable t) {
/* 191 */           logger.warn("Failed to set a channel option: " + channel, t);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     Map<AttributeKey<?>, Object> attrs = attrs();
/* 197 */     synchronized (attrs) {
/* 198 */       for (Map.Entry<AttributeKey<?>, Object> e : attrs.entrySet()) {
/* 199 */         channel.attr(e.getKey()).set(e.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Bootstrap validate() {
/* 206 */     super.validate();
/* 207 */     if (handler() == null) {
/* 208 */       throw new IllegalStateException("handler not set");
/*     */     }
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Bootstrap clone() {
/* 216 */     return new Bootstrap(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 221 */     if (this.remoteAddress == null) {
/* 222 */       return super.toString();
/*     */     }
/*     */     
/* 225 */     StringBuilder buf = new StringBuilder(super.toString());
/* 226 */     buf.setLength(buf.length() - 1);
/* 227 */     buf.append(", remoteAddress: ");
/* 228 */     buf.append(this.remoteAddress);
/* 229 */     buf.append(')');
/*     */     
/* 231 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\bootstrap\Bootstrap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */