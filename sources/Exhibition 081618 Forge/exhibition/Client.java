package exhibition;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import exhibition.gui.altmanager.FileManager;
import exhibition.gui.click.ClickGui;
import exhibition.gui.console.SourceConsoleGUI;
import exhibition.management.ColorManager;
import exhibition.management.command.CommandManager;
import exhibition.management.config.ConfigManager;
import exhibition.management.friend.FriendManager;
import exhibition.management.waypoints.WaypointManager;
import exhibition.module.Module;
import exhibition.module.ModuleManager;
import exhibition.module.impl.other.AutoL;
import exhibition.util.ReflectUtil;
import exhibition.util.render.TTFFontRenderer;
import exhibition.util.security.Connection;
import exhibition.util.security.Connector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.util.ResourceLocation;

public class Client {
   public static Client instance;
   public static final String version = "081618";
   public static String parsedVersion;
   public static String clientName = "ArthimoWare";
   public static final boolean developerMode = false;
   public static ColorManager cm = new ColorManager();
   public static ConfigManager configManager;
   public static WaypointManager waypointManager;
   private final ModuleManager moduleManager;
   private static FileManager fileManager;
   private static ClickGui clickGui;
   private static SourceConsoleGUI sourceConsoleGUI;
   public static CommandManager commandManager;
   public ReflectUtil reflector;
   private File dataDirectory;
   private boolean isHidden;
   public static TTFFontRenderer f;
   public static TTFFontRenderer fs;
   public static TTFFontRenderer fss;
   public static TTFFontRenderer header;
   public static TTFFontRenderer badCache;
   public static TTFFontRenderer verdana16;
   public static TTFFontRenderer verdana10;
   public static TTFFontRenderer fsmallbold;
   public static TTFFontRenderer subHeader;
   public static TTFFontRenderer nametagsFont;
   public static TTFFontRenderer eagleSoftware;
   public static TTFFontRenderer eagleSoftware2;
   public static TTFFontRenderer test1;
   public static TTFFontRenderer test2;
   public static TTFFontRenderer test3;
   
   public Client() {
      instance = this;
      reflector = new ReflectUtil();
      commandManager = new CommandManager();
      this.moduleManager = new ModuleManager(Module.class);
      FriendManager.start();
      parsedVersion = version;
   }
   
   public static ClickGui getClickGui() {
      return clickGui;
   }

   public static SourceConsoleGUI getSourceConsoleGUI() {
      return sourceConsoleGUI;
   }

   public static FileManager getFileManager() {
      return fileManager;
   }

   public void sout(Object output) {
      System.out.println(output);
   }

   public void setup() {
      commandManager.setup();
      this.dataDirectory = new File(clientName);
      this.moduleManager.setup();
      ModuleManager.loadSettings();
      (fileManager = new FileManager()).loadFiles();
      clickGui = new ClickGui();
      sourceConsoleGUI = new SourceConsoleGUI();
      waypointManager = new WaypointManager();
      configManager = new ConfigManager();
      instance.setupFonts();
      instance.getAnnouncement();
      //instance.customMCFont();
   }

   public void customMCFont() {
	   Wrapper.getMinecraft().fontRendererObj = new CustomFontRenderer(Wrapper.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Wrapper.getMinecraft().renderEngine, false);
		if (Wrapper.getMinecraft().gameSettings.language != null)
		{
			Wrapper.getMinecraft().fontRendererObj.setUnicodeFlag(Wrapper.getMinecraft().isUnicode());
			Wrapper.getMinecraft().fontRendererObj.setBidiFlag(Wrapper.getMinecraft().gameSettings.forceUnicodeFont);
		}
		try {
			for(Method m : SimpleReloadableResourceManager.class.getDeclaredMethods()) {
				if(m.getName().equals(Mappings.registerReloadListener)) {
					m.invoke(Wrapper.getMinecraft().getResourceManager(), new Object[] {Wrapper.getMinecraft().fontRendererObj});
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
   }
   
	public void getAnnouncement() {
		try {
			Scanner scanner1Scanner = new Scanner(new URL("https://exusiai.today/minecraftfans.txt").openStream());
			while (scanner1Scanner.hasNext()) {
				AutoL.fuckTextArrayList.add(scanner1Scanner.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
   public void setupFonts() {
      f = new TTFFontRenderer(new Font("Impact", 0, 24), true);
      fs = new TTFFontRenderer(new Font("Tahoma Bold", 0, 11), true);
      test2 = new TTFFontRenderer(new Font("Tahoma Bold", 0, 10), true);
      fss = new TTFFontRenderer(new Font("Tahoma", 0, 10), false);
      test3 = new TTFFontRenderer(new Font("Tahoma", 0, 10), true);
      fsmallbold = new TTFFontRenderer(new Font("Tahoma Bold", 0, 10), true);
      header = new TTFFontRenderer(new Font("Tahoma", 0, 20), true);
      subHeader = new TTFFontRenderer(new Font("Tahoma", 0, 16), true);
      verdana16 = new TTFFontRenderer(new Font("Lucida Console", 0, 9), false);
      test1 = new TTFFontRenderer(new Font("Verdana", 0, 9), true);
      verdana10 = new TTFFontRenderer(new Font("Lucida Console", 0, 10), false);
      nametagsFont = new TTFFontRenderer(new Font("Tahoma", 0, 18), true);
      Font myFont = null;

      try {
         InputStream istream = this.getClass().getResourceAsStream("/assets/skeet/font.ttf");
         myFont = Font.createFont(0, istream);
         myFont = myFont.deriveFont(0, 36.0F);
         badCache = new TTFFontRenderer(myFont, true);
      } catch (Exception var4) {
         var4.printStackTrace();
         badCache = new TTFFontRenderer(new Font("Impact", 0, 36), true);
      }

   }

   public static ModuleManager getModuleManager() {
      return instance.moduleManager;
   }

   public static File getDataDir() {
      return instance.dataDirectory;
   }

   public static boolean isHidden() {
      return instance.isHidden;
   }

   public static void resetClickGui() {
      clickGui = new ClickGui();
   }
}
