/*    1:     */ package net.minecraft.world;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.HashSet;
/*    5:     */ import java.util.Iterator;
/*    6:     */ import java.util.List;
/*    7:     */ import java.util.Map;
/*    8:     */ import java.util.Random;
/*    9:     */ import java.util.Set;
/*   10:     */ import java.util.TreeSet;
/*   11:     */ import net.minecraft.block.Block;
/*   12:     */ import net.minecraft.block.BlockEventData;
/*   13:     */ import net.minecraft.block.material.Material;
/*   14:     */ import net.minecraft.crash.CrashReport;
/*   15:     */ import net.minecraft.crash.CrashReportCategory;
/*   16:     */ import net.minecraft.entity.Entity;
/*   17:     */ import net.minecraft.entity.EntityTracker;
/*   18:     */ import net.minecraft.entity.EnumCreatureType;
/*   19:     */ import net.minecraft.entity.INpc;
/*   20:     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*   21:     */ import net.minecraft.entity.passive.EntityAnimal;
/*   22:     */ import net.minecraft.entity.passive.EntityWaterMob;
/*   23:     */ import net.minecraft.entity.player.EntityPlayer;
/*   24:     */ import net.minecraft.entity.player.EntityPlayerMP;
/*   25:     */ import net.minecraft.init.Blocks;
/*   26:     */ import net.minecraft.init.Items;
/*   27:     */ import net.minecraft.item.Item;
/*   28:     */ import net.minecraft.network.NetHandlerPlayServer;
/*   29:     */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*   30:     */ import net.minecraft.network.play.server.S24PacketBlockAction;
/*   31:     */ import net.minecraft.network.play.server.S27PacketExplosion;
/*   32:     */ import net.minecraft.network.play.server.S2APacketParticles;
/*   33:     */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*   34:     */ import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
/*   35:     */ import net.minecraft.profiler.Profiler;
/*   36:     */ import net.minecraft.scoreboard.ScoreboardSaveData;
/*   37:     */ import net.minecraft.scoreboard.ServerScoreboard;
/*   38:     */ import net.minecraft.server.MinecraftServer;
/*   39:     */ import net.minecraft.server.management.PlayerManager;
/*   40:     */ import net.minecraft.server.management.ServerConfigurationManager;
/*   41:     */ import net.minecraft.tileentity.TileEntity;
/*   42:     */ import net.minecraft.util.ChunkCoordinates;
/*   43:     */ import net.minecraft.util.IProgressUpdate;
/*   44:     */ import net.minecraft.util.IntHashMap;
/*   45:     */ import net.minecraft.util.ReportedException;
/*   46:     */ import net.minecraft.util.Vec3;
/*   47:     */ import net.minecraft.util.WeightedRandom;
/*   48:     */ import net.minecraft.util.WeightedRandomChestContent;
/*   49:     */ import net.minecraft.village.VillageCollection;
/*   50:     */ import net.minecraft.village.VillageSiege;
/*   51:     */ import net.minecraft.world.biome.BiomeGenBase;
/*   52:     */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/*   53:     */ import net.minecraft.world.biome.WorldChunkManager;
/*   54:     */ import net.minecraft.world.chunk.Chunk;
/*   55:     */ import net.minecraft.world.chunk.IChunkProvider;
/*   56:     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*   57:     */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*   58:     */ import net.minecraft.world.gen.ChunkProviderServer;
/*   59:     */ import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
/*   60:     */ import net.minecraft.world.storage.ISaveHandler;
/*   61:     */ import net.minecraft.world.storage.MapStorage;
/*   62:     */ import net.minecraft.world.storage.WorldInfo;
/*   63:     */ import org.apache.logging.log4j.LogManager;
/*   64:     */ import org.apache.logging.log4j.Logger;
/*   65:     */ 
/*   66:     */ public class WorldServer
/*   67:     */   extends World
/*   68:     */ {
/*   69:  60 */   private static final Logger logger = ;
/*   70:     */   private final MinecraftServer mcServer;
/*   71:     */   private final EntityTracker theEntityTracker;
/*   72:     */   private final PlayerManager thePlayerManager;
/*   73:     */   private Set pendingTickListEntriesHashSet;
/*   74:     */   private TreeSet pendingTickListEntriesTreeSet;
/*   75:     */   public ChunkProviderServer theChunkProviderServer;
/*   76:     */   public boolean levelSaving;
/*   77:     */   private boolean allPlayersSleeping;
/*   78:     */   private int updateEntityTick;
/*   79:     */   private final Teleporter worldTeleporter;
/*   80:  81 */   private final SpawnerAnimals animalSpawner = new SpawnerAnimals();
/*   81:  82 */   private ServerBlockEventList[] field_147490_S = { new ServerBlockEventList(null), new ServerBlockEventList(null) };
/*   82:     */   private int field_147489_T;
/*   83:  84 */   private static final WeightedRandomChestContent[] bonusChestContent = { new WeightedRandomChestContent(Items.stick, 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), 0, 1, 3, 10), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), 0, 1, 3, 10), new WeightedRandomChestContent(Items.stone_axe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_axe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.stone_pickaxe, 0, 1, 1, 3), new WeightedRandomChestContent(Items.wooden_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.apple, 0, 2, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 2, 3, 3), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), 0, 1, 3, 10) };
/*   84:  85 */   private List pendingTickListEntriesThisTick = new ArrayList();
/*   85:     */   private IntHashMap entityIdMap;
/*   86:     */   private static final String __OBFID = "CL_00001437";
/*   87:     */   
/*   88:     */   public WorldServer(MinecraftServer p_i45284_1_, ISaveHandler p_i45284_2_, String p_i45284_3_, int p_i45284_4_, WorldSettings p_i45284_5_, Profiler p_i45284_6_)
/*   89:     */   {
/*   90:  93 */     super(p_i45284_2_, p_i45284_3_, p_i45284_5_, WorldProvider.getProviderForDimension(p_i45284_4_), p_i45284_6_);
/*   91:  94 */     this.mcServer = p_i45284_1_;
/*   92:  95 */     this.theEntityTracker = new EntityTracker(this);
/*   93:  96 */     this.thePlayerManager = new PlayerManager(this, p_i45284_1_.getConfigurationManager().getViewDistance());
/*   94:  98 */     if (this.entityIdMap == null) {
/*   95: 100 */       this.entityIdMap = new IntHashMap();
/*   96:     */     }
/*   97: 103 */     if (this.pendingTickListEntriesHashSet == null) {
/*   98: 105 */       this.pendingTickListEntriesHashSet = new HashSet();
/*   99:     */     }
/*  100: 108 */     if (this.pendingTickListEntriesTreeSet == null) {
/*  101: 110 */       this.pendingTickListEntriesTreeSet = new TreeSet();
/*  102:     */     }
/*  103: 113 */     this.worldTeleporter = new Teleporter(this);
/*  104: 114 */     this.worldScoreboard = new ServerScoreboard(p_i45284_1_);
/*  105: 115 */     ScoreboardSaveData var7 = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
/*  106: 117 */     if (var7 == null)
/*  107:     */     {
/*  108: 119 */       var7 = new ScoreboardSaveData();
/*  109: 120 */       this.mapStorage.setData("scoreboard", var7);
/*  110:     */     }
/*  111: 123 */     var7.func_96499_a(this.worldScoreboard);
/*  112: 124 */     ((ServerScoreboard)this.worldScoreboard).func_96547_a(var7);
/*  113:     */   }
/*  114:     */   
/*  115:     */   public void tick()
/*  116:     */   {
/*  117: 132 */     super.tick();
/*  118: 134 */     if ((getWorldInfo().isHardcoreModeEnabled()) && (this.difficultySetting != EnumDifficulty.HARD)) {
/*  119: 136 */       this.difficultySetting = EnumDifficulty.HARD;
/*  120:     */     }
/*  121: 139 */     this.provider.worldChunkMgr.cleanupCache();
/*  122: 141 */     if (areAllPlayersAsleep())
/*  123:     */     {
/*  124: 143 */       if (getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
/*  125:     */       {
/*  126: 145 */         long var1 = this.worldInfo.getWorldTime() + 24000L;
/*  127: 146 */         this.worldInfo.setWorldTime(var1 - var1 % 24000L);
/*  128:     */       }
/*  129: 149 */       wakeAllPlayers();
/*  130:     */     }
/*  131: 152 */     this.theProfiler.startSection("mobSpawner");
/*  132: 154 */     if (getGameRules().getGameRuleBooleanValue("doMobSpawning")) {
/*  133: 156 */       this.animalSpawner.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400L == 0L);
/*  134:     */     }
/*  135: 159 */     this.theProfiler.endStartSection("chunkSource");
/*  136: 160 */     this.chunkProvider.unloadQueuedChunks();
/*  137: 161 */     int var3 = calculateSkylightSubtracted(1.0F);
/*  138: 163 */     if (var3 != this.skylightSubtracted) {
/*  139: 165 */       this.skylightSubtracted = var3;
/*  140:     */     }
/*  141: 168 */     this.worldInfo.incrementTotalWorldTime(this.worldInfo.getWorldTotalTime() + 1L);
/*  142: 170 */     if (getGameRules().getGameRuleBooleanValue("doDaylightCycle")) {
/*  143: 172 */       this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
/*  144:     */     }
/*  145: 175 */     this.theProfiler.endStartSection("tickPending");
/*  146: 176 */     tickUpdates(false);
/*  147: 177 */     this.theProfiler.endStartSection("tickBlocks");
/*  148: 178 */     func_147456_g();
/*  149: 179 */     this.theProfiler.endStartSection("chunkMap");
/*  150: 180 */     this.thePlayerManager.updatePlayerInstances();
/*  151: 181 */     this.theProfiler.endStartSection("village");
/*  152: 182 */     this.villageCollectionObj.tick();
/*  153: 183 */     this.villageSiegeObj.tick();
/*  154: 184 */     this.theProfiler.endStartSection("portalForcer");
/*  155: 185 */     this.worldTeleporter.removeStalePortalLocations(getTotalWorldTime());
/*  156: 186 */     this.theProfiler.endSection();
/*  157: 187 */     func_147488_Z();
/*  158:     */   }
/*  159:     */   
/*  160:     */   public BiomeGenBase.SpawnListEntry spawnRandomCreature(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
/*  161:     */   {
/*  162: 195 */     List var5 = getChunkProvider().getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
/*  163: 196 */     return (var5 != null) && (!var5.isEmpty()) ? (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, var5) : null;
/*  164:     */   }
/*  165:     */   
/*  166:     */   public void updateAllPlayersSleepingFlag()
/*  167:     */   {
/*  168: 204 */     this.allPlayersSleeping = (!this.playerEntities.isEmpty());
/*  169: 205 */     Iterator var1 = this.playerEntities.iterator();
/*  170: 207 */     while (var1.hasNext())
/*  171:     */     {
/*  172: 209 */       EntityPlayer var2 = (EntityPlayer)var1.next();
/*  173: 211 */       if (!var2.isPlayerSleeping())
/*  174:     */       {
/*  175: 213 */         this.allPlayersSleeping = false;
/*  176: 214 */         break;
/*  177:     */       }
/*  178:     */     }
/*  179:     */   }
/*  180:     */   
/*  181:     */   protected void wakeAllPlayers()
/*  182:     */   {
/*  183: 221 */     this.allPlayersSleeping = false;
/*  184: 222 */     Iterator var1 = this.playerEntities.iterator();
/*  185: 224 */     while (var1.hasNext())
/*  186:     */     {
/*  187: 226 */       EntityPlayer var2 = (EntityPlayer)var1.next();
/*  188: 228 */       if (var2.isPlayerSleeping()) {
/*  189: 230 */         var2.wakeUpPlayer(false, false, true);
/*  190:     */       }
/*  191:     */     }
/*  192: 234 */     resetRainAndThunder();
/*  193:     */   }
/*  194:     */   
/*  195:     */   private void resetRainAndThunder()
/*  196:     */   {
/*  197: 239 */     this.worldInfo.setRainTime(0);
/*  198: 240 */     this.worldInfo.setRaining(false);
/*  199: 241 */     this.worldInfo.setThunderTime(0);
/*  200: 242 */     this.worldInfo.setThundering(false);
/*  201:     */   }
/*  202:     */   
/*  203:     */   public boolean areAllPlayersAsleep()
/*  204:     */   {
/*  205: 247 */     if ((this.allPlayersSleeping) && (!this.isClient))
/*  206:     */     {
/*  207: 249 */       Iterator var1 = this.playerEntities.iterator();
/*  208:     */       EntityPlayer var2;
/*  209:     */       do
/*  210:     */       {
/*  211: 254 */         if (!var1.hasNext()) {
/*  212: 256 */           return true;
/*  213:     */         }
/*  214: 259 */         var2 = (EntityPlayer)var1.next();
/*  215: 261 */       } while (var2.isPlayerFullyAsleep());
/*  216: 263 */       return false;
/*  217:     */     }
/*  218: 267 */     return false;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public void setSpawnLocation()
/*  222:     */   {
/*  223: 276 */     if (this.worldInfo.getSpawnY() <= 0) {
/*  224: 278 */       this.worldInfo.setSpawnY(64);
/*  225:     */     }
/*  226: 281 */     int var1 = this.worldInfo.getSpawnX();
/*  227: 282 */     int var2 = this.worldInfo.getSpawnZ();
/*  228: 283 */     int var3 = 0;
/*  229: 285 */     while (getTopBlock(var1, var2).getMaterial() == Material.air)
/*  230:     */     {
/*  231: 287 */       var1 += this.rand.nextInt(8) - this.rand.nextInt(8);
/*  232: 288 */       var2 += this.rand.nextInt(8) - this.rand.nextInt(8);
/*  233: 289 */       var3++;
/*  234: 291 */       if (var3 == 10000) {
/*  235:     */         break;
/*  236:     */       }
/*  237:     */     }
/*  238: 297 */     this.worldInfo.setSpawnX(var1);
/*  239: 298 */     this.worldInfo.setSpawnZ(var2);
/*  240:     */   }
/*  241:     */   
/*  242:     */   protected void func_147456_g()
/*  243:     */   {
/*  244: 303 */     super.func_147456_g();
/*  245: 304 */     int var1 = 0;
/*  246: 305 */     int var2 = 0;
/*  247: 306 */     Iterator var3 = this.activeChunkSet.iterator();
/*  248: 308 */     while (var3.hasNext())
/*  249:     */     {
/*  250: 310 */       ChunkCoordIntPair var4 = (ChunkCoordIntPair)var3.next();
/*  251: 311 */       int var5 = var4.chunkXPos * 16;
/*  252: 312 */       int var6 = var4.chunkZPos * 16;
/*  253: 313 */       this.theProfiler.startSection("getChunk");
/*  254: 314 */       Chunk var7 = getChunkFromChunkCoords(var4.chunkXPos, var4.chunkZPos);
/*  255: 315 */       func_147467_a(var5, var6, var7);
/*  256: 316 */       this.theProfiler.endStartSection("tickChunk");
/*  257: 317 */       var7.func_150804_b(false);
/*  258: 318 */       this.theProfiler.endStartSection("thunder");
/*  259: 324 */       if ((this.rand.nextInt(100000) == 0) && (isRaining()) && (isThundering()))
/*  260:     */       {
/*  261: 326 */         this.updateLCG = (this.updateLCG * 3 + 1013904223);
/*  262: 327 */         int var8 = this.updateLCG >> 2;
/*  263: 328 */         int var9 = var5 + (var8 & 0xF);
/*  264: 329 */         int var10 = var6 + (var8 >> 8 & 0xF);
/*  265: 330 */         int var11 = getPrecipitationHeight(var9, var10);
/*  266: 332 */         if (canLightningStrikeAt(var9, var11, var10)) {
/*  267: 334 */           addWeatherEffect(new EntityLightningBolt(this, var9, var11, var10));
/*  268:     */         }
/*  269:     */       }
/*  270: 338 */       this.theProfiler.endStartSection("iceandsnow");
/*  271: 340 */       if (this.rand.nextInt(16) == 0)
/*  272:     */       {
/*  273: 342 */         this.updateLCG = (this.updateLCG * 3 + 1013904223);
/*  274: 343 */         int var8 = this.updateLCG >> 2;
/*  275: 344 */         int var9 = var8 & 0xF;
/*  276: 345 */         int var10 = var8 >> 8 & 0xF;
/*  277: 346 */         int var11 = getPrecipitationHeight(var9 + var5, var10 + var6);
/*  278: 348 */         if (isBlockFreezableNaturally(var9 + var5, var11 - 1, var10 + var6)) {
/*  279: 350 */           setBlock(var9 + var5, var11 - 1, var10 + var6, Blocks.ice);
/*  280:     */         }
/*  281: 353 */         if ((isRaining()) && (func_147478_e(var9 + var5, var11, var10 + var6, true))) {
/*  282: 355 */           setBlock(var9 + var5, var11, var10 + var6, Blocks.snow_layer);
/*  283:     */         }
/*  284: 358 */         if (isRaining())
/*  285:     */         {
/*  286: 360 */           BiomeGenBase var12 = getBiomeGenForCoords(var9 + var5, var10 + var6);
/*  287: 362 */           if (var12.canSpawnLightningBolt()) {
/*  288: 364 */             getBlock(var9 + var5, var11 - 1, var10 + var6).fillWithRain(this, var9 + var5, var11 - 1, var10 + var6);
/*  289:     */           }
/*  290:     */         }
/*  291:     */       }
/*  292: 369 */       this.theProfiler.endStartSection("tickBlocks");
/*  293: 370 */       ExtendedBlockStorage[] var18 = var7.getBlockStorageArray();
/*  294: 371 */       int var9 = var18.length;
/*  295: 373 */       for (int var10 = 0; var10 < var9; var10++)
/*  296:     */       {
/*  297: 375 */         ExtendedBlockStorage var20 = var18[var10];
/*  298: 377 */         if ((var20 != null) && (var20.getNeedsRandomTick())) {
/*  299: 379 */           for (int var19 = 0; var19 < 3; var19++)
/*  300:     */           {
/*  301: 381 */             this.updateLCG = (this.updateLCG * 3 + 1013904223);
/*  302: 382 */             int var13 = this.updateLCG >> 2;
/*  303: 383 */             int var14 = var13 & 0xF;
/*  304: 384 */             int var15 = var13 >> 8 & 0xF;
/*  305: 385 */             int var16 = var13 >> 16 & 0xF;
/*  306: 386 */             var2++;
/*  307: 387 */             Block var17 = var20.func_150819_a(var14, var16, var15);
/*  308: 389 */             if (var17.getTickRandomly())
/*  309:     */             {
/*  310: 391 */               var1++;
/*  311: 392 */               var17.updateTick(this, var14 + var5, var16 + var20.getYLocation(), var15 + var6, this.rand);
/*  312:     */             }
/*  313:     */           }
/*  314:     */         }
/*  315:     */       }
/*  316: 398 */       this.theProfiler.endSection();
/*  317:     */     }
/*  318:     */   }
/*  319:     */   
/*  320:     */   public boolean func_147477_a(int p_147477_1_, int p_147477_2_, int p_147477_3_, Block p_147477_4_)
/*  321:     */   {
/*  322: 404 */     NextTickListEntry var5 = new NextTickListEntry(p_147477_1_, p_147477_2_, p_147477_3_, p_147477_4_);
/*  323: 405 */     return this.pendingTickListEntriesThisTick.contains(var5);
/*  324:     */   }
/*  325:     */   
/*  326:     */   public void scheduleBlockUpdate(int p_147464_1_, int p_147464_2_, int p_147464_3_, Block p_147464_4_, int p_147464_5_)
/*  327:     */   {
/*  328: 413 */     func_147454_a(p_147464_1_, p_147464_2_, p_147464_3_, p_147464_4_, p_147464_5_, 0);
/*  329:     */   }
/*  330:     */   
/*  331:     */   public void func_147454_a(int p_147454_1_, int p_147454_2_, int p_147454_3_, Block p_147454_4_, int p_147454_5_, int p_147454_6_)
/*  332:     */   {
/*  333: 418 */     NextTickListEntry var7 = new NextTickListEntry(p_147454_1_, p_147454_2_, p_147454_3_, p_147454_4_);
/*  334: 419 */     byte var8 = 0;
/*  335: 421 */     if ((this.scheduledUpdatesAreImmediate) && (p_147454_4_.getMaterial() != Material.air))
/*  336:     */     {
/*  337: 423 */       if (p_147454_4_.func_149698_L())
/*  338:     */       {
/*  339: 425 */         var8 = 8;
/*  340: 427 */         if (checkChunksExist(var7.xCoord - var8, var7.yCoord - var8, var7.zCoord - var8, var7.xCoord + var8, var7.yCoord + var8, var7.zCoord + var8))
/*  341:     */         {
/*  342: 429 */           Block var9 = getBlock(var7.xCoord, var7.yCoord, var7.zCoord);
/*  343: 431 */           if ((var9.getMaterial() != Material.air) && (var9 == var7.func_151351_a())) {
/*  344: 433 */             var9.updateTick(this, var7.xCoord, var7.yCoord, var7.zCoord, this.rand);
/*  345:     */           }
/*  346:     */         }
/*  347: 437 */         return;
/*  348:     */       }
/*  349: 440 */       p_147454_5_ = 1;
/*  350:     */     }
/*  351: 443 */     if (checkChunksExist(p_147454_1_ - var8, p_147454_2_ - var8, p_147454_3_ - var8, p_147454_1_ + var8, p_147454_2_ + var8, p_147454_3_ + var8))
/*  352:     */     {
/*  353: 445 */       if (p_147454_4_.getMaterial() != Material.air)
/*  354:     */       {
/*  355: 447 */         var7.setScheduledTime(p_147454_5_ + this.worldInfo.getWorldTotalTime());
/*  356: 448 */         var7.setPriority(p_147454_6_);
/*  357:     */       }
/*  358: 451 */       if (!this.pendingTickListEntriesHashSet.contains(var7))
/*  359:     */       {
/*  360: 453 */         this.pendingTickListEntriesHashSet.add(var7);
/*  361: 454 */         this.pendingTickListEntriesTreeSet.add(var7);
/*  362:     */       }
/*  363:     */     }
/*  364:     */   }
/*  365:     */   
/*  366:     */   public void func_147446_b(int p_147446_1_, int p_147446_2_, int p_147446_3_, Block p_147446_4_, int p_147446_5_, int p_147446_6_)
/*  367:     */   {
/*  368: 461 */     NextTickListEntry var7 = new NextTickListEntry(p_147446_1_, p_147446_2_, p_147446_3_, p_147446_4_);
/*  369: 462 */     var7.setPriority(p_147446_6_);
/*  370: 464 */     if (p_147446_4_.getMaterial() != Material.air) {
/*  371: 466 */       var7.setScheduledTime(p_147446_5_ + this.worldInfo.getWorldTotalTime());
/*  372:     */     }
/*  373: 469 */     if (!this.pendingTickListEntriesHashSet.contains(var7))
/*  374:     */     {
/*  375: 471 */       this.pendingTickListEntriesHashSet.add(var7);
/*  376: 472 */       this.pendingTickListEntriesTreeSet.add(var7);
/*  377:     */     }
/*  378:     */   }
/*  379:     */   
/*  380:     */   public void updateEntities()
/*  381:     */   {
/*  382: 481 */     if (this.playerEntities.isEmpty())
/*  383:     */     {
/*  384: 483 */       if (this.updateEntityTick++ < 1200) {}
/*  385:     */     }
/*  386:     */     else {
/*  387: 490 */       resetUpdateEntityTick();
/*  388:     */     }
/*  389: 493 */     super.updateEntities();
/*  390:     */   }
/*  391:     */   
/*  392:     */   public void resetUpdateEntityTick()
/*  393:     */   {
/*  394: 501 */     this.updateEntityTick = 0;
/*  395:     */   }
/*  396:     */   
/*  397:     */   public boolean tickUpdates(boolean par1)
/*  398:     */   {
/*  399: 509 */     int var2 = this.pendingTickListEntriesTreeSet.size();
/*  400: 511 */     if (var2 != this.pendingTickListEntriesHashSet.size()) {
/*  401: 513 */       throw new IllegalStateException("TickNextTick list out of synch");
/*  402:     */     }
/*  403: 517 */     if (var2 > 1000) {
/*  404: 519 */       var2 = 1000;
/*  405:     */     }
/*  406: 522 */     this.theProfiler.startSection("cleaning");
/*  407: 525 */     for (int var3 = 0; var3 < var2; var3++)
/*  408:     */     {
/*  409: 527 */       NextTickListEntry var4 = (NextTickListEntry)this.pendingTickListEntriesTreeSet.first();
/*  410: 529 */       if ((!par1) && (var4.scheduledTime > this.worldInfo.getWorldTotalTime())) {
/*  411:     */         break;
/*  412:     */       }
/*  413: 534 */       this.pendingTickListEntriesTreeSet.remove(var4);
/*  414: 535 */       this.pendingTickListEntriesHashSet.remove(var4);
/*  415: 536 */       this.pendingTickListEntriesThisTick.add(var4);
/*  416:     */     }
/*  417: 539 */     this.theProfiler.endSection();
/*  418: 540 */     this.theProfiler.startSection("ticking");
/*  419: 541 */     Iterator var14 = this.pendingTickListEntriesThisTick.iterator();
/*  420: 543 */     while (var14.hasNext())
/*  421:     */     {
/*  422: 545 */       NextTickListEntry var4 = (NextTickListEntry)var14.next();
/*  423: 546 */       var14.remove();
/*  424: 547 */       byte var5 = 0;
/*  425: 549 */       if (checkChunksExist(var4.xCoord - var5, var4.yCoord - var5, var4.zCoord - var5, var4.xCoord + var5, var4.yCoord + var5, var4.zCoord + var5))
/*  426:     */       {
/*  427: 551 */         Block var6 = getBlock(var4.xCoord, var4.yCoord, var4.zCoord);
/*  428: 553 */         if ((var6.getMaterial() != Material.air) && (Block.isEqualTo(var6, var4.func_151351_a()))) {
/*  429:     */           try
/*  430:     */           {
/*  431: 557 */             var6.updateTick(this, var4.xCoord, var4.yCoord, var4.zCoord, this.rand);
/*  432:     */           }
/*  433:     */           catch (Throwable var13)
/*  434:     */           {
/*  435: 561 */             CrashReport var8 = CrashReport.makeCrashReport(var13, "Exception while ticking a block");
/*  436: 562 */             CrashReportCategory var9 = var8.makeCategory("Block being ticked");
/*  437:     */             int var10;
/*  438:     */             try
/*  439:     */             {
/*  440: 567 */               var10 = getBlockMetadata(var4.xCoord, var4.yCoord, var4.zCoord);
/*  441:     */             }
/*  442:     */             catch (Throwable var12)
/*  443:     */             {
/*  444:     */               int var10;
/*  445: 571 */               var10 = -1;
/*  446:     */             }
/*  447: 574 */             CrashReportCategory.func_147153_a(var9, var4.xCoord, var4.yCoord, var4.zCoord, var6, var10);
/*  448: 575 */             throw new ReportedException(var8);
/*  449:     */           }
/*  450:     */         }
/*  451:     */       }
/*  452:     */       else
/*  453:     */       {
/*  454: 581 */         scheduleBlockUpdate(var4.xCoord, var4.yCoord, var4.zCoord, var4.func_151351_a(), 0);
/*  455:     */       }
/*  456:     */     }
/*  457: 585 */     this.theProfiler.endSection();
/*  458: 586 */     this.pendingTickListEntriesThisTick.clear();
/*  459: 587 */     return !this.pendingTickListEntriesTreeSet.isEmpty();
/*  460:     */   }
/*  461:     */   
/*  462:     */   public List getPendingBlockUpdates(Chunk par1Chunk, boolean par2)
/*  463:     */   {
/*  464: 593 */     ArrayList var3 = null;
/*  465: 594 */     ChunkCoordIntPair var4 = par1Chunk.getChunkCoordIntPair();
/*  466: 595 */     int var5 = (var4.chunkXPos << 4) - 2;
/*  467: 596 */     int var6 = var5 + 16 + 2;
/*  468: 597 */     int var7 = (var4.chunkZPos << 4) - 2;
/*  469: 598 */     int var8 = var7 + 16 + 2;
/*  470: 600 */     for (int var9 = 0; var9 < 2; var9++)
/*  471:     */     {
/*  472:     */       Iterator var10;
/*  473:     */       Iterator var10;
/*  474: 604 */       if (var9 == 0)
/*  475:     */       {
/*  476: 606 */         var10 = this.pendingTickListEntriesTreeSet.iterator();
/*  477:     */       }
/*  478:     */       else
/*  479:     */       {
/*  480: 610 */         var10 = this.pendingTickListEntriesThisTick.iterator();
/*  481: 612 */         if (!this.pendingTickListEntriesThisTick.isEmpty()) {
/*  482: 614 */           logger.debug("toBeTicked = " + this.pendingTickListEntriesThisTick.size());
/*  483:     */         }
/*  484:     */       }
/*  485: 618 */       while (var10.hasNext())
/*  486:     */       {
/*  487: 620 */         NextTickListEntry var11 = (NextTickListEntry)var10.next();
/*  488: 622 */         if ((var11.xCoord >= var5) && (var11.xCoord < var6) && (var11.zCoord >= var7) && (var11.zCoord < var8))
/*  489:     */         {
/*  490: 624 */           if (par2)
/*  491:     */           {
/*  492: 626 */             this.pendingTickListEntriesHashSet.remove(var11);
/*  493: 627 */             var10.remove();
/*  494:     */           }
/*  495: 630 */           if (var3 == null) {
/*  496: 632 */             var3 = new ArrayList();
/*  497:     */           }
/*  498: 635 */           var3.add(var11);
/*  499:     */         }
/*  500:     */       }
/*  501:     */     }
/*  502: 640 */     return var3;
/*  503:     */   }
/*  504:     */   
/*  505:     */   public void updateEntityWithOptionalForce(Entity par1Entity, boolean par2)
/*  506:     */   {
/*  507: 649 */     if ((!this.mcServer.getCanSpawnAnimals()) && (((par1Entity instanceof EntityAnimal)) || ((par1Entity instanceof EntityWaterMob)))) {
/*  508: 651 */       par1Entity.setDead();
/*  509:     */     }
/*  510: 654 */     if ((!this.mcServer.getCanSpawnNPCs()) && ((par1Entity instanceof INpc))) {
/*  511: 656 */       par1Entity.setDead();
/*  512:     */     }
/*  513: 659 */     super.updateEntityWithOptionalForce(par1Entity, par2);
/*  514:     */   }
/*  515:     */   
/*  516:     */   protected IChunkProvider createChunkProvider()
/*  517:     */   {
/*  518: 667 */     IChunkLoader var1 = this.saveHandler.getChunkLoader(this.provider);
/*  519: 668 */     this.theChunkProviderServer = new ChunkProviderServer(this, var1, this.provider.createChunkGenerator());
/*  520: 669 */     return this.theChunkProviderServer;
/*  521:     */   }
/*  522:     */   
/*  523:     */   public List func_147486_a(int p_147486_1_, int p_147486_2_, int p_147486_3_, int p_147486_4_, int p_147486_5_, int p_147486_6_)
/*  524:     */   {
/*  525: 674 */     ArrayList var7 = new ArrayList();
/*  526: 676 */     for (int var8 = 0; var8 < this.field_147482_g.size(); var8++)
/*  527:     */     {
/*  528: 678 */       TileEntity var9 = (TileEntity)this.field_147482_g.get(var8);
/*  529: 680 */       if ((var9.field_145851_c >= p_147486_1_) && (var9.field_145848_d >= p_147486_2_) && (var9.field_145849_e >= p_147486_3_) && (var9.field_145851_c < p_147486_4_) && (var9.field_145848_d < p_147486_5_) && (var9.field_145849_e < p_147486_6_)) {
/*  530: 682 */         var7.add(var9);
/*  531:     */       }
/*  532:     */     }
/*  533: 686 */     return var7;
/*  534:     */   }
/*  535:     */   
/*  536:     */   public boolean canMineBlock(EntityPlayer par1EntityPlayer, int par2, int par3, int par4)
/*  537:     */   {
/*  538: 694 */     return !this.mcServer.isBlockProtected(this, par2, par3, par4, par1EntityPlayer);
/*  539:     */   }
/*  540:     */   
/*  541:     */   protected void initialize(WorldSettings par1WorldSettings)
/*  542:     */   {
/*  543: 699 */     if (this.entityIdMap == null) {
/*  544: 701 */       this.entityIdMap = new IntHashMap();
/*  545:     */     }
/*  546: 704 */     if (this.pendingTickListEntriesHashSet == null) {
/*  547: 706 */       this.pendingTickListEntriesHashSet = new HashSet();
/*  548:     */     }
/*  549: 709 */     if (this.pendingTickListEntriesTreeSet == null) {
/*  550: 711 */       this.pendingTickListEntriesTreeSet = new TreeSet();
/*  551:     */     }
/*  552: 714 */     createSpawnPosition(par1WorldSettings);
/*  553: 715 */     super.initialize(par1WorldSettings);
/*  554:     */   }
/*  555:     */   
/*  556:     */   protected void createSpawnPosition(WorldSettings par1WorldSettings)
/*  557:     */   {
/*  558: 723 */     if (!this.provider.canRespawnHere())
/*  559:     */     {
/*  560: 725 */       this.worldInfo.setSpawnPosition(0, this.provider.getAverageGroundLevel(), 0);
/*  561:     */     }
/*  562:     */     else
/*  563:     */     {
/*  564: 729 */       this.findingSpawnPoint = true;
/*  565: 730 */       WorldChunkManager var2 = this.provider.worldChunkMgr;
/*  566: 731 */       List var3 = var2.getBiomesToSpawnIn();
/*  567: 732 */       Random var4 = new Random(getSeed());
/*  568: 733 */       ChunkPosition var5 = var2.func_150795_a(0, 0, 256, var3, var4);
/*  569: 734 */       int var6 = 0;
/*  570: 735 */       int var7 = this.provider.getAverageGroundLevel();
/*  571: 736 */       int var8 = 0;
/*  572: 738 */       if (var5 != null)
/*  573:     */       {
/*  574: 740 */         var6 = var5.field_151329_a;
/*  575: 741 */         var8 = var5.field_151328_c;
/*  576:     */       }
/*  577:     */       else
/*  578:     */       {
/*  579: 745 */         logger.warn("Unable to find spawn biome");
/*  580:     */       }
/*  581: 748 */       int var9 = 0;
/*  582: 750 */       while (!this.provider.canCoordinateBeSpawn(var6, var8))
/*  583:     */       {
/*  584: 752 */         var6 += var4.nextInt(64) - var4.nextInt(64);
/*  585: 753 */         var8 += var4.nextInt(64) - var4.nextInt(64);
/*  586: 754 */         var9++;
/*  587: 756 */         if (var9 == 1000) {
/*  588:     */           break;
/*  589:     */         }
/*  590:     */       }
/*  591: 762 */       this.worldInfo.setSpawnPosition(var6, var7, var8);
/*  592: 763 */       this.findingSpawnPoint = false;
/*  593: 765 */       if (par1WorldSettings.isBonusChestEnabled()) {
/*  594: 767 */         createBonusChest();
/*  595:     */       }
/*  596:     */     }
/*  597:     */   }
/*  598:     */   
/*  599:     */   protected void createBonusChest()
/*  600:     */   {
/*  601: 777 */     WorldGeneratorBonusChest var1 = new WorldGeneratorBonusChest(bonusChestContent, 10);
/*  602: 779 */     for (int var2 = 0; var2 < 10; var2++)
/*  603:     */     {
/*  604: 781 */       int var3 = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
/*  605: 782 */       int var4 = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
/*  606: 783 */       int var5 = getTopSolidOrLiquidBlock(var3, var4) + 1;
/*  607: 785 */       if (var1.generate(this, this.rand, var3, var5, var4)) {
/*  608:     */         break;
/*  609:     */       }
/*  610:     */     }
/*  611:     */   }
/*  612:     */   
/*  613:     */   public ChunkCoordinates getEntrancePortalLocation()
/*  614:     */   {
/*  615: 797 */     return this.provider.getEntrancePortalLocation();
/*  616:     */   }
/*  617:     */   
/*  618:     */   public void saveAllChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
/*  619:     */     throws MinecraftException
/*  620:     */   {
/*  621: 805 */     if (this.chunkProvider.canSave())
/*  622:     */     {
/*  623: 807 */       if (par2IProgressUpdate != null) {
/*  624: 809 */         par2IProgressUpdate.displayProgressMessage("Saving level");
/*  625:     */       }
/*  626: 812 */       saveLevel();
/*  627: 814 */       if (par2IProgressUpdate != null) {
/*  628: 816 */         par2IProgressUpdate.resetProgresAndWorkingMessage("Saving chunks");
/*  629:     */       }
/*  630: 819 */       this.chunkProvider.saveChunks(par1, par2IProgressUpdate);
/*  631:     */     }
/*  632:     */   }
/*  633:     */   
/*  634:     */   public void saveChunkData()
/*  635:     */   {
/*  636: 828 */     if (this.chunkProvider.canSave()) {
/*  637: 830 */       this.chunkProvider.saveExtraData();
/*  638:     */     }
/*  639:     */   }
/*  640:     */   
/*  641:     */   protected void saveLevel()
/*  642:     */     throws MinecraftException
/*  643:     */   {
/*  644: 839 */     checkSessionLock();
/*  645: 840 */     this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
/*  646: 841 */     this.mapStorage.saveAllData();
/*  647:     */   }
/*  648:     */   
/*  649:     */   protected void onEntityAdded(Entity par1Entity)
/*  650:     */   {
/*  651: 846 */     super.onEntityAdded(par1Entity);
/*  652: 847 */     this.entityIdMap.addKey(par1Entity.getEntityId(), par1Entity);
/*  653: 848 */     Entity[] var2 = par1Entity.getParts();
/*  654: 850 */     if (var2 != null) {
/*  655: 852 */       for (int var3 = 0; var3 < var2.length; var3++) {
/*  656: 854 */         this.entityIdMap.addKey(var2[var3].getEntityId(), var2[var3]);
/*  657:     */       }
/*  658:     */     }
/*  659:     */   }
/*  660:     */   
/*  661:     */   protected void onEntityRemoved(Entity par1Entity)
/*  662:     */   {
/*  663: 861 */     super.onEntityRemoved(par1Entity);
/*  664: 862 */     this.entityIdMap.removeObject(par1Entity.getEntityId());
/*  665: 863 */     Entity[] var2 = par1Entity.getParts();
/*  666: 865 */     if (var2 != null) {
/*  667: 867 */       for (int var3 = 0; var3 < var2.length; var3++) {
/*  668: 869 */         this.entityIdMap.removeObject(var2[var3].getEntityId());
/*  669:     */       }
/*  670:     */     }
/*  671:     */   }
/*  672:     */   
/*  673:     */   public Entity getEntityByID(int par1)
/*  674:     */   {
/*  675: 879 */     return (Entity)this.entityIdMap.lookup(par1);
/*  676:     */   }
/*  677:     */   
/*  678:     */   public boolean addWeatherEffect(Entity par1Entity)
/*  679:     */   {
/*  680: 887 */     if (super.addWeatherEffect(par1Entity))
/*  681:     */     {
/*  682: 889 */       this.mcServer.getConfigurationManager().func_148541_a(par1Entity.posX, par1Entity.posY, par1Entity.posZ, 512.0D, this.provider.dimensionId, new S2CPacketSpawnGlobalEntity(par1Entity));
/*  683: 890 */       return true;
/*  684:     */     }
/*  685: 894 */     return false;
/*  686:     */   }
/*  687:     */   
/*  688:     */   public void setEntityState(Entity par1Entity, byte par2)
/*  689:     */   {
/*  690: 903 */     getEntityTracker().func_151248_b(par1Entity, new S19PacketEntityStatus(par1Entity, par2));
/*  691:     */   }
/*  692:     */   
/*  693:     */   public Explosion newExplosion(Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10)
/*  694:     */   {
/*  695: 911 */     Explosion var11 = new Explosion(this, par1Entity, par2, par4, par6, par8);
/*  696: 912 */     var11.isFlaming = par9;
/*  697: 913 */     var11.isSmoking = par10;
/*  698: 914 */     var11.doExplosionA();
/*  699: 915 */     var11.doExplosionB(false);
/*  700: 917 */     if (!par10) {
/*  701: 919 */       var11.affectedBlockPositions.clear();
/*  702:     */     }
/*  703: 922 */     Iterator var12 = this.playerEntities.iterator();
/*  704: 924 */     while (var12.hasNext())
/*  705:     */     {
/*  706: 926 */       EntityPlayer var13 = (EntityPlayer)var12.next();
/*  707: 928 */       if (var13.getDistanceSq(par2, par4, par6) < 4096.0D) {
/*  708: 930 */         ((EntityPlayerMP)var13).playerNetServerHandler.sendPacket(new S27PacketExplosion(par2, par4, par6, par8, var11.affectedBlockPositions, (Vec3)var11.func_77277_b().get(var13)));
/*  709:     */       }
/*  710:     */     }
/*  711: 934 */     return var11;
/*  712:     */   }
/*  713:     */   
/*  714:     */   public void func_147452_c(int p_147452_1_, int p_147452_2_, int p_147452_3_, Block p_147452_4_, int p_147452_5_, int p_147452_6_)
/*  715:     */   {
/*  716: 939 */     BlockEventData var7 = new BlockEventData(p_147452_1_, p_147452_2_, p_147452_3_, p_147452_4_, p_147452_5_, p_147452_6_);
/*  717: 940 */     Iterator var8 = this.field_147490_S[this.field_147489_T].iterator();
/*  718:     */     BlockEventData var9;
/*  719:     */     do
/*  720:     */     {
/*  721: 945 */       if (!var8.hasNext())
/*  722:     */       {
/*  723: 947 */         this.field_147490_S[this.field_147489_T].add(var7);
/*  724: 948 */         return;
/*  725:     */       }
/*  726: 951 */       var9 = (BlockEventData)var8.next();
/*  727: 953 */     } while (!var9.equals(var7));
/*  728:     */   }
/*  729:     */   
/*  730:     */   private void func_147488_Z()
/*  731:     */   {
/*  732: 958 */     while (!this.field_147490_S[this.field_147489_T].isEmpty())
/*  733:     */     {
/*  734: 960 */       int var1 = this.field_147489_T;
/*  735: 961 */       this.field_147489_T ^= 0x1;
/*  736: 962 */       Iterator var2 = this.field_147490_S[var1].iterator();
/*  737: 964 */       while (var2.hasNext())
/*  738:     */       {
/*  739: 966 */         BlockEventData var3 = (BlockEventData)var2.next();
/*  740: 968 */         if (func_147485_a(var3)) {
/*  741: 970 */           this.mcServer.getConfigurationManager().func_148541_a(var3.func_151340_a(), var3.func_151342_b(), var3.func_151341_c(), 64.0D, this.provider.dimensionId, new S24PacketBlockAction(var3.func_151340_a(), var3.func_151342_b(), var3.func_151341_c(), var3.getBlock(), var3.getEventID(), var3.getEventParameter()));
/*  742:     */         }
/*  743:     */       }
/*  744: 974 */       this.field_147490_S[var1].clear();
/*  745:     */     }
/*  746:     */   }
/*  747:     */   
/*  748:     */   private boolean func_147485_a(BlockEventData p_147485_1_)
/*  749:     */   {
/*  750: 980 */     Block var2 = getBlock(p_147485_1_.func_151340_a(), p_147485_1_.func_151342_b(), p_147485_1_.func_151341_c());
/*  751: 981 */     return var2 == p_147485_1_.getBlock() ? var2.onBlockEventReceived(this, p_147485_1_.func_151340_a(), p_147485_1_.func_151342_b(), p_147485_1_.func_151341_c(), p_147485_1_.getEventID(), p_147485_1_.getEventParameter()) : false;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public void flush()
/*  755:     */   {
/*  756: 989 */     this.saveHandler.flush();
/*  757:     */   }
/*  758:     */   
/*  759:     */   protected void updateWeather()
/*  760:     */   {
/*  761: 997 */     boolean var1 = isRaining();
/*  762: 998 */     super.updateWeather();
/*  763:1000 */     if (this.prevRainingStrength != this.rainingStrength) {
/*  764:1002 */       this.mcServer.getConfigurationManager().func_148537_a(new S2BPacketChangeGameState(7, this.rainingStrength), this.provider.dimensionId);
/*  765:     */     }
/*  766:1005 */     if (this.prevThunderingStrength != this.thunderingStrength) {
/*  767:1007 */       this.mcServer.getConfigurationManager().func_148537_a(new S2BPacketChangeGameState(8, this.thunderingStrength), this.provider.dimensionId);
/*  768:     */     }
/*  769:1010 */     if (var1 != isRaining())
/*  770:     */     {
/*  771:1012 */       if (var1) {
/*  772:1014 */         this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(2, 0.0F));
/*  773:     */       } else {
/*  774:1018 */         this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(1, 0.0F));
/*  775:     */       }
/*  776:1021 */       this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(7, this.rainingStrength));
/*  777:1022 */       this.mcServer.getConfigurationManager().func_148540_a(new S2BPacketChangeGameState(8, this.thunderingStrength));
/*  778:     */     }
/*  779:     */   }
/*  780:     */   
/*  781:     */   public MinecraftServer func_73046_m()
/*  782:     */   {
/*  783:1028 */     return this.mcServer;
/*  784:     */   }
/*  785:     */   
/*  786:     */   public EntityTracker getEntityTracker()
/*  787:     */   {
/*  788:1036 */     return this.theEntityTracker;
/*  789:     */   }
/*  790:     */   
/*  791:     */   public PlayerManager getPlayerManager()
/*  792:     */   {
/*  793:1041 */     return this.thePlayerManager;
/*  794:     */   }
/*  795:     */   
/*  796:     */   public Teleporter getDefaultTeleporter()
/*  797:     */   {
/*  798:1046 */     return this.worldTeleporter;
/*  799:     */   }
/*  800:     */   
/*  801:     */   public void func_147487_a(String p_147487_1_, double p_147487_2_, double p_147487_4_, double p_147487_6_, int p_147487_8_, double p_147487_9_, double p_147487_11_, double p_147487_13_, double p_147487_15_)
/*  802:     */   {
/*  803:1051 */     S2APacketParticles var17 = new S2APacketParticles(p_147487_1_, (float)p_147487_2_, (float)p_147487_4_, (float)p_147487_6_, (float)p_147487_9_, (float)p_147487_11_, (float)p_147487_13_, (float)p_147487_15_, p_147487_8_);
/*  804:1053 */     for (int var18 = 0; var18 < this.playerEntities.size(); var18++)
/*  805:     */     {
/*  806:1055 */       EntityPlayerMP var19 = (EntityPlayerMP)this.playerEntities.get(var18);
/*  807:1056 */       ChunkCoordinates var20 = var19.getPlayerCoordinates();
/*  808:1057 */       double var21 = p_147487_2_ - var20.posX;
/*  809:1058 */       double var23 = p_147487_4_ - var20.posY;
/*  810:1059 */       double var25 = p_147487_6_ - var20.posZ;
/*  811:1060 */       double var27 = var21 * var21 + var23 * var23 + var25 * var25;
/*  812:1062 */       if (var27 <= 256.0D) {
/*  813:1064 */         var19.playerNetServerHandler.sendPacket(var17);
/*  814:     */       }
/*  815:     */     }
/*  816:     */   }
/*  817:     */   
/*  818:     */   static class ServerBlockEventList
/*  819:     */     extends ArrayList
/*  820:     */   {
/*  821:     */     private static final String __OBFID = "CL_00001439";
/*  822:     */     
/*  823:     */     private ServerBlockEventList() {}
/*  824:     */     
/*  825:     */     ServerBlockEventList(Object par1ServerBlockEvent)
/*  826:     */     {
/*  827:1077 */       this();
/*  828:     */     }
/*  829:     */   }
/*  830:     */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.WorldServer
 * JD-Core Version:    0.7.0.1
 */