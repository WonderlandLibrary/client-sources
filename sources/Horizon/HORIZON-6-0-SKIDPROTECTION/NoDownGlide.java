package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = 0, Â = "4 Gomme", HorizonCode_Horizon_È = "NoDownGlide")
public class NoDownGlide extends Mod
{
    @Override
    public void HorizonCode_Horizon_È() {
        if (this.Â.á == null) {
            return;
        }
        if (this.Â.Çªà¢() != null) {
            if (this.Â.Çªà¢().Â.contains("ascalter") || this.Â.Çªà¢().Â.contains("hypixel") || this.Â.Çªà¢().Â.contains("gommehd")) {
                for (int i = 0; i < 4; ++i) {
                    this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢ + 1.01, this.Â.á.Ê, false));
                    this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢, this.Â.á.Ê, false));
                }
                this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢ + 0.8, this.Â.á.Ê, false));
            }
            else if (this.Â.á.ŠÂµà) {
                if (this.Â.ÇŽÉ()) {
                    Utiils.HorizonCode_Horizon_È(1);
                }
                else {
                    Utiils.HorizonCode_Horizon_È();
                }
            }
        }
        this.Â.á.Ý(this.Â.á.ŒÏ, this.Â.á.Çªà¢ + 0.01, this.Â.á.Ê);
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventMovementSpeed e) {
        if (this.Â.á.Çªà¢()) {
            return;
        }
        e.Â(e.Ø­áŒŠá() * 1.0E-4);
        this.Â.á.ŠÂµà = true;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate e) {
        if (e.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È && !this.Â.á.áˆºáˆºáŠ.Â && this.Â.á.Ï­à > 0.0f && !this.Â.á.Çªà¢()) {
            this.Â.á.ˆá = -0.06027;
            this.Â.á.ŠÂµà = true;
        }
    }
}
