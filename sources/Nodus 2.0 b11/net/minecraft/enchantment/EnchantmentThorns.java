/*  1:   */ package net.minecraft.enchantment;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLivingBase;
/*  6:   */ import net.minecraft.item.ItemArmor;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraft.util.DamageSource;
/*  9:   */ 
/* 10:   */ public class EnchantmentThorns
/* 11:   */   extends Enchantment
/* 12:   */ {
/* 13:   */   private static final String __OBFID = "CL_00000122";
/* 14:   */   
/* 15:   */   public EnchantmentThorns(int par1, int par2)
/* 16:   */   {
/* 17:16 */     super(par1, par2, EnumEnchantmentType.armor_torso);
/* 18:17 */     setName("thorns");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getMinEnchantability(int par1)
/* 22:   */   {
/* 23:25 */     return 10 + 20 * (par1 - 1);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getMaxEnchantability(int par1)
/* 27:   */   {
/* 28:33 */     return super.getMinEnchantability(par1) + 50;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getMaxLevel()
/* 32:   */   {
/* 33:41 */     return 3;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean canApply(ItemStack par1ItemStack)
/* 37:   */   {
/* 38:46 */     return (par1ItemStack.getItem() instanceof ItemArmor) ? true : super.canApply(par1ItemStack);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void func_151367_b(EntityLivingBase p_151367_1_, Entity p_151367_2_, int p_151367_3_)
/* 42:   */   {
/* 43:51 */     Random var4 = p_151367_1_.getRNG();
/* 44:52 */     ItemStack var5 = EnchantmentHelper.func_92099_a(Enchantment.thorns, p_151367_1_);
/* 45:54 */     if (func_92094_a(p_151367_3_, var4))
/* 46:   */     {
/* 47:56 */       p_151367_2_.attackEntityFrom(DamageSource.causeThornsDamage(p_151367_1_), func_92095_b(p_151367_3_, var4));
/* 48:57 */       p_151367_2_.playSound("damage.thorns", 0.5F, 1.0F);
/* 49:59 */       if (var5 != null) {
/* 50:61 */         var5.damageItem(3, p_151367_1_);
/* 51:   */       }
/* 52:   */     }
/* 53:64 */     else if (var5 != null)
/* 54:   */     {
/* 55:66 */       var5.damageItem(1, p_151367_1_);
/* 56:   */     }
/* 57:   */   }
/* 58:   */   
/* 59:   */   public static boolean func_92094_a(int par0, Random par1Random)
/* 60:   */   {
/* 61:72 */     return par0 > 0;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static int func_92095_b(int par0, Random par1Random)
/* 65:   */   {
/* 66:77 */     return par0 > 10 ? par0 - 10 : 1 + par1Random.nextInt(4);
/* 67:   */   }
/* 68:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnchantmentThorns
 * JD-Core Version:    0.7.0.1
 */