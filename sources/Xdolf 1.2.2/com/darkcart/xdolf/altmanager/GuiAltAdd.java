package com.darkcart.xdolf.altmanager;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Wrapper;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiAltAdd extends GuiScreen {

	private GuiScreen previousScreen;
	
	public GuiTextField usernameBox;
	public GuiPasswordField passwordBox;

	public GuiAltAdd(GuiScreen previousScreen) {
		this.previousScreen = previousScreen;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 96 + 12, "Add"));
		buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 96 + 36, "Back"));
		usernameBox = new GuiTextField(0, this.fontRendererObj, width / 2 - 100, 76, 200, 20);
		passwordBox = new GuiPasswordField(2, this.fontRendererObj, width / 2 - 100, 116, 200, 20);
		usernameBox.setMaxStringLength(200);
		passwordBox.setMaxStringLength(128);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawString(this.fontRendererObj, "Username", width / 2 - 100, 63, 0xa0a0a0);
		drawString(this.fontRendererObj, "Password", width / 2 - 100, 104, 0xa0a0a0);
		try{
			usernameBox.drawTextBox();
			passwordBox.drawTextBox();
		} catch(Exception err) {
			err.printStackTrace();
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		usernameBox.mouseClicked(mouseX, mouseY, mouseButton);
		passwordBox.mouseClicked(mouseX, mouseY, mouseButton);
		try {
			super.mouseClicked(mouseX, mouseY, mouseButton);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		usernameBox.textboxKeyTyped(typedChar, keyCode);
		passwordBox.textboxKeyTyped(typedChar, keyCode);
		if(typedChar == '\t') {
			if(usernameBox.isFocused()) {
				usernameBox.setFocused(false);
				passwordBox.setFocused(true);
			} else {
				usernameBox.setFocused(true);
				passwordBox.setFocused(false);
			}
		}
		if(typedChar == '\r') {
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if(button.id == 1) {
				if(!usernameBox.getText().trim().isEmpty()) {
					if(passwordBox.getText().trim().isEmpty()) {
						Alt theAlt = new Alt(usernameBox.getText().trim());
						if(!Manager.altList.contains(theAlt)) {
							Manager.altList.add(theAlt);
							Wrapper.getFileManager().saveAlts();
						}
					} else {
						Alt theAlt = new Alt(usernameBox.getText().trim(), passwordBox.getText().trim());
						if(!Manager.altList.contains(theAlt)) {
							Manager.altList.add(theAlt);
							Wrapper.getFileManager().saveAlts();
						}
					}
				}
				Client.mc.displayGuiScreen(previousScreen);
			} else if(button.id == 2) {
				Client.mc.displayGuiScreen(previousScreen);
			}
		}
	}

	public void updateScreen() {
		usernameBox.updateCursorCounter();
		passwordBox.updateCursorCounter();
	}
}
