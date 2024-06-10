/*   1:    */ package net.minecraft.client.renderer.texture;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import com.google.common.collect.Maps;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Graphics2D;
/*   7:    */ import java.awt.image.BufferedImage;
/*   8:    */ import java.io.File;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.util.Collection;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import java.util.Map.Entry;
/*  16:    */ import java.util.Set;
/*  17:    */ import java.util.concurrent.Callable;
/*  18:    */ import javax.imageio.ImageIO;
/*  19:    */ import net.minecraft.block.Block;
/*  20:    */ import net.minecraft.block.material.Material;
/*  21:    */ import net.minecraft.client.Minecraft;
/*  22:    */ import net.minecraft.client.renderer.RenderGlobal;
/*  23:    */ import net.minecraft.client.renderer.StitcherException;
/*  24:    */ import net.minecraft.client.renderer.entity.RenderManager;
/*  25:    */ import net.minecraft.client.resources.IResource;
/*  26:    */ import net.minecraft.client.resources.IResourceManager;
/*  27:    */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*  28:    */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*  29:    */ import net.minecraft.crash.CrashReport;
/*  30:    */ import net.minecraft.crash.CrashReportCategory;
/*  31:    */ import net.minecraft.item.Item;
/*  32:    */ import net.minecraft.src.Config;
/*  33:    */ import net.minecraft.src.ConnectedTextures;
/*  34:    */ import net.minecraft.src.Reflector;
/*  35:    */ import net.minecraft.src.ReflectorMethod;
/*  36:    */ import net.minecraft.src.TextureUtils;
/*  37:    */ import net.minecraft.src.WrUpdates;
/*  38:    */ import net.minecraft.util.IIcon;
/*  39:    */ import net.minecraft.util.MathHelper;
/*  40:    */ import net.minecraft.util.RegistryNamespaced;
/*  41:    */ import net.minecraft.util.ReportedException;
/*  42:    */ import net.minecraft.util.ResourceLocation;
/*  43:    */ import org.apache.logging.log4j.LogManager;
/*  44:    */ import org.apache.logging.log4j.Logger;
/*  45:    */ 
/*  46:    */ public class TextureMap
/*  47:    */   extends AbstractTexture
/*  48:    */   implements ITickableTextureObject, IIconRegister
/*  49:    */ {
/*  50: 44 */   private static final Logger logger = ;
/*  51: 45 */   public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
/*  52: 46 */   public static final ResourceLocation locationItemsTexture = new ResourceLocation("textures/atlas/items.png");
/*  53: 47 */   private final List listAnimatedSprites = Lists.newArrayList();
/*  54: 48 */   private final Map mapRegisteredSprites = Maps.newHashMap();
/*  55: 49 */   private final Map mapUploadedSprites = Maps.newHashMap();
/*  56:    */   public final int textureType;
/*  57:    */   public final String basePath;
/*  58:    */   private int field_147636_j;
/*  59: 55 */   private int field_147637_k = 1;
/*  60: 56 */   private final TextureAtlasSprite missingImage = new TextureAtlasSprite("missingno");
/*  61: 57 */   public static TextureMap textureMapBlocks = null;
/*  62: 58 */   public static TextureMap textureMapItems = null;
/*  63: 59 */   private TextureAtlasSprite[] iconGrid = null;
/*  64: 60 */   private int iconGridSize = -1;
/*  65: 61 */   private int iconGridCountX = -1;
/*  66: 62 */   private int iconGridCountY = -1;
/*  67: 63 */   private double iconGridSizeU = -1.0D;
/*  68: 64 */   private double iconGridSizeV = -1.0D;
/*  69:    */   private static final String __OBFID = "CL_00001058";
/*  70:    */   
/*  71:    */   public TextureMap(int par1, String par2Str)
/*  72:    */   {
/*  73: 69 */     this.textureType = par1;
/*  74: 70 */     this.basePath = par2Str;
/*  75: 72 */     if (this.textureType == 0) {
/*  76: 74 */       textureMapBlocks = this;
/*  77:    */     }
/*  78: 77 */     if (this.textureType == 0) {
/*  79: 79 */       textureMapItems = this;
/*  80:    */     }
/*  81: 82 */     registerIcons();
/*  82:    */   }
/*  83:    */   
/*  84:    */   private void initMissingImage()
/*  85:    */   {
/*  86:    */     int[] var1;
/*  87: 89 */     if (this.field_147637_k > 1.0F)
/*  88:    */     {
/*  89: 91 */       boolean var5 = true;
/*  90: 92 */       boolean var3 = true;
/*  91: 93 */       boolean var4 = true;
/*  92: 94 */       this.missingImage.setIconWidth(32);
/*  93: 95 */       this.missingImage.setIconHeight(32);
/*  94: 96 */       int[] var1 = new int[1024];
/*  95: 97 */       System.arraycopy(TextureUtil.missingTextureData, 0, var1, 0, TextureUtil.missingTextureData.length);
/*  96: 98 */       TextureUtil.func_147948_a(var1, 16, 16, 8);
/*  97:    */     }
/*  98:    */     else
/*  99:    */     {
/* 100:102 */       var1 = TextureUtil.missingTextureData;
/* 101:103 */       this.missingImage.setIconWidth(16);
/* 102:104 */       this.missingImage.setIconHeight(16);
/* 103:    */     }
/* 104:107 */     int[][] var51 = new int[this.field_147636_j + 1][];
/* 105:108 */     var51[0] = var1;
/* 106:109 */     this.missingImage.setFramesTextureData(Lists.newArrayList(new int[][][] { var51 }));
/* 107:110 */     this.missingImage.setIndexInMap(0);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void loadTexture(IResourceManager par1ResourceManager)
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:115 */     initMissingImage();
/* 114:116 */     func_147631_c();
/* 115:117 */     loadTextureAtlas(par1ResourceManager);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void loadTextureAtlas(IResourceManager par1ResourceManager)
/* 119:    */   {
/* 120:122 */     Config.dbg("Loading texture map: " + this.basePath);
/* 121:123 */     WrUpdates.finishCurrentUpdate();
/* 122:124 */     registerIcons();
/* 123:125 */     int var2 = Minecraft.getGLMaximumTextureSize();
/* 124:126 */     Stitcher var3 = new Stitcher(var2, var2, true, 0, this.field_147636_j);
/* 125:127 */     this.mapUploadedSprites.clear();
/* 126:128 */     this.listAnimatedSprites.clear();
/* 127:129 */     int var4 = 2147483647;
/* 128:130 */     Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, new Object[] { this });
/* 129:131 */     Iterator var5 = this.mapRegisteredSprites.entrySet().iterator();
/* 130:137 */     while (var5.hasNext())
/* 131:    */     {
/* 132:139 */       Map.Entry var24 = (Map.Entry)var5.next();
/* 133:140 */       ResourceLocation var26 = new ResourceLocation((String)var24.getKey());
/* 134:141 */       TextureAtlasSprite var8 = (TextureAtlasSprite)var24.getValue();
/* 135:142 */       ResourceLocation sheetWidth = func_147634_a(var26, 0);
/* 136:144 */       if (!var8.hasCustomLoader(par1ResourceManager, sheetWidth))
/* 137:    */       {
/* 138:    */         try
/* 139:    */         {
/* 140:148 */           IResource sheetHeight = par1ResourceManager.getResource(sheetWidth);
/* 141:149 */           BufferedImage[] debugImage = new BufferedImage[1 + this.field_147636_j];
/* 142:150 */           debugImage[0] = ImageIO.read(sheetHeight.getInputStream());
/* 143:151 */           TextureMetadataSection var25 = (TextureMetadataSection)sheetHeight.getMetadata("texture");
/* 144:153 */           if (var25 != null)
/* 145:    */           {
/* 146:155 */             List var28 = var25.func_148535_c();
/* 147:158 */             if (!var28.isEmpty())
/* 148:    */             {
/* 149:160 */               int var18 = debugImage[0].getWidth();
/* 150:161 */               int var30 = debugImage[0].getHeight();
/* 151:163 */               if ((MathHelper.roundUpToPowerOfTwo(var18) != var18) || (MathHelper.roundUpToPowerOfTwo(var30) != var30)) {
/* 152:165 */                 throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
/* 153:    */               }
/* 154:    */             }
/* 155:169 */             Iterator var182 = var28.iterator();
/* 156:171 */             while (var182.hasNext())
/* 157:    */             {
/* 158:173 */               int var30 = ((Integer)var182.next()).intValue();
/* 159:175 */               if ((var30 > 0) && (var30 < debugImage.length - 1) && (debugImage[var30] == null))
/* 160:    */               {
/* 161:177 */                 ResourceLocation var31 = func_147634_a(var26, var30);
/* 162:    */                 try
/* 163:    */                 {
/* 164:181 */                   debugImage[var30] = ImageIO.read(par1ResourceManager.getResource(var31).getInputStream());
/* 165:    */                 }
/* 166:    */                 catch (IOException var20)
/* 167:    */                 {
/* 168:185 */                   logger.error("Unable to load miplevel {} from: {}", new Object[] { Integer.valueOf(var30), var31, var20 });
/* 169:    */                 }
/* 170:    */               }
/* 171:    */             }
/* 172:    */           }
/* 173:191 */           AnimationMetadataSection var282 = (AnimationMetadataSection)sheetHeight.getMetadata("animation");
/* 174:192 */           var8.func_147964_a(debugImage, var282, this.field_147637_k > 1.0F);
/* 175:    */         }
/* 176:    */         catch (RuntimeException var22)
/* 177:    */         {
/* 178:196 */           logger.error("Unable to parse metadata from " + sheetWidth, var22);
/* 179:197 */           continue;
/* 180:    */         }
/* 181:    */         catch (IOException var23)
/* 182:    */         {
/* 183:201 */           logger.error("Using missing texture, unable to load " + sheetWidth + ", " + var23.getClass().getName());
/* 184:202 */           continue;
/* 185:    */         }
/* 186:205 */         var4 = Math.min(var4, Math.min(var8.getIconWidth(), var8.getIconHeight()));
/* 187:206 */         var3.addSprite(var8);
/* 188:    */       }
/* 189:208 */       else if (var8.load(par1ResourceManager, sheetWidth))
/* 190:    */       {
/* 191:210 */         var4 = Math.min(var4, Math.min(var8.getIconWidth(), var8.getIconHeight()));
/* 192:211 */         var3.addSprite(var8);
/* 193:    */       }
/* 194:    */     }
/* 195:215 */     int var241 = MathHelper.calculateLogBaseTwo(var4);
/* 196:217 */     if (var241 < 0) {
/* 197:219 */       var241 = 0;
/* 198:    */     }
/* 199:222 */     if (var241 < this.field_147636_j)
/* 200:    */     {
/* 201:224 */       logger.info("{}: dropping miplevel from {} to {}, because of minTexel: {}", new Object[] { this.basePath, Integer.valueOf(this.field_147636_j), Integer.valueOf(var241), Integer.valueOf(var4) });
/* 202:225 */       this.field_147636_j = var241;
/* 203:    */     }
/* 204:228 */     Iterator var261 = this.mapRegisteredSprites.values().iterator();
/* 205:230 */     while (var261.hasNext())
/* 206:    */     {
/* 207:232 */       final TextureAtlasSprite sheetWidth1 = (TextureAtlasSprite)var261.next();
/* 208:    */       try
/* 209:    */       {
/* 210:236 */         sheetWidth1.func_147963_d(this.field_147636_j);
/* 211:    */       }
/* 212:    */       catch (Throwable var19)
/* 213:    */       {
/* 214:240 */         CrashReport debugImage1 = CrashReport.makeCrashReport(var19, "Applying mipmap");
/* 215:241 */         CrashReportCategory var251 = debugImage1.makeCategory("Sprite being mipmapped");
/* 216:242 */         var251.addCrashSectionCallable("Sprite name", new Callable()
/* 217:    */         {
/* 218:    */           private static final String __OBFID = "CL_00001059";
/* 219:    */           
/* 220:    */           public String call()
/* 221:    */           {
/* 222:247 */             return sheetWidth1.getIconName();
/* 223:    */           }
/* 224:249 */         });
/* 225:250 */         var251.addCrashSectionCallable("Sprite size", new Callable()
/* 226:    */         {
/* 227:    */           private static final String __OBFID = "CL_00001060";
/* 228:    */           
/* 229:    */           public String call()
/* 230:    */           {
/* 231:255 */             return sheetWidth1.getIconWidth() + " x " + sheetWidth1.getIconHeight();
/* 232:    */           }
/* 233:257 */         });
/* 234:258 */         var251.addCrashSectionCallable("Sprite frames", new Callable()
/* 235:    */         {
/* 236:    */           private static final String __OBFID = "CL_00001061";
/* 237:    */           
/* 238:    */           public String call()
/* 239:    */           {
/* 240:263 */             return sheetWidth1.getFrameCount() + " frames";
/* 241:    */           }
/* 242:265 */         });
/* 243:266 */         var251.addCrashSection("Mipmap levels", Integer.valueOf(this.field_147636_j));
/* 244:267 */         throw new ReportedException(debugImage1);
/* 245:    */       }
/* 246:    */     }
/* 247:271 */     this.missingImage.func_147963_d(this.field_147636_j);
/* 248:272 */     var3.addSprite(this.missingImage);
/* 249:    */     try
/* 250:    */     {
/* 251:276 */       var3.doStitch();
/* 252:    */     }
/* 253:    */     catch (StitcherException var181)
/* 254:    */     {
/* 255:280 */       throw var181;
/* 256:    */     }
/* 257:283 */     Config.dbg("Texture size: " + this.basePath + ", " + var3.getCurrentWidth() + "x" + var3.getCurrentHeight());
/* 258:284 */     int sheetWidth2 = var3.getCurrentWidth();
/* 259:285 */     int sheetHeight1 = var3.getCurrentHeight();
/* 260:286 */     BufferedImage debugImage2 = null;
/* 261:288 */     if (System.getProperty("saveTextureMap", "false").equalsIgnoreCase("true")) {
/* 262:290 */       debugImage2 = makeDebugImage(sheetWidth2, sheetHeight1);
/* 263:    */     }
/* 264:293 */     logger.info("Created: {}x{} {}-atlas", new Object[] { Integer.valueOf(var3.getCurrentWidth()), Integer.valueOf(var3.getCurrentHeight()), this.basePath });
/* 265:294 */     TextureUtil.func_147946_a(getGlTextureId(), this.field_147636_j, var3.getCurrentWidth(), var3.getCurrentHeight(), this.field_147637_k);
/* 266:295 */     HashMap var252 = Maps.newHashMap(this.mapRegisteredSprites);
/* 267:296 */     Iterator var281 = var3.getStichSlots().iterator();
/* 268:298 */     while (var281.hasNext())
/* 269:    */     {
/* 270:300 */       TextureAtlasSprite var8 = (TextureAtlasSprite)var281.next();
/* 271:301 */       String var301 = var8.getIconName();
/* 272:302 */       var252.remove(var301);
/* 273:303 */       this.mapUploadedSprites.put(var301, var8);
/* 274:    */       try
/* 275:    */       {
/* 276:307 */         TextureUtil.func_147955_a(var8.func_147965_a(0), var8.getIconWidth(), var8.getIconHeight(), var8.getOriginX(), var8.getOriginY(), false, false);
/* 277:309 */         if (debugImage2 != null) {
/* 278:311 */           addDebugSprite(var8, debugImage2);
/* 279:    */         }
/* 280:    */       }
/* 281:    */       catch (Throwable var21)
/* 282:    */       {
/* 283:316 */         CrashReport var311 = CrashReport.makeCrashReport(var21, "Stitching texture atlas");
/* 284:317 */         CrashReportCategory var33 = var311.makeCategory("Texture being stitched together");
/* 285:318 */         var33.addCrashSection("Atlas path", this.basePath);
/* 286:319 */         var33.addCrashSection("Sprite", var8);
/* 287:320 */         throw new ReportedException(var311);
/* 288:    */       }
/* 289:323 */       if (var8.hasAnimationMetadata()) {
/* 290:325 */         this.listAnimatedSprites.add(var8);
/* 291:    */       } else {
/* 292:329 */         var8.clearFramesTextureData();
/* 293:    */       }
/* 294:    */     }
/* 295:333 */     var281 = var252.values().iterator();
/* 296:335 */     while (var281.hasNext())
/* 297:    */     {
/* 298:337 */       TextureAtlasSprite var8 = (TextureAtlasSprite)var281.next();
/* 299:338 */       var8.copyFrom(this.missingImage);
/* 300:    */     }
/* 301:341 */     if (debugImage2 != null) {
/* 302:343 */       writeDebugImage(debugImage2, "debug_" + this.basePath.replace('/', '_') + ".png");
/* 303:    */     }
/* 304:346 */     Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, new Object[] { this });
/* 305:    */   }
/* 306:    */   
/* 307:    */   private ResourceLocation func_147634_a(ResourceLocation p_147634_1_, int p_147634_2_)
/* 308:    */   {
/* 309:353 */     return p_147634_2_ == 0 ? new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/%s%s", new Object[] { this.basePath, p_147634_1_.getResourcePath(), ".png" })) : isAbsoluteLocation(p_147634_1_) ? new ResourceLocation(p_147634_1_.getResourceDomain(), p_147634_1_.getResourcePath() + "mipmap" + p_147634_2_ + ".png") : p_147634_2_ == 0 ? new ResourceLocation(p_147634_1_.getResourceDomain(), p_147634_1_.getResourcePath() + ".png") : new ResourceLocation(p_147634_1_.getResourceDomain(), String.format("%s/mipmaps/%s.%d%s", new Object[] { this.basePath, p_147634_1_.getResourcePath(), Integer.valueOf(p_147634_2_), ".png" }));
/* 310:    */   }
/* 311:    */   
/* 312:    */   private void registerIcons()
/* 313:    */   {
/* 314:358 */     this.mapRegisteredSprites.clear();
/* 315:361 */     if (this.textureType == 0)
/* 316:    */     {
/* 317:363 */       Iterator var1 = Block.blockRegistry.iterator();
/* 318:365 */       while (var1.hasNext())
/* 319:    */       {
/* 320:367 */         Block var3 = (Block)var1.next();
/* 321:369 */         if (var3.getMaterial() != Material.air) {
/* 322:371 */           var3.registerBlockIcons(this);
/* 323:    */         }
/* 324:    */       }
/* 325:375 */       Minecraft.getMinecraft().renderGlobal.registerDestroyBlockIcons(this);
/* 326:376 */       RenderManager.instance.updateIcons(this);
/* 327:377 */       ConnectedTextures.updateIcons(this);
/* 328:    */     }
/* 329:380 */     Iterator var1 = Item.itemRegistry.iterator();
/* 330:382 */     while (var1.hasNext())
/* 331:    */     {
/* 332:384 */       Item var31 = (Item)var1.next();
/* 333:386 */       if ((var31 != null) && (var31.getSpriteNumber() == this.textureType)) {
/* 334:388 */         var31.registerIcons(this);
/* 335:    */       }
/* 336:    */     }
/* 337:    */   }
/* 338:    */   
/* 339:    */   public TextureAtlasSprite getAtlasSprite(String par1Str)
/* 340:    */   {
/* 341:395 */     TextureAtlasSprite var2 = (TextureAtlasSprite)this.mapUploadedSprites.get(par1Str);
/* 342:397 */     if (var2 == null) {
/* 343:399 */       var2 = this.missingImage;
/* 344:    */     }
/* 345:402 */     return var2;
/* 346:    */   }
/* 347:    */   
/* 348:    */   public void updateAnimations()
/* 349:    */   {
/* 350:407 */     TextureUtil.bindTexture(getGlTextureId());
/* 351:408 */     Iterator var1 = this.listAnimatedSprites.iterator();
/* 352:410 */     while (var1.hasNext())
/* 353:    */     {
/* 354:412 */       TextureAtlasSprite var2 = (TextureAtlasSprite)var1.next();
/* 355:414 */       if (this.textureType == 0 ? 
/* 356:    */       
/* 357:416 */         !isTerrainAnimationActive(var2) : 
/* 358:    */         
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:421 */         (this.textureType != 1) || (Config.isAnimatedItems())) {
/* 363:426 */         var2.updateAnimation();
/* 364:    */       }
/* 365:    */     }
/* 366:    */   }
/* 367:    */   
/* 368:    */   public IIcon registerIcon(String par1Str)
/* 369:    */   {
/* 370:432 */     if (par1Str == null) {
/* 371:434 */       throw new IllegalArgumentException("Name cannot be null!");
/* 372:    */     }
/* 373:436 */     if ((par1Str.indexOf('\\') != -1) && (!isAbsoluteLocationPath(par1Str))) {
/* 374:438 */       throw new IllegalArgumentException("Name cannot contain slashes!");
/* 375:    */     }
/* 376:442 */     Object var2 = (TextureAtlasSprite)this.mapRegisteredSprites.get(par1Str);
/* 377:444 */     if ((var2 == null) && (this.textureType == 1) && (Reflector.ModLoader_getCustomAnimationLogic.exists())) {
/* 378:446 */       var2 = Reflector.call(Reflector.ModLoader_getCustomAnimationLogic, new Object[] { par1Str });
/* 379:    */     }
/* 380:449 */     if (var2 == null)
/* 381:    */     {
/* 382:451 */       if (this.textureType == 1)
/* 383:    */       {
/* 384:453 */         if ("clock".equals(par1Str)) {
/* 385:455 */           var2 = new TextureClock(par1Str);
/* 386:457 */         } else if ("compass".equals(par1Str)) {
/* 387:459 */           var2 = new TextureCompass(par1Str);
/* 388:    */         } else {
/* 389:463 */           var2 = new TextureAtlasSprite(par1Str);
/* 390:    */         }
/* 391:    */       }
/* 392:    */       else {
/* 393:468 */         var2 = new TextureAtlasSprite(par1Str);
/* 394:    */       }
/* 395:471 */       this.mapRegisteredSprites.put(par1Str, var2);
/* 396:473 */       if ((var2 instanceof TextureAtlasSprite))
/* 397:    */       {
/* 398:475 */         TextureAtlasSprite tas = (TextureAtlasSprite)var2;
/* 399:476 */         tas.setIndexInMap(this.mapRegisteredSprites.size());
/* 400:    */       }
/* 401:    */     }
/* 402:480 */     return (IIcon)var2;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public int getTextureType()
/* 406:    */   {
/* 407:486 */     return this.textureType;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public void tick()
/* 411:    */   {
/* 412:491 */     updateAnimations();
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void func_147633_a(int p_147633_1_)
/* 416:    */   {
/* 417:496 */     this.field_147636_j = p_147633_1_;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void func_147632_b(int p_147632_1_)
/* 421:    */   {
/* 422:501 */     this.field_147637_k = p_147632_1_;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public TextureAtlasSprite getTextureExtry(String name)
/* 426:    */   {
/* 427:506 */     return (TextureAtlasSprite)this.mapRegisteredSprites.get(name);
/* 428:    */   }
/* 429:    */   
/* 430:    */   public boolean setTextureEntry(String name, TextureAtlasSprite entry)
/* 431:    */   {
/* 432:511 */     if (!this.mapRegisteredSprites.containsKey(name))
/* 433:    */     {
/* 434:513 */       this.mapRegisteredSprites.put(name, entry);
/* 435:514 */       entry.setIndexInMap(this.mapRegisteredSprites.size());
/* 436:515 */       return true;
/* 437:    */     }
/* 438:519 */     return false;
/* 439:    */   }
/* 440:    */   
/* 441:    */   private boolean isAbsoluteLocation(ResourceLocation loc)
/* 442:    */   {
/* 443:525 */     String path = loc.getResourcePath();
/* 444:526 */     return isAbsoluteLocationPath(path);
/* 445:    */   }
/* 446:    */   
/* 447:    */   private boolean isAbsoluteLocationPath(String resPath)
/* 448:    */   {
/* 449:531 */     String path = resPath.toLowerCase();
/* 450:532 */     return (path.startsWith("mcpatcher/")) || (path.startsWith("optifine/"));
/* 451:    */   }
/* 452:    */   
/* 453:    */   public TextureAtlasSprite getIconSafe(String name)
/* 454:    */   {
/* 455:537 */     return (TextureAtlasSprite)this.mapRegisteredSprites.get(name);
/* 456:    */   }
/* 457:    */   
/* 458:    */   private int getStandardTileSize(Collection icons)
/* 459:    */   {
/* 460:542 */     int[] sizeCounts = new int[16];
/* 461:543 */     Iterator mostUsedPo2 = icons.iterator();
/* 462:547 */     while (mostUsedPo2.hasNext())
/* 463:    */     {
/* 464:549 */       TextureAtlasSprite mostUsedCount = (TextureAtlasSprite)mostUsedPo2.next();
/* 465:551 */       if (mostUsedCount != null)
/* 466:    */       {
/* 467:553 */         int value = TextureUtils.getPowerOfTwo(mostUsedCount.getWidth());
/* 468:554 */         int count = TextureUtils.getPowerOfTwo(mostUsedCount.getHeight());
/* 469:555 */         int po2 = Math.max(value, count);
/* 470:557 */         if (po2 < sizeCounts.length) {
/* 471:559 */           sizeCounts[po2] += 1;
/* 472:    */         }
/* 473:    */       }
/* 474:    */     }
/* 475:564 */     int var8 = 4;
/* 476:565 */     int var9 = 0;
/* 477:567 */     for (int value = 0; value < sizeCounts.length; value++)
/* 478:    */     {
/* 479:569 */       int count = sizeCounts[value];
/* 480:571 */       if (count > var9)
/* 481:    */       {
/* 482:573 */         var8 = value;
/* 483:574 */         var9 = count;
/* 484:    */       }
/* 485:    */     }
/* 486:578 */     if (var8 < 4) {
/* 487:580 */       var8 = 4;
/* 488:    */     }
/* 489:583 */     value = TextureUtils.twoToPower(var8);
/* 490:584 */     return value;
/* 491:    */   }
/* 492:    */   
/* 493:    */   private void updateIconGrid(int sheetWidth, int sheetHeight)
/* 494:    */   {
/* 495:589 */     this.iconGridCountX = -1;
/* 496:590 */     this.iconGridCountY = -1;
/* 497:591 */     this.iconGrid = null;
/* 498:593 */     if (this.iconGridSize > 0)
/* 499:    */     {
/* 500:595 */       this.iconGridCountX = (sheetWidth / this.iconGridSize);
/* 501:596 */       this.iconGridCountY = (sheetHeight / this.iconGridSize);
/* 502:597 */       this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
/* 503:598 */       this.iconGridSizeU = (1.0D / this.iconGridCountX);
/* 504:599 */       this.iconGridSizeV = (1.0D / this.iconGridCountY);
/* 505:600 */       Iterator it = this.mapUploadedSprites.values().iterator();
/* 506:    */       int iuMax;
/* 507:    */       int iu;
/* 508:602 */       for (; it.hasNext(); iu <= iuMax)
/* 509:    */       {
/* 510:604 */         TextureAtlasSprite ts = (TextureAtlasSprite)it.next();
/* 511:605 */         double uMin = Math.min(ts.getMinU(), ts.getMaxU());
/* 512:606 */         double vMin = Math.min(ts.getMinV(), ts.getMaxV());
/* 513:607 */         double uMax = Math.max(ts.getMinU(), ts.getMaxU());
/* 514:608 */         double vMax = Math.max(ts.getMinV(), ts.getMaxV());
/* 515:609 */         int iuMin = (int)(uMin / this.iconGridSizeU);
/* 516:610 */         int ivMin = (int)(vMin / this.iconGridSizeV);
/* 517:611 */         iuMax = (int)(uMax / this.iconGridSizeU);
/* 518:612 */         int ivMax = (int)(vMax / this.iconGridSizeV);
/* 519:    */         
/* 520:614 */         iu = iuMin; continue;
/* 521:616 */         if ((iu >= 0) && (iu < this.iconGridCountX)) {
/* 522:618 */           for (int iv = ivMin; iv <= ivMax; iv++) {
/* 523:620 */             if ((iv >= 0) && (iv < this.iconGridCountX))
/* 524:    */             {
/* 525:622 */               int index = iv * this.iconGridCountX + iu;
/* 526:623 */               this.iconGrid[index] = ts;
/* 527:    */             }
/* 528:    */             else
/* 529:    */             {
/* 530:627 */               Config.warn("Invalid grid V: " + iv + ", icon: " + ts.getIconName());
/* 531:    */             }
/* 532:    */           }
/* 533:    */         } else {
/* 534:633 */           Config.warn("Invalid grid U: " + iu + ", icon: " + ts.getIconName());
/* 535:    */         }
/* 536:614 */         iu++;
/* 537:    */       }
/* 538:    */     }
/* 539:    */   }
/* 540:    */   
/* 541:    */   public TextureAtlasSprite getIconByUV(double u, double v)
/* 542:    */   {
/* 543:642 */     if (this.iconGrid == null) {
/* 544:644 */       return null;
/* 545:    */     }
/* 546:648 */     int iu = (int)(u / this.iconGridSizeU);
/* 547:649 */     int iv = (int)(v / this.iconGridSizeV);
/* 548:650 */     int index = iv * this.iconGridCountX + iu;
/* 549:651 */     return (index >= 0) && (index <= this.iconGrid.length) ? this.iconGrid[index] : null;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public TextureAtlasSprite getMissingSprite()
/* 553:    */   {
/* 554:657 */     return this.missingImage;
/* 555:    */   }
/* 556:    */   
/* 557:    */   public int getMaxTextureIndex()
/* 558:    */   {
/* 559:662 */     return this.mapRegisteredSprites.size();
/* 560:    */   }
/* 561:    */   
/* 562:    */   private boolean isTerrainAnimationActive(TextureAtlasSprite ts)
/* 563:    */   {
/* 564:667 */     return (ts != TextureUtils.iconWaterStill) && (ts != TextureUtils.iconWaterFlow) ? Config.isAnimatedLava() : (ts != TextureUtils.iconLavaStill) && (ts != TextureUtils.iconLavaFlow) ? Config.isAnimatedFire() : (ts != TextureUtils.iconFireLayer0) && (ts != TextureUtils.iconFireLayer1) ? Config.isAnimatedTerrain() : ts == TextureUtils.iconPortal ? Config.isAnimatedPortal() : Config.isAnimatedWater();
/* 565:    */   }
/* 566:    */   
/* 567:    */   public void loadTextureSafe(IResourceManager rm)
/* 568:    */   {
/* 569:    */     try
/* 570:    */     {
/* 571:674 */       loadTexture(rm);
/* 572:    */     }
/* 573:    */     catch (IOException var3)
/* 574:    */     {
/* 575:678 */       Config.warn("Error loading texture map: " + this.basePath);
/* 576:679 */       var3.printStackTrace();
/* 577:    */     }
/* 578:    */   }
/* 579:    */   
/* 580:    */   private BufferedImage makeDebugImage(int sheetWidth, int sheetHeight)
/* 581:    */   {
/* 582:685 */     BufferedImage image = new BufferedImage(sheetWidth, sheetHeight, 2);
/* 583:686 */     Graphics2D g = image.createGraphics();
/* 584:687 */     g.setPaint(new Color(255, 255, 0));
/* 585:688 */     g.fillRect(0, 0, image.getWidth(), image.getHeight());
/* 586:689 */     return image;
/* 587:    */   }
/* 588:    */   
/* 589:    */   private void addDebugSprite(TextureAtlasSprite ts, BufferedImage image)
/* 590:    */   {
/* 591:694 */     if (ts.getFrameCount() < 1)
/* 592:    */     {
/* 593:696 */       Config.warn("Debug sprite has no data: " + ts.getIconName());
/* 594:    */     }
/* 595:    */     else
/* 596:    */     {
/* 597:700 */       int[] data = ts.func_147965_a(0)[0];
/* 598:701 */       image.setRGB(ts.getOriginX(), ts.getOriginY(), ts.getIconWidth(), ts.getIconHeight(), data, 0, ts.getIconWidth());
/* 599:    */     }
/* 600:    */   }
/* 601:    */   
/* 602:    */   private void writeDebugImage(BufferedImage image, String pngPath)
/* 603:    */   {
/* 604:    */     try
/* 605:    */     {
/* 606:709 */       ImageIO.write(image, "png", new File(Config.getMinecraft().mcDataDir, pngPath));
/* 607:    */     }
/* 608:    */     catch (Exception var4)
/* 609:    */     {
/* 610:713 */       var4.printStackTrace();
/* 611:    */     }
/* 612:    */   }
/* 613:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.texture.TextureMap
 * JD-Core Version:    0.7.0.1
 */