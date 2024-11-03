package net.augustus.commands.commands;

import net.augustus.commands.Command;

public class CommandTest extends Command {
   public CommandTest() {
      super(".test");
   }

   @Override
   public void commandAction(String[] message) {
      mc.thePlayer.sendChatMessage("Dont mind that. This is a test command");
   }
}
