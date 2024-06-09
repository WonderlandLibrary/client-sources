package com.client.glowclient;

import com.google.common.util.concurrent.*;
import javax.annotation.*;
import com.client.glowclient.utils.*;

public class se implements FutureCallback<R>
{
    public final Lg B;
    public final String b;
    
    public void onSuccess(@Nullable final R r) {
        if (r != null) {
            r.M(Wrapper.mc.player.getName(), (FutureCallback<R>)new rf(this, r));
        }
    }
    
    public se(final Lg b, final String b2) {
        this.B = b;
        this.b = b2;
        super();
    }
    
    public void onSuccess(@Nullable final Object o) {
        this.onSuccess((R)o);
    }
    
    public void onFailure(final Throwable t) {
    }
}
