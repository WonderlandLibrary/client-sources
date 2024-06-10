/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Properties;
/*   5:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*   6:    */ import net.minecraft.util.IIcon;
/*   7:    */ import net.minecraft.util.ResourceLocation;
/*   8:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   9:    */ 
/*  10:    */ public class ConnectedProperties
/*  11:    */ {
/*  12: 12 */   public String name = null;
/*  13: 13 */   public String basePath = null;
/*  14: 14 */   public int[] matchBlocks = null;
/*  15: 15 */   public String[] matchTiles = null;
/*  16: 16 */   public int method = 0;
/*  17: 17 */   public String[] tiles = null;
/*  18: 18 */   public int connect = 0;
/*  19: 19 */   public int faces = 63;
/*  20: 20 */   public int[] metadatas = null;
/*  21: 21 */   public BiomeGenBase[] biomes = null;
/*  22: 22 */   public int minHeight = 0;
/*  23: 23 */   public int maxHeight = 1024;
/*  24: 24 */   public int renderPass = 0;
/*  25: 25 */   public boolean innerSeams = false;
/*  26: 26 */   public int width = 0;
/*  27: 27 */   public int height = 0;
/*  28: 28 */   public int[] weights = null;
/*  29: 29 */   public int symmetry = 1;
/*  30: 30 */   public int[] sumWeights = null;
/*  31: 31 */   public int sumAllWeights = 0;
/*  32: 32 */   public IIcon[] matchTileIcons = null;
/*  33: 33 */   public IIcon[] tileIcons = null;
/*  34:    */   public static final int METHOD_NONE = 0;
/*  35:    */   public static final int METHOD_CTM = 1;
/*  36:    */   public static final int METHOD_HORIZONTAL = 2;
/*  37:    */   public static final int METHOD_TOP = 3;
/*  38:    */   public static final int METHOD_RANDOM = 4;
/*  39:    */   public static final int METHOD_REPEAT = 5;
/*  40:    */   public static final int METHOD_VERTICAL = 6;
/*  41:    */   public static final int METHOD_FIXED = 7;
/*  42:    */   public static final int CONNECT_NONE = 0;
/*  43:    */   public static final int CONNECT_BLOCK = 1;
/*  44:    */   public static final int CONNECT_TILE = 2;
/*  45:    */   public static final int CONNECT_MATERIAL = 3;
/*  46:    */   public static final int CONNECT_UNKNOWN = 128;
/*  47:    */   public static final int FACE_BOTTOM = 1;
/*  48:    */   public static final int FACE_TOP = 2;
/*  49:    */   public static final int FACE_EAST = 4;
/*  50:    */   public static final int FACE_WEST = 8;
/*  51:    */   public static final int FACE_NORTH = 16;
/*  52:    */   public static final int FACE_SOUTH = 32;
/*  53:    */   public static final int FACE_SIDES = 60;
/*  54:    */   public static final int FACE_ALL = 63;
/*  55:    */   public static final int FACE_UNKNOWN = 128;
/*  56:    */   public static final int SYMMETRY_NONE = 1;
/*  57:    */   public static final int SYMMETRY_OPPOSITE = 2;
/*  58:    */   public static final int SYMMETRY_ALL = 6;
/*  59:    */   public static final int SYMMETRY_UNKNOWN = 128;
/*  60:    */   
/*  61:    */   public ConnectedProperties(Properties props, String path)
/*  62:    */   {
/*  63: 63 */     this.name = parseName(path);
/*  64: 64 */     this.basePath = parseBasePath(path);
/*  65: 65 */     this.matchBlocks = parseInts(props.getProperty("matchBlocks"));
/*  66: 66 */     this.matchTiles = parseMatchTiles(props.getProperty("matchTiles"));
/*  67: 67 */     this.method = parseMethod(props.getProperty("method"));
/*  68: 68 */     this.tiles = parseTileNames(props.getProperty("tiles"));
/*  69: 69 */     this.connect = parseConnect(props.getProperty("connect"));
/*  70: 70 */     this.faces = parseFaces(props.getProperty("faces"));
/*  71: 71 */     this.metadatas = parseInts(props.getProperty("metadata"));
/*  72: 72 */     this.biomes = parseBiomes(props.getProperty("biomes"));
/*  73: 73 */     this.minHeight = parseInt(props.getProperty("minHeight"), -1);
/*  74: 74 */     this.maxHeight = parseInt(props.getProperty("maxHeight"), 1024);
/*  75: 75 */     this.renderPass = parseInt(props.getProperty("renderPass"));
/*  76: 76 */     this.innerSeams = parseBoolean(props.getProperty("innerSeams"));
/*  77: 77 */     this.width = parseInt(props.getProperty("width"));
/*  78: 78 */     this.height = parseInt(props.getProperty("height"));
/*  79: 79 */     this.weights = parseInts(props.getProperty("weights"));
/*  80: 80 */     this.symmetry = parseSymmetry(props.getProperty("symmetry"));
/*  81:    */   }
/*  82:    */   
/*  83:    */   private String[] parseMatchTiles(String str)
/*  84:    */   {
/*  85: 85 */     if (str == null) {
/*  86: 87 */       return null;
/*  87:    */     }
/*  88: 91 */     String[] names = Config.tokenize(str, " ");
/*  89: 93 */     for (int i = 0; i < names.length; i++)
/*  90:    */     {
/*  91: 95 */       String iconName = names[i];
/*  92: 97 */       if (iconName.endsWith(".png")) {
/*  93: 99 */         iconName = iconName.substring(0, iconName.length() - 4);
/*  94:    */       }
/*  95:102 */       iconName = TextureUtils.fixResourcePath(iconName, this.basePath);
/*  96:103 */       names[i] = iconName;
/*  97:    */     }
/*  98:106 */     return names;
/*  99:    */   }
/* 100:    */   
/* 101:    */   private static String parseName(String path)
/* 102:    */   {
/* 103:112 */     String str = path;
/* 104:113 */     int pos = path.lastIndexOf('/');
/* 105:115 */     if (pos >= 0) {
/* 106:117 */       str = path.substring(pos + 1);
/* 107:    */     }
/* 108:120 */     int pos2 = str.lastIndexOf('.');
/* 109:122 */     if (pos2 >= 0) {
/* 110:124 */       str = str.substring(0, pos2);
/* 111:    */     }
/* 112:127 */     return str;
/* 113:    */   }
/* 114:    */   
/* 115:    */   private static String parseBasePath(String path)
/* 116:    */   {
/* 117:132 */     int pos = path.lastIndexOf('/');
/* 118:133 */     return pos < 0 ? "" : path.substring(0, pos);
/* 119:    */   }
/* 120:    */   
/* 121:    */   private static BiomeGenBase[] parseBiomes(String str)
/* 122:    */   {
/* 123:138 */     if (str == null) {
/* 124:140 */       return null;
/* 125:    */     }
/* 126:144 */     String[] biomeNames = Config.tokenize(str, " ");
/* 127:145 */     ArrayList list = new ArrayList();
/* 128:147 */     for (int biomeArr = 0; biomeArr < biomeNames.length; biomeArr++)
/* 129:    */     {
/* 130:149 */       String biomeName = biomeNames[biomeArr];
/* 131:150 */       BiomeGenBase biome = findBiome(biomeName);
/* 132:152 */       if (biome == null) {
/* 133:154 */         Config.warn("Biome not found: " + biomeName);
/* 134:    */       } else {
/* 135:158 */         list.add(biome);
/* 136:    */       }
/* 137:    */     }
/* 138:162 */     BiomeGenBase[] var6 = (BiomeGenBase[])list.toArray(new BiomeGenBase[list.size()]);
/* 139:163 */     return var6;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private static BiomeGenBase findBiome(String biomeName)
/* 143:    */   {
/* 144:169 */     biomeName = biomeName.toLowerCase();
/* 145:170 */     BiomeGenBase[] biomeList = BiomeGenBase.getBiomeGenArray();
/* 146:172 */     for (int i = 0; i < biomeList.length; i++)
/* 147:    */     {
/* 148:174 */       BiomeGenBase biome = biomeList[i];
/* 149:176 */       if (biome != null)
/* 150:    */       {
/* 151:178 */         String name = biome.biomeName.replace(" ", "").toLowerCase();
/* 152:180 */         if (name.equals(biomeName)) {
/* 153:182 */           return biome;
/* 154:    */         }
/* 155:    */       }
/* 156:    */     }
/* 157:187 */     return null;
/* 158:    */   }
/* 159:    */   
/* 160:    */   private String[] parseTileNames(String str)
/* 161:    */   {
/* 162:192 */     if (str == null) {
/* 163:194 */       return null;
/* 164:    */     }
/* 165:198 */     ArrayList list = new ArrayList();
/* 166:199 */     String[] iconStrs = Config.tokenize(str, " ,");
/* 167:202 */     for (int names = 0; names < iconStrs.length; names++)
/* 168:    */     {
/* 169:204 */       String i = iconStrs[names];
/* 170:206 */       if (i.contains("-"))
/* 171:    */       {
/* 172:208 */         String[] iconName = Config.tokenize(i, "-");
/* 173:210 */         if (iconName.length == 2)
/* 174:    */         {
/* 175:212 */           int pathBlocks = Config.parseInt(iconName[0], -1);
/* 176:213 */           int max = Config.parseInt(iconName[1], -1);
/* 177:215 */           if ((pathBlocks >= 0) && (max >= 0))
/* 178:    */           {
/* 179:217 */             if (pathBlocks <= max)
/* 180:    */             {
/* 181:219 */               int n = pathBlocks;
/* 182:223 */               while (n <= max)
/* 183:    */               {
/* 184:228 */                 list.add(String.valueOf(n));
/* 185:229 */                 n++;
/* 186:    */               }
/* 187:    */             }
/* 188:233 */             Config.warn("Invalid interval: " + i + ", when parsing: " + str);
/* 189:234 */             continue;
/* 190:    */           }
/* 191:    */         }
/* 192:    */       }
/* 193:239 */       list.add(i);
/* 194:    */     }
/* 195:242 */     String[] var10 = (String[])list.toArray(new String[list.size()]);
/* 196:244 */     for (int var11 = 0; var11 < var10.length; var11++)
/* 197:    */     {
/* 198:246 */       String var12 = var10[var11];
/* 199:247 */       var12 = TextureUtils.fixResourcePath(var12, this.basePath);
/* 200:249 */       if ((!var12.startsWith(this.basePath)) && (!var12.startsWith("textures/")) && (!var12.startsWith("mcpatcher/"))) {
/* 201:251 */         var12 = this.basePath + "/" + var12;
/* 202:    */       }
/* 203:254 */       if (var12.endsWith(".png")) {
/* 204:256 */         var12 = var12.substring(0, var12.length() - 4);
/* 205:    */       }
/* 206:259 */       String var13 = "textures/blocks/";
/* 207:261 */       if (var12.startsWith(var13)) {
/* 208:263 */         var12 = var12.substring(var13.length());
/* 209:    */       }
/* 210:266 */       if (var12.startsWith("/")) {
/* 211:268 */         var12 = var12.substring(1);
/* 212:    */       }
/* 213:271 */       var10[var11] = var12;
/* 214:    */     }
/* 215:274 */     return var10;
/* 216:    */   }
/* 217:    */   
/* 218:    */   private static int parseInt(String str)
/* 219:    */   {
/* 220:280 */     if (str == null) {
/* 221:282 */       return -1;
/* 222:    */     }
/* 223:286 */     int num = Config.parseInt(str, -1);
/* 224:288 */     if (num < 0) {
/* 225:290 */       Config.warn("Invalid number: " + str);
/* 226:    */     }
/* 227:293 */     return num;
/* 228:    */   }
/* 229:    */   
/* 230:    */   private static int parseInt(String str, int defVal)
/* 231:    */   {
/* 232:299 */     if (str == null) {
/* 233:301 */       return defVal;
/* 234:    */     }
/* 235:305 */     int num = Config.parseInt(str, -1);
/* 236:307 */     if (num < 0)
/* 237:    */     {
/* 238:309 */       Config.warn("Invalid number: " + str);
/* 239:310 */       return defVal;
/* 240:    */     }
/* 241:314 */     return num;
/* 242:    */   }
/* 243:    */   
/* 244:    */   private static boolean parseBoolean(String str)
/* 245:    */   {
/* 246:321 */     return str == null ? false : str.toLowerCase().equals("true");
/* 247:    */   }
/* 248:    */   
/* 249:    */   private static int parseSymmetry(String str)
/* 250:    */   {
/* 251:326 */     if (str == null) {
/* 252:328 */       return 1;
/* 253:    */     }
/* 254:330 */     if (str.equals("opposite")) {
/* 255:332 */       return 2;
/* 256:    */     }
/* 257:334 */     if (str.equals("all")) {
/* 258:336 */       return 6;
/* 259:    */     }
/* 260:340 */     Config.warn("Unknown symmetry: " + str);
/* 261:341 */     return 1;
/* 262:    */   }
/* 263:    */   
/* 264:    */   private static int parseFaces(String str)
/* 265:    */   {
/* 266:347 */     if (str == null) {
/* 267:349 */       return 63;
/* 268:    */     }
/* 269:353 */     String[] faceStrs = Config.tokenize(str, " ,");
/* 270:354 */     int facesMask = 0;
/* 271:356 */     for (int i = 0; i < faceStrs.length; i++)
/* 272:    */     {
/* 273:358 */       String faceStr = faceStrs[i];
/* 274:359 */       int faceMask = parseFace(faceStr);
/* 275:360 */       facesMask |= faceMask;
/* 276:    */     }
/* 277:363 */     return facesMask;
/* 278:    */   }
/* 279:    */   
/* 280:    */   private static int parseFace(String str)
/* 281:    */   {
/* 282:369 */     str = str.toLowerCase();
/* 283:371 */     if (str.equals("bottom")) {
/* 284:373 */       return 1;
/* 285:    */     }
/* 286:375 */     if (str.equals("top")) {
/* 287:377 */       return 2;
/* 288:    */     }
/* 289:379 */     if (str.equals("north")) {
/* 290:381 */       return 4;
/* 291:    */     }
/* 292:383 */     if (str.equals("south")) {
/* 293:385 */       return 8;
/* 294:    */     }
/* 295:387 */     if (str.equals("east")) {
/* 296:389 */       return 32;
/* 297:    */     }
/* 298:391 */     if (str.equals("west")) {
/* 299:393 */       return 16;
/* 300:    */     }
/* 301:395 */     if (str.equals("sides")) {
/* 302:397 */       return 60;
/* 303:    */     }
/* 304:399 */     if (str.equals("all")) {
/* 305:401 */       return 63;
/* 306:    */     }
/* 307:405 */     Config.warn("Unknown face: " + str);
/* 308:406 */     return 128;
/* 309:    */   }
/* 310:    */   
/* 311:    */   private static int parseConnect(String str)
/* 312:    */   {
/* 313:412 */     if (str == null) {
/* 314:414 */       return 0;
/* 315:    */     }
/* 316:416 */     if (str.equals("block")) {
/* 317:418 */       return 1;
/* 318:    */     }
/* 319:420 */     if (str.equals("tile")) {
/* 320:422 */       return 2;
/* 321:    */     }
/* 322:424 */     if (str.equals("material")) {
/* 323:426 */       return 3;
/* 324:    */     }
/* 325:430 */     Config.warn("Unknown connect: " + str);
/* 326:431 */     return 128;
/* 327:    */   }
/* 328:    */   
/* 329:    */   private static int[] parseInts(String str)
/* 330:    */   {
/* 331:437 */     if (str == null) {
/* 332:439 */       return null;
/* 333:    */     }
/* 334:443 */     ArrayList list = new ArrayList();
/* 335:444 */     String[] intStrs = Config.tokenize(str, " ,");
/* 336:446 */     for (int ints = 0; ints < intStrs.length; ints++)
/* 337:    */     {
/* 338:448 */       String i = intStrs[ints];
/* 339:450 */       if (i.contains("-"))
/* 340:    */       {
/* 341:452 */         String[] val = Config.tokenize(i, "-");
/* 342:454 */         if (val.length != 2)
/* 343:    */         {
/* 344:456 */           Config.warn("Invalid interval: " + i + ", when parsing: " + str);
/* 345:    */         }
/* 346:    */         else
/* 347:    */         {
/* 348:460 */           int min = Config.parseInt(val[0], -1);
/* 349:461 */           int max = Config.parseInt(val[1], -1);
/* 350:463 */           if ((min >= 0) && (max >= 0) && (min <= max)) {
/* 351:465 */             for (int n = min; n <= max; n++) {
/* 352:467 */               list.add(Integer.valueOf(n));
/* 353:    */             }
/* 354:    */           } else {
/* 355:472 */             Config.warn("Invalid interval: " + i + ", when parsing: " + str);
/* 356:    */           }
/* 357:    */         }
/* 358:    */       }
/* 359:    */       else
/* 360:    */       {
/* 361:478 */         int var11 = Config.parseInt(i, -1);
/* 362:480 */         if (var11 < 0) {
/* 363:482 */           Config.warn("Invalid number: " + i + ", when parsing: " + str);
/* 364:    */         } else {
/* 365:486 */           list.add(Integer.valueOf(var11));
/* 366:    */         }
/* 367:    */       }
/* 368:    */     }
/* 369:491 */     int[] var9 = new int[list.size()];
/* 370:493 */     for (int var10 = 0; var10 < var9.length; var10++) {
/* 371:495 */       var9[var10] = ((Integer)list.get(var10)).intValue();
/* 372:    */     }
/* 373:498 */     return var9;
/* 374:    */   }
/* 375:    */   
/* 376:    */   private static int parseMethod(String str)
/* 377:    */   {
/* 378:504 */     if (str == null) {
/* 379:506 */       return 1;
/* 380:    */     }
/* 381:508 */     if ((!str.equals("ctm")) && (!str.equals("glass")))
/* 382:    */     {
/* 383:510 */       if ((!str.equals("horizontal")) && (!str.equals("bookshelf")))
/* 384:    */       {
/* 385:512 */         if (str.equals("vertical")) {
/* 386:514 */           return 6;
/* 387:    */         }
/* 388:516 */         if (str.equals("top")) {
/* 389:518 */           return 3;
/* 390:    */         }
/* 391:520 */         if (str.equals("random")) {
/* 392:522 */           return 4;
/* 393:    */         }
/* 394:524 */         if (str.equals("repeat")) {
/* 395:526 */           return 5;
/* 396:    */         }
/* 397:528 */         if (str.equals("fixed")) {
/* 398:530 */           return 7;
/* 399:    */         }
/* 400:534 */         Config.warn("Unknown method: " + str);
/* 401:535 */         return 0;
/* 402:    */       }
/* 403:540 */       return 2;
/* 404:    */     }
/* 405:545 */     return 1;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public boolean isValid(String path)
/* 409:    */   {
/* 410:551 */     if ((this.name != null) && (this.name.length() > 0))
/* 411:    */     {
/* 412:553 */       if (this.basePath == null)
/* 413:    */       {
/* 414:555 */         Config.warn("No base path found: " + path);
/* 415:556 */         return false;
/* 416:    */       }
/* 417:560 */       if (this.matchBlocks == null) {
/* 418:562 */         this.matchBlocks = detectMatchBlocks();
/* 419:    */       }
/* 420:565 */       if ((this.matchTiles == null) && (this.matchBlocks == null)) {
/* 421:567 */         this.matchTiles = detectMatchTiles();
/* 422:    */       }
/* 423:570 */       if ((this.matchBlocks == null) && (this.matchTiles == null))
/* 424:    */       {
/* 425:572 */         Config.warn("No matchBlocks or matchTiles specified: " + path);
/* 426:573 */         return false;
/* 427:    */       }
/* 428:575 */       if (this.method == 0)
/* 429:    */       {
/* 430:577 */         Config.warn("No method: " + path);
/* 431:578 */         return false;
/* 432:    */       }
/* 433:580 */       if ((this.tiles != null) && (this.tiles.length > 0))
/* 434:    */       {
/* 435:582 */         if (this.connect == 0) {
/* 436:584 */           this.connect = detectConnect();
/* 437:    */         }
/* 438:587 */         if (this.connect == 128)
/* 439:    */         {
/* 440:589 */           Config.warn("Invalid connect in: " + path);
/* 441:590 */           return false;
/* 442:    */         }
/* 443:592 */         if (this.renderPass > 0)
/* 444:    */         {
/* 445:594 */           Config.warn("Render pass not supported: " + this.renderPass);
/* 446:595 */           return false;
/* 447:    */         }
/* 448:597 */         if ((this.faces & 0x80) != 0)
/* 449:    */         {
/* 450:599 */           Config.warn("Invalid faces in: " + path);
/* 451:600 */           return false;
/* 452:    */         }
/* 453:602 */         if ((this.symmetry & 0x80) != 0)
/* 454:    */         {
/* 455:604 */           Config.warn("Invalid symmetry in: " + path);
/* 456:605 */           return false;
/* 457:    */         }
/* 458:609 */         switch (this.method)
/* 459:    */         {
/* 460:    */         case 1: 
/* 461:612 */           return isValidCtm(path);
/* 462:    */         case 2: 
/* 463:615 */           return isValidHorizontal(path);
/* 464:    */         case 3: 
/* 465:618 */           return isValidTop(path);
/* 466:    */         case 4: 
/* 467:621 */           return isValidRandom(path);
/* 468:    */         case 5: 
/* 469:624 */           return isValidRepeat(path);
/* 470:    */         case 6: 
/* 471:627 */           return isValidVertical(path);
/* 472:    */         case 7: 
/* 473:630 */           return isValidFixed(path);
/* 474:    */         }
/* 475:633 */         Config.warn("Unknown method: " + path);
/* 476:634 */         return false;
/* 477:    */       }
/* 478:640 */       Config.warn("No tiles specified: " + path);
/* 479:641 */       return false;
/* 480:    */     }
/* 481:647 */     Config.warn("No name found: " + path);
/* 482:648 */     return false;
/* 483:    */   }
/* 484:    */   
/* 485:    */   private int detectConnect()
/* 486:    */   {
/* 487:654 */     return this.matchTiles != null ? 2 : this.matchBlocks != null ? 1 : 128;
/* 488:    */   }
/* 489:    */   
/* 490:    */   private int[] detectMatchBlocks()
/* 491:    */   {
/* 492:659 */     if (!this.name.startsWith("block")) {
/* 493:661 */       return null;
/* 494:    */     }
/* 495:665 */     int startPos = "block".length();
/* 496:668 */     for (int pos = startPos; pos < this.name.length(); pos++)
/* 497:    */     {
/* 498:670 */       char idStr = this.name.charAt(pos);
/* 499:672 */       if ((idStr < '0') || (idStr > '9')) {
/* 500:    */         break;
/* 501:    */       }
/* 502:    */     }
/* 503:678 */     if (pos == startPos) {
/* 504:680 */       return null;
/* 505:    */     }
/* 506:684 */     String var5 = this.name.substring(startPos, pos);
/* 507:685 */     int id = Config.parseInt(var5, -1);
/* 508:686 */     return new int[] { id < 0 ? null : id };
/* 509:    */   }
/* 510:    */   
/* 511:    */   private String[] detectMatchTiles()
/* 512:    */   {
/* 513:693 */     IIcon icon = getIcon(this.name);
/* 514:694 */     return new String[] { icon == null ? null : this.name };
/* 515:    */   }
/* 516:    */   
/* 517:    */   private static IIcon getIcon(String iconName)
/* 518:    */   {
/* 519:699 */     return TextureMap.textureMapBlocks.getIconSafe(iconName);
/* 520:    */   }
/* 521:    */   
/* 522:    */   private boolean isValidCtm(String path)
/* 523:    */   {
/* 524:704 */     if (this.tiles == null) {
/* 525:706 */       this.tiles = parseTileNames("0-11 16-27 32-43 48-58");
/* 526:    */     }
/* 527:709 */     if (this.tiles.length < 47)
/* 528:    */     {
/* 529:711 */       Config.warn("Invalid tiles, must be at least 47: " + path);
/* 530:712 */       return false;
/* 531:    */     }
/* 532:716 */     return true;
/* 533:    */   }
/* 534:    */   
/* 535:    */   private boolean isValidHorizontal(String path)
/* 536:    */   {
/* 537:722 */     if (this.tiles == null) {
/* 538:724 */       this.tiles = parseTileNames("12-15");
/* 539:    */     }
/* 540:727 */     if (this.tiles.length != 4)
/* 541:    */     {
/* 542:729 */       Config.warn("Invalid tiles, must be exactly 4: " + path);
/* 543:730 */       return false;
/* 544:    */     }
/* 545:734 */     return true;
/* 546:    */   }
/* 547:    */   
/* 548:    */   private boolean isValidVertical(String path)
/* 549:    */   {
/* 550:740 */     if (this.tiles == null)
/* 551:    */     {
/* 552:742 */       Config.warn("No tiles defined for vertical: " + path);
/* 553:743 */       return false;
/* 554:    */     }
/* 555:745 */     if (this.tiles.length != 4)
/* 556:    */     {
/* 557:747 */       Config.warn("Invalid tiles, must be exactly 4: " + path);
/* 558:748 */       return false;
/* 559:    */     }
/* 560:752 */     return true;
/* 561:    */   }
/* 562:    */   
/* 563:    */   private boolean isValidRandom(String path)
/* 564:    */   {
/* 565:758 */     if ((this.tiles != null) && (this.tiles.length > 0))
/* 566:    */     {
/* 567:760 */       if ((this.weights != null) && (this.weights.length != this.tiles.length))
/* 568:    */       {
/* 569:762 */         Config.warn("Number of weights must equal the number of tiles: " + path);
/* 570:763 */         this.weights = null;
/* 571:    */       }
/* 572:766 */       if (this.weights != null)
/* 573:    */       {
/* 574:768 */         this.sumWeights = new int[this.weights.length];
/* 575:769 */         int sum = 0;
/* 576:771 */         for (int i = 0; i < this.weights.length; i++)
/* 577:    */         {
/* 578:773 */           sum += this.weights[i];
/* 579:774 */           this.sumWeights[i] = sum;
/* 580:    */         }
/* 581:777 */         this.sumAllWeights = sum;
/* 582:    */       }
/* 583:780 */       return true;
/* 584:    */     }
/* 585:784 */     Config.warn("Tiles not defined: " + path);
/* 586:785 */     return false;
/* 587:    */   }
/* 588:    */   
/* 589:    */   private boolean isValidRepeat(String path)
/* 590:    */   {
/* 591:791 */     if (this.tiles == null)
/* 592:    */     {
/* 593:793 */       Config.warn("Tiles not defined: " + path);
/* 594:794 */       return false;
/* 595:    */     }
/* 596:796 */     if ((this.width > 0) && (this.width <= 16))
/* 597:    */     {
/* 598:798 */       if ((this.height > 0) && (this.height <= 16))
/* 599:    */       {
/* 600:800 */         if (this.tiles.length != this.width * this.height)
/* 601:    */         {
/* 602:802 */           Config.warn("Number of tiles does not equal width x height: " + path);
/* 603:803 */           return false;
/* 604:    */         }
/* 605:807 */         return true;
/* 606:    */       }
/* 607:812 */       Config.warn("Invalid height: " + path);
/* 608:813 */       return false;
/* 609:    */     }
/* 610:818 */     Config.warn("Invalid width: " + path);
/* 611:819 */     return false;
/* 612:    */   }
/* 613:    */   
/* 614:    */   private boolean isValidFixed(String path)
/* 615:    */   {
/* 616:825 */     if (this.tiles == null)
/* 617:    */     {
/* 618:827 */       Config.warn("Tiles not defined: " + path);
/* 619:828 */       return false;
/* 620:    */     }
/* 621:830 */     if (this.tiles.length != 1)
/* 622:    */     {
/* 623:832 */       Config.warn("Number of tiles should be 1 for method: fixed.");
/* 624:833 */       return false;
/* 625:    */     }
/* 626:837 */     return true;
/* 627:    */   }
/* 628:    */   
/* 629:    */   private boolean isValidTop(String path)
/* 630:    */   {
/* 631:843 */     if (this.tiles == null) {
/* 632:845 */       this.tiles = parseTileNames("66");
/* 633:    */     }
/* 634:848 */     if (this.tiles.length != 1)
/* 635:    */     {
/* 636:850 */       Config.warn("Invalid tiles, must be exactly 1: " + path);
/* 637:851 */       return false;
/* 638:    */     }
/* 639:855 */     return true;
/* 640:    */   }
/* 641:    */   
/* 642:    */   public void updateIcons(TextureMap textureMap)
/* 643:    */   {
/* 644:861 */     if (this.matchTiles != null) {
/* 645:863 */       this.matchTileIcons = registerIcons(this.matchTiles, textureMap);
/* 646:    */     }
/* 647:866 */     if (this.tiles != null) {
/* 648:868 */       this.tileIcons = registerIcons(this.tiles, textureMap);
/* 649:    */     }
/* 650:    */   }
/* 651:    */   
/* 652:    */   private static IIcon[] registerIcons(String[] tileNames, TextureMap textureMap)
/* 653:    */   {
/* 654:874 */     if (tileNames == null) {
/* 655:876 */       return null;
/* 656:    */     }
/* 657:880 */     ArrayList iconList = new ArrayList();
/* 658:882 */     for (int icons = 0; icons < tileNames.length; icons++)
/* 659:    */     {
/* 660:884 */       String iconName = tileNames[icons];
/* 661:885 */       String fullName = iconName;
/* 662:887 */       if (!iconName.contains("/")) {
/* 663:889 */         fullName = "textures/blocks/" + iconName;
/* 664:    */       }
/* 665:892 */       String fileName = fullName + ".png";
/* 666:893 */       ResourceLocation loc = new ResourceLocation(fileName);
/* 667:894 */       boolean exists = Config.hasResource(loc);
/* 668:896 */       if (!exists) {
/* 669:898 */         Config.warn("File not found: " + fileName);
/* 670:    */       }
/* 671:901 */       IIcon icon = textureMap.registerIcon(iconName);
/* 672:902 */       iconList.add(icon);
/* 673:    */     }
/* 674:905 */     IIcon[] var10 = (IIcon[])iconList.toArray(new IIcon[iconList.size()]);
/* 675:906 */     return var10;
/* 676:    */   }
/* 677:    */   
/* 678:    */   public String toString()
/* 679:    */   {
/* 680:912 */     return "CTM name: " + this.name + ", basePath: " + this.basePath + ", matchBlocks: " + Config.arrayToString(this.matchBlocks) + ", matchTiles: " + Config.arrayToString(this.matchTiles);
/* 681:    */   }
/* 682:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.ConnectedProperties
 * JD-Core Version:    0.7.0.1
 */