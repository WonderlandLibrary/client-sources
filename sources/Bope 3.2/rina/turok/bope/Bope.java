package rina.turok.bope;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.SimpleDateFormat;
import java.util.Date;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rina.turok.bope.bopemod.BopeDiscordRichPresence;
import rina.turok.bope.bopemod.BopeMessage;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.BopeGUI;
import rina.turok.bope.bopemod.guiscreen.BopeHUD;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.manager.BopeCommandManager;
import rina.turok.bope.bopemod.manager.BopeConfigManager;
import rina.turok.bope.bopemod.manager.BopeEventManager;
import rina.turok.bope.bopemod.manager.BopeFriendManager;
import rina.turok.bope.bopemod.manager.BopeHUDManager;
import rina.turok.bope.bopemod.manager.BopeModuleManager;
import rina.turok.bope.bopemod.manager.BopeSettingManager;
import rina.turok.bope.external.BopeEventHandler;
import rina.turok.turok.Turok;
import rina.turok.turok.task.TurokFont;

@Mod(
   modid = "bope",
   version = "0.3"
)
public class Bope {
   @Instance
   private static Bope MASTER;
   public static final EventBus ZERO_ALPINE_EVENT_BUS = new EventManager();
   public static final String BOPE_NAME = "B.O.P.E";
   public static final String BOPE_VERSION = "0.3";
   public static final String BOPE_SPACE = " ";
   public static final String BOPE_SIGN = " \u23d0 ";
   public static final int BOPE_KEY_GUI = 54;
   public static final int BOPE_KEY_DELETE = 211;
   public static final int BOPE_KEY_GUI_ESCAPE = 1;
   public static Logger bope_register_log;
   public static BopeCommandManager command_manager;
   public static BopeSettingManager setting_manager;
   public static BopeConfigManager config_manager;
   public static BopeModuleManager module_manager;
   public static BopeFriendManager friend_manager;
   public static BopeEventManager event_manager;
   public static BopeHUDManager hud_manager;
   public static BopeDiscordRichPresence discord_rpc;
   public static BopeGUI click_gui;
   public static BopeHUD click_hud;
   public static String font_name = "Verdana";
   public static Turok turok;
   public static ChatFormatting r;
   public static ChatFormatting ba;
   public static ChatFormatting re;
   public static ChatFormatting aq;
   public static ChatFormatting bl;
   public static ChatFormatting go;
   public static ChatFormatting g;
   public static ChatFormatting wh;
   public static ChatFormatting gr;
   public static ChatFormatting ye;
   public static ChatFormatting dr;
   public static ChatFormatting da;
   public static ChatFormatting db;
   public static ChatFormatting gg;
   public static ChatFormatting dg;
   public static ChatFormatting dp;
   public static ChatFormatting lp;
   public static int client_r;
   public static int client_g;
   public static int client_b;

