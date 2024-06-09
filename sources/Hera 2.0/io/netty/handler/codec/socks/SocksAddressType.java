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
/*    */ public enum SocksAddressType
/*    */ {
/* 20 */   IPv4((byte)1),
/* 21 */   DOMAIN((byte)3),
/* 22 */   IPv6((byte)4),
/* 23 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksAddressType(byte b) {
/* 28 */     this.b = b;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static SocksAddressType fromByte(byte b) {
/* 36 */     return valueOf(b);
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
/* 49 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksAddressType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */