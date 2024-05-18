package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class GoalColor implements IScoreObjectiveCriteria
{
    private final String áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00001961";
    
    public GoalColor(final String p_i45549_1_, final EnumChatFormatting p_i45549_2_) {
        this.áˆºÑ¢Õ = String.valueOf(p_i45549_1_) + p_i45549_2_.Ø­áŒŠá();
        IScoreObjectiveCriteria.HorizonCode_Horizon_È.put(this.áˆºÑ¢Õ, this);
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
