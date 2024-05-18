package HORIZON-6-0-SKIDPROTECTION;

public class BetterSnow
{
    private static IBakedModel HorizonCode_Horizon_È;
    
    static {
        BetterSnow.HorizonCode_Horizon_È = null;
    }
    
    public static void HorizonCode_Horizon_È() {
        BetterSnow.HorizonCode_Horizon_È = Config.È().Ô().HorizonCode_Horizon_È().Â(Blocks.áŒŠá€.¥à());
    }
    
    public static IBakedModel Â() {
        return BetterSnow.HorizonCode_Horizon_È;
    }
    
    public static IBlockState Ý() {
        return Blocks.áŒŠá€.¥à();
    }
    
    public static boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final Block block, final IBlockState blockState, final BlockPos blockPos) {
        return HorizonCode_Horizon_È(block, blockState) && HorizonCode_Horizon_È(blockAccess, blockPos);
    }
    
    private static boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos pos) {
        final Block blockSnow = Blocks.áŒŠá€;
        return (blockAccess.Â(pos.Ó()).Ý() == blockSnow || blockAccess.Â(pos.à()).Ý() == blockSnow || blockAccess.Â(pos.Ø()).Ý() == blockSnow || blockAccess.Â(pos.áŒŠÆ()).Ý() == blockSnow) && blockAccess.Â(pos.Âµá€()).Ý().Å();
    }
    
    private static boolean HorizonCode_Horizon_È(final Block block, final IBlockState blockState) {
        if (block.áˆºÑ¢Õ()) {
            return false;
        }
        if (block.Å()) {
            return false;
        }
        if (block instanceof BlockSnow) {
            return false;
        }
        if (block instanceof BlockBush && (block instanceof BlockDoublePlant || block instanceof BlockFlower || block instanceof BlockMushroom || block instanceof BlockSapling || block instanceof BlockTallGrass)) {
            return true;
        }
        if (block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockFlowerPot || block instanceof BlockPane || block instanceof BlockReed || block instanceof BlockWall) {
            return true;
        }
        if (block instanceof BlockRedstoneTorch && blockState.HorizonCode_Horizon_È(BlockTorch.Õ) == EnumFacing.Â) {
            return true;
        }
        if (block instanceof BlockLever) {
            final Comparable orient = blockState.HorizonCode_Horizon_È(BlockLever.Õ);
            if (orient == BlockLever.HorizonCode_Horizon_È.à || orient == BlockLever.HorizonCode_Horizon_È.Ó) {
                return true;
            }
        }
        return false;
    }
}
