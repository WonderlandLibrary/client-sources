package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;

public class GuiMemoryErrorScreen extends GuiScreen
{
    private static final String HorizonCode_Horizon_È = "CL_00000702";
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        this.ÇŽÉ.add(new GuiOptionButton(0, GuiMemoryErrorScreen.Çªà¢ / 2 - 155, GuiMemoryErrorScreen.Ê / 4 + 120 + 12, I18n.HorizonCode_Horizon_È("gui.toMenu", new Object[0])));
        this.ÇŽÉ.add(new GuiOptionButton(1, GuiMemoryErrorScreen.Çªà¢ / 2 - 155 + 160, GuiMemoryErrorScreen.Ê / 4 + 120 + 12, I18n.HorizonCode_Horizon_È("menu.quit", new Object[0])));
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        if (button.£à == 0) {
            GuiMemoryErrorScreen.Ñ¢á.HorizonCode_Horizon_È(new GuiMainMenu());
        }
        else if (button.£à == 1) {
            GuiMemoryErrorScreen.Ñ¢á.£á();
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.HorizonCode_Horizon_È(this.É, "Out of memory!", GuiMemoryErrorScreen.Çªà¢ / 2, GuiMemoryErrorScreen.Ê / 4 - 60 + 20, 16777215);
        Gui_1808253012.Â(this.É, "Minecraft has run out of memory.", GuiMemoryErrorScreen.Çªà¢ / 2 - 140, GuiMemoryErrorScreen.Ê / 4 - 60 + 60 + 0, 10526880);
        Gui_1808253012.Â(this.É, "This could be caused by a bug in the game or by the", GuiMemoryErrorScreen.Çªà¢ / 2 - 140, GuiMemoryErrorScreen.Ê / 4 - 60 + 60 + 18, 10526880);
        Gui_1808253012.Â(this.É, "Java Virtual Machine not being allocated enough", GuiMemoryErrorScreen.Çªà¢ / 2 - 140, GuiMemoryErrorScreen.Ê / 4 - 60 + 60 + 27, 10526880);
        Gui_1808253012.Â(this.É, "memory.", GuiMemoryErrorScreen.Çªà¢ / 2 - 140, GuiMemoryErrorScreen.Ê / 4 - 60 + 60 + 36, 10526880);
        Gui_1808253012.Â(this.É, "To prevent level corruption, the current game has quit.", GuiMemoryErrorScreen.Çªà¢ / 2 - 140, GuiMemoryErrorScreen.Ê / 4 - 60 + 60 + 54, 10526880);
        Gui_1808253012.Â(this.É, "We've tried to free up enough memory to let you go back to", GuiMemoryErrorScreen.Çªà¢ / 2 - 140, GuiMemoryErrorScreen.Ê / 4 - 60 + 60 + 63, 10526880);
        Gui_1808253012.Â(this.É, "the main menu and back to playing, but this may not have worked.", GuiMemoryErrorScreen.Çªà¢ / 2 - 140, GuiMemoryErrorScreen.Ê / 4 - 60 + 60 + 72, 10526880);
        Gui_1808253012.Â(this.É, "Please restart the game if you see this message again.", GuiMemoryErrorScreen.Çªà¢ / 2 - 140, GuiMemoryErrorScreen.Ê / 4 - 60 + 60 + 81, 10526880);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
