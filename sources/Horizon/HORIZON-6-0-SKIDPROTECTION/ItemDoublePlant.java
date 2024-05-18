package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Function;

public class ItemDoublePlant extends ItemMultiTexture
{
    private static final String áˆºÑ¢Õ = "CL_00000021";
    
    public ItemDoublePlant(final Block p_i45787_1_, final Block p_i45787_2_, final Function p_i45787_3_) {
        super(p_i45787_1_, p_i45787_2_, p_i45787_3_);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final ItemStack stack, final int renderPass) {
        final BlockDoublePlant.Â var3 = BlockDoublePlant.Â.HorizonCode_Horizon_È(stack.Ø());
        return (var3 != BlockDoublePlant.Â.Ý && var3 != BlockDoublePlant.Â.Ø­áŒŠá) ? super.HorizonCode_Horizon_È(stack, renderPass) : ColorizerGrass.HorizonCode_Horizon_È(0.5, 1.0);
    }
}
