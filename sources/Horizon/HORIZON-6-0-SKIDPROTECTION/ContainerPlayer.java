package HORIZON-6-0-SKIDPROTECTION;

public class ContainerPlayer extends Container
{
    public InventoryCrafting HorizonCode_Horizon_È;
    public IInventory Ó;
    public boolean à;
    private final EntityPlayer Ø;
    private static final String áŒŠÆ = "CL_00001754";
    
    public ContainerPlayer(final InventoryPlayer p_i1819_1_, final boolean p_i1819_2_, final EntityPlayer p_i1819_3_) {
        this.HorizonCode_Horizon_È = new InventoryCrafting(this, 2, 2);
        this.Ó = new InventoryCraftResult();
        this.à = p_i1819_2_;
        this.Ø = p_i1819_3_;
        this.Â(new SlotCrafting(p_i1819_1_.Ø­áŒŠá, this.HorizonCode_Horizon_È, this.Ó, 0, 144, 36));
        for (int var4 = 0; var4 < 2; ++var4) {
            for (int var5 = 0; var5 < 2; ++var5) {
                this.Â(new Slot(this.HorizonCode_Horizon_È, var5 + var4 * 2, 88 + var5 * 18, 26 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 4; ++var4) {
            final int var6 = var4;
            this.Â(new Slot(p_i1819_1_, p_i1819_1_.áŒŠÆ() - 1 - var4, 8, 8 + var4 * 18) {
                private static final String Ó = "CL_00001755";
                
                @Override
                public int Ø­áŒŠá() {
                    return 1;
                }
                
                @Override
                public boolean HorizonCode_Horizon_È(final ItemStack stack) {
                    return stack != null && ((stack.HorizonCode_Horizon_È() instanceof ItemArmor) ? (((ItemArmor)stack.HorizonCode_Horizon_È()).Ø == var6) : ((stack.HorizonCode_Horizon_È() == Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Æ) || stack.HorizonCode_Horizon_È() == Items.ˆ) && var6 == 0));
                }
                
                @Override
                public String Âµá€() {
                    return ItemArmor.à[var6];
                }
            });
        }
        for (int var4 = 0; var4 < 3; ++var4) {
            for (int var5 = 0; var5 < 9; ++var5) {
                this.Â(new Slot(p_i1819_1_, var5 + (var4 + 1) * 9, 8 + var5 * 18, 84 + var4 * 18));
            }
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            this.Â(new Slot(p_i1819_1_, var4, 8 + var4 * 18, 142));
        }
        this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final IInventory p_75130_1_) {
        this.Ó.Ý(0, CraftingManager.HorizonCode_Horizon_È().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Ø.Ï­Ðƒà));
    }
    
    @Override
    public void Â(final EntityPlayer p_75134_1_) {
        super.Â(p_75134_1_);
        for (int var2 = 0; var2 < 4; ++var2) {
            final ItemStack var3 = this.HorizonCode_Horizon_È.ˆÏ­(var2);
            if (var3 != null) {
                p_75134_1_.HorizonCode_Horizon_È(var3, false);
            }
        }
        this.Ó.Ý(0, null);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn) {
        return true;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final EntityPlayer playerIn, final int index) {
        ItemStack var3 = null;
        final Slot var4 = this.Ý.get(index);
        if (var4 != null && var4.Â()) {
            final ItemStack var5 = var4.HorizonCode_Horizon_È();
            var3 = var5.áˆºÑ¢Õ();
            if (index == 0) {
                if (!this.HorizonCode_Horizon_È(var5, 9, 45, true)) {
                    return null;
                }
                var4.HorizonCode_Horizon_È(var5, var3);
            }
            else if (index >= 1 && index < 5) {
                if (!this.HorizonCode_Horizon_È(var5, 9, 45, false)) {
                    return null;
                }
            }
            else if (index >= 5 && index < 9) {
                if (!this.HorizonCode_Horizon_È(var5, 9, 45, false)) {
                    return null;
                }
            }
            else if (var3.HorizonCode_Horizon_È() instanceof ItemArmor && !this.Ý.get(5 + ((ItemArmor)var3.HorizonCode_Horizon_È()).Ø).Â()) {
                final int var6 = 5 + ((ItemArmor)var3.HorizonCode_Horizon_È()).Ø;
                if (!this.HorizonCode_Horizon_È(var5, var6, var6 + 1, false)) {
                    return null;
                }
            }
            else if (index >= 9 && index < 36) {
                if (!this.HorizonCode_Horizon_È(var5, 36, 45, false)) {
                    return null;
                }
            }
            else if (index >= 36 && index < 45) {
                if (!this.HorizonCode_Horizon_È(var5, 9, 36, false)) {
                    return null;
                }
            }
            else if (!this.HorizonCode_Horizon_È(var5, 9, 45, false)) {
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
