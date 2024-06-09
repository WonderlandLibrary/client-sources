package com.client.glowclient;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;

public class Cd extends Event
{
    private final Packet<?> b;
    
    public Packet<?> getPacket() {
        return this.b;
    }
    
    public Cd(final Packet<?> b) {
        super();
        this.b = b;
    }
}
