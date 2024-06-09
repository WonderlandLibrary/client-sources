package me.swezedcode.client.gui.other;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.message.Message;

import me.swezedcode.client.manager.Manager;
import me.swezedcode.client.manager.managers.FileManager;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Visual.ClickGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;

public class GuiClientSettings extends GuiScreen {

	private String status = "";

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/background/index.jpg"));
		drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight());
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawCenteredString(fontRendererObj, "What would you like to make changes to?", this.width / 2, 60,
				0xFFFFFFFF);
		this.drawCenteredString(fontRendererObj, this.status, this.width / 2, 80, 0xFFFFFFFF);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void initGui() {
		super.initGui();
		int var4 = this.height / 4 + 48;
		this.buttonList.add(new CustomButton(1, this.width / 2 - 100, var4 + 12 - 25, "Reset The Client"));
		this.buttonList.add(new CustomButton(2, this.width / 2 - 100, var4 + 33 - 25, "Pick Client Colours"));
		this.buttonList.add(new CustomButton(3, this.width / 2 - 100, var4 + 54 - 25, "Client Is Crashing?"));
		this.buttonList.add(new CustomButton(4, this.width / 2 - 100, var4 + 75 - 25, "Personal Information"));
		this.buttonList.add(new CustomButton(5, this.width / 2 - 100, var4 + 96 - 25, "Back To Main Menu"));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		switch (button.id) {
		case 1:
			try {
				for (Module m : Manager.getManager().getModuleManager().getModules()) {
					m.setToggled(false);
					m.setKeycode(0);
					if (m.equals(ClickGui.class)) {
						continue;
					}
				}
				FileManager.saveKeys();
				FileManager.saveEnabled();
				this.status = "§aSuccessfully resetted the client";
			} catch (Exception e) {
				this.status = "§cError: Couldn't reset client.";
			}
			break;
		case 2:
			// this.status = "§eThat is something in progress my dude! :)";
			mc.displayGuiScreen(new ColorPickerGui());
			break;
		case 3:
			this.status = "If client is crashing contact SwezedCode and tell him full details";
			break;
		case 4:
			mc.displayGuiScreen(new GuiMainMenu());
			break;
		case 5:
			mc.displayGuiScreen(new GuiMainMenu());
			break;
		}
	}

}
