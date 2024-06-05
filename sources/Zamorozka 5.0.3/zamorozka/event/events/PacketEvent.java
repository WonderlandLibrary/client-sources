package zamorozka.event.events;

import net.minecraft.network.Packet;
import zamorozka.event.Event;

public class PacketEvent extends Event
{
    private Packet packet;
    

    public PacketEvent(Packet packet)
    {
        this.packet = packet;
    }

    public Packet getPacket()
    {
        return packet;
    }

    public void setPacket(Packet packet)
    {
        this.packet = packet;
    }
}