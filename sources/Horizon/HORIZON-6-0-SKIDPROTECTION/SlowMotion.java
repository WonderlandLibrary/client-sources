package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.DISPLAY, Ý = 0, Â = "All is Slow", HorizonCode_Horizon_È = "SlowMotion")
public class SlowMotion extends Mod
{
    public void HorizonCode_Horizon_È(final EventTick e) {
        this.Â.Ø.Ø­áŒŠá = 0.4f;
    }
    
    @Override
    public void á() {
        this.Â.Ø.Ø­áŒŠá = 1.0f;
    }
}
