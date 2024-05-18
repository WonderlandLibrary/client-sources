package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Sets;
import java.util.Set;

public class ItemPickaxe extends ItemTool
{
    private static final Set áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000053";
    
    static {
        áŒŠÆ = Sets.newHashSet((Object[])new Block[] { Blocks.Ø­à¢, Blocks.ˆà, Blocks.Ó, Blocks.ˆá, Blocks.Ø­á, Blocks.£Ï, Blocks.£ÂµÄ, Blocks.ÇŽÉ, Blocks.ˆáŠ, Blocks.£à, Blocks.¥Ï, Blocks.áŒŠ, Blocks.µà, Blocks.ŠÄ, Blocks.áŒŠà, Blocks.ÇªØ­, Blocks.áˆºáˆºÈ, Blocks.áŒŠÔ, Blocks.ŠÂµÏ, Blocks.áŒŠáŠ, Blocks.Ñ¢à, Blocks.ŒÏ, Blocks.áˆºÛ, Blocks.Ý, Blocks.Ø­Âµ });
    }
    
    protected ItemPickaxe(final HorizonCode_Horizon_È p_i45347_1_) {
        super(2.0f, p_i45347_1_, ItemPickaxe.áŒŠÆ);
    }
    
    @Override
    public boolean Â(final Block blockIn) {
        return (blockIn == Blocks.ÇŽá€) ? (this.Ø.Ø­áŒŠá() == 3) : ((blockIn != Blocks.Ø­á && blockIn != Blocks.£Ï) ? ((blockIn != Blocks.µÐƒÓ && blockIn != Blocks.ˆØ­áˆº) ? ((blockIn != Blocks.ˆáŠ && blockIn != Blocks.£à) ? ((blockIn != Blocks.áŒŠ && blockIn != Blocks.µà) ? ((blockIn != Blocks.ŠÄ && blockIn != Blocks.áŒŠà) ? ((blockIn != Blocks.Ñ¢à && blockIn != Blocks.ÇªØ­) ? (blockIn.Ó() == Material.Âµá€ || blockIn.Ó() == Material.Ó || blockIn.Ó() == Material.à) : (this.Ø.Ø­áŒŠá() >= 2)) : (this.Ø.Ø­áŒŠá() >= 1)) : (this.Ø.Ø­áŒŠá() >= 1)) : (this.Ø.Ø­áŒŠá() >= 2)) : (this.Ø.Ø­áŒŠá() >= 2)) : (this.Ø.Ø­áŒŠá() >= 2));
    }
    
    @Override
    public float HorizonCode_Horizon_È(final ItemStack stack, final Block p_150893_2_) {
        return (p_150893_2_.Ó() != Material.Ó && p_150893_2_.Ó() != Material.à && p_150893_2_.Ó() != Material.Âµá€) ? super.HorizonCode_Horizon_È(stack, p_150893_2_) : this.à;
    }
}
