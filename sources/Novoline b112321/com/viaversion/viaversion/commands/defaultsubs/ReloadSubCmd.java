package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;

public class ReloadSubCmd extends ViaSubCommand {
   public String name() {
      return "reload";
   }

   public String description() {
      return "Reload the config from the disk";
   }

   public boolean execute(ViaCommandSender var1, String[] var2) {
      Via.d().getConfigurationProvider().reloadConfig();
      this.a(var1, "&6Configuration successfully reloaded! Some features may need a restart.", new Object[0]);
      return true;
   }
}
