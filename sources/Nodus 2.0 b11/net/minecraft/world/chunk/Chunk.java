/*    1:     */ package net.minecraft.world.chunk;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Arrays;
/*    5:     */ import java.util.Collection;
/*    6:     */ import java.util.HashMap;
/*    7:     */ import java.util.Iterator;
/*    8:     */ import java.util.List;
/*    9:     */ import java.util.Map;
/*   10:     */ import java.util.Random;
/*   11:     */ import java.util.concurrent.Callable;
/*   12:     */ import net.minecraft.block.Block;
/*   13:     */ import net.minecraft.block.ITileEntityProvider;
/*   14:     */ import net.minecraft.block.material.Material;
/*   15:     */ import net.minecraft.command.IEntitySelector;
/*   16:     */ import net.minecraft.crash.CrashReport;
/*   17:     */ import net.minecraft.crash.CrashReportCategory;
/*   18:     */ import net.minecraft.entity.Entity;
/*   19:     */ import net.minecraft.init.Blocks;
/*   20:     */ import net.minecraft.profiler.Profiler;
/*   21:     */ import net.minecraft.tileentity.TileEntity;
/*   22:     */ import net.minecraft.util.AxisAlignedBB;
/*   23:     */ import net.minecraft.util.MathHelper;
/*   24:     */ import net.minecraft.util.ReportedException;
/*   25:     */ import net.minecraft.world.ChunkCoordIntPair;
/*   26:     */ import net.minecraft.world.ChunkPosition;
/*   27:     */ import net.minecraft.world.EnumSkyBlock;
/*   28:     */ import net.minecraft.world.World;
/*   29:     */ import net.minecraft.world.WorldProvider;
/*   30:     */ import net.minecraft.world.biome.BiomeGenBase;
/*   31:     */ import net.minecraft.world.biome.WorldChunkManager;
/*   32:     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*   33:     */ import org.apache.logging.log4j.LogManager;
/*   34:     */ import org.apache.logging.log4j.Logger;
/*   35:     */ 
/*   36:     */ public class Chunk
/*   37:     */ {
/*   38:  35 */   private static final Logger logger = ;
/*   39:     */   public static boolean isLit;
/*   40:     */   private ExtendedBlockStorage[] storageArrays;
/*   41:     */   private byte[] blockBiomeArray;
/*   42:     */   public int[] precipitationHeightMap;
/*   43:     */   public boolean[] updateSkylightColumns;
/*   44:     */   public boolean isChunkLoaded;
/*   45:     */   public World worldObj;
/*   46:     */   public int[] heightMap;
/*   47:     */   public final int xPosition;
/*   48:     */   public final int zPosition;
/*   49:     */   private boolean isGapLightingUpdated;
/*   50:     */   public Map chunkTileEntityMap;
/*   51:     */   public List[] entityLists;
/*   52:     */   public boolean isTerrainPopulated;
/*   53:     */   public boolean isLightPopulated;
/*   54:     */   public boolean field_150815_m;
/*   55:     */   public boolean isModified;
/*   56:     */   public boolean hasEntities;
/*   57:     */   public long lastSaveTime;
/*   58:     */   public boolean sendUpdates;
/*   59:     */   public int heightMapMinimum;
/*   60:     */   public long inhabitedTime;
/*   61:     */   private int queuedLightChecks;
/*   62:     */   private static final String __OBFID = "CL_00000373";
/*   63:     */   
/*   64:     */   public Chunk(World par1World, int par2, int par3)
/*   65:     */   {
/*   66: 121 */     this.storageArrays = new ExtendedBlockStorage[16];
/*   67: 122 */     this.blockBiomeArray = new byte[256];
/*   68: 123 */     this.precipitationHeightMap = new int[256];
/*   69: 124 */     this.updateSkylightColumns = new boolean[256];
/*   70: 125 */     this.chunkTileEntityMap = new HashMap();
/*   71: 126 */     this.queuedLightChecks = 4096;
/*   72: 127 */     this.entityLists = new List[16];
/*   73: 128 */     this.worldObj = par1World;
/*   74: 129 */     this.xPosition = par2;
/*   75: 130 */     this.zPosition = par3;
/*   76: 131 */     this.heightMap = new int[256];
/*   77: 133 */     for (int var4 = 0; var4 < this.entityLists.length; var4++) {
/*   78: 135 */       this.entityLists[var4] = new ArrayList();
/*   79:     */     }
/*   80: 138 */     Arrays.fill(this.precipitationHeightMap, -999);
/*   81: 139 */     Arrays.fill(this.blockBiomeArray, (byte)-1);
/*   82:     */   }
/*   83:     */   
/*   84:     */   public Chunk(World p_i45446_1_, Block[] p_i45446_2_, int p_i45446_3_, int p_i45446_4_)
/*   85:     */   {
/*   86: 144 */     this(p_i45446_1_, p_i45446_3_, p_i45446_4_);
/*   87: 145 */     int var5 = p_i45446_2_.length / 256;
/*   88: 146 */     boolean var6 = !p_i45446_1_.provider.hasNoSky;
/*   89: 148 */     for (int var7 = 0; var7 < 16; var7++) {
/*   90: 150 */       for (int var8 = 0; var8 < 16; var8++) {
/*   91: 152 */         for (int var9 = 0; var9 < var5; var9++)
/*   92:     */         {
/*   93: 154 */           Block var10 = p_i45446_2_[(var7 << 11 | var8 << 7 | var9)];
/*   94: 156 */           if ((var10 != null) && (var10.getMaterial() != Material.air))
/*   95:     */           {
/*   96: 158 */             int var11 = var9 >> 4;
/*   97: 160 */             if (this.storageArrays[var11] == null) {
/*   98: 162 */               this.storageArrays[var11] = new ExtendedBlockStorage(var11 << 4, var6);
/*   99:     */             }
/*  100: 165 */             this.storageArrays[var11].func_150818_a(var7, var9 & 0xF, var8, var10);
/*  101:     */           }
/*  102:     */         }
/*  103:     */       }
/*  104:     */     }
/*  105:     */   }
/*  106:     */   
/*  107:     */   public Chunk(World p_i45447_1_, Block[] p_i45447_2_, byte[] p_i45447_3_, int p_i45447_4_, int p_i45447_5_)
/*  108:     */   {
/*  109: 174 */     this(p_i45447_1_, p_i45447_4_, p_i45447_5_);
/*  110: 175 */     int var6 = p_i45447_2_.length / 256;
/*  111: 176 */     boolean var7 = !p_i45447_1_.provider.hasNoSky;
/*  112: 178 */     for (int var8 = 0; var8 < 16; var8++) {
/*  113: 180 */       for (int var9 = 0; var9 < 16; var9++) {
/*  114: 182 */         for (int var10 = 0; var10 < var6; var10++)
/*  115:     */         {
/*  116: 184 */           int var11 = var8 * var6 * 16 | var9 * var6 | var10;
/*  117: 185 */           Block var12 = p_i45447_2_[var11];
/*  118: 187 */           if ((var12 != null) && (var12 != Blocks.air))
/*  119:     */           {
/*  120: 189 */             int var13 = var10 >> 4;
/*  121: 191 */             if (this.storageArrays[var13] == null) {
/*  122: 193 */               this.storageArrays[var13] = new ExtendedBlockStorage(var13 << 4, var7);
/*  123:     */             }
/*  124: 196 */             this.storageArrays[var13].func_150818_a(var8, var10 & 0xF, var9, var12);
/*  125: 197 */             this.storageArrays[var13].setExtBlockMetadata(var8, var10 & 0xF, var9, p_i45447_3_[var11]);
/*  126:     */           }
/*  127:     */         }
/*  128:     */       }
/*  129:     */     }
/*  130:     */   }
/*  131:     */   
/*  132:     */   public boolean isAtLocation(int par1, int par2)
/*  133:     */   {
/*  134: 209 */     return (par1 == this.xPosition) && (par2 == this.zPosition);
/*  135:     */   }
/*  136:     */   
/*  137:     */   public int getHeightValue(int par1, int par2)
/*  138:     */   {
/*  139: 217 */     return this.heightMap[(par2 << 4 | par1)];
/*  140:     */   }
/*  141:     */   
/*  142:     */   public int getTopFilledSegment()
/*  143:     */   {
/*  144: 225 */     for (int var1 = this.storageArrays.length - 1; var1 >= 0; var1--) {
/*  145: 227 */       if (this.storageArrays[var1] != null) {
/*  146: 229 */         return this.storageArrays[var1].getYLocation();
/*  147:     */       }
/*  148:     */     }
/*  149: 233 */     return 0;
/*  150:     */   }
/*  151:     */   
/*  152:     */   public ExtendedBlockStorage[] getBlockStorageArray()
/*  153:     */   {
/*  154: 241 */     return this.storageArrays;
/*  155:     */   }
/*  156:     */   
/*  157:     */   public void generateHeightMap()
/*  158:     */   {
/*  159: 249 */     int var1 = getTopFilledSegment();
/*  160: 250 */     this.heightMapMinimum = 2147483647;
/*  161: 252 */     for (int var2 = 0; var2 < 16; var2++)
/*  162:     */     {
/*  163: 254 */       int var3 = 0;
/*  164: 256 */       while (var3 < 16)
/*  165:     */       {
/*  166: 258 */         this.precipitationHeightMap[(var2 + (var3 << 4))] = -999;
/*  167: 259 */         int var4 = var1 + 16 - 1;
/*  168: 263 */         while (var4 > 0)
/*  169:     */         {
/*  170: 265 */           Block var5 = func_150810_a(var2, var4 - 1, var3);
/*  171: 267 */           if (var5.getLightOpacity() == 0)
/*  172:     */           {
/*  173: 269 */             var4--;
/*  174:     */           }
/*  175:     */           else
/*  176:     */           {
/*  177: 273 */             this.heightMap[(var3 << 4 | var2)] = var4;
/*  178: 275 */             if (var4 < this.heightMapMinimum) {
/*  179: 277 */               this.heightMapMinimum = var4;
/*  180:     */             }
/*  181:     */           }
/*  182:     */         }
/*  183: 281 */         var3++;
/*  184:     */       }
/*  185:     */     }
/*  186: 287 */     this.isModified = true;
/*  187:     */   }
/*  188:     */   
/*  189:     */   public void generateSkylightMap()
/*  190:     */   {
/*  191: 295 */     int var1 = getTopFilledSegment();
/*  192: 296 */     this.heightMapMinimum = 2147483647;
/*  193: 298 */     for (int var2 = 0; var2 < 16; var2++)
/*  194:     */     {
/*  195: 300 */       int var3 = 0;
/*  196: 302 */       while (var3 < 16)
/*  197:     */       {
/*  198: 304 */         this.precipitationHeightMap[(var2 + (var3 << 4))] = -999;
/*  199: 305 */         int var4 = var1 + 16 - 1;
/*  200: 309 */         while (var4 > 0) {
/*  201: 311 */           if (func_150808_b(var2, var4 - 1, var3) == 0)
/*  202:     */           {
/*  203: 313 */             var4--;
/*  204:     */           }
/*  205:     */           else
/*  206:     */           {
/*  207: 317 */             this.heightMap[(var3 << 4 | var2)] = var4;
/*  208: 319 */             if (var4 < this.heightMapMinimum) {
/*  209: 321 */               this.heightMapMinimum = var4;
/*  210:     */             }
/*  211:     */           }
/*  212:     */         }
/*  213: 325 */         if (!this.worldObj.provider.hasNoSky)
/*  214:     */         {
/*  215: 327 */           var4 = 15;
/*  216: 328 */           int var5 = var1 + 16 - 1;
/*  217:     */           do
/*  218:     */           {
/*  219: 332 */             int var6 = func_150808_b(var2, var5, var3);
/*  220: 334 */             if ((var6 == 0) && (var4 != 15)) {
/*  221: 336 */               var6 = 1;
/*  222:     */             }
/*  223: 339 */             var4 -= var6;
/*  224: 341 */             if (var4 > 0)
/*  225:     */             {
/*  226: 343 */               ExtendedBlockStorage var7 = this.storageArrays[(var5 >> 4)];
/*  227: 345 */               if (var7 != null)
/*  228:     */               {
/*  229: 347 */                 var7.setExtSkylightValue(var2, var5 & 0xF, var3, var4);
/*  230: 348 */                 this.worldObj.func_147479_m((this.xPosition << 4) + var2, var5, (this.zPosition << 4) + var3);
/*  231:     */               }
/*  232:     */             }
/*  233: 352 */             var5--;
/*  234: 354 */           } while ((var5 > 0) && (var4 > 0));
/*  235:     */         }
/*  236: 357 */         var3++;
/*  237:     */       }
/*  238:     */     }
/*  239: 363 */     this.isModified = true;
/*  240:     */   }
/*  241:     */   
/*  242:     */   private void propagateSkylightOcclusion(int par1, int par2)
/*  243:     */   {
/*  244: 371 */     this.updateSkylightColumns[(par1 + par2 * 16)] = true;
/*  245: 372 */     this.isGapLightingUpdated = true;
/*  246:     */   }
/*  247:     */   
/*  248:     */   private void recheckGaps(boolean p_150803_1_)
/*  249:     */   {
/*  250: 377 */     this.worldObj.theProfiler.startSection("recheckGaps");
/*  251: 379 */     if (this.worldObj.doChunksNearChunkExist(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8, 16))
/*  252:     */     {
/*  253: 381 */       for (int var2 = 0; var2 < 16; var2++) {
/*  254: 383 */         for (int var3 = 0; var3 < 16; var3++) {
/*  255: 385 */           if (this.updateSkylightColumns[(var2 + var3 * 16)] != 0)
/*  256:     */           {
/*  257: 387 */             this.updateSkylightColumns[(var2 + var3 * 16)] = false;
/*  258: 388 */             int var4 = getHeightValue(var2, var3);
/*  259: 389 */             int var5 = this.xPosition * 16 + var2;
/*  260: 390 */             int var6 = this.zPosition * 16 + var3;
/*  261: 391 */             int var7 = this.worldObj.getChunkHeightMapMinimum(var5 - 1, var6);
/*  262: 392 */             int var8 = this.worldObj.getChunkHeightMapMinimum(var5 + 1, var6);
/*  263: 393 */             int var9 = this.worldObj.getChunkHeightMapMinimum(var5, var6 - 1);
/*  264: 394 */             int var10 = this.worldObj.getChunkHeightMapMinimum(var5, var6 + 1);
/*  265: 396 */             if (var8 < var7) {
/*  266: 398 */               var7 = var8;
/*  267:     */             }
/*  268: 401 */             if (var9 < var7) {
/*  269: 403 */               var7 = var9;
/*  270:     */             }
/*  271: 406 */             if (var10 < var7) {
/*  272: 408 */               var7 = var10;
/*  273:     */             }
/*  274: 411 */             checkSkylightNeighborHeight(var5, var6, var7);
/*  275: 412 */             checkSkylightNeighborHeight(var5 - 1, var6, var4);
/*  276: 413 */             checkSkylightNeighborHeight(var5 + 1, var6, var4);
/*  277: 414 */             checkSkylightNeighborHeight(var5, var6 - 1, var4);
/*  278: 415 */             checkSkylightNeighborHeight(var5, var6 + 1, var4);
/*  279: 417 */             if (p_150803_1_)
/*  280:     */             {
/*  281: 419 */               this.worldObj.theProfiler.endSection();
/*  282: 420 */               return;
/*  283:     */             }
/*  284:     */           }
/*  285:     */         }
/*  286:     */       }
/*  287: 426 */       this.isGapLightingUpdated = false;
/*  288:     */     }
/*  289: 429 */     this.worldObj.theProfiler.endSection();
/*  290:     */   }
/*  291:     */   
/*  292:     */   private void checkSkylightNeighborHeight(int par1, int par2, int par3)
/*  293:     */   {
/*  294: 437 */     int var4 = this.worldObj.getHeightValue(par1, par2);
/*  295: 439 */     if (var4 > par3) {
/*  296: 441 */       updateSkylightNeighborHeight(par1, par2, par3, var4 + 1);
/*  297: 443 */     } else if (var4 < par3) {
/*  298: 445 */       updateSkylightNeighborHeight(par1, par2, var4, par3 + 1);
/*  299:     */     }
/*  300:     */   }
/*  301:     */   
/*  302:     */   private void updateSkylightNeighborHeight(int par1, int par2, int par3, int par4)
/*  303:     */   {
/*  304: 451 */     if ((par4 > par3) && (this.worldObj.doChunksNearChunkExist(par1, 0, par2, 16)))
/*  305:     */     {
/*  306: 453 */       for (int var5 = par3; var5 < par4; var5++) {
/*  307: 455 */         this.worldObj.updateLightByType(EnumSkyBlock.Sky, par1, var5, par2);
/*  308:     */       }
/*  309: 458 */       this.isModified = true;
/*  310:     */     }
/*  311:     */   }
/*  312:     */   
/*  313:     */   private void relightBlock(int par1, int par2, int par3)
/*  314:     */   {
/*  315: 467 */     int var4 = this.heightMap[(par3 << 4 | par1)] & 0xFF;
/*  316: 468 */     int var5 = var4;
/*  317: 470 */     if (par2 > var4) {
/*  318: 472 */       var5 = par2;
/*  319:     */     }
/*  320: 475 */     while ((var5 > 0) && (func_150808_b(par1, var5 - 1, par3) == 0)) {
/*  321: 477 */       var5--;
/*  322:     */     }
/*  323: 480 */     if (var5 != var4)
/*  324:     */     {
/*  325: 482 */       this.worldObj.markBlocksDirtyVertical(par1 + this.xPosition * 16, par3 + this.zPosition * 16, var5, var4);
/*  326: 483 */       this.heightMap[(par3 << 4 | par1)] = var5;
/*  327: 484 */       int var6 = this.xPosition * 16 + par1;
/*  328: 485 */       int var7 = this.zPosition * 16 + par3;
/*  329: 489 */       if (!this.worldObj.provider.hasNoSky)
/*  330:     */       {
/*  331: 493 */         if (var5 < var4) {
/*  332: 495 */           for (int var8 = var5; var8 < var4; var8++)
/*  333:     */           {
/*  334: 497 */             ExtendedBlockStorage var9 = this.storageArrays[(var8 >> 4)];
/*  335: 499 */             if (var9 != null)
/*  336:     */             {
/*  337: 501 */               var9.setExtSkylightValue(par1, var8 & 0xF, par3, 15);
/*  338: 502 */               this.worldObj.func_147479_m((this.xPosition << 4) + par1, var8, (this.zPosition << 4) + par3);
/*  339:     */             }
/*  340:     */           }
/*  341:     */         } else {
/*  342: 508 */           for (var8 = var4; var8 < var5; var8++)
/*  343:     */           {
/*  344: 510 */             ExtendedBlockStorage var9 = this.storageArrays[(var8 >> 4)];
/*  345: 512 */             if (var9 != null)
/*  346:     */             {
/*  347: 514 */               var9.setExtSkylightValue(par1, var8 & 0xF, par3, 0);
/*  348: 515 */               this.worldObj.func_147479_m((this.xPosition << 4) + par1, var8, (this.zPosition << 4) + par3);
/*  349:     */             }
/*  350:     */           }
/*  351:     */         }
/*  352: 520 */         int var8 = 15;
/*  353: 522 */         while ((var5 > 0) && (var8 > 0))
/*  354:     */         {
/*  355: 524 */           var5--;
/*  356: 525 */           int var12 = func_150808_b(par1, var5, par3);
/*  357: 527 */           if (var12 == 0) {
/*  358: 529 */             var12 = 1;
/*  359:     */           }
/*  360: 532 */           var8 -= var12;
/*  361: 534 */           if (var8 < 0) {
/*  362: 536 */             var8 = 0;
/*  363:     */           }
/*  364: 539 */           ExtendedBlockStorage var10 = this.storageArrays[(var5 >> 4)];
/*  365: 541 */           if (var10 != null) {
/*  366: 543 */             var10.setExtSkylightValue(par1, var5 & 0xF, par3, var8);
/*  367:     */           }
/*  368:     */         }
/*  369:     */       }
/*  370: 548 */       int var8 = this.heightMap[(par3 << 4 | par1)];
/*  371: 549 */       int var12 = var4;
/*  372: 550 */       int var13 = var8;
/*  373: 552 */       if (var8 < var4)
/*  374:     */       {
/*  375: 554 */         var12 = var8;
/*  376: 555 */         var13 = var4;
/*  377:     */       }
/*  378: 558 */       if (var8 < this.heightMapMinimum) {
/*  379: 560 */         this.heightMapMinimum = var8;
/*  380:     */       }
/*  381: 563 */       if (!this.worldObj.provider.hasNoSky)
/*  382:     */       {
/*  383: 565 */         updateSkylightNeighborHeight(var6 - 1, var7, var12, var13);
/*  384: 566 */         updateSkylightNeighborHeight(var6 + 1, var7, var12, var13);
/*  385: 567 */         updateSkylightNeighborHeight(var6, var7 - 1, var12, var13);
/*  386: 568 */         updateSkylightNeighborHeight(var6, var7 + 1, var12, var13);
/*  387: 569 */         updateSkylightNeighborHeight(var6, var7, var12, var13);
/*  388:     */       }
/*  389: 572 */       this.isModified = true;
/*  390:     */     }
/*  391:     */   }
/*  392:     */   
/*  393:     */   public int func_150808_b(int p_150808_1_, int p_150808_2_, int p_150808_3_)
/*  394:     */   {
/*  395: 578 */     return func_150810_a(p_150808_1_, p_150808_2_, p_150808_3_).getLightOpacity();
/*  396:     */   }
/*  397:     */   
/*  398:     */   public Block func_150810_a(final int p_150810_1_, final int p_150810_2_, final int p_150810_3_)
/*  399:     */   {
/*  400: 583 */     Block var4 = Blocks.air;
/*  401: 585 */     if (p_150810_2_ >> 4 < this.storageArrays.length)
/*  402:     */     {
/*  403: 587 */       ExtendedBlockStorage var5 = this.storageArrays[(p_150810_2_ >> 4)];
/*  404: 589 */       if (var5 != null) {
/*  405:     */         try
/*  406:     */         {
/*  407: 593 */           var4 = var5.func_150819_a(p_150810_1_, p_150810_2_ & 0xF, p_150810_3_);
/*  408:     */         }
/*  409:     */         catch (Throwable var9)
/*  410:     */         {
/*  411: 597 */           CrashReport var7 = CrashReport.makeCrashReport(var9, "Getting block");
/*  412: 598 */           CrashReportCategory var8 = var7.makeCategory("Block being got");
/*  413: 599 */           var8.addCrashSectionCallable("Location", new Callable()
/*  414:     */           {
/*  415:     */             private static final String __OBFID = "CL_00000374";
/*  416:     */             
/*  417:     */             public String call()
/*  418:     */             {
/*  419: 604 */               return CrashReportCategory.getLocationInfo(p_150810_1_, p_150810_2_, p_150810_3_);
/*  420:     */             }
/*  421: 606 */           });
/*  422: 607 */           throw new ReportedException(var7);
/*  423:     */         }
/*  424:     */       }
/*  425:     */     }
/*  426: 612 */     return var4;
/*  427:     */   }
/*  428:     */   
/*  429:     */   public int getBlockMetadata(int par1, int par2, int par3)
/*  430:     */   {
/*  431: 620 */     if (par2 >> 4 >= this.storageArrays.length) {
/*  432: 622 */       return 0;
/*  433:     */     }
/*  434: 626 */     ExtendedBlockStorage var4 = this.storageArrays[(par2 >> 4)];
/*  435: 627 */     return var4 != null ? var4.getExtBlockMetadata(par1, par2 & 0xF, par3) : 0;
/*  436:     */   }
/*  437:     */   
/*  438:     */   public boolean func_150807_a(int p_150807_1_, int p_150807_2_, int p_150807_3_, Block p_150807_4_, int p_150807_5_)
/*  439:     */   {
/*  440: 633 */     int var6 = p_150807_3_ << 4 | p_150807_1_;
/*  441: 635 */     if (p_150807_2_ >= this.precipitationHeightMap[var6] - 1) {
/*  442: 637 */       this.precipitationHeightMap[var6] = -999;
/*  443:     */     }
/*  444: 640 */     int var7 = this.heightMap[var6];
/*  445: 641 */     Block var8 = func_150810_a(p_150807_1_, p_150807_2_, p_150807_3_);
/*  446: 642 */     int var9 = getBlockMetadata(p_150807_1_, p_150807_2_, p_150807_3_);
/*  447: 644 */     if ((var8 == p_150807_4_) && (var9 == p_150807_5_)) {
/*  448: 646 */       return false;
/*  449:     */     }
/*  450: 650 */     ExtendedBlockStorage var10 = this.storageArrays[(p_150807_2_ >> 4)];
/*  451: 651 */     boolean var11 = false;
/*  452: 653 */     if (var10 == null)
/*  453:     */     {
/*  454: 655 */       if (p_150807_4_ == Blocks.air) {
/*  455: 657 */         return false;
/*  456:     */       }
/*  457: 660 */       var10 = this.storageArrays[(p_150807_2_ >> 4)] =  = new ExtendedBlockStorage(p_150807_2_ >> 4 << 4, !this.worldObj.provider.hasNoSky);
/*  458: 661 */       var11 = p_150807_2_ >= var7;
/*  459:     */     }
/*  460: 664 */     int var12 = this.xPosition * 16 + p_150807_1_;
/*  461: 665 */     int var13 = this.zPosition * 16 + p_150807_3_;
/*  462: 667 */     if (!this.worldObj.isClient) {
/*  463: 669 */       var8.onBlockPreDestroy(this.worldObj, var12, p_150807_2_, var13, var9);
/*  464:     */     }
/*  465: 672 */     var10.func_150818_a(p_150807_1_, p_150807_2_ & 0xF, p_150807_3_, p_150807_4_);
/*  466: 674 */     if (!this.worldObj.isClient) {
/*  467: 676 */       var8.breakBlock(this.worldObj, var12, p_150807_2_, var13, var8, var9);
/*  468: 678 */     } else if (((var8 instanceof ITileEntityProvider)) && (var8 != p_150807_4_)) {
/*  469: 680 */       this.worldObj.removeTileEntity(var12, p_150807_2_, var13);
/*  470:     */     }
/*  471: 683 */     if (var10.func_150819_a(p_150807_1_, p_150807_2_ & 0xF, p_150807_3_) != p_150807_4_) {
/*  472: 685 */       return false;
/*  473:     */     }
/*  474: 689 */     var10.setExtBlockMetadata(p_150807_1_, p_150807_2_ & 0xF, p_150807_3_, p_150807_5_);
/*  475: 691 */     if (var11)
/*  476:     */     {
/*  477: 693 */       generateSkylightMap();
/*  478:     */     }
/*  479:     */     else
/*  480:     */     {
/*  481: 697 */       int var14 = p_150807_4_.getLightOpacity();
/*  482: 698 */       int var15 = var8.getLightOpacity();
/*  483: 700 */       if (var14 > 0)
/*  484:     */       {
/*  485: 702 */         if (p_150807_2_ >= var7) {
/*  486: 704 */           relightBlock(p_150807_1_, p_150807_2_ + 1, p_150807_3_);
/*  487:     */         }
/*  488:     */       }
/*  489: 707 */       else if (p_150807_2_ == var7 - 1) {
/*  490: 709 */         relightBlock(p_150807_1_, p_150807_2_, p_150807_3_);
/*  491:     */       }
/*  492: 712 */       if ((var14 != var15) && ((var14 < var15) || (getSavedLightValue(EnumSkyBlock.Sky, p_150807_1_, p_150807_2_, p_150807_3_) > 0) || (getSavedLightValue(EnumSkyBlock.Block, p_150807_1_, p_150807_2_, p_150807_3_) > 0))) {
/*  493: 714 */         propagateSkylightOcclusion(p_150807_1_, p_150807_3_);
/*  494:     */       }
/*  495:     */     }
/*  496: 720 */     if ((var8 instanceof ITileEntityProvider))
/*  497:     */     {
/*  498: 722 */       TileEntity var16 = func_150806_e(p_150807_1_, p_150807_2_, p_150807_3_);
/*  499: 724 */       if (var16 != null) {
/*  500: 726 */         var16.updateContainingBlockInfo();
/*  501:     */       }
/*  502:     */     }
/*  503: 730 */     if (!this.worldObj.isClient) {
/*  504: 732 */       p_150807_4_.onBlockAdded(this.worldObj, var12, p_150807_2_, var13);
/*  505:     */     }
/*  506: 735 */     if ((p_150807_4_ instanceof ITileEntityProvider))
/*  507:     */     {
/*  508: 737 */       TileEntity var16 = func_150806_e(p_150807_1_, p_150807_2_, p_150807_3_);
/*  509: 739 */       if (var16 == null)
/*  510:     */       {
/*  511: 741 */         var16 = ((ITileEntityProvider)p_150807_4_).createNewTileEntity(this.worldObj, p_150807_5_);
/*  512: 742 */         this.worldObj.setTileEntity(var12, p_150807_2_, var13, var16);
/*  513:     */       }
/*  514: 745 */       if (var16 != null) {
/*  515: 747 */         var16.updateContainingBlockInfo();
/*  516:     */       }
/*  517:     */     }
/*  518: 751 */     this.isModified = true;
/*  519: 752 */     return true;
/*  520:     */   }
/*  521:     */   
/*  522:     */   public boolean setBlockMetadata(int par1, int par2, int par3, int par4)
/*  523:     */   {
/*  524: 762 */     ExtendedBlockStorage var5 = this.storageArrays[(par2 >> 4)];
/*  525: 764 */     if (var5 == null) {
/*  526: 766 */       return false;
/*  527:     */     }
/*  528: 770 */     int var6 = var5.getExtBlockMetadata(par1, par2 & 0xF, par3);
/*  529: 772 */     if (var6 == par4) {
/*  530: 774 */       return false;
/*  531:     */     }
/*  532: 778 */     this.isModified = true;
/*  533: 779 */     var5.setExtBlockMetadata(par1, par2 & 0xF, par3, par4);
/*  534: 781 */     if ((var5.func_150819_a(par1, par2 & 0xF, par3) instanceof ITileEntityProvider))
/*  535:     */     {
/*  536: 783 */       TileEntity var7 = func_150806_e(par1, par2, par3);
/*  537: 785 */       if (var7 != null)
/*  538:     */       {
/*  539: 787 */         var7.updateContainingBlockInfo();
/*  540: 788 */         var7.blockMetadata = par4;
/*  541:     */       }
/*  542:     */     }
/*  543: 792 */     return true;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
/*  547:     */   {
/*  548: 802 */     ExtendedBlockStorage var5 = this.storageArrays[(par3 >> 4)];
/*  549: 803 */     return par1EnumSkyBlock == EnumSkyBlock.Block ? var5.getExtBlocklightValue(par2, par3 & 0xF, par4) : par1EnumSkyBlock == EnumSkyBlock.Sky ? var5.getExtSkylightValue(par2, par3 & 0xF, par4) : this.worldObj.provider.hasNoSky ? 0 : var5 == null ? 0 : canBlockSeeTheSky(par2, par3, par4) ? par1EnumSkyBlock.defaultLightValue : par1EnumSkyBlock.defaultLightValue;
/*  550:     */   }
/*  551:     */   
/*  552:     */   public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5)
/*  553:     */   {
/*  554: 812 */     ExtendedBlockStorage var6 = this.storageArrays[(par3 >> 4)];
/*  555: 814 */     if (var6 == null)
/*  556:     */     {
/*  557: 816 */       var6 = this.storageArrays[(par3 >> 4)] =  = new ExtendedBlockStorage(par3 >> 4 << 4, !this.worldObj.provider.hasNoSky);
/*  558: 817 */       generateSkylightMap();
/*  559:     */     }
/*  560: 820 */     this.isModified = true;
/*  561: 822 */     if (par1EnumSkyBlock == EnumSkyBlock.Sky)
/*  562:     */     {
/*  563: 824 */       if (!this.worldObj.provider.hasNoSky) {
/*  564: 826 */         var6.setExtSkylightValue(par2, par3 & 0xF, par4, par5);
/*  565:     */       }
/*  566:     */     }
/*  567: 829 */     else if (par1EnumSkyBlock == EnumSkyBlock.Block) {
/*  568: 831 */       var6.setExtBlocklightValue(par2, par3 & 0xF, par4, par5);
/*  569:     */     }
/*  570:     */   }
/*  571:     */   
/*  572:     */   public int getBlockLightValue(int par1, int par2, int par3, int par4)
/*  573:     */   {
/*  574: 840 */     ExtendedBlockStorage var5 = this.storageArrays[(par2 >> 4)];
/*  575: 842 */     if (var5 == null) {
/*  576: 844 */       return (!this.worldObj.provider.hasNoSky) && (par4 < EnumSkyBlock.Sky.defaultLightValue) ? EnumSkyBlock.Sky.defaultLightValue - par4 : 0;
/*  577:     */     }
/*  578: 848 */     int var6 = this.worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(par1, par2 & 0xF, par3);
/*  579: 850 */     if (var6 > 0) {
/*  580: 852 */       isLit = true;
/*  581:     */     }
/*  582: 855 */     var6 -= par4;
/*  583: 856 */     int var7 = var5.getExtBlocklightValue(par1, par2 & 0xF, par3);
/*  584: 858 */     if (var7 > var6) {
/*  585: 860 */       var6 = var7;
/*  586:     */     }
/*  587: 863 */     return var6;
/*  588:     */   }
/*  589:     */   
/*  590:     */   public void addEntity(Entity par1Entity)
/*  591:     */   {
/*  592: 872 */     this.hasEntities = true;
/*  593: 873 */     int var2 = MathHelper.floor_double(par1Entity.posX / 16.0D);
/*  594: 874 */     int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0D);
/*  595: 876 */     if ((var2 != this.xPosition) || (var3 != this.zPosition))
/*  596:     */     {
/*  597: 878 */       logger.error("Wrong location! " + par1Entity);
/*  598: 879 */       Thread.dumpStack();
/*  599:     */     }
/*  600: 882 */     int var4 = MathHelper.floor_double(par1Entity.posY / 16.0D);
/*  601: 884 */     if (var4 < 0) {
/*  602: 886 */       var4 = 0;
/*  603:     */     }
/*  604: 889 */     if (var4 >= this.entityLists.length) {
/*  605: 891 */       var4 = this.entityLists.length - 1;
/*  606:     */     }
/*  607: 894 */     par1Entity.addedToChunk = true;
/*  608: 895 */     par1Entity.chunkCoordX = this.xPosition;
/*  609: 896 */     par1Entity.chunkCoordY = var4;
/*  610: 897 */     par1Entity.chunkCoordZ = this.zPosition;
/*  611: 898 */     this.entityLists[var4].add(par1Entity);
/*  612:     */   }
/*  613:     */   
/*  614:     */   public void removeEntity(Entity par1Entity)
/*  615:     */   {
/*  616: 906 */     removeEntityAtIndex(par1Entity, par1Entity.chunkCoordY);
/*  617:     */   }
/*  618:     */   
/*  619:     */   public void removeEntityAtIndex(Entity par1Entity, int par2)
/*  620:     */   {
/*  621: 914 */     if (par2 < 0) {
/*  622: 916 */       par2 = 0;
/*  623:     */     }
/*  624: 919 */     if (par2 >= this.entityLists.length) {
/*  625: 921 */       par2 = this.entityLists.length - 1;
/*  626:     */     }
/*  627: 924 */     this.entityLists[par2].remove(par1Entity);
/*  628:     */   }
/*  629:     */   
/*  630:     */   public boolean canBlockSeeTheSky(int par1, int par2, int par3)
/*  631:     */   {
/*  632: 932 */     return par2 >= this.heightMap[(par3 << 4 | par1)];
/*  633:     */   }
/*  634:     */   
/*  635:     */   public TileEntity func_150806_e(int p_150806_1_, int p_150806_2_, int p_150806_3_)
/*  636:     */   {
/*  637: 937 */     ChunkPosition var4 = new ChunkPosition(p_150806_1_, p_150806_2_, p_150806_3_);
/*  638: 938 */     TileEntity var5 = (TileEntity)this.chunkTileEntityMap.get(var4);
/*  639: 940 */     if (var5 == null)
/*  640:     */     {
/*  641: 942 */       Block var6 = func_150810_a(p_150806_1_, p_150806_2_, p_150806_3_);
/*  642: 944 */       if (!var6.hasTileEntity()) {
/*  643: 946 */         return null;
/*  644:     */       }
/*  645: 949 */       var5 = ((ITileEntityProvider)var6).createNewTileEntity(this.worldObj, getBlockMetadata(p_150806_1_, p_150806_2_, p_150806_3_));
/*  646: 950 */       this.worldObj.setTileEntity(this.xPosition * 16 + p_150806_1_, p_150806_2_, this.zPosition * 16 + p_150806_3_, var5);
/*  647:     */     }
/*  648: 953 */     if ((var5 != null) && (var5.isInvalid()))
/*  649:     */     {
/*  650: 955 */       this.chunkTileEntityMap.remove(var4);
/*  651: 956 */       return null;
/*  652:     */     }
/*  653: 960 */     return var5;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public void addTileEntity(TileEntity p_150813_1_)
/*  657:     */   {
/*  658: 966 */     int var2 = p_150813_1_.field_145851_c - this.xPosition * 16;
/*  659: 967 */     int var3 = p_150813_1_.field_145848_d;
/*  660: 968 */     int var4 = p_150813_1_.field_145849_e - this.zPosition * 16;
/*  661: 969 */     func_150812_a(var2, var3, var4, p_150813_1_);
/*  662: 971 */     if (this.isChunkLoaded) {
/*  663: 973 */       this.worldObj.field_147482_g.add(p_150813_1_);
/*  664:     */     }
/*  665:     */   }
/*  666:     */   
/*  667:     */   public void func_150812_a(int p_150812_1_, int p_150812_2_, int p_150812_3_, TileEntity p_150812_4_)
/*  668:     */   {
/*  669: 979 */     ChunkPosition var5 = new ChunkPosition(p_150812_1_, p_150812_2_, p_150812_3_);
/*  670: 980 */     p_150812_4_.setWorldObj(this.worldObj);
/*  671: 981 */     p_150812_4_.field_145851_c = (this.xPosition * 16 + p_150812_1_);
/*  672: 982 */     p_150812_4_.field_145848_d = p_150812_2_;
/*  673: 983 */     p_150812_4_.field_145849_e = (this.zPosition * 16 + p_150812_3_);
/*  674: 985 */     if ((func_150810_a(p_150812_1_, p_150812_2_, p_150812_3_) instanceof ITileEntityProvider))
/*  675:     */     {
/*  676: 987 */       if (this.chunkTileEntityMap.containsKey(var5)) {
/*  677: 989 */         ((TileEntity)this.chunkTileEntityMap.get(var5)).invalidate();
/*  678:     */       }
/*  679: 992 */       p_150812_4_.validate();
/*  680: 993 */       this.chunkTileEntityMap.put(var5, p_150812_4_);
/*  681:     */     }
/*  682:     */   }
/*  683:     */   
/*  684:     */   public void removeTileEntity(int p_150805_1_, int p_150805_2_, int p_150805_3_)
/*  685:     */   {
/*  686: 999 */     ChunkPosition var4 = new ChunkPosition(p_150805_1_, p_150805_2_, p_150805_3_);
/*  687:1001 */     if (this.isChunkLoaded)
/*  688:     */     {
/*  689:1003 */       TileEntity var5 = (TileEntity)this.chunkTileEntityMap.remove(var4);
/*  690:1005 */       if (var5 != null) {
/*  691:1007 */         var5.invalidate();
/*  692:     */       }
/*  693:     */     }
/*  694:     */   }
/*  695:     */   
/*  696:     */   public void onChunkLoad()
/*  697:     */   {
/*  698:1017 */     this.isChunkLoaded = true;
/*  699:1018 */     this.worldObj.func_147448_a(this.chunkTileEntityMap.values());
/*  700:1020 */     for (int var1 = 0; var1 < this.entityLists.length; var1++)
/*  701:     */     {
/*  702:1022 */       Iterator var2 = this.entityLists[var1].iterator();
/*  703:1024 */       while (var2.hasNext())
/*  704:     */       {
/*  705:1026 */         Entity var3 = (Entity)var2.next();
/*  706:1027 */         var3.onChunkLoad();
/*  707:     */       }
/*  708:1030 */       this.worldObj.addLoadedEntities(this.entityLists[var1]);
/*  709:     */     }
/*  710:     */   }
/*  711:     */   
/*  712:     */   public void onChunkUnload()
/*  713:     */   {
/*  714:1039 */     this.isChunkLoaded = false;
/*  715:1040 */     Iterator var1 = this.chunkTileEntityMap.values().iterator();
/*  716:1042 */     while (var1.hasNext())
/*  717:     */     {
/*  718:1044 */       TileEntity var2 = (TileEntity)var1.next();
/*  719:1045 */       this.worldObj.func_147457_a(var2);
/*  720:     */     }
/*  721:1048 */     for (int var3 = 0; var3 < this.entityLists.length; var3++) {
/*  722:1050 */       this.worldObj.unloadEntities(this.entityLists[var3]);
/*  723:     */     }
/*  724:     */   }
/*  725:     */   
/*  726:     */   public void setChunkModified()
/*  727:     */   {
/*  728:1059 */     this.isModified = true;
/*  729:     */   }
/*  730:     */   
/*  731:     */   public void getEntitiesWithinAABBForEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector)
/*  732:     */   {
/*  733:1068 */     int var5 = MathHelper.floor_double((par2AxisAlignedBB.minY - 2.0D) / 16.0D);
/*  734:1069 */     int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxY + 2.0D) / 16.0D);
/*  735:1070 */     var5 = MathHelper.clamp_int(var5, 0, this.entityLists.length - 1);
/*  736:1071 */     var6 = MathHelper.clamp_int(var6, 0, this.entityLists.length - 1);
/*  737:1073 */     for (int var7 = var5; var7 <= var6; var7++)
/*  738:     */     {
/*  739:1075 */       List var8 = this.entityLists[var7];
/*  740:1077 */       for (int var9 = 0; var9 < var8.size(); var9++)
/*  741:     */       {
/*  742:1079 */         Entity var10 = (Entity)var8.get(var9);
/*  743:1081 */         if ((var10 != par1Entity) && (var10.boundingBox.intersectsWith(par2AxisAlignedBB)) && ((par4IEntitySelector == null) || (par4IEntitySelector.isEntityApplicable(var10))))
/*  744:     */         {
/*  745:1083 */           par3List.add(var10);
/*  746:1084 */           Entity[] var11 = var10.getParts();
/*  747:1086 */           if (var11 != null) {
/*  748:1088 */             for (int var12 = 0; var12 < var11.length; var12++)
/*  749:     */             {
/*  750:1090 */               var10 = var11[var12];
/*  751:1092 */               if ((var10 != par1Entity) && (var10.boundingBox.intersectsWith(par2AxisAlignedBB)) && ((par4IEntitySelector == null) || (par4IEntitySelector.isEntityApplicable(var10)))) {
/*  752:1094 */                 par3List.add(var10);
/*  753:     */               }
/*  754:     */             }
/*  755:     */           }
/*  756:     */         }
/*  757:     */       }
/*  758:     */     }
/*  759:     */   }
/*  760:     */   
/*  761:     */   public void getEntitiesOfTypeWithinAAAB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, List par3List, IEntitySelector par4IEntitySelector)
/*  762:     */   {
/*  763:1108 */     int var5 = MathHelper.floor_double((par2AxisAlignedBB.minY - 2.0D) / 16.0D);
/*  764:1109 */     int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxY + 2.0D) / 16.0D);
/*  765:1110 */     var5 = MathHelper.clamp_int(var5, 0, this.entityLists.length - 1);
/*  766:1111 */     var6 = MathHelper.clamp_int(var6, 0, this.entityLists.length - 1);
/*  767:1113 */     for (int var7 = var5; var7 <= var6; var7++)
/*  768:     */     {
/*  769:1115 */       List var8 = this.entityLists[var7];
/*  770:1117 */       for (int var9 = 0; var9 < var8.size(); var9++)
/*  771:     */       {
/*  772:1119 */         Entity var10 = (Entity)var8.get(var9);
/*  773:1121 */         if ((par1Class.isAssignableFrom(var10.getClass())) && (var10.boundingBox.intersectsWith(par2AxisAlignedBB)) && ((par4IEntitySelector == null) || (par4IEntitySelector.isEntityApplicable(var10)))) {
/*  774:1123 */           par3List.add(var10);
/*  775:     */         }
/*  776:     */       }
/*  777:     */     }
/*  778:     */   }
/*  779:     */   
/*  780:     */   public boolean needsSaving(boolean par1)
/*  781:     */   {
/*  782:1134 */     if (par1)
/*  783:     */     {
/*  784:1136 */       if (((this.hasEntities) && (this.worldObj.getTotalWorldTime() != this.lastSaveTime)) || (this.isModified)) {
/*  785:1138 */         return true;
/*  786:     */       }
/*  787:     */     }
/*  788:1141 */     else if ((this.hasEntities) && (this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L)) {
/*  789:1143 */       return true;
/*  790:     */     }
/*  791:1146 */     return this.isModified;
/*  792:     */   }
/*  793:     */   
/*  794:     */   public Random getRandomWithSeed(long par1)
/*  795:     */   {
/*  796:1151 */     return new Random(this.worldObj.getSeed() + this.xPosition * this.xPosition * 4987142 + this.xPosition * 5947611 + this.zPosition * this.zPosition * 4392871L + this.zPosition * 389711 ^ par1);
/*  797:     */   }
/*  798:     */   
/*  799:     */   public boolean isEmpty()
/*  800:     */   {
/*  801:1156 */     return false;
/*  802:     */   }
/*  803:     */   
/*  804:     */   public void populateChunk(IChunkProvider par1IChunkProvider, IChunkProvider par2IChunkProvider, int par3, int par4)
/*  805:     */   {
/*  806:1161 */     if ((!this.isTerrainPopulated) && (par1IChunkProvider.chunkExists(par3 + 1, par4 + 1)) && (par1IChunkProvider.chunkExists(par3, par4 + 1)) && (par1IChunkProvider.chunkExists(par3 + 1, par4))) {
/*  807:1163 */       par1IChunkProvider.populate(par2IChunkProvider, par3, par4);
/*  808:     */     }
/*  809:1166 */     if ((par1IChunkProvider.chunkExists(par3 - 1, par4)) && (!par1IChunkProvider.provideChunk(par3 - 1, par4).isTerrainPopulated) && (par1IChunkProvider.chunkExists(par3 - 1, par4 + 1)) && (par1IChunkProvider.chunkExists(par3, par4 + 1)) && (par1IChunkProvider.chunkExists(par3 - 1, par4 + 1))) {
/*  810:1168 */       par1IChunkProvider.populate(par2IChunkProvider, par3 - 1, par4);
/*  811:     */     }
/*  812:1171 */     if ((par1IChunkProvider.chunkExists(par3, par4 - 1)) && (!par1IChunkProvider.provideChunk(par3, par4 - 1).isTerrainPopulated) && (par1IChunkProvider.chunkExists(par3 + 1, par4 - 1)) && (par1IChunkProvider.chunkExists(par3 + 1, par4 - 1)) && (par1IChunkProvider.chunkExists(par3 + 1, par4))) {
/*  813:1173 */       par1IChunkProvider.populate(par2IChunkProvider, par3, par4 - 1);
/*  814:     */     }
/*  815:1176 */     if ((par1IChunkProvider.chunkExists(par3 - 1, par4 - 1)) && (!par1IChunkProvider.provideChunk(par3 - 1, par4 - 1).isTerrainPopulated) && (par1IChunkProvider.chunkExists(par3, par4 - 1)) && (par1IChunkProvider.chunkExists(par3 - 1, par4))) {
/*  816:1178 */       par1IChunkProvider.populate(par2IChunkProvider, par3 - 1, par4 - 1);
/*  817:     */     }
/*  818:     */   }
/*  819:     */   
/*  820:     */   public int getPrecipitationHeight(int par1, int par2)
/*  821:     */   {
/*  822:1187 */     int var3 = par1 | par2 << 4;
/*  823:1188 */     int var4 = this.precipitationHeightMap[var3];
/*  824:1190 */     if (var4 == -999)
/*  825:     */     {
/*  826:1192 */       int var5 = getTopFilledSegment() + 15;
/*  827:1193 */       var4 = -1;
/*  828:1195 */       while ((var5 > 0) && (var4 == -1))
/*  829:     */       {
/*  830:1197 */         Block var6 = func_150810_a(par1, var5, par2);
/*  831:1198 */         Material var7 = var6.getMaterial();
/*  832:1200 */         if ((!var7.blocksMovement()) && (!var7.isLiquid())) {
/*  833:1202 */           var5--;
/*  834:     */         } else {
/*  835:1206 */           var4 = var5 + 1;
/*  836:     */         }
/*  837:     */       }
/*  838:1210 */       this.precipitationHeightMap[var3] = var4;
/*  839:     */     }
/*  840:1213 */     return var4;
/*  841:     */   }
/*  842:     */   
/*  843:     */   public void func_150804_b(boolean p_150804_1_)
/*  844:     */   {
/*  845:1218 */     if ((this.isGapLightingUpdated) && (!this.worldObj.provider.hasNoSky) && (!p_150804_1_)) {
/*  846:1220 */       recheckGaps(this.worldObj.isClient);
/*  847:     */     }
/*  848:1223 */     this.field_150815_m = true;
/*  849:1225 */     if ((!this.isLightPopulated) && (this.isTerrainPopulated)) {
/*  850:1227 */       func_150809_p();
/*  851:     */     }
/*  852:     */   }
/*  853:     */   
/*  854:     */   public boolean func_150802_k()
/*  855:     */   {
/*  856:1233 */     return (this.field_150815_m) && (this.isTerrainPopulated) && (this.isLightPopulated);
/*  857:     */   }
/*  858:     */   
/*  859:     */   public ChunkCoordIntPair getChunkCoordIntPair()
/*  860:     */   {
/*  861:1241 */     return new ChunkCoordIntPair(this.xPosition, this.zPosition);
/*  862:     */   }
/*  863:     */   
/*  864:     */   public boolean getAreLevelsEmpty(int par1, int par2)
/*  865:     */   {
/*  866:1250 */     if (par1 < 0) {
/*  867:1252 */       par1 = 0;
/*  868:     */     }
/*  869:1255 */     if (par2 >= 256) {
/*  870:1257 */       par2 = 255;
/*  871:     */     }
/*  872:1260 */     for (int var3 = par1; var3 <= par2; var3 += 16)
/*  873:     */     {
/*  874:1262 */       ExtendedBlockStorage var4 = this.storageArrays[(var3 >> 4)];
/*  875:1264 */       if ((var4 != null) && (!var4.isEmpty())) {
/*  876:1266 */         return false;
/*  877:     */       }
/*  878:     */     }
/*  879:1270 */     return true;
/*  880:     */   }
/*  881:     */   
/*  882:     */   public void setStorageArrays(ExtendedBlockStorage[] par1ArrayOfExtendedBlockStorage)
/*  883:     */   {
/*  884:1275 */     this.storageArrays = par1ArrayOfExtendedBlockStorage;
/*  885:     */   }
/*  886:     */   
/*  887:     */   public void fillChunk(byte[] par1ArrayOfByte, int par2, int par3, boolean par4)
/*  888:     */   {
/*  889:1283 */     int var5 = 0;
/*  890:1284 */     boolean var6 = !this.worldObj.provider.hasNoSky;
/*  891:1287 */     for (int var7 = 0; var7 < this.storageArrays.length; var7++) {
/*  892:1289 */       if ((par2 & 1 << var7) != 0)
/*  893:     */       {
/*  894:1291 */         if (this.storageArrays[var7] == null) {
/*  895:1293 */           this.storageArrays[var7] = new ExtendedBlockStorage(var7 << 4, var6);
/*  896:     */         }
/*  897:1296 */         byte[] var8 = this.storageArrays[var7].getBlockLSBArray();
/*  898:1297 */         System.arraycopy(par1ArrayOfByte, var5, var8, 0, var8.length);
/*  899:1298 */         var5 += var8.length;
/*  900:     */       }
/*  901:1300 */       else if ((par4) && (this.storageArrays[var7] != null))
/*  902:     */       {
/*  903:1302 */         this.storageArrays[var7] = null;
/*  904:     */       }
/*  905:     */     }
/*  906:1308 */     for (var7 = 0; var7 < this.storageArrays.length; var7++) {
/*  907:1310 */       if (((par2 & 1 << var7) != 0) && (this.storageArrays[var7] != null))
/*  908:     */       {
/*  909:1312 */         NibbleArray var9 = this.storageArrays[var7].getMetadataArray();
/*  910:1313 */         System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
/*  911:1314 */         var5 += var9.data.length;
/*  912:     */       }
/*  913:     */     }
/*  914:1318 */     for (var7 = 0; var7 < this.storageArrays.length; var7++) {
/*  915:1320 */       if (((par2 & 1 << var7) != 0) && (this.storageArrays[var7] != null))
/*  916:     */       {
/*  917:1322 */         NibbleArray var9 = this.storageArrays[var7].getBlocklightArray();
/*  918:1323 */         System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
/*  919:1324 */         var5 += var9.data.length;
/*  920:     */       }
/*  921:     */     }
/*  922:1328 */     if (var6) {
/*  923:1330 */       for (var7 = 0; var7 < this.storageArrays.length; var7++) {
/*  924:1332 */         if (((par2 & 1 << var7) != 0) && (this.storageArrays[var7] != null))
/*  925:     */         {
/*  926:1334 */           NibbleArray var9 = this.storageArrays[var7].getSkylightArray();
/*  927:1335 */           System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
/*  928:1336 */           var5 += var9.data.length;
/*  929:     */         }
/*  930:     */       }
/*  931:     */     }
/*  932:1341 */     for (var7 = 0; var7 < this.storageArrays.length; var7++) {
/*  933:1343 */       if ((par3 & 1 << var7) != 0)
/*  934:     */       {
/*  935:1345 */         if (this.storageArrays[var7] == null)
/*  936:     */         {
/*  937:1347 */           var5 += 2048;
/*  938:     */         }
/*  939:     */         else
/*  940:     */         {
/*  941:1351 */           NibbleArray var9 = this.storageArrays[var7].getBlockMSBArray();
/*  942:1353 */           if (var9 == null) {
/*  943:1355 */             var9 = this.storageArrays[var7].createBlockMSBArray();
/*  944:     */           }
/*  945:1358 */           System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
/*  946:1359 */           var5 += var9.data.length;
/*  947:     */         }
/*  948:     */       }
/*  949:1362 */       else if ((par4) && (this.storageArrays[var7] != null) && (this.storageArrays[var7].getBlockMSBArray() != null)) {
/*  950:1364 */         this.storageArrays[var7].clearMSBArray();
/*  951:     */       }
/*  952:     */     }
/*  953:1368 */     if (par4)
/*  954:     */     {
/*  955:1370 */       System.arraycopy(par1ArrayOfByte, var5, this.blockBiomeArray, 0, this.blockBiomeArray.length);
/*  956:1371 */       int i = var5 + this.blockBiomeArray.length;
/*  957:     */     }
/*  958:1374 */     for (var7 = 0; var7 < this.storageArrays.length; var7++) {
/*  959:1376 */       if ((this.storageArrays[var7] != null) && ((par2 & 1 << var7) != 0)) {
/*  960:1378 */         this.storageArrays[var7].removeInvalidBlocks();
/*  961:     */       }
/*  962:     */     }
/*  963:1382 */     this.isLightPopulated = true;
/*  964:1383 */     this.isTerrainPopulated = true;
/*  965:1384 */     generateHeightMap();
/*  966:1385 */     Iterator var10 = this.chunkTileEntityMap.values().iterator();
/*  967:1387 */     while (var10.hasNext())
/*  968:     */     {
/*  969:1389 */       TileEntity var11 = (TileEntity)var10.next();
/*  970:1390 */       var11.updateContainingBlockInfo();
/*  971:     */     }
/*  972:     */   }
/*  973:     */   
/*  974:     */   public BiomeGenBase getBiomeGenForWorldCoords(int par1, int par2, WorldChunkManager par3WorldChunkManager)
/*  975:     */   {
/*  976:1399 */     int var4 = this.blockBiomeArray[(par2 << 4 | par1)] & 0xFF;
/*  977:1401 */     if (var4 == 255)
/*  978:     */     {
/*  979:1403 */       BiomeGenBase var5 = par3WorldChunkManager.getBiomeGenAt((this.xPosition << 4) + par1, (this.zPosition << 4) + par2);
/*  980:1404 */       var4 = var5.biomeID;
/*  981:1405 */       this.blockBiomeArray[(par2 << 4 | par1)] = ((byte)(var4 & 0xFF));
/*  982:     */     }
/*  983:1408 */     return BiomeGenBase.func_150568_d(var4) == null ? BiomeGenBase.plains : BiomeGenBase.func_150568_d(var4);
/*  984:     */   }
/*  985:     */   
/*  986:     */   public byte[] getBiomeArray()
/*  987:     */   {
/*  988:1416 */     return this.blockBiomeArray;
/*  989:     */   }
/*  990:     */   
/*  991:     */   public void setBiomeArray(byte[] par1ArrayOfByte)
/*  992:     */   {
/*  993:1425 */     this.blockBiomeArray = par1ArrayOfByte;
/*  994:     */   }
/*  995:     */   
/*  996:     */   public void resetRelightChecks()
/*  997:     */   {
/*  998:1433 */     this.queuedLightChecks = 0;
/*  999:     */   }
/* 1000:     */   
/* 1001:     */   public void enqueueRelightChecks()
/* 1002:     */   {
/* 1003:1444 */     for (int var1 = 0; var1 < 8; var1++)
/* 1004:     */     {
/* 1005:1446 */       if (this.queuedLightChecks >= 4096) {
/* 1006:1448 */         return;
/* 1007:     */       }
/* 1008:1451 */       int var2 = this.queuedLightChecks % 16;
/* 1009:1452 */       int var3 = this.queuedLightChecks / 16 % 16;
/* 1010:1453 */       int var4 = this.queuedLightChecks / 256;
/* 1011:1454 */       this.queuedLightChecks += 1;
/* 1012:1455 */       int var5 = (this.xPosition << 4) + var3;
/* 1013:1456 */       int var6 = (this.zPosition << 4) + var4;
/* 1014:1458 */       for (int var7 = 0; var7 < 16; var7++)
/* 1015:     */       {
/* 1016:1460 */         int var8 = (var2 << 4) + var7;
/* 1017:1462 */         if (((this.storageArrays[var2] == null) && ((var7 == 0) || (var7 == 15) || (var3 == 0) || (var3 == 15) || (var4 == 0) || (var4 == 15))) || ((this.storageArrays[var2] != null) && (this.storageArrays[var2].func_150819_a(var3, var7, var4).getMaterial() == Material.air)))
/* 1018:     */         {
/* 1019:1464 */           if (this.worldObj.getBlock(var5, var8 - 1, var6).getLightValue() > 0) {
/* 1020:1466 */             this.worldObj.func_147451_t(var5, var8 - 1, var6);
/* 1021:     */           }
/* 1022:1469 */           if (this.worldObj.getBlock(var5, var8 + 1, var6).getLightValue() > 0) {
/* 1023:1471 */             this.worldObj.func_147451_t(var5, var8 + 1, var6);
/* 1024:     */           }
/* 1025:1474 */           if (this.worldObj.getBlock(var5 - 1, var8, var6).getLightValue() > 0) {
/* 1026:1476 */             this.worldObj.func_147451_t(var5 - 1, var8, var6);
/* 1027:     */           }
/* 1028:1479 */           if (this.worldObj.getBlock(var5 + 1, var8, var6).getLightValue() > 0) {
/* 1029:1481 */             this.worldObj.func_147451_t(var5 + 1, var8, var6);
/* 1030:     */           }
/* 1031:1484 */           if (this.worldObj.getBlock(var5, var8, var6 - 1).getLightValue() > 0) {
/* 1032:1486 */             this.worldObj.func_147451_t(var5, var8, var6 - 1);
/* 1033:     */           }
/* 1034:1489 */           if (this.worldObj.getBlock(var5, var8, var6 + 1).getLightValue() > 0) {
/* 1035:1491 */             this.worldObj.func_147451_t(var5, var8, var6 + 1);
/* 1036:     */           }
/* 1037:1494 */           this.worldObj.func_147451_t(var5, var8, var6);
/* 1038:     */         }
/* 1039:     */       }
/* 1040:     */     }
/* 1041:     */   }
/* 1042:     */   
/* 1043:     */   public void func_150809_p()
/* 1044:     */   {
/* 1045:1502 */     this.isTerrainPopulated = true;
/* 1046:1503 */     this.isLightPopulated = true;
/* 1047:1505 */     if (!this.worldObj.provider.hasNoSky) {
/* 1048:1507 */       if (this.worldObj.checkChunksExist(this.xPosition * 16 - 1, 0, this.zPosition * 16 - 1, this.xPosition * 16 + 1, 63, this.zPosition * 16 + 1))
/* 1049:     */       {
/* 1050:1509 */         for (int var1 = 0; var1 < 16; var1++) {
/* 1051:1511 */           for (int var2 = 0; var2 < 16; var2++) {
/* 1052:1513 */             if (!func_150811_f(var1, var2))
/* 1053:     */             {
/* 1054:1515 */               this.isLightPopulated = false;
/* 1055:1516 */               break;
/* 1056:     */             }
/* 1057:     */           }
/* 1058:     */         }
/* 1059:1521 */         if (this.isLightPopulated)
/* 1060:     */         {
/* 1061:1523 */           Chunk var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16 - 1, this.zPosition * 16);
/* 1062:1524 */           var3.func_150801_a(3);
/* 1063:1525 */           var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16 + 16, this.zPosition * 16);
/* 1064:1526 */           var3.func_150801_a(1);
/* 1065:1527 */           var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16, this.zPosition * 16 - 1);
/* 1066:1528 */           var3.func_150801_a(0);
/* 1067:1529 */           var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16, this.zPosition * 16 + 16);
/* 1068:1530 */           var3.func_150801_a(2);
/* 1069:     */         }
/* 1070:     */       }
/* 1071:     */       else
/* 1072:     */       {
/* 1073:1535 */         this.isLightPopulated = false;
/* 1074:     */       }
/* 1075:     */     }
/* 1076:     */   }
/* 1077:     */   
/* 1078:     */   private void func_150801_a(int p_150801_1_)
/* 1079:     */   {
/* 1080:1542 */     if (this.isTerrainPopulated) {
/* 1081:1546 */       if (p_150801_1_ == 3) {
/* 1082:1548 */         for (int var2 = 0; var2 < 16; var2++) {
/* 1083:1550 */           func_150811_f(15, var2);
/* 1084:     */         }
/* 1085:1553 */       } else if (p_150801_1_ == 1) {
/* 1086:1555 */         for (int var2 = 0; var2 < 16; var2++) {
/* 1087:1557 */           func_150811_f(0, var2);
/* 1088:     */         }
/* 1089:1560 */       } else if (p_150801_1_ == 0) {
/* 1090:1562 */         for (int var2 = 0; var2 < 16; var2++) {
/* 1091:1564 */           func_150811_f(var2, 15);
/* 1092:     */         }
/* 1093:1567 */       } else if (p_150801_1_ == 2) {
/* 1094:1569 */         for (int var2 = 0; var2 < 16; var2++) {
/* 1095:1571 */           func_150811_f(var2, 0);
/* 1096:     */         }
/* 1097:     */       }
/* 1098:     */     }
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   private boolean func_150811_f(int p_150811_1_, int p_150811_2_)
/* 1102:     */   {
/* 1103:1579 */     int var3 = getTopFilledSegment();
/* 1104:1580 */     boolean var4 = false;
/* 1105:1581 */     boolean var5 = false;
/* 1106:     */     
/* 1107:     */ 
/* 1108:1584 */     int var6 = var3 + 16 - 1;
/* 1109:     */     do
/* 1110:     */     {
/* 1111:     */       do
/* 1112:     */       {
/* 1113:1586 */         int var7 = func_150808_b(p_150811_1_, var6, p_150811_2_);
/* 1114:1588 */         if ((var7 == 255) && (var6 < 63)) {
/* 1115:1590 */           var5 = true;
/* 1116:     */         }
/* 1117:1593 */         if ((!var4) && (var7 > 0)) {
/* 1118:1595 */           var4 = true;
/* 1119:1597 */         } else if ((var4) && (var7 == 0) && (!this.worldObj.func_147451_t(this.xPosition * 16 + p_150811_1_, var6, this.zPosition * 16 + p_150811_2_))) {
/* 1120:1599 */           return false;
/* 1121:     */         }
/* 1122:1584 */         var6--;
/* 1123:1584 */       } while (var6 > 63);
/* 1124:1584 */       if (var6 <= 0) {
/* 1125:     */         break;
/* 1126:     */       }
/* 1127:1584 */     } while (!var5);
/* 1128:1603 */     for (; var6 > 0; var6--) {
/* 1129:1605 */       if (func_150810_a(p_150811_1_, var6, p_150811_2_).getLightValue() > 0) {
/* 1130:1607 */         this.worldObj.func_147451_t(this.xPosition * 16 + p_150811_1_, var6, this.zPosition * 16 + p_150811_2_);
/* 1131:     */       }
/* 1132:     */     }
/* 1133:1611 */     return true;
/* 1134:     */   }
/* 1135:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.Chunk
 * JD-Core Version:    0.7.0.1
 */