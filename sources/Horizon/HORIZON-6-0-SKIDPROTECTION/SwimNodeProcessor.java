package HORIZON-6-0-SKIDPROTECTION;

public class SwimNodeProcessor extends NodeProcessor
{
    private static final String Ó = "CL_00001966";
    
    @Override
    public void HorizonCode_Horizon_È(final IBlockAccess p_176162_1_, final Entity p_176162_2_) {
        super.HorizonCode_Horizon_È(p_176162_1_, p_176162_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
    }
    
    @Override
    public PathPoint HorizonCode_Horizon_È(final Entity p_176161_1_) {
        return this.HorizonCode_Horizon_È(MathHelper.Ý(p_176161_1_.£É().HorizonCode_Horizon_È), MathHelper.Ý(p_176161_1_.£É().Â + 0.5), MathHelper.Ý(p_176161_1_.£É().Ý));
    }
    
    @Override
    public PathPoint HorizonCode_Horizon_È(final Entity p_176160_1_, final double p_176160_2_, final double p_176160_4_, final double p_176160_6_) {
        return this.HorizonCode_Horizon_È(MathHelper.Ý(p_176160_2_ - p_176160_1_.áŒŠ / 2.0f), MathHelper.Ý(p_176160_4_ + 0.5), MathHelper.Ý(p_176160_6_ - p_176160_1_.áŒŠ / 2.0f));
    }
    
    @Override
    public int HorizonCode_Horizon_È(final PathPoint[] p_176164_1_, final Entity p_176164_2_, final PathPoint p_176164_3_, final PathPoint p_176164_4_, final float p_176164_5_) {
        int var6 = 0;
        for (final EnumFacing var10 : EnumFacing.values()) {
            final PathPoint var11 = this.HorizonCode_Horizon_È(p_176164_2_, p_176164_3_.HorizonCode_Horizon_È + var10.Ø(), p_176164_3_.Â + var10.áŒŠÆ(), p_176164_3_.Ý + var10.áˆºÑ¢Õ());
            if (var11 != null && !var11.áŒŠÆ && var11.HorizonCode_Horizon_È(p_176164_4_) < p_176164_5_) {
                p_176164_1_[var6++] = var11;
            }
        }
        return var6;
    }
    
    private PathPoint HorizonCode_Horizon_È(final Entity p_176185_1_, final int p_176185_2_, final int p_176185_3_, final int p_176185_4_) {
        final int var5 = this.Â(p_176185_1_, p_176185_2_, p_176185_3_, p_176185_4_);
        return (var5 == -1) ? this.HorizonCode_Horizon_È(p_176185_2_, p_176185_3_, p_176185_4_) : null;
    }
    
    private int Â(final Entity p_176186_1_, final int p_176186_2_, final int p_176186_3_, final int p_176186_4_) {
        for (int var5 = p_176186_2_; var5 < p_176186_2_ + this.Ý; ++var5) {
            for (int var6 = p_176186_3_; var6 < p_176186_3_ + this.Ø­áŒŠá; ++var6) {
                for (int var7 = p_176186_4_; var7 < p_176186_4_ + this.Âµá€; ++var7) {
                    final BlockPos var8 = new BlockPos(var5, var6, var7);
                    final Block var9 = this.HorizonCode_Horizon_È.Â(var8).Ý();
                    if (var9.Ó() != Material.Ø) {
                        return 0;
                    }
                }
            }
        }
        return -1;
    }
}
