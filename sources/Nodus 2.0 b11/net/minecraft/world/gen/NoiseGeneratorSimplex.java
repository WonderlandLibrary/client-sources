/*   1:    */ package net.minecraft.world.gen;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public class NoiseGeneratorSimplex
/*   6:    */ {
/*   7:  7 */   private static int[][] field_151611_e = { { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 }, { 1, 0, 1 }, { -1, 0, 1 }, { 1, 0, -1 }, { -1, 0, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
/*   8:  8 */   public static final double field_151614_a = Math.sqrt(3.0D);
/*   9:    */   private int[] field_151608_f;
/*  10:    */   public double field_151612_b;
/*  11:    */   public double field_151613_c;
/*  12:    */   public double field_151610_d;
/*  13: 13 */   private static final double field_151609_g = 0.5D * (field_151614_a - 1.0D);
/*  14: 14 */   private static final double field_151615_h = (3.0D - field_151614_a) / 6.0D;
/*  15:    */   private static final String __OBFID = "CL_00000537";
/*  16:    */   
/*  17:    */   public NoiseGeneratorSimplex()
/*  18:    */   {
/*  19: 19 */     this(new Random());
/*  20:    */   }
/*  21:    */   
/*  22:    */   public NoiseGeneratorSimplex(Random p_i45471_1_)
/*  23:    */   {
/*  24: 24 */     this.field_151608_f = new int[512];
/*  25: 25 */     this.field_151612_b = (p_i45471_1_.nextDouble() * 256.0D);
/*  26: 26 */     this.field_151613_c = (p_i45471_1_.nextDouble() * 256.0D);
/*  27: 27 */     this.field_151610_d = (p_i45471_1_.nextDouble() * 256.0D);
/*  28: 30 */     for (int var2 = 0; var2 < 256; this.field_151608_f[var2] = (var2++)) {}
/*  29: 35 */     for (var2 = 0; var2 < 256; var2++)
/*  30:    */     {
/*  31: 37 */       int var3 = p_i45471_1_.nextInt(256 - var2) + var2;
/*  32: 38 */       int var4 = this.field_151608_f[var2];
/*  33: 39 */       this.field_151608_f[var2] = this.field_151608_f[var3];
/*  34: 40 */       this.field_151608_f[var3] = var4;
/*  35: 41 */       this.field_151608_f[(var2 + 256)] = this.field_151608_f[var2];
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   private static int func_151607_a(double p_151607_0_)
/*  40:    */   {
/*  41: 47 */     return p_151607_0_ > 0.0D ? (int)p_151607_0_ : (int)p_151607_0_ - 1;
/*  42:    */   }
/*  43:    */   
/*  44:    */   private static double func_151604_a(int[] p_151604_0_, double p_151604_1_, double p_151604_3_)
/*  45:    */   {
/*  46: 52 */     return p_151604_0_[0] * p_151604_1_ + p_151604_0_[1] * p_151604_3_;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public double func_151605_a(double p_151605_1_, double p_151605_3_)
/*  50:    */   {
/*  51: 57 */     double var11 = 0.5D * (field_151614_a - 1.0D);
/*  52: 58 */     double var13 = (p_151605_1_ + p_151605_3_) * var11;
/*  53: 59 */     int var15 = func_151607_a(p_151605_1_ + var13);
/*  54: 60 */     int var16 = func_151607_a(p_151605_3_ + var13);
/*  55: 61 */     double var17 = (3.0D - field_151614_a) / 6.0D;
/*  56: 62 */     double var19 = (var15 + var16) * var17;
/*  57: 63 */     double var21 = var15 - var19;
/*  58: 64 */     double var23 = var16 - var19;
/*  59: 65 */     double var25 = p_151605_1_ - var21;
/*  60: 66 */     double var27 = p_151605_3_ - var23;
/*  61:    */     byte var30;
/*  62:    */     byte var29;
/*  63:    */     byte var30;
/*  64: 70 */     if (var25 > var27)
/*  65:    */     {
/*  66: 72 */       byte var29 = 1;
/*  67: 73 */       var30 = 0;
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71: 77 */       var29 = 0;
/*  72: 78 */       var30 = 1;
/*  73:    */     }
/*  74: 81 */     double var31 = var25 - var29 + var17;
/*  75: 82 */     double var33 = var27 - var30 + var17;
/*  76: 83 */     double var35 = var25 - 1.0D + 2.0D * var17;
/*  77: 84 */     double var37 = var27 - 1.0D + 2.0D * var17;
/*  78: 85 */     int var39 = var15 & 0xFF;
/*  79: 86 */     int var40 = var16 & 0xFF;
/*  80: 87 */     int var41 = this.field_151608_f[(var39 + this.field_151608_f[var40])] % 12;
/*  81: 88 */     int var42 = this.field_151608_f[(var39 + var29 + this.field_151608_f[(var40 + var30)])] % 12;
/*  82: 89 */     int var43 = this.field_151608_f[(var39 + 1 + this.field_151608_f[(var40 + 1)])] % 12;
/*  83: 90 */     double var44 = 0.5D - var25 * var25 - var27 * var27;
/*  84:    */     double var5;
/*  85:    */     double var5;
/*  86: 93 */     if (var44 < 0.0D)
/*  87:    */     {
/*  88: 95 */       var5 = 0.0D;
/*  89:    */     }
/*  90:    */     else
/*  91:    */     {
/*  92: 99 */       var44 *= var44;
/*  93:100 */       var5 = var44 * var44 * func_151604_a(field_151611_e[var41], var25, var27);
/*  94:    */     }
/*  95:103 */     double var46 = 0.5D - var31 * var31 - var33 * var33;
/*  96:    */     double var7;
/*  97:    */     double var7;
/*  98:106 */     if (var46 < 0.0D)
/*  99:    */     {
/* 100:108 */       var7 = 0.0D;
/* 101:    */     }
/* 102:    */     else
/* 103:    */     {
/* 104:112 */       var46 *= var46;
/* 105:113 */       var7 = var46 * var46 * func_151604_a(field_151611_e[var42], var31, var33);
/* 106:    */     }
/* 107:116 */     double var48 = 0.5D - var35 * var35 - var37 * var37;
/* 108:    */     double var9;
/* 109:    */     double var9;
/* 110:119 */     if (var48 < 0.0D)
/* 111:    */     {
/* 112:121 */       var9 = 0.0D;
/* 113:    */     }
/* 114:    */     else
/* 115:    */     {
/* 116:125 */       var48 *= var48;
/* 117:126 */       var9 = var48 * var48 * func_151604_a(field_151611_e[var43], var35, var37);
/* 118:    */     }
/* 119:129 */     return 70.0D * (var5 + var7 + var9);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void func_151606_a(double[] p_151606_1_, double p_151606_2_, double p_151606_4_, int p_151606_6_, int p_151606_7_, double p_151606_8_, double p_151606_10_, double p_151606_12_)
/* 123:    */   {
/* 124:134 */     int var14 = 0;
/* 125:136 */     for (int var15 = 0; var15 < p_151606_7_; var15++)
/* 126:    */     {
/* 127:138 */       double var16 = (p_151606_4_ + var15) * p_151606_10_ + this.field_151613_c;
/* 128:140 */       for (int var18 = 0; var18 < p_151606_6_; var18++)
/* 129:    */       {
/* 130:142 */         double var19 = (p_151606_2_ + var18) * p_151606_8_ + this.field_151612_b;
/* 131:143 */         double var27 = (var19 + var16) * field_151609_g;
/* 132:144 */         int var29 = func_151607_a(var19 + var27);
/* 133:145 */         int var30 = func_151607_a(var16 + var27);
/* 134:146 */         double var31 = (var29 + var30) * field_151615_h;
/* 135:147 */         double var33 = var29 - var31;
/* 136:148 */         double var35 = var30 - var31;
/* 137:149 */         double var37 = var19 - var33;
/* 138:150 */         double var39 = var16 - var35;
/* 139:    */         byte var42;
/* 140:    */         byte var41;
/* 141:    */         byte var42;
/* 142:154 */         if (var37 > var39)
/* 143:    */         {
/* 144:156 */           byte var41 = 1;
/* 145:157 */           var42 = 0;
/* 146:    */         }
/* 147:    */         else
/* 148:    */         {
/* 149:161 */           var41 = 0;
/* 150:162 */           var42 = 1;
/* 151:    */         }
/* 152:165 */         double var43 = var37 - var41 + field_151615_h;
/* 153:166 */         double var45 = var39 - var42 + field_151615_h;
/* 154:167 */         double var47 = var37 - 1.0D + 2.0D * field_151615_h;
/* 155:168 */         double var49 = var39 - 1.0D + 2.0D * field_151615_h;
/* 156:169 */         int var51 = var29 & 0xFF;
/* 157:170 */         int var52 = var30 & 0xFF;
/* 158:171 */         int var53 = this.field_151608_f[(var51 + this.field_151608_f[var52])] % 12;
/* 159:172 */         int var54 = this.field_151608_f[(var51 + var41 + this.field_151608_f[(var52 + var42)])] % 12;
/* 160:173 */         int var55 = this.field_151608_f[(var51 + 1 + this.field_151608_f[(var52 + 1)])] % 12;
/* 161:174 */         double var56 = 0.5D - var37 * var37 - var39 * var39;
/* 162:    */         double var21;
/* 163:    */         double var21;
/* 164:177 */         if (var56 < 0.0D)
/* 165:    */         {
/* 166:179 */           var21 = 0.0D;
/* 167:    */         }
/* 168:    */         else
/* 169:    */         {
/* 170:183 */           var56 *= var56;
/* 171:184 */           var21 = var56 * var56 * func_151604_a(field_151611_e[var53], var37, var39);
/* 172:    */         }
/* 173:187 */         double var58 = 0.5D - var43 * var43 - var45 * var45;
/* 174:    */         double var23;
/* 175:    */         double var23;
/* 176:190 */         if (var58 < 0.0D)
/* 177:    */         {
/* 178:192 */           var23 = 0.0D;
/* 179:    */         }
/* 180:    */         else
/* 181:    */         {
/* 182:196 */           var58 *= var58;
/* 183:197 */           var23 = var58 * var58 * func_151604_a(field_151611_e[var54], var43, var45);
/* 184:    */         }
/* 185:200 */         double var60 = 0.5D - var47 * var47 - var49 * var49;
/* 186:    */         double var25;
/* 187:    */         double var25;
/* 188:203 */         if (var60 < 0.0D)
/* 189:    */         {
/* 190:205 */           var25 = 0.0D;
/* 191:    */         }
/* 192:    */         else
/* 193:    */         {
/* 194:209 */           var60 *= var60;
/* 195:210 */           var25 = var60 * var60 * func_151604_a(field_151611_e[var55], var47, var49);
/* 196:    */         }
/* 197:213 */         int var10001 = var14++;
/* 198:214 */         p_151606_1_[var10001] += 70.0D * (var21 + var23 + var25) * p_151606_12_;
/* 199:    */       }
/* 200:    */     }
/* 201:    */   }
/* 202:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.NoiseGeneratorSimplex
 * JD-Core Version:    0.7.0.1
 */