package com.viaversion.viaversion.bungee.commands;

import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeCommandSender implements ViaCommandSender {
   private final CommandSender sender;

   public BungeeCommandSender(CommandSender sender) {
      this.sender = sender;
   }

   @Override
   public boolean hasPermission(String permission) {
      return this.sender.hasPermission(permission);
   }

   @Override
   public void sendMessage(String msg) {
      this.sender.sendMessage(msg);
   }

   @Override
   public UUID getUUID() {
      return this.sender instanceof ProxiedPlayer ? ((ProxiedPlayer)this.sender).getUniqueId() : new UUID(0L, 0L);
   }

   @Override
   public String getName() {
      return this.sender.getName();
   }
}
