/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ public class GenLayerDeepOcean
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerDeepOcean(long p_i45472_1_, GenLayer p_i45472_3_) {
/*  9 */     super(p_i45472_1_);
/* 10 */     this.parent = p_i45472_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 19 */     int i = areaX - 1;
/* 20 */     int j = areaY - 1;
/* 21 */     int k = areaWidth + 2;
/* 22 */     int l = areaHeight + 2;
/* 23 */     int[] aint = this.parent.getInts(i, j, k, l);
/* 24 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 26 */     for (int i1 = 0; i1 < areaHeight; i1++) {
/*    */       
/* 28 */       for (int j1 = 0; j1 < areaWidth; j1++) {
/*    */         
/* 30 */         int k1 = aint[j1 + 1 + (i1 + 1 - 1) * (areaWidth + 2)];
/* 31 */         int l1 = aint[j1 + 1 + 1 + (i1 + 1) * (areaWidth + 2)];
/* 32 */         int i2 = aint[j1 + 1 - 1 + (i1 + 1) * (areaWidth + 2)];
/* 33 */         int j2 = aint[j1 + 1 + (i1 + 1 + 1) * (areaWidth + 2)];
/* 34 */         int k2 = aint[j1 + 1 + (i1 + 1) * k];
/* 35 */         int l2 = 0;
/*    */         
/* 37 */         if (k1 == 0)
/*    */         {
/* 39 */           l2++;
/*    */         }
/*    */         
/* 42 */         if (l1 == 0)
/*    */         {
/* 44 */           l2++;
/*    */         }
/*    */         
/* 47 */         if (i2 == 0)
/*    */         {
/* 49 */           l2++;
/*    */         }
/*    */         
/* 52 */         if (j2 == 0)
/*    */         {
/* 54 */           l2++;
/*    */         }
/*    */         
/* 57 */         if (k2 == 0 && l2 > 3) {
/*    */           
/* 59 */           aint1[j1 + i1 * areaWidth] = BiomeGenBase.deepOcean.biomeID;
/*    */         }
/*    */         else {
/*    */           
/* 63 */           aint1[j1 + i1 * areaWidth] = k2;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 68 */     return aint1;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\gen\layer\GenLayerDeepOcean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */