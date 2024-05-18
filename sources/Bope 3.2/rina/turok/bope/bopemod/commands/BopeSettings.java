package rina.turok.bope.bopemod.commands;

import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeMessage;

public class BopeSettings extends BopeCommand {
   public BopeSettings() {
      super("settings", "To save/load settings.");
   }

   public boolean get_message(String[] message) {
      String what = "null";
      if (message.length > 1) {
         what = message[1];
      }

      if (what.equals("null")) {
         BopeMessage.send_client_error_message("settings save/load");
         return true;
      } else {
         if (what.equalsIgnoreCase("save")) {
            Bope.get_config_manager().save_settings();
            Bope.get_config_manager().save_client();
            Bope.get_config_manager().save_binds();
            Bope.get_config_manager().save_friends();
            BopeMessage.send_client_message(Bope.dg + "Successfully " + Bope.r + "saved!");
         } else {
            if (!what.equalsIgnoreCase("load")) {
               BopeMessage.send_client_error_message("settings save/load");
               return true;
            }

            Bope.get_config_manager().load_settings();
            Bope.get_config_manager().load_client();
            Bope.get_config_manager().load_binds();
            Bope.get_config_manager().load_friends();
            BopeMessage.send_client_message(Bope.dg + "Successfully " + Bope.r + "loaded!");
         }

         return true;
      }
   }
}
