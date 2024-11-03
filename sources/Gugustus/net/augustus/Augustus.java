package net.augustus;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import lombok.Getter;
import lombok.Setter;
import me.jDev.xenza.files.Converter;
import net.augustus.ScriptingAPI.APIImpl;
import net.augustus.ScriptingAPI.ScriptExecutor;
import net.augustus.ScriptingAPI.ScriptingAPI;
import net.augustus.ScriptingAPI.apiFileManager;
import net.augustus.cleanGui.CleanClickGui;
import net.augustus.clickgui.ClickGui;
import net.augustus.clickgui.SettingSorter;
import net.augustus.commands.CommandManager;
import net.augustus.font.testfontbase.FontUtil;
import net.augustus.irc.DiscordBot;
import net.augustus.irc.IrcProcesser;
import net.augustus.modules.Manager;
import net.augustus.modules.ModuleManager;
import net.augustus.notify.rise5.NotificationManager;
import net.augustus.settings.SettingsManager;
import net.augustus.ui.augustusmanager.AugustusSounds;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.ColorUtil;
import net.augustus.utils.TimeHelper;
import net.augustus.utils.YawPitchHelper;
import net.augustus.utils.HackerDetector.ChecksManager;
import net.augustus.utils.shader.BackgroundShaderUtil;
import net.augustus.utils.skid.lorious.font.Fonts;
import net.augustus.utils.sound.SoundUtil;
import net.lenni0451.eventapi.manager.EventManager;
import viamcp.ViaMCP;

@Getter
@Setter
public class Augustus {
	
	public enum ClientBrand {
		RELEASE,
		DEV,
		PRIVATE;
	}
	
   private static Augustus instance = new Augustus();
   private String name = null;
   private String version = "b1.5"; // cool
   private ClientBrand brand = ClientBrand.DEV;
   private int build = 0;
   private String dev = null;
   private final Color clientColor = new Color(41, 146, 222);
   private List<String> lastAlts = new ArrayList<>();
   private final Manager manager = new Manager();
   private ModuleManager moduleManager;
   private SettingsManager settingsManager;
   private CommandManager commandManager;
   private CleanClickGui cleanClickGui;
   private ClickGui clickGui;
   private Converter converter;
   private BackgroundShaderUtil backgroundShaderUtil;
   private float shaderSpeed = 1800.0F;
   private SettingSorter settingSorter;
   private YawPitchHelper yawPitchHelper;
   public String uid = "";
   private BlockUtil blockUtil;
   private Fonts loriousFontService;
   private Proxy proxy;
   private NotificationManager rise5notifyManager;
   private ColorUtil colorUtil;
   private ChecksManager checksManager;
   private String apiBinding = "api";
   private ScriptingAPI api = new APIImpl();
   private ScriptExecutor executor = new ScriptExecutor(api);
   private apiFileManager apiFileManager = new apiFileManager();
   
   private TimeHelper welcomemsgTimeHelper = new TimeHelper();
   
   //irc shit
   private DiscordBot ircBot = new DiscordBot();
   private IrcProcesser irc = new IrcProcesser(ircBot);
   
   //if the updated required gui is showed yet
   public Boolean showedUpdate = false;
   public double latestVersion = -1;

   public static Augustus getInstance() {
      return instance;
   }

   public void preStart() {
	  if(outdated()) {
		  showedUpdate = false;
		  latestVersion = getLatestVersion();
	  }else {
		  showedUpdate = true;
	  }
      Path dir = Paths.get("gugustus/configs");
      if (!Files.exists(dir)) {
         try {
            Files.createDirectories(dir);
         } catch (IOException var2) {
            var2.printStackTrace();
         }
      }
   }

   private double getLatestVersion() {
	   String str;
	   try {
			URL url = new URL("https://raw.githubusercontent.com/ModdingMC/Gugustus-states/main/version");
			Object obj = url.getContent();
			InputStreamReader isr = new InputStreamReader((InputStream) obj);
			BufferedReader br = new BufferedReader(isr);
			while((str=br.readLine())!=null) {
				System.out.println(str);
				return Double.valueOf(str);
				}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
			   return -1D;
		}
	   return -1D;
   }
   
   private boolean outdated() {
	   String str;
	   try {
			URL url = new URL("https://raw.githubusercontent.com/ChloeBestDev/GugustusPublic/main/version");
			Object obj = url.getContent();
			InputStreamReader isr = new InputStreamReader((InputStream) obj);
			BufferedReader br = new BufferedReader(isr);
			while((str=br.readLine())!=null) {
				System.out.println(str);
				if(Double.valueOf(str) > Double.valueOf(version.split("b")[1])) {
					return true;
				}
				}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
			   return false;
		}
	   return false;
   }
   
   public void start() {
      name = "Gugustus";
      dev = "moddingmc + iksang";
      LocalDate today = LocalDate.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
      String formattedDate = today.format(formatter) + "01";
      //prev builds
      //14072403
      build = brand == ClientBrand.DEV ? Integer.valueOf(formattedDate) : 19072407; //change build or die
      System.out.println("Starting Client...");
      FontUtil.bootstrap();
      this.apiFileManager.init();
      this.loriousFontService = new Fonts();
      this.loriousFontService.bootstrap();
      this.colorUtil = new ColorUtil();
      this.yawPitchHelper = new YawPitchHelper();
      this.settingsManager = new SettingsManager();
      this.moduleManager = new ModuleManager();
      this.commandManager = new CommandManager();
      this.rise5notifyManager = new NotificationManager();
      this.clickGui = new ClickGui("ClickGui");
      this.converter = new Converter();
      this.checksManager = new ChecksManager();
      this.converter.settingReader(this.settingsManager.getStgs());
      this.converter.settingSaver(this.settingsManager.getStgs());
      this.converter.moduleReader(this.moduleManager.getModules());
      this.converter.readLastAlts();
//      this.converter.clickGuiLoader(this.clickGui.getCategoryButtons());
      this.backgroundShaderUtil = new BackgroundShaderUtil();
      this.settingSorter = new SettingSorter();
      this.cleanClickGui = new CleanClickGui();
      AugustusSounds.currentSound = this.converter.readSound();
      this.blockUtil = new BlockUtil();
      EventManager.register(this.settingSorter);
      EventManager.register(this);
      Display.setTitle(name + " " + version + (outdated() ? " (Outdated)" : "") + " - " + brand.toString() + " - " + build);
      try {
         ViaMCP.getInstance().start();
      } catch (Exception var2) {
         var2.printStackTrace();
      }
	  SoundUtil.play(SoundUtil.welcomeSound);
//      irc.initBot();
   }

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getVersion() {
	return version;
}

public void setVersion(String version) {
	this.version = version;
}

public String getDev() {
	return dev;
}

public void setDev(String dev) {
	this.dev = dev;
}

public List<String> getLastAlts() {
	return lastAlts;
}

public void setLastAlts(List<String> lastAlts) {
	this.lastAlts = lastAlts;
}

public ModuleManager getModuleManager() {
	return moduleManager;
}

public void setModuleManager(ModuleManager moduleManager) {
	this.moduleManager = moduleManager;
}

public SettingsManager getSettingsManager() {
	return settingsManager;
}

public void setSettingsManager(SettingsManager settingsManager) {
	this.settingsManager = settingsManager;
}

public CommandManager getCommandManager() {
	return commandManager;
}

public void setCommandManager(CommandManager commandManager) {
	this.commandManager = commandManager;
}

public CleanClickGui getCleanClickGui() {
	return cleanClickGui;
}

public void setCleanClickGui(CleanClickGui cleanClickGui) {
	this.cleanClickGui = cleanClickGui;
}

public ClickGui getClickGui() {
	return clickGui;
}

public void setClickGui(ClickGui clickGui) {
	this.clickGui = clickGui;
}

public Converter getConverter() {
	return converter;
}

public void setConverter(Converter converter) {
	this.converter = converter;
}

public BackgroundShaderUtil getBackgroundShaderUtil() {
	return backgroundShaderUtil;
}

public void setBackgroundShaderUtil(BackgroundShaderUtil backgroundShaderUtil) {
	this.backgroundShaderUtil = backgroundShaderUtil;
}

public float getShaderSpeed() {
	return shaderSpeed;
}

public void setShaderSpeed(float shaderSpeed) {
	this.shaderSpeed = shaderSpeed;
}

public SettingSorter getSettingSorter() {
	return settingSorter;
}

public void setSettingSorter(SettingSorter settingSorter) {
	this.settingSorter = settingSorter;
}

public YawPitchHelper getYawPitchHelper() {
	return yawPitchHelper;
}

public void setYawPitchHelper(YawPitchHelper yawPitchHelper) {
	this.yawPitchHelper = yawPitchHelper;
}

public String getUid() {
	return uid;
}

public void setUid(String uid) {
	this.uid = uid;
}

public BlockUtil getBlockUtil() {
	return blockUtil;
}

public void setBlockUtil(BlockUtil blockUtil) {
	this.blockUtil = blockUtil;
}

public Fonts getLoriousFontService() {
	return loriousFontService;
}

public void setLoriousFontService(Fonts loriousFontService) {
	this.loriousFontService = loriousFontService;
}

public Proxy getProxy() {
	return proxy;
}

public void setProxy(Proxy proxy) {
	this.proxy = proxy;
}

public NotificationManager getRise5notifyManager() {
	return rise5notifyManager;
}

public void setRise5notifyManager(NotificationManager rise5notifyManager) {
	this.rise5notifyManager = rise5notifyManager;
}

public ColorUtil getColorUtil() {
	return colorUtil;
}

public void setColorUtil(ColorUtil colorUtil) {
	this.colorUtil = colorUtil;
}

public Color getClientColor() {
	return clientColor;
}

public Manager getManager() {
	return manager;
}
   
   public ChecksManager getChecksManager() {
	   return this.checksManager;
   }

public String getApiBinding() {
	return apiBinding;
}

public void setApiBinding(String apiBinding) {
	this.apiBinding = apiBinding;
}

public ScriptExecutor getExecutor() {
	return executor;
}

public void setExecutor(ScriptExecutor executor) {
	this.executor = executor;
}

public IrcProcesser getIrc() {
	return irc;
}

public void setIrc(IrcProcesser irc) {
	this.irc = irc;
}

public ClientBrand getBrand() {
	return brand;
}

public void setBrand(ClientBrand brand) {
	this.brand = brand;
}

public int getBuild() {
	return build;
}

public void setBuild(int build) {
	this.build = build;
}

}
