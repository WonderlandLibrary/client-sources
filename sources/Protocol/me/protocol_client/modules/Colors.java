package me.protocol_client.modules;

import java.awt.Color;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventTick;

public class Colors extends Module {

	public Colors() {
		super("Colors", "colors", 0, Category.OTHER, new String[] { "color" });
		setShowing(false);
	}

	@EventTarget
	public void onTick(EventTick event) {
		hue++;
		float v = 70;
		if (hue > 270) {
			hue = 0;
		}
		if (rainbow)
			Protocol.setColor(java.awt.Color.getHSBColor(hue / 270f, 70f / 100f, v / 100));
	}

	private static java.awt.Color	color		= Color.BLUE;
	public static boolean			rainbow		= false;
	private static Color			colorsaved	= Color.BLUE;
	public static float				hue			= 0;

	public void toggleRainbow() {
		rainbow = !rainbow;
		if (!rainbow) {
			Protocol.setColor(colorsaved);
		}
		if (rainbow) {
			colorsaved = Protocol.getColor();
		}
	}

	public void onEnable() {
		EventManager.register(this);
	}

	public void runCmd(String s) {
		try {
			if (s.startsWith("primary")) {
				String line2 = s.split(" ")[1];
				if (line2.length() > 1 || line2.length() < 1) {
					Wrapper.tellPlayer("\2477Invalid color, please use a color used in minecraft. Example: -color primary " + Protocol.primColor + "a");
				} else {
					Protocol.primColor = "\247" + line2;
					Wrapper.tellPlayer("\2477This is your new color: " + Protocol.primColor + "|||");
				}
				return;
			}
			if (s.startsWith("secondary")) {
				String line2 = s.split(" ")[1];
				if (line2.length() > 1 || line2.length() < 1) {
					Wrapper.tellPlayer("\2477Invalid color, please use a color used in minecraft. Example: -color sec " + Protocol.secColor + "a");
				} else {
					Protocol.secColor = "\247" + line2;
					Wrapper.tellPlayer("\2477This is your new color: " + Protocol.secColor + "|||");
				}
				return;
			}
			Wrapper.tellPlayer("\2477Invalid arguments. -" + Protocol.primColor + "color \2477<Primary/Secondary> <Color Code>");
		} catch (Exception e) {
			Wrapper.invalidCommand("Color");
			Wrapper.tellPlayer("\2477-" + Protocol.primColor + "color \2477<Primary/Secondary> <Color Code>");
		}
	}
}
