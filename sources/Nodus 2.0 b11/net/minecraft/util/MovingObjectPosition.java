/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.Entity;
/*  4:   */ import net.minecraft.world.World;
/*  5:   */ 
/*  6:   */ public class MovingObjectPosition
/*  7:   */ {
/*  8:   */   public MovingObjectType typeOfHit;
/*  9:   */   public int blockX;
/* 10:   */   public int blockY;
/* 11:   */   public int blockZ;
/* 12:   */   public int sideHit;
/* 13:   */   public Vec3 hitVec;
/* 14:   */   public Entity entityHit;
/* 15:   */   private static final String __OBFID = "CL_00000610";
/* 16:   */   
/* 17:   */   public MovingObjectPosition(int par1, int par2, int par3, int par4, Vec3 par5Vec3)
/* 18:   */   {
/* 19:34 */     this(par1, par2, par3, par4, par5Vec3, true);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public MovingObjectPosition(int p_i45481_1_, int p_i45481_2_, int p_i45481_3_, int p_i45481_4_, Vec3 p_i45481_5_, boolean p_i45481_6_)
/* 23:   */   {
/* 24:39 */     this.typeOfHit = (p_i45481_6_ ? MovingObjectType.BLOCK : MovingObjectType.MISS);
/* 25:40 */     this.blockX = p_i45481_1_;
/* 26:41 */     this.blockY = p_i45481_2_;
/* 27:42 */     this.blockZ = p_i45481_3_;
/* 28:43 */     this.sideHit = p_i45481_4_;
/* 29:44 */     this.hitVec = p_i45481_5_.myVec3LocalPool.getVecFromPool(p_i45481_5_.xCoord, p_i45481_5_.yCoord, p_i45481_5_.zCoord);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public MovingObjectPosition(Entity par1Entity)
/* 33:   */   {
/* 34:49 */     this(par1Entity, par1Entity.worldObj.getWorldVec3Pool().getVecFromPool(par1Entity.posX, par1Entity.posY, par1Entity.posZ));
/* 35:   */   }
/* 36:   */   
/* 37:   */   public MovingObjectPosition(Entity p_i45482_1_, Vec3 p_i45482_2_)
/* 38:   */   {
/* 39:54 */     this.typeOfHit = MovingObjectType.ENTITY;
/* 40:55 */     this.entityHit = p_i45482_1_;
/* 41:56 */     this.hitVec = p_i45482_2_;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String toString()
/* 45:   */   {
/* 46:61 */     return "HitResult{type=" + this.typeOfHit + ", x=" + this.blockX + ", y=" + this.blockY + ", z=" + this.blockZ + ", f=" + this.sideHit + ", pos=" + this.hitVec + ", entity=" + this.entityHit + '}';
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static enum MovingObjectType
/* 50:   */   {
/* 51:66 */     MISS("MISS", 0),  BLOCK("BLOCK", 1),  ENTITY("ENTITY", 2);
/* 52:   */     
/* 53:70 */     private static final MovingObjectType[] $VALUES = { MISS, BLOCK, ENTITY };
/* 54:   */     private static final String __OBFID = "CL_00000611";
/* 55:   */     
/* 56:   */     private MovingObjectType(String par1Str, int par2) {}
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.MovingObjectPosition
 * JD-Core Version:    0.7.0.1
 */