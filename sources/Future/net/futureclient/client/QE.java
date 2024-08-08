package net.futureclient.client;

import net.futureclient.client.events.Event;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.futureclient.client.events.EventPacket;

private final class QE extends n<we> {
    public final mf k;
    
    public QE(final mf mf, final df df) {
        this(mf);
    }
    
    private QE(final mf k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        if (mf.M(this.k) != null) {
            if (eventPacket.M() instanceof SPacketSetPassengers) {
                final SPacketSetPassengers sPacketSetPassengers;
                if ((sPacketSetPassengers = (SPacketSetPassengers)eventPacket.M()).getEntityId() == mf.M(this.k).getEntityId()) {
                    final int[] passengerIds;
                    final int length = (passengerIds = sPacketSetPassengers.getPassengerIds()).length;
                    int i = 0;
                    int n = 0;
                    while (i < length) {
                        if (passengerIds[n] == mf.b(this.k).player.getEntityId()) {
                            return;
                        }
                        i = ++n;
                    }
                    mf.M(this.k).isDead = false;
                    mf.B(this.k).world.spawnEntity(mf.M(this.k));
                    mf.M(this.k, null);
                }
            }
            else if (eventPacket.M() instanceof SPacketDestroyEntities) {
                final int[] entityIDs;
                final int length2 = (entityIDs = ((SPacketDestroyEntities)eventPacket.M()).getEntityIDs()).length;
                int j = 0;
                int n2 = 0;
                while (j < length2) {
                    if (entityIDs[n2] != mf.M(this.k).getEntityId()) {
                        return;
                    }
                    j = ++n2;
                }
                mf.M(this.k, null);
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}