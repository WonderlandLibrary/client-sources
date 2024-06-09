package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.base.Predicate;
import java.util.Random;

public class ChunkProviderHell implements IChunkProvider
{
    private final World Ø;
    private final boolean áŒŠÆ;
    private final Random áˆºÑ¢Õ;
    private double[] ÂµÈ;
    private double[] á;
    private double[] ˆÏ­;
    private double[] £á;
    private final NoiseGeneratorOctaves Å;
    private final NoiseGeneratorOctaves £à;
    private final NoiseGeneratorOctaves µà;
    private final NoiseGeneratorOctaves ˆà;
    private final NoiseGeneratorOctaves ¥Æ;
    public final NoiseGeneratorOctaves HorizonCode_Horizon_È;
    public final NoiseGeneratorOctaves Â;
    private final WorldGenFire Ø­à;
    private final WorldGenGlowStone1 µÕ;
    private final WorldGenGlowStone2 Æ;
    private final WorldGenerator Šáƒ;
    private final WorldGenHellLava Ï­Ðƒà;
    private final WorldGenHellLava áŒŠà;
    private final GeneratorBushFeature ŠÄ;
    private final GeneratorBushFeature Ñ¢á;
    private final MapGenNetherBridge ŒÏ;
    private final MapGenBase Çªà¢;
    double[] Ý;
    double[] Ø­áŒŠá;
    double[] Âµá€;
    double[] Ó;
    double[] à;
    private static final String Ê = "CL_00000392";
    
    public ChunkProviderHell(final World worldIn, final boolean p_i45637_2_, final long p_i45637_3_) {
        this.ÂµÈ = new double[256];
        this.á = new double[256];
        this.ˆÏ­ = new double[256];
        this.Ø­à = new WorldGenFire();
        this.µÕ = new WorldGenGlowStone1();
        this.Æ = new WorldGenGlowStone2();
        this.Šáƒ = new WorldGenMinable(Blocks.ÐƒáˆºÄ.¥à(), 14, (Predicate)BlockHelper.HorizonCode_Horizon_È(Blocks.áŒŠÔ));
        this.Ï­Ðƒà = new WorldGenHellLava(Blocks.á, true);
        this.áŒŠà = new WorldGenHellLava(Blocks.á, false);
        this.ŠÄ = new GeneratorBushFeature(Blocks.È);
        this.Ñ¢á = new GeneratorBushFeature(Blocks.áŠ);
        this.ŒÏ = new MapGenNetherBridge();
        this.Çªà¢ = new MapGenCavesHell();
        this.Ø = worldIn;
        this.áŒŠÆ = p_i45637_2_;
        this.áˆºÑ¢Õ = new Random(p_i45637_3_);
        this.Å = new NoiseGeneratorOctaves(this.áˆºÑ¢Õ, 16);
        this.£à = new NoiseGeneratorOctaves(this.áˆºÑ¢Õ, 16);
        this.µà = new NoiseGeneratorOctaves(this.áˆºÑ¢Õ, 8);
        this.ˆà = new NoiseGeneratorOctaves(this.áˆºÑ¢Õ, 4);
        this.¥Æ = new NoiseGeneratorOctaves(this.áˆºÑ¢Õ, 4);
        this.HorizonCode_Horizon_È = new NoiseGeneratorOctaves(this.áˆºÑ¢Õ, 10);
        this.Â = new NoiseGeneratorOctaves(this.áˆºÑ¢Õ, 16);
    }
    
