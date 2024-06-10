/*    1:     */ package net.minecraft.src;
/*    2:     */ 
/*    3:     */ import java.awt.image.BufferedImage;
/*    4:     */ import java.io.FileNotFoundException;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.InputStream;
/*    7:     */ import java.util.Arrays;
/*    8:     */ import java.util.HashMap;
/*    9:     */ import java.util.Iterator;
/*   10:     */ import java.util.Properties;
/*   11:     */ import java.util.Random;
/*   12:     */ import java.util.Set;
/*   13:     */ import javax.imageio.ImageIO;
/*   14:     */ import net.minecraft.block.Block;
/*   15:     */ import net.minecraft.block.BlockStem;
/*   16:     */ import net.minecraft.block.material.Material;
/*   17:     */ import net.minecraft.client.particle.EntityFX;
/*   18:     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*   19:     */ import net.minecraft.client.settings.GameSettings;
/*   20:     */ import net.minecraft.init.Blocks;
/*   21:     */ import net.minecraft.util.MathHelper;
/*   22:     */ import net.minecraft.util.ResourceLocation;
/*   23:     */ import net.minecraft.util.Vec3;
/*   24:     */ import net.minecraft.world.ColorizerFoliage;
/*   25:     */ import net.minecraft.world.IBlockAccess;
/*   26:     */ import net.minecraft.world.World;
/*   27:     */ import net.minecraft.world.WorldProvider;
/*   28:     */ import net.minecraft.world.biome.BiomeGenBase;
/*   29:     */ 
/*   30:     */ public class CustomColorizer
/*   31:     */ {
/*   32:  30 */   private static int[] grassColors = null;
/*   33:  31 */   private static int[] waterColors = null;
/*   34:  32 */   private static int[] foliageColors = null;
/*   35:  33 */   private static int[] foliagePineColors = null;
/*   36:  34 */   private static int[] foliageBirchColors = null;
/*   37:  35 */   private static int[] swampFoliageColors = null;
/*   38:  36 */   private static int[] swampGrassColors = null;
/*   39:  37 */   private static int[][] blockPalettes = null;
/*   40:  38 */   private static int[][] paletteColors = null;
/*   41:  39 */   private static int[] skyColors = null;
/*   42:  40 */   private static int[] fogColors = null;
/*   43:  41 */   private static int[] underwaterColors = null;
/*   44:  42 */   private static float[][][] lightMapsColorsRgb = null;
/*   45:  43 */   private static int[] lightMapsHeight = null;
/*   46:  44 */   private static float[][] sunRgbs = new float[16][3];
/*   47:  45 */   private static float[][] torchRgbs = new float[16][3];
/*   48:  46 */   private static int[] redstoneColors = null;
/*   49:  47 */   private static int[] stemColors = null;
/*   50:  48 */   private static int[] myceliumParticleColors = null;
/*   51:  49 */   private static boolean useDefaultColorMultiplier = true;
/*   52:  50 */   private static int particleWaterColor = -1;
/*   53:  51 */   private static int particlePortalColor = -1;
/*   54:  52 */   private static int lilyPadColor = -1;
/*   55:  53 */   private static Vec3 fogColorNether = null;
/*   56:  54 */   private static Vec3 fogColorEnd = null;
/*   57:  55 */   private static Vec3 skyColorEnd = null;
/*   58:     */   private static final int TYPE_NONE = 0;
/*   59:     */   private static final int TYPE_GRASS = 1;
/*   60:     */   private static final int TYPE_FOLIAGE = 2;
/*   61:  59 */   private static Random random = new Random();
/*   62:     */   
/*   63:     */   public static void update()
/*   64:     */   {
/*   65:  63 */     grassColors = null;
/*   66:  64 */     waterColors = null;
/*   67:  65 */     foliageColors = null;
/*   68:  66 */     foliageBirchColors = null;
/*   69:  67 */     foliagePineColors = null;
/*   70:  68 */     swampGrassColors = null;
/*   71:  69 */     swampFoliageColors = null;
/*   72:  70 */     skyColors = null;
/*   73:  71 */     fogColors = null;
/*   74:  72 */     underwaterColors = null;
/*   75:  73 */     redstoneColors = null;
/*   76:  74 */     stemColors = null;
/*   77:  75 */     myceliumParticleColors = null;
/*   78:  76 */     lightMapsColorsRgb = null;
/*   79:  77 */     lightMapsHeight = null;
/*   80:  78 */     lilyPadColor = -1;
/*   81:  79 */     particleWaterColor = -1;
/*   82:  80 */     particlePortalColor = -1;
/*   83:  81 */     fogColorNether = null;
/*   84:  82 */     fogColorEnd = null;
/*   85:  83 */     skyColorEnd = null;
/*   86:  84 */     blockPalettes = null;
/*   87:  85 */     paletteColors = null;
/*   88:  86 */     useDefaultColorMultiplier = true;
/*   89:  87 */     String mcpColormap = "mcpatcher/colormap/";
/*   90:  88 */     grassColors = getCustomColors("textures/colormap/grass.png", 65536);
/*   91:  89 */     foliageColors = getCustomColors("textures/colormap/foliage.png", 65536);
/*   92:  90 */     String[] waterPaths = { "water.png", "watercolorX.png" };
/*   93:  91 */     waterColors = getCustomColors(mcpColormap, waterPaths, 65536);
/*   94:  93 */     if (Config.isCustomColors())
/*   95:     */     {
/*   96:  95 */       String[] pinePaths = { "pine.png", "pinecolor.png" };
/*   97:  96 */       foliagePineColors = getCustomColors(mcpColormap, pinePaths, 65536);
/*   98:  97 */       String[] birchPaths = { "birch.png", "birchcolor.png" };
/*   99:  98 */       foliageBirchColors = getCustomColors(mcpColormap, birchPaths, 65536);
/*  100:  99 */       String[] swampGrassPaths = { "swampgrass.png", "swampgrasscolor.png" };
/*  101: 100 */       swampGrassColors = getCustomColors(mcpColormap, swampGrassPaths, 65536);
/*  102: 101 */       String[] swampFoliagePaths = { "swampfoliage.png", "swampfoliagecolor.png" };
/*  103: 102 */       swampFoliageColors = getCustomColors(mcpColormap, swampFoliagePaths, 65536);
/*  104: 103 */       String[] sky0Paths = { "sky0.png", "skycolor0.png" };
/*  105: 104 */       skyColors = getCustomColors(mcpColormap, sky0Paths, 65536);
/*  106: 105 */       String[] fog0Paths = { "fog0.png", "fogcolor0.png" };
/*  107: 106 */       fogColors = getCustomColors(mcpColormap, fog0Paths, 65536);
/*  108: 107 */       String[] underwaterPaths = { "underwater.png", "underwatercolor.png" };
/*  109: 108 */       underwaterColors = getCustomColors(mcpColormap, underwaterPaths, 65536);
/*  110: 109 */       String[] redstonePaths = { "redstone.png", "redstonecolor.png" };
/*  111: 110 */       redstoneColors = getCustomColors(mcpColormap, redstonePaths, 16);
/*  112: 111 */       String[] stemPaths = { "stem.png", "stemcolor.png" };
/*  113: 112 */       stemColors = getCustomColors(mcpColormap, stemPaths, 8);
/*  114: 113 */       String[] myceliumPaths = { "myceliumparticle.png", "myceliumparticlecolor.png" };
/*  115: 114 */       myceliumParticleColors = getCustomColors(mcpColormap, myceliumPaths, -1);
/*  116: 115 */       int[][] lightMapsColors = new int[3][];
/*  117: 116 */       lightMapsColorsRgb = new float[3][][];
/*  118: 117 */       lightMapsHeight = new int[3];
/*  119: 119 */       for (int i = 0; i < lightMapsColors.length; i++)
/*  120:     */       {
/*  121: 121 */         String path = "mcpatcher/lightmap/world" + (i - 1) + ".png";
/*  122: 122 */         lightMapsColors[i] = getCustomColors(path, -1);
/*  123: 124 */         if (lightMapsColors[i] != null) {
/*  124: 126 */           lightMapsColorsRgb[i] = toRgb(lightMapsColors[i]);
/*  125:     */         }
/*  126: 129 */         lightMapsHeight[i] = getTextureHeight(path, 32);
/*  127:     */       }
/*  128: 132 */       readColorProperties("mcpatcher/color.properties");
/*  129: 133 */       updateUseDefaultColorMultiplier();
/*  130:     */     }
/*  131:     */   }
/*  132:     */   
/*  133:     */   private static int getTextureHeight(String path, int defHeight)
/*  134:     */   {
/*  135:     */     try
/*  136:     */     {
/*  137: 141 */       InputStream e = Config.getResourceStream(new ResourceLocation(path));
/*  138: 143 */       if (e == null) {
/*  139: 145 */         return defHeight;
/*  140:     */       }
/*  141: 149 */       BufferedImage bi = ImageIO.read(e);
/*  142: 150 */       return bi == null ? defHeight : bi.getHeight();
/*  143:     */     }
/*  144:     */     catch (IOException var4) {}
/*  145: 155 */     return defHeight;
/*  146:     */   }
/*  147:     */   
/*  148:     */   private static float[][] toRgb(int[] cols)
/*  149:     */   {
/*  150: 161 */     float[][] colsRgb = new float[cols.length][3];
/*  151: 163 */     for (int i = 0; i < cols.length; i++)
/*  152:     */     {
/*  153: 165 */       int col = cols[i];
/*  154: 166 */       float rf = (col >> 16 & 0xFF) / 255.0F;
/*  155: 167 */       float gf = (col >> 8 & 0xFF) / 255.0F;
/*  156: 168 */       float bf = (col & 0xFF) / 255.0F;
/*  157: 169 */       float[] colRgb = colsRgb[i];
/*  158: 170 */       colRgb[0] = rf;
/*  159: 171 */       colRgb[1] = gf;
/*  160: 172 */       colRgb[2] = bf;
/*  161:     */     }
/*  162: 175 */     return colsRgb;
/*  163:     */   }
/*  164:     */   
/*  165:     */   private static void readColorProperties(String fileName)
/*  166:     */   {
/*  167:     */     try
/*  168:     */     {
/*  169: 182 */       ResourceLocation e = new ResourceLocation(fileName);
/*  170: 183 */       InputStream in = Config.getResourceStream(e);
/*  171: 185 */       if (in == null) {
/*  172: 187 */         return;
/*  173:     */       }
/*  174: 190 */       Config.log("Loading " + fileName);
/*  175: 191 */       Properties props = new Properties();
/*  176: 192 */       props.load(in);
/*  177: 193 */       lilyPadColor = readColor(props, "lilypad");
/*  178: 194 */       particleWaterColor = readColor(props, new String[] { "particle.water", "drop.water" });
/*  179: 195 */       particlePortalColor = readColor(props, "particle.portal");
/*  180: 196 */       fogColorNether = readColorVec3(props, "fog.nether");
/*  181: 197 */       fogColorEnd = readColorVec3(props, "fog.end");
/*  182: 198 */       skyColorEnd = readColorVec3(props, "sky.end");
/*  183: 199 */       readCustomPalettes(props, fileName);
/*  184:     */     }
/*  185:     */     catch (FileNotFoundException var4) {}catch (IOException var5)
/*  186:     */     {
/*  187: 207 */       var5.printStackTrace();
/*  188:     */     }
/*  189:     */   }
/*  190:     */   
/*  191:     */   private static void readCustomPalettes(Properties props, String fileName)
/*  192:     */   {
/*  193: 213 */     blockPalettes = new int[256][1];
/*  194: 215 */     for (int palettePrefix = 0; palettePrefix < 256; palettePrefix++) {
/*  195: 217 */       blockPalettes[palettePrefix][0] = -1;
/*  196:     */     }
/*  197: 220 */     String var18 = "palette.block.";
/*  198: 221 */     HashMap map = new HashMap();
/*  199: 222 */     Set keys = props.keySet();
/*  200: 223 */     Iterator propNames = keys.iterator();
/*  201: 226 */     while (propNames.hasNext())
/*  202:     */     {
/*  203: 228 */       String i = (String)propNames.next();
/*  204: 229 */       String name = props.getProperty(i);
/*  205: 231 */       if (i.startsWith(var18)) {
/*  206: 233 */         map.put(i, name);
/*  207:     */       }
/*  208:     */     }
/*  209: 237 */     String[] var19 = (String[])map.keySet().toArray(new String[map.size()]);
/*  210: 238 */     paletteColors = new int[var19.length][];
/*  211: 240 */     for (int var20 = 0; var20 < var19.length; var20++)
/*  212:     */     {
/*  213: 242 */       String name = var19[var20];
/*  214: 243 */       String value = props.getProperty(name);
/*  215: 244 */       Config.log("Block palette: " + name + " = " + value);
/*  216: 245 */       String path = name.substring(var18.length());
/*  217: 246 */       String basePath = TextureUtils.getBasePath(fileName);
/*  218: 247 */       path = TextureUtils.fixResourcePath(path, basePath);
/*  219: 248 */       int[] colors = getCustomColors(path, 65536);
/*  220: 249 */       paletteColors[var20] = colors;
/*  221: 250 */       String[] indexStrs = Config.tokenize(value, " ,;");
/*  222: 252 */       for (int ix = 0; ix < indexStrs.length; ix++)
/*  223:     */       {
/*  224: 254 */         String blockStr = indexStrs[ix];
/*  225: 255 */         int metadata = -1;
/*  226: 257 */         if (blockStr.contains(":"))
/*  227:     */         {
/*  228: 259 */           String[] blockIndex = Config.tokenize(blockStr, ":");
/*  229: 260 */           blockStr = blockIndex[0];
/*  230: 261 */           String metadataStr = blockIndex[1];
/*  231: 262 */           metadata = Config.parseInt(metadataStr, -1);
/*  232: 264 */           if ((metadata < 0) || (metadata > 15))
/*  233:     */           {
/*  234: 266 */             Config.log("Invalid block metadata: " + blockStr + " in palette: " + name);
/*  235: 267 */             continue;
/*  236:     */           }
/*  237:     */         }
/*  238: 271 */         int var21 = Config.parseInt(blockStr, -1);
/*  239: 273 */         if ((var21 >= 0) && (var21 <= 255))
/*  240:     */         {
/*  241: 275 */           if ((var21 != Block.getIdFromBlock(Blocks.grass)) && (var21 != Block.getIdFromBlock(Blocks.tallgrass)) && (var21 != Block.getIdFromBlock(Blocks.leaves)) && (var21 != Block.getIdFromBlock(Blocks.vine))) {
/*  242: 277 */             if (metadata == -1)
/*  243:     */             {
/*  244: 279 */               blockPalettes[var21][0] = var20;
/*  245:     */             }
/*  246:     */             else
/*  247:     */             {
/*  248: 283 */               if (blockPalettes[var21].length < 16)
/*  249:     */               {
/*  250: 285 */                 blockPalettes[var21] = new int[16];
/*  251: 286 */                 Arrays.fill(blockPalettes[var21], -1);
/*  252:     */               }
/*  253: 289 */               blockPalettes[var21][metadata] = var20;
/*  254:     */             }
/*  255:     */           }
/*  256:     */         }
/*  257:     */         else {
/*  258: 295 */           Config.log("Invalid block index: " + var21 + " in palette: " + name);
/*  259:     */         }
/*  260:     */       }
/*  261:     */     }
/*  262:     */   }
/*  263:     */   
/*  264:     */   private static int readColor(Properties props, String[] names)
/*  265:     */   {
/*  266: 303 */     for (int i = 0; i < names.length; i++)
/*  267:     */     {
/*  268: 305 */       String name = names[i];
/*  269: 306 */       int col = readColor(props, name);
/*  270: 308 */       if (col >= 0) {
/*  271: 310 */         return col;
/*  272:     */       }
/*  273:     */     }
/*  274: 314 */     return -1;
/*  275:     */   }
/*  276:     */   
/*  277:     */   private static int readColor(Properties props, String name)
/*  278:     */   {
/*  279: 319 */     String str = props.getProperty(name);
/*  280: 321 */     if (str == null) {
/*  281: 323 */       return -1;
/*  282:     */     }
/*  283:     */     try
/*  284:     */     {
/*  285: 329 */       int e = Integer.parseInt(str, 16) & 0xFFFFFF;
/*  286: 330 */       Config.log("Custom color: " + name + " = " + str);
/*  287: 331 */       return e;
/*  288:     */     }
/*  289:     */     catch (NumberFormatException var4)
/*  290:     */     {
/*  291: 335 */       Config.log("Invalid custom color: " + name + " = " + str);
/*  292:     */     }
/*  293: 336 */     return -1;
/*  294:     */   }
/*  295:     */   
/*  296:     */   private static Vec3 readColorVec3(Properties props, String name)
/*  297:     */   {
/*  298: 343 */     int col = readColor(props, name);
/*  299: 345 */     if (col < 0) {
/*  300: 347 */       return null;
/*  301:     */     }
/*  302: 351 */     int red = col >> 16 & 0xFF;
/*  303: 352 */     int green = col >> 8 & 0xFF;
/*  304: 353 */     int blue = col & 0xFF;
/*  305: 354 */     float redF = red / 255.0F;
/*  306: 355 */     float greenF = green / 255.0F;
/*  307: 356 */     float blueF = blue / 255.0F;
/*  308: 357 */     return Vec3.createVectorHelper(redF, greenF, blueF);
/*  309:     */   }
/*  310:     */   
/*  311:     */   private static int[] getCustomColors(String basePath, String[] paths, int length)
/*  312:     */   {
/*  313: 363 */     for (int i = 0; i < paths.length; i++)
/*  314:     */     {
/*  315: 365 */       String path = paths[i];
/*  316: 366 */       path = basePath + path;
/*  317: 367 */       int[] cols = getCustomColors(path, length);
/*  318: 369 */       if (cols != null) {
/*  319: 371 */         return cols;
/*  320:     */       }
/*  321:     */     }
/*  322: 375 */     return null;
/*  323:     */   }
/*  324:     */   
/*  325:     */   private static int[] getCustomColors(String path, int length)
/*  326:     */   {
/*  327:     */     try
/*  328:     */     {
/*  329: 382 */       ResourceLocation e = new ResourceLocation(path);
/*  330: 383 */       InputStream in = Config.getResourceStream(e);
/*  331: 385 */       if (in == null) {
/*  332: 387 */         return null;
/*  333:     */       }
/*  334: 391 */       int[] colors = TextureUtil.readImageData(Config.getResourceManager(), e);
/*  335: 393 */       if (colors == null) {
/*  336: 395 */         return null;
/*  337:     */       }
/*  338: 397 */       if ((length > 0) && (colors.length != length))
/*  339:     */       {
/*  340: 399 */         Config.log("Invalid custom colors length: " + colors.length + ", path: " + path);
/*  341: 400 */         return null;
/*  342:     */       }
/*  343: 404 */       Config.log("Loading custom colors: " + path);
/*  344: 405 */       return colors;
/*  345:     */     }
/*  346:     */     catch (FileNotFoundException var5)
/*  347:     */     {
/*  348: 411 */       return null;
/*  349:     */     }
/*  350:     */     catch (IOException var6)
/*  351:     */     {
/*  352: 415 */       var6.printStackTrace();
/*  353:     */     }
/*  354: 416 */     return null;
/*  355:     */   }
/*  356:     */   
/*  357:     */   public static void updateUseDefaultColorMultiplier()
/*  358:     */   {
/*  359: 422 */     useDefaultColorMultiplier = (foliageBirchColors == null) && (foliagePineColors == null) && (swampGrassColors == null) && (swampFoliageColors == null) && (blockPalettes == null) && (Config.isSwampColors()) && (Config.isSmoothBiomes());
/*  360:     */   }
/*  361:     */   
/*  362:     */   public static int getColorMultiplier(Block block, IBlockAccess blockAccess, int x, int y, int z)
/*  363:     */   {
/*  364: 427 */     if (useDefaultColorMultiplier) {
/*  365: 429 */       return block.colorMultiplier(blockAccess, x, y, z);
/*  366:     */     }
/*  367: 433 */     int[] colors = null;
/*  368: 434 */     int[] swampColors = null;
/*  369: 437 */     if (blockPalettes != null)
/*  370:     */     {
/*  371: 439 */       int useSwampColors = Block.getIdFromBlock(block);
/*  372: 441 */       if ((useSwampColors >= 0) && (useSwampColors < 256))
/*  373:     */       {
/*  374: 443 */         int[] smoothColors = blockPalettes[useSwampColors];
/*  375: 444 */         boolean type = true;
/*  376:     */         int type1;
/*  377:     */         int type1;
/*  378: 447 */         if (smoothColors.length > 1)
/*  379:     */         {
/*  380: 449 */           int metadata = blockAccess.getBlockMetadata(x, y, z);
/*  381: 450 */           type1 = smoothColors[metadata];
/*  382:     */         }
/*  383:     */         else
/*  384:     */         {
/*  385: 454 */           type1 = smoothColors[0];
/*  386:     */         }
/*  387: 457 */         if (type1 >= 0) {
/*  388: 459 */           colors = paletteColors[type1];
/*  389:     */         }
/*  390:     */       }
/*  391: 463 */       if (colors != null)
/*  392:     */       {
/*  393: 465 */         if (Config.isSmoothBiomes()) {
/*  394: 467 */           return getSmoothColorMultiplier(block, blockAccess, x, y, z, colors, colors, 0, 0);
/*  395:     */         }
/*  396: 470 */         return getCustomColor(colors, blockAccess, x, y, z);
/*  397:     */       }
/*  398:     */     }
/*  399: 474 */     boolean useSwampColors1 = Config.isSwampColors();
/*  400: 475 */     boolean smoothColors1 = false;
/*  401: 476 */     byte type2 = 0;
/*  402: 477 */     int metadata = 0;
/*  403: 479 */     if ((block != Blocks.grass) && (block != Blocks.tallgrass))
/*  404:     */     {
/*  405: 481 */       if (block == Blocks.leaves)
/*  406:     */       {
/*  407: 483 */         type2 = 2;
/*  408: 484 */         smoothColors1 = Config.isSmoothBiomes();
/*  409: 485 */         metadata = blockAccess.getBlockMetadata(x, y, z);
/*  410: 487 */         if ((metadata & 0x3) == 1)
/*  411:     */         {
/*  412: 489 */           colors = foliagePineColors;
/*  413:     */         }
/*  414: 491 */         else if ((metadata & 0x3) == 2)
/*  415:     */         {
/*  416: 493 */           colors = foliageBirchColors;
/*  417:     */         }
/*  418:     */         else
/*  419:     */         {
/*  420: 497 */           colors = foliageColors;
/*  421: 499 */           if (useSwampColors1) {
/*  422: 501 */             swampColors = swampFoliageColors;
/*  423:     */           } else {
/*  424: 505 */             swampColors = colors;
/*  425:     */           }
/*  426:     */         }
/*  427:     */       }
/*  428: 509 */       else if (block == Blocks.vine)
/*  429:     */       {
/*  430: 511 */         type2 = 2;
/*  431: 512 */         smoothColors1 = Config.isSmoothBiomes();
/*  432: 513 */         colors = foliageColors;
/*  433: 515 */         if (useSwampColors1) {
/*  434: 517 */           swampColors = swampFoliageColors;
/*  435:     */         } else {
/*  436: 521 */           swampColors = colors;
/*  437:     */         }
/*  438:     */       }
/*  439:     */     }
/*  440:     */     else
/*  441:     */     {
/*  442: 527 */       type2 = 1;
/*  443: 528 */       smoothColors1 = Config.isSmoothBiomes();
/*  444: 529 */       colors = grassColors;
/*  445: 531 */       if (useSwampColors1) {
/*  446: 533 */         swampColors = swampGrassColors;
/*  447:     */       } else {
/*  448: 537 */         swampColors = colors;
/*  449:     */       }
/*  450:     */     }
/*  451: 541 */     if (smoothColors1) {
/*  452: 543 */       return getSmoothColorMultiplier(block, blockAccess, x, y, z, colors, swampColors, type2, metadata);
/*  453:     */     }
/*  454: 547 */     if ((swampColors != colors) && (blockAccess.getBiomeGenForCoords(x, z) == BiomeGenBase.swampland)) {
/*  455: 549 */       colors = swampColors;
/*  456:     */     }
/*  457: 552 */     return colors != null ? getCustomColor(colors, blockAccess, x, y, z) : block.colorMultiplier(blockAccess, x, y, z);
/*  458:     */   }
/*  459:     */   
/*  460:     */   private static int getSmoothColorMultiplier(Block block, IBlockAccess blockAccess, int x, int y, int z, int[] colors, int[] swampColors, int type, int metadata)
/*  461:     */   {
/*  462: 559 */     int sumRed = 0;
/*  463: 560 */     int sumGreen = 0;
/*  464: 561 */     int sumBlue = 0;
/*  465: 565 */     for (int r = x - 1; r <= x + 1; r++) {
/*  466: 567 */       for (int g = z - 1; g <= z + 1; g++)
/*  467:     */       {
/*  468: 569 */         int[] b = colors;
/*  469: 571 */         if ((swampColors != colors) && (blockAccess.getBiomeGenForCoords(r, g) == BiomeGenBase.swampland)) {
/*  470: 573 */           b = swampColors;
/*  471:     */         }
/*  472: 576 */         boolean col = false;
/*  473:     */         int var17;
/*  474: 579 */         if (b == null)
/*  475:     */         {
/*  476:     */           int var17;
/*  477:     */           int var17;
/*  478:     */           int var17;
/*  479: 581 */           switch (type)
/*  480:     */           {
/*  481:     */           case 1: 
/*  482: 584 */             var17 = blockAccess.getBiomeGenForCoords(r, g).getBiomeGrassColor(x, y, z);
/*  483: 585 */             break;
/*  484:     */           case 2: 
/*  485:     */             int var17;
/*  486: 588 */             if ((metadata & 0x3) == 1)
/*  487:     */             {
/*  488: 590 */               var17 = ColorizerFoliage.getFoliageColorPine();
/*  489:     */             }
/*  490:     */             else
/*  491:     */             {
/*  492:     */               int var17;
/*  493: 592 */               if ((metadata & 0x3) == 2) {
/*  494: 594 */                 var17 = ColorizerFoliage.getFoliageColorBirch();
/*  495:     */               } else {
/*  496: 598 */                 var17 = blockAccess.getBiomeGenForCoords(r, g).getBiomeFoliageColor(x, y, z);
/*  497:     */               }
/*  498:     */             }
/*  499: 601 */             break;
/*  500:     */           default: 
/*  501: 604 */             var17 = block.colorMultiplier(blockAccess, r, y, g);
/*  502:     */             
/*  503: 606 */             break;
/*  504:     */           }
/*  505:     */         }
/*  506:     */         else
/*  507:     */         {
/*  508: 609 */           var17 = getCustomColor(b, blockAccess, r, y, g);
/*  509:     */         }
/*  510: 612 */         sumRed += (var17 >> 16 & 0xFF);
/*  511: 613 */         sumGreen += (var17 >> 8 & 0xFF);
/*  512: 614 */         sumBlue += (var17 & 0xFF);
/*  513:     */       }
/*  514:     */     }
/*  515: 618 */     r = sumRed / 9;
/*  516: 619 */     int g = sumGreen / 9;
/*  517: 620 */     int var16 = sumBlue / 9;
/*  518: 621 */     return r << 16 | g << 8 | var16;
/*  519:     */   }
/*  520:     */   
/*  521:     */   public static int getFluidColor(Block block, IBlockAccess blockAccess, int x, int y, int z)
/*  522:     */   {
/*  523: 626 */     return !Config.isSwampColors() ? 16777215 : waterColors != null ? getCustomColor(waterColors, blockAccess, x, y, z) : Config.isSmoothBiomes() ? getSmoothColor(waterColors, blockAccess, x, y, z, 3, 1) : block.getMaterial() != Material.water ? block.colorMultiplier(blockAccess, x, y, z) : block.colorMultiplier(blockAccess, x, y, z);
/*  524:     */   }
/*  525:     */   
/*  526:     */   private static int getCustomColor(int[] colors, IBlockAccess blockAccess, int x, int y, int z)
/*  527:     */   {
/*  528: 631 */     BiomeGenBase bgb = blockAccess.getBiomeGenForCoords(x, z);
/*  529: 632 */     double temperature = MathHelper.clamp_float(bgb.getFloatTemperature(x, y, z), 0.0F, 1.0F);
/*  530: 633 */     double rainfall = MathHelper.clamp_float(bgb.getFloatRainfall(), 0.0F, 1.0F);
/*  531: 634 */     rainfall *= temperature;
/*  532: 635 */     int cx = (int)((1.0D - temperature) * 255.0D);
/*  533: 636 */     int cy = (int)((1.0D - rainfall) * 255.0D);
/*  534: 637 */     return colors[(cy << 8 | cx)] & 0xFFFFFF;
/*  535:     */   }
/*  536:     */   
/*  537:     */   public static void updatePortalFX(EntityFX fx)
/*  538:     */   {
/*  539: 642 */     if (particlePortalColor >= 0)
/*  540:     */     {
/*  541: 644 */       int col = particlePortalColor;
/*  542: 645 */       int red = col >> 16 & 0xFF;
/*  543: 646 */       int green = col >> 8 & 0xFF;
/*  544: 647 */       int blue = col & 0xFF;
/*  545: 648 */       float redF = red / 255.0F;
/*  546: 649 */       float greenF = green / 255.0F;
/*  547: 650 */       float blueF = blue / 255.0F;
/*  548: 651 */       fx.setRBGColorF(redF, greenF, blueF);
/*  549:     */     }
/*  550:     */   }
/*  551:     */   
/*  552:     */   public static void updateMyceliumFX(EntityFX fx)
/*  553:     */   {
/*  554: 657 */     if (myceliumParticleColors != null)
/*  555:     */     {
/*  556: 659 */       int col = myceliumParticleColors[random.nextInt(myceliumParticleColors.length)];
/*  557: 660 */       int red = col >> 16 & 0xFF;
/*  558: 661 */       int green = col >> 8 & 0xFF;
/*  559: 662 */       int blue = col & 0xFF;
/*  560: 663 */       float redF = red / 255.0F;
/*  561: 664 */       float greenF = green / 255.0F;
/*  562: 665 */       float blueF = blue / 255.0F;
/*  563: 666 */       fx.setRBGColorF(redF, greenF, blueF);
/*  564:     */     }
/*  565:     */   }
/*  566:     */   
/*  567:     */   public static void updateReddustFX(EntityFX fx, IBlockAccess blockAccess, double x, double y, double z)
/*  568:     */   {
/*  569: 672 */     if (redstoneColors != null)
/*  570:     */     {
/*  571: 674 */       int level = blockAccess.getBlockMetadata((int)x, (int)y, (int)z);
/*  572: 675 */       int col = getRedstoneColor(level);
/*  573: 677 */       if (col != -1)
/*  574:     */       {
/*  575: 679 */         int red = col >> 16 & 0xFF;
/*  576: 680 */         int green = col >> 8 & 0xFF;
/*  577: 681 */         int blue = col & 0xFF;
/*  578: 682 */         float redF = red / 255.0F;
/*  579: 683 */         float greenF = green / 255.0F;
/*  580: 684 */         float blueF = blue / 255.0F;
/*  581: 685 */         fx.setRBGColorF(redF, greenF, blueF);
/*  582:     */       }
/*  583:     */     }
/*  584:     */   }
/*  585:     */   
/*  586:     */   public static int getRedstoneColor(int level)
/*  587:     */   {
/*  588: 692 */     return (level >= 0) && (level <= 15) ? redstoneColors[level] & 0xFFFFFF : redstoneColors == null ? -1 : -1;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public static void updateWaterFX(EntityFX fx, IBlockAccess blockAccess)
/*  592:     */   {
/*  593: 697 */     if (waterColors != null)
/*  594:     */     {
/*  595: 699 */       int x = (int)fx.posX;
/*  596: 700 */       int y = (int)fx.posY;
/*  597: 701 */       int z = (int)fx.posZ;
/*  598: 702 */       int col = getFluidColor(Blocks.water, blockAccess, x, y, z);
/*  599: 703 */       int red = col >> 16 & 0xFF;
/*  600: 704 */       int green = col >> 8 & 0xFF;
/*  601: 705 */       int blue = col & 0xFF;
/*  602: 706 */       float redF = red / 255.0F;
/*  603: 707 */       float greenF = green / 255.0F;
/*  604: 708 */       float blueF = blue / 255.0F;
/*  605: 710 */       if (particleWaterColor >= 0)
/*  606:     */       {
/*  607: 712 */         int redDrop = particleWaterColor >> 16 & 0xFF;
/*  608: 713 */         int greenDrop = particleWaterColor >> 8 & 0xFF;
/*  609: 714 */         int blueDrop = particleWaterColor & 0xFF;
/*  610: 715 */         redF *= redDrop / 255.0F;
/*  611: 716 */         greenF *= greenDrop / 255.0F;
/*  612: 717 */         blueF *= blueDrop / 255.0F;
/*  613:     */       }
/*  614: 720 */       fx.setRBGColorF(redF, greenF, blueF);
/*  615:     */     }
/*  616:     */   }
/*  617:     */   
/*  618:     */   public static int getLilypadColor()
/*  619:     */   {
/*  620: 726 */     return lilyPadColor < 0 ? Blocks.waterlily.getBlockColor() : lilyPadColor;
/*  621:     */   }
/*  622:     */   
/*  623:     */   public static Vec3 getFogColorNether(Vec3 col)
/*  624:     */   {
/*  625: 731 */     return fogColorNether == null ? col : fogColorNether;
/*  626:     */   }
/*  627:     */   
/*  628:     */   public static Vec3 getFogColorEnd(Vec3 col)
/*  629:     */   {
/*  630: 736 */     return fogColorEnd == null ? col : fogColorEnd;
/*  631:     */   }
/*  632:     */   
/*  633:     */   public static Vec3 getSkyColorEnd(Vec3 col)
/*  634:     */   {
/*  635: 741 */     return skyColorEnd == null ? col : skyColorEnd;
/*  636:     */   }
/*  637:     */   
/*  638:     */   public static Vec3 getSkyColor(Vec3 skyColor3d, IBlockAccess blockAccess, double x, double y, double z)
/*  639:     */   {
/*  640: 746 */     if (skyColors == null) {
/*  641: 748 */       return skyColor3d;
/*  642:     */     }
/*  643: 752 */     int col = getSmoothColor(skyColors, blockAccess, x, y, z, 10, 1);
/*  644: 753 */     int red = col >> 16 & 0xFF;
/*  645: 754 */     int green = col >> 8 & 0xFF;
/*  646: 755 */     int blue = col & 0xFF;
/*  647: 756 */     float redF = red / 255.0F;
/*  648: 757 */     float greenF = green / 255.0F;
/*  649: 758 */     float blueF = blue / 255.0F;
/*  650: 759 */     float cRed = (float)skyColor3d.xCoord / 0.5F;
/*  651: 760 */     float cGreen = (float)skyColor3d.yCoord / 0.66275F;
/*  652: 761 */     float cBlue = (float)skyColor3d.zCoord;
/*  653: 762 */     redF *= cRed;
/*  654: 763 */     greenF *= cGreen;
/*  655: 764 */     blueF *= cBlue;
/*  656: 765 */     return Vec3.createVectorHelper(redF, greenF, blueF);
/*  657:     */   }
/*  658:     */   
/*  659:     */   public static Vec3 getFogColor(Vec3 fogColor3d, IBlockAccess blockAccess, double x, double y, double z)
/*  660:     */   {
/*  661: 771 */     if (fogColors == null) {
/*  662: 773 */       return fogColor3d;
/*  663:     */     }
/*  664: 777 */     int col = getSmoothColor(fogColors, blockAccess, x, y, z, 10, 1);
/*  665: 778 */     int red = col >> 16 & 0xFF;
/*  666: 779 */     int green = col >> 8 & 0xFF;
/*  667: 780 */     int blue = col & 0xFF;
/*  668: 781 */     float redF = red / 255.0F;
/*  669: 782 */     float greenF = green / 255.0F;
/*  670: 783 */     float blueF = blue / 255.0F;
/*  671: 784 */     float cRed = (float)fogColor3d.xCoord / 0.753F;
/*  672: 785 */     float cGreen = (float)fogColor3d.yCoord / 0.8471F;
/*  673: 786 */     float cBlue = (float)fogColor3d.zCoord;
/*  674: 787 */     redF *= cRed;
/*  675: 788 */     greenF *= cGreen;
/*  676: 789 */     blueF *= cBlue;
/*  677: 790 */     return Vec3.createVectorHelper(redF, greenF, blueF);
/*  678:     */   }
/*  679:     */   
/*  680:     */   public static Vec3 getUnderwaterColor(IBlockAccess blockAccess, double x, double y, double z)
/*  681:     */   {
/*  682: 796 */     if (underwaterColors == null) {
/*  683: 798 */       return null;
/*  684:     */     }
/*  685: 802 */     int col = getSmoothColor(underwaterColors, blockAccess, x, y, z, 10, 1);
/*  686: 803 */     int red = col >> 16 & 0xFF;
/*  687: 804 */     int green = col >> 8 & 0xFF;
/*  688: 805 */     int blue = col & 0xFF;
/*  689: 806 */     float redF = red / 255.0F;
/*  690: 807 */     float greenF = green / 255.0F;
/*  691: 808 */     float blueF = blue / 255.0F;
/*  692: 809 */     return Vec3.createVectorHelper(redF, greenF, blueF);
/*  693:     */   }
/*  694:     */   
/*  695:     */   public static int getSmoothColor(int[] colors, IBlockAccess blockAccess, double x, double y, double z, int samples, int step)
/*  696:     */   {
/*  697: 815 */     if (colors == null) {
/*  698: 817 */       return -1;
/*  699:     */     }
/*  700: 821 */     int x0 = (int)Math.floor(x);
/*  701: 822 */     int y0 = (int)Math.floor(y);
/*  702: 823 */     int z0 = (int)Math.floor(z);
/*  703: 824 */     int n = samples * step / 2;
/*  704: 825 */     int sumRed = 0;
/*  705: 826 */     int sumGreen = 0;
/*  706: 827 */     int sumBlue = 0;
/*  707: 828 */     int count = 0;
/*  708: 833 */     for (int r = x0 - n; r <= x0 + n; r += step) {
/*  709: 835 */       for (int g = z0 - n; g <= z0 + n; g += step)
/*  710:     */       {
/*  711: 837 */         int b = getCustomColor(colors, blockAccess, r, y0, g);
/*  712: 838 */         sumRed += (b >> 16 & 0xFF);
/*  713: 839 */         sumGreen += (b >> 8 & 0xFF);
/*  714: 840 */         sumBlue += (b & 0xFF);
/*  715: 841 */         count++;
/*  716:     */       }
/*  717:     */     }
/*  718: 845 */     r = sumRed / count;
/*  719: 846 */     int g = sumGreen / count;
/*  720: 847 */     int b = sumBlue / count;
/*  721: 848 */     return r << 16 | g << 8 | b;
/*  722:     */   }
/*  723:     */   
/*  724:     */   public static int mixColors(int c1, int c2, float w1)
/*  725:     */   {
/*  726: 854 */     if (w1 <= 0.0F) {
/*  727: 856 */       return c2;
/*  728:     */     }
/*  729: 858 */     if (w1 >= 1.0F) {
/*  730: 860 */       return c1;
/*  731:     */     }
/*  732: 864 */     float w2 = 1.0F - w1;
/*  733: 865 */     int r1 = c1 >> 16 & 0xFF;
/*  734: 866 */     int g1 = c1 >> 8 & 0xFF;
/*  735: 867 */     int b1 = c1 & 0xFF;
/*  736: 868 */     int r2 = c2 >> 16 & 0xFF;
/*  737: 869 */     int g2 = c2 >> 8 & 0xFF;
/*  738: 870 */     int b2 = c2 & 0xFF;
/*  739: 871 */     int r = (int)(r1 * w1 + r2 * w2);
/*  740: 872 */     int g = (int)(g1 * w1 + g2 * w2);
/*  741: 873 */     int b = (int)(b1 * w1 + b2 * w2);
/*  742: 874 */     return r << 16 | g << 8 | b;
/*  743:     */   }
/*  744:     */   
/*  745:     */   private static int averageColor(int c1, int c2)
/*  746:     */   {
/*  747: 880 */     int r1 = c1 >> 16 & 0xFF;
/*  748: 881 */     int g1 = c1 >> 8 & 0xFF;
/*  749: 882 */     int b1 = c1 & 0xFF;
/*  750: 883 */     int r2 = c2 >> 16 & 0xFF;
/*  751: 884 */     int g2 = c2 >> 8 & 0xFF;
/*  752: 885 */     int b2 = c2 & 0xFF;
/*  753: 886 */     int r = (r1 + r2) / 2;
/*  754: 887 */     int g = (g1 + g2) / 2;
/*  755: 888 */     int b = (b1 + b2) / 2;
/*  756: 889 */     return r << 16 | g << 8 | b;
/*  757:     */   }
/*  758:     */   
/*  759:     */   public static int getStemColorMultiplier(BlockStem blockStem, IBlockAccess blockAccess, int x, int y, int z)
/*  760:     */   {
/*  761: 894 */     if (stemColors == null) {
/*  762: 896 */       return blockStem.colorMultiplier(blockAccess, x, y, z);
/*  763:     */     }
/*  764: 900 */     int level = blockAccess.getBlockMetadata(x, y, z);
/*  765: 902 */     if (level < 0) {
/*  766: 904 */       level = 0;
/*  767:     */     }
/*  768: 907 */     if (level >= stemColors.length) {
/*  769: 909 */       level = stemColors.length - 1;
/*  770:     */     }
/*  771: 912 */     return stemColors[level];
/*  772:     */   }
/*  773:     */   
/*  774:     */   public static boolean updateLightmap(World world, float torchFlickerX, int[] lmColors, boolean nightvision)
/*  775:     */   {
/*  776: 918 */     if (world == null) {
/*  777: 920 */       return false;
/*  778:     */     }
/*  779: 922 */     if (lightMapsColorsRgb == null) {
/*  780: 924 */       return false;
/*  781:     */     }
/*  782: 926 */     if (!Config.isCustomColors()) {
/*  783: 928 */       return false;
/*  784:     */     }
/*  785: 932 */     int worldType = world.provider.dimensionId;
/*  786: 934 */     if ((worldType >= -1) && (worldType <= 1))
/*  787:     */     {
/*  788: 936 */       int lightMapIndex = worldType + 1;
/*  789: 937 */       float[][] lightMapRgb = lightMapsColorsRgb[lightMapIndex];
/*  790: 939 */       if (lightMapRgb == null) {
/*  791: 941 */         return false;
/*  792:     */       }
/*  793: 945 */       int height = lightMapsHeight[lightMapIndex];
/*  794: 947 */       if ((nightvision) && (height < 64)) {
/*  795: 949 */         return false;
/*  796:     */       }
/*  797: 953 */       int width = lightMapRgb.length / height;
/*  798: 955 */       if (width < 16)
/*  799:     */       {
/*  800: 957 */         Config.warn("Invalid lightmap width: " + width + " for: /environment/lightmap" + worldType + ".png");
/*  801: 958 */         lightMapsColorsRgb[lightMapIndex] = null;
/*  802: 959 */         return false;
/*  803:     */       }
/*  804: 963 */       int startIndex = 0;
/*  805: 965 */       if (nightvision) {
/*  806: 967 */         startIndex = width * 16 * 2;
/*  807:     */       }
/*  808: 970 */       float sun = 1.166667F * (world.getSunBrightness(1.0F) - 0.2F);
/*  809: 972 */       if (world.lastLightningBolt > 0) {
/*  810: 974 */         sun = 1.0F;
/*  811:     */       }
/*  812: 977 */       sun = Config.limitTo1(sun);
/*  813: 978 */       float sunX = sun * (width - 1);
/*  814: 979 */       float torchX = Config.limitTo1(torchFlickerX + 0.5F) * (width - 1);
/*  815: 980 */       float gamma = Config.limitTo1(Config.getGameSettings().gammaSetting);
/*  816: 981 */       boolean hasGamma = gamma > 1.0E-004F;
/*  817: 982 */       getLightMapColumn(lightMapRgb, sunX, startIndex, width, sunRgbs);
/*  818: 983 */       getLightMapColumn(lightMapRgb, torchX, startIndex + 16 * width, width, torchRgbs);
/*  819: 984 */       float[] rgb = new float[3];
/*  820: 986 */       for (int is = 0; is < 16; is++) {
/*  821: 988 */         for (int it = 0; it < 16; it++)
/*  822:     */         {
/*  823: 992 */           for (int r = 0; r < 3; r++)
/*  824:     */           {
/*  825: 994 */             float g = Config.limitTo1(sunRgbs[is][r] + torchRgbs[it][r]);
/*  826: 996 */             if (hasGamma)
/*  827:     */             {
/*  828: 998 */               float b = 1.0F - g;
/*  829: 999 */               b = 1.0F - b * b * b * b;
/*  830:1000 */               g = gamma * b + (1.0F - gamma) * g;
/*  831:     */             }
/*  832:1003 */             rgb[r] = g;
/*  833:     */           }
/*  834:1006 */           r = (int)(rgb[0] * 255.0F);
/*  835:1007 */           int var22 = (int)(rgb[1] * 255.0F);
/*  836:1008 */           int var21 = (int)(rgb[2] * 255.0F);
/*  837:1009 */           lmColors[(is * 16 + it)] = (0xFF000000 | r << 16 | var22 << 8 | var21);
/*  838:     */         }
/*  839:     */       }
/*  840:1013 */       return true;
/*  841:     */     }
/*  842:1020 */     return false;
/*  843:     */   }
/*  844:     */   
/*  845:     */   private static void getLightMapColumn(float[][] origMap, float x, int offset, int width, float[][] colRgb)
/*  846:     */   {
/*  847:1027 */     int xLow = (int)Math.floor(x);
/*  848:1028 */     int xHigh = (int)Math.ceil(x);
/*  849:1030 */     if (xLow == xHigh)
/*  850:     */     {
/*  851:1032 */       for (int var14 = 0; var14 < 16; var14++)
/*  852:     */       {
/*  853:1034 */         float[] var15 = origMap[(offset + var14 * width + xLow)];
/*  854:1035 */         float[] var16 = colRgb[var14];
/*  855:1037 */         for (int var17 = 0; var17 < 3; var17++) {
/*  856:1039 */           var16[var17] = var15[var17];
/*  857:     */         }
/*  858:     */       }
/*  859:     */     }
/*  860:     */     else
/*  861:     */     {
/*  862:1045 */       float dLow = 1.0F - (x - xLow);
/*  863:1046 */       float dHigh = 1.0F - (xHigh - x);
/*  864:1048 */       for (int y = 0; y < 16; y++)
/*  865:     */       {
/*  866:1050 */         float[] rgbLow = origMap[(offset + y * width + xLow)];
/*  867:1051 */         float[] rgbHigh = origMap[(offset + y * width + xHigh)];
/*  868:1052 */         float[] rgb = colRgb[y];
/*  869:1054 */         for (int i = 0; i < 3; i++) {
/*  870:1056 */           rgb[i] = (rgbLow[i] * dLow + rgbHigh[i] * dHigh);
/*  871:     */         }
/*  872:     */       }
/*  873:     */     }
/*  874:     */   }
/*  875:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.CustomColorizer
 * JD-Core Version:    0.7.0.1
 */