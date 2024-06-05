package net.minecraft.world;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;

public abstract class World implements IBlockAccess {
   public final Profiler theProfiler;
   public final List tickableTileEntities = Lists.newArrayList();
   private int skylightSubtracted;
   public final WorldProvider provider;
   protected final ISaveHandler saveHandler;
   protected MapStorage mapStorage;
   private static final String __OBFID = "CL_00000140";
   protected float prevThunderingStrength;
   private boolean processingLoadedTiles;
   protected boolean spawnPeacefulMobs;
   protected final List unloadedEntityList = Lists.newArrayList();
   private final WorldBorder worldBorder;
   public final Random rand = new Random();
   protected VillageCollection villageCollectionObj;
   protected boolean scheduledUpdatesAreImmediate;
   protected boolean spawnHostileMobs;
   protected Scoreboard worldScoreboard = new Scoreboard();
   protected Set activeChunkSet = Sets.newHashSet();
   protected List worldAccesses = Lists.newArrayList();
   protected final IntHashMap entitiesById = new IntHashMap();
   public final List loadedTileEntityList = Lists.newArrayList();
   private long cloudColour = 16777215L;
   public final boolean isRemote;
   protected IChunkProvider chunkProvider;
   protected WorldInfo worldInfo;
   private final List addedTileEntityList = Lists.newArrayList();
   private int lastLightningBolt;
   protected float prevRainingStrength;
   public final List loadedEntityList = Lists.newArrayList();
   protected boolean findingSpawnPoint;
   protected float rainingStrength;
   private int ambientTickCountdown;
   protected final int DIST_HASH_MAGIC = 1013904223;
   protected float thunderingStrength;
   int[] lightUpdateBlockList;
   public final List weatherEffects = Lists.newArrayList();
   protected int updateLCG = (new Random()).nextInt();
   private final Calendar theCalendar = Calendar.getInstance();
   public final List playerEntities = Lists.newArrayList();
   private final List tileEntitiesToBeRemoved = Lists.newArrayList();

   public int getStrongPower(BlockPos var1, EnumFacing var2) {
      IBlockState var3 = this.getBlockState(var1);
      return var3.getBlock().isProvidingStrongPower(this, var1, var3, var2);
   }

   public Vec3 getFogColor(float var1) {
      float var2 = this.getCelestialAngle(var1);
      return this.provider.getFogColor(var2, var1);
   }

   public void tick() {
      this.updateWeather();
   }

   public void initialize(WorldSettings var1) {
      this.worldInfo.setServerInitialized(true);
   }

