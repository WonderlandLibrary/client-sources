/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.client.multiplayer.ServerAddress;
/*    */ 
/*    */ 
/*    */ public class RealmsServerAddress
/*    */ {
/*    */   private final String host;
/*    */   private final int port;
/*    */   
/*    */   protected RealmsServerAddress(String p_i1121_1_, int p_i1121_2_) {
/* 12 */     this.host = p_i1121_1_;
/* 13 */     this.port = p_i1121_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHost() {
/* 18 */     return this.host;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPort() {
/* 23 */     return this.port;
/*    */   }
/*    */ 
/*    */   
/*    */   public static RealmsServerAddress parseString(String p_parseString_0_) {
/* 28 */     ServerAddress serveraddress = ServerAddress.func_78860_a(p_parseString_0_);
/* 29 */     return new RealmsServerAddress(serveraddress.getIP(), serveraddress.getPort());
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\realms\RealmsServerAddress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */