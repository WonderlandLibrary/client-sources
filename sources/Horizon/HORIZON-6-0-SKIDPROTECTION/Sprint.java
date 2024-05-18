package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -13330213, Â = "Sprints 24/7.", HorizonCode_Horizon_È = "Sprint")
public class Sprint extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È && (this.Â.ŠÄ.ÇªÉ.HorizonCode_Horizon_È || this.Â.ŠÄ.ÇŽà.HorizonCode_Horizon_È || this.Â.ŠÄ.ŠÏ­áˆºá.HorizonCode_Horizon_È || this.Â.ŠÄ.ŠáˆºÂ.HorizonCode_Horizon_È) && !this.Â.á.¥à) {
            this.Â.á.Â(true);
        }
    }
    
    @Override
    public void á() {
        this.Â.á.Â(false);
    }
}
