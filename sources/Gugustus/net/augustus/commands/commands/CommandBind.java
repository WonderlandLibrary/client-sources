package net.augustus.commands.commands;

import net.augustus.Augustus;
import net.augustus.commands.Command;
import net.augustus.modules.Module;
import net.augustus.utils.ChatUtil;

import org.lwjgl.input.Keyboard;

public class CommandBind extends Command {
   public CommandBind() {
      super(".bind");
   }

   @Override
   public void commandAction(String[] message) {
      super.commandAction(message);
      if (message.length >= 3) {
         for(Module module : Augustus.getInstance().getModuleManager().getModules()) {
            if (message[1].equalsIgnoreCase(module.getName())) {
               for(int i = 0; i < 84; ++i) {
                  if (Keyboard.getKeyName(i).equalsIgnoreCase(message[2])) {
                     this.setKey(i, module, message[2].toUpperCase());
                     return;
                  }
               }
            }
         }
      }

      this.errorMessage();
   }

   private void setKey(int key, Module module, String keyName) {
      module.setKey(key);
      this.sendChat("" + module.getName() + " bound to " + keyName.toUpperCase());
   }

   @Override
   public void helpMessage() {
      this.sendChat(".bind (Binds a module to a key)");
   }

   public void errorMessage() {
      this.sendChat(".bind [Module] [Key]");
   }
   
   
}
