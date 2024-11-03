package net.augustus.commands.commands;

import net.augustus.commands.Command;
import net.augustus.utils.interfaces.MM;

public class CommandBlockESP extends Command implements MM {
   public CommandBlockESP() {
      super(".blockesp");
   }

   @Override
   public void commandAction(String[] message) {
      super.commandAction(message);
      if (message.length > 1) {
         if (message[1].equalsIgnoreCase("setID") && message.length >= 3) {
            int i = -1;

            try {
               i = Integer.parseInt(message[2]);
            } catch (Exception var4) {
               System.err.println("No Integer");
            }

            if (i != -1) {
               mm.blockESP.id.setValue((double)i);
               this.sendChat("Set BlockESP ID to " + (int)mm.blockESP.id.getValue());
               return;
            }
         } else if (message[1].equalsIgnoreCase("getID")) {
            this.sendChat("BlockID: " + (int)mm.blockESP.id.getValue());
            return;
         }
      }

      this.errorMessage();
   }

   public void errorMessage() {
      this.sendChat(".blockESP [setID/getID] [BlockID]");
   }

   @Override
   public void helpMessage() {
      this.sendChat(".blockESP (Get or set the blockID for BlockESP)");
   }
}
