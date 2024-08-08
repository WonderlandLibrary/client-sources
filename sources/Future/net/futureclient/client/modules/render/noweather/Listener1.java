package net.futureclient.client.modules.render.noweather;

import net.futureclient.client.events.Event;
import net.futureclient.loader.mixin.common.wrapper.IWorld;
import net.futureclient.client.events.EventUpdate;
import net.futureclient.client.modules.render.NoWeather;
import net.futureclient.client.lF;
import net.futureclient.client.n;

public class Listener1 extends n<lF>
{
    public final NoWeather k;
    
    public Listener1(final NoWeather k) {
        this.k = k;
        super();
    }
    
    public void M(final EventUpdate eventUpdate) {
        if (((IWorld)NoWeather.getMinecraft().world).getRainingStrength() > 1.273197475E-314) {
            String s = "Rain";
            if (((IWorld)NoWeather.getMinecraft1().world).getThunderingStrength() > 1.697596633E-314) {
                s = "Thunder";
            }
            this.k.e(String.format("NoWeather §7[§F%s§7]", s));
            return;
        }
        this.k.e("NoWeather §7[§FClear§7]");
    }
    
    public void M(final Event event) {
        this.M((EventUpdate)event);
    }
}
