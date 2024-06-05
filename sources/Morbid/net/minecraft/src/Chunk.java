package net.minecraft.src;

import java.util.*;

public class Chunk
{
    public static boolean isLit;
    private ExtendedBlockStorage[] storageArrays;
    private byte[] blockBiomeArray;
    public int[] precipitationHeightMap;
    public boolean[] updateSkylightColumns;
    public boolean isChunkLoaded;
    public World worldObj;
    public int[] heightMap;
    public final int xPosition;
    public final int zPosition;
    private boolean isGapLightingUpdated;
    public Map chunkTileEntityMap;
    public List[] entityLists;
    public boolean isTerrainPopulated;
    public boolean isModified;
    public boolean hasEntities;
    public long lastSaveTime;
    public boolean sendUpdates;
    public int heightMapMinimum;
    private int queuedLightChecks;
    boolean field_76653_p;
    
    public Chunk(final World par1World, final int par2, final int par3) {
        this.storageArrays = new ExtendedBlockStorage[16];
        this.blockBiomeArray = new byte[256];
        this.precipitationHeightMap = new int[256];
        this.updateSkylightColumns = new boolean[256];
        this.isGapLightingUpdated = false;
        this.chunkTileEntityMap = new HashMap();
        this.isTerrainPopulated = false;
        this.isModified = false;
        this.hasEntities = false;
        this.lastSaveTime = 0L;
        this.sendUpdates = false;
        this.heightMapMinimum = 0;
        this.queuedLightChecks = 4096;
        this.field_76653_p = false;
        this.entityLists = new List[16];
        this.worldObj = par1World;
        this.xPosition = par2;
        this.zPosition = par3;
        this.heightMap = new int[256];
        for (int var4 = 0; var4 < this.entityLists.length; ++var4) {
            this.entityLists[var4] = new ArrayList();
        }
        Arrays.fill(this.precipitationHeightMap, -999);
        Arrays.fill(this.blockBiomeArray, (byte)(-1));
    }
    
    public Chunk(final World par1World, final byte[] par2ArrayOfByte, final int par3, final int par4) {
        this(par1World, par3, par4);
        final int var5 = par2ArrayOfByte.length / 256;
        for (int var6 = 0; var6 < 16; ++var6) {
            for (int var7 = 0; var7 < 16; ++var7) {
                for (int var8 = 0; var8 < var5; ++var8) {
                    final byte var9 = par2ArrayOfByte[var6 << 11 | var7 << 7 | var8];
                    if (var9 != 0) {
                        final int var10 = var8 >> 4;
                        if (this.storageArrays[var10] == null) {
                            this.storageArrays[var10] = new ExtendedBlockStorage(var10 << 4, !par1World.provider.hasNoSky);
                        }
                        this.storageArrays[var10].setExtBlockID(var6, var8 & 0xF, var7, var9);
                    }
                }
            }
        }
    }
    
    public boolean isAtLocation(final int par1, final int par2) {
        return par1 == this.xPosition && par2 == this.zPosition;
    }
    
    public int getHeightValue(final int par1, final int par2) {
        return this.heightMap[par2 << 4 | par1];
    }
    
    public int getTopFilledSegment() {
        for (int var1 = this.storageArrays.length - 1; var1 >= 0; --var1) {
            if (this.storageArrays[var1] != null) {
                return this.storageArrays[var1].getYLocation();
            }
        }
        return 0;
    }
    
    public ExtendedBlockStorage[] getBlockStorageArray() {
        return this.storageArrays;
    }
    
    public void generateHeightMap() {
        final int var1 = this.getTopFilledSegment();
        for (int var2 = 0; var2 < 16; ++var2) {
            for (int var3 = 0; var3 < 16; ++var3) {
                this.precipitationHeightMap[var2 + (var3 << 4)] = -999;
                for (int var4 = var1 + 16 - 1; var4 > 0; --var4) {
                    final int var5 = this.getBlockID(var2, var4 - 1, var3);
                    if (Block.lightOpacity[var5] != 0) {
                        this.heightMap[var3 << 4 | var2] = var4;
                        break;
                    }
                }
            }
        }
        this.isModified = true;
    }
    
