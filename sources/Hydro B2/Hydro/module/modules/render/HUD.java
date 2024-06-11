package Hydro.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Hydro.Client;
import Hydro.event.events.EventRenderGUI;
import Hydro.module.Module;
import Hydro.util.font.FontUtil;
import Hydro.util.render.ColourUtils.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

public class HUD {

	public Minecraft mc = Minecraft.getMinecraft();
	public FontRenderer fr = mc.fontRendererObj;
	public ScaledResolution sr = new ScaledResolution(mc);

	public List<Long> packets = new ArrayList<Long>();

	public static int rainbow(float seconds, float saturation, float brightness) {
		float hue = (System.currentTimeMillis() % (int) (seconds * 1000)) / (float) (seconds * 1000);
		int color = Color.HSBtoRGB(hue, saturation, brightness);
		return color;
	}

	public static int offsetRainbow(float seconds, float saturation, float brightness, long offset) {
		float hue = ((System.currentTimeMillis()
				+ (Client.instance.settingsManager.getSettingByName("ArrayDirMode").getValString().equals("Up") ? offset
						: -offset))
				% (int) (seconds * 1000)) / (float) (seconds * 1000);
		int color = Color.HSBtoRGB(hue, saturation, brightness);

		return color;
	}

	public static class ModuleComparator implements Comparator<Module> {

		@Override
		public int compare(Module args0, Module args1) {
			if (Client.instance.fontManager.getFont("regular 20")
					.getWidth(args0.displayName) > Client.instance.fontManager.getFont("regular 20")
							.getWidth(args1.displayName)) {
				return -1;
			}
			if (Client.instance.fontManager.getFont("regular 20")
					.getWidth(args0.displayName) < Client.instance.fontManager.getFont("regular 20")
							.getWidth(args1.displayName)) {
				return 1;
			}
			return 0;
		}

	}

	public void render() {

		if (mc.gameSettings.showDebugInfo)
			return;

		// Draw.color(0x90000000);
		// Draw.drawRoundedRect(19, 4, 46, 21, 4);
		Client.instance.fontManager.getFont("regular 20")
				.drawString(EnumChatFormatting.RED + "H" + EnumChatFormatting.WHITE + "ydro", 8, 8, -1);

		Collections.sort(Client.moduleManager.modules, new ModuleComparator());

		double arrayScale = Client.instance.settingsManager.getSettingByName("ArrayScale").getValDouble();

		int count = 0;
		for (Hydro.module.Module m : Client.moduleManager.getEnabledModules()) {
			if (!m.visible)
				continue;

			double idk = (arrayScale + 2);

			if (Client.instance.settingsManager.getSettingByName("ArrayBar").getValBoolean())
				Gui.drawRect(mc.displayWidth / 2, count * (9 + arrayScale) - idk, mc.displayWidth / 2 - 3,
						6 + 9 + count * (9 + arrayScale) - idk + arrayScale, offsetRainbow(4, 0.8f, 1, count * 150L)); // 2261e0
			if (Client.instance.settingsManager.getSettingByName("ArrayBG").getValBoolean())
				Gui.drawRect(
						(int) (mc.displayWidth / 2
								- Client.instance.fontManager.getFont("regular 20").getWidth(m.displayName)
								- (Client.instance.settingsManager.getSettingByName("ArrayBar").getValBoolean() ? 7
										: 2)),
						count * (9 + arrayScale) - arrayScale + 0,
						mc.displayWidth / 2
								- (Client.instance.settingsManager.getSettingByName("ArrayBar").getValBoolean() ? 3
										: 0),
						6 + 9 + count * (9 + arrayScale) - 2, Colors.BLACK);
			Client.instance.fontManager.getFont("regular 20").drawString(m.displayName,
					mc.displayWidth / 2 - Client.instance.fontManager.getFont("regular 20").getWidth(m.displayName)
							- (Client.instance.settingsManager.getSettingByName("ArrayBar").getValBoolean() ? 5 : 2),
					(float) (1 + count * (9 + arrayScale) - 4), offsetRainbow(4, 0.8f, 1, count * 150L));

			count++;
		}

		Client.onEvent(new EventRenderGUI(sr));

		// Extra

		// FontUtil.arrayList.drawString("Packets: " + getPacketCount(), 10, 30, -1);
		// //TODO add packet per second counter

	}

	public int getPacketCount() {
		final long time = System.currentTimeMillis();
		this.packets.removeIf(aLong -> aLong + 1000 < time);
		return this.packets.size();
	}

}
