package HORIZON-6-0-SKIDPROTECTION;

public class ContainerBeacon extends Container
{
    private IInventory HorizonCode_Horizon_È;
    private final HorizonCode_Horizon_È Ó;
    private static final String à = "CL_00001735";
    
    public ContainerBeacon(final IInventory p_i45804_1_, final IInventory p_i45804_2_) {
        this.HorizonCode_Horizon_È = p_i45804_2_;
        this.Â(this.Ó = new HorizonCode_Horizon_È(p_i45804_2_, 0, 136, 110));
        final byte var3 = 36;
        final short var4 = 137;
        for (int var5 = 0; var5 < 3; ++var5) {
            for (int var6 = 0; var6 < 9; ++var6) {
                this.Â(new Slot(p_i45804_1_, var6 + var5 * 9 + 9, var3 + var6 * 18, var4 + var5 * 18));
            }
        }
        for (int var5 = 0; var5 < 9; ++var5) {
            this.Â(new Slot(p_i45804_1_, var5, var3 + var5 * 18, 58 + var4));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ICrafting p_75132_1_) {
        super.HorizonCode_Horizon_È(p_75132_1_);
        p_75132_1_.HorizonCode_Horizon_È(this, this.HorizonCode_Horizon_È);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int p_75137_1_, final int p_75137_2_) {
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_75137_1_, p_75137_2_);
    }
    
    public IInventory HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
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
            if (index == 0) {
                if (!this.HorizonCode_Horizon_È(var5, 1, 37, true)) {
                    return null;
                }
                var4.HorizonCode_Horizon_È(var5, var3);
            }
            else if (!this.Ó.Â() && this.Ó.HorizonCode_Horizon_È(var5) && var5.Â == 1) {
                if (!this.HorizonCode_Horizon_È(var5, 0, 1, false)) {
                    return null;
                }
            }
            else if (index >= 1 && index < 28) {
                if (!this.HorizonCode_Horizon_È(var5, 28, 37, false)) {
                    return null;
                }
            }
            else if (index >= 28 && index < 37) {
                if (!this.HorizonCode_Horizon_È(var5, 1, 28, false)) {
                    return null;
                }
            }
            else if (!this.HorizonCode_Horizon_È(var5, 1, 37, false)) {
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
    
    class HorizonCode_Horizon_È extends Slot
    {
        private static final String Ó = "CL_00001736";
        
        public HorizonCode_Horizon_È(final IInventory p_i1801_2_, final int p_i1801_3_, final int p_i1801_4_, final int p_i1801_5_) {
            super(p_i1801_2_, p_i1801_3_, p_i1801_4_, p_i1801_5_);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final ItemStack stack) {
            return stack != null && (stack.HorizonCode_Horizon_È() == Items.µ || stack.HorizonCode_Horizon_È() == Items.áŒŠÆ || stack.HorizonCode_Horizon_È() == Items.ÂµÈ || stack.HorizonCode_Horizon_È() == Items.áˆºÑ¢Õ);
        }
        
        @Override
        public int Ø­áŒŠá() {
            return 1;
        }
    }
}
