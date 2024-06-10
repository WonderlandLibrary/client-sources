/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ 
/*   6:    */ public class Vec3Pool
/*   7:    */ {
/*   8:    */   private final int truncateArrayResetThreshold;
/*   9:    */   private final int minimumSize;
/*  10: 12 */   private final List vec3Cache = new ArrayList();
/*  11:    */   private int nextFreeSpace;
/*  12:    */   private int maximumSizeSinceLastTruncation;
/*  13:    */   private int resetCount;
/*  14:    */   private static final String __OBFID = "CL_00000613";
/*  15:    */   
/*  16:    */   public Vec3Pool(int par1, int par2)
/*  17:    */   {
/*  18: 27 */     this.truncateArrayResetThreshold = par1;
/*  19: 28 */     this.minimumSize = par2;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Vec3 getVecFromPool(double par1, double par3, double par5)
/*  23:    */   {
/*  24: 36 */     if (skipCache()) {
/*  25: 38 */       return new Vec3(this, par1, par3, par5);
/*  26:    */     }
/*  27:    */     Vec3 var7;
/*  28: 44 */     if (this.nextFreeSpace >= this.vec3Cache.size())
/*  29:    */     {
/*  30: 46 */       Vec3 var7 = new Vec3(this, par1, par3, par5);
/*  31: 47 */       this.vec3Cache.add(var7);
/*  32:    */     }
/*  33:    */     else
/*  34:    */     {
/*  35: 51 */       var7 = (Vec3)this.vec3Cache.get(this.nextFreeSpace);
/*  36: 52 */       var7.setComponents(par1, par3, par5);
/*  37:    */     }
/*  38: 55 */     this.nextFreeSpace += 1;
/*  39: 56 */     return var7;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void clear()
/*  43:    */   {
/*  44: 65 */     if (!skipCache())
/*  45:    */     {
/*  46: 67 */       if (this.nextFreeSpace > this.maximumSizeSinceLastTruncation) {
/*  47: 69 */         this.maximumSizeSinceLastTruncation = this.nextFreeSpace;
/*  48:    */       }
/*  49: 72 */       if (this.resetCount++ == this.truncateArrayResetThreshold)
/*  50:    */       {
/*  51: 74 */         int var1 = Math.max(this.maximumSizeSinceLastTruncation, this.vec3Cache.size() - this.minimumSize);
/*  52: 76 */         while (this.vec3Cache.size() > var1) {
/*  53: 78 */           this.vec3Cache.remove(var1);
/*  54:    */         }
/*  55: 81 */         this.maximumSizeSinceLastTruncation = 0;
/*  56: 82 */         this.resetCount = 0;
/*  57:    */       }
/*  58: 85 */       this.nextFreeSpace = 0;
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void clearAndFreeCache()
/*  63:    */   {
/*  64: 91 */     if (!skipCache())
/*  65:    */     {
/*  66: 93 */       this.nextFreeSpace = 0;
/*  67: 94 */       this.vec3Cache.clear();
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getPoolSize()
/*  72:    */   {
/*  73:103 */     return this.vec3Cache.size();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int getNextFreeSpace()
/*  77:    */   {
/*  78:111 */     return this.nextFreeSpace;
/*  79:    */   }
/*  80:    */   
/*  81:    */   private boolean skipCache()
/*  82:    */   {
/*  83:119 */     return (this.minimumSize < 0) || (this.truncateArrayResetThreshold < 0);
/*  84:    */   }
/*  85:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.Vec3Pool
 * JD-Core Version:    0.7.0.1
 */