package exhibition.management.command.impl;

import exhibition.management.command.Command;
import exhibition.module.ModuleManager;
import exhibition.util.misc.ChatUtil;

public class LoadConfig extends Command {
   public LoadConfig(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      ModuleManager.loadStatus();
      ModuleManager.loadSettings();
      ChatUtil.printChat("Loaded");
   }

   public String getUsage() {
      return null;
   }
}
