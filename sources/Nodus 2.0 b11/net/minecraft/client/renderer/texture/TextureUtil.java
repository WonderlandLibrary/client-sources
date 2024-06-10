/*   1:    */ package net.minecraft.client.renderer.texture;
/*   2:    */ 
/*   3:    */ import java.awt.image.BufferedImage;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.nio.Buffer;
/*   6:    */ import java.nio.IntBuffer;
/*   7:    */ import javax.imageio.ImageIO;
/*   8:    */ import net.minecraft.client.Minecraft;
/*   9:    */ import net.minecraft.client.renderer.GLAllocation;
/*  10:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  11:    */ import net.minecraft.client.resources.IResource;
/*  12:    */ import net.minecraft.client.resources.IResourceManager;
/*  13:    */ import net.minecraft.client.settings.GameSettings;
/*  14:    */ import net.minecraft.util.ResourceLocation;
/*  15:    */ import org.apache.logging.log4j.LogManager;
/*  16:    */ import org.apache.logging.log4j.Logger;
/*  17:    */ import org.lwjgl.opengl.GL11;
/*  18:    */ 
/*  19:    */ public class TextureUtil
/*  20:    */ {
/*  21: 20 */   private static final Logger logger = ;
/*  22: 21 */   private static final IntBuffer dataBuffer = GLAllocation.createDirectIntBuffer(4194304);
/*  23: 22 */   public static final DynamicTexture missingTexture = new DynamicTexture(16, 16);
/*  24: 23 */   public static final int[] missingTextureData = missingTexture.getTextureData();
/*  25: 24 */   private static int field_147958_e = -1;
/*  26: 25 */   private static int field_147956_f = -1;
/*  27:    */   
/*  28:    */   public static int glGenTextures()
/*  29:    */   {
/*  30: 31 */     return GL11.glGenTextures();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static void deleteTexture(int p_147942_0_)
/*  34:    */   {
/*  35: 36 */     GL11.glDeleteTextures(p_147942_0_);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static int uploadTextureImage(int par0, BufferedImage par1BufferedImage)
/*  39:    */   {
/*  40: 41 */     return uploadTextureImageAllocate(par0, par1BufferedImage, false, false);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static void uploadTexture(int par0, int[] par1ArrayOfInteger, int par2, int par3)
/*  44:    */   {
/*  45: 46 */     bindTexture(par0);
/*  46: 47 */     func_147947_a(0, par1ArrayOfInteger, par2, par3, 0, 0, false, false, false);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static int[][] func_147949_a(int p_147949_0_, int p_147949_1_, int[][] p_147949_2_)
/*  50:    */   {
/*  51: 52 */     int[][] var3 = new int[p_147949_0_ + 1][];
/*  52: 53 */     var3[0] = p_147949_2_[0];
/*  53: 55 */     if (p_147949_0_ > 0)
/*  54:    */     {
/*  55: 57 */       boolean var4 = false;
/*  56: 60 */       for (int var5 = 0; var5 < p_147949_2_.length; var5++) {
/*  57: 62 */         if (p_147949_2_[0][var5] >> 24 == 0)
/*  58:    */         {
/*  59: 64 */           var4 = true;
/*  60: 65 */           break;
/*  61:    */         }
/*  62:    */       }
/*  63: 69 */       for (var5 = 1; var5 <= p_147949_0_; var5++) {
/*  64: 71 */         if (p_147949_2_[var5] != null)
/*  65:    */         {
/*  66: 73 */           var3[var5] = p_147949_2_[var5];
/*  67:    */         }
/*  68:    */         else
/*  69:    */         {
/*  70: 77 */           int[] var6 = var3[(var5 - 1)];
/*  71: 78 */           int[] var7 = new int[var6.length >> 2];
/*  72: 79 */           int var8 = p_147949_1_ >> var5;
/*  73: 80 */           int var9 = var7.length / var8;
/*  74: 81 */           int var10 = var8 << 1;
/*  75: 83 */           for (int var11 = 0; var11 < var8; var11++) {
/*  76: 85 */             for (int var12 = 0; var12 < var9; var12++)
/*  77:    */             {
/*  78: 87 */               int var13 = 2 * (var11 + var12 * var10);
/*  79: 88 */               var7[(var11 + var12 * var8)] = func_147943_a(var6[(var13 + 0)], var6[(var13 + 1)], var6[(var13 + 0 + var10)], var6[(var13 + 1 + var10)], var4);
/*  80:    */             }
/*  81:    */           }
/*  82: 92 */           var3[var5] = var7;
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86: 97 */     return var3;
/*  87:    */   }
/*  88:    */   
/*  89:    */   private static int func_147943_a(int p_147943_0_, int p_147943_1_, int p_147943_2_, int p_147943_3_, boolean p_147943_4_)
/*  90:    */   {
/*  91:102 */     if (!p_147943_4_)
/*  92:    */     {
/*  93:104 */       int var13 = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 24);
/*  94:105 */       int var14 = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 16);
/*  95:106 */       int var15 = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 8);
/*  96:107 */       int var16 = func_147944_a(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_, 0);
/*  97:108 */       return var13 << 24 | var14 << 16 | var15 << 8 | var16;
/*  98:    */     }
/*  99:112 */     field_147957_g[0] = p_147943_0_;
/* 100:113 */     field_147957_g[1] = p_147943_1_;
/* 101:114 */     field_147957_g[2] = p_147943_2_;
/* 102:115 */     field_147957_g[3] = p_147943_3_;
/* 103:116 */     float var5 = 0.0F;
/* 104:117 */     float var6 = 0.0F;
/* 105:118 */     float var7 = 0.0F;
/* 106:119 */     float var8 = 0.0F;
/* 107:122 */     for (int var9 = 0; var9 < 4; var9++) {
/* 108:124 */       if (field_147957_g[var9] >> 24 != 0)
/* 109:    */       {
/* 110:126 */         var5 += (float)Math.pow((field_147957_g[var9] >> 24 & 0xFF) / 255.0F, 2.2D);
/* 111:127 */         var6 += (float)Math.pow((field_147957_g[var9] >> 16 & 0xFF) / 255.0F, 2.2D);
/* 112:128 */         var7 += (float)Math.pow((field_147957_g[var9] >> 8 & 0xFF) / 255.0F, 2.2D);
/* 113:129 */         var8 += (float)Math.pow((field_147957_g[var9] >> 0 & 0xFF) / 255.0F, 2.2D);
/* 114:    */       }
/* 115:    */     }
/* 116:133 */     var5 /= 4.0F;
/* 117:134 */     var6 /= 4.0F;
/* 118:135 */     var7 /= 4.0F;
/* 119:136 */     var8 /= 4.0F;
/* 120:137 */     var9 = (int)(Math.pow(var5, 0.4545454545454545D) * 255.0D);
/* 121:138 */     int var10 = (int)(Math.pow(var6, 0.4545454545454545D) * 255.0D);
/* 122:139 */     int var11 = (int)(Math.pow(var7, 0.4545454545454545D) * 255.0D);
/* 123:140 */     int var12 = (int)(Math.pow(var8, 0.4545454545454545D) * 255.0D);
/* 124:142 */     if (var9 < 96) {
/* 125:144 */       var9 = 0;
/* 126:    */     }
/* 127:147 */     return var9 << 24 | var10 << 16 | var11 << 8 | var12;
/* 128:    */   }
/* 129:    */   
/* 130:    */   private static int func_147944_a(int p_147944_0_, int p_147944_1_, int p_147944_2_, int p_147944_3_, int p_147944_4_)
/* 131:    */   {
/* 132:153 */     float var5 = (float)Math.pow((p_147944_0_ >> p_147944_4_ & 0xFF) / 255.0F, 2.2D);
/* 133:154 */     float var6 = (float)Math.pow((p_147944_1_ >> p_147944_4_ & 0xFF) / 255.0F, 2.2D);
/* 134:155 */     float var7 = (float)Math.pow((p_147944_2_ >> p_147944_4_ & 0xFF) / 255.0F, 2.2D);
/* 135:156 */     float var8 = (float)Math.pow((p_147944_3_ >> p_147944_4_ & 0xFF) / 255.0F, 2.2D);
/* 136:157 */     float var9 = (float)Math.pow((var5 + var6 + var7 + var8) * 0.25D, 0.4545454545454545D);
/* 137:158 */     return (int)(var9 * 255.0D);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static void func_147955_a(int[][] p_147955_0_, int p_147955_1_, int p_147955_2_, int p_147955_3_, int p_147955_4_, boolean p_147955_5_, boolean p_147955_6_)
/* 141:    */   {
/* 142:163 */     for (int var7 = 0; var7 < p_147955_0_.length; var7++)
/* 143:    */     {
/* 144:165 */       int[] var8 = p_147955_0_[var7];
/* 145:166 */       func_147947_a(var7, var8, p_147955_1_ >> var7, p_147955_2_ >> var7, p_147955_3_ >> var7, p_147955_4_ >> var7, p_147955_5_, p_147955_6_, p_147955_0_.length > 1);
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   private static void func_147947_a(int p_147947_0_, int[] p_147947_1_, int p_147947_2_, int p_147947_3_, int p_147947_4_, int p_147947_5_, boolean p_147947_6_, boolean p_147947_7_, boolean p_147947_8_)
/* 150:    */   {
/* 151:172 */     int var9 = 4194304 / p_147947_2_;
/* 152:173 */     func_147954_b(p_147947_6_, p_147947_8_);
/* 153:174 */     setTextureClamped(p_147947_7_);
/* 154:    */     int var12;
/* 155:177 */     for (int var10 = 0; var10 < p_147947_2_ * p_147947_3_; var10 += p_147947_2_ * var12)
/* 156:    */     {
/* 157:179 */       int var11 = var10 / p_147947_2_;
/* 158:180 */       var12 = Math.min(var9, p_147947_3_ - var11);
/* 159:181 */       int var13 = p_147947_2_ * var12;
/* 160:182 */       copyToBufferPos(p_147947_1_, var10, var13);
/* 161:183 */       GL11.glTexSubImage2D(3553, p_147947_0_, p_147947_4_, p_147947_5_ + var11, p_147947_2_, var12, 32993, 33639, dataBuffer);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static int uploadTextureImageAllocate(int par0, BufferedImage par1BufferedImage, boolean par2, boolean par3)
/* 166:    */   {
/* 167:189 */     allocateTexture(par0, par1BufferedImage.getWidth(), par1BufferedImage.getHeight());
/* 168:190 */     return uploadTextureImageSub(par0, par1BufferedImage, 0, 0, par2, par3);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static void allocateTexture(int par0, int par1, int par2)
/* 172:    */   {
/* 173:195 */     func_147946_a(par0, 0, par1, par2, 1.0F);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static void func_147946_a(int p_147946_0_, int p_147946_1_, int p_147946_2_, int p_147946_3_, float p_147946_4_)
/* 177:    */   {
/* 178:200 */     deleteTexture(p_147946_0_);
/* 179:201 */     bindTexture(p_147946_0_);
/* 180:203 */     if (OpenGlHelper.anisotropicFilteringSupported) {
/* 181:205 */       GL11.glTexParameterf(3553, 34046, p_147946_4_);
/* 182:    */     }
/* 183:208 */     if (p_147946_1_ > 0)
/* 184:    */     {
/* 185:210 */       GL11.glTexParameteri(3553, 33085, p_147946_1_);
/* 186:211 */       GL11.glTexParameterf(3553, 33082, 0.0F);
/* 187:212 */       GL11.glTexParameterf(3553, 33083, p_147946_1_);
/* 188:213 */       GL11.glTexParameterf(3553, 34049, 0.0F);
/* 189:    */     }
/* 190:216 */     for (int var5 = 0; var5 <= p_147946_1_; var5++) {
/* 191:218 */       GL11.glTexImage2D(3553, var5, 6408, p_147946_2_ >> var5, p_147946_3_ >> var5, 0, 32993, 33639, null);
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static int uploadTextureImageSub(int par0, BufferedImage par1BufferedImage, int par2, int par3, boolean par4, boolean par5)
/* 196:    */   {
/* 197:224 */     bindTexture(par0);
/* 198:225 */     uploadTextureImageSubImpl(par1BufferedImage, par2, par3, par4, par5);
/* 199:226 */     return par0;
/* 200:    */   }
/* 201:    */   
/* 202:    */   private static void uploadTextureImageSubImpl(BufferedImage par0BufferedImage, int par1, int par2, boolean par3, boolean par4)
/* 203:    */   {
/* 204:231 */     int var5 = par0BufferedImage.getWidth();
/* 205:232 */     int var6 = par0BufferedImage.getHeight();
/* 206:233 */     int var7 = 4194304 / var5;
/* 207:234 */     int[] var8 = new int[var7 * var5];
/* 208:235 */     func_147951_b(par3);
/* 209:236 */     setTextureClamped(par4);
/* 210:238 */     for (int var9 = 0; var9 < var5 * var6; var9 += var5 * var7)
/* 211:    */     {
/* 212:240 */       int var10 = var9 / var5;
/* 213:241 */       int var11 = Math.min(var7, var6 - var10);
/* 214:242 */       int var12 = var5 * var11;
/* 215:243 */       par0BufferedImage.getRGB(0, var10, var5, var11, var8, 0, var5);
/* 216:244 */       copyToBuffer(var8, var12);
/* 217:245 */       GL11.glTexSubImage2D(3553, 0, par1, par2 + var10, var5, var11, 32993, 33639, dataBuffer);
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   private static void setTextureClamped(boolean par0)
/* 222:    */   {
/* 223:251 */     if (par0)
/* 224:    */     {
/* 225:253 */       GL11.glTexParameteri(3553, 10242, 10496);
/* 226:254 */       GL11.glTexParameteri(3553, 10243, 10496);
/* 227:    */     }
/* 228:    */     else
/* 229:    */     {
/* 230:258 */       GL11.glTexParameteri(3553, 10242, 10497);
/* 231:259 */       GL11.glTexParameteri(3553, 10243, 10497);
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   private static void func_147951_b(boolean p_147951_0_)
/* 236:    */   {
/* 237:265 */     func_147954_b(p_147951_0_, false);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public static void func_147950_a(boolean p_147950_0_, boolean p_147950_1_)
/* 241:    */   {
/* 242:270 */     field_147958_e = GL11.glGetTexParameteri(3553, 10241);
/* 243:271 */     field_147956_f = GL11.glGetTexParameteri(3553, 10240);
/* 244:272 */     func_147954_b(p_147950_0_, p_147950_1_);
/* 245:    */   }
/* 246:    */   
/* 247:    */   public static void func_147945_b()
/* 248:    */   {
/* 249:277 */     if ((field_147958_e >= 0) && (field_147956_f >= 0))
/* 250:    */     {
/* 251:279 */       func_147952_b(field_147958_e, field_147956_f);
/* 252:280 */       field_147958_e = -1;
/* 253:281 */       field_147956_f = -1;
/* 254:    */     }
/* 255:    */   }
/* 256:    */   
/* 257:    */   private static void func_147952_b(int p_147952_0_, int p_147952_1_)
/* 258:    */   {
/* 259:287 */     GL11.glTexParameteri(3553, 10241, p_147952_0_);
/* 260:288 */     GL11.glTexParameteri(3553, 10240, p_147952_1_);
/* 261:    */   }
/* 262:    */   
/* 263:    */   private static void func_147954_b(boolean p_147954_0_, boolean p_147954_1_)
/* 264:    */   {
/* 265:293 */     if (p_147954_0_)
/* 266:    */     {
/* 267:295 */       GL11.glTexParameteri(3553, 10241, p_147954_1_ ? 9987 : 9729);
/* 268:296 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 269:    */     }
/* 270:    */     else
/* 271:    */     {
/* 272:300 */       GL11.glTexParameteri(3553, 10241, p_147954_1_ ? 9986 : 9728);
/* 273:301 */       GL11.glTexParameteri(3553, 10240, 9728);
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   private static void copyToBuffer(int[] par0ArrayOfInteger, int par1)
/* 278:    */   {
/* 279:307 */     copyToBufferPos(par0ArrayOfInteger, 0, par1);
/* 280:    */   }
/* 281:    */   
/* 282:    */   private static void copyToBufferPos(int[] par0ArrayOfInteger, int par1, int par2)
/* 283:    */   {
/* 284:312 */     int[] var3 = par0ArrayOfInteger;
/* 285:314 */     if (Minecraft.getMinecraft().gameSettings.anaglyph) {
/* 286:316 */       var3 = updateAnaglyph(par0ArrayOfInteger);
/* 287:    */     }
/* 288:319 */     dataBuffer.clear();
/* 289:320 */     dataBuffer.put(var3, par1, par2);
/* 290:321 */     dataBuffer.position(0).limit(par2);
/* 291:    */   }
/* 292:    */   
/* 293:    */   static void bindTexture(int par0)
/* 294:    */   {
/* 295:326 */     GL11.glBindTexture(3553, par0);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public static int[] readImageData(IResourceManager par0ResourceManager, ResourceLocation par1ResourceLocation)
/* 299:    */     throws IOException
/* 300:    */   {
/* 301:331 */     BufferedImage var2 = ImageIO.read(par0ResourceManager.getResource(par1ResourceLocation).getInputStream());
/* 302:332 */     int var3 = var2.getWidth();
/* 303:333 */     int var4 = var2.getHeight();
/* 304:334 */     int[] var5 = new int[var3 * var4];
/* 305:335 */     var2.getRGB(0, 0, var3, var4, var5, 0, var3);
/* 306:336 */     return var5;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public static int[] updateAnaglyph(int[] par0ArrayOfInteger)
/* 310:    */   {
/* 311:341 */     int[] var1 = new int[par0ArrayOfInteger.length];
/* 312:343 */     for (int var2 = 0; var2 < par0ArrayOfInteger.length; var2++)
/* 313:    */     {
/* 314:345 */       int var3 = par0ArrayOfInteger[var2] >> 24 & 0xFF;
/* 315:346 */       int var4 = par0ArrayOfInteger[var2] >> 16 & 0xFF;
/* 316:347 */       int var5 = par0ArrayOfInteger[var2] >> 8 & 0xFF;
/* 317:348 */       int var6 = par0ArrayOfInteger[var2] & 0xFF;
/* 318:349 */       int var7 = (var4 * 30 + var5 * 59 + var6 * 11) / 100;
/* 319:350 */       int var8 = (var4 * 30 + var5 * 70) / 100;
/* 320:351 */       int var9 = (var4 * 30 + var6 * 70) / 100;
/* 321:352 */       var1[var2] = (var3 << 24 | var7 << 16 | var8 << 8 | var9);
/* 322:    */     }
/* 323:355 */     return var1;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public static int[] func_147948_a(int[] p_147948_0_, int p_147948_1_, int p_147948_2_, int p_147948_3_)
/* 327:    */   {
/* 328:360 */     int var4 = p_147948_1_ + 2 * p_147948_3_;
/* 329:364 */     for (int var5 = p_147948_2_ - 1; var5 >= 0; var5--)
/* 330:    */     {
/* 331:366 */       int var6 = var5 * p_147948_1_;
/* 332:367 */       int var7 = p_147948_3_ + (var5 + p_147948_3_) * var4;
/* 333:370 */       for (int var8 = 0; var8 < p_147948_3_; var8 += p_147948_1_)
/* 334:    */       {
/* 335:372 */         int var9 = Math.min(p_147948_1_, p_147948_3_ - var8);
/* 336:373 */         System.arraycopy(p_147948_0_, var6 + p_147948_1_ - var9, p_147948_0_, var7 - var8 - var9, var9);
/* 337:    */       }
/* 338:376 */       System.arraycopy(p_147948_0_, var6, p_147948_0_, var7, p_147948_1_);
/* 339:378 */       for (var8 = 0; var8 < p_147948_3_; var8 += p_147948_1_) {
/* 340:380 */         System.arraycopy(p_147948_0_, var6, p_147948_0_, var7 + p_147948_1_ + var8, Math.min(p_147948_1_, p_147948_3_ - var8));
/* 341:    */       }
/* 342:    */     }
/* 343:384 */     for (var5 = 0; var5 < p_147948_3_; var5 += p_147948_2_)
/* 344:    */     {
/* 345:386 */       int var6 = Math.min(p_147948_2_, p_147948_3_ - var5);
/* 346:387 */       System.arraycopy(p_147948_0_, (p_147948_3_ + p_147948_2_ - var6) * var4, p_147948_0_, (p_147948_3_ - var5 - var6) * var4, var4 * var6);
/* 347:    */     }
/* 348:390 */     for (var5 = 0; var5 < p_147948_3_; var5 += p_147948_2_)
/* 349:    */     {
/* 350:392 */       int var6 = Math.min(p_147948_2_, p_147948_3_ - var5);
/* 351:393 */       System.arraycopy(p_147948_0_, p_147948_3_ * var4, p_147948_0_, (p_147948_2_ + p_147948_3_ + var5) * var4, var4 * var6);
/* 352:    */     }
/* 353:396 */     return p_147948_0_;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public static void func_147953_a(int[] p_147953_0_, int p_147953_1_, int p_147953_2_)
/* 357:    */   {
/* 358:401 */     int[] var3 = new int[p_147953_1_];
/* 359:402 */     int var4 = p_147953_2_ / 2;
/* 360:404 */     for (int var5 = 0; var5 < var4; var5++)
/* 361:    */     {
/* 362:406 */       System.arraycopy(p_147953_0_, var5 * p_147953_1_, var3, 0, p_147953_1_);
/* 363:407 */       System.arraycopy(p_147953_0_, (p_147953_2_ - 1 - var5) * p_147953_1_, p_147953_0_, var5 * p_147953_1_, p_147953_1_);
/* 364:408 */       System.arraycopy(var3, 0, p_147953_0_, (p_147953_2_ - 1 - var5) * p_147953_1_, p_147953_1_);
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   static
/* 369:    */   {
/* 370:414 */     int var0 = -16777216;
/* 371:415 */     int var1 = -524040;
/* 372:416 */     int[] var2 = { -524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040 };
/* 373:417 */     int[] var3 = { -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216 };
/* 374:418 */     int var4 = var2.length;
/* 375:420 */     for (int var5 = 0; var5 < 16; var5++)
/* 376:    */     {
/* 377:422 */       System.arraycopy(var5 < var4 ? var2 : var3, 0, missingTextureData, 16 * var5, var4);
/* 378:423 */       System.arraycopy(var5 < var4 ? var3 : var2, 0, missingTextureData, 16 * var5 + var4, var4);
/* 379:    */     }
/* 380:426 */     missingTexture.updateDynamicTexture();
/* 381:    */   }
/* 382:    */   
/* 383:427 */   private static final int[] field_147957_g = new int[4];
/* 384:    */   private static final String __OBFID = "CL_00001067";
/* 385:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.TextureUtil
 * JD-Core Version:    0.7.0.1
 */