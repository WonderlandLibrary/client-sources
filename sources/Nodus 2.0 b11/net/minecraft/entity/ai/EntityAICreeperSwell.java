/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityLivingBase;
/*  4:   */ import net.minecraft.entity.monster.EntityCreeper;
/*  5:   */ import net.minecraft.pathfinding.PathNavigate;
/*  6:   */ 
/*  7:   */ public class EntityAICreeperSwell
/*  8:   */   extends EntityAIBase
/*  9:   */ {
/* 10:   */   EntityCreeper swellingCreeper;
/* 11:   */   EntityLivingBase creeperAttackTarget;
/* 12:   */   private static final String __OBFID = "CL_00001614";
/* 13:   */   
/* 14:   */   public EntityAICreeperSwell(EntityCreeper par1EntityCreeper)
/* 15:   */   {
/* 16:19 */     this.swellingCreeper = par1EntityCreeper;
/* 17:20 */     setMutexBits(1);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean shouldExecute()
/* 21:   */   {
/* 22:28 */     EntityLivingBase var1 = this.swellingCreeper.getAttackTarget();
/* 23:29 */     return (this.swellingCreeper.getCreeperState() > 0) || ((var1 != null) && (this.swellingCreeper.getDistanceSqToEntity(var1) < 9.0D));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void startExecuting()
/* 27:   */   {
/* 28:37 */     this.swellingCreeper.getNavigator().clearPathEntity();
/* 29:38 */     this.creeperAttackTarget = this.swellingCreeper.getAttackTarget();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void resetTask()
/* 33:   */   {
/* 34:46 */     this.creeperAttackTarget = null;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void updateTask()
/* 38:   */   {
/* 39:54 */     if (this.creeperAttackTarget == null) {
/* 40:56 */       this.swellingCreeper.setCreeperState(-1);
/* 41:58 */     } else if (this.swellingCreeper.getDistanceSqToEntity(this.creeperAttackTarget) > 49.0D) {
/* 42:60 */       this.swellingCreeper.setCreeperState(-1);
/* 43:62 */     } else if (!this.swellingCreeper.getEntitySenses().canSee(this.creeperAttackTarget)) {
/* 44:64 */       this.swellingCreeper.setCreeperState(-1);
/* 45:   */     } else {
/* 46:68 */       this.swellingCreeper.setCreeperState(1);
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAICreeperSwell
 * JD-Core Version:    0.7.0.1
 */