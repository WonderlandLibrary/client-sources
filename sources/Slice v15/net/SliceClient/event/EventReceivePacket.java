package net.SliceClient.event;

import net.minecraft.network.Packet;

public class EventReceivePacket extends com.darkmagician6.eventapi.events.callables.EventCancellable {
  public Packet packet;
  
  public EventReceivePacket() {}
  
  public Packet getPacket() {
    return packet;
  }
  
  public void setPacket(Packet packet) {
    this.packet = packet;
  }
}
