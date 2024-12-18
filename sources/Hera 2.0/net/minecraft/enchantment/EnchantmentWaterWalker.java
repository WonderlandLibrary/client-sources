/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EnchantmentWaterWalker
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentWaterWalker(int p_i45762_1_, ResourceLocation p_i45762_2_, int p_i45762_3_) {
/*  9 */     super(p_i45762_1_, p_i45762_2_, p_i45762_3_, EnumEnchantmentType.ARMOR_FEET);
/* 10 */     setName("waterWalker");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 18 */     return enchantmentLevel * 10;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 26 */     return getMinEnchantability(enchantmentLevel) + 15;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxLevel() {
/* 34 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\enchantment\EnchantmentWaterWalker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */