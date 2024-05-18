package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.util.Collection;
import com.google.common.collect.Lists;
import java.util.List;

public class ItemFirework extends Item_1028566121
{
    private static final String à = "CL_00000031";
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!worldIn.ŠÄ) {
            final EntityFireworkRocket var9 = new EntityFireworkRocket(worldIn, pos.HorizonCode_Horizon_È() + hitX, pos.Â() + hitY, pos.Ý() + hitZ, stack);
            worldIn.HorizonCode_Horizon_È(var9);
            if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                --stack.Â;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        if (stack.£á()) {
            final NBTTagCompound var5 = stack.Å().ˆÏ­("Fireworks");
            if (var5 != null) {
                if (var5.Â("Flight", 99)) {
                    tooltip.add(String.valueOf(StatCollector.HorizonCode_Horizon_È("item.fireworks.flight")) + " " + var5.Ø­áŒŠá("Flight"));
                }
                final NBTTagList var6 = var5.Ý("Explosions", 10);
                if (var6 != null && var6.Âµá€() > 0) {
                    for (int var7 = 0; var7 < var6.Âµá€(); ++var7) {
                        final NBTTagCompound var8 = var6.Â(var7);
                        final ArrayList var9 = Lists.newArrayList();
                        ItemFireworkCharge.HorizonCode_Horizon_È(var8, var9);
                        if (var9.size() > 0) {
                            for (int var10 = 1; var10 < var9.size(); ++var10) {
                                var9.set(var10, "  " + var9.get(var10));
                            }
                            tooltip.addAll(var9);
                        }
                    }
                }
            }
        }
    }
}
