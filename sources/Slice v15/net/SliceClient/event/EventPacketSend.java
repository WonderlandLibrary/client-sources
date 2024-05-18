package net.SliceClient.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;



public class EventPacketSend
  extends EventCancellable
{
  private boolean cancel;
  public Packet packet;
  
  public EventPacketSend(Packet packet)
  {
    this.packet = packet;
  }
  

  public Packet getPacket()
  {
    return packet;
  }
  
  public boolean isCancelled()
  {
    return cancel;
  }
  
  public void setCancelled(boolean cancel)
  {
    this.cancel = cancel;
  }
  
  public void setPacket(Packet packet)
  {
    this.packet = packet;
  }
}
