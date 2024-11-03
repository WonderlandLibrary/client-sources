package com.viaversion.viaversion.commands.defaultsubs;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.util.DumpUtil;

public class DumpSubCmd extends ViaSubCommand {
   @Override
   public String name() {
      return "dump";
   }

   @Override
   public String description() {
      return "Dump information about your server, this is helpful if you report bugs.";
   }

   @Override
   public boolean execute(ViaCommandSender sender, String[] args) {
      DumpUtil.postDump(sender.getUUID()).whenComplete((url, e) -> {
         if (e != null) {
            sender.sendMessage("§4" + e.getMessage());
         } else {
            sender.sendMessage("§2We've made a dump with useful information, report your issue and provide this url: " + url);
         }
      });
      return true;
   }
}
