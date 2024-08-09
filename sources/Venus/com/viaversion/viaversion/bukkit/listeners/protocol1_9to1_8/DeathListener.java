/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.entity.PlayerDeathEvent
 *  org.bukkit.plugin.Plugin
 */
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

public class DeathListener
extends ViaBukkitListener {
    public DeathListener(Plugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }

    @EventHandler(ignoreCancelled=true, priority=EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent playerDeathEvent) {
        Player player = playerDeathEvent.getEntity();
        if (this.isOnPipe(player) && Via.getConfig().isShowNewDeathMessages() && this.checkGamerule(player.getWorld()) && playerDeathEvent.getDeathMessage() != null) {
            this.sendPacket(player, playerDeathEvent.getDeathMessage());
        }
    }

    public boolean checkGamerule(World world) {
        try {
            return Boolean.parseBoolean(world.getGameRuleValue("showDeathMessages"));
        } catch (Exception exception) {
            return true;
        }
    }

    private void sendPacket(Player player, String string) {
        Via.getPlatform().runSync(new Runnable(this, player, string){
            final Player val$p;
            final String val$msg;
            final DeathListener this$0;
            {
                this.this$0 = deathListener;
                this.val$p = player;
                this.val$msg = string;
            }

            @Override
            public void run() {
                UserConnection userConnection = DeathListener.access$000(this.this$0, this.val$p);
                if (userConnection != null) {
                    PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9.COMBAT_EVENT, null, userConnection);
                    try {
                        packetWrapper.write(Type.VAR_INT, 2);
                        packetWrapper.write(Type.VAR_INT, this.val$p.getEntityId());
                        packetWrapper.write(Type.INT, this.val$p.getEntityId());
                        Protocol1_9To1_8.FIX_JSON.write(packetWrapper, this.val$msg);
                        packetWrapper.scheduleSend(Protocol1_9To1_8.class);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
    }

    static UserConnection access$000(DeathListener deathListener, Player player) {
        return deathListener.getUserConnection(player);
    }
}

