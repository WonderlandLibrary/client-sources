package HORIZON-6-0-SKIDPROTECTION;

public class ContainerDispenser extends Container
{
    private IInventory HorizonCode_Horizon_È;
    private static final String Ó = "CL_00001763";
    
    public ContainerDispenser(final IInventory p_i45799_1_, final IInventory p_i45799_2_) {
        this.HorizonCode_Horizon_È = p_i45799_2_;
        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 3; ++var4) {
                this.Â(new Slot(p_i45799_2_, var4 + var3 * 3, 62 + var4 * 18, 17 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.Â(new Slot(p_i45799_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 9; ++var3) {
            this.Â(new Slot(p_i45799_1_, var3, 8 + var3 * 18, 142));
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn) {
        return this.HorizonCode_Horizon_È.Ø­áŒŠá(playerIn);
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final EntityPlayer playerIn, final int index) {
        ItemStack var3 = null;
        final Slot var4 = this.Ý.get(index);
        if (var4 != null && var4.Â()) {
            final ItemStack var5 = var4.HorizonCode_Horizon_È();
            var3 = var5.áˆºÑ¢Õ();
            if (index < 9) {
                if (!this.HorizonCode_Horizon_È(var5, 9, 45, true)) {
                    return null;
                }
            }
            else if (!this.HorizonCode_Horizon_È(var5, 0, 9, false)) {
                return null;
            }
            if (var5.Â == 0) {
                var4.Â(null);
            }
            else {
                var4.Ý();
            }
            if (var5.Â == var3.Â) {
                return null;
            }
            var4.HorizonCode_Horizon_È(playerIn, var5);
        }
        return var3;
    }
}
