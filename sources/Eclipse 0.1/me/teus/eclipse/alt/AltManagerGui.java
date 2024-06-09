package me.teus.eclipse.alt;

import me.teus.eclipse.alt.microsoft.GuiLoginMicrosoft;
import me.teus.eclipse.utils.MicrosoftAuthenticator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class AltManagerGui extends GuiScreen {

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, width / 2 + 4 + 50, height - 24, 100, 20, "Cancel"));
        this.buttonList.add(new GuiButton(1, width / 2 + 4 + 50, height - 48, 100, 20, "Use Cracked"));
        this.buttonList.add(new GuiButton(2, width / 2 - 50, height - 48, 100, 20, "Use Microsoft"));
        this.buttonList.add(new GuiButton(3, width / 2 - 150 - 4, height - 48, 100, 20, "Use Microsoft Browser"));
        this.buttonList.add(new GuiButton(4, width / 2 - 50, height - 24, 100, 20, "Coming Soon..."));
        this.buttonList.add(new GuiButton(5, width / 2 - 150 - 4, height - 24, 100, 20, "Coming Soon..."));
        super.initGui();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(new GuiMainMenu());
        }
        if(button.id == 1){
            mc.displayGuiScreen(new GuiLogin());
        }
        if(button.id == 2){
            mc.displayGuiScreen(new GuiLoginMicrosoft());
        }
        if(button.id == 3){
            MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator("c388a0b6-da2e-4a0e-9c68-d825e61eafa6", "LD68Q~dlbtd.e3qFdJlwUANJbPPJnh7WkDBQwb.2");
            authenticator.login();
        }
    }
}
