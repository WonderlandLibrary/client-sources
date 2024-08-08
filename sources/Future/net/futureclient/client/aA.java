package net.futureclient.client;

import net.futureclient.client.modules.render.Waypoints;

public class aA extends XB
{
    public final Waypoints k;
    
    public aA(final Waypoints k, final String[] array) {
        this.k = k;
        super(array);
    }
    
    @Override
    public String M() {
        return null;
    }
    
    @Override
    public String M(final String[] array) {
        if (Waypoints.M(this.k) == null) {
            return "No destination found.";
        }
        Waypoints.M(this.k, (Xa)null);
        return "Destination removed.";
    }
}
