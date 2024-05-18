package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class ItemLead extends Item_1028566121
{
    private static final String à = "CL_00000045";
    
    public ItemLead() {
        this.HorizonCode_Horizon_È(CreativeTabs.áŒŠÆ);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final Block var9 = worldIn.Â(pos).Ý();
        if (!(var9 instanceof BlockFence)) {
            return false;
        }
        if (worldIn.ŠÄ) {
            return true;
        }
        HorizonCode_Horizon_È(playerIn, worldIn, pos);
        return true;
    }
    
    public static boolean HorizonCode_Horizon_È(final EntityPlayer p_180618_0_, final World worldIn, final BlockPos p_180618_2_) {
        EntityLeashKnot var3 = EntityLeashKnot.Â(worldIn, p_180618_2_);
        boolean var4 = false;
        final double var5 = 7.0;
        final int var6 = p_180618_2_.HorizonCode_Horizon_È();
        final int var7 = p_180618_2_.Â();
        final int var8 = p_180618_2_.Ý();
        final List var9 = worldIn.HorizonCode_Horizon_È(EntityLiving.class, new AxisAlignedBB(var6 - var5, var7 - var5, var8 - var5, var6 + var5, var7 + var5, var8 + var5));
        for (final EntityLiving var11 : var9) {
            if (var11.ÇŽà() && var11.ŠáˆºÂ() == p_180618_0_) {
                if (var3 == null) {
                    var3 = EntityLeashKnot.HorizonCode_Horizon_È(worldIn, p_180618_2_);
                }
                var11.HorizonCode_Horizon_È(var3, true);
                var4 = true;
            }
        }
        return var4;
    }
}
