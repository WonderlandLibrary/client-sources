package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiDownloadTerrain extends GuiScreen
{
    private NetHandlerPlayClient HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00000708";
    
    public GuiDownloadTerrain(final NetHandlerPlayClient p_i45023_1_) {
        this.HorizonCode_Horizon_È = p_i45023_1_;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
    }
    
    @Override
    public void Ý() {
        ++this.Â;
        if (this.Â % 20 == 0) {
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(new C00PacketKeepAlive());
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.Ý(0);
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("multiplayer.downloadingTerrain", new Object[0]), GuiDownloadTerrain.Çªà¢ / 2, GuiDownloadTerrain.Ê / 2 - 50, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return false;
    }
}
