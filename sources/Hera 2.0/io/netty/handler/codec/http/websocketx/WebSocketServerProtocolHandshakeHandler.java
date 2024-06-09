/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMessage;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.handler.ssl.SslHandler;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
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
/*     */ class WebSocketServerProtocolHandshakeHandler
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final String websocketPath;
/*     */   private final String subprotocols;
/*     */   private final boolean allowExtensions;
/*     */   private final int maxFramePayloadSize;
/*     */   
/*     */   WebSocketServerProtocolHandshakeHandler(String websocketPath, String subprotocols, boolean allowExtensions, int maxFrameSize) {
/*  47 */     this.websocketPath = websocketPath;
/*  48 */     this.subprotocols = subprotocols;
/*  49 */     this.allowExtensions = allowExtensions;
/*  50 */     this.maxFramePayloadSize = maxFrameSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
/*  55 */     FullHttpRequest req = (FullHttpRequest)msg;
/*     */     try {
/*  57 */       if (req.getMethod() != HttpMethod.GET) {
/*  58 */         sendHttpResponse(ctx, (HttpRequest)req, (HttpResponse)new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
/*     */         
/*     */         return;
/*     */       } 
/*  62 */       WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(ctx.pipeline(), (HttpRequest)req, this.websocketPath), this.subprotocols, this.allowExtensions, this.maxFramePayloadSize);
/*     */ 
/*     */       
/*  65 */       WebSocketServerHandshaker handshaker = wsFactory.newHandshaker((HttpRequest)req);
/*  66 */       if (handshaker == null) {
/*  67 */         WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
/*     */       } else {
/*  69 */         ChannelFuture handshakeFuture = handshaker.handshake(ctx.channel(), req);
/*  70 */         handshakeFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*     */             {
/*     */               public void operationComplete(ChannelFuture future) throws Exception {
/*  73 */                 if (!future.isSuccess()) {
/*  74 */                   ctx.fireExceptionCaught(future.cause());
/*     */                 } else {
/*  76 */                   ctx.fireUserEventTriggered(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
/*     */                 } 
/*     */               }
/*     */             });
/*     */         
/*  81 */         WebSocketServerProtocolHandler.setHandshaker(ctx, handshaker);
/*  82 */         ctx.pipeline().replace((ChannelHandler)this, "WS403Responder", WebSocketServerProtocolHandler.forbiddenHttpRequestResponder());
/*     */       } 
/*     */     } finally {
/*     */       
/*  86 */       req.release();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
/*  91 */     ChannelFuture f = ctx.channel().writeAndFlush(res);
/*  92 */     if (!HttpHeaders.isKeepAlive((HttpMessage)req) || res.getStatus().code() != 200) {
/*  93 */       f.addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
/*  98 */     String protocol = "ws";
/*  99 */     if (cp.get(SslHandler.class) != null)
/*     */     {
/* 101 */       protocol = "wss";
/*     */     }
/* 103 */     return protocol + "://" + req.headers().get("Host") + path;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerProtocolHandshakeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */