/*    */ package io.netty.handler.codec.socks;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public final class SocksInitResponse
/*    */   extends SocksResponse
/*    */ {
/*    */   private final SocksAuthScheme authScheme;
/*    */   
/*    */   public SocksInitResponse(SocksAuthScheme authScheme) {
/* 30 */     super(SocksResponseType.INIT);
/* 31 */     if (authScheme == null) {
/* 32 */       throw new NullPointerException("authScheme");
/*    */     }
/* 34 */     this.authScheme = authScheme;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksAuthScheme authScheme() {
/* 43 */     return this.authScheme;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf byteBuf) {
/* 48 */     byteBuf.writeByte(protocolVersion().byteValue());
/* 49 */     byteBuf.writeByte(this.authScheme.byteValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksInitResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */