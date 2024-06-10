/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ 
/*   9:    */ public class MapGenCavesHell
/*  10:    */   extends MapGenBase
/*  11:    */ {
/*  12:    */   private static final String __OBFID = "CL_00000395";
/*  13:    */   
/*  14:    */   protected void func_151544_a(long p_151544_1_, int p_151544_3_, int p_151544_4_, Block[] p_151544_5_, double p_151544_6_, double p_151544_8_, double p_151544_10_)
/*  15:    */   {
/*  16: 15 */     func_151543_a(p_151544_1_, p_151544_3_, p_151544_4_, p_151544_5_, p_151544_6_, p_151544_8_, p_151544_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*  17:    */   }
/*  18:    */   
/*  19:    */   protected void func_151543_a(long p_151543_1_, int p_151543_3_, int p_151543_4_, Block[] p_151543_5_, double p_151543_6_, double p_151543_8_, double p_151543_10_, float p_151543_12_, float p_151543_13_, float p_151543_14_, int p_151543_15_, int p_151543_16_, double p_151543_17_)
/*  20:    */   {
/*  21: 20 */     double var19 = p_151543_3_ * 16 + 8;
/*  22: 21 */     double var21 = p_151543_4_ * 16 + 8;
/*  23: 22 */     float var23 = 0.0F;
/*  24: 23 */     float var24 = 0.0F;
/*  25: 24 */     Random var25 = new Random(p_151543_1_);
/*  26: 26 */     if (p_151543_16_ <= 0)
/*  27:    */     {
/*  28: 28 */       int var26 = this.range * 16 - 16;
/*  29: 29 */       p_151543_16_ = var26 - var25.nextInt(var26 / 4);
/*  30:    */     }
/*  31: 32 */     boolean var53 = false;
/*  32: 34 */     if (p_151543_15_ == -1)
/*  33:    */     {
/*  34: 36 */       p_151543_15_ = p_151543_16_ / 2;
/*  35: 37 */       var53 = true;
/*  36:    */     }
/*  37: 40 */     int var27 = var25.nextInt(p_151543_16_ / 2) + p_151543_16_ / 4;
/*  38: 42 */     for (boolean var28 = var25.nextInt(6) == 0; p_151543_15_ < p_151543_16_; p_151543_15_++)
/*  39:    */     {
/*  40: 44 */       double var29 = 1.5D + MathHelper.sin(p_151543_15_ * 3.141593F / p_151543_16_) * p_151543_12_ * 1.0F;
/*  41: 45 */       double var31 = var29 * p_151543_17_;
/*  42: 46 */       float var33 = MathHelper.cos(p_151543_14_);
/*  43: 47 */       float var34 = MathHelper.sin(p_151543_14_);
/*  44: 48 */       p_151543_6_ += MathHelper.cos(p_151543_13_) * var33;
/*  45: 49 */       p_151543_8_ += var34;
/*  46: 50 */       p_151543_10_ += MathHelper.sin(p_151543_13_) * var33;
/*  47: 52 */       if (var28) {
/*  48: 54 */         p_151543_14_ *= 0.92F;
/*  49:    */       } else {
/*  50: 58 */         p_151543_14_ *= 0.7F;
/*  51:    */       }
/*  52: 61 */       p_151543_14_ += var24 * 0.1F;
/*  53: 62 */       p_151543_13_ += var23 * 0.1F;
/*  54: 63 */       var24 *= 0.9F;
/*  55: 64 */       var23 *= 0.75F;
/*  56: 65 */       var24 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 2.0F;
/*  57: 66 */       var23 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 4.0F;
/*  58: 68 */       if ((!var53) && (p_151543_15_ == var27) && (p_151543_12_ > 1.0F))
/*  59:    */       {
/*  60: 70 */         func_151543_a(var25.nextLong(), p_151543_3_, p_151543_4_, p_151543_5_, p_151543_6_, p_151543_8_, p_151543_10_, var25.nextFloat() * 0.5F + 0.5F, p_151543_13_ - 1.570796F, p_151543_14_ / 3.0F, p_151543_15_, p_151543_16_, 1.0D);
/*  61: 71 */         func_151543_a(var25.nextLong(), p_151543_3_, p_151543_4_, p_151543_5_, p_151543_6_, p_151543_8_, p_151543_10_, var25.nextFloat() * 0.5F + 0.5F, p_151543_13_ + 1.570796F, p_151543_14_ / 3.0F, p_151543_15_, p_151543_16_, 1.0D);
/*  62: 72 */         return;
/*  63:    */       }
/*  64: 75 */       if ((var53) || (var25.nextInt(4) != 0))
/*  65:    */       {
/*  66: 77 */         double var35 = p_151543_6_ - var19;
/*  67: 78 */         double var37 = p_151543_10_ - var21;
/*  68: 79 */         double var39 = p_151543_16_ - p_151543_15_;
/*  69: 80 */         double var41 = p_151543_12_ + 2.0F + 16.0F;
/*  70: 82 */         if (var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41) {
/*  71: 84 */           return;
/*  72:    */         }
/*  73: 87 */         if ((p_151543_6_ >= var19 - 16.0D - var29 * 2.0D) && (p_151543_10_ >= var21 - 16.0D - var29 * 2.0D) && (p_151543_6_ <= var19 + 16.0D + var29 * 2.0D) && (p_151543_10_ <= var21 + 16.0D + var29 * 2.0D))
/*  74:    */         {
/*  75: 89 */           int var54 = MathHelper.floor_double(p_151543_6_ - var29) - p_151543_3_ * 16 - 1;
/*  76: 90 */           int var36 = MathHelper.floor_double(p_151543_6_ + var29) - p_151543_3_ * 16 + 1;
/*  77: 91 */           int var56 = MathHelper.floor_double(p_151543_8_ - var31) - 1;
/*  78: 92 */           int var38 = MathHelper.floor_double(p_151543_8_ + var31) + 1;
/*  79: 93 */           int var55 = MathHelper.floor_double(p_151543_10_ - var29) - p_151543_4_ * 16 - 1;
/*  80: 94 */           int var40 = MathHelper.floor_double(p_151543_10_ + var29) - p_151543_4_ * 16 + 1;
/*  81: 96 */           if (var54 < 0) {
/*  82: 98 */             var54 = 0;
/*  83:    */           }
/*  84:101 */           if (var36 > 16) {
/*  85:103 */             var36 = 16;
/*  86:    */           }
/*  87:106 */           if (var56 < 1) {
/*  88:108 */             var56 = 1;
/*  89:    */           }
/*  90:111 */           if (var38 > 120) {
/*  91:113 */             var38 = 120;
/*  92:    */           }
/*  93:116 */           if (var55 < 0) {
/*  94:118 */             var55 = 0;
/*  95:    */           }
/*  96:121 */           if (var40 > 16) {
/*  97:123 */             var40 = 16;
/*  98:    */           }
/*  99:126 */           boolean var57 = false;
/* 100:130 */           for (int var42 = var54; (!var57) && (var42 < var36); var42++) {
/* 101:132 */             for (int var43 = var55; (!var57) && (var43 < var40); var43++) {
/* 102:134 */               for (int var44 = var38 + 1; (!var57) && (var44 >= var56 - 1); var44--)
/* 103:    */               {
/* 104:136 */                 int var45 = (var42 * 16 + var43) * 128 + var44;
/* 105:138 */                 if ((var44 >= 0) && (var44 < 128))
/* 106:    */                 {
/* 107:140 */                   Block var46 = p_151543_5_[var45];
/* 108:142 */                   if ((var46 == Blocks.flowing_lava) || (var46 == Blocks.lava)) {
/* 109:144 */                     var57 = true;
/* 110:    */                   }
/* 111:147 */                   if ((var44 != var56 - 1) && (var42 != var54) && (var42 != var36 - 1) && (var43 != var55) && (var43 != var40 - 1)) {
/* 112:149 */                     var44 = var56;
/* 113:    */                   }
/* 114:    */                 }
/* 115:    */               }
/* 116:    */             }
/* 117:    */           }
/* 118:156 */           if (!var57)
/* 119:    */           {
/* 120:158 */             for (var42 = var54; var42 < var36; var42++)
/* 121:    */             {
/* 122:160 */               double var59 = (var42 + p_151543_3_ * 16 + 0.5D - p_151543_6_) / var29;
/* 123:162 */               for (int var45 = var55; var45 < var40; var45++)
/* 124:    */               {
/* 125:164 */                 double var58 = (var45 + p_151543_4_ * 16 + 0.5D - p_151543_10_) / var29;
/* 126:165 */                 int var48 = (var42 * 16 + var45) * 128 + var38;
/* 127:167 */                 for (int var49 = var38 - 1; var49 >= var56; var49--)
/* 128:    */                 {
/* 129:169 */                   double var50 = (var49 + 0.5D - p_151543_8_) / var31;
/* 130:171 */                   if ((var50 > -0.7D) && (var59 * var59 + var50 * var50 + var58 * var58 < 1.0D))
/* 131:    */                   {
/* 132:173 */                     Block var52 = p_151543_5_[var48];
/* 133:175 */                     if ((var52 == Blocks.netherrack) || (var52 == Blocks.dirt) || (var52 == Blocks.grass)) {
/* 134:177 */                       p_151543_5_[var48] = null;
/* 135:    */                     }
/* 136:    */                   }
/* 137:181 */                   var48--;
/* 138:    */                 }
/* 139:    */               }
/* 140:    */             }
/* 141:186 */             if (var53) {
/* 142:    */               break;
/* 143:    */             }
/* 144:    */           }
/* 145:    */         }
/* 146:    */       }
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected void func_151538_a(World p_151538_1_, int p_151538_2_, int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_)
/* 151:    */   {
/* 152:198 */     int var7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);
/* 153:200 */     if (this.rand.nextInt(5) != 0) {
/* 154:202 */       var7 = 0;
/* 155:    */     }
/* 156:205 */     for (int var8 = 0; var8 < var7; var8++)
/* 157:    */     {
/* 158:207 */       double var9 = p_151538_2_ * 16 + this.rand.nextInt(16);
/* 159:208 */       double var11 = this.rand.nextInt(128);
/* 160:209 */       double var13 = p_151538_3_ * 16 + this.rand.nextInt(16);
/* 161:210 */       int var15 = 1;
/* 162:212 */       if (this.rand.nextInt(4) == 0)
/* 163:    */       {
/* 164:214 */         func_151544_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var9, var11, var13);
/* 165:215 */         var15 += this.rand.nextInt(4);
/* 166:    */       }
/* 167:218 */       for (int var16 = 0; var16 < var15; var16++)
/* 168:    */       {
/* 169:220 */         float var17 = this.rand.nextFloat() * 3.141593F * 2.0F;
/* 170:221 */         float var18 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 171:222 */         float var19 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
/* 172:223 */         func_151543_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var9, var11, var13, var19 * 2.0F, var17, var18, 0, 0, 0.5D);
/* 173:    */       }
/* 174:    */     }
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.MapGenCavesHell
 * JD-Core Version:    0.7.0.1
 */