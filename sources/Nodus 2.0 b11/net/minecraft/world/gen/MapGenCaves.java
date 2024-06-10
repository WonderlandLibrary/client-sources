/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ import net.minecraft.block.Block;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ import net.minecraft.world.World;
/*   8:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   9:    */ 
/*  10:    */ public class MapGenCaves
/*  11:    */   extends MapGenBase
/*  12:    */ {
/*  13:    */   private static final String __OBFID = "CL_00000393";
/*  14:    */   
/*  15:    */   protected void func_151542_a(long p_151542_1_, int p_151542_3_, int p_151542_4_, Block[] p_151542_5_, double p_151542_6_, double p_151542_8_, double p_151542_10_)
/*  16:    */   {
/*  17: 15 */     func_151541_a(p_151542_1_, p_151542_3_, p_151542_4_, p_151542_5_, p_151542_6_, p_151542_8_, p_151542_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*  18:    */   }
/*  19:    */   
/*  20:    */   protected void func_151541_a(long p_151541_1_, int p_151541_3_, int p_151541_4_, Block[] p_151541_5_, double p_151541_6_, double p_151541_8_, double p_151541_10_, float p_151541_12_, float p_151541_13_, float p_151541_14_, int p_151541_15_, int p_151541_16_, double p_151541_17_)
/*  21:    */   {
/*  22: 20 */     double var19 = p_151541_3_ * 16 + 8;
/*  23: 21 */     double var21 = p_151541_4_ * 16 + 8;
/*  24: 22 */     float var23 = 0.0F;
/*  25: 23 */     float var24 = 0.0F;
/*  26: 24 */     Random var25 = new Random(p_151541_1_);
/*  27: 26 */     if (p_151541_16_ <= 0)
/*  28:    */     {
/*  29: 28 */       int var26 = this.range * 16 - 16;
/*  30: 29 */       p_151541_16_ = var26 - var25.nextInt(var26 / 4);
/*  31:    */     }
/*  32: 32 */     boolean var54 = false;
/*  33: 34 */     if (p_151541_15_ == -1)
/*  34:    */     {
/*  35: 36 */       p_151541_15_ = p_151541_16_ / 2;
/*  36: 37 */       var54 = true;
/*  37:    */     }
/*  38: 40 */     int var27 = var25.nextInt(p_151541_16_ / 2) + p_151541_16_ / 4;
/*  39: 42 */     for (boolean var28 = var25.nextInt(6) == 0; p_151541_15_ < p_151541_16_; p_151541_15_++)
/*  40:    */     {
/*  41: 44 */       double var29 = 1.5D + MathHelper.sin(p_151541_15_ * 3.141593F / p_151541_16_) * p_151541_12_ * 1.0F;
/*  42: 45 */       double var31 = var29 * p_151541_17_;
/*  43: 46 */       float var33 = MathHelper.cos(p_151541_14_);
/*  44: 47 */       float var34 = MathHelper.sin(p_151541_14_);
/*  45: 48 */       p_151541_6_ += MathHelper.cos(p_151541_13_) * var33;
/*  46: 49 */       p_151541_8_ += var34;
/*  47: 50 */       p_151541_10_ += MathHelper.sin(p_151541_13_) * var33;
/*  48: 52 */       if (var28) {
/*  49: 54 */         p_151541_14_ *= 0.92F;
/*  50:    */       } else {
/*  51: 58 */         p_151541_14_ *= 0.7F;
/*  52:    */       }
/*  53: 61 */       p_151541_14_ += var24 * 0.1F;
/*  54: 62 */       p_151541_13_ += var23 * 0.1F;
/*  55: 63 */       var24 *= 0.9F;
/*  56: 64 */       var23 *= 0.75F;
/*  57: 65 */       var24 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 2.0F;
/*  58: 66 */       var23 += (var25.nextFloat() - var25.nextFloat()) * var25.nextFloat() * 4.0F;
/*  59: 68 */       if ((!var54) && (p_151541_15_ == var27) && (p_151541_12_ > 1.0F) && (p_151541_16_ > 0))
/*  60:    */       {
/*  61: 70 */         func_151541_a(var25.nextLong(), p_151541_3_, p_151541_4_, p_151541_5_, p_151541_6_, p_151541_8_, p_151541_10_, var25.nextFloat() * 0.5F + 0.5F, p_151541_13_ - 1.570796F, p_151541_14_ / 3.0F, p_151541_15_, p_151541_16_, 1.0D);
/*  62: 71 */         func_151541_a(var25.nextLong(), p_151541_3_, p_151541_4_, p_151541_5_, p_151541_6_, p_151541_8_, p_151541_10_, var25.nextFloat() * 0.5F + 0.5F, p_151541_13_ + 1.570796F, p_151541_14_ / 3.0F, p_151541_15_, p_151541_16_, 1.0D);
/*  63: 72 */         return;
/*  64:    */       }
/*  65: 75 */       if ((var54) || (var25.nextInt(4) != 0))
/*  66:    */       {
/*  67: 77 */         double var35 = p_151541_6_ - var19;
/*  68: 78 */         double var37 = p_151541_10_ - var21;
/*  69: 79 */         double var39 = p_151541_16_ - p_151541_15_;
/*  70: 80 */         double var41 = p_151541_12_ + 2.0F + 16.0F;
/*  71: 82 */         if (var35 * var35 + var37 * var37 - var39 * var39 > var41 * var41) {
/*  72: 84 */           return;
/*  73:    */         }
/*  74: 87 */         if ((p_151541_6_ >= var19 - 16.0D - var29 * 2.0D) && (p_151541_10_ >= var21 - 16.0D - var29 * 2.0D) && (p_151541_6_ <= var19 + 16.0D + var29 * 2.0D) && (p_151541_10_ <= var21 + 16.0D + var29 * 2.0D))
/*  75:    */         {
/*  76: 89 */           int var55 = MathHelper.floor_double(p_151541_6_ - var29) - p_151541_3_ * 16 - 1;
/*  77: 90 */           int var36 = MathHelper.floor_double(p_151541_6_ + var29) - p_151541_3_ * 16 + 1;
/*  78: 91 */           int var57 = MathHelper.floor_double(p_151541_8_ - var31) - 1;
/*  79: 92 */           int var38 = MathHelper.floor_double(p_151541_8_ + var31) + 1;
/*  80: 93 */           int var56 = MathHelper.floor_double(p_151541_10_ - var29) - p_151541_4_ * 16 - 1;
/*  81: 94 */           int var40 = MathHelper.floor_double(p_151541_10_ + var29) - p_151541_4_ * 16 + 1;
/*  82: 96 */           if (var55 < 0) {
/*  83: 98 */             var55 = 0;
/*  84:    */           }
/*  85:101 */           if (var36 > 16) {
/*  86:103 */             var36 = 16;
/*  87:    */           }
/*  88:106 */           if (var57 < 1) {
/*  89:108 */             var57 = 1;
/*  90:    */           }
/*  91:111 */           if (var38 > 248) {
/*  92:113 */             var38 = 248;
/*  93:    */           }
/*  94:116 */           if (var56 < 0) {
/*  95:118 */             var56 = 0;
/*  96:    */           }
/*  97:121 */           if (var40 > 16) {
/*  98:123 */             var40 = 16;
/*  99:    */           }
/* 100:126 */           boolean var58 = false;
/* 101:130 */           for (int var42 = var55; (!var58) && (var42 < var36); var42++) {
/* 102:132 */             for (int var43 = var56; (!var58) && (var43 < var40); var43++) {
/* 103:134 */               for (int var44 = var38 + 1; (!var58) && (var44 >= var57 - 1); var44--)
/* 104:    */               {
/* 105:136 */                 int var45 = (var42 * 16 + var43) * 256 + var44;
/* 106:138 */                 if ((var44 >= 0) && (var44 < 256))
/* 107:    */                 {
/* 108:140 */                   Block var46 = p_151541_5_[var45];
/* 109:142 */                   if ((var46 == Blocks.flowing_water) || (var46 == Blocks.water)) {
/* 110:144 */                     var58 = true;
/* 111:    */                   }
/* 112:147 */                   if ((var44 != var57 - 1) && (var42 != var55) && (var42 != var36 - 1) && (var43 != var56) && (var43 != var40 - 1)) {
/* 113:149 */                     var44 = var57;
/* 114:    */                   }
/* 115:    */                 }
/* 116:    */               }
/* 117:    */             }
/* 118:    */           }
/* 119:156 */           if (!var58)
/* 120:    */           {
/* 121:158 */             for (var42 = var55; var42 < var36; var42++)
/* 122:    */             {
/* 123:160 */               double var60 = (var42 + p_151541_3_ * 16 + 0.5D - p_151541_6_) / var29;
/* 124:162 */               for (int var45 = var56; var45 < var40; var45++)
/* 125:    */               {
/* 126:164 */                 double var59 = (var45 + p_151541_4_ * 16 + 0.5D - p_151541_10_) / var29;
/* 127:165 */                 int var48 = (var42 * 16 + var45) * 256 + var38;
/* 128:166 */                 boolean var49 = false;
/* 129:168 */                 if (var60 * var60 + var59 * var59 < 1.0D) {
/* 130:170 */                   for (int var50 = var38 - 1; var50 >= var57; var50--)
/* 131:    */                   {
/* 132:172 */                     double var51 = (var50 + 0.5D - p_151541_8_) / var31;
/* 133:174 */                     if ((var51 > -0.7D) && (var60 * var60 + var51 * var51 + var59 * var59 < 1.0D))
/* 134:    */                     {
/* 135:176 */                       Block var53 = p_151541_5_[var48];
/* 136:178 */                       if (var53 == Blocks.grass) {
/* 137:180 */                         var49 = true;
/* 138:    */                       }
/* 139:183 */                       if ((var53 == Blocks.stone) || (var53 == Blocks.dirt) || (var53 == Blocks.grass)) {
/* 140:185 */                         if (var50 < 10)
/* 141:    */                         {
/* 142:187 */                           p_151541_5_[var48] = Blocks.lava;
/* 143:    */                         }
/* 144:    */                         else
/* 145:    */                         {
/* 146:191 */                           p_151541_5_[var48] = null;
/* 147:193 */                           if ((var49) && (p_151541_5_[(var48 - 1)] == Blocks.dirt)) {
/* 148:195 */                             p_151541_5_[(var48 - 1)] = this.worldObj.getBiomeGenForCoords(var42 + p_151541_3_ * 16, var45 + p_151541_4_ * 16).topBlock;
/* 149:    */                           }
/* 150:    */                         }
/* 151:    */                       }
/* 152:    */                     }
/* 153:201 */                     var48--;
/* 154:    */                   }
/* 155:    */                 }
/* 156:    */               }
/* 157:    */             }
/* 158:207 */             if (var54) {
/* 159:    */               break;
/* 160:    */             }
/* 161:    */           }
/* 162:    */         }
/* 163:    */       }
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected void func_151538_a(World p_151538_1_, int p_151538_2_, int p_151538_3_, int p_151538_4_, int p_151538_5_, Block[] p_151538_6_)
/* 168:    */   {
/* 169:219 */     int var7 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);
/* 170:221 */     if (this.rand.nextInt(7) != 0) {
/* 171:223 */       var7 = 0;
/* 172:    */     }
/* 173:226 */     for (int var8 = 0; var8 < var7; var8++)
/* 174:    */     {
/* 175:228 */       double var9 = p_151538_2_ * 16 + this.rand.nextInt(16);
/* 176:229 */       double var11 = this.rand.nextInt(this.rand.nextInt(120) + 8);
/* 177:230 */       double var13 = p_151538_3_ * 16 + this.rand.nextInt(16);
/* 178:231 */       int var15 = 1;
/* 179:233 */       if (this.rand.nextInt(4) == 0)
/* 180:    */       {
/* 181:235 */         func_151542_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var9, var11, var13);
/* 182:236 */         var15 += this.rand.nextInt(4);
/* 183:    */       }
/* 184:239 */       for (int var16 = 0; var16 < var15; var16++)
/* 185:    */       {
/* 186:241 */         float var17 = this.rand.nextFloat() * 3.141593F * 2.0F;
/* 187:242 */         float var18 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 188:243 */         float var19 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
/* 189:245 */         if (this.rand.nextInt(10) == 0) {
/* 190:247 */           var19 *= (this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F);
/* 191:    */         }
/* 192:250 */         func_151541_a(this.rand.nextLong(), p_151538_4_, p_151538_5_, p_151538_6_, var9, var11, var13, var19, var17, var18, 0, 0, 1.0D);
/* 193:    */       }
/* 194:    */     }
/* 195:    */   }
/* 196:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.MapGenCaves
 * JD-Core Version:    0.7.0.1
 */