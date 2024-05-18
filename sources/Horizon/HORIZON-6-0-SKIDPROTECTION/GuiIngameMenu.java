package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.awt.TrayIcon;
import org.lwjgl.opengl.Display;

public class GuiIngameMenu extends GuiScreen
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private static final String Ý = "CL_00000703";
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È = 0;
        this.ÇŽÉ.clear();
        final byte var1 = -16;
        final boolean var2 = true;
        if (!Horizon.ÂµÈ) {
            this.ÇŽÉ.add(new GuiMenuButton(1, GuiIngameMenu.Çªà¢ / 2 - 100, GuiIngameMenu.Ê / 4 + 120 + var1, I18n.HorizonCode_Horizon_È("menu.returnToMenu", new Object[0])));
            if (!GuiIngameMenu.Ñ¢á.Ê()) {
                this.ÇŽÉ.get(0).Å = I18n.HorizonCode_Horizon_È("menu.disconnect", new Object[0]);
            }
            this.ÇŽÉ.add(new GuiMenuButton(4, GuiIngameMenu.Çªà¢ / 2 - 100, GuiIngameMenu.Ê / 4 + 24 + var1, I18n.HorizonCode_Horizon_È("menu.returnToGame", new Object[0])));
            this.ÇŽÉ.add(new GuiMenuButton(0, GuiIngameMenu.Çªà¢ / 2 - 100, GuiIngameMenu.Ê / 4 + 96 + var1, 98, 20, I18n.HorizonCode_Horizon_È("menu.options", new Object[0])));
            this.ÇŽÉ.add(new GuiMenuButton(88, GuiIngameMenu.Çªà¢ / 2 - 100, GuiIngameMenu.Ê / 4 + 72 + var1, 200, 20, "Enable GhostMode"));
            final GuiButton var3;
            this.ÇŽÉ.add(var3 = new GuiMenuButton(7, GuiIngameMenu.Çªà¢ / 2 + 2, GuiIngameMenu.Ê / 4 + 96 + var1, 98, 20, I18n.HorizonCode_Horizon_È("menu.shareToLan", new Object[0])));
            this.ÇŽÉ.add(new GuiMenuButton(5, GuiIngameMenu.Çªà¢ / 2 - 100, GuiIngameMenu.Ê / 4 + 48 + var1, 98, 20, I18n.HorizonCode_Horizon_È("gui.achievements", new Object[0])));
            this.ÇŽÉ.add(new GuiMenuButton(6, GuiIngameMenu.Çªà¢ / 2 + 2, GuiIngameMenu.Ê / 4 + 48 + var1, 98, 20, I18n.HorizonCode_Horizon_È("gui.stats", new Object[0])));
            var3.µà = (GuiIngameMenu.Ñ¢á.ÇŽÉ() && !GuiIngameMenu.Ñ¢á.ˆá().ÇŽá());
        }
        else {
            this.ÇŽÉ.add(new GuiButton(1, GuiIngameMenu.Çªà¢ / 2 - 100, GuiIngameMenu.Ê / 4 + 120 + var1, I18n.HorizonCode_Horizon_È("menu.returnToMenu", new Object[0])));
            if (!GuiIngameMenu.Ñ¢á.Ê()) {
                this.ÇŽÉ.get(0).Å = I18n.HorizonCode_Horizon_È("menu.disconnect", new Object[0]);
            }
            this.ÇŽÉ.add(new GuiButton(4, GuiIngameMenu.Çªà¢ / 2 - 100, GuiIngameMenu.Ê / 4 + 24 + var1, I18n.HorizonCode_Horizon_È("menu.returnToGame", new Object[0])));
            this.ÇŽÉ.add(new GuiButton(0, GuiIngameMenu.Çªà¢ / 2 - 100, GuiIngameMenu.Ê / 4 + 96 + var1, 98, 20, I18n.HorizonCode_Horizon_È("menu.options", new Object[0])));
            final GuiButton var3;
            this.ÇŽÉ.add(var3 = new GuiButton(7, GuiIngameMenu.Çªà¢ / 2 + 2, GuiIngameMenu.Ê / 4 + 96 + var1, 98, 20, I18n.HorizonCode_Horizon_È("menu.shareToLan", new Object[0])));
            this.ÇŽÉ.add(new GuiButton(5, GuiIngameMenu.Çªà¢ / 2 - 100, GuiIngameMenu.Ê / 4 + 48 + var1, 98, 20, I18n.HorizonCode_Horizon_È("gui.achievements", new Object[0])));
            this.ÇŽÉ.add(new GuiButton(6, GuiIngameMenu.Çªà¢ / 2 + 2, GuiIngameMenu.Ê / 4 + 48 + var1, 98, 20, I18n.HorizonCode_Horizon_È("gui.stats", new Object[0])));
            var3.µà = (GuiIngameMenu.Ñ¢á.ÇŽÉ() && !GuiIngameMenu.Ñ¢á.ˆá().ÇŽá());
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final GuiButton button) throws IOException {
        switch (button.£à) {
            case 0: {
                GuiIngameMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiOptions(this, GuiIngameMenu.Ñ¢á.ŠÄ));
                break;
            }
            case 1: {
                button.µà = false;
                GuiIngameMenu.Ñ¢á.áŒŠÆ.Ø­áŒŠá();
                GuiIngameMenu.Ñ¢á.HorizonCode_Horizon_È((WorldClient)null);
                GuiIngameMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiMainMenu());
                break;
            }
            case 4: {
                GuiIngameMenu.Ñ¢á.HorizonCode_Horizon_È((GuiScreen)null);
                GuiIngameMenu.Ñ¢á.Å();
                break;
            }
            case 5: {
                GuiIngameMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiAchievements(this, GuiIngameMenu.Ñ¢á.á.c_()));
                break;
            }
            case 6: {
                GuiIngameMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiStats(this, GuiIngameMenu.Ñ¢á.á.c_()));
                break;
            }
            case 7: {
                GuiIngameMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiShareToLan(this));
                break;
            }
            case 88: {
                Horizon.ÂµÈ = true;
                GuiIngameMenu.Ñ¢á.HorizonCode_Horizon_È(new GuiIngameMenu());
                Display.setTitle("Minecraft 1.8");
                GhostTray.HorizonCode_Horizon_È.displayMessage("Horizon Client", "Ghost-Mode Activated", TrayIcon.MessageType.INFO);
                break;
            }
        }
    }
    
    @Override
    public void Ý() {
        super.Ý();
        ++this.Â;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final int mouseX, final int mouseY, final float partialTicks) {
        this.£á();
        this.HorizonCode_Horizon_È(UIFonts.áŒŠÆ, I18n.HorizonCode_Horizon_È("menu.game", new Object[0]), GuiIngameMenu.Çªà¢ / 2 - 7, 30, 16777215);
        super.HorizonCode_Horizon_È(mouseX, mouseY, partialTicks);
    }
}
