package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.collect.Lists;
import java.util.List;

public class WorldChunkManager
{
    private GenLayer HorizonCode_Horizon_È;
    private GenLayer Â;
    private BiomeCache Ý;
    private List Ø­áŒŠá;
    private String Âµá€;
    private static final String Ó = "CL_00000166";
    
    protected WorldChunkManager() {
        this.Ý = new BiomeCache(this);
        this.Âµá€ = "";
        (this.Ø­áŒŠá = Lists.newArrayList()).add(BiomeGenBase.Ø­à);
        this.Ø­áŒŠá.add(BiomeGenBase.µà);
        this.Ø­áŒŠá.add(BiomeGenBase.µÕ);
        this.Ø­áŒŠá.add(BiomeGenBase.áƒ);
        this.Ø­áŒŠá.add(BiomeGenBase.É);
        this.Ø­áŒŠá.add(BiomeGenBase.Õ);
        this.Ø­áŒŠá.add(BiomeGenBase.à¢);
    }
    
    public WorldChunkManager(final long p_i45744_1_, final WorldType p_i45744_3_, final String p_i45744_4_) {
        this();
        this.Âµá€ = p_i45744_4_;
        final GenLayer[] var5 = GenLayer.HorizonCode_Horizon_È(p_i45744_1_, p_i45744_3_, p_i45744_4_);
        this.HorizonCode_Horizon_È = var5[0];
        this.Â = var5[1];
    }
    
    public WorldChunkManager(final World worldIn) {
        this(worldIn.Æ(), worldIn.ŒÏ().Ø­à(), worldIn.ŒÏ().Ñ¢á());
    }
    
    public List HorizonCode_Horizon_È() {
        return this.Ø­áŒŠá;
    }
    
    public BiomeGenBase HorizonCode_Horizon_È(final BlockPos p_180631_1_) {
        return this.HorizonCode_Horizon_È(p_180631_1_, null);
    }
    
    public BiomeGenBase HorizonCode_Horizon_È(final BlockPos p_180300_1_, final BiomeGenBase p_180300_2_) {
        return this.Ý.HorizonCode_Horizon_È(p_180300_1_.HorizonCode_Horizon_È(), p_180300_1_.Ý(), p_180300_2_);
    }
    
    public float[] HorizonCode_Horizon_È(float[] p_76936_1_, final int p_76936_2_, final int p_76936_3_, final int p_76936_4_, final int p_76936_5_) {
        IntCache.HorizonCode_Horizon_È();
        if (p_76936_1_ == null || p_76936_1_.length < p_76936_4_ * p_76936_5_) {
            p_76936_1_ = new float[p_76936_4_ * p_76936_5_];
        }
        final int[] var6 = this.Â.HorizonCode_Horizon_È(p_76936_2_, p_76936_3_, p_76936_4_, p_76936_5_);
        for (int var7 = 0; var7 < p_76936_4_ * p_76936_5_; ++var7) {
            try {
                float var8 = BiomeGenBase.HorizonCode_Horizon_È(var6[var7], BiomeGenBase.ÇªÓ).Ø() / 65536.0f;
                if (var8 > 1.0f) {
                    var8 = 1.0f;
                }
                p_76936_1_[var7] = var8;
            }
            catch (Throwable var10) {
                final CrashReport var9 = CrashReport.HorizonCode_Horizon_È(var10, "Invalid Biome id");
                final CrashReportCategory var11 = var9.HorizonCode_Horizon_È("DownfallBlock");
                var11.HorizonCode_Horizon_È("biome id", var7);
                var11.HorizonCode_Horizon_È("downfalls[] size", p_76936_1_.length);
                var11.HorizonCode_Horizon_È("x", p_76936_2_);
                var11.HorizonCode_Horizon_È("z", p_76936_3_);
                var11.HorizonCode_Horizon_È("w", p_76936_4_);
                var11.HorizonCode_Horizon_È("h", p_76936_5_);
                throw new ReportedException(var9);
            }
        }
        return p_76936_1_;
    }
    
    public float HorizonCode_Horizon_È(final float p_76939_1_, final int p_76939_2_) {
        return p_76939_1_;
    }
    
