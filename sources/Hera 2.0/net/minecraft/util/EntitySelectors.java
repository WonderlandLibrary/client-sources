/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EntitySelectors
/*    */ {
/* 14 */   public static final Predicate<Entity> selectAnything = new Predicate<Entity>()
/*    */     {
/*    */       public boolean apply(Entity p_apply_1_)
/*    */       {
/* 18 */         return p_apply_1_.isEntityAlive();
/*    */       }
/*    */     };
/* 21 */   public static final Predicate<Entity> IS_STANDALONE = new Predicate<Entity>()
/*    */     {
/*    */       public boolean apply(Entity p_apply_1_)
/*    */       {
/* 25 */         return (p_apply_1_.isEntityAlive() && p_apply_1_.riddenByEntity == null && p_apply_1_.ridingEntity == null);
/*    */       }
/*    */     };
/* 28 */   public static final Predicate<Entity> selectInventories = new Predicate<Entity>()
/*    */     {
/*    */       public boolean apply(Entity p_apply_1_)
/*    */       {
/* 32 */         return (p_apply_1_ instanceof net.minecraft.inventory.IInventory && p_apply_1_.isEntityAlive());
/*    */       }
/*    */     };
/* 35 */   public static final Predicate<Entity> NOT_SPECTATING = new Predicate<Entity>()
/*    */     {
/*    */       public boolean apply(Entity p_apply_1_)
/*    */       {
/* 39 */         return !(p_apply_1_ instanceof EntityPlayer && ((EntityPlayer)p_apply_1_).isSpectator());
/*    */       }
/*    */     };
/*    */   
/*    */   public static class ArmoredMob
/*    */     implements Predicate<Entity>
/*    */   {
/*    */     private final ItemStack armor;
/*    */     
/*    */     public ArmoredMob(ItemStack armor) {
/* 49 */       this.armor = armor;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean apply(Entity p_apply_1_) {
/* 54 */       if (!p_apply_1_.isEntityAlive())
/*    */       {
/* 56 */         return false;
/*    */       }
/* 58 */       if (!(p_apply_1_ instanceof EntityLivingBase))
/*    */       {
/* 60 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 64 */       EntityLivingBase entitylivingbase = (EntityLivingBase)p_apply_1_;
/* 65 */       return (entitylivingbase.getEquipmentInSlot(EntityLiving.getArmorPosition(this.armor)) != null) ? false : ((entitylivingbase instanceof EntityLiving) ? ((EntityLiving)entitylivingbase).canPickUpLoot() : ((entitylivingbase instanceof net.minecraft.entity.item.EntityArmorStand) ? true : (entitylivingbase instanceof EntityPlayer)));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\EntitySelectors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */