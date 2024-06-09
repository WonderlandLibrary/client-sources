package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.collect.Lists;
import java.util.List;

public class EntitySenses
{
    EntityLiving HorizonCode_Horizon_È;
    List Â;
    List Ý;
    private static final String Ø­áŒŠá = "CL_00001628";
    
    public EntitySenses(final EntityLiving p_i1672_1_) {
        this.Â = Lists.newArrayList();
        this.Ý = Lists.newArrayList();
        this.HorizonCode_Horizon_È = p_i1672_1_;
    }
    
    public void HorizonCode_Horizon_È() {
        this.Â.clear();
        this.Ý.clear();
    }
    
    public boolean HorizonCode_Horizon_È(final Entity p_75522_1_) {
        if (this.Â.contains(p_75522_1_)) {
            return true;
        }
        if (this.Ý.contains(p_75522_1_)) {
            return false;
        }
        this.HorizonCode_Horizon_È.Ï­Ðƒà.Ï­Ðƒà.HorizonCode_Horizon_È("canSee");
        final boolean var2 = this.HorizonCode_Horizon_È.µà(p_75522_1_);
        this.HorizonCode_Horizon_È.Ï­Ðƒà.Ï­Ðƒà.Â();
        if (var2) {
            this.Â.add(p_75522_1_);
        }
        else {
            this.Ý.add(p_75522_1_);
        }
        return var2;
    }
}
