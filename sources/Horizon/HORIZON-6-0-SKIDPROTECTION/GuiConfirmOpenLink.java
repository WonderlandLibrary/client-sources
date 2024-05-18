package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiConfirmOpenLink extends GuiYesNo
{
    private final String Ó;
    private final String à;
    private final String Ø;
    private boolean áŒŠÆ;
    private static final String áˆºÑ¢Õ = "CL_00000683";
    
    public GuiConfirmOpenLink(final GuiYesNoCallback p_i1084_1_, final String p_i1084_2_, final int p_i1084_3_, final boolean p_i1084_4_) {
        super(p_i1084_1_, I18n.HorizonCode_Horizon_È(p_i1084_4_ ? "chat.link.confirmTrusted" : "chat.link.confirm", new Object[0]), p_i1084_2_, p_i1084_3_);
        this.áŒŠÆ = true;
        this.Ý = I18n.HorizonCode_Horizon_È(p_i1084_4_ ? "chat.link.open" : "gui.yes", new Object[0]);
        this.Ø­áŒŠá = I18n.HorizonCode_Horizon_È(p_i1084_4_ ? "gui.cancel" : "gui.no", new Object[0]);
        this.à = I18n.HorizonCode_Horizon_È("chat.copy", new Object[0]);
        this.Ó = I18n.HorizonCode_Horizon_È("chat.link.warning", new Object[0]);
        this.Ø = p_i1084_2_;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.add(new GuiButton(0, GuiConfirmOpenLink.Çªà¢ / 2 - 50 - 105, GuiConfirmOpenLink.Ê / 6 + 96, 100, 20, this.Ý));
        this.ÇŽÉ.add(new GuiButton(2, GuiConfirmOpenLink.Çªà¢ / 2 - 50, GuiConfirmOpenLink.Ê / 6 + 96, 100, 20, this.à));
        this.ÇŽÉ.add(new GuiButton(1, GuiConfirmOpenLink.Çªà¢ / 2 - 50 + 105, GuiConfirmOpenLink.Ê / 6 + 96, 100, 20, this.Ø­áŒŠá));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == 2) {
            this.Ó();
        }
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(button.£à == 0, this.Âµá€);
    }
    
    public void Ó() {
        GuiScreen.Ø­áŒŠá(this.Ø);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
        if (this.áŒŠÆ) {
            this.HorizonCode_Horizon_È(this.É, this.Ó, GuiConfirmOpenLink.Çªà¢ / 2, 110, 16764108);
        }
    }
    
    public void à() {
        this.áŒŠÆ = false;
    }
}
