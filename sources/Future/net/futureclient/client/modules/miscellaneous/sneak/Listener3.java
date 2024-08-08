package net.futureclient.client.modules.miscellaneous.sneak;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.Sneak;
import net.futureclient.client.fg;
import net.futureclient.client.n;

public class Listener3 extends n<fg>
{
    public final Sneak k;
    
    public Listener3(final Sneak k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final fg fg) {
        if (Sneak.M(this.k).M()) {
            fg.e(true);
        }
    }
    
    public void M(final Event event) {
        this.M((fg)event);
    }
}
