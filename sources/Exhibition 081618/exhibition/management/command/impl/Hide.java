package exhibition.management.command.impl;

import exhibition.Client;
import exhibition.management.command.Command;
import exhibition.module.Module;
import exhibition.module.ModuleManager;
import exhibition.util.misc.ChatUtil;

public class Hide extends Command {
   public Hide(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else {
         Module module = null;
         if (args.length > 0) {
            module = Client.getModuleManager().get(args[0]);
         }

         if (module == null) {
            this.printUsage();
         } else {
            if (args.length == 1) {
               module.setHidden(!module.isHidden());
               ChatUtil.printChat("§4[§cE§4]§8 " + module.getName() + " is now " + (!module.isHidden() ? "§aShown" : "§cHidden") + " §8.");
               ModuleManager.saveStatus();
            }

         }
      }
   }

   public String getUsage() {
      return "toggle <module name>";
   }
}
