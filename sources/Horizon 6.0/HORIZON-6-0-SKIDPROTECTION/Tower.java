package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = 268435455, Â = "Tower you up to the Horizon, c:", HorizonCode_Horizon_È = "Tower")
public class Tower extends Mod
{
    public static int Ý;
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        final Float yaw = this.Â.á.Š;
        if (this.Â.á.áŒŠá() == null) {
            this.Â.ŠÄ.ŠØ.HorizonCode_Horizon_È = false;
            return;
        }
        if (!(this.Â.á.áŒŠá().HorizonCode_Horizon_È() instanceof ItemBlock)) {
            this.Â.ŠÄ.ŠØ.HorizonCode_Horizon_È = false;
            return;
        }
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.Ý) {
            this.Â.á.Â(yaw, 90.0f);
            this.HorizonCode_Horizon_È(this.Â.ŠÄ.ŠØ.HorizonCode_Horizon_È = true);
        }
        else {
            this.HorizonCode_Horizon_È(false);
        }
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            Minecraft.Ê = 0;
            this.Â.á.Ý(this.Â.á.ŒÏ, 0.7 + this.Â.á.Çªà¢, this.Â.á.Ê);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
    }
    
    @Override
    public void á() {
        Minecraft.Ê = 4;
        this.Â.ŠÄ.ŠØ.HorizonCode_Horizon_È = false;
    }
}
