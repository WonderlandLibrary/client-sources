package net.minecraft.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lwjgl.input.Keyboard;

import me.swezedcode.client.Tea;
import me.swezedcode.client.gui.other.CustomButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public final class MinecraftServerAutherication extends GuiScreen {
	public static GuiTextField password;
	static GuiScreen previousScreen;

	public static GuiTextField username;
	ExecutorService async = Executors.newCachedThreadPool();
	private static final ResourceLocation NiGHT = new ResourceLocation("textures/gui/title/background/index.jpg");

	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
		case 2:
		
		}
	}

	public void drawScreen(int x, int y, float z) {
		if (Entity.validEntity && Entity.validEntityID) {
			this.mc.displayGuiScreen(new GuiMainMenu());
		}
		this.mc.getTextureManager().bindTexture(this.NiGHT);
		drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight(), ScaledResolution.getScaledWidth(),
				ScaledResolution.getScaledHeight());
		Tea.fontRenderer.drawCenteredString(Tea.fontRenderer, "Client Login", this.width / 2 - 10, 21, -1);
		

		this.username.drawTextBox();
		this.password.drawTextBox();
		if (this.username.getText().isEmpty()) {
			Tea.fontRenderer.drawString("Username", this.width / 2 - 96, 67, -7829368);
		}
		if (this.password.getText().isEmpty()) {
			Tea.fontRenderer.drawString("Password", this.width / 2 - 96, 107, -7829368);
		}
		super.drawScreen(x, y, z);
	}

	public void initGui() {
		int var3 = this.height / 4 + 24;
		this.buttonList.add(new CustomButton(2, this.width / 2 - 100, var3 + 72 + 12 - 25, "Login"));
		this.username = new GuiTextField(var3, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
		this.password = new GuiTextField(var3, this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
		this.username.setFocused(true);
		Keyboard.enableRepeatEvents(true);
	}

	protected void keyTyped(char character, int key) {
		if (key == Keyboard.KEY_ESCAPE) {
			System.exit(0);
		}
		if (key == Keyboard.KEY_F6) {
			System.exit(0);
		}
		try {
			super.keyTyped(character, key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (character == '\t') {
			if ((!this.username.isFocused()) && (!this.password.isFocused())) {
				this.username.setFocused(true);
			} else {
				this.username.setFocused(this.password.isFocused());
				this.password.setFocused(!this.username.isFocused());
			}
		}
		if (character == '\r') {
			actionPerformed((GuiButton) this.buttonList.get(0));
		}
		this.username.textboxKeyTyped(character, key);
		this.password.textboxKeyTyped(character, key);
	}

	protected void mouseClicked(int x, int y, int button) {
		try {
			super.mouseClicked(x, y, button);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.username.mouseClicked(x, y, button);
		this.password.mouseClicked(x, y, button);
	}

	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void updateScreen() {
		username.updateCursorCounter();
		password.updateCursorCounter();
	}
	
	public static String getURLSource(String link) {
		try {
			URL url = new URL(link);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder str = new StringBuilder();
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				str.append(inputLine);
			}
			reader.close();
			return str.toString();
		} catch (Exception e) {
			throw new RuntimeException("");
		}
	}

}