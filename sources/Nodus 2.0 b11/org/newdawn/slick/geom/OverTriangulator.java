/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ public class OverTriangulator
/*   4:    */   implements Triangulator
/*   5:    */ {
/*   6:    */   private float[][] triangles;
/*   7:    */   
/*   8:    */   public OverTriangulator(Triangulator tris)
/*   9:    */   {
/*  10: 19 */     this.triangles = new float[tris.getTriangleCount() * 6 * 3][2];
/*  11:    */     
/*  12: 21 */     int tcount = 0;
/*  13: 22 */     for (int i = 0; i < tris.getTriangleCount(); i++)
/*  14:    */     {
/*  15: 23 */       float cx = 0.0F;
/*  16: 24 */       float cy = 0.0F;
/*  17: 25 */       for (int p = 0; p < 3; p++)
/*  18:    */       {
/*  19: 26 */         float[] pt = tris.getTrianglePoint(i, p);
/*  20: 27 */         cx += pt[0];
/*  21: 28 */         cy += pt[1];
/*  22:    */       }
/*  23: 31 */       cx /= 3.0F;
/*  24: 32 */       cy /= 3.0F;
/*  25: 34 */       for (int p = 0; p < 3; p++)
/*  26:    */       {
/*  27: 35 */         int n = p + 1;
/*  28: 36 */         if (n > 2) {
/*  29: 37 */           n = 0;
/*  30:    */         }
/*  31: 40 */         float[] pt1 = tris.getTrianglePoint(i, p);
/*  32: 41 */         float[] pt2 = tris.getTrianglePoint(i, n);
/*  33:    */         
/*  34: 43 */         pt1[0] = ((pt1[0] + pt2[0]) / 2.0F);
/*  35: 44 */         pt1[1] = ((pt1[1] + pt2[1]) / 2.0F);
/*  36:    */         
/*  37: 46 */         this.triangles[(tcount * 3 + 0)][0] = cx;
/*  38: 47 */         this.triangles[(tcount * 3 + 0)][1] = cy;
/*  39: 48 */         this.triangles[(tcount * 3 + 1)][0] = pt1[0];
/*  40: 49 */         this.triangles[(tcount * 3 + 1)][1] = pt1[1];
/*  41: 50 */         this.triangles[(tcount * 3 + 2)][0] = pt2[0];
/*  42: 51 */         this.triangles[(tcount * 3 + 2)][1] = pt2[1];
/*  43: 52 */         tcount++;
/*  44:    */       }
/*  45: 55 */       for (int p = 0; p < 3; p++)
/*  46:    */       {
/*  47: 56 */         int n = p + 1;
/*  48: 57 */         if (n > 2) {
/*  49: 58 */           n = 0;
/*  50:    */         }
/*  51: 61 */         float[] pt1 = tris.getTrianglePoint(i, p);
/*  52: 62 */         float[] pt2 = tris.getTrianglePoint(i, n);
/*  53:    */         
/*  54: 64 */         pt2[0] = ((pt1[0] + pt2[0]) / 2.0F);
/*  55: 65 */         pt2[1] = ((pt1[1] + pt2[1]) / 2.0F);
/*  56:    */         
/*  57: 67 */         this.triangles[(tcount * 3 + 0)][0] = cx;
/*  58: 68 */         this.triangles[(tcount * 3 + 0)][1] = cy;
/*  59: 69 */         this.triangles[(tcount * 3 + 1)][0] = pt1[0];
/*  60: 70 */         this.triangles[(tcount * 3 + 1)][1] = pt1[1];
/*  61: 71 */         this.triangles[(tcount * 3 + 2)][0] = pt2[0];
/*  62: 72 */         this.triangles[(tcount * 3 + 2)][1] = pt2[1];
/*  63: 73 */         tcount++;
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void addPolyPoint(float x, float y) {}
/*  69:    */   
/*  70:    */   public int getTriangleCount()
/*  71:    */   {
/*  72: 88 */     return this.triangles.length / 3;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public float[] getTrianglePoint(int tri, int i)
/*  76:    */   {
/*  77: 95 */     float[] pt = this.triangles[(tri * 3 + i)];
/*  78:    */     
/*  79: 97 */     return new float[] { pt[0], pt[1] };
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void startHole() {}
/*  83:    */   
/*  84:    */   public boolean triangulate()
/*  85:    */   {
/*  86:110 */     return true;
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.OverTriangulator
 * JD-Core Version:    0.7.0.1
 */