    public void generateSkylightMap() {
        final int var1 = this.getTopFilledSegment();
        this.heightMapMinimum = Integer.MAX_VALUE;
        for (int var2 = 0; var2 < 16; ++var2) {
            for (int var3 = 0; var3 < 16; ++var3) {
                this.precipitationHeightMap[var2 + (var3 << 4)] = -999;
                int var4 = var1 + 16 - 1;
                while (var4 > 0) {
                    if (this.getBlockLightOpacity(var2, var4 - 1, var3) == 0) {
                        --var4;
                    }
                    else {
                        if ((this.heightMap[var3 << 4 | var2] = var4) < this.heightMapMinimum) {
                            this.heightMapMinimum = var4;
                            break;
                        }
                        break;
                    }
                }
                if (!this.worldObj.provider.hasNoSky) {
                    var4 = 15;
                    int var5 = var1 + 16 - 1;
                    do {
                        var4 -= this.getBlockLightOpacity(var2, var5, var3);
                        if (var4 > 0) {
                            final ExtendedBlockStorage var6 = this.storageArrays[var5 >> 4];
                            if (var6 == null) {
                                continue;
                            }
                            var6.setExtSkylightValue(var2, var5 & 0xF, var3, var4);
                            this.worldObj.markBlockForRenderUpdate((this.xPosition << 4) + var2, var5, (this.zPosition << 4) + var3);
                        }
                    } while (--var5 > 0 && var4 > 0);
                }
            }
        }
        this.isModified = true;
        for (int var2 = 0; var2 < 16; ++var2) {
            for (int var3 = 0; var3 < 16; ++var3) {
                this.propagateSkylightOcclusion(var2, var3);
            }
        }
    }
    
    private void propagateSkylightOcclusion(final int par1, final int par2) {
        this.updateSkylightColumns[par1 + par2 * 16] = true;
        this.isGapLightingUpdated = true;
    }
    
    private void updateSkylight_do() {
        this.worldObj.theProfiler.startSection("recheckGaps");
        if (this.worldObj.doChunksNearChunkExist(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8, 16)) {
            for (int var1 = 0; var1 < 16; ++var1) {
                for (int var2 = 0; var2 < 16; ++var2) {
                    if (this.updateSkylightColumns[var1 + var2 * 16]) {
                        this.updateSkylightColumns[var1 + var2 * 16] = false;
                        final int var3 = this.getHeightValue(var1, var2);
                        final int var4 = this.xPosition * 16 + var1;
                        final int var5 = this.zPosition * 16 + var2;
                        int var6 = this.worldObj.getChunkHeightMapMinimum(var4 - 1, var5);
                        final int var7 = this.worldObj.getChunkHeightMapMinimum(var4 + 1, var5);
                        final int var8 = this.worldObj.getChunkHeightMapMinimum(var4, var5 - 1);
                        final int var9 = this.worldObj.getChunkHeightMapMinimum(var4, var5 + 1);
                        if (var7 < var6) {
                            var6 = var7;
                        }
                        if (var8 < var6) {
                            var6 = var8;
                        }
                        if (var9 < var6) {
                            var6 = var9;
                        }
                        this.checkSkylightNeighborHeight(var4, var5, var6);
                        this.checkSkylightNeighborHeight(var4 - 1, var5, var3);
                        this.checkSkylightNeighborHeight(var4 + 1, var5, var3);
                        this.checkSkylightNeighborHeight(var4, var5 - 1, var3);
                        this.checkSkylightNeighborHeight(var4, var5 + 1, var3);
                    }
                }
            }
            this.isGapLightingUpdated = false;
        }
        this.worldObj.theProfiler.endSection();
    }
    
    private void checkSkylightNeighborHeight(final int par1, final int par2, final int par3) {
        final int var4 = this.worldObj.getHeightValue(par1, par2);
        if (var4 > par3) {
            this.updateSkylightNeighborHeight(par1, par2, par3, var4 + 1);
        }
        else if (var4 < par3) {
            this.updateSkylightNeighborHeight(par1, par2, var4, par3 + 1);
        }
    }
    
    private void updateSkylightNeighborHeight(final int par1, final int par2, final int par3, final int par4) {
        if (par4 > par3 && this.worldObj.doChunksNearChunkExist(par1, 0, par2, 16)) {
            for (int var5 = par3; var5 < par4; ++var5) {
                this.worldObj.updateLightByType(EnumSkyBlock.Sky, par1, var5, par2);
            }
            this.isModified = true;
        }
    }
    
