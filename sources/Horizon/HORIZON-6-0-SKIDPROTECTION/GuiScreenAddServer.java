package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.input.Keyboard;

public class GuiScreenAddServer extends GuiScreen
{
    private final GuiScreen HorizonCode_Horizon_È;
    private final ServerData Â;
    private GuiTextField Ý;
    private GuiTextField Ø­áŒŠá;
    private GuiButton Âµá€;
    private static final String Ó = "CL_00000695";
    
    public GuiScreenAddServer(final GuiScreen p_i1033_1_, final ServerData p_i1033_2_) {
        this.HorizonCode_Horizon_È = p_i1033_1_;
        this.Â = p_i1033_2_;
    }
    
    @Override
    public void Ý() {
        this.Ø­áŒŠá.Â();
        this.Ý.Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Keyboard.enableRepeatEvents(true);
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(new GuiButton(0, GuiScreenAddServer.Çªà¢ / 2 - 100, GuiScreenAddServer.Ê / 4 + 96 + 18, I18n.HorizonCode_Horizon_È("addServer.add", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(1, GuiScreenAddServer.Çªà¢ / 2 - 100, GuiScreenAddServer.Ê / 4 + 120 + 18, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        this.ÇŽÉ.add(this.Âµá€ = new GuiButton(2, GuiScreenAddServer.Çªà¢ / 2 - 100, GuiScreenAddServer.Ê / 4 + 72, String.valueOf(I18n.HorizonCode_Horizon_È("addServer.resourcePack", new Object[0])) + ": " + this.Â.Â().HorizonCode_Horizon_È().áŒŠÆ()));
        (this.Ø­áŒŠá = new GuiTextField(0, this.É, GuiScreenAddServer.Çªà¢ / 2 - 100, 66, 200, 20)).Â(true);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Â.HorizonCode_Horizon_È);
        (this.Ý = new GuiTextField(1, this.É, GuiScreenAddServer.Çªà¢ / 2 - 100, 106, 200, 20)).Ó(128);
        this.Ý.HorizonCode_Horizon_È(this.Â.Â);
        this.ÇŽÉ.get(0).µà = (this.Ý.Ý().length() > 0 && this.Ý.Ý().split(":").length > 0 && this.Ø­áŒŠá.Ý().length() > 0);
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 2) {
                this.Â.HorizonCode_Horizon_È(ServerData.HorizonCode_Horizon_È.values()[(this.Â.Â().ordinal() + 1) % ServerData.HorizonCode_Horizon_È.values().length]);
                this.Âµá€.Å = String.valueOf(I18n.HorizonCode_Horizon_È("addServer.resourcePack", new Object[0])) + ": " + this.Â.Â().HorizonCode_Horizon_È().áŒŠÆ();
            }
            else if (button.£à == 1) {
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(false, 0);
            }
            else if (button.£à == 0) {
                this.Â.HorizonCode_Horizon_È = this.Ø­áŒŠá.Ý();
                this.Â.Â = this.Ý.Ý();
                this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(true, 0);
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        this.Ø­áŒŠá.HorizonCode_Horizon_È(typedChar, keyCode);
        this.Ý.HorizonCode_Horizon_È(typedChar, keyCode);
        if (keyCode == 15) {
            this.Ø­áŒŠá.Â(!this.Ø­áŒŠá.ÂµÈ());
            this.Ý.Â(!this.Ý.ÂµÈ());
        }
        if (keyCode == 28 || keyCode == 156) {
            this.HorizonCode_Horizon_È(this.ÇŽÉ.get(0));
        }
        this.ÇŽÉ.get(0).µà = (this.Ý.Ý().length() > 0 && this.Ý.Ý().split(":").length > 0 && this.Ø­áŒŠá.Ý().length() > 0);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        this.Ý.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("addServer.title", new Object[0]), GuiScreenAddServer.Çªà¢ / 2, 17, 16777215);
        Gui_1808253012.Â(this.É, I18n.HorizonCode_Horizon_È("addServer.enterName", new Object[0]), GuiScreenAddServer.Çªà¢ / 2 - 100, 53, 10526880);
        Gui_1808253012.Â(this.É, I18n.HorizonCode_Horizon_È("addServer.enterIp", new Object[0]), GuiScreenAddServer.Çªà¢ / 2 - 100, 94, 10526880);
        this.Ø­áŒŠá.HorizonCode_Horizon_È();
        this.Ý.HorizonCode_Horizon_È();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
