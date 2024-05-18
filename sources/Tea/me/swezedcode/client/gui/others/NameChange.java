package me.swezedcode.client.gui.others;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.Session;
import net.minecraft.util.StringUtils;

public class NameChange extends GuiScreen {

	private GuiTextField hack;
	private ServerData server;
	
	@Override
	public void initGui() {
		this.server = mc.getCurrentServerData();
        this.hack = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, this.width / 2 - 110, this.height / 2 - 10, 220, 20);
        hack.setMaxStringLength(500);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(mc.fontRendererObj, "name hacker exploiter by lemon", this.width / 2, this.height - 10, -1);
		this.drawCenteredString(mc.fontRendererObj, "Type a name then press enter key", this.width / 2, this.height / 2 - 30, -1);
		this.drawCenteredString(mc.fontRendererObj, "(Most servers only allow case changes)", this.width / 2, this.height / 2 - 20, -1);
		this.hack.drawTextBox();
		this.hack.setFocused(true);
	}
    
    @Override
    public void updateScreen() {
        this.hack.updateCursorCounter();
    }
	
	@Override
	public void keyTyped(char typedChar, int key) {
        if(key == Keyboard.KEY_RETURN) {
	        Session s = mc.getSession();
    		if(!StringUtils.isNullOrEmpty(this.hack.getText())) {
    	        mc.theWorld.sendQuittingDisconnectingPacket();
    	        mc.loadWorld((WorldClient)null);
    	        mc.displayGuiScreen(new GuiMainMenu());
    			mc.session = (new Session(this.hack.getText(), s.getPlayerID(), s.getToken(), s.getSessionType().name().toLowerCase()));
        		this.mc.displayGuiScreen(new GuiConnecting(this, this.mc, server));
    		}
        } else {
        	this.hack.textboxKeyTyped(typedChar, key);
        }
        if (key == 1) {
            this.mc.displayGuiScreen(null);
        }
	}
}