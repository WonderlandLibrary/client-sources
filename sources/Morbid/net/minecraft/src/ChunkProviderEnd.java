package net.minecraft.src;

import java.util.*;

public class ChunkProviderEnd implements IChunkProvider
{
    private Random endRNG;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    public NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    private World endWorld;
    private double[] densities;
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    int[][] field_73203_h;
    
    public ChunkProviderEnd(final World par1World, final long par2) {
        this.field_73203_h = new int[32][32];
        this.endWorld = par1World;
        this.endRNG = new Random(par2);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.endRNG, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.endRNG, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.endRNG, 10);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.endRNG, 16);
    }
    
    public void generateTerrain(final int par1, final int par2, final byte[] par3ArrayOfByte, final BiomeGenBase[] par4ArrayOfBiomeGenBase) {
        final byte var5 = 2;
        final int var6 = var5 + 1;
        final byte var7 = 33;
        final int var8 = var5 + 1;
        this.densities = this.initializeNoiseField(this.densities, par1 * var5, 0, par2 * var5, var6, var7, var8);
        for (int var9 = 0; var9 < var5; ++var9) {
            for (int var10 = 0; var10 < var5; ++var10) {
                for (int var11 = 0; var11 < 32; ++var11) {
                    final double var12 = 0.25;
                    double var13 = this.densities[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var14 = this.densities[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
                    double var15 = this.densities[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var16 = this.densities[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
                    final double var17 = (this.densities[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var13) * var12;
                    final double var18 = (this.densities[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var14) * var12;
                    final double var19 = (this.densities[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var15) * var12;
                    final double var20 = (this.densities[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
                    for (int var21 = 0; var21 < 4; ++var21) {
                        final double var22 = 0.125;
                        double var23 = var13;
                        double var24 = var14;
                        final double var25 = (var15 - var13) * var22;
                        final double var26 = (var16 - var14) * var22;
                        for (int var27 = 0; var27 < 8; ++var27) {
                            int var28 = var27 + var9 * 8 << 11 | 0 + var10 * 8 << 7 | var11 * 4 + var21;
                            final short var29 = 128;
                            final double var30 = 0.125;
                            double var31 = var23;
                            final double var32 = (var24 - var23) * var30;
                            for (int var33 = 0; var33 < 8; ++var33) {
                                int var34 = 0;
                                if (var31 > 0.0) {
                                    var34 = Block.whiteStone.blockID;
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
    
    public void replaceBlocksForBiome(final int par1, final int par2, final byte[] par3ArrayOfByte, final BiomeGenBase[] par4ArrayOfBiomeGenBase) {
        for (int var5 = 0; var5 < 16; ++var5) {
            for (int var6 = 0; var6 < 16; ++var6) {
                final byte var7 = 1;
                int var8 = -1;
                byte var9 = (byte)Block.whiteStone.blockID;
                byte var10 = (byte)Block.whiteStone.blockID;
                for (int var11 = 127; var11 >= 0; --var11) {
                    final int var12 = (var6 * 16 + var5) * 128 + var11;
                    final byte var13 = par3ArrayOfByte[var12];
                    if (var13 == 0) {
                        var8 = -1;
                    }
                    else if (var13 == Block.stone.blockID) {
                        if (var8 == -1) {
                            if (var7 <= 0) {
                                var9 = 0;
                                var10 = (byte)Block.whiteStone.blockID;
                            }
                            var8 = var7;
                            if (var11 >= 0) {
                                par3ArrayOfByte[var12] = var9;
                            }
                            else {
                                par3ArrayOfByte[var12] = var10;
                            }
                        }
                        else if (var8 > 0) {
                            --var8;
                            par3ArrayOfByte[var12] = var10;
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
        this.endRNG.setSeed(par1 * 341873128712L + par2 * 132897987541L);
        final byte[] var3 = new byte[32768];
        this.generateTerrain(par1, par2, var3, this.biomesForGeneration = this.endWorld.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16));
        this.replaceBlocksForBiome(par1, par2, var3, this.biomesForGeneration);
        final Chunk var4 = new Chunk(this.endWorld, var3, par1, par2);
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
        double var8 = 684.412;
        final double var9 = 684.412;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, par2, par4, par5, par7, 1.121, 1.121, 0.5);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, par2, par4, par5, par7, 200.0, 200.0, 0.5);
        var8 *= 2.0;
        this.noiseData1 = this.noiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, var8 / 80.0, var9 / 160.0, var8 / 80.0);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, var8, var9, var8);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, var8, var9, var8);
        int var10 = 0;
        int var11 = 0;
        for (int var12 = 0; var12 < par5; ++var12) {
            for (int var13 = 0; var13 < par7; ++var13) {
                double var14 = (this.noiseData4[var11] + 256.0) / 512.0;
                if (var14 > 1.0) {
                    var14 = 1.0;
                }
                double var15 = this.noiseData5[var11] / 8000.0;
                if (var15 < 0.0) {
                    var15 = -var15 * 0.3;
                }
                var15 = var15 * 3.0 - 2.0;
                final float var16 = (var12 + par2 - 0) / 1.0f;
                final float var17 = (var13 + par4 - 0) / 1.0f;
                float var18 = 100.0f - MathHelper.sqrt_float(var16 * var16 + var17 * var17) * 8.0f;
                if (var18 > 80.0f) {
                    var18 = 80.0f;
                }
                if (var18 < -100.0f) {
                    var18 = -100.0f;
                }
                if (var15 > 1.0) {
                    var15 = 1.0;
                }
                var15 /= 8.0;
                var15 = 0.0;
                if (var14 < 0.0) {
                    var14 = 0.0;
                }
                var14 += 0.5;
                var15 = var15 * par6 / 16.0;
                ++var11;
                final double var19 = par6 / 2.0;
                for (int var20 = 0; var20 < par6; ++var20) {
                    double var21 = 0.0;
                    double var22 = (var20 - var19) * 8.0 / var14;
                    if (var22 < 0.0) {
                        var22 *= -1.0;
                    }
                    final double var23 = this.noiseData2[var10] / 512.0;
                    final double var24 = this.noiseData3[var10] / 512.0;
                    final double var25 = (this.noiseData1[var10] / 10.0 + 1.0) / 2.0;
                    if (var25 < 0.0) {
                        var21 = var23;
                    }
                    else if (var25 > 1.0) {
                        var21 = var24;
                    }
                    else {
                        var21 = var23 + (var24 - var23) * var25;
                    }
                    var21 -= 8.0;
                    var21 += var18;
                    byte var26 = 2;
                    if (var20 > par6 / 2 - var26) {
                        double var27 = (var20 - (par6 / 2 - var26)) / 64.0f;
                        if (var27 < 0.0) {
                            var27 = 0.0;
                        }
                        if (var27 > 1.0) {
                            var27 = 1.0;
                        }
                        var21 = var21 * (1.0 - var27) + -3000.0 * var27;
                    }
                    var26 = 8;
                    if (var20 < var26) {
                        final double var27 = (var26 - var20) / (var26 - 1.0f);
                        var21 = var21 * (1.0 - var27) + -30.0 * var27;
                    }
                    par1ArrayOfDouble[var10] = var21;
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
        final BiomeGenBase var6 = this.endWorld.getBiomeGenForCoords(var4 + 16, var5 + 16);
        var6.decorate(this.endWorld, this.endWorld.rand, var4, var5);
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
        final BiomeGenBase var5 = this.endWorld.getBiomeGenForCoords(par2, par4);
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
    }
}