    private void relightBlock(final int par1, final int par2, final int par3) {
        int var5;
        final int var4 = var5 = (this.heightMap[par3 << 4 | par1] & 0xFF);
        if (par2 > var4) {
            var5 = par2;
        }
        while (var5 > 0 && this.getBlockLightOpacity(par1, var5 - 1, par3) == 0) {
            --var5;
        }
        if (var5 != var4) {
            this.worldObj.markBlocksDirtyVertical(par1 + this.xPosition * 16, par3 + this.zPosition * 16, var5, var4);
            this.heightMap[par3 << 4 | par1] = var5;
            final int var6 = this.xPosition * 16 + par1;
            final int var7 = this.zPosition * 16 + par3;
            if (!this.worldObj.provider.hasNoSky) {
                if (var5 < var4) {
                    for (int var8 = var5; var8 < var4; ++var8) {
                        final ExtendedBlockStorage var9 = this.storageArrays[var8 >> 4];
                        if (var9 != null) {
                            var9.setExtSkylightValue(par1, var8 & 0xF, par3, 15);
                            this.worldObj.markBlockForRenderUpdate((this.xPosition << 4) + par1, var8, (this.zPosition << 4) + par3);
                        }
                    }
                }
                else {
                    for (int var8 = var4; var8 < var5; ++var8) {
                        final ExtendedBlockStorage var9 = this.storageArrays[var8 >> 4];
                        if (var9 != null) {
                            var9.setExtSkylightValue(par1, var8 & 0xF, par3, 0);
                            this.worldObj.markBlockForRenderUpdate((this.xPosition << 4) + par1, var8, (this.zPosition << 4) + par3);
                        }
                    }
                }
                int var8 = 15;
                while (var5 > 0 && var8 > 0) {
                    --var5;
                    int var10 = this.getBlockLightOpacity(par1, var5, par3);
                    if (var10 == 0) {
                        var10 = 1;
                    }
                    var8 -= var10;
                    if (var8 < 0) {
                        var8 = 0;
                    }
                    final ExtendedBlockStorage var11 = this.storageArrays[var5 >> 4];
                    if (var11 != null) {
                        var11.setExtSkylightValue(par1, var5 & 0xF, par3, var8);
                    }
                }
            }
            int var8 = this.heightMap[par3 << 4 | par1];
            int var10;
            int var12;
            if ((var12 = var8) < (var10 = var4)) {
                var10 = var8;
                var12 = var4;
            }
            if (var8 < this.heightMapMinimum) {
                this.heightMapMinimum = var8;
            }
            if (!this.worldObj.provider.hasNoSky) {
                this.updateSkylightNeighborHeight(var6 - 1, var7, var10, var12);
                this.updateSkylightNeighborHeight(var6 + 1, var7, var10, var12);
                this.updateSkylightNeighborHeight(var6, var7 - 1, var10, var12);
                this.updateSkylightNeighborHeight(var6, var7 + 1, var10, var12);
                this.updateSkylightNeighborHeight(var6, var7, var10, var12);
            }
            this.isModified = true;
        }
    }
    
    public int getBlockLightOpacity(final int par1, final int par2, final int par3) {
        return Block.lightOpacity[this.getBlockID(par1, par2, par3)];
    }
    
    public int getBlockID(final int par1, final int par2, final int par3) {
        if (par2 >> 4 >= this.storageArrays.length) {
            return 0;
        }
        final ExtendedBlockStorage var4 = this.storageArrays[par2 >> 4];
        return (var4 != null) ? var4.getExtBlockID(par1, par2 & 0xF, par3) : 0;
    }
    
    public int getBlockMetadata(final int par1, final int par2, final int par3) {
        if (par2 >> 4 >= this.storageArrays.length) {
            return 0;
        }
        final ExtendedBlockStorage var4 = this.storageArrays[par2 >> 4];
        return (var4 != null) ? var4.getExtBlockMetadata(par1, par2 & 0xF, par3) : 0;
    }
    
    public boolean setBlockIDWithMetadata(final int par1, final int par2, final int par3, final int par4, final int par5) {
        final int var6 = par3 << 4 | par1;
        if (par2 >= this.precipitationHeightMap[var6] - 1) {
            this.precipitationHeightMap[var6] = -999;
        }
        final int var7 = this.heightMap[var6];
        final int var8 = this.getBlockID(par1, par2, par3);
        final int var9 = this.getBlockMetadata(par1, par2, par3);
        if (var8 == par4 && var9 == par5) {
            return false;
        }
        ExtendedBlockStorage var10 = this.storageArrays[par2 >> 4];
        boolean var11 = false;
        if (var10 == null) {
            if (par4 == 0) {
                return false;
            }
            final ExtendedBlockStorage[] storageArrays = this.storageArrays;
            final int n = par2 >> 4;
            final ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(par2 >> 4 << 4, !this.worldObj.provider.hasNoSky);
            storageArrays[n] = extendedBlockStorage;
            var10 = extendedBlockStorage;
            var11 = (par2 >= var7);
        }
        final int var12 = this.xPosition * 16 + par1;
        final int var13 = this.zPosition * 16 + par3;
        if (var8 != 0 && !this.worldObj.isRemote) {
            Block.blocksList[var8].onSetBlockIDWithMetaData(this.worldObj, var12, par2, var13, var9);
        }
        var10.setExtBlockID(par1, par2 & 0xF, par3, par4);
        if (var8 != 0) {
            if (!this.worldObj.isRemote) {
                Block.blocksList[var8].breakBlock(this.worldObj, var12, par2, var13, var8, var9);
            }
            else if (Block.blocksList[var8] instanceof ITileEntityProvider && var8 != par4) {
                this.worldObj.removeBlockTileEntity(var12, par2, var13);
            }
        }
        if (var10.getExtBlockID(par1, par2 & 0xF, par3) != par4) {
            return false;
        }
        var10.setExtBlockMetadata(par1, par2 & 0xF, par3, par5);
        if (var11) {
            this.generateSkylightMap();
        }
        else {
            if (Block.lightOpacity[par4 & 0xFFF] > 0) {
                if (par2 >= var7) {
                    this.relightBlock(par1, par2 + 1, par3);
                }
            }
            else if (par2 == var7 - 1) {
                this.relightBlock(par1, par2, par3);
            }
            this.propagateSkylightOcclusion(par1, par3);
        }
        if (par4 != 0) {
            if (!this.worldObj.isRemote) {
                Block.blocksList[par4].onBlockAdded(this.worldObj, var12, par2, var13);
            }
            if (Block.blocksList[par4] instanceof ITileEntityProvider) {
                TileEntity var14 = this.getChunkBlockTileEntity(par1, par2, par3);
                if (var14 == null) {
                    var14 = ((ITileEntityProvider)Block.blocksList[par4]).createNewTileEntity(this.worldObj);
                    this.worldObj.setBlockTileEntity(var12, par2, var13, var14);
                }
                if (var14 != null) {
                    var14.updateContainingBlockInfo();
                }
            }
        }
        else if (var8 > 0 && Block.blocksList[var8] instanceof ITileEntityProvider) {
            final TileEntity var14 = this.getChunkBlockTileEntity(par1, par2, par3);
            if (var14 != null) {
                var14.updateContainingBlockInfo();
            }
        }
        return this.isModified = true;
    }
    
    public boolean setBlockMetadata(final int par1, final int par2, final int par3, final int par4) {
        final ExtendedBlockStorage var5 = this.storageArrays[par2 >> 4];
        if (var5 == null) {
            return false;
        }
        final int var6 = var5.getExtBlockMetadata(par1, par2 & 0xF, par3);
        if (var6 == par4) {
            return false;
        }
        this.isModified = true;
        var5.setExtBlockMetadata(par1, par2 & 0xF, par3, par4);
        final int var7 = var5.getExtBlockID(par1, par2 & 0xF, par3);
        if (var7 > 0 && Block.blocksList[var7] instanceof ITileEntityProvider) {
            final TileEntity var8 = this.getChunkBlockTileEntity(par1, par2, par3);
            if (var8 != null) {
                var8.updateContainingBlockInfo();
                var8.blockMetadata = par4;
            }
        }
        return true;
    }
    
    public int getSavedLightValue(final EnumSkyBlock par1EnumSkyBlock, final int par2, final int par3, final int par4) {
        final ExtendedBlockStorage var5 = this.storageArrays[par3 >> 4];
        return (var5 == null) ? (this.canBlockSeeTheSky(par2, par3, par4) ? par1EnumSkyBlock.defaultLightValue : 0) : ((par1EnumSkyBlock == EnumSkyBlock.Sky) ? (this.worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(par2, par3 & 0xF, par4)) : ((par1EnumSkyBlock == EnumSkyBlock.Block) ? var5.getExtBlocklightValue(par2, par3 & 0xF, par4) : par1EnumSkyBlock.defaultLightValue));
    }
    
    public void setLightValue(final EnumSkyBlock par1EnumSkyBlock, final int par2, final int par3, final int par4, final int par5) {
        ExtendedBlockStorage var6 = this.storageArrays[par3 >> 4];
        if (var6 == null) {
            final ExtendedBlockStorage[] storageArrays = this.storageArrays;
            final int n = par3 >> 4;
            final ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(par3 >> 4 << 4, !this.worldObj.provider.hasNoSky);
            storageArrays[n] = extendedBlockStorage;
            var6 = extendedBlockStorage;
            this.generateSkylightMap();
        }
        this.isModified = true;
        if (par1EnumSkyBlock == EnumSkyBlock.Sky) {
            if (!this.worldObj.provider.hasNoSky) {
                var6.setExtSkylightValue(par2, par3 & 0xF, par4, par5);
            }
        }
        else if (par1EnumSkyBlock == EnumSkyBlock.Block) {
            var6.setExtBlocklightValue(par2, par3 & 0xF, par4, par5);
        }
    }
    
    public int getBlockLightValue(final int par1, final int par2, final int par3, final int par4) {
        final ExtendedBlockStorage var5 = this.storageArrays[par2 >> 4];
        if (var5 == null) {
            return (!this.worldObj.provider.hasNoSky && par4 < EnumSkyBlock.Sky.defaultLightValue) ? (EnumSkyBlock.Sky.defaultLightValue - par4) : 0;
        }
        int var6 = this.worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(par1, par2 & 0xF, par3);
        if (var6 > 0) {
            Chunk.isLit = true;
        }
        var6 -= par4;
        final int var7 = var5.getExtBlocklightValue(par1, par2 & 0xF, par3);
        if (var7 > var6) {
            var6 = var7;
        }
        return var6;
    }
    
    public void addEntity(final Entity par1Entity) {
        this.hasEntities = true;
        final int var2 = MathHelper.floor_double(par1Entity.posX / 16.0);
        final int var3 = MathHelper.floor_double(par1Entity.posZ / 16.0);
        if (var2 != this.xPosition || var3 != this.zPosition) {
            this.worldObj.getWorldLogAgent().logSevere("Wrong location! " + par1Entity);
            Thread.dumpStack();
        }
        int var4 = MathHelper.floor_double(par1Entity.posY / 16.0);
        if (var4 < 0) {
            var4 = 0;
        }
        if (var4 >= this.entityLists.length) {
            var4 = this.entityLists.length - 1;
        }
        par1Entity.addedToChunk = true;
        par1Entity.chunkCoordX = this.xPosition;
        par1Entity.chunkCoordY = var4;
        par1Entity.chunkCoordZ = this.zPosition;
        this.entityLists[var4].add(par1Entity);
    }
    
    public void removeEntity(final Entity par1Entity) {
        this.removeEntityAtIndex(par1Entity, par1Entity.chunkCoordY);
    }
    
    public void removeEntityAtIndex(final Entity par1Entity, int par2) {
        if (par2 < 0) {
            par2 = 0;
        }
        if (par2 >= this.entityLists.length) {
            par2 = this.entityLists.length - 1;
        }
        this.entityLists[par2].remove(par1Entity);
    }
    
    public boolean canBlockSeeTheSky(final int par1, final int par2, final int par3) {
        return par2 >= this.heightMap[par3 << 4 | par1];
    }
    
    public TileEntity getChunkBlockTileEntity(final int par1, final int par2, final int par3) {
        final ChunkPosition var4 = new ChunkPosition(par1, par2, par3);
        TileEntity var5 = this.chunkTileEntityMap.get(var4);
        if (var5 == null) {
            final int var6 = this.getBlockID(par1, par2, par3);
            if (var6 <= 0 || !Block.blocksList[var6].hasTileEntity()) {
                return null;
            }
            if (var5 == null) {
                var5 = ((ITileEntityProvider)Block.blocksList[var6]).createNewTileEntity(this.worldObj);
                this.worldObj.setBlockTileEntity(this.xPosition * 16 + par1, par2, this.zPosition * 16 + par3, var5);
            }
            var5 = this.chunkTileEntityMap.get(var4);
        }
        if (var5 != null && var5.isInvalid()) {
            this.chunkTileEntityMap.remove(var4);
            return null;
        }
        return var5;
    }
    
    public void addTileEntity(final TileEntity par1TileEntity) {
        final int var2 = par1TileEntity.xCoord - this.xPosition * 16;
        final int var3 = par1TileEntity.yCoord;
        final int var4 = par1TileEntity.zCoord - this.zPosition * 16;
        this.setChunkBlockTileEntity(var2, var3, var4, par1TileEntity);
        if (this.isChunkLoaded) {
            this.worldObj.loadedTileEntityList.add(par1TileEntity);
        }
    }
    
    public void setChunkBlockTileEntity(final int par1, final int par2, final int par3, final TileEntity par4TileEntity) {
        final ChunkPosition var5 = new ChunkPosition(par1, par2, par3);
        par4TileEntity.setWorldObj(this.worldObj);
        par4TileEntity.xCoord = this.xPosition * 16 + par1;
        par4TileEntity.yCoord = par2;
        par4TileEntity.zCoord = this.zPosition * 16 + par3;
        if (this.getBlockID(par1, par2, par3) != 0 && Block.blocksList[this.getBlockID(par1, par2, par3)] instanceof ITileEntityProvider) {
            if (this.chunkTileEntityMap.containsKey(var5)) {
                this.chunkTileEntityMap.get(var5).invalidate();
            }
            par4TileEntity.validate();
            this.chunkTileEntityMap.put(var5, par4TileEntity);
        }
    }
    
