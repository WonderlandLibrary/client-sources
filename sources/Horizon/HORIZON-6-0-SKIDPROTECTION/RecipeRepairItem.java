package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;

public class RecipeRepairItem implements IRecipe
{
    private static final String HorizonCode_Horizon_È = "CL_00002156";
    
    @Override
    public boolean HorizonCode_Horizon_È(final InventoryCrafting p_77569_1_, final World worldIn) {
        final ArrayList var3 = Lists.newArrayList();
        for (int var4 = 0; var4 < p_77569_1_.áŒŠÆ(); ++var4) {
            final ItemStack var5 = p_77569_1_.á(var4);
            if (var5 != null) {
                var3.add(var5);
                if (var3.size() > 1) {
                    final ItemStack var6 = var3.get(0);
                    if (var5.HorizonCode_Horizon_È() != var6.HorizonCode_Horizon_È() || var6.Â != 1 || var5.Â != 1 || !var6.HorizonCode_Horizon_È().Ø­áŒŠá()) {
                        return false;
                    }
                }
            }
        }
        return var3.size() == 2;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final InventoryCrafting p_77572_1_) {
        final ArrayList var2 = Lists.newArrayList();
        for (int var3 = 0; var3 < p_77572_1_.áŒŠÆ(); ++var3) {
            final ItemStack var4 = p_77572_1_.á(var3);
            if (var4 != null) {
                var2.add(var4);
                if (var2.size() > 1) {
                    final ItemStack var5 = var2.get(0);
                    if (var4.HorizonCode_Horizon_È() != var5.HorizonCode_Horizon_È() || var5.Â != 1 || var4.Â != 1 || !var5.HorizonCode_Horizon_È().Ø­áŒŠá()) {
                        return null;
                    }
                }
            }
        }
        if (var2.size() == 2) {
            final ItemStack var6 = var2.get(0);
            final ItemStack var4 = var2.get(1);
            if (var6.HorizonCode_Horizon_È() == var4.HorizonCode_Horizon_È() && var6.Â == 1 && var4.Â == 1 && var6.HorizonCode_Horizon_È().Ø­áŒŠá()) {
                final Item_1028566121 var7 = var6.HorizonCode_Horizon_È();
                final int var8 = var7.Ý() - var6.à();
                final int var9 = var7.Ý() - var4.à();
                final int var10 = var8 + var9 + var7.Ý() * 5 / 100;
                int var11 = var7.Ý() - var10;
                if (var11 < 0) {
                    var11 = 0;
                }
                return new ItemStack(var6.HorizonCode_Horizon_È(), 1, var11);
            }
        }
        return null;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 4;
    }
    
    @Override
    public ItemStack Â() {
        return null;
    }
    
    @Override
    public ItemStack[] Â(final InventoryCrafting p_179532_1_) {
        final ItemStack[] var2 = new ItemStack[p_179532_1_.áŒŠÆ()];
        for (int var3 = 0; var3 < var2.length; ++var3) {
            final ItemStack var4 = p_179532_1_.á(var3);
            if (var4 != null && var4.HorizonCode_Horizon_È().ÂµÈ()) {
                var2[var3] = new ItemStack(var4.HorizonCode_Horizon_È().áˆºÑ¢Õ());
            }
        }
        return var2;
    }
}
