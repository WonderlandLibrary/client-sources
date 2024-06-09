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
/*    */ public enum SocksCmdStatus
/*    */ {
/* 20 */   SUCCESS((byte)0),
/* 21 */   FAILURE((byte)1),
/* 22 */   FORBIDDEN((byte)2),
/* 23 */   NETWORK_UNREACHABLE((byte)3),
/* 24 */   HOST_UNREACHABLE((byte)4),
/* 25 */   REFUSED((byte)5),
/* 26 */   TTL_EXPIRED((byte)6),
/* 27 */   COMMAND_NOT_SUPPORTED((byte)7),
/* 28 */   ADDRESS_NOT_SUPPORTED((byte)8),
/* 29 */   UNASSIGNED((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksCmdStatus(byte b) {
/* 34 */     this.b = b;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static SocksCmdStatus fromByte(byte b) {
/* 42 */     return valueOf(b);
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
/* 55 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksCmdStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */