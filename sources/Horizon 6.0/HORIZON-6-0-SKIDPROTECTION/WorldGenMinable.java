package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.base.Predicate;

public class WorldGenMinable extends WorldGenerator
{
    private final IBlockState HorizonCode_Horizon_È;
    private final int Â;
    private final Predicate Ý;
    private static final String Ø­áŒŠá = "CL_00000426";
    
    public WorldGenMinable(final IBlockState p_i45630_1_, final int p_i45630_2_) {
        this(p_i45630_1_, p_i45630_2_, (Predicate)BlockHelper.HorizonCode_Horizon_È(Blocks.Ý));
    }
    
    public WorldGenMinable(final IBlockState p_i45631_1_, final int p_i45631_2_, final Predicate p_i45631_3_) {
        this.HorizonCode_Horizon_È = p_i45631_1_;
        this.Â = p_i45631_2_;
        this.Ý = p_i45631_3_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        final float var4 = p_180709_2_.nextFloat() * 3.1415927f;
        final double var5 = p_180709_3_.HorizonCode_Horizon_È() + 8 + MathHelper.HorizonCode_Horizon_È(var4) * this.Â / 8.0f;
        final double var6 = p_180709_3_.HorizonCode_Horizon_È() + 8 - MathHelper.HorizonCode_Horizon_È(var4) * this.Â / 8.0f;
        final double var7 = p_180709_3_.Ý() + 8 + MathHelper.Â(var4) * this.Â / 8.0f;
        final double var8 = p_180709_3_.Ý() + 8 - MathHelper.Â(var4) * this.Â / 8.0f;
        final double var9 = p_180709_3_.Â() + p_180709_2_.nextInt(3) - 2;
        final double var10 = p_180709_3_.Â() + p_180709_2_.nextInt(3) - 2;
        for (int var11 = 0; var11 < this.Â; ++var11) {
            final float var12 = var11 / this.Â;
            final double var13 = var5 + (var6 - var5) * var12;
            final double var14 = var9 + (var10 - var9) * var12;
            final double var15 = var7 + (var8 - var7) * var12;
            final double var16 = p_180709_2_.nextDouble() * this.Â / 16.0;
            final double var17 = (MathHelper.HorizonCode_Horizon_È(3.1415927f * var12) + 1.0f) * var16 + 1.0;
            final double var18 = (MathHelper.HorizonCode_Horizon_È(3.1415927f * var12) + 1.0f) * var16 + 1.0;
            final int var19 = MathHelper.Ý(var13 - var17 / 2.0);
            final int var20 = MathHelper.Ý(var14 - var18 / 2.0);
            final int var21 = MathHelper.Ý(var15 - var17 / 2.0);
            final int var22 = MathHelper.Ý(var13 + var17 / 2.0);
            final int var23 = MathHelper.Ý(var14 + var18 / 2.0);
            final int var24 = MathHelper.Ý(var15 + var17 / 2.0);
            for (int var25 = var19; var25 <= var22; ++var25) {
                final double var26 = (var25 + 0.5 - var13) / (var17 / 2.0);
                if (var26 * var26 < 1.0) {
                    for (int var27 = var20; var27 <= var23; ++var27) {
                        final double var28 = (var27 + 0.5 - var14) / (var18 / 2.0);
                        if (var26 * var26 + var28 * var28 < 1.0) {
                            for (int var29 = var21; var29 <= var24; ++var29) {
                                final double var30 = (var29 + 0.5 - var15) / (var17 / 2.0);
                                if (var26 * var26 + var28 * var28 + var30 * var30 < 1.0) {
                                    final BlockPos var31 = new BlockPos(var25, var27, var29);
                                    if (this.Ý.apply((Object)worldIn.Â(var31))) {
                                        worldIn.HorizonCode_Horizon_È(var31, this.HorizonCode_Horizon_È, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
