/*  1:   */ package net.minecraft.enchantment;
/*  2:   */ 
/*  3:   */ public class EnchantmentLootBonus
/*  4:   */   extends Enchantment
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00000119";
/*  7:   */   
/*  8:   */   protected EnchantmentLootBonus(int par1, int par2, EnumEnchantmentType par3EnumEnchantmentType)
/*  9:   */   {
/* 10: 9 */     super(par1, par2, par3EnumEnchantmentType);
/* 11:11 */     if (par3EnumEnchantmentType == EnumEnchantmentType.digger) {
/* 12:13 */       setName("lootBonusDigger");
/* 13:15 */     } else if (par3EnumEnchantmentType == EnumEnchantmentType.fishing_rod) {
/* 14:17 */       setName("lootBonusFishing");
/* 15:   */     } else {
/* 16:21 */       setName("lootBonus");
/* 17:   */     }
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getMinEnchantability(int par1)
/* 21:   */   {
/* 22:30 */     return 15 + (par1 - 1) * 9;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getMaxEnchantability(int par1)
/* 26:   */   {
/* 27:38 */     return super.getMinEnchantability(par1) + 50;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getMaxLevel()
/* 31:   */   {
/* 32:46 */     return 3;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean canApplyTogether(Enchantment par1Enchantment)
/* 36:   */   {
/* 37:54 */     return (super.canApplyTogether(par1Enchantment)) && (par1Enchantment.effectId != silkTouch.effectId);
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnchantmentLootBonus
 * JD-Core Version:    0.7.0.1
 */