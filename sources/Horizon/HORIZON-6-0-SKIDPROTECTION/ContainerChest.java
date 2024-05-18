package HORIZON-6-0-SKIDPROTECTION;

public class ContainerChest extends Container
{
    private IInventory HorizonCode_Horizon_È;
    private int Ó;
    private static final String à = "CL_00001742";
    
    public ContainerChest(final IInventory p_i45801_1_, final IInventory p_i45801_2_, final EntityPlayer p_i45801_3_) {
        this.HorizonCode_Horizon_È = p_i45801_2_;
        this.Ó = p_i45801_2_.áŒŠÆ() / 9;
        p_i45801_2_.Âµá€(p_i45801_3_);
        final int var4 = (this.Ó - 4) * 18;
        for (int var5 = 0; var5 < this.Ó; ++var5) {
            for (int var6 = 0; var6 < 9; ++var6) {
                this.Â(new Slot(p_i45801_2_, var6 + var5 * 9, 8 + var6 * 18, 18 + var5 * 18));
            }
        }
        for (int var5 = 0; var5 < 3; ++var5) {
            for (int var6 = 0; var6 < 9; ++var6) {
                this.Â(new Slot(p_i45801_1_, var6 + var5 * 9 + 9, 8 + var6 * 18, 103 + var5 * 18 + var4));
            }
        }
        for (int var5 = 0; var5 < 9; ++var5) {
            this.Â(new Slot(p_i45801_1_, var5, 8 + var5 * 18, 161 + var4));
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
            if (index < this.Ó * 9) {
                if (!this.HorizonCode_Horizon_È(var5, this.Ó * 9, this.Ý.size(), true)) {
                    return null;
                }
            }
            else if (!this.HorizonCode_Horizon_È(var5, 0, this.Ó * 9, false)) {
                return null;
            }
            if (var5.Â == 0) {
                var4.Â(null);
            }
            else {
                var4.Ý();
            }
        }
        return var3;
    }
    
    @Override
    public void Â(final EntityPlayer p_75134_1_) {
        super.Â(p_75134_1_);
        this.HorizonCode_Horizon_È.Ó(p_75134_1_);
    }
    
    public IInventory HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
}
