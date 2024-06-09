/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.handler.codec.http.HttpHeaders;
/*    */ import io.netty.handler.codec.http.HttpMessage;
/*    */ import io.netty.handler.codec.http.HttpRequest;
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
/*    */ 
/*    */ public class RtspRequestEncoder
/*    */   extends RtspObjectEncoder<HttpRequest>
/*    */ {
/* 32 */   private static final byte[] CRLF = new byte[] { 13, 10 };
/*    */ 
/*    */   
/*    */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/* 36 */     return msg instanceof io.netty.handler.codec.http.FullHttpRequest;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void encodeInitialLine(ByteBuf buf, HttpRequest request) throws Exception {
/* 42 */     HttpHeaders.encodeAscii(request.getMethod().toString(), buf);
/* 43 */     buf.writeByte(32);
/* 44 */     buf.writeBytes(request.getUri().getBytes(CharsetUtil.UTF_8));
/* 45 */     buf.writeByte(32);
/* 46 */     encodeAscii(request.getProtocolVersion().toString(), buf);
/* 47 */     buf.writeBytes(CRLF);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\rtsp\RtspRequestEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */