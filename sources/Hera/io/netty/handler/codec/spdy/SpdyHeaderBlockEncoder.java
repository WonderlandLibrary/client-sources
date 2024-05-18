/*    */ package io.netty.handler.codec.spdy;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.util.internal.PlatformDependent;
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
/*    */ abstract class SpdyHeaderBlockEncoder
/*    */ {
/*    */   static SpdyHeaderBlockEncoder newInstance(SpdyVersion version, int compressionLevel, int windowBits, int memLevel) {
/* 26 */     if (PlatformDependent.javaVersion() >= 7) {
/* 27 */       return new SpdyHeaderBlockZlibEncoder(version, compressionLevel);
/*    */     }
/*    */     
/* 30 */     return new SpdyHeaderBlockJZlibEncoder(version, compressionLevel, windowBits, memLevel);
/*    */   }
/*    */   
/*    */   abstract ByteBuf encode(SpdyHeadersFrame paramSpdyHeadersFrame) throws Exception;
/*    */   
/*    */   abstract void end();
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\spdy\SpdyHeaderBlockEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */