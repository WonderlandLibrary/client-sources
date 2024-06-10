/*    1:     */ package net.minecraft.world;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Calendar;
/*    5:     */ import java.util.Collection;
/*    6:     */ import java.util.HashSet;
/*    7:     */ import java.util.Iterator;
/*    8:     */ import java.util.List;
/*    9:     */ import java.util.Random;
/*   10:     */ import java.util.Set;
/*   11:     */ import java.util.concurrent.Callable;
/*   12:     */ import net.minecraft.block.Block;
/*   13:     */ import net.minecraft.block.BlockLiquid;
/*   14:     */ import net.minecraft.block.BlockRedstoneComparator;
/*   15:     */ import net.minecraft.block.material.Material;
/*   16:     */ import net.minecraft.command.IEntitySelector;
/*   17:     */ import net.minecraft.crash.CrashReport;
/*   18:     */ import net.minecraft.crash.CrashReportCategory;
/*   19:     */ import net.minecraft.entity.Entity;
/*   20:     */ import net.minecraft.entity.EntityLiving;
/*   21:     */ import net.minecraft.entity.player.EntityPlayer;
/*   22:     */ import net.minecraft.entity.player.PlayerCapabilities;
/*   23:     */ import net.minecraft.init.Blocks;
/*   24:     */ import net.minecraft.item.ItemStack;
/*   25:     */ import net.minecraft.nbt.NBTTagCompound;
/*   26:     */ import net.minecraft.pathfinding.PathEntity;
/*   27:     */ import net.minecraft.pathfinding.PathFinder;
/*   28:     */ import net.minecraft.profiler.Profiler;
/*   29:     */ import net.minecraft.scoreboard.Scoreboard;
/*   30:     */ import net.minecraft.server.MinecraftServer;
/*   31:     */ import net.minecraft.tileentity.TileEntity;
/*   32:     */ import net.minecraft.util.AxisAlignedBB;
/*   33:     */ import net.minecraft.util.ChunkCoordinates;
/*   34:     */ import net.minecraft.util.MathHelper;
/*   35:     */ import net.minecraft.util.MovingObjectPosition;
/*   36:     */ import net.minecraft.util.ReportedException;
/*   37:     */ import net.minecraft.util.Vec3;
/*   38:     */ import net.minecraft.util.Vec3Pool;
/*   39:     */ import net.minecraft.village.VillageCollection;
/*   40:     */ import net.minecraft.village.VillageSiege;
/*   41:     */ import net.minecraft.world.biome.BiomeGenBase;
/*   42:     */ import net.minecraft.world.biome.WorldChunkManager;
/*   43:     */ import net.minecraft.world.chunk.Chunk;
/*   44:     */ import net.minecraft.world.chunk.IChunkProvider;
/*   45:     */ import net.minecraft.world.storage.ISaveHandler;
/*   46:     */ import net.minecraft.world.storage.MapStorage;
/*   47:     */ import net.minecraft.world.storage.WorldInfo;
/*   48:     */ 
/*   49:     */ public abstract class World
/*   50:     */   implements IBlockAccess
/*   51:     */ {
/*   52:     */   public boolean scheduledUpdatesAreImmediate;
/*   53:  61 */   public List loadedEntityList = new ArrayList();
/*   54:  62 */   protected List unloadedEntityList = new ArrayList();
/*   55:  63 */   public List field_147482_g = new ArrayList();
/*   56:  64 */   private List field_147484_a = new ArrayList();
/*   57:  65 */   private List field_147483_b = new ArrayList();
/*   58:  68 */   public List playerEntities = new ArrayList();
/*   59:  71 */   public List weatherEffects = new ArrayList();
/*   60:  72 */   private long cloudColour = 16777215L;
/*   61:     */   public int skylightSubtracted;
/*   62:  82 */   protected int updateLCG = new Random().nextInt();
/*   63:  87 */   protected final int DIST_HASH_MAGIC = 1013904223;
/*   64:     */   protected float prevRainingStrength;
/*   65:     */   protected float rainingStrength;
/*   66:     */   protected float prevThunderingStrength;
/*   67:     */   protected float thunderingStrength;
/*   68:     */   public int lastLightningBolt;
/*   69:     */   public EnumDifficulty difficultySetting;
/*   70: 103 */   public Random rand = new Random();
/*   71:     */   public final WorldProvider provider;
/*   72: 107 */   protected List worldAccesses = new ArrayList();
/*   73:     */   protected IChunkProvider chunkProvider;
/*   74:     */   protected final ISaveHandler saveHandler;
/*   75:     */   protected WorldInfo worldInfo;
/*   76:     */   public boolean findingSpawnPoint;
/*   77:     */   public MapStorage mapStorage;
/*   78:     */   public final VillageCollection villageCollectionObj;
/*   79: 122 */   protected final VillageSiege villageSiegeObj = new VillageSiege(this);
/*   80:     */   public final Profiler theProfiler;
/*   81: 126 */   private final Vec3Pool vecPool = new Vec3Pool(300, 2000);
/*   82: 127 */   private final Calendar theCalendar = Calendar.getInstance();
/*   83: 128 */   protected Scoreboard worldScoreboard = new Scoreboard();
/*   84:     */   public boolean isClient;
/*   85: 134 */   protected Set activeChunkSet = new HashSet();
/*   86:     */   private int ambientTickCountdown;
/*   87:     */   protected boolean spawnHostileMobs;
/*   88:     */   protected boolean spawnPeacefulMobs;
/*   89:     */   private ArrayList collidingBoundingBoxes;
/*   90:     */   private boolean field_147481_N;
/*   91:     */   int[] lightUpdateBlockList;
/*   92:     */   private static final String __OBFID = "CL_00000140";
/*   93:     */   
/*   94:     */   public BiomeGenBase getBiomeGenForCoords(final int par1, final int par2)
/*   95:     */   {
/*   96: 161 */     if (blockExists(par1, 0, par2))
/*   97:     */     {
/*   98: 163 */       Chunk var3 = getChunkFromBlockCoords(par1, par2);
/*   99:     */       try
/*  100:     */       {
/*  101: 167 */         return var3.getBiomeGenForWorldCoords(par1 & 0xF, par2 & 0xF, this.provider.worldChunkMgr);
/*  102:     */       }
/*  103:     */       catch (Throwable var7)
/*  104:     */       {
/*  105: 171 */         CrashReport var5 = CrashReport.makeCrashReport(var7, "Getting biome");
/*  106: 172 */         CrashReportCategory var6 = var5.makeCategory("Coordinates of biome request");
/*  107: 173 */         var6.addCrashSectionCallable("Location", new Callable()
/*  108:     */         {
/*  109:     */           private static final String __OBFID = "CL_00000141";
/*  110:     */           
/*  111:     */           public String call()
/*  112:     */           {
/*  113: 178 */             return CrashReportCategory.getLocationInfo(par1, 0, par2);
/*  114:     */           }
/*  115: 180 */         });
/*  116: 181 */         throw new ReportedException(var5);
/*  117:     */       }
/*  118:     */     }
/*  119: 186 */     return this.provider.worldChunkMgr.getBiomeGenAt(par1, par2);
/*  120:     */   }
/*  121:     */   
/*  122:     */   public WorldChunkManager getWorldChunkManager()
/*  123:     */   {
/*  124: 192 */     return this.provider.worldChunkMgr;
/*  125:     */   }
/*  126:     */   
/*  127:     */   public World(ISaveHandler p_i45368_1_, String p_i45368_2_, WorldProvider p_i45368_3_, WorldSettings p_i45368_4_, Profiler p_i45368_5_)
/*  128:     */   {
/*  129: 197 */     this.ambientTickCountdown = this.rand.nextInt(12000);
/*  130: 198 */     this.spawnHostileMobs = true;
/*  131: 199 */     this.spawnPeacefulMobs = true;
/*  132: 200 */     this.collidingBoundingBoxes = new ArrayList();
/*  133: 201 */     this.lightUpdateBlockList = new int[32768];
/*  134: 202 */     this.saveHandler = p_i45368_1_;
/*  135: 203 */     this.theProfiler = p_i45368_5_;
/*  136: 204 */     this.worldInfo = new WorldInfo(p_i45368_4_, p_i45368_2_);
/*  137: 205 */     this.provider = p_i45368_3_;
/*  138: 206 */     this.mapStorage = new MapStorage(p_i45368_1_);
/*  139: 207 */     VillageCollection var6 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, "villages");
/*  140: 209 */     if (var6 == null)
/*  141:     */     {
/*  142: 211 */       this.villageCollectionObj = new VillageCollection(this);
/*  143: 212 */       this.mapStorage.setData("villages", this.villageCollectionObj);
/*  144:     */     }
/*  145:     */     else
/*  146:     */     {
/*  147: 216 */       this.villageCollectionObj = var6;
/*  148: 217 */       this.villageCollectionObj.func_82566_a(this);
/*  149:     */     }
/*  150: 220 */     p_i45368_3_.registerWorld(this);
/*  151: 221 */     this.chunkProvider = createChunkProvider();
/*  152: 222 */     calculateInitialSkylight();
/*  153: 223 */     calculateInitialWeather();
/*  154:     */   }
/*  155:     */   
/*  156:     */   public World(ISaveHandler p_i45369_1_, String p_i45369_2_, WorldSettings p_i45369_3_, WorldProvider p_i45369_4_, Profiler p_i45369_5_)
/*  157:     */   {
/*  158: 228 */     this.ambientTickCountdown = this.rand.nextInt(12000);
/*  159: 229 */     this.spawnHostileMobs = true;
/*  160: 230 */     this.spawnPeacefulMobs = true;
/*  161: 231 */     this.collidingBoundingBoxes = new ArrayList();
/*  162: 232 */     this.lightUpdateBlockList = new int[32768];
/*  163: 233 */     this.saveHandler = p_i45369_1_;
/*  164: 234 */     this.theProfiler = p_i45369_5_;
/*  165: 235 */     this.mapStorage = new MapStorage(p_i45369_1_);
/*  166: 236 */     this.worldInfo = p_i45369_1_.loadWorldInfo();
/*  167: 238 */     if (p_i45369_4_ != null) {
/*  168: 240 */       this.provider = p_i45369_4_;
/*  169: 242 */     } else if ((this.worldInfo != null) && (this.worldInfo.getVanillaDimension() != 0)) {
/*  170: 244 */       this.provider = WorldProvider.getProviderForDimension(this.worldInfo.getVanillaDimension());
/*  171:     */     } else {
/*  172: 248 */       this.provider = WorldProvider.getProviderForDimension(0);
/*  173:     */     }
/*  174: 251 */     if (this.worldInfo == null) {
/*  175: 253 */       this.worldInfo = new WorldInfo(p_i45369_3_, p_i45369_2_);
/*  176:     */     } else {
/*  177: 257 */       this.worldInfo.setWorldName(p_i45369_2_);
/*  178:     */     }
/*  179: 260 */     this.provider.registerWorld(this);
/*  180: 261 */     this.chunkProvider = createChunkProvider();
/*  181: 263 */     if (!this.worldInfo.isInitialized())
/*  182:     */     {
/*  183:     */       try
/*  184:     */       {
/*  185: 267 */         initialize(p_i45369_3_);
/*  186:     */       }
/*  187:     */       catch (Throwable var10)
/*  188:     */       {
/*  189: 271 */         CrashReport var7 = CrashReport.makeCrashReport(var10, "Exception initializing level");
/*  190:     */         try
/*  191:     */         {
/*  192: 275 */           addWorldInfoToCrashReport(var7);
/*  193:     */         }
/*  194:     */         catch (Throwable localThrowable1) {}
/*  195: 282 */         throw new ReportedException(var7);
/*  196:     */       }
/*  197: 285 */       this.worldInfo.setServerInitialized(true);
/*  198:     */     }
/*  199: 288 */     VillageCollection var6 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, "villages");
/*  200: 290 */     if (var6 == null)
/*  201:     */     {
/*  202: 292 */       this.villageCollectionObj = new VillageCollection(this);
/*  203: 293 */       this.mapStorage.setData("villages", this.villageCollectionObj);
/*  204:     */     }
/*  205:     */     else
/*  206:     */     {
/*  207: 297 */       this.villageCollectionObj = var6;
/*  208: 298 */       this.villageCollectionObj.func_82566_a(this);
/*  209:     */     }
/*  210: 301 */     calculateInitialSkylight();
/*  211: 302 */     calculateInitialWeather();
/*  212:     */   }
/*  213:     */   
/*  214:     */   protected abstract IChunkProvider createChunkProvider();
/*  215:     */   
/*  216:     */   protected void initialize(WorldSettings par1WorldSettings)
/*  217:     */   {
/*  218: 312 */     this.worldInfo.setServerInitialized(true);
/*  219:     */   }
/*  220:     */   
/*  221:     */   public void setSpawnLocation()
/*  222:     */   {
/*  223: 320 */     setSpawnLocation(8, 64, 8);
/*  224:     */   }
/*  225:     */   
/*  226:     */   public Block getTopBlock(int p_147474_1_, int p_147474_2_)
/*  227:     */   {
/*  228: 327 */     for (int var3 = 63; !isAirBlock(p_147474_1_, var3 + 1, p_147474_2_); var3++) {}
/*  229: 332 */     return getBlock(p_147474_1_, var3, p_147474_2_);
/*  230:     */   }
/*  231:     */   
/*  232:     */   public Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_)
/*  233:     */   {
/*  234: 337 */     if ((p_147439_1_ >= -30000000) && (p_147439_3_ >= -30000000) && (p_147439_1_ < 30000000) && (p_147439_3_ < 30000000) && (p_147439_2_ >= 0) && (p_147439_2_ < 256))
/*  235:     */     {
/*  236: 339 */       Chunk var4 = null;
/*  237:     */       try
/*  238:     */       {
/*  239: 343 */         var4 = getChunkFromChunkCoords(p_147439_1_ >> 4, p_147439_3_ >> 4);
/*  240: 344 */         return var4.func_150810_a(p_147439_1_ & 0xF, p_147439_2_, p_147439_3_ & 0xF);
/*  241:     */       }
/*  242:     */       catch (Throwable var8)
/*  243:     */       {
/*  244: 348 */         CrashReport var6 = CrashReport.makeCrashReport(var8, "Exception getting block type in world");
/*  245: 349 */         CrashReportCategory var7 = var6.makeCategory("Requested block coordinates");
/*  246: 350 */         var7.addCrashSection("Found chunk", Boolean.valueOf(var4 == null));
/*  247: 351 */         var7.addCrashSection("Location", CrashReportCategory.getLocationInfo(p_147439_1_, p_147439_2_, p_147439_3_));
/*  248: 352 */         throw new ReportedException(var6);
/*  249:     */       }
/*  250:     */     }
/*  251: 357 */     return Blocks.air;
/*  252:     */   }
/*  253:     */   
/*  254:     */   public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_)
/*  255:     */   {
/*  256: 366 */     return getBlock(p_147437_1_, p_147437_2_, p_147437_3_).getMaterial() == Material.air;
/*  257:     */   }
/*  258:     */   
/*  259:     */   public boolean blockExists(int par1, int par2, int par3)
/*  260:     */   {
/*  261: 374 */     return (par2 >= 0) && (par2 < 256) ? chunkExists(par1 >> 4, par3 >> 4) : false;
/*  262:     */   }
/*  263:     */   
/*  264:     */   public boolean doChunksNearChunkExist(int par1, int par2, int par3, int par4)
/*  265:     */   {
/*  266: 382 */     return checkChunksExist(par1 - par4, par2 - par4, par3 - par4, par1 + par4, par2 + par4, par3 + par4);
/*  267:     */   }
/*  268:     */   
/*  269:     */   public boolean checkChunksExist(int par1, int par2, int par3, int par4, int par5, int par6)
/*  270:     */   {
/*  271: 390 */     if ((par5 >= 0) && (par2 < 256))
/*  272:     */     {
/*  273: 392 */       par1 >>= 4;
/*  274: 393 */       par3 >>= 4;
/*  275: 394 */       par4 >>= 4;
/*  276: 395 */       par6 >>= 4;
/*  277: 397 */       for (int var7 = par1; var7 <= par4; var7++) {
/*  278: 399 */         for (int var8 = par3; var8 <= par6; var8++) {
/*  279: 401 */           if (!chunkExists(var7, var8)) {
/*  280: 403 */             return false;
/*  281:     */           }
/*  282:     */         }
/*  283:     */       }
/*  284: 408 */       return true;
/*  285:     */     }
/*  286: 412 */     return false;
/*  287:     */   }
/*  288:     */   
/*  289:     */   protected boolean chunkExists(int par1, int par2)
/*  290:     */   {
/*  291: 421 */     return this.chunkProvider.chunkExists(par1, par2);
/*  292:     */   }
/*  293:     */   
/*  294:     */   public Chunk getChunkFromBlockCoords(int par1, int par2)
/*  295:     */   {
/*  296: 429 */     return getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
/*  297:     */   }
/*  298:     */   
/*  299:     */   public Chunk getChunkFromChunkCoords(int par1, int par2)
/*  300:     */   {
/*  301: 437 */     return this.chunkProvider.provideChunk(par1, par2);
/*  302:     */   }
/*  303:     */   
/*  304:     */   public boolean setBlock(int p_147465_1_, int p_147465_2_, int p_147465_3_, Block p_147465_4_, int p_147465_5_, int p_147465_6_)
/*  305:     */   {
/*  306: 447 */     if ((p_147465_1_ >= -30000000) && (p_147465_3_ >= -30000000) && (p_147465_1_ < 30000000) && (p_147465_3_ < 30000000))
/*  307:     */     {
/*  308: 449 */       if (p_147465_2_ < 0) {
/*  309: 451 */         return false;
/*  310:     */       }
/*  311: 453 */       if (p_147465_2_ >= 256) {
/*  312: 455 */         return false;
/*  313:     */       }
/*  314: 459 */       Chunk var7 = getChunkFromChunkCoords(p_147465_1_ >> 4, p_147465_3_ >> 4);
/*  315: 460 */       Block var8 = null;
/*  316: 462 */       if ((p_147465_6_ & 0x1) != 0) {
/*  317: 464 */         var8 = var7.func_150810_a(p_147465_1_ & 0xF, p_147465_2_, p_147465_3_ & 0xF);
/*  318:     */       }
/*  319: 467 */       boolean var9 = var7.func_150807_a(p_147465_1_ & 0xF, p_147465_2_, p_147465_3_ & 0xF, p_147465_4_, p_147465_5_);
/*  320: 468 */       this.theProfiler.startSection("checkLight");
/*  321: 469 */       func_147451_t(p_147465_1_, p_147465_2_, p_147465_3_);
/*  322: 470 */       this.theProfiler.endSection();
/*  323: 472 */       if (var9)
/*  324:     */       {
/*  325: 474 */         if (((p_147465_6_ & 0x2) != 0) && ((!this.isClient) || ((p_147465_6_ & 0x4) == 0)) && (var7.func_150802_k())) {
/*  326: 476 */           func_147471_g(p_147465_1_, p_147465_2_, p_147465_3_);
/*  327:     */         }
/*  328: 479 */         if ((!this.isClient) && ((p_147465_6_ & 0x1) != 0))
/*  329:     */         {
/*  330: 481 */           notifyBlockChange(p_147465_1_, p_147465_2_, p_147465_3_, var8);
/*  331: 483 */           if (p_147465_4_.hasComparatorInputOverride()) {
/*  332: 485 */             func_147453_f(p_147465_1_, p_147465_2_, p_147465_3_, p_147465_4_);
/*  333:     */           }
/*  334:     */         }
/*  335:     */       }
/*  336: 490 */       return var9;
/*  337:     */     }
/*  338: 495 */     return false;
/*  339:     */   }
/*  340:     */   
/*  341:     */   public int getBlockMetadata(int par1, int par2, int par3)
/*  342:     */   {
/*  343: 504 */     if ((par1 >= -30000000) && (par3 >= -30000000) && (par1 < 30000000) && (par3 < 30000000))
/*  344:     */     {
/*  345: 506 */       if (par2 < 0) {
/*  346: 508 */         return 0;
/*  347:     */       }
/*  348: 510 */       if (par2 >= 256) {
/*  349: 512 */         return 0;
/*  350:     */       }
/*  351: 516 */       Chunk var4 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
/*  352: 517 */       par1 &= 0xF;
/*  353: 518 */       par3 &= 0xF;
/*  354: 519 */       return var4.getBlockMetadata(par1, par2, par3);
/*  355:     */     }
/*  356: 524 */     return 0;
/*  357:     */   }
/*  358:     */   
/*  359:     */   public boolean setBlockMetadataWithNotify(int par1, int par2, int par3, int par4, int par5)
/*  360:     */   {
/*  361: 534 */     if ((par1 >= -30000000) && (par3 >= -30000000) && (par1 < 30000000) && (par3 < 30000000))
/*  362:     */     {
/*  363: 536 */       if (par2 < 0) {
/*  364: 538 */         return false;
/*  365:     */       }
/*  366: 540 */       if (par2 >= 256) {
/*  367: 542 */         return false;
/*  368:     */       }
/*  369: 546 */       Chunk var6 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
/*  370: 547 */       int var7 = par1 & 0xF;
/*  371: 548 */       int var8 = par3 & 0xF;
/*  372: 549 */       boolean var9 = var6.setBlockMetadata(var7, par2, var8, par4);
/*  373: 551 */       if (var9)
/*  374:     */       {
/*  375: 553 */         Block var10 = var6.func_150810_a(var7, par2, var8);
/*  376: 555 */         if (((par5 & 0x2) != 0) && ((!this.isClient) || ((par5 & 0x4) == 0)) && (var6.func_150802_k())) {
/*  377: 557 */           func_147471_g(par1, par2, par3);
/*  378:     */         }
/*  379: 560 */         if ((!this.isClient) && ((par5 & 0x1) != 0))
/*  380:     */         {
/*  381: 562 */           notifyBlockChange(par1, par2, par3, var10);
/*  382: 564 */           if (var10.hasComparatorInputOverride()) {
/*  383: 566 */             func_147453_f(par1, par2, par3, var10);
/*  384:     */           }
/*  385:     */         }
/*  386:     */       }
/*  387: 571 */       return var9;
/*  388:     */     }
/*  389: 576 */     return false;
/*  390:     */   }
/*  391:     */   
/*  392:     */   public boolean setBlockToAir(int p_147468_1_, int p_147468_2_, int p_147468_3_)
/*  393:     */   {
/*  394: 582 */     return setBlock(p_147468_1_, p_147468_2_, p_147468_3_, Blocks.air, 0, 3);
/*  395:     */   }
/*  396:     */   
/*  397:     */   public boolean func_147480_a(int p_147480_1_, int p_147480_2_, int p_147480_3_, boolean p_147480_4_)
/*  398:     */   {
/*  399: 587 */     Block var5 = getBlock(p_147480_1_, p_147480_2_, p_147480_3_);
/*  400: 589 */     if (var5.getMaterial() == Material.air) {
/*  401: 591 */       return false;
/*  402:     */     }
/*  403: 595 */     int var6 = getBlockMetadata(p_147480_1_, p_147480_2_, p_147480_3_);
/*  404: 596 */     playAuxSFX(2001, p_147480_1_, p_147480_2_, p_147480_3_, Block.getIdFromBlock(var5) + (var6 << 12));
/*  405: 598 */     if (p_147480_4_) {
/*  406: 600 */       var5.dropBlockAsItem(this, p_147480_1_, p_147480_2_, p_147480_3_, var6, 0);
/*  407:     */     }
/*  408: 603 */     return setBlock(p_147480_1_, p_147480_2_, p_147480_3_, Blocks.air, 0, 3);
/*  409:     */   }
/*  410:     */   
/*  411:     */   public boolean setBlock(int p_147449_1_, int p_147449_2_, int p_147449_3_, Block p_147449_4_)
/*  412:     */   {
/*  413: 612 */     return setBlock(p_147449_1_, p_147449_2_, p_147449_3_, p_147449_4_, 0, 3);
/*  414:     */   }
/*  415:     */   
/*  416:     */   public void func_147471_g(int p_147471_1_, int p_147471_2_, int p_147471_3_)
/*  417:     */   {
/*  418: 617 */     for (int var4 = 0; var4 < this.worldAccesses.size(); var4++) {
/*  419: 619 */       ((IWorldAccess)this.worldAccesses.get(var4)).markBlockForUpdate(p_147471_1_, p_147471_2_, p_147471_3_);
/*  420:     */     }
/*  421:     */   }
/*  422:     */   
/*  423:     */   public void notifyBlockChange(int p_147444_1_, int p_147444_2_, int p_147444_3_, Block p_147444_4_)
/*  424:     */   {
/*  425: 628 */     notifyBlocksOfNeighborChange(p_147444_1_, p_147444_2_, p_147444_3_, p_147444_4_);
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void markBlocksDirtyVertical(int par1, int par2, int par3, int par4)
/*  429:     */   {
/*  430: 638 */     if (par3 > par4)
/*  431:     */     {
/*  432: 640 */       int var5 = par4;
/*  433: 641 */       par4 = par3;
/*  434: 642 */       par3 = var5;
/*  435:     */     }
/*  436: 645 */     if (!this.provider.hasNoSky) {
/*  437: 647 */       for (int var5 = par3; var5 <= par4; var5++) {
/*  438: 649 */         updateLightByType(EnumSkyBlock.Sky, par1, var5, par2);
/*  439:     */       }
/*  440:     */     }
/*  441: 653 */     markBlockRangeForRenderUpdate(par1, par3, par2, par1, par4, par2);
/*  442:     */   }
/*  443:     */   
/*  444:     */   public void markBlockRangeForRenderUpdate(int p_147458_1_, int p_147458_2_, int p_147458_3_, int p_147458_4_, int p_147458_5_, int p_147458_6_)
/*  445:     */   {
/*  446: 658 */     for (int var7 = 0; var7 < this.worldAccesses.size(); var7++) {
/*  447: 660 */       ((IWorldAccess)this.worldAccesses.get(var7)).markBlockRangeForRenderUpdate(p_147458_1_, p_147458_2_, p_147458_3_, p_147458_4_, p_147458_5_, p_147458_6_);
/*  448:     */     }
/*  449:     */   }
/*  450:     */   
/*  451:     */   public void notifyBlocksOfNeighborChange(int p_147459_1_, int p_147459_2_, int p_147459_3_, Block p_147459_4_)
/*  452:     */   {
/*  453: 666 */     func_147460_e(p_147459_1_ - 1, p_147459_2_, p_147459_3_, p_147459_4_);
/*  454: 667 */     func_147460_e(p_147459_1_ + 1, p_147459_2_, p_147459_3_, p_147459_4_);
/*  455: 668 */     func_147460_e(p_147459_1_, p_147459_2_ - 1, p_147459_3_, p_147459_4_);
/*  456: 669 */     func_147460_e(p_147459_1_, p_147459_2_ + 1, p_147459_3_, p_147459_4_);
/*  457: 670 */     func_147460_e(p_147459_1_, p_147459_2_, p_147459_3_ - 1, p_147459_4_);
/*  458: 671 */     func_147460_e(p_147459_1_, p_147459_2_, p_147459_3_ + 1, p_147459_4_);
/*  459:     */   }
/*  460:     */   
/*  461:     */   public void func_147441_b(int p_147441_1_, int p_147441_2_, int p_147441_3_, Block p_147441_4_, int p_147441_5_)
/*  462:     */   {
/*  463: 676 */     if (p_147441_5_ != 4) {
/*  464: 678 */       func_147460_e(p_147441_1_ - 1, p_147441_2_, p_147441_3_, p_147441_4_);
/*  465:     */     }
/*  466: 681 */     if (p_147441_5_ != 5) {
/*  467: 683 */       func_147460_e(p_147441_1_ + 1, p_147441_2_, p_147441_3_, p_147441_4_);
/*  468:     */     }
/*  469: 686 */     if (p_147441_5_ != 0) {
/*  470: 688 */       func_147460_e(p_147441_1_, p_147441_2_ - 1, p_147441_3_, p_147441_4_);
/*  471:     */     }
/*  472: 691 */     if (p_147441_5_ != 1) {
/*  473: 693 */       func_147460_e(p_147441_1_, p_147441_2_ + 1, p_147441_3_, p_147441_4_);
/*  474:     */     }
/*  475: 696 */     if (p_147441_5_ != 2) {
/*  476: 698 */       func_147460_e(p_147441_1_, p_147441_2_, p_147441_3_ - 1, p_147441_4_);
/*  477:     */     }
/*  478: 701 */     if (p_147441_5_ != 3) {
/*  479: 703 */       func_147460_e(p_147441_1_, p_147441_2_, p_147441_3_ + 1, p_147441_4_);
/*  480:     */     }
/*  481:     */   }
/*  482:     */   
/*  483:     */   public void func_147460_e(int p_147460_1_, int p_147460_2_, int p_147460_3_, final Block p_147460_4_)
/*  484:     */   {
/*  485: 709 */     if (!this.isClient)
/*  486:     */     {
/*  487: 711 */       Block var5 = getBlock(p_147460_1_, p_147460_2_, p_147460_3_);
/*  488:     */       try
/*  489:     */       {
/*  490: 715 */         var5.onNeighborBlockChange(this, p_147460_1_, p_147460_2_, p_147460_3_, p_147460_4_);
/*  491:     */       }
/*  492:     */       catch (Throwable var12)
/*  493:     */       {
/*  494: 719 */         CrashReport var7 = CrashReport.makeCrashReport(var12, "Exception while updating neighbours");
/*  495: 720 */         CrashReportCategory var8 = var7.makeCategory("Block being updated");
/*  496:     */         int var9;
/*  497:     */         try
/*  498:     */         {
/*  499: 725 */           var9 = getBlockMetadata(p_147460_1_, p_147460_2_, p_147460_3_);
/*  500:     */         }
/*  501:     */         catch (Throwable var11)
/*  502:     */         {
/*  503:     */           int var9;
/*  504: 729 */           var9 = -1;
/*  505:     */         }
/*  506: 732 */         var8.addCrashSectionCallable("Source block type", new Callable()
/*  507:     */         {
/*  508:     */           private static final String __OBFID = "CL_00000142";
/*  509:     */           
/*  510:     */           public String call()
/*  511:     */           {
/*  512:     */             try
/*  513:     */             {
/*  514: 739 */               return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(Block.getIdFromBlock(p_147460_4_)), p_147460_4_.getUnlocalizedName(), p_147460_4_.getClass().getCanonicalName() });
/*  515:     */             }
/*  516:     */             catch (Throwable var2) {}
/*  517: 743 */             return "ID #" + Block.getIdFromBlock(p_147460_4_);
/*  518:     */           }
/*  519: 746 */         });
/*  520: 747 */         CrashReportCategory.func_147153_a(var8, p_147460_1_, p_147460_2_, p_147460_3_, var5, var9);
/*  521: 748 */         throw new ReportedException(var7);
/*  522:     */       }
/*  523:     */     }
/*  524:     */   }
/*  525:     */   
/*  526:     */   public boolean func_147477_a(int p_147477_1_, int p_147477_2_, int p_147477_3_, Block p_147477_4_)
/*  527:     */   {
/*  528: 755 */     return false;
/*  529:     */   }
/*  530:     */   
/*  531:     */   public boolean canBlockSeeTheSky(int par1, int par2, int par3)
/*  532:     */   {
/*  533: 763 */     return getChunkFromChunkCoords(par1 >> 4, par3 >> 4).canBlockSeeTheSky(par1 & 0xF, par2, par3 & 0xF);
/*  534:     */   }
/*  535:     */   
/*  536:     */   public int getFullBlockLightValue(int par1, int par2, int par3)
/*  537:     */   {
/*  538: 771 */     if (par2 < 0) {
/*  539: 773 */       return 0;
/*  540:     */     }
/*  541: 777 */     if (par2 >= 256) {
/*  542: 779 */       par2 = 255;
/*  543:     */     }
/*  544: 782 */     return getChunkFromChunkCoords(par1 >> 4, par3 >> 4).getBlockLightValue(par1 & 0xF, par2, par3 & 0xF, 0);
/*  545:     */   }
/*  546:     */   
/*  547:     */   public int getBlockLightValue(int par1, int par2, int par3)
/*  548:     */   {
/*  549: 791 */     return getBlockLightValue_do(par1, par2, par3, true);
/*  550:     */   }
/*  551:     */   
/*  552:     */   public int getBlockLightValue_do(int par1, int par2, int par3, boolean par4)
/*  553:     */   {
/*  554: 801 */     if ((par1 >= -30000000) && (par3 >= -30000000) && (par1 < 30000000) && (par3 < 30000000))
/*  555:     */     {
/*  556: 803 */       if ((par4) && (getBlock(par1, par2, par3).func_149710_n()))
/*  557:     */       {
/*  558: 805 */         int var10 = getBlockLightValue_do(par1, par2 + 1, par3, false);
/*  559: 806 */         int var6 = getBlockLightValue_do(par1 + 1, par2, par3, false);
/*  560: 807 */         int var7 = getBlockLightValue_do(par1 - 1, par2, par3, false);
/*  561: 808 */         int var8 = getBlockLightValue_do(par1, par2, par3 + 1, false);
/*  562: 809 */         int var9 = getBlockLightValue_do(par1, par2, par3 - 1, false);
/*  563: 811 */         if (var6 > var10) {
/*  564: 813 */           var10 = var6;
/*  565:     */         }
/*  566: 816 */         if (var7 > var10) {
/*  567: 818 */           var10 = var7;
/*  568:     */         }
/*  569: 821 */         if (var8 > var10) {
/*  570: 823 */           var10 = var8;
/*  571:     */         }
/*  572: 826 */         if (var9 > var10) {
/*  573: 828 */           var10 = var9;
/*  574:     */         }
/*  575: 831 */         return var10;
/*  576:     */       }
/*  577: 833 */       if (par2 < 0) {
/*  578: 835 */         return 0;
/*  579:     */       }
/*  580: 839 */       if (par2 >= 256) {
/*  581: 841 */         par2 = 255;
/*  582:     */       }
/*  583: 844 */       Chunk var5 = getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
/*  584: 845 */       par1 &= 0xF;
/*  585: 846 */       par3 &= 0xF;
/*  586: 847 */       return var5.getBlockLightValue(par1, par2, par3, this.skylightSubtracted);
/*  587:     */     }
/*  588: 852 */     return 15;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public int getHeightValue(int par1, int par2)
/*  592:     */   {
/*  593: 861 */     if ((par1 >= -30000000) && (par2 >= -30000000) && (par1 < 30000000) && (par2 < 30000000))
/*  594:     */     {
/*  595: 863 */       if (!chunkExists(par1 >> 4, par2 >> 4)) {
/*  596: 865 */         return 0;
/*  597:     */       }
/*  598: 869 */       Chunk var3 = getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
/*  599: 870 */       return var3.getHeightValue(par1 & 0xF, par2 & 0xF);
/*  600:     */     }
/*  601: 875 */     return 64;
/*  602:     */   }
/*  603:     */   
/*  604:     */   public int getChunkHeightMapMinimum(int par1, int par2)
/*  605:     */   {
/*  606: 885 */     if ((par1 >= -30000000) && (par2 >= -30000000) && (par1 < 30000000) && (par2 < 30000000))
/*  607:     */     {
/*  608: 887 */       if (!chunkExists(par1 >> 4, par2 >> 4)) {
/*  609: 889 */         return 0;
/*  610:     */       }
/*  611: 893 */       Chunk var3 = getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
/*  612: 894 */       return var3.heightMapMinimum;
/*  613:     */     }
/*  614: 899 */     return 64;
/*  615:     */   }
/*  616:     */   
/*  617:     */   public int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
/*  618:     */   {
/*  619: 909 */     if ((this.provider.hasNoSky) && (par1EnumSkyBlock == EnumSkyBlock.Sky)) {
/*  620: 911 */       return 0;
/*  621:     */     }
/*  622: 915 */     if (par3 < 0) {
/*  623: 917 */       par3 = 0;
/*  624:     */     }
/*  625: 920 */     if (par3 >= 256) {
/*  626: 922 */       return par1EnumSkyBlock.defaultLightValue;
/*  627:     */     }
/*  628: 924 */     if ((par2 >= -30000000) && (par4 >= -30000000) && (par2 < 30000000) && (par4 < 30000000))
/*  629:     */     {
/*  630: 926 */       int var5 = par2 >> 4;
/*  631: 927 */       int var6 = par4 >> 4;
/*  632: 929 */       if (!chunkExists(var5, var6)) {
/*  633: 931 */         return par1EnumSkyBlock.defaultLightValue;
/*  634:     */       }
/*  635: 933 */       if (getBlock(par2, par3, par4).func_149710_n())
/*  636:     */       {
/*  637: 935 */         int var12 = getSavedLightValue(par1EnumSkyBlock, par2, par3 + 1, par4);
/*  638: 936 */         int var8 = getSavedLightValue(par1EnumSkyBlock, par2 + 1, par3, par4);
/*  639: 937 */         int var9 = getSavedLightValue(par1EnumSkyBlock, par2 - 1, par3, par4);
/*  640: 938 */         int var10 = getSavedLightValue(par1EnumSkyBlock, par2, par3, par4 + 1);
/*  641: 939 */         int var11 = getSavedLightValue(par1EnumSkyBlock, par2, par3, par4 - 1);
/*  642: 941 */         if (var8 > var12) {
/*  643: 943 */           var12 = var8;
/*  644:     */         }
/*  645: 946 */         if (var9 > var12) {
/*  646: 948 */           var12 = var9;
/*  647:     */         }
/*  648: 951 */         if (var10 > var12) {
/*  649: 953 */           var12 = var10;
/*  650:     */         }
/*  651: 956 */         if (var11 > var12) {
/*  652: 958 */           var12 = var11;
/*  653:     */         }
/*  654: 961 */         return var12;
/*  655:     */       }
/*  656: 965 */       Chunk var7 = getChunkFromChunkCoords(var5, var6);
/*  657: 966 */       return var7.getSavedLightValue(par1EnumSkyBlock, par2 & 0xF, par3, par4 & 0xF);
/*  658:     */     }
/*  659: 971 */     return par1EnumSkyBlock.defaultLightValue;
/*  660:     */   }
/*  661:     */   
/*  662:     */   public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
/*  663:     */   {
/*  664: 982 */     if (par3 < 0) {
/*  665: 984 */       par3 = 0;
/*  666:     */     }
/*  667: 987 */     if (par3 >= 256) {
/*  668: 989 */       par3 = 255;
/*  669:     */     }
/*  670: 992 */     if ((par2 >= -30000000) && (par4 >= -30000000) && (par2 < 30000000) && (par4 < 30000000))
/*  671:     */     {
/*  672: 994 */       int var5 = par2 >> 4;
/*  673: 995 */       int var6 = par4 >> 4;
/*  674: 997 */       if (!chunkExists(var5, var6)) {
/*  675: 999 */         return par1EnumSkyBlock.defaultLightValue;
/*  676:     */       }
/*  677:1003 */       Chunk var7 = getChunkFromChunkCoords(var5, var6);
/*  678:1004 */       return var7.getSavedLightValue(par1EnumSkyBlock, par2 & 0xF, par3, par4 & 0xF);
/*  679:     */     }
/*  680:1009 */     return par1EnumSkyBlock.defaultLightValue;
/*  681:     */   }
/*  682:     */   
/*  683:     */   public void setLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4, int par5)
/*  684:     */   {
/*  685:1019 */     if ((par2 >= -30000000) && (par4 >= -30000000) && (par2 < 30000000) && (par4 < 30000000)) {
/*  686:1021 */       if (par3 >= 0) {
/*  687:1023 */         if (par3 < 256) {
/*  688:1025 */           if (chunkExists(par2 >> 4, par4 >> 4))
/*  689:     */           {
/*  690:1027 */             Chunk var6 = getChunkFromChunkCoords(par2 >> 4, par4 >> 4);
/*  691:1028 */             var6.setLightValue(par1EnumSkyBlock, par2 & 0xF, par3, par4 & 0xF, par5);
/*  692:1030 */             for (int var7 = 0; var7 < this.worldAccesses.size(); var7++) {
/*  693:1032 */               ((IWorldAccess)this.worldAccesses.get(var7)).markBlockForRenderUpdate(par2, par3, par4);
/*  694:     */             }
/*  695:     */           }
/*  696:     */         }
/*  697:     */       }
/*  698:     */     }
/*  699:     */   }
/*  700:     */   
/*  701:     */   public void func_147479_m(int p_147479_1_, int p_147479_2_, int p_147479_3_)
/*  702:     */   {
/*  703:1042 */     for (int var4 = 0; var4 < this.worldAccesses.size(); var4++) {
/*  704:1044 */       ((IWorldAccess)this.worldAccesses.get(var4)).markBlockForRenderUpdate(p_147479_1_, p_147479_2_, p_147479_3_);
/*  705:     */     }
/*  706:     */   }
/*  707:     */   
/*  708:     */   public int getLightBrightnessForSkyBlocks(int par1, int par2, int par3, int par4)
/*  709:     */   {
/*  710:1053 */     int var5 = getSkyBlockTypeBrightness(EnumSkyBlock.Sky, par1, par2, par3);
/*  711:1054 */     int var6 = getSkyBlockTypeBrightness(EnumSkyBlock.Block, par1, par2, par3);
/*  712:1056 */     if (var6 < par4) {
/*  713:1058 */       var6 = par4;
/*  714:     */     }
/*  715:1061 */     return var5 << 20 | var6 << 4;
/*  716:     */   }
/*  717:     */   
/*  718:     */   public float getLightBrightness(int par1, int par2, int par3)
/*  719:     */   {
/*  720:1070 */     return this.provider.lightBrightnessTable[getBlockLightValue(par1, par2, par3)];
/*  721:     */   }
/*  722:     */   
/*  723:     */   public boolean isDaytime()
/*  724:     */   {
/*  725:1078 */     return this.skylightSubtracted < 4;
/*  726:     */   }
/*  727:     */   
/*  728:     */   public MovingObjectPosition rayTraceBlocks(Vec3 par1Vec3, Vec3 par2Vec3)
/*  729:     */   {
/*  730:1086 */     return func_147447_a(par1Vec3, par2Vec3, false, false, false);
/*  731:     */   }
/*  732:     */   
/*  733:     */   public MovingObjectPosition rayTraceBlocks(Vec3 par1Vec3, Vec3 par2Vec3, boolean par3)
/*  734:     */   {
/*  735:1094 */     return func_147447_a(par1Vec3, par2Vec3, par3, false, false);
/*  736:     */   }
/*  737:     */   
/*  738:     */   public MovingObjectPosition func_147447_a(Vec3 p_147447_1_, Vec3 p_147447_2_, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_)
/*  739:     */   {
/*  740:1099 */     if ((!Double.isNaN(p_147447_1_.xCoord)) && (!Double.isNaN(p_147447_1_.yCoord)) && (!Double.isNaN(p_147447_1_.zCoord)))
/*  741:     */     {
/*  742:1101 */       if ((!Double.isNaN(p_147447_2_.xCoord)) && (!Double.isNaN(p_147447_2_.yCoord)) && (!Double.isNaN(p_147447_2_.zCoord)))
/*  743:     */       {
/*  744:1103 */         int var6 = MathHelper.floor_double(p_147447_2_.xCoord);
/*  745:1104 */         int var7 = MathHelper.floor_double(p_147447_2_.yCoord);
/*  746:1105 */         int var8 = MathHelper.floor_double(p_147447_2_.zCoord);
/*  747:1106 */         int var9 = MathHelper.floor_double(p_147447_1_.xCoord);
/*  748:1107 */         int var10 = MathHelper.floor_double(p_147447_1_.yCoord);
/*  749:1108 */         int var11 = MathHelper.floor_double(p_147447_1_.zCoord);
/*  750:1109 */         Block var12 = getBlock(var9, var10, var11);
/*  751:1110 */         int var13 = getBlockMetadata(var9, var10, var11);
/*  752:1112 */         if (((!p_147447_4_) || (var12.getCollisionBoundingBoxFromPool(this, var9, var10, var11) != null)) && (var12.canCollideCheck(var13, p_147447_3_)))
/*  753:     */         {
/*  754:1114 */           MovingObjectPosition var14 = var12.collisionRayTrace(this, var9, var10, var11, p_147447_1_, p_147447_2_);
/*  755:1116 */           if (var14 != null) {
/*  756:1118 */             return var14;
/*  757:     */           }
/*  758:     */         }
/*  759:1122 */         MovingObjectPosition var40 = null;
/*  760:1123 */         var13 = 200;
/*  761:1125 */         while (var13-- >= 0)
/*  762:     */         {
/*  763:1127 */           if ((Double.isNaN(p_147447_1_.xCoord)) || (Double.isNaN(p_147447_1_.yCoord)) || (Double.isNaN(p_147447_1_.zCoord))) {
/*  764:1129 */             return null;
/*  765:     */           }
/*  766:1132 */           if ((var9 == var6) && (var10 == var7) && (var11 == var8)) {
/*  767:1134 */             return p_147447_5_ ? var40 : null;
/*  768:     */           }
/*  769:1137 */           boolean var41 = true;
/*  770:1138 */           boolean var15 = true;
/*  771:1139 */           boolean var16 = true;
/*  772:1140 */           double var17 = 999.0D;
/*  773:1141 */           double var19 = 999.0D;
/*  774:1142 */           double var21 = 999.0D;
/*  775:1144 */           if (var6 > var9) {
/*  776:1146 */             var17 = var9 + 1.0D;
/*  777:1148 */           } else if (var6 < var9) {
/*  778:1150 */             var17 = var9 + 0.0D;
/*  779:     */           } else {
/*  780:1154 */             var41 = false;
/*  781:     */           }
/*  782:1157 */           if (var7 > var10) {
/*  783:1159 */             var19 = var10 + 1.0D;
/*  784:1161 */           } else if (var7 < var10) {
/*  785:1163 */             var19 = var10 + 0.0D;
/*  786:     */           } else {
/*  787:1167 */             var15 = false;
/*  788:     */           }
/*  789:1170 */           if (var8 > var11) {
/*  790:1172 */             var21 = var11 + 1.0D;
/*  791:1174 */           } else if (var8 < var11) {
/*  792:1176 */             var21 = var11 + 0.0D;
/*  793:     */           } else {
/*  794:1180 */             var16 = false;
/*  795:     */           }
/*  796:1183 */           double var23 = 999.0D;
/*  797:1184 */           double var25 = 999.0D;
/*  798:1185 */           double var27 = 999.0D;
/*  799:1186 */           double var29 = p_147447_2_.xCoord - p_147447_1_.xCoord;
/*  800:1187 */           double var31 = p_147447_2_.yCoord - p_147447_1_.yCoord;
/*  801:1188 */           double var33 = p_147447_2_.zCoord - p_147447_1_.zCoord;
/*  802:1190 */           if (var41) {
/*  803:1192 */             var23 = (var17 - p_147447_1_.xCoord) / var29;
/*  804:     */           }
/*  805:1195 */           if (var15) {
/*  806:1197 */             var25 = (var19 - p_147447_1_.yCoord) / var31;
/*  807:     */           }
/*  808:1200 */           if (var16) {
/*  809:1202 */             var27 = (var21 - p_147447_1_.zCoord) / var33;
/*  810:     */           }
/*  811:1205 */           boolean var35 = false;
/*  812:     */           byte var42;
/*  813:1208 */           if ((var23 < var25) && (var23 < var27))
/*  814:     */           {
/*  815:     */             byte var42;
/*  816:     */             byte var42;
/*  817:1210 */             if (var6 > var9) {
/*  818:1212 */               var42 = 4;
/*  819:     */             } else {
/*  820:1216 */               var42 = 5;
/*  821:     */             }
/*  822:1219 */             p_147447_1_.xCoord = var17;
/*  823:1220 */             p_147447_1_.yCoord += var31 * var23;
/*  824:1221 */             p_147447_1_.zCoord += var33 * var23;
/*  825:     */           }
/*  826:1223 */           else if (var25 < var27)
/*  827:     */           {
/*  828:     */             byte var42;
/*  829:     */             byte var42;
/*  830:1225 */             if (var7 > var10) {
/*  831:1227 */               var42 = 0;
/*  832:     */             } else {
/*  833:1231 */               var42 = 1;
/*  834:     */             }
/*  835:1234 */             p_147447_1_.xCoord += var29 * var25;
/*  836:1235 */             p_147447_1_.yCoord = var19;
/*  837:1236 */             p_147447_1_.zCoord += var33 * var25;
/*  838:     */           }
/*  839:     */           else
/*  840:     */           {
/*  841:     */             byte var42;
/*  842:1240 */             if (var8 > var11) {
/*  843:1242 */               var42 = 2;
/*  844:     */             } else {
/*  845:1246 */               var42 = 3;
/*  846:     */             }
/*  847:1249 */             p_147447_1_.xCoord += var29 * var27;
/*  848:1250 */             p_147447_1_.yCoord += var31 * var27;
/*  849:1251 */             p_147447_1_.zCoord = var21;
/*  850:     */           }
/*  851:1254 */           Vec3 var36 = getWorldVec3Pool().getVecFromPool(p_147447_1_.xCoord, p_147447_1_.yCoord, p_147447_1_.zCoord);
/*  852:1255 */           var9 = (int)(var36.xCoord = MathHelper.floor_double(p_147447_1_.xCoord));
/*  853:1257 */           if (var42 == 5)
/*  854:     */           {
/*  855:1259 */             var9--;
/*  856:1260 */             var36.xCoord += 1.0D;
/*  857:     */           }
/*  858:1263 */           var10 = (int)(var36.yCoord = MathHelper.floor_double(p_147447_1_.yCoord));
/*  859:1265 */           if (var42 == 1)
/*  860:     */           {
/*  861:1267 */             var10--;
/*  862:1268 */             var36.yCoord += 1.0D;
/*  863:     */           }
/*  864:1271 */           var11 = (int)(var36.zCoord = MathHelper.floor_double(p_147447_1_.zCoord));
/*  865:1273 */           if (var42 == 3)
/*  866:     */           {
/*  867:1275 */             var11--;
/*  868:1276 */             var36.zCoord += 1.0D;
/*  869:     */           }
/*  870:1279 */           Block var37 = getBlock(var9, var10, var11);
/*  871:1280 */           int var38 = getBlockMetadata(var9, var10, var11);
/*  872:1282 */           if ((!p_147447_4_) || (var37.getCollisionBoundingBoxFromPool(this, var9, var10, var11) != null)) {
/*  873:1284 */             if (var37.canCollideCheck(var38, p_147447_3_))
/*  874:     */             {
/*  875:1286 */               MovingObjectPosition var39 = var37.collisionRayTrace(this, var9, var10, var11, p_147447_1_, p_147447_2_);
/*  876:1288 */               if (var39 != null) {
/*  877:1290 */                 return var39;
/*  878:     */               }
/*  879:     */             }
/*  880:     */             else
/*  881:     */             {
/*  882:1295 */               var40 = new MovingObjectPosition(var9, var10, var11, var42, p_147447_1_, false);
/*  883:     */             }
/*  884:     */           }
/*  885:     */         }
/*  886:1300 */         return p_147447_5_ ? var40 : null;
/*  887:     */       }
/*  888:1304 */       return null;
/*  889:     */     }
/*  890:1309 */     return null;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public void playSoundAtEntity(Entity par1Entity, String par2Str, float par3, float par4)
/*  894:     */   {
/*  895:1319 */     for (int var5 = 0; var5 < this.worldAccesses.size(); var5++) {
/*  896:1321 */       ((IWorldAccess)this.worldAccesses.get(var5)).playSound(par2Str, par1Entity.posX, par1Entity.posY - par1Entity.yOffset, par1Entity.posZ, par3, par4);
/*  897:     */     }
/*  898:     */   }
/*  899:     */   
/*  900:     */   public void playSoundToNearExcept(EntityPlayer par1EntityPlayer, String par2Str, float par3, float par4)
/*  901:     */   {
/*  902:1330 */     for (int var5 = 0; var5 < this.worldAccesses.size(); var5++) {
/*  903:1332 */       ((IWorldAccess)this.worldAccesses.get(var5)).playSoundToNearExcept(par1EntityPlayer, par2Str, par1EntityPlayer.posX, par1EntityPlayer.posY - par1EntityPlayer.yOffset, par1EntityPlayer.posZ, par3, par4);
/*  904:     */     }
/*  905:     */   }
/*  906:     */   
/*  907:     */   public void playSoundEffect(double par1, double par3, double par5, String par7Str, float par8, float par9)
/*  908:     */   {
/*  909:1343 */     for (int var10 = 0; var10 < this.worldAccesses.size(); var10++) {
/*  910:1345 */       ((IWorldAccess)this.worldAccesses.get(var10)).playSound(par7Str, par1, par3, par5, par8, par9);
/*  911:     */     }
/*  912:     */   }
/*  913:     */   
/*  914:     */   public void playSound(double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10) {}
/*  915:     */   
/*  916:     */   public void playRecord(String par1Str, int par2, int par3, int par4)
/*  917:     */   {
/*  918:1359 */     for (int var5 = 0; var5 < this.worldAccesses.size(); var5++) {
/*  919:1361 */       ((IWorldAccess)this.worldAccesses.get(var5)).playRecord(par1Str, par2, par3, par4);
/*  920:     */     }
/*  921:     */   }
/*  922:     */   
/*  923:     */   public void spawnParticle(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12)
/*  924:     */   {
/*  925:1370 */     for (int var14 = 0; var14 < this.worldAccesses.size(); var14++) {
/*  926:1372 */       ((IWorldAccess)this.worldAccesses.get(var14)).spawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
/*  927:     */     }
/*  928:     */   }
/*  929:     */   
/*  930:     */   public boolean addWeatherEffect(Entity par1Entity)
/*  931:     */   {
/*  932:1381 */     this.weatherEffects.add(par1Entity);
/*  933:1382 */     return true;
/*  934:     */   }
/*  935:     */   
/*  936:     */   public boolean spawnEntityInWorld(Entity par1Entity)
/*  937:     */   {
/*  938:1390 */     int var2 = MathHelper.floor_double(par1Entity.posX / 16.0D);
/*  939:1391 */     int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0D);
/*  940:1392 */     boolean var4 = par1Entity.forceSpawn;
/*  941:1394 */     if ((par1Entity instanceof EntityPlayer)) {
/*  942:1396 */       var4 = true;
/*  943:     */     }
/*  944:1399 */     if ((!var4) && (!chunkExists(var2, var3))) {
/*  945:1401 */       return false;
/*  946:     */     }
/*  947:1405 */     if ((par1Entity instanceof EntityPlayer))
/*  948:     */     {
/*  949:1407 */       EntityPlayer var5 = (EntityPlayer)par1Entity;
/*  950:1408 */       this.playerEntities.add(var5);
/*  951:1409 */       updateAllPlayersSleepingFlag();
/*  952:     */     }
/*  953:1412 */     getChunkFromChunkCoords(var2, var3).addEntity(par1Entity);
/*  954:1413 */     this.loadedEntityList.add(par1Entity);
/*  955:1414 */     onEntityAdded(par1Entity);
/*  956:1415 */     return true;
/*  957:     */   }
/*  958:     */   
/*  959:     */   protected void onEntityAdded(Entity par1Entity)
/*  960:     */   {
/*  961:1421 */     for (int var2 = 0; var2 < this.worldAccesses.size(); var2++) {
/*  962:1423 */       ((IWorldAccess)this.worldAccesses.get(var2)).onEntityCreate(par1Entity);
/*  963:     */     }
/*  964:     */   }
/*  965:     */   
/*  966:     */   protected void onEntityRemoved(Entity par1Entity)
/*  967:     */   {
/*  968:1429 */     for (int var2 = 0; var2 < this.worldAccesses.size(); var2++) {
/*  969:1431 */       ((IWorldAccess)this.worldAccesses.get(var2)).onEntityDestroy(par1Entity);
/*  970:     */     }
/*  971:     */   }
/*  972:     */   
/*  973:     */   public void removeEntity(Entity par1Entity)
/*  974:     */   {
/*  975:1440 */     if (par1Entity.riddenByEntity != null) {
/*  976:1442 */       par1Entity.riddenByEntity.mountEntity(null);
/*  977:     */     }
/*  978:1445 */     if (par1Entity.ridingEntity != null) {
/*  979:1447 */       par1Entity.mountEntity(null);
/*  980:     */     }
/*  981:1450 */     par1Entity.setDead();
/*  982:1452 */     if ((par1Entity instanceof EntityPlayer))
/*  983:     */     {
/*  984:1454 */       this.playerEntities.remove(par1Entity);
/*  985:1455 */       updateAllPlayersSleepingFlag();
/*  986:     */     }
/*  987:     */   }
/*  988:     */   
/*  989:     */   public void removePlayerEntityDangerously(Entity par1Entity)
/*  990:     */   {
/*  991:1464 */     par1Entity.setDead();
/*  992:1466 */     if ((par1Entity instanceof EntityPlayer))
/*  993:     */     {
/*  994:1468 */       this.playerEntities.remove(par1Entity);
/*  995:1469 */       updateAllPlayersSleepingFlag();
/*  996:     */     }
/*  997:1472 */     int var2 = par1Entity.chunkCoordX;
/*  998:1473 */     int var3 = par1Entity.chunkCoordZ;
/*  999:1475 */     if ((par1Entity.addedToChunk) && (chunkExists(var2, var3))) {
/* 1000:1477 */       getChunkFromChunkCoords(var2, var3).removeEntity(par1Entity);
/* 1001:     */     }
/* 1002:1480 */     this.loadedEntityList.remove(par1Entity);
/* 1003:1481 */     onEntityRemoved(par1Entity);
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public void addWorldAccess(IWorldAccess par1IWorldAccess)
/* 1007:     */   {
/* 1008:1489 */     this.worldAccesses.add(par1IWorldAccess);
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public void removeWorldAccess(IWorldAccess par1IWorldAccess)
/* 1012:     */   {
/* 1013:1497 */     this.worldAccesses.remove(par1IWorldAccess);
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   public List getCollidingBoundingBoxes(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB)
/* 1017:     */   {
/* 1018:1506 */     this.collidingBoundingBoxes.clear();
/* 1019:1507 */     int var3 = MathHelper.floor_double(par2AxisAlignedBB.minX);
/* 1020:1508 */     int var4 = MathHelper.floor_double(par2AxisAlignedBB.maxX + 1.0D);
/* 1021:1509 */     int var5 = MathHelper.floor_double(par2AxisAlignedBB.minY);
/* 1022:1510 */     int var6 = MathHelper.floor_double(par2AxisAlignedBB.maxY + 1.0D);
/* 1023:1511 */     int var7 = MathHelper.floor_double(par2AxisAlignedBB.minZ);
/* 1024:1512 */     int var8 = MathHelper.floor_double(par2AxisAlignedBB.maxZ + 1.0D);
/* 1025:1514 */     for (int var9 = var3; var9 < var4; var9++) {
/* 1026:1516 */       for (int var10 = var7; var10 < var8; var10++) {
/* 1027:1518 */         if (blockExists(var9, 64, var10)) {
/* 1028:1520 */           for (int var11 = var5 - 1; var11 < var6; var11++)
/* 1029:     */           {
/* 1030:     */             Block var12;
/* 1031:     */             Block var12;
/* 1032:1524 */             if ((var9 >= -30000000) && (var9 < 30000000) && (var10 >= -30000000) && (var10 < 30000000)) {
/* 1033:1526 */               var12 = getBlock(var9, var11, var10);
/* 1034:     */             } else {
/* 1035:1530 */               var12 = Blocks.stone;
/* 1036:     */             }
/* 1037:1533 */             var12.addCollisionBoxesToList(this, var9, var11, var10, par2AxisAlignedBB, this.collidingBoundingBoxes, par1Entity);
/* 1038:     */           }
/* 1039:     */         }
/* 1040:     */       }
/* 1041:     */     }
/* 1042:1539 */     double var14 = 0.25D;
/* 1043:1540 */     List var15 = getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB.expand(var14, var14, var14));
/* 1044:1542 */     for (int var16 = 0; var16 < var15.size(); var16++)
/* 1045:     */     {
/* 1046:1544 */       AxisAlignedBB var13 = ((Entity)var15.get(var16)).getBoundingBox();
/* 1047:1546 */       if ((var13 != null) && (var13.intersectsWith(par2AxisAlignedBB))) {
/* 1048:1548 */         this.collidingBoundingBoxes.add(var13);
/* 1049:     */       }
/* 1050:1551 */       var13 = par1Entity.getCollisionBox((Entity)var15.get(var16));
/* 1051:1553 */       if ((var13 != null) && (var13.intersectsWith(par2AxisAlignedBB))) {
/* 1052:1555 */         this.collidingBoundingBoxes.add(var13);
/* 1053:     */       }
/* 1054:     */     }
/* 1055:1559 */     return this.collidingBoundingBoxes;
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   public List func_147461_a(AxisAlignedBB p_147461_1_)
/* 1059:     */   {
/* 1060:1564 */     this.collidingBoundingBoxes.clear();
/* 1061:1565 */     int var2 = MathHelper.floor_double(p_147461_1_.minX);
/* 1062:1566 */     int var3 = MathHelper.floor_double(p_147461_1_.maxX + 1.0D);
/* 1063:1567 */     int var4 = MathHelper.floor_double(p_147461_1_.minY);
/* 1064:1568 */     int var5 = MathHelper.floor_double(p_147461_1_.maxY + 1.0D);
/* 1065:1569 */     int var6 = MathHelper.floor_double(p_147461_1_.minZ);
/* 1066:1570 */     int var7 = MathHelper.floor_double(p_147461_1_.maxZ + 1.0D);
/* 1067:1572 */     for (int var8 = var2; var8 < var3; var8++) {
/* 1068:1574 */       for (int var9 = var6; var9 < var7; var9++) {
/* 1069:1576 */         if (blockExists(var8, 64, var9)) {
/* 1070:1578 */           for (int var10 = var4 - 1; var10 < var5; var10++)
/* 1071:     */           {
/* 1072:     */             Block var11;
/* 1073:     */             Block var11;
/* 1074:1582 */             if ((var8 >= -30000000) && (var8 < 30000000) && (var9 >= -30000000) && (var9 < 30000000)) {
/* 1075:1584 */               var11 = getBlock(var8, var10, var9);
/* 1076:     */             } else {
/* 1077:1588 */               var11 = Blocks.bedrock;
/* 1078:     */             }
/* 1079:1591 */             var11.addCollisionBoxesToList(this, var8, var10, var9, p_147461_1_, this.collidingBoundingBoxes, null);
/* 1080:     */           }
/* 1081:     */         }
/* 1082:     */       }
/* 1083:     */     }
/* 1084:1597 */     return this.collidingBoundingBoxes;
/* 1085:     */   }
/* 1086:     */   
/* 1087:     */   public int calculateSkylightSubtracted(float par1)
/* 1088:     */   {
/* 1089:1605 */     float var2 = getCelestialAngle(par1);
/* 1090:1606 */     float var3 = 1.0F - (MathHelper.cos(var2 * 3.141593F * 2.0F) * 2.0F + 0.5F);
/* 1091:1608 */     if (var3 < 0.0F) {
/* 1092:1610 */       var3 = 0.0F;
/* 1093:     */     }
/* 1094:1613 */     if (var3 > 1.0F) {
/* 1095:1615 */       var3 = 1.0F;
/* 1096:     */     }
/* 1097:1618 */     var3 = 1.0F - var3;
/* 1098:1619 */     var3 = (float)(var3 * (1.0D - getRainStrength(par1) * 5.0F / 16.0D));
/* 1099:1620 */     var3 = (float)(var3 * (1.0D - getWeightedThunderStrength(par1) * 5.0F / 16.0D));
/* 1100:1621 */     var3 = 1.0F - var3;
/* 1101:1622 */     return (int)(var3 * 11.0F);
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   public float getSunBrightness(float par1)
/* 1105:     */   {
/* 1106:1630 */     float var2 = getCelestialAngle(par1);
/* 1107:1631 */     float var3 = 1.0F - (MathHelper.cos(var2 * 3.141593F * 2.0F) * 2.0F + 0.2F);
/* 1108:1633 */     if (var3 < 0.0F) {
/* 1109:1635 */       var3 = 0.0F;
/* 1110:     */     }
/* 1111:1638 */     if (var3 > 1.0F) {
/* 1112:1640 */       var3 = 1.0F;
/* 1113:     */     }
/* 1114:1643 */     var3 = 1.0F - var3;
/* 1115:1644 */     var3 = (float)(var3 * (1.0D - getRainStrength(par1) * 5.0F / 16.0D));
/* 1116:1645 */     var3 = (float)(var3 * (1.0D - getWeightedThunderStrength(par1) * 5.0F / 16.0D));
/* 1117:1646 */     return var3 * 0.8F + 0.2F;
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public Vec3 getSkyColor(Entity par1Entity, float par2)
/* 1121:     */   {
/* 1122:1654 */     float var3 = getCelestialAngle(par2);
/* 1123:1655 */     float var4 = MathHelper.cos(var3 * 3.141593F * 2.0F) * 2.0F + 0.5F;
/* 1124:1657 */     if (var4 < 0.0F) {
/* 1125:1659 */       var4 = 0.0F;
/* 1126:     */     }
/* 1127:1662 */     if (var4 > 1.0F) {
/* 1128:1664 */       var4 = 1.0F;
/* 1129:     */     }
/* 1130:1667 */     int var5 = MathHelper.floor_double(par1Entity.posX);
/* 1131:1668 */     int var6 = MathHelper.floor_double(par1Entity.posY);
/* 1132:1669 */     int var7 = MathHelper.floor_double(par1Entity.posZ);
/* 1133:1670 */     BiomeGenBase var8 = getBiomeGenForCoords(var5, var7);
/* 1134:1671 */     float var9 = var8.getFloatTemperature(var5, var6, var7);
/* 1135:1672 */     int var10 = var8.getSkyColorByTemp(var9);
/* 1136:1673 */     float var11 = (var10 >> 16 & 0xFF) / 255.0F;
/* 1137:1674 */     float var12 = (var10 >> 8 & 0xFF) / 255.0F;
/* 1138:1675 */     float var13 = (var10 & 0xFF) / 255.0F;
/* 1139:1676 */     var11 *= var4;
/* 1140:1677 */     var12 *= var4;
/* 1141:1678 */     var13 *= var4;
/* 1142:1679 */     float var14 = getRainStrength(par2);
/* 1143:1683 */     if (var14 > 0.0F)
/* 1144:     */     {
/* 1145:1685 */       float var15 = (var11 * 0.3F + var12 * 0.59F + var13 * 0.11F) * 0.6F;
/* 1146:1686 */       float var16 = 1.0F - var14 * 0.75F;
/* 1147:1687 */       var11 = var11 * var16 + var15 * (1.0F - var16);
/* 1148:1688 */       var12 = var12 * var16 + var15 * (1.0F - var16);
/* 1149:1689 */       var13 = var13 * var16 + var15 * (1.0F - var16);
/* 1150:     */     }
/* 1151:1692 */     float var15 = getWeightedThunderStrength(par2);
/* 1152:1694 */     if (var15 > 0.0F)
/* 1153:     */     {
/* 1154:1696 */       float var16 = (var11 * 0.3F + var12 * 0.59F + var13 * 0.11F) * 0.2F;
/* 1155:1697 */       float var17 = 1.0F - var15 * 0.75F;
/* 1156:1698 */       var11 = var11 * var17 + var16 * (1.0F - var17);
/* 1157:1699 */       var12 = var12 * var17 + var16 * (1.0F - var17);
/* 1158:1700 */       var13 = var13 * var17 + var16 * (1.0F - var17);
/* 1159:     */     }
/* 1160:1703 */     if (this.lastLightningBolt > 0)
/* 1161:     */     {
/* 1162:1705 */       float var16 = this.lastLightningBolt - par2;
/* 1163:1707 */       if (var16 > 1.0F) {
/* 1164:1709 */         var16 = 1.0F;
/* 1165:     */       }
/* 1166:1712 */       var16 *= 0.45F;
/* 1167:1713 */       var11 = var11 * (1.0F - var16) + 0.8F * var16;
/* 1168:1714 */       var12 = var12 * (1.0F - var16) + 0.8F * var16;
/* 1169:1715 */       var13 = var13 * (1.0F - var16) + 1.0F * var16;
/* 1170:     */     }
/* 1171:1718 */     return getWorldVec3Pool().getVecFromPool(var11, var12, var13);
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public float getCelestialAngle(float par1)
/* 1175:     */   {
/* 1176:1726 */     return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), par1);
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   public int getMoonPhase()
/* 1180:     */   {
/* 1181:1731 */     return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
/* 1182:     */   }
/* 1183:     */   
/* 1184:     */   public float getCurrentMoonPhaseFactor()
/* 1185:     */   {
/* 1186:1739 */     return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   public float getCelestialAngleRadians(float par1)
/* 1190:     */   {
/* 1191:1747 */     float var2 = getCelestialAngle(par1);
/* 1192:1748 */     return var2 * 3.141593F * 2.0F;
/* 1193:     */   }
/* 1194:     */   
/* 1195:     */   public Vec3 getCloudColour(float par1)
/* 1196:     */   {
/* 1197:1753 */     float var2 = getCelestialAngle(par1);
/* 1198:1754 */     float var3 = MathHelper.cos(var2 * 3.141593F * 2.0F) * 2.0F + 0.5F;
/* 1199:1756 */     if (var3 < 0.0F) {
/* 1200:1758 */       var3 = 0.0F;
/* 1201:     */     }
/* 1202:1761 */     if (var3 > 1.0F) {
/* 1203:1763 */       var3 = 1.0F;
/* 1204:     */     }
/* 1205:1766 */     float var4 = (float)(this.cloudColour >> 16 & 0xFF) / 255.0F;
/* 1206:1767 */     float var5 = (float)(this.cloudColour >> 8 & 0xFF) / 255.0F;
/* 1207:1768 */     float var6 = (float)(this.cloudColour & 0xFF) / 255.0F;
/* 1208:1769 */     float var7 = getRainStrength(par1);
/* 1209:1773 */     if (var7 > 0.0F)
/* 1210:     */     {
/* 1211:1775 */       float var8 = (var4 * 0.3F + var5 * 0.59F + var6 * 0.11F) * 0.6F;
/* 1212:1776 */       float var9 = 1.0F - var7 * 0.95F;
/* 1213:1777 */       var4 = var4 * var9 + var8 * (1.0F - var9);
/* 1214:1778 */       var5 = var5 * var9 + var8 * (1.0F - var9);
/* 1215:1779 */       var6 = var6 * var9 + var8 * (1.0F - var9);
/* 1216:     */     }
/* 1217:1782 */     var4 *= (var3 * 0.9F + 0.1F);
/* 1218:1783 */     var5 *= (var3 * 0.9F + 0.1F);
/* 1219:1784 */     var6 *= (var3 * 0.85F + 0.15F);
/* 1220:1785 */     float var8 = getWeightedThunderStrength(par1);
/* 1221:1787 */     if (var8 > 0.0F)
/* 1222:     */     {
/* 1223:1789 */       float var9 = (var4 * 0.3F + var5 * 0.59F + var6 * 0.11F) * 0.2F;
/* 1224:1790 */       float var10 = 1.0F - var8 * 0.95F;
/* 1225:1791 */       var4 = var4 * var10 + var9 * (1.0F - var10);
/* 1226:1792 */       var5 = var5 * var10 + var9 * (1.0F - var10);
/* 1227:1793 */       var6 = var6 * var10 + var9 * (1.0F - var10);
/* 1228:     */     }
/* 1229:1796 */     return getWorldVec3Pool().getVecFromPool(var4, var5, var6);
/* 1230:     */   }
/* 1231:     */   
/* 1232:     */   public Vec3 getFogColor(float par1)
/* 1233:     */   {
/* 1234:1804 */     float var2 = getCelestialAngle(par1);
/* 1235:1805 */     return this.provider.getFogColor(var2, par1);
/* 1236:     */   }
/* 1237:     */   
/* 1238:     */   public int getPrecipitationHeight(int par1, int par2)
/* 1239:     */   {
/* 1240:1813 */     return getChunkFromBlockCoords(par1, par2).getPrecipitationHeight(par1 & 0xF, par2 & 0xF);
/* 1241:     */   }
/* 1242:     */   
/* 1243:     */   public int getTopSolidOrLiquidBlock(int par1, int par2)
/* 1244:     */   {
/* 1245:1821 */     Chunk var3 = getChunkFromBlockCoords(par1, par2);
/* 1246:1822 */     int var4 = var3.getTopFilledSegment() + 15;
/* 1247:1823 */     par1 &= 0xF;
/* 1248:1825 */     for (par2 &= 0xF; var4 > 0; var4--)
/* 1249:     */     {
/* 1250:1827 */       Block var5 = var3.func_150810_a(par1, var4, par2);
/* 1251:1829 */       if ((var5.getMaterial().blocksMovement()) && (var5.getMaterial() != Material.leaves)) {
/* 1252:1831 */         return var4 + 1;
/* 1253:     */       }
/* 1254:     */     }
/* 1255:1835 */     return -1;
/* 1256:     */   }
/* 1257:     */   
/* 1258:     */   public float getStarBrightness(float par1)
/* 1259:     */   {
/* 1260:1843 */     float var2 = getCelestialAngle(par1);
/* 1261:1844 */     float var3 = 1.0F - (MathHelper.cos(var2 * 3.141593F * 2.0F) * 2.0F + 0.25F);
/* 1262:1846 */     if (var3 < 0.0F) {
/* 1263:1848 */       var3 = 0.0F;
/* 1264:     */     }
/* 1265:1851 */     if (var3 > 1.0F) {
/* 1266:1853 */       var3 = 1.0F;
/* 1267:     */     }
/* 1268:1856 */     return var3 * var3 * 0.5F;
/* 1269:     */   }
/* 1270:     */   
/* 1271:     */   public void scheduleBlockUpdate(int p_147464_1_, int p_147464_2_, int p_147464_3_, Block p_147464_4_, int p_147464_5_) {}
/* 1272:     */   
/* 1273:     */   public void func_147454_a(int p_147454_1_, int p_147454_2_, int p_147454_3_, Block p_147454_4_, int p_147454_5_, int p_147454_6_) {}
/* 1274:     */   
/* 1275:     */   public void func_147446_b(int p_147446_1_, int p_147446_2_, int p_147446_3_, Block p_147446_4_, int p_147446_5_, int p_147446_6_) {}
/* 1276:     */   
/* 1277:     */   public void updateEntities()
/* 1278:     */   {
/* 1279:1873 */     this.theProfiler.startSection("entities");
/* 1280:1874 */     this.theProfiler.startSection("global");
/* 1281:1880 */     for (int var1 = 0; var1 < this.weatherEffects.size(); var1++)
/* 1282:     */     {
/* 1283:1882 */       Entity var2 = (Entity)this.weatherEffects.get(var1);
/* 1284:     */       try
/* 1285:     */       {
/* 1286:1886 */         var2.ticksExisted += 1;
/* 1287:1887 */         var2.onUpdate();
/* 1288:     */       }
/* 1289:     */       catch (Throwable var8)
/* 1290:     */       {
/* 1291:1891 */         CrashReport var4 = CrashReport.makeCrashReport(var8, "Ticking entity");
/* 1292:1892 */         CrashReportCategory var5 = var4.makeCategory("Entity being ticked");
/* 1293:1894 */         if (var2 == null) {
/* 1294:1896 */           var5.addCrashSection("Entity", "~~NULL~~");
/* 1295:     */         } else {
/* 1296:1900 */           var2.addEntityCrashInfo(var5);
/* 1297:     */         }
/* 1298:1903 */         throw new ReportedException(var4);
/* 1299:     */       }
/* 1300:1906 */       if (var2.isDead) {
/* 1301:1908 */         this.weatherEffects.remove(var1--);
/* 1302:     */       }
/* 1303:     */     }
/* 1304:1912 */     this.theProfiler.endStartSection("remove");
/* 1305:1913 */     this.loadedEntityList.removeAll(this.unloadedEntityList);
/* 1306:1917 */     for (var1 = 0; var1 < this.unloadedEntityList.size(); var1++)
/* 1307:     */     {
/* 1308:1919 */       Entity var2 = (Entity)this.unloadedEntityList.get(var1);
/* 1309:1920 */       int var3 = var2.chunkCoordX;
/* 1310:1921 */       int var13 = var2.chunkCoordZ;
/* 1311:1923 */       if ((var2.addedToChunk) && (chunkExists(var3, var13))) {
/* 1312:1925 */         getChunkFromChunkCoords(var3, var13).removeEntity(var2);
/* 1313:     */       }
/* 1314:     */     }
/* 1315:1929 */     for (var1 = 0; var1 < this.unloadedEntityList.size(); var1++) {
/* 1316:1931 */       onEntityRemoved((Entity)this.unloadedEntityList.get(var1));
/* 1317:     */     }
/* 1318:1934 */     this.unloadedEntityList.clear();
/* 1319:1935 */     this.theProfiler.endStartSection("regular");
/* 1320:1937 */     for (var1 = 0; var1 < this.loadedEntityList.size(); var1++)
/* 1321:     */     {
/* 1322:1939 */       Entity var2 = (Entity)this.loadedEntityList.get(var1);
/* 1323:1941 */       if (var2.ridingEntity != null)
/* 1324:     */       {
/* 1325:1943 */         if ((var2.ridingEntity.isDead) || (var2.ridingEntity.riddenByEntity != var2))
/* 1326:     */         {
/* 1327:1948 */           var2.ridingEntity.riddenByEntity = null;
/* 1328:1949 */           var2.ridingEntity = null;
/* 1329:     */         }
/* 1330:     */       }
/* 1331:     */       else
/* 1332:     */       {
/* 1333:1952 */         this.theProfiler.startSection("tick");
/* 1334:1954 */         if (!var2.isDead) {
/* 1335:     */           try
/* 1336:     */           {
/* 1337:1958 */             updateEntity(var2);
/* 1338:     */           }
/* 1339:     */           catch (Throwable var7)
/* 1340:     */           {
/* 1341:1962 */             CrashReport var4 = CrashReport.makeCrashReport(var7, "Ticking entity");
/* 1342:1963 */             CrashReportCategory var5 = var4.makeCategory("Entity being ticked");
/* 1343:1964 */             var2.addEntityCrashInfo(var5);
/* 1344:1965 */             throw new ReportedException(var4);
/* 1345:     */           }
/* 1346:     */         }
/* 1347:1969 */         this.theProfiler.endSection();
/* 1348:1970 */         this.theProfiler.startSection("remove");
/* 1349:1972 */         if (var2.isDead)
/* 1350:     */         {
/* 1351:1974 */           int var3 = var2.chunkCoordX;
/* 1352:1975 */           int var13 = var2.chunkCoordZ;
/* 1353:1977 */           if ((var2.addedToChunk) && (chunkExists(var3, var13))) {
/* 1354:1979 */             getChunkFromChunkCoords(var3, var13).removeEntity(var2);
/* 1355:     */           }
/* 1356:1982 */           this.loadedEntityList.remove(var1--);
/* 1357:1983 */           onEntityRemoved(var2);
/* 1358:     */         }
/* 1359:1986 */         this.theProfiler.endSection();
/* 1360:     */       }
/* 1361:     */     }
/* 1362:1989 */     this.theProfiler.endStartSection("blockEntities");
/* 1363:1990 */     this.field_147481_N = true;
/* 1364:1991 */     Iterator var14 = this.field_147482_g.iterator();
/* 1365:1993 */     while (var14.hasNext())
/* 1366:     */     {
/* 1367:1995 */       TileEntity var9 = (TileEntity)var14.next();
/* 1368:1997 */       if ((!var9.isInvalid()) && (var9.hasWorldObj()) && (blockExists(var9.field_145851_c, var9.field_145848_d, var9.field_145849_e))) {
/* 1369:     */         try
/* 1370:     */         {
/* 1371:2001 */           var9.updateEntity();
/* 1372:     */         }
/* 1373:     */         catch (Throwable var6)
/* 1374:     */         {
/* 1375:2005 */           CrashReport var4 = CrashReport.makeCrashReport(var6, "Ticking block entity");
/* 1376:2006 */           CrashReportCategory var5 = var4.makeCategory("Block entity being ticked");
/* 1377:2007 */           var9.func_145828_a(var5);
/* 1378:2008 */           throw new ReportedException(var4);
/* 1379:     */         }
/* 1380:     */       }
/* 1381:2012 */       if (var9.isInvalid())
/* 1382:     */       {
/* 1383:2014 */         var14.remove();
/* 1384:2016 */         if (chunkExists(var9.field_145851_c >> 4, var9.field_145849_e >> 4))
/* 1385:     */         {
/* 1386:2018 */           Chunk var11 = getChunkFromChunkCoords(var9.field_145851_c >> 4, var9.field_145849_e >> 4);
/* 1387:2020 */           if (var11 != null) {
/* 1388:2022 */             var11.removeTileEntity(var9.field_145851_c & 0xF, var9.field_145848_d, var9.field_145849_e & 0xF);
/* 1389:     */           }
/* 1390:     */         }
/* 1391:     */       }
/* 1392:     */     }
/* 1393:2028 */     this.field_147481_N = false;
/* 1394:2030 */     if (!this.field_147483_b.isEmpty())
/* 1395:     */     {
/* 1396:2032 */       this.field_147482_g.removeAll(this.field_147483_b);
/* 1397:2033 */       this.field_147483_b.clear();
/* 1398:     */     }
/* 1399:2036 */     this.theProfiler.endStartSection("pendingBlockEntities");
/* 1400:2038 */     if (!this.field_147484_a.isEmpty())
/* 1401:     */     {
/* 1402:2040 */       for (int var10 = 0; var10 < this.field_147484_a.size(); var10++)
/* 1403:     */       {
/* 1404:2042 */         TileEntity var12 = (TileEntity)this.field_147484_a.get(var10);
/* 1405:2044 */         if (!var12.isInvalid())
/* 1406:     */         {
/* 1407:2046 */           if (!this.field_147482_g.contains(var12)) {
/* 1408:2048 */             this.field_147482_g.add(var12);
/* 1409:     */           }
/* 1410:2051 */           if (chunkExists(var12.field_145851_c >> 4, var12.field_145849_e >> 4))
/* 1411:     */           {
/* 1412:2053 */             Chunk var15 = getChunkFromChunkCoords(var12.field_145851_c >> 4, var12.field_145849_e >> 4);
/* 1413:2055 */             if (var15 != null) {
/* 1414:2057 */               var15.func_150812_a(var12.field_145851_c & 0xF, var12.field_145848_d, var12.field_145849_e & 0xF, var12);
/* 1415:     */             }
/* 1416:     */           }
/* 1417:2061 */           func_147471_g(var12.field_145851_c, var12.field_145848_d, var12.field_145849_e);
/* 1418:     */         }
/* 1419:     */       }
/* 1420:2065 */       this.field_147484_a.clear();
/* 1421:     */     }
/* 1422:2068 */     this.theProfiler.endSection();
/* 1423:2069 */     this.theProfiler.endSection();
/* 1424:     */   }
/* 1425:     */   
/* 1426:     */   public void func_147448_a(Collection p_147448_1_)
/* 1427:     */   {
/* 1428:2074 */     if (this.field_147481_N) {
/* 1429:2076 */       this.field_147484_a.addAll(p_147448_1_);
/* 1430:     */     } else {
/* 1431:2080 */       this.field_147482_g.addAll(p_147448_1_);
/* 1432:     */     }
/* 1433:     */   }
/* 1434:     */   
/* 1435:     */   public void updateEntity(Entity par1Entity)
/* 1436:     */   {
/* 1437:2089 */     updateEntityWithOptionalForce(par1Entity, true);
/* 1438:     */   }
/* 1439:     */   
/* 1440:     */   public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2)
/* 1441:     */   {
/* 1442:2098 */     int var3 = MathHelper.floor_double(par1Entity.posX);
/* 1443:2099 */     int var4 = MathHelper.floor_double(par1Entity.posZ);
/* 1444:2100 */     byte var5 = 32;
/* 1445:2102 */     if ((!par2) || (checkChunksExist(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5)))
/* 1446:     */     {
/* 1447:2104 */       par1Entity.lastTickPosX = par1Entity.posX;
/* 1448:2105 */       par1Entity.lastTickPosY = par1Entity.posY;
/* 1449:2106 */       par1Entity.lastTickPosZ = par1Entity.posZ;
/* 1450:2107 */       par1Entity.prevRotationYaw = par1Entity.rotationYaw;
/* 1451:2108 */       par1Entity.prevRotationPitch = par1Entity.rotationPitch;
/* 1452:2110 */       if ((par2) && (par1Entity.addedToChunk))
/* 1453:     */       {
/* 1454:2112 */         par1Entity.ticksExisted += 1;
/* 1455:2114 */         if (par1Entity.ridingEntity != null) {
/* 1456:2116 */           par1Entity.updateRidden();
/* 1457:     */         } else {
/* 1458:2120 */           par1Entity.onUpdate();
/* 1459:     */         }
/* 1460:     */       }
/* 1461:2124 */       this.theProfiler.startSection("chunkCheck");
/* 1462:2126 */       if ((Double.isNaN(par1Entity.posX)) || (Double.isInfinite(par1Entity.posX))) {
/* 1463:2128 */         par1Entity.posX = par1Entity.lastTickPosX;
/* 1464:     */       }
/* 1465:2131 */       if ((Double.isNaN(par1Entity.posY)) || (Double.isInfinite(par1Entity.posY))) {
/* 1466:2133 */         par1Entity.posY = par1Entity.lastTickPosY;
/* 1467:     */       }
/* 1468:2136 */       if ((Double.isNaN(par1Entity.posZ)) || (Double.isInfinite(par1Entity.posZ))) {
/* 1469:2138 */         par1Entity.posZ = par1Entity.lastTickPosZ;
/* 1470:     */       }
/* 1471:2141 */       if ((Double.isNaN(par1Entity.rotationPitch)) || (Double.isInfinite(par1Entity.rotationPitch))) {
/* 1472:2143 */         par1Entity.rotationPitch = par1Entity.prevRotationPitch;
/* 1473:     */       }
/* 1474:2146 */       if ((Double.isNaN(par1Entity.rotationYaw)) || (Double.isInfinite(par1Entity.rotationYaw))) {
/* 1475:2148 */         par1Entity.rotationYaw = par1Entity.prevRotationYaw;
/* 1476:     */       }
/* 1477:2151 */       int var6 = MathHelper.floor_double(par1Entity.posX / 16.0D);
/* 1478:2152 */       int var7 = MathHelper.floor_double(par1Entity.posY / 16.0D);
/* 1479:2153 */       int var8 = MathHelper.floor_double(par1Entity.posZ / 16.0D);
/* 1480:2155 */       if ((!par1Entity.addedToChunk) || (par1Entity.chunkCoordX != var6) || (par1Entity.chunkCoordY != var7) || (par1Entity.chunkCoordZ != var8))
/* 1481:     */       {
/* 1482:2157 */         if ((par1Entity.addedToChunk) && (chunkExists(par1Entity.chunkCoordX, par1Entity.chunkCoordZ))) {
/* 1483:2159 */           getChunkFromChunkCoords(par1Entity.chunkCoordX, par1Entity.chunkCoordZ).removeEntityAtIndex(par1Entity, par1Entity.chunkCoordY);
/* 1484:     */         }
/* 1485:2162 */         if (chunkExists(var6, var8))
/* 1486:     */         {
/* 1487:2164 */           par1Entity.addedToChunk = true;
/* 1488:2165 */           getChunkFromChunkCoords(var6, var8).addEntity(par1Entity);
/* 1489:     */         }
/* 1490:     */         else
/* 1491:     */         {
/* 1492:2169 */           par1Entity.addedToChunk = false;
/* 1493:     */         }
/* 1494:     */       }
/* 1495:2173 */       this.theProfiler.endSection();
/* 1496:2175 */       if ((par2) && (par1Entity.addedToChunk) && (par1Entity.riddenByEntity != null)) {
/* 1497:2177 */         if ((!par1Entity.riddenByEntity.isDead) && (par1Entity.riddenByEntity.ridingEntity == par1Entity))
/* 1498:     */         {
/* 1499:2179 */           updateEntity(par1Entity.riddenByEntity);
/* 1500:     */         }
/* 1501:     */         else
/* 1502:     */         {
/* 1503:2183 */           par1Entity.riddenByEntity.ridingEntity = null;
/* 1504:2184 */           par1Entity.riddenByEntity = null;
/* 1505:     */         }
/* 1506:     */       }
/* 1507:     */     }
/* 1508:     */   }
/* 1509:     */   
/* 1510:     */   public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB)
/* 1511:     */   {
/* 1512:2195 */     return checkNoEntityCollision(par1AxisAlignedBB, null);
/* 1513:     */   }
/* 1514:     */   
/* 1515:     */   public boolean checkNoEntityCollision(AxisAlignedBB par1AxisAlignedBB, Entity par2Entity)
/* 1516:     */   {
/* 1517:2203 */     List var3 = getEntitiesWithinAABBExcludingEntity(null, par1AxisAlignedBB);
/* 1518:2205 */     for (int var4 = 0; var4 < var3.size(); var4++)
/* 1519:     */     {
/* 1520:2207 */       Entity var5 = (Entity)var3.get(var4);
/* 1521:2209 */       if ((!var5.isDead) && (var5.preventEntitySpawning) && (var5 != par2Entity)) {
/* 1522:2211 */         return false;
/* 1523:     */       }
/* 1524:     */     }
/* 1525:2215 */     return true;
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   public boolean checkBlockCollision(AxisAlignedBB par1AxisAlignedBB)
/* 1529:     */   {
/* 1530:2223 */     int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
/* 1531:2224 */     int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
/* 1532:2225 */     int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
/* 1533:2226 */     int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
/* 1534:2227 */     int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
/* 1535:2228 */     int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
/* 1536:2230 */     if (par1AxisAlignedBB.minX < 0.0D) {
/* 1537:2232 */       var2--;
/* 1538:     */     }
/* 1539:2235 */     if (par1AxisAlignedBB.minY < 0.0D) {
/* 1540:2237 */       var4--;
/* 1541:     */     }
/* 1542:2240 */     if (par1AxisAlignedBB.minZ < 0.0D) {
/* 1543:2242 */       var6--;
/* 1544:     */     }
/* 1545:2245 */     for (int var8 = var2; var8 < var3; var8++) {
/* 1546:2247 */       for (int var9 = var4; var9 < var5; var9++) {
/* 1547:2249 */         for (int var10 = var6; var10 < var7; var10++)
/* 1548:     */         {
/* 1549:2251 */           Block var11 = getBlock(var8, var9, var10);
/* 1550:2253 */           if (var11.getMaterial() != Material.air) {
/* 1551:2255 */             return true;
/* 1552:     */           }
/* 1553:     */         }
/* 1554:     */       }
/* 1555:     */     }
/* 1556:2261 */     return false;
/* 1557:     */   }
/* 1558:     */   
/* 1559:     */   public boolean isAnyLiquid(AxisAlignedBB par1AxisAlignedBB)
/* 1560:     */   {
/* 1561:2269 */     int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
/* 1562:2270 */     int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
/* 1563:2271 */     int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
/* 1564:2272 */     int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
/* 1565:2273 */     int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
/* 1566:2274 */     int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
/* 1567:2276 */     if (par1AxisAlignedBB.minX < 0.0D) {
/* 1568:2278 */       var2--;
/* 1569:     */     }
/* 1570:2281 */     if (par1AxisAlignedBB.minY < 0.0D) {
/* 1571:2283 */       var4--;
/* 1572:     */     }
/* 1573:2286 */     if (par1AxisAlignedBB.minZ < 0.0D) {
/* 1574:2288 */       var6--;
/* 1575:     */     }
/* 1576:2291 */     for (int var8 = var2; var8 < var3; var8++) {
/* 1577:2293 */       for (int var9 = var4; var9 < var5; var9++) {
/* 1578:2295 */         for (int var10 = var6; var10 < var7; var10++)
/* 1579:     */         {
/* 1580:2297 */           Block var11 = getBlock(var8, var9, var10);
/* 1581:2299 */           if (var11.getMaterial().isLiquid()) {
/* 1582:2301 */             return true;
/* 1583:     */           }
/* 1584:     */         }
/* 1585:     */       }
/* 1586:     */     }
/* 1587:2307 */     return false;
/* 1588:     */   }
/* 1589:     */   
/* 1590:     */   public boolean func_147470_e(AxisAlignedBB p_147470_1_)
/* 1591:     */   {
/* 1592:2312 */     int var2 = MathHelper.floor_double(p_147470_1_.minX);
/* 1593:2313 */     int var3 = MathHelper.floor_double(p_147470_1_.maxX + 1.0D);
/* 1594:2314 */     int var4 = MathHelper.floor_double(p_147470_1_.minY);
/* 1595:2315 */     int var5 = MathHelper.floor_double(p_147470_1_.maxY + 1.0D);
/* 1596:2316 */     int var6 = MathHelper.floor_double(p_147470_1_.minZ);
/* 1597:2317 */     int var7 = MathHelper.floor_double(p_147470_1_.maxZ + 1.0D);
/* 1598:2319 */     if (checkChunksExist(var2, var4, var6, var3, var5, var7)) {
/* 1599:2321 */       for (int var8 = var2; var8 < var3; var8++) {
/* 1600:2323 */         for (int var9 = var4; var9 < var5; var9++) {
/* 1601:2325 */           for (int var10 = var6; var10 < var7; var10++)
/* 1602:     */           {
/* 1603:2327 */             Block var11 = getBlock(var8, var9, var10);
/* 1604:2329 */             if ((var11 == Blocks.fire) || (var11 == Blocks.flowing_lava) || (var11 == Blocks.lava)) {
/* 1605:2331 */               return true;
/* 1606:     */             }
/* 1607:     */           }
/* 1608:     */         }
/* 1609:     */       }
/* 1610:     */     }
/* 1611:2338 */     return false;
/* 1612:     */   }
/* 1613:     */   
/* 1614:     */   public boolean handleMaterialAcceleration(AxisAlignedBB par1AxisAlignedBB, Material par2Material, Entity par3Entity)
/* 1615:     */   {
/* 1616:2346 */     int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
/* 1617:2347 */     int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
/* 1618:2348 */     int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
/* 1619:2349 */     int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
/* 1620:2350 */     int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
/* 1621:2351 */     int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
/* 1622:2353 */     if (!checkChunksExist(var4, var6, var8, var5, var7, var9)) {
/* 1623:2355 */       return false;
/* 1624:     */     }
/* 1625:2359 */     boolean var10 = false;
/* 1626:2360 */     Vec3 var11 = getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);
/* 1627:2362 */     for (int var12 = var4; var12 < var5; var12++) {
/* 1628:2364 */       for (int var13 = var6; var13 < var7; var13++) {
/* 1629:2366 */         for (int var14 = var8; var14 < var9; var14++)
/* 1630:     */         {
/* 1631:2368 */           Block var15 = getBlock(var12, var13, var14);
/* 1632:2370 */           if (var15.getMaterial() == par2Material)
/* 1633:     */           {
/* 1634:2372 */             double var16 = var13 + 1 - BlockLiquid.func_149801_b(getBlockMetadata(var12, var13, var14));
/* 1635:2374 */             if (var7 >= var16)
/* 1636:     */             {
/* 1637:2376 */               var10 = true;
/* 1638:2377 */               var15.velocityToAddToEntity(this, var12, var13, var14, par3Entity, var11);
/* 1639:     */             }
/* 1640:     */           }
/* 1641:     */         }
/* 1642:     */       }
/* 1643:     */     }
/* 1644:2384 */     if ((var11.lengthVector() > 0.0D) && (par3Entity.isPushedByWater()))
/* 1645:     */     {
/* 1646:2386 */       var11 = var11.normalize();
/* 1647:2387 */       double var18 = 0.014D;
/* 1648:2388 */       par3Entity.motionX += var11.xCoord * var18;
/* 1649:2389 */       par3Entity.motionY += var11.yCoord * var18;
/* 1650:2390 */       par3Entity.motionZ += var11.zCoord * var18;
/* 1651:     */     }
/* 1652:2393 */     return var10;
/* 1653:     */   }
/* 1654:     */   
/* 1655:     */   public boolean isMaterialInBB(AxisAlignedBB par1AxisAlignedBB, Material par2Material)
/* 1656:     */   {
/* 1657:2402 */     int var3 = MathHelper.floor_double(par1AxisAlignedBB.minX);
/* 1658:2403 */     int var4 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
/* 1659:2404 */     int var5 = MathHelper.floor_double(par1AxisAlignedBB.minY);
/* 1660:2405 */     int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
/* 1661:2406 */     int var7 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
/* 1662:2407 */     int var8 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
/* 1663:2409 */     for (int var9 = var3; var9 < var4; var9++) {
/* 1664:2411 */       for (int var10 = var5; var10 < var6; var10++) {
/* 1665:2413 */         for (int var11 = var7; var11 < var8; var11++) {
/* 1666:2415 */           if (getBlock(var9, var10, var11).getMaterial() == par2Material) {
/* 1667:2417 */             return true;
/* 1668:     */           }
/* 1669:     */         }
/* 1670:     */       }
/* 1671:     */     }
/* 1672:2423 */     return false;
/* 1673:     */   }
/* 1674:     */   
/* 1675:     */   public boolean isAABBInMaterial(AxisAlignedBB par1AxisAlignedBB, Material par2Material)
/* 1676:     */   {
/* 1677:2431 */     int var3 = MathHelper.floor_double(par1AxisAlignedBB.minX);
/* 1678:2432 */     int var4 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0D);
/* 1679:2433 */     int var5 = MathHelper.floor_double(par1AxisAlignedBB.minY);
/* 1680:2434 */     int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0D);
/* 1681:2435 */     int var7 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
/* 1682:2436 */     int var8 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0D);
/* 1683:2438 */     for (int var9 = var3; var9 < var4; var9++) {
/* 1684:2440 */       for (int var10 = var5; var10 < var6; var10++) {
/* 1685:2442 */         for (int var11 = var7; var11 < var8; var11++)
/* 1686:     */         {
/* 1687:2444 */           Block var12 = getBlock(var9, var10, var11);
/* 1688:2446 */           if (var12.getMaterial() == par2Material)
/* 1689:     */           {
/* 1690:2448 */             int var13 = getBlockMetadata(var9, var10, var11);
/* 1691:2449 */             double var14 = var10 + 1;
/* 1692:2451 */             if (var13 < 8) {
/* 1693:2453 */               var14 = var10 + 1 - var13 / 8.0D;
/* 1694:     */             }
/* 1695:2456 */             if (var14 >= par1AxisAlignedBB.minY) {
/* 1696:2458 */               return true;
/* 1697:     */             }
/* 1698:     */           }
/* 1699:     */         }
/* 1700:     */       }
/* 1701:     */     }
/* 1702:2465 */     return false;
/* 1703:     */   }
/* 1704:     */   
/* 1705:     */   public Explosion createExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9)
/* 1706:     */   {
/* 1707:2473 */     return newExplosion(par1Entity, par2, par4, par6, par8, false, par9);
/* 1708:     */   }
/* 1709:     */   
/* 1710:     */   public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10)
/* 1711:     */   {
/* 1712:2481 */     Explosion var11 = new Explosion(this, par1Entity, par2, par4, par6, par8);
/* 1713:2482 */     var11.isFlaming = par9;
/* 1714:2483 */     var11.isSmoking = par10;
/* 1715:2484 */     var11.doExplosionA();
/* 1716:2485 */     var11.doExplosionB(true);
/* 1717:2486 */     return var11;
/* 1718:     */   }
/* 1719:     */   
/* 1720:     */   public float getBlockDensity(Vec3 par1Vec3, AxisAlignedBB par2AxisAlignedBB)
/* 1721:     */   {
/* 1722:2494 */     double var3 = 1.0D / ((par2AxisAlignedBB.maxX - par2AxisAlignedBB.minX) * 2.0D + 1.0D);
/* 1723:2495 */     double var5 = 1.0D / ((par2AxisAlignedBB.maxY - par2AxisAlignedBB.minY) * 2.0D + 1.0D);
/* 1724:2496 */     double var7 = 1.0D / ((par2AxisAlignedBB.maxZ - par2AxisAlignedBB.minZ) * 2.0D + 1.0D);
/* 1725:2497 */     int var9 = 0;
/* 1726:2498 */     int var10 = 0;
/* 1727:2500 */     for (float var11 = 0.0F; var11 <= 1.0F; var11 = (float)(var11 + var3)) {
/* 1728:2502 */       for (float var12 = 0.0F; var12 <= 1.0F; var12 = (float)(var12 + var5)) {
/* 1729:2504 */         for (float var13 = 0.0F; var13 <= 1.0F; var13 = (float)(var13 + var7))
/* 1730:     */         {
/* 1731:2506 */           double var14 = par2AxisAlignedBB.minX + (par2AxisAlignedBB.maxX - par2AxisAlignedBB.minX) * var11;
/* 1732:2507 */           double var16 = par2AxisAlignedBB.minY + (par2AxisAlignedBB.maxY - par2AxisAlignedBB.minY) * var12;
/* 1733:2508 */           double var18 = par2AxisAlignedBB.minZ + (par2AxisAlignedBB.maxZ - par2AxisAlignedBB.minZ) * var13;
/* 1734:2510 */           if (rayTraceBlocks(getWorldVec3Pool().getVecFromPool(var14, var16, var18), par1Vec3) == null) {
/* 1735:2512 */             var9++;
/* 1736:     */           }
/* 1737:2515 */           var10++;
/* 1738:     */         }
/* 1739:     */       }
/* 1740:     */     }
/* 1741:2520 */     return var9 / var10;
/* 1742:     */   }
/* 1743:     */   
/* 1744:     */   public boolean extinguishFire(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5)
/* 1745:     */   {
/* 1746:2529 */     if (par5 == 0) {
/* 1747:2531 */       par3--;
/* 1748:     */     }
/* 1749:2534 */     if (par5 == 1) {
/* 1750:2536 */       par3++;
/* 1751:     */     }
/* 1752:2539 */     if (par5 == 2) {
/* 1753:2541 */       par4--;
/* 1754:     */     }
/* 1755:2544 */     if (par5 == 3) {
/* 1756:2546 */       par4++;
/* 1757:     */     }
/* 1758:2549 */     if (par5 == 4) {
/* 1759:2551 */       par2--;
/* 1760:     */     }
/* 1761:2554 */     if (par5 == 5) {
/* 1762:2556 */       par2++;
/* 1763:     */     }
/* 1764:2559 */     if (getBlock(par2, par3, par4) == Blocks.fire)
/* 1765:     */     {
/* 1766:2561 */       playAuxSFXAtEntity(par1EntityPlayer, 1004, par2, par3, par4, 0);
/* 1767:2562 */       setBlockToAir(par2, par3, par4);
/* 1768:2563 */       return true;
/* 1769:     */     }
/* 1770:2567 */     return false;
/* 1771:     */   }
/* 1772:     */   
/* 1773:     */   public String getDebugLoadedEntities()
/* 1774:     */   {
/* 1775:2576 */     return "All: " + this.loadedEntityList.size();
/* 1776:     */   }
/* 1777:     */   
/* 1778:     */   public String getProviderName()
/* 1779:     */   {
/* 1780:2584 */     return this.chunkProvider.makeString();
/* 1781:     */   }
/* 1782:     */   
/* 1783:     */   public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_)
/* 1784:     */   {
/* 1785:2589 */     if ((p_147438_2_ >= 0) && (p_147438_2_ < 256))
/* 1786:     */     {
/* 1787:2591 */       TileEntity var4 = null;
/* 1788:2595 */       if (this.field_147481_N) {
/* 1789:2597 */         for (int var5 = 0; var5 < this.field_147484_a.size(); var5++)
/* 1790:     */         {
/* 1791:2599 */           TileEntity var6 = (TileEntity)this.field_147484_a.get(var5);
/* 1792:2601 */           if ((!var6.isInvalid()) && (var6.field_145851_c == p_147438_1_) && (var6.field_145848_d == p_147438_2_) && (var6.field_145849_e == p_147438_3_))
/* 1793:     */           {
/* 1794:2603 */             var4 = var6;
/* 1795:2604 */             break;
/* 1796:     */           }
/* 1797:     */         }
/* 1798:     */       }
/* 1799:2609 */       if (var4 == null)
/* 1800:     */       {
/* 1801:2611 */         Chunk var7 = getChunkFromChunkCoords(p_147438_1_ >> 4, p_147438_3_ >> 4);
/* 1802:2613 */         if (var7 != null) {
/* 1803:2615 */           var4 = var7.func_150806_e(p_147438_1_ & 0xF, p_147438_2_, p_147438_3_ & 0xF);
/* 1804:     */         }
/* 1805:     */       }
/* 1806:2619 */       if (var4 == null) {
/* 1807:2621 */         for (int var5 = 0; var5 < this.field_147484_a.size(); var5++)
/* 1808:     */         {
/* 1809:2623 */           TileEntity var6 = (TileEntity)this.field_147484_a.get(var5);
/* 1810:2625 */           if ((!var6.isInvalid()) && (var6.field_145851_c == p_147438_1_) && (var6.field_145848_d == p_147438_2_) && (var6.field_145849_e == p_147438_3_))
/* 1811:     */           {
/* 1812:2627 */             var4 = var6;
/* 1813:2628 */             break;
/* 1814:     */           }
/* 1815:     */         }
/* 1816:     */       }
/* 1817:2633 */       return var4;
/* 1818:     */     }
/* 1819:2637 */     return null;
/* 1820:     */   }
/* 1821:     */   
/* 1822:     */   public void setTileEntity(int p_147455_1_, int p_147455_2_, int p_147455_3_, TileEntity p_147455_4_)
/* 1823:     */   {
/* 1824:2643 */     if ((p_147455_4_ != null) && (!p_147455_4_.isInvalid())) {
/* 1825:2645 */       if (this.field_147481_N)
/* 1826:     */       {
/* 1827:2647 */         p_147455_4_.field_145851_c = p_147455_1_;
/* 1828:2648 */         p_147455_4_.field_145848_d = p_147455_2_;
/* 1829:2649 */         p_147455_4_.field_145849_e = p_147455_3_;
/* 1830:2650 */         Iterator var5 = this.field_147484_a.iterator();
/* 1831:2652 */         while (var5.hasNext())
/* 1832:     */         {
/* 1833:2654 */           TileEntity var6 = (TileEntity)var5.next();
/* 1834:2656 */           if ((var6.field_145851_c == p_147455_1_) && (var6.field_145848_d == p_147455_2_) && (var6.field_145849_e == p_147455_3_))
/* 1835:     */           {
/* 1836:2658 */             var6.invalidate();
/* 1837:2659 */             var5.remove();
/* 1838:     */           }
/* 1839:     */         }
/* 1840:2663 */         this.field_147484_a.add(p_147455_4_);
/* 1841:     */       }
/* 1842:     */       else
/* 1843:     */       {
/* 1844:2667 */         this.field_147482_g.add(p_147455_4_);
/* 1845:2668 */         Chunk var7 = getChunkFromChunkCoords(p_147455_1_ >> 4, p_147455_3_ >> 4);
/* 1846:2670 */         if (var7 != null) {
/* 1847:2672 */           var7.func_150812_a(p_147455_1_ & 0xF, p_147455_2_, p_147455_3_ & 0xF, p_147455_4_);
/* 1848:     */         }
/* 1849:     */       }
/* 1850:     */     }
/* 1851:     */   }
/* 1852:     */   
/* 1853:     */   public void removeTileEntity(int p_147475_1_, int p_147475_2_, int p_147475_3_)
/* 1854:     */   {
/* 1855:2680 */     TileEntity var4 = getTileEntity(p_147475_1_, p_147475_2_, p_147475_3_);
/* 1856:2682 */     if ((var4 != null) && (this.field_147481_N))
/* 1857:     */     {
/* 1858:2684 */       var4.invalidate();
/* 1859:2685 */       this.field_147484_a.remove(var4);
/* 1860:     */     }
/* 1861:     */     else
/* 1862:     */     {
/* 1863:2689 */       if (var4 != null)
/* 1864:     */       {
/* 1865:2691 */         this.field_147484_a.remove(var4);
/* 1866:2692 */         this.field_147482_g.remove(var4);
/* 1867:     */       }
/* 1868:2695 */       Chunk var5 = getChunkFromChunkCoords(p_147475_1_ >> 4, p_147475_3_ >> 4);
/* 1869:2697 */       if (var5 != null) {
/* 1870:2699 */         var5.removeTileEntity(p_147475_1_ & 0xF, p_147475_2_, p_147475_3_ & 0xF);
/* 1871:     */       }
/* 1872:     */     }
/* 1873:     */   }
/* 1874:     */   
/* 1875:     */   public void func_147457_a(TileEntity p_147457_1_)
/* 1876:     */   {
/* 1877:2706 */     this.field_147483_b.add(p_147457_1_);
/* 1878:     */   }
/* 1879:     */   
/* 1880:     */   public boolean func_147469_q(int p_147469_1_, int p_147469_2_, int p_147469_3_)
/* 1881:     */   {
/* 1882:2711 */     AxisAlignedBB var4 = getBlock(p_147469_1_, p_147469_2_, p_147469_3_).getCollisionBoundingBoxFromPool(this, p_147469_1_, p_147469_2_, p_147469_3_);
/* 1883:2712 */     return (var4 != null) && (var4.getAverageEdgeLength() >= 1.0D);
/* 1884:     */   }
/* 1885:     */   
/* 1886:     */   public static boolean doesBlockHaveSolidTopSurface(IBlockAccess p_147466_0_, int p_147466_1_, int p_147466_2_, int p_147466_3_)
/* 1887:     */   {
/* 1888:2720 */     Block var4 = p_147466_0_.getBlock(p_147466_1_, p_147466_2_, p_147466_3_);
/* 1889:2721 */     int var5 = p_147466_0_.getBlockMetadata(p_147466_1_, p_147466_2_, p_147466_3_);
/* 1890:2722 */     return (var4.getMaterial().isOpaque()) && (var4.renderAsNormalBlock());
/* 1891:     */   }
/* 1892:     */   
/* 1893:     */   public boolean isBlockNormalCubeDefault(int p_147445_1_, int p_147445_2_, int p_147445_3_, boolean p_147445_4_)
/* 1894:     */   {
/* 1895:2731 */     if ((p_147445_1_ >= -30000000) && (p_147445_3_ >= -30000000) && (p_147445_1_ < 30000000) && (p_147445_3_ < 30000000))
/* 1896:     */     {
/* 1897:2733 */       Chunk var5 = this.chunkProvider.provideChunk(p_147445_1_ >> 4, p_147445_3_ >> 4);
/* 1898:2735 */       if ((var5 != null) && (!var5.isEmpty()))
/* 1899:     */       {
/* 1900:2737 */         Block var6 = getBlock(p_147445_1_, p_147445_2_, p_147445_3_);
/* 1901:2738 */         return (var6.getMaterial().isOpaque()) && (var6.renderAsNormalBlock());
/* 1902:     */       }
/* 1903:2742 */       return p_147445_4_;
/* 1904:     */     }
/* 1905:2747 */     return p_147445_4_;
/* 1906:     */   }
/* 1907:     */   
/* 1908:     */   public void calculateInitialSkylight()
/* 1909:     */   {
/* 1910:2756 */     int var1 = calculateSkylightSubtracted(1.0F);
/* 1911:2758 */     if (var1 != this.skylightSubtracted) {
/* 1912:2760 */       this.skylightSubtracted = var1;
/* 1913:     */     }
/* 1914:     */   }
/* 1915:     */   
/* 1916:     */   public void setAllowedSpawnTypes(boolean par1, boolean par2)
/* 1917:     */   {
/* 1918:2769 */     this.spawnHostileMobs = par1;
/* 1919:2770 */     this.spawnPeacefulMobs = par2;
/* 1920:     */   }
/* 1921:     */   
/* 1922:     */   public void tick()
/* 1923:     */   {
/* 1924:2778 */     updateWeather();
/* 1925:     */   }
/* 1926:     */   
/* 1927:     */   private void calculateInitialWeather()
/* 1928:     */   {
/* 1929:2786 */     if (this.worldInfo.isRaining())
/* 1930:     */     {
/* 1931:2788 */       this.rainingStrength = 1.0F;
/* 1932:2790 */       if (this.worldInfo.isThundering()) {
/* 1933:2792 */         this.thunderingStrength = 1.0F;
/* 1934:     */       }
/* 1935:     */     }
/* 1936:     */   }
/* 1937:     */   
/* 1938:     */   protected void updateWeather()
/* 1939:     */   {
/* 1940:2802 */     if (!this.provider.hasNoSky) {
/* 1941:2804 */       if (!this.isClient)
/* 1942:     */       {
/* 1943:2806 */         int var1 = this.worldInfo.getThunderTime();
/* 1944:2808 */         if (var1 <= 0)
/* 1945:     */         {
/* 1946:2810 */           if (this.worldInfo.isThundering()) {
/* 1947:2812 */             this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
/* 1948:     */           } else {
/* 1949:2816 */             this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
/* 1950:     */           }
/* 1951:     */         }
/* 1952:     */         else
/* 1953:     */         {
/* 1954:2821 */           var1--;
/* 1955:2822 */           this.worldInfo.setThunderTime(var1);
/* 1956:2824 */           if (var1 <= 0) {
/* 1957:2826 */             this.worldInfo.setThundering(!this.worldInfo.isThundering());
/* 1958:     */           }
/* 1959:     */         }
/* 1960:2830 */         this.prevThunderingStrength = this.thunderingStrength;
/* 1961:2832 */         if (this.worldInfo.isThundering()) {
/* 1962:2834 */           this.thunderingStrength = ((float)(this.thunderingStrength + 0.01D));
/* 1963:     */         } else {
/* 1964:2838 */           this.thunderingStrength = ((float)(this.thunderingStrength - 0.01D));
/* 1965:     */         }
/* 1966:2841 */         this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0F, 1.0F);
/* 1967:2842 */         int var2 = this.worldInfo.getRainTime();
/* 1968:2844 */         if (var2 <= 0)
/* 1969:     */         {
/* 1970:2846 */           if (this.worldInfo.isRaining()) {
/* 1971:2848 */             this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
/* 1972:     */           } else {
/* 1973:2852 */             this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
/* 1974:     */           }
/* 1975:     */         }
/* 1976:     */         else
/* 1977:     */         {
/* 1978:2857 */           var2--;
/* 1979:2858 */           this.worldInfo.setRainTime(var2);
/* 1980:2860 */           if (var2 <= 0) {
/* 1981:2862 */             this.worldInfo.setRaining(!this.worldInfo.isRaining());
/* 1982:     */           }
/* 1983:     */         }
/* 1984:2866 */         this.prevRainingStrength = this.rainingStrength;
/* 1985:2868 */         if (this.worldInfo.isRaining()) {
/* 1986:2870 */           this.rainingStrength = ((float)(this.rainingStrength + 0.01D));
/* 1987:     */         } else {
/* 1988:2874 */           this.rainingStrength = ((float)(this.rainingStrength - 0.01D));
/* 1989:     */         }
/* 1990:2877 */         this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0F, 1.0F);
/* 1991:     */       }
/* 1992:     */     }
/* 1993:     */   }
/* 1994:     */   
/* 1995:     */   protected void setActivePlayerChunksAndCheckLight()
/* 1996:     */   {
/* 1997:2884 */     this.activeChunkSet.clear();
/* 1998:2885 */     this.theProfiler.startSection("buildList");
/* 1999:2891 */     for (int var1 = 0; var1 < this.playerEntities.size(); var1++)
/* 2000:     */     {
/* 2001:2893 */       EntityPlayer var2 = (EntityPlayer)this.playerEntities.get(var1);
/* 2002:2894 */       int var3 = MathHelper.floor_double(var2.posX / 16.0D);
/* 2003:2895 */       int var4 = MathHelper.floor_double(var2.posZ / 16.0D);
/* 2004:2896 */       byte var5 = 7;
/* 2005:2898 */       for (int var6 = -var5; var6 <= var5; var6++) {
/* 2006:2900 */         for (int var7 = -var5; var7 <= var5; var7++) {
/* 2007:2902 */           this.activeChunkSet.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
/* 2008:     */         }
/* 2009:     */       }
/* 2010:     */     }
/* 2011:2907 */     this.theProfiler.endSection();
/* 2012:2909 */     if (this.ambientTickCountdown > 0) {
/* 2013:2911 */       this.ambientTickCountdown -= 1;
/* 2014:     */     }
/* 2015:2914 */     this.theProfiler.startSection("playerCheckLight");
/* 2016:2916 */     if (!this.playerEntities.isEmpty())
/* 2017:     */     {
/* 2018:2918 */       var1 = this.rand.nextInt(this.playerEntities.size());
/* 2019:2919 */       EntityPlayer var2 = (EntityPlayer)this.playerEntities.get(var1);
/* 2020:2920 */       int var3 = MathHelper.floor_double(var2.posX) + this.rand.nextInt(11) - 5;
/* 2021:2921 */       int var4 = MathHelper.floor_double(var2.posY) + this.rand.nextInt(11) - 5;
/* 2022:2922 */       int var8 = MathHelper.floor_double(var2.posZ) + this.rand.nextInt(11) - 5;
/* 2023:2923 */       func_147451_t(var3, var4, var8);
/* 2024:     */     }
/* 2025:2926 */     this.theProfiler.endSection();
/* 2026:     */   }
/* 2027:     */   
/* 2028:     */   protected void func_147467_a(int p_147467_1_, int p_147467_2_, Chunk p_147467_3_)
/* 2029:     */   {
/* 2030:2931 */     this.theProfiler.endStartSection("moodSound");
/* 2031:2933 */     if ((this.ambientTickCountdown == 0) && (!this.isClient))
/* 2032:     */     {
/* 2033:2935 */       this.updateLCG = (this.updateLCG * 3 + 1013904223);
/* 2034:2936 */       int var4 = this.updateLCG >> 2;
/* 2035:2937 */       int var5 = var4 & 0xF;
/* 2036:2938 */       int var6 = var4 >> 8 & 0xF;
/* 2037:2939 */       int var7 = var4 >> 16 & 0xFF;
/* 2038:2940 */       Block var8 = p_147467_3_.func_150810_a(var5, var7, var6);
/* 2039:2941 */       var5 += p_147467_1_;
/* 2040:2942 */       var6 += p_147467_2_;
/* 2041:2944 */       if ((var8.getMaterial() == Material.air) && (getFullBlockLightValue(var5, var7, var6) <= this.rand.nextInt(8)) && (getSavedLightValue(EnumSkyBlock.Sky, var5, var7, var6) <= 0))
/* 2042:     */       {
/* 2043:2946 */         EntityPlayer var9 = getClosestPlayer(var5 + 0.5D, var7 + 0.5D, var6 + 0.5D, 8.0D);
/* 2044:2948 */         if ((var9 != null) && (var9.getDistanceSq(var5 + 0.5D, var7 + 0.5D, var6 + 0.5D) > 4.0D))
/* 2045:     */         {
/* 2046:2950 */           playSoundEffect(var5 + 0.5D, var7 + 0.5D, var6 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.rand.nextFloat() * 0.2F);
/* 2047:2951 */           this.ambientTickCountdown = (this.rand.nextInt(12000) + 6000);
/* 2048:     */         }
/* 2049:     */       }
/* 2050:     */     }
/* 2051:2956 */     this.theProfiler.endStartSection("checkLight");
/* 2052:2957 */     p_147467_3_.enqueueRelightChecks();
/* 2053:     */   }
/* 2054:     */   
/* 2055:     */   protected void func_147456_g()
/* 2056:     */   {
/* 2057:2962 */     setActivePlayerChunksAndCheckLight();
/* 2058:     */   }
/* 2059:     */   
/* 2060:     */   public boolean isBlockFreezable(int par1, int par2, int par3)
/* 2061:     */   {
/* 2062:2970 */     return canBlockFreeze(par1, par2, par3, false);
/* 2063:     */   }
/* 2064:     */   
/* 2065:     */   public boolean isBlockFreezableNaturally(int par1, int par2, int par3)
/* 2066:     */   {
/* 2067:2978 */     return canBlockFreeze(par1, par2, par3, true);
/* 2068:     */   }
/* 2069:     */   
/* 2070:     */   public boolean canBlockFreeze(int par1, int par2, int par3, boolean par4)
/* 2071:     */   {
/* 2072:2987 */     BiomeGenBase var5 = getBiomeGenForCoords(par1, par3);
/* 2073:2988 */     float var6 = var5.getFloatTemperature(par1, par2, par3);
/* 2074:2990 */     if (var6 > 0.15F) {
/* 2075:2992 */       return false;
/* 2076:     */     }
/* 2077:2996 */     if ((par2 >= 0) && (par2 < 256) && (getSavedLightValue(EnumSkyBlock.Block, par1, par2, par3) < 10))
/* 2078:     */     {
/* 2079:2998 */       Block var7 = getBlock(par1, par2, par3);
/* 2080:3000 */       if (((var7 == Blocks.water) || (var7 == Blocks.flowing_water)) && (getBlockMetadata(par1, par2, par3) == 0))
/* 2081:     */       {
/* 2082:3002 */         if (!par4) {
/* 2083:3004 */           return true;
/* 2084:     */         }
/* 2085:3007 */         boolean var8 = true;
/* 2086:3009 */         if ((var8) && (getBlock(par1 - 1, par2, par3).getMaterial() != Material.water)) {
/* 2087:3011 */           var8 = false;
/* 2088:     */         }
/* 2089:3014 */         if ((var8) && (getBlock(par1 + 1, par2, par3).getMaterial() != Material.water)) {
/* 2090:3016 */           var8 = false;
/* 2091:     */         }
/* 2092:3019 */         if ((var8) && (getBlock(par1, par2, par3 - 1).getMaterial() != Material.water)) {
/* 2093:3021 */           var8 = false;
/* 2094:     */         }
/* 2095:3024 */         if ((var8) && (getBlock(par1, par2, par3 + 1).getMaterial() != Material.water)) {
/* 2096:3026 */           var8 = false;
/* 2097:     */         }
/* 2098:3029 */         if (!var8) {
/* 2099:3031 */           return true;
/* 2100:     */         }
/* 2101:     */       }
/* 2102:     */     }
/* 2103:3036 */     return false;
/* 2104:     */   }
/* 2105:     */   
/* 2106:     */   public boolean func_147478_e(int p_147478_1_, int p_147478_2_, int p_147478_3_, boolean p_147478_4_)
/* 2107:     */   {
/* 2108:3042 */     BiomeGenBase var5 = getBiomeGenForCoords(p_147478_1_, p_147478_3_);
/* 2109:3043 */     float var6 = var5.getFloatTemperature(p_147478_1_, p_147478_2_, p_147478_3_);
/* 2110:3045 */     if (var6 > 0.15F) {
/* 2111:3047 */       return false;
/* 2112:     */     }
/* 2113:3049 */     if (!p_147478_4_) {
/* 2114:3051 */       return true;
/* 2115:     */     }
/* 2116:3055 */     if ((p_147478_2_ >= 0) && (p_147478_2_ < 256) && (getSavedLightValue(EnumSkyBlock.Block, p_147478_1_, p_147478_2_, p_147478_3_) < 10))
/* 2117:     */     {
/* 2118:3057 */       Block var7 = getBlock(p_147478_1_, p_147478_2_, p_147478_3_);
/* 2119:3059 */       if ((var7.getMaterial() == Material.air) && (Blocks.snow_layer.canPlaceBlockAt(this, p_147478_1_, p_147478_2_, p_147478_3_))) {
/* 2120:3061 */         return true;
/* 2121:     */       }
/* 2122:     */     }
/* 2123:3065 */     return false;
/* 2124:     */   }
/* 2125:     */   
/* 2126:     */   public boolean func_147451_t(int p_147451_1_, int p_147451_2_, int p_147451_3_)
/* 2127:     */   {
/* 2128:3071 */     boolean var4 = false;
/* 2129:3073 */     if (!this.provider.hasNoSky) {
/* 2130:3075 */       var4 |= updateLightByType(EnumSkyBlock.Sky, p_147451_1_, p_147451_2_, p_147451_3_);
/* 2131:     */     }
/* 2132:3078 */     var4 |= updateLightByType(EnumSkyBlock.Block, p_147451_1_, p_147451_2_, p_147451_3_);
/* 2133:3079 */     return var4;
/* 2134:     */   }
/* 2135:     */   
/* 2136:     */   private int computeLightValue(int par1, int par2, int par3, EnumSkyBlock par4EnumSkyBlock)
/* 2137:     */   {
/* 2138:3084 */     if ((par4EnumSkyBlock == EnumSkyBlock.Sky) && (canBlockSeeTheSky(par1, par2, par3))) {
/* 2139:3086 */       return 15;
/* 2140:     */     }
/* 2141:3090 */     Block var5 = getBlock(par1, par2, par3);
/* 2142:3091 */     int var6 = par4EnumSkyBlock == EnumSkyBlock.Sky ? 0 : var5.getLightValue();
/* 2143:3092 */     int var7 = var5.getLightOpacity();
/* 2144:3094 */     if ((var7 >= 15) && (var5.getLightValue() > 0)) {
/* 2145:3096 */       var7 = 1;
/* 2146:     */     }
/* 2147:3099 */     if (var7 < 1) {
/* 2148:3101 */       var7 = 1;
/* 2149:     */     }
/* 2150:3104 */     if (var7 >= 15) {
/* 2151:3106 */       return 0;
/* 2152:     */     }
/* 2153:3108 */     if (var6 >= 14) {
/* 2154:3110 */       return var6;
/* 2155:     */     }
/* 2156:3114 */     for (int var8 = 0; var8 < 6; var8++)
/* 2157:     */     {
/* 2158:3116 */       int var9 = par1 + net.minecraft.util.Facing.offsetsXForSide[var8];
/* 2159:3117 */       int var10 = par2 + net.minecraft.util.Facing.offsetsYForSide[var8];
/* 2160:3118 */       int var11 = par3 + net.minecraft.util.Facing.offsetsZForSide[var8];
/* 2161:3119 */       int var12 = getSavedLightValue(par4EnumSkyBlock, var9, var10, var11) - var7;
/* 2162:3121 */       if (var12 > var6) {
/* 2163:3123 */         var6 = var12;
/* 2164:     */       }
/* 2165:3126 */       if (var6 >= 14) {
/* 2166:3128 */         return var6;
/* 2167:     */       }
/* 2168:     */     }
/* 2169:3132 */     return var6;
/* 2170:     */   }
/* 2171:     */   
/* 2172:     */   public boolean updateLightByType(EnumSkyBlock p_147463_1_, int p_147463_2_, int p_147463_3_, int p_147463_4_)
/* 2173:     */   {
/* 2174:3139 */     if (!doChunksNearChunkExist(p_147463_2_, p_147463_3_, p_147463_4_, 17)) {
/* 2175:3141 */       return false;
/* 2176:     */     }
/* 2177:3145 */     int var5 = 0;
/* 2178:3146 */     int var6 = 0;
/* 2179:3147 */     this.theProfiler.startSection("getBrightness");
/* 2180:3148 */     int var7 = getSavedLightValue(p_147463_1_, p_147463_2_, p_147463_3_, p_147463_4_);
/* 2181:3149 */     int var8 = computeLightValue(p_147463_2_, p_147463_3_, p_147463_4_, p_147463_1_);
/* 2182:3160 */     if (var8 > var7)
/* 2183:     */     {
/* 2184:3162 */       this.lightUpdateBlockList[(var6++)] = 133152;
/* 2185:     */     }
/* 2186:3164 */     else if (var8 < var7)
/* 2187:     */     {
/* 2188:3166 */       this.lightUpdateBlockList[(var6++)] = (0x20820 | var7 << 18);
/* 2189:3168 */       while (var5 < var6)
/* 2190:     */       {
/* 2191:3170 */         int var9 = this.lightUpdateBlockList[(var5++)];
/* 2192:3171 */         int var10 = (var9 & 0x3F) - 32 + p_147463_2_;
/* 2193:3172 */         int var11 = (var9 >> 6 & 0x3F) - 32 + p_147463_3_;
/* 2194:3173 */         int var12 = (var9 >> 12 & 0x3F) - 32 + p_147463_4_;
/* 2195:3174 */         int var13 = var9 >> 18 & 0xF;
/* 2196:3175 */         int var14 = getSavedLightValue(p_147463_1_, var10, var11, var12);
/* 2197:3177 */         if (var14 == var13)
/* 2198:     */         {
/* 2199:3179 */           setLightValue(p_147463_1_, var10, var11, var12, 0);
/* 2200:3181 */           if (var13 > 0)
/* 2201:     */           {
/* 2202:3183 */             int var15 = MathHelper.abs_int(var10 - p_147463_2_);
/* 2203:3184 */             int var16 = MathHelper.abs_int(var11 - p_147463_3_);
/* 2204:3185 */             int var17 = MathHelper.abs_int(var12 - p_147463_4_);
/* 2205:3187 */             if (var15 + var16 + var17 < 17) {
/* 2206:3189 */               for (int var18 = 0; var18 < 6; var18++)
/* 2207:     */               {
/* 2208:3191 */                 int var19 = var10 + net.minecraft.util.Facing.offsetsXForSide[var18];
/* 2209:3192 */                 int var20 = var11 + net.minecraft.util.Facing.offsetsYForSide[var18];
/* 2210:3193 */                 int var21 = var12 + net.minecraft.util.Facing.offsetsZForSide[var18];
/* 2211:3194 */                 int var22 = Math.max(1, getBlock(var19, var20, var21).getLightOpacity());
/* 2212:3195 */                 var14 = getSavedLightValue(p_147463_1_, var19, var20, var21);
/* 2213:3197 */                 if ((var14 == var13 - var22) && (var6 < this.lightUpdateBlockList.length)) {
/* 2214:3199 */                   this.lightUpdateBlockList[(var6++)] = (var19 - p_147463_2_ + 32 | var20 - p_147463_3_ + 32 << 6 | var21 - p_147463_4_ + 32 << 12 | var13 - var22 << 18);
/* 2215:     */                 }
/* 2216:     */               }
/* 2217:     */             }
/* 2218:     */           }
/* 2219:     */         }
/* 2220:     */       }
/* 2221:3207 */       var5 = 0;
/* 2222:     */     }
/* 2223:3210 */     this.theProfiler.endSection();
/* 2224:3211 */     this.theProfiler.startSection("checkedPosition < toCheckCount");
/* 2225:3213 */     while (var5 < var6)
/* 2226:     */     {
/* 2227:3215 */       int var9 = this.lightUpdateBlockList[(var5++)];
/* 2228:3216 */       int var10 = (var9 & 0x3F) - 32 + p_147463_2_;
/* 2229:3217 */       int var11 = (var9 >> 6 & 0x3F) - 32 + p_147463_3_;
/* 2230:3218 */       int var12 = (var9 >> 12 & 0x3F) - 32 + p_147463_4_;
/* 2231:3219 */       int var13 = getSavedLightValue(p_147463_1_, var10, var11, var12);
/* 2232:3220 */       int var14 = computeLightValue(var10, var11, var12, p_147463_1_);
/* 2233:3222 */       if (var14 != var13)
/* 2234:     */       {
/* 2235:3224 */         setLightValue(p_147463_1_, var10, var11, var12, var14);
/* 2236:3226 */         if (var14 > var13)
/* 2237:     */         {
/* 2238:3228 */           int var15 = Math.abs(var10 - p_147463_2_);
/* 2239:3229 */           int var16 = Math.abs(var11 - p_147463_3_);
/* 2240:3230 */           int var17 = Math.abs(var12 - p_147463_4_);
/* 2241:3231 */           boolean var23 = var6 < this.lightUpdateBlockList.length - 6;
/* 2242:3233 */           if ((var15 + var16 + var17 < 17) && (var23))
/* 2243:     */           {
/* 2244:3235 */             if (getSavedLightValue(p_147463_1_, var10 - 1, var11, var12) < var14) {
/* 2245:3237 */               this.lightUpdateBlockList[(var6++)] = (var10 - 1 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12));
/* 2246:     */             }
/* 2247:3240 */             if (getSavedLightValue(p_147463_1_, var10 + 1, var11, var12) < var14) {
/* 2248:3242 */               this.lightUpdateBlockList[(var6++)] = (var10 + 1 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12));
/* 2249:     */             }
/* 2250:3245 */             if (getSavedLightValue(p_147463_1_, var10, var11 - 1, var12) < var14) {
/* 2251:3247 */               this.lightUpdateBlockList[(var6++)] = (var10 - p_147463_2_ + 32 + (var11 - 1 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12));
/* 2252:     */             }
/* 2253:3250 */             if (getSavedLightValue(p_147463_1_, var10, var11 + 1, var12) < var14) {
/* 2254:3252 */               this.lightUpdateBlockList[(var6++)] = (var10 - p_147463_2_ + 32 + (var11 + 1 - p_147463_3_ + 32 << 6) + (var12 - p_147463_4_ + 32 << 12));
/* 2255:     */             }
/* 2256:3255 */             if (getSavedLightValue(p_147463_1_, var10, var11, var12 - 1) < var14) {
/* 2257:3257 */               this.lightUpdateBlockList[(var6++)] = (var10 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 - 1 - p_147463_4_ + 32 << 12));
/* 2258:     */             }
/* 2259:3260 */             if (getSavedLightValue(p_147463_1_, var10, var11, var12 + 1) < var14) {
/* 2260:3262 */               this.lightUpdateBlockList[(var6++)] = (var10 - p_147463_2_ + 32 + (var11 - p_147463_3_ + 32 << 6) + (var12 + 1 - p_147463_4_ + 32 << 12));
/* 2261:     */             }
/* 2262:     */           }
/* 2263:     */         }
/* 2264:     */       }
/* 2265:     */     }
/* 2266:3269 */     this.theProfiler.endSection();
/* 2267:3270 */     return true;
/* 2268:     */   }
/* 2269:     */   
/* 2270:     */   public boolean tickUpdates(boolean par1)
/* 2271:     */   {
/* 2272:3279 */     return false;
/* 2273:     */   }
/* 2274:     */   
/* 2275:     */   public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2)
/* 2276:     */   {
/* 2277:3284 */     return null;
/* 2278:     */   }
/* 2279:     */   
/* 2280:     */   public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB)
/* 2281:     */   {
/* 2282:3292 */     return getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB, null);
/* 2283:     */   }
/* 2284:     */   
/* 2285:     */   public List getEntitiesWithinAABBExcludingEntity(Entity par1Entity, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector)
/* 2286:     */   {
/* 2287:3297 */     ArrayList var4 = new ArrayList();
/* 2288:3298 */     int var5 = MathHelper.floor_double((par2AxisAlignedBB.minX - 2.0D) / 16.0D);
/* 2289:3299 */     int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxX + 2.0D) / 16.0D);
/* 2290:3300 */     int var7 = MathHelper.floor_double((par2AxisAlignedBB.minZ - 2.0D) / 16.0D);
/* 2291:3301 */     int var8 = MathHelper.floor_double((par2AxisAlignedBB.maxZ + 2.0D) / 16.0D);
/* 2292:3303 */     for (int var9 = var5; var9 <= var6; var9++) {
/* 2293:3305 */       for (int var10 = var7; var10 <= var8; var10++) {
/* 2294:3307 */         if (chunkExists(var9, var10)) {
/* 2295:3309 */           getChunkFromChunkCoords(var9, var10).getEntitiesWithinAABBForEntity(par1Entity, par2AxisAlignedBB, var4, par3IEntitySelector);
/* 2296:     */         }
/* 2297:     */       }
/* 2298:     */     }
/* 2299:3314 */     return var4;
/* 2300:     */   }
/* 2301:     */   
/* 2302:     */   public List getEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB)
/* 2303:     */   {
/* 2304:3322 */     return selectEntitiesWithinAABB(par1Class, par2AxisAlignedBB, null);
/* 2305:     */   }
/* 2306:     */   
/* 2307:     */   public List selectEntitiesWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, IEntitySelector par3IEntitySelector)
/* 2308:     */   {
/* 2309:3327 */     int var4 = MathHelper.floor_double((par2AxisAlignedBB.minX - 2.0D) / 16.0D);
/* 2310:3328 */     int var5 = MathHelper.floor_double((par2AxisAlignedBB.maxX + 2.0D) / 16.0D);
/* 2311:3329 */     int var6 = MathHelper.floor_double((par2AxisAlignedBB.minZ - 2.0D) / 16.0D);
/* 2312:3330 */     int var7 = MathHelper.floor_double((par2AxisAlignedBB.maxZ + 2.0D) / 16.0D);
/* 2313:3331 */     ArrayList var8 = new ArrayList();
/* 2314:3333 */     for (int var9 = var4; var9 <= var5; var9++) {
/* 2315:3335 */       for (int var10 = var6; var10 <= var7; var10++) {
/* 2316:3337 */         if (chunkExists(var9, var10)) {
/* 2317:3339 */           getChunkFromChunkCoords(var9, var10).getEntitiesOfTypeWithinAAAB(par1Class, par2AxisAlignedBB, var8, par3IEntitySelector);
/* 2318:     */         }
/* 2319:     */       }
/* 2320:     */     }
/* 2321:3344 */     return var8;
/* 2322:     */   }
/* 2323:     */   
/* 2324:     */   public Entity findNearestEntityWithinAABB(Class par1Class, AxisAlignedBB par2AxisAlignedBB, Entity par3Entity)
/* 2325:     */   {
/* 2326:3349 */     List var4 = getEntitiesWithinAABB(par1Class, par2AxisAlignedBB);
/* 2327:3350 */     Entity var5 = null;
/* 2328:3351 */     double var6 = 1.7976931348623157E+308D;
/* 2329:3353 */     for (int var8 = 0; var8 < var4.size(); var8++)
/* 2330:     */     {
/* 2331:3355 */       Entity var9 = (Entity)var4.get(var8);
/* 2332:3357 */       if (var9 != par3Entity)
/* 2333:     */       {
/* 2334:3359 */         double var10 = par3Entity.getDistanceSqToEntity(var9);
/* 2335:3361 */         if (var10 <= var6)
/* 2336:     */         {
/* 2337:3363 */           var5 = var9;
/* 2338:3364 */           var6 = var10;
/* 2339:     */         }
/* 2340:     */       }
/* 2341:     */     }
/* 2342:3369 */     return var5;
/* 2343:     */   }
/* 2344:     */   
/* 2345:     */   public abstract Entity getEntityByID(int paramInt);
/* 2346:     */   
/* 2347:     */   public List getLoadedEntityList()
/* 2348:     */   {
/* 2349:3382 */     return this.loadedEntityList;
/* 2350:     */   }
/* 2351:     */   
/* 2352:     */   public void func_147476_b(int p_147476_1_, int p_147476_2_, int p_147476_3_, TileEntity p_147476_4_)
/* 2353:     */   {
/* 2354:3387 */     if (blockExists(p_147476_1_, p_147476_2_, p_147476_3_)) {
/* 2355:3389 */       getChunkFromBlockCoords(p_147476_1_, p_147476_3_).setChunkModified();
/* 2356:     */     }
/* 2357:     */   }
/* 2358:     */   
/* 2359:     */   public int countEntities(Class par1Class)
/* 2360:     */   {
/* 2361:3398 */     int var2 = 0;
/* 2362:3400 */     for (int var3 = 0; var3 < this.loadedEntityList.size(); var3++)
/* 2363:     */     {
/* 2364:3402 */       Entity var4 = (Entity)this.loadedEntityList.get(var3);
/* 2365:3404 */       if (((!(var4 instanceof EntityLiving)) || (!((EntityLiving)var4).isNoDespawnRequired())) && (par1Class.isAssignableFrom(var4.getClass()))) {
/* 2366:3406 */         var2++;
/* 2367:     */       }
/* 2368:     */     }
/* 2369:3410 */     return var2;
/* 2370:     */   }
/* 2371:     */   
/* 2372:     */   public void addLoadedEntities(List par1List)
/* 2373:     */   {
/* 2374:3418 */     this.loadedEntityList.addAll(par1List);
/* 2375:3420 */     for (int var2 = 0; var2 < par1List.size(); var2++) {
/* 2376:3422 */       onEntityAdded((Entity)par1List.get(var2));
/* 2377:     */     }
/* 2378:     */   }
/* 2379:     */   
/* 2380:     */   public void unloadEntities(List par1List)
/* 2381:     */   {
/* 2382:3431 */     this.unloadedEntityList.addAll(par1List);
/* 2383:     */   }
/* 2384:     */   
/* 2385:     */   public boolean canPlaceEntityOnSide(Block p_147472_1_, int p_147472_2_, int p_147472_3_, int p_147472_4_, boolean p_147472_5_, int p_147472_6_, Entity p_147472_7_, ItemStack p_147472_8_)
/* 2386:     */   {
/* 2387:3436 */     Block var9 = getBlock(p_147472_2_, p_147472_3_, p_147472_4_);
/* 2388:3437 */     AxisAlignedBB var10 = p_147472_5_ ? null : p_147472_1_.getCollisionBoundingBoxFromPool(this, p_147472_2_, p_147472_3_, p_147472_4_);
/* 2389:3438 */     return (var10 == null) || (checkNoEntityCollision(var10, p_147472_7_));
/* 2390:     */   }
/* 2391:     */   
/* 2392:     */   public PathEntity getPathEntityToEntity(Entity par1Entity, Entity par2Entity, float par3, boolean par4, boolean par5, boolean par6, boolean par7)
/* 2393:     */   {
/* 2394:3443 */     this.theProfiler.startSection("pathfind");
/* 2395:3444 */     int var8 = MathHelper.floor_double(par1Entity.posX);
/* 2396:3445 */     int var9 = MathHelper.floor_double(par1Entity.posY + 1.0D);
/* 2397:3446 */     int var10 = MathHelper.floor_double(par1Entity.posZ);
/* 2398:3447 */     int var11 = (int)(par3 + 16.0F);
/* 2399:3448 */     int var12 = var8 - var11;
/* 2400:3449 */     int var13 = var9 - var11;
/* 2401:3450 */     int var14 = var10 - var11;
/* 2402:3451 */     int var15 = var8 + var11;
/* 2403:3452 */     int var16 = var9 + var11;
/* 2404:3453 */     int var17 = var10 + var11;
/* 2405:3454 */     ChunkCache var18 = new ChunkCache(this, var12, var13, var14, var15, var16, var17, 0);
/* 2406:3455 */     PathEntity var19 = new PathFinder(var18, par4, par5, par6, par7).createEntityPathTo(par1Entity, par2Entity, par3);
/* 2407:3456 */     this.theProfiler.endSection();
/* 2408:3457 */     return var19;
/* 2409:     */   }
/* 2410:     */   
/* 2411:     */   public PathEntity getEntityPathToXYZ(Entity par1Entity, int par2, int par3, int par4, float par5, boolean par6, boolean par7, boolean par8, boolean par9)
/* 2412:     */   {
/* 2413:3462 */     this.theProfiler.startSection("pathfind");
/* 2414:3463 */     int var10 = MathHelper.floor_double(par1Entity.posX);
/* 2415:3464 */     int var11 = MathHelper.floor_double(par1Entity.posY);
/* 2416:3465 */     int var12 = MathHelper.floor_double(par1Entity.posZ);
/* 2417:3466 */     int var13 = (int)(par5 + 8.0F);
/* 2418:3467 */     int var14 = var10 - var13;
/* 2419:3468 */     int var15 = var11 - var13;
/* 2420:3469 */     int var16 = var12 - var13;
/* 2421:3470 */     int var17 = var10 + var13;
/* 2422:3471 */     int var18 = var11 + var13;
/* 2423:3472 */     int var19 = var12 + var13;
/* 2424:3473 */     ChunkCache var20 = new ChunkCache(this, var14, var15, var16, var17, var18, var19, 0);
/* 2425:3474 */     PathEntity var21 = new PathFinder(var20, par6, par7, par8, par9).createEntityPathTo(par1Entity, par2, par3, par4, par5);
/* 2426:3475 */     this.theProfiler.endSection();
/* 2427:3476 */     return var21;
/* 2428:     */   }
/* 2429:     */   
/* 2430:     */   public int isBlockProvidingPowerTo(int par1, int par2, int par3, int par4)
/* 2431:     */   {
/* 2432:3484 */     return getBlock(par1, par2, par3).isProvidingStrongPower(this, par1, par2, par3, par4);
/* 2433:     */   }
/* 2434:     */   
/* 2435:     */   public int getBlockPowerInput(int par1, int par2, int par3)
/* 2436:     */   {
/* 2437:3492 */     byte var4 = 0;
/* 2438:3493 */     int var5 = Math.max(var4, isBlockProvidingPowerTo(par1, par2 - 1, par3, 0));
/* 2439:3495 */     if (var5 >= 15) {
/* 2440:3497 */       return var5;
/* 2441:     */     }
/* 2442:3501 */     var5 = Math.max(var5, isBlockProvidingPowerTo(par1, par2 + 1, par3, 1));
/* 2443:3503 */     if (var5 >= 15) {
/* 2444:3505 */       return var5;
/* 2445:     */     }
/* 2446:3509 */     var5 = Math.max(var5, isBlockProvidingPowerTo(par1, par2, par3 - 1, 2));
/* 2447:3511 */     if (var5 >= 15) {
/* 2448:3513 */       return var5;
/* 2449:     */     }
/* 2450:3517 */     var5 = Math.max(var5, isBlockProvidingPowerTo(par1, par2, par3 + 1, 3));
/* 2451:3519 */     if (var5 >= 15) {
/* 2452:3521 */       return var5;
/* 2453:     */     }
/* 2454:3525 */     var5 = Math.max(var5, isBlockProvidingPowerTo(par1 - 1, par2, par3, 4));
/* 2455:3527 */     if (var5 >= 15) {
/* 2456:3529 */       return var5;
/* 2457:     */     }
/* 2458:3533 */     var5 = Math.max(var5, isBlockProvidingPowerTo(par1 + 1, par2, par3, 5));
/* 2459:3534 */     return var5 >= 15 ? var5 : var5;
/* 2460:     */   }
/* 2461:     */   
/* 2462:     */   public boolean getIndirectPowerOutput(int par1, int par2, int par3, int par4)
/* 2463:     */   {
/* 2464:3548 */     return getIndirectPowerLevelTo(par1, par2, par3, par4) > 0;
/* 2465:     */   }
/* 2466:     */   
/* 2467:     */   public int getIndirectPowerLevelTo(int par1, int par2, int par3, int par4)
/* 2468:     */   {
/* 2469:3556 */     return getBlock(par1, par2, par3).isNormalCube() ? getBlockPowerInput(par1, par2, par3) : getBlock(par1, par2, par3).isProvidingWeakPower(this, par1, par2, par3, par4);
/* 2470:     */   }
/* 2471:     */   
/* 2472:     */   public boolean isBlockIndirectlyGettingPowered(int par1, int par2, int par3)
/* 2473:     */   {
/* 2474:3565 */     return getIndirectPowerLevelTo(par1, par2 - 1, par3, 0) > 0;
/* 2475:     */   }
/* 2476:     */   
/* 2477:     */   public int getStrongestIndirectPower(int par1, int par2, int par3)
/* 2478:     */   {
/* 2479:3570 */     int var4 = 0;
/* 2480:3572 */     for (int var5 = 0; var5 < 6; var5++)
/* 2481:     */     {
/* 2482:3574 */       int var6 = getIndirectPowerLevelTo(par1 + net.minecraft.util.Facing.offsetsXForSide[var5], par2 + net.minecraft.util.Facing.offsetsYForSide[var5], par3 + net.minecraft.util.Facing.offsetsZForSide[var5], var5);
/* 2483:3576 */       if (var6 >= 15) {
/* 2484:3578 */         return 15;
/* 2485:     */       }
/* 2486:3581 */       if (var6 > var4) {
/* 2487:3583 */         var4 = var6;
/* 2488:     */       }
/* 2489:     */     }
/* 2490:3587 */     return var4;
/* 2491:     */   }
/* 2492:     */   
/* 2493:     */   public EntityPlayer getClosestPlayerToEntity(Entity par1Entity, double par2)
/* 2494:     */   {
/* 2495:3596 */     return getClosestPlayer(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par2);
/* 2496:     */   }
/* 2497:     */   
/* 2498:     */   public EntityPlayer getClosestPlayer(double par1, double par3, double par5, double par7)
/* 2499:     */   {
/* 2500:3605 */     double var9 = -1.0D;
/* 2501:3606 */     EntityPlayer var11 = null;
/* 2502:3608 */     for (int var12 = 0; var12 < this.playerEntities.size(); var12++)
/* 2503:     */     {
/* 2504:3610 */       EntityPlayer var13 = (EntityPlayer)this.playerEntities.get(var12);
/* 2505:3611 */       double var14 = var13.getDistanceSq(par1, par3, par5);
/* 2506:3613 */       if (((par7 < 0.0D) || (var14 < par7 * par7)) && ((var9 == -1.0D) || (var14 < var9)))
/* 2507:     */       {
/* 2508:3615 */         var9 = var14;
/* 2509:3616 */         var11 = var13;
/* 2510:     */       }
/* 2511:     */     }
/* 2512:3620 */     return var11;
/* 2513:     */   }
/* 2514:     */   
/* 2515:     */   public EntityPlayer getClosestVulnerablePlayerToEntity(Entity par1Entity, double par2)
/* 2516:     */   {
/* 2517:3628 */     return getClosestVulnerablePlayer(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par2);
/* 2518:     */   }
/* 2519:     */   
/* 2520:     */   public EntityPlayer getClosestVulnerablePlayer(double par1, double par3, double par5, double par7)
/* 2521:     */   {
/* 2522:3636 */     double var9 = -1.0D;
/* 2523:3637 */     EntityPlayer var11 = null;
/* 2524:3639 */     for (int var12 = 0; var12 < this.playerEntities.size(); var12++)
/* 2525:     */     {
/* 2526:3641 */       EntityPlayer var13 = (EntityPlayer)this.playerEntities.get(var12);
/* 2527:3643 */       if ((!var13.capabilities.disableDamage) && (var13.isEntityAlive()))
/* 2528:     */       {
/* 2529:3645 */         double var14 = var13.getDistanceSq(par1, par3, par5);
/* 2530:3646 */         double var16 = par7;
/* 2531:3648 */         if (var13.isSneaking()) {
/* 2532:3650 */           var16 = par7 * 0.800000011920929D;
/* 2533:     */         }
/* 2534:3653 */         if (var13.isInvisible())
/* 2535:     */         {
/* 2536:3655 */           float var18 = var13.getArmorVisibility();
/* 2537:3657 */           if (var18 < 0.1F) {
/* 2538:3659 */             var18 = 0.1F;
/* 2539:     */           }
/* 2540:3662 */           var16 *= 0.7F * var18;
/* 2541:     */         }
/* 2542:3665 */         if (((par7 < 0.0D) || (var14 < var16 * var16)) && ((var9 == -1.0D) || (var14 < var9)))
/* 2543:     */         {
/* 2544:3667 */           var9 = var14;
/* 2545:3668 */           var11 = var13;
/* 2546:     */         }
/* 2547:     */       }
/* 2548:     */     }
/* 2549:3673 */     return var11;
/* 2550:     */   }
/* 2551:     */   
/* 2552:     */   public EntityPlayer getPlayerEntityByName(String par1Str)
/* 2553:     */   {
/* 2554:3681 */     for (int var2 = 0; var2 < this.playerEntities.size(); var2++) {
/* 2555:3683 */       if (par1Str.equals(((EntityPlayer)this.playerEntities.get(var2)).getCommandSenderName())) {
/* 2556:3685 */         return (EntityPlayer)this.playerEntities.get(var2);
/* 2557:     */       }
/* 2558:     */     }
/* 2559:3689 */     return null;
/* 2560:     */   }
/* 2561:     */   
/* 2562:     */   public void sendQuittingDisconnectingPacket() {}
/* 2563:     */   
/* 2564:     */   public void checkSessionLock()
/* 2565:     */     throws MinecraftException
/* 2566:     */   {
/* 2567:3702 */     this.saveHandler.checkSessionLock();
/* 2568:     */   }
/* 2569:     */   
/* 2570:     */   public void func_82738_a(long par1)
/* 2571:     */   {
/* 2572:3707 */     this.worldInfo.incrementTotalWorldTime(par1);
/* 2573:     */   }
/* 2574:     */   
/* 2575:     */   public long getSeed()
/* 2576:     */   {
/* 2577:3715 */     return this.worldInfo.getSeed();
/* 2578:     */   }
/* 2579:     */   
/* 2580:     */   public long getTotalWorldTime()
/* 2581:     */   {
/* 2582:3720 */     return this.worldInfo.getWorldTotalTime();
/* 2583:     */   }
/* 2584:     */   
/* 2585:     */   public long getWorldTime()
/* 2586:     */   {
/* 2587:3725 */     return this.worldInfo.getWorldTime();
/* 2588:     */   }
/* 2589:     */   
/* 2590:     */   public void setWorldTime(long par1)
/* 2591:     */   {
/* 2592:3733 */     this.worldInfo.setWorldTime(par1);
/* 2593:     */   }
/* 2594:     */   
/* 2595:     */   public ChunkCoordinates getSpawnPoint()
/* 2596:     */   {
/* 2597:3741 */     return new ChunkCoordinates(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
/* 2598:     */   }
/* 2599:     */   
/* 2600:     */   public void setSpawnLocation(int par1, int par2, int par3)
/* 2601:     */   {
/* 2602:3746 */     this.worldInfo.setSpawnPosition(par1, par2, par3);
/* 2603:     */   }
/* 2604:     */   
/* 2605:     */   public void joinEntityInSurroundings(Entity par1Entity)
/* 2606:     */   {
/* 2607:3754 */     int var2 = MathHelper.floor_double(par1Entity.posX / 16.0D);
/* 2608:3755 */     int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0D);
/* 2609:3756 */     byte var4 = 2;
/* 2610:3758 */     for (int var5 = var2 - var4; var5 <= var2 + var4; var5++) {
/* 2611:3760 */       for (int var6 = var3 - var4; var6 <= var3 + var4; var6++) {
/* 2612:3762 */         getChunkFromChunkCoords(var5, var6);
/* 2613:     */       }
/* 2614:     */     }
/* 2615:3766 */     if (!this.loadedEntityList.contains(par1Entity)) {
/* 2616:3768 */       this.loadedEntityList.add(par1Entity);
/* 2617:     */     }
/* 2618:     */   }
/* 2619:     */   
/* 2620:     */   public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4)
/* 2621:     */   {
/* 2622:3777 */     return true;
/* 2623:     */   }
/* 2624:     */   
/* 2625:     */   public void setEntityState(Entity par1Entity, byte par2) {}
/* 2626:     */   
/* 2627:     */   public IChunkProvider getChunkProvider()
/* 2628:     */   {
/* 2629:3790 */     return this.chunkProvider;
/* 2630:     */   }
/* 2631:     */   
/* 2632:     */   public void func_147452_c(int p_147452_1_, int p_147452_2_, int p_147452_3_, Block p_147452_4_, int p_147452_5_, int p_147452_6_)
/* 2633:     */   {
/* 2634:3795 */     p_147452_4_.onBlockEventReceived(this, p_147452_1_, p_147452_2_, p_147452_3_, p_147452_5_, p_147452_6_);
/* 2635:     */   }
/* 2636:     */   
/* 2637:     */   public ISaveHandler getSaveHandler()
/* 2638:     */   {
/* 2639:3803 */     return this.saveHandler;
/* 2640:     */   }
/* 2641:     */   
/* 2642:     */   public WorldInfo getWorldInfo()
/* 2643:     */   {
/* 2644:3811 */     return this.worldInfo;
/* 2645:     */   }
/* 2646:     */   
/* 2647:     */   public GameRules getGameRules()
/* 2648:     */   {
/* 2649:3819 */     return this.worldInfo.getGameRulesInstance();
/* 2650:     */   }
/* 2651:     */   
/* 2652:     */   public void updateAllPlayersSleepingFlag() {}
/* 2653:     */   
/* 2654:     */   public float getWeightedThunderStrength(float par1)
/* 2655:     */   {
/* 2656:3829 */     return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * par1) * getRainStrength(par1);
/* 2657:     */   }
/* 2658:     */   
/* 2659:     */   public void setThunderStrength(float p_147442_1_)
/* 2660:     */   {
/* 2661:3837 */     this.prevThunderingStrength = p_147442_1_;
/* 2662:3838 */     this.thunderingStrength = p_147442_1_;
/* 2663:     */   }
/* 2664:     */   
/* 2665:     */   public float getRainStrength(float par1)
/* 2666:     */   {
/* 2667:3846 */     return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * par1;
/* 2668:     */   }
/* 2669:     */   
/* 2670:     */   public void setRainStrength(float par1)
/* 2671:     */   {
/* 2672:3854 */     this.prevRainingStrength = par1;
/* 2673:3855 */     this.rainingStrength = par1;
/* 2674:     */   }
/* 2675:     */   
/* 2676:     */   public boolean isThundering()
/* 2677:     */   {
/* 2678:3863 */     return getWeightedThunderStrength(1.0F) > 0.9D;
/* 2679:     */   }
/* 2680:     */   
/* 2681:     */   public boolean isRaining()
/* 2682:     */   {
/* 2683:3871 */     return getRainStrength(1.0F) > 0.2D;
/* 2684:     */   }
/* 2685:     */   
/* 2686:     */   public boolean canLightningStrikeAt(int par1, int par2, int par3)
/* 2687:     */   {
/* 2688:3876 */     if (!isRaining()) {
/* 2689:3878 */       return false;
/* 2690:     */     }
/* 2691:3880 */     if (!canBlockSeeTheSky(par1, par2, par3)) {
/* 2692:3882 */       return false;
/* 2693:     */     }
/* 2694:3884 */     if (getPrecipitationHeight(par1, par3) > par2) {
/* 2695:3886 */       return false;
/* 2696:     */     }
/* 2697:3890 */     BiomeGenBase var4 = getBiomeGenForCoords(par1, par3);
/* 2698:3891 */     return func_147478_e(par1, par2, par3, false) ? false : var4.getEnableSnow() ? false : var4.canSpawnLightningBolt();
/* 2699:     */   }
/* 2700:     */   
/* 2701:     */   public boolean isBlockHighHumidity(int par1, int par2, int par3)
/* 2702:     */   {
/* 2703:3900 */     BiomeGenBase var4 = getBiomeGenForCoords(par1, par3);
/* 2704:3901 */     return var4.isHighHumidity();
/* 2705:     */   }
/* 2706:     */   
/* 2707:     */   public void setItemData(String par1Str, WorldSavedData par2WorldSavedData)
/* 2708:     */   {
/* 2709:3910 */     this.mapStorage.setData(par1Str, par2WorldSavedData);
/* 2710:     */   }
/* 2711:     */   
/* 2712:     */   public WorldSavedData loadItemData(Class par1Class, String par2Str)
/* 2713:     */   {
/* 2714:3919 */     return this.mapStorage.loadData(par1Class, par2Str);
/* 2715:     */   }
/* 2716:     */   
/* 2717:     */   public int getUniqueDataId(String par1Str)
/* 2718:     */   {
/* 2719:3928 */     return this.mapStorage.getUniqueDataId(par1Str);
/* 2720:     */   }
/* 2721:     */   
/* 2722:     */   public void playBroadcastSound(int par1, int par2, int par3, int par4, int par5)
/* 2723:     */   {
/* 2724:3933 */     for (int var6 = 0; var6 < this.worldAccesses.size(); var6++) {
/* 2725:3935 */       ((IWorldAccess)this.worldAccesses.get(var6)).broadcastSound(par1, par2, par3, par4, par5);
/* 2726:     */     }
/* 2727:     */   }
/* 2728:     */   
/* 2729:     */   public void playAuxSFX(int par1, int par2, int par3, int par4, int par5)
/* 2730:     */   {
/* 2731:3944 */     playAuxSFXAtEntity(null, par1, par2, par3, par4, par5);
/* 2732:     */   }
/* 2733:     */   
/* 2734:     */   public void playAuxSFXAtEntity(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
/* 2735:     */   {
/* 2736:     */     try
/* 2737:     */     {
/* 2738:3954 */       for (int var7 = 0; var7 < this.worldAccesses.size(); var7++) {
/* 2739:3956 */         ((IWorldAccess)this.worldAccesses.get(var7)).playAuxSFX(par1EntityPlayer, par2, par3, par4, par5, par6);
/* 2740:     */       }
/* 2741:     */     }
/* 2742:     */     catch (Throwable var10)
/* 2743:     */     {
/* 2744:3961 */       CrashReport var8 = CrashReport.makeCrashReport(var10, "Playing level event");
/* 2745:3962 */       CrashReportCategory var9 = var8.makeCategory("Level event being played");
/* 2746:3963 */       var9.addCrashSection("Block coordinates", CrashReportCategory.getLocationInfo(par3, par4, par5));
/* 2747:3964 */       var9.addCrashSection("Event source", par1EntityPlayer);
/* 2748:3965 */       var9.addCrashSection("Event type", Integer.valueOf(par2));
/* 2749:3966 */       var9.addCrashSection("Event data", Integer.valueOf(par6));
/* 2750:3967 */       throw new ReportedException(var8);
/* 2751:     */     }
/* 2752:     */   }
/* 2753:     */   
/* 2754:     */   public int getHeight()
/* 2755:     */   {
/* 2756:3976 */     return 256;
/* 2757:     */   }
/* 2758:     */   
/* 2759:     */   public int getActualHeight()
/* 2760:     */   {
/* 2761:3984 */     return this.provider.hasNoSky ? 128 : 256;
/* 2762:     */   }
/* 2763:     */   
/* 2764:     */   public Random setRandomSeed(int par1, int par2, int par3)
/* 2765:     */   {
/* 2766:3992 */     long var4 = par1 * 341873128712L + par2 * 132897987541L + getWorldInfo().getSeed() + par3;
/* 2767:3993 */     this.rand.setSeed(var4);
/* 2768:3994 */     return this.rand;
/* 2769:     */   }
/* 2770:     */   
/* 2771:     */   public ChunkPosition findClosestStructure(String p_147440_1_, int p_147440_2_, int p_147440_3_, int p_147440_4_)
/* 2772:     */   {
/* 2773:4002 */     return getChunkProvider().func_147416_a(this, p_147440_1_, p_147440_2_, p_147440_3_, p_147440_4_);
/* 2774:     */   }
/* 2775:     */   
/* 2776:     */   public boolean extendedLevelsInChunkCache()
/* 2777:     */   {
/* 2778:4010 */     return false;
/* 2779:     */   }
/* 2780:     */   
/* 2781:     */   public double getHorizon()
/* 2782:     */   {
/* 2783:4018 */     return this.worldInfo.getTerrainType() == WorldType.FLAT ? 0.0D : 63.0D;
/* 2784:     */   }
/* 2785:     */   
/* 2786:     */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport par1CrashReport)
/* 2787:     */   {
/* 2788:4026 */     CrashReportCategory var2 = par1CrashReport.makeCategoryDepth("Affected level", 1);
/* 2789:4027 */     var2.addCrashSection("Level name", this.worldInfo == null ? "????" : this.worldInfo.getWorldName());
/* 2790:4028 */     var2.addCrashSectionCallable("All players", new Callable()
/* 2791:     */     {
/* 2792:     */       private static final String __OBFID = "CL_00000143";
/* 2793:     */       
/* 2794:     */       public String call()
/* 2795:     */       {
/* 2796:4033 */         return World.this.playerEntities.size() + " total; " + World.this.playerEntities.toString();
/* 2797:     */       }
/* 2798:4035 */     });
/* 2799:4036 */     var2.addCrashSectionCallable("Chunk stats", new Callable()
/* 2800:     */     {
/* 2801:     */       private static final String __OBFID = "CL_00000144";
/* 2802:     */       
/* 2803:     */       public String call()
/* 2804:     */       {
/* 2805:4041 */         return World.this.chunkProvider.makeString();
/* 2806:     */       }
/* 2807:     */     });
/* 2808:     */     try
/* 2809:     */     {
/* 2810:4047 */       this.worldInfo.addToCrashReport(var2);
/* 2811:     */     }
/* 2812:     */     catch (Throwable var4)
/* 2813:     */     {
/* 2814:4051 */       var2.addCrashSectionThrowable("Level Data Unobtainable", var4);
/* 2815:     */     }
/* 2816:4054 */     return var2;
/* 2817:     */   }
/* 2818:     */   
/* 2819:     */   public void destroyBlockInWorldPartially(int p_147443_1_, int p_147443_2_, int p_147443_3_, int p_147443_4_, int p_147443_5_)
/* 2820:     */   {
/* 2821:4063 */     for (int var6 = 0; var6 < this.worldAccesses.size(); var6++)
/* 2822:     */     {
/* 2823:4065 */       IWorldAccess var7 = (IWorldAccess)this.worldAccesses.get(var6);
/* 2824:4066 */       var7.destroyBlockPartially(p_147443_1_, p_147443_2_, p_147443_3_, p_147443_4_, p_147443_5_);
/* 2825:     */     }
/* 2826:     */   }
/* 2827:     */   
/* 2828:     */   public Vec3Pool getWorldVec3Pool()
/* 2829:     */   {
/* 2830:4075 */     return this.vecPool;
/* 2831:     */   }
/* 2832:     */   
/* 2833:     */   public Calendar getCurrentDate()
/* 2834:     */   {
/* 2835:4083 */     if (getTotalWorldTime() % 600L == 0L) {
/* 2836:4085 */       this.theCalendar.setTimeInMillis(MinecraftServer.getSystemTimeMillis());
/* 2837:     */     }
/* 2838:4088 */     return this.theCalendar;
/* 2839:     */   }
/* 2840:     */   
/* 2841:     */   public void makeFireworks(double par1, double par3, double par5, double par7, double par9, double par11, NBTTagCompound par13NBTTagCompound) {}
/* 2842:     */   
/* 2843:     */   public Scoreboard getScoreboard()
/* 2844:     */   {
/* 2845:4095 */     return this.worldScoreboard;
/* 2846:     */   }
/* 2847:     */   
/* 2848:     */   public void func_147453_f(int p_147453_1_, int p_147453_2_, int p_147453_3_, Block p_147453_4_)
/* 2849:     */   {
/* 2850:4100 */     for (int var5 = 0; var5 < 4; var5++)
/* 2851:     */     {
/* 2852:4102 */       int var6 = p_147453_1_ + net.minecraft.util.Direction.offsetX[var5];
/* 2853:4103 */       int var7 = p_147453_3_ + net.minecraft.util.Direction.offsetZ[var5];
/* 2854:4104 */       Block var8 = getBlock(var6, p_147453_2_, var7);
/* 2855:4106 */       if (Blocks.unpowered_comparator.func_149907_e(var8))
/* 2856:     */       {
/* 2857:4108 */         var8.onNeighborBlockChange(this, var6, p_147453_2_, var7, p_147453_4_);
/* 2858:     */       }
/* 2859:4110 */       else if (var8.isNormalCube())
/* 2860:     */       {
/* 2861:4112 */         var6 += net.minecraft.util.Direction.offsetX[var5];
/* 2862:4113 */         var7 += net.minecraft.util.Direction.offsetZ[var5];
/* 2863:4114 */         Block var9 = getBlock(var6, p_147453_2_, var7);
/* 2864:4116 */         if (Blocks.unpowered_comparator.func_149907_e(var9)) {
/* 2865:4118 */           var9.onNeighborBlockChange(this, var6, p_147453_2_, var7, p_147453_4_);
/* 2866:     */         }
/* 2867:     */       }
/* 2868:     */     }
/* 2869:     */   }
/* 2870:     */   
/* 2871:     */   public float func_147462_b(double p_147462_1_, double p_147462_3_, double p_147462_5_)
/* 2872:     */   {
/* 2873:4126 */     return func_147473_B(MathHelper.floor_double(p_147462_1_), MathHelper.floor_double(p_147462_3_), MathHelper.floor_double(p_147462_5_));
/* 2874:     */   }
/* 2875:     */   
/* 2876:     */   public float func_147473_B(int p_147473_1_, int p_147473_2_, int p_147473_3_)
/* 2877:     */   {
/* 2878:4131 */     float var4 = 0.0F;
/* 2879:4132 */     boolean var5 = this.difficultySetting == EnumDifficulty.HARD;
/* 2880:4134 */     if (blockExists(p_147473_1_, p_147473_2_, p_147473_3_))
/* 2881:     */     {
/* 2882:4136 */       float var6 = getCurrentMoonPhaseFactor();
/* 2883:4137 */       var4 += MathHelper.clamp_float((float)getChunkFromBlockCoords(p_147473_1_, p_147473_3_).inhabitedTime / 3600000.0F, 0.0F, 1.0F) * (var5 ? 1.0F : 0.75F);
/* 2884:4138 */       var4 += var6 * 0.25F;
/* 2885:     */     }
/* 2886:4141 */     if ((this.difficultySetting == EnumDifficulty.EASY) || (this.difficultySetting == EnumDifficulty.PEACEFUL)) {
/* 2887:4143 */       var4 *= this.difficultySetting.getDifficultyId() / 2.0F;
/* 2888:     */     }
/* 2889:4146 */     return MathHelper.clamp_float(var4, 0.0F, var5 ? 1.5F : 1.0F);
/* 2890:     */   }
/* 2891:     */   
/* 2892:     */   public void func_147450_X()
/* 2893:     */   {
/* 2894:4151 */     Iterator var1 = this.worldAccesses.iterator();
/* 2895:4153 */     while (var1.hasNext())
/* 2896:     */     {
/* 2897:4155 */       IWorldAccess var2 = (IWorldAccess)var1.next();
/* 2898:4156 */       var2.onStaticEntitiesChanged();
/* 2899:     */     }
/* 2900:     */   }
/* 2901:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.World
 * JD-Core Version:    0.7.0.1
 */