/*    */ package io.netty.handler.codec.compression;
/*    */ 
/*    */ import io.netty.handler.codec.DecoderException;
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
/*    */ public class DecompressionException
/*    */   extends DecoderException
/*    */ {
/*    */   private static final long serialVersionUID = 3546272712208105199L;
/*    */   
/*    */   public DecompressionException() {}
/*    */   
/*    */   public DecompressionException(String message, Throwable cause) {
/* 37 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DecompressionException(String message) {
/* 44 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DecompressionException(Throwable cause) {
/* 51 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\compression\DecompressionException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */