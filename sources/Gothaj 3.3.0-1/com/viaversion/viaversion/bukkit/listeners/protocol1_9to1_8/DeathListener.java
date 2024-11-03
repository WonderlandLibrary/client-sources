package com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class DeathListener extends ViaBukkitListener {
   public DeathListener(Plugin plugin) {
      super(plugin, Protocol1_9To1_8.class);
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.MONITOR
   )
   public void onDeath(PlayerDeathEvent e) {
      Player p = e.getEntity();
      if (this.isOnPipe(p) && Via.getConfig().isShowNewDeathMessages() && this.checkGamerule(p.getWorld()) && e.getDeathMessage() != null) {
         this.sendPacket(p, e.getDeathMessage());
      }
   }

   public boolean checkGamerule(World w) {
      try {
         return Boolean.parseBoolean(w.getGameRuleValue("showDeathMessages"));
      } catch (Exception var3) {
         return false;
      }
   }

   private void sendPacket(Player p, String msg) {
      Via.getPlatform().runSync(() -> {
         UserConnection userConnection = this.getUserConnection(p);
         if (userConnection != null) {
            PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.COMBAT_EVENT, null, userConnection);

            try {
               wrapper.write(Type.VAR_INT, 2);
               wrapper.write(Type.VAR_INT, p.getEntityId());
               wrapper.write(Type.INT, p.getEntityId());
               Protocol1_9To1_8.FIX_JSON.write(wrapper, msg);
               wrapper.scheduleSend(Protocol1_9To1_8.class);
            } catch (Exception var6) {
               var6.printStackTrace();
            }
         }
      });
   }
}
