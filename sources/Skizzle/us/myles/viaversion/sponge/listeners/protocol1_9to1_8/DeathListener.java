/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.spongepowered.api.entity.living.player.Player
 *  org.spongepowered.api.event.Listener
 *  org.spongepowered.api.event.Order
 *  org.spongepowered.api.event.entity.DestructEntityEvent$Death
 *  org.spongepowered.api.world.World
 */
package us.myles.ViaVersion.sponge.listeners.protocol1_9to1_8;

import java.util.Optional;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.world.World;
import us.myles.ViaVersion.SpongePlugin;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import us.myles.ViaVersion.sponge.listeners.ViaSpongeListener;

public class DeathListener
extends ViaSpongeListener {
    public DeathListener(SpongePlugin plugin) {
        super(plugin, Protocol1_9To1_8.class);
    }

    @Listener(order=Order.LAST)
    public void onDeath(DestructEntityEvent.Death e) {
        if (!(e.getTargetEntity() instanceof Player)) {
            return;
        }
        Player p = (Player)e.getTargetEntity();
        if (this.isOnPipe(p.getUniqueId()) && Via.getConfig().isShowNewDeathMessages() && this.checkGamerule(p.getWorld())) {
            this.sendPacket(p, e.getMessage().toPlain());
        }
    }

    public boolean checkGamerule(World w) {
        Optional gamerule = w.getGameRule("showDeathMessages");
        if (gamerule.isPresent()) {
            try {
                return Boolean.parseBoolean((String)gamerule.get());
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private void sendPacket(final Player p, final String msg) {
        Via.getPlatform().runSync(new Runnable(){

            @Override
            public void run() {
                PacketWrapper wrapper = new PacketWrapper(44, null, DeathListener.this.getUserConnection(p.getUniqueId()));
                try {
                    int entityId = DeathListener.this.getEntityId(p);
                    wrapper.write(Type.VAR_INT, 2);
                    wrapper.write(Type.VAR_INT, entityId);
                    wrapper.write(Type.INT, entityId);
                    Protocol1_9To1_8.FIX_JSON.write(wrapper, msg);
                    wrapper.send(Protocol1_9To1_8.class);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

