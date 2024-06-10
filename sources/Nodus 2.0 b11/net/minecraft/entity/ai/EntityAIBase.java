/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ public abstract class EntityAIBase
/*  4:   */ {
/*  5:   */   private int mutexBits;
/*  6:   */   private static final String __OBFID = "CL_00001587";
/*  7:   */   
/*  8:   */   public abstract boolean shouldExecute();
/*  9:   */   
/* 10:   */   public boolean continueExecuting()
/* 11:   */   {
/* 12:22 */     return shouldExecute();
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean isInterruptible()
/* 16:   */   {
/* 17:30 */     return true;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void startExecuting() {}
/* 21:   */   
/* 22:   */   public void resetTask() {}
/* 23:   */   
/* 24:   */   public void updateTask() {}
/* 25:   */   
/* 26:   */   public void setMutexBits(int par1)
/* 27:   */   {
/* 28:54 */     this.mutexBits = par1;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getMutexBits()
/* 32:   */   {
/* 33:63 */     return this.mutexBits;
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIBase
 * JD-Core Version:    0.7.0.1
 */