package net.augustus.commands.commands;

import net.augustus.Augustus;
import net.augustus.commands.Command;
import net.minecraft.util.ChatComponentText;

public class CommandHelp extends Command {
   public CommandHelp() {
      super(".help");
   }

   @Override
   public void commandAction(String[] message) {
      super.commandAction(message);
      String client = "[" + Augustus.getInstance().getName() + "] ";
      mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("\n\n" + client + "Help commands:"));

      for(Command command : Augustus.getInstance().getCommandManager().getCommands()) {
         command.helpMessage();
      }
   }
}
