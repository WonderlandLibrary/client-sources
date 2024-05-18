package HORIZON-6-0-SKIDPROTECTION;

public class SlotMerchantResult extends Slot
{
    private final InventoryMerchant HorizonCode_Horizon_È;
    private EntityPlayer Ó;
    private int à;
    private final IMerchant Ø;
    private static final String áŒŠÆ = "CL_00001758";
    
    public SlotMerchantResult(final EntityPlayer p_i1822_1_, final IMerchant p_i1822_2_, final InventoryMerchant p_i1822_3_, final int p_i1822_4_, final int p_i1822_5_, final int p_i1822_6_) {
        super(p_i1822_3_, p_i1822_4_, p_i1822_5_, p_i1822_6_);
        this.Ó = p_i1822_1_;
        this.Ø = p_i1822_2_;
        this.HorizonCode_Horizon_È = p_i1822_3_;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack) {
        return false;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final int p_75209_1_) {
        if (this.Â()) {
            this.à += Math.min(p_75209_1_, this.HorizonCode_Horizon_È().Â);
        }
        return super.HorizonCode_Horizon_È(p_75209_1_);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final ItemStack p_75210_1_, final int p_75210_2_) {
        this.à += p_75210_2_;
        this.Âµá€(p_75210_1_);
    }
    
    @Override
    protected void Âµá€(final ItemStack p_75208_1_) {
        p_75208_1_.HorizonCode_Horizon_È(this.Ó.Ï­Ðƒà, this.Ó, this.à);
        this.à = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityPlayer playerIn, final ItemStack stack) {
        this.Âµá€(stack);
        final MerchantRecipe var3 = this.HorizonCode_Horizon_È.Ó();
        if (var3 != null) {
            ItemStack var4 = this.HorizonCode_Horizon_È.á(0);
            ItemStack var5 = this.HorizonCode_Horizon_È.á(1);
            if (this.HorizonCode_Horizon_È(var3, var4, var5) || this.HorizonCode_Horizon_È(var3, var5, var4)) {
                this.Ø.HorizonCode_Horizon_È(var3);
                playerIn.HorizonCode_Horizon_È(StatList.ÇŽÕ);
                if (var4 != null && var4.Â <= 0) {
                    var4 = null;
                }
                if (var5 != null && var5.Â <= 0) {
                    var5 = null;
                }
                this.HorizonCode_Horizon_È.Ý(0, var4);
                this.HorizonCode_Horizon_È.Ý(1, var5);
            }
        }
    }
    
    private boolean HorizonCode_Horizon_È(final MerchantRecipe trade, final ItemStack firstItem, final ItemStack secondItem) {
        final ItemStack var4 = trade.HorizonCode_Horizon_È();
        final ItemStack var5 = trade.Â();
        if (firstItem != null && firstItem.HorizonCode_Horizon_È() == var4.HorizonCode_Horizon_È()) {
            if (var5 != null && secondItem != null && var5.HorizonCode_Horizon_È() == secondItem.HorizonCode_Horizon_È()) {
                firstItem.Â -= var4.Â;
                secondItem.Â -= var5.Â;
                return true;
            }
            if (var5 == null && secondItem == null) {
                firstItem.Â -= var4.Â;
                return true;
            }
        }
        return false;
    }
}
