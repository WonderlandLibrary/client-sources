/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EnchantmentWaterWorker
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentWaterWorker(int p_i45761_1_, ResourceLocation p_i45761_2_, int p_i45761_3_) {
/*  9 */     super(p_i45761_1_, p_i45761_2_, p_i45761_3_, EnumEnchantmentType.ARMOR_HEAD);
/* 10 */     setName("waterWorker");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 18 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 26 */     return getMinEnchantability(enchantmentLevel) + 40;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 34 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\enchantment\EnchantmentWaterWorker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */