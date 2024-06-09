/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.DefaultHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
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
/*     */ public class WebSocketServerHandshakerFactory
/*     */ {
/*     */   private final String webSocketURL;
/*     */   private final String subprotocols;
/*     */   private final boolean allowExtensions;
/*     */   private final int maxFramePayloadLength;
/*     */   
/*     */   public WebSocketServerHandshakerFactory(String webSocketURL, String subprotocols, boolean allowExtensions) {
/*  55 */     this(webSocketURL, subprotocols, allowExtensions, 65536);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerHandshakerFactory(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength) {
/*  75 */     this.webSocketURL = webSocketURL;
/*  76 */     this.subprotocols = subprotocols;
/*  77 */     this.allowExtensions = allowExtensions;
/*  78 */     this.maxFramePayloadLength = maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerHandshaker newHandshaker(HttpRequest req) {
/*  89 */     String version = req.headers().get("Sec-WebSocket-Version");
/*  90 */     if (version != null) {
/*  91 */       if (version.equals(WebSocketVersion.V13.toHttpHeaderValue()))
/*     */       {
/*  93 */         return new WebSocketServerHandshaker13(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
/*     */       }
/*  95 */       if (version.equals(WebSocketVersion.V08.toHttpHeaderValue()))
/*     */       {
/*  97 */         return new WebSocketServerHandshaker08(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
/*     */       }
/*  99 */       if (version.equals(WebSocketVersion.V07.toHttpHeaderValue()))
/*     */       {
/* 101 */         return new WebSocketServerHandshaker07(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
/*     */       }
/*     */       
/* 104 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 108 */     return new WebSocketServerHandshaker00(this.webSocketURL, this.subprotocols, this.maxFramePayloadLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void sendUnsupportedWebSocketVersionResponse(Channel channel) {
/* 117 */     sendUnsupportedVersionResponse(channel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChannelFuture sendUnsupportedVersionResponse(Channel channel) {
/* 124 */     return sendUnsupportedVersionResponse(channel, channel.newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ChannelFuture sendUnsupportedVersionResponse(Channel channel, ChannelPromise promise) {
/* 131 */     DefaultHttpResponse defaultHttpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UPGRADE_REQUIRED);
/*     */ 
/*     */     
/* 134 */     defaultHttpResponse.headers().set("Sec-WebSocket-Version", WebSocketVersion.V13.toHttpHeaderValue());
/* 135 */     return channel.write(defaultHttpResponse, promise);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshakerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */