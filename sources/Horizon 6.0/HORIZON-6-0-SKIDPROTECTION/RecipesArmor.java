package HORIZON-6-0-SKIDPROTECTION;

public class RecipesArmor
{
    private String[][] HorizonCode_Horizon_È;
    private Item_1028566121[][] Â;
    private static final String Ý = "CL_00000080";
    
    public RecipesArmor() {
        this.HorizonCode_Horizon_È = new String[][] { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
        this.Â = new Item_1028566121[][] { { Items.£áŒŠá, Items.áˆºÑ¢Õ, Items.áŒŠÆ, Items.ÂµÈ }, { Items.È, Items.Ï­à, Items.Ô, Items.Œ }, { Items.áŠ, Items.áˆºáˆºÈ, Items.ÇªÓ, Items.£Ï }, { Items.ˆáŠ, Items.ÇŽá€, Items.áˆºÏ, Items.Ø­á }, { Items.áŒŠ, Items.Ï, Items.ˆáƒ, Items.ˆÉ } };
    }
    
    public void HorizonCode_Horizon_È(final CraftingManager p_77609_1_) {
        for (int var2 = 0; var2 < this.Â[0].length; ++var2) {
            final Item_1028566121 var3 = this.Â[0][var2];
            for (int var4 = 0; var4 < this.Â.length - 1; ++var4) {
                final Item_1028566121 var5 = this.Â[var4 + 1][var2];
                p_77609_1_.HorizonCode_Horizon_È(new ItemStack(var5), this.HorizonCode_Horizon_È[var4], 'X', var3);
            }
        }
    }
}
