package com.client.glowclient;

import com.google.common.cache.*;
import net.minecraft.network.*;

public final class Jd extends CacheLoader<Packet, Boolean>
{
    public Boolean load(final Packet packet) throws Exception {
        return false;
    }
    
    public Object load(final Object o) throws Exception {
        return this.load((Packet)o);
    }
    
    public Jd() {
        super();
    }
}
