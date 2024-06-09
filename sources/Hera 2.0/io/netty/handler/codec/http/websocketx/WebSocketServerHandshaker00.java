/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.handler.codec.http.HttpVersion;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class WebSocketServerHandshaker00
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*  49 */   private static final Pattern BEGINNING_DIGIT = Pattern.compile("[^0-9]");
/*  50 */   private static final Pattern BEGINNING_SPACE = Pattern.compile("[^ ]");
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
/*     */   public WebSocketServerHandshaker00(String webSocketURL, String subprotocols, int maxFramePayloadLength) {
/*  65 */     super(WebSocketVersion.V00, webSocketURL, subprotocols, maxFramePayloadLength);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FullHttpResponse newHandshakeResponse(FullHttpRequest req, HttpHeaders headers) {
/* 112 */     if (!"Upgrade".equalsIgnoreCase(req.headers().get("Connection")) || !"WebSocket".equalsIgnoreCase(req.headers().get("Upgrade")))
/*     */     {
/* 114 */       throw new WebSocketHandshakeException("not a WebSocket handshake request: missing upgrade");
/*     */     }
/*     */ 
/*     */     
/* 118 */     boolean isHixie76 = (req.headers().contains("Sec-WebSocket-Key1") && req.headers().contains("Sec-WebSocket-Key2"));
/*     */ 
/*     */     
/* 121 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(101, isHixie76 ? "WebSocket Protocol Handshake" : "Web Socket Protocol Handshake"));
/*     */     
/* 123 */     if (headers != null) {
/* 124 */       defaultFullHttpResponse.headers().add(headers);
/*     */     }
/*     */     
/* 127 */     defaultFullHttpResponse.headers().add("Upgrade", "WebSocket");
/* 128 */     defaultFullHttpResponse.headers().add("Connection", "Upgrade");
/*     */ 
/*     */     
/* 131 */     if (isHixie76) {
/*     */       
/* 133 */       defaultFullHttpResponse.headers().add("Sec-WebSocket-Origin", req.headers().get("Origin"));
/* 134 */       defaultFullHttpResponse.headers().add("Sec-WebSocket-Location", uri());
/* 135 */       String subprotocols = req.headers().get("Sec-WebSocket-Protocol");
/* 136 */       if (subprotocols != null) {
/* 137 */         String selectedSubprotocol = selectSubprotocol(subprotocols);
/* 138 */         if (selectedSubprotocol == null) {
/* 139 */           if (logger.isDebugEnabled()) {
/* 140 */             logger.debug("Requested subprotocol(s) not supported: {}", subprotocols);
/*     */           }
/*     */         } else {
/* 143 */           defaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", selectedSubprotocol);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 148 */       String key1 = req.headers().get("Sec-WebSocket-Key1");
/* 149 */       String key2 = req.headers().get("Sec-WebSocket-Key2");
/* 150 */       int a = (int)(Long.parseLong(BEGINNING_DIGIT.matcher(key1).replaceAll("")) / BEGINNING_SPACE.matcher(key1).replaceAll("").length());
/*     */       
/* 152 */       int b = (int)(Long.parseLong(BEGINNING_DIGIT.matcher(key2).replaceAll("")) / BEGINNING_SPACE.matcher(key2).replaceAll("").length());
/*     */       
/* 154 */       long c = req.content().readLong();
/* 155 */       ByteBuf input = Unpooled.buffer(16);
/* 156 */       input.writeInt(a);
/* 157 */       input.writeInt(b);
/* 158 */       input.writeLong(c);
/* 159 */       defaultFullHttpResponse.content().writeBytes(WebSocketUtil.md5(input.array()));
/*     */     } else {
/*     */       
/* 162 */       defaultFullHttpResponse.headers().add("WebSocket-Origin", req.headers().get("Origin"));
/* 163 */       defaultFullHttpResponse.headers().add("WebSocket-Location", uri());
/* 164 */       String protocol = req.headers().get("WebSocket-Protocol");
/* 165 */       if (protocol != null) {
/* 166 */         defaultFullHttpResponse.headers().add("WebSocket-Protocol", selectSubprotocol(protocol));
/*     */       }
/*     */     } 
/* 169 */     return (FullHttpResponse)defaultFullHttpResponse;
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
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame, ChannelPromise promise) {
/* 182 */     return channel.writeAndFlush(frame, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 187 */     return new WebSocket00FrameDecoder(maxFramePayloadLength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 192 */     return new WebSocket00FrameEncoder();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker00.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */