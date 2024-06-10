/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public class MathHelper
/*   6:    */ {
/*   7:    */   private static final int SIN_BITS = 12;
/*   8:    */   private static final int SIN_MASK = 4095;
/*   9:    */   private static final int SIN_COUNT = 4096;
/*  10:    */   public static final float PI = 3.141593F;
/*  11:    */   public static final float PI2 = 6.283186F;
/*  12:    */   public static final float PId2 = 1.570796F;
/*  13:    */   private static final float radFull = 6.283186F;
/*  14:    */   private static final float degFull = 360.0F;
/*  15:    */   private static final float radToIndex = 651.89862F;
/*  16:    */   private static final float degToIndex = 11.377778F;
/*  17:    */   public static final float deg2Rad = 0.01745329F;
/*  18: 18 */   private static final float[] SIN_TABLE_FAST = new float[4096];
/*  19: 19 */   public static boolean fastMath = false;
/*  20: 24 */   private static float[] SIN_TABLE = new float[65536];
/*  21:    */   private static final int[] multiplyDeBruijnBitPosition;
/*  22:    */   private static final String __OBFID = "CL_00001496";
/*  23:    */   
/*  24:    */   public static final float sin(float par0)
/*  25:    */   {
/*  26: 41 */     return fastMath ? SIN_TABLE_FAST[((int)(par0 * 651.89862F) & 0xFFF)] : SIN_TABLE[((int)(par0 * 10430.378F) & 0xFFFF)];
/*  27:    */   }
/*  28:    */   
/*  29:    */   public static final float cos(float par0)
/*  30:    */   {
/*  31: 49 */     return fastMath ? SIN_TABLE_FAST[((int)((par0 + 1.570796F) * 651.89862F) & 0xFFF)] : SIN_TABLE[((int)(par0 * 10430.378F + 16384.0F) & 0xFFFF)];
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static final float sqrt_float(float par0)
/*  35:    */   {
/*  36: 54 */     return (float)Math.sqrt(par0);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static final float sqrt_double(double par0)
/*  40:    */   {
/*  41: 59 */     return (float)Math.sqrt(par0);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static int floor_float(float par0)
/*  45:    */   {
/*  46: 67 */     int var1 = (int)par0;
/*  47: 68 */     return par0 < var1 ? var1 - 1 : var1;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static int truncateDoubleToInt(double par0)
/*  51:    */   {
/*  52: 76 */     return (int)(par0 + 1024.0D) - 1024;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static int floor_double(double par0)
/*  56:    */   {
/*  57: 84 */     int var2 = (int)par0;
/*  58: 85 */     return par0 < var2 ? var2 - 1 : var2;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static long floor_double_long(double par0)
/*  62:    */   {
/*  63: 93 */     long var2 = par0;
/*  64: 94 */     return par0 < var2 ? var2 - 1L : var2;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static float abs(float par0)
/*  68:    */   {
/*  69: 99 */     return par0 >= 0.0F ? par0 : -par0;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static int abs_int(int par0)
/*  73:    */   {
/*  74:107 */     return par0 >= 0 ? par0 : -par0;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static int ceiling_float_int(float par0)
/*  78:    */   {
/*  79:112 */     int var1 = (int)par0;
/*  80:113 */     return par0 > var1 ? var1 + 1 : var1;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static int ceiling_double_int(double par0)
/*  84:    */   {
/*  85:118 */     int var2 = (int)par0;
/*  86:119 */     return par0 > var2 ? var2 + 1 : var2;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static int clamp_int(int par0, int par1, int par2)
/*  90:    */   {
/*  91:128 */     return par0 > par2 ? par2 : par0 < par1 ? par1 : par0;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static float clamp_float(float par0, float par1, float par2)
/*  95:    */   {
/*  96:137 */     return par0 > par2 ? par2 : par0 < par1 ? par1 : par0;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static double clamp_double(double p_151237_0_, double p_151237_2_, double p_151237_4_)
/* 100:    */   {
/* 101:142 */     return p_151237_0_ > p_151237_4_ ? p_151237_4_ : p_151237_0_ < p_151237_2_ ? p_151237_2_ : p_151237_0_;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static double denormalizeClamp(double p_151238_0_, double p_151238_2_, double p_151238_4_)
/* 105:    */   {
/* 106:147 */     return p_151238_4_ > 1.0D ? p_151238_2_ : p_151238_4_ < 0.0D ? p_151238_0_ : p_151238_0_ + (p_151238_2_ - p_151238_0_) * p_151238_4_;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static double abs_max(double par0, double par2)
/* 110:    */   {
/* 111:155 */     if (par0 < 0.0D) {
/* 112:157 */       par0 = -par0;
/* 113:    */     }
/* 114:160 */     if (par2 < 0.0D) {
/* 115:162 */       par2 = -par2;
/* 116:    */     }
/* 117:165 */     return par0 > par2 ? par0 : par2;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static int bucketInt(int par0, int par1)
/* 121:    */   {
/* 122:173 */     return par0 < 0 ? -((-par0 - 1) / par1) - 1 : par0 / par1;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static boolean stringNullOrLengthZero(String par0Str)
/* 126:    */   {
/* 127:181 */     return (par0Str == null) || (par0Str.length() == 0);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static int getRandomIntegerInRange(Random par0Random, int par1, int par2)
/* 131:    */   {
/* 132:186 */     return par1 >= par2 ? par1 : par0Random.nextInt(par2 - par1 + 1) + par1;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static float randomFloatClamp(Random p_151240_0_, float p_151240_1_, float p_151240_2_)
/* 136:    */   {
/* 137:191 */     return p_151240_1_ >= p_151240_2_ ? p_151240_1_ : p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static double getRandomDoubleInRange(Random par0Random, double par1, double par3)
/* 141:    */   {
/* 142:196 */     return par1 >= par3 ? par1 : par0Random.nextDouble() * (par3 - par1) + par1;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static double average(long[] par0ArrayOfLong)
/* 146:    */   {
/* 147:201 */     long var1 = 0L;
/* 148:202 */     long[] var3 = par0ArrayOfLong;
/* 149:203 */     int var4 = par0ArrayOfLong.length;
/* 150:205 */     for (int var5 = 0; var5 < var4; var5++)
/* 151:    */     {
/* 152:207 */       long var6 = var3[var5];
/* 153:208 */       var1 += var6;
/* 154:    */     }
/* 155:211 */     return var1 / par0ArrayOfLong.length;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static float wrapAngleTo180_float(float par0)
/* 159:    */   {
/* 160:219 */     par0 %= 360.0F;
/* 161:221 */     if (par0 >= 180.0F) {
/* 162:223 */       par0 -= 360.0F;
/* 163:    */     }
/* 164:226 */     if (par0 < -180.0F) {
/* 165:228 */       par0 += 360.0F;
/* 166:    */     }
/* 167:231 */     return par0;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static double wrapAngleTo180_double(double par0)
/* 171:    */   {
/* 172:239 */     par0 %= 360.0D;
/* 173:241 */     if (par0 >= 180.0D) {
/* 174:243 */       par0 -= 360.0D;
/* 175:    */     }
/* 176:246 */     if (par0 < -180.0D) {
/* 177:248 */       par0 += 360.0D;
/* 178:    */     }
/* 179:251 */     return par0;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public static int parseIntWithDefault(String par0Str, int par1)
/* 183:    */   {
/* 184:259 */     int var2 = par1;
/* 185:    */     try
/* 186:    */     {
/* 187:263 */       var2 = Integer.parseInt(par0Str);
/* 188:    */     }
/* 189:    */     catch (Throwable localThrowable) {}
/* 190:270 */     return var2;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static int parseIntWithDefaultAndMax(String par0Str, int par1, int par2)
/* 194:    */   {
/* 195:278 */     int var3 = par1;
/* 196:    */     try
/* 197:    */     {
/* 198:282 */       var3 = Integer.parseInt(par0Str);
/* 199:    */     }
/* 200:    */     catch (Throwable localThrowable) {}
/* 201:289 */     if (var3 < par2) {
/* 202:291 */       var3 = par2;
/* 203:    */     }
/* 204:294 */     return var3;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public static double parseDoubleWithDefault(String par0Str, double par1)
/* 208:    */   {
/* 209:302 */     double var3 = par1;
/* 210:    */     try
/* 211:    */     {
/* 212:306 */       var3 = Double.parseDouble(par0Str);
/* 213:    */     }
/* 214:    */     catch (Throwable localThrowable) {}
/* 215:313 */     return var3;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static double parseDoubleWithDefaultAndMax(String par0Str, double par1, double par3)
/* 219:    */   {
/* 220:318 */     double var5 = par1;
/* 221:    */     try
/* 222:    */     {
/* 223:322 */       var5 = Double.parseDouble(par0Str);
/* 224:    */     }
/* 225:    */     catch (Throwable localThrowable) {}
/* 226:329 */     if (var5 < par3) {
/* 227:331 */       var5 = par3;
/* 228:    */     }
/* 229:334 */     return var5;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static int roundUpToPowerOfTwo(int p_151236_0_)
/* 233:    */   {
/* 234:342 */     int var1 = p_151236_0_ - 1;
/* 235:343 */     var1 |= var1 >> 1;
/* 236:344 */     var1 |= var1 >> 2;
/* 237:345 */     var1 |= var1 >> 4;
/* 238:346 */     var1 |= var1 >> 8;
/* 239:347 */     var1 |= var1 >> 16;
/* 240:348 */     return var1 + 1;
/* 241:    */   }
/* 242:    */   
/* 243:    */   private static boolean isPowerOfTwo(int p_151235_0_)
/* 244:    */   {
/* 245:356 */     return (p_151235_0_ != 0) && ((p_151235_0_ & p_151235_0_ - 1) == 0);
/* 246:    */   }
/* 247:    */   
/* 248:    */   private static int calculateLogBaseTwoDeBruijn(int p_151241_0_)
/* 249:    */   {
/* 250:366 */     p_151241_0_ = isPowerOfTwo(p_151241_0_) ? p_151241_0_ : roundUpToPowerOfTwo(p_151241_0_);
/* 251:367 */     return multiplyDeBruijnBitPosition[((int)(p_151241_0_ * 125613361L >> 27) & 0x1F)];
/* 252:    */   }
/* 253:    */   
/* 254:    */   public static int calculateLogBaseTwo(int p_151239_0_)
/* 255:    */   {
/* 256:376 */     return calculateLogBaseTwoDeBruijn(p_151239_0_) - (isPowerOfTwo(p_151239_0_) ? 0 : 1);
/* 257:    */   }
/* 258:    */   
/* 259:    */   static
/* 260:    */   {
/* 261:383 */     for (int i = 0; i < 65536; i++) {
/* 262:385 */       SIN_TABLE[i] = ((float)Math.sin(i * 3.141592653589793D * 2.0D / 65536.0D));
/* 263:    */     }
/* 264:388 */     multiplyDeBruijnBitPosition = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
/* 265:390 */     for (i = 0; i < 4096; i++) {
/* 266:392 */       SIN_TABLE_FAST[i] = ((float)Math.sin((i + 0.5F) / 4096.0F * 6.283186F));
/* 267:    */     }
/* 268:395 */     for (i = 0; i < 360; i += 90) {
/* 269:397 */       SIN_TABLE_FAST[((int)(i * 11.377778F) & 0xFFF)] = ((float)Math.sin(i * 0.01745329F));
/* 270:    */     }
/* 271:    */   }
/* 272:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.MathHelper
 * JD-Core Version:    0.7.0.1
 */