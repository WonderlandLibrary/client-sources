package com.client.glowclient;

import com.google.common.util.concurrent.*;
import javax.annotation.*;
import net.minecraftforge.common.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ef implements FutureCallback<R>
{
    public final Lg B;
    public final String b;
    
    public ef(final Lg b, final String b2) {
        this.B = b;
        this.b = b2;
        super();
    }
    
    public void onSuccess(@Nullable final Object o) {
        this.onSuccess((R)o);
    }
    
    public void onFailure(final Throwable t) {
    }
    
    public void onSuccess(@Nullable final R r) {
        if (r != null) {
            MinecraftForge.EVENT_BUS.post((Event)EventChat.M(r, this.b));
        }
    }
}
