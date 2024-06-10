/*    1:     */ package net.minecraft.src;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileNotFoundException;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.InputStream;
/*    7:     */ import java.util.ArrayList;
/*    8:     */ import java.util.Arrays;
/*    9:     */ import java.util.Enumeration;
/*   10:     */ import java.util.HashSet;
/*   11:     */ import java.util.List;
/*   12:     */ import java.util.Properties;
/*   13:     */ import java.util.zip.ZipEntry;
/*   14:     */ import java.util.zip.ZipFile;
/*   15:     */ import net.minecraft.block.Block;
/*   16:     */ import net.minecraft.block.BlockLog;
/*   17:     */ import net.minecraft.client.renderer.Tessellator;
/*   18:     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*   19:     */ import net.minecraft.client.renderer.texture.TextureMap;
/*   20:     */ import net.minecraft.client.resources.AbstractResourcePack;
/*   21:     */ import net.minecraft.client.resources.DefaultResourcePack;
/*   22:     */ import net.minecraft.client.resources.IResourcePack;
/*   23:     */ import net.minecraft.util.IIcon;
/*   24:     */ import net.minecraft.util.ResourceLocation;
/*   25:     */ import net.minecraft.world.IBlockAccess;
/*   26:     */ import net.minecraft.world.biome.BiomeGenBase;
/*   27:     */ 
/*   28:     */ public class ConnectedTextures
/*   29:     */ {
/*   30:  30 */   private static ConnectedProperties[][] blockProperties = null;
/*   31:  31 */   private static ConnectedProperties[][] tileProperties = null;
/*   32:  32 */   private static boolean multipass = false;
/*   33:     */   private static final int BOTTOM = 0;
/*   34:     */   private static final int TOP = 1;
/*   35:     */   private static final int EAST = 2;
/*   36:     */   private static final int WEST = 3;
/*   37:     */   private static final int NORTH = 4;
/*   38:     */   private static final int SOUTH = 5;
/*   39:  39 */   private static final String[] propSuffixes = { "", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
/*   40:  40 */   private static final int[] ctmIndexes = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 0, 0, 0, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 0, 0, 0, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 0, 0, 0, 0, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46 };
/*   41:     */   
/*   42:     */   public static IIcon getConnectedTexture(IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon)
/*   43:     */   {
/*   44:  44 */     if (blockAccess == null) {
/*   45:  46 */       return icon;
/*   46:     */     }
/*   47:  50 */     IIcon newIcon = getConnectedTextureSingle(blockAccess, block, x, y, z, side, icon, true);
/*   48:  52 */     if (!multipass) {
/*   49:  54 */       return newIcon;
/*   50:     */     }
/*   51:  56 */     if (newIcon == icon) {
/*   52:  58 */       return newIcon;
/*   53:     */     }
/*   54:  62 */     IIcon mpIcon = newIcon;
/*   55:  64 */     for (int i = 0; i < 3; i++)
/*   56:     */     {
/*   57:  66 */       IIcon newMpIcon = getConnectedTextureSingle(blockAccess, block, x, y, z, side, mpIcon, false);
/*   58:  68 */       if (newMpIcon == mpIcon) {
/*   59:     */         break;
/*   60:     */       }
/*   61:  73 */       mpIcon = newMpIcon;
/*   62:     */     }
/*   63:  76 */     return mpIcon;
/*   64:     */   }
/*   65:     */   
/*   66:     */   public static IIcon getConnectedTextureSingle(IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, boolean checkBlocks)
/*   67:     */   {
/*   68:  83 */     if (!(icon instanceof TextureAtlasSprite)) {
/*   69:  85 */       return icon;
/*   70:     */     }
/*   71:  89 */     TextureAtlasSprite ts = (TextureAtlasSprite)icon;
/*   72:  90 */     int iconId = ts.getIndexInMap();
/*   73:  91 */     int metadata = -1;
/*   74:  93 */     if ((tileProperties != null) && (Tessellator.instance.defaultTexture) && (iconId >= 0) && (iconId < tileProperties.length))
/*   75:     */     {
/*   76:  95 */       ConnectedProperties[] blockId = tileProperties[iconId];
/*   77:  97 */       if (blockId != null)
/*   78:     */       {
/*   79:  99 */         if (metadata < 0) {
/*   80: 101 */           metadata = blockAccess.getBlockMetadata(x, y, z);
/*   81:     */         }
/*   82: 104 */         IIcon cps = getConnectedTexture(blockId, blockAccess, block, x, y, z, side, ts, metadata);
/*   83: 106 */         if (cps != null) {
/*   84: 108 */           return cps;
/*   85:     */         }
/*   86:     */       }
/*   87:     */     }
/*   88: 113 */     if ((blockProperties != null) && (checkBlocks))
/*   89:     */     {
/*   90: 115 */       int blockId1 = Block.getIdFromBlock(block);
/*   91: 117 */       if ((blockId1 >= 0) && (blockId1 < blockProperties.length))
/*   92:     */       {
/*   93: 119 */         ConnectedProperties[] cps1 = blockProperties[blockId1];
/*   94: 121 */         if (cps1 != null)
/*   95:     */         {
/*   96: 123 */           if (metadata < 0) {
/*   97: 125 */             metadata = blockAccess.getBlockMetadata(x, y, z);
/*   98:     */           }
/*   99: 128 */           IIcon newIcon = getConnectedTexture(cps1, blockAccess, block, x, y, z, side, ts, metadata);
/*  100: 130 */           if (newIcon != null) {
/*  101: 132 */             return newIcon;
/*  102:     */           }
/*  103:     */         }
/*  104:     */       }
/*  105:     */     }
/*  106: 138 */     return icon;
/*  107:     */   }
/*  108:     */   
/*  109:     */   public static ConnectedProperties getConnectedProperties(IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon)
/*  110:     */   {
/*  111: 144 */     if (blockAccess == null) {
/*  112: 146 */       return null;
/*  113:     */     }
/*  114: 148 */     if (!(icon instanceof TextureAtlasSprite)) {
/*  115: 150 */       return null;
/*  116:     */     }
/*  117: 154 */     TextureAtlasSprite ts = (TextureAtlasSprite)icon;
/*  118: 155 */     int iconId = ts.getIndexInMap();
/*  119: 156 */     int metadata = -1;
/*  120: 158 */     if ((tileProperties != null) && (Tessellator.instance.defaultTexture) && (iconId >= 0) && (iconId < tileProperties.length))
/*  121:     */     {
/*  122: 160 */       ConnectedProperties[] blockId = tileProperties[iconId];
/*  123: 162 */       if (blockId != null)
/*  124:     */       {
/*  125: 164 */         if (metadata < 0) {
/*  126: 166 */           metadata = blockAccess.getBlockMetadata(x, y, z);
/*  127:     */         }
/*  128: 169 */         ConnectedProperties cps = getConnectedProperties(blockId, blockAccess, block, x, y, z, side, ts, metadata);
/*  129: 171 */         if (cps != null) {
/*  130: 173 */           return cps;
/*  131:     */         }
/*  132:     */       }
/*  133:     */     }
/*  134: 178 */     if (blockProperties != null)
/*  135:     */     {
/*  136: 180 */       int blockId1 = Block.getIdFromBlock(block);
/*  137: 182 */       if ((blockId1 >= 0) && (blockId1 < blockProperties.length))
/*  138:     */       {
/*  139: 184 */         ConnectedProperties[] cps1 = blockProperties[blockId1];
/*  140: 186 */         if (cps1 != null)
/*  141:     */         {
/*  142: 188 */           if (metadata < 0) {
/*  143: 190 */             metadata = blockAccess.getBlockMetadata(x, y, z);
/*  144:     */           }
/*  145: 193 */           ConnectedProperties cp = getConnectedProperties(cps1, blockAccess, block, x, y, z, side, ts, metadata);
/*  146: 195 */           if (cp != null) {
/*  147: 197 */             return cp;
/*  148:     */           }
/*  149:     */         }
/*  150:     */       }
/*  151:     */     }
/*  152: 203 */     return null;
/*  153:     */   }
/*  154:     */   
/*  155:     */   private static IIcon getConnectedTexture(ConnectedProperties[] cps, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata)
/*  156:     */   {
/*  157: 209 */     for (int i = 0; i < cps.length; i++)
/*  158:     */     {
/*  159: 211 */       ConnectedProperties cp = cps[i];
/*  160: 213 */       if (cp != null)
/*  161:     */       {
/*  162: 215 */         IIcon newIcon = getConnectedTexture(cp, blockAccess, block, x, y, z, side, icon, metadata);
/*  163: 217 */         if (newIcon != null) {
/*  164: 219 */           return newIcon;
/*  165:     */         }
/*  166:     */       }
/*  167:     */     }
/*  168: 224 */     return null;
/*  169:     */   }
/*  170:     */   
/*  171:     */   private static ConnectedProperties getConnectedProperties(ConnectedProperties[] cps, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata)
/*  172:     */   {
/*  173: 229 */     for (int i = 0; i < cps.length; i++)
/*  174:     */     {
/*  175: 231 */       ConnectedProperties cp = cps[i];
/*  176: 233 */       if (cp != null)
/*  177:     */       {
/*  178: 235 */         IIcon newIcon = getConnectedTexture(cp, blockAccess, block, x, y, z, side, icon, metadata);
/*  179: 237 */         if (newIcon != null) {
/*  180: 239 */           return cp;
/*  181:     */         }
/*  182:     */       }
/*  183:     */     }
/*  184: 244 */     return null;
/*  185:     */   }
/*  186:     */   
/*  187:     */   private static IIcon getConnectedTexture(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata)
/*  188:     */   {
/*  189: 249 */     if ((y >= cp.minHeight) && (y <= cp.maxHeight))
/*  190:     */     {
/*  191: 251 */       if (cp.biomes != null)
/*  192:     */       {
/*  193: 253 */         BiomeGenBase blockWood = blockAccess.getBiomeGenForCoords(x, z);
/*  194: 254 */         boolean checkMetadata = false;
/*  195: 256 */         for (int mds = 0; mds < cp.biomes.length; mds++)
/*  196:     */         {
/*  197: 258 */           BiomeGenBase metadataFound = cp.biomes[mds];
/*  198: 260 */           if (blockWood == metadataFound)
/*  199:     */           {
/*  200: 262 */             checkMetadata = true;
/*  201: 263 */             break;
/*  202:     */           }
/*  203:     */         }
/*  204: 267 */         if (!checkMetadata) {
/*  205: 269 */           return null;
/*  206:     */         }
/*  207:     */       }
/*  208: 273 */       boolean var14 = block instanceof BlockLog;
/*  209: 276 */       if ((side >= 0) && (cp.faces != 63))
/*  210:     */       {
/*  211: 278 */         int var15 = side;
/*  212: 280 */         if (var14) {
/*  213: 282 */           var15 = fixWoodSide(blockAccess, x, y, z, side, metadata);
/*  214:     */         }
/*  215: 285 */         if ((1 << var15 & cp.faces) == 0) {
/*  216: 287 */           return null;
/*  217:     */         }
/*  218:     */       }
/*  219: 291 */       int var15 = metadata;
/*  220: 293 */       if (var14) {
/*  221: 295 */         var15 = metadata & 0x3;
/*  222:     */       }
/*  223: 298 */       if (cp.metadatas != null)
/*  224:     */       {
/*  225: 300 */         int[] var17 = cp.metadatas;
/*  226: 301 */         boolean var16 = false;
/*  227: 303 */         for (int i = 0; i < var17.length; i++) {
/*  228: 305 */           if (var17[i] == var15)
/*  229:     */           {
/*  230: 307 */             var16 = true;
/*  231: 308 */             break;
/*  232:     */           }
/*  233:     */         }
/*  234: 312 */         if (!var16) {
/*  235: 314 */           return null;
/*  236:     */         }
/*  237:     */       }
/*  238: 318 */       switch (cp.method)
/*  239:     */       {
/*  240:     */       case 1: 
/*  241: 321 */         return getConnectedTextureCtm(cp, blockAccess, block, x, y, z, side, icon, metadata);
/*  242:     */       case 2: 
/*  243: 324 */         return getConnectedTextureHorizontal(cp, blockAccess, block, x, y, z, side, icon, metadata);
/*  244:     */       case 3: 
/*  245: 327 */         return getConnectedTextureTop(cp, blockAccess, block, x, y, z, side, icon, metadata);
/*  246:     */       case 4: 
/*  247: 330 */         return getConnectedTextureRandom(cp, x, y, z, side);
/*  248:     */       case 5: 
/*  249: 333 */         return getConnectedTextureRepeat(cp, x, y, z, side);
/*  250:     */       case 6: 
/*  251: 336 */         return getConnectedTextureVertical(cp, blockAccess, block, x, y, z, side, icon, metadata);
/*  252:     */       case 7: 
/*  253: 339 */         return getConnectedTextureFixed(cp);
/*  254:     */       }
/*  255: 342 */       return null;
/*  256:     */     }
/*  257: 347 */     return null;
/*  258:     */   }
/*  259:     */   
/*  260:     */   private static int fixWoodSide(IBlockAccess blockAccess, int x, int y, int z, int side, int metadata)
/*  261:     */   {
/*  262: 353 */     int orient = (metadata & 0xC) >> 2;
/*  263: 355 */     switch (orient)
/*  264:     */     {
/*  265:     */     case 0: 
/*  266: 358 */       return side;
/*  267:     */     case 1: 
/*  268: 361 */       switch (side)
/*  269:     */       {
/*  270:     */       case 0: 
/*  271: 364 */         return 4;
/*  272:     */       case 1: 
/*  273: 367 */         return 5;
/*  274:     */       case 2: 
/*  275:     */       case 3: 
/*  276:     */       default: 
/*  277: 372 */         return side;
/*  278:     */       case 4: 
/*  279: 375 */         return 1;
/*  280:     */       }
/*  281: 378 */       return 0;
/*  282:     */     case 2: 
/*  283: 382 */       switch (side)
/*  284:     */       {
/*  285:     */       case 0: 
/*  286: 385 */         return 2;
/*  287:     */       case 1: 
/*  288: 388 */         return 3;
/*  289:     */       case 2: 
/*  290: 391 */         return 1;
/*  291:     */       case 3: 
/*  292: 394 */         return 0;
/*  293:     */       }
/*  294: 397 */       return side;
/*  295:     */     case 3: 
/*  296: 401 */       return 2;
/*  297:     */     }
/*  298: 404 */     return side;
/*  299:     */   }
/*  300:     */   
/*  301:     */   private static IIcon getConnectedTextureRandom(ConnectedProperties cp, int x, int y, int z, int side)
/*  302:     */   {
/*  303: 410 */     if (cp.tileIcons.length == 1) {
/*  304: 412 */       return cp.tileIcons[0];
/*  305:     */     }
/*  306: 416 */     int face = side / cp.symmetry * cp.symmetry;
/*  307: 417 */     int rand = Config.getRandom(x, y, z, face) & 0x7FFFFFFF;
/*  308: 418 */     int index = 0;
/*  309: 420 */     if (cp.weights == null)
/*  310:     */     {
/*  311: 422 */       index = rand % cp.tileIcons.length;
/*  312:     */     }
/*  313:     */     else
/*  314:     */     {
/*  315: 426 */       int randWeight = rand % cp.sumAllWeights;
/*  316: 427 */       int[] sumWeights = cp.sumWeights;
/*  317: 429 */       for (int i = 0; i < sumWeights.length; i++) {
/*  318: 431 */         if (randWeight < sumWeights[i])
/*  319:     */         {
/*  320: 433 */           index = i;
/*  321: 434 */           break;
/*  322:     */         }
/*  323:     */       }
/*  324:     */     }
/*  325: 439 */     return cp.tileIcons[index];
/*  326:     */   }
/*  327:     */   
/*  328:     */   private static IIcon getConnectedTextureFixed(ConnectedProperties cp)
/*  329:     */   {
/*  330: 445 */     return cp.tileIcons[0];
/*  331:     */   }
/*  332:     */   
/*  333:     */   private static IIcon getConnectedTextureRepeat(ConnectedProperties cp, int x, int y, int z, int side)
/*  334:     */   {
/*  335: 450 */     if (cp.tileIcons.length == 1) {
/*  336: 452 */       return cp.tileIcons[0];
/*  337:     */     }
/*  338: 456 */     int nx = 0;
/*  339: 457 */     int ny = 0;
/*  340: 459 */     switch (side)
/*  341:     */     {
/*  342:     */     case 0: 
/*  343: 462 */       nx = x;
/*  344: 463 */       ny = z;
/*  345: 464 */       break;
/*  346:     */     case 1: 
/*  347: 467 */       nx = x;
/*  348: 468 */       ny = z;
/*  349: 469 */       break;
/*  350:     */     case 2: 
/*  351: 472 */       nx = -x - 1;
/*  352: 473 */       ny = -y;
/*  353: 474 */       break;
/*  354:     */     case 3: 
/*  355: 477 */       nx = x;
/*  356: 478 */       ny = -y;
/*  357: 479 */       break;
/*  358:     */     case 4: 
/*  359: 482 */       nx = z;
/*  360: 483 */       ny = -y;
/*  361: 484 */       break;
/*  362:     */     case 5: 
/*  363: 487 */       nx = -z - 1;
/*  364: 488 */       ny = -y;
/*  365:     */     }
/*  366: 491 */     nx = nx % cp.width;
/*  367: 492 */     ny %= cp.height;
/*  368: 494 */     if (nx < 0) {
/*  369: 496 */       nx += cp.width;
/*  370:     */     }
/*  371: 499 */     if (ny < 0) {
/*  372: 501 */       ny += cp.height;
/*  373:     */     }
/*  374: 504 */     int index = ny * cp.width + nx;
/*  375: 505 */     return cp.tileIcons[index];
/*  376:     */   }
/*  377:     */   
/*  378:     */   private static IIcon getConnectedTextureCtm(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata)
/*  379:     */   {
/*  380: 511 */     boolean[] borders = new boolean[6];
/*  381: 513 */     switch (side)
/*  382:     */     {
/*  383:     */     case 0: 
/*  384:     */     case 1: 
/*  385: 517 */       borders[0] = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*  386: 518 */       borders[1] = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  387: 519 */       borders[2] = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*  388: 520 */       borders[3] = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  389: 521 */       break;
/*  390:     */     case 2: 
/*  391: 524 */       borders[0] = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*  392: 525 */       borders[1] = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  393: 526 */       borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*  394: 527 */       borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*  395: 528 */       break;
/*  396:     */     case 3: 
/*  397: 531 */       borders[0] = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*  398: 532 */       borders[1] = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  399: 533 */       borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*  400: 534 */       borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*  401: 535 */       break;
/*  402:     */     case 4: 
/*  403: 538 */       borders[0] = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  404: 539 */       borders[1] = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*  405: 540 */       borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*  406: 541 */       borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*  407: 542 */       break;
/*  408:     */     case 5: 
/*  409: 545 */       borders[0] = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  410: 546 */       borders[1] = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*  411: 547 */       borders[2] = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*  412: 548 */       borders[3] = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*  413:     */     }
/*  414: 551 */     byte index = 0;
/*  415: 553 */     if ((borders[0] & (borders[1] != 0 ? 0 : 1) & (borders[2] != 0 ? 0 : 1) & (borders[3] != 0 ? 0 : 1)) != 0) {
/*  416: 555 */       index = 3;
/*  417: 557 */     } else if (((borders[0] != 0 ? 0 : 1) & borders[1] & (borders[2] != 0 ? 0 : 1) & (borders[3] != 0 ? 0 : 1)) != 0) {
/*  418: 559 */       index = 1;
/*  419: 561 */     } else if (((borders[0] != 0 ? 0 : 1) & (borders[1] != 0 ? 0 : 1) & borders[2] & (borders[3] != 0 ? 0 : 1)) != 0) {
/*  420: 563 */       index = 12;
/*  421: 565 */     } else if (((borders[0] != 0 ? 0 : 1) & (borders[1] != 0 ? 0 : 1) & (borders[2] != 0 ? 0 : 1) & borders[3]) != 0) {
/*  422: 567 */       index = 36;
/*  423: 569 */     } else if ((borders[0] & borders[1] & (borders[2] != 0 ? 0 : 1) & (borders[3] != 0 ? 0 : 1)) != 0) {
/*  424: 571 */       index = 2;
/*  425: 573 */     } else if (((borders[0] != 0 ? 0 : 1) & (borders[1] != 0 ? 0 : 1) & borders[2] & borders[3]) != 0) {
/*  426: 575 */       index = 24;
/*  427: 577 */     } else if ((borders[0] & (borders[1] != 0 ? 0 : 1) & borders[2] & (borders[3] != 0 ? 0 : 1)) != 0) {
/*  428: 579 */       index = 15;
/*  429: 581 */     } else if ((borders[0] & (borders[1] != 0 ? 0 : 1) & (borders[2] != 0 ? 0 : 1) & borders[3]) != 0) {
/*  430: 583 */       index = 39;
/*  431: 585 */     } else if (((borders[0] != 0 ? 0 : 1) & borders[1] & borders[2] & (borders[3] != 0 ? 0 : 1)) != 0) {
/*  432: 587 */       index = 13;
/*  433: 589 */     } else if (((borders[0] != 0 ? 0 : 1) & borders[1] & (borders[2] != 0 ? 0 : 1) & borders[3]) != 0) {
/*  434: 591 */       index = 37;
/*  435: 593 */     } else if (((borders[0] != 0 ? 0 : 1) & borders[1] & borders[2] & borders[3]) != 0) {
/*  436: 595 */       index = 25;
/*  437: 597 */     } else if ((borders[0] & (borders[1] != 0 ? 0 : 1) & borders[2] & borders[3]) != 0) {
/*  438: 599 */       index = 27;
/*  439: 601 */     } else if ((borders[0] & borders[1] & (borders[2] != 0 ? 0 : 1) & borders[3]) != 0) {
/*  440: 603 */       index = 38;
/*  441: 605 */     } else if ((borders[0] & borders[1] & borders[2] & (borders[3] != 0 ? 0 : 1)) != 0) {
/*  442: 607 */       index = 14;
/*  443: 609 */     } else if ((borders[0] & borders[1] & borders[2] & borders[3]) != 0) {
/*  444: 611 */       index = 26;
/*  445:     */     }
/*  446: 614 */     if (index == 0) {
/*  447: 616 */       return cp.tileIcons[index];
/*  448:     */     }
/*  449: 618 */     if (!Config.isConnectedTexturesFancy()) {
/*  450: 620 */       return cp.tileIcons[index];
/*  451:     */     }
/*  452: 624 */     boolean[] edges = new boolean[6];
/*  453: 626 */     switch (side)
/*  454:     */     {
/*  455:     */     case 0: 
/*  456:     */     case 1: 
/*  457: 630 */       edges[0] = (isNeighbour(cp, blockAccess, block, x + 1, y, z + 1, side, icon, metadata) ? 0 : true);
/*  458: 631 */       edges[1] = (isNeighbour(cp, blockAccess, block, x - 1, y, z + 1, side, icon, metadata) ? 0 : true);
/*  459: 632 */       edges[2] = (isNeighbour(cp, blockAccess, block, x + 1, y, z - 1, side, icon, metadata) ? 0 : true);
/*  460: 633 */       edges[3] = (isNeighbour(cp, blockAccess, block, x - 1, y, z - 1, side, icon, metadata) ? 0 : true);
/*  461: 634 */       break;
/*  462:     */     case 2: 
/*  463: 637 */       edges[0] = (isNeighbour(cp, blockAccess, block, x + 1, y - 1, z, side, icon, metadata) ? 0 : true);
/*  464: 638 */       edges[1] = (isNeighbour(cp, blockAccess, block, x - 1, y - 1, z, side, icon, metadata) ? 0 : true);
/*  465: 639 */       edges[2] = (isNeighbour(cp, blockAccess, block, x + 1, y + 1, z, side, icon, metadata) ? 0 : true);
/*  466: 640 */       edges[3] = (isNeighbour(cp, blockAccess, block, x - 1, y + 1, z, side, icon, metadata) ? 0 : true);
/*  467: 641 */       break;
/*  468:     */     case 3: 
/*  469: 644 */       edges[0] = (isNeighbour(cp, blockAccess, block, x + 1, y - 1, z, side, icon, metadata) ? 0 : true);
/*  470: 645 */       edges[1] = (isNeighbour(cp, blockAccess, block, x - 1, y - 1, z, side, icon, metadata) ? 0 : true);
/*  471: 646 */       edges[2] = (isNeighbour(cp, blockAccess, block, x + 1, y + 1, z, side, icon, metadata) ? 0 : true);
/*  472: 647 */       edges[3] = (isNeighbour(cp, blockAccess, block, x - 1, y + 1, z, side, icon, metadata) ? 0 : true);
/*  473: 648 */       break;
/*  474:     */     case 4: 
/*  475: 651 */       edges[0] = (isNeighbour(cp, blockAccess, block, x, y - 1, z + 1, side, icon, metadata) ? 0 : true);
/*  476: 652 */       edges[1] = (isNeighbour(cp, blockAccess, block, x, y - 1, z - 1, side, icon, metadata) ? 0 : true);
/*  477: 653 */       edges[2] = (isNeighbour(cp, blockAccess, block, x, y + 1, z + 1, side, icon, metadata) ? 0 : true);
/*  478: 654 */       edges[3] = (isNeighbour(cp, blockAccess, block, x, y + 1, z - 1, side, icon, metadata) ? 0 : true);
/*  479: 655 */       break;
/*  480:     */     case 5: 
/*  481: 658 */       edges[0] = (isNeighbour(cp, blockAccess, block, x, y - 1, z + 1, side, icon, metadata) ? 0 : true);
/*  482: 659 */       edges[1] = (isNeighbour(cp, blockAccess, block, x, y - 1, z - 1, side, icon, metadata) ? 0 : true);
/*  483: 660 */       edges[2] = (isNeighbour(cp, blockAccess, block, x, y + 1, z + 1, side, icon, metadata) ? 0 : true);
/*  484: 661 */       edges[3] = (isNeighbour(cp, blockAccess, block, x, y + 1, z - 1, side, icon, metadata) ? 0 : true);
/*  485:     */     }
/*  486: 664 */     if ((index == 13) && (edges[0] != 0)) {
/*  487: 666 */       index = 4;
/*  488: 668 */     } else if ((index == 15) && (edges[1] != 0)) {
/*  489: 670 */       index = 5;
/*  490: 672 */     } else if ((index == 37) && (edges[2] != 0)) {
/*  491: 674 */       index = 16;
/*  492: 676 */     } else if ((index == 39) && (edges[3] != 0)) {
/*  493: 678 */       index = 17;
/*  494: 680 */     } else if ((index == 14) && (edges[0] != 0) && (edges[1] != 0)) {
/*  495: 682 */       index = 7;
/*  496: 684 */     } else if ((index == 25) && (edges[0] != 0) && (edges[2] != 0)) {
/*  497: 686 */       index = 6;
/*  498: 688 */     } else if ((index == 27) && (edges[3] != 0) && (edges[1] != 0)) {
/*  499: 690 */       index = 19;
/*  500: 692 */     } else if ((index == 38) && (edges[3] != 0) && (edges[2] != 0)) {
/*  501: 694 */       index = 18;
/*  502: 696 */     } else if ((index == 14) && (edges[0] == 0) && (edges[1] != 0)) {
/*  503: 698 */       index = 31;
/*  504: 700 */     } else if ((index == 25) && (edges[0] != 0) && (edges[2] == 0)) {
/*  505: 702 */       index = 30;
/*  506: 704 */     } else if ((index == 27) && (edges[3] == 0) && (edges[1] != 0)) {
/*  507: 706 */       index = 41;
/*  508: 708 */     } else if ((index == 38) && (edges[3] != 0) && (edges[2] == 0)) {
/*  509: 710 */       index = 40;
/*  510: 712 */     } else if ((index == 14) && (edges[0] != 0) && (edges[1] == 0)) {
/*  511: 714 */       index = 29;
/*  512: 716 */     } else if ((index == 25) && (edges[0] == 0) && (edges[2] != 0)) {
/*  513: 718 */       index = 28;
/*  514: 720 */     } else if ((index == 27) && (edges[3] != 0) && (edges[1] == 0)) {
/*  515: 722 */       index = 43;
/*  516: 724 */     } else if ((index == 38) && (edges[3] == 0) && (edges[2] != 0)) {
/*  517: 726 */       index = 42;
/*  518: 728 */     } else if ((index == 26) && (edges[0] != 0) && (edges[1] != 0) && (edges[2] != 0) && (edges[3] != 0)) {
/*  519: 730 */       index = 46;
/*  520: 732 */     } else if ((index == 26) && (edges[0] == 0) && (edges[1] != 0) && (edges[2] != 0) && (edges[3] != 0)) {
/*  521: 734 */       index = 9;
/*  522: 736 */     } else if ((index == 26) && (edges[0] != 0) && (edges[1] == 0) && (edges[2] != 0) && (edges[3] != 0)) {
/*  523: 738 */       index = 21;
/*  524: 740 */     } else if ((index == 26) && (edges[0] != 0) && (edges[1] != 0) && (edges[2] == 0) && (edges[3] != 0)) {
/*  525: 742 */       index = 8;
/*  526: 744 */     } else if ((index == 26) && (edges[0] != 0) && (edges[1] != 0) && (edges[2] != 0) && (edges[3] == 0)) {
/*  527: 746 */       index = 20;
/*  528: 748 */     } else if ((index == 26) && (edges[0] != 0) && (edges[1] != 0) && (edges[2] == 0) && (edges[3] == 0)) {
/*  529: 750 */       index = 11;
/*  530: 752 */     } else if ((index == 26) && (edges[0] == 0) && (edges[1] == 0) && (edges[2] != 0) && (edges[3] != 0)) {
/*  531: 754 */       index = 22;
/*  532: 756 */     } else if ((index == 26) && (edges[0] == 0) && (edges[1] != 0) && (edges[2] == 0) && (edges[3] != 0)) {
/*  533: 758 */       index = 23;
/*  534: 760 */     } else if ((index == 26) && (edges[0] != 0) && (edges[1] == 0) && (edges[2] != 0) && (edges[3] == 0)) {
/*  535: 762 */       index = 10;
/*  536: 764 */     } else if ((index == 26) && (edges[0] != 0) && (edges[1] == 0) && (edges[2] == 0) && (edges[3] != 0)) {
/*  537: 766 */       index = 34;
/*  538: 768 */     } else if ((index == 26) && (edges[0] == 0) && (edges[1] != 0) && (edges[2] != 0) && (edges[3] == 0)) {
/*  539: 770 */       index = 35;
/*  540: 772 */     } else if ((index == 26) && (edges[0] != 0) && (edges[1] == 0) && (edges[2] == 0) && (edges[3] == 0)) {
/*  541: 774 */       index = 32;
/*  542: 776 */     } else if ((index == 26) && (edges[0] == 0) && (edges[1] != 0) && (edges[2] == 0) && (edges[3] == 0)) {
/*  543: 778 */       index = 33;
/*  544: 780 */     } else if ((index == 26) && (edges[0] == 0) && (edges[1] == 0) && (edges[2] != 0) && (edges[3] == 0)) {
/*  545: 782 */       index = 44;
/*  546: 784 */     } else if ((index == 26) && (edges[0] == 0) && (edges[1] == 0) && (edges[2] == 0) && (edges[3] != 0)) {
/*  547: 786 */       index = 45;
/*  548:     */     }
/*  549: 789 */     return cp.tileIcons[index];
/*  550:     */   }
/*  551:     */   
/*  552:     */   private static boolean isNeighbour(ConnectedProperties cp, IBlockAccess iblockaccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata)
/*  553:     */   {
/*  554: 795 */     Block neighbourBlock = iblockaccess.getBlock(x, y, z);
/*  555: 797 */     if (cp.connect == 2)
/*  556:     */     {
/*  557: 799 */       if (neighbourBlock == null) {
/*  558: 801 */         return false;
/*  559:     */       }
/*  560:     */       IIcon neighbourIcon;
/*  561:     */       IIcon neighbourIcon;
/*  562: 807 */       if (side >= 0) {
/*  563: 809 */         neighbourIcon = neighbourBlock.getIcon(iblockaccess, x, y, z, side);
/*  564:     */       } else {
/*  565: 813 */         neighbourIcon = neighbourBlock.getIcon(iblockaccess, x, y, z, 1);
/*  566:     */       }
/*  567: 816 */       return neighbourIcon == icon;
/*  568:     */     }
/*  569: 821 */     return neighbourBlock != null;
/*  570:     */   }
/*  571:     */   
/*  572:     */   private static IIcon getConnectedTextureHorizontal(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata)
/*  573:     */   {
/*  574: 827 */     if ((side != 0) && (side != 1))
/*  575:     */     {
/*  576: 829 */       boolean left = false;
/*  577: 830 */       boolean right = false;
/*  578: 832 */       switch (side)
/*  579:     */       {
/*  580:     */       case 2: 
/*  581: 835 */         left = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  582: 836 */         right = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*  583: 837 */         break;
/*  584:     */       case 3: 
/*  585: 840 */         left = isNeighbour(cp, blockAccess, block, x - 1, y, z, side, icon, metadata);
/*  586: 841 */         right = isNeighbour(cp, blockAccess, block, x + 1, y, z, side, icon, metadata);
/*  587: 842 */         break;
/*  588:     */       case 4: 
/*  589: 845 */         left = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  590: 846 */         right = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*  591: 847 */         break;
/*  592:     */       case 5: 
/*  593: 850 */         left = isNeighbour(cp, blockAccess, block, x, y, z + 1, side, icon, metadata);
/*  594: 851 */         right = isNeighbour(cp, blockAccess, block, x, y, z - 1, side, icon, metadata);
/*  595:     */       }
/*  596: 854 */       boolean index = true;
/*  597:     */       byte index1;
/*  598:     */       byte index1;
/*  599: 857 */       if (left)
/*  600:     */       {
/*  601:     */         byte index1;
/*  602: 859 */         if (right) {
/*  603: 861 */           index1 = 1;
/*  604:     */         } else {
/*  605: 865 */           index1 = 2;
/*  606:     */         }
/*  607:     */       }
/*  608:     */       else
/*  609:     */       {
/*  610:     */         byte index1;
/*  611: 868 */         if (right) {
/*  612: 870 */           index1 = 0;
/*  613:     */         } else {
/*  614: 874 */           index1 = 3;
/*  615:     */         }
/*  616:     */       }
/*  617: 877 */       return cp.tileIcons[index1];
/*  618:     */     }
/*  619: 881 */     return null;
/*  620:     */   }
/*  621:     */   
/*  622:     */   private static IIcon getConnectedTextureVertical(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata)
/*  623:     */   {
/*  624: 887 */     if ((side != 0) && (side != 1))
/*  625:     */     {
/*  626: 889 */       boolean bottom = isNeighbour(cp, blockAccess, block, x, y - 1, z, side, icon, metadata);
/*  627: 890 */       boolean top = isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata);
/*  628: 891 */       boolean index = true;
/*  629:     */       byte index1;
/*  630:     */       byte index1;
/*  631: 894 */       if (bottom)
/*  632:     */       {
/*  633:     */         byte index1;
/*  634: 896 */         if (top) {
/*  635: 898 */           index1 = 1;
/*  636:     */         } else {
/*  637: 902 */           index1 = 2;
/*  638:     */         }
/*  639:     */       }
/*  640:     */       else
/*  641:     */       {
/*  642:     */         byte index1;
/*  643: 905 */         if (top) {
/*  644: 907 */           index1 = 0;
/*  645:     */         } else {
/*  646: 911 */           index1 = 3;
/*  647:     */         }
/*  648:     */       }
/*  649: 914 */       return cp.tileIcons[index1];
/*  650:     */     }
/*  651: 918 */     return null;
/*  652:     */   }
/*  653:     */   
/*  654:     */   private static IIcon getConnectedTextureTop(ConnectedProperties cp, IBlockAccess blockAccess, Block block, int x, int y, int z, int side, IIcon icon, int metadata)
/*  655:     */   {
/*  656: 924 */     return (side != 0) && (side != 1) ? null : isNeighbour(cp, blockAccess, block, x, y + 1, z, side, icon, metadata) ? cp.tileIcons[0] : null;
/*  657:     */   }
/*  658:     */   
/*  659:     */   public static void updateIcons(TextureMap textureMap)
/*  660:     */   {
/*  661: 929 */     blockProperties = null;
/*  662: 930 */     tileProperties = null;
/*  663: 931 */     IResourcePack[] rps = Config.getResourcePacks();
/*  664: 933 */     for (int i = rps.length - 1; i >= 0; i--)
/*  665:     */     {
/*  666: 935 */       IResourcePack rp = rps[i];
/*  667: 936 */       updateIcons(textureMap, rp);
/*  668:     */     }
/*  669: 939 */     updateIcons(textureMap, Config.getDefaultResourcePack());
/*  670:     */   }
/*  671:     */   
/*  672:     */   public static void updateIcons(TextureMap textureMap, IResourcePack rp)
/*  673:     */   {
/*  674: 944 */     String[] names = collectFiles(rp, "mcpatcher/ctm/", ".properties");
/*  675: 945 */     Arrays.sort(names);
/*  676: 946 */     List tileList = makePropertyList(tileProperties);
/*  677: 947 */     List blockList = makePropertyList(blockProperties);
/*  678: 949 */     for (int i = 0; i < names.length; i++)
/*  679:     */     {
/*  680: 951 */       String name = names[i];
/*  681: 952 */       Config.dbg("ConnectedTextures: " + name);
/*  682:     */       try
/*  683:     */       {
/*  684: 956 */         ResourceLocation e = new ResourceLocation(name);
/*  685: 957 */         InputStream in = rp.getInputStream(e);
/*  686: 959 */         if (in == null)
/*  687:     */         {
/*  688: 961 */           Config.warn("ConnectedTextures file not found: " + name);
/*  689:     */         }
/*  690:     */         else
/*  691:     */         {
/*  692: 965 */           Properties props = new Properties();
/*  693: 966 */           props.load(in);
/*  694: 967 */           ConnectedProperties cp = new ConnectedProperties(props, name);
/*  695: 969 */           if (cp.isValid(name))
/*  696:     */           {
/*  697: 971 */             cp.updateIcons(textureMap);
/*  698: 972 */             addToTileList(cp, tileList);
/*  699: 973 */             addToBlockList(cp, blockList);
/*  700:     */           }
/*  701:     */         }
/*  702:     */       }
/*  703:     */       catch (FileNotFoundException var11)
/*  704:     */       {
/*  705: 979 */         Config.warn("ConnectedTextures file not found: " + name);
/*  706:     */       }
/*  707:     */       catch (IOException var12)
/*  708:     */       {
/*  709: 983 */         var12.printStackTrace();
/*  710:     */       }
/*  711:     */     }
/*  712: 987 */     blockProperties = propertyListToArray(blockList);
/*  713: 988 */     tileProperties = propertyListToArray(tileList);
/*  714: 989 */     multipass = detectMultipass();
/*  715: 990 */     Config.dbg("Multipass connected textures: " + multipass);
/*  716:     */   }
/*  717:     */   
/*  718:     */   private static List makePropertyList(ConnectedProperties[][] propsArr)
/*  719:     */   {
/*  720: 995 */     ArrayList list = new ArrayList();
/*  721: 997 */     if (propsArr != null) {
/*  722: 999 */       for (int i = 0; i < propsArr.length; i++)
/*  723:     */       {
/*  724:1001 */         ConnectedProperties[] props = propsArr[i];
/*  725:1002 */         ArrayList propList = null;
/*  726:1004 */         if (props != null) {
/*  727:1006 */           propList = new ArrayList(Arrays.asList(props));
/*  728:     */         }
/*  729:1009 */         list.add(propList);
/*  730:     */       }
/*  731:     */     }
/*  732:1013 */     return list;
/*  733:     */   }
/*  734:     */   
/*  735:     */   private static boolean detectMultipass()
/*  736:     */   {
/*  737:1018 */     ArrayList propList = new ArrayList();
/*  738:1022 */     for (int props = 0; props < tileProperties.length; props++)
/*  739:     */     {
/*  740:1024 */       ConnectedProperties[] matchIconSet = tileProperties[props];
/*  741:1026 */       if (matchIconSet != null) {
/*  742:1028 */         propList.addAll(Arrays.asList(matchIconSet));
/*  743:     */       }
/*  744:     */     }
/*  745:1032 */     for (props = 0; props < blockProperties.length; props++)
/*  746:     */     {
/*  747:1034 */       ConnectedProperties[] matchIconSet = blockProperties[props];
/*  748:1036 */       if (matchIconSet != null) {
/*  749:1038 */         propList.addAll(Arrays.asList(matchIconSet));
/*  750:     */       }
/*  751:     */     }
/*  752:1042 */     ConnectedProperties[] var6 = (ConnectedProperties[])propList.toArray(new ConnectedProperties[propList.size()]);
/*  753:1043 */     HashSet var7 = new HashSet();
/*  754:1044 */     HashSet tileIconSet = new HashSet();
/*  755:1046 */     for (int i = 0; i < var6.length; i++)
/*  756:     */     {
/*  757:1048 */       ConnectedProperties cp = var6[i];
/*  758:1050 */       if (cp.matchTileIcons != null) {
/*  759:1052 */         var7.addAll(Arrays.asList(cp.matchTileIcons));
/*  760:     */       }
/*  761:1055 */       if (cp.tileIcons != null) {
/*  762:1057 */         tileIconSet.addAll(Arrays.asList(cp.tileIcons));
/*  763:     */       }
/*  764:     */     }
/*  765:1061 */     var7.retainAll(tileIconSet);
/*  766:1062 */     return !var7.isEmpty();
/*  767:     */   }
/*  768:     */   
/*  769:     */   private static ConnectedProperties[][] propertyListToArray(List list)
/*  770:     */   {
/*  771:1067 */     ConnectedProperties[][] propArr = new ConnectedProperties[list.size()][];
/*  772:1069 */     for (int i = 0; i < list.size(); i++)
/*  773:     */     {
/*  774:1071 */       List subList = (List)list.get(i);
/*  775:1073 */       if (subList != null)
/*  776:     */       {
/*  777:1075 */         ConnectedProperties[] subArr = (ConnectedProperties[])subList.toArray(new ConnectedProperties[subList.size()]);
/*  778:1076 */         propArr[i] = subArr;
/*  779:     */       }
/*  780:     */     }
/*  781:1080 */     return propArr;
/*  782:     */   }
/*  783:     */   
/*  784:     */   private static void addToTileList(ConnectedProperties cp, List tileList)
/*  785:     */   {
/*  786:1085 */     if (cp.matchTileIcons != null) {
/*  787:1087 */       for (int i = 0; i < cp.matchTileIcons.length; i++)
/*  788:     */       {
/*  789:1089 */         IIcon icon = cp.matchTileIcons[i];
/*  790:1091 */         if (!(icon instanceof TextureAtlasSprite))
/*  791:     */         {
/*  792:1093 */           Config.warn("IIcon is not TextureAtlasSprite: " + icon + ", name: " + icon.getIconName());
/*  793:     */         }
/*  794:     */         else
/*  795:     */         {
/*  796:1097 */           TextureAtlasSprite ts = (TextureAtlasSprite)icon;
/*  797:1098 */           int tileId = ts.getIndexInMap();
/*  798:1100 */           if (tileId < 0) {
/*  799:1102 */             Config.warn("Invalid tile ID: " + tileId + ", icon: " + ts.getIconName());
/*  800:     */           } else {
/*  801:1106 */             addToList(cp, tileList, tileId);
/*  802:     */           }
/*  803:     */         }
/*  804:     */       }
/*  805:     */     }
/*  806:     */   }
/*  807:     */   
/*  808:     */   private static void addToBlockList(ConnectedProperties cp, List blockList)
/*  809:     */   {
/*  810:1115 */     if (cp.matchBlocks != null) {
/*  811:1117 */       for (int i = 0; i < cp.matchBlocks.length; i++)
/*  812:     */       {
/*  813:1119 */         int blockId = cp.matchBlocks[i];
/*  814:1121 */         if (blockId < 0) {
/*  815:1123 */           Config.warn("Invalid block ID: " + blockId);
/*  816:     */         } else {
/*  817:1127 */           addToList(cp, blockList, blockId);
/*  818:     */         }
/*  819:     */       }
/*  820:     */     }
/*  821:     */   }
/*  822:     */   
/*  823:     */   private static void addToList(ConnectedProperties cp, List list, int id)
/*  824:     */   {
/*  825:1135 */     while (id >= list.size()) {
/*  826:1137 */       list.add(null);
/*  827:     */     }
/*  828:1140 */     Object subList = (List)list.get(id);
/*  829:1142 */     if (subList == null)
/*  830:     */     {
/*  831:1144 */       subList = new ArrayList();
/*  832:1145 */       list.set(id, subList);
/*  833:     */     }
/*  834:1148 */     ((List)subList).add(cp);
/*  835:     */   }
/*  836:     */   
/*  837:     */   private static String[] collectFiles(IResourcePack rp, String prefix, String suffix)
/*  838:     */   {
/*  839:1153 */     if ((rp instanceof DefaultResourcePack)) {
/*  840:1155 */       return collectFilesDefault(rp);
/*  841:     */     }
/*  842:1157 */     if (!(rp instanceof AbstractResourcePack)) {
/*  843:1159 */       return new String[0];
/*  844:     */     }
/*  845:1163 */     AbstractResourcePack arp = (AbstractResourcePack)rp;
/*  846:1164 */     File tpFile = ResourceUtils.getResourcePackFile(arp);
/*  847:1165 */     return tpFile.isFile() ? collectFilesZIP(tpFile, prefix, suffix) : tpFile.isDirectory() ? collectFilesFolder(tpFile, "", prefix, suffix) : tpFile == null ? new String[0] : new String[0];
/*  848:     */   }
/*  849:     */   
/*  850:     */   private static String[] collectFilesDefault(IResourcePack rp)
/*  851:     */   {
/*  852:1171 */     ArrayList list = new ArrayList();
/*  853:1172 */     String[] names = getDefaultCtmPaths();
/*  854:1174 */     for (int nameArr = 0; nameArr < names.length; nameArr++)
/*  855:     */     {
/*  856:1176 */       String name = names[nameArr];
/*  857:1177 */       ResourceLocation loc = new ResourceLocation(name);
/*  858:1179 */       if (rp.resourceExists(loc)) {
/*  859:1181 */         list.add(name);
/*  860:     */       }
/*  861:     */     }
/*  862:1185 */     String[] var6 = (String[])list.toArray(new String[list.size()]);
/*  863:1186 */     return var6;
/*  864:     */   }
/*  865:     */   
/*  866:     */   private static String[] getDefaultCtmPaths()
/*  867:     */   {
/*  868:1191 */     ArrayList list = new ArrayList();
/*  869:1192 */     String defPath = "mcpatcher/ctm/default/";
/*  870:1194 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png")))
/*  871:     */     {
/*  872:1196 */       list.add(defPath + "glass.properties");
/*  873:1197 */       list.add(defPath + "glasspane.properties");
/*  874:     */     }
/*  875:1200 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png"))) {
/*  876:1202 */       list.add(defPath + "bookshelf.properties");
/*  877:     */     }
/*  878:1205 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png"))) {
/*  879:1207 */       list.add(defPath + "sandstone.properties");
/*  880:     */     }
/*  881:1210 */     String[] colors = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };
/*  882:1212 */     for (int paths = 0; paths < colors.length; paths++)
/*  883:     */     {
/*  884:1214 */       String color = colors[paths];
/*  885:1216 */       if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + color + ".png")))
/*  886:     */       {
/*  887:1218 */         list.add(defPath + paths + "_glass_" + color + "/glass_" + color + ".properties");
/*  888:1219 */         list.add(defPath + paths + "_glass_" + color + "/glass_pane_" + color + ".properties");
/*  889:     */       }
/*  890:     */     }
/*  891:1223 */     String[] var5 = (String[])list.toArray(new String[list.size()]);
/*  892:1224 */     return var5;
/*  893:     */   }
/*  894:     */   
/*  895:     */   private static String[] collectFilesFolder(File tpFile, String basePath, String prefix, String suffix)
/*  896:     */   {
/*  897:1229 */     ArrayList list = new ArrayList();
/*  898:1230 */     String prefixAssets = "assets/minecraft/";
/*  899:1231 */     File[] files = tpFile.listFiles();
/*  900:1233 */     if (files == null) {
/*  901:1235 */       return new String[0];
/*  902:     */     }
/*  903:1239 */     for (int names = 0; names < files.length; names++)
/*  904:     */     {
/*  905:1241 */       File file = files[names];
/*  906:1244 */       if (file.isFile())
/*  907:     */       {
/*  908:1246 */         String dirPath = basePath + file.getName();
/*  909:1248 */         if (dirPath.startsWith(prefixAssets))
/*  910:     */         {
/*  911:1250 */           dirPath = dirPath.substring(prefixAssets.length());
/*  912:1252 */           if ((dirPath.startsWith(prefix)) && (dirPath.endsWith(suffix))) {
/*  913:1254 */             list.add(dirPath);
/*  914:     */           }
/*  915:     */         }
/*  916:     */       }
/*  917:1258 */       else if (file.isDirectory())
/*  918:     */       {
/*  919:1260 */         String dirPath = basePath + file.getName() + "/";
/*  920:1261 */         String[] names1 = collectFilesFolder(file, dirPath, prefix, suffix);
/*  921:1263 */         for (int n = 0; n < names1.length; n++)
/*  922:     */         {
/*  923:1265 */           String name = names1[n];
/*  924:1266 */           list.add(name);
/*  925:     */         }
/*  926:     */       }
/*  927:     */     }
/*  928:1271 */     String[] var13 = (String[])list.toArray(new String[list.size()]);
/*  929:1272 */     return var13;
/*  930:     */   }
/*  931:     */   
/*  932:     */   private static String[] collectFilesZIP(File tpFile, String prefix, String suffix)
/*  933:     */   {
/*  934:1278 */     ArrayList list = new ArrayList();
/*  935:1279 */     String prefixAssets = "assets/minecraft/";
/*  936:     */     try
/*  937:     */     {
/*  938:1283 */       ZipFile e = new ZipFile(tpFile);
/*  939:1284 */       Enumeration en = e.entries();
/*  940:1286 */       while (en.hasMoreElements())
/*  941:     */       {
/*  942:1288 */         ZipEntry names = (ZipEntry)en.nextElement();
/*  943:1289 */         String name = names.getName();
/*  944:1291 */         if (name.startsWith(prefixAssets))
/*  945:     */         {
/*  946:1293 */           name = name.substring(prefixAssets.length());
/*  947:1295 */           if ((name.startsWith(prefix)) && (name.endsWith(suffix))) {
/*  948:1297 */             list.add(name);
/*  949:     */           }
/*  950:     */         }
/*  951:     */       }
/*  952:1302 */       e.close();
/*  953:1303 */       return (String[])list.toArray(new String[list.size()]);
/*  954:     */     }
/*  955:     */     catch (IOException var9)
/*  956:     */     {
/*  957:1308 */       var9.printStackTrace();
/*  958:     */     }
/*  959:1309 */     return new String[0];
/*  960:     */   }
/*  961:     */   
/*  962:     */   public static int getPaneTextureIndex(boolean linkP, boolean linkN, boolean linkYp, boolean linkYn)
/*  963:     */   {
/*  964:1315 */     return linkYn ? 16 : linkYp ? 48 : linkYn ? 32 : (!linkN) && (linkP) ? 1 : linkYn ? 17 : linkYp ? 49 : linkYn ? 33 : (linkN) && (!linkP) ? 3 : linkYn ? 19 : linkYp ? 51 : linkYn ? 35 : (linkN) && (linkP) ? 2 : linkYn ? 18 : linkYp ? 50 : linkYn ? 34 : 0;
/*  965:     */   }
/*  966:     */   
/*  967:     */   public static int getReversePaneTextureIndex(int texNum)
/*  968:     */   {
/*  969:1320 */     int col = texNum % 16;
/*  970:1321 */     return col == 3 ? texNum - 2 : col == 1 ? texNum + 2 : texNum;
/*  971:     */   }
/*  972:     */   
/*  973:     */   public static IIcon getCtmTexture(ConnectedProperties cp, int ctmIndex, IIcon icon)
/*  974:     */   {
/*  975:1326 */     if (cp.method != 1) {
/*  976:1328 */       return icon;
/*  977:     */     }
/*  978:1330 */     if ((ctmIndex >= 0) && (ctmIndex < ctmIndexes.length))
/*  979:     */     {
/*  980:1332 */       int index = ctmIndexes[ctmIndex];
/*  981:1333 */       IIcon[] ctmIcons = cp.tileIcons;
/*  982:1334 */       return (index >= 0) && (index < ctmIcons.length) ? ctmIcons[index] : icon;
/*  983:     */     }
/*  984:1338 */     return icon;
/*  985:     */   }
/*  986:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.ConnectedTextures
 * JD-Core Version:    0.7.0.1
 */