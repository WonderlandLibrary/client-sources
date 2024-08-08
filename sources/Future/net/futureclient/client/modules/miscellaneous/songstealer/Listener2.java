package net.futureclient.client.modules.miscellaneous.songstealer;

import net.futureclient.client.events.Event;
import net.futureclient.client.Fj;
import net.futureclient.client.YH;
import net.futureclient.client.dh;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.modules.miscellaneous.SongStealer;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener2 extends n<we>
{
    public final SongStealer k;
    
    public Listener2(final SongStealer k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketBlockAction) {
            final SPacketBlockAction sPacketBlockAction = (SPacketBlockAction)eventPacket.M();
            if (SongStealer.getMinecraft().player.getDistanceSq(sPacketBlockAction.getBlockPosition()) > SongStealer.M(this.k).B().floatValue() * SongStealer.M(this.k).B().floatValue() && SongStealer.M(this.k).B().floatValue() > 0.0f) {
                return;
            }
            final dh dh = net.futureclient.client.dh.values()[sPacketBlockAction.getData1()];
            final int data2 = sPacketBlockAction.getData2();
            if (SongStealer.e(this.k) > 0 && SongStealer.M(this.k)) {
                SongStealer.M(this.k).add(new YH(SongStealer.e(this.k)));
            }
            SongStealer.M(this.k).add(new Fj(dh, (byte)data2));
            SongStealer.M(this.k, true);
            SongStealer.M(this.k, 0);
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
