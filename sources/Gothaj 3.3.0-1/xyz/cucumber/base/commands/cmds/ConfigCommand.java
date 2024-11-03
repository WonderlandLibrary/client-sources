package xyz.cucumber.base.commands.cmds;

import java.io.File;
import java.util.Map.Entry;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.commands.Command;
import xyz.cucumber.base.commands.CommandInfo;
import xyz.cucumber.base.utils.cfgs.ConfigFileUtils;
import xyz.cucumber.base.utils.cfgs.PublicConfigUtils;

@CommandInfo(
   aliases = {"cfg", "c", "config"},
   name = "Config",
   usage = ".cfg <load/save/remove/list/loadonline/listonline> <File Name>"
)
public class ConfigCommand extends Command {
   @Override
   public void onSendCommand(String[] args) {
      if (args.length == 0) {
         this.sendUsage();
      } else if (!args[0].equalsIgnoreCase("list") && !args[0].equalsIgnoreCase("listonline") && args.length != 2) {
         this.sendUsage();
      } else {
         if (args[0].toLowerCase().equals("load")) {
            ConfigFileUtils.save(ConfigFileUtils.file, false);
            ConfigFileUtils.file = new File(ConfigFileUtils.directory, args[1] + ".json");
            if (!ConfigFileUtils.file.exists()) {
               Client.INSTANCE.getCommandManager().sendChatMessage("§cSorry, but this config does not exist!");
               return;
            }

            ConfigFileUtils.load(ConfigFileUtils.file, true, false);
            Client.INSTANCE.getCommandManager().sendChatMessage("§aConfig was successfully loaded!");
         } else if (args[0].toLowerCase().equals("loadonline")) {
            for (Entry<String, String> entry : PublicConfigUtils.getPublicConfigs().entrySet()) {
               if (entry.getKey().toLowerCase().equals(args[1])) {
                  ConfigFileUtils.save(ConfigFileUtils.file, false);
                  ConfigFileUtils.file = new File(ConfigFileUtils.directory, args[1] + ".json");
                  ConfigFileUtils.load(entry.getKey(), entry.getValue(), true);
                  Client.INSTANCE.getCommandManager().sendChatMessage("§aPublic config was successfully loaded!");
                  return;
               }
            }

            Client.INSTANCE.getCommandManager().sendChatMessage("§cSorry, but this config does not exist!");
         } else if (args[0].toLowerCase().equals("listonline")) {
            Client.INSTANCE.getCommandManager().sendChatMessage("§7Showing all Public configs: (" + PublicConfigUtils.getPublicConfigs().size() + ")");

            for (Entry<String, String> entryx : PublicConfigUtils.getPublicConfigs().entrySet()) {
               Client.INSTANCE
                  .getCommandManager()
                  .sendChatMessage("§7 > §a" + entryx.getKey() + "   " + ConfigFileUtils.getTimeDifference(ConfigFileUtils.getConfigDate(entryx.getValue())));
            }
         } else if (args[0].toLowerCase().equals("save")) {
            ConfigFileUtils.save(ConfigFileUtils.file, true);
            ConfigFileUtils.file = new File(ConfigFileUtils.directory, args[1] + ".json");
            if (!ConfigFileUtils.file.exists()) {
               Client.INSTANCE.getCommandManager().sendChatMessage("§aConfig was successfully created!");
            } else {
               Client.INSTANCE.getCommandManager().sendChatMessage("§aConfig was saved!");
            }

            ConfigFileUtils.save(ConfigFileUtils.file, true);
         } else if (args[0].toLowerCase().equals("remove")) {
            ConfigFileUtils.file = new File(ConfigFileUtils.directory, args[1] + ".json");
            if (!ConfigFileUtils.file.exists()) {
               Client.INSTANCE.getCommandManager().sendChatMessage("§cSorry, but this config does not exists!");
               return;
            }

            ConfigFileUtils.file.delete();
            Client.INSTANCE.getCommandManager().sendChatMessage("§aConfig was successfully deleted!");
         } else {
            if (!args[0].toLowerCase().equals("list")) {
               this.sendUsage();
               return;
            }

            File[] var5;
            for (File f : var5 = ConfigFileUtils.directory.listFiles()) {
               if (f.getName().endsWith(".json")) {
                  Client.INSTANCE.getCommandManager().sendChatMessage("§a" + f.getName().replace(".json", ""));
               }
            }
         }
      }
   }
}
