/*  1:   */ package net.minecraft.client.renderer.culling;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.AxisAlignedBB;
/*  4:   */ 
/*  5:   */ public class Frustrum
/*  6:   */   implements ICamera
/*  7:   */ {
/*  8: 7 */   private ClippingHelper clippingHelper = ClippingHelperImpl.getInstance();
/*  9:   */   private double xPosition;
/* 10:   */   private double yPosition;
/* 11:   */   private double zPosition;
/* 12:   */   private static final String __OBFID = "CL_00000976";
/* 13:   */   
/* 14:   */   public void setPosition(double par1, double par3, double par5)
/* 15:   */   {
/* 16:15 */     this.xPosition = par1;
/* 17:16 */     this.yPosition = par3;
/* 18:17 */     this.zPosition = par5;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean isBoxInFrustum(double par1, double par3, double par5, double par7, double par9, double par11)
/* 22:   */   {
/* 23:25 */     return this.clippingHelper.isBoxInFrustum(par1 - this.xPosition, par3 - this.yPosition, par5 - this.zPosition, par7 - this.xPosition, par9 - this.yPosition, par11 - this.zPosition);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean isBoundingBoxInFrustum(AxisAlignedBB par1AxisAlignedBB)
/* 27:   */   {
/* 28:33 */     return isBoxInFrustum(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ, par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean isBoxInFrustumFully(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
/* 32:   */   {
/* 33:38 */     return this.clippingHelper.isBoxInFrustumFully(minX - this.xPosition, minY - this.yPosition, minZ - this.zPosition, maxX - this.xPosition, maxY - this.yPosition, maxZ - this.zPosition);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean isBoundingBoxInFrustumFully(AxisAlignedBB aab)
/* 37:   */   {
/* 38:43 */     return isBoxInFrustumFully(aab.minX, aab.minY, aab.minZ, aab.maxX, aab.maxY, aab.maxZ);
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.culling.Frustrum
 * JD-Core Version:    0.7.0.1
 */