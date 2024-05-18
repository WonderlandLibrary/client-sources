/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EntityAIFindEntityNearest
/*     */   extends EntityAIBase {
/*  17 */   private static final Logger field_179444_a = LogManager.getLogger();
/*     */   
/*     */   private EntityLiving field_179442_b;
/*     */   private final Predicate<EntityLivingBase> field_179443_c;
/*     */   private final EntityAINearestAttackableTarget.Sorter field_179440_d;
/*     */   private EntityLivingBase field_179441_e;
/*     */   private Class<? extends EntityLivingBase> field_179439_f;
/*     */   
/*     */   public EntityAIFindEntityNearest(EntityLiving p_i45884_1_, Class<? extends EntityLivingBase> p_i45884_2_) {
/*  26 */     this.field_179442_b = p_i45884_1_;
/*  27 */     this.field_179439_f = p_i45884_2_;
/*     */     
/*  29 */     if (p_i45884_1_ instanceof net.minecraft.entity.EntityCreature)
/*     */     {
/*  31 */       field_179444_a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
/*     */     }
/*     */     
/*  34 */     this.field_179443_c = new Predicate<EntityLivingBase>()
/*     */       {
/*     */         public boolean apply(EntityLivingBase p_apply_1_)
/*     */         {
/*  38 */           double d0 = EntityAIFindEntityNearest.this.func_179438_f();
/*     */           
/*  40 */           if (p_apply_1_.isSneaking())
/*     */           {
/*  42 */             d0 *= 0.800000011920929D;
/*     */           }
/*     */           
/*  45 */           return p_apply_1_.isInvisible() ? false : ((p_apply_1_.getDistanceToEntity((Entity)EntityAIFindEntityNearest.this.field_179442_b) > d0) ? false : EntityAITarget.isSuitableTarget(EntityAIFindEntityNearest.this.field_179442_b, p_apply_1_, false, true));
/*     */         }
/*     */       };
/*  48 */     this.field_179440_d = new EntityAINearestAttackableTarget.Sorter((Entity)p_i45884_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  56 */     double d0 = func_179438_f();
/*  57 */     List<EntityLivingBase> list = this.field_179442_b.worldObj.getEntitiesWithinAABB(this.field_179439_f, this.field_179442_b.getEntityBoundingBox().expand(d0, 4.0D, d0), this.field_179443_c);
/*  58 */     Collections.sort(list, this.field_179440_d);
/*     */     
/*  60 */     if (list.isEmpty())
/*     */     {
/*  62 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  66 */     this.field_179441_e = list.get(0);
/*  67 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  76 */     EntityLivingBase entitylivingbase = this.field_179442_b.getAttackTarget();
/*     */     
/*  78 */     if (entitylivingbase == null)
/*     */     {
/*  80 */       return false;
/*     */     }
/*  82 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/*  84 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  88 */     double d0 = func_179438_f();
/*  89 */     return (this.field_179442_b.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0) ? false : (!(entitylivingbase instanceof EntityPlayerMP && ((EntityPlayerMP)entitylivingbase).theItemInWorldManager.isCreative()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  98 */     this.field_179442_b.setAttackTarget(this.field_179441_e);
/*  99 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 107 */     this.field_179442_b.setAttackTarget(null);
/* 108 */     super.startExecuting();
/*     */   }
/*     */ 
/*     */   
/*     */   protected double func_179438_f() {
/* 113 */     IAttributeInstance iattributeinstance = this.field_179442_b.getEntityAttribute(SharedMonsterAttributes.followRange);
/* 114 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\entity\ai\EntityAIFindEntityNearest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */