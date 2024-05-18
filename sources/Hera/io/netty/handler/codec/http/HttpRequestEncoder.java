/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.util.CharsetUtil;
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
/*    */ public class HttpRequestEncoder
/*    */   extends HttpObjectEncoder<HttpRequest>
/*    */ {
/*    */   private static final char SLASH = '/';
/*    */   private static final char QUESTION_MARK = '?';
/* 30 */   private static final byte[] CRLF = new byte[] { 13, 10 };
/*    */ 
/*    */   
/*    */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 34 */     return (super.acceptOutboundMessage(msg) && !(msg instanceof HttpResponse));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf buf, HttpRequest request) throws Exception {
/* 39 */     request.getMethod().encode(buf);
/* 40 */     buf.writeByte(32);
/*    */ 
/*    */ 
/*    */     
/* 44 */     String uri = request.getUri();
/*    */     
/* 46 */     if (uri.length() == 0) {
/* 47 */       uri = uri + '/';
/*    */     } else {
/* 49 */       int start = uri.indexOf("://");
/* 50 */       if (start != -1 && uri.charAt(0) != '/') {
/* 51 */         int startIndex = start + 3;
/*    */ 
/*    */         
/* 54 */         int index = uri.indexOf('?', startIndex);
/* 55 */         if (index == -1) {
/* 56 */           if (uri.lastIndexOf('/') <= startIndex) {
/* 57 */             uri = uri + '/';
/*    */           }
/*    */         }
/* 60 */         else if (uri.lastIndexOf('/', index) <= startIndex) {
/* 61 */           int len = uri.length();
/* 62 */           StringBuilder sb = new StringBuilder(len + 1);
/* 63 */           sb.append(uri, 0, index);
/* 64 */           sb.append('/');
/* 65 */           sb.append(uri, index, len);
/* 66 */           uri = sb.toString();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 72 */     buf.writeBytes(uri.getBytes(CharsetUtil.UTF_8));
/*    */     
/* 74 */     buf.writeByte(32);
/* 75 */     request.getProtocolVersion().encode(buf);
/* 76 */     buf.writeBytes(CRLF);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpRequestEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */