package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MOVEMENT, Ý = -13330213, Â = "Sneak.", HorizonCode_Horizon_È = "Sneak")
public class Sneak extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        ev.Ý();
        final EventUpdate.HorizonCode_Horizon_È horizonCode_Horizon_È = EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.Ý) {
            this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0BPacketEntityAction(this.Â.á, C0BPacketEntityAction.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
            if (ModuleManager.HorizonCode_Horizon_È("Sprint").áˆºÑ¢Õ() && (this.Â.á.ÇŽÉ != 0.0 || this.Â.á.ÇŽÕ != 0.0 || this.Â.á.£áƒ != 0.0f) && !ModuleManager.HorizonCode_Horizon_È("Freecam").áˆºÑ¢Õ()) {
                this.Â.á.Â(true);
            }
        }
    }
    
    @Override
    public void á() {
        final KeyBinding œâ = this.Â.ŠÄ.ŒÂ;
        KeyBinding.HorizonCode_Horizon_È(this.Â.ŠÄ.ŒÂ.áŒŠÆ(), false);
        this.Â.á.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C0BPacketEntityAction(this.Â.á, C0BPacketEntityAction.HorizonCode_Horizon_È.Â));
    }
}
