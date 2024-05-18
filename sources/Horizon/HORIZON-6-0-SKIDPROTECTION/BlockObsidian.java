package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockObsidian extends Block
{
    private static final String Õ = "CL_00000279";
    
    public BlockObsidian() {
        super(Material.Âµá€);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇŽá€);
    }
    
    @Override
    public MapColor Â(final IBlockState state) {
        return MapColor.á€;
    }
}
