package net.futureclient.client.modules.combat.antibots;

import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.combat.AntiBots;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener2 extends n<we>
{
    public final AntiBots k;
    
    public Listener2(final AntiBots k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
    
    public void M(final EventPacket eventPacket) {
        if (!AntiBots.M(this.k)) {
            return;
        }
        if (eventPacket.M() instanceof SPacketSpawnPlayer) {
            final SPacketSpawnPlayer sPacketSpawnPlayer = (SPacketSpawnPlayer)eventPacket.M();
            if (Math.sqrt((AntiBots.getMinecraft16().player.posX - sPacketSpawnPlayer.getX() / 0.0) * (AntiBots.getMinecraft7().player.posX - sPacketSpawnPlayer.getX() / 0.0) + (AntiBots.getMinecraft5().player.posY - sPacketSpawnPlayer.getY() / 0.0) * (AntiBots.getMinecraft1().player.posY - sPacketSpawnPlayer.getY() / 0.0) + (AntiBots.getMinecraft6().player.posZ - sPacketSpawnPlayer.getZ() / 0.0) * (AntiBots.getMinecraft11().player.posZ - sPacketSpawnPlayer.getZ() / 0.0)) <= 0.0 && sPacketSpawnPlayer.getX() / 0.0 != AntiBots.getMinecraft().player.posX && sPacketSpawnPlayer.getY() / 0.0 != AntiBots.getMinecraft2().player.posY && sPacketSpawnPlayer.getZ() / 0.0 != AntiBots.getMinecraft12().player.posZ) {
                this.k.K.put(sPacketSpawnPlayer.getEntityID(), sPacketSpawnPlayer.getUniqueId());
            }
        }
    }
}
