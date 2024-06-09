package HORIZON-6-0-SKIDPROTECTION;

public class InventoryCrafting implements IInventory
{
    private final ItemStack[] HorizonCode_Horizon_È;
    private final int Â;
    private final int Ý;
    private final Container Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001743";
    
    public InventoryCrafting(final Container p_i1807_1_, final int p_i1807_2_, final int p_i1807_3_) {
        final int var4 = p_i1807_2_ * p_i1807_3_;
        this.HorizonCode_Horizon_È = new ItemStack[var4];
        this.Ø­áŒŠá = p_i1807_1_;
        this.Â = p_i1807_2_;
        this.Ý = p_i1807_3_;
    }
    
    @Override
    public int áŒŠÆ() {
        return this.HorizonCode_Horizon_È.length;
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return (slotIn >= this.áŒŠÆ()) ? null : this.HorizonCode_Horizon_È[slotIn];
    }
    
    public ItemStack Ý(final int p_70463_1_, final int p_70463_2_) {
        return (p_70463_1_ >= 0 && p_70463_1_ < this.Â && p_70463_2_ >= 0 && p_70463_2_ <= this.Ý) ? this.á(p_70463_1_ + p_70463_2_ * this.Â) : null;
    }
    
    @Override
    public String v_() {
        return "container.crafting";
    }
    
    @Override
    public boolean j_() {
        return false;
    }
    
    @Override
    public IChatComponent Ý() {
        return this.j_() ? new ChatComponentText(this.v_()) : new ChatComponentTranslation(this.v_(), new Object[0]);
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        if (this.HorizonCode_Horizon_È[index] != null) {
            final ItemStack var2 = this.HorizonCode_Horizon_È[index];
            this.HorizonCode_Horizon_È[index] = null;
            return var2;
        }
        return null;
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        if (this.HorizonCode_Horizon_È[index] == null) {
            return null;
        }
        if (this.HorizonCode_Horizon_È[index].Â <= count) {
            final ItemStack var3 = this.HorizonCode_Horizon_È[index];
            this.HorizonCode_Horizon_È[index] = null;
            this.Ø­áŒŠá.HorizonCode_Horizon_È(this);
            return var3;
        }
        final ItemStack var3 = this.HorizonCode_Horizon_È[index].HorizonCode_Horizon_È(count);
        if (this.HorizonCode_Horizon_È[index].Â == 0) {
            this.HorizonCode_Horizon_È[index] = null;
        }
        this.Ø­áŒŠá.HorizonCode_Horizon_È(this);
        return var3;
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        this.HorizonCode_Horizon_È[index] = stack;
        this.Ø­áŒŠá.HorizonCode_Horizon_È(this);
    }
    
    @Override
    public int Ñ¢á() {
        return 64;
    }
    
    @Override
    public void ŠÄ() {
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer playerIn) {
        return true;
    }
    
    @Override
    public void Âµá€(final EntityPlayer playerIn) {
    }
    
    @Override
    public void Ó(final EntityPlayer playerIn) {
    }
    
    @Override
    public boolean Ø­áŒŠá(final int index, final ItemStack stack) {
        return true;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int id) {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int id, final int value) {
    }
    
    @Override
    public int Âµá€() {
        return 0;
    }
    
    @Override
    public void ŒÏ() {
        for (int var1 = 0; var1 < this.HorizonCode_Horizon_È.length; ++var1) {
            this.HorizonCode_Horizon_È[var1] = null;
        }
    }
    
    public int Ø­áŒŠá() {
        return this.Ý;
    }
    
    public int Ó() {
        return this.Â;
    }
}
