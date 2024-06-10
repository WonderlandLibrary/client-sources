/*  1:   */ package net.minecraft.world;
/*  2:   */ 
/*  3:   */ public class ChunkCoordIntPair
/*  4:   */ {
/*  5:   */   public final int chunkXPos;
/*  6:   */   public final int chunkZPos;
/*  7:   */   private static final String __OBFID = "CL_00000133";
/*  8:   */   
/*  9:   */   public ChunkCoordIntPair(int par1, int par2)
/* 10:   */   {
/* 11:14 */     this.chunkXPos = par1;
/* 12:15 */     this.chunkZPos = par2;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static long chunkXZ2Int(int par0, int par1)
/* 16:   */   {
/* 17:23 */     return par0 & 0xFFFFFFFF | (par1 & 0xFFFFFFFF) << 32;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int hashCode()
/* 21:   */   {
/* 22:28 */     long var1 = chunkXZ2Int(this.chunkXPos, this.chunkZPos);
/* 23:29 */     int var3 = (int)var1;
/* 24:30 */     int var4 = (int)(var1 >> 32);
/* 25:31 */     return var3 ^ var4;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean equals(Object par1Obj)
/* 29:   */   {
/* 30:36 */     ChunkCoordIntPair var2 = (ChunkCoordIntPair)par1Obj;
/* 31:37 */     return (var2.chunkXPos == this.chunkXPos) && (var2.chunkZPos == this.chunkZPos);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int getCenterXPos()
/* 35:   */   {
/* 36:42 */     return (this.chunkXPos << 4) + 8;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public int getCenterZPosition()
/* 40:   */   {
/* 41:47 */     return (this.chunkZPos << 4) + 8;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public ChunkPosition func_151349_a(int p_151349_1_)
/* 45:   */   {
/* 46:52 */     return new ChunkPosition(getCenterXPos(), p_151349_1_, getCenterZPosition());
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toString()
/* 50:   */   {
/* 51:57 */     return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.ChunkCoordIntPair
 * JD-Core Version:    0.7.0.1
 */