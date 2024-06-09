package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = 0, Â = "Blocks if you are in the range of a TNT Entity.", HorizonCode_Horizon_È = "TNTBlocker")
public class TnTBlocker extends Mod
{
    public boolean Ý;
    
    public TnTBlocker() {
        this.Ý = false;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            if (this.Å() && !this.Ý) {
                this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ý = EntityHelper.HorizonCode_Horizon_È(this.Â.á);
                this.Â.ŠÄ.ŠØ.HorizonCode_Horizon_È = true;
                this.Ý = true;
            }
            else if (!this.Å() && this.Ý) {
                this.Â.ŠÄ.ŠØ.HorizonCode_Horizon_È = false;
                this.Ý = false;
            }
        }
    }
    
    public boolean Å() {
        for (final Object e : this.Â.áŒŠÆ.Â) {
            if (e instanceof EntityTNTPrimed) {
                final EntityTNTPrimed tnt = (EntityTNTPrimed)e;
                if (this.Â.á.Ø­áŒŠá(tnt) <= 6.0) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
}
