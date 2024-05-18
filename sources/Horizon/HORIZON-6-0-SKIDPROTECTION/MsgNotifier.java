package HORIZON-6-0-SKIDPROTECTION;

public class MsgNotifier
{
    public String HorizonCode_Horizon_È;
    public String Â;
    public int Ý;
    public int Ø­áŒŠá;
    public TimeHelper Âµá€;
    public TimeHelper Ó;
    private int à;
    private boolean Ø;
    
    public MsgNotifier() {
        this.HorizonCode_Horizon_È = "";
        this.Â = "";
        this.Ý = 0;
        this.Ø­áŒŠá = 0;
        this.Âµá€ = new TimeHelper();
        this.Ó = new TimeHelper();
        this.à = 0;
        this.Ø = false;
    }
    
    public void HorizonCode_Horizon_È() {
        if (this.Âµá€.Â(1000L)) {
            if (this.Ý == 0) {
                this.Ý = 0;
                this.Ø = true;
            }
            --this.Ý;
            this.Âµá€.Ø­áŒŠá();
        }
        if (this.Ø) {
            this.à -= 5;
            if (this.à == 0) {
                this.HorizonCode_Horizon_È = "";
                this.Â = "";
            }
        }
        else {
            this.à += 5;
        }
        if (this.à >= 44) {
            this.à = 44;
        }
        if (this.HorizonCode_Horizon_È != null && !this.HorizonCode_Horizon_È.equalsIgnoreCase("")) {
            final int stringW = UIFonts.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â) / 2;
            final int stringM = UIFonts.Â.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È) / 2;
            Gui_1808253012.HorizonCode_Horizon_È(GuiScreen.Çªà¢ / 2 - stringW - 2, -12 + this.à, GuiScreen.Çªà¢ / 2 + stringW + 10, -1 + this.à + 20, -13222335);
            Gui_1808253012.HorizonCode_Horizon_È(GuiScreen.Çªà¢ / 2 - stringW, -10 + this.à, GuiScreen.Çªà¢ / 2 + stringW + 8, -1 + this.à + 18, -14210000);
            UIFonts.Â.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, GuiScreen.Çªà¢ / 2 - stringM + 2, -11 + this.à, -16732453);
            UIFonts.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â, GuiScreen.Çªà¢ / 2 - stringW + 4, 8 + this.à, -1);
        }
    }
    
    public void HorizonCode_Horizon_È(final String msg, final String msgx, final int delay) {
        Horizon.à¢.ÇŽá€.HorizonCode_Horizon_È(Horizon.à¢.áŒŠ + "/sounds/notification.wav");
        this.à = 0;
        this.Ø = false;
        this.HorizonCode_Horizon_È = msg;
        this.Â = msgx;
        this.Ý = delay;
    }
}
