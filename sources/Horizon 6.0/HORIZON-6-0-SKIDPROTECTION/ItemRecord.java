package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.collect.Maps;
import java.util.Map;

public class ItemRecord extends Item_1028566121
{
    private static final Map Ø;
    public final String à;
    private static final String áŒŠÆ = "CL_00000057";
    
    static {
        Ø = Maps.newHashMap();
    }
    
    protected ItemRecord(final String p_i45350_1_) {
        this.à = p_i45350_1_;
        this.Ø­áŒŠá = 1;
        this.HorizonCode_Horizon_È(CreativeTabs.Ó);
        ItemRecord.Ø.put("records." + p_i45350_1_, this);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final IBlockState var9 = worldIn.Â(pos);
        if (var9.Ý() != Blocks.Ðƒà || (boolean)var9.HorizonCode_Horizon_È(BlockJukebox.Õ)) {
            return false;
        }
        if (worldIn.ŠÄ) {
            return true;
        }
        ((BlockJukebox)Blocks.Ðƒà).HorizonCode_Horizon_È(worldIn, pos, var9, stack);
        worldIn.HorizonCode_Horizon_È(null, 1005, pos, Item_1028566121.HorizonCode_Horizon_È(this));
        --stack.Â;
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        tooltip.add(this.ˆà());
    }
    
    public String ˆà() {
        return StatCollector.HorizonCode_Horizon_È("item.record." + this.à + ".desc");
    }
    
    @Override
    public EnumRarity áŒŠÆ(final ItemStack stack) {
        return EnumRarity.Ý;
    }
    
    public static ItemRecord Ø­áŒŠá(final String p_150926_0_) {
        return ItemRecord.Ø.get(p_150926_0_);
    }
}
