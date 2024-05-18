/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public class HttpResponseEncoder
/*    */   extends HttpObjectEncoder<HttpResponse>
/*    */ {
/* 27 */   private static final byte[] CRLF = new byte[] { 13, 10 };
/*    */ 
/*    */   
/*    */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 31 */     return (super.acceptOutboundMessage(msg) && !(msg instanceof HttpRequest));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf buf, HttpResponse response) throws Exception {
/* 36 */     response.getProtocolVersion().encode(buf);
/* 37 */     buf.writeByte(32);
/* 38 */     response.getStatus().encode(buf);
/* 39 */     buf.writeBytes(CRLF);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpResponseEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */