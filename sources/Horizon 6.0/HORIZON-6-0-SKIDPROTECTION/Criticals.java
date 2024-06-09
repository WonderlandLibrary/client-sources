package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.COMBAT, Ý = -1618884, Â = "give Criticals when you punch a player.", HorizonCode_Horizon_È = "Criticals")
public class Criticals extends Mod
{
    private float Ý;
    private boolean Ø­áŒŠá;
    private int Âµá€;
    
    public Criticals() {
        this.Ý = 0.0f;
        this.Ø­áŒŠá = false;
    }
    
    @Override
    public void á() {
        this.Ý = 0.0f;
        this.Ø­áŒŠá = false;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.Ø­áŒŠá = true;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventPacketSend e) {
        if (e.Ý() instanceof C03PacketPlayer && this.Âµá€ > 0) {
            --this.Âµá€;
            e.HorizonCode_Horizon_È(true);
        }
    }
    
    public boolean Å() {
        return this.Â.á.£ÂµÄ() || this.Â.á.HorizonCode_Horizon_È(Material.áŒŠÆ) || this.Â.á.i_() || this.Â.á.ÇŽÈ().contains(Potion.µà) || this.Â.á.Æ != null;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventAttack e) {
        if (e.Ý() == EventAttack.HorizonCode_Horizon_È.HorizonCode_Horizon_È && this.Âµá€ == 0 && this.Â.á.Âµà) {
            this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢ + 0.0625101, this.Â.á.Ê, false));
            this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C03PacketPlayer.HorizonCode_Horizon_È(this.Â.á.ŒÏ, this.Â.á.Çªà¢, this.Â.á.Ê, false));
            this.Âµá€ = 1;
        }
    }
}
