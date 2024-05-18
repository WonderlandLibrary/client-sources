/*    */ package io.netty.handler.codec.spdy;
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
/*    */ public final class SpdyHttpCodec
/*    */   extends CombinedChannelDuplexHandler<SpdyHttpDecoder, SpdyHttpEncoder>
/*    */ {
/*    */   public SpdyHttpCodec(SpdyVersion version, int maxContentLength) {
/* 29 */     super((ChannelInboundHandler)new SpdyHttpDecoder(version, maxContentLength), (ChannelOutboundHandler)new SpdyHttpEncoder(version));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpdyHttpCodec(SpdyVersion version, int maxContentLength, boolean validateHttpHeaders) {
/* 36 */     super((ChannelInboundHandler)new SpdyHttpDecoder(version, maxContentLength, validateHttpHeaders), (ChannelOutboundHandler)new SpdyHttpEncoder(version));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHttpCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */