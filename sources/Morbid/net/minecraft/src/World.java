package net.minecraft.src;

import java.util.concurrent.*;
import java.util.*;

public abstract class World implements IBlockAccess
{
    public boolean scheduledUpdatesAreImmediate;
    public List loadedEntityList;
    protected List unloadedEntityList;
    public List loadedTileEntityList;
    private List addedTileEntityList;
    private List entityRemoval;
    public List playerEntities;
    public List weatherEffects;
    private long cloudColour;
    public int skylightSubtracted;
    protected int updateLCG;
    protected final int DIST_HASH_MAGIC = 1013904223;
    protected float prevRainingStrength;
    protected float rainingStrength;
    protected float prevThunderingStrength;
    protected float thunderingStrength;
    public int lastLightningBolt;
    public int difficultySetting;
    public Random rand;
    public final WorldProvider provider;
    protected List worldAccesses;
    protected IChunkProvider chunkProvider;
    protected final ISaveHandler saveHandler;
    protected WorldInfo worldInfo;
    public boolean findingSpawnPoint;
    public MapStorage mapStorage;
    public final VillageCollection villageCollectionObj;
    protected final VillageSiege villageSiegeObj;
    public final Profiler theProfiler;
    private final Vec3Pool vecPool;
    private final Calendar theCalendar;
    protected Scoreboard worldScoreboard;
    private final ILogAgent worldLogAgent;
    private ArrayList collidingBoundingBoxes;
    private boolean scanningTileEntities;
    protected boolean spawnHostileMobs;
    protected boolean spawnPeacefulMobs;
    protected Set activeChunkSet;
    private int ambientTickCountdown;
    int[] lightUpdateBlockList;
    public boolean isRemote;
    
    @Override
    public BiomeGenBase getBiomeGenForCoords(final int par1, final int par2) {
        if (this.blockExists(par1, 0, par2)) {
            final Chunk var3 = this.getChunkFromBlockCoords(par1, par2);
            if (var3 != null) {
                return var3.getBiomeGenForWorldCoords(par1 & 0xF, par2 & 0xF, this.provider.worldChunkMgr);
            }
        }
        return this.provider.worldChunkMgr.getBiomeGenAt(par1, par2);
    }
    
    public WorldChunkManager getWorldChunkManager() {
        return this.provider.worldChunkMgr;
    }
    
