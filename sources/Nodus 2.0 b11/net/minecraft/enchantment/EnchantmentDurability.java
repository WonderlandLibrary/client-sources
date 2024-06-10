/*  1:   */ package net.minecraft.enchantment;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.item.ItemArmor;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class EnchantmentDurability
/*  8:   */   extends Enchantment
/*  9:   */ {
/* 10:   */   private static final String __OBFID = "CL_00000103";
/* 11:   */   
/* 12:   */   protected EnchantmentDurability(int par1, int par2)
/* 13:   */   {
/* 14:13 */     super(par1, par2, EnumEnchantmentType.breakable);
/* 15:14 */     setName("durability");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getMinEnchantability(int par1)
/* 19:   */   {
/* 20:22 */     return 5 + (par1 - 1) * 8;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getMaxEnchantability(int par1)
/* 24:   */   {
/* 25:30 */     return super.getMinEnchantability(par1) + 50;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getMaxLevel()
/* 29:   */   {
/* 30:38 */     return 3;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean canApply(ItemStack par1ItemStack)
/* 34:   */   {
/* 35:43 */     return par1ItemStack.isItemStackDamageable() ? true : super.canApply(par1ItemStack);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static boolean negateDamage(ItemStack par0ItemStack, int par1, Random par2Random)
/* 39:   */   {
/* 40:53 */     return (!(par0ItemStack.getItem() instanceof ItemArmor)) || (par2Random.nextFloat() >= 0.6F);
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnchantmentDurability
 * JD-Core Version:    0.7.0.1
 */