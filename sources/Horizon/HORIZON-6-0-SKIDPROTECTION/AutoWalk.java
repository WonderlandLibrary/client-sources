package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -13330213, Â = "Walks automaticly", HorizonCode_Horizon_È = "AutoWalk")
public class AutoWalk extends Mod
{
    @Handler
    private void HorizonCode_Horizon_È(final EventTick event) {
        this.Â.ŠÄ.ÇªÉ.HorizonCode_Horizon_È = true;
    }
    
    @Override
    public void á() {
        this.Â.ŠÄ.ÇªÉ.HorizonCode_Horizon_È = false;
    }
}