    public World(final ISaveHandler par1ISaveHandler, final String par2Str, final WorldProvider par3WorldProvider, final WorldSettings par4WorldSettings, final Profiler par5Profiler, final ILogAgent par6ILogAgent) {
        this.scheduledUpdatesAreImmediate = false;
        this.loadedEntityList = new ArrayList();
        this.unloadedEntityList = new ArrayList();
        this.loadedTileEntityList = new ArrayList();
        this.addedTileEntityList = new ArrayList();
        this.entityRemoval = new ArrayList();
        this.playerEntities = new ArrayList();
        this.weatherEffects = new ArrayList();
        this.cloudColour = 16777215L;
        this.skylightSubtracted = 0;
        this.updateLCG = new Random().nextInt();
        this.lastLightningBolt = 0;
        this.rand = new Random();
        this.worldAccesses = new ArrayList();
        this.villageSiegeObj = new VillageSiege(this);
        this.vecPool = new Vec3Pool(300, 2000);
        this.theCalendar = Calendar.getInstance();
        this.worldScoreboard = new Scoreboard();
        this.collidingBoundingBoxes = new ArrayList();
        this.spawnHostileMobs = true;
        this.spawnPeacefulMobs = true;
        this.activeChunkSet = new HashSet();
        this.ambientTickCountdown = this.rand.nextInt(12000);
        this.lightUpdateBlockList = new int[32768];
        this.isRemote = false;
        this.saveHandler = par1ISaveHandler;
        this.theProfiler = par5Profiler;
        this.worldInfo = new WorldInfo(par4WorldSettings, par2Str);
        this.provider = par3WorldProvider;
        this.mapStorage = new MapStorage(par1ISaveHandler);
        this.worldLogAgent = par6ILogAgent;
        final VillageCollection var7 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, "villages");
        if (var7 == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData("villages", this.villageCollectionObj);
        }
        else {
            (this.villageCollectionObj = var7).func_82566_a(this);
        }
        par3WorldProvider.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
    }
    
    public World(final ISaveHandler par1ISaveHandler, final String par2Str, final WorldSettings par3WorldSettings, final WorldProvider par4WorldProvider, final Profiler par5Profiler, final ILogAgent par6ILogAgent) {
        this.scheduledUpdatesAreImmediate = false;
        this.loadedEntityList = new ArrayList();
        this.unloadedEntityList = new ArrayList();
        this.loadedTileEntityList = new ArrayList();
        this.addedTileEntityList = new ArrayList();
        this.entityRemoval = new ArrayList();
        this.playerEntities = new ArrayList();
        this.weatherEffects = new ArrayList();
        this.cloudColour = 16777215L;
        this.skylightSubtracted = 0;
        this.updateLCG = new Random().nextInt();
        this.lastLightningBolt = 0;
        this.rand = new Random();
        this.worldAccesses = new ArrayList();
        this.villageSiegeObj = new VillageSiege(this);
        this.vecPool = new Vec3Pool(300, 2000);
        this.theCalendar = Calendar.getInstance();
        this.worldScoreboard = new Scoreboard();
        this.collidingBoundingBoxes = new ArrayList();
        this.spawnHostileMobs = true;
        this.spawnPeacefulMobs = true;
        this.activeChunkSet = new HashSet();
        this.ambientTickCountdown = this.rand.nextInt(12000);
        this.lightUpdateBlockList = new int[32768];
        this.isRemote = false;
        this.saveHandler = par1ISaveHandler;
        this.theProfiler = par5Profiler;
        this.mapStorage = new MapStorage(par1ISaveHandler);
        this.worldLogAgent = par6ILogAgent;
        this.worldInfo = par1ISaveHandler.loadWorldInfo();
        if (par4WorldProvider != null) {
            this.provider = par4WorldProvider;
        }
        else if (this.worldInfo != null && this.worldInfo.getDimension() != 0) {
            this.provider = WorldProvider.getProviderForDimension(this.worldInfo.getDimension());
        }
        else {
            this.provider = WorldProvider.getProviderForDimension(0);
        }
        if (this.worldInfo == null) {
            this.worldInfo = new WorldInfo(par3WorldSettings, par2Str);
        }
        else {
            this.worldInfo.setWorldName(par2Str);
        }
        this.provider.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        if (!this.worldInfo.isInitialized()) {
            try {
                this.initialize(par3WorldSettings);
            }
            catch (Throwable var9) {
                final CrashReport var8 = CrashReport.makeCrashReport(var9, "Exception initializing level");
                try {
                    this.addWorldInfoToCrashReport(var8);
                }
                catch (Throwable t) {}
                throw new ReportedException(var8);
            }
            this.worldInfo.setServerInitialized(true);
        }
        final VillageCollection var10 = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, "villages");
        if (var10 == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData("villages", this.villageCollectionObj);
        }
        else {
            (this.villageCollectionObj = var10).func_82566_a(this);
        }
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
    }
    
    protected abstract IChunkProvider createChunkProvider();
    
    protected void initialize(final WorldSettings par1WorldSettings) {
        this.worldInfo.setServerInitialized(true);
    }
    
    public void setSpawnLocation() {
        this.setSpawnLocation(8, 64, 8);
    }
    
    public int getFirstUncoveredBlock(final int par1, final int par2) {
        int var3;
        for (var3 = 63; !this.isAirBlock(par1, var3 + 1, par2); ++var3) {}
        return this.getBlockId(par1, var3, par2);
    }
    
    @Override
    public int getBlockId(final int par1, final int par2, final int par3) {
        if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000) {
            if (par2 < 0) {
                return 0;
            }
            if (par2 >= 256) {
                return 0;
            }
            Chunk var4 = null;
            try {
                var4 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
                return var4.getBlockID(par1 & 0xF, par2, par3 & 0xF);
            }
            catch (Throwable var6) {
                final CrashReport var5 = CrashReport.makeCrashReport(var6, "Exception getting block type in world");
                final CrashReportCategory var7 = var5.makeCategory("Requested block coordinates");
                var7.addCrashSection("Found chunk", var4 == null);
                var7.addCrashSection("Location", CrashReportCategory.getLocationInfo(par1, par2, par3));
                throw new ReportedException(var5);
            }
        }
        return 0;
    }
    
    @Override
    public boolean isAirBlock(final int par1, final int par2, final int par3) {
        return this.getBlockId(par1, par2, par3) == 0;
    }
    
    public boolean blockHasTileEntity(final int par1, final int par2, final int par3) {
        final int var4 = this.getBlockId(par1, par2, par3);
        return Block.blocksList[var4] != null && Block.blocksList[var4].hasTileEntity();
    }
    
    public int blockGetRenderType(final int par1, final int par2, final int par3) {
        final int var4 = this.getBlockId(par1, par2, par3);
        return (Block.blocksList[var4] != null) ? Block.blocksList[var4].getRenderType() : -1;
    }
    
    public boolean blockExists(final int par1, final int par2, final int par3) {
        return par2 >= 0 && par2 < 256 && this.chunkExists(par1 >> 4, par3 >> 4);
    }
    
    public boolean doChunksNearChunkExist(final int par1, final int par2, final int par3, final int par4) {
        return this.checkChunksExist(par1 - par4, par2 - par4, par3 - par4, par1 + par4, par2 + par4, par3 + par4);
    }
    
    public boolean checkChunksExist(int par1, final int par2, int par3, int par4, final int par5, int par6) {
        if (par5 >= 0 && par2 < 256) {
            par1 >>= 4;
            par3 >>= 4;
            par4 >>= 4;
            par6 >>= 4;
            for (int var7 = par1; var7 <= par4; ++var7) {
                for (int var8 = par3; var8 <= par6; ++var8) {
                    if (!this.chunkExists(var7, var8)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public boolean chunkExists(final int par1, final int par2) {
        return this.chunkProvider.chunkExists(par1, par2);
    }
    
    public Chunk getChunkFromBlockCoords(final int par1, final int par2) {
        return this.getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
    }
    
    public Chunk getChunkFromChunkCoords(final int par1, final int par2) {
        return this.chunkProvider.provideChunk(par1, par2);
    }
    
    public boolean setBlock(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        if (par1 < -30000000 || par3 < -30000000 || par1 >= 30000000 || par3 >= 30000000) {
            return false;
        }
        if (par2 < 0) {
            return false;
        }
        if (par2 >= 256) {
            return false;
        }
        final Chunk var7 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
        int var8 = 0;
        if ((par6 & 0x1) != 0x0) {
            var8 = var7.getBlockID(par1 & 0xF, par2, par3 & 0xF);
        }
        final boolean var9 = var7.setBlockIDWithMetadata(par1 & 0xF, par2, par3 & 0xF, par4, par5);
        this.theProfiler.startSection("checkLight");
        this.updateAllLightTypes(par1, par2, par3);
        this.theProfiler.endSection();
        if (var9) {
            if ((par6 & 0x2) != 0x0 && (!this.isRemote || (par6 & 0x4) == 0x0)) {
                this.markBlockForUpdate(par1, par2, par3);
            }
            if (!this.isRemote && (par6 & 0x1) != 0x0) {
                this.notifyBlockChange(par1, par2, par3, var8);
                final Block var10 = Block.blocksList[par4];
                if (var10 != null && var10.hasComparatorInputOverride()) {
                    this.func_96440_m(par1, par2, par3, par4);
                }
            }
        }
        return var9;
    }
    
    @Override
    public Material getBlockMaterial(final int par1, final int par2, final int par3) {
        final int var4 = this.getBlockId(par1, par2, par3);
        return (var4 == 0) ? Material.air : Block.blocksList[var4].blockMaterial;
    }
    
    @Override
    public int getBlockMetadata(int par1, final int par2, int par3) {
        if (par1 < -30000000 || par3 < -30000000 || par1 >= 30000000 || par3 >= 30000000) {
            return 0;
        }
        if (par2 < 0) {
            return 0;
        }
        if (par2 >= 256) {
            return 0;
        }
        final Chunk var4 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
        par1 &= 0xF;
        par3 &= 0xF;
        return var4.getBlockMetadata(par1, par2, par3);
    }
    
    public boolean setBlockMetadataWithNotify(final int par1, final int par2, final int par3, final int par4, final int par5) {
        if (par1 < -30000000 || par3 < -30000000 || par1 >= 30000000 || par3 >= 30000000) {
            return false;
        }
        if (par2 < 0) {
            return false;
        }
        if (par2 >= 256) {
            return false;
        }
        final Chunk var6 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
        final int var7 = par1 & 0xF;
        final int var8 = par3 & 0xF;
        final boolean var9 = var6.setBlockMetadata(var7, par2, var8, par4);
        if (var9) {
            final int var10 = var6.getBlockID(var7, par2, var8);
            if ((par5 & 0x2) != 0x0 && (!this.isRemote || (par5 & 0x4) == 0x0)) {
                this.markBlockForUpdate(par1, par2, par3);
            }
            if (!this.isRemote && (par5 & 0x1) != 0x0) {
                this.notifyBlockChange(par1, par2, par3, var10);
                final Block var11 = Block.blocksList[var10];
                if (var11 != null && var11.hasComparatorInputOverride()) {
                    this.func_96440_m(par1, par2, par3, var10);
                }
            }
        }
        return var9;
    }
    
    public boolean setBlockToAir(final int par1, final int par2, final int par3) {
        return this.setBlock(par1, par2, par3, 0, 0, 3);
    }
    
    public boolean destroyBlock(final int par1, final int par2, final int par3, final boolean par4) {
        final int var5 = this.getBlockId(par1, par2, par3);
        if (var5 > 0) {
            final int var6 = this.getBlockMetadata(par1, par2, par3);
            this.playAuxSFX(2001, par1, par2, par3, var5 + (var6 << 12));
            if (par4) {
                Block.blocksList[var5].dropBlockAsItem(this, par1, par2, par3, var6, 0);
            }
            return this.setBlock(par1, par2, par3, 0, 0, 3);
        }
        return false;
    }
    
    public boolean setBlock(final int par1, final int par2, final int par3, final int par4) {
        return this.setBlock(par1, par2, par3, par4, 0, 3);
    }
    
    public void markBlockForUpdate(final int par1, final int par2, final int par3) {
        for (int var4 = 0; var4 < this.worldAccesses.size(); ++var4) {
            this.worldAccesses.get(var4).markBlockForUpdate(par1, par2, par3);
        }
    }
    
    public void notifyBlockChange(final int par1, final int par2, final int par3, final int par4) {
        this.notifyBlocksOfNeighborChange(par1, par2, par3, par4);
    }
    
    public void markBlocksDirtyVertical(final int par1, final int par2, int par3, int par4) {
        if (par3 > par4) {
            final int var5 = par4;
            par4 = par3;
            par3 = var5;
        }
        if (!this.provider.hasNoSky) {
            for (int var5 = par3; var5 <= par4; ++var5) {
                this.updateLightByType(EnumSkyBlock.Sky, par1, var5, par2);
            }
        }
        this.markBlockRangeForRenderUpdate(par1, par3, par2, par1, par4, par2);
    }
    
    public void markBlockRangeForRenderUpdate(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        for (int var7 = 0; var7 < this.worldAccesses.size(); ++var7) {
            this.worldAccesses.get(var7).markBlockRangeForRenderUpdate(par1, par2, par3, par4, par5, par6);
        }
    }
    
    public void notifyBlocksOfNeighborChange(final int par1, final int par2, final int par3, final int par4) {
        this.notifyBlockOfNeighborChange(par1 - 1, par2, par3, par4);
        this.notifyBlockOfNeighborChange(par1 + 1, par2, par3, par4);
        this.notifyBlockOfNeighborChange(par1, par2 - 1, par3, par4);
        this.notifyBlockOfNeighborChange(par1, par2 + 1, par3, par4);
        this.notifyBlockOfNeighborChange(par1, par2, par3 - 1, par4);
        this.notifyBlockOfNeighborChange(par1, par2, par3 + 1, par4);
    }
    
    public void notifyBlocksOfNeighborChange(final int par1, final int par2, final int par3, final int par4, final int par5) {
        if (par5 != 4) {
            this.notifyBlockOfNeighborChange(par1 - 1, par2, par3, par4);
        }
        if (par5 != 5) {
            this.notifyBlockOfNeighborChange(par1 + 1, par2, par3, par4);
        }
        if (par5 != 0) {
            this.notifyBlockOfNeighborChange(par1, par2 - 1, par3, par4);
        }
        if (par5 != 1) {
            this.notifyBlockOfNeighborChange(par1, par2 + 1, par3, par4);
        }
        if (par5 != 2) {
            this.notifyBlockOfNeighborChange(par1, par2, par3 - 1, par4);
        }
        if (par5 != 3) {
            this.notifyBlockOfNeighborChange(par1, par2, par3 + 1, par4);
        }
    }
    
    public void notifyBlockOfNeighborChange(final int par1, final int par2, final int par3, final int par4) {
        if (!this.isRemote) {
            final int var5 = this.getBlockId(par1, par2, par3);
            final Block var6 = Block.blocksList[var5];
            if (var6 != null) {
                try {
                    var6.onNeighborBlockChange(this, par1, par2, par3, par4);
                }
                catch (Throwable var8) {
                    final CrashReport var7 = CrashReport.makeCrashReport(var8, "Exception while updating neighbours");
                    final CrashReportCategory var9 = var7.makeCategory("Block being updated");
                    int var10;
                    try {
                        var10 = this.getBlockMetadata(par1, par2, par3);
                    }
                    catch (Throwable var11) {
                        var10 = -1;
                    }
                    var9.addCrashSectionCallable("Source block type", new CallableLvl1(this, par4));
                    CrashReportCategory.func_85068_a(var9, par1, par2, par3, var5, var10);
                    throw new ReportedException(var7);
                }
            }
        }
    }
    
    public boolean isBlockTickScheduled(final int par1, final int par2, final int par3, final int par4) {
        return false;
    }
    
    public boolean canBlockSeeTheSky(final int par1, final int par2, final int par3) {
        return this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4).canBlockSeeTheSky(par1 & 0xF, par2, par3 & 0xF);
    }
    
    public int getFullBlockLightValue(final int par1, int par2, final int par3) {
        if (par2 < 0) {
            return 0;
        }
        if (par2 >= 256) {
            par2 = 255;
        }
        return this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4).getBlockLightValue(par1 & 0xF, par2, par3 & 0xF, 0);
    }
    
    public int getBlockLightValue(final int par1, final int par2, final int par3) {
        return this.getBlockLightValue_do(par1, par2, par3, true);
    }
    
    public int getBlockLightValue_do(int par1, int par2, int par3, final boolean par4) {
        if (par1 < -30000000 || par3 < -30000000 || par1 >= 30000000 || par3 >= 30000000) {
            return 15;
        }
        if (par4) {
            final int var5 = this.getBlockId(par1, par2, par3);
            if (Block.useNeighborBrightness[var5]) {
                int var6 = this.getBlockLightValue_do(par1, par2 + 1, par3, false);
                final int var7 = this.getBlockLightValue_do(par1 + 1, par2, par3, false);
                final int var8 = this.getBlockLightValue_do(par1 - 1, par2, par3, false);
                final int var9 = this.getBlockLightValue_do(par1, par2, par3 + 1, false);
                final int var10 = this.getBlockLightValue_do(par1, par2, par3 - 1, false);
                if (var7 > var6) {
                    var6 = var7;
                }
                if (var8 > var6) {
                    var6 = var8;
                }
                if (var9 > var6) {
                    var6 = var9;
                }
                if (var10 > var6) {
                    var6 = var10;
                }
                return var6;
            }
        }
        if (par2 < 0) {
            return 0;
        }
        if (par2 >= 256) {
            par2 = 255;
        }
        final Chunk var11 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
        par1 &= 0xF;
        par3 &= 0xF;
        return var11.getBlockLightValue(par1, par2, par3, this.skylightSubtracted);
    }
    
    public int getHeightValue(final int par1, final int par2) {
        if (par1 < -30000000 || par2 < -30000000 || par1 >= 30000000 || par2 >= 30000000) {
            return 0;
        }
        if (!this.chunkExists(par1 >> 4, par2 >> 4)) {
            return 0;
        }
        final Chunk var3 = this.getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
        return var3.getHeightValue(par1 & 0xF, par2 & 0xF);
    }
    
    public int getChunkHeightMapMinimum(final int par1, final int par2) {
        if (par1 < -30000000 || par2 < -30000000 || par1 >= 30000000 || par2 >= 30000000) {
            return 0;
        }
        if (!this.chunkExists(par1 >> 4, par2 >> 4)) {
            return 0;
        }
        final Chunk var3 = this.getChunkFromChunkCoords(par1 >> 4, par2 >> 4);
        return var3.heightMapMinimum;
    }
    
    public int getSkyBlockTypeBrightness(final EnumSkyBlock par1EnumSkyBlock, final int par2, int par3, final int par4) {
        if (this.provider.hasNoSky && par1EnumSkyBlock == EnumSkyBlock.Sky) {
            return 0;
        }
        if (par3 < 0) {
            par3 = 0;
        }
        if (par3 >= 256) {
            return par1EnumSkyBlock.defaultLightValue;
        }
        if (par2 < -30000000 || par4 < -30000000 || par2 >= 30000000 || par4 >= 30000000) {
            return par1EnumSkyBlock.defaultLightValue;
        }
        final int var5 = par2 >> 4;
        final int var6 = par4 >> 4;
        if (!this.chunkExists(var5, var6)) {
            return par1EnumSkyBlock.defaultLightValue;
        }
        if (Block.useNeighborBrightness[this.getBlockId(par2, par3, par4)]) {
            int var7 = this.getSavedLightValue(par1EnumSkyBlock, par2, par3 + 1, par4);
            final int var8 = this.getSavedLightValue(par1EnumSkyBlock, par2 + 1, par3, par4);
            final int var9 = this.getSavedLightValue(par1EnumSkyBlock, par2 - 1, par3, par4);
            final int var10 = this.getSavedLightValue(par1EnumSkyBlock, par2, par3, par4 + 1);
            final int var11 = this.getSavedLightValue(par1EnumSkyBlock, par2, par3, par4 - 1);
            if (var8 > var7) {
                var7 = var8;
            }
            if (var9 > var7) {
                var7 = var9;
            }
            if (var10 > var7) {
                var7 = var10;
            }
            if (var11 > var7) {
                var7 = var11;
            }
            return var7;
        }
        final Chunk var12 = this.getChunkFromChunkCoords(var5, var6);
        return var12.getSavedLightValue(par1EnumSkyBlock, par2 & 0xF, par3, par4 & 0xF);
    }
    
    public int getSavedLightValue(final EnumSkyBlock par1EnumSkyBlock, final int par2, int par3, final int par4) {
        if (par3 < 0) {
            par3 = 0;
        }
        if (par3 >= 256) {
            par3 = 255;
        }
        if (par2 < -30000000 || par4 < -30000000 || par2 >= 30000000 || par4 >= 30000000) {
            return par1EnumSkyBlock.defaultLightValue;
        }
        final int var5 = par2 >> 4;
        final int var6 = par4 >> 4;
        if (!this.chunkExists(var5, var6)) {
            return par1EnumSkyBlock.defaultLightValue;
        }
        final Chunk var7 = this.getChunkFromChunkCoords(var5, var6);
        return var7.getSavedLightValue(par1EnumSkyBlock, par2 & 0xF, par3, par4 & 0xF);
    }
    
    public void setLightValue(final EnumSkyBlock par1EnumSkyBlock, final int par2, final int par3, final int par4, final int par5) {
        if (par2 >= -30000000 && par4 >= -30000000 && par2 < 30000000 && par4 < 30000000 && par3 >= 0 && par3 < 256 && this.chunkExists(par2 >> 4, par4 >> 4)) {
            final Chunk var6 = this.getChunkFromChunkCoords(par2 >> 4, par4 >> 4);
            var6.setLightValue(par1EnumSkyBlock, par2 & 0xF, par3, par4 & 0xF, par5);
            for (int var7 = 0; var7 < this.worldAccesses.size(); ++var7) {
                this.worldAccesses.get(var7).markBlockForRenderUpdate(par2, par3, par4);
            }
        }
    }
    
    public void markBlockForRenderUpdate(final int par1, final int par2, final int par3) {
        for (int var4 = 0; var4 < this.worldAccesses.size(); ++var4) {
            this.worldAccesses.get(var4).markBlockForRenderUpdate(par1, par2, par3);
        }
    }
    
    @Override
    public int getLightBrightnessForSkyBlocks(final int par1, final int par2, final int par3, final int par4) {
        final int var5 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, par1, par2, par3);
        int var6 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Block, par1, par2, par3);
        if (var6 < par4) {
            var6 = par4;
        }
        return var5 << 20 | var6 << 4;
    }
    
    @Override
    public float getBrightness(final int par1, final int par2, final int par3, final int par4) {
        int var5 = this.getBlockLightValue(par1, par2, par3);
        if (var5 < par4) {
            var5 = par4;
        }
        return this.provider.lightBrightnessTable[var5];
    }
    
    @Override
    public float getLightBrightness(final int par1, final int par2, final int par3) {
        return this.provider.lightBrightnessTable[this.getBlockLightValue(par1, par2, par3)];
    }
    
    public boolean isDaytime() {
        return this.skylightSubtracted < 4;
    }
    
    public MovingObjectPosition rayTraceBlocks(final Vec3 par1Vec3, final Vec3 par2Vec3) {
        return this.rayTraceBlocks_do_do(par1Vec3, par2Vec3, false, false);
    }
    
    public MovingObjectPosition rayTraceBlocks_do(final Vec3 par1Vec3, final Vec3 par2Vec3, final boolean par3) {
        return this.rayTraceBlocks_do_do(par1Vec3, par2Vec3, par3, false);
    }
    
    public MovingObjectPosition rayTraceBlocks_do_do(final Vec3 par1Vec3, final Vec3 par2Vec3, final boolean par3, final boolean par4) {
        if (Double.isNaN(par1Vec3.xCoord) || Double.isNaN(par1Vec3.yCoord) || Double.isNaN(par1Vec3.zCoord)) {
            return null;
        }
        if (!Double.isNaN(par2Vec3.xCoord) && !Double.isNaN(par2Vec3.yCoord) && !Double.isNaN(par2Vec3.zCoord)) {
            final int var5 = MathHelper.floor_double(par2Vec3.xCoord);
            final int var6 = MathHelper.floor_double(par2Vec3.yCoord);
            final int var7 = MathHelper.floor_double(par2Vec3.zCoord);
            int var8 = MathHelper.floor_double(par1Vec3.xCoord);
            int var9 = MathHelper.floor_double(par1Vec3.yCoord);
            int var10 = MathHelper.floor_double(par1Vec3.zCoord);
            int var11 = this.getBlockId(var8, var9, var10);
            final int var12 = this.getBlockMetadata(var8, var9, var10);
            final Block var13 = Block.blocksList[var11];
            if ((!par4 || var13 == null || var13.getCollisionBoundingBoxFromPool(this, var8, var9, var10) != null) && var11 > 0 && var13.canCollideCheck(var12, par3)) {
                final MovingObjectPosition var14 = var13.collisionRayTrace(this, var8, var9, var10, par1Vec3, par2Vec3);
                if (var14 != null) {
                    return var14;
                }
            }
            var11 = 200;
            while (var11-- >= 0) {
                if (Double.isNaN(par1Vec3.xCoord) || Double.isNaN(par1Vec3.yCoord) || Double.isNaN(par1Vec3.zCoord)) {
                    return null;
                }
                if (var8 == var5 && var9 == var6 && var10 == var7) {
                    return null;
                }
                boolean var15 = true;
                boolean var16 = true;
                boolean var17 = true;
                double var18 = 999.0;
                double var19 = 999.0;
                double var20 = 999.0;
                if (var5 > var8) {
                    var18 = var8 + 1.0;
                }
                else if (var5 < var8) {
                    var18 = var8 + 0.0;
                }
                else {
                    var15 = false;
                }
                if (var6 > var9) {
                    var19 = var9 + 1.0;
                }
                else if (var6 < var9) {
                    var19 = var9 + 0.0;
                }
                else {
                    var16 = false;
                }
                if (var7 > var10) {
                    var20 = var10 + 1.0;
                }
                else if (var7 < var10) {
                    var20 = var10 + 0.0;
                }
                else {
                    var17 = false;
                }
                double var21 = 999.0;
                double var22 = 999.0;
                double var23 = 999.0;
                final double var24 = par2Vec3.xCoord - par1Vec3.xCoord;
                final double var25 = par2Vec3.yCoord - par1Vec3.yCoord;
                final double var26 = par2Vec3.zCoord - par1Vec3.zCoord;
                if (var15) {
                    var21 = (var18 - par1Vec3.xCoord) / var24;
                }
                if (var16) {
                    var22 = (var19 - par1Vec3.yCoord) / var25;
                }
                if (var17) {
                    var23 = (var20 - par1Vec3.zCoord) / var26;
                }
                final boolean var27 = false;
                byte var28;
                if (var21 < var22 && var21 < var23) {
                    if (var5 > var8) {
                        var28 = 4;
                    }
                    else {
                        var28 = 5;
                    }
                    par1Vec3.xCoord = var18;
                    par1Vec3.yCoord += var25 * var21;
                    par1Vec3.zCoord += var26 * var21;
                }
                else if (var22 < var23) {
                    if (var6 > var9) {
                        var28 = 0;
                    }
                    else {
                        var28 = 1;
                    }
                    par1Vec3.xCoord += var24 * var22;
                    par1Vec3.yCoord = var19;
                    par1Vec3.zCoord += var26 * var22;
                }
                else {
                    if (var7 > var10) {
                        var28 = 2;
                    }
                    else {
                        var28 = 3;
                    }
                    par1Vec3.xCoord += var24 * var23;
                    par1Vec3.yCoord += var25 * var23;
                    par1Vec3.zCoord = var20;
                }
                final Vec3 vecFromPool;
                final Vec3 var29 = vecFromPool = this.getWorldVec3Pool().getVecFromPool(par1Vec3.xCoord, par1Vec3.yCoord, par1Vec3.zCoord);
                final double xCoord = MathHelper.floor_double(par1Vec3.xCoord);
                vecFromPool.xCoord = xCoord;
                var8 = (int)xCoord;
                if (var28 == 5) {
                    --var8;
                    final Vec3 vec3 = var29;
                    ++vec3.xCoord;
                }
                final Vec3 vec4 = var29;
                final double yCoord = MathHelper.floor_double(par1Vec3.yCoord);
                vec4.yCoord = yCoord;
                var9 = (int)yCoord;
                if (var28 == 1) {
                    --var9;
                    final Vec3 vec5 = var29;
                    ++vec5.yCoord;
                }
                final Vec3 vec6 = var29;
                final double zCoord = MathHelper.floor_double(par1Vec3.zCoord);
                vec6.zCoord = zCoord;
                var10 = (int)zCoord;
                if (var28 == 3) {
                    --var10;
                    final Vec3 vec7 = var29;
                    ++vec7.zCoord;
                }
                final int var30 = this.getBlockId(var8, var9, var10);
                final int var31 = this.getBlockMetadata(var8, var9, var10);
                final Block var32 = Block.blocksList[var30];
                if ((par4 && var32 != null && var32.getCollisionBoundingBoxFromPool(this, var8, var9, var10) == null) || var30 <= 0 || !var32.canCollideCheck(var31, par3)) {
                    continue;
                }
                final MovingObjectPosition var33 = var32.collisionRayTrace(this, var8, var9, var10, par1Vec3, par2Vec3);
                if (var33 != null) {
                    return var33;
                }
            }
            return null;
        }
        return null;
    }
    
    public void playSoundAtEntity(final Entity par1Entity, final String par2Str, final float par3, final float par4) {
        if (par1Entity != null && par2Str != null) {
            for (int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
                this.worldAccesses.get(var5).playSound(par2Str, par1Entity.posX, par1Entity.posY - par1Entity.yOffset, par1Entity.posZ, par3, par4);
            }
        }
    }
    
    public void playSoundToNearExcept(final EntityPlayer par1EntityPlayer, final String par2Str, final float par3, final float par4) {
        if (par1EntityPlayer != null && par2Str != null) {
            for (int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
                this.worldAccesses.get(var5).playSoundToNearExcept(par1EntityPlayer, par2Str, par1EntityPlayer.posX, par1EntityPlayer.posY - par1EntityPlayer.yOffset, par1EntityPlayer.posZ, par3, par4);
            }
        }
    }
    
    public void playSoundEffect(final double par1, final double par3, final double par5, final String par7Str, final float par8, final float par9) {
        if (par7Str != null) {
            for (int var10 = 0; var10 < this.worldAccesses.size(); ++var10) {
                this.worldAccesses.get(var10).playSound(par7Str, par1, par3, par5, par8, par9);
            }
        }
    }
    
    public void playSound(final double par1, final double par3, final double par5, final String par7Str, final float par8, final float par9, final boolean par10) {
    }
    
    public void playRecord(final String par1Str, final int par2, final int par3, final int par4) {
        for (int var5 = 0; var5 < this.worldAccesses.size(); ++var5) {
            this.worldAccesses.get(var5).playRecord(par1Str, par2, par3, par4);
        }
    }
    
    public void spawnParticle(final String par1Str, final double par2, final double par4, final double par6, final double par8, final double par10, final double par12) {
        for (int var14 = 0; var14 < this.worldAccesses.size(); ++var14) {
            this.worldAccesses.get(var14).spawnParticle(par1Str, par2, par4, par6, par8, par10, par12);
        }
    }
    
    public boolean addWeatherEffect(final Entity par1Entity) {
        this.weatherEffects.add(par1Entity);
        return true;
    }
    
    public boolean spawnEntityInWorld(final Entity par1Entity) {
        final int var2 = MathHelper.floor_double(par1Entity.posX / 16.0);
        final int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0);
        boolean var4 = par1Entity.field_98038_p;
        if (par1Entity instanceof EntityPlayer) {
            var4 = true;
        }
        if (!var4 && !this.chunkExists(var2, var3)) {
            return false;
        }
        if (par1Entity instanceof EntityPlayer) {
            final EntityPlayer var5 = (EntityPlayer)par1Entity;
            this.playerEntities.add(var5);
            this.updateAllPlayersSleepingFlag();
        }
        this.getChunkFromChunkCoords(var2, var3).addEntity(par1Entity);
        this.loadedEntityList.add(par1Entity);
        this.obtainEntitySkin(par1Entity);
        return true;
    }
    
    protected void obtainEntitySkin(final Entity par1Entity) {
        for (int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
            this.worldAccesses.get(var2).onEntityCreate(par1Entity);
        }
    }
    
    protected void releaseEntitySkin(final Entity par1Entity) {
        for (int var2 = 0; var2 < this.worldAccesses.size(); ++var2) {
            this.worldAccesses.get(var2).onEntityDestroy(par1Entity);
        }
    }
    
    public void removeEntity(final Entity par1Entity) {
        if (par1Entity.riddenByEntity != null) {
            par1Entity.riddenByEntity.mountEntity(null);
        }
        if (par1Entity.ridingEntity != null) {
            par1Entity.mountEntity(null);
        }
        par1Entity.setDead();
        if (par1Entity instanceof EntityPlayer) {
            this.playerEntities.remove(par1Entity);
            this.updateAllPlayersSleepingFlag();
        }
    }
    
    public void removePlayerEntityDangerously(final Entity par1Entity) {
        par1Entity.setDead();
        if (par1Entity instanceof EntityPlayer) {
            this.playerEntities.remove(par1Entity);
            this.updateAllPlayersSleepingFlag();
        }
        final int var2 = par1Entity.chunkCoordX;
        final int var3 = par1Entity.chunkCoordZ;
        if (par1Entity.addedToChunk && this.chunkExists(var2, var3)) {
            this.getChunkFromChunkCoords(var2, var3).removeEntity(par1Entity);
        }
        this.loadedEntityList.remove(par1Entity);
        this.releaseEntitySkin(par1Entity);
    }
    
    public void addWorldAccess(final IWorldAccess par1IWorldAccess) {
        this.worldAccesses.add(par1IWorldAccess);
    }
    
    public void removeWorldAccess(final IWorldAccess par1IWorldAccess) {
        this.worldAccesses.remove(par1IWorldAccess);
    }
    
    public List getCollidingBoundingBoxes(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB) {
        this.collidingBoundingBoxes.clear();
        final int var3 = MathHelper.floor_double(par2AxisAlignedBB.minX);
        final int var4 = MathHelper.floor_double(par2AxisAlignedBB.maxX + 1.0);
        final int var5 = MathHelper.floor_double(par2AxisAlignedBB.minY);
        final int var6 = MathHelper.floor_double(par2AxisAlignedBB.maxY + 1.0);
        final int var7 = MathHelper.floor_double(par2AxisAlignedBB.minZ);
        final int var8 = MathHelper.floor_double(par2AxisAlignedBB.maxZ + 1.0);
        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var7; var10 < var8; ++var10) {
                if (this.blockExists(var9, 64, var10)) {
                    for (int var11 = var5 - 1; var11 < var6; ++var11) {
                        final Block var12 = Block.blocksList[this.getBlockId(var9, var11, var10)];
                        if (var12 != null) {
                            var12.addCollisionBoxesToList(this, var9, var11, var10, par2AxisAlignedBB, this.collidingBoundingBoxes, par1Entity);
                        }
                    }
                }
            }
        }
        final double var13 = 0.25;
        final List var14 = this.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB.expand(var13, var13, var13));
        for (int var15 = 0; var15 < var14.size(); ++var15) {
            AxisAlignedBB var16 = var14.get(var15).getBoundingBox();
            if (var16 != null && var16.intersectsWith(par2AxisAlignedBB)) {
                this.collidingBoundingBoxes.add(var16);
            }
            var16 = par1Entity.getCollisionBox(var14.get(var15));
            if (var16 != null && var16.intersectsWith(par2AxisAlignedBB)) {
                this.collidingBoundingBoxes.add(var16);
            }
        }
        return this.collidingBoundingBoxes;
    }
    
    public List getCollidingBlockBounds(final AxisAlignedBB par1AxisAlignedBB) {
        this.collidingBoundingBoxes.clear();
        final int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        final int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        final int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        final int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        final int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        final int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        for (int var8 = var2; var8 < var3; ++var8) {
            for (int var9 = var6; var9 < var7; ++var9) {
                if (this.blockExists(var8, 64, var9)) {
                    for (int var10 = var4 - 1; var10 < var5; ++var10) {
                        final Block var11 = Block.blocksList[this.getBlockId(var8, var10, var9)];
                        if (var11 != null) {
                            var11.addCollisionBoxesToList(this, var8, var10, var9, par1AxisAlignedBB, this.collidingBoundingBoxes, null);
                        }
                    }
                }
            }
        }
        return this.collidingBoundingBoxes;
    }
    
    public int calculateSkylightSubtracted(final float par1) {
        final float var2 = this.getCelestialAngle(par1);
        float var3 = 1.0f - (MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.5f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        var3 = 1.0f - var3;
        var3 *= (float)(1.0 - this.getRainStrength(par1) * 5.0f / 16.0);
        var3 *= (float)(1.0 - this.getWeightedThunderStrength(par1) * 5.0f / 16.0);
        var3 = 1.0f - var3;
        return (int)(var3 * 11.0f);
    }
    
    public float getSunBrightness(final float par1) {
        final float var2 = this.getCelestialAngle(par1);
        float var3 = 1.0f - (MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.2f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        var3 = 1.0f - var3;
        var3 *= (float)(1.0 - this.getRainStrength(par1) * 5.0f / 16.0);
        var3 *= (float)(1.0 - this.getWeightedThunderStrength(par1) * 5.0f / 16.0);
        return var3 * 0.8f + 0.2f;
    }
    
    public Vec3 getSkyColor(final Entity par1Entity, final float par2) {
        final float var3 = this.getCelestialAngle(par2);
        float var4 = MathHelper.cos(var3 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var4 < 0.0f) {
            var4 = 0.0f;
        }
        if (var4 > 1.0f) {
            var4 = 1.0f;
        }
        final int var5 = MathHelper.floor_double(par1Entity.posX);
        final int var6 = MathHelper.floor_double(par1Entity.posZ);
        final BiomeGenBase var7 = this.getBiomeGenForCoords(var5, var6);
        final float var8 = var7.getFloatTemperature();
        final int var9 = var7.getSkyColorByTemp(var8);
        float var10 = (var9 >> 16 & 0xFF) / 255.0f;
        float var11 = (var9 >> 8 & 0xFF) / 255.0f;
        float var12 = (var9 & 0xFF) / 255.0f;
        var10 *= var4;
        var11 *= var4;
        var12 *= var4;
        final float var13 = this.getRainStrength(par2);
        if (var13 > 0.0f) {
            final float var14 = (var10 * 0.3f + var11 * 0.59f + var12 * 0.11f) * 0.6f;
            final float var15 = 1.0f - var13 * 0.75f;
            var10 = var10 * var15 + var14 * (1.0f - var15);
            var11 = var11 * var15 + var14 * (1.0f - var15);
            var12 = var12 * var15 + var14 * (1.0f - var15);
        }
        final float var14 = this.getWeightedThunderStrength(par2);
        if (var14 > 0.0f) {
            final float var15 = (var10 * 0.3f + var11 * 0.59f + var12 * 0.11f) * 0.2f;
            final float var16 = 1.0f - var14 * 0.75f;
            var10 = var10 * var16 + var15 * (1.0f - var16);
            var11 = var11 * var16 + var15 * (1.0f - var16);
            var12 = var12 * var16 + var15 * (1.0f - var16);
        }
        if (this.lastLightningBolt > 0) {
            float var15 = this.lastLightningBolt - par2;
            if (var15 > 1.0f) {
                var15 = 1.0f;
            }
            var15 *= 0.45f;
            var10 = var10 * (1.0f - var15) + 0.8f * var15;
            var11 = var11 * (1.0f - var15) + 0.8f * var15;
            var12 = var12 * (1.0f - var15) + 1.0f * var15;
        }
        return this.getWorldVec3Pool().getVecFromPool(var10, var11, var12);
    }
    
    public float getCelestialAngle(final float par1) {
        return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), par1);
    }
    
    public int getMoonPhase() {
        return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
    }
    
    public float getCelestialAngleRadians(final float par1) {
        final float var2 = this.getCelestialAngle(par1);
        return var2 * 3.1415927f * 2.0f;
    }
    
    public Vec3 getCloudColour(final float par1) {
        final float var2 = this.getCelestialAngle(par1);
        float var3 = MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        float var4 = (this.cloudColour >> 16 & 0xFFL) / 255.0f;
        float var5 = (this.cloudColour >> 8 & 0xFFL) / 255.0f;
        float var6 = (this.cloudColour & 0xFFL) / 255.0f;
        final float var7 = this.getRainStrength(par1);
        if (var7 > 0.0f) {
            final float var8 = (var4 * 0.3f + var5 * 0.59f + var6 * 0.11f) * 0.6f;
            final float var9 = 1.0f - var7 * 0.95f;
            var4 = var4 * var9 + var8 * (1.0f - var9);
            var5 = var5 * var9 + var8 * (1.0f - var9);
            var6 = var6 * var9 + var8 * (1.0f - var9);
        }
        var4 *= var3 * 0.9f + 0.1f;
        var5 *= var3 * 0.9f + 0.1f;
        var6 *= var3 * 0.85f + 0.15f;
        final float var8 = this.getWeightedThunderStrength(par1);
        if (var8 > 0.0f) {
            final float var9 = (var4 * 0.3f + var5 * 0.59f + var6 * 0.11f) * 0.2f;
            final float var10 = 1.0f - var8 * 0.95f;
            var4 = var4 * var10 + var9 * (1.0f - var10);
            var5 = var5 * var10 + var9 * (1.0f - var10);
            var6 = var6 * var10 + var9 * (1.0f - var10);
        }
        return this.getWorldVec3Pool().getVecFromPool(var4, var5, var6);
    }
    
    public Vec3 getFogColor(final float par1) {
        final float var2 = this.getCelestialAngle(par1);
        return this.provider.getFogColor(var2, par1);
    }
    
    public int getPrecipitationHeight(final int par1, final int par2) {
        return this.getChunkFromBlockCoords(par1, par2).getPrecipitationHeight(par1 & 0xF, par2 & 0xF);
    }
    
    public int getTopSolidOrLiquidBlock(int par1, int par2) {
        final Chunk var3 = this.getChunkFromBlockCoords(par1, par2);
        int var4 = var3.getTopFilledSegment() + 15;
        par1 &= 0xF;
        par2 &= 0xF;
        while (var4 > 0) {
            final int var5 = var3.getBlockID(par1, var4, par2);
            if (var5 != 0 && Block.blocksList[var5].blockMaterial.blocksMovement() && Block.blocksList[var5].blockMaterial != Material.leaves) {
                return var4 + 1;
            }
            --var4;
        }
        return -1;
    }
    
    public float getStarBrightness(final float par1) {
        final float var2 = this.getCelestialAngle(par1);
        float var3 = 1.0f - (MathHelper.cos(var2 * 3.1415927f * 2.0f) * 2.0f + 0.25f);
        if (var3 < 0.0f) {
            var3 = 0.0f;
        }
        if (var3 > 1.0f) {
            var3 = 1.0f;
        }
        return var3 * var3 * 0.5f;
    }
    
    public void scheduleBlockUpdate(final int par1, final int par2, final int par3, final int par4, final int par5) {
    }
    
    public void func_82740_a(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
    }
    
    public void scheduleBlockUpdateFromLoad(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
    }
    
    public void updateEntities() {
        this.theProfiler.startSection("entities");
        this.theProfiler.startSection("global");
        for (int var1 = 0; var1 < this.weatherEffects.size(); ++var1) {
            final Entity var2 = this.weatherEffects.get(var1);
            try {
                final Entity entity = var2;
                ++entity.ticksExisted;
                var2.onUpdate();
            }
            catch (Throwable var4) {
                final CrashReport var3 = CrashReport.makeCrashReport(var4, "Ticking entity");
                final CrashReportCategory var5 = var3.makeCategory("Entity being ticked");
                if (var2 == null) {
                    var5.addCrashSection("Entity", "~~NULL~~");
                }
                else {
                    var2.func_85029_a(var5);
                }
                throw new ReportedException(var3);
            }
            if (var2.isDead) {
                this.weatherEffects.remove(var1--);
            }
        }
        this.theProfiler.endStartSection("remove");
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        for (int var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
            final Entity var2 = this.unloadedEntityList.get(var1);
            final int var6 = var2.chunkCoordX;
            final int var7 = var2.chunkCoordZ;
            if (var2.addedToChunk && this.chunkExists(var6, var7)) {
                this.getChunkFromChunkCoords(var6, var7).removeEntity(var2);
            }
        }
        for (int var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
            this.releaseEntitySkin(this.unloadedEntityList.get(var1));
        }
        this.unloadedEntityList.clear();
        this.theProfiler.endStartSection("regular");
        for (int var1 = 0; var1 < this.loadedEntityList.size(); ++var1) {
            final Entity var2 = this.loadedEntityList.get(var1);
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
                }
                catch (Throwable var8) {
                    final CrashReport var3 = CrashReport.makeCrashReport(var8, "Ticking entity");
                    final CrashReportCategory var5 = var3.makeCategory("Entity being ticked");
                    var2.func_85029_a(var5);
                    throw new ReportedException(var3);
                }
            }
            this.theProfiler.endSection();
            this.theProfiler.startSection("remove");
            if (var2.isDead) {
                final int var6 = var2.chunkCoordX;
                final int var7 = var2.chunkCoordZ;
                if (var2.addedToChunk && this.chunkExists(var6, var7)) {
                    this.getChunkFromChunkCoords(var6, var7).removeEntity(var2);
                }
                this.loadedEntityList.remove(var1--);
                this.releaseEntitySkin(var2);
            }
            this.theProfiler.endSection();
        }
        this.theProfiler.endStartSection("tileEntities");
        this.scanningTileEntities = true;
        final Iterator var9 = this.loadedTileEntityList.iterator();
        while (var9.hasNext()) {
            final TileEntity var10 = var9.next();
            if (!var10.isInvalid() && var10.func_70309_m() && this.blockExists(var10.xCoord, var10.yCoord, var10.zCoord)) {
                try {
                    var10.updateEntity();
                }
                catch (Throwable var11) {
                    final CrashReport var3 = CrashReport.makeCrashReport(var11, "Ticking tile entity");
                    final CrashReportCategory var5 = var3.makeCategory("Tile entity being ticked");
                    var10.func_85027_a(var5);
                    throw new ReportedException(var3);
                }
            }
            if (var10.isInvalid()) {
                var9.remove();
                if (!this.chunkExists(var10.xCoord >> 4, var10.zCoord >> 4)) {
                    continue;
                }
                final Chunk var12 = this.getChunkFromChunkCoords(var10.xCoord >> 4, var10.zCoord >> 4);
                if (var12 == null) {
                    continue;
                }
                var12.removeChunkBlockTileEntity(var10.xCoord & 0xF, var10.yCoord, var10.zCoord & 0xF);
            }
        }
        this.scanningTileEntities = false;
        if (!this.entityRemoval.isEmpty()) {
            this.loadedTileEntityList.removeAll(this.entityRemoval);
            this.entityRemoval.clear();
        }
        this.theProfiler.endStartSection("pendingTileEntities");
        if (!this.addedTileEntityList.isEmpty()) {
            for (int var13 = 0; var13 < this.addedTileEntityList.size(); ++var13) {
                final TileEntity var14 = this.addedTileEntityList.get(var13);
                if (!var14.isInvalid()) {
                    if (!this.loadedTileEntityList.contains(var14)) {
                        this.loadedTileEntityList.add(var14);
                    }
                    if (this.chunkExists(var14.xCoord >> 4, var14.zCoord >> 4)) {
                        final Chunk var15 = this.getChunkFromChunkCoords(var14.xCoord >> 4, var14.zCoord >> 4);
                        if (var15 != null) {
                            var15.setChunkBlockTileEntity(var14.xCoord & 0xF, var14.yCoord, var14.zCoord & 0xF, var14);
                        }
                    }
                    this.markBlockForUpdate(var14.xCoord, var14.yCoord, var14.zCoord);
                }
            }
            this.addedTileEntityList.clear();
        }
        this.theProfiler.endSection();
        this.theProfiler.endSection();
    }
    
    public void addTileEntity(final Collection par1Collection) {
        if (this.scanningTileEntities) {
            this.addedTileEntityList.addAll(par1Collection);
        }
        else {
            this.loadedTileEntityList.addAll(par1Collection);
        }
    }
    
    public void updateEntity(final Entity par1Entity) {
        this.updateEntityWithOptionalForce(par1Entity, true);
    }
    
    public void updateEntityWithOptionalForce(final Entity par1Entity, final boolean par2) {
        final int var3 = MathHelper.floor_double(par1Entity.posX);
        final int var4 = MathHelper.floor_double(par1Entity.posZ);
        final byte var5 = 32;
        if (!par2 || this.checkChunksExist(var3 - var5, 0, var4 - var5, var3 + var5, 0, var4 + var5)) {
            par1Entity.lastTickPosX = par1Entity.posX;
            par1Entity.lastTickPosY = par1Entity.posY;
            par1Entity.lastTickPosZ = par1Entity.posZ;
            par1Entity.prevRotationYaw = par1Entity.rotationYaw;
            par1Entity.prevRotationPitch = par1Entity.rotationPitch;
            if (par2 && par1Entity.addedToChunk) {
                if (par1Entity.ridingEntity != null) {
                    par1Entity.updateRidden();
                }
                else {
                    ++par1Entity.ticksExisted;
                    par1Entity.onUpdate();
                }
            }
            this.theProfiler.startSection("chunkCheck");
            if (Double.isNaN(par1Entity.posX) || Double.isInfinite(par1Entity.posX)) {
                par1Entity.posX = par1Entity.lastTickPosX;
            }
            if (Double.isNaN(par1Entity.posY) || Double.isInfinite(par1Entity.posY)) {
                par1Entity.posY = par1Entity.lastTickPosY;
            }
            if (Double.isNaN(par1Entity.posZ) || Double.isInfinite(par1Entity.posZ)) {
                par1Entity.posZ = par1Entity.lastTickPosZ;
            }
            if (Double.isNaN(par1Entity.rotationPitch) || Double.isInfinite(par1Entity.rotationPitch)) {
                par1Entity.rotationPitch = par1Entity.prevRotationPitch;
            }
            if (Double.isNaN(par1Entity.rotationYaw) || Double.isInfinite(par1Entity.rotationYaw)) {
                par1Entity.rotationYaw = par1Entity.prevRotationYaw;
            }
            final int var6 = MathHelper.floor_double(par1Entity.posX / 16.0);
            final int var7 = MathHelper.floor_double(par1Entity.posY / 16.0);
            final int var8 = MathHelper.floor_double(par1Entity.posZ / 16.0);
            if (!par1Entity.addedToChunk || par1Entity.chunkCoordX != var6 || par1Entity.chunkCoordY != var7 || par1Entity.chunkCoordZ != var8) {
                if (par1Entity.addedToChunk && this.chunkExists(par1Entity.chunkCoordX, par1Entity.chunkCoordZ)) {
                    this.getChunkFromChunkCoords(par1Entity.chunkCoordX, par1Entity.chunkCoordZ).removeEntityAtIndex(par1Entity, par1Entity.chunkCoordY);
                }
                if (this.chunkExists(var6, var8)) {
                    par1Entity.addedToChunk = true;
                    this.getChunkFromChunkCoords(var6, var8).addEntity(par1Entity);
                }
                else {
                    par1Entity.addedToChunk = false;
                }
            }
            this.theProfiler.endSection();
            if (par2 && par1Entity.addedToChunk && par1Entity.riddenByEntity != null) {
                if (!par1Entity.riddenByEntity.isDead && par1Entity.riddenByEntity.ridingEntity == par1Entity) {
                    this.updateEntity(par1Entity.riddenByEntity);
                }
                else {
                    par1Entity.riddenByEntity.ridingEntity = null;
                    par1Entity.riddenByEntity = null;
                }
            }
        }
    }
    
    public boolean checkNoEntityCollision(final AxisAlignedBB par1AxisAlignedBB) {
        return this.checkNoEntityCollision(par1AxisAlignedBB, null);
    }
    
    public boolean checkNoEntityCollision(final AxisAlignedBB par1AxisAlignedBB, final Entity par2Entity) {
        final List var3 = this.getEntitiesWithinAABBExcludingEntity(null, par1AxisAlignedBB);
        for (int var4 = 0; var4 < var3.size(); ++var4) {
            final Entity var5 = var3.get(var4);
            if (!var5.isDead && var5.preventEntitySpawning && var5 != par2Entity) {
                return false;
            }
        }
        return true;
    }
    
    public boolean checkBlockCollision(final AxisAlignedBB par1AxisAlignedBB) {
        int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        final int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        final int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        final int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        if (par1AxisAlignedBB.minX < 0.0) {
            --var2;
        }
        if (par1AxisAlignedBB.minY < 0.0) {
            --var4;
        }
        if (par1AxisAlignedBB.minZ < 0.0) {
            --var6;
        }
        for (int var8 = var2; var8 < var3; ++var8) {
            for (int var9 = var4; var9 < var5; ++var9) {
                for (int var10 = var6; var10 < var7; ++var10) {
                    final Block var11 = Block.blocksList[this.getBlockId(var8, var9, var10)];
                    if (var11 != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isAnyLiquid(final AxisAlignedBB par1AxisAlignedBB) {
        int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        final int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        final int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        final int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        if (par1AxisAlignedBB.minX < 0.0) {
            --var2;
        }
        if (par1AxisAlignedBB.minY < 0.0) {
            --var4;
        }
        if (par1AxisAlignedBB.minZ < 0.0) {
            --var6;
        }
        for (int var8 = var2; var8 < var3; ++var8) {
            for (int var9 = var4; var9 < var5; ++var9) {
                for (int var10 = var6; var10 < var7; ++var10) {
                    final Block var11 = Block.blocksList[this.getBlockId(var8, var9, var10)];
                    if (var11 != null && var11.blockMaterial.isLiquid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isBoundingBoxBurning(final AxisAlignedBB par1AxisAlignedBB) {
        final int var2 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        final int var3 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        final int var4 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        final int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        final int var6 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        final int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        if (this.checkChunksExist(var2, var4, var6, var3, var5, var7)) {
            for (int var8 = var2; var8 < var3; ++var8) {
                for (int var9 = var4; var9 < var5; ++var9) {
                    for (int var10 = var6; var10 < var7; ++var10) {
                        final int var11 = this.getBlockId(var8, var9, var10);
                        if (var11 == Block.fire.blockID || var11 == Block.lavaMoving.blockID || var11 == Block.lavaStill.blockID) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public boolean handleMaterialAcceleration(final AxisAlignedBB par1AxisAlignedBB, final Material par2Material, final Entity par3Entity) {
        final int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        final int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        final int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        final int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        final int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        final int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        if (!this.checkChunksExist(var4, var6, var8, var5, var7, var9)) {
            return false;
        }
        boolean var10 = false;
        Vec3 var11 = this.getWorldVec3Pool().getVecFromPool(0.0, 0.0, 0.0);
        for (int var12 = var4; var12 < var5; ++var12) {
            for (int var13 = var6; var13 < var7; ++var13) {
                for (int var14 = var8; var14 < var9; ++var14) {
                    final Block var15 = Block.blocksList[this.getBlockId(var12, var13, var14)];
                    if (var15 != null && var15.blockMaterial == par2Material) {
                        final double var16 = var13 + 1 - BlockFluid.getFluidHeightPercent(this.getBlockMetadata(var12, var13, var14));
                        if (var7 >= var16) {
                            var10 = true;
                            var15.velocityToAddToEntity(this, var12, var13, var14, par3Entity, var11);
                        }
                    }
                }
            }
        }
        if (var11.lengthVector() > 0.0 && par3Entity.func_96092_aw()) {
            var11 = var11.normalize();
            final double var17 = 0.014;
            par3Entity.motionX += var11.xCoord * var17;
            par3Entity.motionY += var11.yCoord * var17;
            par3Entity.motionZ += var11.zCoord * var17;
        }
        return var10;
    }
    
    public boolean isMaterialInBB(final AxisAlignedBB par1AxisAlignedBB, final Material par2Material) {
        final int var3 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        final int var4 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        final int var5 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        final int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        final int var7 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        final int var8 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var5; var10 < var6; ++var10) {
                for (int var11 = var7; var11 < var8; ++var11) {
                    final Block var12 = Block.blocksList[this.getBlockId(var9, var10, var11)];
                    if (var12 != null && var12.blockMaterial == par2Material) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isAABBInMaterial(final AxisAlignedBB par1AxisAlignedBB, final Material par2Material) {
        final int var3 = MathHelper.floor_double(par1AxisAlignedBB.minX);
        final int var4 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
        final int var5 = MathHelper.floor_double(par1AxisAlignedBB.minY);
        final int var6 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
        final int var7 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
        final int var8 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
        for (int var9 = var3; var9 < var4; ++var9) {
            for (int var10 = var5; var10 < var6; ++var10) {
                for (int var11 = var7; var11 < var8; ++var11) {
                    final Block var12 = Block.blocksList[this.getBlockId(var9, var10, var11)];
                    if (var12 != null && var12.blockMaterial == par2Material) {
                        final int var13 = this.getBlockMetadata(var9, var10, var11);
                        double var14 = var10 + 1;
                        if (var13 < 8) {
                            var14 = var10 + 1 - var13 / 8.0;
                        }
                        if (var14 >= par1AxisAlignedBB.minY) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public Explosion createExplosion(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final boolean par9) {
        return this.newExplosion(par1Entity, par2, par4, par6, par8, false, par9);
    }
    
    public Explosion newExplosion(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final boolean par9, final boolean par10) {
        final Explosion var11 = new Explosion(this, par1Entity, par2, par4, par6, par8);
        var11.isFlaming = par9;
        var11.isSmoking = par10;
        var11.doExplosionA();
        var11.doExplosionB(true);
        return var11;
    }
    
    public float getBlockDensity(final Vec3 par1Vec3, final AxisAlignedBB par2AxisAlignedBB) {
        final double var3 = 1.0 / ((par2AxisAlignedBB.maxX - par2AxisAlignedBB.minX) * 2.0 + 1.0);
        final double var4 = 1.0 / ((par2AxisAlignedBB.maxY - par2AxisAlignedBB.minY) * 2.0 + 1.0);
        final double var5 = 1.0 / ((par2AxisAlignedBB.maxZ - par2AxisAlignedBB.minZ) * 2.0 + 1.0);
        int var6 = 0;
        int var7 = 0;
        for (float var8 = 0.0f; var8 <= 1.0f; var8 += (float)var3) {
            for (float var9 = 0.0f; var9 <= 1.0f; var9 += (float)var4) {
                for (float var10 = 0.0f; var10 <= 1.0f; var10 += (float)var5) {
                    final double var11 = par2AxisAlignedBB.minX + (par2AxisAlignedBB.maxX - par2AxisAlignedBB.minX) * var8;
                    final double var12 = par2AxisAlignedBB.minY + (par2AxisAlignedBB.maxY - par2AxisAlignedBB.minY) * var9;
                    final double var13 = par2AxisAlignedBB.minZ + (par2AxisAlignedBB.maxZ - par2AxisAlignedBB.minZ) * var10;
                    if (this.rayTraceBlocks(this.getWorldVec3Pool().getVecFromPool(var11, var12, var13), par1Vec3) == null) {
                        ++var6;
                    }
                    ++var7;
                }
            }
        }
        return var6 / var7;
    }
    
    public boolean extinguishFire(final EntityPlayer par1EntityPlayer, int par2, int par3, int par4, final int par5) {
        if (par5 == 0) {
            --par3;
        }
        if (par5 == 1) {
            ++par3;
        }
        if (par5 == 2) {
            --par4;
        }
        if (par5 == 3) {
            ++par4;
        }
        if (par5 == 4) {
            --par2;
        }
        if (par5 == 5) {
            ++par2;
        }
        if (this.getBlockId(par2, par3, par4) == Block.fire.blockID) {
            this.playAuxSFXAtEntity(par1EntityPlayer, 1004, par2, par3, par4, 0);
            this.setBlockToAir(par2, par3, par4);
            return true;
        }
        return false;
    }
    
    public String getDebugLoadedEntities() {
        return "All: " + this.loadedEntityList.size();
    }
    
    public String getProviderName() {
        return this.chunkProvider.makeString();
    }
    
    @Override
    public TileEntity getBlockTileEntity(final int par1, final int par2, final int par3) {
        if (par2 >= 0 && par2 < 256) {
            TileEntity var4 = null;
            if (this.scanningTileEntities) {
                for (int var5 = 0; var5 < this.addedTileEntityList.size(); ++var5) {
                    final TileEntity var6 = this.addedTileEntityList.get(var5);
                    if (!var6.isInvalid() && var6.xCoord == par1 && var6.yCoord == par2 && var6.zCoord == par3) {
                        var4 = var6;
                        break;
                    }
                }
            }
            if (var4 == null) {
                final Chunk var7 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
                if (var7 != null) {
                    var4 = var7.getChunkBlockTileEntity(par1 & 0xF, par2, par3 & 0xF);
                }
            }
            if (var4 == null) {
                for (int var5 = 0; var5 < this.addedTileEntityList.size(); ++var5) {
                    final TileEntity var6 = this.addedTileEntityList.get(var5);
                    if (!var6.isInvalid() && var6.xCoord == par1 && var6.yCoord == par2 && var6.zCoord == par3) {
                        var4 = var6;
                        break;
                    }
                }
            }
            return var4;
        }
        return null;
    }
    
    public void setBlockTileEntity(final int par1, final int par2, final int par3, final TileEntity par4TileEntity) {
        if (par4TileEntity != null && !par4TileEntity.isInvalid()) {
            if (this.scanningTileEntities) {
                par4TileEntity.xCoord = par1;
                par4TileEntity.yCoord = par2;
                par4TileEntity.zCoord = par3;
                final Iterator var5 = this.addedTileEntityList.iterator();
                while (var5.hasNext()) {
                    final TileEntity var6 = var5.next();
                    if (var6.xCoord == par1 && var6.yCoord == par2 && var6.zCoord == par3) {
                        var6.invalidate();
                        var5.remove();
                    }
                }
                this.addedTileEntityList.add(par4TileEntity);
            }
            else {
                this.loadedTileEntityList.add(par4TileEntity);
                final Chunk var7 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
                if (var7 != null) {
                    var7.setChunkBlockTileEntity(par1 & 0xF, par2, par3 & 0xF, par4TileEntity);
                }
            }
        }
    }
    
    public void removeBlockTileEntity(final int par1, final int par2, final int par3) {
        final TileEntity var4 = this.getBlockTileEntity(par1, par2, par3);
        if (var4 != null && this.scanningTileEntities) {
            var4.invalidate();
            this.addedTileEntityList.remove(var4);
        }
        else {
            if (var4 != null) {
                this.addedTileEntityList.remove(var4);
                this.loadedTileEntityList.remove(var4);
            }
            final Chunk var5 = this.getChunkFromChunkCoords(par1 >> 4, par3 >> 4);
            if (var5 != null) {
                var5.removeChunkBlockTileEntity(par1 & 0xF, par2, par3 & 0xF);
            }
        }
    }
    
    public void markTileEntityForDespawn(final TileEntity par1TileEntity) {
        this.entityRemoval.add(par1TileEntity);
    }
    
    @Override
    public boolean isBlockOpaqueCube(final int par1, final int par2, final int par3) {
        final Block var4 = Block.blocksList[this.getBlockId(par1, par2, par3)];
        return var4 != null && var4.isOpaqueCube();
    }
    
    @Override
    public boolean isBlockNormalCube(final int par1, final int par2, final int par3) {
        return Block.isNormalCube(this.getBlockId(par1, par2, par3));
    }
    
    public boolean func_85174_u(final int par1, final int par2, final int par3) {
        final int var4 = this.getBlockId(par1, par2, par3);
        if (var4 != 0 && Block.blocksList[var4] != null) {
            final AxisAlignedBB var5 = Block.blocksList[var4].getCollisionBoundingBoxFromPool(this, par1, par2, par3);
            return var5 != null && var5.getAverageEdgeLength() >= 1.0;
        }
        return false;
    }
    
    @Override
    public boolean doesBlockHaveSolidTopSurface(final int par1, final int par2, final int par3) {
        final Block var4 = Block.blocksList[this.getBlockId(par1, par2, par3)];
        return this.isBlockTopFacingSurfaceSolid(var4, this.getBlockMetadata(par1, par2, par3));
    }
    
    public boolean isBlockTopFacingSurfaceSolid(final Block par1Block, final int par2) {
        return par1Block != null && ((par1Block.blockMaterial.isOpaque() && par1Block.renderAsNormalBlock()) || ((par1Block instanceof BlockStairs) ? ((par2 & 0x4) == 0x4) : ((par1Block instanceof BlockHalfSlab) ? ((par2 & 0x8) == 0x8) : (par1Block instanceof BlockHopper || (par1Block instanceof BlockSnow && (par2 & 0x7) == 0x7)))));
    }
    
    public boolean isBlockNormalCubeDefault(final int par1, final int par2, final int par3, final boolean par4) {
        if (par1 < -30000000 || par3 < -30000000 || par1 >= 30000000 || par3 >= 30000000) {
            return par4;
        }
        final Chunk var5 = this.chunkProvider.provideChunk(par1 >> 4, par3 >> 4);
        if (var5 != null && !var5.isEmpty()) {
            final Block var6 = Block.blocksList[this.getBlockId(par1, par2, par3)];
            return var6 != null && (var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock());
        }
        return par4;
    }
    
    public void calculateInitialSkylight() {
        final int var1 = this.calculateSkylightSubtracted(1.0f);
        if (var1 != this.skylightSubtracted) {
            this.skylightSubtracted = var1;
        }
    }
    
    public void setAllowedSpawnTypes(final boolean par1, final boolean par2) {
        this.spawnHostileMobs = par1;
        this.spawnPeacefulMobs = par2;
    }
    
    public void tick() {
        this.updateWeather();
    }
    
    private void calculateInitialWeather() {
        if (this.worldInfo.isRaining()) {
            this.rainingStrength = 1.0f;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength = 1.0f;
            }
        }
    }
    
    protected void updateWeather() {
        if (!this.provider.hasNoSky) {
            int var1 = this.worldInfo.getThunderTime();
            if (var1 <= 0) {
                if (this.worldInfo.isThundering()) {
                    this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
                }
                else {
                    this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
                }
            }
            else {
                --var1;
                this.worldInfo.setThunderTime(var1);
                if (var1 <= 0) {
                    this.worldInfo.setThundering(!this.worldInfo.isThundering());
                }
            }
            int var2 = this.worldInfo.getRainTime();
            if (var2 <= 0) {
                if (this.worldInfo.isRaining()) {
                    this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
                }
                else {
                    this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
                }
            }
            else {
                --var2;
                this.worldInfo.setRainTime(var2);
                if (var2 <= 0) {
                    this.worldInfo.setRaining(!this.worldInfo.isRaining());
                }
            }
            this.prevRainingStrength = this.rainingStrength;
            if (this.worldInfo.isRaining()) {
                this.rainingStrength += 0.01;
            }
            else {
                this.rainingStrength -= 0.01;
            }
            if (this.rainingStrength < 0.0f) {
                this.rainingStrength = 0.0f;
            }
            if (this.rainingStrength > 1.0f) {
                this.rainingStrength = 1.0f;
            }
            this.prevThunderingStrength = this.thunderingStrength;
            if (this.worldInfo.isThundering()) {
                this.thunderingStrength += 0.01;
            }
            else {
                this.thunderingStrength -= 0.01;
            }
            if (this.thunderingStrength < 0.0f) {
                this.thunderingStrength = 0.0f;
            }
            if (this.thunderingStrength > 1.0f) {
                this.thunderingStrength = 1.0f;
            }
        }
    }
    
    public void toggleRain() {
        this.worldInfo.setRainTime(1);
    }
    
    protected void setActivePlayerChunksAndCheckLight() {
        this.activeChunkSet.clear();
        this.theProfiler.startSection("buildList");
        for (int var1 = 0; var1 < this.playerEntities.size(); ++var1) {
            final EntityPlayer var2 = this.playerEntities.get(var1);
            final int var3 = MathHelper.floor_double(var2.posX / 16.0);
            final int var4 = MathHelper.floor_double(var2.posZ / 16.0);
            final byte var5 = 7;
            for (int var6 = -var5; var6 <= var5; ++var6) {
                for (int var7 = -var5; var7 <= var5; ++var7) {
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
            final int var1 = this.rand.nextInt(this.playerEntities.size());
            final EntityPlayer var2 = this.playerEntities.get(var1);
            final int var3 = MathHelper.floor_double(var2.posX) + this.rand.nextInt(11) - 5;
            final int var4 = MathHelper.floor_double(var2.posY) + this.rand.nextInt(11) - 5;
            final int var8 = MathHelper.floor_double(var2.posZ) + this.rand.nextInt(11) - 5;
            this.updateAllLightTypes(var3, var4, var8);
        }
        this.theProfiler.endSection();
    }
    
    protected void moodSoundAndLightCheck(final int par1, final int par2, final Chunk par3Chunk) {
        this.theProfiler.endStartSection("moodSound");
        if (this.ambientTickCountdown == 0 && !this.isRemote) {
            this.updateLCG = this.updateLCG * 3 + 1013904223;
            final int var4 = this.updateLCG >> 2;
            int var5 = var4 & 0xF;
            int var6 = var4 >> 8 & 0xF;
            final int var7 = var4 >> 16 & 0x7F;
            final int var8 = par3Chunk.getBlockID(var5, var7, var6);
            var5 += par1;
            var6 += par2;
            if (var8 == 0 && this.getFullBlockLightValue(var5, var7, var6) <= this.rand.nextInt(8) && this.getSavedLightValue(EnumSkyBlock.Sky, var5, var7, var6) <= 0) {
                final EntityPlayer var9 = this.getClosestPlayer(var5 + 0.5, var7 + 0.5, var6 + 0.5, 8.0);
                if (var9 != null && var9.getDistanceSq(var5 + 0.5, var7 + 0.5, var6 + 0.5) > 4.0) {
                    this.playSoundEffect(var5 + 0.5, var7 + 0.5, var6 + 0.5, "ambient.cave.cave", 0.7f, 0.8f + this.rand.nextFloat() * 0.2f);
                    this.ambientTickCountdown = this.rand.nextInt(12000) + 6000;
                }
            }
        }
        this.theProfiler.endStartSection("checkLight");
        par3Chunk.enqueueRelightChecks();
    }
    
    protected void tickBlocksAndAmbiance() {
        this.setActivePlayerChunksAndCheckLight();
    }
    
    public boolean isBlockFreezable(final int par1, final int par2, final int par3) {
        return this.canBlockFreeze(par1, par2, par3, false);
    }
    
    public boolean isBlockFreezableNaturally(final int par1, final int par2, final int par3) {
        return this.canBlockFreeze(par1, par2, par3, true);
    }
    
    public boolean canBlockFreeze(final int par1, final int par2, final int par3, final boolean par4) {
        final BiomeGenBase var5 = this.getBiomeGenForCoords(par1, par3);
        final float var6 = var5.getFloatTemperature();
        if (var6 > 0.15f) {
            return false;
        }
        if (par2 >= 0 && par2 < 256 && this.getSavedLightValue(EnumSkyBlock.Block, par1, par2, par3) < 10) {
            final int var7 = this.getBlockId(par1, par2, par3);
            if ((var7 == Block.waterStill.blockID || var7 == Block.waterMoving.blockID) && this.getBlockMetadata(par1, par2, par3) == 0) {
                if (!par4) {
                    return true;
                }
                boolean var8 = true;
                if (var8 && this.getBlockMaterial(par1 - 1, par2, par3) != Material.water) {
                    var8 = false;
                }
                if (var8 && this.getBlockMaterial(par1 + 1, par2, par3) != Material.water) {
                    var8 = false;
                }
                if (var8 && this.getBlockMaterial(par1, par2, par3 - 1) != Material.water) {
                    var8 = false;
                }
                if (var8 && this.getBlockMaterial(par1, par2, par3 + 1) != Material.water) {
                    var8 = false;
                }
                if (!var8) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean canSnowAt(final int par1, final int par2, final int par3) {
        final BiomeGenBase var4 = this.getBiomeGenForCoords(par1, par3);
        final float var5 = var4.getFloatTemperature();
        if (var5 > 0.15f) {
            return false;
        }
        if (par2 >= 0 && par2 < 256 && this.getSavedLightValue(EnumSkyBlock.Block, par1, par2, par3) < 10) {
            final int var6 = this.getBlockId(par1, par2 - 1, par3);
            final int var7 = this.getBlockId(par1, par2, par3);
            if (var7 == 0 && Block.snow.canPlaceBlockAt(this, par1, par2, par3) && var6 != 0 && var6 != Block.ice.blockID && Block.blocksList[var6].blockMaterial.blocksMovement()) {
                return true;
            }
        }
        return false;
    }
    
    public void updateAllLightTypes(final int par1, final int par2, final int par3) {
        if (!this.provider.hasNoSky) {
            this.updateLightByType(EnumSkyBlock.Sky, par1, par2, par3);
        }
        this.updateLightByType(EnumSkyBlock.Block, par1, par2, par3);
    }
    
    private int computeLightValue(final int par1, final int par2, final int par3, final EnumSkyBlock par4EnumSkyBlock) {
        if (par4EnumSkyBlock == EnumSkyBlock.Sky && this.canBlockSeeTheSky(par1, par2, par3)) {
            return 15;
        }
        final int var5 = this.getBlockId(par1, par2, par3);
        int var6 = (par4EnumSkyBlock == EnumSkyBlock.Sky) ? 0 : Block.lightValue[var5];
        int var7 = Block.lightOpacity[var5];
        if (var7 >= 15 && Block.lightValue[var5] > 0) {
            var7 = 1;
        }
        if (var7 < 1) {
            var7 = 1;
        }
        if (var7 >= 15) {
            return 0;
        }
        if (var6 >= 14) {
            return var6;
        }
        for (int var8 = 0; var8 < 6; ++var8) {
            final int var9 = par1 + Facing.offsetsXForSide[var8];
            final int var10 = par2 + Facing.offsetsYForSide[var8];
            final int var11 = par3 + Facing.offsetsZForSide[var8];
            final int var12 = this.getSavedLightValue(par4EnumSkyBlock, var9, var10, var11) - var7;
            if (var12 > var6) {
                var6 = var12;
            }
            if (var6 >= 14) {
                return var6;
            }
        }
        return var6;
    }
    
    public void updateLightByType(final EnumSkyBlock par1EnumSkyBlock, final int par2, final int par3, final int par4) {
        if (this.doChunksNearChunkExist(par2, par3, par4, 17)) {
            int var5 = 0;
            int var6 = 0;
            this.theProfiler.startSection("getBrightness");
            final int var7 = this.getSavedLightValue(par1EnumSkyBlock, par2, par3, par4);
            final int var8 = this.computeLightValue(par2, par3, par4, par1EnumSkyBlock);
            if (var8 > var7) {
                this.lightUpdateBlockList[var6++] = 133152;
            }
            else if (var8 < var7) {
                this.lightUpdateBlockList[var6++] = (0x20820 | var7 << 18);
                while (var5 < var6) {
                    final int var9 = this.lightUpdateBlockList[var5++];
                    final int var10 = (var9 & 0x3F) - 32 + par2;
                    final int var11 = (var9 >> 6 & 0x3F) - 32 + par3;
                    final int var12 = (var9 >> 12 & 0x3F) - 32 + par4;
                    final int var13 = var9 >> 18 & 0xF;
                    int var14 = this.getSavedLightValue(par1EnumSkyBlock, var10, var11, var12);
                    if (var14 == var13) {
                        this.setLightValue(par1EnumSkyBlock, var10, var11, var12, 0);
                        if (var13 <= 0) {
                            continue;
                        }
                        final int var15 = MathHelper.abs_int(var10 - par2);
                        final int var16 = MathHelper.abs_int(var11 - par3);
                        final int var17 = MathHelper.abs_int(var12 - par4);
                        if (var15 + var16 + var17 >= 17) {
                            continue;
                        }
                        for (int var18 = 0; var18 < 6; ++var18) {
                            final int var19 = var10 + Facing.offsetsXForSide[var18];
                            final int var20 = var11 + Facing.offsetsYForSide[var18];
                            final int var21 = var12 + Facing.offsetsZForSide[var18];
                            final int var22 = Math.max(1, Block.lightOpacity[this.getBlockId(var19, var20, var21)]);
                            var14 = this.getSavedLightValue(par1EnumSkyBlock, var19, var20, var21);
                            if (var14 == var13 - var22 && var6 < this.lightUpdateBlockList.length) {
                                this.lightUpdateBlockList[var6++] = (var19 - par2 + 32 | var20 - par3 + 32 << 6 | var21 - par4 + 32 << 12 | var13 - var22 << 18);
                            }
                        }
                    }
                }
                var5 = 0;
            }
            this.theProfiler.endSection();
            this.theProfiler.startSection("checkedPosition < toCheckCount");
            while (var5 < var6) {
                final int var9 = this.lightUpdateBlockList[var5++];
                final int var10 = (var9 & 0x3F) - 32 + par2;
                final int var11 = (var9 >> 6 & 0x3F) - 32 + par3;
                final int var12 = (var9 >> 12 & 0x3F) - 32 + par4;
                final int var13 = this.getSavedLightValue(par1EnumSkyBlock, var10, var11, var12);
                final int var14 = this.computeLightValue(var10, var11, var12, par1EnumSkyBlock);
                if (var14 != var13) {
                    this.setLightValue(par1EnumSkyBlock, var10, var11, var12, var14);
                    if (var14 <= var13) {
                        continue;
                    }
                    final int var15 = Math.abs(var10 - par2);
                    final int var16 = Math.abs(var11 - par3);
                    final int var17 = Math.abs(var12 - par4);
                    final boolean var23 = var6 < this.lightUpdateBlockList.length - 6;
                    if (var15 + var16 + var17 >= 17 || !var23) {
                        continue;
                    }
                    if (this.getSavedLightValue(par1EnumSkyBlock, var10 - 1, var11, var12) < var14) {
                        this.lightUpdateBlockList[var6++] = var10 - 1 - par2 + 32 + (var11 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
                    }
                    if (this.getSavedLightValue(par1EnumSkyBlock, var10 + 1, var11, var12) < var14) {
                        this.lightUpdateBlockList[var6++] = var10 + 1 - par2 + 32 + (var11 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
                    }
                    if (this.getSavedLightValue(par1EnumSkyBlock, var10, var11 - 1, var12) < var14) {
                        this.lightUpdateBlockList[var6++] = var10 - par2 + 32 + (var11 - 1 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
                    }
                    if (this.getSavedLightValue(par1EnumSkyBlock, var10, var11 + 1, var12) < var14) {
                        this.lightUpdateBlockList[var6++] = var10 - par2 + 32 + (var11 + 1 - par3 + 32 << 6) + (var12 - par4 + 32 << 12);
                    }
                    if (this.getSavedLightValue(par1EnumSkyBlock, var10, var11, var12 - 1) < var14) {
                        this.lightUpdateBlockList[var6++] = var10 - par2 + 32 + (var11 - par3 + 32 << 6) + (var12 - 1 - par4 + 32 << 12);
                    }
                    if (this.getSavedLightValue(par1EnumSkyBlock, var10, var11, var12 + 1) >= var14) {
                        continue;
                    }
                    this.lightUpdateBlockList[var6++] = var10 - par2 + 32 + (var11 - par3 + 32 << 6) + (var12 + 1 - par4 + 32 << 12);
                }
            }
            this.theProfiler.endSection();
        }
    }
    
    public boolean tickUpdates(final boolean par1) {
        return false;
    }
    
    public List getPendingBlockUpdates(final Chunk par1Chunk, final boolean par2) {
        return null;
    }
    
    public List getEntitiesWithinAABBExcludingEntity(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB) {
        return this.getEntitiesWithinAABBExcludingEntity(par1Entity, par2AxisAlignedBB, null);
    }
    
    public List getEntitiesWithinAABBExcludingEntity(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB, final IEntitySelector par3IEntitySelector) {
        final ArrayList var4 = new ArrayList();
        final int var5 = MathHelper.floor_double((par2AxisAlignedBB.minX - 2.0) / 16.0);
        final int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxX + 2.0) / 16.0);
        final int var7 = MathHelper.floor_double((par2AxisAlignedBB.minZ - 2.0) / 16.0);
        final int var8 = MathHelper.floor_double((par2AxisAlignedBB.maxZ + 2.0) / 16.0);
        for (int var9 = var5; var9 <= var6; ++var9) {
            for (int var10 = var7; var10 <= var8; ++var10) {
                if (this.chunkExists(var9, var10)) {
                    this.getChunkFromChunkCoords(var9, var10).getEntitiesWithinAABBForEntity(par1Entity, par2AxisAlignedBB, var4, par3IEntitySelector);
                }
            }
        }
        return var4;
    }
    
    public List getEntitiesWithinAABB(final Class par1Class, final AxisAlignedBB par2AxisAlignedBB) {
        return this.selectEntitiesWithinAABB(par1Class, par2AxisAlignedBB, null);
    }
    
    public List selectEntitiesWithinAABB(final Class par1Class, final AxisAlignedBB par2AxisAlignedBB, final IEntitySelector par3IEntitySelector) {
        final int var4 = MathHelper.floor_double((par2AxisAlignedBB.minX - 2.0) / 16.0);
        final int var5 = MathHelper.floor_double((par2AxisAlignedBB.maxX + 2.0) / 16.0);
        final int var6 = MathHelper.floor_double((par2AxisAlignedBB.minZ - 2.0) / 16.0);
        final int var7 = MathHelper.floor_double((par2AxisAlignedBB.maxZ + 2.0) / 16.0);
        final ArrayList var8 = new ArrayList();
        for (int var9 = var4; var9 <= var5; ++var9) {
            for (int var10 = var6; var10 <= var7; ++var10) {
                if (this.chunkExists(var9, var10)) {
                    this.getChunkFromChunkCoords(var9, var10).getEntitiesOfTypeWithinAAAB(par1Class, par2AxisAlignedBB, var8, par3IEntitySelector);
                }
            }
        }
        return var8;
    }
    
    public Entity findNearestEntityWithinAABB(final Class par1Class, final AxisAlignedBB par2AxisAlignedBB, final Entity par3Entity) {
        final List var4 = this.getEntitiesWithinAABB(par1Class, par2AxisAlignedBB);
        Entity var5 = null;
        double var6 = Double.MAX_VALUE;
        for (int var7 = 0; var7 < var4.size(); ++var7) {
            final Entity var8 = var4.get(var7);
            if (var8 != par3Entity) {
                final double var9 = par3Entity.getDistanceSqToEntity(var8);
                if (var9 <= var6) {
                    var5 = var8;
                    var6 = var9;
                }
            }
        }
        return var5;
    }
    
    public abstract Entity getEntityByID(final int p0);
    
    public List getLoadedEntityList() {
        return this.loadedEntityList;
    }
    
    public void updateTileEntityChunkAndDoNothing(final int par1, final int par2, final int par3, final TileEntity par4TileEntity) {
        if (this.blockExists(par1, par2, par3)) {
            this.getChunkFromBlockCoords(par1, par3).setChunkModified();
        }
    }
    
    public int countEntities(final Class par1Class) {
        int var2 = 0;
        for (int var3 = 0; var3 < this.loadedEntityList.size(); ++var3) {
            final Entity var4 = this.loadedEntityList.get(var3);
            if ((!(var4 instanceof EntityLiving) || !((EntityLiving)var4).func_104002_bU()) && par1Class.isAssignableFrom(var4.getClass())) {
                ++var2;
            }
        }
        return var2;
    }
    
    public void addLoadedEntities(final List par1List) {
        this.loadedEntityList.addAll(par1List);
        for (int var2 = 0; var2 < par1List.size(); ++var2) {
            this.obtainEntitySkin(par1List.get(var2));
        }
    }
    
    public void unloadEntities(final List par1List) {
        this.unloadedEntityList.addAll(par1List);
    }
    
    public boolean canPlaceEntityOnSide(final int par1, final int par2, final int par3, final int par4, final boolean par5, final int par6, final Entity par7Entity, final ItemStack par8ItemStack) {
        final int var9 = this.getBlockId(par2, par3, par4);
        Block var10 = Block.blocksList[var9];
        final Block var11 = Block.blocksList[par1];
        AxisAlignedBB var12 = var11.getCollisionBoundingBoxFromPool(this, par2, par3, par4);
        if (par5) {
            var12 = null;
        }
        if (var12 != null && !this.checkNoEntityCollision(var12, par7Entity)) {
            return false;
        }
        if (var10 != null && (var10 == Block.waterMoving || var10 == Block.waterStill || var10 == Block.lavaMoving || var10 == Block.lavaStill || var10 == Block.fire || var10.blockMaterial.isReplaceable())) {
            var10 = null;
        }
        return (var10 != null && var10.blockMaterial == Material.circuits && var11 == Block.anvil) || (par1 > 0 && var10 == null && var11.canPlaceBlockOnSide(this, par2, par3, par4, par6, par8ItemStack));
    }
    
    public PathEntity getPathEntityToEntity(final Entity par1Entity, final Entity par2Entity, final float par3, final boolean par4, final boolean par5, final boolean par6, final boolean par7) {
        this.theProfiler.startSection("pathfind");
        final int var8 = MathHelper.floor_double(par1Entity.posX);
        final int var9 = MathHelper.floor_double(par1Entity.posY + 1.0);
        final int var10 = MathHelper.floor_double(par1Entity.posZ);
        final int var11 = (int)(par3 + 16.0f);
        final int var12 = var8 - var11;
        final int var13 = var9 - var11;
        final int var14 = var10 - var11;
        final int var15 = var8 + var11;
        final int var16 = var9 + var11;
        final int var17 = var10 + var11;
        final ChunkCache var18 = new ChunkCache(this, var12, var13, var14, var15, var16, var17, 0);
        final PathEntity var19 = new PathFinder(var18, par4, par5, par6, par7).createEntityPathTo(par1Entity, par2Entity, par3);
        this.theProfiler.endSection();
        return var19;
    }
    
    public PathEntity getEntityPathToXYZ(final Entity par1Entity, final int par2, final int par3, final int par4, final float par5, final boolean par6, final boolean par7, final boolean par8, final boolean par9) {
        this.theProfiler.startSection("pathfind");
        final int var10 = MathHelper.floor_double(par1Entity.posX);
        final int var11 = MathHelper.floor_double(par1Entity.posY);
        final int var12 = MathHelper.floor_double(par1Entity.posZ);
        final int var13 = (int)(par5 + 8.0f);
        final int var14 = var10 - var13;
        final int var15 = var11 - var13;
        final int var16 = var12 - var13;
        final int var17 = var10 + var13;
        final int var18 = var11 + var13;
        final int var19 = var12 + var13;
        final ChunkCache var20 = new ChunkCache(this, var14, var15, var16, var17, var18, var19, 0);
        final PathEntity var21 = new PathFinder(var20, par6, par7, par8, par9).createEntityPathTo(par1Entity, par2, par3, par4, par5);
        this.theProfiler.endSection();
        return var21;
    }
    
    @Override
    public int isBlockProvidingPowerTo(final int par1, final int par2, final int par3, final int par4) {
        final int var5 = this.getBlockId(par1, par2, par3);
        return (var5 == 0) ? 0 : Block.blocksList[var5].isProvidingStrongPower(this, par1, par2, par3, par4);
    }
    
    public int getBlockPowerInput(final int par1, final int par2, final int par3) {
        final byte var4 = 0;
        int var5 = Math.max(var4, this.isBlockProvidingPowerTo(par1, par2 - 1, par3, 0));
        if (var5 >= 15) {
            return var5;
        }
        var5 = Math.max(var5, this.isBlockProvidingPowerTo(par1, par2 + 1, par3, 1));
        if (var5 >= 15) {
            return var5;
        }
        var5 = Math.max(var5, this.isBlockProvidingPowerTo(par1, par2, par3 - 1, 2));
        if (var5 >= 15) {
            return var5;
        }
        var5 = Math.max(var5, this.isBlockProvidingPowerTo(par1, par2, par3 + 1, 3));
        if (var5 >= 15) {
            return var5;
        }
        var5 = Math.max(var5, this.isBlockProvidingPowerTo(par1 - 1, par2, par3, 4));
        if (var5 >= 15) {
            return var5;
        }
        var5 = Math.max(var5, this.isBlockProvidingPowerTo(par1 + 1, par2, par3, 5));
        return (var5 >= 15) ? var5 : var5;
    }
    
    public boolean getIndirectPowerOutput(final int par1, final int par2, final int par3, final int par4) {
        return this.getIndirectPowerLevelTo(par1, par2, par3, par4) > 0;
    }
    
    public int getIndirectPowerLevelTo(final int par1, final int par2, final int par3, final int par4) {
        if (this.isBlockNormalCube(par1, par2, par3)) {
            return this.getBlockPowerInput(par1, par2, par3);
        }
        final int var5 = this.getBlockId(par1, par2, par3);
        return (var5 == 0) ? 0 : Block.blocksList[var5].isProvidingWeakPower(this, par1, par2, par3, par4);
    }
    
    public boolean isBlockIndirectlyGettingPowered(final int par1, final int par2, final int par3) {
        return this.getIndirectPowerLevelTo(par1, par2 - 1, par3, 0) > 0 || this.getIndirectPowerLevelTo(par1, par2 + 1, par3, 1) > 0 || this.getIndirectPowerLevelTo(par1, par2, par3 - 1, 2) > 0 || this.getIndirectPowerLevelTo(par1, par2, par3 + 1, 3) > 0 || this.getIndirectPowerLevelTo(par1 - 1, par2, par3, 4) > 0 || this.getIndirectPowerLevelTo(par1 + 1, par2, par3, 5) > 0;
    }
    
    public int getStrongestIndirectPower(final int par1, final int par2, final int par3) {
        int var4 = 0;
        for (int var5 = 0; var5 < 6; ++var5) {
            final int var6 = this.getIndirectPowerLevelTo(par1 + Facing.offsetsXForSide[var5], par2 + Facing.offsetsYForSide[var5], par3 + Facing.offsetsZForSide[var5], var5);
            if (var6 >= 15) {
                return 15;
            }
            if (var6 > var4) {
                var4 = var6;
            }
        }
        return var4;
    }
    
    public EntityPlayer getClosestPlayerToEntity(final Entity par1Entity, final double par2) {
        return this.getClosestPlayer(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par2);
    }
    
    public EntityPlayer getClosestPlayer(final double par1, final double par3, final double par5, final double par7) {
        double var9 = -1.0;
        EntityPlayer var10 = null;
        for (int var11 = 0; var11 < this.playerEntities.size(); ++var11) {
            final EntityPlayer var12 = this.playerEntities.get(var11);
            final double var13 = var12.getDistanceSq(par1, par3, par5);
            if ((par7 < 0.0 || var13 < par7 * par7) && (var9 == -1.0 || var13 < var9)) {
                var9 = var13;
                var10 = var12;
            }
        }
        return var10;
    }
    
    public EntityPlayer getClosestVulnerablePlayerToEntity(final Entity par1Entity, final double par2) {
        return this.getClosestVulnerablePlayer(par1Entity.posX, par1Entity.posY, par1Entity.posZ, par2);
    }
    
    public EntityPlayer getClosestVulnerablePlayer(final double par1, final double par3, final double par5, final double par7) {
        double var9 = -1.0;
        EntityPlayer var10 = null;
        for (int var11 = 0; var11 < this.playerEntities.size(); ++var11) {
            final EntityPlayer var12 = this.playerEntities.get(var11);
            if (!var12.capabilities.disableDamage && var12.isEntityAlive()) {
                final double var13 = var12.getDistanceSq(par1, par3, par5);
                double var14 = par7;
                if (var12.isSneaking()) {
                    var14 = par7 * 0.800000011920929;
                }
                if (var12.isInvisible()) {
                    float var15 = var12.func_82243_bO();
                    if (var15 < 0.1f) {
                        var15 = 0.1f;
                    }
                    var14 *= 0.7f * var15;
                }
                if ((par7 < 0.0 || var13 < var14 * var14) && (var9 == -1.0 || var13 < var9)) {
                    var9 = var13;
                    var10 = var12;
                }
            }
        }
        return var10;
    }
    
    public EntityPlayer getPlayerEntityByName(final String par1Str) {
        for (int var2 = 0; var2 < this.playerEntities.size(); ++var2) {
            if (par1Str.equals(this.playerEntities.get(var2).username)) {
                return this.playerEntities.get(var2);
            }
        }
        return null;
    }
    
    public void sendQuittingDisconnectingPacket() {
    }
    
    public void checkSessionLock() throws MinecraftException {
        this.saveHandler.checkSessionLock();
    }
    
    public void func_82738_a(final long par1) {
        this.worldInfo.incrementTotalWorldTime(par1);
    }
    
    public long getSeed() {
        return this.worldInfo.getSeed();
    }
    
    public long getTotalWorldTime() {
        return this.worldInfo.getWorldTotalTime();
    }
    
    public long getWorldTime() {
        return this.worldInfo.getWorldTime();
    }
    
    public void setWorldTime(final long par1) {
        this.worldInfo.setWorldTime(par1);
    }
    
    public ChunkCoordinates getSpawnPoint() {
        return new ChunkCoordinates(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
    }
    
    public void setSpawnLocation(final int par1, final int par2, final int par3) {
        this.worldInfo.setSpawnPosition(par1, par2, par3);
    }
    
    public void joinEntityInSurroundings(final Entity par1Entity) {
        final int var2 = MathHelper.floor_double(par1Entity.posX / 16.0);
        final int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0);
        final byte var4 = 2;
        for (int var5 = var2 - var4; var5 <= var2 + var4; ++var5) {
            for (int var6 = var3 - var4; var6 <= var3 + var4; ++var6) {
                this.getChunkFromChunkCoords(var5, var6);
            }
        }
        if (!this.loadedEntityList.contains(par1Entity)) {
            this.loadedEntityList.add(par1Entity);
        }
    }
    
    public boolean canMineBlock(final EntityPlayer par1EntityPlayer, final int par2, final int par3, final int par4) {
        return true;
    }
    
    public void setEntityState(final Entity par1Entity, final byte par2) {
    }
    
    public IChunkProvider getChunkProvider() {
        return this.chunkProvider;
    }
    
    public void addBlockEvent(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        if (par4 > 0) {
            Block.blocksList[par4].onBlockEventReceived(this, par1, par2, par3, par5, par6);
        }
    }
    
    public ISaveHandler getSaveHandler() {
        return this.saveHandler;
    }
    
    public WorldInfo getWorldInfo() {
        return this.worldInfo;
    }
    
    public GameRules getGameRules() {
        return this.worldInfo.getGameRulesInstance();
    }
    
    public void updateAllPlayersSleepingFlag() {
    }
    
    public float getWeightedThunderStrength(final float par1) {
        return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * par1) * this.getRainStrength(par1);
    }
    
    public float getRainStrength(final float par1) {
        return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * par1;
    }
    
    public void setRainStrength(final float par1) {
        this.prevRainingStrength = par1;
        this.rainingStrength = par1;
    }
    
    public boolean isThundering() {
        return this.getWeightedThunderStrength(1.0f) > 0.9;
    }
    
    public boolean isRaining() {
        return this.getRainStrength(1.0f) > 0.2;
    }
    
    public boolean canLightningStrikeAt(final int par1, final int par2, final int par3) {
        if (!this.isRaining()) {
            return false;
        }
        if (!this.canBlockSeeTheSky(par1, par2, par3)) {
            return false;
        }
        if (this.getPrecipitationHeight(par1, par3) > par2) {
            return false;
        }
        final BiomeGenBase var4 = this.getBiomeGenForCoords(par1, par3);
        return !var4.getEnableSnow() && var4.canSpawnLightningBolt();
    }
    
    public boolean isBlockHighHumidity(final int par1, final int par2, final int par3) {
        final BiomeGenBase var4 = this.getBiomeGenForCoords(par1, par3);
        return var4.isHighHumidity();
    }
    
    public void setItemData(final String par1Str, final WorldSavedData par2WorldSavedData) {
        this.mapStorage.setData(par1Str, par2WorldSavedData);
    }
    
    public WorldSavedData loadItemData(final Class par1Class, final String par2Str) {
        return this.mapStorage.loadData(par1Class, par2Str);
    }
    
    public int getUniqueDataId(final String par1Str) {
        return this.mapStorage.getUniqueDataId(par1Str);
    }
    
    public void func_82739_e(final int par1, final int par2, final int par3, final int par4, final int par5) {
        for (int var6 = 0; var6 < this.worldAccesses.size(); ++var6) {
            this.worldAccesses.get(var6).broadcastSound(par1, par2, par3, par4, par5);
        }
    }
    
    public void playAuxSFX(final int par1, final int par2, final int par3, final int par4, final int par5) {
        this.playAuxSFXAtEntity(null, par1, par2, par3, par4, par5);
    }
    
    public void playAuxSFXAtEntity(final EntityPlayer par1EntityPlayer, final int par2, final int par3, final int par4, final int par5, final int par6) {
        try {
            for (int var7 = 0; var7 < this.worldAccesses.size(); ++var7) {
                this.worldAccesses.get(var7).playAuxSFX(par1EntityPlayer, par2, par3, par4, par5, par6);
            }
        }
        catch (Throwable var9) {
            final CrashReport var8 = CrashReport.makeCrashReport(var9, "Playing level event");
            final CrashReportCategory var10 = var8.makeCategory("Level event being played");
            var10.addCrashSection("Block coordinates", CrashReportCategory.getLocationInfo(par3, par4, par5));
            var10.addCrashSection("Event source", par1EntityPlayer);
            var10.addCrashSection("Event type", par2);
            var10.addCrashSection("Event data", par6);
            throw new ReportedException(var8);
        }
    }
    
    @Override
    public int getHeight() {
        return 256;
    }
    
    public int getActualHeight() {
        return this.provider.hasNoSky ? 128 : 256;
    }
    
    public IUpdatePlayerListBox func_82735_a(final EntityMinecart par1EntityMinecart) {
        return null;
    }
    
    public Random setRandomSeed(final int par1, final int par2, final int par3) {
        final long var4 = par1 * 341873128712L + par2 * 132897987541L + this.getWorldInfo().getSeed() + par3;
        this.rand.setSeed(var4);
        return this.rand;
    }
    
    public ChunkPosition findClosestStructure(final String par1Str, final int par2, final int par3, final int par4) {
        return this.getChunkProvider().findClosestStructure(this, par1Str, par2, par3, par4);
    }
    
    @Override
    public boolean extendedLevelsInChunkCache() {
        return false;
    }
    
    public double getHorizon() {
        return (this.worldInfo.getTerrainType() == WorldType.FLAT) ? 0.0 : 63.0;
    }
    
    public CrashReportCategory addWorldInfoToCrashReport(final CrashReport par1CrashReport) {
        final CrashReportCategory var2 = par1CrashReport.makeCategoryDepth("Affected level", 1);
        var2.addCrashSection("Level name", (this.worldInfo == null) ? "????" : this.worldInfo.getWorldName());
        var2.addCrashSectionCallable("All players", new CallableLvl2(this));
        var2.addCrashSectionCallable("Chunk stats", new CallableLvl3(this));
        try {
            this.worldInfo.addToCrashReport(var2);
        }
        catch (Throwable var3) {
            var2.addCrashSectionThrowable("Level Data Unobtainable", var3);
        }
        return var2;
    }
    
    public void destroyBlockInWorldPartially(final int par1, final int par2, final int par3, final int par4, final int par5) {
        for (int var6 = 0; var6 < this.worldAccesses.size(); ++var6) {
            final IWorldAccess var7 = this.worldAccesses.get(var6);
            var7.destroyBlockPartially(par1, par2, par3, par4, par5);
        }
    }
    
    @Override
    public Vec3Pool getWorldVec3Pool() {
        return this.vecPool;
    }
    
    public Calendar getCurrentDate() {
        if (this.getTotalWorldTime() % 600L == 0L) {
            this.theCalendar.setTimeInMillis(System.currentTimeMillis());
        }
        return this.theCalendar;
    }
    
    public void func_92088_a(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11, final NBTTagCompound par13NBTTagCompound) {
    }
    
    public Scoreboard getScoreboard() {
        return this.worldScoreboard;
    }
    
    public void func_96440_m(final int par1, final int par2, final int par3, final int par4) {
        for (int var5 = 0; var5 < 4; ++var5) {
            int var6 = par1 + Direction.offsetX[var5];
            int var7 = par3 + Direction.offsetZ[var5];
            int var8 = this.getBlockId(var6, par2, var7);
            if (var8 != 0) {
                Block var9 = Block.blocksList[var8];
                if (Block.redstoneComparatorIdle.func_94487_f(var8)) {
                    var9.onNeighborBlockChange(this, var6, par2, var7, par4);
                }
                else if (Block.isNormalCube(var8)) {
                    var6 += Direction.offsetX[var5];
                    var7 += Direction.offsetZ[var5];
                    var8 = this.getBlockId(var6, par2, var7);
                    var9 = Block.blocksList[var8];
                    if (Block.redstoneComparatorIdle.func_94487_f(var8)) {
                        var9.onNeighborBlockChange(this, var6, par2, var7, par4);
                    }
                }
            }
        }
    }
    
    public ILogAgent getWorldLogAgent() {
        return this.worldLogAgent;
    }
}
