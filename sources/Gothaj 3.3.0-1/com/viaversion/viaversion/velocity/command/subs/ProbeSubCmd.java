package com.viaversion.viaversion.velocity.command.subs;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import com.viaversion.viaversion.api.command.ViaSubCommand;
import com.viaversion.viaversion.velocity.platform.VelocityViaConfig;

public class ProbeSubCmd extends ViaSubCommand {
   @Override
   public String name() {
      return "probe";
   }

   @Override
   public String description() {
      return "Forces ViaVersion to scan server protocol versions "
         + (((VelocityViaConfig)Via.getConfig()).getVelocityPingInterval() == -1 ? "" : "(Also happens at an interval)");
   }

   @Override
   public boolean execute(ViaCommandSender sender, String[] args) {
      Via.proxyPlatform().protocolDetectorService().probeAllServers();
      sendMessage(sender, "&6Started searching for protocol versions", new Object[0]);
      return true;
   }
}
