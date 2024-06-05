package me.rich.module.hud;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.font.Fonts;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.gui.ScaledResolution;

public class KeyBinds extends Feature {

	public KeyBinds() {
		super("KeyBinds", 0, Category.HUD);
		ArrayList<String> indic = new ArrayList<String>();
		indic.add("Onetap");
		indic.add("Crosshair");
		Main.settingsManager.rSetting(new Setting("KeyBinds Mode", this, "Nemesis", indic));
		Main.instance.settingsManager.rSetting(new Setting("PositionX", this, 360.0, 0.0, 500.0, true));
		Main.instance.settingsManager.rSetting(new Setting("PositionY", this, 150.0, 0.0, 170.0, true));
	}

	@EventTarget
	public void biba(Event2D event) {

		String indicat = Main.settingsManager
				.getSettingByName(Main.moduleManager.getModule(KeyBinds.class), "KeyBinds Mode").getValString();
		
        ScaledResolution sr = new ScaledResolution(mc);
        final float scaledWidth = sr.getScaledWidth();
        final float scaledHeight = sr.getScaledHeight();
        
		if (indicat.equalsIgnoreCase("Onetap")) {

			float x = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KeyBinds.class), "PositionX")
					.getValFloat();
			float y = Main.settingsManager.getSettingByName(Main.moduleManager.getModule(KeyBinds.class), "PositionY")
					.getValFloat();
			RenderHelper.drawGradientRect(x, y, x + 95, y + 10, new Color(11, 11, 11, 255).getRGB(),
					new Color(11, 11, 11, 255).getRGB());
			RenderHelper.drawGradientSideways(x, y, x + 95, y + 1, Main.getClientColor().getRGB(),
					new Color(255, 255, 255, 220).getRGB());
			Fonts.smallestpixel_14.drawStringWithShadow("binds", x + 40, y + 4, -1);
			double offsetY = y + 3;
			for (final Feature f : Main.moduleManager.modules) {
				if (f.isToggled() && f.getKey() != 0) {
					Fonts.smallestpixel_15.drawStringWithShadow(f.getName().toLowerCase(), x + 2, offsetY + 10, -1);
					Fonts.smallestpixel_15.drawStringWithShadow("toggled",
							x + 92 - Fonts.neverlose500_14.getStringWidth("toggled"), offsetY + 10, -1);
					offsetY += 8;
				}
			}
		}

			if (indicat.equalsIgnoreCase("Crosshair")) {
				float x = 487;
				float y = 250;
				double offsetY = y + 3;
				for (final Feature f : Main.moduleManager.getModules()) {
					if (f.isToggled() && f.getKey() != 0) {
						Fonts.smallestpixel_14.drawStringWithShadow(f.getName().toLowerCase(), x + 2, offsetY + 10, new Color(255, 82, 65).getRGB());
						offsetY += 5;
					}
				}
			}
		}
	}

