package net.minecraft.util;

import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;

public enum Axis implements Predicate, IStringSerializable
{
    X("X", 0, "X", 0, "x", Plane.HORIZONTAL), 
    Y("Y", 1, "Y", 1, "y", Plane.VERTICAL), 
    Z("Z", 2, "Z", 2, "z", Plane.HORIZONTAL);
    
    private static final Map NAME_LOOKUP;
    private static final Axis[] $VALUES;
    private static final Axis[] $VALUES$;
    private final String name;
    private final Plane plane;
    
    private Axis(final String p_i46390_1_, final int p_i46390_2_, final String p_i46015_1_, final int p_i46015_2_, final String name, final Plane plane) {
        this.name = name;
        this.plane = plane;
    }
    
    public static Axis byName(final String name) {
        return (name == null) ? null : Axis.NAME_LOOKUP.get(name.toLowerCase());
    }
    
    public String getName2() {
        return this.name;
    }
    
    public boolean isVertical() {
        return this.plane == Plane.VERTICAL;
    }
    
    public boolean isHorizontal() {
        return this.plane == Plane.HORIZONTAL;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    public boolean apply(final EnumFacing facing) {
        return facing != null && facing.getAxis() == this;
    }
    
    public Plane getPlane() {
        return this.plane;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.apply((EnumFacing)p_apply_1_);
    }
    
    static {
        NAME_LOOKUP = Maps.newHashMap();
        $VALUES = new Axis[] { Axis.X, Axis.Y, Axis.Z };
        $VALUES$ = new Axis[] { Axis.X, Axis.Y, Axis.Z };
        for (final Axis var4 : values()) {
            Axis.NAME_LOOKUP.put(var4.getName2().toLowerCase(), var4);
        }
    }
}
