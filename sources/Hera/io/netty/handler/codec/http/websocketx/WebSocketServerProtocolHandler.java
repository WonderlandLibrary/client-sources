/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.AttributeKey;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.util.List;
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
/*     */ public class WebSocketServerProtocolHandler
/*     */   extends WebSocketProtocolHandler
/*     */ {
/*     */   public enum ServerHandshakeStateEvent
/*     */   {
/*  60 */     HANDSHAKE_COMPLETE;
/*     */   }
/*     */   
/*  63 */   private static final AttributeKey<WebSocketServerHandshaker> HANDSHAKER_ATTR_KEY = AttributeKey.valueOf(WebSocketServerHandshaker.class.getName() + ".HANDSHAKER");
/*     */   
/*     */   private final String websocketPath;
/*     */   
/*     */   private final String subprotocols;
/*     */   private final boolean allowExtensions;
/*     */   private final int maxFramePayloadLength;
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath) {
/*  72 */     this(websocketPath, null, false);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols) {
/*  76 */     this(websocketPath, subprotocols, false);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions) {
/*  80 */     this(websocketPath, subprotocols, allowExtensions, 65536);
/*     */   }
/*     */ 
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize) {
/*  85 */     this.websocketPath = websocketPath;
/*  86 */     this.subprotocols = subprotocols;
/*  87 */     this.allowExtensions = allowExtensions;
/*  88 */     this.maxFramePayloadLength = maxFrameSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) {
/*  93 */     ChannelPipeline cp = ctx.pipeline();
/*  94 */     if (cp.get(WebSocketServerProtocolHandshakeHandler.class) == null)
/*     */     {
/*  96 */       ctx.pipeline().addBefore(ctx.name(), WebSocketServerProtocolHandshakeHandler.class.getName(), (ChannelHandler)new WebSocketServerProtocolHandshakeHandler(this.websocketPath, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
/* 104 */     if (frame instanceof CloseWebSocketFrame) {
/* 105 */       WebSocketServerHandshaker handshaker = getHandshaker(ctx);
/* 106 */       if (handshaker != null) {
/* 107 */         frame.retain();
/* 108 */         handshaker.close(ctx.channel(), (CloseWebSocketFrame)frame);
/*     */       } else {
/* 110 */         ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */       } 
/*     */       return;
/*     */     } 
/* 114 */     super.decode(ctx, frame, out);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 119 */     if (cause instanceof WebSocketHandshakeException) {
/* 120 */       DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer(cause.getMessage().getBytes()));
/*     */       
/* 122 */       ctx.channel().writeAndFlush(defaultFullHttpResponse).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */     } else {
/* 124 */       ctx.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   static WebSocketServerHandshaker getHandshaker(ChannelHandlerContext ctx) {
/* 129 */     return (WebSocketServerHandshaker)ctx.attr(HANDSHAKER_ATTR_KEY).get();
/*     */   }
/*     */   
/*     */   static void setHandshaker(ChannelHandlerContext ctx, WebSocketServerHandshaker handshaker) {
/* 133 */     ctx.attr(HANDSHAKER_ATTR_KEY).set(handshaker);
/*     */   }
/*     */   
/*     */   static ChannelHandler forbiddenHttpRequestResponder() {
/* 137 */     return (ChannelHandler)new ChannelInboundHandlerAdapter()
/*     */       {
/*     */         public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 140 */           if (msg instanceof FullHttpRequest) {
/* 141 */             ((FullHttpRequest)msg).release();
/* 142 */             DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
/*     */             
/* 144 */             ctx.channel().writeAndFlush(defaultFullHttpResponse);
/*     */           } else {
/* 146 */             ctx.fireChannelRead(msg);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */