package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import org.lwjgl.input.Keyboard;

public class GuiRenameWorld extends GuiScreen
{
    private GuiScreen HorizonCode_Horizon_È;
    private GuiTextField Â;
    private final String Ý;
    private static final String Ø­áŒŠá = "CL_00000709";
    
    public GuiRenameWorld(final GuiScreen p_i46317_1_, final String p_i46317_2_) {
        this.HorizonCode_Horizon_È = p_i46317_1_;
        this.Ý = p_i46317_2_;
    }
    
    @Override
    public void Ý() {
        this.Â.Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        Keyboard.enableRepeatEvents(true);
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(new GuiButton(0, GuiRenameWorld.Çªà¢ / 2 - 100, GuiRenameWorld.Ê / 4 + 96 + 12, I18n.HorizonCode_Horizon_È("selectWorld.renameButton", new Object[0])));
        this.ÇŽÉ.add(new GuiButton(1, GuiRenameWorld.Çªà¢ / 2 - 100, GuiRenameWorld.Ê / 4 + 120 + 12, I18n.HorizonCode_Horizon_È("gui.cancel", new Object[0])));
        final ISaveFormat var1 = GuiRenameWorld.Ñ¢á.à();
        final WorldInfo var2 = var1.Ý(this.Ý);
        final String var3 = var2.áˆºÑ¢Õ();
        (this.Â = new GuiTextField(2, this.É, GuiRenameWorld.Çªà¢ / 2 - 100, 60, 200, 20)).Â(true);
        this.Â.HorizonCode_Horizon_È(var3);
    }
    
    @Override
    public void q_() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.µà) {
            if (button.£à == 1) {
                GuiRenameWorld.Ñ¢á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
            }
            else if (button.£à == 0) {
                final ISaveFormat var2 = GuiRenameWorld.Ñ¢á.à();
                var2.HorizonCode_Horizon_È(this.Ý, this.Â.Ý().trim());
                GuiRenameWorld.Ñ¢á.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
            }
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
        this.Â.HorizonCode_Horizon_È(typedChar, keyCode);
        this.ÇŽÉ.get(0).µà = (this.Â.Ý().trim().length() > 0);
        if (keyCode == 28 || keyCode == 156) {
            this.HorizonCode_Horizon_È(this.ÇŽÉ.get(0));
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
        this.Â.HorizonCode_Horizon_È(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("selectWorld.renameTitle", new Object[0]), GuiRenameWorld.Çªà¢ / 2, 20, 16777215);
        Gui_1808253012.Â(this.É, I18n.HorizonCode_Horizon_È("selectWorld.enterName", new Object[0]), GuiRenameWorld.Çªà¢ / 2 - 100, 47, 10526880);
        this.Â.HorizonCode_Horizon_È();
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
