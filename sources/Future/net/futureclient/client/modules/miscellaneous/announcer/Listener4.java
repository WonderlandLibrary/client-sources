package net.futureclient.client.modules.miscellaneous.announcer;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.Announcer;
import net.futureclient.client.aD;
import net.futureclient.client.n;

public class Listener4 extends n<aD>
{
    public final Announcer k;
    
    public Listener4(final Announcer k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final aD ad) {
        if (Announcer.M(this.k).M()) {
            try {
                if (Announcer.M(this.k).containsKey(ad.M().getDisplayName())) {
                    Announcer.M(this.k).put(ad.M().getDisplayName(), Announcer.M(this.k).get(ad.M().getDisplayName()) + 1);
                    return;
                }
                Announcer.M(this.k).put(ad.M().getDisplayName(), 1);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void M(final Event event) {
        this.M((aD)event);
    }
}
