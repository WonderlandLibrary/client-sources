/*     */ package net.minecraft.enchantment;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class EnchantmentDamage
/*     */   extends Enchantment
/*     */ {
/*  15 */   private static final String[] protectionName = new String[] { "all", "undead", "arthropods" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  20 */   private static final int[] baseEnchantability = new int[] { 1, 5, 5 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   private static final int[] levelEnchantability = new int[] { 11, 8, 8 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   private static final int[] thresholdEnchantability = new int[] { 20, 20, 20 };
/*     */ 
/*     */ 
/*     */   
/*     */   public final int damageType;
/*     */ 
/*     */ 
/*     */   
/*     */   public EnchantmentDamage(int enchID, ResourceLocation enchName, int enchWeight, int classification) {
/*  40 */     super(enchID, enchName, enchWeight, EnumEnchantmentType.WEAPON);
/*  41 */     this.damageType = classification;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinEnchantability(int enchantmentLevel) {
/*  49 */     return baseEnchantability[this.damageType] + (enchantmentLevel - 1) * levelEnchantability[this.damageType];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEnchantability(int enchantmentLevel) {
/*  57 */     return getMinEnchantability(enchantmentLevel) + thresholdEnchantability[this.damageType];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxLevel() {
/*  65 */     return 5;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
/*  74 */     return (this.damageType == 0) ? (level * 1.25F) : ((this.damageType == 1 && creatureType == EnumCreatureAttribute.UNDEAD) ? (level * 2.5F) : ((this.damageType == 2 && creatureType == EnumCreatureAttribute.ARTHROPOD) ? (level * 2.5F) : 0.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  82 */     return "enchantment.damage." + protectionName[this.damageType];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApplyTogether(Enchantment ench) {
/*  90 */     return !(ench instanceof EnchantmentDamage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canApply(ItemStack stack) {
/*  98 */     return (stack.getItem() instanceof net.minecraft.item.ItemAxe) ? true : super.canApply(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
/* 106 */     if (target instanceof EntityLivingBase) {
/*     */       
/* 108 */       EntityLivingBase entitylivingbase = (EntityLivingBase)target;
/*     */       
/* 110 */       if (this.damageType == 2 && entitylivingbase.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
/*     */         
/* 112 */         int i = 20 + user.getRNG().nextInt(10 * level);
/* 113 */         entitylivingbase.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, i, 3));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\enchantment\EnchantmentDamage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */