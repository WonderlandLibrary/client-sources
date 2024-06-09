package HORIZON-6-0-SKIDPROTECTION;

public class ContainerHorseInventory extends Container
{
    private IInventory HorizonCode_Horizon_È;
    private EntityHorse Ó;
    private static final String à = "CL_00001751";
    
    public ContainerHorseInventory(final IInventory p_i45791_1_, final IInventory p_i45791_2_, final EntityHorse p_i45791_3_, final EntityPlayer p_i45791_4_) {
        this.HorizonCode_Horizon_È = p_i45791_2_;
        this.Ó = p_i45791_3_;
        final byte var5 = 3;
        p_i45791_2_.Âµá€(p_i45791_4_);
        final int var6 = (var5 - 4) * 18;
        this.Â(new Slot(p_i45791_2_, 0, 8, 18) {
            private static final String Ó = "CL_00001752";
            
            @Override
            public boolean HorizonCode_Horizon_È(final ItemStack stack) {
                return super.HorizonCode_Horizon_È(stack) && stack.HorizonCode_Horizon_È() == Items.Û && !this.Â();
            }
        });
        this.Â(new Slot(p_i45791_2_, 1, 8, 36) {
            private static final String Ó = "CL_00001753";
            
            @Override
            public boolean HorizonCode_Horizon_È(final ItemStack stack) {
                return super.HorizonCode_Horizon_È(stack) && p_i45791_3_.ÇŽáˆºÈ() && EntityHorse.HorizonCode_Horizon_È(stack.HorizonCode_Horizon_È());
            }
            
            @Override
            public boolean Ó() {
                return p_i45791_3_.ÇŽáˆºÈ();
            }
        });
        if (p_i45791_3_.Ñ¢Õ()) {
            for (int var7 = 0; var7 < var5; ++var7) {
                for (int var8 = 0; var8 < 5; ++var8) {
                    this.Â(new Slot(p_i45791_2_, 2 + var8 + var7 * 5, 80 + var8 * 18, 18 + var7 * 18));
                }
            }
        }
        for (int var7 = 0; var7 < 3; ++var7) {
            for (int var8 = 0; var8 < 9; ++var8) {
                this.Â(new Slot(p_i45791_1_, var8 + var7 * 9 + 9, 8 + var8 * 18, 102 + var7 * 18 + var6));
            }
        }
        for (int var7 = 0; var7 < 9; ++var7) {
            this.Â(new Slot(p_i45791_1_, var7, 8 + var7 * 18, 160 + var6));
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final EntityPlayer playerIn) {
        return this.HorizonCode_Horizon_È.Ø­áŒŠá(playerIn) && this.Ó.Œ() && this.Ó.Ø­áŒŠá(playerIn) < 8.0f;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final EntityPlayer playerIn, final int index) {
        ItemStack var3 = null;
        final Slot var4 = this.Ý.get(index);
        if (var4 != null && var4.Â()) {
            final ItemStack var5 = var4.HorizonCode_Horizon_È();
            var3 = var5.áˆºÑ¢Õ();
            if (index < this.HorizonCode_Horizon_È.áŒŠÆ()) {
                if (!this.HorizonCode_Horizon_È(var5, this.HorizonCode_Horizon_È.áŒŠÆ(), this.Ý.size(), true)) {
                    return null;
                }
            }
            else if (this.HorizonCode_Horizon_È(1).HorizonCode_Horizon_È(var5) && !this.HorizonCode_Horizon_È(1).Â()) {
                if (!this.HorizonCode_Horizon_È(var5, 1, 2, false)) {
                    return null;
                }
            }
            else if (this.HorizonCode_Horizon_È(0).HorizonCode_Horizon_È(var5)) {
                if (!this.HorizonCode_Horizon_È(var5, 0, 1, false)) {
                    return null;
                }
            }
            else if (this.HorizonCode_Horizon_È.áŒŠÆ() <= 2 || !this.HorizonCode_Horizon_È(var5, 2, this.HorizonCode_Horizon_È.áŒŠÆ(), false)) {
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
}
