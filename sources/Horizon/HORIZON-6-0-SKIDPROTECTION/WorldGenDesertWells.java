package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;
import com.google.common.base.Predicates;

public class WorldGenDesertWells extends WorldGenerator
{
    private static final BlockStateHelper HorizonCode_Horizon_È;
    private final IBlockState Â;
    private final IBlockState Ý;
    private final IBlockState Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000407";
    
    static {
        HorizonCode_Horizon_È = BlockStateHelper.HorizonCode_Horizon_È(Blocks.£á).HorizonCode_Horizon_È(BlockSand.Õ, Predicates.equalTo((Object)BlockSand.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
    }
    
    public WorldGenDesertWells() {
        this.Â = Blocks.Ø­Âµ.¥à().HorizonCode_Horizon_È(BlockStoneSlab.ŠÂµà, BlockStoneSlab.HorizonCode_Horizon_È.Â).HorizonCode_Horizon_È(BlockSlab.Õ, BlockSlab.HorizonCode_Horizon_È.Â);
        this.Ý = Blocks.ŒÏ.¥à();
        this.Ø­áŒŠá = Blocks.áˆºÑ¢Õ.¥à();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        while (worldIn.Ø­áŒŠá(p_180709_3_) && p_180709_3_.Â() > 2) {
            p_180709_3_ = p_180709_3_.Âµá€();
        }
        if (!WorldGenDesertWells.HorizonCode_Horizon_È.HorizonCode_Horizon_È(worldIn.Â(p_180709_3_))) {
            return false;
        }
        for (int var4 = -2; var4 <= 2; ++var4) {
            for (int var5 = -2; var5 <= 2; ++var5) {
                if (worldIn.Ø­áŒŠá(p_180709_3_.Â(var4, -1, var5)) && worldIn.Ø­áŒŠá(p_180709_3_.Â(var4, -2, var5))) {
                    return false;
                }
            }
        }
        for (int var4 = -1; var4 <= 0; ++var4) {
            for (int var5 = -2; var5 <= 2; ++var5) {
                for (int var6 = -2; var6 <= 2; ++var6) {
                    worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(var5, var4, var6), this.Ý, 2);
                }
            }
        }
        worldIn.HorizonCode_Horizon_È(p_180709_3_, this.Ø­áŒŠá, 2);
        for (final EnumFacing var8 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            worldIn.HorizonCode_Horizon_È(p_180709_3_.HorizonCode_Horizon_È(var8), this.Ø­áŒŠá, 2);
        }
        for (int var4 = -2; var4 <= 2; ++var4) {
            for (int var5 = -2; var5 <= 2; ++var5) {
                if (var4 == -2 || var4 == 2 || var5 == -2 || var5 == 2) {
                    worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(var4, 1, var5), this.Ý, 2);
                }
            }
        }
        worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(2, 1, 0), this.Â, 2);
        worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(-2, 1, 0), this.Â, 2);
        worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(0, 1, 2), this.Â, 2);
        worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(0, 1, -2), this.Â, 2);
        for (int var4 = -1; var4 <= 1; ++var4) {
            for (int var5 = -1; var5 <= 1; ++var5) {
                if (var4 == 0 && var5 == 0) {
                    worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(var4, 4, var5), this.Ý, 2);
                }
                else {
                    worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(var4, 4, var5), this.Â, 2);
                }
            }
        }
        for (int var4 = 1; var4 <= 3; ++var4) {
            worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(-1, var4, -1), this.Ý, 2);
            worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(-1, var4, 1), this.Ý, 2);
            worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(1, var4, -1), this.Ý, 2);
            worldIn.HorizonCode_Horizon_È(p_180709_3_.Â(1, var4, 1), this.Ý, 2);
        }
        return true;
    }
}
