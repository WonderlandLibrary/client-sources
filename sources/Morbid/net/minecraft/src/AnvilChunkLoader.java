package net.minecraft.src;

import java.io.*;
import java.util.*;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO
{
    private List chunksToRemove;
    private Set pendingAnvilChunksCoordinates;
    private Object syncLockObject;
    private final File chunkSaveLocation;
    
    public AnvilChunkLoader(final File par1File) {
        this.chunksToRemove = new ArrayList();
        this.pendingAnvilChunksCoordinates = new HashSet();
        this.syncLockObject = new Object();
        this.chunkSaveLocation = par1File;
    }
    
    @Override
    public Chunk loadChunk(final World par1World, final int par2, final int par3) throws IOException {
        NBTTagCompound var4 = null;
        final ChunkCoordIntPair var5 = new ChunkCoordIntPair(par2, par3);
        final Object var6 = this.syncLockObject;
        synchronized (this.syncLockObject) {
            if (this.pendingAnvilChunksCoordinates.contains(var5)) {
                for (int var7 = 0; var7 < this.chunksToRemove.size(); ++var7) {
                    if (this.chunksToRemove.get(var7).chunkCoordinate.equals(var5)) {
                        var4 = this.chunksToRemove.get(var7).nbtTags;
                        break;
                    }
                }
            }
        }
        // monitorexit(this.syncLockObject)
        if (var4 == null) {
            final DataInputStream var8 = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, par2, par3);
            if (var8 == null) {
                return null;
            }
            var4 = CompressedStreamTools.read(var8);
        }
        return this.checkedReadChunkFromNBT(par1World, par2, par3, var4);
    }
    
    protected Chunk checkedReadChunkFromNBT(final World par1World, final int par2, final int par3, final NBTTagCompound par4NBTTagCompound) {
        if (!par4NBTTagCompound.hasKey("Level")) {
            par1World.getWorldLogAgent().logSevere("Chunk file at " + par2 + "," + par3 + " is missing level data, skipping");
            return null;
        }
        if (!par4NBTTagCompound.getCompoundTag("Level").hasKey("Sections")) {
            par1World.getWorldLogAgent().logSevere("Chunk file at " + par2 + "," + par3 + " is missing block data, skipping");
            return null;
        }
        Chunk var5 = this.readChunkFromNBT(par1World, par4NBTTagCompound.getCompoundTag("Level"));
        if (!var5.isAtLocation(par2, par3)) {
            par1World.getWorldLogAgent().logSevere("Chunk file at " + par2 + "," + par3 + " is in the wrong location; relocating. (Expected " + par2 + ", " + par3 + ", got " + var5.xPosition + ", " + var5.zPosition + ")");
            par4NBTTagCompound.setInteger("xPos", par2);
            par4NBTTagCompound.setInteger("zPos", par3);
            var5 = this.readChunkFromNBT(par1World, par4NBTTagCompound.getCompoundTag("Level"));
        }
        return var5;
    }
    
    @Override
    public void saveChunk(final World par1World, final Chunk par2Chunk) throws MinecraftException, IOException {
        par1World.checkSessionLock();
        try {
            final NBTTagCompound var3 = new NBTTagCompound();
            final NBTTagCompound var4 = new NBTTagCompound();
            var3.setTag("Level", var4);
            this.writeChunkToNBT(par2Chunk, par1World, var4);
            this.addChunkToPending(par2Chunk.getChunkCoordIntPair(), var3);
        }
        catch (Exception var5) {
            var5.printStackTrace();
        }
    }
    
    protected void addChunkToPending(final ChunkCoordIntPair par1ChunkCoordIntPair, final NBTTagCompound par2NBTTagCompound) {
        final Object var3 = this.syncLockObject;
        synchronized (this.syncLockObject) {
            if (this.pendingAnvilChunksCoordinates.contains(par1ChunkCoordIntPair)) {
                for (int var4 = 0; var4 < this.chunksToRemove.size(); ++var4) {
                    if (this.chunksToRemove.get(var4).chunkCoordinate.equals(par1ChunkCoordIntPair)) {
                        this.chunksToRemove.set(var4, new AnvilChunkLoaderPending(par1ChunkCoordIntPair, par2NBTTagCompound));
                        // monitorexit(this.syncLockObject)
                        return;
                    }
                }
            }
            this.chunksToRemove.add(new AnvilChunkLoaderPending(par1ChunkCoordIntPair, par2NBTTagCompound));
            this.pendingAnvilChunksCoordinates.add(par1ChunkCoordIntPair);
            ThreadedFileIOBase.threadedIOInstance.queueIO(this);
        }
        // monitorexit(this.syncLockObject)
    }
    
    @Override
    public boolean writeNextIO() {
        AnvilChunkLoaderPending var1 = null;
        final Object var2 = this.syncLockObject;
        synchronized (this.syncLockObject) {
            if (this.chunksToRemove.isEmpty()) {
                // monitorexit(this.syncLockObject)
                return false;
            }
            var1 = this.chunksToRemove.remove(0);
            this.pendingAnvilChunksCoordinates.remove(var1.chunkCoordinate);
        }
        // monitorexit(this.syncLockObject)
        if (var1 != null) {
            try {
                this.writeChunkNBTTags(var1);
            }
            catch (Exception var3) {
                var3.printStackTrace();
            }
        }
        return true;
    }
    
    private void writeChunkNBTTags(final AnvilChunkLoaderPending par1AnvilChunkLoaderPending) throws IOException {
        final DataOutputStream var2 = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, par1AnvilChunkLoaderPending.chunkCoordinate.chunkXPos, par1AnvilChunkLoaderPending.chunkCoordinate.chunkZPos);
        CompressedStreamTools.write(par1AnvilChunkLoaderPending.nbtTags, var2);
        var2.close();
    }
    
    @Override
    public void saveExtraChunkData(final World par1World, final Chunk par2Chunk) {
    }
    
    @Override
    public void chunkTick() {
    }
    
    @Override
    public void saveExtraData() {
        while (this.writeNextIO()) {}
    }
    
    private void writeChunkToNBT(final Chunk par1Chunk, final World par2World, final NBTTagCompound par3NBTTagCompound) {
        par3NBTTagCompound.setInteger("xPos", par1Chunk.xPosition);
        par3NBTTagCompound.setInteger("zPos", par1Chunk.zPosition);
        par3NBTTagCompound.setLong("LastUpdate", par2World.getTotalWorldTime());
        par3NBTTagCompound.setIntArray("HeightMap", par1Chunk.heightMap);
        par3NBTTagCompound.setBoolean("TerrainPopulated", par1Chunk.isTerrainPopulated);
        final ExtendedBlockStorage[] var4 = par1Chunk.getBlockStorageArray();
        final NBTTagList var5 = new NBTTagList("Sections");
        final boolean var6 = !par2World.provider.hasNoSky;
        final ExtendedBlockStorage[] var7 = var4;
        for (int var8 = var4.length, var9 = 0; var9 < var8; ++var9) {
            final ExtendedBlockStorage var10 = var7[var9];
            if (var10 != null) {
                final NBTTagCompound var11 = new NBTTagCompound();
                var11.setByte("Y", (byte)(var10.getYLocation() >> 4 & 0xFF));
                var11.setByteArray("Blocks", var10.getBlockLSBArray());
                if (var10.getBlockMSBArray() != null) {
                    var11.setByteArray("Add", var10.getBlockMSBArray().data);
                }
                var11.setByteArray("Data", var10.getMetadataArray().data);
                var11.setByteArray("BlockLight", var10.getBlocklightArray().data);
                if (var6) {
                    var11.setByteArray("SkyLight", var10.getSkylightArray().data);
                }
                else {
                    var11.setByteArray("SkyLight", new byte[var10.getBlocklightArray().data.length]);
                }
                var5.appendTag(var11);
            }
        }
        par3NBTTagCompound.setTag("Sections", var5);
        par3NBTTagCompound.setByteArray("Biomes", par1Chunk.getBiomeArray());
        par1Chunk.hasEntities = false;
        final NBTTagList var12 = new NBTTagList();
        for (int var8 = 0; var8 < par1Chunk.entityLists.length; ++var8) {
            for (final Entity var14 : par1Chunk.entityLists[var8]) {
                final NBTTagCompound var11 = new NBTTagCompound();
                if (var14.addEntityID(var11)) {
                    par1Chunk.hasEntities = true;
                    var12.appendTag(var11);
                }
            }
        }
        par3NBTTagCompound.setTag("Entities", var12);
        final NBTTagList var15 = new NBTTagList();
        for (final TileEntity var16 : par1Chunk.chunkTileEntityMap.values()) {
            final NBTTagCompound var11 = new NBTTagCompound();
            var16.writeToNBT(var11);
            var15.appendTag(var11);
        }
        par3NBTTagCompound.setTag("TileEntities", var15);
        final List var17 = par2World.getPendingBlockUpdates(par1Chunk, false);
        if (var17 != null) {
            final long var18 = par2World.getTotalWorldTime();
            final NBTTagList var19 = new NBTTagList();
            for (final NextTickListEntry var21 : var17) {
                final NBTTagCompound var22 = new NBTTagCompound();
                var22.setInteger("i", var21.blockID);
                var22.setInteger("x", var21.xCoord);
                var22.setInteger("y", var21.yCoord);
                var22.setInteger("z", var21.zCoord);
                var22.setInteger("t", (int)(var21.scheduledTime - var18));
                var22.setInteger("p", var21.field_82754_f);
                var19.appendTag(var22);
            }
            par3NBTTagCompound.setTag("TileTicks", var19);
        }
    }
    
    private Chunk readChunkFromNBT(final World par1World, final NBTTagCompound par2NBTTagCompound) {
        final int var3 = par2NBTTagCompound.getInteger("xPos");
        final int var4 = par2NBTTagCompound.getInteger("zPos");
        final Chunk var5 = new Chunk(par1World, var3, var4);
        var5.heightMap = par2NBTTagCompound.getIntArray("HeightMap");
        var5.isTerrainPopulated = par2NBTTagCompound.getBoolean("TerrainPopulated");
        final NBTTagList var6 = par2NBTTagCompound.getTagList("Sections");
        final byte var7 = 16;
        final ExtendedBlockStorage[] var8 = new ExtendedBlockStorage[var7];
        final boolean var9 = !par1World.provider.hasNoSky;
        for (int var10 = 0; var10 < var6.tagCount(); ++var10) {
            final NBTTagCompound var11 = (NBTTagCompound)var6.tagAt(var10);
            final byte var12 = var11.getByte("Y");
            final ExtendedBlockStorage var13 = new ExtendedBlockStorage(var12 << 4, var9);
            var13.setBlockLSBArray(var11.getByteArray("Blocks"));
            if (var11.hasKey("Add")) {
                var13.setBlockMSBArray(new NibbleArray(var11.getByteArray("Add"), 4));
            }
            var13.setBlockMetadataArray(new NibbleArray(var11.getByteArray("Data"), 4));
            var13.setBlocklightArray(new NibbleArray(var11.getByteArray("BlockLight"), 4));
            if (var9) {
                var13.setSkylightArray(new NibbleArray(var11.getByteArray("SkyLight"), 4));
            }
            var13.removeInvalidBlocks();
            var8[var12] = var13;
        }
        var5.setStorageArrays(var8);
        if (par2NBTTagCompound.hasKey("Biomes")) {
            var5.setBiomeArray(par2NBTTagCompound.getByteArray("Biomes"));
        }
        final NBTTagList var14 = par2NBTTagCompound.getTagList("Entities");
        if (var14 != null) {
            for (int var15 = 0; var15 < var14.tagCount(); ++var15) {
                final NBTTagCompound var16 = (NBTTagCompound)var14.tagAt(var15);
                final Entity var17 = EntityList.createEntityFromNBT(var16, par1World);
                var5.hasEntities = true;
                if (var17 != null) {
                    var5.addEntity(var17);
                    Entity var18 = var17;
                    for (NBTTagCompound var19 = var16; var19.hasKey("Riding"); var19 = var19.getCompoundTag("Riding")) {
                        final Entity var20 = EntityList.createEntityFromNBT(var19.getCompoundTag("Riding"), par1World);
                        if (var20 != null) {
                            var5.addEntity(var20);
                            var18.mountEntity(var20);
                        }
                        var18 = var20;
                    }
                }
            }
        }
        final NBTTagList var21 = par2NBTTagCompound.getTagList("TileEntities");
        if (var21 != null) {
            for (int var22 = 0; var22 < var21.tagCount(); ++var22) {
                final NBTTagCompound var23 = (NBTTagCompound)var21.tagAt(var22);
                final TileEntity var24 = TileEntity.createAndLoadEntity(var23);
                if (var24 != null) {
                    var5.addTileEntity(var24);
                }
            }
        }
        if (par2NBTTagCompound.hasKey("TileTicks")) {
            final NBTTagList var25 = par2NBTTagCompound.getTagList("TileTicks");
            if (var25 != null) {
                for (int var26 = 0; var26 < var25.tagCount(); ++var26) {
                    final NBTTagCompound var27 = (NBTTagCompound)var25.tagAt(var26);
                    par1World.scheduleBlockUpdateFromLoad(var27.getInteger("x"), var27.getInteger("y"), var27.getInteger("z"), var27.getInteger("i"), var27.getInteger("t"), var27.getInteger("p"));
                }
            }
        }
        return var5;
    }
}
