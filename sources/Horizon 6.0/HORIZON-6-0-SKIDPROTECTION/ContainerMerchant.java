package HORIZON-6-0-SKIDPROTECTION;

public class ContainerMerchant extends Container
{
    private IMerchant HorizonCode_Horizon_È;
    private InventoryMerchant Ó;
    private final World à;
    private static final String Ø = "CL_00001757";
    
    public ContainerMerchant(final InventoryPlayer p_i1821_1_, final IMerchant p_i1821_2_, final World worldIn) {
        this.HorizonCode_Horizon_È = p_i1821_2_;
        this.à = worldIn;
        this.Ó = new InventoryMerchant(p_i1821_1_.Ø­áŒŠá, p_i1821_2_);
        this.Â(new Slot(this.Ó, 0, 36, 53));
        this.Â(new Slot(this.Ó, 1, 62, 53));
        this.Â(new SlotMerchantResult(p_i1821_1_.Ø­áŒŠá, p_i1821_2_, this.Ó, 2, 120, 53));
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.Â(new Slot(p_i1821_1_, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            this.Â(new Slot(p_i1821_1_, var4, 8 + var4 * 18, 142));
        }
    }
    
    public InventoryMerchant HorizonCode_Horizon_È() {
        return this.Ó;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICrafting p_75132_1_) {
        super.HorizonCode_Horizon_È(p_75132_1_);
    }
    
    @Override
    public void Ý() {
        super.Ý();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IInventory p_75130_1_) {
        this.Ó.Ø­áŒŠá();
        super.HorizonCode_Horizon_È(p_75130_1_);
    }
    
    public void Ø­áŒŠá(final int p_75175_1_) {
        this.Ó.Â(p_75175_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_75137_1_, final int p_75137_2_) {
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn) {
        return this.HorizonCode_Horizon_È.HorizonCode_Horizon_È() == playerIn;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final EntityPlayer playerIn, final int index) {
        ItemStack var3 = null;
        final Slot var4 = this.Ý.get(index);
        if (var4 != null && var4.Â()) {
            final ItemStack var5 = var4.HorizonCode_Horizon_È();
            var3 = var5.áˆºÑ¢Õ();
            if (index == 2) {
                if (!this.HorizonCode_Horizon_È(var5, 3, 39, true)) {
                    return null;
                }
                var4.HorizonCode_Horizon_È(var5, var3);
            }
            else if (index != 0 && index != 1) {
                if (index >= 3 && index < 30) {
                    if (!this.HorizonCode_Horizon_È(var5, 30, 39, false)) {
                        return null;
                    }
                }
                else if (index >= 30 && index < 39 && !this.HorizonCode_Horizon_È(var5, 3, 30, false)) {
                    return null;
                }
            }
            else if (!this.HorizonCode_Horizon_È(var5, 3, 39, false)) {
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
    
    @Override
    public void Â(final EntityPlayer p_75134_1_) {
        super.Â(p_75134_1_);
        this.HorizonCode_Horizon_È.a_((EntityPlayer)null);
        super.Â(p_75134_1_);
        if (!this.à.ŠÄ) {
            ItemStack var2 = this.Ó.ˆÏ­(0);
            if (var2 != null) {
                p_75134_1_.HorizonCode_Horizon_È(var2, false);
            }
            var2 = this.Ó.ˆÏ­(1);
            if (var2 != null) {
                p_75134_1_.HorizonCode_Horizon_È(var2, false);
            }
        }
    }
}
