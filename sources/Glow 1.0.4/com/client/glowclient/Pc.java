package com.client.glowclient;

import net.minecraft.network.*;
import java.util.concurrent.*;
import com.google.common.cache.*;

public class pc
{
    private static final LoadingCache<Packet, Boolean> b;
    
    public static boolean M(final Packet packet) {
        try {
            return (boolean)pc.b.get((Object)packet);
        }
        catch (ExecutionException ex) {
            return false;
        }
    }
    
    public static void D(final Packet packet) {
        pc.b.invalidate((Object)packet);
    }
    
    public pc() {
        super();
    }
    
    static {
        b = CacheBuilder.newBuilder().expireAfterWrite(15L, TimeUnit.SECONDS).build((CacheLoader)new Jd());
    }
    
    public static void M(final Packet packet) {
        pc.b.put((Object)packet, (Object)true);
    }
}
