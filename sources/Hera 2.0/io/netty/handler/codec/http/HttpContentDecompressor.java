/*    */ package io.netty.handler.codec.http;
/*    */ 
/*    */ import io.netty.channel.ChannelHandler;
/*    */ import io.netty.channel.embedded.EmbeddedChannel;
/*    */ import io.netty.handler.codec.compression.ZlibCodecFactory;
/*    */ import io.netty.handler.codec.compression.ZlibWrapper;
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
/*    */ public class HttpContentDecompressor
/*    */   extends HttpContentDecoder
/*    */ {
/*    */   private final boolean strict;
/*    */   
/*    */   public HttpContentDecompressor() {
/* 35 */     this(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HttpContentDecompressor(boolean strict) {
/* 45 */     this.strict = strict;
/*    */   }
/*    */ 
/*    */   
/*    */   protected EmbeddedChannel newContentDecoder(String contentEncoding) throws Exception {
/* 50 */     if ("gzip".equalsIgnoreCase(contentEncoding) || "x-gzip".equalsIgnoreCase(contentEncoding)) {
/* 51 */       return new EmbeddedChannel(new ChannelHandler[] { (ChannelHandler)ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP) });
/*    */     }
/* 53 */     if ("deflate".equalsIgnoreCase(contentEncoding) || "x-deflate".equalsIgnoreCase(contentEncoding)) {
/*    */       ZlibWrapper wrapper;
/* 55 */       if (this.strict) {
/* 56 */         wrapper = ZlibWrapper.ZLIB;
/*    */       } else {
/* 58 */         wrapper = ZlibWrapper.ZLIB_OR_NONE;
/*    */       } 
/*    */       
/* 61 */       return new EmbeddedChannel(new ChannelHandler[] { (ChannelHandler)ZlibCodecFactory.newZlibDecoder(wrapper) });
/*    */     } 
/*    */ 
/*    */     
/* 65 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpContentDecompressor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */