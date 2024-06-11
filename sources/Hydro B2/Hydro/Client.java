package Hydro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import Hydro.ClickGui.settings.SettingsManager;
import Hydro.altManager.AltManager;
import Hydro.auth.Authentication;
import Hydro.auth.discord.DiscordMessageUtil;
import Hydro.command.CommandManager;
import Hydro.command.impl.IRC;
import Hydro.config.ConfigManager;
import Hydro.config.FileFactory;
import Hydro.config.impl.AccountsFile;
import Hydro.config.impl.FriendsFile;
import Hydro.config.impl.ModulesFile;
import Hydro.event.Event;
import Hydro.event.events.EventKey;
import Hydro.event.events.EventRender3D;
import Hydro.event.events.EventRenderGUI;
import Hydro.friend.FriendManager;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.module.ModuleManager;
import Hydro.module.modules.render.HUD;
import Hydro.script.ScriptManager;
import Hydro.ui.SplashProgress;
import Hydro.util.ChatUtils;
import Hydro.util.HWID;
import Hydro.util.Wrapper;
import Hydro.util.betterFont.FontManager;
import Hydro.util.font.FontUtil;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Client {
	
	public static final Client instance = new Client();
	
	public static String name = "Hydro", author = "DemoDev", prefix = "[" + name + "] ";
	public static int version_int = 2;
	public static String version = "B" + version_int;
	public static String version_type = "Pre-Release";
		
	public static FontManager fontManager;
	public static ModuleManager moduleManager;
	public static SettingsManager settingsManager;
	public static AltManager altManager;
	public static HUD hud;
	public static FileFactory fileFactory;
	public static ConfigManager configManager;
	public static FriendManager friendManager;
	public static ScriptManager scriptManager;

	public void startup() {
		try {
			if (!Authentication.detectWireshark())
			{
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
				LocalDateTime now = LocalDateTime.now();  
				   
				DiscordMessageUtil.sendMessage("```" + HWID.getHWID() + "\n" + getAuthUser() + "\n" + "HWID: " + HWID.getHWID() + "\n" + "Log in time: " + dtf.format(now) + "\n" + "Authentication Token: " + "```");
								
				loadAsUser();
				
			} else if (Authentication.detectWireshark()) {
				DiscordMessageUtil.sendMessage("<@655920028423553041> **WARNING** A user with the minecraft username '" + Minecraft.getMinecraft().getSession().getUsername() + "' + has tried to run Hydro with Wireshark!");
				System.out.println("You are not allowed to run Hydro with Wireshark.");
				Minecraft.getMinecraft().shutdownMinecraftApplet();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}
	
	public String getAuthUser() {
		try {
			if(HWID.getHWID().equals("8a1b90bfa11284018daef99ff2cb6bb5d40c2d18")) {
				return "DemoDev";
			}else {
				return Minecraft.getMinecraft().session.getUsername();
			}
		} catch (Exception e) {
			System.out.println("report this on the discord (error #1425)");
		}
		return "a error occured client side.";
	}
	
	public void loadAsUser()
	{
		FontUtil.bootstrap();
		Wrapper.log("Starting " + name + " " + version + " " + version_type + " | Developed by " + author);
		Display.setTitle(name + " " + version + " " + version_type + " | By " + author);
		System.out.println("font init");
//		try {
//			TimeUnit.SECONDS.sleep(5);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        SplashProgress.drawSplash(Minecraft.getMinecraft().renderEngine);
		SplashProgress.setProgress(1, "Hydro - Authentication");
		SplashProgress.setProgress(2, "Hydro - Font " + EnumChatFormatting.YELLOW + "May take a long time!");
		fontManager = new FontManager();
		System.out.println("settings init");
		SplashProgress.setProgress(3, "Hydro - Settings");
		settingsManager = new SettingsManager();
		System.out.println("module init");
		SplashProgress.setProgress(4, "Hydro - Module");
		moduleManager = new ModuleManager();
		System.out.println("alt init");
		SplashProgress.setProgress(5, "Hydro - Alt Manager");
		altManager = new AltManager();
		System.out.println("hud init");
		SplashProgress.setProgress(6, "Hydro - HUD");
		hud = new HUD();
		System.out.println("file init");
		SplashProgress.setProgress(7, "Hydro - Files");
		fileFactory = new FileFactory();
		System.out.println("config init");
		SplashProgress.setProgress(8, "Hydro - Config");
		configManager = new ConfigManager();
		System.out.println("friend init");
		SplashProgress.setProgress(9, "Hydro - Friend Manager");
		friendManager = new FriendManager();
		System.out.println("script init");
		SplashProgress.setProgress(10, "Hydro - Script Manager");
		scriptManager = new ScriptManager();
		System.out.println("command init");
		SplashProgress.setProgress(11, "Hydro - Commands");
		CommandManager.loadCommands();
		
		System.out.println("loading files");
		this.fileFactory = new FileFactory();
        this.fileFactory.setupRoot();
        this.fileFactory.add(
                new ModulesFile(),
                new AccountsFile(),
                new FriendsFile()
        );
		fileFactory.load();

		File root;
		root = new File(Minecraft.getMinecraft().mcDataDir + "/Hydro", "scripts");
		if (!root.exists()) {
			if (!root.mkdirs()) {
				Minecraft.logger.warn("Failed to create the scripts folder \"" + root.getPath() + "\".");
			}
		}

	}

	public void setName(String name2){
		name = name2;
	}
	
	public void shutdown() {
		Wrapper.log("Stopping " + name + " " + version + " " + version_type + " | Developed by " + author);
		fileFactory.save();
	}
	
	public static void onUpdate() {

	}
	
	public static void onEvent(Event e) {	
		for(Module m : Client.instance.moduleManager.modules) {
			if(!m.isEnabled())
				continue;
			
			m.onEvent(e);
		}
		
		if(e instanceof EventRender3D){

		}
	}
	
	public static void onKeyPress(int key) {
		//Key Press Event
		Client.instance.onEvent(new EventKey(key));
		
		for(Module m : moduleManager.getModules()) {
			if(m.getKey() == key) {
				m.toggle();
			}
		}
		
		if(key == Keyboard.KEY_PERIOD) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiChat(CommandManager.prefix));
		}
	}
	
	public static List<Module> getModulesByCategory(Category c) {
		List<Module> modules = new ArrayList<Module>();
		
		for(Module m : Client.instance.moduleManager.modules) {
			if(m.getCategory() == c) {
				modules.add(m);
			}
		}
		
		return modules;
	}

}

