package me.rich.module.hud;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import clickgui.setting.Setting;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.font.Fonts;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.module.render.NameProtect;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.gui.Gui;

public class Watermark extends Feature {

	public Watermark() {
		super("Watermark", Keyboard.KEY_NONE, Category.HUD);
		ArrayList<String> watermark = new ArrayList<>();
		watermark.add("Nursultan");
		watermark.add("Novoline");
		watermark.add("FDP");
		watermark.add("Onetap");
		
		Main.instance.settingsManager.rSetting(new Setting("WaterMark Mode", this, "Nursultan", watermark));
		Main.settingsManager.rSetting(new Setting("ClientName", this, true));
	}

	@EventTarget
	public void ebatkopat(Event2D render) {
		// Astolfo fix.
		int yTotal = 0;
		int i;
		for (i = 0; i < Main.moduleManager.getModules().size(); ++i) {
			yTotal += Fonts.sfui16.getHeight() + 5;
		} // End.

		String watermark = Main.settingsManager.getSettingByName("WaterMark Mode").getValString();
		String name = mc.player.getName();
		
		if(Main.moduleManager.getModule(NameProtect.class).isToggled())
			name = "Protected";
		
		this.setModuleName("Watermark §7[" + watermark + "]");
		
		if (watermark.equalsIgnoreCase("Novoline")) {
			String time = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());  
			int offset = Fonts.sfui16.getStringWidth(Main.name + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + time + ChatFormatting.GRAY + ")");
			Fonts.sfui18.drawStringWithShadow(Main.name + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + time + ChatFormatting.GRAY + ")", 3, 4, Main.getClientColor().getRGB());
		}
		if (watermark.equalsIgnoreCase("FDP")) {
			if(Main.settingsManager.getSettingByName("ClientName").getValBoolean()) {
			String time = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
			String text = "liquidbounce.net | " + name + " | " + time;
			Gui.drawRect(5, 5, Fonts.roboto_16.getStringWidth(text) + 9, 21, new Color(37, 37, 37, 255).getRGB());
			Gui.drawRect(6, 6, Fonts.roboto_16.getStringWidth(text) + 8, 8.5f, Main.getClientColor().getRGB());
			Gui.drawRect(5, 9f, Fonts.roboto_16.getStringWidth(text) + 9, 9.5f, new Color(20, 20, 20, 100).getRGB());
			Fonts.roboto_16.drawStringWithShadow(text, 7, 13, -1);
			} else {
				String time = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
				String text = name + " | " + time;
				Gui.drawRect(5, 5, Fonts.roboto_16.getStringWidth(text) + 9, 21, new Color(37, 37, 37, 255).getRGB());
				Gui.drawRect(6, 6, Fonts.roboto_16.getStringWidth(text) + 8, 8.5f, Main.getClientColor().getRGB());
				Gui.drawRect(5, 8.5f, Fonts.roboto_16.getStringWidth(text) + 9, 9f, new Color(20, 20, 20, 100).getRGB());
				Fonts.roboto_16.drawStringWithShadow(text, 7, 13, -1);
			}
		}
		if (watermark.equalsIgnoreCase("Nursultan")) {
				String server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
				if(Main.settingsManager.getSettingByName("ClientName").getValBoolean()) {
				String time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
				String text = "Rich Free | " + name + " | " + server + " | " + mc.getDebugFPS() + " fps" + " | " + mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime() + "ms" + " | " + time;
				Gui.drawRect(5, 6, Fonts.sfui16.getStringWidth(text) + 9, 19, new Color(31, 31, 31, 255).getRGB());
				Gui.drawRect(5, 19, Fonts.sfui16.getStringWidth(text) + 9, 20f, Main.getClientColor().getRGB());
				Gui.drawRect(5, 6.5f, Fonts.sfui16.getStringWidth(text) + 9, 7f, new Color(20, 20, 20, 100).getRGB());
				Fonts.sfui16.drawStringWithShadow(text, 7, 10.5, -1);
				} else {
					String time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
					String text = name + " | " + server + " | " + mc.getDebugFPS() + " fps" + " | " + mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime() + "ms" + " | " + time;
					Gui.drawRect(5, 6, Fonts.sfui16.getStringWidth(text) + 9, 19, new Color(31, 31, 31, 255).getRGB());
					Gui.drawRect(5, 19, Fonts.sfui16.getStringWidth(text) + 9, 20f, Main.getClientColor().getRGB());
					Gui.drawRect(5, 18.5f, Fonts.sfui16.getStringWidth(text) + 9, 19f, new Color(20, 20, 20, 100).getRGB());
					Fonts.sfui16.drawStringWithShadow(text, 7, 10.5, -1);
				}
			}
		
		if (watermark.equalsIgnoreCase("Onetap")) {
			String server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
			String time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
			String ping = mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime() + "";
			if(Main.settingsManager.getSettingByName("ClientName").getValBoolean()) {
			RenderHelper.drawGradientRect(5, 185, 95, 198, new Color(22, 22, 22, 205).getRGB(), new Color(21, 21, 21, 0).getRGB());
			RenderHelper.drawGradientSideways(5, 184, 95, 185, Main.getClientColor().getRGB(), new Color(255, 255, 255, 220).getRGB());
			Fonts.roboto_13.drawStringWithShadow("onetap.com", 31, 190, -1);
			Fonts.roboto_13.drawStringWithShadow("user", 9, 200, -1);
			Fonts.roboto_13.drawStringWithShadow("[" + mc.player.getName() + "]", 93 - Fonts.roboto_13.getStringWidth("[" + mc.player.getName() + "]"), 200, -1);
			Fonts.roboto_13.drawStringWithShadow("server", 9, 208, -1);
			Fonts.roboto_13.drawStringWithShadow("[" + server + "]", 93 - Fonts.roboto_13.getStringWidth("[" + server + "]"), 208, -1);
			Fonts.roboto_13.drawStringWithShadow("latency", 9, 216, -1);
			Fonts.roboto_13.drawStringWithShadow("[" + ping + "]", 93 - Fonts.roboto_13.getStringWidth("[" + ping + "]"), 216, -1);
			Fonts.roboto_13.drawStringWithShadow("time", 9, 224, -1);
			Fonts.roboto_13.drawStringWithShadow("[" + time + "]", 93 - Fonts.roboto_13.getStringWidth("[" + time + "]"), 224, -1);
			}
			}
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
