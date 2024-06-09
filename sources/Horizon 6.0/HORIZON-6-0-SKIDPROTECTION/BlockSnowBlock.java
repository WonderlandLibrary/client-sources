package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockSnowBlock extends Block
{
    private static final String Õ = "CL_00000308";
    
    protected BlockSnowBlock() {
        super(Material.ŠÄ);
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.Ñ¢à;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 4;
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (worldIn.Â(EnumSkyBlock.Â, pos) > 11) {
            this.HorizonCode_Horizon_È(worldIn, pos, worldIn.Â(pos), 0);
            worldIn.Ø(pos);
        }
    }
}
