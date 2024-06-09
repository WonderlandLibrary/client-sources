/*    */ package net.minecraft.enchantment;
/*    */ 
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EnchantmentOxygen
/*    */   extends Enchantment
/*    */ {
/*    */   public EnchantmentOxygen(int enchID, ResourceLocation p_i45766_2_, int p_i45766_3_) {
/*  9 */     super(enchID, p_i45766_2_, p_i45766_3_, EnumEnchantmentType.ARMOR_HEAD);
/* 10 */     setName("oxygen");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMinEnchantability(int enchantmentLevel) {
/* 18 */     return 10 * enchantmentLevel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxEnchantability(int enchantmentLevel) {
/* 26 */     return getMinEnchantability(enchantmentLevel) + 30;
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


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\enchantment\EnchantmentOxygen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */