package net.minecraft.util;

import com.google.common.base.*;
import java.util.*;
import com.google.common.collect.*;

public enum Plane implements Predicate, Iterable
{
    HORIZONTAL("HORIZONTAL", 0, "HORIZONTAL", 0), 
    VERTICAL("VERTICAL", 1, "VERTICAL", 1);
    
    private static final Plane[] $VALUES;
    
    private Plane(final String p_i46392_1_, final int p_i46392_2_, final String p_i46013_1_, final int p_i46013_2_) {
    }
    
    public EnumFacing[] facings() {
        switch (SwitchPlane.PLANE_LOOKUP[this.ordinal()]) {
            case 1: {
                return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };
            }
            case 2: {
                return new EnumFacing[] { EnumFacing.UP, EnumFacing.DOWN };
            }
            default: {
                throw new Error("Someone's been tampering with the universe!");
            }
        }
    }
    
    public EnumFacing random(final Random rand) {
        final EnumFacing[] var2 = this.facings();
        return var2[rand.nextInt(var2.length)];
    }
    
    public boolean apply(final EnumFacing facing) {
        return facing != null && facing.getAxis().getPlane() == this;
    }
    
    public Iterator iterator() {
        return (Iterator)Iterators.forArray((Object[])this.facings());
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.apply((EnumFacing)p_apply_1_);
    }
    
    static {
        $VALUES = new Plane[] { Plane.HORIZONTAL, Plane.VERTICAL };
    }
}
