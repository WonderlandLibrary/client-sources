package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.io.IOException;
import java.util.List;

public class GuiDisconnected extends GuiScreen
{
    private String HorizonCode_Horizon_È;
    private IChatComponent Â;
    private List Ý;
    private final GuiScreen Ø­áŒŠá;
    private int Âµá€;
    private static final ResourceLocation_1975012498 Ó;
    private static final String à = "CL_00000693";
    
    static {
        Ó = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
    }
    
    public GuiDisconnected(final GuiScreen p_i45020_1_, final String p_i45020_2_, final IChatComponent p_i45020_3_) {
        this.Ø­áŒŠá = p_i45020_1_;
        this.HorizonCode_Horizon_È = I18n.HorizonCode_Horizon_È(p_i45020_2_, new Object[0]);
        this.Â = p_i45020_3_;
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        this.Ý = this.É.Ý(this.Â.áŒŠÆ(), GuiDisconnected.Çªà¢ - 50);
        this.Âµá€ = this.Ý.size() * this.É.HorizonCode_Horizon_È;
        this.ÇŽÉ.add(new GuiMainMenuButton(0, GuiDisconnected.Çªà¢ / 2 - 100, GuiDisconnected.Ê / 2 + 55, 200, 20, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        this.ÇŽÉ.add(new GuiMainMenuButton(1, GuiDisconnected.Çªà¢ / 2 - 100, GuiDisconnected.Ê / 2 + 80, 200, 20, "Reconnect"));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == 0) {
            GuiDisconnected.Ñ¢á.HorizonCode_Horizon_È(new GuiMultiplayer(new GuiMainMenu()));
        }
        if (button.£à == 1) {
            GuiDisconnected.Ñ¢á.HorizonCode_Horizon_È(new GuiConnecting(this, GuiDisconnected.Ñ¢á, GuiMultiplayer.Â));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution scaledRes = new ScaledResolution(GuiDisconnected.Ñ¢á, GuiDisconnected.Ñ¢á.Ó, GuiDisconnected.Ñ¢á.à);
        GuiDisconnected.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiDisconnected.Ó);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
        this.HorizonCode_Horizon_È(UIFonts.Ý, this.HorizonCode_Horizon_È, GuiDisconnected.Çªà¢ / 2, 10, 11184810);
        int var4 = GuiDisconnected.Ê / 2 - this.Âµá€ / 2 - 60;
        if (this.Ý != null) {
            for (final String var6 : this.Ý) {
                this.HorizonCode_Horizon_È(UIFonts.Ý, StringUtils.HorizonCode_Horizon_È(var6), GuiDisconnected.Çªà¢ / 2, var4, -1249039);
                var4 += this.É.HorizonCode_Horizon_È;
            }
        }
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
