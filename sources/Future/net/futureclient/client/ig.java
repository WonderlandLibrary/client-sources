package net.futureclient.client;

import net.futureclient.client.modules.movement.Velocity;

public class ig extends XB
{
    public ig() {
        super(new String[] { "VelocityPercentage", "velocity%", "%", "Vel", "Velocity_reduction", "Reduce", "Reduction", "Lower" });
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length != 1) {
            return null;
        }
        final String s = array[0];
        final Velocity velocity = (Velocity)pg.M().M().M((Class)ad.class);
        if (s == null) {
            return "No number entered.";
        }
        if (velocity != null) {
            final Velocity velocity2 = velocity;
            velocity2.horizontal.M(Double.parseDouble(s));
            velocity2.vertical.M(Double.parseDouble(s));
        }
        return new StringBuilder().insert(0, "Horizontal and vertical percentage has been set to ").append(Double.parseDouble(s)).toString();
    }
    
    @Override
    public String M() {
        return "&e[number]";
    }
}
