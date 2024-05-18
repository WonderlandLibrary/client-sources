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
/*    */ public final class SocksAuthResponse
/*    */   extends SocksResponse
/*    */ {
/* 27 */   private static final SocksSubnegotiationVersion SUBNEGOTIATION_VERSION = SocksSubnegotiationVersion.AUTH_PASSWORD;
/*    */   private final SocksAuthStatus authStatus;
/*    */   
/*    */   public SocksAuthResponse(SocksAuthStatus authStatus) {
/* 31 */     super(SocksResponseType.AUTH);
/* 32 */     if (authStatus == null) {
/* 33 */       throw new NullPointerException("authStatus");
/*    */     }
/* 35 */     this.authStatus = authStatus;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksAuthStatus authStatus() {
/* 44 */     return this.authStatus;
/*    */   }
/*    */ 
/*    */   
/*    */   public void encodeAsByteBuf(ByteBuf byteBuf) {
/* 49 */     byteBuf.writeByte(SUBNEGOTIATION_VERSION.byteValue());
/* 50 */     byteBuf.writeByte(this.authStatus.byteValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksAuthResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */