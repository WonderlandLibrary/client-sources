package net.minecraft.world.chunk;

import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.storage.*;
import net.minecraft.block.material.*;
import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import net.minecraft.world.biome.*;
import java.util.*;

public class Chunk
{
    private boolean isGapLightingUpdated;
    private final byte[] blockBiomeArray;
    private final Map<BlockPos, TileEntity> chunkTileEntityMap;
    private boolean isModified;
    private int queuedLightChecks;
    private final World worldObj;
    private long lastSaveTime;
    private final int[] heightMap;
    private final ClassInheritanceMultiMap<Entity>[] entityLists;
    private ConcurrentLinkedQueue<BlockPos> tileEntityPosQueue;
    private int heightMapMinimum;
    private static final String[] I;
    private final boolean[] updateSkylightColumns;
    private boolean isLightPopulated;
    private final int[] precipitationHeightMap;
    private static final Logger logger;
    public final int zPosition;
    private boolean isChunkLoaded;
    private boolean isTerrainPopulated;
    private final ExtendedBlockStorage[] storageArrays;
    private long inhabitedTime;
    public final int xPosition;
    private boolean hasEntities;
    private boolean field_150815_m;
    
    public int getLightSubtracted(final BlockPos blockPos, final int n) {
        final int n2 = blockPos.getX() & (0x9E ^ 0x91);
        final int y = blockPos.getY();
        final int n3 = blockPos.getZ() & (0x31 ^ 0x3E);
        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> (0xC ^ 0x8)];
        if (extendedBlockStorage == null) {
            int length;
            if (!this.worldObj.provider.getHasNoSky() && n < EnumSkyBlock.SKY.defaultLightValue) {
                length = EnumSkyBlock.SKY.defaultLightValue - n;
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                length = "".length();
            }
            return length;
        }
        int n4;
        if (this.worldObj.provider.getHasNoSky()) {
            n4 = "".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            n4 = extendedBlockStorage.getExtSkylightValue(n2, y & (0x42 ^ 0x4D), n3);
        }
        int n5 = n4 - n;
        final int extBlocklightValue = extendedBlockStorage.getExtBlocklightValue(n2, y & (0xC8 ^ 0xC7), n3);
        if (extBlocklightValue > n5) {
            n5 = extBlocklightValue;
        }
        return n5;
    }
    
    private void propagateSkylightOcclusion(final int n, final int n2) {
        this.updateSkylightColumns[n + n2 * (0x75 ^ 0x65)] = (" ".length() != 0);
        this.isGapLightingUpdated = (" ".length() != 0);
    }
    
    public void setModified(final boolean isModified) {
        this.isModified = isModified;
    }
    
    public boolean isEmpty() {
        return "".length() != 0;
    }
    
    public void enqueueRelightChecks() {
        final BlockPos blockPos = new BlockPos(this.xPosition << (0x3 ^ 0x7), "".length(), this.zPosition << (0x5F ^ 0x5B));
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < (0xE ^ 0x6)) {
            if (this.queuedLightChecks >= 2468 + 2488 - 2882 + 2022) {
                return;
            }
            final int n = this.queuedLightChecks % (0x9F ^ 0x8F);
            final int n2 = this.queuedLightChecks / (0x62 ^ 0x72) % (0x3D ^ 0x2D);
            final int n3 = this.queuedLightChecks / (222 + 56 - 121 + 99);
            this.queuedLightChecks += " ".length();
            int j = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (j < (0x39 ^ 0x29)) {
                final BlockPos add = blockPos.add(n2, (n << (0x5 ^ 0x1)) + j, n3);
                int n4;
                if (j != 0 && j != (0x4C ^ 0x43) && n2 != 0 && n2 != (0xCD ^ 0xC2) && n3 != 0 && n3 != (0x46 ^ 0x49)) {
                    n4 = "".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else {
                    n4 = " ".length();
                }
                final int n5 = n4;
                if ((this.storageArrays[n] == null && n5 != 0) || (this.storageArrays[n] != null && this.storageArrays[n].getBlockByExtId(n2, j, n3).getMaterial() == Material.air)) {
                    final EnumFacing[] values;
                    final int length = (values = EnumFacing.values()).length;
                    int k = "".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    while (k < length) {
                        final BlockPos offset = add.offset(values[k]);
                        if (this.worldObj.getBlockState(offset).getBlock().getLightValue() > 0) {
                            this.worldObj.checkLight(offset);
                        }
                        ++k;
                    }
                    this.worldObj.checkLight(add);
                }
                ++j;
            }
            ++i;
        }
    }
    
    private void relightBlock(final int n, final int n2, final int n3) {
        int n5;
        final int n4 = n5 = (this.heightMap[n3 << (0x33 ^ 0x37) | n] & 164 + 157 - 246 + 180);
        if (n2 > n4) {
            n5 = n2;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        while (n5 > 0 && this.getBlockLightOpacity(n, n5 - " ".length(), n3) == 0) {
            --n5;
        }
        if (n5 != n4) {
            this.worldObj.markBlocksDirtyVertical(n + this.xPosition * (0x9D ^ 0x8D), n3 + this.zPosition * (0x79 ^ 0x69), n5, n4);
            this.heightMap[n3 << (0x28 ^ 0x2C) | n] = n5;
            final int n6 = this.xPosition * (0x44 ^ 0x54) + n;
            final int n7 = this.zPosition * (0xD7 ^ 0xC7) + n3;
            if (!this.worldObj.provider.getHasNoSky()) {
                if (n5 < n4) {
                    int i = n5;
                    "".length();
                    if (4 == -1) {
                        throw null;
                    }
                    while (i < n4) {
                        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[i >> (0x40 ^ 0x44)];
                        if (extendedBlockStorage != null) {
                            extendedBlockStorage.setExtSkylightValue(n, i & (0x31 ^ 0x3E), n3, 0x8E ^ 0x81);
                            this.worldObj.notifyLightSet(new BlockPos((this.xPosition << (0xE ^ 0xA)) + n, i, (this.zPosition << (0x6F ^ 0x6B)) + n3));
                        }
                        ++i;
                    }
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
                else {
                    int j = n4;
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    while (j < n5) {
                        final ExtendedBlockStorage extendedBlockStorage2 = this.storageArrays[j >> (0x81 ^ 0x85)];
                        if (extendedBlockStorage2 != null) {
                            extendedBlockStorage2.setExtSkylightValue(n, j & (0x50 ^ 0x5F), n3, "".length());
                            this.worldObj.notifyLightSet(new BlockPos((this.xPosition << (0x71 ^ 0x75)) + n, j, (this.zPosition << (0x6 ^ 0x2)) + n3));
                        }
                        ++j;
                    }
                }
                int length = 0x19 ^ 0x16;
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                while (n5 > 0 && length > 0) {
                    --n5;
                    int n8 = this.getBlockLightOpacity(n, n5, n3);
                    if (n8 == 0) {
                        n8 = " ".length();
                    }
                    length -= n8;
                    if (length < 0) {
                        length = "".length();
                    }
                    final ExtendedBlockStorage extendedBlockStorage3 = this.storageArrays[n5 >> (0x67 ^ 0x63)];
                    if (extendedBlockStorage3 != null) {
                        extendedBlockStorage3.setExtSkylightValue(n, n5 & (0xAE ^ 0xA1), n3, length);
                    }
                }
            }
            final int heightMapMinimum = this.heightMap[n3 << (0x83 ^ 0x87) | n];
            int n9;
            int n10;
            if ((n9 = heightMapMinimum) < (n10 = n4)) {
                n10 = heightMapMinimum;
                n9 = n4;
            }
            if (heightMapMinimum < this.heightMapMinimum) {
                this.heightMapMinimum = heightMapMinimum;
            }
            if (!this.worldObj.provider.getHasNoSky()) {
                final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final EnumFacing enumFacing = iterator.next();
                    this.updateSkylightNeighborHeight(n6 + enumFacing.getFrontOffsetX(), n7 + enumFacing.getFrontOffsetZ(), n10, n9);
                }
                this.updateSkylightNeighborHeight(n6, n7, n10, n9);
            }
            this.isModified = (" ".length() != 0);
        }
    }
    
    public boolean canSeeSky(final BlockPos blockPos) {
        if (blockPos.getY() >= this.heightMap[(blockPos.getZ() & (0x54 ^ 0x5B)) << (0x18 ^ 0x1C) | (blockPos.getX() & (0x79 ^ 0x76))]) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setChunkModified() {
        this.isModified = (" ".length() != 0);
    }
    
    public void getEntitiesWithinAABBForEntity(final Entity entity, final AxisAlignedBB axisAlignedBB, final List<Entity> list, final Predicate<? super Entity> predicate) {
        final int floor_double = MathHelper.floor_double((axisAlignedBB.minY - 2.0) / 16.0);
        final int floor_double2 = MathHelper.floor_double((axisAlignedBB.maxY + 2.0) / 16.0);
        final int clamp_int = MathHelper.clamp_int(floor_double, "".length(), this.entityLists.length - " ".length());
        final int clamp_int2 = MathHelper.clamp_int(floor_double2, "".length(), this.entityLists.length - " ".length());
        int i = clamp_int;
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i <= clamp_int2) {
            if (!this.entityLists[i].isEmpty()) {
                final Iterator<Entity> iterator = this.entityLists[i].iterator();
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final Entity entity2 = iterator.next();
                    if (entity2.getEntityBoundingBox().intersectsWith(axisAlignedBB) && entity2 != entity) {
                        if (predicate == null || predicate.apply((Object)entity2)) {
                            list.add(entity2);
                        }
                        final Entity[] parts = entity2.getParts();
                        if (parts == null) {
                            continue;
                        }
                        int j = "".length();
                        "".length();
                        if (-1 == 1) {
                            throw null;
                        }
                        while (j < parts.length) {
                            final Entity entity3 = parts[j];
                            if (entity3 != entity && entity3.getEntityBoundingBox().intersectsWith(axisAlignedBB) && (predicate == null || predicate.apply((Object)entity3))) {
                                list.add(entity3);
                            }
                            ++j;
                        }
                    }
                }
            }
            ++i;
        }
    }
    
    public BlockPos getPrecipitationHeight(final BlockPos blockPos) {
        final int n = (blockPos.getX() & (0x2 ^ 0xD)) | (blockPos.getZ() & (0x7E ^ 0x71)) << (0xB0 ^ 0xB4);
        if (new BlockPos(blockPos.getX(), this.precipitationHeightMap[n], blockPos.getZ()).getY() == -(717 + 658 - 790 + 414)) {
            BlockPos down = new BlockPos(blockPos.getX(), this.getTopFilledSegment() + (0xA1 ^ 0xAE), blockPos.getZ());
            int n2 = -" ".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (down.getY() > 0 && n2 == -" ".length()) {
                final Material material = this.getBlock(down).getMaterial();
                if (!material.blocksMovement() && !material.isLiquid()) {
                    down = down.down();
                    "".length();
                    if (3 < 2) {
                        throw null;
                    }
                    continue;
                }
                else {
                    n2 = down.getY() + " ".length();
                }
            }
            this.precipitationHeightMap[n] = n2;
        }
        return new BlockPos(blockPos.getX(), this.precipitationHeightMap[n], blockPos.getZ());
    }
    
    public int getLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos) {
        final int n = blockPos.getX() & (0x57 ^ 0x58);
        final int y = blockPos.getY();
        final int n2 = blockPos.getZ() & (0x3B ^ 0x34);
        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> (0x1D ^ 0x19)];
        int n3;
        if (extendedBlockStorage == null) {
            if (this.canSeeSky(blockPos)) {
                n3 = enumSkyBlock.defaultLightValue;
                "".length();
                if (0 == 4) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
        }
        else if (enumSkyBlock == EnumSkyBlock.SKY) {
            if (this.worldObj.provider.getHasNoSky()) {
                n3 = "".length();
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n3 = extendedBlockStorage.getExtSkylightValue(n, y & (0xBA ^ 0xB5), n2);
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
        }
        else if (enumSkyBlock == EnumSkyBlock.BLOCK) {
            n3 = extendedBlockStorage.getExtBlocklightValue(n, y & (0x6B ^ 0x64), n2);
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            n3 = enumSkyBlock.defaultLightValue;
        }
        return n3;
    }
    
    public void addTileEntity(final BlockPos pos, final TileEntity tileEntity) {
        tileEntity.setWorldObj(this.worldObj);
        tileEntity.setPos(pos);
        if (this.getBlock(pos) instanceof ITileEntityProvider) {
            if (this.chunkTileEntityMap.containsKey(pos)) {
                this.chunkTileEntityMap.get(pos).invalidate();
            }
            tileEntity.validate();
            this.chunkTileEntityMap.put(pos, tileEntity);
        }
    }
    
    public void setHasEntities(final boolean hasEntities) {
        this.hasEntities = hasEntities;
    }
    
    public boolean getAreLevelsEmpty(int length, int n) {
        if (length < 0) {
            length = "".length();
        }
        if (n >= 228 + 107 - 199 + 120) {
            n = 227 + 73 - 220 + 175;
        }
        int i = length;
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i <= n) {
            final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[i >> (0xA1 ^ 0xA5)];
            if (extendedBlockStorage != null && !extendedBlockStorage.isEmpty()) {
                return "".length() != 0;
            }
            i += 16;
        }
        return " ".length() != 0;
    }
    
    public int getTopFilledSegment() {
        int i = this.storageArrays.length - " ".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i >= 0) {
            if (this.storageArrays[i] != null) {
                return this.storageArrays[i].getYLocation();
            }
            --i;
        }
        return "".length();
    }
    
    public void removeEntityAtIndex(final Entity entity, int length) {
        if (length < 0) {
            length = "".length();
        }
        if (length >= this.entityLists.length) {
            length = this.entityLists.length - " ".length();
        }
        this.entityLists[length].remove(entity);
    }
    
    public ExtendedBlockStorage[] getBlockStorageArray() {
        return this.storageArrays;
    }
    
    public Map<BlockPos, TileEntity> getTileEntityMap() {
        return this.chunkTileEntityMap;
    }
    
    public boolean isPopulated() {
        if (this.field_150815_m && this.isTerrainPopulated && this.isLightPopulated) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void fillChunk(final byte[] array, final int n, final boolean b) {
        int length = "".length();
        int n2;
        if (this.worldObj.provider.getHasNoSky()) {
            n2 = "".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        final int n3 = n2;
        int i = "".length();
        "".length();
        if (1 == 2) {
            throw null;
        }
        while (i < this.storageArrays.length) {
            if ((n & " ".length() << i) != 0x0) {
                if (this.storageArrays[i] == null) {
                    this.storageArrays[i] = new ExtendedBlockStorage(i << (0x67 ^ 0x63), n3 != 0);
                }
                final char[] data = this.storageArrays[i].getData();
                int j = "".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                while (j < data.length) {
                    data[j] = (char)((array[length + " ".length()] & 107 + 145 - 134 + 137) << (0xA7 ^ 0xAF) | (array[length] & 45 + 182 - 1 + 29));
                    length += 2;
                    ++j;
                }
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else if (b && this.storageArrays[i] != null) {
                this.storageArrays[i] = null;
            }
            ++i;
        }
        int k = "".length();
        "".length();
        if (4 == 2) {
            throw null;
        }
        while (k < this.storageArrays.length) {
            if ((n & " ".length() << k) != 0x0 && this.storageArrays[k] != null) {
                final NibbleArray blocklightArray = this.storageArrays[k].getBlocklightArray();
                System.arraycopy(array, length, blocklightArray.getData(), "".length(), blocklightArray.getData().length);
                length += blocklightArray.getData().length;
            }
            ++k;
        }
        if (n3 != 0) {
            int l = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
            while (l < this.storageArrays.length) {
                if ((n & " ".length() << l) != 0x0 && this.storageArrays[l] != null) {
                    final NibbleArray skylightArray = this.storageArrays[l].getSkylightArray();
                    System.arraycopy(array, length, skylightArray.getData(), "".length(), skylightArray.getData().length);
                    length += skylightArray.getData().length;
                }
                ++l;
            }
        }
        if (b) {
            System.arraycopy(array, length, this.blockBiomeArray, "".length(), this.blockBiomeArray.length);
            final int n4 = length + this.blockBiomeArray.length;
        }
        int length2 = "".length();
        "".length();
        if (0 <= -1) {
            throw null;
        }
        while (length2 < this.storageArrays.length) {
            if (this.storageArrays[length2] != null && (n & " ".length() << length2) != 0x0) {
                this.storageArrays[length2].removeInvalidBlocks();
            }
            ++length2;
        }
        this.isLightPopulated = (" ".length() != 0);
        this.isTerrainPopulated = (" ".length() != 0);
        this.generateHeightMap();
        final Iterator<TileEntity> iterator = this.chunkTileEntityMap.values().iterator();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().updateContainingBlockInfo();
        }
    }
    
    public ChunkCoordIntPair getChunkCoordIntPair() {
        return new ChunkCoordIntPair(this.xPosition, this.zPosition);
    }
    
    public int getBlockMetadata(final BlockPos blockPos) {
        return this.getBlockMetadata(blockPos.getX() & (0xA7 ^ 0xA8), blockPos.getY(), blockPos.getZ() & (0x15 ^ 0x1A));
    }
    
    public int[] getHeightMap() {
        return this.heightMap;
    }
    
    public boolean isLightPopulated() {
        return this.isLightPopulated;
    }
    
    public long getInhabitedTime() {
        return this.inhabitedTime;
    }
    
    public IBlockState getBlockState(final BlockPos blockPos) {
        if (this.worldObj.getWorldType() == WorldType.DEBUG_WORLD) {
            IBlockState blockState = null;
            if (blockPos.getY() == (0x28 ^ 0x14)) {
                blockState = Blocks.barrier.getDefaultState();
            }
            if (blockPos.getY() == (0x56 ^ 0x10)) {
                blockState = ChunkProviderDebug.func_177461_b(blockPos.getX(), blockPos.getZ());
            }
            IBlockState defaultState;
            if (blockState == null) {
                defaultState = Blocks.air.getDefaultState();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else {
                defaultState = blockState;
            }
            return defaultState;
        }
        try {
            if (blockPos.getY() >= 0 && blockPos.getY() >> (0x97 ^ 0x93) < this.storageArrays.length) {
                final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[blockPos.getY() >> (0xC4 ^ 0xC0)];
                if (extendedBlockStorage != null) {
                    return extendedBlockStorage.get(blockPos.getX() & (0x36 ^ 0x39), blockPos.getY() & (0xB6 ^ 0xB9), blockPos.getZ() & (0x7E ^ 0x71));
                }
            }
            return Blocks.air.getDefaultState();
        }
        catch (Throwable t) {
            final CrashReport crashReport = CrashReport.makeCrashReport(t, Chunk.I[0x86 ^ 0x80]);
            crashReport.makeCategory(Chunk.I[0x9D ^ 0x9A]).addCrashSectionCallable(Chunk.I[0x7B ^ 0x73], new Callable<String>(this, blockPos) {
                final Chunk this$0;
                private final BlockPos val$pos;
                
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
                        if (-1 >= 2) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public String call() throws Exception {
                    return CrashReportCategory.getCoordinateInfo(this.val$pos);
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
            });
            throw new ReportedException(crashReport);
        }
    }
    
    public TileEntity getTileEntity(final BlockPos blockPos, final EnumCreateEntityType enumCreateEntityType) {
        TileEntity newTileEntity = this.chunkTileEntityMap.get(blockPos);
        if (newTileEntity == null) {
            if (enumCreateEntityType == EnumCreateEntityType.IMMEDIATE) {
                newTileEntity = this.createNewTileEntity(blockPos);
                this.worldObj.setTileEntity(blockPos, newTileEntity);
                "".length();
                if (0 == 2) {
                    throw null;
                }
            }
            else if (enumCreateEntityType == EnumCreateEntityType.QUEUED) {
                this.tileEntityPosQueue.add(blockPos);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
            }
        }
        else if (newTileEntity.isInvalid()) {
            this.chunkTileEntityMap.remove(blockPos);
            return null;
        }
        return newTileEntity;
    }
    
    public void setHeightMap(final int[] array) {
        if (this.heightMap.length != array.length) {
            Chunk.logger.warn(Chunk.I[0xBD ^ 0xAF] + array.length + Chunk.I[0x8 ^ 0x1B] + this.heightMap.length);
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            int i = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
            while (i < this.heightMap.length) {
                this.heightMap[i] = array[i];
                ++i;
            }
        }
    }
    
    private void func_180700_a(final EnumFacing enumFacing) {
        if (this.isTerrainPopulated) {
            if (enumFacing == EnumFacing.EAST) {
                int i = "".length();
                "".length();
                if (0 == -1) {
                    throw null;
                }
                while (i < (0x5B ^ 0x4B)) {
                    this.func_150811_f(0x53 ^ 0x5C, i);
                    ++i;
                }
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            else if (enumFacing == EnumFacing.WEST) {
                int j = "".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
                while (j < (0xD7 ^ 0xC7)) {
                    this.func_150811_f("".length(), j);
                    ++j;
                }
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else if (enumFacing == EnumFacing.SOUTH) {
                int k = "".length();
                "".length();
                if (2 <= 1) {
                    throw null;
                }
                while (k < (0x48 ^ 0x58)) {
                    this.func_150811_f(k, 0xCA ^ 0xC5);
                    ++k;
                }
                "".length();
                if (0 == -1) {
                    throw null;
                }
            }
            else if (enumFacing == EnumFacing.NORTH) {
                int l = "".length();
                "".length();
                if (0 < 0) {
                    throw null;
                }
                while (l < (0x80 ^ 0x90)) {
                    this.func_150811_f(l, "".length());
                    ++l;
                }
            }
        }
    }
    
    public void populateChunk(final IChunkProvider chunkProvider, final IChunkProvider chunkProvider2, final int n, final int n2) {
        final boolean chunkExists = chunkProvider.chunkExists(n, n2 - " ".length());
        final boolean chunkExists2 = chunkProvider.chunkExists(n + " ".length(), n2);
        final boolean chunkExists3 = chunkProvider.chunkExists(n, n2 + " ".length());
        final boolean chunkExists4 = chunkProvider.chunkExists(n - " ".length(), n2);
        final boolean chunkExists5 = chunkProvider.chunkExists(n - " ".length(), n2 - " ".length());
        final boolean chunkExists6 = chunkProvider.chunkExists(n + " ".length(), n2 + " ".length());
        final boolean chunkExists7 = chunkProvider.chunkExists(n - " ".length(), n2 + " ".length());
        final boolean chunkExists8 = chunkProvider.chunkExists(n + " ".length(), n2 - " ".length());
        if (chunkExists2 && chunkExists3 && chunkExists6) {
            if (!this.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n, n2);
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, this, n, n2);
            }
        }
        if (chunkExists4 && chunkExists3 && chunkExists7) {
            final Chunk provideChunk = chunkProvider.provideChunk(n - " ".length(), n2);
            if (!provideChunk.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n - " ".length(), n2);
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, provideChunk, n - " ".length(), n2);
            }
        }
        if (chunkExists && chunkExists2 && chunkExists8) {
            final Chunk provideChunk2 = chunkProvider.provideChunk(n, n2 - " ".length());
            if (!provideChunk2.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n, n2 - " ".length());
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, provideChunk2, n, n2 - " ".length());
            }
        }
        if (chunkExists5 && chunkExists && chunkExists4) {
            final Chunk provideChunk3 = chunkProvider.provideChunk(n - " ".length(), n2 - " ".length());
            if (!provideChunk3.isTerrainPopulated) {
                chunkProvider.populate(chunkProvider2, n - " ".length(), n2 - " ".length());
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                chunkProvider.func_177460_a(chunkProvider2, provideChunk3, n - " ".length(), n2 - " ".length());
            }
        }
    }
    
    public void setLastSaveTime(final long lastSaveTime) {
        this.lastSaveTime = lastSaveTime;
    }
    
    private void updateSkylightNeighborHeight(final int n, final int n2, final int n3, final int n4) {
        if (n4 > n3 && this.worldObj.isAreaLoaded(new BlockPos(n, "".length(), n2), 0x5B ^ 0x4B)) {
            int i = n3;
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < n4) {
                this.worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(n, i, n2));
                ++i;
            }
            this.isModified = (" ".length() != 0);
        }
    }
    
    public void generateSkylightMap() {
        final int topFilledSegment = this.getTopFilledSegment();
        this.heightMapMinimum = 994298881 + 123230439 - 562611493 + 1592565820;
        int i = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (i < (0xA5 ^ 0xB5)) {
            int j = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (j < (0x6F ^ 0x7F)) {
                this.precipitationHeightMap[i + (j << (0x97 ^ 0x93))] = -(96 + 928 - 269 + 244);
                int k = topFilledSegment + (0x8A ^ 0x9A);
                "".length();
                if (0 < 0) {
                    throw null;
                }
                while (k > 0) {
                    if (this.getBlockLightOpacity(i, k - " ".length(), j) != 0) {
                        if ((this.heightMap[j << (0x13 ^ 0x17) | i] = k) >= this.heightMapMinimum) {
                            break;
                        }
                        this.heightMapMinimum = k;
                        "".length();
                        if (0 <= -1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        --k;
                    }
                }
                if (!this.worldObj.provider.getHasNoSky()) {
                    int n = 0x1B ^ 0x14;
                    int n2 = topFilledSegment + (0xBE ^ 0xAE) - " ".length();
                    do {
                        int n3 = this.getBlockLightOpacity(i, n2, j);
                        if (n3 == 0 && n != (0x2F ^ 0x20)) {
                            n3 = " ".length();
                        }
                        n -= n3;
                        if (n > 0) {
                            final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[n2 >> (0x42 ^ 0x46)];
                            if (extendedBlockStorage == null) {
                                continue;
                            }
                            extendedBlockStorage.setExtSkylightValue(i, n2 & (0xA ^ 0x5), j, n);
                            this.worldObj.notifyLightSet(new BlockPos((this.xPosition << (0x59 ^ 0x5D)) + i, n2, (this.zPosition << (0x31 ^ 0x35)) + j));
                        }
                    } while (--n2 > 0 && n > 0);
                }
                ++j;
            }
            ++i;
        }
        this.isModified = (" ".length() != 0);
    }
    
    private TileEntity createNewTileEntity(final BlockPos blockPos) {
        final Block block = this.getBlock(blockPos);
        TileEntity newTileEntity;
        if (!block.hasTileEntity()) {
            newTileEntity = null;
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            newTileEntity = ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, this.getBlockMetadata(blockPos));
        }
        return newTileEntity;
    }
    
    public void setInhabitedTime(final long inhabitedTime) {
        this.inhabitedTime = inhabitedTime;
    }
    
    public IBlockState setBlockState(final BlockPos blockPos, final IBlockState blockState) {
        final int n = blockPos.getX() & (0x70 ^ 0x7F);
        final int y = blockPos.getY();
        final int n2 = blockPos.getZ() & (0x8F ^ 0x80);
        final int n3 = n2 << (0x61 ^ 0x65) | n;
        if (y >= this.precipitationHeightMap[n3] - " ".length()) {
            this.precipitationHeightMap[n3] = -(354 + 274 - 134 + 505);
        }
        final int n4 = this.heightMap[n3];
        final IBlockState blockState2 = this.getBlockState(blockPos);
        if (blockState2 == blockState) {
            return null;
        }
        final Block block = blockState.getBlock();
        final Block block2 = blockState2.getBlock();
        ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> (0xAA ^ 0xAE)];
        int length = "".length();
        if (extendedBlockStorage == null) {
            if (block == Blocks.air) {
                return null;
            }
            final ExtendedBlockStorage[] storageArrays = this.storageArrays;
            final int n5 = y >> (0x25 ^ 0x21);
            final int n6 = y >> (0xBD ^ 0xB9) << (0x43 ^ 0x47);
            int n7;
            if (this.worldObj.provider.getHasNoSky()) {
                n7 = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                n7 = " ".length();
            }
            final ExtendedBlockStorage extendedBlockStorage2 = new ExtendedBlockStorage(n6, n7 != 0);
            storageArrays[n5] = extendedBlockStorage2;
            extendedBlockStorage = extendedBlockStorage2;
            int n8;
            if (y >= n4) {
                n8 = " ".length();
                "".length();
                if (2 >= 3) {
                    throw null;
                }
            }
            else {
                n8 = "".length();
            }
            length = n8;
        }
        extendedBlockStorage.set(n, y & (0x8B ^ 0x84), n2, blockState);
        if (block2 != block) {
            if (!this.worldObj.isRemote) {
                block2.breakBlock(this.worldObj, blockPos, blockState2);
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else if (block2 instanceof ITileEntityProvider) {
                this.worldObj.removeTileEntity(blockPos);
            }
        }
        if (extendedBlockStorage.getBlockByExtId(n, y & (0x3E ^ 0x31), n2) != block) {
            return null;
        }
        if (length != 0) {
            this.generateSkylightMap();
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else {
            final int lightOpacity = block.getLightOpacity();
            final int lightOpacity2 = block2.getLightOpacity();
            if (lightOpacity > 0) {
                if (y >= n4) {
                    this.relightBlock(n, y + " ".length(), n2);
                    "".length();
                    if (3 < 1) {
                        throw null;
                    }
                }
            }
            else if (y == n4 - " ".length()) {
                this.relightBlock(n, y, n2);
            }
            if (lightOpacity != lightOpacity2 && (lightOpacity < lightOpacity2 || this.getLightFor(EnumSkyBlock.SKY, blockPos) > 0 || this.getLightFor(EnumSkyBlock.BLOCK, blockPos) > 0)) {
                this.propagateSkylightOcclusion(n, n2);
            }
        }
        if (block2 instanceof ITileEntityProvider) {
            final TileEntity tileEntity = this.getTileEntity(blockPos, EnumCreateEntityType.CHECK);
            if (tileEntity != null) {
                tileEntity.updateContainingBlockInfo();
            }
        }
        if (!this.worldObj.isRemote && block2 != block) {
            block.onBlockAdded(this.worldObj, blockPos, blockState);
        }
        if (block instanceof ITileEntityProvider) {
            TileEntity tileEntity2 = this.getTileEntity(blockPos, EnumCreateEntityType.CHECK);
            if (tileEntity2 == null) {
                tileEntity2 = ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, block.getMetaFromState(blockState));
                this.worldObj.setTileEntity(blockPos, tileEntity2);
            }
            if (tileEntity2 != null) {
                tileEntity2.updateContainingBlockInfo();
            }
        }
        this.isModified = (" ".length() != 0);
        return blockState2;
    }
    
    public void addTileEntity(final TileEntity tileEntity) {
        this.addTileEntity(tileEntity.getPos(), tileEntity);
        if (this.isChunkLoaded) {
            this.worldObj.addTileEntity(tileEntity);
        }
    }
    
    public void func_150809_p() {
        this.isTerrainPopulated = (" ".length() != 0);
        this.isLightPopulated = (" ".length() != 0);
        final BlockPos blockPos = new BlockPos(this.xPosition << (0xB0 ^ 0xB4), "".length(), this.zPosition << (0x75 ^ 0x71));
        if (!this.worldObj.provider.getHasNoSky()) {
            if (this.worldObj.isAreaLoaded(blockPos.add(-" ".length(), "".length(), -" ".length()), blockPos.add(0xD2 ^ 0xC2, this.worldObj.func_181545_F(), 0x80 ^ 0x90))) {
                int i = "".length();
                "".length();
                if (2 < 2) {
                    throw null;
                }
            Label_0217:
                while (i < (0x73 ^ 0x63)) {
                    int j = "".length();
                    "".length();
                    if (3 <= 0) {
                        throw null;
                    }
                    while (j < (0x8D ^ 0x9D)) {
                        if (!this.func_150811_f(i, j)) {
                            this.isLightPopulated = ("".length() != 0);
                            "".length();
                            if (1 <= 0) {
                                throw null;
                            }
                            break Label_0217;
                        }
                        else {
                            ++j;
                        }
                    }
                    ++i;
                }
                if (this.isLightPopulated) {
                    final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final EnumFacing enumFacing = iterator.next();
                        int length;
                        if (enumFacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) {
                            length = (0xD0 ^ 0xC0);
                            "".length();
                            if (0 >= 2) {
                                throw null;
                            }
                        }
                        else {
                            length = " ".length();
                        }
                        this.worldObj.getChunkFromBlockCoords(blockPos.offset(enumFacing, length)).func_180700_a(enumFacing.getOpposite());
                    }
                    this.func_177441_y();
                    "".length();
                    if (0 >= 3) {
                        throw null;
                    }
                }
            }
            else {
                this.isLightPopulated = ("".length() != 0);
            }
        }
    }
    
    public boolean isAtLocation(final int n, final int n2) {
        if (n == this.xPosition && n2 == this.zPosition) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Chunk(final World worldObj, final int xPosition, final int zPosition) {
        this.storageArrays = new ExtendedBlockStorage[0x7C ^ 0x6C];
        this.blockBiomeArray = new byte[122 + 166 - 258 + 226];
        this.precipitationHeightMap = new int[157 + 212 - 279 + 166];
        this.updateSkylightColumns = new boolean[18 + 98 - 21 + 161];
        this.chunkTileEntityMap = (Map<BlockPos, TileEntity>)Maps.newHashMap();
        this.queuedLightChecks = 3855 + 1292 - 3786 + 2735;
        this.tileEntityPosQueue = (ConcurrentLinkedQueue<BlockPos>)Queues.newConcurrentLinkedQueue();
        this.entityLists = (ClassInheritanceMultiMap<Entity>[])new ClassInheritanceMultiMap[0xB2 ^ 0xA2];
        this.worldObj = worldObj;
        this.xPosition = xPosition;
        this.zPosition = zPosition;
        this.heightMap = new int[231 + 58 - 225 + 192];
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < this.entityLists.length) {
            this.entityLists[i] = new ClassInheritanceMultiMap<Entity>(Entity.class);
            ++i;
        }
        Arrays.fill(this.precipitationHeightMap, -(787 + 187 - 341 + 366));
        Arrays.fill(this.blockBiomeArray, (byte)(-" ".length()));
    }
    
    private boolean func_150811_f(final int n, final int n2) {
        final int topFilledSegment = this.getTopFilledSegment();
        int n3 = "".length();
        int n4 = "".length();
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos((this.xPosition << (0x95 ^ 0x91)) + n, "".length(), (this.zPosition << (0xB ^ 0xF)) + n2);
        int n5 = topFilledSegment + (0x34 ^ 0x24) - " ".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (n5 > this.worldObj.func_181545_F() || (n5 > 0 && n4 == 0)) {
            mutableBlockPos.func_181079_c(mutableBlockPos.getX(), n5, mutableBlockPos.getZ());
            final int blockLightOpacity = this.getBlockLightOpacity(mutableBlockPos);
            if (blockLightOpacity == 215 + 61 - 57 + 36 && mutableBlockPos.getY() < this.worldObj.func_181545_F()) {
                n4 = " ".length();
            }
            if (n3 == 0 && blockLightOpacity > 0) {
                n3 = " ".length();
                "".length();
                if (2 == 4) {
                    throw null;
                }
            }
            else if (n3 != 0 && blockLightOpacity == 0 && !this.worldObj.checkLight(mutableBlockPos)) {
                return "".length() != 0;
            }
            --n5;
        }
        int i = mutableBlockPos.getY();
        "".length();
        if (4 <= 1) {
            throw null;
        }
        while (i > 0) {
            mutableBlockPos.func_181079_c(mutableBlockPos.getX(), i, mutableBlockPos.getZ());
            if (this.getBlock(mutableBlockPos).getLightValue() > 0) {
                this.worldObj.checkLight(mutableBlockPos);
            }
            --i;
        }
        return " ".length() != 0;
    }
    
    public World getWorld() {
        return this.worldObj;
    }
    
    public int getHeight(final BlockPos blockPos) {
        return this.getHeightValue(blockPos.getX() & (0x7E ^ 0x71), blockPos.getZ() & (0x62 ^ 0x6D));
    }
    
    public Chunk(final World world, final ChunkPrimer chunkPrimer, final int n, final int n2) {
        this(world, n, n2);
        final int n3 = 228 + 249 - 458 + 237;
        int n4;
        if (world.provider.getHasNoSky()) {
            n4 = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n4 = " ".length();
        }
        final int n5 = n4;
        int i = "".length();
        "".length();
        if (1 == -1) {
            throw null;
        }
        while (i < (0x7D ^ 0x6D)) {
            int j = "".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
            while (j < (0xA1 ^ 0xB1)) {
                int k = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (k < n3) {
                    final IBlockState blockState = chunkPrimer.getBlockState(i * n3 * (0x73 ^ 0x63) | j * n3 | k);
                    if (blockState.getBlock().getMaterial() != Material.air) {
                        final int n6 = k >> (0x2F ^ 0x2B);
                        if (this.storageArrays[n6] == null) {
                            this.storageArrays[n6] = new ExtendedBlockStorage(n6 << (0xB2 ^ 0xB6), n5 != 0);
                        }
                        this.storageArrays[n6].set(i, k & (0xAA ^ 0xA5), j, blockState);
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
    }
    
    public void setLightPopulated(final boolean isLightPopulated) {
        this.isLightPopulated = isLightPopulated;
    }
    
    public boolean needsSaving(final boolean b) {
        if (b) {
            if ((this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime) || this.isModified) {
                return " ".length() != 0;
            }
        }
        else if (this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L) {
            return " ".length() != 0;
        }
        return this.isModified;
    }
    
    private int getBlockLightOpacity(final int n, final int n2, final int n3) {
        return this.getBlock0(n, n2, n3).getLightOpacity();
    }
    
    public void onChunkLoad() {
        this.isChunkLoaded = (" ".length() != 0);
        this.worldObj.addTileEntities(this.chunkTileEntityMap.values());
        int i = "".length();
        "".length();
        if (0 == 4) {
            throw null;
        }
        while (i < this.entityLists.length) {
            final Iterator<Entity> iterator = this.entityLists[i].iterator();
            "".length();
            if (4 < -1) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().onChunkLoad();
            }
            this.worldObj.loadEntities(this.entityLists[i]);
            ++i;
        }
    }
    
    public Block getBlock(final int n, final int n2, final int n3) {
        try {
            return this.getBlock0(n & (0x98 ^ 0x97), n2, n3 & (0x4 ^ 0xB));
        }
        catch (ReportedException ex) {
            ex.getCrashReport().makeCategory(Chunk.I["  ".length()]).addCrashSectionCallable(Chunk.I["   ".length()], new Callable<String>(this, n, n2, n3) {
                final Chunk this$0;
                private final int val$z;
                private final int val$x;
                private final int val$y;
                
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
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public String call() throws Exception {
                    return CrashReportCategory.getCoordinateInfo(new BlockPos(this.this$0.xPosition * (0xB1 ^ 0xA1) + this.val$x, this.val$y, this.this$0.zPosition * (0x8E ^ 0x9E) + this.val$z));
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
            });
            throw ex;
        }
    }
    
    public void removeTileEntity(final BlockPos blockPos) {
        if (this.isChunkLoaded) {
            final TileEntity tileEntity = this.chunkTileEntityMap.remove(blockPos);
            if (tileEntity != null) {
                tileEntity.invalidate();
            }
        }
    }
    
    public <T extends Entity> void getEntitiesOfTypeWithinAAAB(final Class<? extends T> clazz, final AxisAlignedBB axisAlignedBB, final List<T> list, final Predicate<? super T> predicate) {
        final int floor_double = MathHelper.floor_double((axisAlignedBB.minY - 2.0) / 16.0);
        final int floor_double2 = MathHelper.floor_double((axisAlignedBB.maxY + 2.0) / 16.0);
        final int clamp_int = MathHelper.clamp_int(floor_double, "".length(), this.entityLists.length - " ".length());
        final int clamp_int2 = MathHelper.clamp_int(floor_double2, "".length(), this.entityLists.length - " ".length());
        int i = clamp_int;
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i <= clamp_int2) {
            final Iterator<T> iterator = this.entityLists[i].getByClass((Class<T>)clazz).iterator();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Entity entity = iterator.next();
                if (entity.getEntityBoundingBox().intersectsWith(axisAlignedBB) && (predicate == null || predicate.apply((Object)entity))) {
                    list.add((T)entity);
                }
            }
            ++i;
        }
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void removeEntity(final Entity entity) {
        this.removeEntityAtIndex(entity, entity.chunkCoordY);
    }
    
    public int getBlockLightOpacity(final BlockPos blockPos) {
        return this.getBlock(blockPos).getLightOpacity();
    }
    
    public ClassInheritanceMultiMap<Entity>[] getEntityLists() {
        return this.entityLists;
    }
    
    private void checkSkylightNeighborHeight(final int n, final int n2, final int n3) {
        final int y = this.worldObj.getHeight(new BlockPos(n, "".length(), n2)).getY();
        if (y > n3) {
            this.updateSkylightNeighborHeight(n, n2, n3, y + " ".length());
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else if (y < n3) {
            this.updateSkylightNeighborHeight(n, n2, y, n3 + " ".length());
        }
    }
    
    public void setStorageArrays(final ExtendedBlockStorage[] array) {
        if (this.storageArrays.length != array.length) {
            Chunk.logger.warn(Chunk.I[0x27 ^ 0x29] + array.length + Chunk.I[0x9F ^ 0x90] + this.storageArrays.length);
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            int i = "".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
            while (i < this.storageArrays.length) {
                this.storageArrays[i] = array[i];
                ++i;
            }
        }
    }
    
    public byte[] getBiomeArray() {
        return this.blockBiomeArray;
    }
    
    public void setBiomeArray(final byte[] array) {
        if (this.blockBiomeArray.length != array.length) {
            Chunk.logger.warn(Chunk.I[0x8D ^ 0x9D] + array.length + Chunk.I[0x59 ^ 0x48] + this.blockBiomeArray.length);
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            int i = "".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
            while (i < this.blockBiomeArray.length) {
                this.blockBiomeArray[i] = array[i];
                ++i;
            }
        }
    }
    
    private Block getBlock0(final int n, final int n2, final int n3) {
        Block block = Blocks.air;
        if (n2 >= 0 && n2 >> (0x7 ^ 0x3) < this.storageArrays.length) {
            final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[n2 >> (0x91 ^ 0x95)];
            if (extendedBlockStorage != null) {
                try {
                    block = extendedBlockStorage.getBlockByExtId(n, n2 & (0x18 ^ 0x17), n3);
                    "".length();
                    if (3 == 2) {
                        throw null;
                    }
                }
                catch (Throwable t) {
                    throw new ReportedException(CrashReport.makeCrashReport(t, Chunk.I[" ".length()]));
                }
            }
        }
        return block;
    }
    
    public void setLightFor(final EnumSkyBlock enumSkyBlock, final BlockPos blockPos, final int n) {
        final int n2 = blockPos.getX() & (0x79 ^ 0x76);
        final int y = blockPos.getY();
        final int n3 = blockPos.getZ() & (0x6B ^ 0x64);
        ExtendedBlockStorage extendedBlockStorage = this.storageArrays[y >> (0xA8 ^ 0xAC)];
        if (extendedBlockStorage == null) {
            final ExtendedBlockStorage[] storageArrays = this.storageArrays;
            final int n4 = y >> (0xA0 ^ 0xA4);
            final int n5 = y >> (0x1 ^ 0x5) << (0x71 ^ 0x75);
            int n6;
            if (this.worldObj.provider.getHasNoSky()) {
                n6 = "".length();
                "".length();
                if (0 >= 3) {
                    throw null;
                }
            }
            else {
                n6 = " ".length();
            }
            final ExtendedBlockStorage extendedBlockStorage2 = new ExtendedBlockStorage(n5, n6 != 0);
            storageArrays[n4] = extendedBlockStorage2;
            extendedBlockStorage = extendedBlockStorage2;
            this.generateSkylightMap();
        }
        this.isModified = (" ".length() != 0);
        if (enumSkyBlock == EnumSkyBlock.SKY) {
            if (!this.worldObj.provider.getHasNoSky()) {
                extendedBlockStorage.setExtSkylightValue(n2, y & (0x89 ^ 0x86), n3, n);
                "".length();
                if (-1 == 0) {
                    throw null;
                }
            }
        }
        else if (enumSkyBlock == EnumSkyBlock.BLOCK) {
            extendedBlockStorage.setExtBlocklightValue(n2, y & (0x41 ^ 0x4E), n3, n);
        }
    }
    
    protected void generateHeightMap() {
        final int topFilledSegment = this.getTopFilledSegment();
        this.heightMapMinimum = 1052866126 + 297823653 + 445603966 + 351189902;
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < (0xB0 ^ 0xA0)) {
            int j = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (j < (0x80 ^ 0x90)) {
                this.precipitationHeightMap[i + (j << (0x48 ^ 0x4C))] = -(53 + 146 + 26 + 774);
                int k = topFilledSegment + (0x56 ^ 0x46);
                "".length();
                if (2 <= 0) {
                    throw null;
                }
                while (k > 0) {
                    if (this.getBlock0(i, k - " ".length(), j).getLightOpacity() != 0) {
                        if ((this.heightMap[j << (0x71 ^ 0x75) | i] = k) >= this.heightMapMinimum) {
                            break;
                        }
                        this.heightMapMinimum = k;
                        "".length();
                        if (2 >= 4) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        --k;
                    }
                }
                ++j;
            }
            ++i;
        }
        this.isModified = (" ".length() != 0);
    }
    
    private static void I() {
        (I = new String[0x24 ^ 0x30])["".length()] = I("\u001a)5 \r\u000b'\u0011)\u0018\u001b", "hLVHh");
        Chunk.I[" ".length()] = I("\f'?\u001e\u0018%%k\b\u001d$! ", "KBKjq");
        Chunk.I["  ".length()] = I("\u0015 ;\u000b*w.1\u0001/0l3\u00075", "WLThA");
        Chunk.I["   ".length()] = I("\u0000-\u0019\u0000\u0003%-\u0014", "LBzaw");
        Chunk.I[0x6B ^ 0x6F] = I("\u000e')$\u001fl)#.\u001a+k!(\u0000", "LKFGt");
        Chunk.I[0x53 ^ 0x56] = I(")\u0017/\u00020\f\u0017\"", "exLcD");
        Chunk.I[0x74 ^ 0x72] = I("\u001f\u000e&\u0003<6\fr\u001597\b9W&,\n&\u0012", "XkRwU");
        Chunk.I[0x15 ^ 0x12] = I(";&\n\u0002 Y(\u0000\b%\u001ej\u0002\u000e?", "yJeaK");
        Chunk.I[0x94 ^ 0x9C] = I("%\u00023&:\u0000\u0002>", "imPGN");
        Chunk.I[0x8B ^ 0x82] = I("<\":;1K<:67\u001f9:;wKx", "kPUUV");
        Chunk.I[0x0 ^ 0xA] = I("Cq", "oQcca");
        Chunk.I[0x1F ^ 0x14] = I("MZ1*\u001b\u0011\u0016&b\u0016\u0001Zj", "dzBBt");
        Chunk.I[0x7C ^ 0x70] = I("VU", "zuWIb");
        Chunk.I[0x8 ^ 0x5] = I("dIl", "MeLEG");
        Chunk.I[0x91 ^ 0x9F] = I("\"'\u001e\u0000\u0007A&\u0004\u0018C\u0012-\u001fL\u000f\u0004>\u000e\u0000C\u0002 \u001e\u0002\bA;\u000e\u000f\u0017\b'\u0005\u001fOA)\u0019\u001e\u0002\u0018h\u0007\t\r\u0006<\u0003L\n\u0012h", "aHklc");
        Chunk.I[0x7D ^ 0x72] = I("O!;#8\n)1p#\th", "oHUPL");
        Chunk.I[0x62 ^ 0x72] = I("\u00107\u0014)*s6\u000e1n =\u0015e\"6.\u0004)n00\u0014+%s:\b*#6+Me/!*\u0000<n?=\u000f\":;x\b6n", "SXaEN");
        Chunk.I[0x78 ^ 0x69] = I("O\u001e74:\n\u0016=g!\tW", "owYGN");
        Chunk.I[0x90 ^ 0x82] = I("\u0012\u001c\u0012\u001c1q\u001d\b\u0004u\"\u0016\u0013P94\u0005\u0002\u001cu2\u001b\u0012\u001e>q\u001b\u0002\u001929\u0007\n\u0011%}S\u0006\u0002'0\nG\u001c0?\u0014\u0013\u0018u8\u0000G", "QsgpU");
        Chunk.I[0xB6 ^ 0xA5] = I("d\u001b\u0002\u0016\u001a!\u0013\bE\u0001\"R", "Drlen");
    }
    
    public int getLowestHeight() {
        return this.heightMapMinimum;
    }
    
    public boolean isTerrainPopulated() {
        return this.isTerrainPopulated;
    }
    
    public boolean isLoaded() {
        return this.isChunkLoaded;
    }
    
    public Block getBlock(final BlockPos blockPos) {
        try {
            return this.getBlock0(blockPos.getX() & (0x91 ^ 0x9E), blockPos.getY(), blockPos.getZ() & (0x59 ^ 0x56));
        }
        catch (ReportedException ex) {
            ex.getCrashReport().makeCategory(Chunk.I[0x60 ^ 0x64]).addCrashSectionCallable(Chunk.I[0x64 ^ 0x61], new Callable<String>(this, blockPos) {
                final Chunk this$0;
                private final BlockPos val$pos;
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
                
                @Override
                public String call() throws Exception {
                    return CrashReportCategory.getCoordinateInfo(this.val$pos);
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
                        if (3 <= 0) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
            throw ex;
        }
    }
    
    public void onChunkUnload() {
        this.isChunkLoaded = ("".length() != 0);
        final Iterator<TileEntity> iterator = this.chunkTileEntityMap.values().iterator();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.worldObj.markTileEntityForRemoval(iterator.next());
        }
        int i = "".length();
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (i < this.entityLists.length) {
            this.worldObj.unloadEntities(this.entityLists[i]);
            ++i;
        }
    }
    
    private int getBlockMetadata(final int n, final int n2, final int n3) {
        if (n2 >> (0x22 ^ 0x26) >= this.storageArrays.length) {
            return "".length();
        }
        final ExtendedBlockStorage extendedBlockStorage = this.storageArrays[n2 >> (0x23 ^ 0x27)];
        int n4;
        if (extendedBlockStorage != null) {
            n4 = extendedBlockStorage.getExtBlockMetadata(n, n2 & (0x7C ^ 0x73), n3);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        return n4;
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    public BiomeGenBase getBiome(final BlockPos blockPos, final WorldChunkManager worldChunkManager) {
        final int n = blockPos.getX() & (0xBC ^ 0xB3);
        final int n2 = blockPos.getZ() & (0x76 ^ 0x79);
        int biomeID = this.blockBiomeArray[n2 << (0x2E ^ 0x2A) | n] & 50 + 162 - 86 + 129;
        if (biomeID == 40 + 220 - 200 + 195) {
            biomeID = worldChunkManager.getBiomeGenerator(blockPos, BiomeGenBase.plains).biomeID;
            this.blockBiomeArray[n2 << (0xA7 ^ 0xA3) | n] = (byte)(biomeID & 233 + 224 - 325 + 123);
        }
        final BiomeGenBase biome = BiomeGenBase.getBiome(biomeID);
        BiomeGenBase plains;
        if (biome == null) {
            plains = BiomeGenBase.plains;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            plains = biome;
        }
        return plains;
    }
    
    public void func_150804_b(final boolean b) {
        if (this.isGapLightingUpdated && !this.worldObj.provider.getHasNoSky() && !b) {
            this.recheckGaps(this.worldObj.isRemote);
        }
        this.field_150815_m = (" ".length() != 0);
        if (!this.isLightPopulated && this.isTerrainPopulated) {
            this.func_150809_p();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        while (!this.tileEntityPosQueue.isEmpty()) {
            final BlockPos blockPos = this.tileEntityPosQueue.poll();
            if (this.getTileEntity(blockPos, EnumCreateEntityType.CHECK) == null && this.getBlock(blockPos).hasTileEntity()) {
                this.worldObj.setTileEntity(blockPos, this.createNewTileEntity(blockPos));
                this.worldObj.markBlockRangeForRenderUpdate(blockPos, blockPos);
            }
        }
    }
    
    public void setChunkLoaded(final boolean isChunkLoaded) {
        this.isChunkLoaded = isChunkLoaded;
    }
    
    private void recheckGaps(final boolean b) {
        this.worldObj.theProfiler.startSection(Chunk.I["".length()]);
        if (this.worldObj.isAreaLoaded(new BlockPos(this.xPosition * (0xF ^ 0x1F) + (0x5A ^ 0x52), "".length(), this.zPosition * (0xBF ^ 0xAF) + (0x5A ^ 0x52)), 0x98 ^ 0x88)) {
            int i = "".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
            while (i < (0x54 ^ 0x44)) {
                int j = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (j < (0x96 ^ 0x86)) {
                    if (this.updateSkylightColumns[i + j * (0xD5 ^ 0xC5)]) {
                        this.updateSkylightColumns[i + j * (0x4B ^ 0x5B)] = ("".length() != 0);
                        final int heightValue = this.getHeightValue(i, j);
                        final int n = this.xPosition * (0x60 ^ 0x70) + i;
                        final int n2 = this.zPosition * (0x1D ^ 0xD) + j;
                        int min = 1456832465 + 1816036416 - 1812797881 + 687412647;
                        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
                        "".length();
                        if (2 <= -1) {
                            throw null;
                        }
                        while (iterator.hasNext()) {
                            final EnumFacing enumFacing = iterator.next();
                            min = Math.min(min, this.worldObj.getChunksLowestHorizon(n + enumFacing.getFrontOffsetX(), n2 + enumFacing.getFrontOffsetZ()));
                        }
                        this.checkSkylightNeighborHeight(n, n2, min);
                        final Iterator iterator2 = EnumFacing.Plane.HORIZONTAL.iterator();
                        "".length();
                        if (0 == 3) {
                            throw null;
                        }
                        while (iterator2.hasNext()) {
                            final EnumFacing enumFacing2 = iterator2.next();
                            this.checkSkylightNeighborHeight(n + enumFacing2.getFrontOffsetX(), n2 + enumFacing2.getFrontOffsetZ(), heightValue);
                        }
                        if (b) {
                            this.worldObj.theProfiler.endSection();
                            return;
                        }
                    }
                    ++j;
                }
                ++i;
            }
            this.isGapLightingUpdated = ("".length() != 0);
        }
        this.worldObj.theProfiler.endSection();
    }
    
    private void func_177441_y() {
        int i = "".length();
        "".length();
        if (2 <= 0) {
            throw null;
        }
        while (i < this.updateSkylightColumns.length) {
            this.updateSkylightColumns[i] = (" ".length() != 0);
            ++i;
        }
        this.recheckGaps("".length() != 0);
    }
    
    public Random getRandomWithSeed(final long n) {
        return new Random(this.worldObj.getSeed() + this.xPosition * this.xPosition * (4420372 + 2488511 - 4443786 + 2522045) + this.xPosition * (3671823 + 880641 - 2847354 + 4242501) + this.zPosition * this.zPosition * 4392871L + this.zPosition * (22637 + 224101 + 54049 + 88924) ^ n);
    }
    
    public void setTerrainPopulated(final boolean isTerrainPopulated) {
        this.isTerrainPopulated = isTerrainPopulated;
    }
    
    public void addEntity(final Entity entity) {
        this.hasEntities = (" ".length() != 0);
        final int floor_double = MathHelper.floor_double(entity.posX / 16.0);
        final int floor_double2 = MathHelper.floor_double(entity.posZ / 16.0);
        if (floor_double != this.xPosition || floor_double2 != this.zPosition) {
            final Logger logger = Chunk.logger;
            final String string = Chunk.I[0xA2 ^ 0xAB] + floor_double + Chunk.I[0x3C ^ 0x36] + floor_double2 + Chunk.I[0x33 ^ 0x38] + this.xPosition + Chunk.I[0x1F ^ 0x13] + this.zPosition + Chunk.I[0x35 ^ 0x38] + entity;
            final Object[] array = new Object[" ".length()];
            array["".length()] = entity;
            logger.warn(string, array);
            entity.setDead();
        }
        int chunkCoordY = MathHelper.floor_double(entity.posY / 16.0);
        if (chunkCoordY < 0) {
            chunkCoordY = "".length();
        }
        if (chunkCoordY >= this.entityLists.length) {
            chunkCoordY = this.entityLists.length - " ".length();
        }
        entity.addedToChunk = (" ".length() != 0);
        entity.chunkCoordX = this.xPosition;
        entity.chunkCoordY = chunkCoordY;
        entity.chunkCoordZ = this.zPosition;
        this.entityLists[chunkCoordY].add(entity);
    }
    
    public void resetRelightChecks() {
        this.queuedLightChecks = "".length();
    }
    
    public int getHeightValue(final int n, final int n2) {
        return this.heightMap[n2 << (0x3D ^ 0x39) | n];
    }
    
    public enum EnumCreateEntityType
    {
        private static final EnumCreateEntityType[] ENUM$VALUES;
        
        IMMEDIATE(EnumCreateEntityType.I["".length()], "".length()), 
        QUEUED(EnumCreateEntityType.I[" ".length()], " ".length()), 
        CHECK(EnumCreateEntityType.I["  ".length()], "  ".length());
        
        private static final String[] I;
        
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
        
        static {
            I();
            final EnumCreateEntityType[] enum$VALUES = new EnumCreateEntityType["   ".length()];
            enum$VALUES["".length()] = EnumCreateEntityType.IMMEDIATE;
            enum$VALUES[" ".length()] = EnumCreateEntityType.QUEUED;
            enum$VALUES["  ".length()] = EnumCreateEntityType.CHECK;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String["   ".length()])["".length()] = I("\u001b\u0017\u001b\r\u0010\u001b\u001b\u0002\r", "RZVHT");
            EnumCreateEntityType.I[" ".length()] = I("\u0001\u0013\u0000\u0010,\u0014", "PFEEi");
            EnumCreateEntityType.I["  ".length()] = I("\u001b-\u001d\b\u0019", "XeXKR");
        }
        
        private EnumCreateEntityType(final String s, final int n) {
        }
    }
}
