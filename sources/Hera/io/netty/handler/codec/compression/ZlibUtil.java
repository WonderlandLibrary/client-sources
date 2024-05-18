/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import com.jcraft.jzlib.Deflater;
/*    */ import com.jcraft.jzlib.Inflater;
/*    */ import com.jcraft.jzlib.JZlib;
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
/*    */ final class ZlibUtil
/*    */ {
/*    */   static void fail(Inflater z, String message, int resultCode) {
/* 28 */     throw inflaterException(z, message, resultCode);
/*    */   }
/*    */   
/*    */   static void fail(Deflater z, String message, int resultCode) {
/* 32 */     throw deflaterException(z, message, resultCode);
/*    */   }
/*    */   
/*    */   static DecompressionException inflaterException(Inflater z, String message, int resultCode) {
/* 36 */     return new DecompressionException(message + " (" + resultCode + ')' + ((z.msg != null) ? (": " + z.msg) : ""));
/*    */   }
/*    */   
/*    */   static CompressionException deflaterException(Deflater z, String message, int resultCode) {
/* 40 */     return new CompressionException(message + " (" + resultCode + ')' + ((z.msg != null) ? (": " + z.msg) : ""));
/*    */   }
/*    */   
/*    */   static JZlib.WrapperType convertWrapperType(ZlibWrapper wrapper) {
/*    */     JZlib.WrapperType convertedWrapperType;
/* 45 */     switch (wrapper) {
/*    */       case NONE:
/* 47 */         convertedWrapperType = JZlib.W_NONE;
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
/* 61 */         return convertedWrapperType;case ZLIB: convertedWrapperType = JZlib.W_ZLIB; return convertedWrapperType;case GZIP: convertedWrapperType = JZlib.W_GZIP; return convertedWrapperType;case ZLIB_OR_NONE: convertedWrapperType = JZlib.W_ANY; return convertedWrapperType;
/*    */     } 
/*    */     throw new Error();
/*    */   } static int wrapperOverhead(ZlibWrapper wrapper) {
/*    */     int overhead;
/* 66 */     switch (wrapper) {
/*    */       case NONE:
/* 68 */         overhead = 0;
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
/* 80 */         return overhead;case ZLIB: case ZLIB_OR_NONE: overhead = 2; return overhead;case GZIP: overhead = 10; return overhead;
/*    */     } 
/*    */     throw new Error();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\compression\ZlibUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */