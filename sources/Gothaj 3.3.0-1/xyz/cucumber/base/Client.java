package xyz.cucumber.base;

import de.florianmichael.viamcp.ViaMCP;
import god.buddy.aot.BCompiler;
import i.dupx.launcher.CLAPI;
import org.lwjgl.opengl.Display;
import xyz.cucumber.base.commands.CommandManager;
import xyz.cucumber.base.events.EventBus;
import xyz.cucumber.base.file.FileManager;
import xyz.cucumber.base.interf.clientsettings.ClientSettings;
import xyz.cucumber.base.interf.config.ConfigManager;
import xyz.cucumber.base.module.ModuleManager;
import xyz.cucumber.base.utils.cfgs.ConfigFileUtils;
import xyz.cucumber.base.utils.cfgs.PublicConfigUtils;
import xyz.cucumber.base.utils.render.Fonts;

public enum Client {
   INSTANCE;

   private ModuleManager moduleManager;
   private EventBus eventBus;
   private Fonts fonts;
   private CommandManager commandManager;
   private ConfigManager configManager;
   private ClientSettings clientSettings;
   private FileManager fileManager;
   public String version = "3.3.0.1";
   public int startTime;

   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onEnable() {
      Display.setTitle("Gothaj " + this.version);
      this.moduleManager = new ModuleManager();
      this.eventBus = new EventBus();
      this.fonts = new Fonts();
      this.commandManager = new CommandManager();
      this.fileManager = new FileManager();

      try {
         ViaMCP.create();
         ViaMCP.INSTANCE.initAsyncSlider();
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      this.configManager = new ConfigManager();
      this.clientSettings = new ClientSettings();
      this.fileManager.load();
      ConfigFileUtils.load(ConfigFileUtils.file, false, true);
      PublicConfigUtils.reload();
      CLAPI.setClient(new CLAPI.IClient() {
         @Override
         public String writeCurrentConfig() {
            return ConfigFileUtils.getString(ConfigFileUtils.file);
         }

         @Override
         public void loadCurrentConfig(String data) {
            ConfigFileUtils.load("CL Config", data, true);
         }

         @Override
         public void joinServer(String data) {
            System.out.println("joining server: " + data);
         }
      });
      this.startTime = (int)System.currentTimeMillis();
   }

   public void onDisable() {
      this.fileManager.save();
      ConfigFileUtils.save(ConfigFileUtils.file, false);
   }

   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   public EventBus getEventBus() {
      return this.eventBus;
   }

   public CommandManager getCommandManager() {
      return this.commandManager;
   }

   public FileManager getFileManager() {
      return this.fileManager;
   }

   public ConfigManager getConfigManager() {
      return this.configManager;
   }

   public void setConfigManager(ConfigManager configManager) {
      this.configManager = configManager;
   }

   public ClientSettings getClientSettings() {
      return this.clientSettings;
   }
}
