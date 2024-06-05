package net.shoreline.client.impl.event.network;

import net.shoreline.client.api.event.Event;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;

public class InventoryEvent extends Event
{
    private final InventoryS2CPacket packet;

    public InventoryEvent(InventoryS2CPacket packet)
    {
        this.packet = packet;
    }

    public InventoryS2CPacket getPacket()
    {
        return packet;
    }
}
