/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityCreature;
/*  4:   */ import net.minecraft.entity.EntityLivingBase;
/*  5:   */ import net.minecraft.pathfinding.PathNavigate;
/*  6:   */ import net.minecraft.util.Vec3;
/*  7:   */ import net.minecraft.util.Vec3Pool;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class EntityAIMoveTowardsTarget
/* 11:   */   extends EntityAIBase
/* 12:   */ {
/* 13:   */   private EntityCreature theEntity;
/* 14:   */   private EntityLivingBase targetEntity;
/* 15:   */   private double movePosX;
/* 16:   */   private double movePosY;
/* 17:   */   private double movePosZ;
/* 18:   */   private double speed;
/* 19:   */   private float maxTargetDistance;
/* 20:   */   private static final String __OBFID = "CL_00001599";
/* 21:   */   
/* 22:   */   public EntityAIMoveTowardsTarget(EntityCreature par1EntityCreature, double par2, float par4)
/* 23:   */   {
/* 24:24 */     this.theEntity = par1EntityCreature;
/* 25:25 */     this.speed = par2;
/* 26:26 */     this.maxTargetDistance = par4;
/* 27:27 */     setMutexBits(1);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean shouldExecute()
/* 31:   */   {
/* 32:35 */     this.targetEntity = this.theEntity.getAttackTarget();
/* 33:37 */     if (this.targetEntity == null) {
/* 34:39 */       return false;
/* 35:   */     }
/* 36:41 */     if (this.targetEntity.getDistanceSqToEntity(this.theEntity) > this.maxTargetDistance * this.maxTargetDistance) {
/* 37:43 */       return false;
/* 38:   */     }
/* 39:47 */     Vec3 var1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ));
/* 40:49 */     if (var1 == null) {
/* 41:51 */       return false;
/* 42:   */     }
/* 43:55 */     this.movePosX = var1.xCoord;
/* 44:56 */     this.movePosY = var1.yCoord;
/* 45:57 */     this.movePosZ = var1.zCoord;
/* 46:58 */     return true;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public boolean continueExecuting()
/* 50:   */   {
/* 51:68 */     return (!this.theEntity.getNavigator().noPath()) && (this.targetEntity.isEntityAlive()) && (this.targetEntity.getDistanceSqToEntity(this.theEntity) < this.maxTargetDistance * this.maxTargetDistance);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void resetTask()
/* 55:   */   {
/* 56:76 */     this.targetEntity = null;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public void startExecuting()
/* 60:   */   {
/* 61:84 */     this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.speed);
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIMoveTowardsTarget
 * JD-Core Version:    0.7.0.1
 */