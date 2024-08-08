package net.futureclient.client.modules.miscellaneous.antivanish;

import net.futureclient.client.events.Event;
import java.util.Iterator;
import net.minecraft.network.Packet;
import net.futureclient.client.YI;
import net.futureclient.client.s;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.modules.miscellaneous.AntiVanish;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener1 extends n<we>
{
    public final AntiVanish k;
    
    public Listener1(final AntiVanish k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        final Packet<?> m;
        final SPacketPlayerListItem sPacketPlayerListItem;
        if ((m = eventPacket.M()) instanceof SPacketPlayerListItem && (sPacketPlayerListItem = (SPacketPlayerListItem)m).getAction() == SPacketPlayerListItem.Action.UPDATE_LATENCY) {
            for (final SPacketPlayerListItem.AddPlayerData addPlayerData : sPacketPlayerListItem.getEntries()) {
                if (AntiVanish.getMinecraft1().getConnection().getPlayerInfo(addPlayerData.getProfile().getId()) == null && !AntiVanish.M(this.k, addPlayerData.getProfile().getId()) && AntiVanish.M(this.k).M(1000L)) {
                    s.M().M(new StringBuilder().insert(0, YI.M(addPlayerData.getProfile().getId())).append(" is vanished.").toString());
                    AntiVanish.M(this.k).M();
                }
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
