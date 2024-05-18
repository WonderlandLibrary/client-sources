package net.SliceClient.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class PacketRecieved
  extends EventCancellable
{
  private boolean cancel;
  private Packet packet;
  
  public PacketRecieved(Packet p_channelRead0_2_)
  {
    packet = p_channelRead0_2_;
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
