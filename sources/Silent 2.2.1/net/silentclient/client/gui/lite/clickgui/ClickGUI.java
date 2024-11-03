package net.silentclient.client.gui.lite.clickgui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.silentclient.client.Client;
import net.silentclient.client.config.AddConfigModal;
import net.silentclient.client.gui.SilentScreen;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.animation.normal.Animation;
import net.silentclient.client.gui.animation.normal.Direction;
import net.silentclient.client.gui.animation.normal.impl.EaseBackIn;
import net.silentclient.client.gui.elements.Button;
import net.silentclient.client.gui.elements.Input;
import net.silentclient.client.gui.elements.Switch;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.hud.HUDConfigScreen;
import net.silentclient.client.gui.lite.clickgui.utils.GlUtils;
import net.silentclient.client.gui.lite.clickgui.utils.MouseUtils;
import net.silentclient.client.gui.theme.Theme;
import net.silentclient.client.gui.theme.button.DefaultButtonTheme;
import net.silentclient.client.gui.theme.button.SelectedButtonTheme;
import net.silentclient.client.gui.util.RenderUtil;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.premium.PremiumGui;
import net.silentclient.client.utils.ColorUtils;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.Sounds;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ClickGUI extends SilentScreen {
	private ModCategory selectedCategory;
	public static double scrollY;
	public static SimpleAnimation scrollAnimation = new SimpleAnimation(0.0F);
	private Input nametagMessageInput = new Input("Nametag Message", Pattern
            .compile("^[~`!@#$%^&*()_+=[\\\\]\\\\\\\\\\\\{\\\\}|;':\\\",.\\\\/<>?a-zA-Z0-9-\\s]+$"), 40);
	private float scrollHeight = 0;
	public static Animation introAnimation;
	public static boolean close;
	private boolean loaded;
	
	public ClickGUI() {
		this.loaded = false;
		selectedCategory = ModCategory.MODS;
	}

	public ClickGUI(ModCategory category) {
		this.loaded = false;
		selectedCategory = category;
	}

	@Override
	public void initGui() {
		close = false;
		defaultCursor = false;
		Client.getInstance().configManager.updateConfigs();
		ClickGUI.scrollY = 0;
		MenuBlurUtils.loadBlur();
		this.silentInputs.add(new Input("Search"));
		int categoryOffsetY = 25;
		int addX = 190;
		int addY = 110;
		if(!loaded) {
			introAnimation = new EaseBackIn(Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Menu Animations").getValBoolean() ? Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Menu Animations Speed").getValInt() : 1, 1, 2);
		}

		int x = (width / 2) - addX;
		int y = (height / 2) - addY;
		int height = addY * 2;
		int tabId = 1;
		for(ModCategory c : ModCategory.values()) {

			String formattedName = c.toString();

			this.buttonList.add(new Button(tabId, x + 5, y + categoryOffsetY, 75, 20, formattedName, false, selectedCategory == c ? new SelectedButtonTheme() : new DefaultButtonTheme()));

			categoryOffsetY +=25;
			tabId++;
		}
		this.buttonList.add(new Button(tabId, x + 5, (y + height) - 26, 75, 20, "Edit HUD"));
        nametagMessageInput.setValue(Client.getInstance().getAccount().getNametagMessage());
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		MenuBlurUtils.renderBackground(this);
		int addX = 190;
		int addY = 110;

		int x = (width / 2) - addX;
		int y = (height / 2) - addY;
		int width = addX * 2;
		int height = addY * 2;

		int modOffsetY = 25;
		//Draw background
		MouseCursorHandler.CursorType cursorType = getCursor(silentInputs, buttonList);
		GlStateManager.pushMatrix();
		GlUtils.startScale(((x) + (x) + width) / 2, ((y) + (y + height)) / 2, (float) introAnimation.getValue());
		RenderUtil.drawRoundedRect(x, y, width, height, 10, Theme.backgroundColor().getRGB());
		Client.getInstance().getSilentFontRenderer().drawString(selectedCategory.toString(), x + 100, (int) (y + 5), 14, SilentFontRenderer.FontType.TITLE);
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		ScaledResolution r = new ScaledResolution(mc);
		int s = r.getScaleFactor();
		int translatedY = r.getScaledHeight() - (y + 25) - (height - 25);
		GL11.glScissor(x * s, translatedY * s, (width * s), (height - 25) * s);
		int column = 1;
		int modOffsetX = x + 100;
		this.scrollHeight = 25;
		boolean headerHovered = MouseUtils.isInside(mouseX, mouseY, modOffsetX, y, width, 25);
		if(selectedCategory != ModCategory.PLUS && selectedCategory != ModCategory.CONFIGS) {
			for(Mod m : getMods()) {

				if(mouseInContent(x, (int) (y + modOffsetY - scrollAnimation.getValue()), y + 25, height - 25) || mouseInContent(x, (int) (y + modOffsetY - scrollAnimation.getValue() + 70), y + 25, height - 25)) {
					float switchX = modOffsetX + ((65 / 2) - (15 / 2));
					float switchY = y + modOffsetY - scrollAnimation.getValue() + 55;
					boolean switchHovered = selectedCategory.equals(ModCategory.MODS) && Switch.isHovered(mouseX, mouseY, switchX, switchY) && !headerHovered;
					boolean isHovered = MouseUtils.isInside(mouseX, mouseY, modOffsetX, y + modOffsetY - scrollAnimation.getValue(), 65, 70) && !headerHovered && !switchHovered && (Client.getInstance().getSettingsManager().getSettingByMod(m).size() != 0 || m.getName() == "Auto Text");
					RenderUtil.drawRoundedOutline(modOffsetX, y + modOffsetY - scrollAnimation.getValue(), 65,  70, 3, 1, Theme.borderColor().getRGB());
					RenderUtil.drawRoundedRect(modOffsetX, y + modOffsetY - scrollAnimation.getValue(), 65,  70, 3, new Color(255, 255, 255, isHovered ? 30 : 0).getRGB());
					GL11.glColor4f(1, 1, 1, 1);
					Client.getInstance().getSilentFontRenderer().drawString(m.getName(), modOffsetX + ((65 / 2) - ((Client.getInstance().getSilentFontRenderer().getStringWidth(m.getName(), 8, SilentFontRenderer.FontType.HEADER)) / 2)), y + modOffsetY - scrollAnimation.getValue() + 4, 8, SilentFontRenderer.FontType.HEADER);
					if(m.getIcon() != null) {
						RenderUtil.drawImage(new ResourceLocation(m.getIcon()), modOffsetX + ((65 / 2) - 10), y + modOffsetY - scrollAnimation.getValue() + ((70 / 2) - 10), 20, 20, false);
					}
					if(selectedCategory.equals(ModCategory.MODS)) {
						Switch.render(mouseX, mouseY, switchX, switchY, m.simpleAnimation, m.isEnabled(), m.isForceDisabled(), m.isForceDisabled() ? "Force disabled" : null);
					}

					if(switchHovered || isHovered) {
						cursorType = MouseCursorHandler.CursorType.POINTER;
					}

					if(m.isUpdated() || m.isNew()) {
						String status = "UPDATED";
						if(m.isNew()) {
							status = "NEW";
						}
						float badgeX = modOffsetX + ((65 / 2) - 15);
						float badgeY = y + modOffsetY - scrollAnimation.getValue() + 13;
						RenderUtil.drawRoundedRect(badgeX, badgeY, 30, 8, 8, Color.RED.getRGB());
						Client.getInstance().getSilentFontRenderer().drawString(status, badgeX + (status.equals("UPDATED") ? 3 : 9), badgeY + 1, 6, SilentFontRenderer.FontType.HEADER);
					}
				}
				
				modOffsetY += column == 4 ? 75 : 0;
				modOffsetX = column == 4 ? x + 100 : modOffsetX + 70;
				this.scrollHeight += column == 4 ? 70 : 0;
				column = column == 4 ? 1 : column + 1;
			}
		} else if(selectedCategory == ModCategory.CONFIGS) {
			RenderUtil.drawRoundedOutline(x + 100 + (column == 1 ? 0 : 140), y + modOffsetY - scrollAnimation.getValue(), 135, 28, 10, 2, -1);

			Client.getInstance().getSilentFontRenderer().drawString("New Config", x + 110 + (column == 1 ? 0 : 140), y + modOffsetY + 7 - (int) scrollAnimation.getValue(), 14, SilentFontRenderer.FontType.TITLE);

			if(MouseUtils.isInside(mouseX, mouseY, x + 100 + (column == 1 ? 0 : 140), y + modOffsetY - scrollAnimation.getValue(), 135, 28)) {
				cursorType = MouseCursorHandler.CursorType.POINTER;
			}

			modOffsetY+=column == 1 ? 0 : 35;
			this.scrollHeight += column == 2 ? 28 : 0;
			column = column == 1 ? 2 : 1;
			RenderUtil.drawRoundedOutline(x + 100 + (column == 1 ? 0 : 140), y + modOffsetY - scrollAnimation.getValue(), 135, 28, 10, 2, -1);

			Client.getInstance().getSilentFontRenderer().drawString("Open Folder", x + 110 + (column == 1 ? 0 : 140), y + modOffsetY + 7 - (int) scrollAnimation.getValue(), 14, SilentFontRenderer.FontType.TITLE);
			if(MouseUtils.isInside(mouseX, mouseY, x + 100 + (column == 1 ? 0 : 140), y + modOffsetY - scrollAnimation.getValue(), 135, 28)) {
				cursorType = MouseCursorHandler.CursorType.POINTER;
			}
			modOffsetY+=column == 1 ? 0 : 35;
			this.scrollHeight += column == 2 ? 28 : 0;
			column = column == 1 ? 2 : 1;
			RenderUtil.drawRoundedOutline(x + 100 + (column == 1 ? 0 : 140), y + modOffsetY - scrollAnimation.getValue(), 135, 28, 10, 2, new Color(32, 252, 3).getRGB());
			RenderUtil.drawImage(new ResourceLocation("silentclient/transparent.png"), x + 90 + (column == 1 ? 0 : 130) + (column == 1 ? 125 : 135), y + modOffsetY + 7 - scrollAnimation.getValue(), 15, 15, false);
			ColorUtils.setColor(new Color(255, 255, 255).getRGB());
			Client.getInstance().getSilentFontRenderer().drawString(Client.getInstance().configManager.configFile.getName().replace(".txt", ""), x + 110 + (column == 1 ? 0 : 140), y + modOffsetY + 7 - (int) scrollAnimation.getValue(), 14, SilentFontRenderer.FontType.TITLE, 45);

			modOffsetY+=column == 1 ? 0 : 35;
			this.scrollHeight += column == 2 ? 28 : 0;
			column = column == 1 ? 2 : 1;
			for(String config : Client.getInstance().configManager.getConfigFiles()) {
				if(config.equals(".config-settings.txt") || config.equals(".DS_Store") || Client.getInstance().configManager.configFile.getName().equals(config)) {
					continue;
				}
				RenderUtil.drawRoundedOutline(x + 100 + (column == 1 ? 0 : 140), y + modOffsetY - scrollAnimation.getValue(), 135, 28, 10, 2, new Color(252, 3, 3).getRGB());
				RenderUtil.drawImage(new ResourceLocation("silentclient/icons/trash-icon.png"), x + 90 + (column == 1 ? 0 : 130) + (column == 1 ? 125 : 135), y + modOffsetY + 7 - scrollAnimation.getValue(), 15, 15, false);
				ColorUtils.setColor(new Color(255, 255, 255).getRGB());
				Client.getInstance().getSilentFontRenderer().drawString(config.replace(".txt", ""), x + 110 + (column == 1 ? 0 : 140), y + modOffsetY + 7 - (int) scrollAnimation.getValue(), 14, SilentFontRenderer.FontType.TITLE, 45);
				if(MouseUtils.isInside(mouseX, mouseY, x + 100 + (column == 1 ? 0 : 140), y + modOffsetY - scrollAnimation.getValue(), 135, 28)) {
					cursorType = MouseCursorHandler.CursorType.POINTER;
				}
				modOffsetY+=column == 1 ? 0 : 35;
				this.scrollHeight += column == 2 ? 28 : 0;
				column = column == 1 ? 2 : 1;
			}
		}

		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glPopMatrix();

		RenderUtil.drawImage(new ResourceLocation("silentclient/logos/logo.png"), x + 5, y + 5, 77, 15);
		
		if(selectedCategory == ModCategory.PLUS) {
			MouseCursorHandler.CursorType premiumCursor = PremiumGui.drawScreen(x, y, width, height, mouseX, mouseY, partialTicks, nametagMessageInput);
			if(premiumCursor != null) {
				cursorType = premiumCursor;
			}
		}

		super.drawScreen(mouseX, mouseY, partialTicks);
        
		scrollAnimation.setAnimation((float) scrollY, 16);

		if(selectedCategory == ModCategory.MODS) {
			this.silentInputs.get(0).render(mouseX, mouseY, x + width - 140, y + 2, 135);
		}
		
		if(close) {
			introAnimation.setDirection(Direction.BACKWARDS);
			if(introAnimation.isDone(Direction.BACKWARDS)) {
				mc.displayGuiScreen(null);
			}
			loaded = false;
			Client.getInstance().getMouseCursorHandler().disableCursor();
		} else {
			Client.getInstance().getMouseCursorHandler().enableCursor(cursorType);
		}
		if(introAnimation.isDone() && !close) {
			loaded = true;
		}
		GlUtils.stopScale();
		
		GlStateManager.popMatrix();
	}
	
	@Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int dw = Mouse.getEventDWheel();
        double newScrollY = scrollY;
        if(dw != 0) {
            if (dw > 0) {
                dw = -1;
            } else {
                dw = 1;
            }
            float amountScrolled = (float) (dw * 10);
            if (newScrollY + amountScrolled > 0)
            	newScrollY += amountScrolled;
            else
            	newScrollY = 0;
            if(newScrollY < scrollHeight) {
            	scrollY = newScrollY;
            }
        }
    }

	public static boolean mouseInContent(int mouseX, int mouseY, int y, int height) {
		return MouseUtils.isInside(mouseX, mouseY, mouseX, y,mouseX + 1, height);
	}

	private ArrayList<Mod> getMods() {
		if(this.silentInputs.get(0).getValue().trim().equals("") || !selectedCategory.equals(ModCategory.MODS)) {
			return Client.getInstance().getModInstances().getModByCategory(selectedCategory);
		}

		return Client.getInstance().getModInstances().searchMods(this.silentInputs.get(0).getValue());
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		scrollY = 0;
		if(button instanceof Button) {
			switch(button.id) {
				case 1:
					this.buttonList.forEach(oldButton -> {
						if(oldButton instanceof Button) {
							((Button) oldButton).setTheme(new DefaultButtonTheme());
						}
					});
					((Button) button).setTheme(new SelectedButtonTheme());
					selectedCategory = ModCategory.MODS;
					break;
				case 2:
					this.buttonList.forEach(oldButton -> {
						if(oldButton instanceof Button) {
							((Button) oldButton).setTheme(new DefaultButtonTheme());
						}
					});
					((Button) button).setTheme(new SelectedButtonTheme());
					selectedCategory = ModCategory.SETTINGS;
					break;
				case 3:
					this.buttonList.forEach(oldButton -> {
						if(oldButton instanceof Button) {
							((Button) oldButton).setTheme(new DefaultButtonTheme());
						}
					});
					((Button) button).setTheme(new SelectedButtonTheme());
					selectedCategory = ModCategory.CONFIGS;
					break;
				case 4:
					this.buttonList.forEach(oldButton -> {
						if(oldButton instanceof Button) {
							((Button) oldButton).setTheme(new DefaultButtonTheme());
						}
					});
					((Button) button).setTheme(new SelectedButtonTheme());
					selectedCategory = ModCategory.PLUS;
					break;
			}
		}
		Client.getInstance().configManager.save();

		if(button.id == 5) {
			mc.displayGuiScreen(new HUDConfigScreen(this));
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		int addX = 190;
		int addY = 110;

		int x = (width / 2) - addX;
		int y = (height / 2) - addY;
		int width = addX * 2;
		int height = addY * 2;

		int modOffsetY = 25;
		
		int column = 1;
		int modOffsetX = x + 100;

		if(selectedCategory == ModCategory.MODS) {
			this.silentInputs.get(0).onClick(mouseX, mouseY, x + width - 140, y + 2, 135);
		}
		
		if(MouseUtils.isInside(mouseX, mouseY, modOffsetX, y, width, 25)) {
			return;
		}
		
		if(selectedCategory != ModCategory.PLUS && selectedCategory != ModCategory.CONFIGS) {
			for(Mod m : getMods()) {
				float switchX = modOffsetX + ((65 / 2) - (15 / 2));
				float switchY = y + modOffsetY - scrollAnimation.getValue() + 55;
				boolean switchHovered = selectedCategory.equals(ModCategory.MODS) && Switch.isHovered(mouseX, mouseY, switchX, switchY);
				boolean isHovered = MouseUtils.isInside(mouseX, mouseY, modOffsetX, y + modOffsetY - scrollAnimation.getValue(), 65, 70) && !switchHovered && (Client.getInstance().getSettingsManager().getSettingByMod(m).size() != 0 || m.getName() == "Auto Text");
				
				if(switchHovered && !m.isForceDisabled()) {
					Sounds.playButtonSound();
					m.toggle();
				}
				if(isHovered) {
					Sounds.playButtonSound();
					mc.displayGuiScreen(new ModSettings(m, this));
				}
					
				modOffsetY += column == 4 ? 75 : 0;
				modOffsetX = column == 4 ? x + 100 : modOffsetX + 70;
				column = column == 4 ? 1 : column + 1;
			}
		}else if(selectedCategory == ModCategory.CONFIGS) {
			if(MouseUtils.isInside(mouseX, mouseY,x + 100 + (column == 1 ? 0 : 140), y + modOffsetY - scrollAnimation.getValue(), 135, 28) && mouseButton == 0) {
				Sounds.playButtonSound();
				mc.displayGuiScreen(new AddConfigModal(this));
			}
			modOffsetY+=column == 1 ? 0 : 35;
			column = column == 1 ? 2 : 1;

			if(MouseUtils.isInside(mouseX, mouseY,x + 100 + (column == 1 ? 0 : 140), y + modOffsetY - scrollAnimation.getValue(), 135, 28) && mouseButton == 0) {
				Sounds.playButtonSound();
				File file1 = Client.getInstance().configDir;
				String s = file1.getAbsolutePath();

				if (Util.getOSType() == Util.EnumOS.OSX) {
					try {
						Client.logger.info(s);
						Runtime.getRuntime().exec(new String[]{"/usr/bin/open", s});
						return;
					} catch (IOException ioexception1) {
						Client.logger.error((String) "Couldn\'t open file", (Throwable) ioexception1);
					}
				} else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
					String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[]{s});

					try {
						Runtime.getRuntime().exec(s1);
						return;
					} catch (IOException ioexception) {
						Client.logger.error((String) "Couldn\'t open file", (Throwable) ioexception);
					}
				}

				boolean flag = false;

				try {
					Class<?> oclass = Class.forName("java.awt.Desktop");
					Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
					oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{file1.toURI()});
				} catch (Throwable throwable) {
					Client.logger.error("Couldn\'t open link", throwable);
					flag = true;
				}

				if (flag) {
					Client.logger.info("Opening via system class!");
					Sys.openURL("file://" + s);
				}
			}
			modOffsetY+=column == 1 ? 0 : 35;
			column = column == 1 ? 2 : 1;

			modOffsetY+=column == 1 ? 0 : 35;
			column = column == 1 ? 2 : 1;

			for(String config : Client.getInstance().configManager.getConfigFiles()) {
				if(config.equals(".config-settings.txt") || config.equals(".DS_Store") || Client.getInstance().configManager.configFile.getName().equals(config)) {
					continue;
				}

				if(MouseUtils.isInside(mouseX, mouseY, x + 90 + (column == 1 ? 0 : 130) + (column == 1 ? 125 : 135), y + modOffsetY + 7 - scrollAnimation.getValue(), 15, 15) && mouseButton == 0) {
					Client.getInstance().configManager.deleteConfig(config);
					Sounds.playButtonSound();
				} else if(MouseUtils.isInside(mouseX, mouseY,x + 100 + (column == 1 ? 0 : 140), y + modOffsetY - scrollAnimation.getValue(), 135, 28) && mouseButton == 0) {
					Client.getInstance().configManager.loadConfig(config);
					Sounds.playButtonSound();
				}

				modOffsetY+=column == 1 ? 0 : 35;
				column = column == 1 ? 2 : 1;
			}
		} else {
			PremiumGui.mouseClicked(x, y, width, height, mouseX, mouseY, mouseButton, this, nametagMessageInput);
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
	        close = true;
		}

		if(selectedCategory != ModCategory.PLUS && selectedCategory != ModCategory.CONFIGS) {
			if (selectedCategory == ModCategory.MODS) {
				this.silentInputs.get(0).onKeyTyped(typedChar, keyCode);
				if(this.silentInputs.get(0).isFocused()) {
					ClickGUI.scrollY = 0;
				}
			}
		}
		if(selectedCategory == ModCategory.PLUS && Client.getInstance().getAccount().isPremiumPlus()) {
			nametagMessageInput.onKeyTyped(typedChar, keyCode);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return !(selectedCategory.equals(ModCategory.CONFIGS));
	}
	
	@Override
    public void onGuiClosed() {
		super.onGuiClosed();
		Client.getInstance().configManager.save();
		MenuBlurUtils.unloadBlur();
    }
}
