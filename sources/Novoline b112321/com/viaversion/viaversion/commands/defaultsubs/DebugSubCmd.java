package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import net.abo;

public class DebugSubCmd extends ViaSubCommand {
   public String name() {
      return "debug";
   }

   public String description() {
      return "Toggle debug mode";
   }

   public boolean execute(ViaCommandSender var1, String[] var2) {
      boolean var3 = abo.c();
      Via.b().a(!Via.b().c());
      this.a(var1, "&6Debug mode is now %s", new Object[]{Via.b().c()?"&aenabled":"&cdisabled"});
      return true;
   }
}
