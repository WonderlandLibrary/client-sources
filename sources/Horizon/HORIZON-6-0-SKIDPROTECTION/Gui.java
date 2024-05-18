package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.NONE, Ý = 0, Â = "", HorizonCode_Horizon_È = "Gui")
public class Gui extends Mod
{
    private int Ý;
    
    public Gui() {
        this.Ý = 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        if (Horizon.à¢.Ä == null) {
            Horizon.à¢.Ä = new GuiDragUI();
            Horizon.à¢.Ñ¢Â.Â();
        }
        this.Â.HorizonCode_Horizon_È(Horizon.à¢.Ä);
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventTick e) {
        ++this.Ý;
        if (this.Ý == 2) {
            this.Ý = 0;
            this.ˆÏ­();
        }
    }
}
