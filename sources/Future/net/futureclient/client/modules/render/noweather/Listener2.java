package net.futureclient.client.modules.render.noweather;

import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.NoWeather;
import net.futureclient.client.Tf;
import net.futureclient.client.n;

public class Listener2 extends n<Tf>
{
    public final NoWeather k;
    
    public Listener2(final NoWeather k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Tf tf) {
        tf.M(true);
    }
    
    public void M(final Event event) {
        this.M((Tf)event);
    }
}
