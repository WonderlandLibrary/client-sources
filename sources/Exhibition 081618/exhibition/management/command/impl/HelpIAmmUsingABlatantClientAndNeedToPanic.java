package exhibition.management.command.impl;

import exhibition.Client;
import exhibition.event.EventSystem;
import exhibition.management.command.Command;
import exhibition.module.Module;

public class HelpIAmmUsingABlatantClientAndNeedToPanic extends Command {
   public HelpIAmmUsingABlatantClientAndNeedToPanic(String[] names, String description) {
      super(names, description);
   }

   public String getUsage() {
      return "You're using a blatant client for fucks sake.";
   }

   public void fire(String[] args) {
      Module[] var2 = (Module[])Client.getModuleManager().getArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module module = var2[var4];
         if (module.isEnabled()) {
            module.setEnabled(false);
            EventSystem.unregister(module);
            module.onDisable();
         }
      }

   }
}
