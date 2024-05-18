package rina.turok.bope.bopemod.commands;

import java.util.Iterator;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeMessage;

public class BopeHelp extends BopeCommand {
   public BopeHelp() {
      super("help", "A help util.");
   }

   public boolean get_message(String[] message) {
      String type = "null";
      if (message.length > 1) {
         type = message[1];
      }

      if (message.length > 2) {
         BopeMessage.send_client_error_message("help list/command");
         return true;
      } else if (type.equals("null")) {
         BopeMessage.send_client_error_message("help list/command");
         return true;
      } else if (type.equalsIgnoreCase("list")) {
         int count = 0;
         int size = BopeListCommand.get_pure_command_list().size();
         StringBuilder commands_names = new StringBuilder();
         Iterator var6 = BopeListCommand.get_pure_command_list().iterator();

         while(var6.hasNext()) {
            BopeCommand commands = (BopeCommand)var6.next();
            ++count;
            if (count >= size) {
               commands_names.append(Bope.dg + commands.get_name() + Bope.r + ".");
            } else {
               commands_names.append(Bope.dg + commands.get_name() + Bope.r + ", ");
            }
         }

         BopeMessage.send_client_message(commands_names.toString());
         return true;
      } else {
         BopeCommand command_requested = BopeListCommand.get_command_with_name(type);
         if (command_requested == null) {
            BopeMessage.send_client_error_message("This command does not exist.");
            return true;
         } else {
            String upper_case_name = command_requested.get_name();
            BopeMessage.send_client_message(Bope.dg + upper_case_name.toUpperCase() + Bope.r + " " + command_requested.get_description());
            return true;
         }
      }
   }
}
