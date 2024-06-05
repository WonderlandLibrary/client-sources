package net.minecraft.src;

import java.util.*;

public class ChunkProviderHell implements IChunkProvider
{
    private Random hellRNG;
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves netherNoiseGen6;
    public NoiseGeneratorOctaves netherNoiseGen7;
    private World worldObj;
    private double[] noiseField;
    public MapGenNetherBridge genNetherBridge;
    private double[] slowsandNoise;
    private double[] gravelNoise;
    private double[] netherrackExclusivityNoise;
    private MapGenBase netherCaveGenerator;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    
    public ChunkProviderHell(final World par1World, final long par2) {
        this.genNetherBridge = new MapGenNetherBridge();
        this.slowsandNoise = new double[256];
        this.gravelNoise = new double[256];
        this.netherrackExclusivityNoise = new double[256];
        this.netherCaveGenerator = new MapGenCavesHell();
        this.worldObj = par1World;
        this.hellRNG = new Random(par2);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
    }
    
    public void generateNetherTerrain(final int par1, final int par2, final byte[] par3ArrayOfByte) {
        final byte var4 = 4;
        final byte var5 = 32;
        final int var6 = var4 + 1;
        final byte var7 = 17;
        final int var8 = var4 + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, par1 * var4, 0, par2 * var4, var6, var7, var8);
        for (int var9 = 0; var9 < var4; ++var9) {
            for (int var10 = 0; var10 < var4; ++var10) {
                for (int var11 = 0; var11 < 16; ++var11) {
                    final double var12 = 0.125;
                    double var13 = this.noiseField[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var14 = this.noiseField[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
                    double var15 = this.noiseField[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var16 = this.noiseField[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
                    final double var17 = (this.noiseField[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var13) * var12;
                    final double var18 = (this.noiseField[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var14) * var12;
                    final double var19 = (this.noiseField[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var15) * var12;
                    final double var20 = (this.noiseField[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
                    for (int var21 = 0; var21 < 8; ++var21) {
                        final double var22 = 0.25;
                        double var23 = var13;
                        double var24 = var14;
                        final double var25 = (var15 - var13) * var22;
                        final double var26 = (var16 - var14) * var22;
                        for (int var27 = 0; var27 < 4; ++var27) {
                            int var28 = var27 + var9 * 4 << 11 | 0 + var10 * 4 << 7 | var11 * 8 + var21;
                            final short var29 = 128;
                            final double var30 = 0.25;
                            double var31 = var23;
                            final double var32 = (var24 - var23) * var30;
                            for (int var33 = 0; var33 < 4; ++var33) {
                                int var34 = 0;
                                if (var11 * 8 + var21 < var5) {
                                    var34 = Block.lavaStill.blockID;
                                }
                                if (var31 > 0.0) {
                                    var34 = Block.netherrack.blockID;
                                }
                                par3ArrayOfByte[var28] = (byte)var34;
                                var28 += var29;
                                var31 += var32;
                            }
                            var23 += var25;
                            var24 += var26;
                        }
                        var13 += var17;
                        var14 += var18;
                        var15 += var19;
                        var16 += var20;
                    }
                }
            }
        }
    }
    
    public void replaceBlocksForBiome(final int par1, final int par2, final byte[] par3ArrayOfByte) {
        final byte var4 = 64;
        final double var5 = 0.03125;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var5, var5, 1.0);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, par1 * 16, 109, par2 * 16, 16, 1, 16, var5, 1.0, var5);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var5 * 2.0, var5 * 2.0, var5 * 2.0);
        for (int var6 = 0; var6 < 16; ++var6) {
            for (int var7 = 0; var7 < 16; ++var7) {
                final boolean var8 = this.slowsandNoise[var6 + var7 * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                final boolean var9 = this.gravelNoise[var6 + var7 * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                final int var10 = (int)(this.netherrackExclusivityNoise[var6 + var7 * 16] / 3.0 + 3.0 + this.hellRNG.nextDouble() * 0.25);
                int var11 = -1;
                byte var12 = (byte)Block.netherrack.blockID;
                byte var13 = (byte)Block.netherrack.blockID;
                for (int var14 = 127; var14 >= 0; --var14) {
                    final int var15 = (var7 * 16 + var6) * 128 + var14;
                    if (var14 < 127 - this.hellRNG.nextInt(5) && var14 > 0 + this.hellRNG.nextInt(5)) {
                        final byte var16 = par3ArrayOfByte[var15];
                        if (var16 == 0) {
                            var11 = -1;
                        }
                        else if (var16 == Block.netherrack.blockID) {
                            if (var11 == -1) {
                                if (var10 <= 0) {
                                    var12 = 0;
                                    var13 = (byte)Block.netherrack.blockID;
                                }
                                else if (var14 >= var4 - 4 && var14 <= var4 + 1) {
                                    var12 = (byte)Block.netherrack.blockID;
                                    var13 = (byte)Block.netherrack.blockID;
                                    if (var9) {
                                        var12 = (byte)Block.gravel.blockID;
                                    }
                                    if (var9) {
                                        var13 = (byte)Block.netherrack.blockID;
                                    }
                                    if (var8) {
                                        var12 = (byte)Block.slowSand.blockID;
                                    }
                                    if (var8) {
                                        var13 = (byte)Block.slowSand.blockID;
                                    }
                                }
                                if (var14 < var4 && var12 == 0) {
                                    var12 = (byte)Block.lavaStill.blockID;
                                }
                                var11 = var10;
                                if (var14 >= var4 - 1) {
                                    par3ArrayOfByte[var15] = var12;
                                }
                                else {
                                    par3ArrayOfByte[var15] = var13;
                                }
                            }
                            else if (var11 > 0) {
                                --var11;
                                par3ArrayOfByte[var15] = var13;
                            }
                        }
                    }
                    else {
                        par3ArrayOfByte[var15] = (byte)Block.bedrock.blockID;
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
        this.hellRNG.setSeed(par1 * 341873128712L + par2 * 132897987541L);
        final byte[] var3 = new byte[32768];
        this.generateNetherTerrain(par1, par2, var3);
        this.replaceBlocksForBiome(par1, par2, var3);
        this.netherCaveGenerator.generate(this, this.worldObj, par1, par2, var3);
        this.genNetherBridge.generate(this, this.worldObj, par1, par2, var3);
        final Chunk var4 = new Chunk(this.worldObj, var3, par1, par2);
        final BiomeGenBase[] var5 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, par1 * 16, par2 * 16, 16, 16);
        final byte[] var6 = var4.getBiomeArray();
        for (int var7 = 0; var7 < var6.length; ++var7) {
            var6[var7] = (byte)var5[var7].biomeID;
        }
        var4.resetRelightChecks();
        return var4;
    }
    
    private double[] initializeNoiseField(double[] par1ArrayOfDouble, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7) {
        if (par1ArrayOfDouble == null) {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }
        final double var8 = 684.412;
        final double var9 = 2053.236;
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, par2, par3, par4, par5, 1, par7, 1.0, 0.0, 1.0);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, par2, par3, par4, par5, 1, par7, 100.0, 0.0, 100.0);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, var8 / 80.0, var9 / 60.0, var8 / 80.0);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, var8, var9, var8);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, var8, var9, var8);
        int var10 = 0;
        int var11 = 0;
        final double[] var12 = new double[par6];
        for (int var13 = 0; var13 < par6; ++var13) {
            var12[var13] = Math.cos(var13 * 3.141592653589793 * 6.0 / par6) * 2.0;
            double var14 = var13;
            if (var13 > par6 / 2) {
                var14 = par6 - 1 - var13;
            }
            if (var14 < 4.0) {
                var14 = 4.0 - var14;
                final double[] array = var12;
                final int n = var13;
                array[n] -= var14 * var14 * var14 * 10.0;
            }
        }
        for (int var13 = 0; var13 < par5; ++var13) {
            for (int var15 = 0; var15 < par7; ++var15) {
                double var16 = (this.noiseData4[var11] + 256.0) / 512.0;
                if (var16 > 1.0) {
                    var16 = 1.0;
                }
                final double var17 = 0.0;
                double var18 = this.noiseData5[var11] / 8000.0;
                if (var18 < 0.0) {
                    var18 = -var18;
                }
                var18 = var18 * 3.0 - 3.0;
                if (var18 < 0.0) {
                    var18 /= 2.0;
                    if (var18 < -1.0) {
                        var18 = -1.0;
                    }
                    var18 /= 1.4;
                    var18 /= 2.0;
                    var16 = 0.0;
                }
                else {
                    if (var18 > 1.0) {
                        var18 = 1.0;
                    }
                    var18 /= 6.0;
                }
                var16 += 0.5;
                var18 = var18 * par6 / 16.0;
                ++var11;
                for (int var19 = 0; var19 < par6; ++var19) {
                    double var20 = 0.0;
                    final double var21 = var12[var19];
                    final double var22 = this.noiseData2[var10] / 512.0;
                    final double var23 = this.noiseData3[var10] / 512.0;
                    final double var24 = (this.noiseData1[var10] / 10.0 + 1.0) / 2.0;
                    if (var24 < 0.0) {
                        var20 = var22;
                    }
                    else if (var24 > 1.0) {
                        var20 = var23;
                    }
                    else {
                        var20 = var22 + (var23 - var22) * var24;
                    }
                    var20 -= var21;
                    if (var19 > par6 - 4) {
                        final double var25 = (var19 - (par6 - 4)) / 3.0f;
                        var20 = var20 * (1.0 - var25) + -10.0 * var25;
                    }
                    if (var19 < var17) {
                        double var25 = (var17 - var19) / 4.0;
                        if (var25 < 0.0) {
                            var25 = 0.0;
                        }
                        if (var25 > 1.0) {
                            var25 = 1.0;
                        }
                        var20 = var20 * (1.0 - var25) + -10.0 * var25;
                    }
                    par1ArrayOfDouble[var10] = var20;
                    ++var10;
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
        final int var4 = par2 * 16;
        final int var5 = par3 * 16;
        this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.hellRNG, par2, par3);
        for (int var6 = 0; var6 < 8; ++var6) {
            final int var7 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var8 = this.hellRNG.nextInt(120) + 4;
            final int var9 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenHellLava(Block.lavaMoving.blockID, false).generate(this.worldObj, this.hellRNG, var7, var8, var9);
        }
        int var6;
        for (var6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1, int var7 = 0; var7 < var6; ++var7) {
            final int var8 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var9 = this.hellRNG.nextInt(120) + 4;
            final int var10 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFire().generate(this.worldObj, this.hellRNG, var8, var9, var10);
        }
        for (var6 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1), int var7 = 0; var7 < var6; ++var7) {
            final int var8 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var9 = this.hellRNG.nextInt(120) + 4;
            final int var10 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenGlowStone1().generate(this.worldObj, this.hellRNG, var8, var9, var10);
        }
        for (int var7 = 0; var7 < 10; ++var7) {
            final int var8 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var9 = this.hellRNG.nextInt(128);
            final int var10 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenGlowStone2().generate(this.worldObj, this.hellRNG, var8, var9, var10);
        }
        if (this.hellRNG.nextInt(1) == 0) {
            final int var7 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var8 = this.hellRNG.nextInt(128);
            final int var9 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFlowers(Block.mushroomBrown.blockID).generate(this.worldObj, this.hellRNG, var7, var8, var9);
        }
        if (this.hellRNG.nextInt(1) == 0) {
            final int var7 = var4 + this.hellRNG.nextInt(16) + 8;
            final int var8 = this.hellRNG.nextInt(128);
            final int var9 = var5 + this.hellRNG.nextInt(16) + 8;
            new WorldGenFlowers(Block.mushroomRed.blockID).generate(this.worldObj, this.hellRNG, var7, var8, var9);
        }
        final WorldGenMinable var11 = new WorldGenMinable(Block.oreNetherQuartz.blockID, 13, Block.netherrack.blockID);
        for (int var8 = 0; var8 < 16; ++var8) {
            final int var9 = var4 + this.hellRNG.nextInt(16);
            final int var10 = this.hellRNG.nextInt(108) + 10;
            final int var12 = var5 + this.hellRNG.nextInt(16);
            var11.generate(this.worldObj, this.hellRNG, var9, var10, var12);
        }
        for (int var8 = 0; var8 < 16; ++var8) {
            final int var9 = var4 + this.hellRNG.nextInt(16);
            final int var10 = this.hellRNG.nextInt(108) + 10;
            final int var12 = var5 + this.hellRNG.nextInt(16);
            new WorldGenHellLava(Block.lavaMoving.blockID, true).generate(this.worldObj, this.hellRNG, var9, var10, var12);
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
        return "HellRandomLevelSource";
    }
    
    @Override
    public List getPossibleCreatures(final EnumCreatureType par1EnumCreatureType, final int par2, final int par3, final int par4) {
        if (par1EnumCreatureType == EnumCreatureType.monster && this.genNetherBridge.hasStructureAt(par2, par3, par4)) {
            return this.genNetherBridge.getSpawnList();
        }
        final BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
        return (var5 == null) ? null : var5.getSpawnableList(par1EnumCreatureType);
    }
    
    @Override
    public ChunkPosition findClosestStructure(final World par1World, final String par2Str, final int par3, final int par4, final int par5) {
        return null;
    }
    
    @Override
    public int getLoadedChunkCount() {
        return 0;
    }
    
    @Override
    public void recreateStructures(final int par1, final int par2) {
        this.genNetherBridge.generate(this, this.worldObj, par1, par2, null);
    }
}
