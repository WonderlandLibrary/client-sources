package com.client.glowclient.events;

import com.client.glowclient.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;

@Cancelable
public class EventClientPacket extends Cd
{
    public EventClientPacket(final Packet<?> packet) {
        super(packet);
    }
}
