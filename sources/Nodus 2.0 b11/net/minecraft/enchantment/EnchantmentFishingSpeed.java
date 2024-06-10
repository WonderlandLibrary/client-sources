/*  1:   */ package net.minecraft.enchantment;
/*  2:   */ 
/*  3:   */ public class EnchantmentFishingSpeed
/*  4:   */   extends Enchantment
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000117";
/*  7:   */   
/*  8:   */   protected EnchantmentFishingSpeed(int p_i45361_1_, int p_i45361_2_, EnumEnchantmentType p_i45361_3_)
/*  9:   */   {
/* 10: 9 */     super(p_i45361_1_, p_i45361_2_, p_i45361_3_);
/* 11:10 */     setName("fishingSpeed");
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int getMinEnchantability(int par1)
/* 15:   */   {
/* 16:18 */     return 15 + (par1 - 1) * 9;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public int getMaxEnchantability(int par1)
/* 20:   */   {
/* 21:26 */     return super.getMinEnchantability(par1) + 50;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getMaxLevel()
/* 25:   */   {
/* 26:34 */     return 3;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnchantmentFishingSpeed
 * JD-Core Version:    0.7.0.1
 */