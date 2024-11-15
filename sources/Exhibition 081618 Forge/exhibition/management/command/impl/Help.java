package exhibition.management.command.impl;

import exhibition.Client;
import exhibition.management.command.Command;
import exhibition.util.misc.ChatUtil;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.util.EnumChatFormatting;

public class Help extends Command {
   public Help(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      int i = 1;
      if (args == null) {
         ArrayList used = new ArrayList();
         Iterator var4 = Client.commandManager.getCommands().iterator();

         while(var4.hasNext()) {
            Command command = (Command)var4.next();
            if (!used.contains(command.getName())) {
               used.add(command.getName());
               ChatUtil.printChat("§4[§cE§4]§8 " + i + ". " + command.getName() + " - " + command.getDescription());
               ++i;
            }
         }

         ChatUtil.printChat("§4[§cE§4]§8 Specify a name of a command for details about it.");
      } else if (args.length > 0) {
         String name = args[0];
         Command command = Client.commandManager.getCommand(name);
         if (command == null) {
            ChatUtil.printChat("§4[§cE§4]§8 Could not find: " + name);
            return;
         }

         ChatUtil.printChat("§4[§cE§4]§8 " + command.getName() + ": " + command.getDescription());
         if (command.getUsage() != null) {
            ChatUtil.printChat(command.getUsage());
         }
      }

   }

   public String getUsage() {
      return "Help " + EnumChatFormatting.ITALIC + " [optional] " + EnumChatFormatting.RESET + "<Config>";
   }
}
