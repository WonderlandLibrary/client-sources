/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.stats.AchievementList;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAIMate
/*     */   extends EntityAIBase
/*     */ {
/*     */   private EntityAnimal theAnimal;
/*     */   World theWorld;
/*     */   private EntityAnimal targetMate;
/*     */   int spawnBabyDelay;
/*     */   double moveSpeed;
/*     */   
/*     */   public EntityAIMate(EntityAnimal animal, double speedIn) {
/*  31 */     this.theAnimal = animal;
/*  32 */     this.theWorld = animal.worldObj;
/*  33 */     this.moveSpeed = speedIn;
/*  34 */     setMutexBits(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  42 */     if (!this.theAnimal.isInLove())
/*     */     {
/*  44 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  48 */     this.targetMate = getNearbyMate();
/*  49 */     return (this.targetMate != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  58 */     return (this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  66 */     this.targetMate = null;
/*  67 */     this.spawnBabyDelay = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  75 */     this.theAnimal.getLookHelper().setLookPositionWithEntity((Entity)this.targetMate, 10.0F, this.theAnimal.getVerticalFaceSpeed());
/*  76 */     this.theAnimal.getNavigator().tryMoveToEntityLiving((Entity)this.targetMate, this.moveSpeed);
/*  77 */     this.spawnBabyDelay++;
/*     */     
/*  79 */     if (this.spawnBabyDelay >= 60 && this.theAnimal.getDistanceSqToEntity((Entity)this.targetMate) < 9.0D)
/*     */     {
/*  81 */       spawnBaby();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityAnimal getNearbyMate() {
/*  91 */     float f = 8.0F;
/*  92 */     List<EntityAnimal> list = this.theWorld.getEntitiesWithinAABB(this.theAnimal.getClass(), this.theAnimal.getEntityBoundingBox().expand(f, f, f));
/*  93 */     double d0 = Double.MAX_VALUE;
/*  94 */     EntityAnimal entityanimal = null;
/*     */     
/*  96 */     for (EntityAnimal entityanimal1 : list) {
/*     */       
/*  98 */       if (this.theAnimal.canMateWith(entityanimal1) && this.theAnimal.getDistanceSqToEntity((Entity)entityanimal1) < d0) {
/*     */         
/* 100 */         entityanimal = entityanimal1;
/* 101 */         d0 = this.theAnimal.getDistanceSqToEntity((Entity)entityanimal1);
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return entityanimal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void spawnBaby() {
/* 113 */     EntityAgeable entityageable = this.theAnimal.createChild((EntityAgeable)this.targetMate);
/*     */     
/* 115 */     if (entityageable != null) {
/*     */       
/* 117 */       EntityPlayer entityplayer = this.theAnimal.getPlayerInLove();
/*     */       
/* 119 */       if (entityplayer == null && this.targetMate.getPlayerInLove() != null)
/*     */       {
/* 121 */         entityplayer = this.targetMate.getPlayerInLove();
/*     */       }
/*     */       
/* 124 */       if (entityplayer != null) {
/*     */         
/* 126 */         entityplayer.triggerAchievement(StatList.animalsBredStat);
/*     */         
/* 128 */         if (this.theAnimal instanceof net.minecraft.entity.passive.EntityCow)
/*     */         {
/* 130 */           entityplayer.triggerAchievement((StatBase)AchievementList.breedCow);
/*     */         }
/*     */       } 
/*     */       
/* 134 */       this.theAnimal.setGrowingAge(6000);
/* 135 */       this.targetMate.setGrowingAge(6000);
/* 136 */       this.theAnimal.resetInLove();
/* 137 */       this.targetMate.resetInLove();
/* 138 */       entityageable.setGrowingAge(-24000);
/* 139 */       entityageable.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0F, 0.0F);
/* 140 */       this.theWorld.spawnEntityInWorld((Entity)entityageable);
/* 141 */       Random random = this.theAnimal.getRNG();
/*     */       
/* 143 */       for (int i = 0; i < 7; i++) {
/*     */         
/* 145 */         double d0 = random.nextGaussian() * 0.02D;
/* 146 */         double d1 = random.nextGaussian() * 0.02D;
/* 147 */         double d2 = random.nextGaussian() * 0.02D;
/* 148 */         double d3 = random.nextDouble() * this.theAnimal.width * 2.0D - this.theAnimal.width;
/* 149 */         double d4 = 0.5D + random.nextDouble() * this.theAnimal.height;
/* 150 */         double d5 = random.nextDouble() * this.theAnimal.width * 2.0D - this.theAnimal.width;
/* 151 */         this.theWorld.spawnParticle(EnumParticleTypes.HEART, this.theAnimal.posX + d3, this.theAnimal.posY + d4, this.theAnimal.posZ + d5, d0, d1, d2, new int[0]);
/*     */       } 
/*     */       
/* 154 */       if (this.theWorld.getGameRules().getBoolean("doMobLoot"))
/*     */       {
/* 156 */         this.theWorld.spawnEntityInWorld((Entity)new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, random.nextInt(7) + 1));
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAIMate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */