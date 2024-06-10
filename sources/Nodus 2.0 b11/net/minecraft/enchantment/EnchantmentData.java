/*  1:   */ package net.minecraft.enchantment;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.WeightedRandom.Item;
/*  4:   */ 
/*  5:   */ public class EnchantmentData
/*  6:   */   extends WeightedRandom.Item
/*  7:   */ {
/*  8:   */   public final Enchantment enchantmentobj;
/*  9:   */   public final int enchantmentLevel;
/* 10:   */   private static final String __OBFID = "CL_00000115";
/* 11:   */   
/* 12:   */   public EnchantmentData(Enchantment par1Enchantment, int par2)
/* 13:   */   {
/* 14:16 */     super(par1Enchantment.getWeight());
/* 15:17 */     this.enchantmentobj = par1Enchantment;
/* 16:18 */     this.enchantmentLevel = par2;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public EnchantmentData(int par1, int par2)
/* 20:   */   {
/* 21:23 */     this(Enchantment.enchantmentsList[par1], par2);
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnchantmentData
 * JD-Core Version:    0.7.0.1
 */