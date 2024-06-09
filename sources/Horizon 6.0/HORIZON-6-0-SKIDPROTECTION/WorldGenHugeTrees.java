package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public abstract class WorldGenHugeTrees extends WorldGenAbstractTree
{
    protected final int HorizonCode_Horizon_È;
    protected final int Â;
    protected final int Ý;
    protected int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000423";
    
    public WorldGenHugeTrees(final boolean p_i45458_1_, final int p_i45458_2_, final int p_i45458_3_, final int p_i45458_4_, final int p_i45458_5_) {
        super(p_i45458_1_);
        this.HorizonCode_Horizon_È = p_i45458_2_;
        this.Ø­áŒŠá = p_i45458_3_;
        this.Â = p_i45458_4_;
        this.Ý = p_i45458_5_;
    }
    
    protected int HorizonCode_Horizon_È(final Random p_150533_1_) {
        int var2 = p_150533_1_.nextInt(3) + this.HorizonCode_Horizon_È;
        if (this.Ø­áŒŠá > 1) {
            var2 += p_150533_1_.nextInt(this.Ø­áŒŠá);
        }
        return var2;
    }
    
    private boolean Ý(final World worldIn, final BlockPos p_175926_2_, final int p_175926_3_) {
        boolean var4 = true;
        if (p_175926_2_.Â() >= 1 && p_175926_2_.Â() + p_175926_3_ + 1 <= 256) {
            for (int var5 = 0; var5 <= 1 + p_175926_3_; ++var5) {
                byte var6 = 2;
                if (var5 == 0) {
                    var6 = 1;
                }
                else if (var5 >= 1 + p_175926_3_ - 2) {
                    var6 = 2;
                }
                for (int var7 = -var6; var7 <= var6 && var4; ++var7) {
                    for (int var8 = -var6; var8 <= var6 && var4; ++var8) {
                        if (p_175926_2_.Â() + var5 < 0 || p_175926_2_.Â() + var5 >= 256 || !this.HorizonCode_Horizon_È(worldIn.Â(p_175926_2_.Â(var7, var5, var8)).Ý())) {
                            var4 = false;
                        }
                    }
                }
            }
            return var4;
        }
        return false;
    }
    
    private boolean HorizonCode_Horizon_È(final BlockPos p_175927_1_, final World worldIn) {
        final BlockPos var3 = p_175927_1_.Âµá€();
        final Block var4 = worldIn.Â(var3).Ý();
        if ((var4 == Blocks.Ø­áŒŠá || var4 == Blocks.Âµá€) && p_175927_1_.Â() >= 2) {
            this.HorizonCode_Horizon_È(worldIn, var3);
            this.HorizonCode_Horizon_È(worldIn, var3.áŒŠÆ());
            this.HorizonCode_Horizon_È(worldIn, var3.à());
            this.HorizonCode_Horizon_È(worldIn, var3.à().áŒŠÆ());
            return true;
        }
        return false;
    }
    
    protected boolean HorizonCode_Horizon_È(final World worldIn, final Random p_175929_2_, final BlockPos p_175929_3_, final int p_175929_4_) {
        return this.Ý(worldIn, p_175929_3_, p_175929_4_) && this.HorizonCode_Horizon_È(p_175929_3_, worldIn);
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_175925_2_, final int p_175925_3_) {
        final int var4 = p_175925_3_ * p_175925_3_;
        for (int var5 = -p_175925_3_; var5 <= p_175925_3_ + 1; ++var5) {
            for (int var6 = -p_175925_3_; var6 <= p_175925_3_ + 1; ++var6) {
                final int var7 = var5 - 1;
                final int var8 = var6 - 1;
                if (var5 * var5 + var6 * var6 <= var4 || var7 * var7 + var8 * var8 <= var4 || var5 * var5 + var8 * var8 <= var4 || var7 * var7 + var6 * var6 <= var4) {
                    final BlockPos var9 = p_175925_2_.Â(var5, 0, var6);
                    final Material var10 = worldIn.Â(var9).Ý().Ó();
                    if (var10 == Material.HorizonCode_Horizon_È || var10 == Material.áˆºÑ¢Õ) {
                        this.HorizonCode_Horizon_È(worldIn, var9, Blocks.µÕ, this.Ý);
                    }
                }
            }
        }
    }
    
    protected void Â(final World worldIn, final BlockPos p_175928_2_, final int p_175928_3_) {
        final int var4 = p_175928_3_ * p_175928_3_;
        for (int var5 = -p_175928_3_; var5 <= p_175928_3_; ++var5) {
            for (int var6 = -p_175928_3_; var6 <= p_175928_3_; ++var6) {
                if (var5 * var5 + var6 * var6 <= var4) {
                    final BlockPos var7 = p_175928_2_.Â(var5, 0, var6);
                    final Material var8 = worldIn.Â(var7).Ý().Ó();
                    if (var8 == Material.HorizonCode_Horizon_È || var8 == Material.áˆºÑ¢Õ) {
                        this.HorizonCode_Horizon_È(worldIn, var7, Blocks.µÕ, this.Ý);
                    }
                }
            }
        }
    }
}
