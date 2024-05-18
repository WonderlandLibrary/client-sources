/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import io.netty.util.CharsetUtil;
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
/*     */ 
/*     */ 
/*     */ public class WebSocketServerHandshaker07
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*     */   public static final String WEBSOCKET_07_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */   private final boolean allowExtensions;
/*     */   
/*     */   public WebSocketServerHandshaker07(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength) {
/*  58 */     super(WebSocketVersion.V07, webSocketURL, subprotocols, maxFramePayloadLength);
/*  59 */     this.allowExtensions = allowExtensions;
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
/*     */   protected FullHttpResponse newHandshakeResponse(FullHttpRequest req, HttpHeaders headers) {
/*  98 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS);
/*     */ 
/*     */     
/* 101 */     if (headers != null) {
/* 102 */       defaultFullHttpResponse.headers().add(headers);
/*     */     }
/*     */     
/* 105 */     String key = req.headers().get("Sec-WebSocket-Key");
/* 106 */     if (key == null) {
/* 107 */       throw new WebSocketHandshakeException("not a WebSocket request: missing key");
/*     */     }
/* 109 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 110 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 111 */     String accept = WebSocketUtil.base64(sha1);
/*     */     
/* 113 */     if (logger.isDebugEnabled()) {
/* 114 */       logger.debug("WebSocket version 07 server handshake key: {}, response: {}.", key, accept);
/*     */     }
/*     */     
/* 117 */     defaultFullHttpResponse.headers().add("Upgrade", "WebSocket".toLowerCase());
/* 118 */     defaultFullHttpResponse.headers().add("Connection", "Upgrade");
/* 119 */     defaultFullHttpResponse.headers().add("Sec-WebSocket-Accept", accept);
/* 120 */     String subprotocols = req.headers().get("Sec-WebSocket-Protocol");
/* 121 */     if (subprotocols != null) {
/* 122 */       String selectedSubprotocol = selectSubprotocol(subprotocols);
/* 123 */       if (selectedSubprotocol == null) {
/* 124 */         if (logger.isDebugEnabled()) {
/* 125 */           logger.debug("Requested subprotocol(s) not supported: {}", subprotocols);
/*     */         }
/*     */       } else {
/* 128 */         defaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", selectedSubprotocol);
/*     */       } 
/*     */     } 
/* 131 */     return (FullHttpResponse)defaultFullHttpResponse;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 136 */     return new WebSocket07FrameDecoder(true, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 141 */     return new WebSocket07FrameEncoder(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker07.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */