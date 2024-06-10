/*   1:    */ package net.minecraft.enchantment;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.entity.Entity;
/*   5:    */ import net.minecraft.entity.EntityLivingBase;
/*   6:    */ import net.minecraft.entity.EnumCreatureAttribute;
/*   7:    */ import net.minecraft.item.ItemAxe;
/*   8:    */ import net.minecraft.item.ItemStack;
/*   9:    */ import net.minecraft.potion.Potion;
/*  10:    */ import net.minecraft.potion.PotionEffect;
/*  11:    */ 
/*  12:    */ public class EnchantmentDamage
/*  13:    */   extends Enchantment
/*  14:    */ {
/*  15: 14 */   private static final String[] protectionName = { "all", "undead", "arthropods" };
/*  16: 19 */   private static final int[] baseEnchantability = { 1, 5, 5 };
/*  17: 24 */   private static final int[] levelEnchantability = { 11, 8, 8 };
/*  18: 30 */   private static final int[] thresholdEnchantability = { 20, 20, 20 };
/*  19:    */   public final int damageType;
/*  20:    */   private static final String __OBFID = "CL_00000102";
/*  21:    */   
/*  22:    */   public EnchantmentDamage(int par1, int par2, int par3)
/*  23:    */   {
/*  24: 40 */     super(par1, par2, EnumEnchantmentType.weapon);
/*  25: 41 */     this.damageType = par3;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int getMinEnchantability(int par1)
/*  29:    */   {
/*  30: 49 */     return baseEnchantability[this.damageType] + (par1 - 1) * levelEnchantability[this.damageType];
/*  31:    */   }
/*  32:    */   
/*  33:    */   public int getMaxEnchantability(int par1)
/*  34:    */   {
/*  35: 57 */     return getMinEnchantability(par1) + thresholdEnchantability[this.damageType];
/*  36:    */   }
/*  37:    */   
/*  38:    */   public int getMaxLevel()
/*  39:    */   {
/*  40: 65 */     return 5;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public float calcModifierLiving(int par1, EntityLivingBase par2EntityLivingBase)
/*  44:    */   {
/*  45: 73 */     return (this.damageType == 2) && (par2EntityLivingBase.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) ? par1 * 2.5F : (this.damageType == 1) && (par2EntityLivingBase.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) ? par1 * 2.5F : this.damageType == 0 ? par1 * 1.25F : 0.0F;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public String getName()
/*  49:    */   {
/*  50: 81 */     return "enchantment.damage." + protectionName[this.damageType];
/*  51:    */   }
/*  52:    */   
/*  53:    */   public boolean canApplyTogether(Enchantment par1Enchantment)
/*  54:    */   {
/*  55: 89 */     return !(par1Enchantment instanceof EnchantmentDamage);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean canApply(ItemStack par1ItemStack)
/*  59:    */   {
/*  60: 94 */     return (par1ItemStack.getItem() instanceof ItemAxe) ? true : super.canApply(par1ItemStack);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void func_151368_a(EntityLivingBase p_151368_1_, Entity p_151368_2_, int p_151368_3_)
/*  64:    */   {
/*  65: 99 */     if ((p_151368_2_ instanceof EntityLivingBase))
/*  66:    */     {
/*  67:101 */       EntityLivingBase var4 = (EntityLivingBase)p_151368_2_;
/*  68:103 */       if ((this.damageType == 2) && (var4.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD))
/*  69:    */       {
/*  70:105 */         int var5 = 20 + p_151368_1_.getRNG().nextInt(10 * p_151368_3_);
/*  71:106 */         var4.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, var5, 3));
/*  72:    */       }
/*  73:    */     }
/*  74:    */   }
/*  75:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.enchantment.EnchantmentDamage
 * JD-Core Version:    0.7.0.1
 */