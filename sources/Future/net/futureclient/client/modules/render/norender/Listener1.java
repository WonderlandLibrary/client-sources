package net.futureclient.client.modules.render.norender;

import net.futureclient.client.oC;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.NoRender;
import net.futureclient.client.Bg;
import net.futureclient.client.n;

public class Listener1 extends n<Bg>
{
    public final NoRender k;
    
    public Listener1(final NoRender k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((Bg)event);
    }
    
    @Override
    public void M(final Bg bg) {
        bg.M(((oC.YA)NoRender.M(this.k).M()).equals((Object)oC.YA.k));
    }
}
