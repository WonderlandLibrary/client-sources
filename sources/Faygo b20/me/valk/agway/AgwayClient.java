package me.valk.agway;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.List;

import me.valk.Client;
import me.valk.SecurityUtils;
import me.valk.Vital;
import me.valk.agway.gui.AgwayClientOverlay;
import me.valk.agway.gui.altmanager.AltManager;
import me.valk.agway.gui.altmanager.GuiAddAlt;
import me.valk.command.commands.ThemeCommand;
import me.valk.event.EventSystem;
import me.valk.overlay.defaultOverlays.TabGuiOverlay;
import me.valk.overlay.tabGui.TabGui;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabTheme;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabThemeProperties;
import me.valk.overlay.tabGui.theme.themes.simple.SimpleTabThemeProperties.SimpleTabAlignment;
import me.valk.utils.chat.ChatColor;
import me.valk.utils.render.VitalFontRenderer;
import me.valk.utils.render.VitalFontRenderer.FontObjectType;
import me.valk.utils.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;

public class AgwayClient extends Client {

	public static AltManager altManager;
	private TabGui tabGui;
	public static boolean hide = false;
	private boolean hud = true;
	public static boolean useFont = true;
	public static boolean red = true;
	public static Value<String> tabTheme = new Value<String>("theme", "Faygo");

	public AgwayClient() {
		super("Faygo", 20, new String[] { "Xtasy and Nefarious Intent" }, new ClientData(ChatColor.RED));
		addValue(tabTheme);
		GuiAddAlt.loadAlts();
	}

	@Override
	public void start() {
		EventSystem.register(this);
        //SecurityUtils.checkUUID("https://pastebin.com/raw/faT3zpZn");
		tabGui = new TabGui(new SimpleTabTheme(
				VitalFontRenderer.createFontRenderer(FontObjectType.CFONT, new Font("CleanStephanieee", Font.PLAIN, 18)),
				new SimpleTabThemeProperties(new Color(135, 13, 55), Color.WHITE, new Color(35, 35, 35, 200), 0.8f,
						SimpleTabAlignment.CENTER)));
		Vital.getVital().setTabGui(tabGui);

		ThemeCommand.setTheme(tabTheme.getValue());

		Vital.getManagers().getOverlayManager().addContent(new AgwayClientOverlay());
		Vital.getManagers().getOverlayManager().addContent(new TabGuiOverlay());
		Vital.getManagers().getModDataManager().load();
        GuiAddAlt.loadAlts();
	
	}

	public static File getDirectory() {
		return AgwayClient.getDirectory();
	}

}
