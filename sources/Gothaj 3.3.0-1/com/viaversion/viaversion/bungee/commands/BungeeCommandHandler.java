package com.viaversion.viaversion.bungee.commands;

import com.viaversion.viaversion.bungee.commands.subs.ProbeSubCmd;
import com.viaversion.viaversion.commands.ViaCommandHandler;

public class BungeeCommandHandler extends ViaCommandHandler {
   public BungeeCommandHandler() {
      this.registerSubCommand(new ProbeSubCmd());
   }
}
