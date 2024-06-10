/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.Entity;
/*  5:   */ import net.minecraft.entity.EntityLiving;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.util.AxisAlignedBB;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class EntityAIWatchClosest
/* 11:   */   extends EntityAIBase
/* 12:   */ {
/* 13:   */   private EntityLiving theWatcher;
/* 14:   */   protected Entity closestEntity;
/* 15:   */   private float maxDistanceForPlayer;
/* 16:   */   private int lookTime;
/* 17:   */   private float field_75331_e;
/* 18:   */   private Class watchedClass;
/* 19:   */   private static final String __OBFID = "CL_00001592";
/* 20:   */   
/* 21:   */   public EntityAIWatchClosest(EntityLiving par1EntityLiving, Class par2Class, float par3)
/* 22:   */   {
/* 23:23 */     this.theWatcher = par1EntityLiving;
/* 24:24 */     this.watchedClass = par2Class;
/* 25:25 */     this.maxDistanceForPlayer = par3;
/* 26:26 */     this.field_75331_e = 0.02F;
/* 27:27 */     setMutexBits(2);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public EntityAIWatchClosest(EntityLiving par1EntityLiving, Class par2Class, float par3, float par4)
/* 31:   */   {
/* 32:32 */     this.theWatcher = par1EntityLiving;
/* 33:33 */     this.watchedClass = par2Class;
/* 34:34 */     this.maxDistanceForPlayer = par3;
/* 35:35 */     this.field_75331_e = par4;
/* 36:36 */     setMutexBits(2);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public boolean shouldExecute()
/* 40:   */   {
/* 41:44 */     if (this.theWatcher.getRNG().nextFloat() >= this.field_75331_e) {
/* 42:46 */       return false;
/* 43:   */     }
/* 44:50 */     if (this.theWatcher.getAttackTarget() != null) {
/* 45:52 */       this.closestEntity = this.theWatcher.getAttackTarget();
/* 46:   */     }
/* 47:55 */     if (this.watchedClass == EntityPlayer.class) {
/* 48:57 */       this.closestEntity = this.theWatcher.worldObj.getClosestPlayerToEntity(this.theWatcher, this.maxDistanceForPlayer);
/* 49:   */     } else {
/* 50:61 */       this.closestEntity = this.theWatcher.worldObj.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.boundingBox.expand(this.maxDistanceForPlayer, 3.0D, this.maxDistanceForPlayer), this.theWatcher);
/* 51:   */     }
/* 52:64 */     return this.closestEntity != null;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean continueExecuting()
/* 56:   */   {
/* 57:73 */     return this.closestEntity.isEntityAlive();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void startExecuting()
/* 61:   */   {
/* 62:81 */     this.lookTime = (40 + this.theWatcher.getRNG().nextInt(40));
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void resetTask()
/* 66:   */   {
/* 67:89 */     this.closestEntity = null;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void updateTask()
/* 71:   */   {
/* 72:97 */     this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, this.theWatcher.getVerticalFaceSpeed());
/* 73:98 */     this.lookTime -= 1;
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIWatchClosest
 * JD-Core Version:    0.7.0.1
 */