package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.MINIGAMES, Ý = 0, Â = "Hax 4 RCPigRacing on Hive.", HorizonCode_Horizon_È = "RCPigRacing")
public class RcPigRacing extends Mod
{
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate e) {
        for (int inventorySlot = 0; inventorySlot < 9; ++inventorySlot) {
            if (this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.á(inventorySlot) != null) {
                final Item_1028566121 emerald = this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.á(inventorySlot).HorizonCode_Horizon_È();
                if (emerald != null && emerald == Items.µ) {
                    this.Â.á.Ø­Ñ¢Ï­Ø­áˆº.Ý = inventorySlot;
                }
            }
        }
    }
}
