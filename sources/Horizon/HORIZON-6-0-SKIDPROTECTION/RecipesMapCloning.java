package HORIZON-6-0-SKIDPROTECTION;

public class RecipesMapCloning implements IRecipe
{
    private static final String HorizonCode_Horizon_È = "CL_00000087";
    
    @Override
    public boolean HorizonCode_Horizon_È(final InventoryCrafting p_77569_1_, final World worldIn) {
        int var3 = 0;
        ItemStack var4 = null;
        for (int var5 = 0; var5 < p_77569_1_.áŒŠÆ(); ++var5) {
            final ItemStack var6 = p_77569_1_.á(var5);
            if (var6 != null) {
                if (var6.HorizonCode_Horizon_È() == Items.ˆØ) {
                    if (var4 != null) {
                        return false;
                    }
                    var4 = var6;
                }
                else {
                    if (var6.HorizonCode_Horizon_È() != Items.£Ô) {
                        return false;
                    }
                    ++var3;
                }
            }
        }
        return var4 != null && var3 > 0;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final InventoryCrafting p_77572_1_) {
        int var2 = 0;
        ItemStack var3 = null;
        for (int var4 = 0; var4 < p_77572_1_.áŒŠÆ(); ++var4) {
            final ItemStack var5 = p_77572_1_.á(var4);
            if (var5 != null) {
                if (var5.HorizonCode_Horizon_È() == Items.ˆØ) {
                    if (var3 != null) {
                        return null;
                    }
                    var3 = var5;
                }
                else {
                    if (var5.HorizonCode_Horizon_È() != Items.£Ô) {
                        return null;
                    }
                    ++var2;
                }
            }
        }
        if (var3 != null && var2 >= 1) {
            final ItemStack var6 = new ItemStack(Items.ˆØ, var2 + 1, var3.Ø());
            if (var3.¥Æ()) {
                var6.HorizonCode_Horizon_È(var3.µà());
            }
            return var6;
        }
        return null;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 9;
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
