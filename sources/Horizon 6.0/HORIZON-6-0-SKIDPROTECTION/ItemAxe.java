package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Sets;
import java.util.Set;

public class ItemAxe extends ItemTool
{
    private static final Set áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001770";
    
    static {
        áŒŠÆ = Sets.newHashSet((Object[])new Block[] { Blocks.à, Blocks.Ï­à, Blocks.¥Æ, Blocks.Ø­à, Blocks.ˆáƒ, Blocks.Ø­Æ, Blocks.áŒŠÕ, Blocks.ˆÅ, Blocks.áŒŠÏ });
    }
    
    protected ItemAxe(final HorizonCode_Horizon_È p_i45327_1_) {
        super(3.0f, p_i45327_1_, ItemAxe.áŒŠÆ);
    }
    
    @Override
    public float HorizonCode_Horizon_È(final ItemStack stack, final Block p_150893_2_) {
        return (p_150893_2_.Ó() != Material.Ø­áŒŠá && p_150893_2_.Ó() != Material.ÂµÈ && p_150893_2_.Ó() != Material.á) ? super.HorizonCode_Horizon_È(stack, p_150893_2_) : this.à;
    }
}
