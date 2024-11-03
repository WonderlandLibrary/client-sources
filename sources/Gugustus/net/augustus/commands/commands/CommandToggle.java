package net.augustus.commands.commands;

import net.augustus.Augustus;
import net.augustus.commands.Command;
import net.augustus.modules.Module;

public class CommandToggle extends Command {
   public CommandToggle() {
      super(".t");
   }

   @Override
   public void commandAction(String[] message) {
      super.commandAction(message);
      if (message.length > 1) {
         for(Module module : Augustus.getInstance().getModuleManager().getModules()) {
            if (message[1].equalsIgnoreCase(module.getName())) {
               module.toggle();
               this.sendChat("" + module.getName() + " toggled");
               return;
            }
         }
      } else {
         this.sendChat(".t [Module]");
      }
   }

   @Override
   public void helpMessage() {
      this.sendChat(".t (Toggles a module)");
   }
}
