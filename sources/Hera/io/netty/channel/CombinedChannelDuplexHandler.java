/*     */ package io.netty.channel;
/*     */ 
/*     */ import java.net.SocketAddress;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CombinedChannelDuplexHandler<I extends ChannelInboundHandler, O extends ChannelOutboundHandler>
/*     */   extends ChannelDuplexHandler
/*     */ {
/*     */   private I inboundHandler;
/*     */   private O outboundHandler;
/*     */   
/*     */   protected CombinedChannelDuplexHandler() {}
/*     */   
/*     */   public CombinedChannelDuplexHandler(I inboundHandler, O outboundHandler) {
/*  41 */     init(inboundHandler, outboundHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void init(I inboundHandler, O outboundHandler) {
/*  53 */     validate(inboundHandler, outboundHandler);
/*  54 */     this.inboundHandler = inboundHandler;
/*  55 */     this.outboundHandler = outboundHandler;
/*     */   }
/*     */   
/*     */   private void validate(I inboundHandler, O outboundHandler) {
/*  59 */     if (this.inboundHandler != null) {
/*  60 */       throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with non-default constructor.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  65 */     if (inboundHandler == null) {
/*  66 */       throw new NullPointerException("inboundHandler");
/*     */     }
/*  68 */     if (outboundHandler == null) {
/*  69 */       throw new NullPointerException("outboundHandler");
/*     */     }
/*  71 */     if (inboundHandler instanceof ChannelOutboundHandler) {
/*  72 */       throw new IllegalArgumentException("inboundHandler must not implement " + ChannelOutboundHandler.class.getSimpleName() + " to get combined.");
/*     */     }
/*     */ 
/*     */     
/*  76 */     if (outboundHandler instanceof ChannelInboundHandler) {
/*  77 */       throw new IllegalArgumentException("outboundHandler must not implement " + ChannelInboundHandler.class.getSimpleName() + " to get combined.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final I inboundHandler() {
/*  84 */     return this.inboundHandler;
/*     */   }
/*     */   
/*     */   protected final O outboundHandler() {
/*  88 */     return this.outboundHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/*  93 */     if (this.inboundHandler == null) {
/*  94 */       throw new IllegalStateException("init() must be invoked before being added to a " + ChannelPipeline.class.getSimpleName() + " if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with the default constructor.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 100 */       this.inboundHandler.handlerAdded(ctx);
/*     */     } finally {
/* 102 */       this.outboundHandler.handlerAdded(ctx);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/*     */     try {
/* 109 */       this.inboundHandler.handlerRemoved(ctx);
/*     */     } finally {
/* 111 */       this.outboundHandler.handlerRemoved(ctx);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 117 */     this.inboundHandler.channelRegistered(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
/* 122 */     this.inboundHandler.channelUnregistered(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelActive(ChannelHandlerContext ctx) throws Exception {
/* 127 */     this.inboundHandler.channelActive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 132 */     this.inboundHandler.channelInactive(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 137 */     this.inboundHandler.exceptionCaught(ctx, cause);
/*     */   }
/*     */ 
/*     */   
/*     */   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
/* 142 */     this.inboundHandler.userEventTriggered(ctx, evt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 147 */     this.inboundHandler.channelRead(ctx, msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
/* 152 */     this.inboundHandler.channelReadComplete(ctx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 159 */     this.outboundHandler.bind(ctx, localAddress, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
/* 167 */     this.outboundHandler.connect(ctx, remoteAddress, localAddress, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 172 */     this.outboundHandler.disconnect(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 177 */     this.outboundHandler.close(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
/* 182 */     this.outboundHandler.deregister(ctx, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(ChannelHandlerContext ctx) throws Exception {
/* 187 */     this.outboundHandler.read(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 192 */     this.outboundHandler.write(ctx, msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void flush(ChannelHandlerContext ctx) throws Exception {
/* 197 */     this.outboundHandler.flush(ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
/* 202 */     this.inboundHandler.channelWritabilityChanged(ctx);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\CombinedChannelDuplexHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */