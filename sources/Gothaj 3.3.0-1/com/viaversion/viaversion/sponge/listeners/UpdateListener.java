package com.viaversion.viaversion.sponge.listeners;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.update.UpdateUtil;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ServerSideConnectionEvent.Join;

public class UpdateListener {
   @Listener
   public void onJoin(Join join) {
      if (join.player().hasPermission("viaversion.update") && Via.getConfig().isCheckForUpdates()) {
         UpdateUtil.sendUpdateMessage(join.player().uniqueId());
      }
   }
}
