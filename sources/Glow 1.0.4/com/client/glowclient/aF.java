package com.client.glowclient;

import com.google.common.util.concurrent.*;
import javax.annotation.*;
import net.minecraftforge.common.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class aF implements FutureCallback<R>
{
    public final R B;
    public final oE b;
    
    public void onSuccess(@Nullable final R r) {
        if (r != null) {
            MinecraftForge.EVENT_BUS.post((Event)EventChat.M(r, this.B, this.b.b));
        }
    }
    
    public void onFailure(final Throwable t) {
    }
    
    public aF(final oE b, final R b2) {
        this.b = b;
        this.B = b2;
        super();
    }
    
    public void onSuccess(@Nullable final Object o) {
        this.onSuccess((R)o);
    }
}
