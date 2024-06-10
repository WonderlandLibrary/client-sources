/*  1:   */ package net.minecraft.entity.ai;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.entity.EntityCreature;
/*  5:   */ import net.minecraft.pathfinding.PathNavigate;
/*  6:   */ import net.minecraft.util.AxisAlignedBB;
/*  7:   */ import net.minecraft.util.MathHelper;
/*  8:   */ import net.minecraft.util.Vec3;
/*  9:   */ import net.minecraft.util.Vec3Pool;
/* 10:   */ import net.minecraft.world.World;
/* 11:   */ 
/* 12:   */ public class EntityAIFleeSun
/* 13:   */   extends EntityAIBase
/* 14:   */ {
/* 15:   */   private EntityCreature theCreature;
/* 16:   */   private double shelterX;
/* 17:   */   private double shelterY;
/* 18:   */   private double shelterZ;
/* 19:   */   private double movementSpeed;
/* 20:   */   private World theWorld;
/* 21:   */   private static final String __OBFID = "CL_00001583";
/* 22:   */   
/* 23:   */   public EntityAIFleeSun(EntityCreature par1EntityCreature, double par2)
/* 24:   */   {
/* 25:21 */     this.theCreature = par1EntityCreature;
/* 26:22 */     this.movementSpeed = par2;
/* 27:23 */     this.theWorld = par1EntityCreature.worldObj;
/* 28:24 */     setMutexBits(1);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean shouldExecute()
/* 32:   */   {
/* 33:32 */     if (!this.theWorld.isDaytime()) {
/* 34:34 */       return false;
/* 35:   */     }
/* 36:36 */     if (!this.theCreature.isBurning()) {
/* 37:38 */       return false;
/* 38:   */     }
/* 39:40 */     if (!this.theWorld.canBlockSeeTheSky(MathHelper.floor_double(this.theCreature.posX), (int)this.theCreature.boundingBox.minY, MathHelper.floor_double(this.theCreature.posZ))) {
/* 40:42 */       return false;
/* 41:   */     }
/* 42:46 */     Vec3 var1 = findPossibleShelter();
/* 43:48 */     if (var1 == null) {
/* 44:50 */       return false;
/* 45:   */     }
/* 46:54 */     this.shelterX = var1.xCoord;
/* 47:55 */     this.shelterY = var1.yCoord;
/* 48:56 */     this.shelterZ = var1.zCoord;
/* 49:57 */     return true;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public boolean continueExecuting()
/* 53:   */   {
/* 54:67 */     return !this.theCreature.getNavigator().noPath();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void startExecuting()
/* 58:   */   {
/* 59:75 */     this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
/* 60:   */   }
/* 61:   */   
/* 62:   */   private Vec3 findPossibleShelter()
/* 63:   */   {
/* 64:80 */     Random var1 = this.theCreature.getRNG();
/* 65:82 */     for (int var2 = 0; var2 < 10; var2++)
/* 66:   */     {
/* 67:84 */       int var3 = MathHelper.floor_double(this.theCreature.posX + var1.nextInt(20) - 10.0D);
/* 68:85 */       int var4 = MathHelper.floor_double(this.theCreature.boundingBox.minY + var1.nextInt(6) - 3.0D);
/* 69:86 */       int var5 = MathHelper.floor_double(this.theCreature.posZ + var1.nextInt(20) - 10.0D);
/* 70:88 */       if ((!this.theWorld.canBlockSeeTheSky(var3, var4, var5)) && (this.theCreature.getBlockPathWeight(var3, var4, var5) < 0.0F)) {
/* 71:90 */         return this.theWorld.getWorldVec3Pool().getVecFromPool(var3, var4, var5);
/* 72:   */       }
/* 73:   */     }
/* 74:94 */     return null;
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.EntityAIFleeSun
 * JD-Core Version:    0.7.0.1
 */