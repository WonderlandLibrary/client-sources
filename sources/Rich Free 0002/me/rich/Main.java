package me.rich;

import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.lwjgl.opengl.Display;

import clickgui.ClickGuiScreen;
import clickgui.setting.SettingsManager;
import me.rich.changelogs.ChangeLogMngr;
import me.rich.config.Config;
import me.rich.event.EventManager;
import me.rich.event.EventTarget;
import me.rich.event.events.EventKey;
import me.rich.font.Fonts;
import me.rich.helpers.command.CommandManager;
import me.rich.helpers.render.ColorHelper;
import me.rich.module.FeatureDirector;
import me.rich.module.hud.FeatureList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Main {

	public static Main instance = new Main();
	public static String name = "Rich Free", version = "#0002";
	public static EventManager eventManager;
	public static FeatureDirector moduleManager;
	public static SettingsManager settingsManager;
    public static CommandManager commandManager;
	public static ChangeLogMngr changeLogMngr;
    public static ClickGuiScreen clickGui1;
	public static Config config;
	
	public static void startClient()  throws IOException {
		
		try {
			Desktop.getDesktop().browse(new URI("https://vk.com/richsense"));
			Desktop.getDesktop().browse(new URI("https://discord.gg/uBRAbYNt"));
		} catch (URISyntaxException e) {
			System.out.println("Error loading site.");
			e.printStackTrace();
		}
		
		// Managers.
        (changeLogMngr = new ChangeLogMngr()).setChangeLogs();
		settingsManager = new SettingsManager();
        commandManager = new CommandManager();
		eventManager = new EventManager();
		moduleManager = new FeatureDirector();
        clickGui1 = new ClickGuiScreen();
        config = new Config();
       
        
		// Minecraft name.
		Display.setTitle(name);
		
		EventManager.register(instance);
	}
	

	
	public static void stopClient() {
		EventManager.unregister(instance);
	}
	public static ChangeLogMngr getChangeLogMngr() {
        return  instance.changeLogMngr;
    }
	
	public static void msg(String s, boolean prefix) {
		s = (prefix ? "§f[§c" + "RC" + "§f]" + "§7: " : "") + s;
		Minecraft.getMinecraft().player.addChatMessage(new TextComponentTranslation(s.replace("&", "??")));
	}
	
	@EventTarget
	public void onKey(EventKey event) {
		moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
	}
	
	public static Color getClientColor() {
		
	       Color color = Color.white;
        String mode = Main.settingsManager.getSettingByName("FeatureList").getValString();
	        float yDist = 4.0f;
         double time = Main.settingsManager.getSettingByName("ColorTime").getValDouble();
         int[] counter = new int[]{1};

	        int yTotal = 0;
	        for (int i = 0; i < 30; ++i) {
	            yTotal += Fonts.roboto_16.getHeight() + 5;
	            
	        }
	        switch (mode) {
	            case "Candy": {
	                color = ColorHelper.TwoColoreffect(new Color(65, 179, 255), new Color(248, 54, 255), (double)Math.abs(System.currentTimeMillis() / (long) time) / 100.0D + 3.0D * (double)counter[0] * 2.55D / 60.0D);
	                break;
	            }
	            case "Rainbow": {
	                color = ColorHelper.rainbow2((int) (1 * 200 * Main.settingsManager.getSettingByName("Rainbow Spread").getValDouble()),(float) Main.settingsManager.getSettingByName("Saturation").getValDouble(), 1.0f);
	                break;
	            }
	            case "Astolfo": {
	                color = ColorHelper.astolfoColors45((float) 0, (float) yTotal, (float) Main.settingsManager.getSettingByName(Main.moduleManager.getModule(FeatureList.class),"Saturation").getValDouble());
	                break;
	            }
	            case "FDP": {
	                color = ColorHelper.TwoColoreffect((new Color(42, 191, 255)), new Color(38, 116, 149), (double)Math.abs(System.currentTimeMillis() / (long) time) / 100.0D + 3.0D * (double)counter[0] * 2.55D / 60.0D);
	                break;
	            }
	            case "Blood": {
	                color = ColorHelper.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (counter[0] * 2.55) / 60);
	                break;
	            }
	            case "Pinky": {
	                color = ColorHelper.TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (counter[0] * 2.55) / 90);
	                break;
	            }
	            case "Toxic": {
	                color = ColorHelper.TwoColoreffect(new Color(90, 200, 79), new Color(30, 120, 20), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (counter[0] * 2.55) / 90);
	                break;
	            }

	        }
	        return color;
	    }
}