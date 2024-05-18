package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.input.Keyboard;

public class GuiScreenServerList extends GuiScreen
{
    private final GuiScreen HorizonCode_Horizon_È;
    private final ServerData Â;
    private GuiTextField Ý;
    private static final String Ø­áŒŠá = "CL_00000692";
    
    public GuiScreenServerList(final GuiScreen p_i1031_1_, final ServerData p_i1031_2_) {
        this.HorizonCode_Horizon_È = p_i1031_1_;
        this.Â = p_i1031_2_;
    }
    
    @Override
    public void Ý() {
        this.Ý.Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Keyboard.enableRepeatEvents(true);
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(new GuiButton(0, GuiScreenServerList.Çªà¢ / 2 - 100, GuiScreenServerList.Ê / 4 + 96 + 12, I18n.HorizonCode_Horizon_È("selectServer.select", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(1, GuiScreenServerList.Çªà¢ / 2 - 100, GuiScreenServerList.Ê / 4 + 120 + 12, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        (this.Ý = new GuiTextField(2, this.É, GuiScreenServerList.Çªà¢ / 2 - 100, 116, 200, 20)).Ó(128);
        this.Ý.Â(true);
        this.Ý.HorizonCode_Horizon_È(GuiScreenServerList.Ñ¢á.ŠÄ.ˆÂ);
        this.ÇŽÉ.get(0).µà = (this.Ý.Ý().length() > 0 && this.Ý.Ý().split(":").length > 0);
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
        GuiScreenServerList.Ñ¢á.ŠÄ.ˆÂ = this.Ý.Ý();
        GuiScreenServerList.Ñ¢á.ŠÄ.Â();
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 1) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(false, 0);
            }
            else if (button.£à == 0) {
                this.Â.Â = this.Ý.Ý();
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(true, 0);
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        if (this.Ý.HorizonCode_Horizon_È(typedChar, keyCode)) {
            this.ÇŽÉ.get(0).µà = (this.Ý.Ý().length() > 0 && this.Ý.Ý().split(":").length > 0);
        }
        else if (keyCode == 28 || keyCode == 156) {
            this.HorizonCode_Horizon_È(this.ÇŽÉ.get(0));
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        this.Ý.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("selectServer.direct", new Object[0]), GuiScreenServerList.Çªà¢ / 2, 20, 16777215);
        Gui_1808253012.Â(this.É, I18n.HorizonCode_Horizon_È("addServer.enterIp", new Object[0]), GuiScreenServerList.Çªà¢ / 2 - 100, 100, 10526880);
        this.Ý.HorizonCode_Horizon_È();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
