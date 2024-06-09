package com.client.glowclient.events;

import com.client.glowclient.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;

@Cancelable
public class EventServerPacket extends Cd
{
    public EventServerPacket(final Packet<?> packet) {
        super(packet);
    }
}
