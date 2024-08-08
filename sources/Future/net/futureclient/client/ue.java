package net.futureclient.client;

import java.util.Iterator;
import net.minecraft.network.play.server.SPacketTabComplete;
import java.util.List;
import java.util.ArrayList;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.events.Event;

public class ue extends n<we>
{
    public final je k;
    
    public ue(final je k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
    
    public void M(final EventPacket eventPacket) {
        je.M(this.k, new ArrayList());
        if (eventPacket.M() instanceof SPacketTabComplete) {
            final SPacketTabComplete sPacketTabComplete = (SPacketTabComplete)eventPacket.M();
            eventPacket.M(true);
            final String[] matches;
            final int length = (matches = sPacketTabComplete.getMatches()).length;
            int i = 0;
            int n = 0;
            while (i < length) {
                final String[] split;
                if ((split = matches[n].split(":")).length > 1 && !je.M(this.k).contains(split[0].substring(1))) {
                    je.M(this.k).add(split[0].substring(1));
                }
                i = ++n;
            }
            final StringBuilder sb;
            (sb = new StringBuilder()).append(String.format("Found plugins (%s): ", je.M(this.k).size()));
            Iterator<String> iterator2;
            final Iterator<String> iterator = iterator2 = je.M(this.k).iterator();
            while (iterator2.hasNext()) {
                final String s = iterator.next();
                iterator2 = iterator;
                sb.append(s);
                sb.append(", ");
            }
            final s m = s.M();
            final StringBuilder sb2 = new StringBuilder();
            final int n2 = 0;
            final int n3 = 0;
            final StringBuilder sb3 = sb;
            m.M(sb2.insert(n2, sb3.substring(n3, sb3.length() - 2)).append(".").toString());
            pg.M().M().e(this);
        }
    }
}
