package net.futureclient.client.modules.miscellaneous.autoeat;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.AutoEat;
import net.futureclient.client.Df;
import net.futureclient.client.n;

public class Listener2 extends n<Df>
{
    public final AutoEat k;
    
    public Listener2(final AutoEat k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Df df) {
        if (df.M().equals((Object)AutoEat.getMinecraft12().player)) {
            AutoEat.M(this.k, true);
            AutoEat.M(this.k).e();
        }
    }
    
    public void M(final Event event) {
        this.M((Df)event);
    }
}
