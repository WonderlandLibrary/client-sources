/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ public class ChunkCoordinates
/*  4:   */   implements Comparable
/*  5:   */ {
/*  6:   */   public int posX;
/*  7:   */   public int posY;
/*  8:   */   public int posZ;
/*  9:   */   private static final String __OBFID = "CL_00001555";
/* 10:   */   
/* 11:   */   public ChunkCoordinates() {}
/* 12:   */   
/* 13:   */   public ChunkCoordinates(int par1, int par2, int par3)
/* 14:   */   {
/* 15:18 */     this.posX = par1;
/* 16:19 */     this.posY = par2;
/* 17:20 */     this.posZ = par3;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ChunkCoordinates(ChunkCoordinates par1ChunkCoordinates)
/* 21:   */   {
/* 22:25 */     this.posX = par1ChunkCoordinates.posX;
/* 23:26 */     this.posY = par1ChunkCoordinates.posY;
/* 24:27 */     this.posZ = par1ChunkCoordinates.posZ;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean equals(Object par1Obj)
/* 28:   */   {
/* 29:32 */     if (!(par1Obj instanceof ChunkCoordinates)) {
/* 30:34 */       return false;
/* 31:   */     }
/* 32:38 */     ChunkCoordinates var2 = (ChunkCoordinates)par1Obj;
/* 33:39 */     return (this.posX == var2.posX) && (this.posY == var2.posY) && (this.posZ == var2.posZ);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public int hashCode()
/* 37:   */   {
/* 38:45 */     return this.posX + this.posZ << 8 + this.posY << 16;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int compareTo(ChunkCoordinates par1ChunkCoordinates)
/* 42:   */   {
/* 43:50 */     return this.posY == par1ChunkCoordinates.posY ? this.posZ - par1ChunkCoordinates.posZ : this.posZ == par1ChunkCoordinates.posZ ? this.posX - par1ChunkCoordinates.posX : this.posY - par1ChunkCoordinates.posY;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void set(int par1, int par2, int par3)
/* 47:   */   {
/* 48:55 */     this.posX = par1;
/* 49:56 */     this.posY = par2;
/* 50:57 */     this.posZ = par3;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public float getDistanceSquared(int par1, int par2, int par3)
/* 54:   */   {
/* 55:65 */     float var4 = this.posX - par1;
/* 56:66 */     float var5 = this.posY - par2;
/* 57:67 */     float var6 = this.posZ - par3;
/* 58:68 */     return var4 * var4 + var5 * var5 + var6 * var6;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public float getDistanceSquaredToChunkCoordinates(ChunkCoordinates par1ChunkCoordinates)
/* 62:   */   {
/* 63:76 */     return getDistanceSquared(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ);
/* 64:   */   }
/* 65:   */   
/* 66:   */   public String toString()
/* 67:   */   {
/* 68:81 */     return "Pos{x=" + this.posX + ", y=" + this.posY + ", z=" + this.posZ + '}';
/* 69:   */   }
/* 70:   */   
/* 71:   */   public int compareTo(Object par1Obj)
/* 72:   */   {
/* 73:86 */     return compareTo((ChunkCoordinates)par1Obj);
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.ChunkCoordinates
 * JD-Core Version:    0.7.0.1
 */