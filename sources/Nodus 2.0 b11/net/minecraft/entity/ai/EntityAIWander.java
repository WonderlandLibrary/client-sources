/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.EntityCreature;
/*  5:   */ import net.minecraft.pathfinding.PathNavigate;
/*  6:   */ import net.minecraft.util.Vec3;
/*  7:   */ 
/*  8:   */ public class EntityAIWander
/*  9:   */   extends EntityAIBase
/* 10:   */ {
/* 11:   */   private EntityCreature entity;
/* 12:   */   private double xPosition;
/* 13:   */   private double yPosition;
/* 14:   */   private double zPosition;
/* 15:   */   private double speed;
/* 16:   */   private static final String __OBFID = "CL_00001608";
/* 17:   */   
/* 18:   */   public EntityAIWander(EntityCreature par1EntityCreature, double par2)
/* 19:   */   {
/* 20:17 */     this.entity = par1EntityCreature;
/* 21:18 */     this.speed = par2;
/* 22:19 */     setMutexBits(1);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean shouldExecute()
/* 26:   */   {
/* 27:27 */     if (this.entity.getAge() >= 100) {
/* 28:29 */       return false;
/* 29:   */     }
/* 30:31 */     if (this.entity.getRNG().nextInt(120) != 0) {
/* 31:33 */       return false;
/* 32:   */     }
/* 33:37 */     Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
/* 34:39 */     if (var1 == null) {
/* 35:41 */       return false;
/* 36:   */     }
/* 37:45 */     this.xPosition = var1.xCoord;
/* 38:46 */     this.yPosition = var1.yCoord;
/* 39:47 */     this.zPosition = var1.zCoord;
/* 40:48 */     return true;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean continueExecuting()
/* 44:   */   {
/* 45:58 */     return !this.entity.getNavigator().noPath();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void startExecuting()
/* 49:   */   {
/* 50:66 */     this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIWander
 * JD-Core Version:    0.7.0.1
 */