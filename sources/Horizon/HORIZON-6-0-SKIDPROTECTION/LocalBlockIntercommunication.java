package HORIZON-6-0-SKIDPROTECTION;

public class LocalBlockIntercommunication implements IInteractionObject
{
    private String HorizonCode_Horizon_È;
    private IChatComponent Â;
    private static final String Ý = "CL_00002571";
    
    public LocalBlockIntercommunication(final String p_i46277_1_, final IChatComponent p_i46277_2_) {
        this.HorizonCode_Horizon_È = p_i46277_1_;
        this.Â = p_i46277_2_;
    }
    
    @Override
    public Container HorizonCode_Horizon_È(final InventoryPlayer playerInventory, final EntityPlayer playerIn) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String v_() {
        return this.Â.Ø();
    }
    
    @Override
    public boolean j_() {
        return true;
    }
    
    @Override
    public String Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public IChatComponent Ý() {
        return this.Â;
    }
}
