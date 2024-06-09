package HORIZON-6-0-SKIDPROTECTION;

public class LockCode
{
    public static final LockCode HorizonCode_Horizon_È;
    private final String Â;
    private static final String Ý = "CL_00002260";
    
    static {
        HorizonCode_Horizon_È = new LockCode("");
    }
    
    public LockCode(final String p_i45903_1_) {
        this.Â = p_i45903_1_;
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Â == null || this.Â.isEmpty();
    }
    
    public String Â() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        nbt.HorizonCode_Horizon_È("Lock", this.Â);
    }
    
    public static LockCode Â(final NBTTagCompound nbt) {
        if (nbt.Â("Lock", 8)) {
            final String var1 = nbt.áˆºÑ¢Õ("Lock");
            return new LockCode(var1);
        }
        return LockCode.HorizonCode_Horizon_È;
    }
}
