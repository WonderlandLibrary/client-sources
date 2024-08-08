package net.futureclient.client.modules.movement.speed;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.ZG;
import net.futureclient.client.db;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.movement.Speed;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final Speed k;
    
    public Listener1(final Speed k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (this.k.autoSprint.M()) {
            Speed.getMinecraft52().player.setSprinting(Speed.b(this.k));
        }
        if (this.k.mode.M() == db.fC.k && Speed.getMinecraft6().player.collidedHorizontally && Speed.getMinecraft141().player.motionY < 1.1034378113E-314 + ZG.M()) {
            if (Speed.getMinecraft105().player.onGround) {
                Speed.getMinecraft37().player.connection.sendPacket((Packet)new CPacketPlayer(false));
                Speed.e(this.k, true);
                return;
            }
            Speed.getMinecraft128().player.motionY = 1.4429571377E-314 + ZG.M();
            Speed.e(this.k, true);
            Speed.getMinecraft90().player.connection.sendPacket((Packet)new CPacketPlayer(true));
        }
    }
}
