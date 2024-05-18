package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Sets;
import java.util.Set;

public class ItemSpade extends ItemTool
{
    private static final Set áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000063";
    
    static {
        áŒŠÆ = Sets.newHashSet((Object[])new Block[] { Blocks.£É, Blocks.Âµá€, Blocks.£Â, Blocks.Ø­áŒŠá, Blocks.Å, Blocks.Œáƒ, Blocks.£á, Blocks.ˆà¢, Blocks.áŒŠá€, Blocks.ŠÕ });
    }
    
    public ItemSpade(final HorizonCode_Horizon_È p_i45353_1_) {
        super(1.0f, p_i45353_1_, ItemSpade.áŒŠÆ);
    }
    
    @Override
    public boolean Â(final Block blockIn) {
        return blockIn == Blocks.áŒŠá€ || blockIn == Blocks.ˆà¢;
    }
}
