package net.minecraft.world;

import net.minecraft.server.management.*;
import net.minecraft.world.gen.*;
import net.minecraft.server.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.block.state.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.biome.*;
import net.minecraft.profiler.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.entity.effect.*;
import net.minecraft.world.chunk.storage.*;
import com.google.common.util.concurrent.*;
import net.minecraft.world.storage.*;
import net.minecraft.village.*;
import net.minecraft.scoreboard.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import com.google.common.base.*;

public class WorldServer extends World implements IThreadListener
{
    private boolean allPlayersSleeping;
    private final TreeSet<NextTickListEntry> pendingTickListEntriesTreeSet;
    private int blockEventCacheIndex;
    protected final VillageSiege villageSiege;
    private final SpawnerAnimals mobSpawner;
    private final Teleporter worldTeleporter;
    private static final Logger logger;
    private final Map<UUID, Entity> entitiesByUuid;
    private static final List<WeightedRandomChestContent> bonusChestContent;
    private final EntityTracker theEntityTracker;
    private final PlayerManager thePlayerManager;
    private ServerBlockEventList[] field_147490_S;
    public ChunkProviderServer theChunkProviderServer;
    private final MinecraftServer mcServer;
    public boolean disableLevelSaving;
    private final Set<NextTickListEntry> pendingTickListEntriesHashSet;
    private List<NextTickListEntry> pendingTickListEntriesThisTick;
    private int updateEntityTick;
    private static final String[] I;
    
    public EntityTracker getEntityTracker() {
        return this.theEntityTracker;
    }
    
    public void resetUpdateEntityTick() {
        this.updateEntityTick = "".length();
    }
    
    @Override
    public boolean isBlockModifiable(final EntityPlayer entityPlayer, final BlockPos blockPos) {
        if (!this.mcServer.isBlockProtected(this, blockPos, entityPlayer) && this.getWorldBorder().contains(blockPos)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean tickUpdates(final boolean b) {
        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            return "".length() != 0;
        }
        int size = this.pendingTickListEntriesTreeSet.size();
        if (size != this.pendingTickListEntriesHashSet.size()) {
            throw new IllegalStateException(WorldServer.I[0x40 ^ 0x52]);
        }
        if (size > 763 + 655 - 861 + 443) {
            size = 212 + 123 + 301 + 364;
        }
        this.theProfiler.startSection(WorldServer.I[0x73 ^ 0x60]);
        int i = "".length();
        "".length();
        if (4 < 3) {
            throw null;
        }
        while (i < size) {
            final NextTickListEntry nextTickListEntry = this.pendingTickListEntriesTreeSet.first();
            if (!b && nextTickListEntry.scheduledTime > this.worldInfo.getWorldTotalTime()) {
                "".length();
                if (4 < 3) {
                    throw null;
                }
                break;
            }
            else {
                this.pendingTickListEntriesTreeSet.remove(nextTickListEntry);
                this.pendingTickListEntriesHashSet.remove(nextTickListEntry);
                this.pendingTickListEntriesThisTick.add(nextTickListEntry);
                ++i;
            }
        }
        this.theProfiler.endSection();
        this.theProfiler.startSection(WorldServer.I[0x5F ^ 0x4B]);
        final Iterator<NextTickListEntry> iterator = this.pendingTickListEntriesThisTick.iterator();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final NextTickListEntry nextTickListEntry2 = iterator.next();
            iterator.remove();
            final int length = "".length();
            if (this.isAreaLoaded(nextTickListEntry2.position.add(-length, -length, -length), nextTickListEntry2.position.add(length, length, length))) {
                final IBlockState blockState = this.getBlockState(nextTickListEntry2.position);
                if (blockState.getBlock().getMaterial() == Material.air || !Block.isEqualTo(blockState.getBlock(), nextTickListEntry2.getBlock())) {
                    continue;
                }
                try {
                    blockState.getBlock().updateTick(this, nextTickListEntry2.position, blockState, this.rand);
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                    continue;
                }
                catch (Throwable t) {
                    final CrashReport crashReport = CrashReport.makeCrashReport(t, WorldServer.I[0x1A ^ 0xF]);
                    CrashReportCategory.addBlockInfo(crashReport.makeCategory(WorldServer.I[0x3F ^ 0x29]), nextTickListEntry2.position, blockState);
                    throw new ReportedException(crashReport);
                }
            }
            this.scheduleUpdate(nextTickListEntry2.position, nextTickListEntry2.getBlock(), "".length());
        }
        this.theProfiler.endSection();
        this.pendingTickListEntriesThisTick.clear();
        int n;
        if (this.pendingTickListEntriesTreeSet.isEmpty()) {
            n = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public void setEntityState(final Entity entity, final byte b) {
        this.getEntityTracker().func_151248_b(entity, new S19PacketEntityStatus(entity, b));
    }
    
    public MinecraftServer getMinecraftServer() {
        return this.mcServer;
    }
    
    public void spawnParticle(final EnumParticleTypes enumParticleTypes, final double n, final double n2, final double n3, final int n4, final double n5, final double n6, final double n7, final double n8, final int... array) {
        this.spawnParticle(enumParticleTypes, "".length() != 0, n, n2, n3, n4, n5, n6, n7, n8, array);
    }
    
    public Entity getEntityFromUuid(final UUID uuid) {
        return this.entitiesByUuid.get(uuid);
    }
    
    public BiomeGenBase.SpawnListEntry getSpawnListEntryForTypeAt(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        final List<BiomeGenBase.SpawnListEntry> possibleCreatures = this.getChunkProvider().getPossibleCreatures(enumCreatureType, blockPos);
        BiomeGenBase.SpawnListEntry spawnListEntry;
        if (possibleCreatures != null && !possibleCreatures.isEmpty()) {
            spawnListEntry = WeightedRandom.getRandomItem(this.rand, possibleCreatures);
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            spawnListEntry = null;
        }
        return spawnListEntry;
    }
    
    @Override
    public Explosion newExplosion(final Entity entity, final double n, final double n2, final double n3, final float n4, final boolean b, final boolean b2) {
        final Explosion explosion = new Explosion(this, entity, n, n2, n3, n4, b, b2);
        explosion.doExplosionA();
        explosion.doExplosionB("".length() != 0);
        if (!b2) {
            explosion.func_180342_d();
        }
        final Iterator<EntityPlayer> iterator = this.playerEntities.iterator();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayer entityPlayer = iterator.next();
            if (entityPlayer.getDistanceSq(n, n2, n3) < 4096.0) {
                ((EntityPlayerMP)entityPlayer).playerNetServerHandler.sendPacket(new S27PacketExplosion(n, n2, n3, n4, explosion.getAffectedBlockPositions(), explosion.getPlayerKnockbackMap().get(entityPlayer)));
            }
        }
        return explosion;
    }
    
    @Override
    public void addBlockEvent(final BlockPos blockPos, final Block block, final int n, final int n2) {
        final BlockEventData blockEventData = new BlockEventData(blockPos, block, n, n2);
        final Iterator<BlockEventData> iterator = this.field_147490_S[this.blockEventCacheIndex].iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (iterator.next().equals(blockEventData)) {
                return;
            }
        }
        this.field_147490_S[this.blockEventCacheIndex].add(blockEventData);
    }
    
    @Override
    public void updateEntityWithOptionalForce(final Entity entity, final boolean b) {
        if (!this.canSpawnAnimals() && (entity instanceof EntityAnimal || entity instanceof EntityWaterMob)) {
            entity.setDead();
        }
        if (!this.canSpawnNPCs() && entity instanceof INpc) {
            entity.setDead();
        }
        super.updateEntityWithOptionalForce(entity, b);
    }
    
    @Override
    public void setInitialSpawnLocation() {
        if (this.worldInfo.getSpawnY() <= 0) {
            this.worldInfo.setSpawnY(this.func_181545_F() + " ".length());
        }
        int spawnX = this.worldInfo.getSpawnX();
        int spawnZ = this.worldInfo.getSpawnZ();
        int length = "".length();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (this.getGroundAboveSeaLevel(new BlockPos(spawnX, "".length(), spawnZ)).getMaterial() == Material.air) {
            spawnX += this.rand.nextInt(0x41 ^ 0x49) - this.rand.nextInt(0xB ^ 0x3);
            spawnZ += this.rand.nextInt(0x7E ^ 0x76) - this.rand.nextInt(0x65 ^ 0x6D);
            if (++length == 9715 + 2064 - 7369 + 5590) {
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
        }
        this.worldInfo.setSpawnX(spawnX);
        this.worldInfo.setSpawnZ(spawnZ);
    }
    
    protected void createBonusChest() {
        final WorldGeneratorBonusChest worldGeneratorBonusChest = new WorldGeneratorBonusChest(WorldServer.bonusChestContent, 0x80 ^ 0x8A);
        int i = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (i < (0xB3 ^ 0xB9)) {
            if (worldGeneratorBonusChest.generate(this, this.rand, this.getTopSolidOrLiquidBlock(new BlockPos(this.worldInfo.getSpawnX() + this.rand.nextInt(0xE ^ 0x8) - this.rand.nextInt(0x6 ^ 0x0), "".length(), this.worldInfo.getSpawnZ() + this.rand.nextInt(0x20 ^ 0x26) - this.rand.nextInt(0x67 ^ 0x61))).up())) {
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                break;
            }
            else {
                ++i;
            }
        }
    }
    
    private void createSpawnPosition(final WorldSettings worldSettings) {
        if (!this.provider.canRespawnHere()) {
            this.worldInfo.setSpawn(BlockPos.ORIGIN.up(this.provider.getAverageGroundLevel()));
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            this.worldInfo.setSpawn(BlockPos.ORIGIN.up());
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            this.findingSpawnPoint = (" ".length() != 0);
            final WorldChunkManager worldChunkManager = this.provider.getWorldChunkManager();
            final List<BiomeGenBase> biomesToSpawnIn = worldChunkManager.getBiomesToSpawnIn();
            final Random random = new Random(this.getSeed());
            final BlockPos biomePosition = worldChunkManager.findBiomePosition("".length(), "".length(), 27 + 85 + 88 + 56, biomesToSpawnIn, random);
            int n = "".length();
            final int averageGroundLevel = this.provider.getAverageGroundLevel();
            int n2 = "".length();
            if (biomePosition != null) {
                n = biomePosition.getX();
                n2 = biomePosition.getZ();
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
            else {
                WorldServer.logger.warn(WorldServer.I[0x76 ^ 0x6C]);
            }
            int length = "".length();
            "".length();
            if (-1 == 3) {
                throw null;
            }
            while (!this.provider.canCoordinateBeSpawn(n, n2)) {
                n += random.nextInt(0x24 ^ 0x64) - random.nextInt(0xC9 ^ 0x89);
                n2 += random.nextInt(0xEC ^ 0xAC) - random.nextInt(0xFB ^ 0xBB);
                if (++length == 62 + 990 - 299 + 247) {
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                    break;
                }
            }
            this.worldInfo.setSpawn(new BlockPos(n, averageGroundLevel, n2));
            this.findingSpawnPoint = ("".length() != 0);
            if (worldSettings.isBonusChestEnabled()) {
                this.createBonusChest();
            }
        }
    }
    
    public WorldServer(final MinecraftServer mcServer, final ISaveHandler saveHandler, final WorldInfo worldInfo, final int n, final Profiler profiler) {
        super(saveHandler, worldInfo, WorldProvider.getProviderForDimension(n), profiler, "".length() != 0);
        this.pendingTickListEntriesHashSet = (Set<NextTickListEntry>)Sets.newHashSet();
        this.pendingTickListEntriesTreeSet = new TreeSet<NextTickListEntry>();
        this.entitiesByUuid = (Map<UUID, Entity>)Maps.newHashMap();
        this.mobSpawner = new SpawnerAnimals();
        this.villageSiege = new VillageSiege(this);
        final ServerBlockEventList[] field_147490_S = new ServerBlockEventList["  ".length()];
        field_147490_S["".length()] = new ServerBlockEventList(null);
        field_147490_S[" ".length()] = new ServerBlockEventList(null);
        this.field_147490_S = field_147490_S;
        this.pendingTickListEntriesThisTick = (List<NextTickListEntry>)Lists.newArrayList();
        this.mcServer = mcServer;
        this.theEntityTracker = new EntityTracker(this);
        this.thePlayerManager = new PlayerManager(this);
        this.provider.registerWorld(this);
        this.chunkProvider = this.createChunkProvider();
        this.worldTeleporter = new Teleporter(this);
        this.calculateInitialSkylight();
        this.calculateInitialWeather();
        this.getWorldBorder().setSize(mcServer.getMaxWorldSize());
    }
    
    protected void saveLevel() throws MinecraftException {
        this.checkSessionLock();
        this.worldInfo.setBorderSize(this.getWorldBorder().getDiameter());
        this.worldInfo.getBorderCenterX(this.getWorldBorder().getCenterX());
        this.worldInfo.getBorderCenterZ(this.getWorldBorder().getCenterZ());
        this.worldInfo.setBorderSafeZone(this.getWorldBorder().getDamageBuffer());
        this.worldInfo.setBorderDamagePerBlock(this.getWorldBorder().getDamageAmount());
        this.worldInfo.setBorderWarningDistance(this.getWorldBorder().getWarningDistance());
        this.worldInfo.setBorderWarningTime(this.getWorldBorder().getWarningTime());
        this.worldInfo.setBorderLerpTarget(this.getWorldBorder().getTargetSize());
        this.worldInfo.setBorderLerpTime(this.getWorldBorder().getTimeUntilTarget());
        this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getConfigurationManager().getHostPlayerData());
        this.mapStorage.saveAllData();
    }
    
    @Override
    protected IChunkProvider createChunkProvider() {
        return this.theChunkProviderServer = new ChunkProviderServer(this, this.saveHandler.getChunkLoader(this.provider), this.provider.createChunkGenerator());
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        final WeightedRandomChestContent[] array = new WeightedRandomChestContent[0x7F ^ 0x75];
        array["".length()] = new WeightedRandomChestContent(Items.stick, "".length(), " ".length(), "   ".length(), 0xAB ^ 0xA1);
        array[" ".length()] = new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.planks), "".length(), " ".length(), "   ".length(), 0x7B ^ 0x71);
        array["  ".length()] = new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log), "".length(), " ".length(), "   ".length(), 0xBF ^ 0xB5);
        array["   ".length()] = new WeightedRandomChestContent(Items.stone_axe, "".length(), " ".length(), " ".length(), "   ".length());
        array[0x79 ^ 0x7D] = new WeightedRandomChestContent(Items.wooden_axe, "".length(), " ".length(), " ".length(), 0x9A ^ 0x9F);
        array[0x1B ^ 0x1E] = new WeightedRandomChestContent(Items.stone_pickaxe, "".length(), " ".length(), " ".length(), "   ".length());
        array[0x68 ^ 0x6E] = new WeightedRandomChestContent(Items.wooden_pickaxe, "".length(), " ".length(), " ".length(), 0x79 ^ 0x7C);
        array[0x79 ^ 0x7E] = new WeightedRandomChestContent(Items.apple, "".length(), "  ".length(), "   ".length(), 0x58 ^ 0x5D);
        array[0x66 ^ 0x6E] = new WeightedRandomChestContent(Items.bread, "".length(), "  ".length(), "   ".length(), "   ".length());
        array[0x12 ^ 0x1B] = new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.log2), "".length(), " ".length(), "   ".length(), 0x8B ^ 0x81);
        bonusChestContent = Lists.newArrayList((Object[])array);
    }
    
    private void sendQueuedBlockEvents() {
        "".length();
        if (true != true) {
            throw null;
        }
        while (!this.field_147490_S[this.blockEventCacheIndex].isEmpty()) {
            final int blockEventCacheIndex = this.blockEventCacheIndex;
            this.blockEventCacheIndex ^= " ".length();
            final Iterator<BlockEventData> iterator = this.field_147490_S[blockEventCacheIndex].iterator();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final BlockEventData blockEventData = iterator.next();
                if (this.fireBlockEvent(blockEventData)) {
                    this.mcServer.getConfigurationManager().sendToAllNear(blockEventData.getPosition().getX(), blockEventData.getPosition().getY(), blockEventData.getPosition().getZ(), 64.0, this.provider.getDimensionId(), new S24PacketBlockAction(blockEventData.getPosition(), blockEventData.getBlock(), blockEventData.getEventID(), blockEventData.getEventParameter()));
                }
            }
            this.field_147490_S[blockEventCacheIndex].clear();
        }
    }
    
    @Override
    public void updateAllPlayersSleepingFlag() {
        this.allPlayersSleeping = ("".length() != 0);
        if (!this.playerEntities.isEmpty()) {
            int length = "".length();
            int length2 = "".length();
            final Iterator<EntityPlayer> iterator = this.playerEntities.iterator();
            "".length();
            if (1 == 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityPlayer entityPlayer = iterator.next();
                if (entityPlayer.isSpectator()) {
                    ++length;
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                    continue;
                }
                else {
                    if (!entityPlayer.isPlayerSleeping()) {
                        continue;
                    }
                    ++length2;
                }
            }
            int allPlayersSleeping;
            if (length2 > 0 && length2 >= this.playerEntities.size() - length) {
                allPlayersSleeping = " ".length();
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                allPlayersSleeping = "".length();
            }
            this.allPlayersSleeping = (allPlayersSleeping != 0);
        }
    }
    
    @Override
    protected void updateWeather() {
        final boolean raining = this.isRaining();
        super.updateWeather();
        if (this.prevRainingStrength != this.rainingStrength) {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(0x4F ^ 0x48, this.rainingStrength), this.provider.getDimensionId());
        }
        if (this.prevThunderingStrength != this.thunderingStrength) {
            this.mcServer.getConfigurationManager().sendPacketToAllPlayersInDimension(new S2BPacketChangeGameState(0x40 ^ 0x48, this.thunderingStrength), this.provider.getDimensionId());
        }
        if (raining != this.isRaining()) {
            if (raining) {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState("  ".length(), 0.0f));
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(" ".length(), 0.0f));
            }
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(0xD ^ 0xA, this.rainingStrength));
            this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(0xA6 ^ 0xAE, this.thunderingStrength));
        }
    }
    
    @Override
    protected int getRenderDistanceChunks() {
        return this.mcServer.getConfigurationManager().getViewDistance();
    }
    
    public boolean areAllPlayersAsleep() {
        if (!this.allPlayersSleeping || this.isRemote) {
            return "".length() != 0;
        }
        final Iterator<EntityPlayer> iterator = this.playerEntities.iterator();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayer entityPlayer = iterator.next();
            if (entityPlayer.isSpectator() || !entityPlayer.isPlayerFullyAsleep()) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
    }
    
    @Override
    public void updateBlockTick(final BlockPos blockPos, final Block block, int length, final int priority) {
        final NextTickListEntry nextTickListEntry = new NextTickListEntry(blockPos, block);
        final int length2 = "".length();
        if (this.scheduledUpdatesAreImmediate && block.getMaterial() != Material.air) {
            if (block.requiresUpdates()) {
                final int n = 0xA0 ^ 0xA8;
                if (this.isAreaLoaded(nextTickListEntry.position.add(-n, -n, -n), nextTickListEntry.position.add(n, n, n))) {
                    final IBlockState blockState = this.getBlockState(nextTickListEntry.position);
                    if (blockState.getBlock().getMaterial() != Material.air && blockState.getBlock() == nextTickListEntry.getBlock()) {
                        blockState.getBlock().updateTick(this, nextTickListEntry.position, blockState, this.rand);
                    }
                }
                return;
            }
            length = " ".length();
        }
        if (this.isAreaLoaded(blockPos.add(-length2, -length2, -length2), blockPos.add(length2, length2, length2))) {
            if (block.getMaterial() != Material.air) {
                nextTickListEntry.setScheduledTime(length + this.worldInfo.getWorldTotalTime());
                nextTickListEntry.setPriority(priority);
            }
            if (!this.pendingTickListEntriesHashSet.contains(nextTickListEntry)) {
                this.pendingTickListEntriesHashSet.add(nextTickListEntry);
                this.pendingTickListEntriesTreeSet.add(nextTickListEntry);
            }
        }
    }
    
    public PlayerManager getPlayerManager() {
        return this.thePlayerManager;
    }
    
    private void resetRainAndThunder() {
        this.worldInfo.setRainTime("".length());
        this.worldInfo.setRaining("".length() != 0);
        this.worldInfo.setThunderTime("".length());
        this.worldInfo.setThundering("".length() != 0);
    }
    
    @Override
    public List<NextTickListEntry> getPendingBlockUpdates(final Chunk chunk, final boolean b) {
        final ChunkCoordIntPair chunkCoordIntPair = chunk.getChunkCoordIntPair();
        final int n = (chunkCoordIntPair.chunkXPos << (0x55 ^ 0x51)) - "  ".length();
        final int n2 = n + (0x24 ^ 0x34) + "  ".length();
        final int n3 = (chunkCoordIntPair.chunkZPos << (0x5F ^ 0x5B)) - "  ".length();
        return this.func_175712_a(new StructureBoundingBox(n, "".length(), n3, n2, 179 + 40 - 0 + 37, n3 + (0x43 ^ 0x53) + "  ".length()), b);
    }
    
    public void spawnParticle(final EnumParticleTypes enumParticleTypes, final boolean b, final double n, final double n2, final double n3, final int n4, final double n5, final double n6, final double n7, final double n8, final int... array) {
        final S2APacketParticles s2APacketParticles = new S2APacketParticles(enumParticleTypes, b, (float)n, (float)n2, (float)n3, (float)n5, (float)n6, (float)n7, (float)n8, n4, array);
        int i = "".length();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (i < this.playerEntities.size()) {
            final EntityPlayerMP entityPlayerMP = this.playerEntities.get(i);
            final double distanceSq = entityPlayerMP.getPosition().distanceSq(n, n2, n3);
            if (distanceSq <= 256.0 || (b && distanceSq <= 65536.0)) {
                entityPlayerMP.playerNetServerHandler.sendPacket(s2APacketParticles);
            }
            ++i;
        }
    }
    
    @Override
    protected void onEntityRemoved(final Entity entity) {
        super.onEntityRemoved(entity);
        this.entitiesById.removeObject(entity.getEntityId());
        this.entitiesByUuid.remove(entity.getUniqueID());
        final Entity[] parts = entity.getParts();
        if (parts != null) {
            int i = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (i < parts.length) {
                this.entitiesById.removeObject(parts[i].getEntityId());
                ++i;
            }
        }
    }
    
    @Override
    public void updateEntities() {
        if (this.playerEntities.isEmpty()) {
            final int updateEntityTick = this.updateEntityTick;
            this.updateEntityTick = updateEntityTick + " ".length();
            if (updateEntityTick >= 683 + 1071 - 1471 + 917) {
                return;
            }
        }
        else {
            this.resetUpdateEntityTick();
        }
        super.updateEntities();
    }
    
    @Override
    public void scheduleBlockUpdate(final BlockPos blockPos, final Block block, final int n, final int priority) {
        final NextTickListEntry nextTickListEntry = new NextTickListEntry(blockPos, block);
        nextTickListEntry.setPriority(priority);
        if (block.getMaterial() != Material.air) {
            nextTickListEntry.setScheduledTime(n + this.worldInfo.getWorldTotalTime());
        }
        if (!this.pendingTickListEntriesHashSet.contains(nextTickListEntry)) {
            this.pendingTickListEntriesHashSet.add(nextTickListEntry);
            this.pendingTickListEntriesTreeSet.add(nextTickListEntry);
        }
    }
    
    public void flush() {
        this.saveHandler.flush();
    }
    
    private void setDebugWorldSettings() {
        this.worldInfo.setMapFeaturesEnabled("".length() != 0);
        this.worldInfo.setAllowCommands(" ".length() != 0);
        this.worldInfo.setRaining("".length() != 0);
        this.worldInfo.setThundering("".length() != 0);
        this.worldInfo.setCleanWeatherTime(626002105 + 239459034 - 613106866 + 747645727);
        this.worldInfo.setWorldTime(6000L);
        this.worldInfo.setGameType(WorldSettings.GameType.SPECTATOR);
        this.worldInfo.setHardcore("".length() != 0);
        this.worldInfo.setDifficulty(EnumDifficulty.PEACEFUL);
        this.worldInfo.setDifficultyLocked(" ".length() != 0);
        this.getGameRules().setOrCreateGameRule(WorldServer.I[0x6F ^ 0x77], WorldServer.I[0xA5 ^ 0xBC]);
    }
    
    public void saveChunkData() {
        if (this.chunkProvider.canSave()) {
            this.chunkProvider.saveExtraData();
        }
    }
    
    private boolean fireBlockEvent(final BlockEventData blockEventData) {
        final IBlockState blockState = this.getBlockState(blockEventData.getPosition());
        int n;
        if (blockState.getBlock() == blockEventData.getBlock()) {
            n = (blockState.getBlock().onBlockEventReceived(this, blockEventData.getPosition(), blockState, blockEventData.getEventID(), blockEventData.getEventParameter()) ? 1 : 0);
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void scheduleUpdate(final BlockPos blockPos, final Block block, final int n) {
        this.updateBlockTick(blockPos, block, n, "".length());
    }
    
    @Override
    protected void updateBlocks() {
        super.updateBlocks();
        if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
            final Iterator<ChunkCoordIntPair> iterator = this.activeChunkSet.iterator();
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final ChunkCoordIntPair chunkCoordIntPair = iterator.next();
                this.getChunkFromChunkCoords(chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos).func_150804_b("".length() != 0);
            }
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            int length = "".length();
            int length2 = "".length();
            final Iterator<ChunkCoordIntPair> iterator2 = this.activeChunkSet.iterator();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final ChunkCoordIntPair chunkCoordIntPair2 = iterator2.next();
                final int n = chunkCoordIntPair2.chunkXPos * (0x73 ^ 0x63);
                final int n2 = chunkCoordIntPair2.chunkZPos * (0x10 ^ 0x0);
                this.theProfiler.startSection(WorldServer.I[0x16 ^ 0x1A]);
                final Chunk chunkFromChunkCoords = this.getChunkFromChunkCoords(chunkCoordIntPair2.chunkXPos, chunkCoordIntPair2.chunkZPos);
                this.playMoodSoundAndCheckLight(n, n2, chunkFromChunkCoords);
                this.theProfiler.endStartSection(WorldServer.I[0x54 ^ 0x59]);
                chunkFromChunkCoords.func_150804_b("".length() != 0);
                this.theProfiler.endStartSection(WorldServer.I[0xBC ^ 0xB2]);
                if (this.rand.nextInt(10781 + 59440 + 24870 + 4909) == 0 && this.isRaining() && this.isThundering()) {
                    this.updateLCG = this.updateLCG * "   ".length() + (818851673 + 493324496 - 454611668 + 156339722);
                    final int n3 = this.updateLCG >> "  ".length();
                    final BlockPos adjustPosToNearbyEntity = this.adjustPosToNearbyEntity(new BlockPos(n + (n3 & (0x27 ^ 0x28)), "".length(), n2 + (n3 >> (0xC8 ^ 0xC0) & (0xA4 ^ 0xAB))));
                    if (this.canLightningStrike(adjustPosToNearbyEntity)) {
                        this.addWeatherEffect(new EntityLightningBolt(this, adjustPosToNearbyEntity.getX(), adjustPosToNearbyEntity.getY(), adjustPosToNearbyEntity.getZ()));
                    }
                }
                this.theProfiler.endStartSection(WorldServer.I[0x82 ^ 0x8D]);
                if (this.rand.nextInt(0x68 ^ 0x78) == 0) {
                    this.updateLCG = this.updateLCG * "   ".length() + (61133720 + 410268298 - 39074703 + 581576908);
                    final int n4 = this.updateLCG >> "  ".length();
                    final BlockPos precipitationHeight = this.getPrecipitationHeight(new BlockPos(n + (n4 & (0xCD ^ 0xC2)), "".length(), n2 + (n4 >> (0x58 ^ 0x50) & (0x29 ^ 0x26))));
                    final BlockPos down = precipitationHeight.down();
                    if (this.canBlockFreezeNoWater(down)) {
                        this.setBlockState(down, Blocks.ice.getDefaultState());
                    }
                    if (this.isRaining() && this.canSnowAt(precipitationHeight, " ".length() != 0)) {
                        this.setBlockState(precipitationHeight, Blocks.snow_layer.getDefaultState());
                    }
                    if (this.isRaining() && this.getBiomeGenForCoords(down).canSpawnLightningBolt()) {
                        this.getBlockState(down).getBlock().fillWithRain(this, down);
                    }
                }
                this.theProfiler.endStartSection(WorldServer.I[0xA7 ^ 0xB7]);
                final int int1 = this.getGameRules().getInt(WorldServer.I[0x4C ^ 0x5D]);
                if (int1 > 0) {
                    final ExtendedBlockStorage[] blockStorageArray;
                    final int length3 = (blockStorageArray = chunkFromChunkCoords.getBlockStorageArray()).length;
                    int i = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                    while (i < length3) {
                        final ExtendedBlockStorage extendedBlockStorage = blockStorageArray[i];
                        if (extendedBlockStorage != null && extendedBlockStorage.getNeedsRandomTick()) {
                            int j = "".length();
                            "".length();
                            if (3 < 3) {
                                throw null;
                            }
                            while (j < int1) {
                                this.updateLCG = this.updateLCG * "   ".length() + (115409327 + 191483485 + 659042244 + 47969167);
                                final int n5 = this.updateLCG >> "  ".length();
                                final int n6 = n5 & (0xBD ^ 0xB2);
                                final int n7 = n5 >> (0x47 ^ 0x4F) & (0x5 ^ 0xA);
                                final int n8 = n5 >> (0x11 ^ 0x1) & (0x4 ^ 0xB);
                                ++length2;
                                final IBlockState value = extendedBlockStorage.get(n6, n8, n7);
                                final Block block = value.getBlock();
                                if (block.getTickRandomly()) {
                                    ++length;
                                    block.randomTick(this, new BlockPos(n6 + n, n8 + extendedBlockStorage.getYLocation(), n7 + n2), value, this.rand);
                                }
                                ++j;
                            }
                        }
                        ++i;
                    }
                }
                this.theProfiler.endSection();
            }
        }
    }
    
    public void saveAllChunks(final boolean b, final IProgressUpdate progressUpdate) throws MinecraftException {
        if (this.chunkProvider.canSave()) {
            if (progressUpdate != null) {
                progressUpdate.displaySavingString(WorldServer.I[0x5D ^ 0x46]);
            }
            this.saveLevel();
            if (progressUpdate != null) {
                progressUpdate.displayLoadingString(WorldServer.I[0x13 ^ 0xF]);
            }
            this.chunkProvider.saveChunks(b, progressUpdate);
            final Iterator<Chunk> iterator = Lists.newArrayList((Iterable)this.theChunkProviderServer.func_152380_a()).iterator();
            "".length();
            if (false) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Chunk chunk = iterator.next();
                if (chunk != null && !this.thePlayerManager.hasPlayerInstance(chunk.xPosition, chunk.zPosition)) {
                    this.theChunkProviderServer.dropChunk(chunk.xPosition, chunk.zPosition);
                }
            }
        }
    }
    
    @Override
    public boolean isBlockTickPending(final BlockPos blockPos, final Block block) {
        return this.pendingTickListEntriesThisTick.contains(new NextTickListEntry(blockPos, block));
    }
    
    @Override
    public List<NextTickListEntry> func_175712_a(final StructureBoundingBox structureBoundingBox, final boolean b) {
        List<NextTickListEntry> arrayList = null;
        int i = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < "  ".length()) {
            Iterator<NextTickListEntry> iterator;
            if (i == 0) {
                iterator = this.pendingTickListEntriesTreeSet.iterator();
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else {
                iterator = this.pendingTickListEntriesThisTick.iterator();
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            while (iterator.hasNext()) {
                final NextTickListEntry nextTickListEntry = iterator.next();
                final BlockPos position = nextTickListEntry.position;
                if (position.getX() >= structureBoundingBox.minX && position.getX() < structureBoundingBox.maxX && position.getZ() >= structureBoundingBox.minZ && position.getZ() < structureBoundingBox.maxZ) {
                    if (b) {
                        this.pendingTickListEntriesHashSet.remove(nextTickListEntry);
                        iterator.remove();
                    }
                    if (arrayList == null) {
                        arrayList = (List<NextTickListEntry>)Lists.newArrayList();
                    }
                    arrayList.add(nextTickListEntry);
                }
            }
            ++i;
        }
        return arrayList;
    }
    
    public Teleporter getDefaultTeleporter() {
        return this.worldTeleporter;
    }
    
    protected void wakeAllPlayers() {
        this.allPlayersSleeping = ("".length() != 0);
        final Iterator<EntityPlayer> iterator = this.playerEntities.iterator();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayer entityPlayer = iterator.next();
            if (entityPlayer.isPlayerSleeping()) {
                entityPlayer.wakeUpPlayer("".length() != 0, "".length() != 0, " ".length() != 0);
            }
        }
        this.resetRainAndThunder();
    }
    
    private static void I() {
        (I = new String[0x9 ^ 0x14])["".length()] = I("\u00035\t\b0\u00129\u0007\b1", "pVfzU");
        WorldServer.I[" ".length()] = I("1\u0014\t!0 \u0018\u0007!1", "BwfSU");
        WorldServer.I["  ".length()] = I("\f$\u00102 \u0004\"3;-+27?<", "hKTSY");
        WorldServer.I["   ".length()] = I("48#\u001838 /.1", "YWAKC");
        WorldServer.I[0x44 ^ 0x40] = I("'7\")-\u0010(\u000e1!*6\b", "CXoFO");
        WorldServer.I[0x3F ^ 0x3A] = I(".\t- \t\u001e\u000e-<\u0001(", "MaXNb");
        WorldServer.I[0xB6 ^ 0xB0] = I(">\u0006\r\t\f6\u0000.\u0000\u0001\u0019\u0010*\u0004\u0010", "ZiIhu");
        WorldServer.I[0x1E ^ 0x19] = I("\u0002\b\u0007(3\u0013\u000f\u0000*\r\u0011", "vadCc");
        WorldServer.I[0xF ^ 0x7] = I("\u001e9);\u0005\u0006?);4", "jPJPG");
        WorldServer.I[0x4D ^ 0x44] = I("\u0019 ='97)8", "zHHIR");
        WorldServer.I[0x2E ^ 0x24] = I("\u0012 \u001c+\u000b\u0003,", "dIpGj");
        WorldServer.I[0xCA ^ 0xC1] = I("\n\u0002&\u00114\u0016+;\u00176\u001f\u001f", "zmTeU");
        WorldServer.I[0x52 ^ 0x5E] = I("\u0016/\u001c\u0001\u0002\u0004$\u0003", "qJhBj");
        WorldServer.I[0x98 ^ 0x95] = I(".3\b>\u000e2/\u0005>", "ZZkUM");
        WorldServer.I[0x13 ^ 0x1D] = I("?!4?\t.;", "KIAQm");
        WorldServer.I[0x99 ^ 0x96] = I("\u000e4-;9\u0003$&5 ", "gWHZW");
        WorldServer.I[0x8A ^ 0x9A] = I("%\u001d320=\u001b32\u0001", "QtPYr");
        WorldServer.I[0xAF ^ 0xBE] = I("\u0007/9*\u0003\u0018\u001a>-\u0007&>2+\b", "uNWNl");
        WorldServer.I[0x2F ^ 0x3D] = I("=\u00060\u001c?\f\u0017'#\u0018\n\u0004s\u001b\u0018\u001a\u001bs\u0018\u0004\u001dO<\u0011Q\u001a\u0016=\u0014\u0019", "ioSwq");
        WorldServer.I[0xB1 ^ 0xA2] = I("\u0013\u00147\u0004<\u0019\u00165", "pxReR");
        WorldServer.I[0x6D ^ 0x79] = I(",\u0000\u0019\u0019:6\u000e", "XizrS");
        WorldServer.I[0x26 ^ 0x33] = I("5?&=?\u0004.*6o\u0007/,4*P3,;$\u0019)\"x.P%)7,\u001b", "pGEXO");
        WorldServer.I[0x42 ^ 0x54] = I("\u0018& \u0005\u0005z(*\u000f\u0000=j;\u000f\r1/+", "ZJOfn");
        WorldServer.I[0xAF ^ 0xB8] = I("\u00151'\u0004?$ +\u000fo9'-\u0015&1%-\u001b&>.d\r*&,(", "PIDaO");
        WorldServer.I[0x38 ^ 0x20] = I("\u0006\u00061\u000b\u0018\u000e\u0000\u0012\u0002\u0015!\u0010\u0016\u0006\u0004", "biuja");
        WorldServer.I[0x98 ^ 0x81] = I("\u000e9 #\u001d", "hXLPx");
        WorldServer.I[0x3C ^ 0x26] = I("\u0018\u000f\b.\u0015(A\u001d#Y+\b\u0007(Y>\u0011\b;\u0017m\u0003\u0000#\u0014(", "MaiLy");
        WorldServer.I[0xA5 ^ 0xBE] = I("\u00153\u001d\u0003\u0005!r\u0007\u000f\u001d#>", "FRkjk");
        WorldServer.I[0x25 ^ 0x39] = I("\"*/\u000f\u0003\u0016k:\u000e\u0018\u001f *", "qKYfm");
    }
    
    private boolean canSpawnAnimals() {
        return this.mcServer.getCanSpawnAnimals();
    }
    
    @Override
    public boolean isCallingFromMinecraftThread() {
        return this.mcServer.isCallingFromMinecraftThread();
    }
    
    private boolean canSpawnNPCs() {
        return this.mcServer.getCanSpawnNPCs();
    }
    
    @Override
    public ListenableFuture<Object> addScheduledTask(final Runnable runnable) {
        return this.mcServer.addScheduledTask(runnable);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public World init() {
        this.mapStorage = new MapStorage(this.saveHandler);
        final String fileNameForProvider = VillageCollection.fileNameForProvider(this.provider);
        final VillageCollection villageCollectionObj = (VillageCollection)this.mapStorage.loadData(VillageCollection.class, fileNameForProvider);
        if (villageCollectionObj == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData(fileNameForProvider, this.villageCollectionObj);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            (this.villageCollectionObj = villageCollectionObj).setWorldsForAll(this);
        }
        this.worldScoreboard = new ServerScoreboard(this.mcServer);
        ScoreboardSaveData scoreboardSaveData = (ScoreboardSaveData)this.mapStorage.loadData(ScoreboardSaveData.class, WorldServer.I["".length()]);
        if (scoreboardSaveData == null) {
            scoreboardSaveData = new ScoreboardSaveData();
            this.mapStorage.setData(WorldServer.I[" ".length()], scoreboardSaveData);
        }
        scoreboardSaveData.setScoreboard(this.worldScoreboard);
        ((ServerScoreboard)this.worldScoreboard).func_96547_a(scoreboardSaveData);
        this.getWorldBorder().setCenter(this.worldInfo.getBorderCenterX(), this.worldInfo.getBorderCenterZ());
        this.getWorldBorder().setDamageAmount(this.worldInfo.getBorderDamagePerBlock());
        this.getWorldBorder().setDamageBuffer(this.worldInfo.getBorderSafeZone());
        this.getWorldBorder().setWarningDistance(this.worldInfo.getBorderWarningDistance());
        this.getWorldBorder().setWarningTime(this.worldInfo.getBorderWarningTime());
        if (this.worldInfo.getBorderLerpTime() > 0L) {
            this.getWorldBorder().setTransition(this.worldInfo.getBorderSize(), this.worldInfo.getBorderLerpTarget(), this.worldInfo.getBorderLerpTime());
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else {
            this.getWorldBorder().setTransition(this.worldInfo.getBorderSize());
        }
        return this;
    }
    
    public BlockPos getSpawnCoordinate() {
        return this.provider.getSpawnCoordinate();
    }
    
    public List<TileEntity> getTileEntitiesIn(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final ArrayList arrayList = Lists.newArrayList();
        int i = "".length();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (i < this.loadedTileEntityList.size()) {
            final TileEntity tileEntity = this.loadedTileEntityList.get(i);
            final BlockPos pos = tileEntity.getPos();
            if (pos.getX() >= n && pos.getY() >= n2 && pos.getZ() >= n3 && pos.getX() < n4 && pos.getY() < n5 && pos.getZ() < n6) {
                arrayList.add(tileEntity);
            }
            ++i;
        }
        return (List<TileEntity>)arrayList;
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.getWorldInfo().isHardcoreModeEnabled() && this.getDifficulty() != EnumDifficulty.HARD) {
            this.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
        }
        this.provider.getWorldChunkManager().cleanupCache();
        if (this.areAllPlayersAsleep()) {
            if (this.getGameRules().getBoolean(WorldServer.I["  ".length()])) {
                final long n = this.worldInfo.getWorldTime() + 24000L;
                this.worldInfo.setWorldTime(n - n % 24000L);
            }
            this.wakeAllPlayers();
        }
        this.theProfiler.startSection(WorldServer.I["   ".length()]);
        if (this.getGameRules().getBoolean(WorldServer.I[0xA5 ^ 0xA1]) && this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD) {
            final SpawnerAnimals mobSpawner = this.mobSpawner;
            final boolean spawnHostileMobs = this.spawnHostileMobs;
            final boolean spawnPeacefulMobs = this.spawnPeacefulMobs;
            int n2;
            if (this.worldInfo.getWorldTotalTime() % 400L == 0L) {
                n2 = " ".length();
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            mobSpawner.findChunksForSpawning(this, spawnHostileMobs, spawnPeacefulMobs, n2 != 0);
        }
        this.theProfiler.endStartSection(WorldServer.I[0xD ^ 0x8]);
        this.chunkProvider.unloadQueuedChunks();
        final int calculateSkylightSubtracted = this.calculateSkylightSubtracted(1.0f);
        if (calculateSkylightSubtracted != this.getSkylightSubtracted()) {
            this.setSkylightSubtracted(calculateSkylightSubtracted);
        }
        this.worldInfo.setWorldTotalTime(this.worldInfo.getWorldTotalTime() + 1L);
        if (this.getGameRules().getBoolean(WorldServer.I[0x7F ^ 0x79])) {
            this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
        }
        this.theProfiler.endStartSection(WorldServer.I[0x3F ^ 0x38]);
        this.tickUpdates("".length() != 0);
        this.theProfiler.endStartSection(WorldServer.I[0xA0 ^ 0xA8]);
        this.updateBlocks();
        this.theProfiler.endStartSection(WorldServer.I[0xCF ^ 0xC6]);
        this.thePlayerManager.updatePlayerInstances();
        this.theProfiler.endStartSection(WorldServer.I[0x13 ^ 0x19]);
        this.villageCollectionObj.tick();
        this.villageSiege.tick();
        this.theProfiler.endStartSection(WorldServer.I[0x83 ^ 0x88]);
        this.worldTeleporter.removeStalePortalLocations(this.getTotalWorldTime());
        this.theProfiler.endSection();
        this.sendQueuedBlockEvents();
    }
    
    @Override
    public boolean addWeatherEffect(final Entity entity) {
        if (super.addWeatherEffect(entity)) {
            this.mcServer.getConfigurationManager().sendToAllNear(entity.posX, entity.posY, entity.posZ, 512.0, this.provider.getDimensionId(), new S2CPacketSpawnGlobalEntity(entity));
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected BlockPos adjustPosToNearbyEntity(final BlockPos blockPos) {
        final BlockPos precipitationHeight = this.getPrecipitationHeight(blockPos);
        final List<Entity> entitiesWithinAABB = this.getEntitiesWithinAABB((Class<? extends Entity>)EntityLivingBase.class, new AxisAlignedBB(precipitationHeight, new BlockPos(precipitationHeight.getX(), this.getHeight(), precipitationHeight.getZ())).expand(3.0, 3.0, 3.0), (com.google.common.base.Predicate<? super Entity>)new Predicate<EntityLivingBase>(this) {
            final WorldServer this$0;
            
            public boolean apply(final EntityLivingBase entityLivingBase) {
                if (entityLivingBase != null && entityLivingBase.isEntityAlive() && this.this$0.canSeeSky(entityLivingBase.getPosition())) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final Object o) {
                return this.apply((EntityLivingBase)o);
            }
        });
        BlockPos position;
        if (!entitiesWithinAABB.isEmpty()) {
            position = entitiesWithinAABB.get(this.rand.nextInt(entitiesWithinAABB.size())).getPosition();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            position = precipitationHeight;
        }
        return position;
    }
    
    public boolean canCreatureTypeSpawnHere(final EnumCreatureType enumCreatureType, final BiomeGenBase.SpawnListEntry spawnListEntry, final BlockPos blockPos) {
        final List<BiomeGenBase.SpawnListEntry> possibleCreatures = this.getChunkProvider().getPossibleCreatures(enumCreatureType, blockPos);
        int n;
        if (possibleCreatures != null && !possibleCreatures.isEmpty()) {
            n = (possibleCreatures.contains(spawnListEntry) ? 1 : 0);
            "".length();
            if (0 == 3) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    protected void onEntityAdded(final Entity entity) {
        super.onEntityAdded(entity);
        this.entitiesById.addKey(entity.getEntityId(), entity);
        this.entitiesByUuid.put(entity.getUniqueID(), entity);
        final Entity[] parts = entity.getParts();
        if (parts != null) {
            int i = "".length();
            "".length();
            if (2 == 0) {
                throw null;
            }
            while (i < parts.length) {
                this.entitiesById.addKey(parts[i].getEntityId(), parts[i]);
                ++i;
            }
        }
    }
    
    @Override
    public void initialize(final WorldSettings worldSettings) {
        if (!this.worldInfo.isInitialized()) {
            try {
                this.createSpawnPosition(worldSettings);
                if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
                    this.setDebugWorldSettings();
                }
                super.initialize(worldSettings);
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            catch (Throwable t) {
                final CrashReport crashReport = CrashReport.makeCrashReport(t, WorldServer.I[0x19 ^ 0xE]);
                try {
                    this.addWorldInfoToCrashReport(crashReport);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                catch (Throwable t2) {}
                throw new ReportedException(crashReport);
            }
            this.worldInfo.setServerInitialized(" ".length() != 0);
        }
    }
    
    static class ServerBlockEventList extends ArrayList<BlockEventData>
    {
        ServerBlockEventList(final ServerBlockEventList list) {
            this();
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private ServerBlockEventList() {
        }
    }
}
