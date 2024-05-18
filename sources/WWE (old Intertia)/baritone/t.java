package baritone;

import baritone.api.cache.*;
import java.util.*;

public final class t implements IWaypoint
{
    private final String a;
    private final IWaypoint$Tag a;
    private final long a;
    private final et a;
    
    public t(final String s, final IWaypoint$Tag waypoint$Tag, final et et) {
        this(s, waypoint$Tag, et, System.currentTimeMillis());
    }
    
    t(final String a, final IWaypoint$Tag a2, final et a3, final long a4) {
        super();
        this.a = a;
        this.a = a2;
        this.a = a3;
        this.a = a4;
    }
    
    @Override
    public final int hashCode() {
        return this.a.hashCode() + this.a.hashCode() + this.a.hashCode();
    }
    
    @Override
    public final String getName() {
        return this.a;
    }
    
    @Override
    public final IWaypoint$Tag getTag() {
        return this.a;
    }
    
    @Override
    public final long getCreationTimestamp() {
        return this.a;
    }
    
    @Override
    public final et getLocation() {
        return this.a;
    }
    
    @Override
    public final String toString() {
        return this.a + " " + this.a.toString() + " " + new Date(this.a).toString();
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof IWaypoint)) {
            return false;
        }
        final IWaypoint waypoint = (IWaypoint)o;
        return this.a.equals(waypoint.getName()) && this.a == waypoint.getTag() && this.a.equals((Object)waypoint.getLocation());
    }
}
