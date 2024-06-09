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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SocksMessage
/*    */ {
/*    */   private final SocksMessageType type;
/* 30 */   private final SocksProtocolVersion protocolVersion = SocksProtocolVersion.SOCKS5;
/*    */   
/*    */   protected SocksMessage(SocksMessageType type) {
/* 33 */     if (type == null) {
/* 34 */       throw new NullPointerException("type");
/*    */     }
/* 36 */     this.type = type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksMessageType type() {
/* 45 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SocksProtocolVersion protocolVersion() {
/* 54 */     return this.protocolVersion;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public abstract void encodeAsByteBuf(ByteBuf paramByteBuf);
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */