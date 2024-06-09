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
/*     */ import io.netty.handler.codec.http.HttpContentCompressor;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpObjectAggregator;
/*     */ import io.netty.handler.codec.http.HttpRequestDecoder;
/*     */ import io.netty.handler.codec.http.HttpResponseEncoder;
/*     */ import io.netty.handler.codec.http.HttpServerCodec;
/*     */ import io.netty.util.concurrent.Future;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.EmptyArrays;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WebSocketServerHandshaker
/*     */ {
/*  45 */   protected static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketServerHandshaker.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String uri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String[] subprotocols;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final WebSocketVersion version;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int maxFramePayloadLength;
/*     */ 
/*     */ 
/*     */   
/*     */   private String selectedSubprotocol;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String SUB_PROTOCOL_WILDCARD = "*";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketServerHandshaker(WebSocketVersion version, String uri, String subprotocols, int maxFramePayloadLength) {
/*  78 */     this.version = version;
/*  79 */     this.uri = uri;
/*  80 */     if (subprotocols != null) {
/*  81 */       String[] subprotocolArray = StringUtil.split(subprotocols, ',');
/*  82 */       for (int i = 0; i < subprotocolArray.length; i++) {
/*  83 */         subprotocolArray[i] = subprotocolArray[i].trim();
/*     */       }
/*  85 */       this.subprotocols = subprotocolArray;
/*     */     } else {
/*  87 */       this.subprotocols = EmptyArrays.EMPTY_STRINGS;
/*     */     } 
/*  89 */     this.maxFramePayloadLength = maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String uri() {
/*  96 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> subprotocols() {
/* 103 */     Set<String> ret = new LinkedHashSet<String>();
/* 104 */     Collections.addAll(ret, this.subprotocols);
/* 105 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketVersion version() {
/* 112 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxFramePayloadLength() {
/* 121 */     return this.maxFramePayloadLength;
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
/*     */   public ChannelFuture handshake(Channel channel, FullHttpRequest req) {
/* 136 */     return handshake(channel, req, null, channel.newPromise());
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
/*     */   public final ChannelFuture handshake(Channel channel, FullHttpRequest req, HttpHeaders responseHeaders, final ChannelPromise promise) {
/*     */     final String encoderName;
/* 158 */     if (logger.isDebugEnabled()) {
/* 159 */       logger.debug("{} WebSocket version {} server handshake", channel, version());
/*     */     }
/* 161 */     FullHttpResponse response = newHandshakeResponse(req, responseHeaders);
/* 162 */     ChannelPipeline p = channel.pipeline();
/* 163 */     if (p.get(HttpObjectAggregator.class) != null) {
/* 164 */       p.remove(HttpObjectAggregator.class);
/*     */     }
/* 166 */     if (p.get(HttpContentCompressor.class) != null) {
/* 167 */       p.remove(HttpContentCompressor.class);
/*     */     }
/* 169 */     ChannelHandlerContext ctx = p.context(HttpRequestDecoder.class);
/*     */     
/* 171 */     if (ctx == null) {
/*     */       
/* 173 */       ctx = p.context(HttpServerCodec.class);
/* 174 */       if (ctx == null) {
/* 175 */         promise.setFailure(new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
/*     */         
/* 177 */         return (ChannelFuture)promise;
/*     */       } 
/* 179 */       p.addBefore(ctx.name(), "wsdecoder", (ChannelHandler)newWebsocketDecoder());
/* 180 */       p.addBefore(ctx.name(), "wsencoder", (ChannelHandler)newWebSocketEncoder());
/* 181 */       encoderName = ctx.name();
/*     */     } else {
/* 183 */       p.replace(ctx.name(), "wsdecoder", (ChannelHandler)newWebsocketDecoder());
/*     */       
/* 185 */       encoderName = p.context(HttpResponseEncoder.class).name();
/* 186 */       p.addBefore(encoderName, "wsencoder", (ChannelHandler)newWebSocketEncoder());
/*     */     } 
/* 188 */     channel.writeAndFlush(response).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/* 191 */             if (future.isSuccess()) {
/* 192 */               ChannelPipeline p = future.channel().pipeline();
/* 193 */               p.remove(encoderName);
/* 194 */               promise.setSuccess();
/*     */             } else {
/* 196 */               promise.setFailure(future.cause());
/*     */             } 
/*     */           }
/*     */         });
/* 200 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract FullHttpResponse newHandshakeResponse(FullHttpRequest paramFullHttpRequest, HttpHeaders paramHttpHeaders);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame) {
/* 217 */     if (channel == null) {
/* 218 */       throw new NullPointerException("channel");
/*     */     }
/* 220 */     return close(channel, frame, channel.newPromise());
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
/* 234 */     if (channel == null) {
/* 235 */       throw new NullPointerException("channel");
/*     */     }
/* 237 */     return channel.writeAndFlush(frame, promise).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String selectSubprotocol(String requestedSubprotocols) {
/* 248 */     if (requestedSubprotocols == null || this.subprotocols.length == 0) {
/* 249 */       return null;
/*     */     }
/*     */     
/* 252 */     String[] requestedSubprotocolArray = StringUtil.split(requestedSubprotocols, ',');
/* 253 */     for (String p : requestedSubprotocolArray) {
/* 254 */       String requestedSubprotocol = p.trim();
/*     */       
/* 256 */       for (String supportedSubprotocol : this.subprotocols) {
/* 257 */         if ("*".equals(supportedSubprotocol) || requestedSubprotocol.equals(supportedSubprotocol)) {
/*     */           
/* 259 */           this.selectedSubprotocol = requestedSubprotocol;
/* 260 */           return requestedSubprotocol;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String selectedSubprotocol() {
/* 276 */     return this.selectedSubprotocol;
/*     */   }
/*     */   
/*     */   protected abstract WebSocketFrameDecoder newWebsocketDecoder();
/*     */   
/*     */   protected abstract WebSocketFrameEncoder newWebSocketEncoder();
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */