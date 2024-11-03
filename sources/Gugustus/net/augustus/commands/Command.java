package net.augustus.commands;

import net.augustus.Augustus;
import net.augustus.utils.interfaces.MC;
import net.minecraft.util.ChatComponentText;

public class Command implements MC {
   protected String command;
   protected String[] message;

   public Command(String command) {
      this.command = command;
   }

   public void commandAction(String[] message) {
      this.message = message;
   }

   public void sendChat(String msg) {
      String client = Augustus.getInstance().getName();
      mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(client + " " + msg));
   }

   public String getCommand() {
      return this.command;
   }

   public void setCommand(String command) {
      this.command = command;
   }

   public void helpMessage() {
   }
}
