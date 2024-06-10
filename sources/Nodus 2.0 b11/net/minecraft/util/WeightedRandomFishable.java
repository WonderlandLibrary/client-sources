/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.enchantment.EnchantmentHelper;
/*  5:   */ import net.minecraft.item.ItemStack;
/*  6:   */ 
/*  7:   */ public class WeightedRandomFishable
/*  8:   */   extends WeightedRandom.Item
/*  9:   */ {
/* 10:   */   private final ItemStack field_150711_b;
/* 11:   */   private float field_150712_c;
/* 12:   */   private boolean field_150710_d;
/* 13:   */   private static final String __OBFID = "CL_00001664";
/* 14:   */   
/* 15:   */   public WeightedRandomFishable(ItemStack p_i45317_1_, int p_i45317_2_)
/* 16:   */   {
/* 17:16 */     super(p_i45317_2_);
/* 18:17 */     this.field_150711_b = p_i45317_1_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ItemStack func_150708_a(Random p_150708_1_)
/* 22:   */   {
/* 23:22 */     ItemStack var2 = this.field_150711_b.copy();
/* 24:24 */     if (this.field_150712_c > 0.0F)
/* 25:   */     {
/* 26:26 */       int var3 = (int)(this.field_150712_c * this.field_150711_b.getMaxDamage());
/* 27:27 */       int var4 = var2.getMaxDamage() - p_150708_1_.nextInt(p_150708_1_.nextInt(var3) + 1);
/* 28:29 */       if (var4 > var3) {
/* 29:31 */         var4 = var3;
/* 30:   */       }
/* 31:34 */       if (var4 < 1) {
/* 32:36 */         var4 = 1;
/* 33:   */       }
/* 34:39 */       var2.setItemDamage(var4);
/* 35:   */     }
/* 36:42 */     if (this.field_150710_d) {
/* 37:44 */       EnchantmentHelper.addRandomEnchantment(p_150708_1_, var2, 30);
/* 38:   */     }
/* 39:47 */     return var2;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public WeightedRandomFishable func_150709_a(float p_150709_1_)
/* 43:   */   {
/* 44:52 */     this.field_150712_c = p_150709_1_;
/* 45:53 */     return this;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public WeightedRandomFishable func_150707_a()
/* 49:   */   {
/* 50:58 */     this.field_150710_d = true;
/* 51:59 */     return this;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.WeightedRandomFishable
 * JD-Core Version:    0.7.0.1
 */