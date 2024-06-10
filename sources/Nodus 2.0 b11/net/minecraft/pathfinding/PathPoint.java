/*   1:    */ package net.minecraft.pathfinding;
/*   2:    */ 
/*   3:    */ import net.minecraft.util.MathHelper;
/*   4:    */ 
/*   5:    */ public class PathPoint
/*   6:    */ {
/*   7:    */   public final int xCoord;
/*   8:    */   public final int yCoord;
/*   9:    */   public final int zCoord;
/*  10:    */   private final int hash;
/*  11: 20 */   int index = -1;
/*  12:    */   float totalPathDistance;
/*  13:    */   float distanceToNext;
/*  14:    */   float distanceToTarget;
/*  15:    */   PathPoint previous;
/*  16:    */   public boolean isFirst;
/*  17:    */   private static final String __OBFID = "CL_00000574";
/*  18:    */   
/*  19:    */   public PathPoint(int par1, int par2, int par3)
/*  20:    */   {
/*  21: 40 */     this.xCoord = par1;
/*  22: 41 */     this.yCoord = par2;
/*  23: 42 */     this.zCoord = par3;
/*  24: 43 */     this.hash = makeHash(par1, par2, par3);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static int makeHash(int par0, int par1, int par2)
/*  28:    */   {
/*  29: 48 */     return par1 & 0xFF | (par0 & 0x7FFF) << 8 | (par2 & 0x7FFF) << 24 | (par0 < 0 ? -2147483648 : 0) | (par2 < 0 ? 32768 : 0);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public float distanceTo(PathPoint par1PathPoint)
/*  33:    */   {
/*  34: 56 */     float var2 = par1PathPoint.xCoord - this.xCoord;
/*  35: 57 */     float var3 = par1PathPoint.yCoord - this.yCoord;
/*  36: 58 */     float var4 = par1PathPoint.zCoord - this.zCoord;
/*  37: 59 */     return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public float distanceToSquared(PathPoint par1PathPoint)
/*  41:    */   {
/*  42: 67 */     float var2 = par1PathPoint.xCoord - this.xCoord;
/*  43: 68 */     float var3 = par1PathPoint.yCoord - this.yCoord;
/*  44: 69 */     float var4 = par1PathPoint.zCoord - this.zCoord;
/*  45: 70 */     return var2 * var2 + var3 * var3 + var4 * var4;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean equals(Object par1Obj)
/*  49:    */   {
/*  50: 75 */     if (!(par1Obj instanceof PathPoint)) {
/*  51: 77 */       return false;
/*  52:    */     }
/*  53: 81 */     PathPoint var2 = (PathPoint)par1Obj;
/*  54: 82 */     return (this.hash == var2.hash) && (this.xCoord == var2.xCoord) && (this.yCoord == var2.yCoord) && (this.zCoord == var2.zCoord);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int hashCode()
/*  58:    */   {
/*  59: 88 */     return this.hash;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isAssigned()
/*  63:    */   {
/*  64: 96 */     return this.index >= 0;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String toString()
/*  68:    */   {
/*  69:101 */     return this.xCoord + ", " + this.yCoord + ", " + this.zCoord;
/*  70:    */   }
/*  71:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.pathfinding.PathPoint
 * JD-Core Version:    0.7.0.1
 */