/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultFullHttpRequest
/*     */   extends DefaultHttpRequest
/*     */   implements FullHttpRequest
/*     */ {
/*     */   private final ByteBuf content;
/*     */   private final HttpHeaders trailingHeader;
/*     */   private final boolean validateHeaders;
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri) {
/*  30 */     this(httpVersion, method, uri, Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content) {
/*  34 */     this(httpVersion, method, uri, content, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultFullHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, ByteBuf content, boolean validateHeaders) {
/*  39 */     super(httpVersion, method, uri, validateHeaders);
/*  40 */     if (content == null) {
/*  41 */       throw new NullPointerException("content");
/*     */     }
/*  43 */     this.content = content;
/*  44 */     this.trailingHeader = new DefaultHttpHeaders(validateHeaders);
/*  45 */     this.validateHeaders = validateHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders trailingHeaders() {
/*  50 */     return this.trailingHeader;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/*  55 */     return this.content;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/*  60 */     return this.content.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest retain() {
/*  65 */     this.content.retain();
/*  66 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest retain(int increment) {
/*  71 */     this.content.retain(increment);
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/*  77 */     return this.content.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/*  82 */     return this.content.release(decrement);
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest setProtocolVersion(HttpVersion version) {
/*  87 */     super.setProtocolVersion(version);
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest setMethod(HttpMethod method) {
/*  93 */     super.setMethod(method);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest setUri(String uri) {
/*  99 */     super.setUri(uri);
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest copy() {
/* 105 */     DefaultFullHttpRequest copy = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().copy(), this.validateHeaders);
/*     */     
/* 107 */     copy.headers().set(headers());
/* 108 */     copy.trailingHeaders().set(trailingHeaders());
/* 109 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpRequest duplicate() {
/* 114 */     DefaultFullHttpRequest duplicate = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().duplicate(), this.validateHeaders);
/*     */     
/* 116 */     duplicate.headers().set(headers());
/* 117 */     duplicate.trailingHeaders().set(trailingHeaders());
/* 118 */     return duplicate;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\DefaultFullHttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */