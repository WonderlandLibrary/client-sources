package rina.turok.bope.bopemod.commands;

import org.lwjgl.input.Keyboard;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeMessage;
import rina.turok.bope.bopemod.BopeModule;

public class BopeBind extends BopeCommand {
   public BopeBind() {
      super("bind", "To bind module.");
   }

   public boolean get_message(String[] message) {
      String module = "null;";
      String key = "null";
      if (message.length > 1) {
         module = message[1];
      }

      if (message.length > 2) {
         key = message[2].toUpperCase();
      }

      if (message.length > 3) {
         BopeMessage.send_client_error_message("bind module key");
         return true;
      } else if (module.equals("null")) {
         BopeMessage.send_client_error_message("bind module key");
         return true;
      } else if (key.equals("null")) {
         BopeMessage.send_client_error_message("bind module key");
         return true;
      } else {
         module = module.toUpperCase();
         BopeModule module_requested = Bope.get_module_manager().get_module_with_tag(module);
         if (module_requested == null) {
            BopeMessage.send_client_error_message("Module does not exist.");
            return true;
         } else if (key.equalsIgnoreCase("NONE")) {
            module_requested.set_bind(0);
            BopeMessage.send_client_message(Bope.dg + module_requested.get_tag() + Bope.r + " is bound to " + Bope.g + "none");
            return true;
         } else {
            int new_bind = Keyboard.getKeyIndex(key.toUpperCase());
            if (new_bind == 0) {
               BopeMessage.send_client_error_message("Key does not exist.");
               return true;
            } else if (new_bind == module_requested.get_bind(0)) {
               BopeMessage.send_client_error_message("The " + module_requested.get_tag() + " already have it key.");
               return true;
            } else {
               module_requested.set_bind(new_bind);
               BopeMessage.send_client_message(Bope.dg + module_requested.get_tag() + Bope.r + " is bound to " + Bope.g + module_requested.get_bind(""));
               return true;
            }
         }
      }
   }
}
