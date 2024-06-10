/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityLiving;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.util.MathHelper;
/*  6:   */ 
/*  7:   */ public class EntityAILeapAtTarget
/*  8:   */   extends EntityAIBase
/*  9:   */ {
/* 10:   */   EntityLiving leaper;
/* 11:   */   EntityLivingBase leapTarget;
/* 12:   */   float leapMotionY;
/* 13:   */   private static final String __OBFID = "CL_00001591";
/* 14:   */   
/* 15:   */   public EntityAILeapAtTarget(EntityLiving par1EntityLiving, float par2)
/* 16:   */   {
/* 17:21 */     this.leaper = par1EntityLiving;
/* 18:22 */     this.leapMotionY = par2;
/* 19:23 */     setMutexBits(5);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean shouldExecute()
/* 23:   */   {
/* 24:31 */     this.leapTarget = this.leaper.getAttackTarget();
/* 25:33 */     if (this.leapTarget == null) {
/* 26:35 */       return false;
/* 27:   */     }
/* 28:39 */     double var1 = this.leaper.getDistanceSqToEntity(this.leapTarget);
/* 29:40 */     return this.leaper.onGround;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public boolean continueExecuting()
/* 33:   */   {
/* 34:49 */     return !this.leaper.onGround;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void startExecuting()
/* 38:   */   {
/* 39:57 */     double var1 = this.leapTarget.posX - this.leaper.posX;
/* 40:58 */     double var3 = this.leapTarget.posZ - this.leaper.posZ;
/* 41:59 */     float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
/* 42:60 */     this.leaper.motionX += var1 / var5 * 0.5D * 0.800000011920929D + this.leaper.motionX * 0.2000000029802322D;
/* 43:61 */     this.leaper.motionZ += var3 / var5 * 0.5D * 0.800000011920929D + this.leaper.motionZ * 0.2000000029802322D;
/* 44:62 */     this.leaper.motionY = this.leapMotionY;
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAILeapAtTarget
 * JD-Core Version:    0.7.0.1
 */