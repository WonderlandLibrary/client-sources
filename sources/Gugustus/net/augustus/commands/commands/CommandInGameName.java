package net.augustus.commands.commands;

import net.augustus.commands.Command;
import net.minecraft.client.gui.GuiScreen;

public class CommandInGameName extends Command {
   public CommandInGameName() {
      super(".ign");
   }

   @Override
   public void commandAction(String[] message) {
      if (mc.thePlayer != null) {
         String name = mc.thePlayer.getName();
         GuiScreen.setClipboardString(name);
         this.sendChat("Your name: " + name);
      }
   }

   @Override
   public void helpMessage() {
      this.sendChat(".ign (Copys ingame name to clipboard)");
   }
}
