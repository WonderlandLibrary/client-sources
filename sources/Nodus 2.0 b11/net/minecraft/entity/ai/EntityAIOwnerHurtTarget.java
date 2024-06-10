/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityCreature;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.passive.EntityTameable;
/*  6:   */ 
/*  7:   */ public class EntityAIOwnerHurtTarget
/*  8:   */   extends EntityAITarget
/*  9:   */ {
/* 10:   */   EntityTameable theEntityTameable;
/* 11:   */   EntityLivingBase theTarget;
/* 12:   */   private int field_142050_e;
/* 13:   */   private static final String __OBFID = "CL_00001625";
/* 14:   */   
/* 15:   */   public EntityAIOwnerHurtTarget(EntityTameable par1EntityTameable)
/* 16:   */   {
/* 17:15 */     super(par1EntityTameable, false);
/* 18:16 */     this.theEntityTameable = par1EntityTameable;
/* 19:17 */     setMutexBits(1);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean shouldExecute()
/* 23:   */   {
/* 24:25 */     if (!this.theEntityTameable.isTamed()) {
/* 25:27 */       return false;
/* 26:   */     }
/* 27:31 */     EntityLivingBase var1 = this.theEntityTameable.getOwner();
/* 28:33 */     if (var1 == null) {
/* 29:35 */       return false;
/* 30:   */     }
/* 31:39 */     this.theTarget = var1.getLastAttacker();
/* 32:40 */     int var2 = var1.getLastAttackerTime();
/* 33:41 */     return (var2 != this.field_142050_e) && (isSuitableTarget(this.theTarget, false)) && (this.theEntityTameable.func_142018_a(this.theTarget, var1));
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void startExecuting()
/* 37:   */   {
/* 38:51 */     this.taskOwner.setAttackTarget(this.theTarget);
/* 39:52 */     EntityLivingBase var1 = this.theEntityTameable.getOwner();
/* 40:54 */     if (var1 != null) {
/* 41:56 */       this.field_142050_e = var1.getLastAttackerTime();
/* 42:   */     }
/* 43:59 */     super.startExecuting();
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIOwnerHurtTarget
 * JD-Core Version:    0.7.0.1
 */