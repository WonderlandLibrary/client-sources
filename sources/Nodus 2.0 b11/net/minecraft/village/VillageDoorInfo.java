/*  1:   */ package net.minecraft.village;
/*  2:   */ 
/*  3:   */ public class VillageDoorInfo
/*  4:   */ {
/*  5:   */   public final int posX;
/*  6:   */   public final int posY;
/*  7:   */   public final int posZ;
/*  8:   */   public final int insideDirectionX;
/*  9:   */   public final int insideDirectionZ;
/* 10:   */   public int lastActivityTimestamp;
/* 11:   */   public boolean isDetachedFromVillageFlag;
/* 12:   */   private int doorOpeningRestrictionCounter;
/* 13:   */   private static final String __OBFID = "CL_00001630";
/* 14:   */   
/* 15:   */   public VillageDoorInfo(int par1, int par2, int par3, int par4, int par5, int par6)
/* 16:   */   {
/* 17:17 */     this.posX = par1;
/* 18:18 */     this.posY = par2;
/* 19:19 */     this.posZ = par3;
/* 20:20 */     this.insideDirectionX = par4;
/* 21:21 */     this.insideDirectionZ = par5;
/* 22:22 */     this.lastActivityTimestamp = par6;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getDistanceSquared(int par1, int par2, int par3)
/* 26:   */   {
/* 27:30 */     int var4 = par1 - this.posX;
/* 28:31 */     int var5 = par2 - this.posY;
/* 29:32 */     int var6 = par3 - this.posZ;
/* 30:33 */     return var4 * var4 + var5 * var5 + var6 * var6;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int getInsideDistanceSquare(int par1, int par2, int par3)
/* 34:   */   {
/* 35:42 */     int var4 = par1 - this.posX - this.insideDirectionX;
/* 36:43 */     int var5 = par2 - this.posY;
/* 37:44 */     int var6 = par3 - this.posZ - this.insideDirectionZ;
/* 38:45 */     return var4 * var4 + var5 * var5 + var6 * var6;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int getInsidePosX()
/* 42:   */   {
/* 43:50 */     return this.posX + this.insideDirectionX;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public int getInsidePosY()
/* 47:   */   {
/* 48:55 */     return this.posY;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public int getInsidePosZ()
/* 52:   */   {
/* 53:60 */     return this.posZ + this.insideDirectionZ;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public boolean isInside(int par1, int par2)
/* 57:   */   {
/* 58:65 */     int var3 = par1 - this.posX;
/* 59:66 */     int var4 = par2 - this.posZ;
/* 60:67 */     return var3 * this.insideDirectionX + var4 * this.insideDirectionZ >= 0;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void resetDoorOpeningRestrictionCounter()
/* 64:   */   {
/* 65:72 */     this.doorOpeningRestrictionCounter = 0;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void incrementDoorOpeningRestrictionCounter()
/* 69:   */   {
/* 70:77 */     this.doorOpeningRestrictionCounter += 1;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public int getDoorOpeningRestrictionCounter()
/* 74:   */   {
/* 75:82 */     return this.doorOpeningRestrictionCounter;
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.village.VillageDoorInfo
 * JD-Core Version:    0.7.0.1
 */