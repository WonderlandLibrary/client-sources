package HORIZON-6-0-SKIDPROTECTION;

public class SaveDataMemoryStorage extends MapStorage
{
    private static final String Â = "CL_00001963";
    
    public SaveDataMemoryStorage() {
        super(null);
    }
    
    @Override
    public WorldSavedData HorizonCode_Horizon_È(final Class p_75742_1_, final String p_75742_2_) {
        return this.HorizonCode_Horizon_È.get(p_75742_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_75745_1_, final WorldSavedData p_75745_2_) {
        this.HorizonCode_Horizon_È.put(p_75745_1_, p_75745_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
    }
    
    @Override
    public int HorizonCode_Horizon_È(final String p_75743_1_) {
        return 0;
    }
}
