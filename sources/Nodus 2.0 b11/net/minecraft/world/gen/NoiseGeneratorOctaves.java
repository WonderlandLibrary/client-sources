/*  1:   */ package net.minecraft.world.gen;
/*  2:   */ 
/*  3:   */ import java.util.Random;
/*  4:   */ import net.minecraft.util.MathHelper;
/*  5:   */ 
/*  6:   */ public class NoiseGeneratorOctaves
/*  7:   */   extends NoiseGenerator
/*  8:   */ {
/*  9:   */   private NoiseGeneratorImproved[] generatorCollection;
/* 10:   */   private int octaves;
/* 11:   */   private static final String __OBFID = "CL_00000535";
/* 12:   */   
/* 13:   */   public NoiseGeneratorOctaves(Random par1Random, int par2)
/* 14:   */   {
/* 15:17 */     this.octaves = par2;
/* 16:18 */     this.generatorCollection = new NoiseGeneratorImproved[par2];
/* 17:20 */     for (int var3 = 0; var3 < par2; var3++) {
/* 18:22 */       this.generatorCollection[var3] = new NoiseGeneratorImproved(par1Random);
/* 19:   */     }
/* 20:   */   }
/* 21:   */   
/* 22:   */   public double[] generateNoiseOctaves(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7, double par8, double par10, double par12)
/* 23:   */   {
/* 24:32 */     if (par1ArrayOfDouble == null) {
/* 25:34 */       par1ArrayOfDouble = new double[par5 * par6 * par7];
/* 26:   */     } else {
/* 27:38 */       for (int var14 = 0; var14 < par1ArrayOfDouble.length; var14++) {
/* 28:40 */         par1ArrayOfDouble[var14] = 0.0D;
/* 29:   */       }
/* 30:   */     }
/* 31:44 */     double var27 = 1.0D;
/* 32:46 */     for (int var16 = 0; var16 < this.octaves; var16++)
/* 33:   */     {
/* 34:48 */       double var17 = par2 * var27 * par8;
/* 35:49 */       double var19 = par3 * var27 * par10;
/* 36:50 */       double var21 = par4 * var27 * par12;
/* 37:51 */       long var23 = MathHelper.floor_double_long(var17);
/* 38:52 */       long var25 = MathHelper.floor_double_long(var21);
/* 39:53 */       var17 -= var23;
/* 40:54 */       var21 -= var25;
/* 41:55 */       var23 %= 16777216L;
/* 42:56 */       var25 %= 16777216L;
/* 43:57 */       var17 += var23;
/* 44:58 */       var21 += var25;
/* 45:59 */       this.generatorCollection[var16].populateNoiseArray(par1ArrayOfDouble, var17, var19, var21, par5, par6, par7, par8 * var27, par10 * var27, par12 * var27, var27);
/* 46:60 */       var27 /= 2.0D;
/* 47:   */     }
/* 48:63 */     return par1ArrayOfDouble;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public double[] generateNoiseOctaves(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, double par6, double par8, double par10)
/* 52:   */   {
/* 53:71 */     return generateNoiseOctaves(par1ArrayOfDouble, par2, 10, par3, par4, 1, par5, par6, 1.0D, par8);
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.NoiseGeneratorOctaves
 * JD-Core Version:    0.7.0.1
 */