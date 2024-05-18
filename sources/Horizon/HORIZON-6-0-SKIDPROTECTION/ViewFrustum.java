package HORIZON-6-0-SKIDPROTECTION;

public class ViewFrustum
{
    protected final RenderGlobal HorizonCode_Horizon_È;
    protected final World Â;
    protected int Ý;
    protected int Ø­áŒŠá;
    protected int Âµá€;
    public RenderChunk[] Ó;
    private static final String à = "CL_00002531";
    
    public ViewFrustum(final World worldIn, final int p_i46246_2_, final RenderGlobal p_i46246_3_, final IRenderChunkFactory p_i46246_4_) {
        this.HorizonCode_Horizon_È = p_i46246_3_;
        this.Â = worldIn;
        this.HorizonCode_Horizon_È(p_i46246_2_);
        this.HorizonCode_Horizon_È(p_i46246_4_);
    }
    
    protected void HorizonCode_Horizon_È(final IRenderChunkFactory p_178158_1_) {
        final int var2 = this.Ø­áŒŠá * this.Ý * this.Âµá€;
        this.Ó = new RenderChunk[var2];
        int var3 = 0;
        for (int var4 = 0; var4 < this.Ø­áŒŠá; ++var4) {
            for (int var5 = 0; var5 < this.Ý; ++var5) {
                for (int var6 = 0; var6 < this.Âµá€; ++var6) {
                    final int var7 = (var6 * this.Ý + var5) * this.Ø­áŒŠá + var4;
                    final BlockPos var8 = new BlockPos(var4 * 16, var5 * 16, var6 * 16);
                    this.Ó[var7] = p_178158_1_.HorizonCode_Horizon_È(this.Â, this.HorizonCode_Horizon_È, var8, var3++);
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È() {
        for (final RenderChunk var4 : this.Ó) {
            var4.HorizonCode_Horizon_È();
        }
    }
    
    protected void HorizonCode_Horizon_È(final int p_178159_1_) {
        final int var2 = p_178159_1_ * 2 + 1;
        this.Ø­áŒŠá = var2;
        this.Ý = 16;
        this.Âµá€ = var2;
    }
    
    public void HorizonCode_Horizon_È(final double p_178163_1_, final double p_178163_3_) {
        final int var5 = MathHelper.Ý(p_178163_1_) - 8;
        final int var6 = MathHelper.Ý(p_178163_3_) - 8;
        final int var7 = this.Ø­áŒŠá * 16;
        for (int var8 = 0; var8 < this.Ø­áŒŠá; ++var8) {
            final int var9 = this.HorizonCode_Horizon_È(var5, var7, var8);
            for (int var10 = 0; var10 < this.Âµá€; ++var10) {
                final int var11 = this.HorizonCode_Horizon_È(var6, var7, var10);
                for (int var12 = 0; var12 < this.Ý; ++var12) {
                    final int var13 = var12 * 16;
                    final RenderChunk var14 = this.Ó[(var10 * this.Ý + var12) * this.Ø­áŒŠá + var8];
                    final BlockPos var15 = new BlockPos(var9, var13, var11);
                    if (!var15.equals(var14.áŒŠÆ())) {
                        var14.HorizonCode_Horizon_È(var15);
                    }
                }
            }
        }
    }
    
    private int HorizonCode_Horizon_È(final int p_178157_1_, final int p_178157_2_, final int p_178157_3_) {
        final int var4 = p_178157_3_ * 16;
        int var5 = var4 - p_178157_1_ + p_178157_2_ / 2;
        if (var5 < 0) {
            var5 -= p_178157_2_ - 1;
        }
        return var4 - var5 / p_178157_2_ * p_178157_2_;
    }
    
    public void HorizonCode_Horizon_È(final int p_178162_1_, final int p_178162_2_, final int p_178162_3_, final int p_178162_4_, final int p_178162_5_, final int p_178162_6_) {
        final int var7 = MathHelper.HorizonCode_Horizon_È(p_178162_1_, 16);
        final int var8 = MathHelper.HorizonCode_Horizon_È(p_178162_2_, 16);
        final int var9 = MathHelper.HorizonCode_Horizon_È(p_178162_3_, 16);
        final int var10 = MathHelper.HorizonCode_Horizon_È(p_178162_4_, 16);
        final int var11 = MathHelper.HorizonCode_Horizon_È(p_178162_5_, 16);
        final int var12 = MathHelper.HorizonCode_Horizon_È(p_178162_6_, 16);
        for (int var13 = var7; var13 <= var10; ++var13) {
            int var14 = var13 % this.Ø­áŒŠá;
            if (var14 < 0) {
                var14 += this.Ø­áŒŠá;
            }
            for (int var15 = var8; var15 <= var11; ++var15) {
                int var16 = var15 % this.Ý;
                if (var16 < 0) {
                    var16 += this.Ý;
                }
                for (int var17 = var9; var17 <= var12; ++var17) {
                    int var18 = var17 % this.Âµá€;
                    if (var18 < 0) {
                        var18 += this.Âµá€;
                    }
                    final int var19 = (var18 * this.Ý + var16) * this.Ø­áŒŠá + var14;
                    final RenderChunk var20 = this.Ó[var19];
                    var20.HorizonCode_Horizon_È(true);
                }
            }
        }
    }
    
    protected RenderChunk HorizonCode_Horizon_È(final BlockPos p_178161_1_) {
        int var2 = MathHelper.HorizonCode_Horizon_È(p_178161_1_.HorizonCode_Horizon_È(), 16);
        final int var3 = MathHelper.HorizonCode_Horizon_È(p_178161_1_.Â(), 16);
        int var4 = MathHelper.HorizonCode_Horizon_È(p_178161_1_.Ý(), 16);
        if (var3 >= 0 && var3 < this.Ý) {
            var2 %= this.Ø­áŒŠá;
            if (var2 < 0) {
                var2 += this.Ø­áŒŠá;
            }
            var4 %= this.Âµá€;
            if (var4 < 0) {
                var4 += this.Âµá€;
            }
            final int var5 = (var4 * this.Ý + var3) * this.Ø­áŒŠá + var2;
            return this.Ó[var5];
        }
        return null;
    }
}
