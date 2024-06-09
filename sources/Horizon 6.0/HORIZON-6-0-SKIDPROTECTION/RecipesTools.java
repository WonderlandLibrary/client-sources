package HORIZON-6-0-SKIDPROTECTION;

public class RecipesTools
{
    private String[][] HorizonCode_Horizon_È;
    private Object[][] Â;
    private static final String Ý = "CL_00000096";
    
    public RecipesTools() {
        this.HorizonCode_Horizon_È = new String[][] { { "XXX", " # ", " # " }, { "X", "#", "#" }, { "XX", "X#", " #" }, { "XX", " #", " #" } };
        this.Â = new Object[][] { { Blocks.à, Blocks.Ó, Items.áˆºÑ¢Õ, Items.áŒŠÆ, Items.ÂµÈ }, { Items.Å, Items.¥Æ, Items.Â, Items.Šáƒ, Items.Ê }, { Items.£á, Items.ˆà, Items.HorizonCode_Horizon_È, Items.Æ, Items.Çªà¢ }, { Items.£à, Items.Ø­à, Items.Ý, Items.Ï­Ðƒà, Items.ÇŽÉ }, { Items.áƒ, Items.á€, Items.Õ, Items.à¢, Items.ŠÂµà } };
    }
    
    public void HorizonCode_Horizon_È(final CraftingManager p_77586_1_) {
        for (int var2 = 0; var2 < this.Â[0].length; ++var2) {
            final Object var3 = this.Â[0][var2];
            for (int var4 = 0; var4 < this.Â.length - 1; ++var4) {
                final Item_1028566121 var5 = (Item_1028566121)this.Â[var4 + 1][var2];
                p_77586_1_.HorizonCode_Horizon_È(new ItemStack(var5), this.HorizonCode_Horizon_È[var4], '#', Items.áŒŠà, 'X', var3);
            }
        }
        p_77586_1_.HorizonCode_Horizon_È(new ItemStack(Items.áˆºà), " #", "# ", '#', Items.áˆºÑ¢Õ);
    }
}
