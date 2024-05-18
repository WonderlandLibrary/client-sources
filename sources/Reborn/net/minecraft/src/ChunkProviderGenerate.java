package net.minecraft.src;

import java.util.*;

public class ChunkProviderGenerate implements IChunkProvider
{
    private Random rand;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    private NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private World worldObj;
    private final boolean mapFeaturesEnabled;
    private double[] noiseArray;
    private double[] stoneNoise;
    private MapGenBase caveGenerator;
    private MapGenStronghold strongholdGenerator;
    private MapGenVillage villageGenerator;
    private MapGenMineshaft mineshaftGenerator;
    private MapGenScatteredFeature scatteredFeatureGenerator;
    private MapGenBase ravineGenerator;
    private BiomeGenBase[] biomesForGeneration;
    double[] noise3;
    double[] noise1;
    double[] noise2;
    double[] noise5;
    double[] noise6;
    float[] parabolicField;
    int[][] field_73219_j;
    
    public ChunkProviderGenerate(final World par1World, final long par2, final boolean par4) {
        this.stoneNoise = new double[256];
        this.caveGenerator = new MapGenCaves();
        this.strongholdGenerator = new MapGenStronghold();
        this.villageGenerator = new MapGenVillage();
        this.mineshaftGenerator = new MapGenMineshaft();
        this.scatteredFeatureGenerator = new MapGenScatteredFeature();
        this.ravineGenerator = new MapGenRavine();
        this.field_73219_j = new int[32][32];
        this.worldObj = par1World;
        this.mapFeaturesEnabled = par4;
        this.rand = new Random(par2);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.rand, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
    }
    
