package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class WorldGenBigTree extends WorldGenAbstractTree
{
    private Random ÂµÈ;
    private World á;
    private BlockPos ˆÏ­;
    int HorizonCode_Horizon_È;
    int Â;
    double Ý;
    double Ø­áŒŠá;
    double Âµá€;
    double Ó;
    int à;
    int Ø;
    int áŒŠÆ;
    List áˆºÑ¢Õ;
    private static final String £á = "CL_00000400";
    
    public WorldGenBigTree(final boolean p_i2008_1_) {
        super(p_i2008_1_);
        this.ˆÏ­ = BlockPos.HorizonCode_Horizon_È;
        this.Ý = 0.618;
        this.Ø­áŒŠá = 0.381;
        this.Âµá€ = 1.0;
        this.Ó = 1.0;
        this.à = 1;
        this.Ø = 12;
        this.áŒŠÆ = 4;
    }
    
    void HorizonCode_Horizon_È() {
        this.Â = (int)(this.HorizonCode_Horizon_È * this.Ý);
        if (this.Â >= this.HorizonCode_Horizon_È) {
            this.Â = this.HorizonCode_Horizon_È - 1;
        }
        int var1 = (int)(1.382 + Math.pow(this.Ó * this.HorizonCode_Horizon_È / 13.0, 2.0));
        if (var1 < 1) {
            var1 = 1;
        }
        final int var2 = this.ˆÏ­.Â() + this.Â;
        int var3 = this.HorizonCode_Horizon_È - this.áŒŠÆ;
        (this.áˆºÑ¢Õ = Lists.newArrayList()).add(new HorizonCode_Horizon_È(this.ˆÏ­.Â(var3), var2));
        while (var3 >= 0) {
            final float var4 = this.HorizonCode_Horizon_È(var3);
            if (var4 >= 0.0f) {
                for (int var5 = 0; var5 < var1; ++var5) {
                    final double var6 = this.Âµá€ * var4 * (this.ÂµÈ.nextFloat() + 0.328);
                    final double var7 = this.ÂµÈ.nextFloat() * 2.0f * 3.141592653589793;
                    final double var8 = var6 * Math.sin(var7) + 0.5;
                    final double var9 = var6 * Math.cos(var7) + 0.5;
                    final BlockPos var10 = this.ˆÏ­.Â(var8, var3 - 1, var9);
                    final BlockPos var11 = var10.Â(this.áŒŠÆ);
                    if (this.HorizonCode_Horizon_È(var10, var11) == -1) {
                        final int var12 = this.ˆÏ­.HorizonCode_Horizon_È() - var10.HorizonCode_Horizon_È();
                        final int var13 = this.ˆÏ­.Ý() - var10.Ý();
                        final double var14 = var10.Â() - Math.sqrt(var12 * var12 + var13 * var13) * this.Ø­áŒŠá;
                        final int var15 = (var14 > var2) ? var2 : ((int)var14);
                        final BlockPos var16 = new BlockPos(this.ˆÏ­.HorizonCode_Horizon_È(), var15, this.ˆÏ­.Ý());
                        if (this.HorizonCode_Horizon_È(var16, var10) == -1) {
                            this.áˆºÑ¢Õ.add(new HorizonCode_Horizon_È(var10, var16.Â()));
                        }
                    }
                }
            }
            --var3;
        }
    }
    
    void HorizonCode_Horizon_È(final BlockPos p_180712_1_, final float p_180712_2_, final Block p_180712_3_) {
        for (int var4 = (int)(p_180712_2_ + 0.618), var5 = -var4; var5 <= var4; ++var5) {
            for (int var6 = -var4; var6 <= var4; ++var6) {
                if (Math.pow(Math.abs(var5) + 0.5, 2.0) + Math.pow(Math.abs(var6) + 0.5, 2.0) <= p_180712_2_ * p_180712_2_) {
                    final BlockPos var7 = p_180712_1_.Â(var5, 0, var6);
                    final Material var8 = this.á.Â(var7).Ý().Ó();
                    if (var8 == Material.HorizonCode_Horizon_È || var8 == Material.áˆºÑ¢Õ) {
                        this.HorizonCode_Horizon_È(this.á, var7, p_180712_3_, 0);
                    }
                }
            }
        }
    }
    
    float HorizonCode_Horizon_È(final int p_76490_1_) {
        if (p_76490_1_ < this.HorizonCode_Horizon_È * 0.3f) {
            return -1.0f;
        }
        final float var2 = this.HorizonCode_Horizon_È / 2.0f;
        final float var3 = var2 - p_76490_1_;
        float var4 = MathHelper.Ý(var2 * var2 - var3 * var3);
        if (var3 == 0.0f) {
            var4 = var2;
        }
        else if (Math.abs(var3) >= var2) {
            return 0.0f;
        }
        return var4 * 0.5f;
    }
    
    float Â(final int p_76495_1_) {
        return (p_76495_1_ >= 0 && p_76495_1_ < this.áŒŠÆ) ? ((p_76495_1_ != 0 && p_76495_1_ != this.áŒŠÆ - 1) ? 3.0f : 2.0f) : -1.0f;
    }
    
    void HorizonCode_Horizon_È(final BlockPos p_175940_1_) {
        for (int var2 = 0; var2 < this.áŒŠÆ; ++var2) {
            this.HorizonCode_Horizon_È(p_175940_1_.Â(var2), this.Â(var2), Blocks.µÕ);
        }
    }
    
    void HorizonCode_Horizon_È(final BlockPos p_175937_1_, final BlockPos p_175937_2_, final Block p_175937_3_) {
        final BlockPos var4 = p_175937_2_.Â(-p_175937_1_.HorizonCode_Horizon_È(), -p_175937_1_.Â(), -p_175937_1_.Ý());
        final int var5 = this.Â(var4);
        final float var6 = var4.HorizonCode_Horizon_È() / var5;
        final float var7 = var4.Â() / var5;
        final float var8 = var4.Ý() / var5;
        for (int var9 = 0; var9 <= var5; ++var9) {
            final BlockPos var10 = p_175937_1_.Â(0.5f + var9 * var6, 0.5f + var9 * var7, 0.5f + var9 * var8);
            final BlockLog.HorizonCode_Horizon_È var11 = this.Â(p_175937_1_, var10);
            this.HorizonCode_Horizon_È(this.á, var10, p_175937_3_.¥à().HorizonCode_Horizon_È(BlockLog.Õ, var11));
        }
    }
    
    private int Â(final BlockPos p_175935_1_) {
        final int var2 = MathHelper.HorizonCode_Horizon_È(p_175935_1_.HorizonCode_Horizon_È());
        final int var3 = MathHelper.HorizonCode_Horizon_È(p_175935_1_.Â());
        final int var4 = MathHelper.HorizonCode_Horizon_È(p_175935_1_.Ý());
        return (var4 > var2 && var4 > var3) ? var4 : ((var3 > var2) ? var3 : var2);
    }
    
    private BlockLog.HorizonCode_Horizon_È Â(final BlockPos p_175938_1_, final BlockPos p_175938_2_) {
        BlockLog.HorizonCode_Horizon_È var3 = BlockLog.HorizonCode_Horizon_È.Â;
        final int var4 = Math.abs(p_175938_2_.HorizonCode_Horizon_È() - p_175938_1_.HorizonCode_Horizon_È());
        final int var5 = Math.abs(p_175938_2_.Ý() - p_175938_1_.Ý());
        final int var6 = Math.max(var4, var5);
        if (var6 > 0) {
            if (var4 == var6) {
                var3 = BlockLog.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            }
            else if (var5 == var6) {
                var3 = BlockLog.HorizonCode_Horizon_È.Ý;
            }
        }
        return var3;
    }
    
    void Â() {
        for (final HorizonCode_Horizon_È var2 : this.áˆºÑ¢Õ) {
            this.HorizonCode_Horizon_È(var2);
        }
    }
    
    boolean Ý(final int p_76493_1_) {
        return p_76493_1_ >= this.HorizonCode_Horizon_È * 0.2;
    }
    
    void Ý() {
        final BlockPos var1 = this.ˆÏ­;
        final BlockPos var2 = this.ˆÏ­.Â(this.Â);
        final Block var3 = Blocks.¥Æ;
        this.HorizonCode_Horizon_È(var1, var2, var3);
        if (this.à == 2) {
            this.HorizonCode_Horizon_È(var1.áŒŠÆ(), var2.áŒŠÆ(), var3);
            this.HorizonCode_Horizon_È(var1.áŒŠÆ().à(), var2.áŒŠÆ().à(), var3);
            this.HorizonCode_Horizon_È(var1.à(), var2.à(), var3);
        }
    }
    
    void Ø­áŒŠá() {
        for (final HorizonCode_Horizon_È var2 : this.áˆºÑ¢Õ) {
            final int var3 = var2.ÂµÈ();
            final BlockPos var4 = new BlockPos(this.ˆÏ­.HorizonCode_Horizon_È(), var3, this.ˆÏ­.Ý());
            if (this.Ý(var3 - this.ˆÏ­.Â())) {
                this.HorizonCode_Horizon_È(var4, var2, Blocks.¥Æ);
            }
        }
    }
    
    int HorizonCode_Horizon_È(final BlockPos p_175936_1_, final BlockPos p_175936_2_) {
        final BlockPos var3 = p_175936_2_.Â(-p_175936_1_.HorizonCode_Horizon_È(), -p_175936_1_.Â(), -p_175936_1_.Ý());
        final int var4 = this.Â(var3);
        final float var5 = var3.HorizonCode_Horizon_È() / var4;
        final float var6 = var3.Â() / var4;
        final float var7 = var3.Ý() / var4;
        if (var4 == 0) {
            return -1;
        }
        for (int var8 = 0; var8 <= var4; ++var8) {
            final BlockPos var9 = p_175936_1_.Â(0.5f + var8 * var5, 0.5f + var8 * var6, 0.5f + var8 * var7);
            if (!this.HorizonCode_Horizon_È(this.á.Â(var9).Ý())) {
                return var8;
            }
        }
        return -1;
    }
    
    @Override
    public void Âµá€() {
        this.áŒŠÆ = 5;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        this.á = worldIn;
        this.ˆÏ­ = p_180709_3_;
        this.ÂµÈ = new Random(p_180709_2_.nextLong());
        if (this.HorizonCode_Horizon_È == 0) {
            this.HorizonCode_Horizon_È = 5 + this.ÂµÈ.nextInt(this.Ø);
        }
        if (!this.Ó()) {
            return false;
        }
        this.HorizonCode_Horizon_È();
        this.Â();
        this.Ý();
        this.Ø­áŒŠá();
        return true;
    }
    
    private boolean Ó() {
        final Block var1 = this.á.Â(this.ˆÏ­.Âµá€()).Ý();
        if (var1 != Blocks.Âµá€ && var1 != Blocks.Ø­áŒŠá && var1 != Blocks.£Â) {
            return false;
        }
        final int var2 = this.HorizonCode_Horizon_È(this.ˆÏ­, this.ˆÏ­.Â(this.HorizonCode_Horizon_È - 1));
        if (var2 == -1) {
            return true;
        }
        if (var2 < 6) {
            return false;
        }
        this.HorizonCode_Horizon_È = var2;
        return true;
    }
    
    static class HorizonCode_Horizon_È extends BlockPos
    {
        private final int Â;
        private static final String Ý = "CL_00002001";
        
        public HorizonCode_Horizon_È(final BlockPos p_i45635_1_, final int p_i45635_2_) {
            super(p_i45635_1_.HorizonCode_Horizon_È(), p_i45635_1_.Â(), p_i45635_1_.Ý());
            this.Â = p_i45635_2_;
        }
        
        public int ÂµÈ() {
            return this.Â;
        }
    }
}
