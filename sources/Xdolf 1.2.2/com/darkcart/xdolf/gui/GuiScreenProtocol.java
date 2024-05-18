package com.darkcart.xdolf.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiScreenProtocol extends GuiScreen {

	private GuiTextField protocol;
	private GuiScreen previousScreen;

	public static int proto = 316;

	public GuiScreenProtocol(GuiScreen previousScreen) {
		this.previousScreen = previousScreen;
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Select"));
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
		this.protocol = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
		this.protocol.setMaxStringLength(128);
		this.protocol.setFocused(true);
		this.protocol.setText(String.valueOf(proto));
		((GuiButton) this.buttonList.get(0)).enabled = !this.protocol.getText().isEmpty();
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		proto = Integer.parseInt(protocol.getText());
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Change Protocol", this.width / 2, 20, 16777215);
		this.drawString(this.fontRendererObj, "Protocol", this.width / 2 - 100, 100, 10526880);
		protocol.drawTextBox();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
        this.protocol.mouseClicked(mouseX, mouseY, mouseButton);
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (this.protocol.textboxKeyTyped(typedChar, keyCode)) {
			((GuiButton) this.buttonList.get(0)).enabled = !this.protocol.getText().isEmpty();
		} else if (keyCode == 28 || keyCode == 156) {
			this.actionPerformed((GuiButton) this.buttonList.get(0));
		}
	}

	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			if (button.id == 0) {
				proto = Integer.parseInt(protocol.getText());
				Client.mc.displayGuiScreen(previousScreen);
			} else {
				Client.mc.displayGuiScreen(previousScreen);
			}
		}
	}

	public void updateScreen() {
		this.protocol.updateCursorCounter();
	}
}
