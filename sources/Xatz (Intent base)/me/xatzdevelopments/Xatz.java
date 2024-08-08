package me.xatzdevelopments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONObject;
import org.lwjgl.opengl.Display;

//import com.github.creeper123123321.viafabric.ViaFabric;
import com.thealtening.auth.TheAlteningAuthentication;

import me.xatzdevelopments.alts.AltManager;
import me.xatzdevelopments.changelog.Checker;
import me.xatzdevelopments.clickgui.ClickGUI;
import me.xatzdevelopments.command.CommandManager;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventChat;
import me.xatzdevelopments.events.listeners.EventKey;
import me.xatzdevelopments.irc.IrcManager;
import me.xatzdevelopments.manager.FileManager;
import me.xatzdevelopments.manager.FontManager;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.modules.Module.Category;
import me.xatzdevelopments.modules.combat.*;
import me.xatzdevelopments.modules.movement.*;
import me.xatzdevelopments.modules.player.*;
import me.xatzdevelopments.modules.render.*;
import me.xatzdevelopments.modules.exploits.*;
import me.xatzdevelopments.modules.world.*;
import me.xatzdevelopments.modules.minigames.*;
import me.xatzdevelopments.rpc.DiscordRichPresenceClass;
import me.xatzdevelopments.ui.FXManager;
import me.xatzdevelopments.ui.HUD;
import me.xatzdevelopments.util.LoginUtil;
import me.xatzdevelopments.util.Timer;
import me.xatzdevelopments.util.security.WhitelistUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Session;


public class Xatz {

	public static String name = "Xatz", version = "1", author = "Xatz Dev Team";
	
	public static int currentVersionNum = 1;
	public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
	public static HUD hud = new HUD();
	public static TheAlteningAuthentication theAlteningAuth;
	public static final Timer saveTimer = new Timer();
	private static FileManager fileManager;
	public static String USER = "User";
	public static String alteningToken;
	public static FXManager fxManager = new FXManager();
	public static CommandManager commandManager = new CommandManager();
	public String username;
	public static LoginUtil loginUtil;
	public static FontManager fm;
	//public static CommandManager commandManager = new CommandManager();
	public static boolean overridePitch = false;
	public static boolean overrideOverridePitch = false;
	public static ClickGUI clickgui = new ClickGUI();
    public static boolean LoggedIn;
	public static IrcManager irc;
	private static String IRCChatPrefix = "§7[§3" + "IRC" + "§7] §7";
    private static final Timer inventoryTimer = new Timer();
	private static AltManager altManager;
	private static String clientmultiVersion = "1.8x1";
//    public static int NotifTimer;
//    public static String NotifTitle;
//    public static String NotifDesc;
//    public static int NotifColor;
//    private static CommandManager cmdManager;
    
	
    static {
        Xatz.LoggedIn = false;
//        Xatz.NotifTimer = 9999;
//        Xatz.NotifTitle = "NotifTitle";
//        Xatz.NotifDesc = "NotifDesc";
//        Xatz.NotifColor = -1;
        
    }
	
// Taskbar title related	

	public static void startup() {
		System.out.println("Starting " + name + " v" + version);
		Display.setTitle(name + " v" + version);
		irc = new IrcManager(Minecraft.getMinecraft().session.getUsername());
		irc.connect();
		altManager = new AltManager();
		Checker.serverInfoFetcher.run();
		Checker.doAntiLeak();
		Checker.alertInfoFetcher.run();
// Loading the modules

		
//------------------Combat--------------------	
//		modules.add(new KillAura());
		modules.add(new KillAura2());
		modules.add(new Criticals());
//		modules.add(new TPAura());
		modules.add(new Autoclicker());
		modules.add(new WTap());
		modules.add(new Antibot());
		modules.add(new Criticals2());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
		
//------------------Movement--------------------
		modules.add(new Fly());
//		modules.add(new AutoSprint());
		modules.add(new Speed());
		modules.add(new NoSlow());
		modules.add(new Annoy());
//		modules.add(new Scaffold());
		modules.add(new InventoryMove());
		modules.add(new TargetStrafe());
		modules.add(new Sprint());
		modules.add(new Jesus());
		modules.add(new Step());
		modules.add(new Eagle());
        modules.add(new Longjump());
//		modules.add(new Fly2());
		modules.add(new Phase());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
		
//------------------Player--------------------	
		modules.add(new NoFall());
		modules.add(new DiscordRPCModule());
		modules.add(new Velocity());
		modules.add(new Killsuilts());
		modules.add(new NoRotate());
//		modules.add(new InventoryManager());
		modules.add(new AutoArmor());
		modules.add(new InventroyManager2());
		modules.add(new FastPlace());
		modules.add(new AntiVoid());
		modules.add(new AntiStupid());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
		
//------------------Render--------------------	
		modules.add(new FullBright());
		modules.add(new TargetHUD());
		modules.add(new TargetHud2());
		modules.add(new TabGUI());
		modules.add(new ActiveMods());
		modules.add(new ClickGuiModule());
		modules.add(new Camera());
		modules.add(new Animations());
		modules.add(new Radar());
		modules.add(new Notifications());
		modules.add(new SuperHeroFX());
//		modules.add(new LSD());
//		modules.add(new Chams());
//		modules.add(new PenisESP());
		modules.add(new NameTags());
//		modules.add(new Skeletons());
		modules.add(new NoBobbing());
		//modules.add(new Crumbs());
//		modules.add(new ESP());
		//modules.add(new LSD());
		modules.add(new KeyStrokes());
//		modules.add(new IRC());
//		modules.add(new Nametags2());
		modules.add(new NoHurtcam());
		modules.add(new Australia());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
		
//------------------Exploits--------------------	
		modules.add(new Disabler());
		modules.add(new SigmaDeleter());

//------------------World--------------------
		modules.add(new me.xatzdevelopments.modules.world.Timer());
		modules.add(new ChestStealer());
		modules.add(new Scaffold2());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
//		modules.add(new PlaceHolder());
		
//------------------Minigame--------------------
		modules.add(new Destroyer());
		
// File manager related	
		fm = new FontManager();
		fileManager = new FileManager();
		try {
			fileManager.load();
		} catch (IOException e) {
			System.out.println("File error!");
			e.printStackTrace();
			System.exit(-1);
		}
		
		//try {
            //new ViaFabric().onInitialize();
        //} catch (Exception e) {
            //e.printStackTrace();
        //}
		
		ClickGUI clickgui = new ClickGUI();
		Xatz.getFileMananger().loadSettings();
		System.out.println("Loading Settings");
		Xatz.getFileMananger().loadKeybinds();
		System.out.println("Loading Keybinds");
		Xatz.getFileMananger().loadWinData();
		System.out.println("Loading ClickGUI Panels");
		Xatz.getFileMananger().loadAlts();
		System.out.println("Loading Alts");
		
		if(Xatz.getModuleByName("DiscordRPC").isEnabled())
		DiscordRichPresenceClass.start();
/*        final String var1 = "https://i";
        final String var2 = "ntent";
        final String var3 = "."; // Idk why i added this, maybe for our prot in the future, we will change the intent links to site of our own or something
        final String var4 = "sto";
        final String var5 = "re/product/25/whitelist?hwid=";
        try {
            final HttpsURLConnection connection = (HttpsURLConnection)new URL(String.valueOf(var1) + var2 + var3 + var4 + var5 + WhitelistUtils.HWID()).openConnection();
            connection.addRequestProperty("", "");
            final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final ArrayList response = new ArrayList();
            final String classpathStr = System.getProperty("java.class.path");
            System.out.print(classpathStr);
//            Xatz.cmdManager = new CommandManager();
            Xatz.LoggedIn = false;
            if (Minecraft.getMinecraft().gameSettings.guiScale != 2) {
                Minecraft.getMinecraft().gameSettings.guiScale = 2;
            }
            String currentln;
            while ((currentln = in.readLine()) != null) {
                response.add(currentln);
            }
            if (!response.contains("true") || response.contains("false")) {
                Minecraft.getMinecraft().shutdown();
            }
            Xatz.LoggedIn = true;
        }
        catch (Exception e) {
            Minecraft.getMinecraft().shutdown();
        } */
	} 
	
	public static void onEvent(Event e) {
		if(e instanceof EventChat) {
			commandManager.handleChat((EventChat)e);
		}
		
		for(Module m : modules) {
			if(!m.toggled) 
				continue;
			
			m.onEvent(e);
		}

// Saving settings and keybinds after a certain time

		if(saveTimer.hasTimeElapsed(100000, true)) {
			Xatz.getFileMananger().saveSettings();
			System.out.println("Saving Settings");
			Xatz.getFileMananger().saveKeybinds();
			System.out.println("Saving Keybinds");
			Xatz.getFileMananger().saveWinData();
			System.out.println("Saving ClickGUI Panels");
			Xatz.getFileMananger().saveAlts();
//			System.out.println("Saving Alts");
		}
	}
	

	
		
	public static void keyPress(int key) {
		Xatz.onEvent(new EventKey(key));
		for(Module m: modules) {
			if(m.getKey() == key) {
				m.toggle();
			}
		}
	}
	
	public static void addChatMessage(String message) {
		message = "\2479" + name + "\2477: " + message;
		
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message));
	}
	
	
	public static List<Module> getModulesByCategory(Category c){
		List<Module> modules = new ArrayList<Module>();
		
		for(Module m : Xatz.modules) {
			if(m.category == c) 
				modules.add(m);
			}
			
			return modules;
		}
	
	public static Module getModuleByName(String name) {
		List<Module> modules = new ArrayList<Module>();
		
		for(Module m : Xatz.modules) {
			if(m.name == name) 
				return m;
			}
			
			
		
		System.err.println("Module: " + name + " was not found");
		return null;
	}
	
	 public static Module getModuleByNumber(final int Number) {
	        int ModuleNumber = 0;
	        for (final Module m : Xatz.modules) {
	            if (ModuleNumber == Number) {
	                return m;
	            }
	            ++ModuleNumber;
	        }
	        return null;
	    }
	
	public static String getAlteningToken() {
        return alteningToken;
    }

    public void setAlteningToken(String alteningToken) {
        this.alteningToken = alteningToken;
    }
    
    public static FileManager getFileMananger() {
		return fileManager;
	}

// Saves Settings and Keybinds on close  

    public static void onMCClose() {
    		Xatz.getFileMananger().saveSettings();
    		System.out.println("Saving Settings");
			Xatz.getFileMananger().saveKeybinds();
			System.out.println("Saving Keybinds");
			Xatz.getFileMananger().saveWinData();
			System.out.println("Saving ClickGUI Panels");
			Xatz.getFileMananger().saveAlts();
//			System.out.println("Saving Alts"); Already had a saving message
		
	}

	public static void shutdown() {
		
		DiscordRichPresenceClass.stop();
		
		
	}

	public static void tellPlayer(String message) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("\2478[§9Xatz\2478]\247r: " + message));
		
	}

	public static EntityPlayerSP getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

    public static boolean invCooldownElapsed(long time) {
        return inventoryTimer.hasTimeElapsed(time, true);
    }
    
	public static AltManager getAltManager() {
		return altManager;
	}
	
	public static String getClientmultiVersion() {
		return clientmultiVersion;
	}
	
}
	

