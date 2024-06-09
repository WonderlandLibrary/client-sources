package com.client.glowclient;

import net.minecraftforge.fml.common.eventhandler.*;
import com.mojang.authlib.*;
import java.util.*;

public class yd extends Event
{
    private final R B;
    private final GameProfile b;
    
    public yd(final R b, final GameProfile b2) {
        super();
        Objects.requireNonNull(b2);
        this.B = b;
        this.b = b2;
    }
    
    public R getPlayerInfo() {
        return this.B;
    }
    
    public GameProfile getProfile() {
        return this.b;
    }
}
