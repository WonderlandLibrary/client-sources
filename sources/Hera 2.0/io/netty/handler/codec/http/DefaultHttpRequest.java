/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultHttpRequest
/*     */   extends DefaultHttpMessage
/*     */   implements HttpRequest
/*     */ {
/*     */   private HttpMethod method;
/*     */   private String uri;
/*     */   
/*     */   public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri) {
/*  36 */     this(httpVersion, method, uri, true);
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
/*     */   public DefaultHttpRequest(HttpVersion httpVersion, HttpMethod method, String uri, boolean validateHeaders) {
/*  48 */     super(httpVersion, validateHeaders);
/*  49 */     if (method == null) {
/*  50 */       throw new NullPointerException("method");
/*     */     }
/*  52 */     if (uri == null) {
/*  53 */       throw new NullPointerException("uri");
/*     */     }
/*  55 */     this.method = method;
/*  56 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpMethod getMethod() {
/*  61 */     return this.method;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUri() {
/*  66 */     return this.uri;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpRequest setMethod(HttpMethod method) {
/*  71 */     if (method == null) {
/*  72 */       throw new NullPointerException("method");
/*     */     }
/*  74 */     this.method = method;
/*  75 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpRequest setUri(String uri) {
/*  80 */     if (uri == null) {
/*  81 */       throw new NullPointerException("uri");
/*     */     }
/*  83 */     this.uri = uri;
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpRequest setProtocolVersion(HttpVersion version) {
/*  89 */     super.setProtocolVersion(version);
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  95 */     StringBuilder buf = new StringBuilder();
/*  96 */     buf.append(StringUtil.simpleClassName(this));
/*  97 */     buf.append("(decodeResult: ");
/*  98 */     buf.append(getDecoderResult());
/*  99 */     buf.append(')');
/* 100 */     buf.append(StringUtil.NEWLINE);
/* 101 */     buf.append(getMethod());
/* 102 */     buf.append(' ');
/* 103 */     buf.append(getUri());
/* 104 */     buf.append(' ');
/* 105 */     buf.append(getProtocolVersion().text());
/* 106 */     buf.append(StringUtil.NEWLINE);
/* 107 */     appendHeaders(buf);
/*     */ 
/*     */     
/* 110 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/* 111 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\DefaultHttpRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */