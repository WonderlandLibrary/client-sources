package com.viaversion.viaversion.bungee.listeners;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.update.UpdateUtil;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class UpdateListener implements Listener {
   @EventHandler
   public void onJoin(PostLoginEvent e) {
      if (e.getPlayer().hasPermission("viaversion.update") && Via.getConfig().isCheckForUpdates()) {
         UpdateUtil.sendUpdateMessage(e.getPlayer().getUniqueId());
      }
   }
}
