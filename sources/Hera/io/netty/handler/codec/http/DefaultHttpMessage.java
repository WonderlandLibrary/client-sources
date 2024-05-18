/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.util.internal.StringUtil;
/*    */ import java.util.Map;
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
/*    */ public abstract class DefaultHttpMessage
/*    */   extends DefaultHttpObject
/*    */   implements HttpMessage
/*    */ {
/*    */   private HttpVersion version;
/*    */   private final HttpHeaders headers;
/*    */   
/*    */   protected DefaultHttpMessage(HttpVersion version) {
/* 34 */     this(version, true);
/*    */   }
/*    */   
/*    */   protected DefaultHttpMessage(HttpVersion version, boolean validate) {
/* 38 */     if (version == null) {
/* 39 */       throw new NullPointerException("version");
/*    */     }
/* 41 */     this.version = version;
/* 42 */     this.headers = new DefaultHttpHeaders(validate);
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpHeaders headers() {
/* 47 */     return this.headers;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpVersion getProtocolVersion() {
/* 52 */     return this.version;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     StringBuilder buf = new StringBuilder();
/* 58 */     buf.append(StringUtil.simpleClassName(this));
/* 59 */     buf.append("(version: ");
/* 60 */     buf.append(getProtocolVersion().text());
/* 61 */     buf.append(", keepAlive: ");
/* 62 */     buf.append(HttpHeaders.isKeepAlive(this));
/* 63 */     buf.append(')');
/* 64 */     buf.append(StringUtil.NEWLINE);
/* 65 */     appendHeaders(buf);
/*    */ 
/*    */     
/* 68 */     buf.setLength(buf.length() - StringUtil.NEWLINE.length());
/* 69 */     return buf.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpMessage setProtocolVersion(HttpVersion version) {
/* 74 */     if (version == null) {
/* 75 */       throw new NullPointerException("version");
/*    */     }
/* 77 */     this.version = version;
/* 78 */     return this;
/*    */   }
/*    */   
/*    */   void appendHeaders(StringBuilder buf) {
/* 82 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)headers()) {
/* 83 */       buf.append(e.getKey());
/* 84 */       buf.append(": ");
/* 85 */       buf.append(e.getValue());
/* 86 */       buf.append(StringUtil.NEWLINE);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\DefaultHttpMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */