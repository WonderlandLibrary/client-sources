/*     */ package io.netty.handler.codec.http.cors;
/*     */ 
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFutureListener;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.handler.codec.http.HttpResponse;
/*     */ import io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import io.netty.util.concurrent.GenericFutureListener;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CorsHandler
/*     */   extends ChannelDuplexHandler
/*     */ {
/*  41 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(CorsHandler.class);
/*     */   
/*     */   private final CorsConfig config;
/*     */   private HttpRequest request;
/*     */   
/*     */   public CorsHandler(CorsConfig config) {
/*  47 */     this.config = config;
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  52 */     if (this.config.isCorsSupportEnabled() && msg instanceof HttpRequest) {
/*  53 */       this.request = (HttpRequest)msg;
/*  54 */       if (isPreflightRequest(this.request)) {
/*  55 */         handlePreflight(ctx, this.request);
/*     */         return;
/*     */       } 
/*  58 */       if (this.config.isShortCurcuit() && !validateOrigin()) {
/*  59 */         forbidden(ctx, this.request);
/*     */         return;
/*     */       } 
/*     */     } 
/*  63 */     ctx.fireChannelRead(msg);
/*     */   }
/*     */   
/*     */   private void handlePreflight(ChannelHandlerContext ctx, HttpRequest request) {
/*  67 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
/*  68 */     if (setOrigin((HttpResponse)defaultFullHttpResponse)) {
/*  69 */       setAllowMethods((HttpResponse)defaultFullHttpResponse);
/*  70 */       setAllowHeaders((HttpResponse)defaultFullHttpResponse);
/*  71 */       setAllowCredentials((HttpResponse)defaultFullHttpResponse);
/*  72 */       setMaxAge((HttpResponse)defaultFullHttpResponse);
/*  73 */       setPreflightHeaders((HttpResponse)defaultFullHttpResponse);
/*     */     } 
/*  75 */     ctx.writeAndFlush(defaultFullHttpResponse).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setPreflightHeaders(HttpResponse response) {
/*  85 */     response.headers().add(this.config.preflightResponseHeaders());
/*     */   }
/*     */   
/*     */   private boolean setOrigin(HttpResponse response) {
/*  89 */     String origin = this.request.headers().get("Origin");
/*  90 */     if (origin != null) {
/*  91 */       if ("null".equals(origin) && this.config.isNullOriginAllowed()) {
/*  92 */         setAnyOrigin(response);
/*  93 */         return true;
/*     */       } 
/*  95 */       if (this.config.isAnyOriginSupported()) {
/*  96 */         if (this.config.isCredentialsAllowed()) {
/*  97 */           echoRequestOrigin(response);
/*  98 */           setVaryHeader(response);
/*     */         } else {
/* 100 */           setAnyOrigin(response);
/*     */         } 
/* 102 */         return true;
/*     */       } 
/* 104 */       if (this.config.origins().contains(origin)) {
/* 105 */         setOrigin(response, origin);
/* 106 */         setVaryHeader(response);
/* 107 */         return true;
/*     */       } 
/* 109 */       logger.debug("Request origin [" + origin + "] was not among the configured origins " + this.config.origins());
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   private boolean validateOrigin() {
/* 115 */     if (this.config.isAnyOriginSupported()) {
/* 116 */       return true;
/*     */     }
/*     */     
/* 119 */     String origin = this.request.headers().get("Origin");
/* 120 */     if (origin == null)
/*     */     {
/* 122 */       return true;
/*     */     }
/*     */     
/* 125 */     if ("null".equals(origin) && this.config.isNullOriginAllowed()) {
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     return this.config.origins().contains(origin);
/*     */   }
/*     */   
/*     */   private void echoRequestOrigin(HttpResponse response) {
/* 133 */     setOrigin(response, this.request.headers().get("Origin"));
/*     */   }
/*     */   
/*     */   private static void setVaryHeader(HttpResponse response) {
/* 137 */     response.headers().set("Vary", "Origin");
/*     */   }
/*     */   
/*     */   private static void setAnyOrigin(HttpResponse response) {
/* 141 */     setOrigin(response, "*");
/*     */   }
/*     */   
/*     */   private static void setOrigin(HttpResponse response, String origin) {
/* 145 */     response.headers().set("Access-Control-Allow-Origin", origin);
/*     */   }
/*     */   
/*     */   private void setAllowCredentials(HttpResponse response) {
/* 149 */     if (this.config.isCredentialsAllowed()) {
/* 150 */       response.headers().set("Access-Control-Allow-Credentials", "true");
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isPreflightRequest(HttpRequest request) {
/* 155 */     HttpHeaders headers = request.headers();
/* 156 */     return (request.getMethod().equals(HttpMethod.OPTIONS) && headers.contains("Origin") && headers.contains("Access-Control-Request-Method"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setExposeHeaders(HttpResponse response) {
/* 162 */     if (!this.config.exposedHeaders().isEmpty()) {
/* 163 */       response.headers().set("Access-Control-Expose-Headers", this.config.exposedHeaders());
/*     */     }
/*     */   }
/*     */   
/*     */   private void setAllowMethods(HttpResponse response) {
/* 168 */     response.headers().set("Access-Control-Allow-Methods", this.config.allowedRequestMethods());
/*     */   }
/*     */   
/*     */   private void setAllowHeaders(HttpResponse response) {
/* 172 */     response.headers().set("Access-Control-Allow-Headers", this.config.allowedRequestHeaders());
/*     */   }
/*     */   
/*     */   private void setMaxAge(HttpResponse response) {
/* 176 */     response.headers().set("Access-Control-Max-Age", Long.valueOf(this.config.maxAge()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 182 */     if (this.config.isCorsSupportEnabled() && msg instanceof HttpResponse) {
/* 183 */       HttpResponse response = (HttpResponse)msg;
/* 184 */       if (setOrigin(response)) {
/* 185 */         setAllowCredentials(response);
/* 186 */         setAllowHeaders(response);
/* 187 */         setExposeHeaders(response);
/*     */       } 
/*     */     } 
/* 190 */     ctx.writeAndFlush(msg, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 195 */     logger.error("Caught error in CorsHandler", cause);
/* 196 */     ctx.fireExceptionCaught(cause);
/*     */   }
/*     */   
/*     */   private static void forbidden(ChannelHandlerContext ctx, HttpRequest request) {
/* 200 */     ctx.writeAndFlush(new DefaultFullHttpResponse(request.getProtocolVersion(), HttpResponseStatus.FORBIDDEN)).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\cors\CorsHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */