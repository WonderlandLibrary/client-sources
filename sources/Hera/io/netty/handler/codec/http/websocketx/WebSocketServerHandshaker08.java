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
/*     */ public class WebSocketServerHandshaker08
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*     */   public static final String WEBSOCKET_08_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */   private final boolean allowExtensions;
/*     */   
/*     */   public WebSocketServerHandshaker08(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength) {
/*  58 */     super(WebSocketVersion.V08, webSocketURL, subprotocols, maxFramePayloadLength);
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
/* 100 */     if (headers != null) {
/* 101 */       defaultFullHttpResponse.headers().add(headers);
/*     */     }
/*     */     
/* 104 */     String key = req.headers().get("Sec-WebSocket-Key");
/* 105 */     if (key == null) {
/* 106 */       throw new WebSocketHandshakeException("not a WebSocket request: missing key");
/*     */     }
/* 108 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 109 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 110 */     String accept = WebSocketUtil.base64(sha1);
/*     */     
/* 112 */     if (logger.isDebugEnabled()) {
/* 113 */       logger.debug("WebSocket version 08 server handshake key: {}, response: {}", key, accept);
/*     */     }
/*     */     
/* 116 */     defaultFullHttpResponse.headers().add("Upgrade", "WebSocket".toLowerCase());
/* 117 */     defaultFullHttpResponse.headers().add("Connection", "Upgrade");
/* 118 */     defaultFullHttpResponse.headers().add("Sec-WebSocket-Accept", accept);
/* 119 */     String subprotocols = req.headers().get("Sec-WebSocket-Protocol");
/* 120 */     if (subprotocols != null) {
/* 121 */       String selectedSubprotocol = selectSubprotocol(subprotocols);
/* 122 */       if (selectedSubprotocol == null) {
/* 123 */         if (logger.isDebugEnabled()) {
/* 124 */           logger.debug("Requested subprotocol(s) not supported: {}", subprotocols);
/*     */         }
/*     */       } else {
/* 127 */         defaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", selectedSubprotocol);
/*     */       } 
/*     */     } 
/* 130 */     return (FullHttpResponse)defaultFullHttpResponse;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 135 */     return new WebSocket08FrameDecoder(true, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 140 */     return new WebSocket08FrameEncoder(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker08.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */