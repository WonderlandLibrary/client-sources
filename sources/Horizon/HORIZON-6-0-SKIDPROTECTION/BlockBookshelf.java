package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockBookshelf extends Block
{
    private static final String Õ = "CL_00000206";
    
    public BlockBookshelf() {
        super(Material.Ø­áŒŠá);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 3;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.Ñ¢Ç;
    }
}
