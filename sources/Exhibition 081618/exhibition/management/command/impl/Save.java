package exhibition.management.command.impl;

import exhibition.management.command.Command;
import exhibition.module.ModuleManager;
import exhibition.util.misc.ChatUtil;

public class Save extends Command {
   public Save(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      ModuleManager.saveStatus();
      ModuleManager.saveSettings();
      ChatUtil.printChat("Saved");
   }

   public String getUsage() {
      return null;
   }
}
