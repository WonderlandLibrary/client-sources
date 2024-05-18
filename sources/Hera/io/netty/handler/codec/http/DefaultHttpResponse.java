/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.util.internal.StringUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultHttpResponse
/*    */   extends DefaultHttpMessage
/*    */   implements HttpResponse
/*    */ {
/*    */   private HttpResponseStatus status;
/*    */   
/*    */   public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status) {
/* 34 */     this(version, status, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultHttpResponse(HttpVersion version, HttpResponseStatus status, boolean validateHeaders) {
/* 45 */     super(version, validateHeaders);
/* 46 */     if (status == null) {
/* 47 */       throw new NullPointerException("status");
/*    */     }
/* 49 */     this.status = status;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpResponseStatus getStatus() {
/* 54 */     return this.status;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpResponse setStatus(HttpResponseStatus status) {
/* 59 */     if (status == null) {
/* 60 */       throw new NullPointerException("status");
/*    */     }
/* 62 */     this.status = status;
/* 63 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpResponse setProtocolVersion(HttpVersion version) {
/* 68 */     super.setProtocolVersion(version);
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 74 */     StringBuilder buf = new StringBuilder();
/* 75 */     buf.append(StringUtil.simpleClassName(this));
/* 76 */     buf.append("(decodeResult: ");
/* 77 */     buf.append(getDecoderResult());
/* 78 */     buf.append(')');
/* 79 */     buf.append(StringUtil.NEWLINE);
/* 80 */     buf.append(getProtocolVersion().text());
/* 81 */     buf.append(' ');
/* 82 */     buf.append(getStatus());
/* 83 */     buf.append(StringUtil.NEWLINE);
/* 84 */     appendHeaders(buf);
/*    */ 
/*    */     
/* 87 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/* 88 */     return buf.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\DefaultHttpResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */