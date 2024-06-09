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
/*    */ public enum SocksProtocolVersion
/*    */ {
/* 20 */   SOCKS4a((byte)4),
/* 21 */   SOCKS5((byte)5),
/* 22 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksProtocolVersion(byte b) {
/* 27 */     this.b = b;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static SocksProtocolVersion fromByte(byte b) {
/* 35 */     return valueOf(b);
/*    */   }
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
/*    */   public byte byteValue() {
/* 48 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksProtocolVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */