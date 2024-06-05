package net.minecraft.src;

import java.util.*;

public class ChunkProviderFlat implements IChunkProvider
{
    private World worldObj;
    private Random random;
    private final byte[] field_82700_c;
    private final byte[] field_82698_d;
    private final FlatGeneratorInfo field_82699_e;
    private final List structureGenerators;
    private final boolean field_82697_g;
    private final boolean field_82702_h;
    private WorldGenLakes waterLakeGenerator;
    private WorldGenLakes lavaLakeGenerator;
    
    public ChunkProviderFlat(final World par1World, final long par2, final boolean par4, final String par5Str) {
        this.field_82700_c = new byte[256];
        this.field_82698_d = new byte[256];
        this.structureGenerators = new ArrayList();
        this.worldObj = par1World;
        this.random = new Random(par2);
        this.field_82699_e = FlatGeneratorInfo.createFlatGeneratorFromString(par5Str);
        if (par4) {
            final Map var6 = this.field_82699_e.getWorldFeatures();
            if (var6.containsKey("village")) {
                final Map var7 = var6.get("village");
                if (!var7.containsKey("size")) {
                    var7.put("size", "1");
                }
                this.structureGenerators.add(new MapGenVillage(var7));
            }
            if (var6.containsKey("biome_1")) {
                this.structureGenerators.add(new MapGenScatteredFeature(var6.get("biome_1")));
            }
            if (var6.containsKey("mineshaft")) {
                this.structureGenerators.add(new MapGenMineshaft(var6.get("mineshaft")));
            }
            if (var6.containsKey("stronghold")) {
                this.structureGenerators.add(new MapGenStronghold(var6.get("stronghold")));
            }
        }
        this.field_82697_g = this.field_82699_e.getWorldFeatures().containsKey("decoration");
        if (this.field_82699_e.getWorldFeatures().containsKey("lake")) {
            this.waterLakeGenerator = new WorldGenLakes(Block.waterStill.blockID);
        }
        if (this.field_82699_e.getWorldFeatures().containsKey("lava_lake")) {
            this.lavaLakeGenerator = new WorldGenLakes(Block.lavaStill.blockID);
        }
        this.field_82702_h = this.field_82699_e.getWorldFeatures().containsKey("dungeon");
        for (final FlatLayerInfo var9 : this.field_82699_e.getFlatLayers()) {
            for (int var10 = var9.getMinY(); var10 < var9.getMinY() + var9.getLayerCount(); ++var10) {
                this.field_82700_c[var10] = (byte)(var9.getFillBlock() & 0xFF);
                this.field_82698_d[var10] = (byte)var9.getFillBlockMeta();
            }
        }
    }
    
    @Override
    public Chunk loadChunk(final int par1, final int par2) {
        return this.provideChunk(par1, par2);
    }
    
    @Override
    public Chunk provideChunk(final int par1, final int par2) {
        final Chunk var3 = new Chunk(this.worldObj, par1, par2);
        for (int var4 = 0; var4 < this.field_82700_c.length; ++var4) {
            final int var5 = var4 >> 4;
            ExtendedBlockStorage var6 = var3.getBlockStorageArray()[var5];
            if (var6 == null) {
                var6 = new ExtendedBlockStorage(var4, !this.worldObj.provider.hasNoSky);
                var3.getBlockStorageArray()[var5] = var6;
            }
            for (int var7 = 0; var7 < 16; ++var7) {
                for (int var8 = 0; var8 < 16; ++var8) {
                    var6.setExtBlockID(var7, var4 & 0xF, var8, this.field_82700_c[var4] & 0xFF);
                    var6.setExtBlockMetadata(var7, var4 & 0xF, var8, this.field_82698_d[var4]);
                }
            }
        }
        var3.generateSkylightMap();
        final BiomeGenBase[] var9 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, par1 * 16, par2 * 16, 16, 16);
        final byte[] var10 = var3.getBiomeArray();
        for (int var11 = 0; var11 < var10.length; ++var11) {
            var10[var11] = (byte)var9[var11].biomeID;
        }
        for (final MapGenStructure var13 : this.structureGenerators) {
            var13.generate(this, this.worldObj, par1, par2, null);
        }
        var3.generateSkylightMap();
        return var3;
    }
    
    @Override
    public boolean chunkExists(final int par1, final int par2) {
        return true;
    }
    
    @Override
    public void populate(final IChunkProvider par1IChunkProvider, final int par2, final int par3) {
        final int var4 = par2 * 16;
        final int var5 = par3 * 16;
        final BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
        boolean var7 = false;
        this.random.setSeed(this.worldObj.getSeed());
        final long var8 = this.random.nextLong() / 2L * 2L + 1L;
        final long var9 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed(par2 * var8 + par3 * var9 ^ this.worldObj.getSeed());
        for (final MapGenStructure var11 : this.structureGenerators) {
            final boolean var12 = var11.generateStructuresInChunk(this.worldObj, this.random, par2, par3);
            if (var11 instanceof MapGenVillage) {
                var7 |= var12;
            }
        }
        if (this.waterLakeGenerator != null && !var7 && this.random.nextInt(4) == 0) {
            final int var13 = var4 + this.random.nextInt(16) + 8;
            final int var14 = this.random.nextInt(128);
            final int var15 = var5 + this.random.nextInt(16) + 8;
            this.waterLakeGenerator.generate(this.worldObj, this.random, var13, var14, var15);
        }
        if (this.lavaLakeGenerator != null && !var7 && this.random.nextInt(8) == 0) {
            final int var13 = var4 + this.random.nextInt(16) + 8;
            final int var14 = this.random.nextInt(this.random.nextInt(120) + 8);
            final int var15 = var5 + this.random.nextInt(16) + 8;
            if (var14 < 63 || this.random.nextInt(10) == 0) {
                this.lavaLakeGenerator.generate(this.worldObj, this.random, var13, var14, var15);
            }
        }
        if (this.field_82702_h) {
            for (int var13 = 0; var13 < 8; ++var13) {
                final int var14 = var4 + this.random.nextInt(16) + 8;
                final int var15 = this.random.nextInt(128);
                final int var16 = var5 + this.random.nextInt(16) + 8;
                new WorldGenDungeons().generate(this.worldObj, this.random, var14, var15, var16);
            }
        }
        if (this.field_82697_g) {
            var6.decorate(this.worldObj, this.random, var4, var5);
        }
    }
    
    @Override
    public boolean saveChunks(final boolean par1, final IProgressUpdate par2IProgressUpdate) {
        return true;
    }
    
    @Override
    public void func_104112_b() {
    }
    
    @Override
    public boolean unloadQueuedChunks() {
        return false;
    }
    
    @Override
    public boolean canSave() {
        return true;
    }
    
    @Override
    public String makeString() {
        return "FlatLevelSource";
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType par1EnumCreatureType, final int par2, final int par3, final int par4) {
        final BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
        return (var5 == null) ? null : var5.getSpawnableList(par1EnumCreatureType);
    }
    
    @Override
    public ChunkPosition findClosestStructure(final World par1World, final String par2Str, final int par3, final int par4, final int par5) {
        if ("Stronghold".equals(par2Str)) {
            for (final MapGenStructure var7 : this.structureGenerators) {
                if (var7 instanceof MapGenStronghold) {
                    return var7.getNearestInstance(par1World, par3, par4, par5);
                }
            }
        }
        return null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public void recreateStructures(final int par1, final int par2) {
        for (final MapGenStructure var4 : this.structureGenerators) {
            var4.generate(this, this.worldObj, par1, par2, null);
        }
    }
}
