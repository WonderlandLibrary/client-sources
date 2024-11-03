package com.viaversion.viaversion.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.viaversion.viaversion.VelocityPlugin;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;

public class VelocityCommandSender implements ViaCommandSender {
   private final CommandSource source;

   public VelocityCommandSender(CommandSource source) {
      this.source = source;
   }

   @Override
   public boolean hasPermission(String permission) {
      return this.source.hasPermission(permission);
   }

   @Override
   public void sendMessage(String msg) {
      this.source.sendMessage(VelocityPlugin.COMPONENT_SERIALIZER.deserialize(msg));
   }

   @Override
   public UUID getUUID() {
      return this.source instanceof Player ? ((Player)this.source).getUniqueId() : new UUID(0L, 0L);
   }

   @Override
   public String getName() {
      return this.source instanceof Player ? ((Player)this.source).getUsername() : "?";
   }
}
