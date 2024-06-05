package Ascii4UwUWareClient.API.Auth;

import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Modules.Render.HUD;
import Ascii4UwUWareClient.Util.MainMenuUtil;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.*;

public class GuiBanned extends GuiScreen {

    public void initGui() {
        super.initGui();
    }
    boolean full;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        if (!full){
            mc.toggleFullscreen();
            full = true;
        }

        MainMenuUtil.drawString("You were Banned from UwUWare Client", width/2 - this.fontRendererObj.getStringWidth("You were Banned from UwUWare Client")/10, height/3, new Color(253, 0, 0, 255).getRGB());
            try {
                String banReason;
                banReason = Auth.getBanReason().replace('+', ' ');
                MainMenuUtil.drawString("Reason: " + banReason, width/2 - this.fontRendererObj.getStringWidth(Auth.getBanReason())/10, height/2, HUD.astofloc(1));
            }catch (Exception e){

            }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
