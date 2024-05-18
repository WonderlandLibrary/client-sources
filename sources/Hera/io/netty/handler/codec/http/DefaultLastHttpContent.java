/*     */ package io.netty.handler.codec.http;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultLastHttpContent
/*     */   extends DefaultHttpContent
/*     */   implements LastHttpContent
/*     */ {
/*     */   private final HttpHeaders trailingHeaders;
/*     */   private final boolean validateHeaders;
/*     */   
/*     */   public DefaultLastHttpContent() {
/*  33 */     this(Unpooled.buffer(0));
/*     */   }
/*     */   
/*     */   public DefaultLastHttpContent(ByteBuf content) {
/*  37 */     this(content, true);
/*     */   }
/*     */   
/*     */   public DefaultLastHttpContent(ByteBuf content, boolean validateHeaders) {
/*  41 */     super(content);
/*  42 */     this.trailingHeaders = new TrailingHeaders(validateHeaders);
/*  43 */     this.validateHeaders = validateHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent copy() {
/*  48 */     DefaultLastHttpContent copy = new DefaultLastHttpContent(content().copy(), this.validateHeaders);
/*  49 */     copy.trailingHeaders().set(trailingHeaders());
/*  50 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent duplicate() {
/*  55 */     DefaultLastHttpContent copy = new DefaultLastHttpContent(content().duplicate(), this.validateHeaders);
/*  56 */     copy.trailingHeaders().set(trailingHeaders());
/*  57 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent retain(int increment) {
/*  62 */     super.retain(increment);
/*  63 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LastHttpContent retain() {
/*  68 */     super.retain();
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpHeaders trailingHeaders() {
/*  74 */     return this.trailingHeaders;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  79 */     StringBuilder buf = new StringBuilder(super.toString());
/*  80 */     buf.append(StringUtil.NEWLINE);
/*  81 */     appendHeaders(buf);
/*     */ 
/*     */     
/*  84 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/*  85 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private void appendHeaders(StringBuilder buf) {
/*  89 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)trailingHeaders()) {
/*  90 */       buf.append(e.getKey());
/*  91 */       buf.append(": ");
/*  92 */       buf.append(e.getValue());
/*  93 */       buf.append(StringUtil.NEWLINE);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class TrailingHeaders extends DefaultHttpHeaders {
/*     */     TrailingHeaders(boolean validate) {
/*  99 */       super(validate);
/*     */     }
/*     */ 
/*     */     
/*     */     void validateHeaderName0(CharSequence name) {
/* 104 */       super.validateHeaderName0(name);
/* 105 */       if (HttpHeaders.equalsIgnoreCase("Content-Length", name) || HttpHeaders.equalsIgnoreCase("Transfer-Encoding", name) || HttpHeaders.equalsIgnoreCase("Trailer", name))
/*     */       {
/*     */         
/* 108 */         throw new IllegalArgumentException("prohibited trailing header: " + name);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\DefaultLastHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */