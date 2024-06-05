package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import net.abo;

public class DontBugMeSubCmd extends ViaSubCommand {
   public String name() {
      return "dontbugme";
   }

   public String description() {
      return "Toggle checking for updates";
   }

   public boolean execute(ViaCommandSender var1, String[] var2) {
      abo.c();
      ConfigurationProvider var4 = Via.d().getConfigurationProvider();
      boolean var5 = !Via.c().i();
      Via.c().setCheckForUpdates(var5);
      var4.saveConfig();
      this.a(var1, "&6We will %snotify you about updates.", new Object[]{"&a"});
      return true;
   }
}
