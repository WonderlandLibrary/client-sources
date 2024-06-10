/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.EntityCreature;
/*  4:   */ import net.minecraft.pathfinding.PathNavigate;
/*  5:   */ import net.minecraft.util.Vec3;
/*  6:   */ 
/*  7:   */ public class EntityAIPanic
/*  8:   */   extends EntityAIBase
/*  9:   */ {
/* 10:   */   private EntityCreature theEntityCreature;
/* 11:   */   private double speed;
/* 12:   */   private double randPosX;
/* 13:   */   private double randPosY;
/* 14:   */   private double randPosZ;
/* 15:   */   private static final String __OBFID = "CL_00001604";
/* 16:   */   
/* 17:   */   public EntityAIPanic(EntityCreature par1EntityCreature, double par2)
/* 18:   */   {
/* 19:17 */     this.theEntityCreature = par1EntityCreature;
/* 20:18 */     this.speed = par2;
/* 21:19 */     setMutexBits(1);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean shouldExecute()
/* 25:   */   {
/* 26:27 */     if ((this.theEntityCreature.getAITarget() == null) && (!this.theEntityCreature.isBurning())) {
/* 27:29 */       return false;
/* 28:   */     }
/* 29:33 */     Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
/* 30:35 */     if (var1 == null) {
/* 31:37 */       return false;
/* 32:   */     }
/* 33:41 */     this.randPosX = var1.xCoord;
/* 34:42 */     this.randPosY = var1.yCoord;
/* 35:43 */     this.randPosZ = var1.zCoord;
/* 36:44 */     return true;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void startExecuting()
/* 40:   */   {
/* 41:54 */     this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public boolean continueExecuting()
/* 45:   */   {
/* 46:62 */     return !this.theEntityCreature.getNavigator().noPath();
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIPanic
 * JD-Core Version:    0.7.0.1
 */