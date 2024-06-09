package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria
{
    private final String áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00000622";
    
    public ScoreDummyCriteria(final String p_i2311_1_) {
        this.áˆºÑ¢Õ = p_i2311_1_;
        IScoreObjectiveCriteria.HorizonCode_Horizon_È.put(p_i2311_1_, this);
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return this.áˆºÑ¢Õ;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final List p_96635_1_) {
        return 0;
    }
    
    @Override
    public boolean Â() {
        return false;
    }
    
    @Override
    public HorizonCode_Horizon_È Ý() {
        return IScoreObjectiveCriteria.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
    }
}
