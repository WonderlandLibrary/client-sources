package HORIZON-6-0-SKIDPROTECTION;

public class ShapedRecipes implements IRecipe
{
    private final int HorizonCode_Horizon_È;
    private final int Â;
    private final ItemStack[] Ý;
    private final ItemStack Ø­áŒŠá;
    private boolean Âµá€;
    private static final String Ó = "CL_00000093";
    
    public ShapedRecipes(final int p_i1917_1_, final int p_i1917_2_, final ItemStack[] p_i1917_3_, final ItemStack p_i1917_4_) {
        this.HorizonCode_Horizon_È = p_i1917_1_;
        this.Â = p_i1917_2_;
        this.Ý = p_i1917_3_;
        this.Ø­áŒŠá = p_i1917_4_;
    }
    
    @Override
    public ItemStack Â() {
        return this.Ø­áŒŠá;
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
        for (int var3 = 0; var3 <= 3 - this.HorizonCode_Horizon_È; ++var3) {
            for (int var4 = 0; var4 <= 3 - this.Â; ++var4) {
                if (this.HorizonCode_Horizon_È(p_77569_1_, var3, var4, true)) {
                    return true;
                }
                if (this.HorizonCode_Horizon_È(p_77569_1_, var3, var4, false)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean HorizonCode_Horizon_È(final InventoryCrafting p_77573_1_, final int p_77573_2_, final int p_77573_3_, final boolean p_77573_4_) {
        for (int var5 = 0; var5 < 3; ++var5) {
            for (int var6 = 0; var6 < 3; ++var6) {
                final int var7 = var5 - p_77573_2_;
                final int var8 = var6 - p_77573_3_;
                ItemStack var9 = null;
                if (var7 >= 0 && var8 >= 0 && var7 < this.HorizonCode_Horizon_È && var8 < this.Â) {
                    if (p_77573_4_) {
                        var9 = this.Ý[this.HorizonCode_Horizon_È - var7 - 1 + var8 * this.HorizonCode_Horizon_È];
                    }
                    else {
                        var9 = this.Ý[var7 + var8 * this.HorizonCode_Horizon_È];
                    }
                }
                final ItemStack var10 = p_77573_1_.Ý(var5, var6);
                if (var10 != null || var9 != null) {
                    if ((var10 == null && var9 != null) || (var10 != null && var9 == null)) {
                        return false;
                    }
                    if (var9.HorizonCode_Horizon_È() != var10.HorizonCode_Horizon_È()) {
                        return false;
                    }
                    if (var9.Ø() != 32767 && var9.Ø() != var10.Ø()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final InventoryCrafting p_77572_1_) {
        final ItemStack var2 = this.Â().áˆºÑ¢Õ();
        if (this.Âµá€) {
            for (int var3 = 0; var3 < p_77572_1_.áŒŠÆ(); ++var3) {
                final ItemStack var4 = p_77572_1_.á(var3);
                if (var4 != null && var4.£á()) {
                    var2.Ø­áŒŠá((NBTTagCompound)var4.Å().Â());
                }
            }
        }
        return var2;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È * this.Â;
    }
    
    public ShapedRecipes Ý() {
        this.Âµá€ = true;
        return this;
    }
}
