package com.client.glowclient;

import com.google.common.util.concurrent.*;
import net.minecraft.network.play.server.*;
import javax.annotation.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class yE implements FutureCallback<R>
{
    public final SPacketPlayerListItem A;
    public final Lg B;
    public final SPacketPlayerListItem$AddPlayerData b;
    
    public yE(final Lg b, final SPacketPlayerListItem a, final SPacketPlayerListItem$AddPlayerData b2) {
        this.B = b;
        this.A = a;
        this.b = b2;
        super();
    }
    
    public void onFailure(final Throwable t) {
    }
    
    public void onSuccess(@Nullable final Object o) {
        this.onSuccess((R)o);
    }
    
    public void onSuccess(@Nullable final R r) {
        if (r != null) {
            switch (KF.b[this.A.getAction().ordinal()]) {
                case 1: {
                    final EventBus event_BUS = MinecraftForge.EVENT_BUS;
                    while (false) {}
                    event_BUS.post((Event)new Qf(r, this.b.getProfile()));
                }
                case 2: {
                    MinecraftForge.EVENT_BUS.post((Event)new cg(r, this.b.getProfile()));
                    break;
                }
            }
        }
    }
}
