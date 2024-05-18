package rina.turok.bope.bopemod.commands;

import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeMessage;
import rina.turok.bope.bopemod.manager.BopeCommandManager;

public class BopePrefix extends BopeCommand {
   public BopePrefix() {
      super("prefix", "Change prefix.");
   }

   public boolean get_message(String[] message) {
      String prefix = "null";
      if (message.length > 1) {
         prefix = message[1];
      }

      if (message.length > 2) {
         BopeMessage.send_client_error_message("prefix character");
         return true;
      } else if (prefix.equals("null")) {
         BopeMessage.send_client_error_message("prefix character");
         return true;
      } else {
         Bope.get_instance();
         BopeCommandManager var10000 = Bope.command_manager;
         BopeCommandManager.set_prefix(prefix);
         BopeMessage.send_client_message("The new prefix is " + Bope.g + prefix);
         return true;
      }
   }
}
