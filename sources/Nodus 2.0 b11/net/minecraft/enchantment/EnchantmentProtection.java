/*   1:    */ package net.minecraft.enchantment;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.util.DamageSource;
/*   5:    */ import net.minecraft.util.MathHelper;
/*   6:    */ 
/*   7:    */ public class EnchantmentProtection
/*   8:    */   extends Enchantment
/*   9:    */ {
/*  10: 10 */   private static final String[] protectionName = { "all", "fire", "fall", "explosion", "projectile" };
/*  11: 15 */   private static final int[] baseEnchantability = { 1, 10, 5, 5, 3 };
/*  12: 20 */   private static final int[] levelEnchantability = { 11, 8, 6, 8, 6 };
/*  13: 26 */   private static final int[] thresholdEnchantability = { 20, 12, 10, 12, 15 };
/*  14:    */   public final int protectionType;
/*  15:    */   private static final String __OBFID = "CL_00000121";
/*  16:    */   
/*  17:    */   public EnchantmentProtection(int par1, int par2, int par3)
/*  18:    */   {
/*  19: 37 */     super(par1, par2, EnumEnchantmentType.armor);
/*  20: 38 */     this.protectionType = par3;
/*  21: 40 */     if (par3 == 2) {
/*  22: 42 */       this.type = EnumEnchantmentType.armor_feet;
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int getMinEnchantability(int par1)
/*  27:    */   {
/*  28: 51 */     return baseEnchantability[this.protectionType] + (par1 - 1) * levelEnchantability[this.protectionType];
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int getMaxEnchantability(int par1)
/*  32:    */   {
/*  33: 59 */     return getMinEnchantability(par1) + thresholdEnchantability[this.protectionType];
/*  34:    */   }
/*  35:    */   
/*  36:    */   public int getMaxLevel()
/*  37:    */   {
/*  38: 67 */     return 4;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int calcModifierDamage(int par1, DamageSource par2DamageSource)
/*  42:    */   {
/*  43: 75 */     if (par2DamageSource.canHarmInCreative()) {
/*  44: 77 */       return 0;
/*  45:    */     }
/*  46: 81 */     float var3 = (6 + par1 * par1) / 3.0F;
/*  47: 82 */     return (this.protectionType == 4) && (par2DamageSource.isProjectile()) ? MathHelper.floor_float(var3 * 1.5F) : (this.protectionType == 3) && (par2DamageSource.isExplosion()) ? MathHelper.floor_float(var3 * 1.5F) : (this.protectionType == 2) && (par2DamageSource == DamageSource.fall) ? MathHelper.floor_float(var3 * 2.5F) : (this.protectionType == 1) && (par2DamageSource.isFireDamage()) ? MathHelper.floor_float(var3 * 1.25F) : this.protectionType == 0 ? MathHelper.floor_float(var3 * 0.75F) : 0;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getName()
/*  51:    */   {
/*  52: 91 */     return "enchantment.protect." + protectionName[this.protectionType];
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean canApplyTogether(Enchantment par1Enchantment)
/*  56:    */   {
/*  57: 99 */     if ((par1Enchantment instanceof EnchantmentProtection))
/*  58:    */     {
/*  59:101 */       EnchantmentProtection var2 = (EnchantmentProtection)par1Enchantment;
/*  60:102 */       return var2.protectionType != this.protectionType;
/*  61:    */     }
/*  62:106 */     return super.canApplyTogether(par1Enchantment);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static int getFireTimeForEntity(Entity par0Entity, int par1)
/*  66:    */   {
/*  67:115 */     int var2 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, par0Entity.getLastActiveItems());
/*  68:117 */     if (var2 > 0) {
/*  69:119 */       par1 -= MathHelper.floor_float(par1 * var2 * 0.15F);
/*  70:    */     }
/*  71:122 */     return par1;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static double func_92092_a(Entity par0Entity, double par1)
/*  75:    */   {
/*  76:127 */     int var3 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, par0Entity.getLastActiveItems());
/*  77:129 */     if (var3 > 0) {
/*  78:131 */       par1 -= MathHelper.floor_double(par1 * (var3 * 0.15F));
/*  79:    */     }
/*  80:134 */     return par1;
/*  81:    */   }
/*  82:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnchantmentProtection
 * JD-Core Version:    0.7.0.1
 */