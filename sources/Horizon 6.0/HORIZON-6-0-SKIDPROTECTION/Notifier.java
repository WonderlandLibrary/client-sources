package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.DISPLAY, Ý = 0, Â = "Some Infomations about yourself.", HorizonCode_Horizon_È = "Notifier")
public class Notifier extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        if (!Horizon.Çªà¢ && this.Â.á.ŠÏ­áˆºá().HorizonCode_Horizon_È() <= 6) {
            Horizon.Çªà¢ = true;
            Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Your Food-Level is low!", 3, "note.pling", 20.0f, 1.0f);
        }
        if (Horizon.Çªà¢ && this.Â.á.ŠÏ­áˆºá().HorizonCode_Horizon_È() > 6) {
            Horizon.Çªà¢ = false;
        }
        if (!Horizon.Ê && this.Â.á.Ï­Ä() <= 6.0f) {
            Horizon.Ê = true;
            Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Your Health-Level is low!", 3, "note.pling", 20.0f, 1.0f);
        }
        if (Horizon.Ê && this.Â.á.Ï­Ä() > 6.0f) {
            Horizon.Ê = false;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Notifier is now enabled!", 3, "note.bass", 20.0f, 1.0f);
    }
    
    @Override
    public void á() {
        Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Notifier is now disabled!", 3, "note.bd", 20.0f, 1.0f);
    }
}
