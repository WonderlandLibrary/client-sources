package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;

public class HelpSubCmd extends ViaSubCommand {
   public String name() {
      return "help";
   }

   public String description() {
      return "You are looking at it right now!";
   }

   public boolean execute(ViaCommandSender var1, String[] var2) {
      Via.b().a().a(var1);
      return true;
   }
}
