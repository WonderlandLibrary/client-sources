package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockGlass extends BlockBreakable
{
    private static final String Õ = "CL_00000249";
    
    public BlockGlass(final Material p_i45408_1_, final boolean p_i45408_2_) {
        super(p_i45408_1_, p_i45408_2_);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    protected boolean Ñ¢á() {
        return true;
    }
}
