package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.DISPLAY, Ý = -1671646, Â = "FullBright, nothing to say...", HorizonCode_Horizon_È = "FullBright")
public class FullBright extends Mod
{
    private Minecraft Ý;
    
    @Handler
    private void HorizonCode_Horizon_È(final EventTick event) {
        this.Ý = this.Â;
        this.Ý.ŠÄ.ŠÏ = 1000000.0f;
    }
    
    @Override
    public void á() {
        this.Ý.ŠÄ.ŠÏ = 1.0f;
    }
}
