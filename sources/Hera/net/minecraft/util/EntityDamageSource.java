/*    */ package net.minecraft.util;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityDamageSource
/*    */   extends DamageSource
/*    */ {
/*    */   protected Entity damageSourceEntity;
/*    */   private boolean isThornsDamage = false;
/*    */   
/*    */   public EntityDamageSource(String p_i1567_1_, Entity damageSourceEntityIn) {
/* 19 */     super(p_i1567_1_);
/* 20 */     this.damageSourceEntity = damageSourceEntityIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EntityDamageSource setIsThornsDamage() {
/* 28 */     this.isThornsDamage = true;
/* 29 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getIsThornsDamage() {
/* 34 */     return this.isThornsDamage;
/*    */   }
/*    */ 
/*    */   
/*    */   public Entity getEntity() {
/* 39 */     return this.damageSourceEntity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChatComponent getDeathMessage(EntityLivingBase p_151519_1_) {
/* 47 */     ItemStack itemstack = (this.damageSourceEntity instanceof EntityLivingBase) ? ((EntityLivingBase)this.damageSourceEntity).getHeldItem() : null;
/* 48 */     String s = "death.attack." + this.damageType;
/* 49 */     String s1 = String.valueOf(s) + ".item";
/* 50 */     return (itemstack != null && itemstack.hasDisplayName() && StatCollector.canTranslate(s1)) ? new ChatComponentTranslation(s1, new Object[] { p_151519_1_.getDisplayName(), this.damageSourceEntity.getDisplayName(), itemstack.getChatComponent() }) : new ChatComponentTranslation(s, new Object[] { p_151519_1_.getDisplayName(), this.damageSourceEntity.getDisplayName() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDifficultyScaled() {
/* 58 */     return (this.damageSourceEntity != null && this.damageSourceEntity instanceof EntityLivingBase && !(this.damageSourceEntity instanceof net.minecraft.entity.player.EntityPlayer));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\EntityDamageSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */