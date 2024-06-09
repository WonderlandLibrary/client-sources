package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.input.Mouse;

public class GuiDashboard extends GuiScreen
{
    private int HorizonCode_Horizon_È;
    private static final ResourceLocation_1975012498 Â;
    
    static {
        Â = new ResourceLocation_1975012498("textures/horizon/gui/bg.png");
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution scaledRes = new ScaledResolution(GuiDashboard.Ñ¢á, GuiDashboard.Ñ¢á.Ó, GuiDashboard.Ñ¢á.à);
        GuiDashboard.Ñ¢á.¥à().HorizonCode_Horizon_È(GuiDashboard.Â);
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
        if (Mouse.hasWheel()) {
            final int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                this.HorizonCode_Horizon_È += 26;
                if (this.HorizonCode_Horizon_È < 0) {
                    this.HorizonCode_Horizon_È = 0;
                }
                if (this.HorizonCode_Horizon_È > 320) {
                    this.HorizonCode_Horizon_È = 320;
                }
            }
            else if (wheel > 0) {
                this.HorizonCode_Horizon_È -= 26;
                if (this.HorizonCode_Horizon_È < 0) {
                    this.HorizonCode_Horizon_È = 0;
                }
                if (this.HorizonCode_Horizon_È > 320) {
                    this.HorizonCode_Horizon_È = 320;
                }
            }
        }
        Gui_1808253012.HorizonCode_Horizon_È(0, 0 - this.HorizonCode_Horizon_È, 140, 34 - this.HorizonCode_Horizon_È, 1073741824);
        Gui_1808253012.HorizonCode_Horizon_È(0, 35 - this.HorizonCode_Horizon_È, 140, 164 - this.HorizonCode_Horizon_È, 1610612736);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
