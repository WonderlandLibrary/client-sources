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
/*     */ 
/*     */ 
/*     */ public class DefaultFullHttpResponse
/*     */   extends DefaultHttpResponse
/*     */   implements FullHttpResponse
/*     */ {
/*     */   private final ByteBuf content;
/*     */   private final HttpHeaders trailingHeaders;
/*     */   private final boolean validateHeaders;
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status) {
/*  32 */     this(version, status, Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, ByteBuf content) {
/*  36 */     this(version, status, content, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public DefaultFullHttpResponse(HttpVersion version, HttpResponseStatus status, ByteBuf content, boolean validateHeaders) {
/*  41 */     super(version, status, validateHeaders);
/*  42 */     if (content == null) {
/*  43 */       throw new NullPointerException("content");
/*     */     }
/*  45 */     this.content = content;
/*  46 */     this.trailingHeaders = new DefaultHttpHeaders(validateHeaders);
/*  47 */     this.validateHeaders = validateHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders trailingHeaders() {
/*  52 */     return this.trailingHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/*  57 */     return this.content;
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/*  62 */     return this.content.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse retain() {
/*  67 */     this.content.retain();
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse retain(int increment) {
/*  73 */     this.content.retain(increment);
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/*  79 */     return this.content.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/*  84 */     return this.content.release(decrement);
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse setProtocolVersion(HttpVersion version) {
/*  89 */     super.setProtocolVersion(version);
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse setStatus(HttpResponseStatus status) {
/*  95 */     super.setStatus(status);
/*  96 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse copy() {
/* 101 */     DefaultFullHttpResponse copy = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content().copy(), this.validateHeaders);
/*     */     
/* 103 */     copy.headers().set(headers());
/* 104 */     copy.trailingHeaders().set(trailingHeaders());
/* 105 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public FullHttpResponse duplicate() {
/* 110 */     DefaultFullHttpResponse duplicate = new DefaultFullHttpResponse(getProtocolVersion(), getStatus(), content().duplicate(), this.validateHeaders);
/*     */     
/* 112 */     duplicate.headers().set(headers());
/* 113 */     duplicate.trailingHeaders().set(trailingHeaders());
/* 114 */     return duplicate;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\DefaultFullHttpResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */