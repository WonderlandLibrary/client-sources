/*    */ package io.netty.handler.codec.socks;
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
/*    */ public abstract class SocksRequest
/*    */   extends SocksMessage
/*    */ {
/*    */   private final SocksRequestType requestType;
/*    */   
/*    */   protected SocksRequest(SocksRequestType requestType) {
/* 31 */     super(SocksMessageType.REQUEST);
/* 32 */     if (requestType == null) {
/* 33 */       throw new NullPointerException("requestType");
/*    */     }
/* 35 */     this.requestType = requestType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksRequestType requestType() {
/* 44 */     return this.requestType;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */