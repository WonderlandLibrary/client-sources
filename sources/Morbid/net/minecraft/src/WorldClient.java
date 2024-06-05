package net.minecraft.src;

import net.minecraft.client.*;
import java.util.*;
import java.util.concurrent.*;

public class WorldClient extends World
{
    private NetClientHandler sendQueue;
    private ChunkProviderClient clientChunkProvider;
    private IntHashMap entityHashSet;
    private Set entityList;
    private Set entitySpawnQueue;
    private final Minecraft mc;
    private final Set previousActiveChunkSet;
    
    public WorldClient(final NetClientHandler par1NetClientHandler, final WorldSettings par2WorldSettings, final int par3, final int par4, final Profiler par5Profiler, final ILogAgent par6ILogAgent) {
        super(new SaveHandlerMP(), "MpServer", WorldProvider.getProviderForDimension(par3), par2WorldSettings, par5Profiler, par6ILogAgent);
        this.entityHashSet = new IntHashMap();
        this.entityList = new HashSet();
        this.entitySpawnQueue = new HashSet();
        this.mc = Minecraft.getMinecraft();
        this.previousActiveChunkSet = new HashSet();
        this.sendQueue = par1NetClientHandler;
        this.difficultySetting = par4;
        this.setSpawnLocation(8, 64, 8);
        this.mapStorage = par1NetClientHandler.mapStorage;
    }
    
    @Override
    public void tick() {
        super.tick();
        this.func_82738_a(this.getTotalWorldTime() + 1L);
        this.setWorldTime(this.getWorldTime() + 1L);
        this.theProfiler.startSection("reEntryProcessing");
        for (int var1 = 0; var1 < 10 && !this.entitySpawnQueue.isEmpty(); ++var1) {
            final Entity var2 = this.entitySpawnQueue.iterator().next();
            this.entitySpawnQueue.remove(var2);
            if (!this.loadedEntityList.contains(var2)) {
                this.spawnEntityInWorld(var2);
            }
        }
        this.theProfiler.endStartSection("connection");
        this.sendQueue.processReadPackets();
        this.theProfiler.endStartSection("chunkCache");
        this.clientChunkProvider.unloadQueuedChunks();
        this.theProfiler.endStartSection("tiles");
        this.tickBlocksAndAmbiance();
        this.theProfiler.endSection();
    }
    
    public void invalidateBlockReceiveRegion(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
    }
    
    @Override
    protected IChunkProvider createChunkProvider() {
        return this.clientChunkProvider = new ChunkProviderClient(this);
    }
    
    @Override
    protected void tickBlocksAndAmbiance() {
        super.tickBlocksAndAmbiance();
        this.previousActiveChunkSet.retainAll(this.activeChunkSet);
        if (this.previousActiveChunkSet.size() == this.activeChunkSet.size()) {
            this.previousActiveChunkSet.clear();
        }
        int var1 = 0;
        for (final ChunkCoordIntPair var3 : this.activeChunkSet) {
            if (!this.previousActiveChunkSet.contains(var3)) {
                final int var4 = var3.chunkXPos * 16;
                final int var5 = var3.chunkZPos * 16;
                this.theProfiler.startSection("getChunk");
                final Chunk var6 = this.getChunkFromChunkCoords(var3.chunkXPos, var3.chunkZPos);
                this.moodSoundAndLightCheck(var4, var5, var6);
                this.theProfiler.endSection();
                this.previousActiveChunkSet.add(var3);
                if (++var1 >= 10) {
                    return;
                }
                continue;
            }
        }
    }
    
    public void doPreChunk(final int par1, final int par2, final boolean par3) {
        if (par3) {
            this.clientChunkProvider.loadChunk(par1, par2);
        }
        else {
            this.clientChunkProvider.unloadChunk(par1, par2);
        }
        if (!par3) {
            this.markBlockRangeForRenderUpdate(par1 * 16, 0, par2 * 16, par1 * 16 + 15, 256, par2 * 16 + 15);
        }
    }
    
    @Override
    public boolean spawnEntityInWorld(final Entity par1Entity) {
        final boolean var2 = super.spawnEntityInWorld(par1Entity);
        this.entityList.add(par1Entity);
        if (!var2) {
            this.entitySpawnQueue.add(par1Entity);
        }
        return var2;
    }
    
    @Override
    public void removeEntity(final Entity par1Entity) {
        super.removeEntity(par1Entity);
        this.entityList.remove(par1Entity);
    }
    
    @Override
    protected void obtainEntitySkin(final Entity par1Entity) {
        super.obtainEntitySkin(par1Entity);
        if (this.entitySpawnQueue.contains(par1Entity)) {
            this.entitySpawnQueue.remove(par1Entity);
        }
    }
    
    @Override
    protected void releaseEntitySkin(final Entity par1Entity) {
        super.releaseEntitySkin(par1Entity);
        if (this.entityList.contains(par1Entity)) {
            if (par1Entity.isEntityAlive()) {
                this.entitySpawnQueue.add(par1Entity);
            }
            else {
                this.entityList.remove(par1Entity);
            }
        }
    }
    
    public void addEntityToWorld(final int par1, final Entity par2Entity) {
        final Entity var3 = this.getEntityByID(par1);
        if (var3 != null) {
            this.removeEntity(var3);
        }
        this.entityList.add(par2Entity);
        par2Entity.entityId = par1;
        if (!this.spawnEntityInWorld(par2Entity)) {
            this.entitySpawnQueue.add(par2Entity);
        }
        this.entityHashSet.addKey(par1, par2Entity);
    }
    
