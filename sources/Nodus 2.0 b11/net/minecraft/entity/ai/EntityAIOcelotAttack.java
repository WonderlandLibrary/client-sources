/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityLiving;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.pathfinding.PathNavigate;
/*  6:   */ import net.minecraft.util.AxisAlignedBB;
/*  7:   */ import net.minecraft.world.World;
/*  8:   */ 
/*  9:   */ public class EntityAIOcelotAttack
/* 10:   */   extends EntityAIBase
/* 11:   */ {
/* 12:   */   World theWorld;
/* 13:   */   EntityLiving theEntity;
/* 14:   */   EntityLivingBase theVictim;
/* 15:   */   int attackCountdown;
/* 16:   */   private static final String __OBFID = "CL_00001600";
/* 17:   */   
/* 18:   */   public EntityAIOcelotAttack(EntityLiving par1EntityLiving)
/* 19:   */   {
/* 20:17 */     this.theEntity = par1EntityLiving;
/* 21:18 */     this.theWorld = par1EntityLiving.worldObj;
/* 22:19 */     setMutexBits(3);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean shouldExecute()
/* 26:   */   {
/* 27:27 */     EntityLivingBase var1 = this.theEntity.getAttackTarget();
/* 28:29 */     if (var1 == null) {
/* 29:31 */       return false;
/* 30:   */     }
/* 31:35 */     this.theVictim = var1;
/* 32:36 */     return true;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean continueExecuting()
/* 36:   */   {
/* 37:45 */     return this.theVictim.isEntityAlive();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void resetTask()
/* 41:   */   {
/* 42:53 */     this.theVictim = null;
/* 43:54 */     this.theEntity.getNavigator().clearPathEntity();
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void updateTask()
/* 47:   */   {
/* 48:62 */     this.theEntity.getLookHelper().setLookPositionWithEntity(this.theVictim, 30.0F, 30.0F);
/* 49:63 */     double var1 = this.theEntity.width * 2.0F * this.theEntity.width * 2.0F;
/* 50:64 */     double var3 = this.theEntity.getDistanceSq(this.theVictim.posX, this.theVictim.boundingBox.minY, this.theVictim.posZ);
/* 51:65 */     double var5 = 0.8D;
/* 52:67 */     if ((var3 > var1) && (var3 < 16.0D)) {
/* 53:69 */       var5 = 1.33D;
/* 54:71 */     } else if (var3 < 225.0D) {
/* 55:73 */       var5 = 0.6D;
/* 56:   */     }
/* 57:76 */     this.theEntity.getNavigator().tryMoveToEntityLiving(this.theVictim, var5);
/* 58:77 */     this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
/* 59:79 */     if (var3 <= var1) {
/* 60:81 */       if (this.attackCountdown <= 0)
/* 61:   */       {
/* 62:83 */         this.attackCountdown = 20;
/* 63:84 */         this.theEntity.attackEntityAsMob(this.theVictim);
/* 64:   */       }
/* 65:   */     }
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIOcelotAttack
 * JD-Core Version:    0.7.0.1
 */