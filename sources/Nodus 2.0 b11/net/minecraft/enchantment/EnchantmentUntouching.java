/*  1:   */ package net.minecraft.enchantment;
/*  2:   */ 
/*  3:   */ import net.minecraft.init.Items;
/*  4:   */ import net.minecraft.item.ItemStack;
/*  5:   */ 
/*  6:   */ public class EnchantmentUntouching
/*  7:   */   extends Enchantment
/*  8:   */ {
/*  9:   */   private static final String __OBFID = "CL_00000123";
/* 10:   */   
/* 11:   */   protected EnchantmentUntouching(int par1, int par2)
/* 12:   */   {
/* 13:12 */     super(par1, par2, EnumEnchantmentType.digger);
/* 14:13 */     setName("untouching");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int getMinEnchantability(int par1)
/* 18:   */   {
/* 19:21 */     return 15;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int getMaxEnchantability(int par1)
/* 23:   */   {
/* 24:29 */     return super.getMinEnchantability(par1) + 50;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int getMaxLevel()
/* 28:   */   {
/* 29:37 */     return 1;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean canApplyTogether(Enchantment par1Enchantment)
/* 33:   */   {
/* 34:45 */     return (super.canApplyTogether(par1Enchantment)) && (par1Enchantment.effectId != fortune.effectId);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean canApply(ItemStack par1ItemStack)
/* 38:   */   {
/* 39:50 */     return par1ItemStack.getItem() == Items.shears ? true : super.canApply(par1ItemStack);
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnchantmentUntouching
 * JD-Core Version:    0.7.0.1
 */