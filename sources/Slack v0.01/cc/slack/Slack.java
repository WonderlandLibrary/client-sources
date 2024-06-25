package cc.slack;

import cc.slack.events.Event;
import cc.slack.events.impl.game.ChatEvent;
import cc.slack.events.impl.input.KeyEvent;
import cc.slack.features.commands.CMDManager;
import cc.slack.features.commands.api.CMD;
import cc.slack.features.config.configManager;
import cc.slack.features.modules.ModuleManager;
import cc.slack.features.modules.impl.movement.Sprint;
import cc.slack.features.modules.impl.other.Targets;
import cc.slack.features.modules.impl.other.Tweaks;
import cc.slack.features.modules.impl.render.Animations;
import cc.slack.features.modules.impl.render.HUD;
import cc.slack.features.modules.impl.render.ScoreboardModule;
import cc.slack.features.modules.impl.render.TargetHUD;
import cc.slack.utils.EventUtil;
import cc.slack.utils.client.ClientInfo;
import cc.slack.utils.other.PrintUtil;
import de.florianmichael.viamcp.ViaMCP;
import io.github.nevalackin.radbus.Listen;
import io.github.nevalackin.radbus.PubSub;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Iterator;
import org.lwjgl.opengl.Display;

public class Slack {
   private static final Slack instance = new Slack();
   public final ClientInfo info;
   private final PubSub<Event> eventBus;
   private final ModuleManager moduleManager;
   private final CMDManager cmdManager;
   public final String changelog;

   public Slack() {
      this.info = new ClientInfo("Slack", "v0.1", ClientInfo.VersionType.ALPHA);
      PrintStream var10001 = System.err;
      var10001.getClass();
      this.eventBus = PubSub.newInstance(var10001::println);
      this.moduleManager = new ModuleManager();
      this.cmdManager = new CMDManager();
      this.changelog = "Release v0.01:\r\n-Added all modules (56)\r\n-Added SexModule";
   }

   public void start() {
      PrintUtil.print("Initializing " + this.info.getName());
      Display.setTitle(this.info.getName() + " " + this.info.getVersion() + " | " + this.info.getType() + " Build");
      EventUtil.register(this);
      this.moduleManager.initialize();
      this.cmdManager.initialize();
      configManager.init();
      ((ScoreboardModule)this.moduleManager.getInstance(ScoreboardModule.class)).toggle();
      ((Animations)this.moduleManager.getInstance(Animations.class)).toggle();
      ((HUD)this.moduleManager.getInstance(HUD.class)).toggle();
      ((Sprint)this.moduleManager.getInstance(Sprint.class)).toggle();
      ((Tweaks)this.moduleManager.getInstance(Tweaks.class)).toggle();
      ((TargetHUD)this.moduleManager.getInstance(TargetHUD.class)).toggle();
      ((Targets)this.moduleManager.getInstance(Targets.class)).toggle();

      try {
         ViaMCP.create();
         ViaMCP.INSTANCE.initAsyncSlider();
      } catch (Exception var2) {
      }

   }

   public void shutdown() {
      EventUtil.unRegister(this);
      configManager.stop();
   }

   @Listen
   public void handleKey(KeyEvent e) {
      this.moduleManager.getModules().forEach((module) -> {
         if (module.getKey() == e.getKey()) {
            module.toggle();
         }

      });
   }

   @Listen
   public void handleChat(ChatEvent e) {
      String message = e.getMessage();
      if (message.startsWith(this.cmdManager.getPrefix())) {
         e.cancel();
         message = message.substring(this.cmdManager.getPrefix().length());
         if (message.split("\\s")[0].equalsIgnoreCase("")) {
            PrintUtil.message("This is Â§cSlack'sÂ§f prefix for ingame client commands. Type Â§c.help Â§fto get started.");
         } else {
            if (message.split("\\s").length > 0) {
               String cmdName = message.split("\\s")[0];
               Iterator var4 = this.cmdManager.getCommands().iterator();

               while(true) {
                  if (!var4.hasNext()) {
                     PrintUtil.message("\"" + message + "\" is not a recognized command. Use Â§c.help Â§fto get other commands.");
                     break;
                  }

                  CMD cmd = (CMD)var4.next();
                  if (cmd.getName().equalsIgnoreCase(cmdName) || cmd.getAlias().equalsIgnoreCase(cmdName)) {
                     cmd.onCommand((String[])Arrays.copyOfRange(message.split("\\s"), 1, message.split("\\s").length), message);
                     return;
                  }
               }
            }

         }
      }
   }

   public void addNotification(String bigText, String smallText, Long duration) {
      ((HUD)instance.getModuleManager().getInstance(HUD.class)).addNotification(bigText, smallText, duration);
   }

   public ClientInfo getInfo() {
      return this.info;
   }

   public PubSub<Event> getEventBus() {
      return this.eventBus;
   }

   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   public CMDManager getCmdManager() {
      return this.cmdManager;
   }

   public String getChangelog() {
      this.getClass();
      return "Release v0.01:\r\n-Added all modules (56)\r\n-Added SexModule";
   }

   public static Slack getInstance() {
      return instance;
   }
}
