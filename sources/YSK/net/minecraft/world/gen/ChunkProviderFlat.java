package net.minecraft.world.gen;

import net.minecraft.block.state.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.biome.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.world.gen.structure.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class ChunkProviderFlat implements IChunkProvider
{
    private Random random;
    private final IBlockState[] cachedBlockIDs;
    private WorldGenLakes waterLakeGenerator;
    private WorldGenLakes lavaLakeGenerator;
    private final FlatGeneratorInfo flatWorldGenInfo;
    private final List<MapGenStructure> structureGenerators;
    private final boolean hasDecoration;
    private World worldObj;
    private final boolean hasDungeons;
    private static final String[] I;
    
    @Override
    public Chunk provideChunk(final int n, final int n2) {
        final ChunkPrimer chunkPrimer = new ChunkPrimer();
        int i = "".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i < this.cachedBlockIDs.length) {
            final IBlockState blockState = this.cachedBlockIDs[i];
            if (blockState != null) {
                int j = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                while (j < (0xA2 ^ 0xB2)) {
                    int k = "".length();
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    while (k < (0xB0 ^ 0xA0)) {
                        chunkPrimer.setBlockState(j, i, k, blockState);
                        ++k;
                    }
                    ++j;
                }
            }
            ++i;
        }
        final Iterator<MapGenStructure> iterator = this.structureGenerators.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().generate(this, this.worldObj, n, n2, chunkPrimer);
        }
        final Chunk chunk = new Chunk(this.worldObj, chunkPrimer, n, n2);
        final BiomeGenBase[] loadBlockGeneratorData = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, n * (0x11 ^ 0x1), n2 * (0xBE ^ 0xAE), 0x4B ^ 0x5B, 0x74 ^ 0x64);
        final byte[] biomeArray = chunk.getBiomeArray();
        int l = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (l < biomeArray.length) {
            biomeArray[l] = (byte)loadBlockGeneratorData[l].biomeID;
            ++l;
        }
        chunk.generateSkylightMap();
        return chunk;
    }
    
    @Override
    public void recreateStructures(final Chunk chunk, final int n, final int n2) {
        final Iterator<MapGenStructure> iterator = this.structureGenerators.iterator();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().generate(this, this.worldObj, n, n2, null);
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
            if (false) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ChunkProviderFlat(final World worldObj, final long n, final boolean b, final String s) {
        this.cachedBlockIDs = new IBlockState[25 + 138 + 1 + 92];
        this.structureGenerators = (List<MapGenStructure>)Lists.newArrayList();
        this.worldObj = worldObj;
        this.random = new Random(n);
        this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(s);
        if (b) {
            final Map<String, Map<String, String>> worldFeatures = this.flatWorldGenInfo.getWorldFeatures();
            if (worldFeatures.containsKey(ChunkProviderFlat.I["".length()])) {
                final Map<String, String> map = worldFeatures.get(ChunkProviderFlat.I[" ".length()]);
                if (!map.containsKey(ChunkProviderFlat.I["  ".length()])) {
                    map.put(ChunkProviderFlat.I["   ".length()], ChunkProviderFlat.I[0x15 ^ 0x11]);
                }
                this.structureGenerators.add(new MapGenVillage(map));
            }
            if (worldFeatures.containsKey(ChunkProviderFlat.I[0x3 ^ 0x6])) {
                this.structureGenerators.add(new MapGenScatteredFeature(worldFeatures.get(ChunkProviderFlat.I[0xC4 ^ 0xC2])));
            }
            if (worldFeatures.containsKey(ChunkProviderFlat.I[0x71 ^ 0x76])) {
                this.structureGenerators.add(new MapGenMineshaft(worldFeatures.get(ChunkProviderFlat.I[0xC ^ 0x4])));
            }
            if (worldFeatures.containsKey(ChunkProviderFlat.I[0xB ^ 0x2])) {
                this.structureGenerators.add(new MapGenStronghold(worldFeatures.get(ChunkProviderFlat.I[0x6E ^ 0x64])));
            }
            if (worldFeatures.containsKey(ChunkProviderFlat.I[0x6C ^ 0x67])) {
                this.structureGenerators.add(new StructureOceanMonument(worldFeatures.get(ChunkProviderFlat.I[0x72 ^ 0x7E])));
            }
        }
        if (this.flatWorldGenInfo.getWorldFeatures().containsKey(ChunkProviderFlat.I[0x27 ^ 0x2A])) {
            this.waterLakeGenerator = new WorldGenLakes(Blocks.water);
        }
        if (this.flatWorldGenInfo.getWorldFeatures().containsKey(ChunkProviderFlat.I[0x84 ^ 0x8A])) {
            this.lavaLakeGenerator = new WorldGenLakes(Blocks.lava);
        }
        this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey(ChunkProviderFlat.I[0x56 ^ 0x59]);
        int length = "".length();
        int n2 = "".length();
        int n3 = " ".length();
        final Iterator<FlatLayerInfo> iterator = this.flatWorldGenInfo.getFlatLayers().iterator();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final FlatLayerInfo flatLayerInfo = iterator.next();
            int i = flatLayerInfo.getMinY();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (i < flatLayerInfo.getMinY() + flatLayerInfo.getLayerCount()) {
                final IBlockState func_175900_c = flatLayerInfo.func_175900_c();
                if (func_175900_c.getBlock() != Blocks.air) {
                    n3 = "".length();
                    this.cachedBlockIDs[i] = func_175900_c;
                }
                ++i;
            }
            if (flatLayerInfo.func_175900_c().getBlock() == Blocks.air) {
                n2 += flatLayerInfo.getLayerCount();
                "".length();
                if (3 < 2) {
                    throw null;
                }
                continue;
            }
            else {
                length += flatLayerInfo.getLayerCount() + n2;
                n2 = "".length();
            }
        }
        worldObj.func_181544_b(length);
        int hasDecoration;
        if (n3 != 0) {
            hasDecoration = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            hasDecoration = (this.flatWorldGenInfo.getWorldFeatures().containsKey(ChunkProviderFlat.I[0x9E ^ 0x8E]) ? 1 : 0);
        }
        this.hasDecoration = (hasDecoration != 0);
    }
    
    @Override
    public String makeString() {
        return ChunkProviderFlat.I[0x59 ^ 0x48];
    }
    
    @Override
    public BlockPos getStrongholdGen(final World world, final String s, final BlockPos blockPos) {
        if (ChunkProviderFlat.I[0xF ^ 0x1D].equals(s)) {
            final Iterator<MapGenStructure> iterator = this.structureGenerators.iterator();
            "".length();
            if (3 == 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                final MapGenStructure mapGenStructure = iterator.next();
                if (mapGenStructure instanceof MapGenStronghold) {
                    return mapGenStructure.getClosestStrongholdPos(world, blockPos);
                }
            }
        }
        return null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return "".length();
    }
    
    @Override
    public void populate(final IChunkProvider chunkProvider, final int n, final int n2) {
        final int n3 = n * (0x11 ^ 0x1);
        final int n4 = n2 * (0xB4 ^ 0xA4);
        final BlockPos blockPos = new BlockPos(n3, "".length(), n4);
        final BiomeGenBase biomeGenForCoords = this.worldObj.getBiomeGenForCoords(new BlockPos(n3 + (0x51 ^ 0x41), "".length(), n4 + (0x1A ^ 0xA)));
        int length = "".length();
        this.random.setSeed(this.worldObj.getSeed());
        this.random.setSeed(n * (this.random.nextLong() / 2L * 2L + 1L) + n2 * (this.random.nextLong() / 2L * 2L + 1L) ^ this.worldObj.getSeed());
        final ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(n, n2);
        final Iterator<MapGenStructure> iterator = this.structureGenerators.iterator();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final MapGenStructure mapGenStructure = iterator.next();
            final boolean generateStructure = mapGenStructure.generateStructure(this.worldObj, this.random, chunkCoordIntPair);
            if (mapGenStructure instanceof MapGenVillage) {
                length |= (generateStructure ? 1 : 0);
            }
        }
        if (this.waterLakeGenerator != null && length == 0 && this.random.nextInt(0xA5 ^ 0xA1) == 0) {
            this.waterLakeGenerator.generate(this.worldObj, this.random, blockPos.add(this.random.nextInt(0x2F ^ 0x3F) + (0x5F ^ 0x57), this.random.nextInt(219 + 104 - 161 + 94), this.random.nextInt(0x83 ^ 0x93) + (0xA ^ 0x2)));
        }
        if (this.lavaLakeGenerator != null && length == 0 && this.random.nextInt(0xB8 ^ 0xB0) == 0) {
            final BlockPos add = blockPos.add(this.random.nextInt(0x70 ^ 0x60) + (0x72 ^ 0x7A), this.random.nextInt(this.random.nextInt(119 + 246 - 277 + 160) + (0x6E ^ 0x66)), this.random.nextInt(0x32 ^ 0x22) + (0x9F ^ 0x97));
            if (add.getY() < this.worldObj.func_181545_F() || this.random.nextInt(0x7B ^ 0x71) == 0) {
                this.lavaLakeGenerator.generate(this.worldObj, this.random, add);
            }
        }
        if (this.hasDungeons) {
            int i = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (i < (0x3C ^ 0x34)) {
                new WorldGenDungeons().generate(this.worldObj, this.random, blockPos.add(this.random.nextInt(0x7F ^ 0x6F) + (0x19 ^ 0x11), this.random.nextInt(8 + 66 + 109 + 73), this.random.nextInt(0x7E ^ 0x6E) + (0xA2 ^ 0xAA)));
                ++i;
            }
        }
        if (this.hasDecoration) {
            biomeGenForCoords.decorate(this.worldObj, this.random, blockPos);
        }
    }
    
    @Override
    public boolean saveChunks(final boolean b, final IProgressUpdate progressUpdate) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean canSave() {
        return " ".length() != 0;
    }
    
    @Override
    public void saveExtraData() {
    }
    
    private static void I() {
        (I = new String[0x2E ^ 0x3D])["".length()] = I("\u00011\u0002\u000e\b\u0010=", "wXnbi");
        ChunkProviderFlat.I[" ".length()] = I("38\u0001\u0005\t\"4", "EQmih");
        ChunkProviderFlat.I["  ".length()] = I("9\u00075.", "JnOKr");
        ChunkProviderFlat.I["   ".length()] = I("\"\u001e\u001e\u0001", "QwddZ");
        ChunkProviderFlat.I[0x84 ^ 0x80] = I("W", "fMTIu");
        ChunkProviderFlat.I[0x92 ^ 0x97] = I("\u00161\f\"\u0012+i", "tXcOw");
        ChunkProviderFlat.I[0x4E ^ 0x48] = I("\u0010\u0000\t$,-X", "rifII");
        ChunkProviderFlat.I[0x66 ^ 0x61] = I("\u001a\u0019-\u0015\u0012\u001f\u0011%\u0004", "wpCpa");
        ChunkProviderFlat.I[0xAD ^ 0xA5] = I("&9\"\u00107#1*\u0001", "KPLuD");
        ChunkProviderFlat.I[0x3E ^ 0x37] = I("5\u0018\u001c&%!\u0004\u0001%/", "FlnIK");
        ChunkProviderFlat.I[0x2B ^ 0x21] = I("9\u00024,\u001f-\u001e)/\u0015", "JvFCq");
        ChunkProviderFlat.I[0xB8 ^ 0xB3] = I(" \u00056\u0011\"\"\t=\u0005!*\b'", "OfSpL");
        ChunkProviderFlat.I[0x54 ^ 0x58] = I("!\u001a!\u0014\f#\u0016*\u0000\u000f+\u00170", "NyDub");
        ChunkProviderFlat.I[0x72 ^ 0x7F] = I("\u0002\t\u001d?", "nhvZd");
        ChunkProviderFlat.I[0x4 ^ 0xA] = I("\u0002\u000f\u0012#%\u0002\u000f\u000f'", "nndBz");
        ChunkProviderFlat.I[0x6F ^ 0x60] = I("\u000b &!\n\u0000;", "oUHFo");
        ChunkProviderFlat.I[0xAA ^ 0xBA] = I("+\r*?;.\u001c ?'", "OhIPI");
        ChunkProviderFlat.I[0x8F ^ 0x9E] = I("(\u000b\b9)\u000b\u0011\f!6\u0001\u0012\u001b.\u0000", "ngiMe");
        ChunkProviderFlat.I[0xB ^ 0x19] = I("$&3&\u0019\u0010:.%\u0013", "wRAIw");
    }
    
    @Override
    public Chunk provideChunk(final BlockPos blockPos) {
        return this.provideChunk(blockPos.getX() >> (0x61 ^ 0x65), blockPos.getZ() >> (0x6B ^ 0x6F));
    }
    
    @Override
    public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(final EnumCreatureType enumCreatureType, final BlockPos blockPos) {
        return this.worldObj.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return "".length() != 0;
    }
    
    @Override
    public boolean chunkExists(final int n, final int n2) {
        return " ".length() != 0;
    }
    
    @Override
    public boolean func_177460_a(final IChunkProvider chunkProvider, final Chunk chunk, final int n, final int n2) {
        return "".length() != 0;
    }
    
    static {
        I();
    }
}
