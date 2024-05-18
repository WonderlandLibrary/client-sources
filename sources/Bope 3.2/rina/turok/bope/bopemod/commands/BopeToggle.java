package rina.turok.bope.bopemod.commands;

import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeMessage;
import rina.turok.bope.bopemod.BopeModule;

public class BopeToggle extends BopeCommand {
   public BopeToggle() {
      super("t", "For toggle module.");
   }

   public boolean get_message(String[] message) {
      String module = "null";
      if (message.length > 1) {
         module = message[1];
      }

      if (message.length > 2) {
         BopeMessage.send_client_error_message("t module");
         return true;
      } else if (module.equals("null")) {
         BopeMessage.send_client_error_message("t module");
         return true;
      } else {
         Bope.get_instance();
         BopeModule module_requested = Bope.module_manager.get_module_with_tag(module);
         if (module_requested != null) {
            module_requested.toggle();
            if (!module_requested.alert()) {
               BopeMessage.send_client_message(module_requested.is_active() ? Bope.dg + module_requested.get_tag() : Bope.re + module_requested.get_tag());
            }
         } else {
            BopeMessage.send_client_error_message("Module does not exist.");
         }

         return true;
      }
   }
}
