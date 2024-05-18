/*    */ package io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToMessageDecoder;
/*    */ import java.util.List;
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
/*    */ abstract class WebSocketProtocolHandler
/*    */   extends MessageToMessageDecoder<WebSocketFrame>
/*    */ {
/*    */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
/* 27 */     if (frame instanceof PingWebSocketFrame) {
/* 28 */       frame.content().retain();
/* 29 */       ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content()));
/*    */       return;
/*    */     } 
/* 32 */     if (frame instanceof PongWebSocketFrame) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 37 */     out.add(frame.retain());
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 42 */     ctx.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\websocketx\WebSocketProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */