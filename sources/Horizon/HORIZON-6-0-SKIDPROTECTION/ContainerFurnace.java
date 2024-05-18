package HORIZON-6-0-SKIDPROTECTION;

public class ContainerFurnace extends Container
{
    private final IInventory HorizonCode_Horizon_È;
    private int Ó;
    private int à;
    private int Ø;
    private int áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00001748";
    
    public ContainerFurnace(final InventoryPlayer p_i45794_1_, final IInventory p_i45794_2_) {
        this.HorizonCode_Horizon_È = p_i45794_2_;
        this.Â(new Slot(p_i45794_2_, 0, 56, 17));
        this.Â(new SlotFurnaceFuel(p_i45794_2_, 1, 56, 53));
        this.Â(new SlotFurnaceOutput(p_i45794_1_.Ø­áŒŠá, p_i45794_2_, 2, 116, 35));
        for (int var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.Â(new Slot(p_i45794_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }
        for (int var3 = 0; var3 < 9; ++var3) {
            this.Â(new Slot(p_i45794_1_, var3, 8 + var3 * 18, 142));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICrafting p_75132_1_) {
        super.HorizonCode_Horizon_È(p_75132_1_);
        p_75132_1_.HorizonCode_Horizon_È(this, this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void Ý() {
        super.Ý();
        for (int var1 = 0; var1 < this.Âµá€.size(); ++var1) {
            final ICrafting var2 = this.Âµá€.get(var1);
            if (this.Ó != this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(2)) {
                var2.HorizonCode_Horizon_È(this, 2, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(2));
            }
            if (this.Ø != this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0)) {
                var2.HorizonCode_Horizon_È(this, 0, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0));
            }
            if (this.áŒŠÆ != this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1)) {
                var2.HorizonCode_Horizon_È(this, 1, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1));
            }
            if (this.à != this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(3)) {
                var2.HorizonCode_Horizon_È(this, 3, this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(3));
            }
        }
        this.Ó = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(2);
        this.Ø = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0);
        this.áŒŠÆ = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(1);
        this.à = this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(3);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_75137_1_, final int p_75137_2_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_75137_1_, p_75137_2_);
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
            if (index == 2) {
                if (!this.HorizonCode_Horizon_È(var5, 3, 39, true)) {
                    return null;
                }
                var4.HorizonCode_Horizon_È(var5, var3);
            }
            else if (index != 1 && index != 0) {
                if (FurnaceRecipes.HorizonCode_Horizon_È().HorizonCode_Horizon_È(var5) != null) {
                    if (!this.HorizonCode_Horizon_È(var5, 0, 1, false)) {
                        return null;
                    }
                }
                else if (TileEntityFurnace.Ý(var5)) {
                    if (!this.HorizonCode_Horizon_È(var5, 1, 2, false)) {
                        return null;
                    }
                }
                else if (index >= 3 && index < 30) {
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
}
