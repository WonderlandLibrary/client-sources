package me.swezedcode.client.gui.alts;


import java.io.IOException;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.gui.other.CustomButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;


public class GuiAltAdd extends GuiScreen {
	public GuiScreen parent;
	public GuiTextField usernameBox;
	public GuiPasswordField passwordBox;
	
	public GuiAltAdd(GuiScreen paramScreen) {
		this.parent = paramScreen;
	}
	
	public void initGui() {
		Keyboard.enableRepeatEvents(true);
		buttonList.add(new CustomButton(1, width / 2 - 100, height / 4 + 96 + 12, "Add"));
		buttonList.add(new CustomButton(2, width / 2 - 100, height / 4 + 96 + 36, "Back"));
		usernameBox = new GuiTextField(3, fontRendererObj, width / 2 - 100, 76, 200, 20);
		passwordBox = new GuiPasswordField(fontRendererObj, width / 2 - 100, 116, 200, 20);
		usernameBox.setMaxStringLength(200);
		passwordBox.setMaxStringLength(128);
	}
	
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	public void updateScreen() {
		usernameBox.updateCursorCounter();
		passwordBox.updateCursorCounter();
	}
	
	public void mouseClicked(int x, int y, int b) {
		usernameBox.mouseClicked(x, y, b);
		passwordBox.mouseClicked(x, y, b);
		
		try {
			super.mouseClicked(x, y, b);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(GuiButton button) {
		if(button.id == 1) {
			if(!usernameBox.getText().trim().isEmpty()) {
				if(passwordBox.getText().trim().isEmpty()) {
					Alt theAlt = new Alt(usernameBox.getText().trim());
					if(!Manager.altList.contains(theAlt)) {
						Manager.altList.add(theAlt);
						me.swezedcode.client.manager.Manager.getManager().getFileManager().saveAlts();
					}
				} else {
					Alt theAlt = new Alt(usernameBox.getText().trim(), passwordBox.getText().trim());
					if(!Manager.altList.contains(theAlt)) {
						Manager.altList.add(theAlt);
						me.swezedcode.client.manager.Manager.getManager().getFileManager().saveAlts();
					}
				}
			}
			mc.displayGuiScreen(parent);
		} else if(button.id == 2) {
			mc.displayGuiScreen(parent);
		}
	}
	
	protected void keyTyped(char c, int i) {
		usernameBox.textboxKeyTyped(c, i);
		passwordBox.textboxKeyTyped(c, i);
		if(c == '\t') {
			if(usernameBox.isFocused()) {
				usernameBox.setFocused(false);
				passwordBox.setFocused(true);
			} else {
				usernameBox.setFocused(true);
				passwordBox.setFocused(false);
			}
		}
		if(c == '\r') {
			actionPerformed((GuiButton) buttonList.get(0));
		}
	}
	
	public void drawScreen(int x, int y, float f) {
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/background/index.jpg"));
		drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight());
		drawString(fontRendererObj, "Username", width / 2 - 100, 63, 0xa0a0a0);
		drawString(fontRendererObj, "Password", width / 2 - 100, 104, 0xa0a0a0);
		try{
			usernameBox.drawTextBox();
			passwordBox.drawTextBox();
		} catch(Exception err) {
			err.printStackTrace();
		}
		super.drawScreen(x, y, f);
	}
}
