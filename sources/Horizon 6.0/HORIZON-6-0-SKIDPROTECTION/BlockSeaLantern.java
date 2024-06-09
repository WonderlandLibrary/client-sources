package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockSeaLantern extends Block
{
    private static final String Õ = "CL_00002066";
    
    public BlockSeaLantern(final Material p_i45685_1_) {
        super(p_i45685_1_);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 2 + random.nextInt(2);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int fortune, final Random random) {
        return MathHelper.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(random) + random.nextInt(fortune + 1), 1, 5);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.Ðƒ;
    }
    
    @Override
    public MapColor Â(final IBlockState state) {
        return MapColor.£à;
    }
    
    @Override
    protected boolean Ñ¢á() {
        return true;
    }
}
