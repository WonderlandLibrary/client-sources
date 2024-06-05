package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;
import net.abo;

public class DisplayLeaksSubCmd extends ViaSubCommand {
   public String name() {
      return "displayleaks";
   }

   public String description() {
      return "Try to hunt memory leaks!";
   }

   public boolean execute(ViaCommandSender var1, String[] var2) {
      boolean var3 = abo.c();
      if(ResourceLeakDetector.getLevel() != Level.ADVANCED) {
         ResourceLeakDetector.setLevel(Level.ADVANCED);
      }

      ResourceLeakDetector.setLevel(Level.DISABLED);
      this.a(var1, "&6Leak detector is now %s", new Object[]{ResourceLeakDetector.getLevel() == Level.ADVANCED?"&aenabled":"&cdisabled"});
      return true;
   }
}
