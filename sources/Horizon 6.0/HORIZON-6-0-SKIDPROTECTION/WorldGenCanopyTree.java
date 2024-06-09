package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class WorldGenCanopyTree extends WorldGenAbstractTree
{
    private static final String HorizonCode_Horizon_È = "CL_00000430";
    
    public WorldGenCanopyTree(final boolean p_i45461_1_) {
        super(p_i45461_1_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        final int var4 = p_180709_2_.nextInt(3) + p_180709_2_.nextInt(2) + 6;
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
            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(1, -1, 0));
            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(1, -1, 1));
            this.HorizonCode_Horizon_È(worldIn, p_180709_3_.Â(0, -1, 1));
            final EnumFacing var11 = EnumFacing.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_180709_2_);
            final int var8 = var4 - p_180709_2_.nextInt(4);
            int var9 = 2 - p_180709_2_.nextInt(3);
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
                    this.HorizonCode_Horizon_È(worldIn, var17, Blocks.Ø­à, BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4);
                    this.HorizonCode_Horizon_È(worldIn, var17.áŒŠÆ(), Blocks.Ø­à, BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4);
                    this.HorizonCode_Horizon_È(worldIn, var17.à(), Blocks.Ø­à, BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4);
                    this.HorizonCode_Horizon_È(worldIn, var17.áŒŠÆ().à(), Blocks.Ø­à, BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4);
                    var14 = var16;
                }
            }
            for (int var15 = -2; var15 <= 0; ++var15) {
                for (int var16 = -2; var16 <= 0; ++var16) {
                    final byte var19 = -1;
                    this.HorizonCode_Horizon_È(worldIn, var12 + var15, var14 + var19, var13 + var16);
                    this.HorizonCode_Horizon_È(worldIn, 1 + var12 - var15, var14 + var19, var13 + var16);
                    this.HorizonCode_Horizon_È(worldIn, var12 + var15, var14 + var19, 1 + var13 - var16);
                    this.HorizonCode_Horizon_È(worldIn, 1 + var12 - var15, var14 + var19, 1 + var13 - var16);
                    if ((var15 > -2 || var16 > -1) && (var15 != -1 || var16 != -2)) {
                        final byte var20 = 1;
                        this.HorizonCode_Horizon_È(worldIn, var12 + var15, var14 + var20, var13 + var16);
                        this.HorizonCode_Horizon_È(worldIn, 1 + var12 - var15, var14 + var20, var13 + var16);
                        this.HorizonCode_Horizon_È(worldIn, var12 + var15, var14 + var20, 1 + var13 - var16);
                        this.HorizonCode_Horizon_È(worldIn, 1 + var12 - var15, var14 + var20, 1 + var13 - var16);
                    }
                }
            }
            if (p_180709_2_.nextBoolean()) {
                this.HorizonCode_Horizon_È(worldIn, var12, var14 + 2, var13);
                this.HorizonCode_Horizon_È(worldIn, var12 + 1, var14 + 2, var13);
                this.HorizonCode_Horizon_È(worldIn, var12 + 1, var14 + 2, var13 + 1);
                this.HorizonCode_Horizon_È(worldIn, var12, var14 + 2, var13 + 1);
            }
            for (int var15 = -3; var15 <= 4; ++var15) {
                for (int var16 = -3; var16 <= 4; ++var16) {
                    if ((var15 != -3 || var16 != -3) && (var15 != -3 || var16 != 4) && (var15 != 4 || var16 != -3) && (var15 != 4 || var16 != 4) && (Math.abs(var15) < 3 || Math.abs(var16) < 3)) {
                        this.HorizonCode_Horizon_È(worldIn, var12 + var15, var14, var13 + var16);
                    }
                }
            }
            for (int var15 = -1; var15 <= 2; ++var15) {
                for (int var16 = -1; var16 <= 2; ++var16) {
                    if ((var15 < 0 || var15 > 1 || var16 < 0 || var16 > 1) && p_180709_2_.nextInt(3) <= 0) {
                        for (int var21 = p_180709_2_.nextInt(3) + 2, var22 = 0; var22 < var21; ++var22) {
                            this.HorizonCode_Horizon_È(worldIn, new BlockPos(p_180709_3_.HorizonCode_Horizon_È() + var15, var14 - var22 - 1, p_180709_3_.Ý() + var16), Blocks.Ø­à, BlockPlanks.HorizonCode_Horizon_È.Ó.Â() - 4);
                        }
                        for (int var22 = -1; var22 <= 1; ++var22) {
                            for (int var23 = -1; var23 <= 1; ++var23) {
                                this.HorizonCode_Horizon_È(worldIn, var12 + var15 + var22, var14 - 0, var13 + var16 + var23);
                            }
                        }
                        for (int var22 = -2; var22 <= 2; ++var22) {
                            for (int var23 = -2; var23 <= 2; ++var23) {
                                if (Math.abs(var22) != 2 || Math.abs(var23) != 2) {
                                    this.HorizonCode_Horizon_È(worldIn, var12 + var15 + var22, var14 - 1, var13 + var16 + var23);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void HorizonCode_Horizon_È(final World worldIn, final int p_150526_2_, final int p_150526_3_, final int p_150526_4_) {
        final Block var5 = worldIn.Â(new BlockPos(p_150526_2_, p_150526_3_, p_150526_4_)).Ý();
        if (var5.Ó() == Material.HorizonCode_Horizon_È) {
            this.HorizonCode_Horizon_È(worldIn, new BlockPos(p_150526_2_, p_150526_3_, p_150526_4_), Blocks.Æ, 1);
        }
    }
}
