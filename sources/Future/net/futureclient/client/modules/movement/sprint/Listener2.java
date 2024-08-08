package net.futureclient.client.modules.movement.sprint;

import net.futureclient.client.events.Event;
import net.futureclient.client.hC;
import net.futureclient.client.modules.movement.Sprint;
import net.futureclient.client.Hf;
import net.futureclient.client.n;

public class Listener2 extends n<Hf>
{
    public final Sprint k;
    
    public Listener2(final Sprint k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Hf hf) {
        if (((hC.OA)Sprint.M(this.k).M()).equals((Object)hC.OA.k) && Sprint.M(this.k)) {
            hf.e(true);
        }
    }
    
    public void M(final Event event) {
        this.M((Hf)event);
    }
}
