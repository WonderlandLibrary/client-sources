package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class ItemCoal extends Item_1028566121
{
    private static final String à = "CL_00000002";
    
    public ItemCoal() {
        this.HorizonCode_Horizon_È(true);
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(CreativeTabs.á);
    }
    
    @Override
    public String Â(final ItemStack stack) {
        return (stack.Ø() == 1) ? "item.charcoal" : "item.coal";
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List subItems) {
        subItems.add(new ItemStack(itemIn, 1, 0));
        subItems.add(new ItemStack(itemIn, 1, 1));
    }
}
