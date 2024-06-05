package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import net.abo;

public class AutoTeamSubCmd extends ViaSubCommand {
   public String name() {
      return "autoteam";
   }

   public String description() {
      return "Toggle automatically teaming to prevent colliding.";
   }

   public boolean execute(ViaCommandSender var1, String[] var2) {
      abo.a();
      ConfigurationProvider var4 = Via.d().getConfigurationProvider();
      boolean var5 = !Via.c().l();
      var4.set("auto-team", Boolean.valueOf(var5));
      var4.saveConfig();
      this.a(var1, "&6We will %s", new Object[]{"&aautomatically team players"});
      this.a(var1, "&6All players will need to re-login for the change to take place.", new Object[0]);
      return true;
   }
}
