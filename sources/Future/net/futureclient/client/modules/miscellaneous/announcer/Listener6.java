package net.futureclient.client.modules.miscellaneous.announcer;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.Announcer;
import net.futureclient.client.Df;
import net.futureclient.client.n;

public class Listener6 extends n<Df>
{
    public final Announcer k;
    
    public Listener6(final Announcer k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Df df) {
        if (Announcer.i(this.k).M()) {
            try {
                if (Announcer.b(this.k).containsKey(df.M().getDisplayName())) {
                    Announcer.b(this.k).put(df.M().getDisplayName(), Announcer.b(this.k).get(df.M().getDisplayName()) + 1);
                    return;
                }
                Announcer.b(this.k).put(df.M().getDisplayName(), 1);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void M(final Event event) {
        this.M((Df)event);
    }
}
