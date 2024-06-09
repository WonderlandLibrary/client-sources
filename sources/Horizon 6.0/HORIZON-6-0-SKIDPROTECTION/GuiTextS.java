package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiTextS extends GuiScreen
{
    public String HorizonCode_Horizon_È;
    
    public GuiTextS(final String horizonCode_Horizon_È) {
        this.HorizonCode_Horizon_È = horizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.add(new GuiMenuButton(1, GuiTextS.Çªà¢ / 2 - 95, GuiTextS.Ê / 2 + 30, I18n.HorizonCode_Horizon_È("menu.quit", new Object[0])));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int n, final int n2, final float n3) {
        GuiTextS.Ñ¢á.HorizonCode_Horizon_È(new GuiMainMenu());
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton guiButton) throws IOException {
        switch (guiButton.£à) {
            case 1: {
                GuiTextS.Ñ¢á.£á();
                break;
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char c, final int n) throws IOException {
    }
}
