package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.COMBAT, Ý = -1, Â = "Automaticly throws Pots if you are low life.", HorizonCode_Horizon_È = "AutoPot")
public class AutoPot extends Mod
{
    private int Ý;
    private TimeHelper Ø­áŒŠá;
    
    public AutoPot() {
        this.Ø­áŒŠá = new TimeHelper();
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate ev) {
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            this.Ý = -1;
            if (this.Â.á.Ï­Ä() < 12.0f) {
                if (!this.Ø­áŒŠá.Â(125L)) {
                    return;
                }
                final int oldSlot = this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ý;
                for (int slot = 44; slot >= 9; --slot) {
                    final ItemStack stack = this.Â.á.ŒÂ.HorizonCode_Horizon_È(slot).HorizonCode_Horizon_È();
                    if (stack != null) {
                        if (slot >= 36 && slot <= 44) {
                            if (Item_1028566121.HorizonCode_Horizon_È(stack.HorizonCode_Horizon_È()) == 373 && (stack.à() == 16421 || stack.à() == 16453)) {
                                this.Ý = slot - 36;
                                this.Ø­áŒŠá.Ø­áŒŠá();
                            }
                        }
                        else if (Item_1028566121.HorizonCode_Horizon_È(stack.HorizonCode_Horizon_È()) == 373 && (stack.à() == 16421 || stack.à() == 16453)) {
                            this.Â.Âµá€.HorizonCode_Horizon_È(0, slot, 0, 1, this.Â.á);
                            this.Ø­áŒŠá.Ø­áŒŠá();
                            return;
                        }
                    }
                }
            }
        }
        if (ev.Ý() == EventUpdate.HorizonCode_Horizon_È.Ý && this.Ý != -1) {
            final int oldSlot = this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ý;
            this.Â.µÕ().HorizonCode_Horizon_È(new C09PacketHeldItemChange(this.Ý));
            this.Â.µÕ().HorizonCode_Horizon_È(new C08PacketPlayerBlockPlacement(this.Â.á.ŒÂ.HorizonCode_Horizon_È(this.Ý + 36).HorizonCode_Horizon_È()));
            this.Â.µÕ().HorizonCode_Horizon_È(new C09PacketHeldItemChange(oldSlot));
            this.Ý = -1;
        }
    }
}
