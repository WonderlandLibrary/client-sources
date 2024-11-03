package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.ResourceLeakDetector.Level;

public class DisplayLeaksSubCmd extends ViaSubCommand {
   @Override
   public String name() {
      return "displayleaks";
   }

   @Override
   public String description() {
      return "Try to hunt memory leaks!";
   }

   @Override
   public boolean execute(ViaCommandSender sender, String[] args) {
      if (ResourceLeakDetector.getLevel() != Level.PARANOID) {
         ResourceLeakDetector.setLevel(Level.PARANOID);
      } else {
         ResourceLeakDetector.setLevel(Level.DISABLED);
      }

      sendMessage(sender, "&6Leak detector is now %s", new Object[]{ResourceLeakDetector.getLevel() == Level.PARANOID ? "&aenabled" : "&cdisabled"});
      return true;
   }
}
