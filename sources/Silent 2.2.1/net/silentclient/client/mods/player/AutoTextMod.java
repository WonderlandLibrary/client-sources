package net.silentclient.client.mods.player;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.KeyEvent;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.IconButton;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.lite.clickgui.utils.RenderUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.mods.CustomFontRenderer;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.util.Server;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.NotificationUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class AutoTextMod extends Mod {
	
	public ArrayList<AutoTextCommand> commands = new ArrayList<AutoTextCommand>();
	public HashMap<Integer, AutoTextCommand> commandsMap = new HashMap<>();
	public boolean sending = false;
	private int componentHeight = 0;
	
	public AutoTextMod() {
		super("Auto Text", ModCategory.MODS, "silentclient/icons/mods/autotext.png");
	}

	@Override
	public boolean isForceDisabled() {
		return Server.isHypixel();
	}

	@Override
	public MouseCursorHandler.CursorType renderCustomLiteComponent(int x, int y, int width, int height, int mouseX, int mouseY) {
		MouseCursorHandler.CursorType cursorType = null;
		CustomFontRenderer font = new CustomFontRenderer();
		font.setRenderMode(CustomFontRenderer.RenderMode.CUSTOM);
		
		font.drawString("Macros:", x, y, -1, 14);

		boolean createHovered = MouseUtils.isInside(mouseX, mouseY, x + customComponentLiteWidth() - font.getStringWidth("Create Macro", 12, SilentFontRenderer.FontType.TITLE) - 20, y, font.getStringWidth("Create Macro", 12, SilentFontRenderer.FontType.TITLE), 12);
		font.drawString("Create Macro", x + customComponentLiteWidth() - font.getStringWidth("Create Macro", 12, SilentFontRenderer.FontType.TITLE) - 20, y, createHovered ? new Color(255, 255, 255, 127).getRGB() : -1, 12);
		if(createHovered) {
			cursorType = MouseCursorHandler.CursorType.POINTER;
		}
		int spacing = y + 20;
		
		for(AutoTextCommand command : commands) {
			font.drawString("Command: " + command.getCommand(), x, spacing, -1, 12);
			font.drawString("Key: " + Keyboard.getKeyName(command.getKey()), x, spacing + 10, -1, 12);
			boolean deleteHovered = MouseUtils.isInside(mouseX, mouseY, x, spacing + 20, font.getStringWidth("Remove"), 12);
			font.drawString("Remove", x, spacing + 20, deleteHovered ? new Color(255, 255, 255, 127).getRGB() : -1, 12);
			if(deleteHovered) {
				cursorType = MouseCursorHandler.CursorType.POINTER;
			}
			spacing += 40;
		}
		
		componentHeight = spacing - 30;

		return cursorType;
	}

	@Override
	public MouseCursorHandler.CursorType renderCustomComponent(int x, int y, int width, int height, int mouseX, int mouseY) {
		MouseCursorHandler.CursorType cursorType = null;
		CustomFontRenderer font = new CustomFontRenderer();
		font.setRenderMode(CustomFontRenderer.RenderMode.CUSTOM);

		font.drawString("Macros:", x, y, -1, 14);

		boolean createHovered = MouseUtils.isInside(mouseX, mouseY, x + customComponentWidth() - font.getStringWidth("Create Macro", 12, SilentFontRenderer.FontType.TITLE), y, font.getStringWidth("Create Macro", 12, SilentFontRenderer.FontType.TITLE), 12);
		font.drawString("Create Macro", x + customComponentWidth() - font.getStringWidth("Create Macro", 12, SilentFontRenderer.FontType.TITLE), y, createHovered ? new Color(255, 255, 255, 127).getRGB() : -1, 12);
		if(createHovered) {
			cursorType = MouseCursorHandler.CursorType.POINTER;
		}
		int spacing = y + 20;

		for(AutoTextCommand command : commands) {
			font.drawString("Command: " + command.getCommand(), x, spacing, -1, 12);
			font.drawString("Key: " + Keyboard.getKeyName(command.getKey()), x, spacing + 10, -1, 12);
			boolean deleteHovered = MouseUtils.isInside(mouseX, mouseY, x, spacing + 20, font.getStringWidth("Remove"), 12);
			font.drawString("Remove", x, spacing + 20, deleteHovered ? new Color(255, 255, 255, 127).getRGB() : -1, 12);
			if(deleteHovered) {
				cursorType = MouseCursorHandler.CursorType.POINTER;
			}
			spacing += 40;
		}

		componentHeight = spacing - 30;

		return cursorType;
	}

	@Override
	public int customComponentLiteHeight() {
		return componentHeight;
	}

	@Override
	public int customComponentHeight() {
		return customComponentLiteHeight();
	}

	@Override
	public int customComponentLiteWidth() {
		return 290;
	}

	@Override
	public int customComponentWidth() {
		return 144;
	}

	@Override
	public void customLiteComponentClick(int x, int y, int mouseX, int mouseY, int mouseButton, GuiScreen screen) {
		CustomFontRenderer font = new CustomFontRenderer();
		font.setRenderMode(CustomFontRenderer.RenderMode.CUSTOM);
		
		if(MouseUtils.isInside(mouseX, mouseY, x + customComponentLiteWidth() - font.getStringWidth("Create Macro", 12, SilentFontRenderer.FontType.TITLE) - 20, y, font.getStringWidth("Create Macro", 12, SilentFontRenderer.FontType.TITLE), 12)) {
			mc.displayGuiScreen(new AutoTextAddCommandGui(screen));
		}
		
		int spacing = y + 20;
		for(AutoTextCommand command : commands) {
			if(MouseUtils.isInside(mouseX, mouseY, x, spacing + 20, font.getStringWidth("Remove", 12, SilentFontRenderer.FontType.TITLE), 12)) {
				try {
					this.removeCommand(command.getCommand(), command.getKey());
				} catch(Exception err) {
					err.printStackTrace();
				}
			}
			spacing += 40;
		}
	}

	@Override
	public void customComponentClick(int x, int y, int mouseX, int mouseY, int mouseButton, GuiScreen screen) {
		CustomFontRenderer font = new CustomFontRenderer();
		font.setRenderMode(CustomFontRenderer.RenderMode.CUSTOM);

		if(MouseUtils.isInside(mouseX, mouseY, x + customComponentWidth() - font.getStringWidth("Create Macro", 12, SilentFontRenderer.FontType.TITLE), y, font.getStringWidth("Create Macro", 12, SilentFontRenderer.FontType.TITLE), 12)) {
			mc.displayGuiScreen(new AutoTextAddCommandGui(screen));
		}

		int spacing = y + 20;
		for(AutoTextCommand command : commands) {
			if(MouseUtils.isInside(mouseX, mouseY, x, spacing + 20, font.getStringWidth("Remove", 12, SilentFontRenderer.FontType.TITLE), 12)) {
				try {
					this.removeCommand(command.getCommand(), command.getKey());
				} catch(Exception err) {
					err.printStackTrace();
				}
			}
			spacing += 40;
		}
	}

	public void removeCommand(String commandText, int key) {
		ArrayList<AutoTextCommand> newCommands = new ArrayList<AutoTextCommand>();
		
		for(AutoTextCommand command : commands) {
			if(command.getCommand() == commandText && command.getKey() == key) {
				continue;
			}
			
			newCommands.add(command);
		}
		
		this.commands = newCommands;
		updateHashMap();
	}
	
	@EventTarget
	public void onClick(KeyEvent event) {
		if(mc.thePlayer != null && mc.theWorld != null && mc.currentScreen == null && !sending && !isForceDisabled()) {
			AutoTextCommand command = commandsMap.get(event.getKey());
			if(command != null) {
				Client.getInstance().getModInstances().getAutoText().sending = true;
				(new Thread("ATC: " + command.getCommand()) {
					public void run() {
						mc.thePlayer.sendChatMessage(command.getCommand());
						try {
							Thread.sleep(2000L);
						} catch (InterruptedException e) {
							Client.logger.catching(e);
						}
						Client.getInstance().getModInstances().getAutoText().sending = false;
					}
				}).start();
			}
		}
	}
	
	public void addCommand(String command, int key) {
		this.commands.add(new AutoTextCommand(command, key));
		updateHashMap();
	}
	
	public ArrayList<AutoTextCommand> getCommands() {
		return commands;
	}
	
	public class AutoTextCommand {
		
		private final String command;
		private final int key;
		
		public AutoTextCommand(String command, int key) {
			this.command = command;
			this.key = key;
		}
		
		public String getCommand() {
			return command;
		}
		
		public int getKey() {
			return key;
		}
		
	}

	public void updateHashMap() {
		HashMap<Integer, AutoTextCommand> map = new HashMap<>();
		for (AutoTextCommand bind : commands) {
			map.put(bind.key, bind);
		}
		commandsMap = map;
	}
	
	public class AutoTextAddCommandGui extends SilentScreen {
	    private final GuiScreen parentScreen;

		private int modalWidth;
		private int modalHeight;

		public AutoTextAddCommandGui(GuiScreen parentScreen) {
			this.parentScreen = parentScreen;
			this.modalWidth = 200;
			this.modalHeight = 100;
		}

		@Override
		public void initGui() {
			MenuBlurUtils.loadBlur();
			int x = width / 2 - (this.modalWidth / 2);
			int y = height / 2 - (this.modalHeight / 2);
			this.buttonList.add(new IconButton(1, x + this.modalWidth - 14 - 3, y + 3, 14, 14, 8, 8, new ResourceLocation("silentclient/icons/exit.png")));
			this.buttonList.add(new Button(2, x + 3, y + this.modalHeight - 23, this.modalWidth - 6, 20, "Done"));
			this.silentInputs.add(new Input("Command", Pattern
					.compile("^[~`!@#$%^&*()_+=[\\\\]\\\\\\\\\\\\{\\\\}|;':\\\",.\\\\/<>?a-zA-Z0-9-\\s]+$"), 30));
			this.silentInputs.add(new Input("Key", -1));
		}

		@Override
		protected void actionPerformed(GuiButton button) throws IOException {
			super.actionPerformed(button);
			switch (button.id) {
				case 1:
					mc.displayGuiScreen(parentScreen);
					break;
				case 2:
					if(this.silentInputs.get(0).getValue().length() == 0) {
						NotificationUtils.showNotification("Error", "Please enter a Command");
						break;
					}
					if(this.silentInputs.get(1).getKey() == -1) {
						NotificationUtils.showNotification("Error", "Please enter a Key");
						break;
					}
					Client.getInstance().getModInstances().getAutoText().addCommand(this.silentInputs.get(0).getValue(), this.silentInputs.get(1).getKey());
					NotificationUtils.showNotification("Success", "The macro has been successfully added!");
					mc.displayGuiScreen(parentScreen);
					break;
			}
		}

		@Override
		public void drawScreen(int mouseX, int mouseY, float partialTicks) {
			MenuBlurUtils.renderBackground(this);
			GlStateManager.pushMatrix();
			int x = width / 2 - (this.modalWidth / 2);
			int y = height / 2 - (this.modalHeight / 2);

			// Header
			RenderUtils.drawRect(x, y, this.modalWidth, this.modalHeight, Theme.backgroundColor().getRGB());
			Client.getInstance().getSilentFontRenderer().drawString("Create Macro", x + 3, y + 3, 14, SilentFontRenderer.FontType.TITLE);

			// Content
			this.silentInputs.get(0).render(mouseX, mouseY, x + 3, y + 23, this.modalWidth - 6);
			this.silentInputs.get(1).render(mouseX, mouseY, x + 3, y + 46, this.modalWidth - 6);

			super.drawScreen(mouseX, mouseY, partialTicks);

			GlStateManager.popMatrix();
		}

		@Override
		protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
			super.mouseClicked(mouseX, mouseY, mouseButton);
			int x = width / 2 - (this.modalWidth / 2);
			int y = height / 2 - (this.modalHeight / 2);
			this.silentInputs.get(0).onClick(mouseX, mouseY, x + 3, y + 23, this.modalWidth - 6);
			this.silentInputs.get(1).onClick(mouseX, mouseY, x + 3, y + 46, this.modalWidth - 6);
		}


		@Override
		public void onGuiClosed() {
			MenuBlurUtils.unloadBlur();
		}

		@Override
		protected void keyTyped(char typedChar, int keyCode) throws IOException {
			if (keyCode == Keyboard.KEY_ESCAPE) {
				mc.displayGuiScreen(parentScreen);
				return;
			};

			this.silentInputs.get(0).onKeyTyped(typedChar, keyCode);
			this.silentInputs.get(1).onKeyTyped(typedChar, keyCode);
		}
	}
}
