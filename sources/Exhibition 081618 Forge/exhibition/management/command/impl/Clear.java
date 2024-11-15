package exhibition.management.command.impl;

import exhibition.management.command.Command;
import net.minecraft.client.Minecraft;

public class Clear extends Command {
   public Clear(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages();
   }

   public String getUsage() {
      return "Clear";
   }
}
