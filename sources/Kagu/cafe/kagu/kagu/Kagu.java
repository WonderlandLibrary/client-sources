package cafe.kagu.kagu;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL13;

import cafe.kagu.kagu.commands.CommandManager;
import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.eventBus.Event.EventPosition;
import cafe.kagu.kagu.eventBus.impl.EventCheatProcessTick;
import cafe.kagu.kagu.eventBus.impl.EventCheatRenderTick;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.managers.AltManager;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.managers.KeybindManager;
import cafe.kagu.kagu.managers.SessionManager;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.prot.BasicProcessLookupCheck;
import cafe.kagu.kagu.prot.LoadedClassesCheck;
import cafe.kagu.kagu.prot.Note;
import cafe.kagu.kagu.prot.OffAuthScreensWithoutLoginCheck;
import cafe.kagu.kagu.prot.ui.GuiAuthNeeded;
import cafe.kagu.kagu.ui.Hud;
import cafe.kagu.kagu.ui.clickgui.GuiCsgoClickgui;
import cafe.kagu.kagu.ui.clickgui.GuiDropdownClickgui;
import cafe.kagu.kagu.ui.ghost.GhostUi;
import cafe.kagu.kagu.ui.gui.GuiDefaultMainMenu;
import cafe.kagu.kagu.ui.gui.MainMenuHandler;
import cafe.kagu.kagu.utils.ClickGuiUtils;
import cafe.kagu.kagu.utils.InventoryUtils;
import cafe.kagu.kagu.utils.MovementUtils;
import cafe.kagu.kagu.utils.OSUtil;
import cafe.kagu.kagu.utils.RotationUtils;
import cafe.kagu.kagu.utils.SpoofUtils;
import cafe.kagu.kagu.utils.StencilUtil;
import cafe.kagu.kagu.utils.WorldUtils;
import cafe.kagu.keyauth.KeyAuth;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * @author lavaflowglow
 *
 */
public class Kagu {
	
	private static String name = "Kagu";
	private static String version = "0.0.2";
	private static String loggedInUser = null;
	
	private static Logger logger = LogManager.getLogger();
	
//	public static final char UNIT_SEPARATOR = (char)31;
//	public static final char RECORD_SEPARATOR = (char)30;
//	public static final char GROUP_SEPARATOR = (char)29;
	
	public static String UNIT_SEPARATOR = "﷽"; // final removed from these so people can't find the kagu class via string lookup
	public static String GROUP_SEPARATOR = "🐀";
	public static String RECORD_SEPARATOR = "👺";
	
	private static int activeTexture = GL13.GL_TEXTURE0;
	private static boolean firstStageInit = false;
	
	private static final EventBus EVENT_BUS = new EventBus();
	private static final KeyAuth KEY_AUTH = new KeyAuth("ZvEPlLo1aX", name, "6c24f57efcd23dc83c2f69d214d5e29ff06b3abcb242f9a5bba3a908be1e0487", version);
	private static final ModuleManager MODULE_MANAGER = new ModuleManager();
	
	// Only used if the font texture size is greater than the size limit
	public static final char[] FONT_RENDERER_SUPPORTED_CHARACTERS = new char[] {
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 
			'q', 'w', 'e', 'r', 't', 'y', 'y', 'u', 'i', 'o', 'p', 
			'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 
			'z', 'x', 'c', 'v', 'b', 'n', 'm', 
			'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 
			'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 
			'Z', 'X', 'C', 'V', 'B', 'N', 'M', 
			'`', '~', '!', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', 
			'[', '{', '}', ']', '\\', '|', 
			';', ':', '\'', '"', 
			'<', '>', ',', '.', '/', '?', ' '
	};
	
	private static ResourceLocation skinOverride = null;
	
