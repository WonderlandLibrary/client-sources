/*  1:   */ package net.minecraft.client.renderer;
/*  2:   */ 
/*  3:   */ public class DestroyBlockProgress
/*  4:   */ {
/*  5:   */   private final int miningPlayerEntId;
/*  6:   */   private final int partialBlockX;
/*  7:   */   private final int partialBlockY;
/*  8:   */   private final int partialBlockZ;
/*  9:   */   private int partialBlockProgress;
/* 10:   */   private int createdAtCloudUpdateTick;
/* 11:   */   private static final String __OBFID = "CL_00001427";
/* 12:   */   
/* 13:   */   public DestroyBlockProgress(int par1, int par2, int par3, int par4)
/* 14:   */   {
/* 15:27 */     this.miningPlayerEntId = par1;
/* 16:28 */     this.partialBlockX = par2;
/* 17:29 */     this.partialBlockY = par3;
/* 18:30 */     this.partialBlockZ = par4;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getPartialBlockX()
/* 22:   */   {
/* 23:35 */     return this.partialBlockX;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getPartialBlockY()
/* 27:   */   {
/* 28:40 */     return this.partialBlockY;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public int getPartialBlockZ()
/* 32:   */   {
/* 33:45 */     return this.partialBlockZ;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setPartialBlockDamage(int par1)
/* 37:   */   {
/* 38:54 */     if (par1 > 10) {
/* 39:56 */       par1 = 10;
/* 40:   */     }
/* 41:59 */     this.partialBlockProgress = par1;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int getPartialBlockDamage()
/* 45:   */   {
/* 46:64 */     return this.partialBlockProgress;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setCloudUpdateTick(int par1)
/* 50:   */   {
/* 51:72 */     this.createdAtCloudUpdateTick = par1;
/* 52:   */   }
/* 53:   */   
/* 54:   */   public int getCreationCloudUpdateTick()
/* 55:   */   {
/* 56:80 */     return this.createdAtCloudUpdateTick;
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.DestroyBlockProgress
 * JD-Core Version:    0.7.0.1
 */