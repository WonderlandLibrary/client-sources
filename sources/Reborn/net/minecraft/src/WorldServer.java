package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;

public class WorldServer extends World
{
    private final MinecraftServer mcServer;
    private final EntityTracker theEntityTracker;
    private final PlayerManager thePlayerManager;
    private Set field_73064_N;
    private TreeSet pendingTickListEntries;
    public ChunkProviderServer theChunkProviderServer;
    public boolean canNotSave;
    private boolean allPlayersSleeping;
    private int updateEntityTick;
    private final Teleporter field_85177_Q;
    private ServerBlockEventList[] blockEventCache;
    private int blockEventCacheIndex;
    private static final WeightedRandomChestContent[] bonusChestContent;
    private ArrayList field_94579_S;
    private IntHashMap entityIdMap;
    
    static {
        bonusChestContent = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.stick.itemID, 0, 1, 3, 10), new WeightedRandomChestContent(Block.planks.blockID, 0, 1, 3, 10), new WeightedRandomChestContent(Block.wood.blockID, 0, 1, 3, 10), new WeightedRandomChestContent(Item.axeStone.itemID, 0, 1, 1, 3), new WeightedRandomChestContent(Item.axeWood.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.pickaxeStone.itemID, 0, 1, 1, 3), new WeightedRandomChestContent(Item.pickaxeWood.itemID, 0, 1, 1, 5), new WeightedRandomChestContent(Item.appleRed.itemID, 0, 2, 3, 5), new WeightedRandomChestContent(Item.bread.itemID, 0, 2, 3, 3) };
    }
    
    public WorldServer(final MinecraftServer par1MinecraftServer, final ISaveHandler par2ISaveHandler, final String par3Str, final int par4, final WorldSettings par5WorldSettings, final Profiler par6Profiler, final ILogAgent par7ILogAgent) {
        super(par2ISaveHandler, par3Str, par5WorldSettings, WorldProvider.getProviderForDimension(par4), par6Profiler, par7ILogAgent);
        this.updateEntityTick = 0;
        this.blockEventCache = new ServerBlockEventList[] { new ServerBlockEventList((ServerBlockEvent)null), new ServerBlockEventList((ServerBlockEvent)null) };
        this.blockEventCacheIndex = 0;
        this.field_94579_S = new ArrayList();
        this.mcServer = par1MinecraftServer;
        this.theEntityTracker = new EntityTracker(this);
        this.thePlayerManager = new PlayerManager(this, par1MinecraftServer.getConfigurationManager().getViewDistance());
        if (this.entityIdMap == null) {
            this.entityIdMap = new IntHashMap();
        }
        if (this.field_73064_N == null) {
            this.field_73064_N = new HashSet();
        }
        if (this.pendingTickListEntries == null) {
            this.pendingTickListEntries = new TreeSet();
        }
        this.field_85177_Q = new Teleporter(this);
        this.worldScoreboard = new ServerScoreboard(par1MinecraftServer);
        ScoreboardSaveData var8 = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, "scoreboard");
        if (var8 == null) {
            var8 = new ScoreboardSaveData();
            this.mapStorage.setData("scoreboard", var8);
        }
        var8.func_96499_a(this.worldScoreboard);
        ((ServerScoreboard)this.worldScoreboard).func_96547_a(var8);
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.getWorldInfo().isHardcoreModeEnabled() && this.difficultySetting < 3) {
            this.difficultySetting = 3;
        }
        this.provider.worldChunkMgr.cleanupCache();
        if (this.areAllPlayersAsleep()) {
            final boolean var1 = false;
            if (!this.spawnHostileMobs || this.difficultySetting >= 1) {}
            if (!var1) {
                final long var2 = this.worldInfo.getWorldTime() + 24000L;
                this.worldInfo.setWorldTime(var2 - var2 % 24000L);
                this.wakeAllPlayers();
            }
        }
        this.theProfiler.startSection("mobSpawner");
        if (this.getGameRules().getGameRuleBooleanValue("doMobSpawning")) {
            SpawnerAnimals.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, this.worldInfo.getWorldTotalTime() % 400L == 0L);
        }
        this.theProfiler.endStartSection("chunkSource");
        this.chunkProvider.unloadQueuedChunks();
        final int var3 = this.calculateSkylightSubtracted(1.0f);
        if (var3 != this.skylightSubtracted) {
            this.skylightSubtracted = var3;
        }
        this.worldInfo.incrementTotalWorldTime(this.worldInfo.getWorldTotalTime() + 1L);
        this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
        this.theProfiler.endStartSection("tickPending");
        this.tickUpdates(false);
        this.theProfiler.endStartSection("tickTiles");
        this.tickBlocksAndAmbiance();
        this.theProfiler.endStartSection("chunkMap");
        this.thePlayerManager.updatePlayerInstances();
        this.theProfiler.endStartSection("village");
        this.villageCollectionObj.tick();
        this.villageSiegeObj.tick();
        this.theProfiler.endStartSection("portalForcer");
        this.field_85177_Q.removeStalePortalLocations(this.getTotalWorldTime());
        this.theProfiler.endSection();
        this.sendAndApplyBlockEvents();
    }
    
    public SpawnListEntry spawnRandomCreature(final EnumCreatureType par1EnumCreatureType, final int par2, final int par3, final int par4) {
        final List var5 = this.getChunkProvider().getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
        return (var5 != null && !var5.isEmpty()) ? ((SpawnListEntry)WeightedRandom.getRandomItem(this.rand, var5)) : null;
    }
    
    @Override
    public void updateAllPlayersSleepingFlag() {
        this.allPlayersSleeping = !this.playerEntities.isEmpty();
        for (final EntityPlayer var2 : this.playerEntities) {
            if (!var2.isPlayerSleeping()) {
                this.allPlayersSleeping = false;
                break;
            }
        }
    }
    
    protected void wakeAllPlayers() {
        this.allPlayersSleeping = false;
        for (final EntityPlayer var2 : this.playerEntities) {
            if (var2.isPlayerSleeping()) {
                var2.wakeUpPlayer(false, false, true);
            }
        }
        this.resetRainAndThunder();
    }
    
    private void resetRainAndThunder() {
        this.worldInfo.setRainTime(0);
        this.worldInfo.setRaining(false);
        this.worldInfo.setThunderTime(0);
        this.worldInfo.setThundering(false);
    }
    
    public boolean areAllPlayersAsleep() {
        if (this.allPlayersSleeping && !this.isRemote) {
            for (final EntityPlayer var2 : this.playerEntities) {
                if (!var2.isPlayerFullyAsleep()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void setSpawnLocation() {
        if (this.worldInfo.getSpawnY() <= 0) {
            this.worldInfo.setSpawnY(64);
        }
        int var1 = this.worldInfo.getSpawnX();
        int var2 = this.worldInfo.getSpawnZ();
        int var3 = 0;
        while (this.getFirstUncoveredBlock(var1, var2) == 0) {
            var1 += this.rand.nextInt(8) - this.rand.nextInt(8);
            var2 += this.rand.nextInt(8) - this.rand.nextInt(8);
            if (++var3 == 10000) {
                break;
            }
        }
        this.worldInfo.setSpawnX(var1);
        this.worldInfo.setSpawnZ(var2);
    }
    
    @Override
    protected void tickBlocksAndAmbiance() {
        super.tickBlocksAndAmbiance();
        int var1 = 0;
        int var2 = 0;
        for (final ChunkCoordIntPair var4 : this.activeChunkSet) {
            final int var5 = var4.chunkXPos * 16;
            final int var6 = var4.chunkZPos * 16;
            this.theProfiler.startSection("getChunk");
            final Chunk var7 = this.getChunkFromChunkCoords(var4.chunkXPos, var4.chunkZPos);
            this.moodSoundAndLightCheck(var5, var6, var7);
            this.theProfiler.endStartSection("tickChunk");
            var7.updateSkylight();
            this.theProfiler.endStartSection("thunder");
            if (this.rand.nextInt(100000) == 0 && this.isRaining() && this.isThundering()) {
                this.updateLCG = this.updateLCG * 3 + 1013904223;
                final int var8 = this.updateLCG >> 2;
                final int var9 = var5 + (var8 & 0xF);
                final int var10 = var6 + (var8 >> 8 & 0xF);
                final int var11 = this.getPrecipitationHeight(var9, var10);
                if (this.canLightningStrikeAt(var9, var11, var10)) {
                    this.addWeatherEffect(new EntityLightningBolt(this, var9, var11, var10));
                }
            }
            this.theProfiler.endStartSection("iceandsnow");
            if (this.rand.nextInt(16) == 0) {
                this.updateLCG = this.updateLCG * 3 + 1013904223;
                final int var8 = this.updateLCG >> 2;
                final int var9 = var8 & 0xF;
                final int var10 = var8 >> 8 & 0xF;
                final int var11 = this.getPrecipitationHeight(var9 + var5, var10 + var6);
                if (this.isBlockFreezableNaturally(var9 + var5, var11 - 1, var10 + var6)) {
                    this.setBlock(var9 + var5, var11 - 1, var10 + var6, Block.ice.blockID);
                }
                if (this.isRaining() && this.canSnowAt(var9 + var5, var11, var10 + var6)) {
                    this.setBlock(var9 + var5, var11, var10 + var6, Block.snow.blockID);
                }
                if (this.isRaining()) {
                    final BiomeGenBase var12 = this.getBiomeGenForCoords(var9 + var5, var10 + var6);
                    if (var12.canSpawnLightningBolt()) {
                        final int var13 = this.getBlockId(var9 + var5, var11 - 1, var10 + var6);
                        if (var13 != 0) {
                            Block.blocksList[var13].fillWithRain(this, var9 + var5, var11 - 1, var10 + var6);
                        }
                    }
                }
            }
            this.theProfiler.endStartSection("tickTiles");
            for (final ExtendedBlockStorage var15 : var7.getBlockStorageArray()) {
                if (var15 != null && var15.getNeedsRandomTick()) {
                    for (int var16 = 0; var16 < 3; ++var16) {
                        this.updateLCG = this.updateLCG * 3 + 1013904223;
                        final int var13 = this.updateLCG >> 2;
                        final int var17 = var13 & 0xF;
                        final int var18 = var13 >> 8 & 0xF;
                        final int var19 = var13 >> 16 & 0xF;
                        final int var20 = var15.getExtBlockID(var17, var19, var18);
                        ++var2;
                        final Block var21 = Block.blocksList[var20];
                        if (var21 != null && var21.getTickRandomly()) {
                            ++var1;
                            var21.updateTick(this, var17 + var5, var19 + var15.getYLocation(), var18 + var6, this.rand);
                        }
                    }
                }
            }
            this.theProfiler.endSection();
        }
    }
    
    @Override
    public boolean isBlockTickScheduled(final int par1, final int par2, final int par3, final int par4) {
        final NextTickListEntry var5 = new NextTickListEntry(par1, par2, par3, par4);
        return this.field_94579_S.contains(var5);
    }
    
    @Override
    public void scheduleBlockUpdate(final int par1, final int par2, final int par3, final int par4, final int par5) {
        this.func_82740_a(par1, par2, par3, par4, par5, 0);
    }
    
    @Override
    public void func_82740_a(final int par1, final int par2, final int par3, final int par4, int par5, final int par6) {
        final NextTickListEntry var7 = new NextTickListEntry(par1, par2, par3, par4);
        final byte var8 = 0;
        if (this.scheduledUpdatesAreImmediate && par4 > 0) {
            if (Block.blocksList[par4].func_82506_l()) {
                if (this.checkChunksExist(var7.xCoord - var8, var7.yCoord - var8, var7.zCoord - var8, var7.xCoord + var8, var7.yCoord + var8, var7.zCoord + var8)) {
                    final int var9 = this.getBlockId(var7.xCoord, var7.yCoord, var7.zCoord);
                    if (var9 == var7.blockID && var9 > 0) {
                        Block.blocksList[var9].updateTick(this, var7.xCoord, var7.yCoord, var7.zCoord, this.rand);
                    }
                }
                return;
            }
            par5 = 1;
        }
        if (this.checkChunksExist(par1 - var8, par2 - var8, par3 - var8, par1 + var8, par2 + var8, par3 + var8)) {
            if (par4 > 0) {
                var7.setScheduledTime(par5 + this.worldInfo.getWorldTotalTime());
                var7.func_82753_a(par6);
            }
            if (!this.field_73064_N.contains(var7)) {
                this.field_73064_N.add(var7);
                this.pendingTickListEntries.add(var7);
            }
        }
    }
    
    @Override
    public void scheduleBlockUpdateFromLoad(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final NextTickListEntry var7 = new NextTickListEntry(par1, par2, par3, par4);
        var7.func_82753_a(par6);
        if (par4 > 0) {
            var7.setScheduledTime(par5 + this.worldInfo.getWorldTotalTime());
        }
        if (!this.field_73064_N.contains(var7)) {
            this.field_73064_N.add(var7);
            this.pendingTickListEntries.add(var7);
        }
    }
    
    @Override
    public void updateEntities() {
        if (this.playerEntities.isEmpty()) {
            if (this.updateEntityTick++ >= 1200) {
                return;
            }
        }
        else {
            this.resetUpdateEntityTick();
        }
        super.updateEntities();
    }
    
    public void resetUpdateEntityTick() {
        this.updateEntityTick = 0;
    }
    
    @Override
    public boolean tickUpdates(final boolean par1) {
        int var2 = this.pendingTickListEntries.size();
        if (var2 != this.field_73064_N.size()) {
            throw new IllegalStateException("TickNextTick list out of synch");
        }
        if (var2 > 1000) {
            var2 = 1000;
        }
        this.theProfiler.startSection("cleaning");
        for (int var3 = 0; var3 < var2; ++var3) {
            final NextTickListEntry var4 = this.pendingTickListEntries.first();
            if (!par1 && var4.scheduledTime > this.worldInfo.getWorldTotalTime()) {
                break;
            }
            this.pendingTickListEntries.remove(var4);
            this.field_73064_N.remove(var4);
            this.field_94579_S.add(var4);
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection("ticking");
        final Iterator var5 = this.field_94579_S.iterator();
        while (var5.hasNext()) {
            final NextTickListEntry var4 = var5.next();
            var5.remove();
            final byte var6 = 0;
            if (this.checkChunksExist(var4.xCoord - var6, var4.yCoord - var6, var4.zCoord - var6, var4.xCoord + var6, var4.yCoord + var6, var4.zCoord + var6)) {
                final int var7 = this.getBlockId(var4.xCoord, var4.yCoord, var4.zCoord);
                if (var7 <= 0 || !Block.isAssociatedBlockID(var7, var4.blockID)) {
                    continue;
                }
                try {
                    Block.blocksList[var7].updateTick(this, var4.xCoord, var4.yCoord, var4.zCoord, this.rand);
                    continue;
                }
                catch (Throwable var9) {
                    final CrashReport var8 = CrashReport.makeCrashReport(var9, "Exception while ticking a block");
                    final CrashReportCategory var10 = var8.makeCategory("Block being ticked");
                    int var11;
                    try {
                        var11 = this.getBlockMetadata(var4.xCoord, var4.yCoord, var4.zCoord);
                    }
                    catch (Throwable var12) {
                        var11 = -1;
                    }
                    CrashReportCategory.func_85068_a(var10, var4.xCoord, var4.yCoord, var4.zCoord, var7, var11);
                    throw new ReportedException(var8);
                }
            }
            this.scheduleBlockUpdate(var4.xCoord, var4.yCoord, var4.zCoord, var4.blockID, 0);
        }
        this.theProfiler.endSection();
        this.field_94579_S.clear();
        return !this.pendingTickListEntries.isEmpty();
    }
    
    @Override
    public List getPendingBlockUpdates(final Chunk par1Chunk, final boolean par2) {
        ArrayList var3 = null;
        final ChunkCoordIntPair var4 = par1Chunk.getChunkCoordIntPair();
        final int var5 = (var4.chunkXPos << 4) - 2;
        final int var6 = var5 + 16 + 2;
        final int var7 = (var4.chunkZPos << 4) - 2;
        final int var8 = var7 + 16 + 2;
        for (int var9 = 0; var9 < 2; ++var9) {
            Iterator var10;
            if (var9 == 0) {
                var10 = this.pendingTickListEntries.iterator();
            }
            else {
                var10 = this.field_94579_S.iterator();
                if (!this.field_94579_S.isEmpty()) {
                    System.out.println(this.field_94579_S.size());
                }
            }
            while (var10.hasNext()) {
                final NextTickListEntry var11 = var10.next();
                if (var11.xCoord >= var5 && var11.xCoord < var6 && var11.zCoord >= var7 && var11.zCoord < var8) {
                    if (par2) {
                        this.field_73064_N.remove(var11);
                        var10.remove();
                    }
                    if (var3 == null) {
                        var3 = new ArrayList();
                    }
                    var3.add(var11);
                }
            }
        }
        return var3;
    }
    
    @Override
    public void updateEntityWithOptionalForce(final Entity par1Entity, final boolean par2) {
        if (!this.mcServer.getCanSpawnAnimals() && (par1Entity instanceof EntityAnimal || par1Entity instanceof EntityWaterMob)) {
            par1Entity.setDead();
        }
        if (!this.mcServer.getCanSpawnNPCs() && par1Entity instanceof INpc) {
            par1Entity.setDead();
        }
        if (!(par1Entity.riddenByEntity instanceof EntityPlayer)) {
            super.updateEntityWithOptionalForce(par1Entity, par2);
        }
    }
    
    public void uncheckedUpdateEntity(final Entity par1Entity, final boolean par2) {
        super.updateEntityWithOptionalForce(par1Entity, par2);
    }
    
    @Override
    protected IChunkProvider createChunkProvider() {
        final IChunkLoader var1 = this.saveHandler.getChunkLoader(this.provider);
        return this.theChunkProviderServer = new ChunkProviderServer(this, var1, this.provider.createChunkGenerator());
    }
    
    public List getAllTileEntityInBox(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final ArrayList var7 = new ArrayList();
        for (int var8 = 0; var8 < this.loadedTileEntityList.size(); ++var8) {
            final TileEntity var9 = this.loadedTileEntityList.get(var8);
            if (var9.xCoord >= par1 && var9.yCoord >= par2 && var9.zCoord >= par3 && var9.xCoord < par4 && var9.yCoord < par5 && var9.zCoord < par6) {
                var7.add(var9);
            }
        }
        return var7;
    }
    
    @Override
    public boolean canMineBlock(final EntityPlayer par1EntityPlayer, final int par2, final int par3, final int par4) {
        return !this.mcServer.func_96290_a(this, par2, par3, par4, par1EntityPlayer);
    }
    
    @Override
    protected void initialize(final WorldSettings par1WorldSettings) {
        if (this.entityIdMap == null) {
            this.entityIdMap = new IntHashMap();
        }
        if (this.field_73064_N == null) {
            this.field_73064_N = new HashSet();
        }
        if (this.pendingTickListEntries == null) {
            this.pendingTickListEntries = new TreeSet();
        }
        this.createSpawnPosition(par1WorldSettings);
        super.initialize(par1WorldSettings);
    }
    
    protected void createSpawnPosition(final WorldSettings par1WorldSettings) {
        if (!this.provider.canRespawnHere()) {
            this.worldInfo.setSpawnPosition(0, this.provider.getAverageGroundLevel(), 0);
        }
        else {
            this.findingSpawnPoint = true;
            final WorldChunkManager var2 = this.provider.worldChunkMgr;
            final List var3 = var2.getBiomesToSpawnIn();
            final Random var4 = new Random(this.getSeed());
            final ChunkPosition var5 = var2.findBiomePosition(0, 0, 256, var3, var4);
            int var6 = 0;
            final int var7 = this.provider.getAverageGroundLevel();
            int var8 = 0;
            if (var5 != null) {
                var6 = var5.x;
                var8 = var5.z;
            }
            else {
                this.getWorldLogAgent().logWarning("Unable to find spawn biome");
            }
            int var9 = 0;
            while (!this.provider.canCoordinateBeSpawn(var6, var8)) {
                var6 += var4.nextInt(64) - var4.nextInt(64);
                var8 += var4.nextInt(64) - var4.nextInt(64);
                if (++var9 == 1000) {
                    break;
                }
            }
            this.worldInfo.setSpawnPosition(var6, var7, var8);
            this.findingSpawnPoint = false;
            if (par1WorldSettings.isBonusChestEnabled()) {
                this.createBonusChest();
            }
        }
    }
    
    protected void createBonusChest() {
        final WorldGeneratorBonusChest var1 = new WorldGeneratorBonusChest(WorldServer.bonusChestContent, 10);
        for (int var2 = 0; var2 < 10; ++var2) {
            final int var3 = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
            final int var4 = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
            final int var5 = this.getTopSolidOrLiquidBlock(var3, var4) + 1;
            if (var1.generate(this, this.rand, var3, var5, var4)) {
                break;
            }
        }
    }
    
    public ChunkCoordinates getEntrancePortalLocation() {
        return this.provider.getEntrancePortalLocation();
    }
    
    public void saveAllChunks(final boolean par1, final IProgressUpdate par2IProgressUpdate) throws MinecraftException {
        if (this.chunkProvider.canSave()) {
            if (par2IProgressUpdate != null) {
                par2IProgressUpdate.displayProgressMessage("Saving level");
            }
            this.saveLevel();
            if (par2IProgressUpdate != null) {
                par2IProgressUpdate.resetProgresAndWorkingMessage("Saving chunks");
            }
            this.chunkProvider.saveChunks(par1, par2IProgressUpdate);
        }
    }
    
    public void func_104140_m() {
        if (this.chunkProvider.canSave()) {
            this.chunkProvider.func_104112_b();
        }
    }
    
    protected void saveLevel() throws MinecraftException {
        this.checkSessionLock();
        this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
        this.mapStorage.saveAllData();
    }
    
    @Override
    protected void obtainEntitySkin(final Entity par1Entity) {
        super.obtainEntitySkin(par1Entity);
        this.entityIdMap.addKey(par1Entity.entityId, par1Entity);
        final Entity[] var2 = par1Entity.getParts();
        if (var2 != null) {
            for (int var3 = 0; var3 < var2.length; ++var3) {
                this.entityIdMap.addKey(var2[var3].entityId, var2[var3]);
            }
        }
    }
    
    @Override
    protected void releaseEntitySkin(final Entity par1Entity) {
        super.releaseEntitySkin(par1Entity);
        this.entityIdMap.removeObject(par1Entity.entityId);
        final Entity[] var2 = par1Entity.getParts();
        if (var2 != null) {
            for (int var3 = 0; var3 < var2.length; ++var3) {
                this.entityIdMap.removeObject(var2[var3].entityId);
            }
        }
    }
    
    @Override
    public Entity getEntityByID(final int par1) {
        return (Entity)this.entityIdMap.lookup(par1);
    }
    
    @Override
    public boolean addWeatherEffect(final Entity par1Entity) {
        if (super.addWeatherEffect(par1Entity)) {
            this.mcServer.getConfigurationManager().sendToAllNear(par1Entity.posX, par1Entity.posY, par1Entity.posZ, 512.0, this.provider.dimensionId, new Packet71Weather(par1Entity));
            return true;
        }
        return false;
    }
    
    @Override
    public void setEntityState(final Entity par1Entity, final byte par2) {
        final Packet38EntityStatus var3 = new Packet38EntityStatus(par1Entity.entityId, par2);
        this.getEntityTracker().sendPacketToAllAssociatedPlayers(par1Entity, var3);
    }
    
    @Override
    public Explosion newExplosion(final Entity par1Entity, final double par2, final double par4, final double par6, final float par8, final boolean par9, final boolean par10) {
        final Explosion var11 = new Explosion(this, par1Entity, par2, par4, par6, par8);
        var11.isFlaming = par9;
        var11.isSmoking = par10;
        var11.doExplosionA();
        var11.doExplosionB(false);
        if (!par10) {
            var11.affectedBlockPositions.clear();
        }
        for (final EntityPlayer var13 : this.playerEntities) {
            if (var13.getDistanceSq(par2, par4, par6) < 4096.0) {
                ((EntityPlayerMP)var13).playerNetServerHandler.sendPacketToPlayer(new Packet60Explosion(par2, par4, par6, par8, var11.affectedBlockPositions, var11.func_77277_b().get(var13)));
            }
        }
        return var11;
    }
    
    @Override
    public void addBlockEvent(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final BlockEventData var7 = new BlockEventData(par1, par2, par3, par4, par5, par6);
        for (final BlockEventData var9 : this.blockEventCache[this.blockEventCacheIndex]) {
            if (var9.equals(var7)) {
                return;
            }
        }
        this.blockEventCache[this.blockEventCacheIndex].add(var7);
    }
    
    private void sendAndApplyBlockEvents() {
        while (!this.blockEventCache[this.blockEventCacheIndex].isEmpty()) {
            final int var1 = this.blockEventCacheIndex;
            this.blockEventCacheIndex ^= 0x1;
            for (final BlockEventData var3 : this.blockEventCache[var1]) {
                if (this.onBlockEventReceived(var3)) {
                    this.mcServer.getConfigurationManager().sendToAllNear(var3.getX(), var3.getY(), var3.getZ(), 64.0, this.provider.dimensionId, new Packet54PlayNoteBlock(var3.getX(), var3.getY(), var3.getZ(), var3.getBlockID(), var3.getEventID(), var3.getEventParameter()));
                }
            }
            this.blockEventCache[var1].clear();
        }
    }
    
    private boolean onBlockEventReceived(final BlockEventData par1BlockEventData) {
        final int var2 = this.getBlockId(par1BlockEventData.getX(), par1BlockEventData.getY(), par1BlockEventData.getZ());
        return var2 == par1BlockEventData.getBlockID() && Block.blocksList[var2].onBlockEventReceived(this, par1BlockEventData.getX(), par1BlockEventData.getY(), par1BlockEventData.getZ(), par1BlockEventData.getEventID(), par1BlockEventData.getEventParameter());
    }
    
    public void flush() {
        this.saveHandler.flush();
    }
    
    @Override
    protected void updateWeather() {
        final boolean var1 = this.isRaining();
        super.updateWeather();
        if (var1 != this.isRaining()) {
            if (var1) {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet70GameEvent(2, 0));
            }
            else {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new Packet70GameEvent(1, 0));
            }
        }
    }
    
    public MinecraftServer getMinecraftServer() {
        return this.mcServer;
    }
    
    public EntityTracker getEntityTracker() {
        return this.theEntityTracker;
    }
    
    public PlayerManager getPlayerManager() {
        return this.thePlayerManager;
    }
    
    public Teleporter getDefaultTeleporter() {
        return this.field_85177_Q;
    }
}
