package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiErrorScreen extends GuiScreen
{
    private String HorizonCode_Horizon_È;
    private String Â;
    private static final String Ý = "CL_00000696";
    
    public GuiErrorScreen(final String p_i46319_1_, final String p_i46319_2_) {
        this.HorizonCode_Horizon_È = p_i46319_1_;
        this.Â = p_i46319_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        super.HorizonCode_Horizon_È();
        this.ÇŽÉ.add(new GuiButton(0, GuiErrorScreen.Çªà¢ / 2 - 100, 140, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiErrorScreen.Çªà¢, GuiErrorScreen.Ê, -12574688, -11530224);
        this.HorizonCode_Horizon_È(this.É, this.HorizonCode_Horizon_È, GuiErrorScreen.Çªà¢ / 2, 90, 16777215);
        this.HorizonCode_Horizon_È(this.É, this.Â, GuiErrorScreen.Çªà¢ / 2, 110, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        GuiErrorScreen.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
    }
}
