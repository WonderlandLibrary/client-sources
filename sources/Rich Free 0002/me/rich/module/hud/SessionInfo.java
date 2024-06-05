package me.rich.module.hud;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventRender2D;
import me.rich.font.Fonts;
import me.rich.helpers.math.MathHelper;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.render.NameProtect;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.util.ResourceLocation;

public class SessionInfo extends Feature {
    public static StatisticsManager stats;
	public static StatList st = new StatList();
    private final float[] ticks = new float[20];
    
	public SessionInfo() {
		super("SessionInfo", Keyboard.KEY_NONE, Category.HUD);
		ArrayList<String> session = new ArrayList<String>();
		session.add("Nursultan");
		session.add("Hanabi");
		ArrayList<String> theme = new ArrayList<String>();
		theme.add("Black");
		theme.add("White");
		Main.instance.settingsManager.rSetting(new Setting("SessionInfo Mode", this, "Hanabi", session));
		Main.instance.settingsManager.rSetting(new Setting("Theme Mode", this, "White", theme));
	}

	@EventTarget
	public void ebatkopat(EventRender2D render) {
		String session = Main.settingsManager
				.getSettingByName(Main.moduleManager.getModule(SessionInfo.class), "SessionInfo Mode").getValString();
		
		String theme = Main.settingsManager
				.getSettingByName(Main.moduleManager.getModule(SessionInfo.class), "Theme Mode").getValString();
		
		// Astolfo fix.
		int yTotal = 0;
		int i;
		for (i = 0; i < Main.moduleManager.getModules().size(); ++i) {
			yTotal += Fonts.sfui16.getHeight() + 5;
		} // End.

		String server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
		String name = "Name: " + mc.player.getName();

		if (Main.moduleManager.getModule(NameProtect.class).isToggled())
			name = "Name: Protected";
		
		if(session.equalsIgnoreCase("Nursultan")) {

		float str1 = Fonts.neverlose500_15.getStringWidth(name);
		float str2 = Fonts.neverlose500_15.getStringWidth("IP: " + server);

		RenderHelper.drawNewRect(5, 50, (str1 > str2 ? str1 : str2) + 15, 106, new Color(31, 31, 31, 255).getRGB());
		RenderHelper.drawNewRect(8, 64, (str1 > str2 ? str1 : str2) + 12, 64.5, new Color(88, 88, 88, 255).getRGB());
		RenderHelper.drawNewRect(5, 50, (str1 > str2 ? str1 : str2) + 15, 51, Main.getClientColor().getRGB());

		double prevZ = mc.player.posZ - mc.player.prevPosZ;
		double prevX = mc.player.posX - mc.player.prevPosX;
		double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
		double currSpeed = lastDist * 15.3571428571D;

		String speed = String.format("%.2f bps", currSpeed);

		Fonts.neverlose500_18.drawStringWithShadow("Session Info", 8.5, 55.5, -1);
		Fonts.neverlose500_15.drawStringWithShadow("TPS: " + String.format("%.2f", getTickRate()), 8.5, 69, -1);
		Fonts.neverlose500_15.drawStringWithShadow("Speed: " + speed, 8.5, 78, -1);
		Fonts.neverlose500_15.drawStringWithShadow(name, 8.5, 88, -1);
		Fonts.neverlose500_15.drawStringWithShadow("IP: " + server, 8.5, 98, -1);
		}
		
		if(session.equalsIgnoreCase("Hanabi")) {
			
			int color = -1;
			int colortext = -1;
			int colortext1 = -1;
			
			if(theme.equalsIgnoreCase("White")) {
				color = new Color(60, 61, 60).getRGB();
				colortext = new Color(245, 245, 245, 215).getRGB();
				colortext1 = new Color(245, 245, 245, 145).getRGB();
			} else if(theme.equalsIgnoreCase("Black")) {
				color = -1;
				colortext = new Color(33, 33, 33, 215).getRGB();
				colortext1 = new Color(33, 33, 33, 145).getRGB();
			}
			
			double prevZ = mc.player.posZ - mc.player.prevPosZ;
			double prevX = mc.player.posX - mc.player.prevPosX;
			double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
			double currSpeed = lastDist * 15.3571428571D;
			
			float str1 = Fonts.sfui16.getStringWidth(name);
			float str2 = Fonts.sfui16.getStringWidth("IP: " + server);
			String time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
			
			RenderHelper.drawNewRect(5, 50, (str1 > str2 ? str1 : str2) + 45, 103, colortext1);
			RenderHelper.drawNewRect(5, 42, (str1 > str2 ? str1 : str2) + 45, 51, colortext);
			RenderHelper.drawGradientRect(5, 51, (str1 > str2 ? str1 : str2) + 45, 53, new Color(1, 1, 1, 90).getRGB(), new Color(0,0,0,0).getRGB());

			
			
			String speed = String.format("%.2f bps", currSpeed);
			
			Fonts.sfui14.drawString("Session Info", 7, 45, color);
			Fonts.elegant_20.drawString("}", 7.5, 57, color);
			Fonts.sfui15.drawString("Time: " + time, 20, 58, color);
			Fonts.icons_20.drawString("b", 7.5, 69, color);
			Fonts.sfui15.drawString("Move Speed: " + speed, 20, 69, color);
			Fonts.sfui15.drawString(name, 20, 80.5, color);
			Fonts.icons_20.drawString("c", 7.5, 80, color);
			Fonts.sfui15.drawString("IP: " + server, 20, 93, color);
			Fonts.elegant_20.drawString("b", 7.5, 92, color);
			Fonts.elegant_18.drawString("M", (str1 > str2 ? str1 : str2) + 35.5, 44, color);

		}
	}

	private float getTickRate() {
        int tickCount = 0;
        float tickRate = 0.0f;

        for (float tick : this.ticks) {
            if (tick > 0.0f) {
                tickRate += tick;
                tickCount++;
            }
        }

        return MathHelper.clamp((tickRate / tickCount), 0.0f, 20.0f);
    }
	
	@Override
	public void onEnable() {
		super.onEnable();
		NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
	}

	public void onDisable() {
		NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
		super.onDisable();
	}
}