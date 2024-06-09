/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.util.IChatComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatLine
/*    */ {
/*    */   private final int updateCounterCreated;
/*    */   private final IChatComponent lineString;
/*    */   private final int chatLineID;
/*    */   
/*    */   public ChatLine(int p_i45000_1_, IChatComponent p_i45000_2_, int p_i45000_3_) {
/* 18 */     this.lineString = p_i45000_2_;
/* 19 */     this.updateCounterCreated = p_i45000_1_;
/* 20 */     this.chatLineID = p_i45000_3_;
/*    */   }
/*    */ 
/*    */   
/*    */   public IChatComponent getChatComponent() {
/* 25 */     return this.lineString;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getUpdatedCounter() {
/* 30 */     return this.updateCounterCreated;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getChatLineID() {
/* 35 */     return this.chatLineID;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\ChatLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */