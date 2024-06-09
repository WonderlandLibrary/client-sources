package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = 0, Â = "Regeneration, only on Vanilla Servers.", HorizonCode_Horizon_È = "Regen")
public class Regen extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final PotionEffect po = new PotionEffect(10, 20, 2);
            this.Â.á.HorizonCode_Horizon_È(po);
        }
    }
    
    @Override
    public void á() {
        this.Â.á.Å(10);
    }
}
