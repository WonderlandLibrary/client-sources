package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Objects;
import java.util.List;

public class SpectatorDetails
{
    private final ISpectatorMenuView HorizonCode_Horizon_È;
    private final List Â;
    private final int Ý;
    private static final String Ø­áŒŠá = "CL_00001923";
    
    public SpectatorDetails(final ISpectatorMenuView p_i45494_1_, final List p_i45494_2_, final int p_i45494_3_) {
        this.HorizonCode_Horizon_È = p_i45494_1_;
        this.Â = p_i45494_2_;
        this.Ý = p_i45494_3_;
    }
    
    public ISpectatorMenuObject HorizonCode_Horizon_È(final int p_178680_1_) {
        return (ISpectatorMenuObject)((p_178680_1_ >= 0 && p_178680_1_ < this.Â.size()) ? Objects.firstNonNull(this.Â.get(p_178680_1_), (Object)SpectatorMenu.HorizonCode_Horizon_È) : SpectatorMenu.HorizonCode_Horizon_È);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ý;
    }
}