    public void HorizonCode_Horizon_È(final int p_180515_1_, final int p_180515_2_, final ChunkPrimer p_180515_3_) {
        final byte var4 = 4;
        final byte var5 = 32;
        final int var6 = var4 + 1;
        final byte var7 = 17;
        final int var8 = var4 + 1;
        this.£á = this.HorizonCode_Horizon_È(this.£á, p_180515_1_ * var4, 0, p_180515_2_ * var4, var6, var7, var8);
        for (int var9 = 0; var9 < var4; ++var9) {
            for (int var10 = 0; var10 < var4; ++var10) {
                for (int var11 = 0; var11 < 16; ++var11) {
                    final double var12 = 0.125;
                    double var13 = this.£á[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var14 = this.£á[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 0];
                    double var15 = this.£á[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 0];
                    double var16 = this.£á[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 0];
                    final double var17 = (this.£á[((var9 + 0) * var8 + var10 + 0) * var7 + var11 + 1] - var13) * var12;
                    final double var18 = (this.£á[((var9 + 0) * var8 + var10 + 1) * var7 + var11 + 1] - var14) * var12;
                    final double var19 = (this.£á[((var9 + 1) * var8 + var10 + 0) * var7 + var11 + 1] - var15) * var12;
                    final double var20 = (this.£á[((var9 + 1) * var8 + var10 + 1) * var7 + var11 + 1] - var16) * var12;
                    for (int var21 = 0; var21 < 8; ++var21) {
                        final double var22 = 0.25;
                        double var23 = var13;
                        double var24 = var14;
                        final double var25 = (var15 - var13) * var22;
                        final double var26 = (var16 - var14) * var22;
                        for (int var27 = 0; var27 < 4; ++var27) {
                            final double var28 = 0.25;
                            double var29 = var23;
                            final double var30 = (var24 - var23) * var28;
                            for (int var31 = 0; var31 < 4; ++var31) {
                                IBlockState var32 = null;
                                if (var11 * 8 + var21 < var5) {
                                    var32 = Blocks.ˆÏ­.¥à();
                                }
                                if (var29 > 0.0) {
                                    var32 = Blocks.áŒŠÔ.¥à();
                                }
                                final int var33 = var27 + var9 * 4;
                                final int var34 = var21 + var11 * 8;
                                final int var35 = var31 + var10 * 4;
                                p_180515_3_.HorizonCode_Horizon_È(var33, var34, var35, var32);
                                var29 += var30;
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
    
    public void Â(final int p_180516_1_, final int p_180516_2_, final ChunkPrimer p_180516_3_) {
        final byte var4 = 64;
        final double var5 = 0.03125;
        this.ÂµÈ = this.ˆà.HorizonCode_Horizon_È(this.ÂµÈ, p_180516_1_ * 16, p_180516_2_ * 16, 0, 16, 16, 1, var5, var5, 1.0);
        this.á = this.ˆà.HorizonCode_Horizon_È(this.á, p_180516_1_ * 16, 109, p_180516_2_ * 16, 16, 1, 16, var5, 1.0, var5);
        this.ˆÏ­ = this.¥Æ.HorizonCode_Horizon_È(this.ˆÏ­, p_180516_1_ * 16, p_180516_2_ * 16, 0, 16, 16, 1, var5 * 2.0, var5 * 2.0, var5 * 2.0);
        for (int var6 = 0; var6 < 16; ++var6) {
            for (int var7 = 0; var7 < 16; ++var7) {
                final boolean var8 = this.ÂµÈ[var6 + var7 * 16] + this.áˆºÑ¢Õ.nextDouble() * 0.2 > 0.0;
                final boolean var9 = this.á[var6 + var7 * 16] + this.áˆºÑ¢Õ.nextDouble() * 0.2 > 0.0;
                final int var10 = (int)(this.ˆÏ­[var6 + var7 * 16] / 3.0 + 3.0 + this.áˆºÑ¢Õ.nextDouble() * 0.25);
                int var11 = -1;
                IBlockState var12 = Blocks.áŒŠÔ.¥à();
                IBlockState var13 = Blocks.áŒŠÔ.¥à();
                for (int var14 = 127; var14 >= 0; --var14) {
                    if (var14 < 127 - this.áˆºÑ¢Õ.nextInt(5) && var14 > this.áˆºÑ¢Õ.nextInt(5)) {
                        final IBlockState var15 = p_180516_3_.HorizonCode_Horizon_È(var7, var14, var6);
                        if (var15.Ý() != null && var15.Ý().Ó() != Material.HorizonCode_Horizon_È) {
                            if (var15.Ý() == Blocks.áŒŠÔ) {
                                if (var11 == -1) {
                                    if (var10 <= 0) {
                                        var12 = null;
                                        var13 = Blocks.áŒŠÔ.¥à();
                                    }
                                    else if (var14 >= var4 - 4 && var14 <= var4 + 1) {
                                        var12 = Blocks.áŒŠÔ.¥à();
                                        var13 = Blocks.áŒŠÔ.¥à();
                                        if (var9) {
                                            var12 = Blocks.Å.¥à();
                                            var13 = Blocks.áŒŠÔ.¥à();
                                        }
                                        if (var8) {
                                            var12 = Blocks.ŠÕ.¥à();
                                            var13 = Blocks.ŠÕ.¥à();
                                        }
                                    }
                                    if (var14 < var4 && (var12 == null || var12.Ý().Ó() == Material.HorizonCode_Horizon_È)) {
                                        var12 = Blocks.ˆÏ­.¥à();
                                    }
                                    var11 = var10;
                                    if (var14 >= var4 - 1) {
                                        p_180516_3_.HorizonCode_Horizon_È(var7, var14, var6, var12);
                                    }
                                    else {
                                        p_180516_3_.HorizonCode_Horizon_È(var7, var14, var6, var13);
                                    }
                                }
                                else if (var11 > 0) {
                                    --var11;
                                    p_180516_3_.HorizonCode_Horizon_È(var7, var14, var6, var13);
                                }
                            }
                        }
                        else {
                            var11 = -1;
                        }
                    }
                    else {
                        p_180516_3_.HorizonCode_Horizon_È(var7, var14, var6, Blocks.áŒŠÆ.¥à());
                    }
                }
            }
        }
    }
    
    @Override
    public Chunk Ø­áŒŠá(final int p_73154_1_, final int p_73154_2_) {
        this.áˆºÑ¢Õ.setSeed(p_73154_1_ * 341873128712L + p_73154_2_ * 132897987541L);
        final ChunkPrimer var3 = new ChunkPrimer();
        this.HorizonCode_Horizon_È(p_73154_1_, p_73154_2_, var3);
        this.Â(p_73154_1_, p_73154_2_, var3);
        this.Çªà¢.HorizonCode_Horizon_È(this, this.Ø, p_73154_1_, p_73154_2_, var3);
        if (this.áŒŠÆ) {
            this.ŒÏ.HorizonCode_Horizon_È(this, this.Ø, p_73154_1_, p_73154_2_, var3);
        }
        final Chunk var4 = new Chunk(this.Ø, var3, p_73154_1_, p_73154_2_);
        final BiomeGenBase[] var5 = this.Ø.áŒŠÆ().Â(null, p_73154_1_ * 16, p_73154_2_ * 16, 16, 16);
        final byte[] var6 = var4.ÂµÈ();
        for (int var7 = 0; var7 < var6.length; ++var7) {
            var6[var7] = (byte)var5[var7].ÇªÔ;
        }
        var4.á();
        return var4;
    }
    
    private double[] HorizonCode_Horizon_È(double[] p_73164_1_, final int p_73164_2_, final int p_73164_3_, final int p_73164_4_, final int p_73164_5_, final int p_73164_6_, final int p_73164_7_) {
        if (p_73164_1_ == null) {
            p_73164_1_ = new double[p_73164_5_ * p_73164_6_ * p_73164_7_];
        }
        final double var8 = 684.412;
        final double var9 = 2053.236;
        this.Ó = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(this.Ó, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 1.0, 0.0, 1.0);
        this.à = this.Â.HorizonCode_Horizon_È(this.à, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 100.0, 0.0, 100.0);
        this.Ý = this.µà.HorizonCode_Horizon_È(this.Ý, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, var8 / 80.0, var9 / 60.0, var8 / 80.0);
        this.Ø­áŒŠá = this.Å.HorizonCode_Horizon_È(this.Ø­áŒŠá, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, var8, var9, var8);
        this.Âµá€ = this.£à.HorizonCode_Horizon_È(this.Âµá€, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, var8, var9, var8);
        int var10 = 0;
        final double[] var11 = new double[p_73164_6_];
        for (int var12 = 0; var12 < p_73164_6_; ++var12) {
            var11[var12] = Math.cos(var12 * 3.141592653589793 * 6.0 / p_73164_6_) * 2.0;
            double var13 = var12;
            if (var12 > p_73164_6_ / 2) {
                var13 = p_73164_6_ - 1 - var12;
            }
            if (var13 < 4.0) {
                var13 = 4.0 - var13;
                final double[] array = var11;
                final int n = var12;
                array[n] -= var13 * var13 * var13 * 10.0;
            }
        }
        for (int var12 = 0; var12 < p_73164_5_; ++var12) {
            for (int var14 = 0; var14 < p_73164_7_; ++var14) {
                final double var15 = 0.0;
                for (int var16 = 0; var16 < p_73164_6_; ++var16) {
                    double var17 = 0.0;
                    final double var18 = var11[var16];
                    final double var19 = this.Ø­áŒŠá[var10] / 512.0;
                    final double var20 = this.Âµá€[var10] / 512.0;
                    final double var21 = (this.Ý[var10] / 10.0 + 1.0) / 2.0;
                    if (var21 < 0.0) {
                        var17 = var19;
                    }
                    else if (var21 > 1.0) {
                        var17 = var20;
                    }
                    else {
                        var17 = var19 + (var20 - var19) * var21;
                    }
                    var17 -= var18;
                    if (var16 > p_73164_6_ - 4) {
                        final double var22 = (var16 - (p_73164_6_ - 4)) / 3.0f;
                        var17 = var17 * (1.0 - var22) + -10.0 * var22;
                    }
                    if (var16 < var15) {
                        double var22 = (var15 - var16) / 4.0;
                        var22 = MathHelper.HorizonCode_Horizon_È(var22, 0.0, 1.0);
                        var17 = var17 * (1.0 - var22) + -10.0 * var22;
                    }
                    p_73164_1_[var10] = var17;
                    ++var10;
                }
            }
        }
        return p_73164_1_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int p_73149_1_, final int p_73149_2_) {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IChunkProvider p_73153_1_, final int p_73153_2_, final int p_73153_3_) {
        BlockFalling.ŠÂµà = true;
        final BlockPos var4 = new BlockPos(p_73153_2_ * 16, 0, p_73153_3_ * 16);
        final ChunkCoordIntPair var5 = new ChunkCoordIntPair(p_73153_2_, p_73153_3_);
        this.ŒÏ.HorizonCode_Horizon_È(this.Ø, this.áˆºÑ¢Õ, var5);
        for (int var6 = 0; var6 < 8; ++var6) {
            this.áŒŠà.HorizonCode_Horizon_È(this.Ø, this.áˆºÑ¢Õ, var4.Â(this.áˆºÑ¢Õ.nextInt(16) + 8, this.áˆºÑ¢Õ.nextInt(120) + 4, this.áˆºÑ¢Õ.nextInt(16) + 8));
        }
        for (int var6 = 0; var6 < this.áˆºÑ¢Õ.nextInt(this.áˆºÑ¢Õ.nextInt(10) + 1) + 1; ++var6) {
            this.Ø­à.HorizonCode_Horizon_È(this.Ø, this.áˆºÑ¢Õ, var4.Â(this.áˆºÑ¢Õ.nextInt(16) + 8, this.áˆºÑ¢Õ.nextInt(120) + 4, this.áˆºÑ¢Õ.nextInt(16) + 8));
        }
        for (int var6 = 0; var6 < this.áˆºÑ¢Õ.nextInt(this.áˆºÑ¢Õ.nextInt(10) + 1); ++var6) {
            this.µÕ.HorizonCode_Horizon_È(this.Ø, this.áˆºÑ¢Õ, var4.Â(this.áˆºÑ¢Õ.nextInt(16) + 8, this.áˆºÑ¢Õ.nextInt(120) + 4, this.áˆºÑ¢Õ.nextInt(16) + 8));
        }
        for (int var6 = 0; var6 < 10; ++var6) {
            this.Æ.HorizonCode_Horizon_È(this.Ø, this.áˆºÑ¢Õ, var4.Â(this.áˆºÑ¢Õ.nextInt(16) + 8, this.áˆºÑ¢Õ.nextInt(128), this.áˆºÑ¢Õ.nextInt(16) + 8));
        }
        if (this.áˆºÑ¢Õ.nextBoolean()) {
            this.ŠÄ.HorizonCode_Horizon_È(this.Ø, this.áˆºÑ¢Õ, var4.Â(this.áˆºÑ¢Õ.nextInt(16) + 8, this.áˆºÑ¢Õ.nextInt(128), this.áˆºÑ¢Õ.nextInt(16) + 8));
        }
        if (this.áˆºÑ¢Õ.nextBoolean()) {
            this.Ñ¢á.HorizonCode_Horizon_È(this.Ø, this.áˆºÑ¢Õ, var4.Â(this.áˆºÑ¢Õ.nextInt(16) + 8, this.áˆºÑ¢Õ.nextInt(128), this.áˆºÑ¢Õ.nextInt(16) + 8));
        }
        for (int var6 = 0; var6 < 16; ++var6) {
            this.Šáƒ.HorizonCode_Horizon_È(this.Ø, this.áˆºÑ¢Õ, var4.Â(this.áˆºÑ¢Õ.nextInt(16), this.áˆºÑ¢Õ.nextInt(108) + 10, this.áˆºÑ¢Õ.nextInt(16)));
        }
        for (int var6 = 0; var6 < 16; ++var6) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this.Ø, this.áˆºÑ¢Õ, var4.Â(this.áˆºÑ¢Õ.nextInt(16), this.áˆºÑ¢Õ.nextInt(108) + 10, this.áˆºÑ¢Õ.nextInt(16)));
        }
        BlockFalling.ŠÂµà = false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IChunkProvider p_177460_1_, final Chunk p_177460_2_, final int p_177460_3_, final int p_177460_4_) {
        return false;
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
        return "HellRandomLevelSource";
    }
    
    @Override
    public List HorizonCode_Horizon_È(final EnumCreatureType p_177458_1_, final BlockPos p_177458_2_) {
        if (p_177458_1_ == EnumCreatureType.HorizonCode_Horizon_È) {
            if (this.ŒÏ.Â(p_177458_2_)) {
                return this.ŒÏ.A_();
            }
            if (this.ŒÏ.HorizonCode_Horizon_È(this.Ø, p_177458_2_) && this.Ø.Â(p_177458_2_.Âµá€()).Ý() == Blocks.µÂ) {
                return this.ŒÏ.A_();
            }
        }
        final BiomeGenBase var3 = this.Ø.Ý(p_177458_2_);
        return var3.HorizonCode_Horizon_È(p_177458_1_);
    }
    
    @Override
    public BlockPos HorizonCode_Horizon_È(final World worldIn, final String p_180513_2_, final BlockPos p_180513_3_) {
        return null;
    }
    
    @Override
    public int Âµá€() {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Chunk p_180514_1_, final int p_180514_2_, final int p_180514_3_) {
        this.ŒÏ.HorizonCode_Horizon_È(this, this.Ø, p_180514_2_, p_180514_3_, null);
    }
    
    @Override
    public Chunk HorizonCode_Horizon_È(final BlockPos p_177459_1_) {
        return this.Ø­áŒŠá(p_177459_1_.HorizonCode_Horizon_È() >> 4, p_177459_1_.Ý() >> 4);
    }
}
