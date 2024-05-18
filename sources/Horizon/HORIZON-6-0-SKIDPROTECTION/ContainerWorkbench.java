package HORIZON-6-0-SKIDPROTECTION;

public class ContainerWorkbench extends Container
{
    public InventoryCrafting HorizonCode_Horizon_È;
    public IInventory Ó;
    private World à;
    private BlockPos Ø;
    private static final String áŒŠÆ = "CL_00001744";
    
    public ContainerWorkbench(final InventoryPlayer p_i45800_1_, final World worldIn, final BlockPos p_i45800_3_) {
        this.HorizonCode_Horizon_È = new InventoryCrafting(this, 3, 3);
        this.Ó = new InventoryCraftResult();
        this.à = worldIn;
        this.Ø = p_i45800_3_;
        this.Â(new SlotCrafting(p_i45800_1_.Ø­áŒŠá, this.HorizonCode_Horizon_È, this.Ó, 0, 124, 35));
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 3; ++var5) {
                this.Â(new Slot(this.HorizonCode_Horizon_È, var5 + var4 * 3, 30 + var5 * 18, 17 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.Â(new Slot(p_i45800_1_, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            this.Â(new Slot(p_i45800_1_, var4, 8 + var4 * 18, 142));
        }
        this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IInventory p_75130_1_) {
        this.Ó.Ý(0, CraftingManager.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.à));
    }
    
    @Override
    public void Â(final EntityPlayer p_75134_1_) {
        super.Â(p_75134_1_);
        if (!this.à.ŠÄ) {
            for (int var2 = 0; var2 < 9; ++var2) {
                final ItemStack var3 = this.HorizonCode_Horizon_È.ˆÏ­(var2);
                if (var3 != null) {
                    p_75134_1_.HorizonCode_Horizon_È(var3, false);
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn) {
        return this.à.Â(this.Ø).Ý() == Blocks.ˆÉ && playerIn.Âµá€(this.Ø.HorizonCode_Horizon_È() + 0.5, this.Ø.Â() + 0.5, this.Ø.Ý() + 0.5) <= 64.0;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final EntityPlayer playerIn, final int index) {
        ItemStack var3 = null;
        final Slot var4 = this.Ý.get(index);
        if (var4 != null && var4.Â()) {
            final ItemStack var5 = var4.HorizonCode_Horizon_È();
            var3 = var5.áˆºÑ¢Õ();
            if (index == 0) {
                if (!this.HorizonCode_Horizon_È(var5, 10, 46, true)) {
                    return null;
                }
                var4.HorizonCode_Horizon_È(var5, var3);
            }
            else if (index >= 10 && index < 37) {
                if (!this.HorizonCode_Horizon_È(var5, 37, 46, false)) {
                    return null;
                }
            }
            else if (index >= 37 && index < 46) {
                if (!this.HorizonCode_Horizon_È(var5, 10, 37, false)) {
                    return null;
                }
            }
            else if (!this.HorizonCode_Horizon_È(var5, 10, 46, false)) {
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
    public boolean HorizonCode_Horizon_È(final ItemStack p_94530_1_, final Slot p_94530_2_) {
        return p_94530_2_.Â != this.Ó && super.HorizonCode_Horizon_È(p_94530_1_, p_94530_2_);
    }
}
