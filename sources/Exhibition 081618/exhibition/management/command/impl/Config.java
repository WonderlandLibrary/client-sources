package exhibition.management.command.impl;

import exhibition.Client;
import exhibition.management.command.Command;
import exhibition.management.config.ConfigManager;
import exhibition.util.misc.ChatUtil;
import java.util.function.Consumer;

public class Config extends Command {
   public Config(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args != null && args.length >= 1) {
         ConfigManager cm = Client.configManager;
         String configName;
         if ((args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("create")) && args.length == 2) {
            configName = args[1];
            cm.createConfig(configName);
            return;
         }

         if ((args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("d") || args[0].equalsIgnoreCase("delete")) && args.length == 2) {
            configName = args[1];
            cm.deleteConfig(configName);
            return;
         }

         if ((args[0].equalsIgnoreCase("save") || args[0].equalsIgnoreCase("s")) && args.length == 2) {
            configName = args[1];
            cm.save(configName);
            return;
         }

         if ((args[0].equalsIgnoreCase("load") || args[0].equalsIgnoreCase("l")) && args.length == 2) {
            configName = args[1];
            cm.load(configName);
            return;
         }

         if (args[0].equalsIgnoreCase("list")) {
            ChatUtil.printChat("§4[§cE§4]§8 Total Configs [" + cm.getConfigs().size() + "]");
            cm.getConfigs().forEach((o) -> {
               ChatUtil.printChat("§8- §7" + o);
            });
            return;
         }
      }

      this.printUsage();
   }

   public String getUsage() {
      return "<create/delete/save/load/list> [config name]";
   }
}
