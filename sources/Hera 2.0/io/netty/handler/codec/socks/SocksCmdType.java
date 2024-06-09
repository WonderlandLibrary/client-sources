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
/*    */ public enum SocksCmdType
/*    */ {
/* 20 */   CONNECT((byte)1),
/* 21 */   BIND((byte)2),
/* 22 */   UDP((byte)3),
/* 23 */   UNKNOWN((byte)-1);
/*    */   
/*    */   private final byte b;
/*    */   
/*    */   SocksCmdType(byte b) {
/* 28 */     this.b = b;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static SocksCmdType fromByte(byte b) {
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


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\socks\SocksCmdType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */