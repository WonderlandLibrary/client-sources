package lunadevs.luna.main;

import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import lunadevs.luna.Connector.ConnectSettings.ConnectSettings;
import lunadevs.luna.font.MistFontRenderer;
import lunadevs.luna.friend.FriendManager;
import lunadevs.luna.gui.clickgui.ClickGui;
import lunadevs.luna.login.AltManager;
import lunadevs.luna.manage.CommandManager;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.manage.TabGuiManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.OptionManager;
import lunadevs.luna.utils.FileUtils;
import lunadevs.luna.utils.ModuleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class Luna {

	
	/** TO EDIT THE 
	* PASTEBIN TO WHITELIST PEOPLE, PLEASE CONTACT FlyingPig#1057 ON DISCORD.
	*
	*
	**/
	//Nigger Jim
	private static Luna theClient;
	public static String CLIENT_NAME = ConnectSettings.NAME;
	public static String CLIENT_VERSION = ConnectSettings.VERSION;
	public static String CLIENT_BUILD = ConnectSettings.VERSION;
	public static String isPRE = ConnectSettings.PRERELEASE;
	public static int NUMBER_OF_CLIENT_DEVELOPERS = -1;
	// VERSION
	public static ModuleManager moduleManager = new ModuleManager();
	public static Minecraft mc = Minecraft.getMinecraft();
	public static FriendManager fm = new FriendManager();
	public static FontRenderer fr = mc.fontRendererObj;
	public static CommandManager cmdManager = new CommandManager();
	public static TabGuiManager tabGuiManager = new TabGuiManager();
	private static AltManager altManager;
	public static Logger clientLogger = LogManager.getLogger();
	public static Module module;
	public static ModuleHelper moduleHelper;
	private final static Logger logger = LogManager.getLogger();
	 
	// MANAGE
	public static final FontRenderer minecraftFontRenderer = new FontRenderer(mc.gameSettings,
			new ResourceLocation("textures/font/ascii.png"), mc.getTextureManager(), false);
	public static final MistFontRenderer fontRenderer50 = new MistFontRenderer(getFont(90), true, 8);
	public static final MistFontRenderer fontRendererGUI = new MistFontRenderer(getFont(40), true, 8);
	public static final MistFontRenderer fontRendererBOLD = new MistFontRenderer(getFont2(60), true, 8);
	public static final MistFontRenderer fontRenderer = new MistFontRenderer(getFont(36), true, 8);
	public static final MistFontRenderer fontRendererCO = new MistFontRenderer(getFontCorbert(36), true, 8);
	public static final MistFontRenderer fontRendererMAIN = new MistFontRenderer(getFont3(250), true, 8);
	public static final MistFontRenderer fontRendererGUItwo = new MistFontRenderer(getFont3(50), true, 8);
	public static final MistFontRenderer fontRendererMainMenu = new MistFontRenderer(getFont2(250), true, 8);
	public static final MistFontRenderer fontRendererGUIThree = new MistFontRenderer(getFont5(40), true, 8);
	public static final MistFontRenderer fontRendererMainMenuTwo = new MistFontRenderer(getFont5(250), true, 8);
	public static final MistFontRenderer fontRendererGuiFour = new MistFontRenderer(getFont6(100), true, 8);
	public static final MistFontRenderer fontRendererArrayList = new MistFontRenderer(getFont7(150), true, 8);
	public static final MistFontRenderer fontRendererHotBar = new MistFontRenderer(getFont8(150), true, 8);
	public static final MistFontRenderer fontRendererTabGuiName = new MistFontRenderer(getFontTitle(170), true, 8);
	
	
	
	// FONT
	public static void start() throws Exception 
	{
		// URLHWID.whitelist(); //Beta
		Display.setTitle(ConnectSettings.STARTUPTITLE + " " + ConnectSettings.NAME + " b" + Luna.CLIENT_VERSION);
		moduleManager = new ModuleManager();
		moduleManager.loadmods();
		OptionManager.start();
		altManager = new AltManager();
		altManager.setupAlts();
		FileUtils.init();
	}

	public static void onUpdate() {
		for (Module m : moduleManager.mods) {
			m.onUpdate();
		}
	}

	public static void onRender() {
		for (Module m : moduleManager.mods) {
			m.onRender();
		}
	}

	public static Luna client(){
		return theClient;
	}
	
	private static Font getFont(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("NightMist/Comfortaa_Regular.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, size);
		}
		return font;
	}
	
	private static Font getFontCorbert(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("luna/CODE.otf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, size);
		}
		return font;
	}
	
	private static Font getFontTitle(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("NightMist/Comfortaa_Regular.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}
	private static Font getFontLuna(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("luna/font.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +10);
		}
		return font;
	}
	private static Font getFont7(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("luna/BebasNeue.otf")).getInputStream(); //Thx nero for the font
			font = Font.createFont(0, is);
			font = font.deriveFont(0, +48);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +48);
		}
		return font;
	}
	
	private static Font getFont8(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("luna/BebasNeue.otf")).getInputStream(); //Thx nero for the font
			font = Font.createFont(0, is);
			font = font.deriveFont(0, +70);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, +70);
		}
		return font;
	}

	private static Font getFont2(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("NightMist/Night_Ride.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, size);
		}
		return font;
	}

	private static Font getFont3(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("NightMist/theboldfont.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, size);
		}
		return font;
	}
	
	private static Font getFont4(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("NightMist/Speeding_Brush.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, size);
		}
		return font;
	}

	private static Font getFont5(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("Luna/Pacifico.ttf")).getInputStream();
			font = Font.createFont(0,  is);
			font = font.deriveFont(0,  size);
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			font = new Font("default", 0, size);
		}
		return font;
	}
	
	private static Font getFont6(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("Luna/Albondigas.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		}catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Couldn't load font.");
			font = new Font("default", 0, size);
		}
		return font;
	}
	
	public static void addChatMessage(String s) {
		mc.thePlayer.addChatMessage(new ChatComponentText("\2478[\2477Luna\2478] \2477" + s));
	}

	public static void sendChatMessage(String s) {
		mc.thePlayer.sendChatMessage(s);
	}

	public static boolean onSendChatMessage(String s) {
		if (s.startsWith("-")) {
			cmdManager.callCommand(s.substring(1));
			return false;
		}
		for (Module m : Luna.moduleManager.getModules()) {
			if (Module.isToggled(true)) {
				return m.onSendChatMessage(s);
			}
		}
		return true;
	}

	public static boolean onRecieveChatMessage(S02PacketChat packet) {
		for (Module m : ModuleManager.mods) {
			if (Module.isToggled(true)) {
				return m.onRecieveChatMessage(packet);
			}
		}
		return true;
	}

	public static AltManager getAltManager() {
		return altManager;
	}

	public static void onKeyPressed(int keyCode) {
		TabGuiManager.keyPress(keyCode);
		for (Module module : ModuleManager.getModules()) {
			if (module.getBind() == keyCode) {
				module.toggle();
			}
		}
	}

	public static void onGUIPressed() {
		if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			if (mc.theWorld != null) {
				mc.displayGuiScreen(new ClickGui());
			}else if (mc.currentScreen instanceof GuiChat){
				mc.displayGuiScreen(null);
			}
		}
	}

	public static int getStringWidth(String colorString) {
		return 2;
	}

	public static void addIRCMessage(String IRCMessage) {
		String ChatPrefix = "\2478[\2477" + "IRC" + "\2478] \2477";

		mc.thePlayer.addChatMessage(new ChatComponentText(ChatPrefix + IRCMessage));
	}



	public static String getClientName() {
		return "Luna";
	}

	public static String getClientBuild() {
		return "b" + CLIENT_BUILD;
	}

	public static int getRainbow(int speed, int offset) {
		float hue = (System.currentTimeMillis() + offset) % speed;
		hue /= speed;
		return Color.getHSBColor(hue, 1f, 1f).getRGB();
	}

	public static void addNormalChatMessage(String message) {
		mc.thePlayer.addChatMessage(new ChatComponentText(message));
	}

	public static String getClientDevelopers() {
		String prefix = "\2478[\2477Luna\2478] \2477";
		return prefix + "@ZiTROXClient, @Mega_Mixer74, ugotsmashed, TimeSwitcher, FlyingPig";
	}

	public static void getClientDeveloperMixer() {
		String mega = "Mega_Mixer";
		addChatMessage(mega);
	}

	public static void getClientDeveloperTimothy() {
		String timothy = "Timothy/ZiTROX";
		addChatMessage(timothy);
	}
	public static void getClientDeveloperPig() {
		String flyingpig = "FlyingPig // OmgItsAFlyingPig";
		addChatMessage(flyingpig);
	}
	public static void getClientDeveloperU() {
		String ugotsmashed = "ugotsmashed";
		addChatMessage(ugotsmashed);
	}
	
	public static void getClientDeveloperTime() {
		String time = "TimeSwitcher";
		addChatMessage(time);
	}

	public static void sendPacket(Packet packet) {
		mc.thePlayer.sendQueue.addToSendQueue(packet);
	}

	public static EntityPlayerSP getPlayer() {
		Minecraft.getMinecraft();
		return Minecraft.thePlayer;
	}
	
	public static WorldClient getWorld(){
		Minecraft.getMinecraft();
		return Minecraft.theWorld;
	}
	
	public static NetHandlerPlayClient getQueue() {
		return getPlayer().sendQueue;
	}
	
	public static ModuleHelper getModuleHelper() {
		if(moduleHelper == null) {
			moduleHelper = new ModuleHelper();
		}
		return moduleHelper;
	}
	
	public static void logChatMessage(String message) {
		 if (Minecraft.getMinecraft().thePlayer != null)
		    {
		    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("\2478[\2477Luna\2478] \2477" + message));
		    }
		    else
		    {
		      System.out.println(message);
		    }
	}
	public static String getDeveloperTimothy() {
		return "Timothy";
	}
	
	public static String getDeveloperMixer() {
		return "Mega_Mixer";
	}
	
	public static Logger getLogger() {
		return logger;
	}
	  public static InventoryPlayer getInventory()
	  {
	    return getPlayer().inventory;
	  }
}
