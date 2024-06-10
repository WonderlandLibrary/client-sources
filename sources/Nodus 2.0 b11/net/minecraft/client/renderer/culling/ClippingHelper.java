/*  1:   */ package net.minecraft.client.renderer.culling;
/*  2:   */ 
/*  3:   */ public class ClippingHelper
/*  4:   */ {
/*  5: 5 */   public float[][] frustum = new float[16][16];
/*  6: 6 */   public float[] projectionMatrix = new float[16];
/*  7: 7 */   public float[] modelviewMatrix = new float[16];
/*  8: 8 */   public float[] clippingMatrix = new float[16];
/*  9:   */   private static final String __OBFID = "CL_00000977";
/* 10:   */   
/* 11:   */   public boolean isBoxInFrustum(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
/* 12:   */   {
/* 13:16 */     for (int i = 0; i < 6; i++)
/* 14:   */     {
/* 15:18 */       float minXf = (float)minX;
/* 16:19 */       float minYf = (float)minY;
/* 17:20 */       float minZf = (float)minZ;
/* 18:21 */       float maxXf = (float)maxX;
/* 19:22 */       float maxYf = (float)maxY;
/* 20:23 */       float maxZf = (float)maxZ;
/* 21:25 */       if ((this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F)) {
/* 22:27 */         return false;
/* 23:   */       }
/* 24:   */     }
/* 25:31 */     return true;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean isBoxInFrustumFully(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
/* 29:   */   {
/* 30:36 */     for (int i = 0; i < 6; i++)
/* 31:   */     {
/* 32:38 */       float minXf = (float)minX;
/* 33:39 */       float minYf = (float)minY;
/* 34:40 */       float minZf = (float)minZ;
/* 35:41 */       float maxXf = (float)maxX;
/* 36:42 */       float maxYf = (float)maxY;
/* 37:43 */       float maxZf = (float)maxZ;
/* 38:45 */       if (i < 4)
/* 39:   */       {
/* 40:47 */         if ((this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) || (this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) || (this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) || (this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) || (this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F) || (this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F) || (this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F) || (this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F)) {
/* 41:49 */           return false;
/* 42:   */         }
/* 43:   */       }
/* 44:52 */       else if ((this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * minZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * minXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * maxXf + this.frustum[i][1] * minYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * minXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F) && (this.frustum[i][0] * maxXf + this.frustum[i][1] * maxYf + this.frustum[i][2] * maxZf + this.frustum[i][3] <= 0.0F)) {
/* 45:54 */         return false;
/* 46:   */       }
/* 47:   */     }
/* 48:58 */     return true;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.culling.ClippingHelper
 * JD-Core Version:    0.7.0.1
 */