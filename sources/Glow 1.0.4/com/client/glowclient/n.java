package com.client.glowclient;

import net.minecraft.entity.*;

public abstract class N implements Comparable<N>
{
    public boolean D(final Entity entity) {
        return false;
    }
    
    public int M(final N n) {
        return this.M().compareTo(n.M());
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.M((N)o);
    }
    
    public V D(final Entity entity) {
        try {
            return this.M(entity);
        }
        catch (Throwable t) {
            return V.B;
        }
    }
    
    public VB M() {
        return VB.G;
    }
    
    public abstract V M(final Entity p0);
    
    public abstract boolean M(final Entity p0);
    
    public N() {
        super();
    }
}
