package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.ArrayList;

public class BetterGrass
{
    private static IBakedModel HorizonCode_Horizon_È;
    private static IBakedModel Â;
    private static IBakedModel Ý;
    private static IBakedModel Ø­áŒŠá;
    
    static {
        BetterGrass.HorizonCode_Horizon_È = new SimpleBakedModel(new ArrayList(), new ArrayList(), false, false, null, null);
        BetterGrass.Â = null;
        BetterGrass.Ý = null;
        BetterGrass.Ø­áŒŠá = null;
    }
    
    public static void HorizonCode_Horizon_È() {
        BetterGrass.Ø­áŒŠá = BlockModelUtils.HorizonCode_Horizon_È("minecraft:blocks/grass_top", 0);
        BetterGrass.Ý = BlockModelUtils.HorizonCode_Horizon_È("minecraft:blocks/snow", -1);
        BetterGrass.Â = BlockModelUtils.HorizonCode_Horizon_È("minecraft:blocks/mycelium_top", -1);
    }
    
    public static List HorizonCode_Horizon_È(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final EnumFacing facing, final List quads) {
        if (facing == EnumFacing.Â || facing == EnumFacing.HorizonCode_Horizon_È) {
            return quads;
        }
        if (block instanceof BlockMycelium) {
            return Config.áˆºáˆºÈ() ? ((HorizonCode_Horizon_È(blockPos.Âµá€(), facing, blockAccess) == Blocks.Œáƒ) ? BetterGrass.Â.HorizonCode_Horizon_È(facing) : quads) : BetterGrass.Â.HorizonCode_Horizon_È(facing);
        }
        if (block instanceof BlockGrass) {
            final Block blockUp = blockAccess.Â(blockPos.Ø­áŒŠá()).Ý();
            final boolean snowy = blockUp == Blocks.ˆà¢ || blockUp == Blocks.áŒŠá€;
            if (!Config.áˆºáˆºÈ()) {
                if (snowy) {
                    return BetterGrass.Ý.HorizonCode_Horizon_È(facing);
                }
                return BetterGrass.Ø­áŒŠá.HorizonCode_Horizon_È(facing);
            }
            else if (snowy) {
                if (HorizonCode_Horizon_È(blockPos, facing, blockAccess) == Blocks.áŒŠá€) {
                    return BetterGrass.Ý.HorizonCode_Horizon_È(facing);
                }
            }
            else if (HorizonCode_Horizon_È(blockPos.Âµá€(), facing, blockAccess) == Blocks.Ø­áŒŠá) {
                return BetterGrass.Ø­áŒŠá.HorizonCode_Horizon_È(facing);
            }
        }
        return quads;
    }
    
    private static Block HorizonCode_Horizon_È(final BlockPos blockPos, final EnumFacing facing, final IBlockAccess blockAccess) {
        final BlockPos pos = blockPos.HorizonCode_Horizon_È(facing);
        final Block block = blockAccess.Â(pos).Ý();
        return block;
    }
}
