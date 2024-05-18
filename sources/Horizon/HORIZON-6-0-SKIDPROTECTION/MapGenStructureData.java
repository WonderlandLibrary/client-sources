package HORIZON-6-0-SKIDPROTECTION;

public class MapGenStructureData extends WorldSavedData
{
    private NBTTagCompound Â;
    private static final String Ý = "CL_00000510";
    
    public MapGenStructureData(final String p_i43001_1_) {
        super(p_i43001_1_);
        this.Â = new NBTTagCompound();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        this.Â = nbt.ˆÏ­("Features");
    }
    
    @Override
    public void Ý(final NBTTagCompound nbt) {
        nbt.HorizonCode_Horizon_È("Features", this.Â);
    }
    
    public void HorizonCode_Horizon_È(final NBTTagCompound p_143043_1_, final int p_143043_2_, final int p_143043_3_) {
        this.Â.HorizonCode_Horizon_È(HorizonCode_Horizon_È(p_143043_2_, p_143043_3_), p_143043_1_);
    }
    
    public static String HorizonCode_Horizon_È(final int p_143042_0_, final int p_143042_1_) {
        return "[" + p_143042_0_ + "," + p_143042_1_ + "]";
    }
    
    public NBTTagCompound HorizonCode_Horizon_È() {
        return this.Â;
    }
}
