package HORIZON-6-0-SKIDPROTECTION;

public class RecipesMapExtending extends ShapedRecipes
{
    private static final String HorizonCode_Horizon_È = "CL_00000088";
    
    public RecipesMapExtending() {
        super(3, 3, new ItemStack[] { new ItemStack(Items.ˆà¢), new ItemStack(Items.ˆà¢), new ItemStack(Items.ˆà¢), new ItemStack(Items.ˆà¢), new ItemStack(Items.ˆØ, 0, 32767), new ItemStack(Items.ˆà¢), new ItemStack(Items.ˆà¢), new ItemStack(Items.ˆà¢), new ItemStack(Items.ˆà¢) }, new ItemStack(Items.£Ô, 0, 0));
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final InventoryCrafting p_77569_1_, final World worldIn) {
        if (!super.HorizonCode_Horizon_È(p_77569_1_, worldIn)) {
            return false;
        }
        ItemStack var3 = null;
        for (int var4 = 0; var4 < p_77569_1_.áŒŠÆ() && var3 == null; ++var4) {
            final ItemStack var5 = p_77569_1_.á(var4);
            if (var5 != null && var5.HorizonCode_Horizon_È() == Items.ˆØ) {
                var3 = var5;
            }
        }
        if (var3 == null) {
            return false;
        }
        final MapData var6 = Items.ˆØ.HorizonCode_Horizon_È(var3, worldIn);
        return var6 != null && var6.Âµá€ < 4;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final InventoryCrafting p_77572_1_) {
        ItemStack var2 = null;
        for (int var3 = 0; var3 < p_77572_1_.áŒŠÆ() && var2 == null; ++var3) {
            final ItemStack var4 = p_77572_1_.á(var3);
            if (var4 != null && var4.HorizonCode_Horizon_È() == Items.ˆØ) {
                var2 = var4;
            }
        }
        var2 = var2.áˆºÑ¢Õ();
        var2.Â = 1;
        if (var2.Å() == null) {
            var2.Ø­áŒŠá(new NBTTagCompound());
        }
        var2.Å().HorizonCode_Horizon_È("map_is_scaling", true);
        return var2;
    }
}
