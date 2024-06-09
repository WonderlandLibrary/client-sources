package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockClay extends Block
{
    private static final String Õ = "CL_00000215";
    
    public BlockClay() {
        super(Material.ŒÏ);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.áŒŠá€;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 4;
    }
}
