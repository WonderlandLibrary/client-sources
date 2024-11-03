package net.silentclient.client.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventManager;
import net.silentclient.client.gui.animation.SimpleAnimation;
import net.silentclient.client.gui.hud.ScreenPosition;
import net.silentclient.client.utils.MenuBlurUtils;
import net.silentclient.client.utils.MouseCursorHandler;
import net.silentclient.client.utils.RawInputHandler;

import java.awt.*;
import java.util.ArrayList;

public class Mod implements IMod {
	private boolean isEnabled;
	
	protected final Minecraft mc;
	protected final FontRenderer font;
	protected final Client client;

	private boolean toggled;

	private String name;
	private ModCategory category;
	private String icon;
	public boolean defaultEnabled;
	
	private boolean updated = false;
	private boolean newMod = false;
	
	public SimpleAnimation simpleAnimation = new SimpleAnimation(0);

	public Mod(String name, ModCategory category, String icon, boolean defaultEnabled, boolean updated, boolean newMod) {
		this.mc = Minecraft.getMinecraft();
		this.font = mc.fontRendererObj;
		this.client = Client.getInstance();
		this.isEnabled = defaultEnabled;
		this.defaultEnabled = defaultEnabled;

		this.name = name;
		this.category = category;
		this.icon = icon;
		
		this.updated = updated;
		this.newMod = newMod;

		setEnabled(isEnabled);
		
		this.setup();
	}
	
	public Mod(String name, ModCategory category, String icon, boolean defaultEnabled) {
		this(name, category, icon, defaultEnabled, false, false);
	}
		
	public Mod(String name, ModCategory category, String icon) {
		this(name, category, icon, false, false, false);
	}

	public void reset(boolean resetEnabled) {
		if(resetEnabled && isEnabled != defaultEnabled) {
			this.toggle();
		}
		Client.getInstance().getSettingsManager().getSettingByMod(this).forEach(setting -> {
			if(setting.getName() == "Cape Shoulders" || setting.getName() == "Cape Type") {
				return;
			}
			if(this.isEnabled) {
				if(this.getName() == "Pack Tweaks") {
					mc.renderGlobal.loadRenderers();
				}
				if(this.getName() == "FPS Boost") {
					mc.renderGlobal.loadRenderers();
				}
				if(setting.getName() == "Raw Mouse Input") {
					RawInputHandler.toggleRawInput(setting.getValBoolean());
				}
				if(setting.getName() == "Menu Background Blur") {
					if(setting.getValBoolean()) {
						MenuBlurUtils.unloadBlur();
					} else {
						MenuBlurUtils.loadBlur();
					}
				}
			}
			if(setting.isCheck()) {
				setting.setValBoolean(setting.defaultbval);
			}

			if(setting.isKeybind()) {
				setting.setKeybind(setting.defaultkval);
			}

			if(setting.isColor()) {
				setting.setValColor(setting.defaultcval);
				setting.setChroma(false);
				setting.setOpacity(setting.defaultopacity);
			}

			if(setting.isSlider()) {
				if(setting.defaultdval != setting.getValDouble()) {
					if(this.getName() == "Pack Tweaks" && this.isEnabled()) {
						mc.renderGlobal.loadRenderers();
					}

					if(this.getName() == "Color Saturation" && this.isEnabled()) {
						Client.getInstance().getModInstances().getColorSaturation().update();
					}
				}
				setting.setValDouble(setting.defaultdval);
			}

			if(setting.isCombo()) {
				setting.setValString(setting.defaultsval);
			}
			
			if(setting.isInput()) {
				setting.setValString(setting.defaultsval);
			}
		});
		if(this instanceof ModDraggable) {
			((ModDraggable) this).setPos(ScreenPosition.fromRelativePosition(0, 0));
		}
	}
	
