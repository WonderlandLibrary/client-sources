/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityLivingBase;
/*  4:   */ import net.minecraft.entity.passive.EntityTameable;
/*  5:   */ import net.minecraft.pathfinding.PathNavigate;
/*  6:   */ 
/*  7:   */ public class EntityAISit
/*  8:   */   extends EntityAIBase
/*  9:   */ {
/* 10:   */   private EntityTameable theEntity;
/* 11:   */   private boolean isSitting;
/* 12:   */   private static final String __OBFID = "CL_00001613";
/* 13:   */   
/* 14:   */   public EntityAISit(EntityTameable par1EntityTameable)
/* 15:   */   {
/* 16:16 */     this.theEntity = par1EntityTameable;
/* 17:17 */     setMutexBits(5);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean shouldExecute()
/* 21:   */   {
/* 22:25 */     if (!this.theEntity.isTamed()) {
/* 23:27 */       return false;
/* 24:   */     }
/* 25:29 */     if (this.theEntity.isInWater()) {
/* 26:31 */       return false;
/* 27:   */     }
/* 28:33 */     if (!this.theEntity.onGround) {
/* 29:35 */       return false;
/* 30:   */     }
/* 31:39 */     EntityLivingBase var1 = this.theEntity.getOwner();
/* 32:40 */     return (this.theEntity.getDistanceSqToEntity(var1) < 144.0D) && (var1.getAITarget() != null) ? false : var1 == null ? true : this.isSitting;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void startExecuting()
/* 36:   */   {
/* 37:49 */     this.theEntity.getNavigator().clearPathEntity();
/* 38:50 */     this.theEntity.setSitting(true);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void resetTask()
/* 42:   */   {
/* 43:58 */     this.theEntity.setSitting(false);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setSitting(boolean par1)
/* 47:   */   {
/* 48:66 */     this.isSitting = par1;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAISit
 * JD-Core Version:    0.7.0.1
 */