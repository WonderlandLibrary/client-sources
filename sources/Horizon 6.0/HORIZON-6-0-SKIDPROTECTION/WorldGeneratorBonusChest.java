package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.List;

public class WorldGeneratorBonusChest extends WorldGenerator
{
    private final List HorizonCode_Horizon_È;
    private final int Â;
    private static final String Ý = "CL_00000403";
    
    public WorldGeneratorBonusChest(final List p_i45634_1_, final int p_i45634_2_) {
        this.HorizonCode_Horizon_È = p_i45634_1_;
        this.Â = p_i45634_2_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, BlockPos p_180709_3_) {
        Block var4;
        while (((var4 = worldIn.Â(p_180709_3_).Ý()).Ó() == Material.HorizonCode_Horizon_È || var4.Ó() == Material.áˆºÑ¢Õ) && p_180709_3_.Â() > 1) {
            p_180709_3_ = p_180709_3_.Âµá€();
        }
        if (p_180709_3_.Â() < 1) {
            return false;
        }
        p_180709_3_ = p_180709_3_.Ø­áŒŠá();
        for (int var5 = 0; var5 < 4; ++var5) {
            final BlockPos var6 = p_180709_3_.Â(p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4), p_180709_2_.nextInt(3) - p_180709_2_.nextInt(3), p_180709_2_.nextInt(4) - p_180709_2_.nextInt(4));
            if (worldIn.Ø­áŒŠá(var6) && World.HorizonCode_Horizon_È(worldIn, var6.Âµá€())) {
                worldIn.HorizonCode_Horizon_È(var6, Blocks.ˆáƒ.¥à(), 2);
                final TileEntity var7 = worldIn.HorizonCode_Horizon_È(var6);
                if (var7 instanceof TileEntityChest) {
                    WeightedRandomChestContent.HorizonCode_Horizon_È(p_180709_2_, this.HorizonCode_Horizon_È, (IInventory)var7, this.Â);
                }
                final BlockPos var8 = var6.áŒŠÆ();
                final BlockPos var9 = var6.Ø();
                final BlockPos var10 = var6.Ó();
                final BlockPos var11 = var6.à();
                if (worldIn.Ø­áŒŠá(var9) && World.HorizonCode_Horizon_È(worldIn, var9.Âµá€())) {
                    worldIn.HorizonCode_Horizon_È(var9, Blocks.Ï.¥à(), 2);
                }
                if (worldIn.Ø­áŒŠá(var8) && World.HorizonCode_Horizon_È(worldIn, var8.Âµá€())) {
                    worldIn.HorizonCode_Horizon_È(var8, Blocks.Ï.¥à(), 2);
                }
                if (worldIn.Ø­áŒŠá(var10) && World.HorizonCode_Horizon_È(worldIn, var10.Âµá€())) {
                    worldIn.HorizonCode_Horizon_È(var10, Blocks.Ï.¥à(), 2);
                }
                if (worldIn.Ø­áŒŠá(var11) && World.HorizonCode_Horizon_È(worldIn, var11.Âµá€())) {
                    worldIn.HorizonCode_Horizon_È(var11, Blocks.Ï.¥à(), 2);
                }
                return true;
            }
        }
        return false;
    }
}
