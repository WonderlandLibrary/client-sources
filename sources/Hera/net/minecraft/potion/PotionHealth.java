/*    */ package net.minecraft.potion;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class PotionHealth
/*    */   extends Potion
/*    */ {
/*    */   public PotionHealth(int potionID, ResourceLocation location, boolean badEffect, int potionColor) {
/*  9 */     super(potionID, location, badEffect, potionColor);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInstant() {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isReady(int p_76397_1_, int p_76397_2_) {
/* 25 */     return (p_76397_1_ >= 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\potion\PotionHealth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */