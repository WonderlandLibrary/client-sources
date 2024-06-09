package HORIZON-6-0-SKIDPROTECTION;

public abstract class WorldSavedData
{
    public final String HorizonCode_Horizon_È;
    private boolean Â;
    private static final String Ý = "CL_00000580";
    
    public WorldSavedData(final String name) {
        this.HorizonCode_Horizon_È = name;
    }
    
    public abstract void HorizonCode_Horizon_È(final NBTTagCompound p0);
    
    public abstract void Ý(final NBTTagCompound p0);
    
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È(true);
    }
    
    public void HorizonCode_Horizon_È(final boolean isDirty) {
        this.Â = isDirty;
    }
    
    public boolean Âµá€() {
        return this.Â;
    }
}
