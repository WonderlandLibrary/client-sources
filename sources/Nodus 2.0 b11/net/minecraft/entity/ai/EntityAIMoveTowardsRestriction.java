/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityCreature;
/*  4:   */ import net.minecraft.pathfinding.PathNavigate;
/*  5:   */ import net.minecraft.util.ChunkCoordinates;
/*  6:   */ import net.minecraft.util.Vec3;
/*  7:   */ import net.minecraft.util.Vec3Pool;
/*  8:   */ import net.minecraft.world.World;
/*  9:   */ 
/* 10:   */ public class EntityAIMoveTowardsRestriction
/* 11:   */   extends EntityAIBase
/* 12:   */ {
/* 13:   */   private EntityCreature theEntity;
/* 14:   */   private double movePosX;
/* 15:   */   private double movePosY;
/* 16:   */   private double movePosZ;
/* 17:   */   private double movementSpeed;
/* 18:   */   private static final String __OBFID = "CL_00001598";
/* 19:   */   
/* 20:   */   public EntityAIMoveTowardsRestriction(EntityCreature par1EntityCreature, double par2)
/* 21:   */   {
/* 22:18 */     this.theEntity = par1EntityCreature;
/* 23:19 */     this.movementSpeed = par2;
/* 24:20 */     setMutexBits(1);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean shouldExecute()
/* 28:   */   {
/* 29:28 */     if (this.theEntity.isWithinHomeDistanceCurrentPosition()) {
/* 30:30 */       return false;
/* 31:   */     }
/* 32:34 */     ChunkCoordinates var1 = this.theEntity.getHomePosition();
/* 33:35 */     Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(var1.posX, var1.posY, var1.posZ));
/* 34:37 */     if (var2 == null) {
/* 35:39 */       return false;
/* 36:   */     }
/* 37:43 */     this.movePosX = var2.xCoord;
/* 38:44 */     this.movePosY = var2.yCoord;
/* 39:45 */     this.movePosZ = var2.zCoord;
/* 40:46 */     return true;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean continueExecuting()
/* 44:   */   {
/* 45:56 */     return !this.theEntity.getNavigator().noPath();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void startExecuting()
/* 49:   */   {
/* 50:64 */     this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIMoveTowardsRestriction
 * JD-Core Version:    0.7.0.1
 */