package Ascii4UwUWareClient.API.Updater;

import Ascii4UwUWareClient.Util.MainMenuUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;

public class GuiOutdated extends GuiScreen {

    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 30, "Updated Client to latest version"));
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();


        MainMenuUtil.drawString("Your Client Version is outdated", width/2, height/3, new Color(253, 0, 0, 255).getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        //TODO: Update Client
        Update.downloadAndReplaceJar();
        super.actionPerformed(button);
    }
}