    public BiomeGenBase[] HorizonCode_Horizon_È(BiomeGenBase[] p_76937_1_, final int p_76937_2_, final int p_76937_3_, final int p_76937_4_, final int p_76937_5_) {
        IntCache.HorizonCode_Horizon_È();
        if (p_76937_1_ == null || p_76937_1_.length < p_76937_4_ * p_76937_5_) {
            p_76937_1_ = new BiomeGenBase[p_76937_4_ * p_76937_5_];
        }
        final int[] var6 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_76937_2_, p_76937_3_, p_76937_4_, p_76937_5_);
        try {
            for (int var7 = 0; var7 < p_76937_4_ * p_76937_5_; ++var7) {
                p_76937_1_[var7] = BiomeGenBase.HorizonCode_Horizon_È(var6[var7], BiomeGenBase.ÇªÓ);
            }
            return p_76937_1_;
        }
        catch (Throwable var9) {
            final CrashReport var8 = CrashReport.HorizonCode_Horizon_È(var9, "Invalid Biome id");
            final CrashReportCategory var10 = var8.HorizonCode_Horizon_È("RawBiomeBlock");
            var10.HorizonCode_Horizon_È("biomes[] size", p_76937_1_.length);
            var10.HorizonCode_Horizon_È("x", p_76937_2_);
            var10.HorizonCode_Horizon_È("z", p_76937_3_);
            var10.HorizonCode_Horizon_È("w", p_76937_4_);
            var10.HorizonCode_Horizon_È("h", p_76937_5_);
            throw new ReportedException(var8);
        }
    }
    
    public BiomeGenBase[] Â(final BiomeGenBase[] p_76933_1_, final int p_76933_2_, final int p_76933_3_, final int p_76933_4_, final int p_76933_5_) {
        return this.HorizonCode_Horizon_È(p_76933_1_, p_76933_2_, p_76933_3_, p_76933_4_, p_76933_5_, true);
    }
    
    public BiomeGenBase[] HorizonCode_Horizon_È(BiomeGenBase[] p_76931_1_, final int p_76931_2_, final int p_76931_3_, final int p_76931_4_, final int p_76931_5_, final boolean p_76931_6_) {
        IntCache.HorizonCode_Horizon_È();
        if (p_76931_1_ == null || p_76931_1_.length < p_76931_4_ * p_76931_5_) {
            p_76931_1_ = new BiomeGenBase[p_76931_4_ * p_76931_5_];
        }
        if (p_76931_6_ && p_76931_4_ == 16 && p_76931_5_ == 16 && (p_76931_2_ & 0xF) == 0x0 && (p_76931_3_ & 0xF) == 0x0) {
            final BiomeGenBase[] var9 = this.Ý.Â(p_76931_2_, p_76931_3_);
            System.arraycopy(var9, 0, p_76931_1_, 0, p_76931_4_ * p_76931_5_);
            return p_76931_1_;
        }
        final int[] var10 = this.Â.HorizonCode_Horizon_È(p_76931_2_, p_76931_3_, p_76931_4_, p_76931_5_);
        for (int var11 = 0; var11 < p_76931_4_ * p_76931_5_; ++var11) {
            p_76931_1_[var11] = BiomeGenBase.HorizonCode_Horizon_È(var10[var11], BiomeGenBase.ÇªÓ);
        }
        return p_76931_1_;
    }
    
    public boolean HorizonCode_Horizon_È(final int p_76940_1_, final int p_76940_2_, final int p_76940_3_, final List p_76940_4_) {
        IntCache.HorizonCode_Horizon_È();
        final int var5 = p_76940_1_ - p_76940_3_ >> 2;
        final int var6 = p_76940_2_ - p_76940_3_ >> 2;
        final int var7 = p_76940_1_ + p_76940_3_ >> 2;
        final int var8 = p_76940_2_ + p_76940_3_ >> 2;
        final int var9 = var7 - var5 + 1;
        final int var10 = var8 - var6 + 1;
        final int[] var11 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var5, var6, var9, var10);
        try {
            for (int var12 = 0; var12 < var9 * var10; ++var12) {
                final BiomeGenBase var13 = BiomeGenBase.Âµá€(var11[var12]);
                if (!p_76940_4_.contains(var13)) {
                    return false;
                }
            }
            return true;
        }
        catch (Throwable var15) {
            final CrashReport var14 = CrashReport.HorizonCode_Horizon_È(var15, "Invalid Biome id");
            final CrashReportCategory var16 = var14.HorizonCode_Horizon_È("Layer");
            var16.HorizonCode_Horizon_È("Layer", this.HorizonCode_Horizon_È.toString());
            var16.HorizonCode_Horizon_È("x", p_76940_1_);
            var16.HorizonCode_Horizon_È("z", p_76940_2_);
            var16.HorizonCode_Horizon_È("radius", p_76940_3_);
            var16.HorizonCode_Horizon_È("allowed", p_76940_4_);
            throw new ReportedException(var14);
        }
    }
    
    public BlockPos HorizonCode_Horizon_È(final int x, final int z, final int range, final List biomes, final Random random) {
        IntCache.HorizonCode_Horizon_È();
        final int var6 = x - range >> 2;
        final int var7 = z - range >> 2;
        final int var8 = x + range >> 2;
        final int var9 = z + range >> 2;
        final int var10 = var8 - var6 + 1;
        final int var11 = var9 - var7 + 1;
        final int[] var12 = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var6, var7, var10, var11);
        BlockPos var13 = null;
        int var14 = 0;
        for (int var15 = 0; var15 < var10 * var11; ++var15) {
            final int var16 = var6 + var15 % var10 << 2;
            final int var17 = var7 + var15 / var10 << 2;
            final BiomeGenBase var18 = BiomeGenBase.Âµá€(var12[var15]);
            if (biomes.contains(var18) && (var13 == null || random.nextInt(var14 + 1) == 0)) {
                var13 = new BlockPos(var16, 0, var17);
                ++var14;
            }
        }
        return var13;
    }
    
    public void Â() {
        this.Ý.HorizonCode_Horizon_È();
    }
}
