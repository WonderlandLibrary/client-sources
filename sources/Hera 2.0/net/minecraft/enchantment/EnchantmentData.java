/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.util.WeightedRandom;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnchantmentData
/*    */   extends WeightedRandom.Item
/*    */ {
/*    */   public final Enchantment enchantmentobj;
/*    */   public final int enchantmentLevel;
/*    */   
/*    */   public EnchantmentData(Enchantment enchantmentObj, int enchLevel) {
/* 15 */     super(enchantmentObj.getWeight());
/* 16 */     this.enchantmentobj = enchantmentObj;
/* 17 */     this.enchantmentLevel = enchLevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\enchantment\EnchantmentData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */