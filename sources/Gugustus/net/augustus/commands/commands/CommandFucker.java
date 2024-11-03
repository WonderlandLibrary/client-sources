package net.augustus.commands.commands;

import net.augustus.commands.Command;
import net.augustus.utils.interfaces.MM;

public class CommandFucker extends Command implements MM {
   public CommandFucker() {
      super(".fucker");
   }

   @Override
   public void commandAction(String[] message) {
      super.commandAction(message);
      if (message.length > 1) {
         if (message[1].equalsIgnoreCase("set") && message.length >= 3) {
            int i = -1;

            try {
               i = Integer.parseInt(message[2]);
            } catch (Exception var4) {
               System.err.println("No Integer");
            }

            if (i != -1) {
               mm.fucker.customID.setValue((double)i);
               this.sendChat("Set Fucker ID to " + (int)mm.fucker.customID.getValue());
               return;
            }
         } else if (message[1].equalsIgnoreCase("get")) {
            this.sendChat("BlockID: " + (int)mm.fucker.customID.getValue());
            return;
         }
      }

      this.errorMessage();
   }

   public void errorMessage() {
      this.sendChat(".fucker [set/get] [BlockID]");
   }

   @Override
   public void helpMessage() {
      this.sendChat(".fucker (Get or set the blockID for custom mode)");
   }
}
