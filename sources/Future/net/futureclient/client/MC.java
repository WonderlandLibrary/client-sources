package net.futureclient.client;

import net.futureclient.client.modules.render.Waypoints;

public class MC extends XB
{
    public final Waypoints k;
    
    public MC(final Waypoints k, final String[] array) {
        this.k = k;
        super(array);
    }
    
    @Override
    public String M() {
        return "&e[name]";
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length != 1) {
            return null;
        }
        final Xa e;
        if ((e = this.k.e(array[0])) == null) {
            return "Invalid waypoint entered.";
        }
        if (Waypoints.M(this.k, e)) {
            this.k.k.remove(e);
        }
        return String.format("Removed waypoint &e%s&7.", e.M());
    }
}
