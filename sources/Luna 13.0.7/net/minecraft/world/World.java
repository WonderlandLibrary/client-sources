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
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
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
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import space.lunaclient.luna.impl.elements.render.hud.HUD;

public abstract class World
  implements IBlockAccess
{
  protected boolean scheduledUpdatesAreImmediate;
  public final List loadedEntityList = Lists.newArrayList();
  protected final List unloadedEntityList = Lists.newArrayList();
  public final List loadedTileEntityList = Lists.newArrayList();
  public final List tickableTileEntities = Lists.newArrayList();
  private final List addedTileEntityList = Lists.newArrayList();
  private final List tileEntitiesToBeRemoved = Lists.newArrayList();
  public final List playerEntities = Lists.newArrayList();
  public final List weatherEffects = Lists.newArrayList();
  protected final IntHashMap entitiesById = new IntHashMap();
  private long cloudColour = 16777215L;
  private int skylightSubtracted;
  protected int updateLCG = new Random().nextInt();
  protected final int DIST_HASH_MAGIC = 1013904223;
  protected float prevRainingStrength;
  protected float rainingStrength;
  protected float prevThunderingStrength;
  protected float thunderingStrength;
  private int lastLightningBolt;
  public final Random rand = new Random();
  public final WorldProvider provider;
  protected List worldAccesses = Lists.newArrayList();
  protected IChunkProvider chunkProvider;
  protected final ISaveHandler saveHandler;
  protected WorldInfo worldInfo;
  protected boolean findingSpawnPoint;
  protected MapStorage mapStorage;
  protected VillageCollection villageCollectionObj;
  public final Profiler theProfiler;
  private final Calendar theCalendar = Calendar.getInstance();
  protected Scoreboard worldScoreboard = new Scoreboard();
  public final boolean isRemote;
  protected Set activeChunkSet = Sets.newHashSet();
  private int ambientTickCountdown;
  protected boolean spawnHostileMobs;
  protected boolean spawnPeacefulMobs;
  private boolean processingLoadedTiles;
  private final WorldBorder worldBorder;
  int[] lightUpdateBlockList;
  private static final String __OBFID = "CL_00000140";
  
  protected World(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client)
  {
    this.ambientTickCountdown = this.rand.nextInt(12000);
    this.spawnHostileMobs = true;
    this.spawnPeacefulMobs = true;
    this.lightUpdateBlockList = new int[32768];
    this.saveHandler = saveHandlerIn;
    this.theProfiler = profilerIn;
    this.worldInfo = info;
    this.provider = providerIn;
    this.isRemote = client;
    this.worldBorder = providerIn.getWorldBorder();
  }
  
  public World init()
  {
    return this;
  }
  
  public BiomeGenBase getBiomeGenForCoords(final BlockPos pos)
  {
    if (isBlockLoaded(pos))
    {
      Chunk var2 = getChunkFromBlockCoords(pos);
      try
      {
        return var2.getBiome(pos, this.provider.getWorldChunkManager());
      }
      catch (Throwable var6)
      {
        CrashReport var4 = CrashReport.makeCrashReport(var6, "Getting biome");
        CrashReportCategory var5 = var4.makeCategory("Coordinates of biome request");
        var5.addCrashSectionCallable("Location", new Callable()
        {
          private static final String __OBFID = "CL_00000141";
          
          public String call()
          {
            return CrashReportCategory.getCoordinateInfo(pos);
          }
        });
        throw new ReportedException(var4);
      }
    }
    return this.provider.getWorldChunkManager().func_180300_a(pos, BiomeGenBase.plains);
  }
  
  public WorldChunkManager getWorldChunkManager()
  {
    return this.provider.getWorldChunkManager();
  }
  
  protected abstract IChunkProvider createChunkProvider();
  
  public void initialize(WorldSettings settings)
  {
    this.worldInfo.setServerInitialized(true);
  }
  
  public void setInitialSpawnLocation()
  {
    setSpawnLocation(new BlockPos(8, 64, 8));
  }
  
  public Block getGroundAboveSeaLevel(BlockPos pos)
  {
    for (BlockPos var2 = new BlockPos(pos.getX(), 63, pos.getZ()); !isAirBlock(var2.offsetUp()); var2 = var2.offsetUp()) {}
    return getBlockState(var2).getBlock();
  }
  
  private boolean isValid(BlockPos pos)
  {
    return (pos.getX() >= -30000000) && (pos.getZ() >= -30000000) && (pos.getX() < 30000000) && (pos.getZ() < 30000000) && (pos.getY() >= 0) && (pos.getY() < 256);
  }
  
  public boolean isAirBlock(BlockPos pos)
  {
    return getBlockState(pos).getBlock().getMaterial() == Material.air;
  }
  
  public boolean isBlockLoaded(BlockPos pos)
  {
    return isBlockLoaded(pos, true);
  }
  
  public boolean isBlockLoaded(BlockPos pos, boolean p_175668_2_)
  {
    return (isValid(pos)) && (isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4, p_175668_2_));
  }
  
  public boolean isAreaLoaded(BlockPos p_175697_1_, int radius)
  {
    return isAreaLoaded(p_175697_1_, radius, true);
  }
  
  public boolean isAreaLoaded(BlockPos p_175648_1_, int radius, boolean p_175648_3_)
  {
    return isAreaLoaded(p_175648_1_.getX() - radius, p_175648_1_.getY() - radius, p_175648_1_.getZ() - radius, p_175648_1_.getX() + radius, p_175648_1_.getY() + radius, p_175648_1_.getZ() + radius, p_175648_3_);
  }
  
  public boolean isAreaLoaded(BlockPos p_175707_1_, BlockPos p_175707_2_)
  {
    return isAreaLoaded(p_175707_1_, p_175707_2_, true);
  }
  
  public boolean isAreaLoaded(BlockPos p_175706_1_, BlockPos p_175706_2_, boolean p_175706_3_)
  {
    return isAreaLoaded(p_175706_1_.getX(), p_175706_1_.getY(), p_175706_1_.getZ(), p_175706_2_.getX(), p_175706_2_.getY(), p_175706_2_.getZ(), p_175706_3_);
  }
  
  public boolean isAreaLoaded(StructureBoundingBox p_175711_1_)
  {
    return isAreaLoaded(p_175711_1_, true);
  }
  
  public boolean isAreaLoaded(StructureBoundingBox p_175639_1_, boolean p_175639_2_)
  {
    return isAreaLoaded(p_175639_1_.minX, p_175639_1_.minY, p_175639_1_.minZ, p_175639_1_.maxX, p_175639_1_.maxY, p_175639_1_.maxZ, p_175639_2_);
  }
  
  private boolean isAreaLoaded(int p_175663_1_, int p_175663_2_, int p_175663_3_, int p_175663_4_, int p_175663_5_, int p_175663_6_, boolean p_175663_7_)
  {
    if ((p_175663_5_ >= 0) && (p_175663_2_ < 256))
    {
      p_175663_1_ >>= 4;
      p_175663_3_ >>= 4;
      p_175663_4_ >>= 4;
      p_175663_6_ >>= 4;
      for (int var8 = p_175663_1_; var8 <= p_175663_4_; var8++) {
        for (int var9 = p_175663_3_; var9 <= p_175663_6_; var9++) {
          if (!isChunkLoaded(var8, var9, p_175663_7_)) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }
  
  protected boolean isChunkLoaded(int x, int z, boolean allowEmpty)
  {
    return (this.chunkProvider.chunkExists(x, z)) && ((allowEmpty) || (!this.chunkProvider.provideChunk(x, z).isEmpty()));
  }
  
  public Chunk getChunkFromBlockCoords(BlockPos pos)
  {
    return getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4);
  }
  
  public Chunk getChunkFromChunkCoords(int chunkX, int chunkZ)
  {
    return this.chunkProvider.provideChunk(chunkX, chunkZ);
  }
  
  public boolean setBlockState(BlockPos pos, IBlockState newState, int flags)
  {
    if (!isValid(pos)) {
      return false;
    }
    if ((!this.isRemote) && (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD)) {
      return false;
    }
    Chunk var4 = getChunkFromBlockCoords(pos);
    Block var5 = newState.getBlock();
    IBlockState var6 = var4.setBlockState(pos, newState);
    if (var6 == null) {
      return false;
    }
    Block var7 = var6.getBlock();
    if ((var5.getLightOpacity() != var7.getLightOpacity()) || (var5.getLightValue() != var7.getLightValue()))
    {
      this.theProfiler.startSection("checkLight");
      checkLight(pos);
      this.theProfiler.endSection();
    }
    if (((flags & 0x2) != 0) && ((!this.isRemote) || ((flags & 0x4) == 0)) && (var4.isPopulated())) {
      markBlockForUpdate(pos);
    }
    if ((!this.isRemote) && ((flags & 0x1) != 0))
    {
      func_175722_b(pos, var6.getBlock());
      if (var5.hasComparatorInputOverride()) {
        updateComparatorOutputLevel(pos, var5);
      }
    }
    return true;
  }
  
  public boolean setBlockToAir(BlockPos pos)
  {
    return setBlockState(pos, Blocks.air.getDefaultState(), 3);
  }
  
  public boolean destroyBlock(BlockPos pos, boolean dropBlock)
  {
    IBlockState var3 = getBlockState(pos);
    Block var4 = var3.getBlock();
    if (var4.getMaterial() == Material.air) {
      return false;
    }
    playAuxSFX(2001, pos, Block.getStateId(var3));
    if (dropBlock) {
      var4.dropBlockAsItem(this, pos, var3, 0);
    }
    return setBlockState(pos, Blocks.air.getDefaultState(), 3);
  }
  
  public boolean setBlockState(BlockPos pos, IBlockState state)
  {
    return setBlockState(pos, state, 3);
  }
  
  public void markBlockForUpdate(BlockPos pos)
  {
    for (int var2 = 0; var2 < this.worldAccesses.size(); var2++) {
      ((IWorldAccess)this.worldAccesses.get(var2)).markBlockForUpdate(pos);
    }
  }
  
  public void func_175722_b(BlockPos pos, Block blockType)
  {
    if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
      notifyNeighborsOfStateChange(pos, blockType);
    }
  }
  
  public void markBlocksDirtyVertical(int x1, int z1, int x2, int z2)
  {
    if (x2 > z2)
    {
      int var5 = z2;
      z2 = x2;
      x2 = var5;
    }
    if (!this.provider.getHasNoSky()) {
      for (int var5 = x2; var5 <= z2; var5++) {
        checkLightFor(EnumSkyBlock.SKY, new BlockPos(x1, var5, z1));
      }
    }
    markBlockRangeForRenderUpdate(x1, x2, z1, x1, z2, z1);
  }
  
  public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax)
  {
    markBlockRangeForRenderUpdate(rangeMin.getX(), rangeMin.getY(), rangeMin.getZ(), rangeMax.getX(), rangeMax.getY(), rangeMax.getZ());
  }
  
  public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2)
  {
    for (int var7 = 0; var7 < this.worldAccesses.size(); var7++) {
      ((IWorldAccess)this.worldAccesses.get(var7)).markBlockRangeForRenderUpdate(x1, y1, z1, x2, y2, z2);
    }
  }
  
  public void notifyNeighborsOfStateChange(BlockPos pos, Block blockType)
  {
    notifyBlockOfStateChange(pos.offsetWest(), blockType);
    notifyBlockOfStateChange(pos.offsetEast(), blockType);
    notifyBlockOfStateChange(pos.offsetDown(), blockType);
    notifyBlockOfStateChange(pos.offsetUp(), blockType);
    notifyBlockOfStateChange(pos.offsetNorth(), blockType);
    notifyBlockOfStateChange(pos.offsetSouth(), blockType);
  }
  
  public void notifyNeighborsOfStateExcept(BlockPos pos, Block blockType, EnumFacing skipSide)
  {
    if (skipSide != EnumFacing.WEST) {
      notifyBlockOfStateChange(pos.offsetWest(), blockType);
    }
    if (skipSide != EnumFacing.EAST) {
      notifyBlockOfStateChange(pos.offsetEast(), blockType);
    }
    if (skipSide != EnumFacing.DOWN) {
      notifyBlockOfStateChange(pos.offsetDown(), blockType);
    }
    if (skipSide != EnumFacing.UP) {
      notifyBlockOfStateChange(pos.offsetUp(), blockType);
    }
    if (skipSide != EnumFacing.NORTH) {
      notifyBlockOfStateChange(pos.offsetNorth(), blockType);
    }
    if (skipSide != EnumFacing.SOUTH) {
      notifyBlockOfStateChange(pos.offsetSouth(), blockType);
    }
  }
  
  public void notifyBlockOfStateChange(BlockPos pos, final Block blockIn)
  {
    if (!this.isRemote)
    {
      IBlockState var3 = getBlockState(pos);
      try
      {
        var3.getBlock().onNeighborBlockChange(this, pos, var3, blockIn);
      }
      catch (Throwable var7)
      {
        CrashReport var5 = CrashReport.makeCrashReport(var7, "Exception while updating neighbours");
        CrashReportCategory var6 = var5.makeCategory("Block being updated");
        var6.addCrashSectionCallable("Source block type", new Callable()
        {
          private static final String __OBFID = "CL_00000142";
          
          public String call()
          {
            try
            {
              return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(Block.getIdFromBlock(blockIn)), blockIn.getUnlocalizedName(), blockIn.getClass().getCanonicalName() });
            }
            catch (Throwable var2) {}
            return "ID #" + Block.getIdFromBlock(blockIn);
          }
        });
        CrashReportCategory.addBlockInfo(var6, pos, var3);
        throw new ReportedException(var5);
      }
    }
  }
  
  public boolean isBlockTickPending(BlockPos pos, Block blockType)
  {
    return false;
  }
  
  public boolean isAgainstSky(BlockPos pos)
  {
    return getChunkFromBlockCoords(pos).canSeeSky(pos);
  }
  
  public boolean canBlockSeeSky(BlockPos pos)
  {
    if (pos.getY() >= 63) {
      return isAgainstSky(pos);
    }
    BlockPos var2 = new BlockPos(pos.getX(), 63, pos.getZ());
    if (!isAgainstSky(var2)) {
      return false;
    }
    for (var2 = var2.offsetDown(); var2.getY() > pos.getY(); var2 = var2.offsetDown())
    {
      Block var3 = getBlockState(var2).getBlock();
      if ((var3.getLightOpacity() > 0) && (!var3.getMaterial().isLiquid())) {
        return false;
      }
    }
    return true;
  }
  
  public int getLight(BlockPos pos)
  {
    if (pos.getY() < 0) {
      return 0;
    }
    if (pos.getY() >= 256) {
      pos = new BlockPos(pos.getX(), 255, pos.getZ());
    }
    return getChunkFromBlockCoords(pos).setLight(pos, 0);
  }
  
  public int getLightFromNeighbors(BlockPos pos)
  {
    return getLight(pos, true);
  }
  
  public int getLight(BlockPos pos, boolean checkNeighbors)
  {
    if ((pos.getX() >= -30000000) && (pos.getZ() >= -30000000) && (pos.getX() < 30000000) && (pos.getZ() < 30000000))
    {
      if ((checkNeighbors) && (getBlockState(pos).getBlock().getUseNeighborBrightness()))
      {
        int var8 = getLight(pos.offsetUp(), false);
        int var4 = getLight(pos.offsetEast(), false);
        int var5 = getLight(pos.offsetWest(), false);
        int var6 = getLight(pos.offsetSouth(), false);
        int var7 = getLight(pos.offsetNorth(), false);
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
      }
      if (pos.getY() < 0) {
        return 0;
      }
      if (pos.getY() >= 256) {
        pos = new BlockPos(pos.getX(), 255, pos.getZ());
      }
      Chunk var3 = getChunkFromBlockCoords(pos);
      return var3.setLight(pos, this.skylightSubtracted);
    }
    return 15;
  }
  
  public BlockPos getHorizon(BlockPos pos)
  {
    int var2;
    int var2;
    if ((pos.getX() >= -30000000) && (pos.getZ() >= -30000000) && (pos.getX() < 30000000) && (pos.getZ() < 30000000))
    {
      int var2;
      if (isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4, true)) {
        var2 = getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4).getHeight(pos.getX() & 0xF, pos.getZ() & 0xF);
      } else {
        var2 = 0;
      }
    }
    else
    {
      var2 = 64;
    }
    return new BlockPos(pos.getX(), var2, pos.getZ());
  }
  
  public int getChunksLowestHorizon(int x, int z)
  {
    if ((x >= -30000000) && (z >= -30000000) && (x < 30000000) && (z < 30000000))
    {
      if (!isChunkLoaded(x >> 4, z >> 4, true)) {
        return 0;
      }
      Chunk var3 = getChunkFromChunkCoords(x >> 4, z >> 4);
      return var3.getLowestHeight();
    }
    return 64;
  }
  
  public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos p_175705_2_)
  {
    if ((this.provider.getHasNoSky()) && (type == EnumSkyBlock.SKY)) {
      return 0;
    }
    if (p_175705_2_.getY() < 0) {
      p_175705_2_ = new BlockPos(p_175705_2_.getX(), 0, p_175705_2_.getZ());
    }
    if (!isValid(p_175705_2_)) {
      return type.defaultLightValue;
    }
    if (!isBlockLoaded(p_175705_2_)) {
      return type.defaultLightValue;
    }
    if (getBlockState(p_175705_2_).getBlock().getUseNeighborBrightness())
    {
      int var8 = getLightFor(type, p_175705_2_.offsetUp());
      int var4 = getLightFor(type, p_175705_2_.offsetEast());
      int var5 = getLightFor(type, p_175705_2_.offsetWest());
      int var6 = getLightFor(type, p_175705_2_.offsetSouth());
      int var7 = getLightFor(type, p_175705_2_.offsetNorth());
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
    }
    Chunk var3 = getChunkFromBlockCoords(p_175705_2_);
    return var3.getLightFor(type, p_175705_2_);
  }
  
  public int getLightFor(EnumSkyBlock type, BlockPos pos)
  {
    if (pos.getY() < 0) {
      pos = new BlockPos(pos.getX(), 0, pos.getZ());
    }
    if (!isValid(pos)) {
      return type.defaultLightValue;
    }
    if (!isBlockLoaded(pos)) {
      return type.defaultLightValue;
    }
    Chunk var3 = getChunkFromBlockCoords(pos);
    return var3.getLightFor(type, pos);
  }
  
  public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue)
  {
    if ((isValid(pos)) && 
      (isBlockLoaded(pos)))
    {
      Chunk var4 = getChunkFromBlockCoords(pos);
      var4.setLightFor(type, pos, lightValue);
      notifyLightSet(pos);
    }
  }
  
  public void notifyLightSet(BlockPos pos)
  {
    for (int var2 = 0; var2 < this.worldAccesses.size(); var2++) {
      ((IWorldAccess)this.worldAccesses.get(var2)).notifyLightSet(pos);
    }
  }
  
  public int getCombinedLight(BlockPos p_175626_1_, int p_175626_2_)
  {
    int var3 = getLightFromNeighborsFor(EnumSkyBlock.SKY, p_175626_1_);
    int var4 = getLightFromNeighborsFor(EnumSkyBlock.BLOCK, p_175626_1_);
    if (var4 < p_175626_2_) {
      var4 = p_175626_2_;
    }
    return var3 << 20 | var4 << 4;
  }
  
  public float getLightBrightness(BlockPos pos)
  {
    return this.provider.getLightBrightnessTable()[getLightFromNeighbors(pos)];
  }
  
  public IBlockState getBlockState(BlockPos pos)
  {
    if (!isValid(pos)) {
      return Blocks.air.getDefaultState();
    }
    Chunk var2 = getChunkFromBlockCoords(pos);
    return var2.getBlockState(pos);
  }
  
  public boolean isDaytime()
  {
    return this.skylightSubtracted < 4;
  }
  
  public MovingObjectPosition rayTraceBlocks(Vec3 p_72933_1_, Vec3 p_72933_2_)
  {
    return rayTraceBlocks(p_72933_1_, p_72933_2_, false, false, false);
  }
  
  public MovingObjectPosition rayTraceBlocks(Vec3 p_72901_1_, Vec3 p_72901_2_, boolean p_72901_3_)
  {
    return rayTraceBlocks(p_72901_1_, p_72901_2_, p_72901_3_, false, false);
  }
  
  public MovingObjectPosition rayTraceBlocks(Vec3 p_147447_1_, Vec3 p_147447_2_, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_)
  {
    if ((!Double.isNaN(p_147447_1_.xCoord)) && (!Double.isNaN(p_147447_1_.yCoord)) && (!Double.isNaN(p_147447_1_.zCoord)))
    {
      if ((!Double.isNaN(p_147447_2_.xCoord)) && (!Double.isNaN(p_147447_2_.yCoord)) && (!Double.isNaN(p_147447_2_.zCoord)))
      {
        int var6 = MathHelper.floor_double(p_147447_2_.xCoord);
        int var7 = MathHelper.floor_double(p_147447_2_.yCoord);
        int var8 = MathHelper.floor_double(p_147447_2_.zCoord);
        int var9 = MathHelper.floor_double(p_147447_1_.xCoord);
        int var10 = MathHelper.floor_double(p_147447_1_.yCoord);
        int var11 = MathHelper.floor_double(p_147447_1_.zCoord);
        BlockPos var12 = new BlockPos(var9, var10, var11);
        new BlockPos(var6, var7, var8);
        IBlockState var14 = getBlockState(var12);
        Block var15 = var14.getBlock();
        if (((!p_147447_4_) || (var15.getCollisionBoundingBox(this, var12, var14) != null)) && (var15.canCollideCheck(var14, p_147447_3_)))
        {
          MovingObjectPosition var16 = var15.collisionRayTrace(this, var12, p_147447_1_, p_147447_2_);
          if (var16 != null) {
            return var16;
          }
        }
        MovingObjectPosition var41 = null;
        int var42 = 200;
        while (var42-- >= 0)
        {
          if ((Double.isNaN(p_147447_1_.xCoord)) || (Double.isNaN(p_147447_1_.yCoord)) || (Double.isNaN(p_147447_1_.zCoord))) {
            return null;
          }
          if ((var9 == var6) && (var10 == var7) && (var11 == var8)) {
            return p_147447_5_ ? var41 : null;
          }
          boolean var43 = true;
          boolean var17 = true;
          boolean var18 = true;
          double var19 = 999.0D;
          double var21 = 999.0D;
          double var23 = 999.0D;
          if (var6 > var9) {
            var19 = var9 + 1.0D;
          } else if (var6 < var9) {
            var19 = var9 + 0.0D;
          } else {
            var43 = false;
          }
          if (var7 > var10) {
            var21 = var10 + 1.0D;
          } else if (var7 < var10) {
            var21 = var10 + 0.0D;
          } else {
            var17 = false;
          }
          if (var8 > var11) {
            var23 = var11 + 1.0D;
          } else if (var8 < var11) {
            var23 = var11 + 0.0D;
          } else {
            var18 = false;
          }
          double var25 = 999.0D;
          double var27 = 999.0D;
          double var29 = 999.0D;
          double var31 = p_147447_2_.xCoord - p_147447_1_.xCoord;
          double var33 = p_147447_2_.yCoord - p_147447_1_.yCoord;
          double var35 = p_147447_2_.zCoord - p_147447_1_.zCoord;
          if (var43) {
            var25 = (var19 - p_147447_1_.xCoord) / var31;
          }
          if (var17) {
            var27 = (var21 - p_147447_1_.yCoord) / var33;
          }
          if (var18) {
            var29 = (var23 - p_147447_1_.zCoord) / var35;
          }
          if (var25 == -0.0D) {
            var25 = -1.0E-4D;
          }
          if (var27 == -0.0D) {
            var27 = -1.0E-4D;
          }
          if (var29 == -0.0D) {
            var29 = -1.0E-4D;
          }
          EnumFacing var37;
          if ((var25 < var27) && (var25 < var29))
          {
            EnumFacing var37 = var6 > var9 ? EnumFacing.WEST : EnumFacing.EAST;
            p_147447_1_ = new Vec3(var19, p_147447_1_.yCoord + var33 * var25, p_147447_1_.zCoord + var35 * var25);
          }
          else if (var27 < var29)
          {
            EnumFacing var37 = var7 > var10 ? EnumFacing.DOWN : EnumFacing.UP;
            p_147447_1_ = new Vec3(p_147447_1_.xCoord + var31 * var27, var21, p_147447_1_.zCoord + var35 * var27);
          }
          else
          {
            var37 = var8 > var11 ? EnumFacing.NORTH : EnumFacing.SOUTH;
            p_147447_1_ = new Vec3(p_147447_1_.xCoord + var31 * var29, p_147447_1_.yCoord + var33 * var29, var23);
          }
          var9 = MathHelper.floor_double(p_147447_1_.xCoord) - (var37 == EnumFacing.EAST ? 1 : 0);
          var10 = MathHelper.floor_double(p_147447_1_.yCoord) - (var37 == EnumFacing.UP ? 1 : 0);
          var11 = MathHelper.floor_double(p_147447_1_.zCoord) - (var37 == EnumFacing.SOUTH ? 1 : 0);
          var12 = new BlockPos(var9, var10, var11);
          IBlockState var38 = getBlockState(var12);
          Block var39 = var38.getBlock();
          if ((!p_147447_4_) || (var39.getCollisionBoundingBox(this, var12, var38) != null)) {
            if (var39.canCollideCheck(var38, p_147447_3_))
            {
              MovingObjectPosition var40 = var39.collisionRayTrace(this, var12, p_147447_1_, p_147447_2_);
              if (var40 != null) {
                return var40;
              }
            }
            else
            {
              var41 = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, p_147447_1_, var37, var12);
            }
          }
        }
        return p_147447_5_ ? var41 : null;
      }
      return null;
    }
    return null;
  }
  
  public void playSoundAtEntity(Entity p_72956_1_, String p_72956_2_, float p_72956_3_, float p_72956_4_)
  {
    for (int var5 = 0; var5 < this.worldAccesses.size(); var5++) {
      ((IWorldAccess)this.worldAccesses.get(var5)).playSound(p_72956_2_, p_72956_1_.posX, p_72956_1_.posY, p_72956_1_.posZ, p_72956_3_, p_72956_4_);
    }
  }
  
  public void playSoundToNearExcept(EntityPlayer p_85173_1_, String p_85173_2_, float p_85173_3_, float p_85173_4_)
  {
    for (int var5 = 0; var5 < this.worldAccesses.size(); var5++) {
      ((IWorldAccess)this.worldAccesses.get(var5)).playSoundToNearExcept(p_85173_1_, p_85173_2_, p_85173_1_.posX, p_85173_1_.posY, p_85173_1_.posZ, p_85173_3_, p_85173_4_);
    }
  }
  
  public void playSoundEffect(double x, double y, double z, String soundName, float volume, float pitch)
  {
    for (int var10 = 0; var10 < this.worldAccesses.size(); var10++) {
      ((IWorldAccess)this.worldAccesses.get(var10)).playSound(soundName, x, y, z, volume, pitch);
    }
  }
  
  public void playSound(double x, double y, double z, String soundName, float volume, float pitch, boolean distanceDelay) {}
  
  public void func_175717_a(BlockPos p_175717_1_, String p_175717_2_)
  {
    for (int var3 = 0; var3 < this.worldAccesses.size(); var3++) {
      ((IWorldAccess)this.worldAccesses.get(var3)).func_174961_a(p_175717_2_, p_175717_1_);
    }
  }
  
  public void spawnParticle(EnumParticleTypes p_175688_1_, double p_175688_2_, double p_175688_4_, double p_175688_6_, double p_175688_8_, double p_175688_10_, double p_175688_12_, int... p_175688_14_)
  {
    spawnParticle(p_175688_1_.func_179348_c(), p_175688_1_.func_179344_e(), p_175688_2_, p_175688_4_, p_175688_6_, p_175688_8_, p_175688_10_, p_175688_12_, p_175688_14_);
  }
  
  public void spawnParticle(EnumParticleTypes p_175682_1_, boolean p_175682_2_, double p_175682_3_, double p_175682_5_, double p_175682_7_, double p_175682_9_, double p_175682_11_, double p_175682_13_, int... p_175682_15_)
  {
    spawnParticle(p_175682_1_.func_179348_c(), p_175682_1_.func_179344_e() | p_175682_2_, p_175682_3_, p_175682_5_, p_175682_7_, p_175682_9_, p_175682_11_, p_175682_13_, p_175682_15_);
  }
  
  private void spawnParticle(int p_175720_1_, boolean p_175720_2_, double p_175720_3_, double p_175720_5_, double p_175720_7_, double p_175720_9_, double p_175720_11_, double p_175720_13_, int... p_175720_15_)
  {
    for (int var16 = 0; var16 < this.worldAccesses.size(); var16++) {
      ((IWorldAccess)this.worldAccesses.get(var16)).func_180442_a(p_175720_1_, p_175720_2_, p_175720_3_, p_175720_5_, p_175720_7_, p_175720_9_, p_175720_11_, p_175720_13_, p_175720_15_);
    }
  }
  
  public boolean addWeatherEffect(Entity p_72942_1_)
  {
    this.weatherEffects.add(p_72942_1_);
    return true;
  }
  
  public boolean spawnEntityInWorld(Entity p_72838_1_)
  {
    int var2 = MathHelper.floor_double(p_72838_1_.posX / 16.0D);
    int var3 = MathHelper.floor_double(p_72838_1_.posZ / 16.0D);
    boolean var4 = p_72838_1_.forceSpawn;
    if ((p_72838_1_ instanceof EntityPlayer)) {
      var4 = true;
    }
    if ((!var4) && (!isChunkLoaded(var2, var3, true))) {
      return false;
    }
    if ((p_72838_1_ instanceof EntityPlayer))
    {
      EntityPlayer var5 = (EntityPlayer)p_72838_1_;
      this.playerEntities.add(var5);
      updateAllPlayersSleepingFlag();
    }
    getChunkFromChunkCoords(var2, var3).addEntity(p_72838_1_);
    this.loadedEntityList.add(p_72838_1_);
    onEntityAdded(p_72838_1_);
    return true;
  }
  
  protected void onEntityAdded(Entity p_72923_1_)
  {
    for (int var2 = 0; var2 < this.worldAccesses.size(); var2++) {
      ((IWorldAccess)this.worldAccesses.get(var2)).onEntityAdded(p_72923_1_);
    }
  }
  
  protected void onEntityRemoved(Entity p_72847_1_)
  {
    for (int var2 = 0; var2 < this.worldAccesses.size(); var2++) {
      ((IWorldAccess)this.worldAccesses.get(var2)).onEntityRemoved(p_72847_1_);
    }
  }
  
  public void removeEntity(Entity p_72900_1_)
  {
    if (p_72900_1_.riddenByEntity != null) {
      p_72900_1_.riddenByEntity.mountEntity(null);
    }
    if (p_72900_1_.ridingEntity != null) {
      p_72900_1_.mountEntity(null);
    }
    p_72900_1_.setDead();
    if ((p_72900_1_ instanceof EntityPlayer))
    {
      this.playerEntities.remove(p_72900_1_);
      updateAllPlayersSleepingFlag();
      onEntityRemoved(p_72900_1_);
    }
  }
  
  public void removePlayerEntityDangerously(Entity p_72973_1_)
  {
    p_72973_1_.setDead();
    if ((p_72973_1_ instanceof EntityPlayer))
    {
      this.playerEntities.remove(p_72973_1_);
      updateAllPlayersSleepingFlag();
    }
    int var2 = p_72973_1_.chunkCoordX;
    int var3 = p_72973_1_.chunkCoordZ;
    if ((p_72973_1_.addedToChunk) && (isChunkLoaded(var2, var3, true))) {
      getChunkFromChunkCoords(var2, var3).removeEntity(p_72973_1_);
    }
    this.loadedEntityList.remove(p_72973_1_);
    onEntityRemoved(p_72973_1_);
  }
  
  public void addWorldAccess(IWorldAccess p_72954_1_)
  {
    this.worldAccesses.add(p_72954_1_);
  }
  
  public void removeWorldAccess(IWorldAccess p_72848_1_)
  {
    this.worldAccesses.remove(p_72848_1_);
  }
  
  public List getCollidingBoundingBoxes(Entity p_72945_1_, AxisAlignedBB p_72945_2_)
  {
    ArrayList var3 = Lists.newArrayList();
    int var4 = MathHelper.floor_double(p_72945_2_.minX);
    int var5 = MathHelper.floor_double(p_72945_2_.maxX + 1.0D);
    int var6 = MathHelper.floor_double(p_72945_2_.minY);
    int var7 = MathHelper.floor_double(p_72945_2_.maxY + 1.0D);
    int var8 = MathHelper.floor_double(p_72945_2_.minZ);
    int var9 = MathHelper.floor_double(p_72945_2_.maxZ + 1.0D);
    for (int var10 = var4; var10 < var5; var10++) {
      for (int var11 = var8; var11 < var9; var11++) {
        if (isBlockLoaded(new BlockPos(var10, 64, var11))) {
          for (int var12 = var6 - 1; var12 < var7; var12++)
          {
            BlockPos var13 = new BlockPos(var10, var12, var11);
            boolean var14 = p_72945_1_.isOutsideBorder();
            boolean var15 = isInsideBorder(getWorldBorder(), p_72945_1_);
            if ((var14) && (var15)) {
              p_72945_1_.setOutsideBorder(false);
            } else if ((!var14) && (!var15)) {
              p_72945_1_.setOutsideBorder(true);
            }
            IBlockState var16;
            IBlockState var16;
            if ((!getWorldBorder().contains(var13)) && (var15)) {
              var16 = Blocks.stone.getDefaultState();
            } else {
              var16 = getBlockState(var13);
            }
            var16.getBlock().addCollisionBoxesToList(this, var13, var16, p_72945_2_, var3, p_72945_1_);
          }
        }
      }
    }
    double var17 = 0.25D;
    List var18 = getEntitiesWithinAABBExcludingEntity(p_72945_1_, p_72945_2_.expand(var17, var17, var17));
    for (int var19 = 0; var19 < var18.size(); var19++) {
      if ((p_72945_1_.riddenByEntity != var18) && (p_72945_1_.ridingEntity != var18))
      {
        AxisAlignedBB var20 = ((Entity)var18.get(var19)).getBoundingBox();
        if ((var20 != null) && (var20.intersectsWith(p_72945_2_))) {
          var3.add(var20);
        }
        var20 = p_72945_1_.getCollisionBox((Entity)var18.get(var19));
        if ((var20 != null) && (var20.intersectsWith(p_72945_2_))) {
          var3.add(var20);
        }
      }
    }
    return var3;
  }
  
  public boolean isInsideBorder(WorldBorder p_175673_1_, Entity p_175673_2_)
  {
    double var3 = p_175673_1_.minX();
    double var5 = p_175673_1_.minZ();
    double var7 = p_175673_1_.maxX();
    double var9 = p_175673_1_.maxZ();
    if (p_175673_2_.isOutsideBorder())
    {
      var3 += 1.0D;
      var5 += 1.0D;
      var7 -= 1.0D;
      var9 -= 1.0D;
    }
    else
    {
      var3 -= 1.0D;
      var5 -= 1.0D;
      var7 += 1.0D;
      var9 += 1.0D;
    }
    return (p_175673_2_.posX > var3) && (p_175673_2_.posX < var7) && (p_175673_2_.posZ > var5) && (p_175673_2_.posZ < var9);
  }
  
  public List func_147461_a(AxisAlignedBB p_147461_1_)
  {
    ArrayList var2 = Lists.newArrayList();
    int var3 = MathHelper.floor_double(p_147461_1_.minX);
    int var4 = MathHelper.floor_double(p_147461_1_.maxX + 1.0D);
    int var5 = MathHelper.floor_double(p_147461_1_.minY);
    int var6 = MathHelper.floor_double(p_147461_1_.maxY + 1.0D);
    int var7 = MathHelper.floor_double(p_147461_1_.minZ);
    int var8 = MathHelper.floor_double(p_147461_1_.maxZ + 1.0D);
    for (int var9 = var3; var9 < var4; var9++) {
      for (int var10 = var7; var10 < var8; var10++) {
        if (isBlockLoaded(new BlockPos(var9, 64, var10))) {
          for (int var11 = var5 - 1; var11 < var6; var11++)
          {
            BlockPos var13 = new BlockPos(var9, var11, var10);
            IBlockState var12;
            IBlockState var12;
            if ((var9 >= -30000000) && (var9 < 30000000) && (var10 >= -30000000) && (var10 < 30000000)) {
              var12 = getBlockState(var13);
            } else {
              var12 = Blocks.bedrock.getDefaultState();
            }
            var12.getBlock().addCollisionBoxesToList(this, var13, var12, p_147461_1_, var2, null);
          }
        }
      }
    }
    return var2;
  }
  
  public int calculateSkylightSubtracted(float p_72967_1_)
  {
    float var2 = getCelestialAngle(p_72967_1_);
    float var3 = 1.0F - (MathHelper.cos(var2 * 3.1415927F * 2.0F) * 2.0F + 0.5F);
    var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
    var3 = 1.0F - var3;
    var3 = (float)(var3 * (1.0D - getRainStrength(p_72967_1_) * 5.0F / 16.0D));
    var3 = (float)(var3 * (1.0D - getWeightedThunderStrength(p_72967_1_) * 5.0F / 16.0D));
    var3 = 1.0F - var3;
    return (int)(var3 * 11.0F);
  }
  
  public float getSunBrightness(float p_72971_1_)
  {
    float var2 = getCelestialAngle(p_72971_1_);
    float var3 = 1.0F - (MathHelper.cos(var2 * 3.1415927F * 2.0F) * 2.0F + 0.2F);
    var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
    var3 = 1.0F - var3;
    var3 = (float)(var3 * (1.0D - getRainStrength(p_72971_1_) * 5.0F / 16.0D));
    var3 = (float)(var3 * (1.0D - getWeightedThunderStrength(p_72971_1_) * 5.0F / 16.0D));
    return var3 * 0.8F + 0.2F;
  }
  
  public Vec3 getSkyColor(Entity p_72833_1_, float p_72833_2_)
  {
    float var3 = getCelestialAngle(p_72833_2_);
    float var4 = MathHelper.cos(var3 * 3.1415927F * 2.0F) * 2.0F + 0.5F;
    var4 = MathHelper.clamp_float(var4, 0.0F, 1.0F);
    int var5 = MathHelper.floor_double(p_72833_1_.posX);
    int var6 = MathHelper.floor_double(p_72833_1_.posY);
    int var7 = MathHelper.floor_double(p_72833_1_.posZ);
    BlockPos var8 = new BlockPos(var5, var6, var7);
    BiomeGenBase var9 = getBiomeGenForCoords(var8);
    float var10 = var9.func_180626_a(var8);
    int var11 = var9.getSkyColorByTemp(var10);
    float var12 = (var11 >> 16 & 0xFF) / 255.0F;
    float var13 = (var11 >> 8 & 0xFF) / 255.0F;
    float var14 = (var11 & 0xFF) / 255.0F;
    var12 *= var4;
    var13 *= var4;
    var14 *= var4;
    float var15 = getRainStrength(p_72833_2_);
    if (var15 > 0.0F)
    {
      float var16 = (var12 * 0.3F + var13 * 0.59F + var14 * 0.11F) * 0.6F;
      float var17 = 1.0F - var15 * 0.75F;
      var12 = var12 * var17 + var16 * (1.0F - var17);
      var13 = var13 * var17 + var16 * (1.0F - var17);
      var14 = var14 * var17 + var16 * (1.0F - var17);
    }
    float var16 = getWeightedThunderStrength(p_72833_2_);
    if (var16 > 0.0F)
    {
      float var17 = (var12 * 0.3F + var13 * 0.59F + var14 * 0.11F) * 0.2F;
      float var18 = 1.0F - var16 * 0.75F;
      var12 = var12 * var18 + var17 * (1.0F - var18);
      var13 = var13 * var18 + var17 * (1.0F - var18);
      var14 = var14 * var18 + var17 * (1.0F - var18);
    }
    if (this.lastLightningBolt > 0)
    {
      float var17 = this.lastLightningBolt - p_72833_2_;
      if (var17 > 1.0F) {
        var17 = 1.0F;
      }
      var17 *= 0.45F;
      var12 = var12 * (1.0F - var17) + 0.8F * var17;
      var13 = var13 * (1.0F - var17) + 0.8F * var17;
      var14 = var14 * (1.0F - var17) + 1.0F * var17;
    }
    return new Vec3(var12, var13, var14);
  }
  
  public float getCelestialAngle(float p_72826_1_)
  {
    if (HUD.isModifiedTime) {
      return this.provider.calculateCelestialAngle(HUD.worldTime, p_72826_1_);
    }
    return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), p_72826_1_);
  }
  
  public int getMoonPhase()
  {
    return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
  }
  
  public float getCurrentMoonPhaseFactor()
  {
    return WorldProvider.moonPhaseFactors[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
  }
  
  public float getCelestialAngleRadians(float p_72929_1_)
  {
    float var2 = getCelestialAngle(p_72929_1_);
    return var2 * 3.1415927F * 2.0F;
  }
  
  public Vec3 getCloudColour(float p_72824_1_)
  {
    float var2 = getCelestialAngle(p_72824_1_);
    float var3 = MathHelper.cos(var2 * 3.1415927F * 2.0F) * 2.0F + 0.5F;
    var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
    float var4 = (float)(this.cloudColour >> 16 & 0xFF) / 255.0F;
    float var5 = (float)(this.cloudColour >> 8 & 0xFF) / 255.0F;
    float var6 = (float)(this.cloudColour & 0xFF) / 255.0F;
    float var7 = getRainStrength(p_72824_1_);
    if (var7 > 0.0F)
    {
      float var8 = (var4 * 0.3F + var5 * 0.59F + var6 * 0.11F) * 0.6F;
      float var9 = 1.0F - var7 * 0.95F;
      var4 = var4 * var9 + var8 * (1.0F - var9);
      var5 = var5 * var9 + var8 * (1.0F - var9);
      var6 = var6 * var9 + var8 * (1.0F - var9);
    }
    var4 *= (var3 * 0.9F + 0.1F);
    var5 *= (var3 * 0.9F + 0.1F);
    var6 *= (var3 * 0.85F + 0.15F);
    float var8 = getWeightedThunderStrength(p_72824_1_);
    if (var8 > 0.0F)
    {
      float var9 = (var4 * 0.3F + var5 * 0.59F + var6 * 0.11F) * 0.2F;
      float var10 = 1.0F - var8 * 0.95F;
      var4 = var4 * var10 + var9 * (1.0F - var10);
      var5 = var5 * var10 + var9 * (1.0F - var10);
      var6 = var6 * var10 + var9 * (1.0F - var10);
    }
    return new Vec3(var4, var5, var6);
  }
  
  public Vec3 getFogColor(float p_72948_1_)
  {
    float var2 = getCelestialAngle(p_72948_1_);
    return this.provider.getFogColor(var2, p_72948_1_);
  }
  
  public BlockPos func_175725_q(BlockPos p_175725_1_)
  {
    return getChunkFromBlockCoords(p_175725_1_).func_177440_h(p_175725_1_);
  }
  
  public BlockPos func_175672_r(BlockPos p_175672_1_)
  {
    Chunk var2 = getChunkFromBlockCoords(p_175672_1_);
    BlockPos var4;
    for (BlockPos var3 = new BlockPos(p_175672_1_.getX(), var2.getTopFilledSegment() + 16, p_175672_1_.getZ()); var3.getY() >= 0; var3 = var4)
    {
      var4 = var3.offsetDown();
      Material var5 = var2.getBlock(var4).getMaterial();
      if ((var5.blocksMovement()) && (var5 != Material.leaves)) {
        break;
      }
    }
    return var3;
  }
  
  public float getStarBrightness(float p_72880_1_)
  {
    float var2 = getCelestialAngle(p_72880_1_);
    float var3 = 1.0F - (MathHelper.cos(var2 * 3.1415927F * 2.0F) * 2.0F + 0.25F);
    var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
    return var3 * var3 * 0.5F;
  }
  
  public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {}
  
  public void func_175654_a(BlockPos p_175654_1_, Block p_175654_2_, int p_175654_3_, int p_175654_4_) {}
  
  public void func_180497_b(BlockPos p_180497_1_, Block p_180497_2_, int p_180497_3_, int p_180497_4_) {}
  
  public void updateEntities()
  {
    this.theProfiler.startSection("entities");
    this.theProfiler.startSection("global");
    for (int var1 = 0; var1 < this.weatherEffects.size(); var1++)
    {
      Entity var2 = (Entity)this.weatherEffects.get(var1);
      try
      {
        var2.ticksExisted += 1;
        var2.onUpdate();
      }
      catch (Throwable var9)
      {
        CrashReport var4 = CrashReport.makeCrashReport(var9, "Ticking entity");
        CrashReportCategory var5 = var4.makeCategory("Entity being ticked");
        if (var2 == null) {
          var5.addCrashSection("Entity", "~~NULL~~");
        } else {
          var2.addEntityCrashInfo(var5);
        }
        throw new ReportedException(var4);
      }
      if (var2.isDead) {
        this.weatherEffects.remove(var1--);
      }
    }
    this.theProfiler.endStartSection("remove");
    this.loadedEntityList.removeAll(this.unloadedEntityList);
    for (var1 = 0; var1 < this.unloadedEntityList.size(); var1++)
    {
      Entity var2 = (Entity)this.unloadedEntityList.get(var1);
      int var3 = var2.chunkCoordX;
      int var15 = var2.chunkCoordZ;
      if ((var2.addedToChunk) && (isChunkLoaded(var3, var15, true))) {
        getChunkFromChunkCoords(var3, var15).removeEntity(var2);
      }
    }
    for (var1 = 0; var1 < this.unloadedEntityList.size(); var1++) {
      onEntityRemoved((Entity)this.unloadedEntityList.get(var1));
    }
    this.unloadedEntityList.clear();
    this.theProfiler.endStartSection("regular");
    for (var1 = 0; var1 < this.loadedEntityList.size(); var1++)
    {
      Entity var2 = (Entity)this.loadedEntityList.get(var1);
      if (var2.ridingEntity != null)
      {
        if ((var2.ridingEntity.isDead) || (var2.ridingEntity.riddenByEntity != var2))
        {
          var2.ridingEntity.riddenByEntity = null;
          var2.ridingEntity = null;
        }
      }
      else
      {
        this.theProfiler.startSection("tick");
        if (!var2.isDead) {
          try
          {
            updateEntity(var2);
          }
          catch (Throwable var8)
          {
            CrashReport var4 = CrashReport.makeCrashReport(var8, "Ticking entity");
            CrashReportCategory var5 = var4.makeCategory("Entity being ticked");
            var2.addEntityCrashInfo(var5);
            throw new ReportedException(var4);
          }
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("remove");
        if (var2.isDead)
        {
          int var3 = var2.chunkCoordX;
          int var15 = var2.chunkCoordZ;
          if ((var2.addedToChunk) && (isChunkLoaded(var3, var15, true))) {
            getChunkFromChunkCoords(var3, var15).removeEntity(var2);
          }
          this.loadedEntityList.remove(var1--);
          onEntityRemoved(var2);
        }
        this.theProfiler.endSection();
      }
    }
    this.theProfiler.endStartSection("blockEntities");
    this.processingLoadedTiles = true;
    Iterator var10 = this.tickableTileEntities.iterator();
    while (var10.hasNext())
    {
      TileEntity var11 = (TileEntity)var10.next();
      if ((!var11.isInvalid()) && (var11.hasWorldObj()))
      {
        BlockPos var13 = var11.getPos();
        if ((isBlockLoaded(var13)) && (this.worldBorder.contains(var13))) {
          try
          {
            ((IUpdatePlayerListBox)var11).update();
          }
          catch (Throwable var7)
          {
            CrashReport var16 = CrashReport.makeCrashReport(var7, "Ticking block entity");
            CrashReportCategory var6 = var16.makeCategory("Block entity being ticked");
            var11.addInfoToCrashReport(var6);
            throw new ReportedException(var16);
          }
        }
      }
      if (var11.isInvalid())
      {
        var10.remove();
        this.loadedTileEntityList.remove(var11);
        if (isBlockLoaded(var11.getPos())) {
          getChunkFromBlockCoords(var11.getPos()).removeTileEntity(var11.getPos());
        }
      }
    }
    this.processingLoadedTiles = false;
    if (!this.tileEntitiesToBeRemoved.isEmpty())
    {
      this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
      this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
      this.tileEntitiesToBeRemoved.clear();
    }
    this.theProfiler.endStartSection("pendingBlockEntities");
    if (!this.addedTileEntityList.isEmpty())
    {
      for (int var12 = 0; var12 < this.addedTileEntityList.size(); var12++)
      {
        TileEntity var14 = (TileEntity)this.addedTileEntityList.get(var12);
        if (!var14.isInvalid())
        {
          if (!this.loadedTileEntityList.contains(var14)) {
            addTileEntity(var14);
          }
          if (isBlockLoaded(var14.getPos())) {
            getChunkFromBlockCoords(var14.getPos()).addTileEntity(var14.getPos(), var14);
          }
          markBlockForUpdate(var14.getPos());
        }
      }
      this.addedTileEntityList.clear();
    }
    this.theProfiler.endSection();
    this.theProfiler.endSection();
  }
  
  public boolean addTileEntity(TileEntity tile)
  {
    boolean var2 = this.loadedTileEntityList.add(tile);
    if ((var2) && ((tile instanceof IUpdatePlayerListBox))) {
      this.tickableTileEntities.add(tile);
    }
    return var2;
  }
  
  public void addTileEntities(Collection tileEntityCollection)
  {
    if (this.processingLoadedTiles)
    {
      this.addedTileEntityList.addAll(tileEntityCollection);
    }
    else
    {
      Iterator var2 = tileEntityCollection.iterator();
      while (var2.hasNext())
      {
        TileEntity var3 = (TileEntity)var2.next();
        this.loadedTileEntityList.add(var3);
        if ((var3 instanceof IUpdatePlayerListBox)) {
          this.tickableTileEntities.add(var3);
        }
      }
    }
  }
  
  public void updateEntity(Entity ent)
  {
    updateEntityWithOptionalForce(ent, true);
  }
  
  public void updateEntityWithOptionalForce(Entity p_72866_1_, boolean p_72866_2_)
  {
    int var3 = MathHelper.floor_double(p_72866_1_.posX);
    int var4 = MathHelper.floor_double(p_72866_1_.posZ);
    byte var5 = 32;
    if ((!p_72866_2_) || (isAreaLoaded(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5, true)))
    {
      p_72866_1_.lastTickPosX = p_72866_1_.posX;
      p_72866_1_.lastTickPosY = p_72866_1_.posY;
      p_72866_1_.lastTickPosZ = p_72866_1_.posZ;
      p_72866_1_.prevRotationYaw = p_72866_1_.rotationYaw;
      p_72866_1_.prevRotationPitch = p_72866_1_.rotationPitch;
      if ((p_72866_2_) && (p_72866_1_.addedToChunk))
      {
        p_72866_1_.ticksExisted += 1;
        if (p_72866_1_.ridingEntity != null) {
          p_72866_1_.updateRidden();
        } else {
          p_72866_1_.onUpdate();
        }
      }
      this.theProfiler.startSection("chunkCheck");
      if ((Double.isNaN(p_72866_1_.posX)) || (Double.isInfinite(p_72866_1_.posX))) {
        p_72866_1_.posX = p_72866_1_.lastTickPosX;
      }
      if ((Double.isNaN(p_72866_1_.posY)) || (Double.isInfinite(p_72866_1_.posY))) {
        p_72866_1_.posY = p_72866_1_.lastTickPosY;
      }
      if ((Double.isNaN(p_72866_1_.posZ)) || (Double.isInfinite(p_72866_1_.posZ))) {
        p_72866_1_.posZ = p_72866_1_.lastTickPosZ;
      }
      if ((Double.isNaN(p_72866_1_.rotationPitch)) || (Double.isInfinite(p_72866_1_.rotationPitch))) {
        p_72866_1_.rotationPitch = p_72866_1_.prevRotationPitch;
      }
      if ((Double.isNaN(p_72866_1_.rotationYaw)) || (Double.isInfinite(p_72866_1_.rotationYaw))) {
        p_72866_1_.rotationYaw = p_72866_1_.prevRotationYaw;
      }
      int var6 = MathHelper.floor_double(p_72866_1_.posX / 16.0D);
      int var7 = MathHelper.floor_double(p_72866_1_.posY / 16.0D);
      int var8 = MathHelper.floor_double(p_72866_1_.posZ / 16.0D);
      if ((!p_72866_1_.addedToChunk) || (p_72866_1_.chunkCoordX != var6) || (p_72866_1_.chunkCoordY != var7) || (p_72866_1_.chunkCoordZ != var8))
      {
        if ((p_72866_1_.addedToChunk) && (isChunkLoaded(p_72866_1_.chunkCoordX, p_72866_1_.chunkCoordZ, true))) {
          getChunkFromChunkCoords(p_72866_1_.chunkCoordX, p_72866_1_.chunkCoordZ).removeEntityAtIndex(p_72866_1_, p_72866_1_.chunkCoordY);
        }
        if (isChunkLoaded(var6, var8, true))
        {
          p_72866_1_.addedToChunk = true;
          getChunkFromChunkCoords(var6, var8).addEntity(p_72866_1_);
        }
        else
        {
          p_72866_1_.addedToChunk = false;
        }
      }
      this.theProfiler.endSection();
      if ((p_72866_2_) && (p_72866_1_.addedToChunk) && (p_72866_1_.riddenByEntity != null)) {
        if ((!p_72866_1_.riddenByEntity.isDead) && (p_72866_1_.riddenByEntity.ridingEntity == p_72866_1_))
        {
          updateEntity(p_72866_1_.riddenByEntity);
        }
        else
        {
          p_72866_1_.riddenByEntity.ridingEntity = null;
          p_72866_1_.riddenByEntity = null;
        }
      }
    }
  }
  
  public boolean checkNoEntityCollision(AxisAlignedBB p_72855_1_)
  {
    return checkNoEntityCollision(p_72855_1_, null);
  }
  
  public boolean checkNoEntityCollision(AxisAlignedBB p_72917_1_, Entity p_72917_2_)
  {
    List var3 = getEntitiesWithinAABBExcludingEntity(null, p_72917_1_);
    for (int var4 = 0; var4 < var3.size(); var4++)
    {
      Entity var5 = (Entity)var3.get(var4);
      if ((!var5.isDead) && (var5.preventEntitySpawning) && (var5 != p_72917_2_) && ((p_72917_2_ == null) || ((p_72917_2_.ridingEntity != var5) && (p_72917_2_.riddenByEntity != var5)))) {
        return false;
      }
    }
    return true;
  }
  
  public boolean checkBlockCollision(AxisAlignedBB p_72829_1_)
  {
    int var2 = MathHelper.floor_double(p_72829_1_.minX);
    int var3 = MathHelper.floor_double(p_72829_1_.maxX);
    int var4 = MathHelper.floor_double(p_72829_1_.minY);
    int var5 = MathHelper.floor_double(p_72829_1_.maxY);
    int var6 = MathHelper.floor_double(p_72829_1_.minZ);
    int var7 = MathHelper.floor_double(p_72829_1_.maxZ);
    for (int var8 = var2; var8 <= var3; var8++) {
      for (int var9 = var4; var9 <= var5; var9++) {
        for (int var10 = var6; var10 <= var7; var10++)
        {
          Block var11 = getBlockState(new BlockPos(var8, var9, var10)).getBlock();
          if (var11.getMaterial() != Material.air) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public boolean isAnyLiquid(AxisAlignedBB p_72953_1_)
  {
    int var2 = MathHelper.floor_double(p_72953_1_.minX);
    int var3 = MathHelper.floor_double(p_72953_1_.maxX);
    int var4 = MathHelper.floor_double(p_72953_1_.minY);
    int var5 = MathHelper.floor_double(p_72953_1_.maxY);
    int var6 = MathHelper.floor_double(p_72953_1_.minZ);
    int var7 = MathHelper.floor_double(p_72953_1_.maxZ);
    for (int var8 = var2; var8 <= var3; var8++) {
      for (int var9 = var4; var9 <= var5; var9++) {
        for (int var10 = var6; var10 <= var7; var10++)
        {
          Block var11 = getBlockState(new BlockPos(var8, var9, var10)).getBlock();
          if (var11.getMaterial().isLiquid()) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public boolean func_147470_e(AxisAlignedBB p_147470_1_)
  {
    int var2 = MathHelper.floor_double(p_147470_1_.minX);
    int var3 = MathHelper.floor_double(p_147470_1_.maxX + 1.0D);
    int var4 = MathHelper.floor_double(p_147470_1_.minY);
    int var5 = MathHelper.floor_double(p_147470_1_.maxY + 1.0D);
    int var6 = MathHelper.floor_double(p_147470_1_.minZ);
    int var7 = MathHelper.floor_double(p_147470_1_.maxZ + 1.0D);
    if (isAreaLoaded(var2, var4, var6, var3, var5, var7, true)) {
      for (int var8 = var2; var8 < var3; var8++) {
        for (int var9 = var4; var9 < var5; var9++) {
          for (int var10 = var6; var10 < var7; var10++)
          {
            Block var11 = getBlockState(new BlockPos(var8, var9, var10)).getBlock();
            if ((var11 == Blocks.fire) || (var11 == Blocks.flowing_lava) || (var11 == Blocks.lava)) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }
  
  public boolean handleMaterialAcceleration(AxisAlignedBB p_72918_1_, Material p_72918_2_, Entity p_72918_3_)
  {
    int var4 = MathHelper.floor_double(p_72918_1_.minX);
    int var5 = MathHelper.floor_double(p_72918_1_.maxX + 1.0D);
    int var6 = MathHelper.floor_double(p_72918_1_.minY);
    int var7 = MathHelper.floor_double(p_72918_1_.maxY + 1.0D);
    int var8 = MathHelper.floor_double(p_72918_1_.minZ);
    int var9 = MathHelper.floor_double(p_72918_1_.maxZ + 1.0D);
    if (!isAreaLoaded(var4, var6, var8, var5, var7, var9, true)) {
      return false;
    }
    boolean var10 = false;
    Vec3 var11 = new Vec3(0.0D, 0.0D, 0.0D);
    for (int var12 = var4; var12 < var5; var12++) {
      for (int var13 = var6; var13 < var7; var13++) {
        for (int var14 = var8; var14 < var9; var14++)
        {
          BlockPos var15 = new BlockPos(var12, var13, var14);
          IBlockState var16 = getBlockState(var15);
          Block var17 = var16.getBlock();
          if (var17.getMaterial() == p_72918_2_)
          {
            double var18 = var13 + 1 - BlockLiquid.getLiquidHeightPercent(((Integer)var16.getValue(BlockLiquid.LEVEL)).intValue());
            if (var7 >= var18)
            {
              var10 = true;
              var11 = var17.modifyAcceleration(this, var15, p_72918_3_, var11);
            }
          }
        }
      }
    }
    if ((var11.lengthVector() > 0.0D) && (p_72918_3_.isPushedByWater()))
    {
      var11 = var11.normalize();
      double var20 = 0.014D;
      p_72918_3_.motionX += var11.xCoord * var20;
      p_72918_3_.motionY += var11.yCoord * var20;
      p_72918_3_.motionZ += var11.zCoord * var20;
    }
    return var10;
  }
  
  public boolean isMaterialInBB(AxisAlignedBB p_72875_1_, Material p_72875_2_)
  {
    int var3 = MathHelper.floor_double(p_72875_1_.minX);
    int var4 = MathHelper.floor_double(p_72875_1_.maxX + 1.0D);
    int var5 = MathHelper.floor_double(p_72875_1_.minY);
    int var6 = MathHelper.floor_double(p_72875_1_.maxY + 1.0D);
    int var7 = MathHelper.floor_double(p_72875_1_.minZ);
    int var8 = MathHelper.floor_double(p_72875_1_.maxZ + 1.0D);
    for (int var9 = var3; var9 < var4; var9++) {
      for (int var10 = var5; var10 < var6; var10++) {
        for (int var11 = var7; var11 < var8; var11++) {
          if (getBlockState(new BlockPos(var9, var10, var11)).getBlock().getMaterial() == p_72875_2_) {
            return true;
          }
        }
      }
    }
    return false;
  }
  
  public boolean isAABBInMaterial(AxisAlignedBB p_72830_1_, Material p_72830_2_)
  {
    int var3 = MathHelper.floor_double(p_72830_1_.minX);
    int var4 = MathHelper.floor_double(p_72830_1_.maxX + 1.0D);
    int var5 = MathHelper.floor_double(p_72830_1_.minY);
    int var6 = MathHelper.floor_double(p_72830_1_.maxY + 1.0D);
    int var7 = MathHelper.floor_double(p_72830_1_.minZ);
    int var8 = MathHelper.floor_double(p_72830_1_.maxZ + 1.0D);
    for (int var9 = var3; var9 < var4; var9++) {
      for (int var10 = var5; var10 < var6; var10++) {
        for (int var11 = var7; var11 < var8; var11++)
        {
          BlockPos var12 = new BlockPos(var9, var10, var11);
          IBlockState var13 = getBlockState(var12);
          Block var14 = var13.getBlock();
          if (var14.getMaterial() == p_72830_2_)
          {
            int var15 = ((Integer)var13.getValue(BlockLiquid.LEVEL)).intValue();
            double var16 = var10 + 1;
            if (var15 < 8) {
              var16 = var10 + 1 - var15 / 8.0D;
            }
            if (var16 >= p_72830_1_.minY) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }
  
  public Explosion createExplosion(Entity p_72876_1_, double p_72876_2_, double p_72876_4_, double p_72876_6_, float p_72876_8_, boolean p_72876_9_)
  {
    return newExplosion(p_72876_1_, p_72876_2_, p_72876_4_, p_72876_6_, p_72876_8_, false, p_72876_9_);
  }
  
  public Explosion newExplosion(Entity p_72885_1_, double p_72885_2_, double p_72885_4_, double p_72885_6_, float p_72885_8_, boolean p_72885_9_, boolean p_72885_10_)
  {
    Explosion var11 = new Explosion(this, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, p_72885_9_, p_72885_10_);
    var11.doExplosionA();
    var11.doExplosionB(true);
    return var11;
  }
  
  public float getBlockDensity(Vec3 p_72842_1_, AxisAlignedBB p_72842_2_)
  {
    double var3 = 1.0D / ((p_72842_2_.maxX - p_72842_2_.minX) * 2.0D + 1.0D);
    double var5 = 1.0D / ((p_72842_2_.maxY - p_72842_2_.minY) * 2.0D + 1.0D);
    double var7 = 1.0D / ((p_72842_2_.maxZ - p_72842_2_.minZ) * 2.0D + 1.0D);
    if ((var3 >= 0.0D) && (var5 >= 0.0D) && (var7 >= 0.0D))
    {
      int var9 = 0;
      int var10 = 0;
      for (float var11 = 0.0F; var11 <= 1.0F; var11 = (float)(var11 + var3)) {
        for (float var12 = 0.0F; var12 <= 1.0F; var12 = (float)(var12 + var5)) {
          for (float var13 = 0.0F; var13 <= 1.0F; var13 = (float)(var13 + var7))
          {
            double var14 = p_72842_2_.minX + (p_72842_2_.maxX - p_72842_2_.minX) * var11;
            double var16 = p_72842_2_.minY + (p_72842_2_.maxY - p_72842_2_.minY) * var12;
            double var18 = p_72842_2_.minZ + (p_72842_2_.maxZ - p_72842_2_.minZ) * var13;
            if (rayTraceBlocks(new Vec3(var14, var16, var18), p_72842_1_) == null) {
              var9++;
            }
            var10++;
          }
        }
      }
      return var9 / var10;
    }
    return 0.0F;
  }
  
  public boolean func_175719_a(EntityPlayer p_175719_1_, BlockPos p_175719_2_, EnumFacing p_175719_3_)
  {
    p_175719_2_ = p_175719_2_.offset(p_175719_3_);
    if (getBlockState(p_175719_2_).getBlock() == Blocks.fire)
    {
      playAuxSFXAtEntity(p_175719_1_, 1004, p_175719_2_, 0);
      setBlockToAir(p_175719_2_);
      return true;
    }
    return false;
  }
  
  public String getDebugLoadedEntities()
  {
    return "All: " + this.loadedEntityList.size();
  }
  
  public String getProviderName()
  {
    return this.chunkProvider.makeString();
  }
  
  public TileEntity getTileEntity(BlockPos pos)
  {
    if (!isValid(pos)) {
      return null;
    }
    TileEntity var2 = null;
    if (this.processingLoadedTiles) {
      for (int var3 = 0; var3 < this.addedTileEntityList.size(); var3++)
      {
        TileEntity var4 = (TileEntity)this.addedTileEntityList.get(var3);
        if ((!var4.isInvalid()) && (var4.getPos().equals(pos)))
        {
          var2 = var4;
          break;
        }
      }
    }
    if (var2 == null) {
      var2 = getChunkFromBlockCoords(pos).func_177424_a(pos, Chunk.EnumCreateEntityType.IMMEDIATE);
    }
    if (var2 == null) {
      for (int var3 = 0; var3 < this.addedTileEntityList.size(); var3++)
      {
        TileEntity var4 = (TileEntity)this.addedTileEntityList.get(var3);
        if ((!var4.isInvalid()) && (var4.getPos().equals(pos)))
        {
          var2 = var4;
          break;
        }
      }
    }
    return var2;
  }
  
  public void setTileEntity(BlockPos p_175690_1_, TileEntity p_175690_2_)
  {
    if ((p_175690_2_ != null) && (!p_175690_2_.isInvalid())) {
      if (this.processingLoadedTiles)
      {
        p_175690_2_.setPos(p_175690_1_);
        Iterator var3 = this.addedTileEntityList.iterator();
        while (var3.hasNext())
        {
          TileEntity var4 = (TileEntity)var3.next();
          if (var4.getPos().equals(p_175690_1_))
          {
            var4.invalidate();
            var3.remove();
          }
        }
        this.addedTileEntityList.add(p_175690_2_);
      }
      else
      {
        addTileEntity(p_175690_2_);
        getChunkFromBlockCoords(p_175690_1_).addTileEntity(p_175690_1_, p_175690_2_);
      }
    }
  }
  
  public void removeTileEntity(BlockPos pos)
  {
    TileEntity var2 = getTileEntity(pos);
    if ((var2 != null) && (this.processingLoadedTiles))
    {
      var2.invalidate();
      this.addedTileEntityList.remove(var2);
    }
    else
    {
      if (var2 != null)
      {
        this.addedTileEntityList.remove(var2);
        this.loadedTileEntityList.remove(var2);
        this.tickableTileEntities.remove(var2);
      }
      getChunkFromBlockCoords(pos).removeTileEntity(pos);
    }
  }
  
  public void markTileEntityForRemoval(TileEntity tileEntityIn)
  {
    this.tileEntitiesToBeRemoved.add(tileEntityIn);
  }
  
  public boolean func_175665_u(BlockPos p_175665_1_)
  {
    IBlockState var2 = getBlockState(p_175665_1_);
    AxisAlignedBB var3 = var2.getBlock().getCollisionBoundingBox(this, p_175665_1_, var2);
    return (var3 != null) && (var3.getAverageEdgeLength() >= 1.0D);
  }
  
  public static boolean doesBlockHaveSolidTopSurface(IBlockAccess p_175683_0_, BlockPos p_175683_1_)
  {
    IBlockState var2 = p_175683_0_.getBlockState(p_175683_1_);
    Block var3 = var2.getBlock();
    return ((var3.getMaterial().isOpaque()) && (var3.isFullCube())) || ((var3 instanceof BlockStairs) ? var2.getValue(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP : (var3 instanceof BlockSlab) ? var2.getValue(BlockSlab.HALF_PROP) == BlockSlab.EnumBlockHalf.TOP : ((var3 instanceof BlockHopper)) || (((var3 instanceof BlockSnow)) && (((Integer)var2.getValue(BlockSnow.LAYERS_PROP)).intValue() == 7)));
  }
  
  public boolean func_175677_d(BlockPos p_175677_1_, boolean p_175677_2_)
  {
    if (!isValid(p_175677_1_)) {
      return p_175677_2_;
    }
    Chunk var3 = this.chunkProvider.func_177459_a(p_175677_1_);
    if (var3.isEmpty()) {
      return p_175677_2_;
    }
    Block var4 = getBlockState(p_175677_1_).getBlock();
    return (var4.getMaterial().isOpaque()) && (var4.isFullCube());
  }
  
  public void calculateInitialSkylight()
  {
    int var1 = calculateSkylightSubtracted(1.0F);
    if (var1 != this.skylightSubtracted) {
      this.skylightSubtracted = var1;
    }
  }
  
  public void setAllowedSpawnTypes(boolean hostile, boolean peaceful)
  {
    this.spawnHostileMobs = hostile;
    this.spawnPeacefulMobs = peaceful;
  }
  
  public void tick()
  {
    updateWeather();
  }
  
  protected void calculateInitialWeather()
  {
    if (this.worldInfo.isRaining())
    {
      this.rainingStrength = 1.0F;
      if (this.worldInfo.isThundering()) {
        this.thunderingStrength = 1.0F;
      }
    }
  }
  
  protected void updateWeather()
  {
    if ((!this.provider.getHasNoSky()) && 
      (!this.isRemote))
    {
      int var1 = this.worldInfo.func_176133_A();
      if (var1 > 0)
      {
        var1--;
        this.worldInfo.func_176142_i(var1);
        this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
        this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
      }
      int var2 = this.worldInfo.getThunderTime();
      if (var2 <= 0)
      {
        if (this.worldInfo.isThundering()) {
          this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
        } else {
          this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
        }
      }
      else
      {
        var2--;
        this.worldInfo.setThunderTime(var2);
        if (var2 <= 0) {
          this.worldInfo.setThundering(!this.worldInfo.isThundering());
        }
      }
      this.prevThunderingStrength = this.thunderingStrength;
      if (this.worldInfo.isThundering()) {
        this.thunderingStrength = ((float)(this.thunderingStrength + 0.01D));
      } else {
        this.thunderingStrength = ((float)(this.thunderingStrength - 0.01D));
      }
      this.thunderingStrength = MathHelper.clamp_float(this.thunderingStrength, 0.0F, 1.0F);
      int var3 = this.worldInfo.getRainTime();
      if (var3 <= 0)
      {
        if (this.worldInfo.isRaining()) {
          this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
        } else {
          this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
        }
      }
      else
      {
        var3--;
        this.worldInfo.setRainTime(var3);
        if (var3 <= 0) {
          this.worldInfo.setRaining(!this.worldInfo.isRaining());
        }
      }
      this.prevRainingStrength = this.rainingStrength;
      if (this.worldInfo.isRaining()) {
        this.rainingStrength = ((float)(this.rainingStrength + 0.01D));
      } else {
        this.rainingStrength = ((float)(this.rainingStrength - 0.01D));
      }
      this.rainingStrength = MathHelper.clamp_float(this.rainingStrength, 0.0F, 1.0F);
    }
  }
  
  protected void setActivePlayerChunksAndCheckLight()
  {
    this.activeChunkSet.clear();
    this.theProfiler.startSection("buildList");
    for (int var1 = 0; var1 < this.playerEntities.size(); var1++)
    {
      EntityPlayer var2 = (EntityPlayer)this.playerEntities.get(var1);
      int var3 = MathHelper.floor_double(var2.posX / 16.0D);
      int var4 = MathHelper.floor_double(var2.posZ / 16.0D);
      int var5 = getRenderDistanceChunks();
      for (int var6 = -var5; var6 <= var5; var6++) {
        for (int var7 = -var5; var7 <= var5; var7++) {
          this.activeChunkSet.add(new ChunkCoordIntPair(var6 + var3, var7 + var4));
        }
      }
    }
    this.theProfiler.endSection();
    if (this.ambientTickCountdown > 0) {
      this.ambientTickCountdown -= 1;
    }
    this.theProfiler.startSection("playerCheckLight");
    if (!this.playerEntities.isEmpty())
    {
      var1 = this.rand.nextInt(this.playerEntities.size());
      EntityPlayer var2 = (EntityPlayer)this.playerEntities.get(var1);
      int var3 = MathHelper.floor_double(var2.posX) + this.rand.nextInt(11) - 5;
      int var4 = MathHelper.floor_double(var2.posY) + this.rand.nextInt(11) - 5;
      int var5 = MathHelper.floor_double(var2.posZ) + this.rand.nextInt(11) - 5;
      checkLight(new BlockPos(var3, var4, var5));
    }
    this.theProfiler.endSection();
  }
  
  protected abstract int getRenderDistanceChunks();
  
  protected void func_147467_a(int p_147467_1_, int p_147467_2_, Chunk p_147467_3_)
  {
    this.theProfiler.endStartSection("moodSound");
    if ((this.ambientTickCountdown == 0) && (!this.isRemote))
    {
      this.updateLCG = (this.updateLCG * 3 + 1013904223);
      int var4 = this.updateLCG >> 2;
      int var5 = var4 & 0xF;
      int var6 = var4 >> 8 & 0xF;
      int var7 = var4 >> 16 & 0xFF;
      BlockPos var8 = new BlockPos(var5, var7, var6);
      Block var9 = p_147467_3_.getBlock(var8);
      var5 += p_147467_1_;
      var6 += p_147467_2_;
      if ((var9.getMaterial() == Material.air) && (getLight(var8) <= this.rand.nextInt(8)) && (getLightFor(EnumSkyBlock.SKY, var8) <= 0))
      {
        EntityPlayer var10 = getClosestPlayer(var5 + 0.5D, var7 + 0.5D, var6 + 0.5D, 8.0D);
        if ((var10 != null) && (var10.getDistanceSq(var5 + 0.5D, var7 + 0.5D, var6 + 0.5D) > 4.0D))
        {
          playSoundEffect(var5 + 0.5D, var7 + 0.5D, var6 + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.rand.nextFloat() * 0.2F);
          this.ambientTickCountdown = (this.rand.nextInt(12000) + 6000);
        }
      }
    }
    this.theProfiler.endStartSection("checkLight");
    p_147467_3_.enqueueRelightChecks();
  }
  
  protected void func_147456_g()
  {
    setActivePlayerChunksAndCheckLight();
  }
  
  public void func_175637_a(Block p_175637_1_, BlockPos p_175637_2_, Random p_175637_3_)
  {
    this.scheduledUpdatesAreImmediate = true;
    p_175637_1_.updateTick(this, p_175637_2_, getBlockState(p_175637_2_), p_175637_3_);
    this.scheduledUpdatesAreImmediate = false;
  }
  
  public boolean func_175675_v(BlockPos p_175675_1_)
  {
    return func_175670_e(p_175675_1_, false);
  }
  
  public boolean func_175662_w(BlockPos p_175662_1_)
  {
    return func_175670_e(p_175662_1_, true);
  }
  
  public boolean func_175670_e(BlockPos p_175670_1_, boolean p_175670_2_)
  {
    BiomeGenBase var3 = getBiomeGenForCoords(p_175670_1_);
    float var4 = var3.func_180626_a(p_175670_1_);
    if (var4 > 0.15F) {
      return false;
    }
    if ((p_175670_1_.getY() >= 0) && (p_175670_1_.getY() < 256) && (getLightFor(EnumSkyBlock.BLOCK, p_175670_1_) < 10))
    {
      IBlockState var5 = getBlockState(p_175670_1_);
      Block var6 = var5.getBlock();
      if (((var6 == Blocks.water) || (var6 == Blocks.flowing_water)) && (((Integer)var5.getValue(BlockLiquid.LEVEL)).intValue() == 0))
      {
        if (!p_175670_2_) {
          return true;
        }
        boolean var7 = (func_175696_F(p_175670_1_.offsetWest())) && (func_175696_F(p_175670_1_.offsetEast())) && (func_175696_F(p_175670_1_.offsetNorth())) && (func_175696_F(p_175670_1_.offsetSouth()));
        
        return !var7;
      }
    }
    return false;
  }
  
  private boolean func_175696_F(BlockPos p_175696_1_)
  {
    return getBlockState(p_175696_1_).getBlock().getMaterial() == Material.water;
  }
  
  public boolean func_175708_f(BlockPos p_175708_1_, boolean p_175708_2_)
  {
    BiomeGenBase var3 = getBiomeGenForCoords(p_175708_1_);
    float var4 = var3.func_180626_a(p_175708_1_);
    if (var4 > 0.15F) {
      return false;
    }
    if (!p_175708_2_) {
      return true;
    }
    if ((p_175708_1_.getY() >= 0) && (p_175708_1_.getY() < 256) && (getLightFor(EnumSkyBlock.BLOCK, p_175708_1_) < 10))
    {
      Block var5 = getBlockState(p_175708_1_).getBlock();
      
      return (var5.getMaterial() == Material.air) && (Blocks.snow_layer.canPlaceBlockAt(this, p_175708_1_));
    }
    return false;
  }
  
  public boolean checkLight(BlockPos p_175664_1_)
  {
    boolean var2 = false;
    if (!this.provider.getHasNoSky()) {
      var2 |= checkLightFor(EnumSkyBlock.SKY, p_175664_1_);
    }
    var2 |= checkLightFor(EnumSkyBlock.BLOCK, p_175664_1_);
    return var2;
  }
  
  private int func_175638_a(BlockPos p_175638_1_, EnumSkyBlock p_175638_2_)
  {
    if ((p_175638_2_ == EnumSkyBlock.SKY) && (isAgainstSky(p_175638_1_))) {
      return 15;
    }
    Block var3 = getBlockState(p_175638_1_).getBlock();
    int var4 = p_175638_2_ == EnumSkyBlock.SKY ? 0 : var3.getLightValue();
    int var5 = var3.getLightOpacity();
    if ((var5 >= 15) && (var3.getLightValue() > 0)) {
      var5 = 1;
    }
    if (var5 < 1) {
      var5 = 1;
    }
    if (var5 >= 15) {
      return 0;
    }
    if (var4 >= 14) {
      return var4;
    }
    EnumFacing[] var6 = EnumFacing.values();
    int var7 = var6.length;
    for (int var8 = 0; var8 < var7; var8++)
    {
      EnumFacing var9 = var6[var8];
      BlockPos var10 = p_175638_1_.offset(var9);
      int var11 = getLightFor(p_175638_2_, var10) - var5;
      if (var11 > var4) {
        var4 = var11;
      }
      if (var4 >= 14) {
        return var4;
      }
    }
    return var4;
  }
  
  public boolean checkLightFor(EnumSkyBlock p_180500_1_, BlockPos p_180500_2_)
  {
    if (!isAreaLoaded(p_180500_2_, 17, false)) {
      return false;
    }
    int var3 = 0;
    int var4 = 0;
    this.theProfiler.startSection("getBrightness");
    int var5 = getLightFor(p_180500_1_, p_180500_2_);
    int var6 = func_175638_a(p_180500_2_, p_180500_1_);
    int var7 = p_180500_2_.getX();
    int var8 = p_180500_2_.getY();
    int var9 = p_180500_2_.getZ();
    if (var6 > var5)
    {
      this.lightUpdateBlockList[(var4++)] = 133152;
    }
    else if (var6 < var5)
    {
      this.lightUpdateBlockList[(var4++)] = (0x20820 | var5 << 18);
      while (var3 < var4)
      {
        int var10 = this.lightUpdateBlockList[(var3++)];
        int var11 = (var10 & 0x3F) - 32 + var7;
        int var12 = (var10 >> 6 & 0x3F) - 32 + var8;
        int var13 = (var10 >> 12 & 0x3F) - 32 + var9;
        int var14 = var10 >> 18 & 0xF;
        BlockPos var15 = new BlockPos(var11, var12, var13);
        int var16 = getLightFor(p_180500_1_, var15);
        if (var16 == var14)
        {
          setLightFor(p_180500_1_, var15, 0);
          if (var14 > 0)
          {
            int var17 = MathHelper.abs_int(var11 - var7);
            int var18 = MathHelper.abs_int(var12 - var8);
            int var19 = MathHelper.abs_int(var13 - var9);
            if (var17 + var18 + var19 < 17)
            {
              EnumFacing[] var20 = EnumFacing.values();
              int var21 = var20.length;
              for (int var22 = 0; var22 < var21; var22++)
              {
                EnumFacing var23 = var20[var22];
                int var24 = var11 + var23.getFrontOffsetX();
                int var25 = var12 + var23.getFrontOffsetY();
                int var26 = var13 + var23.getFrontOffsetZ();
                BlockPos var27 = new BlockPos(var24, var25, var26);
                int var28 = Math.max(1, getBlockState(var27).getBlock().getLightOpacity());
                var16 = getLightFor(p_180500_1_, var27);
                if ((var16 == var14 - var28) && (var4 < this.lightUpdateBlockList.length)) {
                  this.lightUpdateBlockList[(var4++)] = (var24 - var7 + 32 | var25 - var8 + 32 << 6 | var26 - var9 + 32 << 12 | var14 - var28 << 18);
                }
              }
            }
          }
        }
      }
      var3 = 0;
    }
    this.theProfiler.endSection();
    this.theProfiler.startSection("checkedPosition < toCheckCount");
    while (var3 < var4)
    {
      int var10 = this.lightUpdateBlockList[(var3++)];
      int var11 = (var10 & 0x3F) - 32 + var7;
      int var12 = (var10 >> 6 & 0x3F) - 32 + var8;
      int var13 = (var10 >> 12 & 0x3F) - 32 + var9;
      BlockPos var29 = new BlockPos(var11, var12, var13);
      int var30 = getLightFor(p_180500_1_, var29);
      int var16 = func_175638_a(var29, p_180500_1_);
      if (var16 != var30)
      {
        setLightFor(p_180500_1_, var29, var16);
        if (var16 > var30)
        {
          int var17 = Math.abs(var11 - var7);
          int var18 = Math.abs(var12 - var8);
          int var19 = Math.abs(var13 - var9);
          boolean var31 = var4 < this.lightUpdateBlockList.length - 6;
          if ((var17 + var18 + var19 < 17) && (var31))
          {
            if (getLightFor(p_180500_1_, var29.offsetWest()) < var16) {
              this.lightUpdateBlockList[(var4++)] = (var11 - 1 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 - var9 + 32 << 12));
            }
            if (getLightFor(p_180500_1_, var29.offsetEast()) < var16) {
              this.lightUpdateBlockList[(var4++)] = (var11 + 1 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 - var9 + 32 << 12));
            }
            if (getLightFor(p_180500_1_, var29.offsetDown()) < var16) {
              this.lightUpdateBlockList[(var4++)] = (var11 - var7 + 32 + (var12 - 1 - var8 + 32 << 6) + (var13 - var9 + 32 << 12));
            }
            if (getLightFor(p_180500_1_, var29.offsetUp()) < var16) {
              this.lightUpdateBlockList[(var4++)] = (var11 - var7 + 32 + (var12 + 1 - var8 + 32 << 6) + (var13 - var9 + 32 << 12));
            }
            if (getLightFor(p_180500_1_, var29.offsetNorth()) < var16) {
              this.lightUpdateBlockList[(var4++)] = (var11 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 - 1 - var9 + 32 << 12));
            }
            if (getLightFor(p_180500_1_, var29.offsetSouth()) < var16) {
              this.lightUpdateBlockList[(var4++)] = (var11 - var7 + 32 + (var12 - var8 + 32 << 6) + (var13 + 1 - var9 + 32 << 12));
            }
          }
        }
      }
    }
    this.theProfiler.endSection();
    return true;
  }
  
  public boolean tickUpdates(boolean p_72955_1_)
  {
    return false;
  }
  
  public List getPendingBlockUpdates(Chunk p_72920_1_, boolean p_72920_2_)
  {
    return null;
  }
  
  public List func_175712_a(StructureBoundingBox p_175712_1_, boolean p_175712_2_)
  {
    return null;
  }
  
  public List getEntitiesWithinAABBExcludingEntity(Entity p_72839_1_, AxisAlignedBB p_72839_2_)
  {
    return func_175674_a(p_72839_1_, p_72839_2_, IEntitySelector.field_180132_d);
  }
  
  public List func_175674_a(Entity p_175674_1_, AxisAlignedBB p_175674_2_, Predicate p_175674_3_)
  {
    ArrayList var4 = Lists.newArrayList();
    int var5 = MathHelper.floor_double((p_175674_2_.minX - 2.0D) / 16.0D);
    int var6 = MathHelper.floor_double((p_175674_2_.maxX + 2.0D) / 16.0D);
    int var7 = MathHelper.floor_double((p_175674_2_.minZ - 2.0D) / 16.0D);
    int var8 = MathHelper.floor_double((p_175674_2_.maxZ + 2.0D) / 16.0D);
    for (int var9 = var5; var9 <= var6; var9++) {
      for (int var10 = var7; var10 <= var8; var10++) {
        if (isChunkLoaded(var9, var10, true)) {
          getChunkFromChunkCoords(var9, var10).func_177414_a(p_175674_1_, p_175674_2_, var4, p_175674_3_);
        }
      }
    }
    return var4;
  }
  
  public List func_175644_a(Class p_175644_1_, Predicate p_175644_2_)
  {
    ArrayList var3 = Lists.newArrayList();
    Iterator var4 = this.loadedEntityList.iterator();
    while (var4.hasNext())
    {
      Entity var5 = (Entity)var4.next();
      if ((p_175644_1_.isAssignableFrom(var5.getClass())) && (p_175644_2_.apply(var5))) {
        var3.add(var5);
      }
    }
    return var3;
  }
  
  public List func_175661_b(Class p_175661_1_, Predicate p_175661_2_)
  {
    ArrayList var3 = Lists.newArrayList();
    Iterator var4 = this.playerEntities.iterator();
    while (var4.hasNext())
    {
      Entity var5 = (Entity)var4.next();
      if ((p_175661_1_.isAssignableFrom(var5.getClass())) && (p_175661_2_.apply(var5))) {
        var3.add(var5);
      }
    }
    return var3;
  }
  
  public List getEntitiesWithinAABB(Class p_72872_1_, AxisAlignedBB p_72872_2_)
  {
    return func_175647_a(p_72872_1_, p_72872_2_, IEntitySelector.field_180132_d);
  }
  
  public List func_175647_a(Class p_175647_1_, AxisAlignedBB p_175647_2_, Predicate p_175647_3_)
  {
    int var4 = MathHelper.floor_double((p_175647_2_.minX - 2.0D) / 16.0D);
    int var5 = MathHelper.floor_double((p_175647_2_.maxX + 2.0D) / 16.0D);
    int var6 = MathHelper.floor_double((p_175647_2_.minZ - 2.0D) / 16.0D);
    int var7 = MathHelper.floor_double((p_175647_2_.maxZ + 2.0D) / 16.0D);
    ArrayList var8 = Lists.newArrayList();
    for (int var9 = var4; var9 <= var5; var9++) {
      for (int var10 = var6; var10 <= var7; var10++) {
        if (isChunkLoaded(var9, var10, true)) {
          getChunkFromChunkCoords(var9, var10).func_177430_a(p_175647_1_, p_175647_2_, var8, p_175647_3_);
        }
      }
    }
    return var8;
  }
  
  public Entity findNearestEntityWithinAABB(Class p_72857_1_, AxisAlignedBB p_72857_2_, Entity p_72857_3_)
  {
    List var4 = getEntitiesWithinAABB(p_72857_1_, p_72857_2_);
    Entity var5 = null;
    double var6 = Double.MAX_VALUE;
    for (int var8 = 0; var8 < var4.size(); var8++)
    {
      Entity var9 = (Entity)var4.get(var8);
      if ((var9 != p_72857_3_) && (IEntitySelector.field_180132_d.apply(var9)))
      {
        double var10 = p_72857_3_.getDistanceSqToEntity(var9);
        if (var10 <= var6)
        {
          var5 = var9;
          var6 = var10;
        }
      }
    }
    return var5;
  }
  
  public Entity getEntityByID(int p_73045_1_)
  {
    return (Entity)this.entitiesById.lookup(p_73045_1_);
  }
  
  public List getLoadedEntityList()
  {
    return this.loadedEntityList;
  }
  
  public void func_175646_b(BlockPos p_175646_1_, TileEntity p_175646_2_)
  {
    if (isBlockLoaded(p_175646_1_)) {
      getChunkFromBlockCoords(p_175646_1_).setChunkModified();
    }
  }
  
  public int countEntities(Class entityType)
  {
    int var2 = 0;
    Iterator var3 = this.loadedEntityList.iterator();
    while (var3.hasNext())
    {
      Entity var4 = (Entity)var3.next();
      if (((!(var4 instanceof EntityLiving)) || (!((EntityLiving)var4).isNoDespawnRequired())) && (entityType.isAssignableFrom(var4.getClass()))) {
        var2++;
      }
    }
    return var2;
  }
  
  public void loadEntities(Collection entityCollection)
  {
    this.loadedEntityList.addAll(entityCollection);
    Iterator var2 = entityCollection.iterator();
    while (var2.hasNext())
    {
      Entity var3 = (Entity)var2.next();
      onEntityAdded(var3);
    }
  }
  
  public void unloadEntities(Collection entityCollection)
  {
    this.unloadedEntityList.addAll(entityCollection);
  }
  
  public boolean canBlockBePlaced(Block p_175716_1_, BlockPos p_175716_2_, boolean p_175716_3_, EnumFacing p_175716_4_, Entity p_175716_5_, ItemStack p_175716_6_)
  {
    Block var7 = getBlockState(p_175716_2_).getBlock();
    AxisAlignedBB var8 = p_175716_3_ ? null : p_175716_1_.getCollisionBoundingBox(this, p_175716_2_, p_175716_1_.getDefaultState());
    return ((var8 == null) || (checkNoEntityCollision(var8, p_175716_5_))) && (((var7.getMaterial() == Material.circuits) && (p_175716_1_ == Blocks.anvil)) || ((var7.getMaterial().isReplaceable()) && (p_175716_1_.canReplace(this, p_175716_2_, p_175716_4_, p_175716_6_))));
  }
  
  public int getStrongPower(BlockPos pos, EnumFacing direction)
  {
    IBlockState var3 = getBlockState(pos);
    return var3.getBlock().isProvidingStrongPower(this, pos, var3, direction);
  }
  
  public WorldType getWorldType()
  {
    return this.worldInfo.getTerrainType();
  }
  
  public int getStrongPower(BlockPos pos)
  {
    byte var2 = 0;
    int var3 = Math.max(var2, getStrongPower(pos.offsetDown(), EnumFacing.DOWN));
    if (var3 >= 15) {
      return var3;
    }
    var3 = Math.max(var3, getStrongPower(pos.offsetUp(), EnumFacing.UP));
    if (var3 >= 15) {
      return var3;
    }
    var3 = Math.max(var3, getStrongPower(pos.offsetNorth(), EnumFacing.NORTH));
    if (var3 >= 15) {
      return var3;
    }
    var3 = Math.max(var3, getStrongPower(pos.offsetSouth(), EnumFacing.SOUTH));
    if (var3 >= 15) {
      return var3;
    }
    var3 = Math.max(var3, getStrongPower(pos.offsetWest(), EnumFacing.WEST));
    if (var3 >= 15) {
      return var3;
    }
    var3 = Math.max(var3, getStrongPower(pos.offsetEast(), EnumFacing.EAST));
    return var3 >= 15 ? var3 : var3;
  }
  
  public boolean func_175709_b(BlockPos p_175709_1_, EnumFacing p_175709_2_)
  {
    return getRedstonePower(p_175709_1_, p_175709_2_) > 0;
  }
  
  public int getRedstonePower(BlockPos pos, EnumFacing facing)
  {
    IBlockState var3 = getBlockState(pos);
    Block var4 = var3.getBlock();
    return var4.isNormalCube() ? getStrongPower(pos) : var4.isProvidingWeakPower(this, pos, var3, facing);
  }
  
  public boolean isBlockPowered(BlockPos pos)
  {
    return (getRedstonePower(pos.offsetDown(), EnumFacing.DOWN) > 0) || (getRedstonePower(pos.offsetUp(), EnumFacing.UP) > 0) || (getRedstonePower(pos.offsetNorth(), EnumFacing.NORTH) > 0) || (getRedstonePower(pos.offsetSouth(), EnumFacing.SOUTH) > 0) || (getRedstonePower(pos.offsetWest(), EnumFacing.WEST) > 0) || (getRedstonePower(pos.offsetEast(), EnumFacing.EAST) > 0);
  }
  
  public int func_175687_A(BlockPos p_175687_1_)
  {
    int var2 = 0;
    EnumFacing[] var3 = EnumFacing.values();
    int var4 = var3.length;
    for (int var5 = 0; var5 < var4; var5++)
    {
      EnumFacing var6 = var3[var5];
      int var7 = getRedstonePower(p_175687_1_.offset(var6), var6);
      if (var7 >= 15) {
        return 15;
      }
      if (var7 > var2) {
        var2 = var7;
      }
    }
    return var2;
  }
  
  public EntityPlayer getClosestPlayerToEntity(Entity entityIn, double distance)
  {
    return getClosestPlayer(entityIn.posX, entityIn.posY, entityIn.posZ, distance);
  }
  
  public EntityPlayer getClosestPlayer(double x, double y, double z, double distance)
  {
    double var9 = -1.0D;
    EntityPlayer var11 = null;
    for (int var12 = 0; var12 < this.playerEntities.size(); var12++)
    {
      EntityPlayer var13 = (EntityPlayer)this.playerEntities.get(var12);
      if (IEntitySelector.field_180132_d.apply(var13))
      {
        double var14 = var13.getDistanceSq(x, y, z);
        if (((distance < 0.0D) || (var14 < distance * distance)) && ((var9 == -1.0D) || (var14 < var9)))
        {
          var9 = var14;
          var11 = var13;
        }
      }
    }
    return var11;
  }
  
  public boolean func_175636_b(double p_175636_1_, double p_175636_3_, double p_175636_5_, double p_175636_7_)
  {
    for (int var9 = 0; var9 < this.playerEntities.size(); var9++)
    {
      EntityPlayer var10 = (EntityPlayer)this.playerEntities.get(var9);
      if (IEntitySelector.field_180132_d.apply(var10))
      {
        double var11 = var10.getDistanceSq(p_175636_1_, p_175636_3_, p_175636_5_);
        if ((p_175636_7_ < 0.0D) || (var11 < p_175636_7_ * p_175636_7_)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public EntityPlayer getPlayerEntityByName(String name)
  {
    for (int var2 = 0; var2 < this.playerEntities.size(); var2++)
    {
      EntityPlayer var3 = (EntityPlayer)this.playerEntities.get(var2);
      if (name.equals(var3.getName())) {
        return var3;
      }
    }
    return null;
  }
  
  public EntityPlayer getPlayerEntityByUUID(UUID uuid)
  {
    for (int var2 = 0; var2 < this.playerEntities.size(); var2++)
    {
      EntityPlayer var3 = (EntityPlayer)this.playerEntities.get(var2);
      if (uuid.equals(var3.getUniqueID())) {
        return var3;
      }
    }
    return null;
  }
  
  public void sendQuittingDisconnectingPacket() {}
  
  public void checkSessionLock()
    throws MinecraftException
  {
    this.saveHandler.checkSessionLock();
  }
  
  public void func_82738_a(long p_82738_1_)
  {
    this.worldInfo.incrementTotalWorldTime(p_82738_1_);
  }
  
  public long getSeed()
  {
    return this.worldInfo.getSeed();
  }
  
  public long getTotalWorldTime()
  {
    return this.worldInfo.getWorldTotalTime();
  }
  
  public long getWorldTime()
  {
    return this.worldInfo.getWorldTime();
  }
  
  public void setWorldTime(long time)
  {
    this.worldInfo.setWorldTime(time);
  }
  
  public BlockPos getSpawnPoint()
  {
    BlockPos var1 = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
    if (!getWorldBorder().contains(var1)) {
      var1 = getHorizon(new BlockPos(getWorldBorder().getCenterX(), 0.0D, getWorldBorder().getCenterZ()));
    }
    return var1;
  }
  
  public void setSpawnLocation(BlockPos p_175652_1_)
  {
    this.worldInfo.setSpawn(p_175652_1_);
  }
  
  public void joinEntityInSurroundings(Entity entityIn)
  {
    int var2 = MathHelper.floor_double(entityIn.posX / 16.0D);
    int var3 = MathHelper.floor_double(entityIn.posZ / 16.0D);
    byte var4 = 2;
    for (int var5 = var2 - var4; var5 <= var2 + var4; var5++) {
      for (int var6 = var3 - var4; var6 <= var3 + var4; var6++) {
        getChunkFromChunkCoords(var5, var6);
      }
    }
    if (!this.loadedEntityList.contains(entityIn)) {
      this.loadedEntityList.add(entityIn);
    }
  }
  
  public boolean isBlockModifiable(EntityPlayer p_175660_1_, BlockPos p_175660_2_)
  {
    return true;
  }
  
  public void setEntityState(Entity entityIn, byte p_72960_2_) {}
  
  public IChunkProvider getChunkProvider()
  {
    return this.chunkProvider;
  }
  
  public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam)
  {
    blockIn.onBlockEventReceived(this, pos, getBlockState(pos), eventID, eventParam);
  }
  
  public ISaveHandler getSaveHandler()
  {
    return this.saveHandler;
  }
  
  public WorldInfo getWorldInfo()
  {
    return this.worldInfo;
  }
  
  public GameRules getGameRules()
  {
    return this.worldInfo.getGameRulesInstance();
  }
  
  public void updateAllPlayersSleepingFlag() {}
  
  public float getWeightedThunderStrength(float p_72819_1_)
  {
    return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * p_72819_1_) * getRainStrength(p_72819_1_);
  }
  
  public void setThunderStrength(float p_147442_1_)
  {
    this.prevThunderingStrength = p_147442_1_;
    this.thunderingStrength = p_147442_1_;
  }
  
  public float getRainStrength(float p_72867_1_)
  {
    return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * p_72867_1_;
  }
  
  public void setRainStrength(float strength)
  {
    this.prevRainingStrength = strength;
    this.rainingStrength = strength;
  }
  
  public boolean isThundering()
  {
    return getWeightedThunderStrength(1.0F) > 0.9D;
  }
  
  public boolean isRaining()
  {
    return getRainStrength(1.0F) > 0.2D;
  }
  
  public boolean func_175727_C(BlockPos p_175727_1_)
  {
    if (!isRaining()) {
      return false;
    }
    if (!isAgainstSky(p_175727_1_)) {
      return false;
    }
    if (func_175725_q(p_175727_1_).getY() > p_175727_1_.getY()) {
      return false;
    }
    BiomeGenBase var2 = getBiomeGenForCoords(p_175727_1_);
    return (!var2.getEnableSnow()) && (!func_175708_f(p_175727_1_, false)) && (var2.canSpawnLightningBolt());
  }
  
  public boolean func_180502_D(BlockPos p_180502_1_)
  {
    BiomeGenBase var2 = getBiomeGenForCoords(p_180502_1_);
    return var2.isHighHumidity();
  }
  
  public MapStorage func_175693_T()
  {
    return this.mapStorage;
  }
  
  public void setItemData(String p_72823_1_, WorldSavedData p_72823_2_)
  {
    this.mapStorage.setData(p_72823_1_, p_72823_2_);
  }
  
  public WorldSavedData loadItemData(Class p_72943_1_, String p_72943_2_)
  {
    return this.mapStorage.loadData(p_72943_1_, p_72943_2_);
  }
  
  public int getUniqueDataId(String p_72841_1_)
  {
    return this.mapStorage.getUniqueDataId(p_72841_1_);
  }
  
  public void func_175669_a(int p_175669_1_, BlockPos p_175669_2_, int p_175669_3_)
  {
    for (int var4 = 0; var4 < this.worldAccesses.size(); var4++) {
      ((IWorldAccess)this.worldAccesses.get(var4)).func_180440_a(p_175669_1_, p_175669_2_, p_175669_3_);
    }
  }
  
  public void playAuxSFX(int p_175718_1_, BlockPos p_175718_2_, int p_175718_3_)
  {
    playAuxSFXAtEntity(null, p_175718_1_, p_175718_2_, p_175718_3_);
  }
  
  public void playAuxSFXAtEntity(EntityPlayer p_180498_1_, int p_180498_2_, BlockPos p_180498_3_, int p_180498_4_)
  {
    try
    {
      for (int var5 = 0; var5 < this.worldAccesses.size(); var5++) {
        ((IWorldAccess)this.worldAccesses.get(var5)).func_180439_a(p_180498_1_, p_180498_2_, p_180498_3_, p_180498_4_);
      }
    }
    catch (Throwable var8)
    {
      CrashReport var6 = CrashReport.makeCrashReport(var8, "Playing level event");
      CrashReportCategory var7 = var6.makeCategory("Level event being played");
      var7.addCrashSection("Block coordinates", CrashReportCategory.getCoordinateInfo(p_180498_3_));
      var7.addCrashSection("Event source", p_180498_1_);
      var7.addCrashSection("Event type", Integer.valueOf(p_180498_2_));
      var7.addCrashSection("Event data", Integer.valueOf(p_180498_4_));
      throw new ReportedException(var6);
    }
  }
  
  public int getHeight()
  {
    return 256;
  }
  
  public int getActualHeight()
  {
    return this.provider.getHasNoSky() ? 128 : 256;
  }
  
  public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_)
  {
    long var4 = p_72843_1_ * 341873128712L + p_72843_2_ * 132897987541L + getWorldInfo().getSeed() + p_72843_3_;
    this.rand.setSeed(var4);
    return this.rand;
  }
  
  public BlockPos func_180499_a(String p_180499_1_, BlockPos p_180499_2_)
  {
    return getChunkProvider().func_180513_a(this, p_180499_1_, p_180499_2_);
  }
  
  public boolean extendedLevelsInChunkCache()
  {
    return false;
  }
  
  public double getHorizon()
  {
    return this.worldInfo.getTerrainType() == WorldType.FLAT ? 0.0D : 63.0D;
  }
  
  public CrashReportCategory addWorldInfoToCrashReport(CrashReport report)
  {
    CrashReportCategory var2 = report.makeCategoryDepth("Affected level", 1);
    var2.addCrashSection("Level name", this.worldInfo == null ? "????" : this.worldInfo.getWorldName());
    var2.addCrashSectionCallable("All players", new Callable()
    {
      private static final String __OBFID = "CL_00000143";
      
      public String call()
      {
        return World.this.playerEntities.size() + " total; " + World.this.playerEntities.toString();
      }
    });
    var2.addCrashSectionCallable("Chunk stats", new Callable()
    {
      private static final String __OBFID = "CL_00000144";
      
      public String call()
      {
        return World.this.chunkProvider.makeString();
      }
    });
    try
    {
      this.worldInfo.addToCrashReport(var2);
    }
    catch (Throwable var4)
    {
      var2.addCrashSectionThrowable("Level Data Unobtainable", var4);
    }
    return var2;
  }
  
  public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress)
  {
    for (int var4 = 0; var4 < this.worldAccesses.size(); var4++)
    {
      IWorldAccess var5 = (IWorldAccess)this.worldAccesses.get(var4);
      var5.sendBlockBreakProgress(breakerId, pos, progress);
    }
  }
  
  public Calendar getCurrentDate()
  {
    if (getTotalWorldTime() % 600L == 0L) {
      this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
    }
    return this.theCalendar;
  }
  
  public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, NBTTagCompound compund) {}
  
  public Scoreboard getScoreboard()
  {
    return this.worldScoreboard;
  }
  
  public void updateComparatorOutputLevel(BlockPos pos, Block blockIn)
  {
    Iterator var3 = EnumFacing.Plane.HORIZONTAL.iterator();
    while (var3.hasNext())
    {
      EnumFacing var4 = (EnumFacing)var3.next();
      BlockPos var5 = pos.offset(var4);
      if (isBlockLoaded(var5))
      {
        IBlockState var6 = getBlockState(var5);
        if (Blocks.unpowered_comparator.func_149907_e(var6.getBlock()))
        {
          var6.getBlock().onNeighborBlockChange(this, var5, var6, blockIn);
        }
        else if (var6.getBlock().isNormalCube())
        {
          var5 = var5.offset(var4);
          var6 = getBlockState(var5);
          if (Blocks.unpowered_comparator.func_149907_e(var6.getBlock())) {
            var6.getBlock().onNeighborBlockChange(this, var5, var6, blockIn);
          }
        }
      }
    }
  }
  
  public DifficultyInstance getDifficultyForLocation(BlockPos pos)
  {
    long var2 = 0L;
    float var4 = 0.0F;
    if (isBlockLoaded(pos))
    {
      var4 = getCurrentMoonPhaseFactor();
      var2 = getChunkFromBlockCoords(pos).getInhabitedTime();
    }
    return new DifficultyInstance(getDifficulty(), getWorldTime(), var2, var4);
  }
  
  public EnumDifficulty getDifficulty()
  {
    return getWorldInfo().getDifficulty();
  }
  
  public int getSkylightSubtracted()
  {
    return this.skylightSubtracted;
  }
  
  public void setSkylightSubtracted(int newSkylightSubtracted)
  {
    this.skylightSubtracted = newSkylightSubtracted;
  }
  
  public int func_175658_ac()
  {
    return this.lastLightningBolt;
  }
  
  public void setLastLightningBolt(int lastLightningBoltIn)
  {
    this.lastLightningBolt = lastLightningBoltIn;
  }
  
  public boolean isFindingSpawnPoint()
  {
    return this.findingSpawnPoint;
  }
  
  public VillageCollection getVillageCollection()
  {
    return this.villageCollectionObj;
  }
  
  public WorldBorder getWorldBorder()
  {
    return this.worldBorder;
  }
  
  public boolean chunkExists(int x, int z)
  {
    BlockPos var3 = getSpawnPoint();
    int var4 = x * 16 + 8 - var3.getX();
    int var5 = z * 16 + 8 - var3.getZ();
    short var6 = 128;
    return (var4 >= -var6) && (var4 <= var6) && (var5 >= -var6) && (var5 <= var6);
  }
}
