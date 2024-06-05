package zamorozka.event.events;

import net.minecraft.network.Packet;
import zamorozka.event.Event;

public class EventNetworkPacketEvent extends Event {
    public Packet m_Packet;
    
    public EventNetworkPacketEvent(Packet p_Packet)
    {
        super();
        m_Packet = p_Packet;
    }
    
    public Packet GetPacket()
    {
        return m_Packet;
    }
    
    public Packet getPacket()
    {
        return m_Packet;
    }
}