	/**
	 * The start method, everything should be initialized here
	 */
	public static void start() {
		
		if (!firstStageInit) {
			// Start the file manager
			logger.info("Starting the file manager...");
			FileManager.start();
			logger.info("Started the file manager");
			
			// Start the spoof utils
			logger.info("Starting the spoof utils...");
			SpoofUtils.start();
			logger.info("Started the spoof utils");
			
			// Start the module manager
			logger.info("Starting the module manager...");
			MODULE_MANAGER.start();
			logger.info("Started the module manager");
			firstStageInit = true;
		}
		
		KEY_AUTH.initialize(msg -> {
			System.exit(Note.WINAUTH_APP_DISABLED);
		}, msg -> {
			System.exit(Note.WINAUTH_REQUEST_FAILED);
		}, msg -> {
			Runtime.getRuntime().halt(Note.WINAUTH_RESPONSE_TAMPERED);
		});
		
		KEY_AUTH.checkBlacklist(msg -> {
			System.exit(Note.WINAUTH_REQUEST_FAILED);
		}, msg -> {
			Runtime.getRuntime().halt(Note.WINAUTH_RESPONSE_TAMPERED);
		}, msg -> {
			KEY_AUTH.log("Blacklisted user tried to run cheat", m -> {}, m -> {});
			try {
				Desktop.getDesktop().browse(URI.create("https://www.youtube.com/watch?v=Jube3tBCCfQ"));
			} catch (IOException e) {}
			Runtime.getRuntime().halt(Note.WINAUTH_BLACKLISTED);
		});
		
		BasicProcessLookupCheck.start(); // Basic process checks
		LoadedClassesCheck.start(); // More prot
		OffAuthScreensWithoutLoginCheck.start(); // More prot
		
		// Check if logged in
		if (!KEY_AUTH.isLoggedIn()) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiAuthNeeded());
			return;
		}
		
		logger.info("Starting " + name + " v" + version + " :3");
		
		// Start the stencil util
		logger.info("Starting the stencil util...");
		StencilUtil.start();
		logger.info("Started the stencil util");
		
		// Start the command manager
		logger.info("Starting the command manager...");
		CommandManager.start();
		logger.info("Started the command manager");
		
		// Start the keybind manager
		logger.info("Starting the keybind manager...");
		KeybindManager.start();
		logger.info("Started the keybind manager");
		
		// Start the alt manager
		logger.info("Starting the alt manager...");
		AltManager.start();
		logger.info("Started the alt manager");
		
		// Start the session manager
		logger.info("Starting the session manager...");
		SessionManager.start();
		logger.info("Started the session manager");
		
		// Load fonts
		logger.info("Loading fonts...");
		FontUtils.start();
		logger.info("Loaded fonts");
		
		// Start the clickgui
		logger.info("Starting the clickguis...");
		GuiCsgoClickgui.getInstance().start();
		GuiDropdownClickgui.getInstance().start();
		logger.info("Started the clickguis");
		
		// Load the main menu
		logger.info("Loading the main menu handler...");
		MainMenuHandler.getMainMenu();
		logger.info("Loaded the main menu handler");
		
		// Load the main menu
		logger.info("Loading the main menus...");
		GuiDefaultMainMenu.start();
		logger.info("Loaded the main menus");
		
		// Load the movement utils
		logger.info("Loading the movement utils...");
		MovementUtils.start();
		logger.info("Loaded the movement utils");
		
		// Load the rotation utils
		logger.info("Loading the rotation utils...");
		RotationUtils.start();
		logger.info("Loaded the rotation utils");
		
		// Load the world utils
		logger.info("Loading the world utils...");
		WorldUtils.start();
		logger.info("Loaded the world utils");
		
		// Load the inventory utils
		logger.info("Loading the inventory utils...");
		InventoryUtils.start();
		logger.info("Loaded the inventory utils");
		
		// Load the clickgui utils
		logger.info("Loading the clickgui utils...");
		ClickGuiUtils.start();
		logger.info("Loaded the clickgui utils");
		
		// Hook the hud
		logger.info("Hooking the hud...");
		EVENT_BUS.subscribe(new Hud());
		logger.info("Hooked the hud");
		
		// Start a cheat loop thread
		logger.info("Starting the cheat loop threads...");
		long cheatLoopDelay = 1000 /* One second */ / 64 /* Tick rate */;
		new Timer("Cheat Render Event", true).scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// Kagu hook
				{
					EventCheatRenderTick eventCheatTick = new EventCheatRenderTick(EventPosition.PRE);
					eventCheatTick.post();
					if (eventCheatTick.isCanceled())
						return;
				}
				// Kagu hook
				{
					EventCheatRenderTick eventCheatTick = new EventCheatRenderTick(EventPosition.POST);
					eventCheatTick.post();
				}
			}
		}, 0, cheatLoopDelay);
		new Timer("Cheat Process Event", true).scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// Kagu hook
				{
					EventCheatProcessTick eventCheatTick = new EventCheatProcessTick(EventPosition.PRE);
					eventCheatTick.post();
					if (eventCheatTick.isCanceled())
						return;
				}
				// Kagu hook
				{
					EventCheatProcessTick eventCheatTick = new EventCheatProcessTick(EventPosition.POST);
					eventCheatTick.post();
				}
			}
		}, 0, cheatLoopDelay);
		logger.info("Started the cheat loop threads");
		
		// Start the ghost ui
		logger.info("Starting the obs proof ui (Windows OS only)...");
		if (OSUtil.isWindows()) {
			GhostUi.start();
			logger.info("Started the obs proof ui");
		}else {
			logger.info("Failed to start obs proof ui");
		}
		
		logger.info(name + " v" + version + " has been started");
		
	}

	/**
	 * @return the name
	 */
	public static String getName() {
		return name;
	}

	/**
	 * @return the version
	 */
	public static String getVersion() {
		return version;
	}

	/**
	 * @return the activeTexture
	 */
	public static int getActiveTexture() {
		return activeTexture;
	}

	/**
	 * @param activeTexture the activeTexture to set
	 */
	public static void setActiveTexture(int activeTexture) {
		Kagu.activeTexture = activeTexture;
	}
	
	/**
	 * @return the eventBus
	 */
	public static EventBus getEventBus() {
		return EVENT_BUS;
	}
	
	/**
	 * @return the loggedIn
	 */
	public static boolean isLoggedIn() {
		return KEY_AUTH.isLoggedIn();
	}
	
	/**
	 * @return the keyAuth
	 */
	public static KeyAuth getKeyAuth() {
		return KEY_AUTH;
	}
	
	/**
	 * @return the skinOverride
	 */
	public static ResourceLocation getSkinOverride() {
		return skinOverride;
	}
	
	/**
	 * @param skinOverride the skinOverride to set
	 */
	public static void setSkinOverride(ResourceLocation skinOverride) {
		Kagu.skinOverride = skinOverride;
	}
	
	/**
	 * @return the loggedInUser
	 */
	public static String getLoggedInUser() {
		return loggedInUser;
	}
	
	/**
	 * @param loggedInUser the loggedInUser to set
	 */
	public static void setLoggedInUser(String loggedInUser) {
		Kagu.loggedInUser = loggedInUser;
	}
	
	/**
	 * @return the moduleManager
	 */
	public static ModuleManager getModuleManager() {
		return MODULE_MANAGER;
	}
	
}
