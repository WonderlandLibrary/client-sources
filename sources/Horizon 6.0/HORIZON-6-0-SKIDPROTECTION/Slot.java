package HORIZON-6-0-SKIDPROTECTION;

public class Slot
{
    private final int HorizonCode_Horizon_È;
    public final IInventory Â;
    public int Ý;
    public int Ø­áŒŠá;
    public int Âµá€;
    private static final String Ó = "CL_00001762";
    
    public Slot(final IInventory p_i1824_1_, final int p_i1824_2_, final int p_i1824_3_, final int p_i1824_4_) {
        this.Â = p_i1824_1_;
        this.HorizonCode_Horizon_È = p_i1824_2_;
        this.Ø­áŒŠá = p_i1824_3_;
        this.Âµá€ = p_i1824_4_;
    }
    
    public void HorizonCode_Horizon_È(final ItemStack p_75220_1_, final ItemStack p_75220_2_) {
        if (p_75220_1_ != null && p_75220_2_ != null && p_75220_1_.HorizonCode_Horizon_È() == p_75220_2_.HorizonCode_Horizon_È()) {
            final int var3 = p_75220_2_.Â - p_75220_1_.Â;
            if (var3 > 0) {
                this.HorizonCode_Horizon_È(p_75220_1_, var3);
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final ItemStack p_75210_1_, final int p_75210_2_) {
    }
    
    protected void Âµá€(final ItemStack p_75208_1_) {
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer playerIn, final ItemStack stack) {
        this.Ý();
    }
    
    public boolean HorizonCode_Horizon_È(final ItemStack stack) {
        return true;
    }
    
    public ItemStack HorizonCode_Horizon_È() {
        return this.Â.á(this.HorizonCode_Horizon_È);
    }
    
    public boolean Â() {
        return this.HorizonCode_Horizon_È() != null;
    }
    
    public void Â(final ItemStack p_75215_1_) {
        this.Â.Ý(this.HorizonCode_Horizon_È, p_75215_1_);
        this.Ý();
    }
    
    public void Ý() {
        this.Â.ŠÄ();
    }
    
    public int Ø­áŒŠá() {
        return this.Â.Ñ¢á();
    }
    
    public int Ý(final ItemStack p_178170_1_) {
        return this.Ø­áŒŠá();
    }
    
    public String Âµá€() {
        return null;
    }
    
    public ItemStack HorizonCode_Horizon_È(final int p_75209_1_) {
        return this.Â.Â(this.HorizonCode_Horizon_È, p_75209_1_);
    }
    
    public boolean HorizonCode_Horizon_È(final IInventory p_75217_1_, final int p_75217_2_) {
        return p_75217_1_ == this.Â && p_75217_2_ == this.HorizonCode_Horizon_È;
    }
    
    public boolean HorizonCode_Horizon_È(final EntityPlayer p_82869_1_) {
        return true;
    }
    
    public boolean Ó() {
        return true;
    }
}
