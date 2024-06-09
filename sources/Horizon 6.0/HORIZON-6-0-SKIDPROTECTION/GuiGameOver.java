package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.Iterator;

public class GuiGameOver extends GuiScreen implements GuiYesNoCallback
{
    private int HorizonCode_Horizon_È;
    private boolean Â;
    private static final String Ý = "CL_00000690";
    
    public GuiGameOver() {
        this.Â = false;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.ÇŽÉ.clear();
        if (GuiGameOver.Ñ¢á.áŒŠÆ.ŒÏ().¥Æ()) {
            if (GuiGameOver.Ñ¢á.Ê()) {
                this.ÇŽÉ.add(new GuiButton(1, GuiGameOver.Çªà¢ / 2 - 100, GuiGameOver.Ê / 4 + 96, I18n.HorizonCode_Horizon_È("deathScreen.deleteWorld", new Object[0])));
            }
            else {
                this.ÇŽÉ.add(new GuiButton(1, GuiGameOver.Çªà¢ / 2 - 100, GuiGameOver.Ê / 4 + 96, I18n.HorizonCode_Horizon_È("deathScreen.leaveServer", new Object[0])));
            }
        }
        else {
            this.ÇŽÉ.add(new GuiButton(0, GuiGameOver.Çªà¢ / 2 - 100, GuiGameOver.Ê / 4 + 72, I18n.HorizonCode_Horizon_È("deathScreen.respawn", new Object[0])));
            this.ÇŽÉ.add(new GuiButton(1, GuiGameOver.Çªà¢ / 2 - 100, GuiGameOver.Ê / 4 + 96, I18n.HorizonCode_Horizon_È("deathScreen.titleScreen", new Object[0])));
            if (GuiGameOver.Ñ¢á.Õ() == null) {
                this.ÇŽÉ.get(1).µà = false;
            }
        }
        for (final GuiButton var2 : this.ÇŽÉ) {
            var2.µà = false;
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final char typedChar, final int keyCode) throws IOException {
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        switch (button.£à) {
            case 0: {
                GuiGameOver.Ñ¢á.á.µà();
                GuiGameOver.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
                break;
            }
            case 1: {
                final GuiYesNo var2 = new GuiYesNo(this, I18n.HorizonCode_Horizon_È("deathScreen.quit.confirm", new Object[0]), "", I18n.HorizonCode_Horizon_È("deathScreen.titleScreen", new Object[0]), I18n.HorizonCode_Horizon_È("deathScreen.respawn", new Object[0]), 0);
                GuiGameOver.Ñ¢á.HorizonCode_Horizon_È(var2);
                var2.HorizonCode_Horizon_È(20);
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean result, final int id) {
        if (result) {
            GuiGameOver.Ñ¢á.áŒŠÆ.Ø­áŒŠá();
            GuiGameOver.Ñ¢á.HorizonCode_Horizon_È((WorldClient)null);
            GuiGameOver.Ñ¢á.HorizonCode_Horizon_È(new GuiMainMenu());
        }
        else {
            GuiGameOver.Ñ¢á.á.µà();
            GuiGameOver.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        Gui_1808253012.HorizonCode_Horizon_È(0, 0, GuiGameOver.Çªà¢, GuiGameOver.Ê, 1615855616, -1602211792);
        GlStateManager.Çªà¢();
        GlStateManager.HorizonCode_Horizon_È(2.0f, 2.0f, 2.0f);
        final boolean var4 = GuiGameOver.Ñ¢á.áŒŠÆ.ŒÏ().¥Æ();
        final String var5 = var4 ? I18n.HorizonCode_Horizon_È("deathScreen.title.hardcore", new Object[0]) : I18n.HorizonCode_Horizon_È("deathScreen.title", new Object[0]);
        this.HorizonCode_Horizon_È(this.É, var5, GuiGameOver.Çªà¢ / 2 / 2, 30, 16777215);
        GlStateManager.Ê();
        if (var4) {
            this.HorizonCode_Horizon_È(this.É, I18n.HorizonCode_Horizon_È("deathScreen.hardcoreInfo", new Object[0]), GuiGameOver.Çªà¢ / 2, 144, 16777215);
        }
        this.HorizonCode_Horizon_È(this.É, String.valueOf(I18n.HorizonCode_Horizon_È("deathScreen.score", new Object[0])) + ": " + EnumChatFormatting.Å + GuiGameOver.Ñ¢á.á.áŒŠÕ(), GuiGameOver.Çªà¢ / 2, 100, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return false;
    }
    
    @Override
    public void Ý() {
        super.Ý();
        ++this.HorizonCode_Horizon_È;
        if (this.HorizonCode_Horizon_È == 20) {
            for (final GuiButton var2 : this.ÇŽÉ) {
                var2.µà = true;
            }
        }
    }
}
