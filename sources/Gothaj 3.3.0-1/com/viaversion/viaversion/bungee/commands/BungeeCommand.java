package com.viaversion.viaversion.bungee.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BungeeCommand extends Command implements TabExecutor {
   private final BungeeCommandHandler handler;

   public BungeeCommand(BungeeCommandHandler handler) {
      super("viaversion", "viaversion.command", new String[]{"viaver", "vvbungee"});
      this.handler = handler;
   }

   public void execute(CommandSender commandSender, String[] strings) {
      this.handler.onCommand(new BungeeCommandSender(commandSender), strings);
   }

   public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
      return this.handler.onTabComplete(new BungeeCommandSender(commandSender), strings);
   }
}