	public boolean isForceDisabled() {
		return false;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public String getName() {
		return name;
	}
	
	public ModCategory getCategory() {
		return category;
	}
	
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		
		if(toggled) {
			setEnabled(true);
			try {
				onEnable();
			} catch (Exception err) {
				err.printStackTrace();
			}
		}else {
			setEnabled(false);
			try {
				onDisable();
			} catch (Exception err) {
				err.printStackTrace();
			}
		}
	}

	public void toggle() {
		toggled = !toggled;
		
		if(toggled) {
			setEnabled(true);
		}else {
			setEnabled(false);
		}
	}
	
	public boolean isToggled() {
		return toggled;
	}
	
	public MouseCursorHandler.CursorType renderCustomLiteComponent(int x, int y, int width, int height, int mouseX, int mouseY) {
		return null;
	}
	
	public int customComponentLiteWidth() {
		return 0;
	}
	
	public int customComponentLiteHeight() {
		return 0;
	}

	public void customLiteComponentClick(int x, int y, int mouseX, int mouseY, int mouseButton, GuiScreen screen) {

	}

	public MouseCursorHandler.CursorType renderCustomComponent(int x, int y, int width, int height, int mouseX, int mouseY) {
		return null;
	}

	public int customComponentWidth() {
		return 0;
	}

	public int customComponentHeight() {
		return 0;
	}

	public void customComponentClick(int x, int y, int mouseX, int mouseY, int mouseButton, GuiScreen screen) {

	}
	
	public void onChangeSettingValue(Setting setting) {
		
	}
	
	public boolean isUpdated() {
		return updated;
	}
	
	public boolean isNew() {
		return newMod;
	}
	
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
	public void setNewMod(boolean newMod) {
		this.newMod = newMod;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
		
		if(isEnabled) {
			try {
				onEnable();
			} catch (Exception err) {
				err.printStackTrace();
			}
			EventManager.register(this);
		} else {
			try {
				onDisable();
			} catch (Exception err) {
				err.printStackTrace();
			}
			EventManager.unregister(this);
		}
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}

	public Setting addBooleanSetting(String name, Mod mod, boolean toggle) {
		Setting setting;

		Client.getInstance().getSettingsManager().addSetting(setting = new Setting(name, mod, toggle));
		return setting;
	}

	public Setting addCellSetting(String name, Mod mod, boolean[][] cells) {
		return Client.getInstance().getSettingsManager().addSetting(new Setting(name, mod, cells));
	}

	public Setting addKeybindSetting(String name, Mod mod, int keybind) {
		Setting setting;

		Client.getInstance().getSettingsManager().addSetting(setting = new Setting(name, mod, keybind));
		return setting;
	}
	
	public Setting addModeSetting(String name, Mod mod, String defaultMode, ArrayList<String> options) {
		Setting setting;
		Client.getInstance().getSettingsManager().addSetting(setting =new Setting(name, mod, defaultMode, options));
		return setting;
	}
	
	public Setting addSliderSetting(String name, Mod mod, double defaultValue, double minValue, double maxValue, boolean intValue) {
		Setting setting;
		Client.getInstance().getSettingsManager().addSetting(setting =new Setting(name, mod, defaultValue, minValue, maxValue, intValue));
		return setting;
	}
	
	public Setting addColorSetting(String name, Mod mod, Color defaultValue) {
		Setting setting;
		Client.getInstance().getSettingsManager().addSetting(setting =new Setting(name, mod, defaultValue));
		return setting;
	}

	public Setting addColorSetting(String name, Mod mod, Color defaultValue, int opacity) {
		Setting setting;
		Client.getInstance().getSettingsManager().addSetting(setting =new Setting(name, mod, defaultValue, opacity));
		return setting;
	}

	public Setting addInputSetting(String name, Mod mod, String defaultMode) {
		Setting setting;
		Client.getInstance().getSettingsManager().addSetting(setting =new Setting(name, mod, defaultMode));
		return setting;
	}
	
	public Setting addInputSetting(String name, Mod mod, String defaultMode, boolean premium) {
		Setting setting;
		Client.getInstance().getSettingsManager().addSetting(setting =new Setting(name, mod, defaultMode, premium));
		return setting;
	}
}
