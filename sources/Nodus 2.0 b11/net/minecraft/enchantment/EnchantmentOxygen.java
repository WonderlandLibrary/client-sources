/*  1:   */ package net.minecraft.enchantment;
/*  2:   */ 
/*  3:   */ public class EnchantmentOxygen
/*  4:   */   extends Enchantment
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000120";
/*  7:   */   
/*  8:   */   public EnchantmentOxygen(int par1, int par2)
/*  9:   */   {
/* 10: 9 */     super(par1, par2, EnumEnchantmentType.armor_head);
/* 11:10 */     setName("oxygen");
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int getMinEnchantability(int par1)
/* 15:   */   {
/* 16:18 */     return 10 * par1;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public int getMaxEnchantability(int par1)
/* 20:   */   {
/* 21:26 */     return getMinEnchantability(par1) + 30;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getMaxLevel()
/* 25:   */   {
/* 26:34 */     return 3;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnchantmentOxygen
 * JD-Core Version:    0.7.0.1
 */