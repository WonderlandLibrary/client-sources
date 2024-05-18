package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiSleepMP extends GuiChat
{
    private static final String Â = "CL_00000697";
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
        this.ÇŽÉ.add(new GuiButton(1, GuiSleepMP.Çªà¢ / 2 - 100, GuiSleepMP.Ê - 40, I18n.HorizonCode_Horizon_È("multiplayer.stopSleeping", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (keyCode == 1) {
            this.à();
        }
        else if (keyCode != 28 && keyCode != 156) {
            super.HorizonCode_Horizon_È(typedChar, keyCode);
        }
        else {
            final String var3 = this.HorizonCode_Horizon_È.Ý().trim();
            if (!var3.isEmpty()) {
                GuiSleepMP.Ñ¢á.á.Â(var3);
            }
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È("");
            GuiSleepMP.Ñ¢á.Šáƒ.Ø­áŒŠá().Âµá€();
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == 1) {
            this.à();
        }
        else {
            super.HorizonCode_Horizon_È(button);
        }
    }
    
    private void à() {
        final NetHandlerPlayClient var1 = GuiSleepMP.Ñ¢á.á.HorizonCode_Horizon_È;
        var1.HorizonCode_Horizon_È(new C0BPacketEntityAction(GuiSleepMP.Ñ¢á.á, C0BPacketEntityAction.HorizonCode_Horizon_È.Ý));
    }
}
