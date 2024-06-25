package cc.slack.features.commands.impl;

import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.api.CMDInfo;
import cc.slack.features.config.configManager;
import cc.slack.utils.other.PrintUtil;
import java.util.Iterator;

@CMDInfo(
   name = "Config",
   alias = "c",
   description = "Save and Load Configs."
)
public class ConfigCMD extends CMD {
   public void onCommand(String[] args, String command) {
      String var3;
      byte var4;
      switch(args.length) {
      case 0:
         this.configsMessage();
         this.commandsMessage();
         break;
      case 1:
         var3 = args[0];
         var4 = -1;
         switch(var3.hashCode()) {
         case -1335458389:
            if (var3.equals("delete")) {
               var4 = 2;
            }
            break;
         case 3322014:
            if (var3.equals("list")) {
               var4 = 3;
            }
            break;
         case 3327206:
            if (var3.equals("load")) {
               var4 = 1;
            }
            break;
         case 3522941:
            if (var3.equals("save")) {
               var4 = 0;
            }
         }

         switch(var4) {
         case 0:
            configManager.saveConfig(configManager.currentConfig);
         case 1:
         case 2:
            this.commandsMessage();
            return;
         case 3:
            this.configsMessage();
            return;
         default:
            this.commandsMessage();
            return;
         }
      case 2:
         var3 = args[0];
         var4 = -1;
         switch(var3.hashCode()) {
         case -1335458389:
            if (var3.equals("delete")) {
               var4 = 2;
            }
            break;
         case 3322014:
            if (var3.equals("list")) {
               var4 = 3;
            }
            break;
         case 3327206:
            if (var3.equals("load")) {
               var4 = 1;
            }
            break;
         case 3522941:
            if (var3.equals("save")) {
               var4 = 0;
            }
         }

         switch(var4) {
         case 0:
            configManager.saveConfig(args[1]);
         case 1:
            configManager.loadConfig(args[1]);
         case 2:
            configManager.delete(args[1]);
         case 3:
            this.configsMessage();
            break;
         default:
            this.commandsMessage();
         }
      }

   }

   private void configsMessage() {
      PrintUtil.message("Â§fÂ§lSlack configs:");
      Iterator var1 = configManager.getConfigList().iterator();

      while(var1.hasNext()) {
         String str = (String)var1.next();
         PrintUtil.message("Â§e " + str);
      }

   }

   private void commandsMessage() {
      PrintUtil.message("Â§fÂ§lConfig commands:");
      PrintUtil.message("Â§f .config save [config name]");
      PrintUtil.message("Â§f .config load [config name]");
      PrintUtil.message("Â§f .config delete [config name]");
      PrintUtil.message("Â§f .config list");
   }
}
