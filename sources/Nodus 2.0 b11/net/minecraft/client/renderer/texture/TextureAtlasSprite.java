/*   1:    */ package net.minecraft.client.renderer.texture;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import java.awt.image.BufferedImage;
/*   5:    */ import java.nio.IntBuffer;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.concurrent.Callable;
/*  11:    */ import net.minecraft.client.resources.IResourceManager;
/*  12:    */ import net.minecraft.client.resources.data.AnimationFrame;
/*  13:    */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*  14:    */ import net.minecraft.crash.CrashReport;
/*  15:    */ import net.minecraft.crash.CrashReportCategory;
/*  16:    */ import net.minecraft.src.Mipmaps;
/*  17:    */ import net.minecraft.util.IIcon;
/*  18:    */ import net.minecraft.util.ReportedException;
/*  19:    */ import net.minecraft.util.ResourceLocation;
/*  20:    */ import org.lwjgl.opengl.GL11;
/*  21:    */ 
/*  22:    */ public class TextureAtlasSprite
/*  23:    */   implements IIcon
/*  24:    */ {
/*  25:    */   private final String iconName;
/*  26: 24 */   protected List framesTextureData = Lists.newArrayList();
/*  27:    */   private AnimationMetadataSection animationMetadata;
/*  28:    */   protected boolean rotated;
/*  29:    */   private boolean field_147966_k;
/*  30:    */   protected int originX;
/*  31:    */   protected int originY;
/*  32:    */   protected int width;
/*  33:    */   protected int height;
/*  34:    */   private float minU;
/*  35:    */   private float maxU;
/*  36:    */   private float minV;
/*  37:    */   private float maxV;
/*  38:    */   protected int frameCounter;
/*  39:    */   protected int tickCounter;
/*  40: 38 */   private int indexInMap = -1;
/*  41:    */   public float baseU;
/*  42:    */   public float baseV;
/*  43:    */   public int sheetWidth;
/*  44:    */   public int sheetHeight;
/*  45: 43 */   private boolean mipmapActive = false;
/*  46: 44 */   public int glOwnTextureId = -1;
/*  47: 45 */   private int uploadedFrameIndex = -1;
/*  48: 46 */   private int uploadedOwnFrameIndex = -1;
/*  49:    */   public IntBuffer[] frameBuffers;
/*  50:    */   public Mipmaps[] frameMipmaps;
/*  51:    */   private static final String __OBFID = "CL_00001062";
/*  52:    */   
/*  53:    */   protected TextureAtlasSprite(String par1Str)
/*  54:    */   {
/*  55: 53 */     this.iconName = par1Str;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void initSprite(int par1, int par2, int par3, int par4, boolean par5)
/*  59:    */   {
/*  60: 58 */     this.originX = par3;
/*  61: 59 */     this.originY = par4;
/*  62: 60 */     this.rotated = par5;
/*  63: 61 */     float var6 = (float)(0.009999999776482582D / par1);
/*  64: 62 */     float var7 = (float)(0.009999999776482582D / par2);
/*  65: 63 */     this.minU = (par3 / (float)par1 + var6);
/*  66: 64 */     this.maxU = ((par3 + this.width) / (float)par1 - var6);
/*  67: 65 */     this.minV = (par4 / par2 + var7);
/*  68: 66 */     this.maxV = ((par4 + this.height) / par2 - var7);
/*  69: 68 */     if (this.field_147966_k)
/*  70:    */     {
/*  71: 70 */       float var8 = 8.0F / par1;
/*  72: 71 */       float var9 = 8.0F / par2;
/*  73: 72 */       this.minU += var8;
/*  74: 73 */       this.maxU -= var8;
/*  75: 74 */       this.minV += var9;
/*  76: 75 */       this.maxV -= var9;
/*  77:    */     }
/*  78: 78 */     this.baseU = Math.min(this.minU, this.maxU);
/*  79: 79 */     this.baseV = Math.min(this.minV, this.maxV);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void copyFrom(TextureAtlasSprite par1TextureAtlasSprite)
/*  83:    */   {
/*  84: 84 */     this.originX = par1TextureAtlasSprite.originX;
/*  85: 85 */     this.originY = par1TextureAtlasSprite.originY;
/*  86: 86 */     this.width = par1TextureAtlasSprite.width;
/*  87: 87 */     this.height = par1TextureAtlasSprite.height;
/*  88: 88 */     this.rotated = par1TextureAtlasSprite.rotated;
/*  89: 89 */     this.minU = par1TextureAtlasSprite.minU;
/*  90: 90 */     this.maxU = par1TextureAtlasSprite.maxU;
/*  91: 91 */     this.minV = par1TextureAtlasSprite.minV;
/*  92: 92 */     this.maxV = par1TextureAtlasSprite.maxV;
/*  93: 93 */     this.baseU = Math.min(this.minU, this.maxU);
/*  94: 94 */     this.baseV = Math.min(this.minV, this.maxV);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int getOriginX()
/*  98:    */   {
/*  99:102 */     return this.originX;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public int getOriginY()
/* 103:    */   {
/* 104:110 */     return this.originY;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public int getIconWidth()
/* 108:    */   {
/* 109:118 */     return this.width;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public int getIconHeight()
/* 113:    */   {
/* 114:126 */     return this.height;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public float getMinU()
/* 118:    */   {
/* 119:134 */     return this.minU;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public float getMaxU()
/* 123:    */   {
/* 124:142 */     return this.maxU;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public float getInterpolatedU(double par1)
/* 128:    */   {
/* 129:150 */     float var3 = this.maxU - this.minU;
/* 130:151 */     return this.minU + var3 * (float)par1 / 16.0F;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public float getMinV()
/* 134:    */   {
/* 135:159 */     return this.minV;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public float getMaxV()
/* 139:    */   {
/* 140:167 */     return this.maxV;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public float getInterpolatedV(double par1)
/* 144:    */   {
/* 145:175 */     float var3 = this.maxV - this.minV;
/* 146:176 */     return this.minV + var3 * ((float)par1 / 16.0F);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getIconName()
/* 150:    */   {
/* 151:181 */     return this.iconName;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void updateAnimation()
/* 155:    */   {
/* 156:186 */     this.tickCounter += 1;
/* 157:188 */     if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter))
/* 158:    */     {
/* 159:190 */       int var1 = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 160:191 */       int var2 = this.animationMetadata.getFrameCount() == 0 ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
/* 161:192 */       this.frameCounter = ((this.frameCounter + 1) % var2);
/* 162:193 */       this.tickCounter = 0;
/* 163:194 */       int var3 = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 164:196 */       if ((var1 != var3) && (var3 >= 0) && (var3 < this.framesTextureData.size()))
/* 165:    */       {
/* 166:198 */         TextureUtil.func_147955_a((int[][])this.framesTextureData.get(var3), this.width, this.height, this.originX, this.originY, false, false);
/* 167:199 */         this.uploadedFrameIndex = var3;
/* 168:    */       }
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   public int[][] func_147965_a(int p_147965_1_)
/* 173:    */   {
/* 174:206 */     return (int[][])this.framesTextureData.get(p_147965_1_);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int getFrameCount()
/* 178:    */   {
/* 179:211 */     return this.framesTextureData.size();
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setIconWidth(int par1)
/* 183:    */   {
/* 184:216 */     this.width = par1;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setIconHeight(int par1)
/* 188:    */   {
/* 189:221 */     this.height = par1;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void func_147964_a(BufferedImage[] p_147964_1_, AnimationMetadataSection p_147964_2_, boolean p_147964_3_)
/* 193:    */   {
/* 194:226 */     resetSprite();
/* 195:227 */     this.field_147966_k = p_147964_3_;
/* 196:228 */     int var4 = p_147964_1_[0].getWidth();
/* 197:229 */     int var5 = p_147964_1_[0].getHeight();
/* 198:230 */     this.width = var4;
/* 199:231 */     this.height = var5;
/* 200:233 */     if (p_147964_3_)
/* 201:    */     {
/* 202:235 */       this.width += 16;
/* 203:236 */       this.height += 16;
/* 204:    */     }
/* 205:239 */     int[][] var6 = new int[p_147964_1_.length][];
/* 206:242 */     for (int var7 = 0; var7 < p_147964_1_.length; var7++)
/* 207:    */     {
/* 208:244 */       BufferedImage var12 = p_147964_1_[var7];
/* 209:246 */       if (var12 != null)
/* 210:    */       {
/* 211:248 */         if ((var7 > 0) && ((var12.getWidth() != var4 >> var7) || (var12.getHeight() != var5 >> var7))) {
/* 212:250 */           throw new RuntimeException(String.format("Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", new Object[] { Integer.valueOf(var7), Integer.valueOf(var12.getWidth()), Integer.valueOf(var12.getHeight()), Integer.valueOf(var4 >> var7), Integer.valueOf(var5 >> var7) }));
/* 213:    */         }
/* 214:253 */         var6[var7] = new int[var12.getWidth() * var12.getHeight()];
/* 215:254 */         var12.getRGB(0, 0, var12.getWidth(), var12.getHeight(), var6[var7], 0, var12.getWidth());
/* 216:    */       }
/* 217:    */     }
/* 218:258 */     if (p_147964_2_ == null)
/* 219:    */     {
/* 220:260 */       if (var5 != var4) {
/* 221:262 */         throw new RuntimeException("broken aspect ratio and not an animation");
/* 222:    */       }
/* 223:265 */       func_147961_a(var6);
/* 224:266 */       this.framesTextureData.add(func_147960_a(var6, var4, var5));
/* 225:    */     }
/* 226:    */     else
/* 227:    */     {
/* 228:270 */       var7 = var5 / var4;
/* 229:271 */       int var121 = var4;
/* 230:272 */       int var9 = var4;
/* 231:273 */       this.height = this.width;
/* 232:276 */       if (p_147964_2_.getFrameCount() > 0)
/* 233:    */       {
/* 234:278 */         Iterator var13 = p_147964_2_.getFrameIndexSet().iterator();
/* 235:280 */         while (var13.hasNext())
/* 236:    */         {
/* 237:282 */           int var11 = ((Integer)var13.next()).intValue();
/* 238:284 */           if (var11 >= var7) {
/* 239:286 */             throw new RuntimeException("invalid frameindex " + var11);
/* 240:    */           }
/* 241:289 */           allocateFrameTextureData(var11);
/* 242:290 */           this.framesTextureData.set(var11, func_147960_a(func_147962_a(var6, var121, var9, var11), var121, var9));
/* 243:    */         }
/* 244:293 */         this.animationMetadata = p_147964_2_;
/* 245:    */       }
/* 246:    */       else
/* 247:    */       {
/* 248:297 */         ArrayList var131 = Lists.newArrayList();
/* 249:299 */         for (int var11 = 0; var11 < var7; var11++)
/* 250:    */         {
/* 251:301 */           this.framesTextureData.add(func_147960_a(func_147962_a(var6, var121, var9, var11), var121, var9));
/* 252:302 */           var131.add(new AnimationFrame(var11, -1));
/* 253:    */         }
/* 254:305 */         this.animationMetadata = new AnimationMetadataSection(var131, this.width, this.height, p_147964_2_.getFrameTime());
/* 255:    */       }
/* 256:    */     }
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void func_147963_d(int p_147963_1_)
/* 260:    */   {
/* 261:312 */     ArrayList var2 = Lists.newArrayList();
/* 262:314 */     for (int var3 = 0; var3 < this.framesTextureData.size(); var3++)
/* 263:    */     {
/* 264:316 */       final int[][] var4 = (int[][])this.framesTextureData.get(var3);
/* 265:318 */       if (var4 != null) {
/* 266:    */         try
/* 267:    */         {
/* 268:322 */           var2.add(TextureUtil.func_147949_a(p_147963_1_, this.width, var4));
/* 269:    */         }
/* 270:    */         catch (Throwable var8)
/* 271:    */         {
/* 272:326 */           CrashReport var6 = CrashReport.makeCrashReport(var8, "Generating mipmaps for frame");
/* 273:327 */           CrashReportCategory var7 = var6.makeCategory("Frame being iterated");
/* 274:328 */           var7.addCrashSection("Frame index", Integer.valueOf(var3));
/* 275:329 */           var7.addCrashSectionCallable("Frame sizes", new Callable()
/* 276:    */           {
/* 277:    */             private static final String __OBFID = "CL_00001063";
/* 278:    */             
/* 279:    */             public String call()
/* 280:    */             {
/* 281:334 */               StringBuilder var1 = new StringBuilder();
/* 282:335 */               int[][] var2 = var4;
/* 283:336 */               int var3 = var2.length;
/* 284:338 */               for (int var4x = 0; var4x < var3; var4x++)
/* 285:    */               {
/* 286:340 */                 int[] var5 = var2[var4x];
/* 287:342 */                 if (var1.length() > 0) {
/* 288:344 */                   var1.append(", ");
/* 289:    */                 }
/* 290:347 */                 var1.append(var5 == null ? "null" : Integer.valueOf(var5.length));
/* 291:    */               }
/* 292:350 */               return var1.toString();
/* 293:    */             }
/* 294:352 */           });
/* 295:353 */           throw new ReportedException(var6);
/* 296:    */         }
/* 297:    */       }
/* 298:    */     }
/* 299:358 */     setFramesTextureData(var2);
/* 300:    */   }
/* 301:    */   
/* 302:    */   private void func_147961_a(int[][] p_147961_1_)
/* 303:    */   {
/* 304:363 */     int[] var2 = p_147961_1_[0];
/* 305:364 */     int var3 = 0;
/* 306:365 */     int var4 = 0;
/* 307:366 */     int var5 = 0;
/* 308:367 */     int var6 = 0;
/* 309:370 */     for (int var7 = 0; var7 < var2.length; var7++) {
/* 310:372 */       if ((var2[var7] & 0xFF000000) != 0)
/* 311:    */       {
/* 312:374 */         var4 += (var2[var7] >> 16 & 0xFF);
/* 313:375 */         var5 += (var2[var7] >> 8 & 0xFF);
/* 314:376 */         var6 += (var2[var7] >> 0 & 0xFF);
/* 315:377 */         var3++;
/* 316:    */       }
/* 317:    */     }
/* 318:381 */     if (var3 != 0)
/* 319:    */     {
/* 320:383 */       var4 /= var3;
/* 321:384 */       var5 /= var3;
/* 322:385 */       var6 /= var3;
/* 323:387 */       for (var7 = 0; var7 < var2.length; var7++) {
/* 324:389 */         if ((var2[var7] & 0xFF000000) == 0) {
/* 325:391 */           var2[var7] = (var4 << 16 | var5 << 8 | var6);
/* 326:    */         }
/* 327:    */       }
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   private int[][] func_147960_a(int[][] p_147960_1_, int p_147960_2_, int p_147960_3_)
/* 332:    */   {
/* 333:399 */     if (!this.field_147966_k) {
/* 334:401 */       return p_147960_1_;
/* 335:    */     }
/* 336:405 */     int[][] var4 = new int[p_147960_1_.length][];
/* 337:407 */     for (int var5 = 0; var5 < p_147960_1_.length; var5++)
/* 338:    */     {
/* 339:409 */       int[] var6 = p_147960_1_[var5];
/* 340:411 */       if (var6 != null)
/* 341:    */       {
/* 342:413 */         int[] var7 = new int[(p_147960_2_ + 16 >> var5) * (p_147960_3_ + 16 >> var5)];
/* 343:414 */         System.arraycopy(var6, 0, var7, 0, var6.length);
/* 344:415 */         var4[var5] = TextureUtil.func_147948_a(var7, p_147960_2_ >> var5, p_147960_3_ >> var5, 8 >> var5);
/* 345:    */       }
/* 346:    */     }
/* 347:419 */     return var4;
/* 348:    */   }
/* 349:    */   
/* 350:    */   private void allocateFrameTextureData(int par1)
/* 351:    */   {
/* 352:425 */     if (this.framesTextureData.size() <= par1) {
/* 353:427 */       for (int var2 = this.framesTextureData.size(); var2 <= par1; var2++) {
/* 354:429 */         this.framesTextureData.add(null);
/* 355:    */       }
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   private static int[][] func_147962_a(int[][] p_147962_0_, int p_147962_1_, int p_147962_2_, int p_147962_3_)
/* 360:    */   {
/* 361:436 */     int[][] var4 = new int[p_147962_0_.length][];
/* 362:438 */     for (int var5 = 0; var5 < p_147962_0_.length; var5++)
/* 363:    */     {
/* 364:440 */       int[] var6 = p_147962_0_[var5];
/* 365:442 */       if (var6 != null)
/* 366:    */       {
/* 367:444 */         var4[var5] = new int[(p_147962_1_ >> var5) * (p_147962_2_ >> var5)];
/* 368:445 */         System.arraycopy(var6, p_147962_3_ * var4[var5].length, var4[var5], 0, var4[var5].length);
/* 369:    */       }
/* 370:    */     }
/* 371:449 */     return var4;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public void clearFramesTextureData()
/* 375:    */   {
/* 376:454 */     this.framesTextureData.clear();
/* 377:    */   }
/* 378:    */   
/* 379:    */   public boolean hasAnimationMetadata()
/* 380:    */   {
/* 381:459 */     return this.animationMetadata != null;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public void setFramesTextureData(List par1List)
/* 385:    */   {
/* 386:464 */     this.framesTextureData = par1List;
/* 387:466 */     for (int i = 0; i < this.framesTextureData.size(); i++) {
/* 388:468 */       if (this.framesTextureData.get(i) == null) {
/* 389:470 */         this.framesTextureData.set(i, new int[this.width * this.height]);
/* 390:    */       }
/* 391:    */     }
/* 392:    */   }
/* 393:    */   
/* 394:    */   private void resetSprite()
/* 395:    */   {
/* 396:477 */     this.animationMetadata = null;
/* 397:478 */     setFramesTextureData(Lists.newArrayList());
/* 398:479 */     this.frameCounter = 0;
/* 399:480 */     this.tickCounter = 0;
/* 400:481 */     deleteOwnTexture();
/* 401:482 */     this.uploadedFrameIndex = -1;
/* 402:483 */     this.uploadedOwnFrameIndex = -1;
/* 403:484 */     this.frameBuffers = null;
/* 404:485 */     this.frameMipmaps = null;
/* 405:    */   }
/* 406:    */   
/* 407:    */   public String toString()
/* 408:    */   {
/* 409:490 */     return "TextureAtlasSprite{name='" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
/* 410:    */   }
/* 411:    */   
/* 412:    */   public int getWidth()
/* 413:    */   {
/* 414:495 */     return this.width;
/* 415:    */   }
/* 416:    */   
/* 417:    */   public int getHeight()
/* 418:    */   {
/* 419:500 */     return this.height;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public int getIndexInMap()
/* 423:    */   {
/* 424:505 */     return this.indexInMap;
/* 425:    */   }
/* 426:    */   
/* 427:    */   public void setIndexInMap(int indexInMap)
/* 428:    */   {
/* 429:510 */     this.indexInMap = indexInMap;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void setMipmapActive(boolean mipmapActive)
/* 433:    */   {
/* 434:515 */     this.mipmapActive = mipmapActive;
/* 435:516 */     this.frameMipmaps = null;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void uploadFrameTexture()
/* 439:    */   {
/* 440:521 */     uploadFrameTexture(this.frameCounter, this.originX, this.originY);
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void uploadFrameTexture(int frameIndex, int xPos, int yPos) {}
/* 444:    */   
/* 445:    */   private void uploadFrameMipmaps(int frameIndex, int xPos, int yPos) {}
/* 446:    */   
/* 447:    */   public void bindOwnTexture() {}
/* 448:    */   
/* 449:    */   public void bindUploadOwnTexture()
/* 450:    */   {
/* 451:532 */     bindOwnTexture();
/* 452:533 */     uploadFrameTexture(this.frameCounter, 0, 0);
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void uploadOwnAnimation()
/* 456:    */   {
/* 457:538 */     if (this.uploadedFrameIndex != this.uploadedOwnFrameIndex)
/* 458:    */     {
/* 459:540 */       TextureUtil.bindTexture(this.glOwnTextureId);
/* 460:541 */       uploadFrameTexture(this.uploadedFrameIndex, 0, 0);
/* 461:542 */       this.uploadedOwnFrameIndex = this.uploadedFrameIndex;
/* 462:    */     }
/* 463:    */   }
/* 464:    */   
/* 465:    */   public void deleteOwnTexture()
/* 466:    */   {
/* 467:548 */     if (this.glOwnTextureId >= 0)
/* 468:    */     {
/* 469:550 */       GL11.glDeleteTextures(this.glOwnTextureId);
/* 470:551 */       this.glOwnTextureId = -1;
/* 471:    */     }
/* 472:    */   }
/* 473:    */   
/* 474:    */   private void fixTransparentColor(int[] data)
/* 475:    */   {
/* 476:557 */     long redSum = 0L;
/* 477:558 */     long greenSum = 0L;
/* 478:559 */     long blueSum = 0L;
/* 479:560 */     long count = 0L;
/* 480:568 */     for (int redAvg = 0; redAvg < data.length; redAvg++)
/* 481:    */     {
/* 482:570 */       int greenAvg = data[redAvg];
/* 483:571 */       int blueAvg = greenAvg >> 24 & 0xFF;
/* 484:573 */       if (blueAvg != 0)
/* 485:    */       {
/* 486:575 */         int i = greenAvg >> 16 & 0xFF;
/* 487:576 */         int col = greenAvg >> 8 & 0xFF;
/* 488:577 */         int alpha = greenAvg & 0xFF;
/* 489:578 */         redSum += i;
/* 490:579 */         greenSum += col;
/* 491:580 */         blueSum += alpha;
/* 492:581 */         count += 1L;
/* 493:    */       }
/* 494:    */     }
/* 495:585 */     if (count > 0L)
/* 496:    */     {
/* 497:587 */       redAvg = (int)(redSum / count);
/* 498:588 */       int greenAvg = (int)(greenSum / count);
/* 499:589 */       int blueAvg = (int)(blueSum / count);
/* 500:591 */       for (int i = 0; i < data.length; i++)
/* 501:    */       {
/* 502:593 */         int col = data[i];
/* 503:594 */         int alpha = col >> 24 & 0xFF;
/* 504:596 */         if (alpha == 0) {
/* 505:598 */           data[i] = (redAvg << 16 | greenAvg << 8 | blueAvg);
/* 506:    */         }
/* 507:    */       }
/* 508:    */     }
/* 509:    */   }
/* 510:    */   
/* 511:    */   public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location)
/* 512:    */   {
/* 513:606 */     return false;
/* 514:    */   }
/* 515:    */   
/* 516:    */   public boolean load(IResourceManager manager, ResourceLocation location)
/* 517:    */   {
/* 518:611 */     return true;
/* 519:    */   }
/* 520:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.TextureAtlasSprite
 * JD-Core Version:    0.7.0.1
 */