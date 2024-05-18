package me.valk.command.commands;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import me.valk.Vital;
import me.valk.agway.AgwayClient;
import me.valk.command.Command;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabTheme;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabThemeProperties;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabThemeProperties.SimpleTabAlignment;
import me.valk.utils.chat.ChatBuilder;
import me.valk.utils.chat.ChatColor;
import me.valk.utils.render.VitalFontRenderer;
import me.valk.utils.render.VitalFontRenderer.FontObjectType;

public class ThemeCommand extends Command {

	public static int red = 255;
	public static int green = 10;
	public static int blue = 10;
	public static int opacity = 225;
	
	public ThemeCommand() {
		super("Theme", new String[] { "th" }, "Change the tabgui theme");
	}

	public static void setTheme(String theme) {
		String t = AgwayClient.tabTheme.getValue();
		AgwayClient.tabTheme.setValue(theme);
		if (theme.equalsIgnoreCase("Agway")) {
			Vital.getVital().getTabGui().setTabTheme(new SimpleTabTheme(
					VitalFontRenderer.createFontRenderer(FontObjectType.DEFAULT, new Font("CleanStephanieee", Font.PLAIN, 20)),
					new SimpleTabThemeProperties(
							new Color(red, green, blue, opacity),
							Color.WHITE, new Color(0, 0, 0, 180), 0.8f,
							SimpleTabAlignment.CENTER)));
		}  else {
			AgwayClient.tabTheme.setValue(t);
		}
	}

	@Override
	public void onCommand(List<String> args) {
		if (args.size() == 1) {
			String t = AgwayClient.tabTheme.getValue();
			AgwayClient.tabTheme.setValue(args.get(0));
			if (args.get(0).equalsIgnoreCase("Agway")) {
				Vital.getVital().getTabGui().setTabTheme(new SimpleTabTheme(
						VitalFontRenderer.createFontRenderer(FontObjectType.DEFAULT, new Font("CleanStephanieee", Font.PLAIN, 20)),
						new SimpleTabThemeProperties(
								new Color(red, green, blue, opacity),
								Color.WHITE, new Color(0, 0, 0, 180), 0.8f,
								SimpleTabAlignment.CENTER)));

			}  else {
				AgwayClient.tabTheme.setValue(t);
				error("Theme not found. Themes §8: ");

				String[] themes = new String[] { "Agway" };

				ChatBuilder chat = new ChatBuilder().appendText("   ");

				for (String string : themes) {
					chat.appendText(string + " ", ChatColor.GRAY);
				}

				chat.send();
			}
		} else {
			error("Invalid args! Usage : 'Theme [theme]'");
			String[] themes = new String[] { "Agway" };

			ChatBuilder chat = new ChatBuilder().appendText("   ");

			for (String string : themes) {
				chat.appendText(string + " ", ChatColor.GRAY);
			}

			chat.send();
		}
	}
}
