package HORIZON-6-0-SKIDPROTECTION;

public class RecipesWeapons
{
    private String[][] HorizonCode_Horizon_È;
    private Object[][] Â;
    private static final String Ý = "CL_00000097";
    
    public RecipesWeapons() {
        this.HorizonCode_Horizon_È = new String[][] { { "X", "X", "#" } };
        this.Â = new Object[][] { { Blocks.à, Blocks.Ó, Items.áˆºÑ¢Õ, Items.áŒŠÆ, Items.ÂµÈ }, { Items.ˆÏ­, Items.µà, Items.á, Items.µÕ, Items.ŒÏ } };
    }
    
    public void HorizonCode_Horizon_È(final CraftingManager p_77583_1_) {
        for (int var2 = 0; var2 < this.Â[0].length; ++var2) {
            final Object var3 = this.Â[0][var2];
            for (int var4 = 0; var4 < this.Â.length - 1; ++var4) {
                final Item_1028566121 var5 = (Item_1028566121)this.Â[var4 + 1][var2];
                p_77583_1_.HorizonCode_Horizon_È(new ItemStack(var5), this.HorizonCode_Horizon_È[var4], '#', Items.áŒŠà, 'X', var3);
            }
        }
        p_77583_1_.HorizonCode_Horizon_È(new ItemStack(Items.Ó, 1), " #X", "# X", " #X", 'X', Items.ˆá, '#', Items.áŒŠà);
        p_77583_1_.HorizonCode_Horizon_È(new ItemStack(Items.à, 4), "X", "#", "Y", 'Y', Items.ÇŽÕ, 'X', Items.Ï­Ï­Ï, '#', Items.áŒŠà);
    }
}