    public void removeChunkBlockTileEntity(final int par1, final int par2, final int par3) {
        final ChunkPosition var4 = new ChunkPosition(par1, par2, par3);
        if (this.isChunkLoaded) {
            final TileEntity var5 = this.chunkTileEntityMap.remove(var4);
            if (var5 != null) {
                var5.invalidate();
            }
        }
    }
    
    public void onChunkLoad() {
        this.isChunkLoaded = true;
        this.worldObj.addTileEntity(this.chunkTileEntityMap.values());
        for (int var1 = 0; var1 < this.entityLists.length; ++var1) {
            this.worldObj.addLoadedEntities(this.entityLists[var1]);
        }
    }
    
    public void onChunkUnload() {
        this.isChunkLoaded = false;
        for (final TileEntity var2 : this.chunkTileEntityMap.values()) {
            this.worldObj.markTileEntityForDespawn(var2);
        }
        for (int var3 = 0; var3 < this.entityLists.length; ++var3) {
            this.worldObj.unloadEntities(this.entityLists[var3]);
        }
    }
    
    public void setChunkModified() {
        this.isModified = true;
    }
    
    public void getEntitiesWithinAABBForEntity(final Entity par1Entity, final AxisAlignedBB par2AxisAlignedBB, final List par3List, final IEntitySelector par4IEntitySelector) {
        int var5 = MathHelper.floor_double((par2AxisAlignedBB.minY - 2.0) / 16.0);
        int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxY + 2.0) / 16.0);
        if (var5 < 0) {
            var5 = 0;
            var6 = Math.max(var5, var6);
        }
        if (var6 >= this.entityLists.length) {
            var6 = this.entityLists.length - 1;
            var5 = Math.min(var5, var6);
        }
        for (int var7 = var5; var7 <= var6; ++var7) {
            final List var8 = this.entityLists[var7];
            for (int var9 = 0; var9 < var8.size(); ++var9) {
                Entity var10 = var8.get(var9);
                if (var10 != par1Entity && var10.boundingBox.intersectsWith(par2AxisAlignedBB) && (par4IEntitySelector == null || par4IEntitySelector.isEntityApplicable(var10))) {
                    par3List.add(var10);
                    final Entity[] var11 = var10.getParts();
                    if (var11 != null) {
                        for (int var12 = 0; var12 < var11.length; ++var12) {
                            var10 = var11[var12];
                            if (var10 != par1Entity && var10.boundingBox.intersectsWith(par2AxisAlignedBB) && (par4IEntitySelector == null || par4IEntitySelector.isEntityApplicable(var10))) {
                                par3List.add(var10);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void getEntitiesOfTypeWithinAAAB(final Class par1Class, final AxisAlignedBB par2AxisAlignedBB, final List par3List, final IEntitySelector par4IEntitySelector) {
        int var5 = MathHelper.floor_double((par2AxisAlignedBB.minY - 2.0) / 16.0);
        int var6 = MathHelper.floor_double((par2AxisAlignedBB.maxY + 2.0) / 16.0);
        if (var5 < 0) {
            var5 = 0;
        }
        else if (var5 >= this.entityLists.length) {
            var5 = this.entityLists.length - 1;
        }
        if (var6 >= this.entityLists.length) {
            var6 = this.entityLists.length - 1;
        }
        else if (var6 < 0) {
            var6 = 0;
        }
        for (int var7 = var5; var7 <= var6; ++var7) {
            final List var8 = this.entityLists[var7];
            for (int var9 = 0; var9 < var8.size(); ++var9) {
                final Entity var10 = var8.get(var9);
                if (par1Class.isAssignableFrom(var10.getClass()) && var10.boundingBox.intersectsWith(par2AxisAlignedBB) && (par4IEntitySelector == null || par4IEntitySelector.isEntityApplicable(var10))) {
                    par3List.add(var10);
                }
            }
        }
    }
    
    public boolean needsSaving(final boolean par1) {
        if (par1) {
            if ((this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime) || this.isModified) {
                return true;
            }
        }
        else if (this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L) {
            return true;
        }
        return this.isModified;
    }
    
    public Random getRandomWithSeed(final long par1) {
        return new Random(this.worldObj.getSeed() + this.xPosition * this.xPosition * 4987142 + this.xPosition * 5947611 + this.zPosition * this.zPosition * 4392871L + this.zPosition * 389711 ^ par1);
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public void populateChunk(final IChunkProvider par1IChunkProvider, final IChunkProvider par2IChunkProvider, final int par3, final int par4) {
        if (!this.isTerrainPopulated && par1IChunkProvider.chunkExists(par3 + 1, par4 + 1) && par1IChunkProvider.chunkExists(par3, par4 + 1) && par1IChunkProvider.chunkExists(par3 + 1, par4)) {
            par1IChunkProvider.populate(par2IChunkProvider, par3, par4);
        }
        if (par1IChunkProvider.chunkExists(par3 - 1, par4) && !par1IChunkProvider.provideChunk(par3 - 1, par4).isTerrainPopulated && par1IChunkProvider.chunkExists(par3 - 1, par4 + 1) && par1IChunkProvider.chunkExists(par3, par4 + 1) && par1IChunkProvider.chunkExists(par3 - 1, par4 + 1)) {
            par1IChunkProvider.populate(par2IChunkProvider, par3 - 1, par4);
        }
        if (par1IChunkProvider.chunkExists(par3, par4 - 1) && !par1IChunkProvider.provideChunk(par3, par4 - 1).isTerrainPopulated && par1IChunkProvider.chunkExists(par3 + 1, par4 - 1) && par1IChunkProvider.chunkExists(par3 + 1, par4 - 1) && par1IChunkProvider.chunkExists(par3 + 1, par4)) {
            par1IChunkProvider.populate(par2IChunkProvider, par3, par4 - 1);
        }
        if (par1IChunkProvider.chunkExists(par3 - 1, par4 - 1) && !par1IChunkProvider.provideChunk(par3 - 1, par4 - 1).isTerrainPopulated && par1IChunkProvider.chunkExists(par3, par4 - 1) && par1IChunkProvider.chunkExists(par3 - 1, par4)) {
            par1IChunkProvider.populate(par2IChunkProvider, par3 - 1, par4 - 1);
        }
    }
    
    public int getPrecipitationHeight(final int par1, final int par2) {
        final int var3 = par1 | par2 << 4;
        int var4 = this.precipitationHeightMap[var3];
        if (var4 == -999) {
            int var5 = this.getTopFilledSegment() + 15;
            var4 = -1;
            while (var5 > 0 && var4 == -1) {
                final int var6 = this.getBlockID(par1, var5, par2);
                final Material var7 = (var6 == 0) ? Material.air : Block.blocksList[var6].blockMaterial;
                if (!var7.blocksMovement() && !var7.isLiquid()) {
                    --var5;
                }
                else {
                    var4 = var5 + 1;
                }
            }
            this.precipitationHeightMap[var3] = var4;
        }
        return var4;
    }
    
    public void updateSkylight() {
        if (this.isGapLightingUpdated && !this.worldObj.provider.hasNoSky) {
            this.updateSkylight_do();
        }
    }
    
    public ChunkCoordIntPair getChunkCoordIntPair() {
        return new ChunkCoordIntPair(this.xPosition, this.zPosition);
    }
    
    public boolean getAreLevelsEmpty(int par1, int par2) {
        if (par1 < 0) {
            par1 = 0;
        }
        if (par2 >= 256) {
            par2 = 255;
        }
        for (int var3 = par1; var3 <= par2; var3 += 16) {
            final ExtendedBlockStorage var4 = this.storageArrays[var3 >> 4];
            if (var4 != null && !var4.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    public void setStorageArrays(final ExtendedBlockStorage[] par1ArrayOfExtendedBlockStorage) {
        this.storageArrays = par1ArrayOfExtendedBlockStorage;
    }
    
    public void fillChunk(final byte[] par1ArrayOfByte, final int par2, final int par3, final boolean par4) {
        int var5 = 0;
        final boolean var6 = !this.worldObj.provider.hasNoSky;
        for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
            if ((par2 & 1 << var7) != 0x0) {
                if (this.storageArrays[var7] == null) {
                    this.storageArrays[var7] = new ExtendedBlockStorage(var7 << 4, var6);
                }
                final byte[] var8 = this.storageArrays[var7].getBlockLSBArray();
                System.arraycopy(par1ArrayOfByte, var5, var8, 0, var8.length);
                var5 += var8.length;
            }
            else if (par4 && this.storageArrays[var7] != null) {
                this.storageArrays[var7] = null;
            }
        }
        for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
            if ((par2 & 1 << var7) != 0x0 && this.storageArrays[var7] != null) {
                final NibbleArray var9 = this.storageArrays[var7].getMetadataArray();
                System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
                var5 += var9.data.length;
            }
        }
        for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
            if ((par2 & 1 << var7) != 0x0 && this.storageArrays[var7] != null) {
                final NibbleArray var9 = this.storageArrays[var7].getBlocklightArray();
                System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
                var5 += var9.data.length;
            }
        }
        if (var6) {
            for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
                if ((par2 & 1 << var7) != 0x0 && this.storageArrays[var7] != null) {
                    final NibbleArray var9 = this.storageArrays[var7].getSkylightArray();
                    System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
                    var5 += var9.data.length;
                }
            }
        }
        for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
            if ((par3 & 1 << var7) != 0x0) {
                if (this.storageArrays[var7] == null) {
                    var5 += 2048;
                }
                else {
                    NibbleArray var9 = this.storageArrays[var7].getBlockMSBArray();
                    if (var9 == null) {
                        var9 = this.storageArrays[var7].createBlockMSBArray();
                    }
                    System.arraycopy(par1ArrayOfByte, var5, var9.data, 0, var9.data.length);
                    var5 += var9.data.length;
                }
            }
            else if (par4 && this.storageArrays[var7] != null && this.storageArrays[var7].getBlockMSBArray() != null) {
                this.storageArrays[var7].clearMSBArray();
            }
        }
        if (par4) {
            System.arraycopy(par1ArrayOfByte, var5, this.blockBiomeArray, 0, this.blockBiomeArray.length);
            final int n = var5 + this.blockBiomeArray.length;
        }
        for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
            if (this.storageArrays[var7] != null && (par2 & 1 << var7) != 0x0) {
                this.storageArrays[var7].removeInvalidBlocks();
            }
        }
        this.generateHeightMap();
        for (final TileEntity var11 : this.chunkTileEntityMap.values()) {
            var11.updateContainingBlockInfo();
        }
    }
    
    public BiomeGenBase getBiomeGenForWorldCoords(final int par1, final int par2, final WorldChunkManager par3WorldChunkManager) {
        int var4 = this.blockBiomeArray[par2 << 4 | par1] & 0xFF;
        if (var4 == 255) {
            final BiomeGenBase var5 = par3WorldChunkManager.getBiomeGenAt((this.xPosition << 4) + par1, (this.zPosition << 4) + par2);
            var4 = var5.biomeID;
            this.blockBiomeArray[par2 << 4 | par1] = (byte)(var4 & 0xFF);
        }
        return (BiomeGenBase.biomeList[var4] == null) ? BiomeGenBase.plains : BiomeGenBase.biomeList[var4];
    }
    
    public byte[] getBiomeArray() {
        return this.blockBiomeArray;
    }
    
    public void setBiomeArray(final byte[] par1ArrayOfByte) {
        this.blockBiomeArray = par1ArrayOfByte;
    }
    
    public void resetRelightChecks() {
        this.queuedLightChecks = 0;
    }
    
    public void enqueueRelightChecks() {
        for (int var1 = 0; var1 < 8; ++var1) {
            if (this.queuedLightChecks >= 4096) {
                return;
            }
            final int var2 = this.queuedLightChecks % 16;
            final int var3 = this.queuedLightChecks / 16 % 16;
            final int var4 = this.queuedLightChecks / 256;
            ++this.queuedLightChecks;
            final int var5 = (this.xPosition << 4) + var3;
            final int var6 = (this.zPosition << 4) + var4;
            for (int var7 = 0; var7 < 16; ++var7) {
                final int var8 = (var2 << 4) + var7;
                if ((this.storageArrays[var2] == null && (var7 == 0 || var7 == 15 || var3 == 0 || var3 == 15 || var4 == 0 || var4 == 15)) || (this.storageArrays[var2] != null && this.storageArrays[var2].getExtBlockID(var3, var7, var4) == 0)) {
                    if (Block.lightValue[this.worldObj.getBlockId(var5, var8 - 1, var6)] > 0) {
                        this.worldObj.updateAllLightTypes(var5, var8 - 1, var6);
                    }
                    if (Block.lightValue[this.worldObj.getBlockId(var5, var8 + 1, var6)] > 0) {
                        this.worldObj.updateAllLightTypes(var5, var8 + 1, var6);
                    }
                    if (Block.lightValue[this.worldObj.getBlockId(var5 - 1, var8, var6)] > 0) {
                        this.worldObj.updateAllLightTypes(var5 - 1, var8, var6);
                    }
                    if (Block.lightValue[this.worldObj.getBlockId(var5 + 1, var8, var6)] > 0) {
                        this.worldObj.updateAllLightTypes(var5 + 1, var8, var6);
                    }
                    if (Block.lightValue[this.worldObj.getBlockId(var5, var8, var6 - 1)] > 0) {
                        this.worldObj.updateAllLightTypes(var5, var8, var6 - 1);
                    }
                    if (Block.lightValue[this.worldObj.getBlockId(var5, var8, var6 + 1)] > 0) {
                        this.worldObj.updateAllLightTypes(var5, var8, var6 + 1);
                    }
                    this.worldObj.updateAllLightTypes(var5, var8, var6);
                }
            }
        }
    }
}
