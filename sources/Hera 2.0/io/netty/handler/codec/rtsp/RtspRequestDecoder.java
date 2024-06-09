/*    */ package io.netty.handler.codec.rtsp;
/*    */ 
/*    */ import io.netty.handler.codec.http.DefaultHttpRequest;
/*    */ import io.netty.handler.codec.http.HttpMessage;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RtspRequestDecoder
/*    */   extends RtspObjectDecoder
/*    */ {
/*    */   public RtspRequestDecoder() {}
/*    */   
/*    */   public RtspRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxContentLength) {
/* 65 */     super(maxInitialLineLength, maxHeaderSize, maxContentLength);
/*    */   }
/*    */ 
/*    */   
/*    */   public RtspRequestDecoder(int maxInitialLineLength, int maxHeaderSize, int maxContentLength, boolean validateHeaders) {
/* 70 */     super(maxInitialLineLength, maxHeaderSize, maxContentLength, validateHeaders);
/*    */   }
/*    */ 
/*    */   
/*    */   protected HttpMessage createMessage(String[] initialLine) throws Exception {
/* 75 */     return (HttpMessage)new DefaultHttpRequest(RtspVersions.valueOf(initialLine[2]), RtspMethods.valueOf(initialLine[0]), initialLine[1], this.validateHeaders);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected HttpMessage createInvalidMessage() {
/* 81 */     return (HttpMessage)new DefaultHttpRequest(RtspVersions.RTSP_1_0, RtspMethods.OPTIONS, "/bad-request", this.validateHeaders);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isDecodingRequest() {
/* 86 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\rtsp\RtspRequestDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */