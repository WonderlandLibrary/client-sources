package HORIZON-6-0-SKIDPROTECTION;

public class InventoryLargeChest implements ILockableContainer
{
    private String HorizonCode_Horizon_È;
    private ILockableContainer Â;
    private ILockableContainer Ý;
    private static final String Ø­áŒŠá = "CL_00001507";
    
    public InventoryLargeChest(final String p_i45905_1_, ILockableContainer p_i45905_2_, ILockableContainer p_i45905_3_) {
        this.HorizonCode_Horizon_È = p_i45905_1_;
        if (p_i45905_2_ == null) {
            p_i45905_2_ = p_i45905_3_;
        }
        if (p_i45905_3_ == null) {
            p_i45905_3_ = p_i45905_2_;
        }
        this.Â = p_i45905_2_;
        this.Ý = p_i45905_3_;
        if (p_i45905_2_.Ó()) {
            p_i45905_3_.HorizonCode_Horizon_È(p_i45905_2_.x_());
        }
        else if (p_i45905_3_.Ó()) {
            p_i45905_2_.HorizonCode_Horizon_È(p_i45905_3_.x_());
        }
    }
    
    @Override
    public int áŒŠÆ() {
        return this.Â.áŒŠÆ() + this.Ý.áŒŠÆ();
    }
    
    public boolean HorizonCode_Horizon_È(final IInventory p_90010_1_) {
        return this.Â == p_90010_1_ || this.Ý == p_90010_1_;
    }
    
    @Override
    public String v_() {
        return this.Â.j_() ? this.Â.v_() : (this.Ý.j_() ? this.Ý.v_() : this.HorizonCode_Horizon_È);
    }
    
    @Override
    public boolean j_() {
        return this.Â.j_() || this.Ý.j_();
    }
    
    @Override
    public IChatComponent Ý() {
        return this.j_() ? new ChatComponentText(this.v_()) : new ChatComponentTranslation(this.v_(), new Object[0]);
    }
    
    @Override
    public ItemStack á(final int slotIn) {
        return (slotIn >= this.Â.áŒŠÆ()) ? this.Ý.á(slotIn - this.Â.áŒŠÆ()) : this.Â.á(slotIn);
    }
    
    @Override
    public ItemStack Â(final int index, final int count) {
        return (index >= this.Â.áŒŠÆ()) ? this.Ý.Â(index - this.Â.áŒŠÆ(), count) : this.Â.Â(index, count);
    }
    
    @Override
    public ItemStack ˆÏ­(final int index) {
        return (index >= this.Â.áŒŠÆ()) ? this.Ý.ˆÏ­(index - this.Â.áŒŠÆ()) : this.Â.ˆÏ­(index);
    }
    
    @Override
    public void Ý(final int index, final ItemStack stack) {
        if (index >= this.Â.áŒŠÆ()) {
            this.Ý.Ý(index - this.Â.áŒŠÆ(), stack);
        }
        else {
            this.Â.Ý(index, stack);
        }
    }
    
    @Override
    public int Ñ¢á() {
        return this.Â.Ñ¢á();
    }
    
    @Override
    public void ŠÄ() {
        this.Â.ŠÄ();
        this.Ý.ŠÄ();
    }
    
    @Override
    public boolean Ø­áŒŠá(final EntityPlayer playerIn) {
        return this.Â.Ø­áŒŠá(playerIn) && this.Ý.Ø­áŒŠá(playerIn);
    }
    
    @Override
    public void Âµá€(final EntityPlayer playerIn) {
        this.Â.Âµá€(playerIn);
        this.Ý.Âµá€(playerIn);
    }
    
    @Override
    public void Ó(final EntityPlayer playerIn) {
        this.Â.Ó(playerIn);
        this.Ý.Ó(playerIn);
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
    public boolean Ó() {
        return this.Â.Ó() || this.Ý.Ó();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final LockCode code) {
        this.Â.HorizonCode_Horizon_È(code);
        this.Ý.HorizonCode_Horizon_È(code);
    }
    
    @Override
    public LockCode x_() {
        return this.Â.x_();
    }
    
    @Override
    public String Ø­áŒŠá() {
        return this.Â.Ø­áŒŠá();
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        return new ContainerChest(playerInventory, this, playerIn);
    }
    
    @Override
    public void ŒÏ() {
        this.Â.ŒÏ();
        this.Ý.ŒÏ();
    }
}
