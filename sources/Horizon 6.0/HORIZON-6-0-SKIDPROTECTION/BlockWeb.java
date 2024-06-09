package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockWeb extends Block
{
    private static final String Õ = "CL_00000333";
    
    public BlockWeb() {
        super(Material.ÇŽÕ);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        entityIn.¥Ä();
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.ˆá;
    }
    
    @Override
    protected boolean Ñ¢á() {
        return true;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
}
