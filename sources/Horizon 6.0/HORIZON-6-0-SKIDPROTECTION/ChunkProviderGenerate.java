package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class ChunkProviderGenerate implements IChunkProvider
{
    private Random Ø;
    private NoiseGeneratorOctaves áŒŠÆ;
    private NoiseGeneratorOctaves áˆºÑ¢Õ;
    private NoiseGeneratorOctaves ÂµÈ;
    private NoiseGeneratorPerlin á;
    public NoiseGeneratorOctaves HorizonCode_Horizon_È;
    public NoiseGeneratorOctaves Â;
    public NoiseGeneratorOctaves Ý;
    private World ˆÏ­;
    private final boolean £á;
    private WorldType Å;
    private final double[] £à;
    private final float[] µà;
    private ChunkProviderSettings ˆà;
    private Block ¥Æ;
    private double[] Ø­à;
    private MapGenBase µÕ;
    private MapGenStronghold Æ;
    private MapGenVillage Šáƒ;
    private MapGenMineshaft Ï­Ðƒà;
    private MapGenScatteredFeature áŒŠà;
    private MapGenBase ŠÄ;
    private StructureOceanMonument Ñ¢á;
    private BiomeGenBase[] ŒÏ;
    double[] Ø­áŒŠá;
    double[] Âµá€;
    double[] Ó;
    double[] à;
    private static final String Çªà¢ = "CL_00000396";
    
    public ChunkProviderGenerate(final World worldIn, final long p_i45636_2_, final boolean p_i45636_4_, final String p_i45636_5_) {
        this.¥Æ = Blocks.ÂµÈ;
        this.Ø­à = new double[256];
        this.µÕ = new MapGenCaves();
        this.Æ = new MapGenStronghold();
        this.Šáƒ = new MapGenVillage();
        this.Ï­Ðƒà = new MapGenMineshaft();
        this.áŒŠà = new MapGenScatteredFeature();
        this.ŠÄ = new MapGenRavine();
        this.Ñ¢á = new StructureOceanMonument();
        this.ˆÏ­ = worldIn;
        this.£á = p_i45636_4_;
        this.Å = worldIn.ŒÏ().Ø­à();
        this.Ø = new Random(p_i45636_2_);
        this.áŒŠÆ = new NoiseGeneratorOctaves(this.Ø, 16);
        this.áˆºÑ¢Õ = new NoiseGeneratorOctaves(this.Ø, 16);
        this.ÂµÈ = new NoiseGeneratorOctaves(this.Ø, 8);
        this.á = new NoiseGeneratorPerlin(this.Ø, 4);
        this.HorizonCode_Horizon_È = new NoiseGeneratorOctaves(this.Ø, 10);
        this.Â = new NoiseGeneratorOctaves(this.Ø, 16);
        this.Ý = new NoiseGeneratorOctaves(this.Ø, 8);
        this.£à = new double[825];
        this.µà = new float[25];
        for (int var6 = -2; var6 <= 2; ++var6) {
            for (int var7 = -2; var7 <= 2; ++var7) {
                final float var8 = 10.0f / MathHelper.Ý(var6 * var6 + var7 * var7 + 0.2f);
                this.µà[var6 + 2 + (var7 + 2) * 5] = var8;
            }
        }
        if (p_i45636_5_ != null) {
            this.ˆà = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_i45636_5_).Â();
            this.¥Æ = (this.ˆà.ÇŽÉ ? Blocks.ˆÏ­ : Blocks.ÂµÈ);
        }
    }
    
    public void HorizonCode_Horizon_È(final int p_180518_1_, final int p_180518_2_, final ChunkPrimer p_180518_3_) {
        this.ŒÏ = this.ˆÏ­.áŒŠÆ().HorizonCode_Horizon_È(this.ŒÏ, p_180518_1_ * 4 - 2, p_180518_2_ * 4 - 2, 10, 10);
        this.HorizonCode_Horizon_È(p_180518_1_ * 4, 0, p_180518_2_ * 4);
        for (int var4 = 0; var4 < 4; ++var4) {
            final int var5 = var4 * 5;
            final int var6 = (var4 + 1) * 5;
            for (int var7 = 0; var7 < 4; ++var7) {
                final int var8 = (var5 + var7) * 33;
                final int var9 = (var5 + var7 + 1) * 33;
                final int var10 = (var6 + var7) * 33;
                final int var11 = (var6 + var7 + 1) * 33;
                for (int var12 = 0; var12 < 32; ++var12) {
                    final double var13 = 0.125;
                    double var14 = this.£à[var8 + var12];
                    double var15 = this.£à[var9 + var12];
                    double var16 = this.£à[var10 + var12];
                    double var17 = this.£à[var11 + var12];
                    final double var18 = (this.£à[var8 + var12 + 1] - var14) * var13;
                    final double var19 = (this.£à[var9 + var12 + 1] - var15) * var13;
                    final double var20 = (this.£à[var10 + var12 + 1] - var16) * var13;
                    final double var21 = (this.£à[var11 + var12 + 1] - var17) * var13;
                    for (int var22 = 0; var22 < 8; ++var22) {
                        final double var23 = 0.25;
                        double var24 = var14;
                        double var25 = var15;
                        final double var26 = (var16 - var14) * var23;
                        final double var27 = (var17 - var15) * var23;
                        for (int var28 = 0; var28 < 4; ++var28) {
                            final double var29 = 0.25;
                            final double var30 = (var25 - var24) * var29;
                            double var31 = var24 - var30;
                            for (int var32 = 0; var32 < 4; ++var32) {
                                if ((var31 += var30) > 0.0) {
                                    p_180518_3_.HorizonCode_Horizon_È(var4 * 4 + var28, var12 * 8 + var22, var7 * 4 + var32, Blocks.Ý.¥à());
                                }
                                else if (var12 * 8 + var22 < this.ˆà.µà) {
                                    p_180518_3_.HorizonCode_Horizon_È(var4 * 4 + var28, var12 * 8 + var22, var7 * 4 + var32, this.¥Æ.¥à());
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
    
    public void HorizonCode_Horizon_È(final int p_180517_1_, final int p_180517_2_, final ChunkPrimer p_180517_3_, final BiomeGenBase[] p_180517_4_) {
        final double var5 = 0.03125;
        this.Ø­à = this.á.HorizonCode_Horizon_È(this.Ø­à, p_180517_1_ * 16, p_180517_2_ * 16, 16, 16, var5 * 2.0, var5 * 2.0, 1.0);
        for (int var6 = 0; var6 < 16; ++var6) {
            for (int var7 = 0; var7 < 16; ++var7) {
                final BiomeGenBase var8 = p_180517_4_[var7 + var6 * 16];
                var8.HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, p_180517_3_, p_180517_1_ * 16 + var6, p_180517_2_ * 16 + var7, this.Ø­à[var7 + var6 * 16]);
            }
        }
    }
    
    @Override
    public Chunk Ø­áŒŠá(final int p_73154_1_, final int p_73154_2_) {
        this.Ø.setSeed(p_73154_1_ * 341873128712L + p_73154_2_ * 132897987541L);
        final ChunkPrimer var3 = new ChunkPrimer();
        this.HorizonCode_Horizon_È(p_73154_1_, p_73154_2_, var3);
        this.HorizonCode_Horizon_È(p_73154_1_, p_73154_2_, var3, this.ŒÏ = this.ˆÏ­.áŒŠÆ().Â(this.ŒÏ, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16));
        if (this.ˆà.ˆà) {
            this.µÕ.HorizonCode_Horizon_È(this, this.ˆÏ­, p_73154_1_, p_73154_2_, var3);
        }
        if (this.ˆà.ŠÄ) {
            this.ŠÄ.HorizonCode_Horizon_È(this, this.ˆÏ­, p_73154_1_, p_73154_2_, var3);
        }
        if (this.ˆà.Šáƒ && this.£á) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.ˆÏ­, p_73154_1_, p_73154_2_, var3);
        }
        if (this.ˆà.Æ && this.£á) {
            this.Šáƒ.HorizonCode_Horizon_È(this, this.ˆÏ­, p_73154_1_, p_73154_2_, var3);
        }
        if (this.ˆà.µÕ && this.£á) {
            this.Æ.HorizonCode_Horizon_È(this, this.ˆÏ­, p_73154_1_, p_73154_2_, var3);
        }
        if (this.ˆà.Ï­Ðƒà && this.£á) {
            this.áŒŠà.HorizonCode_Horizon_È(this, this.ˆÏ­, p_73154_1_, p_73154_2_, var3);
        }
        if (this.ˆà.áŒŠà && this.£á) {
            this.Ñ¢á.HorizonCode_Horizon_È(this, this.ˆÏ­, p_73154_1_, p_73154_2_, var3);
        }
        final Chunk var4 = new Chunk(this.ˆÏ­, var3, p_73154_1_, p_73154_2_);
        final byte[] var5 = var4.ÂµÈ();
        for (int var6 = 0; var6 < var5.length; ++var6) {
            var5[var6] = (byte)this.ŒÏ[var6].ÇªÔ;
        }
        var4.Ø­áŒŠá();
        return var4;
    }
    
    private void HorizonCode_Horizon_È(final int p_147423_1_, final int p_147423_2_, final int p_147423_3_) {
        this.à = this.Â.HorizonCode_Horizon_È(this.à, p_147423_1_, p_147423_3_, 5, 5, this.ˆà.Âµá€, this.ˆà.Ó, this.ˆà.à);
        final float var4 = this.ˆà.HorizonCode_Horizon_È;
        final float var5 = this.ˆà.Â;
        this.Ø­áŒŠá = this.ÂµÈ.HorizonCode_Horizon_È(this.Ø­áŒŠá, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, var4 / this.ˆà.Ø, var5 / this.ˆà.áŒŠÆ, var4 / this.ˆà.áˆºÑ¢Õ);
        this.Âµá€ = this.áŒŠÆ.HorizonCode_Horizon_È(this.Âµá€, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, var4, var5, var4);
        this.Ó = this.áˆºÑ¢Õ.HorizonCode_Horizon_È(this.Ó, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, var4, var5, var4);
        final boolean var6 = false;
        final boolean var7 = false;
        int var8 = 0;
        int var9 = 0;
        for (int var10 = 0; var10 < 5; ++var10) {
            for (int var11 = 0; var11 < 5; ++var11) {
                float var12 = 0.0f;
                float var13 = 0.0f;
                float var14 = 0.0f;
                final byte var15 = 2;
                final BiomeGenBase var16 = this.ŒÏ[var10 + 2 + (var11 + 2) * 10];
                for (int var17 = -var15; var17 <= var15; ++var17) {
                    for (int var18 = -var15; var18 <= var15; ++var18) {
                        final BiomeGenBase var19 = this.ŒÏ[var10 + var17 + 2 + (var11 + var18 + 2) * 10];
                        float var20 = this.ˆà.£á + var19.ˆÐƒØ­à * this.ˆà.ˆÏ­;
                        float var21 = this.ˆà.£à + var19.£Õ * this.ˆà.Å;
                        if (this.Å == WorldType.Ó && var20 > 0.0f) {
                            var20 = 1.0f + var20 * 2.0f;
                            var21 = 1.0f + var21 * 4.0f;
                        }
                        float var22 = this.µà[var17 + 2 + (var18 + 2) * 5] / (var20 + 2.0f);
                        if (var19.ˆÐƒØ­à > var16.ˆÐƒØ­à) {
                            var22 /= 2.0f;
                        }
                        var12 += var21 * var22;
                        var13 += var20 * var22;
                        var14 += var22;
                    }
                }
                var12 /= var14;
                var13 /= var14;
                var12 = var12 * 0.9f + 0.1f;
                var13 = (var13 * 4.0f - 1.0f) / 8.0f;
                double var23 = this.à[var9] / 8000.0;
                if (var23 < 0.0) {
                    var23 = -var23 * 0.3;
                }
                var23 = var23 * 3.0 - 2.0;
                if (var23 < 0.0) {
                    var23 /= 2.0;
                    if (var23 < -1.0) {
                        var23 = -1.0;
                    }
                    var23 /= 1.4;
                    var23 /= 2.0;
                }
                else {
                    if (var23 > 1.0) {
                        var23 = 1.0;
                    }
                    var23 /= 8.0;
                }
                ++var9;
                double var24 = var13;
                final double var25 = var12;
                var24 += var23 * 0.2;
                var24 = var24 * this.ˆà.ÂµÈ / 8.0;
                final double var26 = this.ˆà.ÂµÈ + var24 * 4.0;
                for (int var27 = 0; var27 < 33; ++var27) {
                    double var28 = (var27 - var26) * this.ˆà.á * 128.0 / 256.0 / var25;
                    if (var28 < 0.0) {
                        var28 *= 4.0;
                    }
                    final double var29 = this.Âµá€[var8] / this.ˆà.Ø­áŒŠá;
                    final double var30 = this.Ó[var8] / this.ˆà.Ý;
                    final double var31 = (this.Ø­áŒŠá[var8] / 10.0 + 1.0) / 2.0;
                    double var32 = MathHelper.Â(var29, var30, var31) - var28;
                    if (var27 > 29) {
                        final double var33 = (var27 - 29) / 3.0f;
                        var32 = var32 * (1.0 - var33) + -10.0 * var33;
                    }
                    this.£à[var8] = var32;
                    ++var8;
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int p_73149_1_, final int p_73149_2_) {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
        BlockFalling.ŠÂµà = true;
        final int var4 = p_73153_2_ * 16;
        final int var5 = p_73153_3_ * 16;
        BlockPos var6 = new BlockPos(var4, 0, var5);
        final BiomeGenBase var7 = this.ˆÏ­.Ý(var6.Â(16, 0, 16));
        this.Ø.setSeed(this.ˆÏ­.Æ());
        final long var8 = this.Ø.nextLong() / 2L * 2L + 1L;
        final long var9 = this.Ø.nextLong() / 2L * 2L + 1L;
        this.Ø.setSeed(p_73153_2_ * var8 + p_73153_3_ * var9 ^ this.ˆÏ­.Æ());
        boolean var10 = false;
        final ChunkCoordIntPair var11 = new ChunkCoordIntPair(p_73153_2_, p_73153_3_);
        if (this.ˆà.Šáƒ && this.£á) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, var11);
        }
        if (this.ˆà.Æ && this.£á) {
            var10 = this.Šáƒ.HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, var11);
        }
        if (this.ˆà.µÕ && this.£á) {
            this.Æ.HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, var11);
        }
        if (this.ˆà.Ï­Ðƒà && this.£á) {
            this.áŒŠà.HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, var11);
        }
        if (this.ˆà.áŒŠà && this.£á) {
            this.Ñ¢á.HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, var11);
        }
        if (var7 != BiomeGenBase.ˆà && var7 != BiomeGenBase.ÇŽÕ && this.ˆà.Ñ¢á && !var10 && this.Ø.nextInt(this.ˆà.ŒÏ) == 0) {
            final int var12 = this.Ø.nextInt(16) + 8;
            final int var13 = this.Ø.nextInt(256);
            final int var14 = this.Ø.nextInt(16) + 8;
            new WorldGenLakes(Blocks.ÂµÈ).HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, var6.Â(var12, var13, var14));
        }
        if (!var10 && this.Ø.nextInt(this.ˆà.Ê / 10) == 0 && this.ˆà.Çªà¢) {
            final int var12 = this.Ø.nextInt(16) + 8;
            final int var13 = this.Ø.nextInt(this.Ø.nextInt(248) + 8);
            final int var14 = this.Ø.nextInt(16) + 8;
            if (var13 < 63 || this.Ø.nextInt(this.ˆà.Ê / 8) == 0) {
                new WorldGenLakes(Blocks.ˆÏ­).HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, var6.Â(var12, var13, var14));
            }
        }
        if (this.ˆà.¥Æ) {
            for (int var12 = 0; var12 < this.ˆà.Ø­à; ++var12) {
                final int var13 = this.Ø.nextInt(16) + 8;
                final int var14 = this.Ø.nextInt(256);
                final int var15 = this.Ø.nextInt(16) + 8;
                new WorldGenDungeons().HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, var6.Â(var13, var14, var15));
            }
        }
        var7.HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, new BlockPos(var4, 0, var5));
        SpawnerAnimals.HorizonCode_Horizon_È(this.ˆÏ­, var7, var4 + 8, var5 + 8, 16, 16, this.Ø);
        var6 = var6.Â(8, 0, 8);
        for (int var12 = 0; var12 < 16; ++var12) {
            for (int var13 = 0; var13 < 16; ++var13) {
                final BlockPos var16 = this.ˆÏ­.µà(var6.Â(var12, 0, var13));
                final BlockPos var17 = var16.Âµá€();
                if (this.ˆÏ­.µÕ(var17)) {
                    this.ˆÏ­.HorizonCode_Horizon_È(var17, Blocks.¥Ï.¥à(), 2);
                }
                if (this.ˆÏ­.Ó(var16, true)) {
                    this.ˆÏ­.HorizonCode_Horizon_È(var16, Blocks.áŒŠá€.¥à(), 2);
                }
            }
        }
        BlockFalling.ŠÂµà = false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IChunkProvider p_177460_1_, final Chunk p_177460_2_, final int p_177460_3_, final int p_177460_4_) {
        boolean var5 = false;
        if (this.ˆà.áŒŠà && this.£á && p_177460_2_.Šáƒ() < 3600L) {
            var5 |= this.Ñ¢á.HorizonCode_Horizon_È(this.ˆÏ­, this.Ø, new ChunkCoordIntPair(p_177460_3_, p_177460_4_));
        }
        return var5;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final boolean p_73151_1_, final IProgressUpdate p_73151_2_) {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public boolean Ý() {
        return true;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return "RandomLevelSource";
    }
    
    @Override
    public List HorizonCode_Horizon_È(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
        final BiomeGenBase var3 = this.ˆÏ­.Ý(p_177458_2_);
        if (this.£á) {
            if (p_177458_1_ == EnumCreatureType.HorizonCode_Horizon_È && this.áŒŠà.HorizonCode_Horizon_È(p_177458_2_)) {
                return this.áŒŠà.B_();
            }
            if (p_177458_1_ == EnumCreatureType.HorizonCode_Horizon_È && this.ˆà.áŒŠà && this.Ñ¢á.HorizonCode_Horizon_È(this.ˆÏ­, p_177458_2_)) {
                return this.Ñ¢á.Ý();
            }
        }
        return var3.HorizonCode_Horizon_È(p_177458_1_);
    }
    
    @Override
    public BlockPos HorizonCode_Horizon_È(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
        return ("Stronghold".equals(p_180513_2_) && this.Æ != null) ? this.Æ.Â(worldIn, p_180513_3_) : null;
    }
    
    @Override
    public int Âµá€() {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {
        if (this.ˆà.Šáƒ && this.£á) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this, this.ˆÏ­, p_180514_2_, p_180514_3_, null);
        }
        if (this.ˆà.Æ && this.£á) {
            this.Šáƒ.HorizonCode_Horizon_È(this, this.ˆÏ­, p_180514_2_, p_180514_3_, null);
        }
        if (this.ˆà.µÕ && this.£á) {
            this.Æ.HorizonCode_Horizon_È(this, this.ˆÏ­, p_180514_2_, p_180514_3_, null);
        }
        if (this.ˆà.Ï­Ðƒà && this.£á) {
            this.áŒŠà.HorizonCode_Horizon_È(this, this.ˆÏ­, p_180514_2_, p_180514_3_, null);
        }
        if (this.ˆà.áŒŠà && this.£á) {
            this.Ñ¢á.HorizonCode_Horizon_È(this, this.ˆÏ­, p_180514_2_, p_180514_3_, null);
        }
    }
    
    @Override
    public Chunk HorizonCode_Horizon_È(final BlockPos p_177459_1_) {
        return this.Ø­áŒŠá(p_177459_1_.HorizonCode_Horizon_È() >> 4, p_177459_1_.Ý() >> 4);
    }
}