   public boolean func_175708_f(BlockPos var1, boolean var2) {
      BiomeGenBase var3 = this.getBiomeGenForCoords(var1);
      float var4 = var3.func_180626_a(var1);
      if (var4 > 0.15F) {
         return false;
      } else if (!var2) {
         return true;
      } else {
         if (var1.getY() >= 0 && var1.getY() < 256 && this.getLightFor(EnumSkyBlock.BLOCK, var1) < 10) {
            Block var5 = this.getBlockState(var1).getBlock();
            if (var5.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this, var1)) {
               return true;
            }
         }

         return false;
      }
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 var1, Vec3 var2) {
      return this.rayTraceBlocks(var1, var2, false, false, false);
   }

   public List func_175661_b(Class var1, Predicate var2) {
      ArrayList var3 = Lists.newArrayList();
      Iterator var4 = this.playerEntities.iterator();

      while(var4.hasNext()) {
         Entity var5 = (Entity)var4.next();
         if (var1.isAssignableFrom(var5.getClass()) && var2.apply(var5)) {
            var3.add(var5);
         }
      }

      return var3;
   }

   public BlockPos getHorizon(BlockPos var1) {
      int var2;
      if (var1.getX() >= -30000000 && var1.getZ() >= -30000000 && var1.getX() < 30000000 && var1.getZ() < 30000000) {
         if (this.isChunkLoaded(var1.getX() >> 4, var1.getZ() >> 4, true)) {
            var2 = this.getChunkFromChunkCoords(var1.getX() >> 4, var1.getZ() >> 4).getHeight(var1.getX() & 15, var1.getZ() & 15);
         } else {
            var2 = 0;
         }
      } else {
         var2 = 64;
      }

      return new BlockPos(var1.getX(), var2, var1.getZ());
   }

   private int func_175638_a(BlockPos var1, EnumSkyBlock var2) {
      if (var2 == EnumSkyBlock.SKY && this.isAgainstSky(var1)) {
         return 15;
      } else {
         Block var3 = this.getBlockState(var1).getBlock();
         int var4 = var2 == EnumSkyBlock.SKY ? 0 : var3.getLightValue();
         int var5 = var3.getLightOpacity();
         if (var5 >= 15 && var3.getLightValue() > 0) {
            var5 = 1;
         }

         if (var5 < 1) {
            var5 = 1;
         }

         if (var5 >= 15) {
            return 0;
         } else if (var4 >= 14) {
            return var4;
         } else {
            EnumFacing[] var6 = EnumFacing.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               EnumFacing var9 = var6[var8];
               BlockPos var10 = var1.offset(var9);
               int var11 = this.getLightFor(var2, var10) - var5;
               if (var11 > var4) {
                  var4 = var11;
               }

               if (var4 >= 14) {
                  return var4;
               }
            }

            return var4;
         }
      }
   }

   public List func_147461_a(AxisAlignedBB var1) {
      ArrayList var2 = Lists.newArrayList();
      int var3 = MathHelper.floor_double(var1.minX);
      int var4 = MathHelper.floor_double(var1.maxX + 1.0D);
      int var5 = MathHelper.floor_double(var1.minY);
      int var6 = MathHelper.floor_double(var1.maxY + 1.0D);
      int var7 = MathHelper.floor_double(var1.minZ);
      int var8 = MathHelper.floor_double(var1.maxZ + 1.0D);

      for(int var9 = var3; var9 < var4; ++var9) {
         for(int var10 = var7; var10 < var8; ++var10) {
            if (this.isBlockLoaded(new BlockPos(var9, 64, var10))) {
               for(int var11 = var5 - 1; var11 < var6; ++var11) {
                  BlockPos var12 = new BlockPos(var9, var11, var10);
                  IBlockState var13;
                  if (var9 >= -30000000 && var9 < 30000000 && var10 >= -30000000 && var10 < 30000000) {
                     var13 = this.getBlockState(var12);
                  } else {
                     var13 = Blocks.bedrock.getDefaultState();
                  }

                  var13.getBlock().addCollisionBoxesToList(this, var12, var13, var1, var2, (Entity)null);
               }
            }
         }
      }

      return var2;
   }

   public void setAllowedSpawnTypes(boolean var1, boolean var2) {
      this.spawnHostileMobs = var1;
      this.spawnPeacefulMobs = var2;
   }

   public boolean isAnyLiquid(AxisAlignedBB var1) {
      int var2 = MathHelper.floor_double(var1.minX);
      int var3 = MathHelper.floor_double(var1.maxX);
      int var4 = MathHelper.floor_double(var1.minY);
      int var5 = MathHelper.floor_double(var1.maxY);
      int var6 = MathHelper.floor_double(var1.minZ);
      int var7 = MathHelper.floor_double(var1.maxZ);

      for(int var8 = var2; var8 <= var3; ++var8) {
         for(int var9 = var4; var9 <= var5; ++var9) {
            for(int var10 = var6; var10 <= var7; ++var10) {
               Block var11 = this.getBlockState(new BlockPos(var8, var9, var10)).getBlock();
               if (var11.getMaterial().isLiquid()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public WorldSavedData loadItemData(Class var1, String var2) {
      return this.mapStorage.loadData(var1, var2);
   }

   public boolean isFindingSpawnPoint() {
      return this.findingSpawnPoint;
   }

   public boolean checkNoEntityCollision(AxisAlignedBB var1) {
      return this.checkNoEntityCollision(var1, (Entity)null);
   }

   public boolean isAreaLoaded(BlockPos var1, BlockPos var2, boolean var3) {
      return this.isAreaLoaded(var1.getX(), var1.getY(), var1.getZ(), var2.getX(), var2.getY(), var2.getZ(), var3);
   }

   public int getHeight() {
      return 256;
   }

   public int getLight(BlockPos var1) {
      if (var1.getY() < 0) {
         return 0;
      } else {
         if (var1.getY() >= 256) {
            var1 = new BlockPos(var1.getX(), 255, var1.getZ());
         }

         return this.getChunkFromBlockCoords(var1).setLight(var1, 0);
      }
   }

   public long getSeed() {
      return this.worldInfo.getSeed();
   }

   protected void func_147467_a(int var1, int var2, Chunk var3) {
      this.theProfiler.endStartSection("moodSound");
      if (this.ambientTickCountdown == 0 && !this.isRemote) {
         this.updateLCG = this.updateLCG * 3 + 1013904223;
         int var4 = this.updateLCG >> 2;
         int var5 = var4 & 15;
         int var6 = var4 >> 8 & 15;
         int var7 = var4 >> 16 & 255;
         BlockPos var8 = new BlockPos(var5, var7, var6);
         Block var9 = var3.getBlock(var8);
         var5 += var1;
         var6 += var2;
         if (var9.getMaterial() == Material.air && this.getLight(var8) <= this.rand.nextInt(8) && this.getLightFor(EnumSkyBlock.SKY, var8) <= 0) {
            EntityPlayer var10 = this.getClosestPlayer((double)var5 + 0.5D, (double)var7 + 0.5D, (double)var6 + 0.5D, 8.0D);
            if (var10 != null && var10.getDistanceSq((double)var5 + 0.5D, (double)var7 + 0.5D, (double)var6 + 0.5D) > 4.0D) {
               this.playSoundEffect((double)var5 + 0.5D, (double)var7 + 0.5D, (double)var6 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.rand.nextFloat() * 0.2F);
               this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
            }
         }
      }

      this.theProfiler.endStartSection("checkLight");
      var3.enqueueRelightChecks();
   }

   public boolean canBlockSeeSky(BlockPos var1) {
      if (var1.getY() >= 63) {
         return this.isAgainstSky(var1);
      } else {
         BlockPos var2 = new BlockPos(var1.getX(), 63, var1.getZ());
         if (!this.isAgainstSky(var2)) {
            return false;
         } else {
            for(var2 = var2.offsetDown(); var2.getY() > var1.getY(); var2 = var2.offsetDown()) {
               Block var3 = this.getBlockState(var2).getBlock();
               if (var3.getLightOpacity() > 0 && !var3.getMaterial().isLiquid()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public void makeFireworks(double var1, double var3, double var5, double var7, double var9, double var11, NBTTagCompound var13) {
   }

   public int func_175658_ac() {
      return this.lastLightningBolt;
   }

   public boolean func_175709_b(BlockPos var1, EnumFacing var2) {
      return this.getRedstonePower(var1, var2) > 0;
   }

   public boolean extendedLevelsInChunkCache() {
      return false;
   }

   public boolean addTileEntity(TileEntity var1) {
      boolean var2 = this.loadedTileEntityList.add(var1);
      if (var2 && var1 instanceof IUpdatePlayerListBox) {
         this.tickableTileEntities.add(var1);
      }

      return var2;
   }

   public void removePlayerEntityDangerously(Entity var1) {
      var1.setDead();
      if (var1 instanceof EntityPlayer) {
         this.playerEntities.remove(var1);
         this.updateAllPlayersSleepingFlag();
      }

      int var2 = var1.chunkCoordX;
      int var3 = var1.chunkCoordZ;
      if (var1.addedToChunk && this.isChunkLoaded(var2, var3, true)) {
         this.getChunkFromChunkCoords(var2, var3).removeEntity(var1);
      }

      this.loadedEntityList.remove(var1);
      this.onEntityRemoved(var1);
   }

   public ISaveHandler getSaveHandler() {
      return this.saveHandler;
   }

   public void notifyBlockOfStateChange(BlockPos var1, Block var2) {
      if (!this.isRemote) {
         IBlockState var3 = this.getBlockState(var1);

         try {
            var3.getBlock().onNeighborBlockChange(this, var1, var3, var2);
         } catch (Throwable var7) {
            CrashReport var5 = CrashReport.makeCrashReport(var7, "Exception while updating neighbours");
            CrashReportCategory var6 = var5.makeCategory("Block being updated");
            var6.addCrashSectionCallable("Source block type", new Callable(this, var2) {
               final World this$0;
               private final Block val$blockIn;
               private static final String __OBFID = "CL_00000142";

               public Object call() throws Exception {
                  return this.call();
               }

               {
                  this.this$0 = var1;
                  this.val$blockIn = var2;
               }

               public String call() {
                  try {
                     return String.format("ID #%d (%s // %s)", Block.getIdFromBlock(this.val$blockIn), this.val$blockIn.getUnlocalizedName(), this.val$blockIn.getClass().getCanonicalName());
                  } catch (Throwable var2) {
                     return String.valueOf((new StringBuilder("ID #")).append(Block.getIdFromBlock(this.val$blockIn)));
                  }
               }
            });
            CrashReportCategory.addBlockInfo(var6, var1, var3);
            throw new ReportedException(var5);
         }
      }

   }

   protected World(ISaveHandler var1, WorldInfo var2, WorldProvider var3, Profiler var4, boolean var5) {
      this.ambientTickCountdown = this.rand.nextInt(12000);
      this.spawnHostileMobs = true;
      this.spawnPeacefulMobs = true;
      this.lightUpdateBlockList = new int['耀'];
      this.saveHandler = var1;
      this.theProfiler = var4;
      this.worldInfo = var2;
      this.provider = var3;
      this.isRemote = var5;
      this.worldBorder = var3.getWorldBorder();
   }

   public List getEntitiesWithinAABBExcludingEntity(Entity var1, AxisAlignedBB var2) {
      return this.func_175674_a(var1, var2, IEntitySelector.field_180132_d);
   }

   public EntityPlayer getClosestPlayerToEntity(Entity var1, double var2) {
      return this.getClosestPlayer(var1.posX, var1.posY, var1.posZ, var2);
   }

   public double getHorizon() {
      return this.worldInfo.getTerrainType() == WorldType.FLAT ? 0.0D : 63.0D;
   }

   public VillageCollection getVillageCollection() {
      return this.villageCollectionObj;
   }

   public void spawnParticle(EnumParticleTypes var1, double var2, double var4, double var6, double var8, double var10, double var12, int... var14) {
      this.spawnParticle(var1.func_179348_c(), var1.func_179344_e(), var2, var4, var6, var8, var10, var12, var14);
   }

   public int getLight(BlockPos var1, boolean var2) {
      if (var1.getX() >= -30000000 && var1.getZ() >= -30000000 && var1.getX() < 30000000 && var1.getZ() < 30000000) {
         if (var2 && this.getBlockState(var1).getBlock().getUseNeighborBrightness()) {
            int var8 = this.getLight(var1.offsetUp(), false);
            int var4 = this.getLight(var1.offsetEast(), false);
            int var5 = this.getLight(var1.offsetWest(), false);
            int var6 = this.getLight(var1.offsetSouth(), false);
            int var7 = this.getLight(var1.offsetNorth(), false);
            if (var4 > var8) {
               var8 = var4;
            }

            if (var5 > var8) {
               var8 = var5;
            }

            if (var6 > var8) {
               var8 = var6;
            }

            if (var7 > var8) {
               var8 = var7;
            }

            return var8;
         } else if (var1.getY() < 0) {
            return 0;
         } else {
            if (var1.getY() >= 256) {
               var1 = new BlockPos(var1.getX(), 255, var1.getZ());
            }

            Chunk var3 = this.getChunkFromBlockCoords(var1);
            return var3.setLight(var1, this.skylightSubtracted);
         }
      } else {
         return 15;
      }
   }

   public Block getGroundAboveSeaLevel(BlockPos var1) {
      BlockPos var2;
      for(var2 = new BlockPos(var1.getX(), 63, var1.getZ()); !this.isAirBlock(var2.offsetUp()); var2 = var2.offsetUp()) {
      }

      return this.getBlockState(var2).getBlock();
   }

   public boolean checkLight(BlockPos var1) {
      boolean var2 = false;
      if (!this.provider.getHasNoSky()) {
         var2 |= this.checkLightFor(EnumSkyBlock.SKY, var1);
      }

      var2 |= this.checkLightFor(EnumSkyBlock.BLOCK, var1);
      return var2;
   }

   public boolean isInsideBorder(WorldBorder var1, Entity var2) {
      double var3 = var1.minX();
      double var5 = var1.minZ();
      double var7 = var1.maxX();
      double var9 = var1.maxZ();
      if (var2.isOutsideBorder()) {
         ++var3;
         ++var5;
         --var7;
         --var9;
      } else {
         --var3;
         --var5;
         ++var7;
         ++var9;
      }

      return var2.posX > var3 && var2.posX < var7 && var2.posZ > var5 && var2.posZ < var9;
   }

   public void playSoundEffect(double var1, double var3, double var5, String var7, float var8, float var9) {
      for(int var10 = 0; var10 < this.worldAccesses.size(); ++var10) {
         ((IWorldAccess)this.worldAccesses.get(var10)).playSound(var7, var1, var3, var5, var8, var9);
      }

   }

   public void joinEntityInSurroundings(Entity var1) {
      int var2 = MathHelper.floor_double(var1.posX / 16.0D);
      int var3 = MathHelper.floor_double(var1.posZ / 16.0D);
      byte var4 = 2;

      for(int var5 = var2 - var4; var5 <= var2 + var4; ++var5) {
         for(int var6 = var3 - var4; var6 <= var3 + var4; ++var6) {
            this.getChunkFromChunkCoords(var5, var6);
         }
      }

      if (!this.loadedEntityList.contains(var1)) {
         this.loadedEntityList.add(var1);
      }

   }

   public static boolean doesBlockHaveSolidTopSurface(IBlockAccess var0, BlockPos var1) {
      IBlockState var2 = var0.getBlockState(var1);
      Block var3 = var2.getBlock();
      return var3.getMaterial().isOpaque() && var3.isFullCube() ? true : (var3 instanceof BlockStairs ? var2.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP : (var3 instanceof BlockSlab ? var2.getValue(BlockSlab.HALF_PROP) == BlockSlab.EnumBlockHalf.TOP : (var3 instanceof BlockHopper ? true : (var3 instanceof BlockSnow ? (Integer)var2.getValue(BlockSnow.LAYERS_PROP) == 7 : false))));
   }

   private void spawnParticle(int var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      for(int var16 = 0; var16 < this.worldAccesses.size(); ++var16) {
         ((IWorldAccess)this.worldAccesses.get(var16)).func_180442_a(var1, var2, var3, var5, var7, var9, var11, var13, var15);
      }

   }

   public void sendBlockBreakProgress(int var1, BlockPos var2, int var3) {
      for(int var4 = 0; var4 < this.worldAccesses.size(); ++var4) {
         IWorldAccess var5 = (IWorldAccess)this.worldAccesses.get(var4);
         var5.sendBlockBreakProgress(var1, var2, var3);
      }

   }

   public BlockPos func_180499_a(String var1, BlockPos var2) {
      return this.getChunkProvider().func_180513_a(this, var1, var2);
   }

   public void markTileEntityForRemoval(TileEntity var1) {
      this.tileEntitiesToBeRemoved.add(var1);
   }

   public BlockPos func_175725_q(BlockPos var1) {
      return this.getChunkFromBlockCoords(var1).func_177440_h(var1);
   }

   public WorldBorder getWorldBorder() {
      return this.worldBorder;
   }

   public void setSkylightSubtracted(int var1) {
      this.skylightSubtracted = var1;
   }

   public void func_175646_b(BlockPos var1, TileEntity var2) {
      if (this.isBlockLoaded(var1)) {
         this.getChunkFromBlockCoords(var1).setChunkModified();
      }

   }

   public boolean isAirBlock(BlockPos var1) {
      return this.getBlockState(var1).getBlock().getMaterial() == Material.air;
   }

   public void checkSessionLock() throws MinecraftException {
      this.saveHandler.checkSessionLock();
   }

   public void updateEntities() {
      this.theProfiler.startSection("entities");
      this.theProfiler.startSection("global");

      int var1;
      Entity var2;
      CrashReport var3;
      CrashReportCategory var4;
      for(var1 = 0; var1 < this.weatherEffects.size(); ++var1) {
         var2 = (Entity)this.weatherEffects.get(var1);

         try {
            ++var2.ticksExisted;
            var2.onUpdate();
         } catch (Throwable var15) {
            var3 = CrashReport.makeCrashReport(var15, "Ticking entity");
            var4 = var3.makeCategory("Entity being ticked");
            if (var2 == null) {
               var4.addCrashSection("Entity", "~~NULL~~");
            } else {
               var2.addEntityCrashInfo(var4);
            }

            throw new ReportedException(var3);
         }

         if (var2.isDead) {
            this.weatherEffects.remove(var1--);
         }
      }

      this.theProfiler.endStartSection("remove");
      this.loadedEntityList.removeAll(this.unloadedEntityList);

      int var5;
      int var6;
      for(var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
         var2 = (Entity)this.unloadedEntityList.get(var1);
         var5 = var2.chunkCoordX;
         var6 = var2.chunkCoordZ;
         if (var2.addedToChunk && this.isChunkLoaded(var5, var6, true)) {
            this.getChunkFromChunkCoords(var5, var6).removeEntity(var2);
         }
      }

      for(var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
         this.onEntityRemoved((Entity)this.unloadedEntityList.get(var1));
      }

      this.unloadedEntityList.clear();
      this.theProfiler.endStartSection("regular");

      for(var1 = 0; var1 < this.loadedEntityList.size(); ++var1) {
         var2 = (Entity)this.loadedEntityList.get(var1);
         if (var2.ridingEntity != null) {
            if (!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) {
               continue;
            }

            var2.ridingEntity.riddenByEntity = null;
            var2.ridingEntity = null;
         }

         this.theProfiler.startSection("tick");
         if (!var2.isDead) {
            try {
               this.updateEntity(var2);
            } catch (Throwable var14) {
               var3 = CrashReport.makeCrashReport(var14, "Ticking entity");
               var4 = var3.makeCategory("Entity being ticked");
               var2.addEntityCrashInfo(var4);
               throw new ReportedException(var3);
            }
         }

         this.theProfiler.endSection();
         this.theProfiler.startSection("remove");
         if (var2.isDead) {
            var5 = var2.chunkCoordX;
            var6 = var2.chunkCoordZ;
            if (var2.addedToChunk && this.isChunkLoaded(var5, var6, true)) {
               this.getChunkFromChunkCoords(var5, var6).removeEntity(var2);
            }

            this.loadedEntityList.remove(var1--);
            this.onEntityRemoved(var2);
         }

         this.theProfiler.endSection();
      }

      this.theProfiler.endStartSection("blockEntities");
      this.processingLoadedTiles = true;
      Iterator var7 = this.tickableTileEntities.iterator();

      while(var7.hasNext()) {
         TileEntity var8 = (TileEntity)var7.next();
         if (!var8.isInvalid() && var8.hasWorldObj()) {
            BlockPos var9 = var8.getPos();
            if (this.isBlockLoaded(var9) && this.worldBorder.contains(var9)) {
               try {
                  ((IUpdatePlayerListBox)var8).update();
               } catch (Throwable var13) {
                  CrashReport var11 = CrashReport.makeCrashReport(var13, "Ticking block entity");
                  CrashReportCategory var12 = var11.makeCategory("Block entity being ticked");
                  var8.addInfoToCrashReport(var12);
                  throw new ReportedException(var11);
               }
            }
         }

         if (var8.isInvalid()) {
            var7.remove();
            this.loadedTileEntityList.remove(var8);
            if (this.isBlockLoaded(var8.getPos())) {
               this.getChunkFromBlockCoords(var8.getPos()).removeTileEntity(var8.getPos());
            }
         }
      }

      this.processingLoadedTiles = false;
      if (!this.tileEntitiesToBeRemoved.isEmpty()) {
         this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
         this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
         this.tileEntitiesToBeRemoved.clear();
      }

      this.theProfiler.endStartSection("pendingBlockEntities");
      if (!this.addedTileEntityList.isEmpty()) {
         for(int var16 = 0; var16 < this.addedTileEntityList.size(); ++var16) {
            TileEntity var17 = (TileEntity)this.addedTileEntityList.get(var16);
            if (!var17.isInvalid()) {
               if (!this.loadedTileEntityList.contains(var17)) {
                  this.addTileEntity(var17);
               }

               if (this.isBlockLoaded(var17.getPos())) {
                  this.getChunkFromBlockCoords(var17.getPos()).addTileEntity(var17.getPos(), var17);
               }

               this.markBlockForUpdate(var17.getPos());
            }
         }

         this.addedTileEntityList.clear();
      }

      this.theProfiler.endSection();
      this.theProfiler.endSection();
   }

   public List func_175647_a(Class var1, AxisAlignedBB var2, Predicate var3) {
      int var4 = MathHelper.floor_double((var2.minX - 2.0D) / 16.0D);
      int var5 = MathHelper.floor_double((var2.maxX + 2.0D) / 16.0D);
      int var6 = MathHelper.floor_double((var2.minZ - 2.0D) / 16.0D);
      int var7 = MathHelper.floor_double((var2.maxZ + 2.0D) / 16.0D);
      ArrayList var8 = Lists.newArrayList();

      for(int var9 = var4; var9 <= var5; ++var9) {
         for(int var10 = var6; var10 <= var7; ++var10) {
            if (this.isChunkLoaded(var9, var10, true)) {
               this.getChunkFromChunkCoords(var9, var10).func_177430_a(var1, var2, var8, var3);
            }
         }
      }

      return var8;
   }

   public void markBlockForUpdate(BlockPos var1) {
      for(int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
         ((IWorldAccess)this.worldAccesses.get(var2)).markBlockForUpdate(var1);
      }

   }

   public long getTotalWorldTime() {
      return this.worldInfo.getWorldTotalTime();
   }

   public List func_175674_a(Entity var1, AxisAlignedBB var2, Predicate var3) {
      ArrayList var4 = Lists.newArrayList();
      int var5 = MathHelper.floor_double((var2.minX - 2.0D) / 16.0D);
      int var6 = MathHelper.floor_double((var2.maxX + 2.0D) / 16.0D);
      int var7 = MathHelper.floor_double((var2.minZ - 2.0D) / 16.0D);
      int var8 = MathHelper.floor_double((var2.maxZ + 2.0D) / 16.0D);

      for(int var9 = var5; var9 <= var6; ++var9) {
         for(int var10 = var7; var10 <= var8; ++var10) {
            if (this.isChunkLoaded(var9, var10, true)) {
               this.getChunkFromChunkCoords(var9, var10).func_177414_a(var1, var2, var4, var3);
            }
         }
      }

      return var4;
   }

   public boolean func_175662_w(BlockPos var1) {
      return this.func_175670_e(var1, true);
   }

   public void addTileEntities(Collection var1) {
      if (this.processingLoadedTiles) {
         this.addedTileEntityList.addAll(var1);
      } else {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            TileEntity var3 = (TileEntity)var2.next();
            this.loadedTileEntityList.add(var3);
            if (var3 instanceof IUpdatePlayerListBox) {
               this.tickableTileEntities.add(var3);
            }
         }
      }

   }

   public Scoreboard getScoreboard() {
      return this.worldScoreboard;
   }

   public void removeEntity(Entity var1) {
      if (var1.riddenByEntity != null) {
         var1.riddenByEntity.mountEntity((Entity)null);
      }

      if (var1.ridingEntity != null) {
         var1.mountEntity((Entity)null);
      }

      var1.setDead();
      if (var1 instanceof EntityPlayer) {
         this.playerEntities.remove(var1);
         this.updateAllPlayersSleepingFlag();
         this.onEntityRemoved(var1);
      }

   }

   public void playSoundToNearExcept(EntityPlayer var1, String var2, float var3, float var4) {
      for(int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
         ((IWorldAccess)this.worldAccesses.get(var5)).playSoundToNearExcept(var1, var2, var1.posX, var1.posY, var1.posZ, var3, var4);
      }

   }

   public int getChunksLowestHorizon(int var1, int var2) {
      if (var1 >= -30000000 && var2 >= -30000000 && var1 < 30000000 && var2 < 30000000) {
         if (!this.isChunkLoaded(var1 >> 4, var2 >> 4, true)) {
            return 0;
         } else {
            Chunk var3 = this.getChunkFromChunkCoords(var1 >> 4, var2 >> 4);
            return var3.getLowestHeight();
         }
      } else {
         return 64;
      }
   }

   public boolean isAreaLoaded(BlockPos var1, BlockPos var2) {
      return this.isAreaLoaded(var1, var2, true);
   }

   public void playSoundAtEntity(Entity var1, String var2, float var3, float var4) {
      for(int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
         ((IWorldAccess)this.worldAccesses.get(var5)).playSound(var2, var1.posX, var1.posY, var1.posZ, var3, var4);
      }

   }

   public void setLightFor(EnumSkyBlock var1, BlockPos var2, int var3) {
      if (this.isValid(var2) && this.isBlockLoaded(var2)) {
         Chunk var4 = this.getChunkFromBlockCoords(var2);
         var4.setLightFor(var1, var2, var3);
         this.notifyLightSet(var2);
      }

   }

   public int countEntities(Class var1) {
      int var2 = 0;
      Iterator var3 = this.loadedEntityList.iterator();

      while(true) {
         Entity var4;
         do {
            if (!var3.hasNext()) {
               return var2;
            }

            var4 = (Entity)var3.next();
         } while(var4 instanceof EntityLiving && ((EntityLiving)var4).isNoDespawnRequired());

         if (var1.isAssignableFrom(var4.getClass())) {
            ++var2;
         }
      }
   }

   public void setLastLightningBolt(int var1) {
      this.lastLightningBolt = var1;
   }

   public int getSkylightSubtracted() {
      return this.skylightSubtracted;
   }

   public boolean func_147470_e(AxisAlignedBB var1) {
      int var2 = MathHelper.floor_double(var1.minX);
      int var3 = MathHelper.floor_double(var1.maxX + 1.0D);
      int var4 = MathHelper.floor_double(var1.minY);
      int var5 = MathHelper.floor_double(var1.maxY + 1.0D);
      int var6 = MathHelper.floor_double(var1.minZ);
      int var7 = MathHelper.floor_double(var1.maxZ + 1.0D);
      if (this.isAreaLoaded(var2, var4, var6, var3, var5, var7, true)) {
         for(int var8 = var2; var8 < var3; ++var8) {
            for(int var9 = var4; var9 < var5; ++var9) {
               for(int var10 = var6; var10 < var7; ++var10) {
                  Block var11 = this.getBlockState(new BlockPos(var8, var9, var10)).getBlock();
                  if (var11 == Blocks.fire || var11 == Blocks.flowing_lava || var11 == Blocks.lava) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public void updateEntityWithOptionalForce(Entity var1, boolean var2) {
      int var3 = MathHelper.floor_double(var1.posX);
      int var4 = MathHelper.floor_double(var1.posZ);
      byte var5 = 32;
      if (!var2 || this.isAreaLoaded(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5, true)) {
         var1.lastTickPosX = var1.posX;
         var1.lastTickPosY = var1.posY;
         var1.lastTickPosZ = var1.posZ;
         var1.prevRotationYaw = var1.rotationYaw;
         var1.prevRotationPitch = var1.rotationPitch;
         if (var2 && var1.addedToChunk) {
            ++var1.ticksExisted;
            if (var1.ridingEntity != null) {
               var1.updateRidden();
            } else {
               var1.onUpdate();
            }
         }

         this.theProfiler.startSection("chunkCheck");
         if (Double.isNaN(var1.posX) || Double.isInfinite(var1.posX)) {
            var1.posX = var1.lastTickPosX;
         }

         if (Double.isNaN(var1.posY) || Double.isInfinite(var1.posY)) {
            var1.posY = var1.lastTickPosY;
         }

         if (Double.isNaN(var1.posZ) || Double.isInfinite(var1.posZ)) {
            var1.posZ = var1.lastTickPosZ;
         }

         if (Double.isNaN((double)var1.rotationPitch) || Double.isInfinite((double)var1.rotationPitch)) {
            var1.rotationPitch = var1.prevRotationPitch;
         }

         if (Double.isNaN((double)var1.rotationYaw) || Double.isInfinite((double)var1.rotationYaw)) {
            var1.rotationYaw = var1.prevRotationYaw;
         }

         int var6 = MathHelper.floor_double(var1.posX / 16.0D);
         int var7 = MathHelper.floor_double(var1.posY / 16.0D);
         int var8 = MathHelper.floor_double(var1.posZ / 16.0D);
         if (!var1.addedToChunk || var1.chunkCoordX != var6 || var1.chunkCoordY != var7 || var1.chunkCoordZ != var8) {
            if (var1.addedToChunk && this.isChunkLoaded(var1.chunkCoordX, var1.chunkCoordZ, true)) {
               this.getChunkFromChunkCoords(var1.chunkCoordX, var1.chunkCoordZ).removeEntityAtIndex(var1, var1.chunkCoordY);
            }

            if (this.isChunkLoaded(var6, var8, true)) {
               var1.addedToChunk = true;
               this.getChunkFromChunkCoords(var6, var8).addEntity(var1);
            } else {
               var1.addedToChunk = false;
            }
         }

         this.theProfiler.endSection();
         if (var2 && var1.addedToChunk && var1.riddenByEntity != null) {
            if (!var1.riddenByEntity.isDead && var1.riddenByEntity.ridingEntity == var1) {
               this.updateEntity(var1.riddenByEntity);
            } else {
               var1.riddenByEntity.ridingEntity = null;
               var1.riddenByEntity = null;
            }
         }
      }

   }

   public boolean isAreaLoaded(StructureBoundingBox var1, boolean var2) {
      return this.isAreaLoaded(var1.minX, var1.minY, var1.minZ, var1.maxX, var1.maxY, var1.maxZ, var2);
   }

   public boolean isAABBInMaterial(AxisAlignedBB var1, Material var2) {
      int var3 = MathHelper.floor_double(var1.minX);
      int var4 = MathHelper.floor_double(var1.maxX + 1.0D);
      int var5 = MathHelper.floor_double(var1.minY);
      int var6 = MathHelper.floor_double(var1.maxY + 1.0D);
      int var7 = MathHelper.floor_double(var1.minZ);
      int var8 = MathHelper.floor_double(var1.maxZ + 1.0D);

      for(int var9 = var3; var9 < var4; ++var9) {
         for(int var10 = var5; var10 < var6; ++var10) {
            for(int var11 = var7; var11 < var8; ++var11) {
               BlockPos var12 = new BlockPos(var9, var10, var11);
               IBlockState var13 = this.getBlockState(var12);
               Block var14 = var13.getBlock();
               if (var14.getMaterial() == var2) {
                  int var15 = (Integer)var13.getValue(BlockLiquid.LEVEL);
                  double var16 = (double)(var10 + 1);
                  if (var15 < 8) {
                     var16 = (double)(var10 + 1) - (double)var15 / 8.0D;
                  }

                  if (var16 >= var1.minY) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public TileEntity getTileEntity(BlockPos var1) {
      if (!this.isValid(var1)) {
         return null;
      } else {
         TileEntity var2 = null;
         int var3;
         TileEntity var4;
         if (this.processingLoadedTiles) {
            for(var3 = 0; var3 < this.addedTileEntityList.size(); ++var3) {
               var4 = (TileEntity)this.addedTileEntityList.get(var3);
               if (!var4.isInvalid() && var4.getPos().equals(var1)) {
                  var2 = var4;
                  break;
               }
            }
         }

         if (var2 == null) {
            var2 = this.getChunkFromBlockCoords(var1).func_177424_a(var1, Chunk.EnumCreateEntityType.IMMEDIATE);
         }

         if (var2 == null) {
            for(var3 = 0; var3 < this.addedTileEntityList.size(); ++var3) {
               var4 = (TileEntity)this.addedTileEntityList.get(var3);
               if (!var4.isInvalid() && var4.getPos().equals(var1)) {
                  var2 = var4;
                  break;
               }
            }
         }

         return var2;
      }
   }

   public boolean func_175636_b(double var1, double var3, double var5, double var7) {
      for(int var9 = 0; var9 < this.playerEntities.size(); ++var9) {
         EntityPlayer var10 = (EntityPlayer)this.playerEntities.get(var9);
         if (IEntitySelector.field_180132_d.apply(var10)) {
            double var11 = var10.getDistanceSq(var1, var3, var5);
            if (var7 < 0.0D || var11 < var7 * var7) {
               return true;
            }
         }
      }

      return false;
   }

   public World init() {
      return this;
   }

   public boolean spawnEntityInWorld(Entity var1) {
      int var2 = MathHelper.floor_double(var1.posX / 16.0D);
      int var3 = MathHelper.floor_double(var1.posZ / 16.0D);
      boolean var4 = var1.forceSpawn;
      if (var1 instanceof EntityPlayer) {
         var4 = true;
      }

      if (!var4 && !this.isChunkLoaded(var2, var3, true)) {
         return false;
      } else {
         if (var1 instanceof EntityPlayer) {
            EntityPlayer var5 = (EntityPlayer)var1;
            this.playerEntities.add(var5);
            this.updateAllPlayersSleepingFlag();
         }

         this.getChunkFromChunkCoords(var2, var3).addEntity(var1);
         this.loadedEntityList.add(var1);
         this.onEntityAdded(var1);
         return true;
      }
   }

   public List getPendingBlockUpdates(Chunk var1, boolean var2) {
      return null;
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 var1, Vec3 var2, boolean var3) {
      return this.rayTraceBlocks(var1, var2, var3, false, false);
   }

   protected void func_147456_g() {
      this.setActivePlayerChunksAndCheckLight();
   }

   public void markBlockRangeForRenderUpdate(BlockPos var1, BlockPos var2) {
      this.markBlockRangeForRenderUpdate(var1.getX(), var1.getY(), var1.getZ(), var2.getX(), var2.getY(), var2.getZ());
   }

   public GameRules getGameRules() {
      return this.worldInfo.getGameRulesInstance();
   }

   public String getProviderName() {
      return this.chunkProvider.makeString();
   }

   public boolean isDaytime() {
      return this.skylightSubtracted < 4;
   }

   public void addWorldAccess(IWorldAccess var1) {
      this.worldAccesses.add(var1);
   }

   public void scheduleUpdate(BlockPos var1, Block var2, int var3) {
   }

   private boolean isValid(BlockPos var1) {
      return var1.getX() >= -30000000 && var1.getZ() >= -30000000 && var1.getX() < 30000000 && var1.getZ() < 30000000 && var1.getY() >= 0 && var1.getY() < 256;
   }

   public Explosion newExplosion(Entity var1, double var2, double var4, double var6, float var8, boolean var9, boolean var10) {
      Explosion var11 = new Explosion(this, var1, var2, var4, var6, var8, var9, var10);
      var11.doExplosionA();
      var11.doExplosionB(true);
      return var11;
   }

   public void sendQuittingDisconnectingPacket() {
   }

   public void playSound(double var1, double var3, double var5, String var7, float var8, float var9, boolean var10) {
   }

   public float getSunBrightness(float var1) {
      float var2 = this.getCelestialAngle(var1);
      float var3 = 1.0F - (MathHelper.cos(var2 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
      var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
      var3 = 1.0F - var3;
      var3 = (float)((double)var3 * (1.0D - (double)(this.getRainStrength(var1) * 5.0F) / 16.0D));
      var3 = (float)((double)var3 * (1.0D - (double)(this.getWeightedThunderStrength(var1) * 5.0F) / 16.0D));
      return var3 * 0.8F + 0.2F;
   }

   public int func_175687_A(BlockPos var1) {
      int var2 = 0;
      EnumFacing[] var3 = EnumFacing.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EnumFacing var6 = var3[var5];
         int var7 = this.getRedstonePower(var1.offset(var6), var6);
         if (var7 >= 15) {
            return 15;
         }

         if (var7 > var2) {
            var2 = var7;
         }
      }

      return var2;
   }

   public void setItemData(String var1, WorldSavedData var2) {
      this.mapStorage.setData(var1, var2);
   }

   public Vec3 getSkyColor(Entity var1, float var2) {
      float var3 = this.getCelestialAngle(var2);
      float var4 = MathHelper.cos(var3 * 3.1415927F * 2.0F) * 2.0F + 0.5F;
      var4 = MathHelper.clamp_float(var4, 0.0F, 1.0F);
      int var5 = MathHelper.floor_double(var1.posX);
      int var6 = MathHelper.floor_double(var1.posY);
      int var7 = MathHelper.floor_double(var1.posZ);
      BlockPos var8 = new BlockPos(var5, var6, var7);
      BiomeGenBase var9 = this.getBiomeGenForCoords(var8);
      float var10 = var9.func_180626_a(var8);
      int var11 = var9.getSkyColorByTemp(var10);
      float var12 = (float)(var11 >> 16 & 255) / 255.0F;
      float var13 = (float)(var11 >> 8 & 255) / 255.0F;
      float var14 = (float)(var11 & 255) / 255.0F;
      var12 *= var4;
      var13 *= var4;
      var14 *= var4;
      float var15 = this.getRainStrength(var2);
      float var16;
      float var17;
      if (var15 > 0.0F) {
         var16 = (var12 * 0.3F + var13 * 0.59F + var14 * 0.11F) * 0.6F;
         var17 = 1.0F - var15 * 0.75F;
         var12 = var12 * var17 + var16 * (1.0F - var17);
         var13 = var13 * var17 + var16 * (1.0F - var17);
         var14 = var14 * var17 + var16 * (1.0F - var17);
      }

      var16 = this.getWeightedThunderStrength(var2);
      if (var16 > 0.0F) {
         var17 = (var12 * 0.3F + var13 * 0.59F + var14 * 0.11F) * 0.2F;
         float var18 = 1.0F - var16 * 0.75F;
         var12 = var12 * var18 + var17 * (1.0F - var18);
         var13 = var13 * var18 + var17 * (1.0F - var18);
         var14 = var14 * var18 + var17 * (1.0F - var18);
      }

      if (this.lastLightningBolt > 0) {
         var17 = (float)this.lastLightningBolt - var2;
         if (var17 > 1.0F) {
            var17 = 1.0F;
         }

         var17 *= 0.45F;
         var12 = var12 * (1.0F - var17) + 0.8F * var17;
         var13 = var13 * (1.0F - var17) + 0.8F * var17;
         var14 = var14 * (1.0F - var17) + 1.0F * var17;
      }

      return new Vec3((double)var12, (double)var13, (double)var14);
   }

   public int calculateSkylightSubtracted(float var1) {
      float var2 = this.getCelestialAngle(var1);
      float var3 = 1.0F - (MathHelper.cos(var2 * 3.1415927F * 2.0F) * 2.0F + 0.5F);
      var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
      var3 = 1.0F - var3;
      var3 = (float)((double)var3 * (1.0D - (double)(this.getRainStrength(var1) * 5.0F) / 16.0D));
      var3 = (float)((double)var3 * (1.0D - (double)(this.getWeightedThunderStrength(var1) * 5.0F) / 16.0D));
      var3 = 1.0F - var3;
      return (int)(var3 * 11.0F);
   }

   public boolean isBlockLoaded(BlockPos var1, boolean var2) {
      return !this.isValid(var1) ? false : this.isChunkLoaded(var1.getX() >> 4, var1.getZ() >> 4, var2);
   }

   public boolean setBlockState(BlockPos var1, IBlockState var2, int var3) {
      if (!this.isValid(var1)) {
         return false;
      } else if (!this.isRemote && this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
         return false;
      } else {
         Chunk var4 = this.getChunkFromBlockCoords(var1);
         Block var5 = var2.getBlock();
         IBlockState var6 = var4.setBlockState(var1, var2);
         if (var6 == null) {
            return false;
         } else {
            Block var7 = var6.getBlock();
            if (var5.getLightOpacity() != var7.getLightOpacity() || var5.getLightValue() != var7.getLightValue()) {
               this.theProfiler.startSection("checkLight");
               this.checkLight(var1);
               this.theProfiler.endSection();
            }

            if ((var3 & 2) != 0 && (!this.isRemote || (var3 & 4) == 0) && var4.isPopulated()) {
               this.markBlockForUpdate(var1);
            }

            if (!this.isRemote && (var3 & 1) != 0) {
               this.func_175722_b(var1, var6.getBlock());
               if (var5.hasComparatorInputOverride()) {
                  this.updateComparatorOutputLevel(var1, var5);
               }
            }

            return true;
         }
      }
   }

   public CrashReportCategory addWorldInfoToCrashReport(CrashReport var1) {
      CrashReportCategory var2 = var1.makeCategoryDepth("Affected level", 1);
      var2.addCrashSection("Level name", this.worldInfo == null ? "????" : this.worldInfo.getWorldName());
      var2.addCrashSectionCallable("All players", new Callable(this) {
         final World this$0;
         private static final String __OBFID = "CL_00000143";

         public Object call() throws Exception {
            return this.call();
         }

         public String call() {
            return String.valueOf((new StringBuilder(String.valueOf(this.this$0.playerEntities.size()))).append(" total; ").append(this.this$0.playerEntities.toString()));
         }

         {
            this.this$0 = var1;
         }
      });
      var2.addCrashSectionCallable("Chunk stats", new Callable(this) {
         final World this$0;
         private static final String __OBFID = "CL_00000144";

         public String call() {
            return this.this$0.chunkProvider.makeString();
         }

         {
            this.this$0 = var1;
         }

         public Object call() throws Exception {
            return this.call();
         }
      });

      try {
         this.worldInfo.addToCrashReport(var2);
      } catch (Throwable var4) {
         var2.addCrashSectionThrowable("Level Data Unobtainable", var4);
      }

      return var2;
   }

   public int getRedstonePower(BlockPos var1, EnumFacing var2) {
      IBlockState var3 = this.getBlockState(var1);
      Block var4 = var3.getBlock();
      return var4.isNormalCube() ? this.getStrongPower(var1) : var4.isProvidingWeakPower(this, var1, var3, var2);
   }

   public long getWorldTime() {
      return this.worldInfo.getWorldTime();
   }

   public List getLoadedEntityList() {
      return this.loadedEntityList;
   }

   public boolean isThundering() {
      return (double)this.getWeightedThunderStrength(1.0F) > 0.9D;
   }

   public void setTileEntity(BlockPos var1, TileEntity var2) {
      if (var2 != null && !var2.isInvalid()) {
         if (this.processingLoadedTiles) {
            var2.setPos(var1);
            Iterator var3 = this.addedTileEntityList.iterator();

            while(var3.hasNext()) {
               TileEntity var4 = (TileEntity)var3.next();
               if (var4.getPos().equals(var1)) {
                  var4.invalidate();
                  var3.remove();
               }
            }

            this.addedTileEntityList.add(var2);
         } else {
            this.addTileEntity(var2);
            this.getChunkFromBlockCoords(var1).addTileEntity(var1, var2);
         }
      }

   }

   public List func_175644_a(Class var1, Predicate var2) {
      ArrayList var3 = Lists.newArrayList();
      Iterator var4 = this.loadedEntityList.iterator();

      while(var4.hasNext()) {
         Entity var5 = (Entity)var4.next();
         if (var1.isAssignableFrom(var5.getClass()) && var2.apply(var5)) {
            var3.add(var5);
         }
      }

      return var3;
   }

   public boolean checkNoEntityCollision(AxisAlignedBB var1, Entity var2) {
      List var3 = this.getEntitiesWithinAABBExcludingEntity((Entity)null, var1);

      for(int var4 = 0; var4 < var3.size(); ++var4) {
         Entity var5 = (Entity)var3.get(var4);
         if (!var5.isDead && var5.preventEntitySpawning && var5 != var2 && (var2 == null || var2.ridingEntity != var5 && var2.riddenByEntity != var5)) {
            return false;
         }
      }

      return true;
   }

   public Random setRandomSeed(int var1, int var2, int var3) {
      long var4 = (long)var1 * 341873128712L + (long)var2 * 132897987541L + this.getWorldInfo().getSeed() + (long)var3;
      this.rand.setSeed(var4);
      return this.rand;
   }

   public void setEntityState(Entity var1, byte var2) {
   }

   protected void setActivePlayerChunksAndCheckLight() {
      this.activeChunkSet.clear();
      this.theProfiler.startSection("buildList");

      int var1;
      EntityPlayer var2;
      int var3;
      int var4;
      int var5;
      for(var1 = 0; var1 < this.playerEntities.size(); ++var1) {
         var2 = (EntityPlayer)this.playerEntities.get(var1);
         var3 = MathHelper.floor_double(var2.posX / 16.0D);
         var4 = MathHelper.floor_double(var2.posZ / 16.0D);
         var5 = this.getRenderDistanceChunks();

         for(int var6 = -var5; var6 <= var5; ++var6) {
            for(int var7 = -var5; var7 <= var5; ++var7) {
               this.activeChunkSet.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
            }
         }
      }

      this.theProfiler.endSection();
      if (this.ambientTickCountdown > 0) {
         --this.ambientTickCountdown;
      }

      this.theProfiler.startSection("playerCheckLight");
      if (!this.playerEntities.isEmpty()) {
         var1 = this.rand.nextInt(this.playerEntities.size());
         var2 = (EntityPlayer)this.playerEntities.get(var1);
         var3 = MathHelper.floor_double(var2.posX) + this.rand.nextInt(11) - 5;
         var4 = MathHelper.floor_double(var2.posY) + this.rand.nextInt(11) - 5;
         var5 = MathHelper.floor_double(var2.posZ) + this.rand.nextInt(11) - 5;
         this.checkLight(new BlockPos(var3, var4, var5));
      }

      this.theProfiler.endSection();
   }

   public void playAuxSFX(int var1, BlockPos var2, int var3) {
      this.playAuxSFXAtEntity((EntityPlayer)null, var1, var2, var3);
   }

   public Entity findNearestEntityWithinAABB(Class var1, AxisAlignedBB var2, Entity var3) {
      List var4 = this.getEntitiesWithinAABB(var1, var2);
      Entity var5 = null;
      double var6 = Double.MAX_VALUE;

      for(int var8 = 0; var8 < var4.size(); ++var8) {
         Entity var9 = (Entity)var4.get(var8);
         if (var9 != var3 && IEntitySelector.field_180132_d.apply(var9)) {
            double var10 = var3.getDistanceSqToEntity(var9);
            if (var10 <= var6) {
               var5 = var9;
               var6 = var10;
            }
         }
      }

      return var5;
   }

   public boolean isAgainstSky(BlockPos var1) {
      return this.getChunkFromBlockCoords(var1).canSeeSky(var1);
   }

   public void playAuxSFXAtEntity(EntityPlayer var1, int var2, BlockPos var3, int var4) {
      try {
         for(int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
            ((IWorldAccess)this.worldAccesses.get(var5)).func_180439_a(var1, var2, var3, var4);
         }

      } catch (Throwable var8) {
         CrashReport var6 = CrashReport.makeCrashReport(var8, "Playing level event");
         CrashReportCategory var7 = var6.makeCategory("Level event being played");
         var7.addCrashSection("Block coordinates", CrashReportCategory.getCoordinateInfo(var3));
         var7.addCrashSection("Event source", var1);
         var7.addCrashSection("Event type", var2);
         var7.addCrashSection("Event data", var4);
         throw new ReportedException(var6);
      }
   }

   public EntityPlayer getClosestPlayer(double var1, double var3, double var5, double var7) {
      double var9 = -1.0D;
      EntityPlayer var11 = null;

      for(int var12 = 0; var12 < this.playerEntities.size(); ++var12) {
         EntityPlayer var13 = (EntityPlayer)this.playerEntities.get(var12);
         if (IEntitySelector.field_180132_d.apply(var13)) {
            double var14 = var13.getDistanceSq(var1, var3, var5);
            if ((var7 < 0.0D || var14 < var7 * var7) && (var9 == -1.0D || var14 < var9)) {
               var9 = var14;
               var11 = var13;
            }
         }
      }

      return var11;
   }

   public EntityPlayer getPlayerEntityByName(String var1) {
      for(int var2 = 0; var2 < this.playerEntities.size(); ++var2) {
         EntityPlayer var3 = (EntityPlayer)this.playerEntities.get(var2);
         if (var1.equals(var3.getName())) {
            return var3;
         }
      }

      return null;
   }

   public int getLightFromNeighborsFor(EnumSkyBlock var1, BlockPos var2) {
      if (this.provider.getHasNoSky() && var1 == EnumSkyBlock.SKY) {
         return 0;
      } else {
         if (var2.getY() < 0) {
            var2 = new BlockPos(var2.getX(), 0, var2.getZ());
         }

         if (!this.isValid(var2)) {
            return var1.defaultLightValue;
         } else if (!this.isBlockLoaded(var2)) {
            return var1.defaultLightValue;
         } else if (this.getBlockState(var2).getBlock().getUseNeighborBrightness()) {
            int var8 = this.getLightFor(var1, var2.offsetUp());
            int var4 = this.getLightFor(var1, var2.offsetEast());
            int var5 = this.getLightFor(var1, var2.offsetWest());
            int var6 = this.getLightFor(var1, var2.offsetSouth());
            int var7 = this.getLightFor(var1, var2.offsetNorth());
            if (var4 > var8) {
               var8 = var4;
            }

            if (var5 > var8) {
               var8 = var5;
            }

            if (var6 > var8) {
               var8 = var6;
            }

            if (var7 > var8) {
               var8 = var7;
            }

            return var8;
         } else {
            Chunk var3 = this.getChunkFromBlockCoords(var2);
            return var3.getLightFor(var1, var2);
         }
      }
   }

   public void spawnParticle(EnumParticleTypes var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
      this.spawnParticle(var1.func_179348_c(), var1.func_179344_e() | var2, var3, var5, var7, var9, var11, var13, var15);
   }

   public float getCurrentMoonPhaseFactor() {
      return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
   }

   public List getCollidingBoundingBoxes(Entity var1, AxisAlignedBB var2) {
      ArrayList var3 = Lists.newArrayList();
      int var4 = MathHelper.floor_double(var2.minX);
      int var5 = MathHelper.floor_double(var2.maxX + 1.0D);
      int var6 = MathHelper.floor_double(var2.minY);
      int var7 = MathHelper.floor_double(var2.maxY + 1.0D);
      int var8 = MathHelper.floor_double(var2.minZ);
      int var9 = MathHelper.floor_double(var2.maxZ + 1.0D);

      for(int var10 = var4; var10 < var5; ++var10) {
         for(int var11 = var8; var11 < var9; ++var11) {
            if (this.isBlockLoaded(new BlockPos(var10, 64, var11))) {
               for(int var12 = var6 - 1; var12 < var7; ++var12) {
                  BlockPos var13 = new BlockPos(var10, var12, var11);
                  boolean var14 = var1.isOutsideBorder();
                  boolean var15 = this.isInsideBorder(this.getWorldBorder(), var1);
                  if (var14 && var15) {
                     var1.setOutsideBorder(false);
                  } else if (!var14 && !var15) {
                     var1.setOutsideBorder(true);
                  }

                  IBlockState var16;
                  if (!this.getWorldBorder().contains(var13) && var15) {
                     var16 = Blocks.stone.getDefaultState();
                  } else {
                     var16 = this.getBlockState(var13);
                  }

                  var16.getBlock().addCollisionBoxesToList(this, var13, var16, var2, var3, var1);
               }
            }
         }
      }

      double var17 = 0.25D;
      List var18 = this.getEntitiesWithinAABBExcludingEntity(var1, var2.expand(var17, var17, var17));

      for(int var19 = 0; var19 < var18.size(); ++var19) {
         if (var1.riddenByEntity != var18 && var1.ridingEntity != var18) {
            AxisAlignedBB var20 = ((Entity)var18.get(var19)).getBoundingBox();
            if (var20 != null && var20.intersectsWith(var2)) {
               var3.add(var20);
            }

            var20 = var1.getCollisionBox((Entity)var18.get(var19));
            if (var20 != null && var20.intersectsWith(var2)) {
               var3.add(var20);
            }
         }
      }

      return var3;
   }

   public IChunkProvider getChunkProvider() {
      return this.chunkProvider;
   }

   public boolean isAreaLoaded(BlockPos var1, int var2, boolean var3) {
      return this.isAreaLoaded(var1.getX() - var2, var1.getY() - var2, var1.getZ() - var2, var1.getX() + var2, var1.getY() + var2, var1.getZ() + var2, var3);
   }

   public String getDebugLoadedEntities() {
      return String.valueOf((new StringBuilder("All: ")).append(this.loadedEntityList.size()));
   }

   public void func_180497_b(BlockPos var1, Block var2, int var3, int var4) {
   }

   public Vec3 getCloudColour(float var1) {
      float var2 = this.getCelestialAngle(var1);
      float var3 = MathHelper.cos(var2 * 3.1415927F * 2.0F) * 2.0F + 0.5F;
      var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
      float var4 = (float)(this.cloudColour >> 16 & 255L) / 255.0F;
      float var5 = (float)(this.cloudColour >> 8 & 255L) / 255.0F;
      float var6 = (float)(this.cloudColour & 255L) / 255.0F;
      float var7 = this.getRainStrength(var1);
      float var8;
      float var9;
      if (var7 > 0.0F) {
         var8 = (var4 * 0.3F + var5 * 0.59F + var6 * 0.11F) * 0.6F;
         var9 = 1.0F - var7 * 0.95F;
         var4 = var4 * var9 + var8 * (1.0F - var9);
         var5 = var5 * var9 + var8 * (1.0F - var9);
         var6 = var6 * var9 + var8 * (1.0F - var9);
      }

      var4 *= var3 * 0.9F + 0.1F;
      var5 *= var3 * 0.9F + 0.1F;
      var6 *= var3 * 0.85F + 0.15F;
      var8 = this.getWeightedThunderStrength(var1);
      if (var8 > 0.0F) {
         var9 = (var4 * 0.3F + var5 * 0.59F + var6 * 0.11F) * 0.2F;
         float var10 = 1.0F - var8 * 0.95F;
         var4 = var4 * var10 + var9 * (1.0F - var10);
         var5 = var5 * var10 + var9 * (1.0F - var10);
         var6 = var6 * var10 + var9 * (1.0F - var10);
      }

      return new Vec3((double)var4, (double)var5, (double)var6);
   }

   public boolean setBlockToAir(BlockPos var1) {
      return this.setBlockState(var1, Blocks.air.getDefaultState(), 3);
   }

   public int getMoonPhase() {
      return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
   }

   public void func_175654_a(BlockPos var1, Block var2, int var3, int var4) {
   }

   public void setSpawnLocation(BlockPos var1) {
      this.worldInfo.setSpawn(var1);
   }

   public BlockPos getSpawnPoint() {
      BlockPos var1 = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
      if (!this.getWorldBorder().contains(var1)) {
         var1 = this.getHorizon(new BlockPos(this.getWorldBorder().getCenterX(), 0.0D, this.getWorldBorder().getCenterZ()));
      }

      return var1;
   }

   public boolean destroyBlock(BlockPos var1, boolean var2) {
      IBlockState var3 = this.getBlockState(var1);
      Block var4 = var3.getBlock();
      if (var4.getMaterial() == Material.air) {
         return false;
      } else {
         this.playAuxSFX(2001, var1, Block.getStateId(var3));
         if (var2) {
            var4.dropBlockAsItem(this, var1, var3, 0);
         }

         return this.setBlockState(var1, Blocks.air.getDefaultState(), 3);
      }
   }

   public BlockPos func_175672_r(BlockPos var1) {
      Chunk var2 = this.getChunkFromBlockCoords(var1);

      BlockPos var3;
      BlockPos var4;
      for(var3 = new BlockPos(var1.getX(), var2.getTopFilledSegment() + 16, var1.getZ()); var3.getY() >= 0; var3 = var4) {
         var4 = var3.offsetDown();
         Material var5 = var2.getBlock(var4).getMaterial();
         if (var5.blocksMovement() && var5 != Material.leaves) {
            break;
         }
      }

      return var3;
   }

   public boolean func_175727_C(BlockPos var1) {
      if (!this.isRaining()) {
         return false;
      } else if (!this.isAgainstSky(var1)) {
         return false;
      } else if (this.func_175725_q(var1).getY() > var1.getY()) {
         return false;
      } else {
         BiomeGenBase var2 = this.getBiomeGenForCoords(var1);
         return var2.getEnableSnow() ? false : (this.func_175708_f(var1, false) ? false : var2.canSpawnLightningBolt());
      }
   }

   public boolean addWeatherEffect(Entity var1) {
      this.weatherEffects.add(var1);
      return true;
   }

   public boolean chunkExists(int var1, int var2) {
      BlockPos var3 = this.getSpawnPoint();
      int var4 = var1 * 16 + 8 - var3.getX();
      int var5 = var2 * 16 + 8 - var3.getZ();
      short var6 = 128;
      return var4 >= -var6 && var4 <= var6 && var5 >= -var6 && var5 <= var6;
   }

   public WorldInfo getWorldInfo() {
      return this.worldInfo;
   }

   public boolean isMaterialInBB(AxisAlignedBB var1, Material var2) {
      int var3 = MathHelper.floor_double(var1.minX);
      int var4 = MathHelper.floor_double(var1.maxX + 1.0D);
      int var5 = MathHelper.floor_double(var1.minY);
      int var6 = MathHelper.floor_double(var1.maxY + 1.0D);
      int var7 = MathHelper.floor_double(var1.minZ);
      int var8 = MathHelper.floor_double(var1.maxZ + 1.0D);

      for(int var9 = var3; var9 < var4; ++var9) {
         for(int var10 = var5; var10 < var6; ++var10) {
            for(int var11 = var7; var11 < var8; ++var11) {
               if (this.getBlockState(new BlockPos(var9, var10, var11)).getBlock().getMaterial() == var2) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean func_175719_a(EntityPlayer var1, BlockPos var2, EnumFacing var3) {
      var2 = var2.offset(var3);
      if (this.getBlockState(var2).getBlock() == Blocks.fire) {
         this.playAuxSFXAtEntity(var1, 1004, var2, 0);
         this.setBlockToAir(var2);
         return true;
      } else {
         return false;
      }
   }

   public boolean func_175675_v(BlockPos var1) {
      return this.func_175670_e(var1, false);
   }

   public void notifyLightSet(BlockPos var1) {
      for(int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
         ((IWorldAccess)this.worldAccesses.get(var2)).notifyLightSet(var1);
      }

   }

   public float getLightBrightness(BlockPos var1) {
      return this.provider.getLightBrightnessTable()[this.getLightFromNeighbors(var1)];
   }

   public int getLightFromNeighbors(BlockPos var1) {
      return this.getLight(var1, true);
   }

   public void func_175722_b(BlockPos var1, Block var2) {
      if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
         this.notifyNeighborsOfStateChange(var1, var2);
      }

   }

   public List getEntitiesWithinAABB(Class var1, AxisAlignedBB var2) {
      return this.func_175647_a(var1, var2, IEntitySelector.field_180132_d);
   }

   public WorldType getWorldType() {
      return this.worldInfo.getTerrainType();
   }

   public boolean isBlockModifiable(EntityPlayer var1, BlockPos var2) {
      return true;
   }

   public void updateEntity(Entity var1) {
      this.updateEntityWithOptionalForce(var1, true);
   }

   protected abstract int getRenderDistanceChunks();

   public boolean func_180502_D(BlockPos var1) {
      BiomeGenBase var2 = this.getBiomeGenForCoords(var1);
      return var2.isHighHumidity();
   }

   public MovingObjectPosition rayTraceBlocks(Vec3 var1, Vec3 var2, boolean var3, boolean var4, boolean var5) {
      if (!Double.isNaN(var1.xCoord) && !Double.isNaN(var1.yCoord) && !Double.isNaN(var1.zCoord)) {
         if (!Double.isNaN(var2.xCoord) && !Double.isNaN(var2.yCoord) && !Double.isNaN(var2.zCoord)) {
            int var6 = MathHelper.floor_double(var2.xCoord);
            int var7 = MathHelper.floor_double(var2.yCoord);
            int var8 = MathHelper.floor_double(var2.zCoord);
            int var9 = MathHelper.floor_double(var1.xCoord);
            int var10 = MathHelper.floor_double(var1.yCoord);
            int var11 = MathHelper.floor_double(var1.zCoord);
            BlockPos var12 = new BlockPos(var9, var10, var11);
            new BlockPos(var6, var7, var8);
            IBlockState var13 = this.getBlockState(var12);
            Block var14 = var13.getBlock();
            MovingObjectPosition var15;
            if ((!var4 || var14.getCollisionBoundingBox(this, var12, var13) != null) && var14.canCollideCheck(var13, var3)) {
               var15 = var14.collisionRayTrace(this, var12, var1, var2);
               if (var15 != null) {
                  return var15;
               }
            }

            var15 = null;
            int var16 = 200;

            while(var16-- >= 0) {
               if (Double.isNaN(var1.xCoord) || Double.isNaN(var1.yCoord) || Double.isNaN(var1.zCoord)) {
                  return null;
               }

               if (var9 == var6 && var10 == var7 && var11 == var8) {
                  return var5 ? var15 : null;
               }

               boolean var17 = true;
               boolean var18 = true;
               boolean var19 = true;
               double var20 = 999.0D;
               double var22 = 999.0D;
               double var24 = 999.0D;
               if (var6 > var9) {
                  var20 = (double)var9 + 1.0D;
               } else if (var6 < var9) {
                  var20 = (double)var9 + 0.0D;
               } else {
                  var17 = false;
               }

               if (var7 > var10) {
                  var22 = (double)var10 + 1.0D;
               } else if (var7 < var10) {
                  var22 = (double)var10 + 0.0D;
               } else {
                  var18 = false;
               }

               if (var8 > var11) {
                  var24 = (double)var11 + 1.0D;
               } else if (var8 < var11) {
                  var24 = (double)var11 + 0.0D;
               } else {
                  var19 = false;
               }

               double var26 = 999.0D;
               double var28 = 999.0D;
               double var30 = 999.0D;
               double var32 = var2.xCoord - var1.xCoord;
               double var34 = var2.yCoord - var1.yCoord;
               double var36 = var2.zCoord - var1.zCoord;
               if (var17) {
                  var26 = (var20 - var1.xCoord) / var32;
               }

               if (var18) {
                  var28 = (var22 - var1.yCoord) / var34;
               }

               if (var19) {
                  var30 = (var24 - var1.zCoord) / var36;
               }

               if (var26 == -0.0D) {
                  var26 = -1.0E-4D;
               }

               if (var28 == -0.0D) {
                  var28 = -1.0E-4D;
               }

               if (var30 == -0.0D) {
                  var30 = -1.0E-4D;
               }

               EnumFacing var38;
               if (var26 < var28 && var26 < var30) {
                  var38 = var6 > var9 ? EnumFacing.WEST : EnumFacing.EAST;
                  var1 = new Vec3(var20, var1.yCoord + var34 * var26, var1.zCoord + var36 * var26);
               } else if (var28 < var30) {
                  var38 = var7 > var10 ? EnumFacing.DOWN : EnumFacing.UP;
                  var1 = new Vec3(var1.xCoord + var32 * var28, var22, var1.zCoord + var36 * var28);
               } else {
                  var38 = var8 > var11 ? EnumFacing.NORTH : EnumFacing.SOUTH;
                  var1 = new Vec3(var1.xCoord + var32 * var30, var1.yCoord + var34 * var30, var24);
               }

               var9 = MathHelper.floor_double(var1.xCoord) - (var38 == EnumFacing.EAST ? 1 : 0);
               var10 = MathHelper.floor_double(var1.yCoord) - (var38 == EnumFacing.UP ? 1 : 0);
               var11 = MathHelper.floor_double(var1.zCoord) - (var38 == EnumFacing.SOUTH ? 1 : 0);
               var12 = new BlockPos(var9, var10, var11);
               IBlockState var39 = this.getBlockState(var12);
               Block var40 = var39.getBlock();
               if (!var4 || var40.getCollisionBoundingBox(this, var12, var39) != null) {
                  if (var40.canCollideCheck(var39, var3)) {
                     MovingObjectPosition var41 = var40.collisionRayTrace(this, var12, var1, var2);
                     if (var41 != null) {
                        return var41;
                     }
                  } else {
                     var15 = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, var1, var38, var12);
                  }
               }
            }

            return var5 ? var15 : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public void setInitialSpawnLocation() {
      this.setSpawnLocation(new BlockPos(8, 64, 8));
   }

   public boolean setBlockState(BlockPos var1, IBlockState var2) {
      return this.setBlockState(var1, var2, 3);
   }

   public Chunk getChunkFromChunkCoords(int var1, int var2) {
      return this.chunkProvider.provideChunk(var1, var2);
   }

   public int getActualHeight() {
      return this.provider.getHasNoSky() ? 128 : 256;
   }

   public boolean func_175677_d(BlockPos var1, boolean var2) {
      if (!this.isValid(var1)) {
         return var2;
      } else {
         Chunk var3 = this.chunkProvider.func_177459_a(var1);
         if (var3.isEmpty()) {
            return var2;
         } else {
            Block var4 = this.getBlockState(var1).getBlock();
            return var4.getMaterial().isOpaque() && var4.isFullCube();
         }
      }
   }

   public EnumDifficulty getDifficulty() {
      return this.getWorldInfo().getDifficulty();
   }

   public MapStorage func_175693_T() {
      return this.mapStorage;
   }

   public Chunk getChunkFromBlockCoords(BlockPos var1) {
      return this.getChunkFromChunkCoords(var1.getX() >> 4, var1.getZ() >> 4);
   }

   protected void updateWeather() {
      if (!this.provider.getHasNoSky() && !this.isRemote) {
         int var1 = this.worldInfo.func_176133_A();
         if (var1 > 0) {
            --var1;
            this.worldInfo.func_176142_i(var1);
            this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
            this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
         }

         int var2 = this.worldInfo.getThunderTime();
         if (var2 <= 0) {
            if (this.worldInfo.isThundering()) {
               this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
            } else {
               this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
            }
         } else {
            --var2;
            this.worldInfo.setThunderTime(var2);
            if (var2 <= 0) {
               this.worldInfo.setThundering(!this.worldInfo.isThundering());
            }
         }

         this.prevThunderingStrength = this.thunderingStrength;
         if (this.worldInfo.isThundering()) {
            this.thunderingStrength = (float)((double)this.thunderingStrength + 0.01D);
         } else {
            this.thunderingStrength = (float)((double)this.thunderingStrength - 0.01D);
         }

         this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0F, 1.0F);
         int var3 = this.worldInfo.getRainTime();
         if (var3 <= 0) {
            if (this.worldInfo.isRaining()) {
               this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
            } else {
               this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
            }
         } else {
            --var3;
            this.worldInfo.setRainTime(var3);
            if (var3 <= 0) {
               this.worldInfo.setRaining(!this.worldInfo.isRaining());
            }
         }

         this.prevRainingStrength = this.rainingStrength;
         if (this.worldInfo.isRaining()) {
            this.rainingStrength = (float)((double)this.rainingStrength + 0.01D);
         } else {
            this.rainingStrength = (float)((double)this.rainingStrength - 0.01D);
         }

         this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0F, 1.0F);
      }

   }

   public void removeWorldAccess(IWorldAccess var1) {
      this.worldAccesses.remove(var1);
   }

   public int getLightFor(EnumSkyBlock var1, BlockPos var2) {
      if (var2.getY() < 0) {
         var2 = new BlockPos(var2.getX(), 0, var2.getZ());
      }

      if (!this.isValid(var2)) {
         return var1.defaultLightValue;
      } else if (!this.isBlockLoaded(var2)) {
         return var1.defaultLightValue;
      } else {
         Chunk var3 = this.getChunkFromBlockCoords(var2);
         return var3.getLightFor(var1, var2);
      }
   }

   protected void calculateInitialWeather() {
      if (this.worldInfo.isRaining()) {
         this.rainingStrength = 1.0F;
         if (this.worldInfo.isThundering()) {
            this.thunderingStrength = 1.0F;
         }
      }

   }

   public int getUniqueDataId(String var1) {
      return this.mapStorage.getUniqueDataId(var1);
   }

   public void markBlockRangeForRenderUpdate(int var1, int var2, int var3, int var4, int var5, int var6) {
      for(int var7 = 0; var7 < this.worldAccesses.size(); ++var7) {
         ((IWorldAccess)this.worldAccesses.get(var7)).markBlockRangeForRenderUpdate(var1, var2, var3, var4, var5, var6);
      }

   }

   public DifficultyInstance getDifficultyForLocation(BlockPos var1) {
      long var2 = 0L;
      float var4 = 0.0F;
      if (this.isBlockLoaded(var1)) {
         var4 = this.getCurrentMoonPhaseFactor();
         var2 = this.getChunkFromBlockCoords(var1).getInhabitedTime();
      }

      return new DifficultyInstance(this.getDifficulty(), this.getWorldTime(), var2, var4);
   }

   public boolean isRaining() {
      return (double)this.getRainStrength(1.0F) > 0.2D;
   }

   public float getCelestialAngle(float var1) {
      return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), var1);
   }

   public boolean func_175665_u(BlockPos var1) {
      IBlockState var2 = this.getBlockState(var1);
      AxisAlignedBB var3 = var2.getBlock().getCollisionBoundingBox(this, var1, var2);
      return var3 != null && var3.getAverageEdgeLength() >= 1.0D;
   }

   private boolean isAreaLoaded(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      if (var5 >= 0 && var2 < 256) {
         var1 >>= 4;
         var3 >>= 4;
         var4 >>= 4;
         var6 >>= 4;

         for(int var8 = var1; var8 <= var4; ++var8) {
            for(int var9 = var3; var9 <= var6; ++var9) {
               if (!this.isChunkLoaded(var8, var9, var7)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public float getWeightedThunderStrength(float var1) {
      return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * var1) * this.getRainStrength(var1);
   }

   public boolean checkLightFor(EnumSkyBlock var1, BlockPos var2) {
      if (!this.isAreaLoaded(var2, 17, false)) {
         return false;
      } else {
         int var3 = 0;
         int var4 = 0;
         this.theProfiler.startSection("getBrightness");
         int var5 = this.getLightFor(var1, var2);
         int var6 = this.func_175638_a(var2, var1);
         int var7 = var2.getX();
         int var8 = var2.getY();
         int var9 = var2.getZ();
         int var10;
         int var11;
         int var12;
         int var13;
         int var14;
         int var15;
         int var16;
         int var17;
         if (var6 > var5) {
            this.lightUpdateBlockList[var4++] = 133152;
         } else if (var6 < var5) {
            this.lightUpdateBlockList[var4++] = 133152 | var5 << 18;

            label127:
            while(true) {
               int var18;
               do {
                  do {
                     BlockPos var19;
                     do {
                        if (var3 >= var4) {
                           var3 = 0;
                           break label127;
                        }

                        var10 = this.lightUpdateBlockList[var3++];
                        var11 = (var10 & 63) - 32 + var7;
                        var12 = (var10 >> 6 & 63) - 32 + var8;
                        var13 = (var10 >> 12 & 63) - 32 + var9;
                        var18 = var10 >> 18 & 15;
                        var19 = new BlockPos(var11, var12, var13);
                        var14 = this.getLightFor(var1, var19);
                     } while(var14 != var18);

                     this.setLightFor(var1, var19, 0);
                  } while(var18 <= 0);

                  var15 = MathHelper.abs_int(var11 - var7);
                  var16 = MathHelper.abs_int(var12 - var8);
                  var17 = MathHelper.abs_int(var13 - var9);
               } while(var15 + var16 + var17 >= 17);

               EnumFacing[] var20 = EnumFacing.values();
               int var21 = var20.length;

               for(int var22 = 0; var22 < var21; ++var22) {
                  EnumFacing var23 = var20[var22];
                  int var24 = var11 + var23.getFrontOffsetX();
                  int var25 = var12 + var23.getFrontOffsetY();
                  int var26 = var13 + var23.getFrontOffsetZ();
                  BlockPos var27 = new BlockPos(var24, var25, var26);
                  int var28 = Math.max(1, this.getBlockState(var27).getBlock().getLightOpacity());
                  var14 = this.getLightFor(var1, var27);
                  if (var14 == var18 - var28 && var4 < this.lightUpdateBlockList.length) {
                     this.lightUpdateBlockList[var4++] = var24 - var7 + 32 | var25 - var8 + 32 << 6 | var26 - var9 + 32 << 12 | var18 - var28 << 18;
                  }
               }
            }
         }

         this.theProfiler.endSection();
         this.theProfiler.startSection("checkedPosition < toCheckCount");

         while(var3 < var4) {
            var10 = this.lightUpdateBlockList[var3++];
            var11 = (var10 & 63) - 32 + var7;
            var12 = (var10 >> 6 & 63) - 32 + var8;
            var13 = (var10 >> 12 & 63) - 32 + var9;
            BlockPos var29 = new BlockPos(var11, var12, var13);
            int var30 = this.getLightFor(var1, var29);
            var14 = this.func_175638_a(var29, var1);
            if (var14 != var30) {
               this.setLightFor(var1, var29, var14);
               if (var14 > var30) {
                  var15 = Math.abs(var11 - var7);
                  var16 = Math.abs(var12 - var8);
                  var17 = Math.abs(var13 - var9);
                  boolean var31 = var4 < this.lightUpdateBlockList.length - 6;
                  if (var15 + var16 + var17 < 17 && var31) {
                     if (this.getLightFor(var1, var29.offsetWest()) < var14) {
                        this.lightUpdateBlockList[var4++] = var11 - 1 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 - var9 + 32 << 12);
                     }

                     if (this.getLightFor(var1, var29.offsetEast()) < var14) {
                        this.lightUpdateBlockList[var4++] = var11 + 1 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 - var9 + 32 << 12);
                     }

                     if (this.getLightFor(var1, var29.offsetDown()) < var14) {
                        this.lightUpdateBlockList[var4++] = var11 - var7 + 32 + (var12 - 1 - var8 + 32 << 6) + (var13 - var9 + 32 << 12);
                     }

                     if (this.getLightFor(var1, var29.offsetUp()) < var14) {
                        this.lightUpdateBlockList[var4++] = var11 - var7 + 32 + (var12 + 1 - var8 + 32 << 6) + (var13 - var9 + 32 << 12);
                     }

                     if (this.getLightFor(var1, var29.offsetNorth()) < var14) {
                        this.lightUpdateBlockList[var4++] = var11 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 - 1 - var9 + 32 << 12);
                     }

                     if (this.getLightFor(var1, var29.offsetSouth()) < var14) {
                        this.lightUpdateBlockList[var4++] = var11 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 + 1 - var9 + 32 << 12);
                     }
                  }
               }
            }
         }

         this.theProfiler.endSection();
         return true;
      }
   }

   public void unloadEntities(Collection var1) {
      this.unloadedEntityList.addAll(var1);
   }

   protected abstract IChunkProvider createChunkProvider();

   public BiomeGenBase getBiomeGenForCoords(BlockPos var1) {
      if (this.isBlockLoaded(var1)) {
         Chunk var2 = this.getChunkFromBlockCoords(var1);

         try {
            return var2.getBiome(var1, this.provider.getWorldChunkManager());
         } catch (Throwable var6) {
            CrashReport var4 = CrashReport.makeCrashReport(var6, "Getting biome");
            CrashReportCategory var5 = var4.makeCategory("Coordinates of biome request");
            var5.addCrashSectionCallable("Location", new Callable(this, var1) {
               final World this$0;
               private final BlockPos val$pos;
               private static final String __OBFID = "CL_00000141";

               {
                  this.this$0 = var1;
                  this.val$pos = var2;
               }

               public Object call() throws Exception {
                  return this.call();
               }

               public String call() {
                  return CrashReportCategory.getCoordinateInfo(this.val$pos);
               }
            });
            throw new ReportedException(var4);
         }
      } else {
         return this.provider.getWorldChunkManager().func_180300_a(var1, BiomeGenBase.plains);
      }
   }

   public void func_82738_a(long var1) {
      this.worldInfo.incrementTotalWorldTime(var1);
   }

   public boolean func_175670_e(BlockPos var1, boolean var2) {
      BiomeGenBase var3 = this.getBiomeGenForCoords(var1);
      float var4 = var3.func_180626_a(var1);
      if (var4 > 0.15F) {
         return false;
      } else {
         if (var1.getY() >= 0 && var1.getY() < 256 && this.getLightFor(EnumSkyBlock.BLOCK, var1) < 10) {
            IBlockState var5 = this.getBlockState(var1);
            Block var6 = var5.getBlock();
            if ((var6 == Blocks.water || var6 == Blocks.flowing_water) && (Integer)var5.getValue(BlockLiquid.LEVEL) == 0) {
               if (!var2) {
                  return true;
               }

               boolean var7 = this.func_175696_F(var1.offsetWest()) && this.func_175696_F(var1.offsetEast()) && this.func_175696_F(var1.offsetNorth()) && this.func_175696_F(var1.offsetSouth());
               if (!var7) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void func_175717_a(BlockPos var1, String var2) {
      for(int var3 = 0; var3 < this.worldAccesses.size(); ++var3) {
         ((IWorldAccess)this.worldAccesses.get(var3)).func_174961_a(var2, var1);
      }

   }

   public void removeTileEntity(BlockPos var1) {
      TileEntity var2 = this.getTileEntity(var1);
      if (var2 != null && this.processingLoadedTiles) {
         var2.invalidate();
         this.addedTileEntityList.remove(var2);
      } else {
         if (var2 != null) {
            this.addedTileEntityList.remove(var2);
            this.loadedTileEntityList.remove(var2);
            this.tickableTileEntities.remove(var2);
         }

         this.getChunkFromBlockCoords(var1).removeTileEntity(var1);
      }

   }

   public void func_175637_a(Block var1, BlockPos var2, Random var3) {
      this.scheduledUpdatesAreImmediate = true;
      var1.updateTick(this, var2, this.getBlockState(var2), var3);
      this.scheduledUpdatesAreImmediate = false;
   }

   public void loadEntities(Collection var1) {
      this.loadedEntityList.addAll(var1);
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         Entity var3 = (Entity)var2.next();
         this.onEntityAdded(var3);
      }

   }

   public int getCombinedLight(BlockPos var1, int var2) {
      int var3 = this.getLightFromNeighborsFor(EnumSkyBlock.SKY, var1);
      int var4 = this.getLightFromNeighborsFor(EnumSkyBlock.BLOCK, var1);
      if (var4 < var2) {
         var4 = var2;
      }

      return var3 << 20 | var4 << 4;
   }

   public void calculateInitialSkylight() {
      int var1 = this.calculateSkylightSubtracted(1.0F);
      if (var1 != this.skylightSubtracted) {
         this.skylightSubtracted = var1;
      }

   }

   public float getRainStrength(float var1) {
      return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * var1;
   }

   public void notifyNeighborsOfStateExcept(BlockPos var1, Block var2, EnumFacing var3) {
      if (var3 != EnumFacing.WEST) {
         this.notifyBlockOfStateChange(var1.offsetWest(), var2);
      }

      if (var3 != EnumFacing.EAST) {
         this.notifyBlockOfStateChange(var1.offsetEast(), var2);
      }

      if (var3 != EnumFacing.DOWN) {
         this.notifyBlockOfStateChange(var1.offsetDown(), var2);
      }

      if (var3 != EnumFacing.UP) {
         this.notifyBlockOfStateChange(var1.offsetUp(), var2);
      }

      if (var3 != EnumFacing.NORTH) {
         this.notifyBlockOfStateChange(var1.offsetNorth(), var2);
      }

      if (var3 != EnumFacing.SOUTH) {
         this.notifyBlockOfStateChange(var1.offsetSouth(), var2);
      }

   }

   public boolean isAreaLoaded(StructureBoundingBox var1) {
      return this.isAreaLoaded(var1, true);
   }

   public List func_175712_a(StructureBoundingBox var1, boolean var2) {
      return null;
   }

   public boolean isBlockLoaded(BlockPos var1) {
      return this.isBlockLoaded(var1, true);
   }

   public boolean canBlockBePlaced(Block var1, BlockPos var2, boolean var3, EnumFacing var4, Entity var5, ItemStack var6) {
      Block var7 = this.getBlockState(var2).getBlock();
      AxisAlignedBB var8 = var3 ? null : var1.getCollisionBoundingBox(this, var2, var1.getDefaultState());
      return var8 != null && !this.checkNoEntityCollision(var8, var5) ? false : (var7.getMaterial() == Material.circuits && var1 == Blocks.anvil ? true : var7.getMaterial().isReplaceable() && var1.canReplace(this, var2, var4, var6));
   }

   public void setWorldTime(long var1) {
      this.worldInfo.setWorldTime(var1);
   }

   public boolean checkBlockCollision(AxisAlignedBB var1) {
      int var2 = MathHelper.floor_double(var1.minX);
      int var3 = MathHelper.floor_double(var1.maxX);
      int var4 = MathHelper.floor_double(var1.minY);
      int var5 = MathHelper.floor_double(var1.maxY);
      int var6 = MathHelper.floor_double(var1.minZ);
      int var7 = MathHelper.floor_double(var1.maxZ);

      for(int var8 = var2; var8 <= var3; ++var8) {
         for(int var9 = var4; var9 <= var5; ++var9) {
            for(int var10 = var6; var10 <= var7; ++var10) {
               Block var11 = this.getBlockState(new BlockPos(var8, var9, var10)).getBlock();
               if (var11.getMaterial() != Material.air) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public void addBlockEvent(BlockPos var1, Block var2, int var3, int var4) {
      var2.onBlockEventReceived(this, var1, this.getBlockState(var1), var3, var4);
   }

   public void markBlocksDirtyVertical(int var1, int var2, int var3, int var4) {
      int var5;
      if (var3 > var4) {
         var5 = var4;
         var4 = var3;
         var3 = var5;
      }

      if (!this.provider.getHasNoSky()) {
         for(var5 = var3; var5 <= var4; ++var5) {
            this.checkLightFor(EnumSkyBlock.SKY, new BlockPos(var1, var5, var2));
         }
      }

      this.markBlockRangeForRenderUpdate(var1, var3, var2, var1, var4, var2);
   }

   public void setRainStrength(float var1) {
      this.prevRainingStrength = var1;
      this.rainingStrength = var1;
   }

   public void updateAllPlayersSleepingFlag() {
   }

   public float getStarBrightness(float var1) {
      float var2 = this.getCelestialAngle(var1);
      float var3 = 1.0F - (MathHelper.cos(var2 * 3.1415927F * 2.0F) * 2.0F + 0.25F);
      var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
      return var3 * var3 * 0.5F;
   }

   public Entity getEntityByID(int var1) {
      return (Entity)this.entitiesById.lookup(var1);
   }

   public int getStrongPower(BlockPos var1) {
      byte var2 = 0;
      int var3 = Math.max(var2, this.getStrongPower(var1.offsetDown(), EnumFacing.DOWN));
      if (var3 >= 15) {
         return var3;
      } else {
         var3 = Math.max(var3, this.getStrongPower(var1.offsetUp(), EnumFacing.UP));
         if (var3 >= 15) {
            return var3;
         } else {
            var3 = Math.max(var3, this.getStrongPower(var1.offsetNorth(), EnumFacing.NORTH));
            if (var3 >= 15) {
               return var3;
            } else {
               var3 = Math.max(var3, this.getStrongPower(var1.offsetSouth(), EnumFacing.SOUTH));
               if (var3 >= 15) {
                  return var3;
               } else {
                  var3 = Math.max(var3, this.getStrongPower(var1.offsetWest(), EnumFacing.WEST));
                  if (var3 >= 15) {
                     return var3;
                  } else {
                     var3 = Math.max(var3, this.getStrongPower(var1.offsetEast(), EnumFacing.EAST));
                     return var3 >= 15 ? var3 : var3;
                  }
               }
            }
         }
      }
   }

   public boolean tickUpdates(boolean var1) {
      return false;
   }

   public boolean isBlockPowered(BlockPos var1) {
      return this.getRedstonePower(var1.offsetDown(), EnumFacing.DOWN) > 0 ? true : (this.getRedstonePower(var1.offsetUp(), EnumFacing.UP) > 0 ? true : (this.getRedstonePower(var1.offsetNorth(), EnumFacing.NORTH) > 0 ? true : (this.getRedstonePower(var1.offsetSouth(), EnumFacing.SOUTH) > 0 ? true : (this.getRedstonePower(var1.offsetWest(), EnumFacing.WEST) > 0 ? true : this.getRedstonePower(var1.offsetEast(), EnumFacing.EAST) > 0))));
   }

   public boolean isAreaLoaded(BlockPos var1, int var2) {
      return this.isAreaLoaded(var1, var2, true);
   }

   private boolean func_175696_F(BlockPos var1) {
      return this.getBlockState(var1).getBlock().getMaterial() == Material.water;
   }

   public Calendar getCurrentDate() {
      if (this.getTotalWorldTime() % 600L == 0L) {
         this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
      }

      return this.theCalendar;
   }

   protected void onEntityRemoved(Entity var1) {
      for(int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
         ((IWorldAccess)this.worldAccesses.get(var2)).onEntityRemoved(var1);
      }

   }

   public boolean handleMaterialAcceleration(AxisAlignedBB var1, Material var2, Entity var3) {
      int var4 = MathHelper.floor_double(var1.minX);
      int var5 = MathHelper.floor_double(var1.maxX + 1.0D);
      int var6 = MathHelper.floor_double(var1.minY);
      int var7 = MathHelper.floor_double(var1.maxY + 1.0D);
      int var8 = MathHelper.floor_double(var1.minZ);
      int var9 = MathHelper.floor_double(var1.maxZ + 1.0D);
      if (!this.isAreaLoaded(var4, var6, var8, var5, var7, var9, true)) {
         return false;
      } else {
         boolean var10 = false;
         Vec3 var11 = new Vec3(0.0D, 0.0D, 0.0D);

         for(int var12 = var4; var12 < var5; ++var12) {
            for(int var13 = var6; var13 < var7; ++var13) {
               for(int var14 = var8; var14 < var9; ++var14) {
                  BlockPos var15 = new BlockPos(var12, var13, var14);
                  IBlockState var16 = this.getBlockState(var15);
                  Block var17 = var16.getBlock();
                  if (var17.getMaterial() == var2) {
                     double var18 = (double)((float)(var13 + 1) - BlockLiquid.getLiquidHeightPercent((Integer)var16.getValue(BlockLiquid.LEVEL)));
                     if ((double)var7 >= var18) {
                        var10 = true;
                        var11 = var17.modifyAcceleration(this, var15, var3, var11);
                     }
                  }
               }
            }
         }

         if (var11.lengthVector() > 0.0D && var3.isPushedByWater()) {
            var11 = var11.normalize();
            double var20 = 0.014D;
            var3.motionX += var11.xCoord * var20;
            var3.motionY += var11.yCoord * var20;
            var3.motionZ += var11.zCoord * var20;
         }

         return var10;
      }
   }

   public void updateComparatorOutputLevel(BlockPos var1, Block var2) {
      Iterator var3 = EnumFacing.Plane.HORIZONTAL.iterator();

      while(var3.hasNext()) {
         EnumFacing var4 = (EnumFacing)var3.next();
         BlockPos var5 = var1.offset(var4);
         if (this.isBlockLoaded(var5)) {
            IBlockState var6 = this.getBlockState(var5);
            if (Blocks.unpowered_comparator.func_149907_e(var6.getBlock())) {
               var6.getBlock().onNeighborBlockChange(this, var5, var6, var2);
            } else if (var6.getBlock().isNormalCube()) {
               var5 = var5.offset(var4);
               var6 = this.getBlockState(var5);
               if (Blocks.unpowered_comparator.func_149907_e(var6.getBlock())) {
                  var6.getBlock().onNeighborBlockChange(this, var5, var6, var2);
               }
            }
         }
      }

   }

   public float getBlockDensity(Vec3 var1, AxisAlignedBB var2) {
      double var3 = 1.0D / ((var2.maxX - var2.minX) * 2.0D + 1.0D);
      double var5 = 1.0D / ((var2.maxY - var2.minY) * 2.0D + 1.0D);
      double var7 = 1.0D / ((var2.maxZ - var2.minZ) * 2.0D + 1.0D);
      if (var3 >= 0.0D && var5 >= 0.0D && var7 >= 0.0D) {
         int var9 = 0;
         int var10 = 0;

         for(float var11 = 0.0F; var11 <= 1.0F; var11 = (float)((double)var11 + var3)) {
            for(float var12 = 0.0F; var12 <= 1.0F; var12 = (float)((double)var12 + var5)) {
               for(float var13 = 0.0F; var13 <= 1.0F; var13 = (float)((double)var13 + var7)) {
                  double var14 = var2.minX + (var2.maxX - var2.minX) * (double)var11;
                  double var16 = var2.minY + (var2.maxY - var2.minY) * (double)var12;
                  double var18 = var2.minZ + (var2.maxZ - var2.minZ) * (double)var13;
                  if (this.rayTraceBlocks(new Vec3(var14, var16, var18), var1) == null) {
                     ++var9;
                  }

                  ++var10;
               }
            }
         }

         return (float)var9 / (float)var10;
      } else {
         return 0.0F;
      }
   }

   public void func_175669_a(int var1, BlockPos var2, int var3) {
      for(int var4 = 0; var4 < this.worldAccesses.size(); ++var4) {
         ((IWorldAccess)this.worldAccesses.get(var4)).func_180440_a(var1, var2, var3);
      }

   }

   protected void onEntityAdded(Entity var1) {
      for(int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
         ((IWorldAccess)this.worldAccesses.get(var2)).onEntityAdded(var1);
      }

   }

   protected boolean isChunkLoaded(int var1, int var2, boolean var3) {
      return this.chunkProvider.chunkExists(var1, var2) && (var3 || !this.chunkProvider.provideChunk(var1, var2).isEmpty());
   }

   public float getCelestialAngleRadians(float var1) {
      float var2 = this.getCelestialAngle(var1);
      return var2 * 3.1415927F * 2.0F;
   }

   public IBlockState getBlockState(BlockPos var1) {
      if (!this.isValid(var1)) {
         return Blocks.air.getDefaultState();
      } else {
         Chunk var2 = this.getChunkFromBlockCoords(var1);
         return var2.getBlockState(var1);
      }
   }

   public void setThunderStrength(float var1) {
      this.prevThunderingStrength = var1;
      this.thunderingStrength = var1;
   }

   public Explosion createExplosion(Entity var1, double var2, double var4, double var6, float var8, boolean var9) {
      return this.newExplosion(var1, var2, var4, var6, var8, false, var9);
   }

   public boolean isBlockTickPending(BlockPos var1, Block var2) {
      return false;
   }

   public WorldChunkManager getWorldChunkManager() {
      return this.provider.getWorldChunkManager();
   }

   public EntityPlayer getPlayerEntityByUUID(UUID var1) {
      for(int var2 = 0; var2 < this.playerEntities.size(); ++var2) {
         EntityPlayer var3 = (EntityPlayer)this.playerEntities.get(var2);
         if (var1.equals(var3.getUniqueID())) {
            return var3;
         }
      }

      return null;
   }

   public void notifyNeighborsOfStateChange(BlockPos var1, Block var2) {
      this.notifyBlockOfStateChange(var1.offsetWest(), var2);
      this.notifyBlockOfStateChange(var1.offsetEast(), var2);
      this.notifyBlockOfStateChange(var1.offsetDown(), var2);
      this.notifyBlockOfStateChange(var1.offsetUp(), var2);
      this.notifyBlockOfStateChange(var1.offsetNorth(), var2);
      this.notifyBlockOfStateChange(var1.offsetSouth(), var2);
   }
}
