/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.channel.ChannelInboundHandler;
/*    */ import io.netty.channel.ChannelOutboundHandler;
/*    */ import io.netty.channel.CombinedChannelDuplexHandler;
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
/*    */ public final class HttpServerCodec
/*    */   extends CombinedChannelDuplexHandler<HttpRequestDecoder, HttpResponseEncoder>
/*    */ {
/*    */   public HttpServerCodec() {
/* 36 */     this(4096, 8192, 8192);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
/* 43 */     super((ChannelInboundHandler)new HttpRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize), (ChannelOutboundHandler)new HttpResponseEncoder());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean validateHeaders) {
/* 50 */     super((ChannelInboundHandler)new HttpRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize, validateHeaders), (ChannelOutboundHandler)new HttpResponseEncoder());
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpServerCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */