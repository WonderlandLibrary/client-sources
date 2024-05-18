package HORIZON-6-0-SKIDPROTECTION;

@ModInfo(Ø­áŒŠá = Category.PLAYER, Ý = -1671646, Â = "Raids automaticly chests.", HorizonCode_Horizon_È = "Stealer")
public class Stealer extends Mod
{
    private TimeHelper Ø­áŒŠá;
    int Ý;
    
    public Stealer() {
        this.Ø­áŒŠá = new TimeHelper();
        this.Ý = 0;
    }
    
    public boolean HorizonCode_Horizon_È(final Container container) {
        boolean temp = true;
        for (int i = 0, slotAmount = (container.Ý.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.HorizonCode_Horizon_È(i).Â()) {
                temp = false;
            }
        }
        return temp;
    }
    
    @Handler
    public void HorizonCode_Horizon_È(final EventUpdate e) {
        final ModuleManager áˆºÏ = Horizon.à¢.áˆºÏ;
        final ESP cp = (ESP)ModuleManager.HorizonCode_Horizon_È(ESP.class);
        if (this.Â.¥Æ instanceof GuiInventory) {
            this.Â.Âµá€.Âµá€();
        }
        if (e.Ý() == EventUpdate.HorizonCode_Horizon_È.HorizonCode_Horizon_È && this.Â.áŒŠÆ != null) {
            if (this.Â.¥Æ instanceof GuiChest) {
                final GuiChest chest = (GuiChest)this.Â.¥Æ;
                if (this.HorizonCode_Horizon_È(this.Â.á.Ï­Ï)) {
                    this.Â.á.ˆà();
                }
                if (GuiChest.HorizonCode_Horizon_È.v_().contains("Where") || GuiChest.HorizonCode_Horizon_È.v_().contains("Shop") || GuiChest.HorizonCode_Horizon_È.v_().contains("go") || GuiChest.HorizonCode_Horizon_È.v_().contains("MiniGame") || GuiChest.HorizonCode_Horizon_È.v_().contains("Wähle") || GuiChest.HorizonCode_Horizon_È.v_().contains("Select") || GuiChest.HorizonCode_Horizon_È.v_().contains("Selector") || GuiChest.HorizonCode_Horizon_È.v_().contains("Menu")) {
                    return;
                }
                final int i = this.Ý;
                while (this.Ý < GuiChest.HorizonCode_Horizon_È.áŒŠÆ()) {
                    if (GuiChest.á.HorizonCode_Horizon_È(this.Ý).Â()) {
                        if (!this.Ø­áŒŠá.Â(40L)) {
                            break;
                        }
                        this.Â.Âµá€.HorizonCode_Horizon_È(GuiChest.á.Ø­áŒŠá, this.Ý, 0, 1, this.Â.á);
                        this.Ø­áŒŠá.Ø­áŒŠá();
                        if (this.HorizonCode_Horizon_È(this.Â.á.Ï­Ï)) {
                            this.Â.á.ˆà();
                            final ModuleManager áˆºÏ2 = Horizon.à¢.áˆºÏ;
                            if (ModuleManager.HorizonCode_Horizon_È(Notifier.class).áˆºÑ¢Õ()) {
                                Horizon.à¢.ÇªÓ.HorizonCode_Horizon_È("Chest cleared!", 1, "random.successfull_hit", 10.0f, 2.0f);
                            }
                        }
                    }
                    ++this.Ý;
                }
            }
            else {
                this.Ý = 0;
            }
        }
    }
}
