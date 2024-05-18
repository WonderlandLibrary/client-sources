package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.List;

public class ShapelessRecipes implements IRecipe
{
    private final ItemStack HorizonCode_Horizon_È;
    private final List Â;
    private static final String Ý = "CL_00000094";
    
    public ShapelessRecipes(final ItemStack p_i1918_1_, final List p_i1918_2_) {
        this.HorizonCode_Horizon_È = p_i1918_1_;
        this.Â = p_i1918_2_;
    }
    
    @Override
    public ItemStack Â() {
        return this.HorizonCode_Horizon_È;
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
    
    @Override
    public boolean HorizonCode_Horizon_È(final InventoryCrafting p_77569_1_, final World worldIn) {
        final ArrayList var3 = Lists.newArrayList((Iterable)this.Â);
        for (int var4 = 0; var4 < p_77569_1_.Ø­áŒŠá(); ++var4) {
            for (int var5 = 0; var5 < p_77569_1_.Ó(); ++var5) {
                final ItemStack var6 = p_77569_1_.Ý(var5, var4);
                if (var6 != null) {
                    boolean var7 = false;
                    for (final ItemStack var9 : var3) {
                        if (var6.HorizonCode_Horizon_È() == var9.HorizonCode_Horizon_È() && (var9.Ø() == 32767 || var6.Ø() == var9.Ø())) {
                            var7 = true;
                            var3.remove(var9);
                            break;
                        }
                    }
                    if (!var7) {
                        return false;
                    }
                }
            }
        }
        return var3.isEmpty();
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final InventoryCrafting p_77572_1_) {
        return this.HorizonCode_Horizon_È.áˆºÑ¢Õ();
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.Â.size();
    }
}
