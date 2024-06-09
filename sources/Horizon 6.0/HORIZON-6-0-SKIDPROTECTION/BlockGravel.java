package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockGravel extends BlockFalling
{
    private static final String Õ = "CL_00000252";
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, int fortune) {
        if (fortune > 3) {
            fortune = 3;
        }
        return (rand.nextInt(10 - fortune * 3) == 0) ? Items.Ï­Ï­Ï : Item_1028566121.HorizonCode_Horizon_È(this);
    }
}
