package net.futureclient.client.modules.render.waypoints;

import net.futureclient.client.events.Event;
import java.util.Iterator;
import net.futureclient.client.Xa;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.futureclient.client.modules.render.Waypoints;
import net.futureclient.client.tF;
import net.futureclient.client.n;

public class Listener3 extends n<tF>
{
    public final Waypoints k;
    
    public Listener3(final Waypoints k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final tF tf) {
        if (Waypoints.b(this.k).M()) {
            final Iterator<Entity> iterator = Waypoints.getMinecraft20().world.playerEntities.iterator();
            while (iterator.hasNext()) {
                final EntityPlayer entityPlayer;
                if ((entityPlayer = (EntityPlayer)iterator.next()).getName().equals(tf.M())) {
                    String replaceAll = "";
                    if (Waypoints.getMinecraft9().isSingleplayer()) {
                        replaceAll = "singleplayer";
                    }
                    else if (Waypoints.getMinecraft30().getCurrentServerData() != null) {
                        replaceAll = Waypoints.getMinecraft11().getCurrentServerData().serverIP.replaceAll(":", "_");
                    }
                    else if (Waypoints.getMinecraft38().isConnectedToRealms()) {
                        replaceAll = "realms";
                    }
                    final Xa xa = new Xa(new StringBuilder().insert(0, entityPlayer.getName()).append("_logout_spot").toString(), replaceAll, Double.parseDouble(Waypoints.M(this.k).format(entityPlayer.posX).replaceAll(",", ".")), Double.parseDouble(Waypoints.M(this.k).format(entityPlayer.posY).replaceAll(",", ".")), Double.parseDouble(Waypoints.M(this.k).format(entityPlayer.posZ).replaceAll(",", ".")), Waypoints.getMinecraft2().world.provider.getDimensionType().getName());
                    if (Waypoints.M(this.k, xa)) {
                        continue;
                    }
                    this.k.k.add(xa);
                }
            }
        }
    }
    
    public void M(final Event event) {
        this.M((tF)event);
    }
}
