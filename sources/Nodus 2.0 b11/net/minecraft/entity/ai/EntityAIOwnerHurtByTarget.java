/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityCreature;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.entity.passive.EntityTameable;
/*  6:   */ 
/*  7:   */ public class EntityAIOwnerHurtByTarget
/*  8:   */   extends EntityAITarget
/*  9:   */ {
/* 10:   */   EntityTameable theDefendingTameable;
/* 11:   */   EntityLivingBase theOwnerAttacker;
/* 12:   */   private int field_142051_e;
/* 13:   */   private static final String __OBFID = "CL_00001624";
/* 14:   */   
/* 15:   */   public EntityAIOwnerHurtByTarget(EntityTameable par1EntityTameable)
/* 16:   */   {
/* 17:15 */     super(par1EntityTameable, false);
/* 18:16 */     this.theDefendingTameable = par1EntityTameable;
/* 19:17 */     setMutexBits(1);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean shouldExecute()
/* 23:   */   {
/* 24:25 */     if (!this.theDefendingTameable.isTamed()) {
/* 25:27 */       return false;
/* 26:   */     }
/* 27:31 */     EntityLivingBase var1 = this.theDefendingTameable.getOwner();
/* 28:33 */     if (var1 == null) {
/* 29:35 */       return false;
/* 30:   */     }
/* 31:39 */     this.theOwnerAttacker = var1.getAITarget();
/* 32:40 */     int var2 = var1.func_142015_aE();
/* 33:41 */     return (var2 != this.field_142051_e) && (isSuitableTarget(this.theOwnerAttacker, false)) && (this.theDefendingTameable.func_142018_a(this.theOwnerAttacker, var1));
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void startExecuting()
/* 37:   */   {
/* 38:51 */     this.taskOwner.setAttackTarget(this.theOwnerAttacker);
/* 39:52 */     EntityLivingBase var1 = this.theDefendingTameable.getOwner();
/* 40:54 */     if (var1 != null) {
/* 41:56 */       this.field_142051_e = var1.func_142015_aE();
/* 42:   */     }
/* 43:59 */     super.startExecuting();
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIOwnerHurtByTarget
 * JD-Core Version:    0.7.0.1
 */