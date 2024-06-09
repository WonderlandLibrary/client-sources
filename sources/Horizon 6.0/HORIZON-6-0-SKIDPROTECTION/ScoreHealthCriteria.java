package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class ScoreHealthCriteria extends ScoreDummyCriteria
{
    private static final String áˆºÑ¢Õ = "CL_00000623";
    
    public ScoreHealthCriteria(final String p_i2312_1_) {
        super(p_i2312_1_);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final List p_96635_1_) {
        float var2 = 0.0f;
        for (final EntityPlayer var4 : p_96635_1_) {
            var2 += var4.Ï­Ä() + var4.Ñ¢È();
        }
        if (p_96635_1_.size() > 0) {
            var2 /= p_96635_1_.size();
        }
        return MathHelper.Ó(var2);
    }
    
    @Override
    public boolean Â() {
        return true;
    }
    
    @Override
    public IScoreObjectiveCriteria.HorizonCode_Horizon_È Ý() {
        return IScoreObjectiveCriteria.HorizonCode_Horizon_È.Â;
    }
}
