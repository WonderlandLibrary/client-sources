/*  1:   */ package net.minecraft.world.gen;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ 
/*  5:   */ public class NoiseGeneratorPerlin
/*  6:   */   extends NoiseGenerator
/*  7:   */ {
/*  8:   */   private NoiseGeneratorSimplex[] field_151603_a;
/*  9:   */   private int field_151602_b;
/* 10:   */   private static final String __OBFID = "CL_00000536";
/* 11:   */   
/* 12:   */   public NoiseGeneratorPerlin(Random p_i45470_1_, int p_i45470_2_)
/* 13:   */   {
/* 14:13 */     this.field_151602_b = p_i45470_2_;
/* 15:14 */     this.field_151603_a = new NoiseGeneratorSimplex[p_i45470_2_];
/* 16:16 */     for (int var3 = 0; var3 < p_i45470_2_; var3++) {
/* 17:18 */       this.field_151603_a[var3] = new NoiseGeneratorSimplex(p_i45470_1_);
/* 18:   */     }
/* 19:   */   }
/* 20:   */   
/* 21:   */   public double func_151601_a(double p_151601_1_, double p_151601_3_)
/* 22:   */   {
/* 23:24 */     double var5 = 0.0D;
/* 24:25 */     double var7 = 1.0D;
/* 25:27 */     for (int var9 = 0; var9 < this.field_151602_b; var9++)
/* 26:   */     {
/* 27:29 */       var5 += this.field_151603_a[var9].func_151605_a(p_151601_1_ * var7, p_151601_3_ * var7) / var7;
/* 28:30 */       var7 /= 2.0D;
/* 29:   */     }
/* 30:33 */     return var5;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public double[] func_151599_a(double[] p_151599_1_, double p_151599_2_, double p_151599_4_, int p_151599_6_, int p_151599_7_, double p_151599_8_, double p_151599_10_, double p_151599_12_)
/* 34:   */   {
/* 35:38 */     return func_151600_a(p_151599_1_, p_151599_2_, p_151599_4_, p_151599_6_, p_151599_7_, p_151599_8_, p_151599_10_, p_151599_12_, 0.5D);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public double[] func_151600_a(double[] p_151600_1_, double p_151600_2_, double p_151600_4_, int p_151600_6_, int p_151600_7_, double p_151600_8_, double p_151600_10_, double p_151600_12_, double p_151600_14_)
/* 39:   */   {
/* 40:43 */     if ((p_151600_1_ != null) && (p_151600_1_.length >= p_151600_6_ * p_151600_7_)) {
/* 41:45 */       for (int var16 = 0; var16 < p_151600_1_.length; var16++) {
/* 42:47 */         p_151600_1_[var16] = 0.0D;
/* 43:   */       }
/* 44:   */     } else {
/* 45:52 */       p_151600_1_ = new double[p_151600_6_ * p_151600_7_];
/* 46:   */     }
/* 47:55 */     double var21 = 1.0D;
/* 48:56 */     double var18 = 1.0D;
/* 49:58 */     for (int var20 = 0; var20 < this.field_151602_b; var20++)
/* 50:   */     {
/* 51:60 */       this.field_151603_a[var20].func_151606_a(p_151600_1_, p_151600_2_, p_151600_4_, p_151600_6_, p_151600_7_, p_151600_8_ * var18 * var21, p_151600_10_ * var18 * var21, 0.55D / var21);
/* 52:61 */       var18 *= p_151600_12_;
/* 53:62 */       var21 *= p_151600_14_;
/* 54:   */     }
/* 55:65 */     return p_151600_1_;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.NoiseGeneratorPerlin
 * JD-Core Version:    0.7.0.1
 */