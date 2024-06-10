/*   1:    */ package net.minecraft.pathfinding;
/*   2:    */ 
/*   3:    */ import net.minecraft.entity.Entity;
/*   4:    */ import net.minecraft.util.Vec3;
/*   5:    */ import net.minecraft.util.Vec3Pool;
/*   6:    */ import net.minecraft.world.World;
/*   7:    */ 
/*   8:    */ public class PathEntity
/*   9:    */ {
/*  10:    */   private final PathPoint[] points;
/*  11:    */   private int currentPathIndex;
/*  12:    */   private int pathLength;
/*  13:    */   private static final String __OBFID = "CL_00000575";
/*  14:    */   
/*  15:    */   public PathEntity(PathPoint[] par1ArrayOfPathPoint)
/*  16:    */   {
/*  17: 20 */     this.points = par1ArrayOfPathPoint;
/*  18: 21 */     this.pathLength = par1ArrayOfPathPoint.length;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void incrementPathIndex()
/*  22:    */   {
/*  23: 29 */     this.currentPathIndex += 1;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean isFinished()
/*  27:    */   {
/*  28: 37 */     return this.currentPathIndex >= this.pathLength;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public PathPoint getFinalPathPoint()
/*  32:    */   {
/*  33: 45 */     return this.pathLength > 0 ? this.points[(this.pathLength - 1)] : null;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public PathPoint getPathPointFromIndex(int par1)
/*  37:    */   {
/*  38: 53 */     return this.points[par1];
/*  39:    */   }
/*  40:    */   
/*  41:    */   public int getCurrentPathLength()
/*  42:    */   {
/*  43: 58 */     return this.pathLength;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setCurrentPathLength(int par1)
/*  47:    */   {
/*  48: 63 */     this.pathLength = par1;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getCurrentPathIndex()
/*  52:    */   {
/*  53: 68 */     return this.currentPathIndex;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setCurrentPathIndex(int par1)
/*  57:    */   {
/*  58: 73 */     this.currentPathIndex = par1;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Vec3 getVectorFromIndex(Entity par1Entity, int par2)
/*  62:    */   {
/*  63: 81 */     double var3 = this.points[par2].xCoord + (int)(par1Entity.width + 1.0F) * 0.5D;
/*  64: 82 */     double var5 = this.points[par2].yCoord;
/*  65: 83 */     double var7 = this.points[par2].zCoord + (int)(par1Entity.width + 1.0F) * 0.5D;
/*  66: 84 */     return par1Entity.worldObj.getWorldVec3Pool().getVecFromPool(var3, var5, var7);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Vec3 getPosition(Entity par1Entity)
/*  70:    */   {
/*  71: 92 */     return getVectorFromIndex(par1Entity, this.currentPathIndex);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean isSamePath(PathEntity par1PathEntity)
/*  75:    */   {
/*  76:100 */     if (par1PathEntity == null) {
/*  77:102 */       return false;
/*  78:    */     }
/*  79:104 */     if (par1PathEntity.points.length != this.points.length) {
/*  80:106 */       return false;
/*  81:    */     }
/*  82:110 */     for (int var2 = 0; var2 < this.points.length; var2++) {
/*  83:112 */       if ((this.points[var2].xCoord != par1PathEntity.points[var2].xCoord) || (this.points[var2].yCoord != par1PathEntity.points[var2].yCoord) || (this.points[var2].zCoord != par1PathEntity.points[var2].zCoord)) {
/*  84:114 */         return false;
/*  85:    */       }
/*  86:    */     }
/*  87:118 */     return true;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isDestinationSame(Vec3 par1Vec3)
/*  91:    */   {
/*  92:127 */     PathPoint var2 = getFinalPathPoint();
/*  93:128 */     return var2 != null;
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.pathfinding.PathEntity
 * JD-Core Version:    0.7.0.1
 */