package com.client.glowclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.*;
import com.google.common.base.*;
import javax.annotation.*;

public class EventChat extends Event
{
    private final R A;
    private final R B;
    private final String b;
    
    public R getSender() {
        return this.B;
    }
    
    public EventChat(final R b, final String s, final R a) {
        super();
        this.B = b;
        this.b = Strings.nullToEmpty(s);
        this.A = a;
    }
    
    public boolean isWhispering() {
        return this.A != null;
    }
    
    public static EventChat M(final R r, final String s) {
        return new EventChat(r, s, null);
    }
    
    public String getMessage() {
        return this.b;
    }
    
    public static EventChat M(final R r, final R r2, final String s) {
        return new EventChat(r, s, r2);
    }
    
    @Nullable
    public R getReceiver() {
        return this.A;
    }
}
