package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.DISPLAY, Ý = 0, Â = "Get AiDs", HorizonCode_Horizon_È = "Aids")
public class Aids extends Mod
{
    TimeHelper Ý;
    
    public Aids() {
        this.Ý = new TimeHelper();
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventTick e) {
        if (this.Ý.Â(99L)) {
            this.Ý.Ø­áŒŠá();
            this.Â.µÕ.Ý();
        }
    }
    
    @Override
    public void á() {
        this.Â.µÕ.Â();
    }
}
