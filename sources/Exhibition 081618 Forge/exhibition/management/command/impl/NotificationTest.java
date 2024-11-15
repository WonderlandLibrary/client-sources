package exhibition.management.command.impl;

import exhibition.Client;
import exhibition.management.command.Command;
import exhibition.management.notifications.dev.DevNotifications;
import exhibition.management.notifications.user.Notifications;
import exhibition.util.misc.ChatUtil;

public class NotificationTest extends Command {
   public NotificationTest(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else {
         Notifications not = Notifications.getManager();
         DevNotifications dev = DevNotifications.getManager();
         if (args[0].equalsIgnoreCase("notify")) {
            not.post("Player Warning", "Some one called you a §chacker!", 2500L, Notifications.Type.NOTIFY);
         } else if (args[0].equalsIgnoreCase("warning")) {
            not.post("Warning Alert", "§cBob §fis now §6Vanished!", 2500L, Notifications.Type.WARNING);
         } else if (args[0].equalsIgnoreCase("info")) {
            not.post("Friend Info", "§bArithmo §fhas §cdied!", 2500L, Notifications.Type.INFO);
         } else if (args[0].equalsIgnoreCase("f")) {
            not.post("Friend Info", "§aA §fG §cD!", 2500L, Notifications.Type.INFO);
         } else if (args[0].equalsIgnoreCase("cgui")) {
            Client.resetClickGui();
         } else if (args[0].equalsIgnoreCase("font")) {
            Client.instance.setupFonts();
         } else if (args[0].equalsIgnoreCase("dev")) {
            dev.post("Missed shot due to spread.");
         } else if (args[0].equalsIgnoreCase("console")) {
            Client.getSourceConsoleGUI().sourceConsole.clearStringList();
            Client.getSourceConsoleGUI().sourceConsole.addStringList("Console Testing: " + Math.random() + " §cPLAYER TICK COUNT: " + this.mc.thePlayer.ticksExisted);
         } else if (args[0].equalsIgnoreCase("config") && args.length > 1) {
            Client.configManager.createConfig(args[1]);
         } else if (args[0].equalsIgnoreCase("spotify")) {
            Notifications.getManager().post("Status", "Testing spotify logo for a bit.", 100000L, Notifications.Type.SPOTIFY);
         } else {
            ChatUtil.printChat("§4[§cE§4]§8 ???");
         }

      }
   }

   public String getUsage() {
      return null;
   }
}
