package rina.turok.bope.bopemod.commands;

import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeMessage;
import rina.turok.bope.bopemod.BopeModule;

public class BopeToggleMessage extends BopeCommand {
   public BopeToggleMessage() {
      super("alert", "For set alert if a module does active.");
   }

   public boolean get_message(String[] message) {
      String module = "null";
      String state = "null";
      if (message.length > 1) {
         module = message[1];
      }

      if (message.length > 2) {
         state = message[2];
      }

      if (message.length > 3) {
         BopeMessage.send_client_error_message("alert module true/on/false/off");
         return true;
      } else if (module.equals("null")) {
         BopeMessage.send_client_error_message("alert module true/on/false/off");
         return true;
      } else if (state.equals("null")) {
         BopeMessage.send_client_error_message("alert module true/on/false/off");
         return true;
      } else {
         module = module.toLowerCase();
         state = state.toLowerCase();
         BopeModule module_requested = Bope.get_module_manager().get_module_with_tag(module);
         if (module_requested == null) {
            BopeMessage.send_client_error_message("This module does not exist.");
            return true;
         } else {
            boolean value = true;
            if (!state.equals("true") && !state.equals("on")) {
               if (!state.equals("false") && !state.equals("off")) {
                  BopeMessage.send_client_error_message("This value does not exist.");
                  return true;
               }

               value = false;
            } else {
               value = true;
            }

            module_requested.alert(value);
            BopeMessage.send_client_message("Alert " + (module_requested.alert() ? Bope.dg + module_requested.get_tag() : Bope.re + module_requested.get_tag()));
            return true;
         }
      }
   }
}
