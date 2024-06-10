/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.EntityLiving;
/*  5:   */ 
/*  6:   */ public class EntityAILookIdle
/*  7:   */   extends EntityAIBase
/*  8:   */ {
/*  9:   */   private EntityLiving idleEntity;
/* 10:   */   private double lookX;
/* 11:   */   private double lookZ;
/* 12:   */   private int idleTime;
/* 13:   */   private static final String __OBFID = "CL_00001607";
/* 14:   */   
/* 15:   */   public EntityAILookIdle(EntityLiving par1EntityLiving)
/* 16:   */   {
/* 17:24 */     this.idleEntity = par1EntityLiving;
/* 18:25 */     setMutexBits(3);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean shouldExecute()
/* 22:   */   {
/* 23:33 */     return this.idleEntity.getRNG().nextFloat() < 0.02F;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean continueExecuting()
/* 27:   */   {
/* 28:41 */     return this.idleTime >= 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void startExecuting()
/* 32:   */   {
/* 33:49 */     double var1 = 6.283185307179586D * this.idleEntity.getRNG().nextDouble();
/* 34:50 */     this.lookX = Math.cos(var1);
/* 35:51 */     this.lookZ = Math.sin(var1);
/* 36:52 */     this.idleTime = (20 + this.idleEntity.getRNG().nextInt(20));
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void updateTask()
/* 40:   */   {
/* 41:60 */     this.idleTime -= 1;
/* 42:61 */     this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, 10.0F, this.idleEntity.getVerticalFaceSpeed());
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAILookIdle
 * JD-Core Version:    0.7.0.1
 */