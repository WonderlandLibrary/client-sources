package com.viaversion.viaversion.sponge.commands;

import com.viaversion.viaversion.SpongePlugin;
import com.viaversion.viaversion.api.command.ViaCommandSender;
import java.util.UUID;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

public class SpongePlayer implements ViaCommandSender {
   private final ServerPlayer player;

   public SpongePlayer(ServerPlayer player) {
      this.player = player;
   }

   @Override
   public boolean hasPermission(String permission) {
      return this.player.hasPermission(permission);
   }

   @Override
   public void sendMessage(String msg) {
      this.player.sendMessage(SpongePlugin.LEGACY_SERIALIZER.deserialize(msg));
   }

   @Override
   public UUID getUUID() {
      return this.player.uniqueId();
   }

   @Override
   public String getName() {
      return this.player.friendlyIdentifier().orElse(this.player.identifier());
   }
}
