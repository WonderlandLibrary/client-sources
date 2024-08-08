package net.futureclient.client.modules.movement.speed;

import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.db;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Speed;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener4 extends n<Ag>
{
    public final Speed k;
    
    public Listener4(final Speed k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
    
    @Override
    public void M(final Ag ag) {
        if (this.k.mode.M() == db.fC.k && ag.M() instanceof CPacketPlayer && Speed.C(this.k)) {
            ag.M(true);
            Speed.e(this.k, false);
        }
    }
}