    public void generateTerrain(final int par1, final int par2, final byte[] par3ArrayOfByte) {
        final byte var4 = 4;
        final byte var5 = 16;
        final byte var6 = 63;
        final int var7 = var4 + 1;
        final byte var8 = 17;
        final int var9 = var4 + 1;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, var7 + 5, var9 + 5);
        this.noiseArray = this.initializeNoiseField(this.noiseArray, par1 * var4, 0, par2 * var4, var7, var8, var9);
        for (int var10 = 0; var10 < var4; ++var10) {
            for (int var11 = 0; var11 < var4; ++var11) {
                for (int var12 = 0; var12 < var5; ++var12) {
                    final double var13 = 0.125;
                    double var14 = this.noiseArray[((var10 + 0) * var9 + var11 + 0) * var8 + var12 + 0];
                    double var15 = this.noiseArray[((var10 + 0) * var9 + var11 + 1) * var8 + var12 + 0];
                    double var16 = this.noiseArray[((var10 + 1) * var9 + var11 + 0) * var8 + var12 + 0];
                    double var17 = this.noiseArray[((var10 + 1) * var9 + var11 + 1) * var8 + var12 + 0];
                    final double var18 = (this.noiseArray[((var10 + 0) * var9 + var11 + 0) * var8 + var12 + 1] - var14) * var13;
                    final double var19 = (this.noiseArray[((var10 + 0) * var9 + var11 + 1) * var8 + var12 + 1] - var15) * var13;
                    final double var20 = (this.noiseArray[((var10 + 1) * var9 + var11 + 0) * var8 + var12 + 1] - var16) * var13;
                    final double var21 = (this.noiseArray[((var10 + 1) * var9 + var11 + 1) * var8 + var12 + 1] - var17) * var13;
                    for (int var22 = 0; var22 < 8; ++var22) {
                        final double var23 = 0.25;
                        double var24 = var14;
                        double var25 = var15;
                        final double var26 = (var16 - var14) * var23;
                        final double var27 = (var17 - var15) * var23;
                        for (int var28 = 0; var28 < 4; ++var28) {
                            int var29 = var28 + var10 * 4 << 11 | 0 + var11 * 4 << 7 | var12 * 8 + var22;
                            final short var30 = 128;
                            var29 -= var30;
                            final double var31 = 0.25;
                            final double var32 = (var25 - var24) * var31;
                            double var33 = var24 - var32;
                            for (int var34 = 0; var34 < 4; ++var34) {
                                if ((var33 += var32) > 0.0) {
                                    par3ArrayOfByte[var29 += var30] = (byte)Block.stone.blockID;
                                }
                                else if (var12 * 8 + var22 < var6) {
                                    par3ArrayOfByte[var29 += var30] = (byte)Block.waterStill.blockID;
                                }
                                else {
                                    par3ArrayOfByte[var29 += var30] = 0;
                                }
                            }
                            var24 += var26;
                            var25 += var27;
                        }
                        var14 += var18;
                        var15 += var19;
                        var16 += var20;
                        var17 += var21;
                    }
                }
            }
        }
    }
    
    public void replaceBlocksForBiome(final int par1, final int par2, final byte[] par3ArrayOfByte, final BiomeGenBase[] par4ArrayOfBiomeGenBase) {
        final byte var5 = 63;
        final double var6 = 0.03125;
        this.stoneNoise = this.noiseGen4.generateNoiseOctaves(this.stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0, var6 * 2.0, var6 * 2.0);
        for (int var7 = 0; var7 < 16; ++var7) {
            for (int var8 = 0; var8 < 16; ++var8) {
                final BiomeGenBase var9 = par4ArrayOfBiomeGenBase[var8 + var7 * 16];
                final float var10 = var9.getFloatTemperature();
                final int var11 = (int)(this.stoneNoise[var7 + var8 * 16] / 3.0 + 3.0 + this.rand.nextDouble() * 0.25);
                int var12 = -1;
                byte var13 = var9.topBlock;
                byte var14 = var9.fillerBlock;
                for (int var15 = 127; var15 >= 0; --var15) {
                    final int var16 = (var8 * 16 + var7) * 128 + var15;
                    if (var15 <= 0 + this.rand.nextInt(5)) {
                        par3ArrayOfByte[var16] = (byte)Block.bedrock.blockID;
                    }
                    else {
                        final byte var17 = par3ArrayOfByte[var16];
                        if (var17 == 0) {
                            var12 = -1;
                        }
                        else if (var17 == Block.stone.blockID) {
                            if (var12 == -1) {
                                if (var11 <= 0) {
                                    var13 = 0;
                                    var14 = (byte)Block.stone.blockID;
                                }
                                else if (var15 >= var5 - 4 && var15 <= var5 + 1) {
                                    var13 = var9.topBlock;
                                    var14 = var9.fillerBlock;
                                }
                                if (var15 < var5 && var13 == 0) {
                                    if (var10 < 0.15f) {
                                        var13 = (byte)Block.ice.blockID;
                                    }
                                    else {
                                        var13 = (byte)Block.waterStill.blockID;
                                    }
                                }
                                var12 = var11;
                                if (var15 >= var5 - 1) {
                                    par3ArrayOfByte[var16] = var13;
                                }
                                else {
                                    par3ArrayOfByte[var16] = var14;
                                }
                            }
                            else if (var12 > 0) {
                                --var12;
                                par3ArrayOfByte[var16] = var14;
                                if (var12 == 0 && var14 == Block.sand.blockID) {
                                    var12 = this.rand.nextInt(4);
                                    var14 = (byte)Block.sandStone.blockID;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public Chunk loadChunk(final int par1, final int par2) {
        return this.provideChunk(par1, par2);
    }
    
    @Override
    public Chunk provideChunk(final int par1, final int par2) {
        this.rand.setSeed(par1 * 341873128712L + par2 * 132897987541L);
        final byte[] var3 = new byte[32768];
        this.generateTerrain(par1, par2, var3);
        this.replaceBlocksForBiome(par1, par2, var3, this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16));
        this.caveGenerator.generate(this, this.worldObj, par1, par2, var3);
        this.ravineGenerator.generate(this, this.worldObj, par1, par2, var3);
        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generate(this, this.worldObj, par1, par2, var3);
            this.villageGenerator.generate(this, this.worldObj, par1, par2, var3);
            this.strongholdGenerator.generate(this, this.worldObj, par1, par2, var3);
            this.scatteredFeatureGenerator.generate(this, this.worldObj, par1, par2, var3);
        }
        final Chunk var4 = new Chunk(this.worldObj, var3, par1, par2);
        final byte[] var5 = var4.getBiomeArray();
        for (int var6 = 0; var6 < var5.length; ++var6) {
            var5[var6] = (byte)this.biomesForGeneration[var6].biomeID;
        }
        var4.generateSkylightMap();
        return var4;
    }
    
    private double[] initializeNoiseField(double[] par1ArrayOfDouble, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7) {
        if (par1ArrayOfDouble == null) {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }
        if (this.parabolicField == null) {
            this.parabolicField = new float[25];
            for (int var8 = -2; var8 <= 2; ++var8) {
                for (int var9 = -2; var9 <= 2; ++var9) {
                    final float var10 = 10.0f / MathHelper.sqrt_float(var8 * var8 + var9 * var9 + 0.2f);
                    this.parabolicField[var8 + 2 + (var9 + 2) * 5] = var10;
                }
            }
        }
        final double var11 = 684.412;
        final double var12 = 684.412;
        this.noise5 = this.noiseGen5.generateNoiseOctaves(this.noise5, par2, par4, par5, par7, 1.121, 1.121, 0.5);
        this.noise6 = this.noiseGen6.generateNoiseOctaves(this.noise6, par2, par4, par5, par7, 200.0, 200.0, 0.5);
        this.noise3 = this.noiseGen3.generateNoiseOctaves(this.noise3, par2, par3, par4, par5, par6, par7, var11 / 80.0, var12 / 160.0, var11 / 80.0);
        this.noise1 = this.noiseGen1.generateNoiseOctaves(this.noise1, par2, par3, par4, par5, par6, par7, var11, var12, var11);
        this.noise2 = this.noiseGen2.generateNoiseOctaves(this.noise2, par2, par3, par4, par5, par6, par7, var11, var12, var11);
        final boolean var13 = false;
        final boolean var14 = false;
        int var15 = 0;
        int var16 = 0;
        for (int var17 = 0; var17 < par5; ++var17) {
            for (int var18 = 0; var18 < par7; ++var18) {
                float var19 = 0.0f;
                float var20 = 0.0f;
                float var21 = 0.0f;
                final byte var22 = 2;
                final BiomeGenBase var23 = this.biomesForGeneration[var17 + 2 + (var18 + 2) * (par5 + 5)];
                for (int var24 = -var22; var24 <= var22; ++var24) {
                    for (int var25 = -var22; var25 <= var22; ++var25) {
                        final BiomeGenBase var26 = this.biomesForGeneration[var17 + var24 + 2 + (var18 + var25 + 2) * (par5 + 5)];
                        float var27 = this.parabolicField[var24 + 2 + (var25 + 2) * 5] / (var26.minHeight + 2.0f);
                        if (var26.minHeight > var23.minHeight) {
                            var27 /= 2.0f;
                        }
                        var19 += var26.maxHeight * var27;
                        var20 += var26.minHeight * var27;
                        var21 += var27;
                    }
                }
                var19 /= var21;
                var20 /= var21;
                var19 = var19 * 0.9f + 0.1f;
                var20 = (var20 * 4.0f - 1.0f) / 8.0f;
                double var28 = this.noise6[var16] / 8000.0;
                if (var28 < 0.0) {
                    var28 = -var28 * 0.3;
                }
                var28 = var28 * 3.0 - 2.0;
                if (var28 < 0.0) {
                    var28 /= 2.0;
                    if (var28 < -1.0) {
                        var28 = -1.0;
                    }
                    var28 /= 1.4;
                    var28 /= 2.0;
                }
                else {
                    if (var28 > 1.0) {
                        var28 = 1.0;
                    }
                    var28 /= 8.0;
                }
                ++var16;
                for (int var29 = 0; var29 < par6; ++var29) {
                    double var30 = var20;
                    final double var31 = var19;
                    var30 += var28 * 0.2;
                    var30 = var30 * par6 / 16.0;
                    final double var32 = par6 / 2.0 + var30 * 4.0;
                    double var33 = 0.0;
                    double var34 = (var29 - var32) * 12.0 * 128.0 / 128.0 / var31;
                    if (var34 < 0.0) {
                        var34 *= 4.0;
                    }
                    final double var35 = this.noise1[var15] / 512.0;
                    final double var36 = this.noise2[var15] / 512.0;
                    final double var37 = (this.noise3[var15] / 10.0 + 1.0) / 2.0;
                    if (var37 < 0.0) {
                        var33 = var35;
                    }
                    else if (var37 > 1.0) {
                        var33 = var36;
                    }
                    else {
                        var33 = var35 + (var36 - var35) * var37;
                    }
                    var33 -= var34;
                    if (var29 > par6 - 4) {
                        final double var38 = (var29 - (par6 - 4)) / 3.0f;
                        var33 = var33 * (1.0 - var38) + -10.0 * var38;
                    }
                    par1ArrayOfDouble[var15] = var33;
                    ++var15;
                }
            }
        }
        return par1ArrayOfDouble;
    }
    
    @Override
    public boolean chunkExists(final int par1, final int par2) {
        return true;
    }
    
    @Override
    public void populate(final IChunkProvider par1IChunkProvider, final int par2, final int par3) {
        BlockSand.fallInstantly = true;
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        final BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        final long var7 = this.rand.nextLong() / 2L * 2L + 1L;
        final long var8 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(par2 * var7 + par3 * var8 ^ this.worldObj.getSeed());
        boolean var9 = false;
        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
            var9 = this.villageGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
            this.strongholdGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
            this.scatteredFeatureGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
        }
        if (!var9 && this.rand.nextInt(4) == 0) {
            final int var10 = var4 + this.rand.nextInt(16) + 8;
            final int var11 = this.rand.nextInt(128);
            final int var12 = var5 + this.rand.nextInt(16) + 8;
            new WorldGenLakes(Block.waterStill.blockID).generate(this.worldObj, this.rand, var10, var11, var12);
        }
        if (!var9 && this.rand.nextInt(8) == 0) {
            final int var10 = var4 + this.rand.nextInt(16) + 8;
            final int var11 = this.rand.nextInt(this.rand.nextInt(120) + 8);
            final int var12 = var5 + this.rand.nextInt(16) + 8;
            if (var11 < 63 || this.rand.nextInt(10) == 0) {
                new WorldGenLakes(Block.lavaStill.blockID).generate(this.worldObj, this.rand, var10, var11, var12);
            }
        }
        for (int var10 = 0; var10 < 8; ++var10) {
            final int var11 = var4 + this.rand.nextInt(16) + 8;
            final int var12 = this.rand.nextInt(128);
            final int var13 = var5 + this.rand.nextInt(16) + 8;
            if (new WorldGenDungeons().generate(this.worldObj, this.rand, var11, var12, var13)) {}
        }
        var6.decorate(this.worldObj, this.rand, var4, var5);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, var6, var4 + 8, var5 + 8, 16, 16, this.rand);
        var4 += 8;
        var5 += 8;
        for (int var10 = 0; var10 < 16; ++var10) {
            for (int var11 = 0; var11 < 16; ++var11) {
                final int var12 = this.worldObj.getPrecipitationHeight(var4 + var10, var5 + var11);
                if (this.worldObj.isBlockFreezable(var10 + var4, var12 - 1, var11 + var5)) {
                    this.worldObj.setBlock(var10 + var4, var12 - 1, var11 + var5, Block.ice.blockID, 0, 2);
                }
                if (this.worldObj.canSnowAt(var10 + var4, var12, var11 + var5)) {
                    this.worldObj.setBlock(var10 + var4, var12, var11 + var5, Block.snow.blockID, 0, 2);
                }
            }
        }
        BlockSand.fallInstantly = false;
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
        return "RandomLevelSource";
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType par1EnumCreatureType, final int par2, final int par3, final int par4) {
        final BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
        return (var5 == null) ? null : ((var5 == BiomeGenBase.swampland && par1EnumCreatureType == EnumCreatureType.monster && this.scatteredFeatureGenerator.hasStructureAt(par2, par3, par4)) ? this.scatteredFeatureGenerator.getScatteredFeatureSpawnList() : var5.getSpawnableList(par1EnumCreatureType));
    }
    
    @Override
    public ChunkPosition findClosestStructure(final World par1World, final String par2Str, final int par3, final int par4, final int par5) {
        return ("Stronghold".equals(par2Str) && this.strongholdGenerator != null) ? this.strongholdGenerator.getNearestInstance(par1World, par3, par4, par5) : null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public void recreateStructures(final int par1, final int par2) {
        if (this.mapFeaturesEnabled) {
            this.mineshaftGenerator.generate(this, this.worldObj, par1, par2, null);
            this.villageGenerator.generate(this, this.worldObj, par1, par2, null);
            this.strongholdGenerator.generate(this, this.worldObj, par1, par2, null);
            this.scatteredFeatureGenerator.generate(this, this.worldObj, par1, par2, null);
        }
    }
}
