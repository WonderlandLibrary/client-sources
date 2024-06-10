/*   1:    */ package net.minecraft.world.gen.layer;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class IntCache
/*   7:    */ {
/*   8:  8 */   private static int intCacheSize = 256;
/*   9: 13 */   private static List freeSmallArrays = new ArrayList();
/*  10: 19 */   private static List inUseSmallArrays = new ArrayList();
/*  11: 24 */   private static List freeLargeArrays = new ArrayList();
/*  12: 30 */   private static List inUseLargeArrays = new ArrayList();
/*  13:    */   private static final String __OBFID = "CL_00000557";
/*  14:    */   
/*  15:    */   public static synchronized int[] getIntCache(int par0)
/*  16:    */   {
/*  17: 37 */     if (par0 <= 256)
/*  18:    */     {
/*  19: 39 */       if (freeSmallArrays.isEmpty())
/*  20:    */       {
/*  21: 41 */         int[] var1 = new int[256];
/*  22: 42 */         inUseSmallArrays.add(var1);
/*  23: 43 */         return var1;
/*  24:    */       }
/*  25: 47 */       int[] var1 = (int[])freeSmallArrays.remove(freeSmallArrays.size() - 1);
/*  26: 48 */       inUseSmallArrays.add(var1);
/*  27: 49 */       return var1;
/*  28:    */     }
/*  29: 52 */     if (par0 > intCacheSize)
/*  30:    */     {
/*  31: 54 */       intCacheSize = par0;
/*  32: 55 */       freeLargeArrays.clear();
/*  33: 56 */       inUseLargeArrays.clear();
/*  34: 57 */       int[] var1 = new int[intCacheSize];
/*  35: 58 */       inUseLargeArrays.add(var1);
/*  36: 59 */       return var1;
/*  37:    */     }
/*  38: 61 */     if (freeLargeArrays.isEmpty())
/*  39:    */     {
/*  40: 63 */       int[] var1 = new int[intCacheSize];
/*  41: 64 */       inUseLargeArrays.add(var1);
/*  42: 65 */       return var1;
/*  43:    */     }
/*  44: 69 */     int[] var1 = (int[])freeLargeArrays.remove(freeLargeArrays.size() - 1);
/*  45: 70 */     inUseLargeArrays.add(var1);
/*  46: 71 */     return var1;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static synchronized void resetIntCache()
/*  50:    */   {
/*  51: 80 */     if (!freeLargeArrays.isEmpty()) {
/*  52: 82 */       freeLargeArrays.remove(freeLargeArrays.size() - 1);
/*  53:    */     }
/*  54: 85 */     if (!freeSmallArrays.isEmpty()) {
/*  55: 87 */       freeSmallArrays.remove(freeSmallArrays.size() - 1);
/*  56:    */     }
/*  57: 90 */     freeLargeArrays.addAll(inUseLargeArrays);
/*  58: 91 */     freeSmallArrays.addAll(inUseSmallArrays);
/*  59: 92 */     inUseLargeArrays.clear();
/*  60: 93 */     inUseSmallArrays.clear();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static synchronized String getCacheSizes()
/*  64:    */   {
/*  65:102 */     return "cache: " + freeLargeArrays.size() + ", tcache: " + freeSmallArrays.size() + ", allocated: " + inUseLargeArrays.size() + ", tallocated: " + inUseSmallArrays.size();
/*  66:    */   }
/*  67:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.layer.IntCache
 * JD-Core Version:    0.7.0.1
 */