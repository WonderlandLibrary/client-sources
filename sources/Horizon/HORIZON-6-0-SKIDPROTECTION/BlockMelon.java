package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockMelon extends Block
{
    private static final String Õ = "CL_00000267";
    
    protected BlockMelon() {
        super(Material.Çªà¢);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.ÐƒÂ;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 3 + random.nextInt(5);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int fortune, final Random random) {
        return Math.min(9, this.HorizonCode_Horizon_È(random) + random.nextInt(1 + fortune));
    }
}
