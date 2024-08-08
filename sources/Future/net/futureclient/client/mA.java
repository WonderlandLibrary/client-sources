package net.futureclient.client;

import net.futureclient.client.modules.render.Waypoints;

public class mA extends XB
{
    public final Waypoints k;
    
    public mA(final Waypoints k, final String[] array) {
        this.k = k;
        super(array);
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length != 1) {
            return null;
        }
        final Xa m;
        if ((m = Waypoints.M(this.k, array[0])) == null) {
            return "Invalid waypoint entered.";
        }
        if (Waypoints.M(this.k, m)) {
            Waypoints.M(this.k, m);
        }
        return String.format("Destination has been set to &e%s&7.", m.M());
    }
    
    @Override
    public String M() {
        return "&e[point]";
    }
}
