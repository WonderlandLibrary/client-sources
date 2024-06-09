package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenSavannaTree extends WorldGenAbstractTree
{
    private static final String HorizonCode_Horizon_È = "CL_00000432";
    
    public WorldGenSavannaTree(final boolean p_i45463_1_) {
        super(p_i45463_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        final int var4 = p_180709_2_.nextInt(3) + p_180709_2_.nextInt(3) + 5;
        boolean var5 = true;
        if (p_180709_3_.Â() < 1 || p_180709_3_.Â() + var4 + 1 > 256) {
            return false;
        }
        for (int var6 = p_180709_3_.Â(); var6 <= p_180709_3_.Â() + 1 + var4; ++var6) {
            byte var7 = 1;
            if (var6 == p_180709_3_.Â()) {
                var7 = 0;
            }
            if (var6 >= p_180709_3_.Â() + 1 + var4 - 2) {
                var7 = 2;
            }
            for (int var8 = p_180709_3_.HorizonCode_Horizon_È() - var7; var8 <= p_180709_3_.HorizonCode_Horizon_È() + var7 && var5; ++var8) {
                for (int var9 = p_180709_3_.Ý() - var7; var9 <= p_180709_3_.Ý() + var7 && var5; ++var9) {
                    if (var6 >= 0 && var6 < 256) {
                        if (!this.HorizonCode_Horizon_È(worldIn.Â(new BlockPos(var8, var6, var9)).Ý())) {
                            var5 = false;
                        }
                    }
                    else {
                        var5 = false;
                    }
                }
            }
        }
        if (!var5) {
            return false;
        }
        final Block var10 = worldIn.Â(p_180709_3_.Âµá€()).Ý();
        if ((var10 == Blocks.Ø­áŒŠá || var10 == Blocks.Âµá€) && p_180709_3_.Â() < 256 - var4 - 1) {
            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Âµá€());
            final EnumFacing var11 = EnumFacing.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_180709_2_);
            final int var8 = var4 - p_180709_2_.nextInt(4) - 1;
            int var9 = 3 - p_180709_2_.nextInt(3);
            int var12 = p_180709_3_.HorizonCode_Horizon_È();
            int var13 = p_180709_3_.Ý();
            int var14 = 0;
            for (int var15 = 0; var15 < var4; ++var15) {
                final int var16 = p_180709_3_.Â() + var15;
                if (var15 >= var8 && var9 > 0) {
                    var12 += var11.Ø();
                    var13 += var11.áˆºÑ¢Õ();
                    --var9;
                }
                final BlockPos var17 = new BlockPos(var12, var16, var13);
                final Material var18 = worldIn.Â(var17).Ý().Ó();
                if (var18 == Material.HorizonCode_Horizon_È || var18 == Material.áˆºÑ¢Õ) {
                    this.HorizonCode_Horizon_È(worldIn, var17, Blocks.Ø­à, BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4);
                    var14 = var16;
                }
            }
            BlockPos var19 = new BlockPos(var12, var14, var13);
            for (int var16 = -3; var16 <= 3; ++var16) {
                for (int var20 = -3; var20 <= 3; ++var20) {
                    if (Math.abs(var16) != 3 || Math.abs(var20) != 3) {
                        this.Â(worldIn, var19.Â(var16, 0, var20));
                    }
                }
            }
            var19 = var19.Ø­áŒŠá();
            for (int var16 = -1; var16 <= 1; ++var16) {
                for (int var20 = -1; var20 <= 1; ++var20) {
                    this.Â(worldIn, var19.Â(var16, 0, var20));
                }
            }
            this.Â(worldIn, var19.à(2));
            this.Â(worldIn, var19.Ó(2));
            this.Â(worldIn, var19.Âµá€(2));
            this.Â(worldIn, var19.Ø­áŒŠá(2));
            var12 = p_180709_3_.HorizonCode_Horizon_È();
            var13 = p_180709_3_.Ý();
            final EnumFacing var21 = EnumFacing.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_180709_2_);
            if (var21 != var11) {
                final int var16 = var8 - p_180709_2_.nextInt(2) - 1;
                int var20 = 1 + p_180709_2_.nextInt(3);
                var14 = 0;
                for (int var22 = var16; var22 < var4 && var20 > 0; ++var22, --var20) {
                    if (var22 >= 1) {
                        final int var23 = p_180709_3_.Â() + var22;
                        var12 += var21.Ø();
                        var13 += var21.áˆºÑ¢Õ();
                        final BlockPos var24 = new BlockPos(var12, var23, var13);
                        final Material var25 = worldIn.Â(var24).Ý().Ó();
                        if (var25 == Material.HorizonCode_Horizon_È || var25 == Material.áˆºÑ¢Õ) {
                            this.HorizonCode_Horizon_È(worldIn, var24, Blocks.Ø­à, BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â() - 4);
                            var14 = var23;
                        }
                    }
                }
                if (var14 > 0) {
                    BlockPos var26 = new BlockPos(var12, var14, var13);
                    for (int var23 = -2; var23 <= 2; ++var23) {
                        for (int var27 = -2; var27 <= 2; ++var27) {
                            if (Math.abs(var23) != 2 || Math.abs(var27) != 2) {
                                this.Â(worldIn, var26.Â(var23, 0, var27));
                            }
                        }
                    }
                    var26 = var26.Ø­áŒŠá();
                    for (int var23 = -1; var23 <= 1; ++var23) {
                        for (int var27 = -1; var27 <= 1; ++var27) {
                            this.Â(worldIn, var26.Â(var23, 0, var27));
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void Â(final World worldIn, final BlockPos p_175924_2_) {
        final Material var3 = worldIn.Â(p_175924_2_).Ý().Ó();
        if (var3 == Material.HorizonCode_Horizon_È || var3 == Material.áˆºÑ¢Õ) {
            this.HorizonCode_Horizon_È(worldIn, p_175924_2_, Blocks.Æ, 0);
        }
    }
}
