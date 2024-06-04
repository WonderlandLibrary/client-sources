package yung.purity.api.events;

import net.minecraft.network.Packet;
import yung.purity.api.Event;











public class EventPacket
  extends Event
{
  private Packet packet;
  
  public EventPacket(Packet packet, byte type)
  {
    this.packet = packet;
    setType(type);
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
