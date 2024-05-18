package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiTestMenu extends GuiScreen
{
    public ParticleEngine HorizonCode_Horizon_È;
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution scaledRes = new ScaledResolution(GuiTestMenu.Ñ¢á, GuiTestMenu.Ñ¢á.Ó, GuiTestMenu.Ñ¢á.à);
        GuiTestMenu.Ñ¢á.¥à().HorizonCode_Horizon_È(new ResourceLocation_1975012498("textures/horizon/gui/bg.png"));
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, 0.0f, 0.0f, scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â(), scaledRes.HorizonCode_Horizon_È(), scaledRes.Â());
        this.HorizonCode_Horizon_È.Â();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È = new ParticleEngine();
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È.Ø­áŒŠá();
        for (int i = 0; i < 1; ++i) {
            this.HorizonCode_Horizon_È.Ý();
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.HorizonCode_Horizon_È.Ý();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        this.HorizonCode_Horizon_È.Ý();
        super.HorizonCode_Horizon_È(typedChar, keyCode);
    }
}
