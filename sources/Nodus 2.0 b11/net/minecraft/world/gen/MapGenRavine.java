/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   9:    */ 
/*  10:    */ public class MapGenRavine
/*  11:    */   extends MapGenBase
/*  12:    */ {
/*  13: 11 */   private float[] field_75046_d = new float[1024];
/*  14:    */   private static final String __OBFID = "CL_00000390";
/*  15:    */   
/*  16:    */   protected void func_151540_a(long p_151540_1_, int p_151540_3_, int p_151540_4_, Block[] p_151540_5_, double p_151540_6_, double p_151540_8_, double p_151540_10_, float p_151540_12_, float p_151540_13_, float p_151540_14_, int p_151540_15_, int p_151540_16_, double p_151540_17_)
/*  17:    */   {
/*  18: 16 */     Random var19 = new Random(p_151540_1_);
/*  19: 17 */     double var20 = p_151540_3_ * 16 + 8;
/*  20: 18 */     double var22 = p_151540_4_ * 16 + 8;
/*  21: 19 */     float var24 = 0.0F;
/*  22: 20 */     float var25 = 0.0F;
/*  23: 22 */     if (p_151540_16_ <= 0)
/*  24:    */     {
/*  25: 24 */       int var26 = this.range * 16 - 16;
/*  26: 25 */       p_151540_16_ = var26 - var19.nextInt(var26 / 4);
/*  27:    */     }
/*  28: 28 */     boolean var54 = false;
/*  29: 30 */     if (p_151540_15_ == -1)
/*  30:    */     {
/*  31: 32 */       p_151540_15_ = p_151540_16_ / 2;
/*  32: 33 */       var54 = true;
/*  33:    */     }
/*  34: 36 */     float var27 = 1.0F;
/*  35: 38 */     for (int var28 = 0; var28 < 256; var28++)
/*  36:    */     {
/*  37: 40 */       if ((var28 == 0) || (var19.nextInt(3) == 0)) {
/*  38: 42 */         var27 = 1.0F + var19.nextFloat() * var19.nextFloat() * 1.0F;
/*  39:    */       }
/*  40: 45 */       this.field_75046_d[var28] = (var27 * var27);
/*  41:    */     }
/*  42: 48 */     for (; p_151540_15_ < p_151540_16_; p_151540_15_++)
/*  43:    */     {
/*  44: 50 */       double var53 = 1.5D + MathHelper.sin(p_151540_15_ * 3.141593F / p_151540_16_) * p_151540_12_ * 1.0F;
/*  45: 51 */       double var30 = var53 * p_151540_17_;
/*  46: 52 */       var53 *= (var19.nextFloat() * 0.25D + 0.75D);
/*  47: 53 */       var30 *= (var19.nextFloat() * 0.25D + 0.75D);
/*  48: 54 */       float var32 = MathHelper.cos(p_151540_14_);
/*  49: 55 */       float var33 = MathHelper.sin(p_151540_14_);
/*  50: 56 */       p_151540_6_ += MathHelper.cos(p_151540_13_) * var32;
/*  51: 57 */       p_151540_8_ += var33;
/*  52: 58 */       p_151540_10_ += MathHelper.sin(p_151540_13_) * var32;
/*  53: 59 */       p_151540_14_ *= 0.7F;
/*  54: 60 */       p_151540_14_ += var25 * 0.05F;
/*  55: 61 */       p_151540_13_ += var24 * 0.05F;
/*  56: 62 */       var25 *= 0.8F;
/*  57: 63 */       var24 *= 0.5F;
/*  58: 64 */       var25 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 2.0F;
/*  59: 65 */       var24 += (var19.nextFloat() - var19.nextFloat()) * var19.nextFloat() * 4.0F;
/*  60: 67 */       if ((var54) || (var19.nextInt(4) != 0))
/*  61:    */       {
/*  62: 69 */         double var34 = p_151540_6_ - var20;
/*  63: 70 */         double var36 = p_151540_10_ - var22;
/*  64: 71 */         double var38 = p_151540_16_ - p_151540_15_;
/*  65: 72 */         double var40 = p_151540_12_ + 2.0F + 16.0F;
/*  66: 74 */         if (var34 * var34 + var36 * var36 - var38 * var38 > var40 * var40) {
/*  67: 76 */           return;
/*  68:    */         }
/*  69: 79 */         if ((p_151540_6_ >= var20 - 16.0D - var53 * 2.0D) && (p_151540_10_ >= var22 - 16.0D - var53 * 2.0D) && (p_151540_6_ <= var20 + 16.0D + var53 * 2.0D) && (p_151540_10_ <= var22 + 16.0D + var53 * 2.0D))
/*  70:    */         {
/*  71: 81 */           int var56 = MathHelper.floor_double(p_151540_6_ - var53) - p_151540_3_ * 16 - 1;
/*  72: 82 */           int var35 = MathHelper.floor_double(p_151540_6_ + var53) - p_151540_3_ * 16 + 1;
/*  73: 83 */           int var55 = MathHelper.floor_double(p_151540_8_ - var30) - 1;
/*  74: 84 */           int var37 = MathHelper.floor_double(p_151540_8_ + var30) + 1;
/*  75: 85 */           int var57 = MathHelper.floor_double(p_151540_10_ - var53) - p_151540_4_ * 16 - 1;
/*  76: 86 */           int var39 = MathHelper.floor_double(p_151540_10_ + var53) - p_151540_4_ * 16 + 1;
/*  77: 88 */           if (var56 < 0) {
/*  78: 90 */             var56 = 0;
/*  79:    */           }
/*  80: 93 */           if (var35 > 16) {
/*  81: 95 */             var35 = 16;
/*  82:    */           }
/*  83: 98 */           if (var55 < 1) {
/*  84:100 */             var55 = 1;
/*  85:    */           }
/*  86:103 */           if (var37 > 248) {
/*  87:105 */             var37 = 248;
/*  88:    */           }
/*  89:108 */           if (var57 < 0) {
/*  90:110 */             var57 = 0;
/*  91:    */           }
/*  92:113 */           if (var39 > 16) {
/*  93:115 */             var39 = 16;
/*  94:    */           }
/*  95:118 */           boolean var58 = false;
/*  96:122 */           for (int var41 = var56; (!var58) && (var41 < var35); var41++) {
/*  97:124 */             for (int var42 = var57; (!var58) && (var42 < var39); var42++) {
/*  98:126 */               for (int var43 = var37 + 1; (!var58) && (var43 >= var55 - 1); var43--)
/*  99:    */               {
/* 100:128 */                 int var44 = (var41 * 16 + var42) * 256 + var43;
/* 101:130 */                 if ((var43 >= 0) && (var43 < 256))
/* 102:    */                 {
/* 103:132 */                   Block var45 = p_151540_5_[var44];
/* 104:134 */                   if ((var45 == Blocks.flowing_water) || (var45 == Blocks.water)) {
/* 105:136 */                     var58 = true;
/* 106:    */                   }
/* 107:139 */                   if ((var43 != var55 - 1) && (var41 != var56) && (var41 != var35 - 1) && (var42 != var57) && (var42 != var39 - 1)) {
/* 108:141 */                     var43 = var55;
/* 109:    */                   }
/* 110:    */                 }
/* 111:    */               }
/* 112:    */             }
/* 113:    */           }
/* 114:148 */           if (!var58)
/* 115:    */           {
/* 116:150 */             for (var41 = var56; var41 < var35; var41++)
/* 117:    */             {
/* 118:152 */               double var60 = (var41 + p_151540_3_ * 16 + 0.5D - p_151540_6_) / var53;
/* 119:154 */               for (int var44 = var57; var44 < var39; var44++)
/* 120:    */               {
/* 121:156 */                 double var59 = (var44 + p_151540_4_ * 16 + 0.5D - p_151540_10_) / var53;
/* 122:157 */                 int var47 = (var41 * 16 + var44) * 256 + var37;
/* 123:158 */                 boolean var48 = false;
/* 124:160 */                 if (var60 * var60 + var59 * var59 < 1.0D) {
/* 125:162 */                   for (int var49 = var37 - 1; var49 >= var55; var49--)
/* 126:    */                   {
/* 127:164 */                     double var50 = (var49 + 0.5D - p_151540_8_) / var30;
/* 128:166 */                     if ((var60 * var60 + var59 * var59) * this.field_75046_d[var49] + var50 * var50 / 6.0D < 1.0D)
/* 129:    */                     {
/* 130:168 */                       Block var52 = p_151540_5_[var47];
/* 131:170 */                       if (var52 == Blocks.grass) {
/* 132:172 */                         var48 = true;
/* 133:    */                       }
/* 134:175 */                       if ((var52 == Blocks.stone) || (var52 == Blocks.dirt) || (var52 == Blocks.grass)) {
/* 135:177 */                         if (var49 < 10)
/* 136:    */                         {
/* 137:179 */                           p_151540_5_[var47] = Blocks.flowing_lava;
/* 138:    */                         }
/* 139:    */                         else
/* 140:    */                         {
/* 141:183 */                           p_151540_5_[var47] = null;
/* 142:185 */                           if ((var48) && (p_151540_5_[(var47 - 1)] == Blocks.dirt)) {
/* 143:187 */                             p_151540_5_[(var47 - 1)] = this.worldObj.getBiomeGenForCoords(var41 + p_151540_3_ * 16, var44 + p_151540_4_ * 16).topBlock;
/* 144:    */                           }
/* 145:    */                         }
/* 146:    */                       }
/* 147:    */                     }
/* 148:193 */                     var47--;
/* 149:    */                   }
/* 150:    */                 }
/* 151:    */               }
/* 152:    */             }
/* 153:199 */             if (var54) {
/* 154:    */               break;
/* 155:    */             }
/* 156:    */           }
/* 157:    */         }
/* 158:    */       }
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   protected void func_151538_a(World p_151538_1_, int p_151538_2_, int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_)
/* 163:    */   {
/* 164:211 */     if (this.rand.nextInt(50) == 0)
/* 165:    */     {
/* 166:213 */       double var7 = p_151538_2_ * 16 + this.rand.nextInt(16);
/* 167:214 */       double var9 = this.rand.nextInt(this.rand.nextInt(40) + 8) + 20;
/* 168:215 */       double var11 = p_151538_3_ * 16 + this.rand.nextInt(16);
/* 169:216 */       byte var13 = 1;
/* 170:218 */       for (int var14 = 0; var14 < var13; var14++)
/* 171:    */       {
/* 172:220 */         float var15 = this.rand.nextFloat() * 3.141593F * 2.0F;
/* 173:221 */         float var16 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 174:222 */         float var17 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
/* 175:223 */         func_151540_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var7, var9, var11, var17, var15, var16, 0, 0, 3.0D);
/* 176:    */       }
/* 177:    */     }
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.MapGenRavine
 * JD-Core Version:    0.7.0.1
 */