   @EventHandler
   public void BopeStarting(FMLInitializationEvent event) {
      this.init_log("B.O.P.E");
      send_minecraft_log("Loading packages initializing in main class. [Bope.class]");
      BopeEventHandler.INSTANCE = new BopeEventHandler();
      setting_manager = new BopeSettingManager("setting");
      command_manager = new BopeCommandManager("command");
      config_manager = new BopeConfigManager("config");
      module_manager = new BopeModuleManager("module");
      friend_manager = new BopeFriendManager("friend");
      event_manager = new BopeEventManager("event");
      hud_manager = new BopeHUDManager("hud");

      try {
         config_manager.load_settings();
         config_manager.load_binds();
         config_manager.load_client("stuff");
      } catch (Exception var5) {
      }

      send_minecraft_log("Managers are initialed.");
      click_hud = new BopeHUD();
      click_gui = new BopeGUI();

      try {
         config_manager.load_client();
         config_manager.load_friends();
      } catch (Exception var4) {
      }

      turok = new Turok("Turok");
      send_minecraft_log("Turok framework initialed.");
      BopeEventRegister.register_command_manager(command_manager);
      BopeEventRegister.register_module_manager(event_manager);
      send_minecraft_log("Events registered.");
      send_minecraft_log("GUI and HUD initialed.");

      try {
         config_manager.load_settings();
         config_manager.load_binds();
         config_manager.load_client("stuff");
         config_manager.load_client();
         config_manager.load_friends();
      } catch (Exception var3) {
      }

      discord_rpc = new BopeDiscordRichPresence("RPC");
      if (module_manager.get_module_with_tag("RPC").is_active()) {
         discord_rpc.run();
      }

      if (module_manager.get_module_with_tag("GUI").is_active()) {
         module_manager.get_module_with_tag("GUI").set_active(false);
      }

      if (module_manager.get_module_with_tag("HUD").is_active()) {
         module_manager.get_module_with_tag("HUD").set_active(false);
      }

      client_r = get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
      client_g = get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
      client_b = get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);
      send_minecraft_log("Client started.");
   }

   public void init_log(String name) {
      bope_register_log = LogManager.getLogger(name);
      send_minecraft_log("...");
   }

   public static void dev(String message) {
      BopeMessage.send_client_message(message);
   }

   public static void hud_notify(String value) {
      get_hud_manager().notify_client.notify_hud(value);
   }

   public static Bope get_instance() {
      return MASTER;
   }

   public static void send_minecraft_log(String log) {
      bope_register_log.info(log);
   }

   public static void send_client_log(String log) {
      Date hora = new Date();
      String data = (new SimpleDateFormat("HH:mm:ss:")).format(hora);
      get_instance();
      config_manager.send_log("<B.O.P.E><" + data + "> " + log);
   }

   public static String get_name() {
      return "B.O.P.E";
   }

   public static String get_version() {
      return "0.3";
   }

   public static String get_actual_user() {
      String player_requested = "NoName";
      if (Minecraft.getMinecraft().player != null) {
         player_requested = Minecraft.getMinecraft().player.getName();
      }

      return player_requested;
   }

   public static BopeCommandManager get_command_manager() {
      get_instance();
      return command_manager;
   }

   public static BopeConfigManager get_config_manager() {
      get_instance();
      return config_manager;
   }

   public static BopeModuleManager get_module_manager() {
      get_instance();
      return module_manager;
   }

   public static BopeFriendManager get_friend_manager() {
      get_instance();
      return friend_manager;
   }

   public static BopeSettingManager get_setting_manager() {
      get_instance();
      return setting_manager;
   }

   public static BopeHUDManager get_hud_manager() {
      get_instance();
      return hud_manager;
   }

   public static BopeDiscordRichPresence get_rpc() {
      get_instance();
      return discord_rpc;
   }

   public static BopeEventHandler get_event_handler() {
      return BopeEventHandler.INSTANCE;
   }

   public static Turok get_turok() {
      get_instance();
      return turok;
   }

   public static String smooth(String base) {
      get_turok().get_font_manager();
      return TurokFont.smooth(base);
   }

   public static BopeSetting get_setting(String module, String setting) {
      return get_setting_manager().get_setting_with_tag(module, setting);
   }

   public static BopeModule get_module(String module) {
      return get_module_manager().get_module_with_tag(module);
   }

   public static boolean module_is_active(String module) {
      return get_module_manager().get_module_with_tag(module).is_active();
   }

   static {
      r = ChatFormatting.RESET;
      ba = ChatFormatting.BLACK;
      re = ChatFormatting.RED;
      aq = ChatFormatting.AQUA;
      bl = ChatFormatting.BLUE;
      go = ChatFormatting.GOLD;
      g = ChatFormatting.GRAY;
      wh = ChatFormatting.WHITE;
      gr = ChatFormatting.GREEN;
      ye = ChatFormatting.YELLOW;
      dr = ChatFormatting.DARK_RED;
      da = ChatFormatting.DARK_AQUA;
      db = ChatFormatting.DARK_BLUE;
      gg = ChatFormatting.DARK_GRAY;
      dg = ChatFormatting.DARK_GREEN;
      dp = ChatFormatting.DARK_PURPLE;
      lp = ChatFormatting.LIGHT_PURPLE;
      client_r = 0;
      client_g = 0;
      client_b = 0;
   }
}