    @Override
    public Entity getEntityByID(final int par1) {
        return (par1 == Minecraft.thePlayer.entityId) ? Minecraft.thePlayer : ((Entity)this.entityHashSet.lookup(par1));
    }
    
    public Entity removeEntityFromWorld(final int par1) {
        final Entity var2 = (Entity)this.entityHashSet.removeObject(par1);
        if (var2 != null) {
            this.entityList.remove(var2);
            this.removeEntity(var2);
        }
        return var2;
    }
    
    public boolean setBlockAndMetadataAndInvalidate(final int par1, final int par2, final int par3, final int par4, final int par5) {
        this.invalidateBlockReceiveRegion(par1, par2, par3, par1, par2, par3);
        return super.setBlock(par1, par2, par3, par4, par5, 3);
    }
    
    @Override
    public void sendQuittingDisconnectingPacket() {
        this.sendQueue.quitWithPacket(new Packet255KickDisconnect("Quitting"));
    }
    
    @Override
    public IUpdatePlayerListBox func_82735_a(final EntityMinecart par1EntityMinecart) {
        return new SoundUpdaterMinecart(this.mc.sndManager, par1EntityMinecart, Minecraft.thePlayer);
    }
    
    @Override
    protected void updateWeather() {
        if (!this.provider.hasNoSky) {
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
    
    public void doVoidFogParticles(final int par1, final int par2, final int par3) {
        final byte var4 = 16;
        final Random var5 = new Random();
        for (int var6 = 0; var6 < 1000; ++var6) {
            final int var7 = par1 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
            final int var8 = par2 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
            final int var9 = par3 + this.rand.nextInt(var4) - this.rand.nextInt(var4);
            final int var10 = this.getBlockId(var7, var8, var9);
            if (var10 == 0 && this.rand.nextInt(8) > var8 && this.provider.getWorldHasVoidParticles()) {
                this.spawnParticle("depthsuspend", var7 + this.rand.nextFloat(), var8 + this.rand.nextFloat(), var9 + this.rand.nextFloat(), 0.0, 0.0, 0.0);
            }
            else if (var10 > 0) {
                Block.blocksList[var10].randomDisplayTick(this, var7, var8, var9, var5);
            }
        }
    }
    
    public void removeAllEntities() {
        this.loadedEntityList.removeAll(this.unloadedEntityList);
        for (int var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
            final Entity var2 = this.unloadedEntityList.get(var1);
            final int var3 = var2.chunkCoordX;
            final int var4 = var2.chunkCoordZ;
            if (var2.addedToChunk && this.chunkExists(var3, var4)) {
                this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
            }
        }
        for (int var1 = 0; var1 < this.unloadedEntityList.size(); ++var1) {
            this.releaseEntitySkin(this.unloadedEntityList.get(var1));
        }
        this.unloadedEntityList.clear();
        for (int var1 = 0; var1 < this.loadedEntityList.size(); ++var1) {
            final Entity var2 = this.loadedEntityList.get(var1);
            if (var2.ridingEntity != null) {
                if (!var2.ridingEntity.isDead && var2.ridingEntity.riddenByEntity == var2) {
                    continue;
                }
                var2.ridingEntity.riddenByEntity = null;
                var2.ridingEntity = null;
            }
            if (var2.isDead) {
                final int var3 = var2.chunkCoordX;
                final int var4 = var2.chunkCoordZ;
                if (var2.addedToChunk && this.chunkExists(var3, var4)) {
                    this.getChunkFromChunkCoords(var3, var4).removeEntity(var2);
                }
                this.loadedEntityList.remove(var1--);
                this.releaseEntitySkin(var2);
            }
        }
    }
    
    @Override
    public CrashReportCategory addWorldInfoToCrashReport(final CrashReport par1CrashReport) {
        final CrashReportCategory var2 = super.addWorldInfoToCrashReport(par1CrashReport);
        var2.addCrashSectionCallable("Forced entities", new CallableMPL1(this));
        var2.addCrashSectionCallable("Retry entities", new CallableMPL2(this));
        return var2;
    }
    
    @Override
    public void playSound(final double par1, final double par3, final double par5, final String par7Str, final float par8, final float par9, final boolean par10) {
        float var11 = 16.0f;
        if (par8 > 1.0f) {
            var11 *= par8;
        }
        final double var12 = this.mc.renderViewEntity.getDistanceSq(par1, par3, par5);
        if (var12 < var11 * var11) {
            if (par10 && var12 > 100.0) {
                final double var13 = Math.sqrt(var12) / 40.0;
                this.mc.sndManager.func_92070_a(par7Str, (float)par1, (float)par3, (float)par5, par8, par9, (int)Math.round(var13 * 20.0));
            }
            else {
                this.mc.sndManager.playSound(par7Str, (float)par1, (float)par3, (float)par5, par8, par9);
            }
        }
    }
    
    @Override
    public void func_92088_a(final double par1, final double par3, final double par5, final double par7, final double par9, final double par11, final NBTTagCompound par13NBTTagCompound) {
        this.mc.effectRenderer.addEffect(new EntityFireworkStarterFX(this, par1, par3, par5, par7, par9, par11, this.mc.effectRenderer, par13NBTTagCompound));
    }
    
    public void func_96443_a(final Scoreboard par1Scoreboard) {
        this.worldScoreboard = par1Scoreboard;
    }
    
    static Set getEntityList(final WorldClient par0WorldClient) {
        return par0WorldClient.entityList;
    }
    
    static Set getEntitySpawnQueue(final WorldClient par0WorldClient) {
        return par0WorldClient.entitySpawnQueue;
    }
}
