/*     */ package io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.FullHttpRequest;
/*     */ import io.netty.handler.codec.http.FullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpClientCodec;
/*     */ import io.netty.handler.codec.http.HttpContentDecompressor;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpRequestEncoder;
/*     */ import io.netty.handler.codec.http.HttpResponseDecoder;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import java.net.URI;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WebSocketClientHandshaker
/*     */ {
/*     */   private final URI uri;
/*     */   private final WebSocketVersion version;
/*     */   private volatile boolean handshakeComplete;
/*     */   private final String expectedSubprotocol;
/*     */   private volatile String actualSubprotocol;
/*     */   protected final HttpHeaders customHeaders;
/*     */   private final int maxFramePayloadLength;
/*     */   
/*     */   protected WebSocketClientHandshaker(URI uri, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength) {
/*  70 */     this.uri = uri;
/*  71 */     this.version = version;
/*  72 */     this.expectedSubprotocol = subprotocol;
/*  73 */     this.customHeaders = customHeaders;
/*  74 */     this.maxFramePayloadLength = maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI uri() {
/*  81 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketVersion version() {
/*  88 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxFramePayloadLength() {
/*  95 */     return this.maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHandshakeComplete() {
/* 102 */     return this.handshakeComplete;
/*     */   }
/*     */   
/*     */   private void setHandshakeComplete() {
/* 106 */     this.handshakeComplete = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String expectedSubprotocol() {
/* 113 */     return this.expectedSubprotocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String actualSubprotocol() {
/* 121 */     return this.actualSubprotocol;
/*     */   }
/*     */   
/*     */   private void setActualSubprotocol(String actualSubprotocol) {
/* 125 */     this.actualSubprotocol = actualSubprotocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture handshake(Channel channel) {
/* 135 */     if (channel == null) {
/* 136 */       throw new NullPointerException("channel");
/*     */     }
/* 138 */     return handshake(channel, channel.newPromise());
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
/*     */   public final ChannelFuture handshake(Channel channel, final ChannelPromise promise) {
/* 150 */     FullHttpRequest request = newHandshakeRequest();
/*     */     
/* 152 */     HttpResponseDecoder decoder = (HttpResponseDecoder)channel.pipeline().get(HttpResponseDecoder.class);
/* 153 */     if (decoder == null) {
/* 154 */       HttpClientCodec codec = (HttpClientCodec)channel.pipeline().get(HttpClientCodec.class);
/* 155 */       if (codec == null) {
/* 156 */         promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
/*     */         
/* 158 */         return (ChannelFuture)promise;
/*     */       } 
/*     */     } 
/*     */     
/* 162 */     channel.writeAndFlush(request).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) {
/* 165 */             if (future.isSuccess()) {
/* 166 */               ChannelPipeline p = future.channel().pipeline();
/* 167 */               ChannelHandlerContext ctx = p.context(HttpRequestEncoder.class);
/* 168 */               if (ctx == null) {
/* 169 */                 ctx = p.context(HttpClientCodec.class);
/*     */               }
/* 171 */               if (ctx == null) {
/* 172 */                 promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec"));
/*     */                 
/*     */                 return;
/*     */               } 
/* 176 */               p.addAfter(ctx.name(), "ws-encoder", (ChannelHandler)WebSocketClientHandshaker.this.newWebSocketEncoder());
/*     */               
/* 178 */               promise.setSuccess();
/*     */             } else {
/* 180 */               promise.setFailure(future.cause());
/*     */             } 
/*     */           }
/*     */         });
/* 184 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract FullHttpRequest newHandshakeRequest();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void finishHandshake(Channel channel, FullHttpResponse response) {
/* 201 */     verify(response);
/* 202 */     setActualSubprotocol(response.headers().get("Sec-WebSocket-Protocol"));
/* 203 */     setHandshakeComplete();
/*     */     
/* 205 */     ChannelPipeline p = channel.pipeline();
/*     */     
/* 207 */     HttpContentDecompressor decompressor = (HttpContentDecompressor)p.get(HttpContentDecompressor.class);
/* 208 */     if (decompressor != null) {
/* 209 */       p.remove((ChannelHandler)decompressor);
/*     */     }
/*     */     
/* 212 */     ChannelHandlerContext ctx = p.context(HttpResponseDecoder.class);
/* 213 */     if (ctx == null) {
/* 214 */       ctx = p.context(HttpClientCodec.class);
/* 215 */       if (ctx == null) {
/* 216 */         throw new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec");
/*     */       }
/*     */       
/* 219 */       p.replace(ctx.name(), "ws-decoder", (ChannelHandler)newWebsocketDecoder());
/*     */     } else {
/* 221 */       if (p.get(HttpRequestEncoder.class) != null) {
/* 222 */         p.remove(HttpRequestEncoder.class);
/*     */       }
/* 224 */       p.replace(ctx.name(), "ws-decoder", (ChannelHandler)newWebsocketDecoder());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void verify(FullHttpResponse paramFullHttpResponse);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract WebSocketFrameDecoder newWebsocketDecoder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract WebSocketFrameEncoder newWebSocketEncoder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame) {
/* 253 */     if (channel == null) {
/* 254 */       throw new NullPointerException("channel");
/*     */     }
/* 256 */     return close(channel, frame, channel.newPromise());
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
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame, ChannelPromise promise) {
/* 270 */     if (channel == null) {
/* 271 */       throw new NullPointerException("channel");
/*     */     }
/* 273 */     return channel.writeAndFlush(frame, promise);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */