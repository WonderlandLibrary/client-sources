package HORIZON-6-0-SKIDPROTECTION;

public class RecipesIngots
{
    private Object[][] HorizonCode_Horizon_È;
    private static final String Â = "CL_00000089";
    
    public RecipesIngots() {
        this.HorizonCode_Horizon_È = new Object[][] { { Blocks.ˆáŠ, new ItemStack(Items.ÂµÈ, 9) }, { Blocks.áŒŠ, new ItemStack(Items.áˆºÑ¢Õ, 9) }, { Blocks.Ø­á, new ItemStack(Items.áŒŠÆ, 9) }, { Blocks.ˆØ­áˆº, new ItemStack(Items.µ, 9) }, { Blocks.ŠÄ, new ItemStack(Items.áŒŠÔ, 9, EnumDyeColor.á.Ý()) }, { Blocks.ŒÐƒà, new ItemStack(Items.ÇŽá, 9) }, { Blocks.ÐƒÉ, new ItemStack(Items.Ø, 9, 0) }, { Blocks.ÂµÊ, new ItemStack(Items.Âµà, 9) }, { Blocks.ÇŽØ­à, new ItemStack(Items.£É, 9) } };
    }
    
    public void HorizonCode_Horizon_È(final CraftingManager p_77590_1_) {
        for (int var2 = 0; var2 < this.HorizonCode_Horizon_È.length; ++var2) {
            final Block var3 = (Block)this.HorizonCode_Horizon_È[var2][0];
            final ItemStack var4 = (ItemStack)this.HorizonCode_Horizon_È[var2][1];
            p_77590_1_.HorizonCode_Horizon_È(new ItemStack(var3), "###", "###", "###", '#', var4);
            p_77590_1_.HorizonCode_Horizon_È(var4, "#", '#', var3);
        }
        p_77590_1_.HorizonCode_Horizon_È(new ItemStack(Items.ÂµÈ), "###", "###", "###", '#', Items.Œáƒ);
        p_77590_1_.HorizonCode_Horizon_È(new ItemStack(Items.Œáƒ, 9), "#", '#', Items.ÂµÈ);
    }
}
