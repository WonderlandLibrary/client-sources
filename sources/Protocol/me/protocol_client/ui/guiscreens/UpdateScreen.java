package me.protocol_client.ui.guiscreens;

import java.io.IOException;
import java.net.URL;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.utils.MiscUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class UpdateScreen extends GuiScreen{
	URL url;
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		drawDefaultBackground();
		Gui.drawCenteredString(Wrapper.fr(), "Updates are available.", this.width / 2, 1, -1);
		Gui.drawCenteredString(Wrapper.fr(), "Your version: b" + Protocol.version, width / 2, 170, -1);
		Gui.drawCenteredString(Wrapper.fr(), "Current version: " + Protocol.updateManager.newestversionnumber, width / 2, 180, -1);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	public void initGui(){
		this.buttonList.add(new GuiButton(420, width / 2 - 100, 120, "Download b" + Protocol.updateManager.newestversionnumber));
		this.buttonList.add(new GuiButton(911, width / 2 - 100, 145, "fuck you I like this version"));
		this.updateScreen();
	}
	protected void actionPerformed(GuiButton button) throws IOException{
		if(button.id == 420){
			MiscUtils.openLink("http://HypnoHacks.weebly.com/downloads.html");
		}
		if(button.id == 911){
			mc.displayGuiScreen(new GuiMainMenu());
		}
	}

}
