/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ 
/*     */ public class EntityAIFollowGolem
/*     */   extends EntityAIBase {
/*     */   private EntityVillager theVillager;
/*     */   private EntityIronGolem theGolem;
/*     */   private int takeGolemRoseTick;
/*     */   private boolean tookGolemRose;
/*     */   
/*     */   public EntityAIFollowGolem(EntityVillager theVillagerIn) {
/*  16 */     this.theVillager = theVillagerIn;
/*  17 */     setMutexBits(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  25 */     if (this.theVillager.getGrowingAge() >= 0)
/*     */     {
/*  27 */       return false;
/*     */     }
/*  29 */     if (!this.theVillager.worldObj.isDaytime())
/*     */     {
/*  31 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  35 */     List<EntityIronGolem> list = this.theVillager.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, this.theVillager.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D));
/*     */     
/*  37 */     if (list.isEmpty())
/*     */     {
/*  39 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  43 */     for (EntityIronGolem entityirongolem : list) {
/*     */       
/*  45 */       if (entityirongolem.getHoldRoseTick() > 0) {
/*     */         
/*  47 */         this.theGolem = entityirongolem;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  52 */     return (this.theGolem != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  62 */     return (this.theGolem.getHoldRoseTick() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  70 */     this.takeGolemRoseTick = this.theVillager.getRNG().nextInt(320);
/*  71 */     this.tookGolemRose = false;
/*  72 */     this.theGolem.getNavigator().clearPathEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  80 */     this.theGolem = null;
/*  81 */     this.theVillager.getNavigator().clearPathEntity();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  89 */     this.theVillager.getLookHelper().setLookPositionWithEntity((Entity)this.theGolem, 30.0F, 30.0F);
/*     */     
/*  91 */     if (this.theGolem.getHoldRoseTick() == this.takeGolemRoseTick) {
/*     */       
/*  93 */       this.theVillager.getNavigator().tryMoveToEntityLiving((Entity)this.theGolem, 0.5D);
/*  94 */       this.tookGolemRose = true;
/*     */     } 
/*     */     
/*  97 */     if (this.tookGolemRose && this.theVillager.getDistanceSqToEntity((Entity)this.theGolem) < 4.0D) {
/*     */       
/*  99 */       this.theGolem.setHoldingRose(false);
/* 100 */       this.theVillager.getNavigator().clearPathEntity();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAIFollowGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */