package net.futureclient.client.modules.miscellaneous.antivanish;

import java.util.Iterator;
import net.futureclient.client.YI;
import net.futureclient.client.s;
import java.util.UUID;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.AntiVanish;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener2 extends n<lF>
{
    public final AntiVanish k;
    
    public Listener2(final AntiVanish k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
    
    public void M(final EventUpdate eventUpdate) {
        for (final UUID uuid : AntiVanish.M(this.k)) {
            if (AntiVanish.getMinecraft().getConnection().getPlayerInfo(uuid) != null) {
                if (AntiVanish.M(this.k).M(1000L)) {
                    s.M().M(new StringBuilder().insert(0, YI.M(uuid)).append(" is no longer vanished.").toString());
                    AntiVanish.M(this.k).M();
                }
                AntiVanish.M(this.k).remove(uuid);
            }
        }
    }
}
